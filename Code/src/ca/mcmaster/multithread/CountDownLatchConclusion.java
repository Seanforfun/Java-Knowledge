package multithread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchConclusion {
    private static int count = 0;
    public static void main(String[] args) {
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        new Thread(() -> {
            count++;
            System.out.println("[target- 1]: finish counting.");
            latch1.countDown();
        }, "target-1").start();
        new Thread(() -> {
            try {
                latch1.await(); //等待target1线程结束
                count++;
                System.out.println("[target- 2]: finish counting.");
                latch2.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "target-2").start();
        try {
            latch2.await(); //等待target2线程结束。
            System.out.println("[Main]: Count is " + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
