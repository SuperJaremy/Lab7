package Lab.Service;

import java.util.Scanner;

public class Authorizator {
    private static String username=null;
    private static String password=null;
    public static boolean authorize(){
        SkipBox sb = new SkipBox();
        Scanner input = new Scanner(System.in);
        boolean success=false;
        do {
            System.out.println("Введите логин");
            if (sb.requestSkip(input))
                return false;
            String line = sb.getLine();
            if (line.length() > 0){
                username=line;
                success=true;
            }
        }while(!success);
        success=false;
        do{
            System.out.println("Введите пароль");
            if (sb.requestSkip(input))
                return false;
            String line = sb.getLine();
            if (line.length() > 0){
                password=line;
                success=true;
            }
        }while (!success);
        return true;
    }

    public static String getUsername(){
        return username;
    }
    public static String getPassword(){
        return password;
    }
}
