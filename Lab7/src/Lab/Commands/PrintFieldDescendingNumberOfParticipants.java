package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;

import java.util.Comparator;
import java.util.stream.Collectors;

public class PrintFieldDescendingNumberOfParticipants extends Command {
    {
        name="print_field_descending_number_of_participants";
        description="вывести значения поля numberOfParticipants в порядке убывания";
    }
    @Override
    public String describe() {
        return name.concat(": ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        String answer = work.getV().stream().
                filter(musicBand -> musicBand.getNumberOfParticipants()!=null).
                map(MusicBand::getTrueNumberOfParticipants).distinct().
                sorted((i1,i2)->-i1.compareTo(i2)).
                map(Object::toString).map(s->s.concat("\n")).
                collect(Collectors.joining()).
                concat("Конец списка");
        return new Answer(answer,true,exit);
    }
}
