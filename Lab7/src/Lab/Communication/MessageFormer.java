package Lab.Communication;

import Lab.Commands.Meta;
import Lab.Service.Answer;

import java.io.*;
import java.util.Arrays;

public class MessageFormer {
    final String ENDER = "_MESSAGE_END_";
    boolean hasEnded;
    String message;
    byte[] arr=new byte[0];
    MessageFormer(){
        hasEnded=false;
        message="";
    }
    void formFromByte(byte[] list){
        String es = new String(list);
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
