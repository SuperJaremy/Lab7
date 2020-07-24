package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Database;
import Lab.Service.Work;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

public class RemoveByID extends Command {
    private final static Logger logger = LogManager.getLogger();
    {
        name="remove_by_id";
        description = "удалить элемент из коллекции по его id";
    }
    @Override
    public String describe() {
        return name.concat(" id: ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        Integer id = meta.getElement().getId();
        Optional<MusicBand> removable = Database.GetCollection().stream().
                filter((MusicBand mb)->mb.getId().equals(id)).findFirst();
        if(removable.isPresent()){
            try(Database db = new Database()) {
                if (db.removeId(removable.get().getId(), meta.getUsername()))
                    return new Answer("Элемент был успешно удалён", true, exit);
                else
                    return new Answer("Вы не можете удалить объект с id: " + id, false, exit);
            }
            catch (SQLException e){
                logger.error("Ошибка соединения с базой данных");
                return new Answer("Ошибка приложения",false,true);
            }
        }
        return new Answer("Элемент с id ".concat(id.toString()).concat(" не был найден"),
                true,exit);
    }
}
