package Lab.Objects.Validation.ConsoleValidation;

import Lab.Objects.Album;
import Lab.Objects.Validation.AlbumValidator;
import Lab.Service.SkipBox;

import java.util.Scanner;

class ConsoleAlbumValidator extends AlbumValidator {
    private String skip(Scanner input){
        SkipBox sb = new SkipBox();
        if(sb.requestSkip(input)) {
            System.out.println("Команда прервана");
            throw new NullPointerException();
        }
        return sb.getLine().length()!=0?sb.getLine():null;
    }
    @Override
    protected Album Validate(){
        Scanner input = new Scanner(System.in);
        boolean success;
        System.out.println("Пожалуйста, введите название альбома");
        System.out.println("Это поле может остаться пустым");
        do{
            success=checkName(skip(input));
        }while(!success);
        if(!isNull){
            System.out.println("Пожалуйста, введите длительность альбома");
            do{
                success=checkLength(skip(input));
            }while(!success);
        }
        else
            checkLength(null);
        return build();
    }
}
