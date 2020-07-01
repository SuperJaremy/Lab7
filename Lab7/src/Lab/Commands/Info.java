package Lab.Commands;

import Lab.Service.Answer;
import Lab.Service.Work;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Info extends Command {
    {
        name="info";
        description = "вывести в стандартный поток вывода информацию о коллекции";
    }
    private SimpleDateFormat sdf= new SimpleDateFormat("dd.MM.yyyy");
    @Override
    public String describe() {
        return name.concat(": ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        String answer = "Тип коллекции: ".concat(work.getV().getClass().toString()).
                concat("\n").concat("Время инициализации: ").
                concat("Время последнего изменения: ").
                concat(sdf.format(work.getChangeDate())).concat("\n").
                concat("Размер коллекции: ").
                concat(Integer.valueOf(work.getV().size()).toString());
        return new Answer(answer,true,exit);
    }
}
