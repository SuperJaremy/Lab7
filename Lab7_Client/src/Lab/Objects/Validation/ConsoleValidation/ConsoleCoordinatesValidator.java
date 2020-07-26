package Lab.Objects.Validation.ConsoleValidation;

import Lab.Objects.Coordinates;
import Lab.Objects.Validation.CoordinatesValidator;
import Lab.Service.SkipBox;

import java.util.Scanner;

class ConsoleCoordinatesValidator extends CoordinatesValidator {
    private String skip(Scanner input){
        SkipBox sb = new SkipBox();
        if(sb.requestSkip(input)) {
            System.out.println("Команда прервана");
            throw new NullPointerException();
        }
        return sb.getLine();
    }
    @Override
    protected Coordinates Validate(){
        Scanner input = new Scanner(System.in);
        boolean success;
        System.out.println("Пожалуйста, введите координату x");
        do{
            success=checkX(skip(input));
        }while(!success);
        System.out.println("Пожалуйста, введите координату y");
        do{
            success=checkY(skip(input));
        }while(!success);
        return build();
    }
}
