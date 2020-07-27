package Lab.Service;

import Lab.Objects.Album;
import Lab.Objects.Coordinates;
import Lab.Objects.MusicBand;
import Lab.Objects.MusicGenre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;


public class Database implements  AutoCloseable{

    private static Vector<MusicBand> V = new Vector<>();
    private final static Logger logger = LogManager.getLogger(Database.class);
    private Connection connection=null;
    private PreparedStatement upload=null;
    private PreparedStatement addBand=null;
    private PreparedStatement addCoordinates=null;
    private PreparedStatement addAlbum=null;
    private PreparedStatement addOwner=null;
    private PreparedStatement listIds=null;
    private PreparedStatement removeBand=null;
    private PreparedStatement removeAlbum = null;
    private PreparedStatement removeCoordinates = null;
    private PreparedStatement removeOwner = null;
    private PreparedStatement updateBand=null;
    private PreparedStatement updateCoordinates=null;
    private PreparedStatement updateAlbum=null;
    private PreparedStatement getId=null;
    private final static ReentrantLock lock = new ReentrantLock(true);
    private static String username="";
    private static String password="";

    public static boolean authorize(String username, String password){
        try{
            DriverManager.getConnection("jdbc:postgresql://localhost:15683/MusicBands",
                    username, password);
            Database.username= username;
            Database.password=password;
            return true;
        }
        catch (SQLException e){
            logger.warn("Неверный логин и/или пароль");
            return false;
        }
    }

    public Database(){
        try{
            Class.forName("org.postgresql.Driver");
            connection=DriverManager.getConnection("jdbc:postgresql://localhost:15683/MusicBands",
                    username, password);
            upload=connection.prepareStatement("SELECT * FROM ((public.\"MusicBands\" INNER " +
                    "JOIN public.\"Coordinates\" USING(music_band_id))" +
                    " LEFT JOIN public.\"Albums\" USING (music_band_id))");
            addBand=connection.prepareStatement("INSERT INTO public.\"MusicBands\" VALUES(" +
                    "nextval('public.\"Assign_id\"'),?," +
                    "TO_DATE(?,'DD.MM.YYYY'),?,?,TO_DATE(?,'DD.MM.YYYY'),?::music_genre)");
            addCoordinates = connection.prepareStatement("INSERT INTO public.\"Coordinates\"" +
                    "VALUES(currval('public.\"Assign_id\"'),?,?)");
            addAlbum = connection.prepareStatement("INSERT INTO public.\"Albums\"" +
                    "VALUES(currval('public.\"Assign_id\"'),?,?)");
            addOwner =connection.prepareStatement("INSERT INTO public.\"Owners\"" +
                    "VALUES(currval('public.\"Assign_id\"'),?)");
            removeBand = connection.prepareStatement("DELETE FROM public.\"MusicBands\" " +
                    "WHERE music_band_id=?");
            removeCoordinates = connection.prepareStatement("DELETE FROM public.\"Coordinates\"" +
                    "WHERE music_band_id=?");
            removeAlbum = connection.prepareStatement("DELETE FROM public.\"Albums\"" +
                    "WHERE music_band_id=?");
            removeOwner = connection.prepareStatement("DELETE FROM public.\"Owners\"" +
                    "WHERE music_band_id=?");
            listIds = connection.prepareStatement("SELECT music_band_id FROM public.\"Owners\"" +
                    "WHERE username = ?");
            getId = connection.prepareStatement("SELECT currval('public.\"Assign_id\"')");
            updateBand = connection.prepareStatement("UPDATE public.\"MusicBands\" " +
                    "SET name=?,number_of_participants=?,albums_count=?," +
                    "establishment_date=TO_DATE(?,'DD.MM.YYYY'),genre=?::music_genre " +
                    "WHERE music_band_id=?");
            updateCoordinates = connection.prepareStatement("UPDATE public.\"Coordinates\" " +
                    "SET x=?,y=? WHERE music_band_id=?");
            updateAlbum = connection.prepareStatement("UPDATE public.\"Albums\" " +
                    "SET album_name=?,length=? WHERE music_band_id=?");
            logger.info("Установлено соединение с базой данных");
        }
        catch (SQLException e){
            logger.error("Не удалось установить соединение с базой "+ e.getLocalizedMessage());
        }
        catch (ClassNotFoundException e){
            logger.error("Не найден драйвер базы данных");
        }
    }

