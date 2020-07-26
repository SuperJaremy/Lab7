package Lab.Commands;

import Lab.Service.Work;

public class Exit extends Command {
    public Exit(){
        name="exit";
    }


    @Override
    public Meta validate(Work work) {
        if(work.getElement()!=null) {
            System.out.println("у команды "+name+" не должно быть аргументов");
            return null;
        }
        return new Meta(name,null);
    }
}
