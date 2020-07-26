package Lab.Communication;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class MessageReceiver {
    private final Logger logger = LogManager.getLogger();
    DatagramChannel dc;
    Selector selector;
    private final int MAX_PACKS=1000;
    static ForkJoinPool pool = ForkJoinPool.commonPool();
    static Scanner input = new Scanner(System.in);
    static Callable<Boolean> typeInCommands = ()-> input.hasNext();
    static ExecutorService consoleCheck = Executors.newFixedThreadPool(1);
    MessageReceiver(DatagramChannel dc){
        this.dc=dc;
    }
    int receiveMessage() throws IOException, ExitException{
        Set<SocketAddress> senders = new HashSet<>();
        List<PackagePair> packages = new ArrayList<>();
        int packageNumber=0;
        byte[] thing = new byte[100];
        ByteBuffer message = ByteBuffer.wrap(thing);
        selector=Selector.open();
        dc.register(selector,SelectionKey.OP_READ);
        int i=0;
        while(i==0){
            try{
                Future<Boolean> result = consoleCheck.submit(typeInCommands);
                boolean res = result.get(250,TimeUnit.MICROSECONDS);
                String line;
                if(res) {
                    line = input.nextLine().trim();
                    if (line.equals("exit")) {
                        consoleCheck.shutdown();
                        throw new ExitException();
                    }
                }
            }
            catch (InterruptedException | ExecutionException | TimeoutException ignored){}
            i=selector.selectNow();
        }
        while(i>0&&packageNumber<=MAX_PACKS){
            Iterator<SelectionKey> channel = selector.selectedKeys().iterator();
            channel.next();
            channel.remove();
            message.clear();
            SocketAddress sender = dc.receive(message);
            senders.add(sender);
            packageNumber++;
            packages.add(new PackagePair(sender,message.array()));
            thing = new byte[100];
            message = ByteBuffer.wrap(thing);
            i=selector.selectNow();
        }
        ReceivingAction task = new ReceivingAction(senders,packages,Communicator.Senders);
        pool.execute(task);
        try{
            pool.awaitTermination(1, TimeUnit.SECONDS);
        }
        catch (InterruptedException e){
            logger.error("Прерван приём сообщений");
            return 0;
        }
        int messages = ReceivingAction.messagesNumber.get();
        ReceivingAction.messagesNumber= new AtomicInteger();
        return messages;
    }
}
