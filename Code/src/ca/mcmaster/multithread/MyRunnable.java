package ca.mcmaster.multithread;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 7, 2018 6:43:41 PM
 * @version 1.0
 */
public class MyRunnable implements Runnable {

	private int i = 0;
	synchronized public void print(){
		System.out.println(Thread.currentThread().getName() + " " + i);
	}
	@Override
	public void run() {
		for(; i < 100; i++){
			print();
		}
	}
	
	public static void main(String[] args) {
		MyRunnable myRunnable = new MyRunnable();
		for(int i = 0; i < 100; i++){
			System.out.println(Thread.currentThread() + " " + i);
			if(i == 20){
				new Thread(myRunnable, "thread-0").start();
				new Thread(myRunnable, "thread-1").start();
				new Thread(myRunnable, "thread-2").start();
				new Thread(myRunnable, "thread-3").start();
			}
		}
	}
}
