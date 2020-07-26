package Lab.Communication;


import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

class ReceivingAction extends RecursiveAction {
    Set<SocketAddress> senders;
    List<PackagePair> packages;
    static AtomicInteger messagesNumber=new AtomicInteger();
    Map<SocketAddress,MessageFormer> messages;
    ReceivingAction(Set<SocketAddress> senders, List<PackagePair> packages,
                    Map<SocketAddress,MessageFormer> messages){
        this.senders=senders;
        this.messages=messages;
        this.packages=packages;
    }
    @Override
    protected void compute() {
        List<ReceivingAction> tasks = new ArrayList<>();
        if(senders.size()!=1){
            for(SocketAddress i : senders){
                Set<SocketAddress> sender = new HashSet<>();
                sender.add(i);
                tasks.add(new ReceivingAction(sender,packages,messages));
            }
            ForkJoinTask.invokeAll(tasks);
        }
        else{
            SocketAddress address = senders.stream().findFirst().get();
            Consumer<PackagePair> forming = packagePair -> {
                if(!messages.containsKey(address))
                    messages.put(address,new MessageFormer(address));
                messages.get(address).formFromByte(packagePair.pack);
                if(messages.get(address).hasEnded)
                    messagesNumber.incrementAndGet();
            };
            packages.parallelStream().filter(i->i.sender.equals(address)).forEachOrdered(forming);
        }
    }
}
