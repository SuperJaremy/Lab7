package Lab.Commands;

import Lab.Service.Work;

public class Info extends Command {
    public Info(){
        name="info";
    }

    @Override
    public Meta validate(Work work) {
        if(work.getElement()!=null) {
            System.out.println("У команды " + name + " не должно быть аргументов");
            return null;
        }
        return new Meta(name, null);
    }
}
