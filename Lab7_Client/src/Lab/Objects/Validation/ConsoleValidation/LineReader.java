package Lab.Objects.Validation.ConsoleValidation;

import java.util.Scanner;

class LineReader {
    static String readLine(Scanner input){
        String line = input.nextLine();
        return line.length()!=0 ? line.trim() : null;
    }
}
