package ca.mcmaster.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 7, 2018 7:29:10 PM
 * @version 1.0
 */
public class MyCallable implements Callable<Integer> {
	@Override
	public Integer call() throws Exception {
		int i = 0;
		for (i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
		return i;
	}
	
	public static void main(String[] args) {
		MyCallable myCallable = new MyCallable();
		FutureTask<Integer> futureTask = new FutureTask<>(myCallable);
		for(int i = 0; i < 100; i ++){
			System.out.println(Thread.currentThread().getName() + " " + i);
			if(i == 20){
				new Thread(futureTask, "callable-0").start();
				new Thread(futureTask, "callable-1").start();
			}
		}
		try {
			System.out.println("Value returned from thread: " + futureTask.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
