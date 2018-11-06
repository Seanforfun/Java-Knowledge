package singleton;

import annotation.ThreadSafe;

@ThreadSafe
@Discription("饿汉模式->在类被加载的阶段就会被初始化" +
        "类加载器保证了线程安全，但是会造成效率的下降")
public class SingletonExample2 {
    private SingletonExample2(){};
    static {
        instance = new SingletonExample2();
    }
    private static SingletonExample2 instance = null;
    public static SingletonExample2 getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(instance);
        System.out.println(instance);
    }
}
