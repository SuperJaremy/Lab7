package Lab.Objects.Validation;

import Lab.Objects.Album;
import Lab.Objects.Coordinates;
import Lab.Objects.MusicBand;
import Lab.Objects.MusicGenre;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class MusicBandValidator {
    protected String name;
    protected Coordinates coordinates;
    protected Integer numberOfParticipants;
    protected long albumsCount;
    protected Date establishmentDate;
    protected MusicGenre genre;
    protected Album bestAlbum;
    protected int lines=0;
    public int getLines(){
        return lines;
    }

    protected boolean checkName(String name){
        if(name!=null) {
            this.name=name;
            lines++;
            return true;
        }
        else {
            System.out.println("У группы должно быть имя. Повторите ввод");
            return false;
        }
    }
    protected boolean checkNumberOfParticipants(String numberOfParticipants){
        if(numberOfParticipants!=null) {
            int i;
            try {
                i = Integer.parseInt(numberOfParticipants);
            } catch (NumberFormatException e) {
                System.out.println("Количество участников должно быть натуральным" +
                        " числом");
                return false;
            }
            if (i <= 0) {
                System.out.println("Количество участников должно быть натуральным" +
                        " числом");
                return false;
            }
            this.numberOfParticipants = i;
            lines++;
            return true;
        }
        else{
            this.numberOfParticipants=null;
            lines++;
            return true;
        }
    }
    protected boolean checkAlbumsCount(String albumsCount){
        if(albumsCount!=null) {
            int i;
            try {
                i = Integer.parseInt(albumsCount);
            } catch (NumberFormatException e){
                System.out.println("Количество альбомов должно быть натуральным" +
                        " числом");
                return false;
            }
            if(i<=0) {
                System.out.println("Количество альбомов должно быть натуральным" +
                        " числом");
                return false;
            }
            this.albumsCount=i;
            lines++;
            return true;
        }
        System.out.println("Количество альбомов должно быть натуральным" +
                " числом");
        return false;
    }
    protected boolean checkEstablishmentDate(String establishmentDate){
        if(establishmentDate!=null){
            Date date;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            try{
                date=sdf.parse(establishmentDate);
            }
            catch (ParseException e){
                System.out.println("Неверный формат даты создания");
                return false;
            }
            this.establishmentDate=date;
            lines++;
            return true;
        }
        else{
            this.establishmentDate=null;
            lines++;
            return true;
        }
    }
    protected boolean checkGenre(String genre){
        if(genre!=null) {
            MusicGenre g;
            try {
                g = MusicGenre.valueOf(genre);
            } catch (IllegalArgumentException e) {
                System.out.println("Жанр введён неверно. Выберите жанр из списка:\n");
                MusicGenre.list();
                return false;
            }
            this.genre = g;
            lines++;
            return true;
        }
        System.out.println("Жанр введён неверно. Выберите жанр из списка:\n");
        MusicGenre.list();
        return false;
    }
    protected void checkCoordinates(CoordinatesValidator cv){
        this.coordinates=cv.Validate();
        lines+=2;
    }
    protected void checkCoordinates(CoordinatesValidator cv, String x, String y){
        this.coordinates=cv.Validate(x,y);
        lines+=2;
    }
    protected void checkAlbum(AlbumValidator av){
        bestAlbum=av.Validate();
        lines+=av.lines;
    }
    protected void checkAlbum(AlbumValidator av, String name, String length){
        this.bestAlbum=av.Validate(name, length);
        lines+=av.lines;
    }
    protected MusicBand build(){
        return new MusicBand(name,coordinates,numberOfParticipants,
                albumsCount, establishmentDate, genre, bestAlbum);
    }
    protected MusicBand Validate(){return null;}
    protected MusicBand Validate(String name, String x, String y,
                                 String numberOfParticipants, String albumsCount,
                                 String establishmentDate, String genre,
                                 String albumName, String length){
        return null;
    }
}
