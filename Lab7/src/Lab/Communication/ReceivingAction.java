package Lab.Communication;


import java.net.SocketAddress;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ReceivingAction extends RecursiveAction {
    private final int leftBorder;
    private final int rightBorder;

    private final byte[] bytes;
    ReceivingAction(int leftBorder, int rightBorder, byte[] bytes){
        this.leftBorder=leftBorder;
        this.rightBorder=rightBorder;
        this.bytes=bytes;
    }
    @Override
    protected void compute() {
        int THRESHOLD = 10;
        if(bytes.length> THRESHOLD){
            int middle = (leftBorder+rightBorder)/2;
            ReceivingAction task1 = new ReceivingAction(leftBorder,middle,bytes);
            ReceivingAction task2 = new ReceivingAction(middle,rightBorder,bytes);
            ForkJoinTask.invokeAll(task1,task2);
        }
        else{

        }
    }
}
