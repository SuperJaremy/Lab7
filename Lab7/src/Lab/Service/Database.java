package Lab.Service;

import Lab.Objects.Album;
import Lab.Objects.Coordinates;
import Lab.Objects.MusicBand;
import Lab.Objects.MusicGenre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Vector;


public class Database {
    static Vector<MusicBand> V = new Vector<>();
    private final static Logger logger = LogManager.getLogger(Database.class);
    private static class DatabaseHolder {
        static Database db = null;
    }

    public static void Connect(){
        DatabaseHolder.db = new Database();
    }

    public static Database getInstance(){
            return DatabaseHolder.db;
    }
    static Connection connection=null;
    static Statement statement=null;
    static PreparedStatement update;
    private Database(){
        try{
            connection=DriverManager.getConnection("jdbc:postgresql://localhost:50101/MusicBands",
                    "postgres",
                    "superPassword");
            statement=connection.createStatement();
            update=connection.prepareStatement("SELECT * FROM ((pubic.\"MusicBands\" INNER " +
                    "JOIN piblic.\"Coordinates\" USING(music_band_id))" +
                    " LEFT JOIN public.\"Albums\" USING (music_band_id))");
            logger.info("Установлено соединение с базой данных");
        }
        catch (SQLException e){
            logger.error("Не удалось установить соединение с базой");
        }
    }
    public static boolean updateCollection(){
        if(update!=null){
            try{
                Vector<MusicBand> tempV=new Vector<>();
                ResultSet res = update.executeQuery();
                while(res.next()){
                    tempV.add(new MusicBand(res.getInt("music_band_id"),
                            res.getString("name"), Coordinates.getCoordinates(
                                    res.getFloat("x"), res.getLong("y")),
                            Helper.localDateFromDate(res.getDate("creation_date")),
                            res.getInt("number_of_participants"),
                            res.getLong("albums_count"),
                            res.getDate("establishment_date"),
                            MusicGenre.valueOf(res.getString("genre")), Album.getAlbum(
                                    res.getString("album_name"),res.getLong("length")
                    )));
                }
                V=tempV;
                return true;
            }
            catch (SQLException e){
                logger.error("Не удалось обновить коллекцию: "+e.getSQLState());
            }
        }
        return false;
    }

    public static Vector<MusicBand> GetCollection(){
        return V;
    }
}
