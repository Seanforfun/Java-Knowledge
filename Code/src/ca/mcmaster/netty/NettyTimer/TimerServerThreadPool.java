package fnio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimerServerThreadPool {
    private ThreadPoolExecutor executors = null;
    ArrayBlockingQueue<Runnable> blockingQueue = null;
    public TimerServerThreadPool(int coreNum, int maxNum){
        this.blockingQueue = new ArrayBlockingQueue<Runnable>(1000, false);
        this.executors = new ThreadPoolExecutor(coreNum, maxNum, 1000L ,
                TimeUnit.SECONDS, blockingQueue, Executors.defaultThreadFactory());
    }
    public void execute(Runnable target){
        this.executors.execute(target);
    }
}
