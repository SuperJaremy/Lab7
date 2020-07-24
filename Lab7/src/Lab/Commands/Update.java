package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Database;
import Lab.Service.Work;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class Update extends Command {
    {
        name="update";
        description = "обновить значение элемента коллекции, id которого равен заданному";
    }
    private final static Logger logger = LogManager.getLogger();
    @Override
    public String describe() {
        return name.concat(" id: ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        Integer id = meta.getElement().getId();
        MusicBand mb = meta.getElement().getElem();
        boolean validId = Database.GetCollection().stream().map(MusicBand::getId).
                anyMatch(id::equals);
        if(validId) {
            try(Database db =new Database()) {
                if(db.updateId(id,mb,meta.getUsername()))
                    return new Answer("Объект ".concat(id.toString()).
                            concat(" обновлён"), true, exit);
                return new Answer("Объект ".concat(id.toString()).
                        concat(" не может быть обновлён"),false,exit);
            }
            catch (SQLException e){
                logger.error("Ошибка соединения с базой данных");
                return new Answer("Ошибка приложения",false,true);
            }
        }
        return new Answer("Объект ".concat(id.toString()).concat(" не найден"),
                true,exit);
    }
}
