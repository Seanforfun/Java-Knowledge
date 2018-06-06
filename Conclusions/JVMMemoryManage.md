# 自动内存管理机制

### 运行时数据区域
![JVM ram areas](https://i.imgur.com/5a3nvpz.jpg)

1. 程序计数器（Program counter register）
> 类似于Stack pointer。用于控制代码的运行行号，控制分支，循环，跳转，异常处理。
> 存储虚拟机字节码指令的地址。
> 每一条线程均有自己的PC。线程间的PC互不影响，相互独立。
> 当正在运行native方法时，PC值为空（Undifined）。

2. Java虚拟机栈（JVM Stack）
> 线程私有，生命周期和线程相同。可以理解为一种局部变量表。
> 在一个方法被执行的时候，会开辟一块栈空间（Stack Frame）,用于存储局部变量，操作栈，动态链接，方法出口信息。
> 开辟栈空间后会将局部变量等信息压栈。
> 除了double和long会占用2个slots（4 bytes）。别的占用1slot（32位）。例如对象的引用，存储了对象的指针。returnAddress存储了字节码的地址。由此可见，JVM是32位机。
> 局部变量表所需的内存空间在编译期就完成了分配。

3. 本地方法栈（Native method stack）
> 作用和JVM Stack非常类似。
> 为虚拟机使用到的Native方法服务。

4. Java堆（Java Heap）
> 所有线程共享堆。
> 存放对象实例。所有的对象和数组均在对上分配空间。
> Garbage collection的重要区域。
> 物理上的不连续区域，但是在逻辑上是连续的区域。

5. 方法区（Method Area）
> 线程的共享区域。
> 存储类信息，常量，静态变量即时编译器编译后的代码。

5.1. 运行时常量池(Runtime constant pool)
> 运行时常量池是方法区的一部分。
> Class文件中有一项信息是常量池（Constant Pool Table）,用于存放编译期生成的字面量和符号引用。这部分将在类加载后存放到方法区的运行时常量池中。

6. 直接内存（Direct Memory）
> 为了避免在Java堆和Native堆中来回复制数据而使用的堆外内存。
> 在NIO中被使用。

### 对象访问
> 对象的访问会涉及java栈，java堆，方法区三个区域。
```Java
Object obj = new Object();
```
1. `Object obj`会反应到Java栈的本地变量表中，作为一个reference类型数据出现。可以理解为在Java栈中存储了一个指针。
2. `new Object()`将会在Java堆中开辟一块空间，存储Object实例。
3. 在方法区中存储了对象的类型数据（对象类型，父类，实现的接口，方法等）。
	* 通过句柄存储
	![句柄访问对象](https://i.imgur.com/G4W4vqf.png)
	* 通过指针访问
	![指针访问对象](https://i.imgur.com/dpg3RYy.png)

### Java内存溢出
* 通过Eclispe Menmory Analyzer，可以分析出内存泄露的位置
* 通过减少内存的手段来判断何处造成内存溢出。
```
-Dfile.encoding=UTF-8    
-Xms20m -Xmx20m ##设置堆大小20m，并将最小和最大值设置相等，避免扩展
-XX:+HeapDumpOnOutOfMemoryError ##dump出当前的内存堆转储快照
-XX:HeapDumpPath=E:\job   ##指定路径(转储文件还是挺大的)
-XX:SurvivorRatio=8    ## 存活比2:8
```

![Eclispe Menmory Analyzer](https://i.imgur.com/DfWsc7G.png)

### 通过Unsafe分配内存(Very Bad!!!)
```Java
	public static void main(String[] args) throws Exception{
		Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
		long allocateMemory = unsafe.allocateMemory(_1MB);
		System.out.println(Long.toHexString(allocateMemory));
		unsafe.freeMemory(allocateMemory);
	}
```
>类似于C语言中的malloc和free。

### 垃圾收集器与内存分配策略
* 垃圾收集的三个问题
1. 什么内存需要回收
2. 什么时候回收
3. 如何回收

* 程序计数器，Java栈和本地方法区是线程私有的，和线程的生命周期一致。
	* 这三块区域在编译完成后的大小基本已经确定。
* Java堆和方法区
	* 对象实例的大小是无法确定的。
	* 方法区中的循环，分支造成的方法区所占的大小是无法确定的。
	* 垃圾收集器关注的就是这两块区域。

### 如何确定对象的区域是否仍然存活
#### 引用计数算法（JVM使用的并不是该方法）
1. 给对象添加一个引用计数器，当被引用时就加1，引用失效时就减1。
2. 缺陷：两个未失效的对象相互引用，都未失效但是变成了野指针。
```Java
	ObjA.instance = ObjB;
	ObjB.instance = ObjA;
```
由于两个对象相互引用，rc均不为0，但实际上都是游离的对象。

3. [Linux使用jstat命令查看jvm的GC情况](https://blog.csdn.net/zlzlei/article/details/46471627)

#### 根搜索算法（JVM使用的方法）
>通过一系列“GC ROOT”对象作为起始点，从这些结点开始向下搜索，搜索所走过的路径被称为引用链（Reference Chain）,当一个对象到所有GC ROOTS均不可达，说明是游离对象，可以被清理。

* 可以作为GC Root的对象：
1. JVM栈中的引用对象。（线程没有消亡时，是一个reference）
2. 方法区中的类静态属性引用的对象。（静态变量的reference，引用存储在方法区）
	`public static Integer i = new Integer(1);`
3. 方法区中的常量引用的对象。
	`private final Double PI = 3.1415D;`
4. 本地方法栈中引用对象。（Native 方法）

### 获取GC日志
```Java
    -Xms20m --jvm堆的最小值
    -Xmx20m --jvm堆的最大值
    -XX:+PrintGCTimeStamps -- 打印出GC的时间信息
    -XX:+PrintGCDetails  --打印出GC的详细信息
    -verbose:gc --开启gc日志
    -Xloggc:d:/gc.log -- gc日志的存放位置
    -Xmn10M -- 新生代内存区域的大小
    -XX:SurvivorRatio=8 --新生代内存区域中Eden和Survivor的比例
```

#### 引用
* 强引用（Strong Reference）
	* "Object obj = new Object()",只要强引用存在，内存不会被回收。
	* 强引用有引用变量指向时永远不会被垃圾回收，JVM宁愿抛出OutOfMemory错误也不会回收这种对象。
	* 如果想中断强引用和某个对象之间的关联，可以显示地将引用赋值为null，这样一来的话，JVM在合适的时间就会回收该对象。
* 软引用（Soft Reference）
	* 此处的软引用，因为一只调用了gc所以不会造成爆堆
```Java
public class GCCollection {
	private static Byte[] bytes = new Byte[1024 *1024];
	public static void main(String[] args) throws InterruptedException {
		List<SoftReference<Byte[]>> l = new LinkedList<SoftReference<Byte[]>>();
		while(true){
			l.add(new SoftReference<Byte[]>(bytes));
			System.gc();
		}
	}
}
```
	* 换成强引用，即使不断调用gc仍然会爆堆
```Java
public class GCCollection {
	private static Byte[] bytes = new Byte[1024 *1024];
	public static void main(String[] args) throws InterruptedException {
		List<Byte[]> l = new LinkedList<Byte[]>();
		while(true){
			l.add(new Byte[1024 *1024]);
			System.gc();
		}
	}
}
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at ca.mcmaster.jvm.GCCollection.main(GCCollection.java:17)
```

### Reference
1. [深入理解JVM](https://book.douban.com/subject/6522893/)
