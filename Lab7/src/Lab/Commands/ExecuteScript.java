package Lab.Commands;

import Lab.Service.Answer;
import Lab.Service.Work;

public class ExecuteScript extends Command {
    {
        name="execute_script";
        description="считать и исполнить скрипт из указанного файла";
    }
    @Override
    public String describe() {
        return name.concat(": ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        return null;
    }
}
