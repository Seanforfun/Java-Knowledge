package ca.mcmaster.spring.i18n;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 9:38:41 AM
 * @version 1.0
 */
public class MyNumberFormat {
	public static void main(String[] args) {
		Locale ch = new Locale("zh", "CN");
		NumberFormat zhnf = NumberFormat.getCurrencyInstance(ch);
		Locale ca = new Locale("en", "CA");
		NumberFormat canf = NumberFormat.getCurrencyInstance(ca);
		double currency = 12345.6D;
		System.out.println(zhnf.format(currency));
		System.out.println(canf.format(currency));
	}
}
