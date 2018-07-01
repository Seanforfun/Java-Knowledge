package ca.mcmaster.oopdesign.observer;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 1, 2018 5:39:51 PM
 * @version 1.0
 */
public interface Observable {
	public void registerObserver(Observer observer);
	public void deleteObserver(Observer observer);
	public void notifyObserver(String message);
}
