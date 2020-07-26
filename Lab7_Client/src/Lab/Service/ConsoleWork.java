package Lab.Service;

import Lab.Commands.*;
import Lab.Communication.Communicator;
import Lab.Objects.MusicBand;
import Lab.Objects.Validation.ConsoleValidation.ConsoleMusicBandValidator;


import java.io.IOException;
import java.net.SocketException;
import java.util.*;

/**
 * Это менеджер коллекции. Здесь происходит чтение команд из консоли и их запуск
 */
public class ConsoleWork extends Work {
    protected Vector<Integer> Scripts = new Vector<>();

    public ConsoleWork(){
        element=null;
        inProcess=true;
    }

    /**
     * Запускает менджер коллекций
     */
    @Override
    public boolean start(){
         try{
             communicator = new Communicator();
             communicator.open();
         }
         catch (SocketException e){
             System.out.println("Сокет не открыт");
             inProcess=false;
         }
        if(inProcess) {
            boolean success;
            do {
                if (!Authorizator.authorize())
                    return true;
                try {
                    communicator.communicatorSend(new Meta());
                    Answer ans = communicator.communicatorReceive();
                    System.out.println(ans.getAnswer());
                    success=ans.isSuccess();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Не удалось авторизоваться");
                    return false;
                }
            }while(!success);
            System.out.println("Давайте начнём");
        }
        while(inProcess){
            element=null;
            Scanner input=new Scanner(System.in);
            String line=input.nextLine();
            Meta meta;
            try{
                meta=validateLine(line);
                System.out.println("...");
                inProcess=!executeCommand(meta);
            }
            catch(NullPointerException ignored){ }
            catch (IOException e){
                inProcess=true;
            }
        }
        communicator.close();
        System.out.println("Работа программы завершена");
        return true;
    }

    @Override
    public MusicBand validateMusicBand() {
        ConsoleMusicBandValidator cmbv = new ConsoleMusicBandValidator();
        return cmbv.Validate();
    }
}