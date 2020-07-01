package Lab.Commands;

import Lab.Service.Answer;
import Lab.Service.Work;


public class History extends Command {
    {
        name="history";
        description="вывести последние 8 команд";
    }


    @Override
    public String describe() {
        return name+": "+description;
    }

    @Override
    public Answer act(Meta meta, Work work) {
        String info="";
        for(Command i : work.getHistory())
            info=info.concat(i.describe()).concat("\n");
        info=info.concat("Конец истории");
        return new Answer(info,true,exit);
    }
}
