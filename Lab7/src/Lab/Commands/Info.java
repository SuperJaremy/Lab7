package Lab.Commands;

import Lab.Service.Answer;
import Lab.Service.Database;
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
        String answer = "Размер коллекции: ".
                concat(Integer.valueOf(Database.GetCollection().size()).toString());
        return new Answer(answer,true,exit);
    }
}
