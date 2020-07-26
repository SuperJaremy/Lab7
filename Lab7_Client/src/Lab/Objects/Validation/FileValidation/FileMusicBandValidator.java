package Lab.Objects.Validation.FileValidation;

import Lab.Objects.MusicBand;
import Lab.Objects.Validation.MusicBandValidator;


public class FileMusicBandValidator extends MusicBandValidator {
    @Override
    public MusicBand Validate(String name, String x, String y,
                              String numberOfParticipants, String albumsCount,
                              String establishmentDate, String genre,
                              String albumName, String length){
        String line1=name.length()==0?null:name;
        String line2=numberOfParticipants.length()==0?null:numberOfParticipants;
        String line3=albumsCount.length()==0?null:albumsCount;
        String line4=establishmentDate.length()==0?null:establishmentDate;
        String line5=genre.length()==0?null:genre;
        if(checkName(line1)&&checkNumberOfParticipants(line2)&&
        checkAlbumsCount(line3)&&checkEstablishmentDate(line4)&&
        checkGenre(line5)){
            checkCoordinates(new FileCoordinatesValidator(),x,y);
            checkAlbum(new FileAlbumValidator(),albumName,length);
            return build();
        }
        throw new NullPointerException();
    }
}
