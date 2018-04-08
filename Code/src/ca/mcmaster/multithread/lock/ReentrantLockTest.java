package ca.mcmaster.multithread.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest implements Runnable {
	private ReentrantLock reentrantLock = new ReentrantLock(false);
	private int value = 0;
	@Override
	public void run() {
		reentrantLock.lock();
		for(int i = 0; i < 3; i++){
			updateValue();
		}
		reentrantLock.unlock();
	}
	
	private void updateValue() {
		reentrantLock.lock();
		System.out.println(Thread.currentThread() + " " + value++);
		reentrantLock.unlock();
	}

	public static void main(String[] args) {
		ReentrantLockTest rtLock = new ReentrantLockTest();
		for(int i = 0; i < 10; i ++){
			Thread thread = new Thread(rtLock);
			if(i == 3){
				thread.setPriority(Thread.MAX_PRIORITY);
			}else if (i == 0) {
				thread.setPriority(Thread.MIN_PRIORITY);
			}
			thread.start();
		}
	}
}

