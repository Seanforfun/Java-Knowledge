package ca.mcmaster.multithread.pass;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 8, 2018 9:43:26 PM
 * @version 1.0
 */
public class PassParaByMethod implements Runnable{
	private ReentrantLock reentrantLock = new ReentrantLock();
	private Integer a = null;
	public void run() {
		reentrantLock.lock();
		for (int j = 0; j < 2; j++) {
			System.out.println(Thread.currentThread().getName() + " " + a++);
		}
		reentrantLock.unlock();
	}
	private void setA(Integer a){
		this.a = a;
	}
	public static void main(String[] args) {
		PassParaByMethod r = new PassParaByMethod();
		r.setA(10);
		for(int i = 0; i <10; i++){
			new Thread(r).start();
		}
	}
}
