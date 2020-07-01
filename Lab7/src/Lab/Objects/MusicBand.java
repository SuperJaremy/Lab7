package Lab.Objects;



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MusicBand implements Comparable<MusicBand>, Serializable {
    private final static Logger logger = LogManager.getLogger(MusicBand.class);
    private final static long serialVersionUID=9878675667368546L;
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private Integer numberOfParticipants;
    private long albumsCount;
    private java.util.Date establishmentDate;
    private MusicGenre genre;
    private Album bestAlbum;
    public static final SimpleDateFormat sdf= new SimpleDateFormat("dd.MM.yyyy");
    static {
        sdf.setLenient(false);
    }
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    static private final Set<Integer> ids = new HashSet<>();
    private Integer size;
    public MusicBand() {
        coordinates=new Coordinates();
        bestAlbum = new Album();
        creationDate = LocalDate.now();
    }

    public MusicBand(Integer id, String name, Coordinates coordinates, LocalDate creationDate,
                     Integer numberOfParticipants, long albumsCount,
                     java.util.Date establishmentDate, MusicGenre genre, Album bestAlbum)
    {
        if(id!=null) {
            boolean uniqueId = !ids.contains(id);
            if (id > 0) {
                if(uniqueId)
                    this.id=id;
                else {
                    logger.error("id " + id + "повторяется");
                    throw new NullPointerException();
                }
            }
            else {
                logger.error("id объектов должен быть" +
                        " положительным целым числом." + id+" им не является");
                throw new NullPointerException();
            }
        }
        else{
            logger.error("Не задан id объекта");
            throw new NullPointerException();
        }
        if(name!=null&&name.length()!=0)
            this.name=name;
        else{
            logger.error("У объекта с id "+id+" должно быть имя");
            throw new NullPointerException();
        }
        if(coordinates!=null){
            if(coordinates.getX()!=null){
                if(coordinates.getX()>-801)
                    this.coordinates=coordinates;
                else{
                    logger.error("Координата x " +
                            "объекта с id "+id+"  должна быть больше -801");
                    throw new NullPointerException();
                }
            }
            else{
                logger.error("Координата x объекта с id "+id+"  должна быть задана");
                throw new NullPointerException();
            }
        }
        else{
            logger.error("Координаты объекта с id "+ id+" должны быть заданы");
            throw new NullPointerException();
        }
        if(creationDate!=null)
            this.creationDate=creationDate;
        else{
            logger.error("Дата создания объекта с id "+ id+" должна быть задана");
            throw new NullPointerException();
        }
        this.establishmentDate=establishmentDate;
        if(numberOfParticipants==null||numberOfParticipants>0){
            this.numberOfParticipants=numberOfParticipants;
        }
        else{
            logger.error("Количество участников группы объекта с id "+id+
                    " должно быть либо null, либо целым положительным числом");
            throw new NullPointerException();
        }
        if(albumsCount>0)
            this.albumsCount=albumsCount;
        else{
            logger.error("Количество альбомов объекта с id "+
                    id+" должно быть целым положительным числом");
            throw new NullPointerException();
        }
        if(genre!=null){
            this.genre=genre;
        }
        else{
            logger.error("Жанр объекта с id "+id+" должен быть выбран из списка:\n"
                    +MusicGenre.list());
            throw new NullPointerException();
        }
       if(bestAlbum==null)
           this.bestAlbum=null;
       else if(bestAlbum.getName()!=null){
           if(bestAlbum.getLength()>0){
               this.bestAlbum=bestAlbum;
           }
           else{
               logger.error("Длина альбома объекта с id "+id
                       +" должна быть целым положительным числом");
               throw new NullPointerException();
           }
       }
       else{
           logger.error("У лучшего альбома объекта с id "+id+" должно быть название");
           throw new NullPointerException();
       }
       size=name.length();
       if(this.bestAlbum!=null)
           size+=bestAlbum.getName().length();
       ids.add(id);
    }
    public Integer getNumberOfParticipants(){
        return numberOfParticipants;
    }
    public Integer getId(){
        return id;
    }

    public String getName() {
        return name;
    }



    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public long getAlbumsCount() {
        return albumsCount;
    }

    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Album getBestAlbum() {
        return bestAlbum;
    }

    @Override
    public int compareTo(MusicBand musicBand) {
        if(!this.equals(musicBand)) {
            Integer a,b;
            if(this.numberOfParticipants==null)
                a=0;
            else
                a=this.numberOfParticipants;
            if(musicBand.numberOfParticipants==null)
                b=0;
            else
                b=musicBand.numberOfParticipants;
            return a.compareTo(b);
        }
        return 0;
    }

    @Override
    public String toString() {
        String estDate=null;
        if(establishmentDate!=null)
            estDate=sdf.format(establishmentDate);
        return "Id=" + id +
                "\n Название группы: " + name +
                "\n Координаты:\n " + coordinates +
                "\n Дата создания элемента: " + creationDate.format(formatter) +
                "\n Количество участников: " + numberOfParticipants +
                "\n Количество альбомов: " + albumsCount +
                "\n Дата создания группы: " + estDate +
                "\n Жанр: " + genre +
                "\n Лучший альбом:\n " + bestAlbum +"\n";
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicBand musicBand = (MusicBand) o;
        return albumsCount == musicBand.albumsCount &&
                name.equals(musicBand.name) &&
                coordinates.equals(musicBand.coordinates) &&
                creationDate.equals(musicBand.creationDate) &&
                numberOfParticipants.equals(musicBand.numberOfParticipants) &&
                establishmentDate.equals(musicBand.establishmentDate) &&
                genre == musicBand.genre &&
                bestAlbum.equals(musicBand.bestAlbum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, numberOfParticipants, albumsCount, establishmentDate, genre, bestAlbum);
    }
    public static MusicBand create(MusicBand mb) throws Exception{
        Integer i= 1;
        while(i>0){
            if(!ids.contains(i)){
                MusicBand musicBand= new MusicBand(i,mb.name, mb.coordinates, LocalDate.now(), mb.numberOfParticipants,
                        mb.albumsCount,mb.establishmentDate,mb.genre,mb.bestAlbum);
                musicBand.size=musicBand.name.length()+(musicBand.bestAlbum!=null?
                        musicBand.bestAlbum.getName().length():0);
                return musicBand;
            }
            i++;
        }
        throw new Exception();
    }
    public void update(MusicBand mb){
        name=mb.name;
        coordinates=mb.coordinates;
        numberOfParticipants=mb.numberOfParticipants;
        albumsCount=mb.albumsCount;
        establishmentDate=mb.establishmentDate;
        genre=mb.genre;
        bestAlbum=mb.bestAlbum;
    }
    public static boolean idExists(Integer id){
        return ids.contains(id);
    }
    public static boolean removeId(Integer id){
        if(idExists(id)) {
            ids.remove(id);
            return true;
        }
        return false;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getTrueNumberOfParticipants(){
        return numberOfParticipants!=null?numberOfParticipants:0;
    }
}
