package Lab.Commands;

import Lab.Service.Work;

public class SumOfNumberOfParticipants extends Command {
    public SumOfNumberOfParticipants(){
        name="sum_of_number_of_participants";
    }
    @Override
    public Meta validate(Work work) {
        if(work.getElement()!=null){
            System.out.println("У команды "+name+" не может быть аргументов");
            return null;
        }
        return new Meta(name,null);
    }
}
