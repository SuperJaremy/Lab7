package Lab.Commands;

import Lab.Service.Work;

public class History extends  Command {
    private static final long serialVersionUID=75138327632L;
    public History(){
        name="history";
    }
    @Override
    public Meta validate(Work work) {
        if(work.getElement()!=null) {
            System.out.println("У команды "+name+" не должно быть аргументов");
            return null;
        }
        return new Meta(name,null);
    }
}
