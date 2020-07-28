package Lab.Service;

import Lab.Commands.Command;
import Lab.Commands.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Work {
    private static final  Map<String, Command> Commands = new HashMap<>();
    private final static Queue<Command> History = new LinkedList<>();
    public static Map<String,Command> getCommands(){
        return Commands;
    }
    static Answer falseAnswer= new Answer("Такой команды не существует",
            false,false);
    private String username;
    private static final Logger logger = LogManager.getLogger();
    public Work(String username){
        this.username=username;
    }
    public Work(){}
    static {
        Command info = new Info();
        Command help = new Help();
        Command show = new Show();
        Command add = new Add();
        Command clear = new Clear();
        Command exit = new Exit();
        Command remove=new RemoveFirst();
        Command ifMax = new AddIfMax();
        Command history = new History();
        Command sum = new SumOfNumberOfParticipants();
        Command count=new CountByNumberOfParticipants();
        Command printFieldDescendingNumberOfParticipants=
                new PrintFieldDescendingNumberOfParticipants();
        Command uid=new Update();
        Command remove_id = new RemoveByID();
        Command script = new ExecuteScript();
        Commands.put(info.getName(), info);
        Commands.put(help.getName(), help);
        Commands.put(show.getName(),show);
        Commands.put(add.getName(), add);
        Commands.put(clear.getName(),clear);
        Commands.put(exit.getName(), exit);
        Commands.put(remove.getName(),remove);
        Commands.put(ifMax.getName(),ifMax);
        Commands.put(history.getName(),history);
        Commands.put(sum.getName(),sum);
        Commands.put(count.getName(),count);
        Commands.put(printFieldDescendingNumberOfParticipants.getName(),
        printFieldDescendingNumberOfParticipants);
        Commands.put(uid.getName(),uid);
        Commands.put(remove_id.getName(),remove_id);
        Commands.put(script.getName(),script);
    }
    public Answer execute(Meta meta) {
        Answer answer;
        String command = meta.getName();
        if(Commands.containsKey(command)) {
            logger.info("Исполняется команда "+command);
            answer = Commands.get(command).act(meta, this);
            if (answer.isSuccess()) {
                if (History.size() == 8) {
                    History.poll();
                }
                History.offer(Commands.get(command));
            }
            if (Commands.get(command).getRewrite()) {

            }
            return answer;
        }
        return falseAnswer;
    }

    public Queue<Command> getHistory() {
        return History;
    }

    public String getUsername(){
        return username;
    }

}