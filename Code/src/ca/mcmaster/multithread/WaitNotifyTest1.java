package ca.mcmaster.multithread;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 9, 2018 10:49:24 AM
 * @version 1.0
 */
public class WaitNotifyTest1 {
	private static  Integer mutex = 0;
	
	static class Wait implements Runnable{
		public void run() {
			synchronized (mutex) {
				while(mutex != 1){
					System.out.println(Thread.currentThread().getName() + " " + " entering waiting");
					try {
						mutex.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName() + " mutex= " + ++mutex);
			}
		}
	}
	static class Notify implements Runnable{
		public void run() {
			synchronized (mutex) {
				mutex.notify();
				System.out.println(Thread.currentThread().getName() + " " + "notify");
				mutex = 1;
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Wait wait = new Wait();
		new Thread(wait).start();
		Thread.sleep(10);
		Notify notify = new Notify();
		new Thread(notify).start();
	}
}
