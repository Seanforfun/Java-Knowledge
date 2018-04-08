package ca.mcmaster.multithread.lock;

public class RtLock implements Runnable {
	private int i = 0;
	private void set(){
		for(int j = 0; j < 10; j++)
			System.out.println(Thread.currentThread() + " " + i++);
	}
	public synchronized void get(){
		System.out.println(Thread.currentThread() + " get");
		set();
	}
	@Override
	public synchronized void run() {
		get();
	}
	
	public static void main(String[] args) {
		RtLock rtLock = new RtLock();
		for(int i = 0; i < 2; i++){
			new Thread(rtLock, "thread-"+i).start();
		}
		for(int j = 0; j < 10; j++)
			rtLock.set();
	}
}
