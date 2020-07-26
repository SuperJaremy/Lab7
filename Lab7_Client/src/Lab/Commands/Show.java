package Lab.Commands;

import Lab.Service.Work;

public class Show extends Command {
    public Show(){
        name="show";
    }
    @Override
    public Meta validate(Work work) {
        if(work.getElement()!=null){
            System.out.println("У команды "+name+" не должно быть аргументов");
            return null;
        }
        return new Meta(name,null);
    }
}
