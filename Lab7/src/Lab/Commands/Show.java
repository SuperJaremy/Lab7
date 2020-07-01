package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;

import java.util.Comparator;
import java.util.stream.Collectors;

public class Show  extends Command{
    {
        name="show";
        description="вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
    @Override
    public String describe() {
        return name.concat(": ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        String info= work.getV().stream().sorted(Comparator.comparing(MusicBand::getSize)).
                map(MusicBand::toString).map(mb->mb.concat("\n")).
                collect(Collectors.joining()).concat("Конец списка");
        return new Answer(info,true,exit);
    }
}
