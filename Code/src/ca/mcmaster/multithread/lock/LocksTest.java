package ca.mcmaster.multithread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 7, 2018 9:27:07 PM
 * @version 1.0
 */
public class LocksTest implements Runnable{
//	ReentrantLock fairLock = new ReentrantLock(true);			
	private final ReentrantLock unfairLock;					
	private final Condition lockCondition;
	private final Condition lockCondition1;
	@Override
	public void run() {
		unfairLock.lock();
		try {
			lockCondition.await();
			System.out.println("After await......");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			unfairLock.unlock();
		}
		unfairLock.lock();
		try {
			lockCondition1.await();
			System.out.println("After await1......");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			unfairLock.unlock();
		}	
	}
	public LocksTest(ReentrantLock lock, Condition condition, Condition condition1){
		this.unfairLock = lock;
		this.lockCondition = condition;
		this.lockCondition1 = condition1;
	}
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		Condition condition1 = lock.newCondition();
		Thread t = new Thread(new LocksTest(lock, condition, condition1));
		Thread t1 = new Thread(new ConditionReleaseTest(lock, condition, condition1));
		t.start();
		t1.start();
	}
}
