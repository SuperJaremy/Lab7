package Lab.Communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

class MessageSender {
    private final static Logger logger = LogManager.getLogger();
    byte[] arr;
    DatagramChannel dc;
    MessageSender(DatagramChannel dc){
        this.dc=dc;
    }
    void sendMessage(MessageFormer mf, SocketAddress targetAddress) throws IOException{
        arr=mf.arr;
        byte[] buf = new byte[100];
            while(arr.length>0){
                if(arr.length>100){
                    System.arraycopy(arr,0,buf,0,100);
                    arr= Arrays.copyOfRange(arr,100,arr.length);
                }
                else{
                    System.arraycopy(arr, 0, buf, 0, arr.length);
                    arr=new byte[0];
                }
                ByteBuffer message = ByteBuffer.wrap(buf);
                dc.send(message,targetAddress);
            }
            ByteBuffer message = ByteBuffer.wrap(mf.ENDER.getBytes());
            dc.send(message,targetAddress);
            logger.info("Отправлено сообщение на адрес: "+targetAddress);
    }
}
