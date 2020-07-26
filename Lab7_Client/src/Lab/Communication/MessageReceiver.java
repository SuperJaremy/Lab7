package Lab.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
class MessageReceiver {
    DatagramSocket ds;
    MessageReceiver(DatagramSocket ds){
        this.ds=ds;
    }
    MessageFormer receiveMessage() throws IOException,ClassNotFoundException{
        MessageFormer mf = new MessageFormer();
        ds.setSoTimeout(10000);
        byte[] message = new byte[100];
        DatagramPacket packet = new DatagramPacket(message,100);
        while (!mf.hasEnded) {
            ds.receive(packet);
            mf.formFromByte(message);
        }
        return mf;
    }
}

