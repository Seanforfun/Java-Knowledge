package ca.mcmaster.multithread;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 8, 2018 8:44:18 PM
 * @version 1.0
 */
public class ThreadLocalTest implements Runnable{
	private static ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
	private static ThreadLocal<String> stringLocal = new ThreadLocal<String>();
	@Override
	public void run() {
		stringLocal.set(Thread.currentThread().getName());
		longLocal.set(Thread.currentThread().getId());
		print();
	}
	
	private synchronized void print() {
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.println(Thread.currentThread().getName() + " " + stringLocal.get() + " " + longLocal.get());
		System.out.println("------------------------------------------------------------------------------------------");
	}

	public static void main(String[] args) {
		ThreadLocalTest threadLocalTest = new ThreadLocalTest();
		for(int i = 0; i < 4; i++){
			new Thread(threadLocalTest).start();
		}
	}
}
