package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class RemoveFirst extends Command {
    {
        name="remove_first";
        description="удалить первый элемент из коллекции";
        rewrite=true;
    }
    @Override
    public String describe() {
        return name+": "+description;
    }

    @Override
    public Answer act(Meta meta, Work work) {
        Optional<MusicBand> MAX = work.getV().stream().max(Comparator.comparing(MusicBand::getSize));
        if(MAX.isPresent()){
            work.getV().remove(MAX.get());
            return new Answer("Элемент был успешно удалён",true,exit);
        }
        return new Answer("В коллекции нет элкментов",false,exit);
    }
}
