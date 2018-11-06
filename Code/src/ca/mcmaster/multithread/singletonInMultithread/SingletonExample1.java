package singleton;

import annotation.ThreadNotSafe;
import annotation.ThreadSafe;

@ThreadSafe
public class SingletonExample1 {
    /**
     * 使用私有构造器保证外部无法调用构造器实现单例模式。
     */
    private  SingletonExample1(){

    }

    /**
     * 使用静态变量持有单例。
     * volatile + 双重检测机制 = 禁止指令重排
     */
    private volatile static SingletonExample1 instance = null;

    /**
     * JVM创建一个新的对象实例的步骤：
     * 1. memory = allocate() ; 在Java堆中开辟一块空间。
     * 2. ctorInstance(); //初始化对象，赋值等等操作。
     * 3. instance = memory； 将实例的引用指向该块内存区。
     * volatile变量保证了JVM不会进行指令重排。
     * @return
     */
    @ThreadSafe
    @Discription("双重检测->懒汉单例模式")
    public static SingletonExample1 getInstance(){
        if(instance == null){
            synchronized (SingletonExample1.class){
                if(instance == null)
                /**
                 * volatile保证了指令重排，确定了线程安全。
                 */
                    instance = new SingletonExample1();
            }
        }
        return instance;
    }
}
