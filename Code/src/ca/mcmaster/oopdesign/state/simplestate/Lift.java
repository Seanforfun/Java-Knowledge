package ca.mcmaster.oopdesign.state.simplestate;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 4, 2018 9:45:03 AM
 * @version 1.0
 */
public class Lift implements ILift {
	private int state;
	@Override
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public void open() {
		switch (this.state) {
		// 如果当前状态是打开，则什么都不做。
		case OPENING_STATE:	
			break;
		case CLOSE_STATE:
			this.openWithoutLogic();
			break;
		case RUNNING_STATE:
			break;
		case STOPPING_STATE:
			this.openWithoutLogic();
			break;
		default:
			break;
		}
	}
	@Override
	public void close() {
		switch (this.state) {		
		case OPENING_STATE:
			closeWithoutLogic();
			break;
		case CLOSE_STATE:
			break;
		case RUNNING_STATE:
			break;
		case STOPPING_STATE:
			break;
		default:
			break;
		}
	}
	@Override
	public void run() {
		switch (this.state) {		
		case OPENING_STATE:
			break;
		case CLOSE_STATE:
			runWithoutLogic();
			break;
		case RUNNING_STATE:
			break;
		case STOPPING_STATE:
			runWithoutLogic();
			break;
		default:
			break;
		}
	}
	@Override
	public void stop() {
		switch (this.state) {		
		case OPENING_STATE:
			break;
		case CLOSE_STATE:
			stopWithoutLogic();
			break;
		case RUNNING_STATE:
			stopWithoutLogic();
			break;
		case STOPPING_STATE:
			break;
		default:
			break;
		}
	}
	private void openWithoutLogic(){
		System.out.println("Elevator open.");
		setState(OPENING_STATE);
	}
	private void closeWithoutLogic(){
		System.out.println("Elevator close.");
		setState(CLOSE_STATE);
	}
	private void runWithoutLogic(){
		System.out.println("Elevator run.");
		setState(RUNNING_STATE);
	}
	private void stopWithoutLogic(){
		System.out.println("Elevator stop.");
		setState(STOPPING_STATE);
	}
	public static void main(String[] args) {
		ILift elevator = new Lift();
		elevator.setState(STOPPING_STATE);
		elevator.open();
		elevator.close();
		elevator.run();
		elevator.stop();
	}
}
