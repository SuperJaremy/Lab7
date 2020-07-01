package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;

import java.util.Optional;

public class Update extends Command {
    {
        name="update";
        description = "обновить значение элемента коллекции, id которого равен заданному";
    }
    @Override
    public String describe() {
        return name.concat(" id: ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        Integer id = meta.getElement().getId();
        Optional<MusicBand> updating = work.getV().stream().filter(mb->mb.getId().equals(id)).
                findFirst();
        if(updating.isPresent()) {
            updating.get().update(meta.getElement().getElem());
            return new Answer("Объект ".concat(id.toString()).
                    concat( " обновлён"),true,exit);
        }
        return new Answer("Объект ".concat(id.toString()).concat(" не найден"),
                true,exit);
    }
}
