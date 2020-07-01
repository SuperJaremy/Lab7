package Lab.Commands;

import Lab.Service.Answer;
import Lab.Service.Work;

public class Clear extends Command{
    {
        name="clear";
        description="очистить коллекцию";
        rewrite=true;
    }
    @Override
    public String describe() {
        return name+": "+description;
    }

    @Override
    public Answer act(Meta meta, Work work) {
        work.getV().clear();
        return new Answer("Коллекция очищена",true,exit);
    }
}
