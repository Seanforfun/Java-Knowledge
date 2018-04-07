package ca.mcmaster.multithread;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 7, 2018 5:51:53 PM
 * @version 1.0
 */
public class MyThread extends Thread {
	private int i = 0;
	@Override
	public void run() {
		for (; i < 100; i++) {
			System.out.println(getName() + " " + i);
		}
	}
	public static void main(String[] args) {
		for(int i = 0; i < 100; i++){
			System.out.println(Thread.currentThread().getName() + " " + i);
			if(i == 20){
				new MyThread().start();
				new MyThread().start();
			}
		}
	}
}
