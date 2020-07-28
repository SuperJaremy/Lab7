package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Database;
import Lab.Service.Work;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Vector;

public class AddIfMax extends Command {
    private final static Logger logger = LogManager.getLogger();
    {
        name="add_if_max";
        description="добавить новый элемент в коллекцию," +
                " если его значение превышает значение наибольшего элемента этой коллекции";
        rewrite=true;
    }
    @Override
    public String describe() {
        return name+" {element}: "+description;
    }

    @Override
    public Answer act(Meta meta, Work work) {
        MusicBand mb;
        mb = MusicBand.create(meta.getElement().getElem());
        Vector<MusicBand> V = Database.GetCollection();
        Optional<MusicBand> MAX = V.stream().max(MusicBand::compareTo);
        if(V.get(V.size()-1).getId()<=Integer.MAX_VALUE)
            if(MAX.isPresent()){
               if(mb.compareTo(MAX.get()) > 0) {
                   try(Database db =new Database()) {
                       db.Add(mb, meta.getUsername());
                       return new Answer("Элемент успешно добавлен", true, exit);
                   }
                 catch (SQLException e){
                     logger.error("Ошибка соединения с базой данных");
                     return new Answer("Ошибка приложения",false,true);
                 }
             }
              else{
                  return new Answer("Элемент не является наибольшим",false,exit);
              }
            }
         else{
                return new Answer("В коллекции нет элементов",false,exit);
         }
        return new Answer("Слишком много элементов коллекции",false,exit);
    }
}