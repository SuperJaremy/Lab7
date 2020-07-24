package Lab.Communication;

import Lab.Commands.Meta;
import Lab.Service.Answer;

import java.io.*;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageFormer {
    final String ENDER = "_MESSAGE_END_";
    boolean hasEnded;
    String message;
    SocketAddress sender;
    byte[] arr=new byte[0];
    MessageFormer(SocketAddress sender){
        this.sender=sender;
        hasEnded=false;
        message="";
    }
    private MessageFormer(){
        hasEnded=false;
    }
    static MessageFormer EmptyMessage = new MessageFormer();
    void formFromByte(byte[] list){
        String es = new String(list, StandardCharsets.UTF_8);
        if(es.contains(ENDER)){
            hasEnded=true;
        }
        else {
            message = message.concat(es);
            arr = Arrays.copyOf(arr, arr.length + list.length);
            System.arraycopy(list, 0, arr, arr.length - list.length, list.length);
        }
    }
    public MessageFormer(Answer answer) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(answer);
        oos.close();
        arr=baos.toByteArray();
    }
    Meta toMeta() throws ClassNotFoundException, IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Meta meta = (Meta) ois.readObject();
        ois.close();
        return meta;
    }
}
