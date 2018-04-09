package ca.mcmaster.multithread.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 8, 2018 6:30:22 PM
 * @version 1.0
 */
class SpinLock {
	private AtomicReference<Thread> sign = new AtomicReference<Thread>(){
		public String toString() {
			return "sign";
		}
	};  
	public void lock(){
		while(!sign.compareAndSet(null, Thread.currentThread())){
			
		}
	}
	public void unlock(){
		sign.compareAndSet(Thread.currentThread(), null);
	}
	public static void main(String[] args) {
		SpinLock lock = new SpinLock();
		System.out.println(lock.sign.toString());
	}
}
