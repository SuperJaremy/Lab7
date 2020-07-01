package Lab.Communication;

import Lab.Commands.Meta;
import Lab.Service.Answer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

public class Communicator {
    MessageSender ms;
    MessageReceiver mr;
    MessageFormer mf;
    DatagramChannel dc;
    SocketAddress sender;
    boolean isOpened=false;
    private final static Logger logger = LogManager.getLogger(Communicator.class);

    public SocketAddress getSender() {
        return sender;
    }
    public boolean isOpened(){
        return isOpened;
    }

    public void open() throws IOException {
        dc=DatagramChannel.open();
        dc.configureBlocking(false);
        dc.bind(new InetSocketAddress(InetAddress.getLocalHost(),8888));
        isOpened=true;
    }
    public void close() throws IOException{
        dc.close();
        isOpened=false;
    }
    public void sendAnswer(Answer answer) throws IOException{
        if(isOpened) {
            ms = new MessageSender(dc);
            if(sender!=null)
            ms.sendMessage(new MessageFormer(answer),sender);
            else
                throw new SocketException();
        }
        else
            throw new SocketException();
    }

    public Meta toMeta() throws IOException, ClassNotFoundException{
       return mf.toMeta();
    }
    public String toFile(){
        return mf.message;
    }
    public boolean receiveMessage() throws ExitException{
        if(isOpened)
            try{
                mr= new MessageReceiver(dc);
                mf = mr.receiveMessage();
                sender=mr.getSender();
            }
        catch (IOException e){
                logger.error("Ошибка на сервере",e);
                return false;
        }
        return true;
    }
}
