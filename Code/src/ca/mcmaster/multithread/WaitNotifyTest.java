package ca.mcmaster.multithread;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 9, 2018 10:49:24 AM
 * @version 1.0
 */
public class WaitNotifyTest {
	private static Object cr = new Object();
	private static Boolean block = true;
	
	static class Wait implements Runnable{
		public void run() {
			synchronized (cr) {
				while(block){
					System.out.println(Thread.currentThread().getName() + " " + " entering waiting");
					try {
						cr.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName() + " :do service");
			}
		}
	}
	static class Notify implements Runnable{
		public void run() {
			synchronized (cr) {
				cr.notify();
				System.out.println(Thread.currentThread().getName() + " " + "notify");
				block = false;
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
