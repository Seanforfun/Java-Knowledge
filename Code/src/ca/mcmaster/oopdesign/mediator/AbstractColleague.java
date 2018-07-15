package ca.mcmaster.oopdesign.mediator;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 15, 2018 11:58:13 AM
 * @version 1.0
 */
public abstract class AbstractColleague {
	protected Mediator mediator;
	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}
	public abstract void self();
	public abstract void out() throws Exception;
}
