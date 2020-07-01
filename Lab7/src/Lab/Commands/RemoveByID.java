package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;

import java.util.Optional;

public class RemoveByID extends Command {
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
        Optional<MusicBand> removable = work.getV().stream().
                filter((MusicBand mb)->mb.getId().equals(id)).findFirst();
        if(removable.isPresent()){
            work.getV().remove(removable.get());
            return new Answer("Элемент был успешно удалён",true,exit);
        }
        return new Answer("Элемент с id ".concat(id.toString()).concat(" не был найден"),
                true,exit);
    }
}
