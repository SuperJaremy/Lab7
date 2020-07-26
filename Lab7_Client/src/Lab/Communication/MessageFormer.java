package Lab.Communication;

import Lab.Commands.Meta;
import Lab.Service.Answer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Vector;

public class MessageFormer {
    final String ENDER = "_MESSAGE_END_";
    byte[] message = new byte[0];
    boolean hasEnded;
    String line;
    MessageFormer(){
        hasEnded=false;
        line="";
    }
    MessageFormer(Meta meta) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(meta);
        oos.close();
        message=baos.toByteArray();
    }
    void formFromByte(byte[] list){
        String es = new String(list, StandardCharsets.UTF_8);
        if(es.contains(ENDER)){
            hasEnded=true;
        }
        else {
            line = line.concat(es);
            message = Arrays.copyOf(message, message.length + list.length);
            System.arraycopy(list, 0, message, message.length - list.length,
                    list.length);
        }
    }
    Answer formAnswer() throws IOException, ClassNotFoundException{
        ByteArrayInputStream bais = new ByteArrayInputStream(message);
        ObjectInputStream ois = new ObjectInputStream(bais);
       return (Answer)ois.readObject();
    }
}
