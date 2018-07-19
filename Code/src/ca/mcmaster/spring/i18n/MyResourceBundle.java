package ca.mcmaster.spring.i18n;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 10:06:51 AM
 * @version 1.0
 */
public class MyResourceBundle {
	public static void main(String[] args) throws UnsupportedEncodingException {
		ResourceBundle enBundle = ResourceBundle.getBundle("ca/mcmaster/spring/i18n/greeting", Locale.CANADA);
		ResourceBundle cnBundle = ResourceBundle.getBundle("ca/mcmaster/spring/i18n/greeting", Locale.CHINESE);
		System.out.println(enBundle.getString("greeting.common"));
		System.out.println(new String(cnBundle.getString("greeting.common").getBytes("ISO-8859-1"), "utf-8"));
		ResourceBundle fmtBundle = ResourceBundle.getBundle("ca/mcmaster/spring/i18n/fmt_greeting", Locale.CANADA);
		MessageFormat msgFormat = new MessageFormat(fmtBundle.getString("greeting.common"), Locale.CANADA);
		Object[] params = {"Sean", new GregorianCalendar().getTime()};
		String formatMsg = msgFormat.format(params);
		System.out.println(formatMsg);
	}
}
