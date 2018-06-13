# Condition

### Condition的介绍
1. Condition是通过锁获取的，Condition依赖于Lock接口，生成一个Condition的基本代码是lock.newCondition()。
2. 调用Condition的await()和signal()方法，都必须在lock保护之内，就是说必须在lock.lock()和lock.unlock之间才可以使用。所以必须在获取到锁以后使用！
3. condition.await()
	* 首先在condition.await()必须在获取所以后使用。
	* 调用该方法会让线程阻塞，并且让锁可以被别的线程获取，但是最终仍然要释放锁资源！
	* 一般都要在while中使用，一般通过一个别的变量来让condition在while中循环生效等待，原因是为了协同，不让condition在await之前就被signal过了，这样的话当前所期望的await将会永久的阻塞。
4. condition.signal()
	* 必须要获取锁才能使用该方法，不然会出现java.lang.IllegalMonitorStateException。
	* 解除condition.await()造成的阻塞。

### Condition的使用
```Java
public class LocksTest implements Runnable{
//	ReentrantLock fairLock = new ReentrantLock(true);			
	private final ReentrantLock unfairLock;					
	private final Condition lockCondition;
	private final Condition lockCondition1;
	@Override
	public void run() {
		unfairLock.lock();
		try {
			lockCondition.await();	//condition进入阻塞，此处编程并不好，应该让condition进入阻塞应该配合别的变量在while中使用。不然会造成signal在await之前调用。
			System.out.println("After await......");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			unfairLock.unlock();
		}
		unfairLock.lock();
		try {
			lockCondition1.await();	//condition1进入阻塞
			System.out.println("After await1......");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			unfairLock.unlock();
		}
	}
	public LocksTest(ReentrantLock lock, Condition condition, Condition condition1){
		this.unfairLock = lock;
		this.lockCondition = condition;
		this.lockCondition1 = condition1;
	}
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		Condition condition1 = lock.newCondition();
		Thread t = new Thread(new LocksTest(lock, condition, condition1));
		Thread t1 = new Thread(new ConditionReleaseTest(lock, condition, condition1));
		t.start();
		t1.start();
	}
}
```
```Java
public class ConditionReleaseTest implements Runnable {
	private final Condition condition;
	private final Condition condition1;
	private final ReentrantLock lock;
	public ConditionReleaseTest(ReentrantLock lock, Condition condition, Condition condition1){
		this.lock = lock;
		this.condition = condition;
		this.condition1 = condition1;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			lock.lock();
			condition.signalAll();	//condition解除阻塞
			lock.unlock();
			Thread.sleep(2000);
			lock.lock();
			condition1.signalAll();	//condition1解除阻塞
			lock.unlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
```