package Lab.Commands;

import Lab.Service.Work;

public class Clear extends Command {
    public Clear(){
        name="clear";
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
