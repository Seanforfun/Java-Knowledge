package ca.mcmaster.multithread.pass;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 8, 2018 9:33:11 PM
 * @version 1.0
 */
public class PassParameterByConstructor implements Runnable {
	private volatile Integer i = null;
	private ReentrantLock rtLock = new ReentrantLock();
	public PassParameterByConstructor(Integer i){
		this.i = i;
	}
	@Override
	public void run() {
		rtLock.lock();
		for (int j = 0; j < 2; j++) {
			System.out.println(Thread.currentThread().getName() + " " + i++);
		}
		rtLock.unlock();
	}

	public static void main(String[] args) {
		PassParameterByConstructor p = new PassParameterByConstructor(10);
		for(int j = 0; j < 10; j++){
			new Thread(p).start();
		}
	}
}
