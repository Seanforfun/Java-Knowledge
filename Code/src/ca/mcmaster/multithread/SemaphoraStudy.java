package multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoraStudy implements Runnable{
    private static final int RESOURCE_NUM = 5;
    private static final int WORKER_NUM = 8;
    private Integer id = null;
    private Semaphore semaphore = null;
    public SemaphoraStudy(int id,  Semaphore semaphore) {
        this.id = id;
        this.semaphore = semaphore;
    }
    public static void main(String[] args) {
        ExecutorService executors = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(RESOURCE_NUM);
        for(int i = 0; i < WORKER_NUM; i++){
            executors.execute(new SemaphoraStudy(i, semaphore));
        }
    }
    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("Worker-" + this.id +" got the resource.");
            Thread.sleep(2000L);
            System.out.println("Worker-" + this.id +" released the resource.");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
