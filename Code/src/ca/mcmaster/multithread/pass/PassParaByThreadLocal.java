package ca.mcmaster.multithread.pass;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 8, 2018 9:49:52 PM
 * @version 1.0
 */
public class PassParaByThreadLocal implements Runnable {
	private ReentrantLock reentrantLock = new ReentrantLock();
	private static ThreadLocal<Integer> integerLocal = new ThreadLocal<>(); 
	@Override
	public void run() {
		reentrantLock.lock();
		integerLocal.set(10);
		Integer a = integerLocal.get();
		for(int i = 0; i < 2; i++){
			System.out.println(Thread.currentThread().getName() + " " + a++);
		}
		reentrantLock.unlock();
	}
	
	public static void main(String[] args) {
		PassParaByThreadLocal p = new PassParaByThreadLocal();
		for(int i = 0; i < 10; i++){
			new Thread(p).start();
		}
	}
}
