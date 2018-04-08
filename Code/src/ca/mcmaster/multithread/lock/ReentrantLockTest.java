package ca.mcmaster.multithread.lock;

public class ReentrantLockTest extends Thread {
	private synchronized void get(){
		System.out.println(Thread.currentThread() + " :get");
		set();
	}
	private void set() {
		for(int i = 0; i < 5; i++)
			System.out.println(Thread.currentThread() + " :set");
	}
	@Override
	public void run() {
		for(int i = 0; i < 5; i++)
			get();
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 2; i++){
			new ReentrantLockTest().start();
		}
	}
}