    public boolean uploadCollection(){
        if(upload!=null){
            lock.lock();
            try{
                Vector<MusicBand> tempV=new Vector<>();
                ResultSet res = upload.executeQuery();
                while(res.next()){
                    Integer nop = res.getObject("number_of_participants")==null?
                            null:res.getInt("number_of_participants");
                    tempV.add(new MusicBand(res.getInt("music_band_id"),
                            res.getString("name"), Coordinates.getCoordinates(
                                    res.getFloat("x"), res.getLong("y")),
                            Helper.getLocalDateFromDate(res.getDate("creation_date")),
                            nop,
                            res.getLong("albums_count"),
                            res.getDate("establishment_date"),
                            MusicGenre.valueOf(res.getString("genre")), Album.getAlbum(
                                    res.getString("album_name"),
                            res.getLong("length")
                    )));
                }
                V=tempV;
                return true;
            }
            catch (SQLException e){
                logger.error("Не удалось обновить коллекцию: "+e.getLocalizedMessage());
            }
            finally {
                lock.unlock();
            }
        }
        return false;
    }

    public static Vector<MusicBand> GetCollection(){
        lock.lock();
        try {
            return V;
        }
        finally {
            lock.unlock();
        }
    }

    public boolean Add(MusicBand mb, String login){
        lock.lock();
        try {
            connection.setAutoCommit(false);
            connection.commit();
            addBand.setString(1, mb.getName());
            addBand.setString(2, MusicBand.formatter.format(mb.getCreationDate()));
            if (mb.getNumberOfParticipants() != null)
                addBand.setInt(3, mb.getNumberOfParticipants());
            else
                addBand.setNull(3, Types.INTEGER);
            addBand.setLong(4, mb.getAlbumsCount());
            if(mb.getEstablishmentDate()!=null)
                addBand.setString(5, MusicBand.sdf.format(mb.getEstablishmentDate()));
            else
                addBand.setNull(5,Types.VARCHAR);
            addBand.setString(6, mb.getGenre().toString());
            addBand.execute();
            addCoordinates.setFloat(1, mb.getCoordinates().getX());
            addCoordinates.setLong(2, mb.getCoordinates().getY());
            addCoordinates.execute();
            if (mb.getBestAlbum() != null) {
                addAlbum.setString(1, mb.getBestAlbum().getName());
                addAlbum.setLong(2, mb.getBestAlbum().getLength());
                addAlbum.execute();
            }
            addOwner.setString(1,login);
            addOwner.execute();
            ResultSet res = getId.executeQuery();
            Integer id=null;
            if(res.next())
                id = res.getInt(1);
            if(id!=null)
                mb.setId(id);
            else {
                logger.error("Неверно получен id");
                connection.rollback();
            }
            V.add(mb);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        }
        catch (SQLException e){
            logger.error("Неверное SQL выражения для добавления элемента "+e.getMessage());
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            }
            catch (SQLException err){
                logger.error("Ошибка отката транзакции");
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }

    public boolean removeId(Integer id, String username){
        lock.lock();
        Vector<Integer> possibleIds = findIds(username);
        if(!possibleIds.contains(id))
            return false;
        try {
            connection.setAutoCommit(false);
            connection.commit();
            removeBand.setInt(1,id);
            removeCoordinates.setInt(1,id);
            removeAlbum.setInt(1,id);
            removeOwner.setInt(1,id);
            removeBand.execute();
            removeCoordinates.execute();
            removeAlbum.execute();
            removeOwner.execute();
            connection.commit();
            connection.setAutoCommit(true);
            V.remove(getById(id));
            return true;
        }
        catch (SQLException e){
            logger.error("Не удалось удалить элемент из таблицы");
            e.printStackTrace();
            if(connection!=null){
                try{
                    connection.rollback();
                }
                catch (SQLException err){
                    logger.error("Ошибка отката транзакции");
                    err.printStackTrace();
                }
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }

    public boolean clear(String username){
        lock.lock();
        Vector<Integer> ids = findIds(username);
        try{
            connection.setAutoCommit(false);
            for(int i : ids){
                removeBand.setInt(1,i);
                removeBand.addBatch();
                removeCoordinates.setInt(1,i);
                removeCoordinates.addBatch();
                removeAlbum.setInt(1,i);
                removeAlbum.addBatch();
                removeOwner.setInt(1,i);
                removeOwner.addBatch();
            }
            connection.commit();
            if(ids.size()>0) {
                removeBand.executeBatch();
                removeCoordinates.executeBatch();
                removeAlbum.executeBatch();
                removeOwner.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
            }
            for(int i :ids)
                V.remove(getById(i));
            return true;
        }
        catch (SQLException e){
            logger.error("Не удалось удалить все элементы пользователя");
            e.printStackTrace();
            if(connection!=null) {
                try {
                    connection.rollback();
                } catch (SQLException err) {
                    logger.error("Ошибка отката транзакции");
                    e.printStackTrace();
                }
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }

    public boolean updateId(Integer id, MusicBand mb, String username){
        lock.lock();
        Vector<Integer> possibleIds = findIds(username);
        if(!possibleIds.contains(id))
            return false;
        try {
            connection.setAutoCommit(false);
            connection.commit();
            updateBand.setString(1, mb.getName());
            if (mb.getNumberOfParticipants() != null)
                updateBand.setInt(2, mb.getNumberOfParticipants());
            else
                updateBand.setNull(2, Types.INTEGER);
            updateBand.setLong(3, mb.getAlbumsCount());
            if(mb.getEstablishmentDate()!=null)
                updateBand.setString(4, MusicBand.sdf.format(mb.getEstablishmentDate()));
            else
                updateBand.setNull(4,Types.VARCHAR);
            updateBand.setString(5, mb.getGenre().toString());
            updateBand.setInt(6,id);
            updateBand.execute();
            updateCoordinates.setFloat(1, mb.getCoordinates().getX());
            updateCoordinates.setLong(2, mb.getCoordinates().getY());
            updateCoordinates.setInt(3,id);
            updateCoordinates.execute();
            if (mb.getBestAlbum() != null) {
                updateAlbum.setString(1, mb.getBestAlbum().getName());
                updateAlbum.setLong(2, mb.getBestAlbum().getLength());
                updateAlbum.setInt(3,id);
                updateAlbum.execute();
            }
            else{
                removeAlbum.setInt(1,id);
                removeAlbum.execute();
            }
            connection.commit();
            connection.setAutoCommit(true);
            getById(id).update(mb);
            return true;
            }
        catch (SQLException e){
            logger.error("Неверно обновляется ряд");
            e.printStackTrace();
            if(connection!=null){
                try{
                    connection.rollback();
                }
                catch (SQLException err){
                    logger.error("Ошибка отката транзакции");
                    err.printStackTrace();
                }
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }

    private Vector<Integer> findIds(String username){
        try {
            Vector<Integer> ids = new Vector<>();
            listIds.setString(1, username);
            ResultSet res = listIds.executeQuery();
            while(res.next())
                ids.add(res.getInt(1));
            return ids;
        }
        catch (SQLException e){
            logger.error("Ошибка поиска id");
            e.printStackTrace();
            return new Vector<>();
        }
    }

    private static MusicBand getById(int id){
        Optional<MusicBand> band = V.stream().
                filter((MusicBand mb)->mb.getId().equals(id)).findFirst();
        return band.orElse(null);
    }

    @Override
    public void close() throws SQLException {
        if(connection!=null)
            connection.close();
        if(upload!=null)
            upload.close();
        if(addBand!=null)
            addBand.close();
        if(addCoordinates!=null)
            addCoordinates.close();
        if(addAlbum!=null)
            addAlbum.close();
        if(addOwner!=null)
            addOwner.close();
        if(removeBand!=null)
            removeBand.close();
        if(listIds!=null)
            listIds.close();
        if(getId!=null)
            getId.close();
    }
}