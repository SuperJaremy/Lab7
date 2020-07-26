package Lab.Commands;

import Lab.Service.Work;

public class CountByNumberOfParticipants extends Command {
    public CountByNumberOfParticipants(){
        name="count_by_number_of_participants";
    }


    @Override
    public Meta validate(Work work) {
        if(work.getElement()!=null){
            try{
                String line=work.getElement();
                int i = Integer.parseInt(line);
                if(i<=0)
                    throw new NumberFormatException();
                return new Meta(name, new Element(i));
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
