package Lab.Communication;

import Lab.Commands.Meta;
import Lab.Service.Answer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class MessageFormer {
    final String ENDER = "_MESSAGE_END_";
    boolean hasEnded;
    String message;
    SocketAddress sender;
    byte[] arr=new byte[0];
    private final Logger logger = LogManager.getLogger();
    MessageFormer(SocketAddress sender){
        this.sender=sender;
        hasEnded=false;
        message="";
    }
    void formFromByte(byte[] list){
        String es = new String(list, StandardCharsets.UTF_8);
        if(es.contains(ENDER)){
            hasEnded=true;
            logger.info("Принято сообщение с адреса: "+sender);
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
        logger.info("Сообщение с адреса: "+sender+" успешно собрано");
        return meta;
    }
}
