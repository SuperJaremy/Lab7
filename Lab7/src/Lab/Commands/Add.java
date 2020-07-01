package Lab.Commands;


import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;


public class Add extends Command {
    {
        name="add";
        description="добавить новый элемент в коллекцию";
        rewrite=true;
    }
    @Override
    public String describe() {
        return name+" {element}: "+description;
    }

    @Override
    public Answer act(Meta meta, Work work) {
        if(work.getV().size()<Integer.MAX_VALUE) {
            MusicBand mb;
            try {
                mb = MusicBand.create(meta.getElement().getElem());
                work.getV().add(mb);
                return new Answer("Объект добавлен в список",true, exit);
            }
            catch (Exception e){
                return new Answer("Слишком много элементов в коллекции",false,exit);
            }
        }
        return new Answer("Слишком много элементов в коллекции",false,exit);
    }
}
