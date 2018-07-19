package ca.mcmaster.spring.i18n;

import java.text.MessageFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 9:48:25 AM
 * @version 1.0
 */
public class MyMessageFormat {
	public static void main(String[] args) {
		// 信息格式化串
		String chMsg = "{0}, 你好！你于{1}在工商银行存入{2}元！";
		String usMsg = "At {1, time, short} On {1, date,long}, {0} saved {2, number, currency}";
		// 定义动态占位符的参数
		Object[] params = {"Sean", new GregorianCalendar().getTime(), 1000000000};
		// 指定国际化信息
		MessageFormat zhMf = new MessageFormat(chMsg, Locale.CHINESE);
		//装配参数
		String zhPattern = zhMf.format(params);
		System.out.println(zhPattern);
		MessageFormat usMf = new MessageFormat(usMsg, Locale.CANADA);
		String usPattern = usMf.format(params);
		System.out.println(usPattern);
	}
}
