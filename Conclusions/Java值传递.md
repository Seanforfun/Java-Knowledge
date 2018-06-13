# Java值传递

### Java Pass Value exmaple
```Java
	class Test{
		String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	private static void call(Test t){
		Test tnew = new Test();
		t.setName("abc");
		System.out.println(t);	//ca.mcmaster.chapter.one.Test@2a8b83e3
		tnew.setName("111");
		t = tnew;
		System.out.println(t);	//ca.mcmaster.chapter.one.Test@2d7fc1e7
	}
	public static void main(String[] args) {
		//Java pass value test
		Test t = new Test();
		System.out.println(t);	//ca.mcmaster.chapter.one.Test@2a8b83e3
		call(t);
		System.out.println(t);	//ca.mcmaster.chapter.one.Test@2a8b83e3
	}
}
```

### 总结
1. 基本数据类型都是值传递。
2. 引用类型传递是引用的拷贝，既不是引用本身，更不是对象。（这一点非常重要）
* 因为java本身不存在地址，传参时相当于复制了当前地址的引用，并将复制的引用进行传递。
	* 那为什么可以改变引用对象的引用的值呢？
	```Java
	public class PassValue {
	class Test{
		private Integer test = new Integer(0);
	}
	private int i = 0;
	public void change(Test t){
		t.test ++;	//t是JVM栈中被复制出来的引用。但是引用实际上是一个指针，所以可以通过指针（引用）修改指向地址（在JAVA堆中）的值。
	}
	public static void main(String[] args) {
		Integer i = new Integer(0);
		PassValue passValue = new PassValue();
		Test test = passValue.new Test();
		passValue.change(test);	//传入的是一个引用类型，实际上传入的引用的复制，在Java Stack中复制了一块完全一致的内存块。
		System.out.println(test.test);	//最后发现test的值被更改了。
	}
}
```

* 所谓的值传递：
1. 基本数据类型，传递的是值，所以作为形参传递的基本数据类型可以被读取，但是无法在方法中修改原，因为传递的是值，实际上在Java栈中复制出了一个相同的值，所以无法被修改。
2. 引用类型
	* 传递的是复制出来的引用。引用指向的是一块内存区域，所以可以修改该内存区域内部的值，说明原对象的只可以被改变。
	* 无法改变原始的引用，就是说line34，并没有改变obj对2a8b83e3的引用。
	* 数组也是对象。