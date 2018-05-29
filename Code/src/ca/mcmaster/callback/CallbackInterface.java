package ca.mcmaster.callback;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date May 29, 2018 12:19:13 PM
 * @version 1.0
 */
public interface CallbackInterface {
	default public void CallbackHandler(Integer v){
		System.out.println("callback " + v);
	}
}
