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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Communicator {
    DatagramChannel dcReceiver;
    DatagramChannel dcSender;
    boolean isOpened=false;
    private final static Logger logger = LogManager.getLogger(Communicator.class);
    static final Map<SocketAddress,MessageFormer> Senders = new ConcurrentHashMap<>();
    private final Map<String, SocketAddress> users = new ConcurrentHashMap<>();

    public boolean isOpened(){
        return isOpened;
    }

    public void open() throws IOException {
        dcReceiver=DatagramChannel.open();
        dcReceiver.configureBlocking(false);
        dcReceiver.bind(new InetSocketAddress(InetAddress.getLocalHost(),14087));
        dcSender=DatagramChannel.open();
        dcSender.configureBlocking(false);
        dcSender.bind(new InetSocketAddress(InetAddress.getLocalHost(),14088));
        isOpened=true;
    }
    public void close() throws IOException{
        dcReceiver.close();
        dcSender.close();
        isOpened=false;
    }
    public void sendAnswer(String username, Answer answer) throws IOException{
        if(isOpened) {
            MessageSender ms = new MessageSender(dcSender);
            SocketAddress sender = users.get(username);
            users.remove(username);
            Senders.remove(sender);
            if(sender!=null)
                ms.sendMessage(new MessageFormer(answer),sender);
            else
                throw new IOException("Нет пользователя с таким именем: "+username);
        }
        else
            throw new SocketException("Communicator не был открыт");
    }
    public boolean receiveMessage() throws ExitException{
        if(isOpened)
            try{
                MessageReceiver mr= new MessageReceiver(dcReceiver);
                MessageFormer mf = mr.receiveMessage();
            }
        catch (IOException e){
                logger.error("Ошибка на сервере",e);
                return false;
        }
        return true;
    }
    public Meta getMeta() throws IOException, ClassNotFoundException{
        Iterator<MessageFormer> iterator = Senders.values().iterator();
        while(iterator.hasNext()){
            try{
                MessageFormer mf = iterator.next();
                if(mf.hasEnded) {
                    Meta i = mf.toMeta();
                    users.put(i.getUsername(), mf.sender);
                    return i;
                }
            }
            catch (IOException | ClassNotFoundException e){
                if(!iterator.hasNext())
                    throw e;
            }
        }
        return null;
    }
}
