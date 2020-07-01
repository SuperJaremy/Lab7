package Lab.Commands;

import Lab.Service.Answer;
import Lab.Service.Work;

public class Exit extends Command {
    {
       name="exit";
       description="завершить программу";
       exit=true;
    }

    @Override
    public String describe() {
        return name+": "+description;
    }

    @Override
    public Answer act(Meta meta, Work work) {
        return new Answer("Выход из программы...",true,exit);
    }
}
