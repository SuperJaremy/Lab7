package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;

public class CountByNumberOfParticipants extends Command {
    {
        name="count_by_number_of_participants";
        description="вывести количество элементов, значения поля" +
                " numberOfParticipants которых равно заданному";
    }
    @Override
    public String describe() {
        return name.concat(" numberOfParticipants: ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        Integer num = meta.getElement().getNum();
        Long count = work.getV().stream().filter((MusicBand mb)->{
            Integer participants=mb.getNumberOfParticipants();
            if(participants!=null)
                return num.equals(participants);
            return false;
        }).count();
        return new Answer(count.toString(),true,exit);
    }
}
