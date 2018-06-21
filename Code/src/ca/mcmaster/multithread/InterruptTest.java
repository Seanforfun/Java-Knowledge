package ca.mcmaster.multithread;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 21, 2018 3:57:49 PM
 * @version 1.0
 */
public class InterruptTest {
	public static void beforeInterrupt(){
		System.out.println("Before Interrupt.");
	}
	public static void afterInterrupt(){
		System.out.println("After Interrupt.");
	}
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(()->{
			beforeInterrupt();
			try {
				while(true){
					if(Thread.currentThread().isInterrupted())
		                break;
		            else  
		                System.out.println("Thread is Going..."); 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().interrupted());
			afterInterrupt();
		});
		t.start();
		Thread.currentThread().sleep(1000);
		t.interrupt();
		Thread.currentThread().sleep(1000);
	}
}
