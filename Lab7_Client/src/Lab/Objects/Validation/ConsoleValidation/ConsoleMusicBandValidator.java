package Lab.Objects.Validation.ConsoleValidation;

import Lab.Objects.MusicBand;
import Lab.Objects.MusicGenre;
import Lab.Objects.Validation.MusicBandValidator;
import Lab.Service.SkipBox;

import java.util.Scanner;

public class ConsoleMusicBandValidator extends MusicBandValidator {
    private String skip(Scanner input){
        SkipBox sb = new SkipBox();
        if(sb.requestSkip(input)) {
            System.out.println("Команда прервана");
            throw new NullPointerException();
        }
        return sb.getLine().length()==0?null:sb.getLine();
    }
    @Override
    public MusicBand Validate(){
        Scanner input = new Scanner(System.in);
        boolean success;
        System.out.println("Пожалуйста, введите имя");
        do{
            success=checkName(skip(input));
        }while(!success);
        checkCoordinates(new ConsoleCoordinatesValidator());
        System.out.println("Пожалуйста, введите количество участников");
        System.out.println("Это поле может быть пустым");
        do{
            success=checkNumberOfParticipants(skip(input));
        }while(!success);
        System.out.println("Пожалуйста, введите количество альбомов");
        do{
           success=checkAlbumsCount(skip(input));
        }while(!success);
        System.out.println("Пожалуйста, введите дату создания группы в формате ДД.ММ.ГГГГ");
        System.out.println("Это поле может быть пустым");
        do{
            success=checkEstablishmentDate(skip(input));
        }while(!success);
        System.out.println("Пожалуйста, выберите музыкальный жанр из представленных: ");
        MusicGenre.list();
        do{
            success=checkGenre(skip(input));
        }while(!success);
        System.out.println("Вспомните лучший альбом группы");
        checkAlbum(new ConsoleAlbumValidator());
        return build();
    }
}
