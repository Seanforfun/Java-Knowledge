package singleton;

import annotation.ThreadSafe;

@ThreadSafe
@Discription("通过枚举模式实现线程安全的单例模式")
public class SingletonExample3 {
    private SingletonExample3(){};
    public static SingletonExample3 getInstance(){
        return Singleton.INSTANCE.getInstance();
    }
    private enum Singleton{
        INSTANCE;
        private SingletonExample3 singleton;
        /**
         * 枚举的构造器，JVM会保证该构造器只会被调用一次
         */
        Singleton(){
            singleton = new SingletonExample3();
        }
        public SingletonExample3 getInstance(){
            return singleton;
        }
    }
}
