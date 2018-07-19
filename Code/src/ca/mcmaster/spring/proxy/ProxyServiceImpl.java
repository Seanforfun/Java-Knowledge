package ca.mcmaster.spring.proxy;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 2:25:56 PM
 * @version 1.0
 */
public class ProxyServiceImpl implements ProxyService {
	@Override
	public void doOperation() throws Exception {
		System.out.println("do operation!");
		Thread.currentThread().sleep(20);
	}
}
