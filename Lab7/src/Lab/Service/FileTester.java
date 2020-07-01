package Lab.Service;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Path;


/**
 * Класс для проверки файла на существование, чтение и запись
 */
public class FileTester {
    private final static Logger logger = LogManager.getLogger(FileTester.class);
    static boolean TestFileToExist(Path path){
        File file=path.toFile();
        if(!file.exists()){
            logger.error("Файл не существует");
            return false;
        }
        else return true;
    }
    static public boolean TestFileToRead(Path path) {
        if (TestFileToExist(path)) {
            File file = path.toFile();
            if (!file.canRead()) {
                logger.error("Недостаточно прав для чтения файла");
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
                logger.error("Недостаточно прав для записи в файл");
                return false;
            }
            else return true;
        }
        else return false;
    }

}
