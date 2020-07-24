package Lab.Commands;

import Lab.Service.Answer;
import Lab.Service.Database;
import Lab.Service.Work;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class Clear extends Command{
    private final static Logger logger = LogManager.getLogger();
    {
        name="clear";
        description="очистить коллекцию";
        rewrite=true;
    }
    @Override
    public String describe() {
        return name+": "+description;
    }

    @Override
    public Answer act(Meta meta, Work work) {
        try(Database db = new Database()){
            db.clear(meta.getUsername());
            return new Answer("Коллекция очищена",true,exit);
        }
        catch (SQLException e){
            logger.error("Ошибка соединения с базой данных");
            return new Answer("Ошибка приложения",false,true);
        }
    }
}
