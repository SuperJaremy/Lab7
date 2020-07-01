package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;

public class SumOfNumberOfParticipants extends Command {
    {
        name="sum_of_number_of_participants";
        description="вывести сумму значений поля numberOfParticipants для всех эллементов коллекции";
    }
    @Override
    public String describe() {
        return name.concat(": ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        Long a = (long) work.getV().stream().mapToInt((MusicBand::getTrueNumberOfParticipants)).sum();
        return new Answer(a.toString(),true,exit);
    }
}
