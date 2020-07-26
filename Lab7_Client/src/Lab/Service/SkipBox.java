package Lab.Service;

import java.util.Scanner;

public class SkipBox {
    String line;
    public boolean requestSkip(Scanner input){
        System.out.println("Если вы хотите прервать выполнение команды, введите exit");
        line = input.nextLine().trim();
        if(line.equals("exit"))
            return true;
        return false;
    }

    public String getLine() {
        return line;
    }
}
