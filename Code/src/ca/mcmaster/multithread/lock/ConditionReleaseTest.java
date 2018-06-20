package ca.mcmaster.multithread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 13, 2018 10:42:31 AM
 * @version 1.0
 */
public class ConditionReleaseTest implements Runnable {
	private final Condition condition;
	private final Condition condition1;
	private final ReentrantLock lock;
	public ConditionReleaseTest(ReentrantLock lock, Condition condition, Condition condition1){
		this.lock = lock;
		this.condition = condition;
		this.condition1 = condition1;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			lock.lock();
			condition.signalAll();
			lock.unlock();
			Thread.sleep(2000);
			lock.lock();
			condition1.signalAll();
			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
