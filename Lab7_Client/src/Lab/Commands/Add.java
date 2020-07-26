package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Work;

public class Add extends Command {
    public Add(){
        name="add";
    }

    @Override
    public Meta validate(Work work) {
        if(work.getElement()!=null){
            System.out.println("У команды "+name+" не может быть аргументов");
            return null;
        }
        MusicBand mb = work.validateMusicBand();
        return new Meta(name,new Element(null,mb));
    }
}
