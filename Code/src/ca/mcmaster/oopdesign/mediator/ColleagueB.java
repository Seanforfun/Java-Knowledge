package ca.mcmaster.oopdesign.mediator;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 15, 2018 12:00:31 PM
 * @version 1.0
 */
public class ColleagueB extends AbstractColleague {
	public void self(){
		System.out.println("Colleague B -> do self.");
	}
	public void out() throws Exception{
		System.out.println("Colleague B-> Colleague A please help!");
		super.mediator.execute("a", mediator.map.get("a").getClass().getDeclaredMethod("self", null));
	}
}
