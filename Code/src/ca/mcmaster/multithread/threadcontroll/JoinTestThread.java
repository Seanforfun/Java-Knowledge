package ca.mcmaster.multithread.threadcontroll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javax.xml.stream.events.StartDocument;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date May 15, 2018 11:09:04 AM
 * @version 1.0
 */
public class JoinTestThread implements Runnable{
	private String name;
	public String getName() {
		return name;
	}
	
	public JoinTestThread(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 1000; i++)
			System.out.println(this.name + ":"+ i);
	}
	
	public static void main(String[] args) throws InterruptedException {
		JoinTestThread jt1 = new JoinTestThread("aaaaa");
		JoinTestThread jt2 = new JoinTestThread("bbbbb");
		
		Thread thread1 = new Thread(jt1);
		thread1.start();
		thread1.join();
		new Thread(jt2).start();
	}
}
