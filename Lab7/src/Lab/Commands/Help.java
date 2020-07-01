package Lab.Commands;

import Lab.Service.Answer;
import Lab.Service.Work;

public class Help extends Command {
    {
        name="help";
        description="вывести справку по доступным командам";
    }
    @Override
    public String describe() {
        return name.concat(": ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        String info="";
        for(Command i : Work.getCommands().values())
            info=info.concat(i.describe()).concat("\n");
        return new Answer(info.concat("Конец списка"),true,exit);
    }
}
