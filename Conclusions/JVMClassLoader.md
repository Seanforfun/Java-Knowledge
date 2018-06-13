# JVMClassLoader

### Class类文件结构
1. Class文件是一组以8位字节为基础单位的二进制流。
2. 当遇到需要占用8字节以上空间的数据项时，会按照高位在前的方式分割若干个8位字节进行存储。
3. Class文件格式采用一种类似C语言结构体的伪结构储存：
	* 无符号数
	* 表是由多个无符号数或其他表作为数据项构成的复合数据类型。

![Class文件格式](https://i.imgur.com/UegLrwk.jpg)

#### 魔数与Class文件的版本
1. 每个Class文件的头4个字节叫做魔数（Magic Number），确定这个文件能否被JVM接受。
![Magic Number](https://i.imgur.com/NEBFnqp.png)

2. 第5,6字节表示词版本号，7,8代表主版本号。
![Version](https://i.imgur.com/LjDv7Vz.png)

3. 常量池
	* 常量池的长度是不固定的，常量池的开始有u2来代表常量池容量计数值。
		* 字面量（Literal）,文本字符串，被声明为final的常量值。
		* 符号引用（Symbolic Reference）
			* 类的接口和全限定名。（Fully qualified name）
			* 字段的名称和描述符。（Field Descriptor）
			* 方法的名称和描述符。（Method Descriptor）

![常量池的项目类型](https://i.imgur.com/lK6WaPK.jpg)

#### 访问标志
1. 访问标识用于识别一些类或接口的层次的访问信息。
![访问标志](https://i.imgur.com/iuz6sPi.jpg)

#### 类引索（this_class），父类引索(super_class)，接口引索(interfaces)
1. 类引索用于确定这个类的全限定名。
2. 父类引索用于确定这个类的父类的全限定名。
3. 接口引索用来描述这个类实现了哪些接口。

#### 字段表集合
1. 字段表用于描述接口或类中声明的变量。（Fields）
	* 字段的作用域。（public, private, protected, default）
	* 是否是静态变量。（static）
	* 可变性（final）
	* 并发可见性（volatile，是否强制从内存读写）
	* 可否序列化（transient）
	* 字段数据类型（基本类型，对象，数组）
	* 字段名称

#### 方法表集合
1. 方法表用于描述类中的方法（Methods）
	* 访问标志（Access flag）
	* 名称引索（name index）
	* 描述符引索（descriptor index）
	* 属性集合表（attributes）

#### 属性集合表
1. 在Class文件，字段表，方法表都可以携带自己的属性表集合。
![虚拟机规范](https://i.imgur.com/VN8VAzw.jpg)
* Code属性
	* Javac编译器处理Java程序，最终变成字节码指令存在Code属性中。
	* 只有方法的实体才会具有Code属性，而接口和抽象类中的一些方法就没有Code属性。
* Exception属性
	* Exception属性列出方法的可能的受查异常。
* LineNumberTable属性
	* 它用于描述Java源码行号与字节码行号之间的对应关系。
* LocalVariableTable属性
	* 它用于描述栈帧中局部变量表中的变量与Java源码中定义的变量之间的对应关系。
* SourceFile属性
	* 它用于记录生成这个Class文件的源码文件名称。
* ConstantValue属性
	* ConstantValue属性的作用是通知虚拟机自动为静态变量赋值，只有被static修饰的变量才可以使用这项属性。
* InnerClasses属性
	*  该属性用于记录内部类与宿主类之间的关联。如果一个类中定义了内部类，那么编译器将会为它及它所包含的内部类生成InnerClasses属性。
* Deprecated属性
	* 该属性用于表示某个类、字段和方法，已经被程序作者定为不再推荐使用，它可以通过在代码中使用@Deprecated注释进行设置。
* Synthetic属性
	* 该属性代表此字段或方法并不是Java源代码直接生成的，而是由编译器自行添加的，如this字段和实例构造器、类构造器等。