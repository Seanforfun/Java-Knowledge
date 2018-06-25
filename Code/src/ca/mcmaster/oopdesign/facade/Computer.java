package ca.mcmaster.oopdesign.facade;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 25, 2018 10:38:42 AM
 * @version 1.0
 */
public class Computer {
	private final CPU cpu;
	private final Memory memory;
	private final Disk disk;
	public Computer(){
		this.cpu = new CPU();
		this.disk = new Disk();
		this.memory = new Memory();
	}
	public void start(){
		this.cpu.start();
		this.disk.start();
		this.memory.start();
		System.out.println("Computer is starting...");
	}
	public void shutdown(){
		this.cpu.shutdown();
		this.disk.shutdown();
		this.memory.shutdown();
		System.out.println("Computer is shutdown...");
	}
	public static void main(String[] args) {
		Computer computer = new Computer();
		computer.start();
		System.out.println("===================");
		computer.shutdown();
	}
}
