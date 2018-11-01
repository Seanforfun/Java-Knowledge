package multithread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierConclusion {
    private static final int N = 4;
    private static CyclicBarrier barrier = new CyclicBarrier(N, () -> {
        System.out.println("Finished all writting process.");
    });
    public static void main(String[] args) throws InterruptedException {
        writeToFile();
        Thread.sleep(10000L);
        System.out.println("Barrier reuse.");
        writeToFile();
    }
    private static void writeToFile(){
        for(int i = 0; i < N; i++){
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is writting to file.");
                try {
                    Thread.sleep(5000L);
                    System.out.println(Thread.currentThread().getName() + " finished writting.");
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i).start();
        }
    }
}
