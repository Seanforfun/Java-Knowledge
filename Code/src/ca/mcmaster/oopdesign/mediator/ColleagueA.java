package ca.mcmaster.oopdesign.mediator;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 15, 2018 12:00:31 PM
 * @version 1.0
 */
public class ColleagueA extends AbstractColleague {
	public void self(){
		System.out.println("Colleague A -> do self.");
	}
	@Override
	public void out() throws Exception{
		System.out.println("Colleague A-> Colleague B please help!");
		super.mediator.execute("b", mediator.map.get("b").getClass().getDeclaredMethod("self", null));
	}
}
