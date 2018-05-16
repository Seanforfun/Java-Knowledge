# Inner class 内部类
* 内部类可以访问所属外部类所有的属性，包括私有属性。
* 内部类对同一个包中其他的类隐藏起来。
* 当想定义一个回调函数并不想写大量代码时，可以使用 anonymous inner class (匿名内部类)进行实现。

## 内部类访问对象状态
* 内部类的对象总有一个隐式的引用**outer**, 她指向了创建它的外部类对象。
* 对于每个外部对象，会分别有一个内部类实例。
* 内部类的所有静态域都是`final`的。
* 创建**非静态内部类**的方法：
> 创建非静态内部类必须要有外部类的实例，通过外部类的实例去创建非静态内部类实例。
```Java
public class Node {
	public static void main(String[] args) {
		Node node = new Node();
		Node.ListNode<Integer> listNode = node.new ListNode<Integer>(0);
	}
	
	class ListNode<T>{
		ListNode<T> next;
		T val;
		public ListNode(T val) {this.val = val;}
	}
}
```
* 创建**静态内部类**：
> 应为静态内部类存在静态区，所以**不需要**创建外部类实现内部类，可以直接实例化静态内部类。
```Java
public class Node {
	public static void main(String[] args) {
		Node.ListNode<Integer> listNode = new ListNode<Integer>(0);
		System.out.println(listNode.val);
	}
	
	private static class ListNode<T>{
		ListNode<T> next;
		T val;
		public ListNode(T val) {this.val = val;}
	}
}
```