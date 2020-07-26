package Lab.Communication;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class MessageSender {
    private byte[] message;
    DatagramSocket ds;
    MessageSender(DatagramSocket ds){
        this.ds=ds;
    }
    void sendMessage(MessageFormer mf) throws IOException{
        message=mf.message;
            byte[] buffer = new byte[100];
            SocketAddress address = new InetSocketAddress(InetAddress.
                    getLocalHost(),14087);
            DatagramPacket packet;
            while(message.length>0){
                if(message.length>100){
                    System.arraycopy(message,0,buffer,0,100);
                    message= Arrays.copyOfRange(message,100,message.length);
                    packet = new DatagramPacket(buffer,100,address);
                }
                else {
                    packet = new DatagramPacket(message,message.length,address);
                    message=new byte[0];
                }
                ds.send(packet);
            }
            buffer=mf.ENDER.getBytes(StandardCharsets.UTF_8);
            ds.send(new DatagramPacket(buffer,buffer.length,address));
    }
}
