package ca.mcmaster.multithread.lock;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 21, 2018 2:44:40 PM
 * @version 1.0
 */
public class VolatileTest{
	public static volatile int count = 0;
	public static void increase(){
		count ++;
	}
	public static void main(String[] args) throws InterruptedException {
		Thread[] threads = new Thread[20];
		for(int i = 0; i < 20; i++){
			threads[i] = new Thread(() -> {
				for(int j = 0; j < 10000; j++)
					increase();
//				System.out.println(Thread.currentThread().getName());
			}, "thread-" + i);
			threads[i].start();
//			t.join();
		}
		for(Thread t : threads)
			t.join();
//		while(Thread.activeCount() > 1)
//			Thread.yield();		
		System.out.println(count);
	}
}

