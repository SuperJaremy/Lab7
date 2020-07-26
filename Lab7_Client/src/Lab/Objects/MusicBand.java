package Lab.Objects;


import java.io.Serializable;
import java.util.Date;


public class MusicBand implements Serializable {
    private final static long serialVersionUID=9878675667368546L;
    private String name;
    private Coordinates coordinates;
    private Integer numberOfParticipants;
    private long albumsCount;
    private java.util.Date establishmentDate;
    private MusicGenre genre;
    private Album bestAlbum;
    public MusicBand(String name, Coordinates coordinates, Integer numberOfParticipants,
    long albumsCount, Date establishmentDate, MusicGenre genre, Album bestAlbum){
        this.name=name;
        this.coordinates=coordinates;
        this.numberOfParticipants=numberOfParticipants;
        this.albumsCount=albumsCount;
        this.establishmentDate=establishmentDate;
        this.genre=genre;
        this.bestAlbum=bestAlbum;
    }

    @Override
    public String toString() {
        return "MusicBand{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", numberOfParticipants=" + numberOfParticipants +
                ", albumsCount=" + albumsCount +
                ", establishmentDate=" + establishmentDate +
                ", genre=" + genre +
                ", bestAlbum=" + bestAlbum +
                '}';
    }
}
