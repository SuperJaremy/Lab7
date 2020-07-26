package Lab.Service;

import java.io.File;
import java.nio.file.Path;

/**
 * Класс для проверки файла на существование, чтение и запись
 */
public class FileTester {
    static boolean TestFileToExist(Path path){
        File file=path.toFile();
        if(!file.exists()){
            System.out.println("Файл не существует");
            return false;
        }
        else return true;
    }
    static public boolean TestFileToRead(Path path) {
        if (TestFileToExist(path)) {
            File file = path.toFile();
            if (!file.canRead()) {
                System.out.println("Недостаточно прав для чтения файла");
                return false;
            } else
                return true;
        }
        else return false;
    }
    static public boolean TestFileToWrite(Path path){
        if(TestFileToExist(path)){
            File file = path.toFile();
            if(!file.canWrite()){
                System.out.println("Недостаточно прав для записи в файл");
                return false;
            }
            else return true;
        }
        else return false;
    }

}
