package ca.mcmaster.jvm;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 6, 2018 5:21:16 PM
 * @version 1.0
 * -Xms20m
 * -Xmx20m
 * -Xmn10M
 * -XX:+PrintGCTimeStamps 
 * -XX:+PrintGCDetails
 * -verbose:gc
 * -Xloggc:f:/dump/dc.log
 * -XX:SurvivorRatio=8
 * -XX:PretenureSizeThreshold=3145728
 */
public class GCCollection {
	private final static int _1MB = 1024 * 1024;
//	private Byte[] bytes = new Byte[1024 *1024];	//1Mb
	public static void main(String[] args) throws InterruptedException {
		while(true){
			byte[] a1 = new byte[2 * _1MB];
			byte[] a2 = new byte[2 * _1MB];
			byte[] a3 = new byte[2 * _1MB];
			byte[] a4 = new byte[4 * _1MB];
			byte[] a5 = new byte[4 * _1MB];
			Thread.currentThread().sleep(2000);
		}		
//		byte[] a5 = new byte[4 * _1MB];
//		byte[] a6 = new byte[4 * _1MB];
//		byte[] a7 = new byte[2 * _1MB];
//		byte[] a6 = new byte[4 * _1MB];
//		byte[] a7 = new byte[4 * _1MB];
	}
}
