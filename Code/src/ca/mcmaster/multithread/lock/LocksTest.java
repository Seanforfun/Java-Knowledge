package ca.mcmaster.multithread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 7, 2018 9:27:07 PM
 * @version 1.0
 */
public class LocksTest {
	ReentrantLock fairLock = new ReentrantLock(true);			//公平锁，与非公平锁相比吞吐量较小
	ReentrantLock unfairLock = new ReentrantLock();					//非公平锁
}
