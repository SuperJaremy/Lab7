package Lab.Communication;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.io.IOException;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class MessageReceiver {
    DatagramChannel dc;
    Selector selector;
    ForkJoinPool pool = ForkJoinPool.commonPool();
    private final static Logger logger = LogManager.getLogger(MessageReceiver.class);
    MessageReceiver(DatagramChannel dc){
        this.dc=dc;
    }
    MessageFormer receiveMessage() throws IOException, ExitException{
        MessageFormer mf = MessageFormer.EmptyMessage;
        byte[] thing = new byte[100];
        ByteBuffer message = ByteBuffer.wrap(thing);
        selector=Selector.open();
        SocketAddress sender = null;
        dc.register(selector,SelectionKey.OP_READ);
        int i=0;
        while(i==0) {
            i = selector.selectNow();
        }
        while (!mf.hasEnded) {
            message.clear();
            sender = dc.receive(message);
            if(Communicator.Senders.containsKey(sender))
                mf=Communicator.Senders.get(sender);
            else {
                mf = new MessageFormer(sender);
                Communicator.Senders.put(sender,mf);
            }
            byte[] arr = Arrays.copyOf(thing, message.position());
            message.flip();
            mf.formFromByte(arr);
        }
        logger.info("Приянто сообщение от "+sender);
        return mf;
    }
}
