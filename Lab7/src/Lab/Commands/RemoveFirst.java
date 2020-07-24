package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Database;
import Lab.Service.Work;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;


public class RemoveFirst extends Command {
    {
        name="remove_first";
        description="удалить первый элемент из коллекции";
        rewrite=true;
    }
    private final static Logger logger = LogManager.getLogger();
    @Override
    public String describe() {
        return name+": "+description;
    }

    @Override
    public Answer act(Meta meta, Work work) {
        Optional<MusicBand> MIN = Database.GetCollection().stream().min(Comparator.comparing(MusicBand::getSize));
        if(MIN.isPresent()){
            try(Database db = new Database()) {
                if(db.removeId(MIN.get().getId(),meta.getUsername()))
                    return new Answer("Элемент был успешно удалён", true, exit);
                else
                    return new Answer("Вы не можете удалить первый элемент",false,exit);
            }
            catch (SQLException e){
                logger.error("Ошибка соединения с базой данных");
                return new Answer("Ошибка приложения",false,true);
            }
        }
        return new Answer("В коллекции нет элкментов",false,exit);
    }
}
