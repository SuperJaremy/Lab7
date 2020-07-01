package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;

import java.util.Optional;

public class AddIfMax extends Command {
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
        try {
            mb = MusicBand.create(meta.getElement().getElem());
        }
        catch (Exception e){
            return new Answer("Слишком много элементов коллекции",false,exit);
        }
        Optional<MusicBand> MAX = work.getV().stream().max(MusicBand::compareTo);
        if(MAX.isPresent()){
            if(mb.compareTo(MAX.get()) > 0) {
                work.getV().add(mb);
                return new Answer("Элемент успешно добавлен", true, exit);
            }
            else{
                MusicBand.removeId(mb.getId());
                return new Answer("Элемент не является наибольшим",false,exit);
            }
        }
        else{
            MusicBand.removeId(mb.getId());
            return new Answer("В коллекции нет элементов",false,exit);
        }
    }
}