package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Objects.Validation.ConsoleValidation.ConsoleMusicBandValidator;
import Lab.Service.Work;

public class Update extends Command {
    public Update(){
        name="update";
    }
    @Override
    public Meta validate(Work work) {
        if(work.getElement()!=null){
            try{
                String line=work.getElement();
                int i = Integer.parseInt(line);
                if(i<=0)
                    throw new NumberFormatException();
                MusicBand mb = work.validateMusicBand();
                return new Meta(name,new Element(i,mb));
            }
            catch (NumberFormatException e){
                System.out.println("Аргументом команды "+name+" должно быть целое" +
                        " положительное число");
            }
        }
        else
            System.out.println("У команды "+name+" должен быть аргумент");
        return null;
    }
}
