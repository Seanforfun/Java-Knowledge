package ca.mcmaster.oopdesign.iterator;

import java.util.Iterator;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 2, 2018 1:41:54 PM
 * @version 1.0
 */
public class MyIterable<T> implements Iterable<T> {
	private Node<T> dummy;
	private int length;
	private Node<T> head;
	private Node<T> tail;
	public MyIterable() {
		this.dummy = new Node<T>(null);
		tail = head = dummy;
	}
	@Override
	public Iterator<T> iterator() {
		return new MyIterator<T>();
	}
	
	@SuppressWarnings("hiding")
	private class MyIterator<T> implements Iterator<T>{
		@SuppressWarnings("unchecked")
		private MyIterable<T>.Node<T> current = (MyIterable<T>.Node<T>) dummy;
		@Override
		public boolean hasNext() {
			return current.next != null;
		}
		@Override
		public T next() {	
			T result = (T) current.next.t;
			current = current.next;
			return result;
		}
	}
	private class Node<T>{
		T t;
		Node<T> next;
		Node<T> pre;
		public Node(T t){
			this.t = t;
		}
	}
	public void add(T t){
		Node<T> newNode = new Node<>(t);
		if(this.length == 0){
			dummy.next = newNode;
			newNode.pre = dummy;
			this.head = dummy.next;
			this.tail = this.head;
		}else{
			newNode.pre = tail;
			tail.next = newNode;
			tail = newNode;
		}
		this.length++; 
	}
	public void remove(T t){
		Node<T> current = this.dummy;
		while(current.next != null){
			current = current.next;
			if(current.t.equals(t)){
				current.pre.next = current.next;
			}
		}
	}
	public static void main(String[] args) {
		MyIterable<Integer> it = new MyIterable<>();
		it.add(1);
		it.add(2);
		it.add(3);
		it.add(4);
		it.add(5);
//		for(Integer i : it){
//			System.out.println(i);
//		}
//		Iterator<Integer> iterator = it.iterator();
//		while(iterator.hasNext()){
//			System.out.println(iterator.next());
//		}
		it.remove(5);
		for(Integer i : it){
			System.out.println(i);
		}
	}
}
