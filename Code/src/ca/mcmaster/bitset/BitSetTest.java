package ca.mcmaster.bitset;

import java.util.BitSet;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date May 31, 2018 12:28:01 PM
 * @version 1.0
 */
public class BitSetTest {
	public final static BitSet bs;
	
	static{
		bs = new BitSet();
		bs.set(0);
		bs.set(1);
		bs.set(2);
		bs.set(3);
		bs.set(4);
		bs.set(5);
	}
	
	public static void main(String[] args) {
		BitSetTest bst = new BitSetTest();
		System.out.println(BitSetTest.bs.get(6));
		System.out.println(BitSetTest.bs.get(5));
	}
}
