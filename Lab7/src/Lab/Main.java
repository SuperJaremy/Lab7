package Lab;



import Lab.Objects.MusicBand;
import Lab.Service.Database;
import Lab.Service.Server;
import com.sun.deploy.security.SelectableSecurityManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;




public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        // write your code here
//        Server server = new Server();
//        boolean success = server.start();
//        if(!success){
//            logger.error("Ошибка в работе программы");
//        }
        Database.Connect();
        Database.getInstance();
        if(Database.updateCollection())
            for(MusicBand i : Database.GetCollection())
                logger.info(i);
        else
            logger.error("Не удалось обновить коллекцию");
    }
}
