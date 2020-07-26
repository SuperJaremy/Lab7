package Lab.Communication;


import Lab.Commands.Meta;
import Lab.Service.Answer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Vector;


public class Communicator {
    DatagramSocket ds;
    MessageSender ms;
    MessageReceiver mr;
    boolean isOpened=false;
    public void open() throws SocketException {
            ds = new DatagramSocket();
            isOpened=true;
    }
    public void close(){
            ds.close();
            isOpened=false;
    }
    public void communicatorSend(Meta meta) throws IOException{
        if(isOpened){
            ms=new MessageSender(ds);
            ms.sendMessage(new MessageFormer(meta));
        }
        else throw new SocketException();
    }
    public Answer communicatorReceive() throws IOException,SocketException,ClassNotFoundException{
        if(isOpened) {
            mr = new MessageReceiver(ds);
            MessageFormer mf = mr.receiveMessage();
            return mf.formAnswer();
        }
        else
            throw new SocketException();
    }
}
