package Lab.Communication;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.io.IOException;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

public class MessageReceiver {
    private SocketAddress sender;
    DatagramChannel dc;
    Selector selector;
    private final static Logger logger = LogManager.getLogger(MessageReceiver.class);
    MessageReceiver(DatagramChannel dc){
        this.dc=dc;
    }
    SocketAddress getSender() throws SocketException{
        if(sender!=null)
            return sender;
        else
            throw new SocketException();
    }

    MessageFormer receiveMessage() throws IOException, ExitException{
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<String> hasCommand = () -> {
            Scanner input = new Scanner(System.in);
            if(input.hasNext())
                return input.nextLine().trim();
            return "";
        };
        Future<String> future = executor.submit(hasCommand);
        MessageFormer mf = new MessageFormer();
        byte[] thing = new byte[100];
        ByteBuffer message = ByteBuffer.wrap(thing);
        selector=Selector.open();
        dc.register(selector,SelectionKey.OP_READ);
        int i=0;
        while(i==0) {
            String result="";
            try{
                result = future.get(1, TimeUnit.MILLISECONDS);
                executor.shutdownNow();
            }
            catch (TimeoutException | ExecutionException ignored){
            }
            catch (InterruptedException e){
                logger.error("Считывание с консоли не удалось");
            }
            if(result.equals("exit"))
                throw new ExitException();
            i = selector.selectNow();
        }
        while (!mf.hasEnded) {
            message.clear();
            sender = dc.receive(message);
            byte[] arr = Arrays.copyOf(thing, message.position());
            message.flip();
            mf.formFromByte(arr);
        }
        logger.info("Приянто сообщение от "+sender);
        return mf;
    }
}
