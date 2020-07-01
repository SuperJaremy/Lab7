package Lab.Service;

import Lab.Commands.Command;
import Lab.Commands.*;
import Lab.Objects.MusicBand;

import java.util.*;

public class Work {
    private static final  Map<String, Command> Commands = new HashMap<>();
    Vector<MusicBand>V;
    private final Queue<Command> History = new LinkedList<>();
    private Date changeDate;
    public static Map<String,Command> getCommands(){
        return Commands;
    }
    static Answer falseAnswer= new Answer("Такой команды не существует",
            false,false);
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
    }
    public Answer execute(Meta meta) {
        Answer answer;
        String command = meta.getName();
        if(Commands.containsKey(command)) {
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

    public Vector<MusicBand> getV() {
        return V;
    }


    public Date getChangeDate() {
        return changeDate;
    }
}