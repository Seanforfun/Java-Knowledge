package ca.mcmaster.oopdesign.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 1, 2018 6:03:32 PM
 * @version 1.0
 */
public class WeChatServer implements Observable {
	List<Observer> registeredUser = new ArrayList<Observer>();
	@Override
	public void registerObserver(Observer observer) {
		registeredUser.add(observer);
	}
	@Override
	public void deleteObserver(Observer observer) {
		registeredUser.remove(observer);
	}
	@Override
	public void notifyObserver(String message) {
		for(Observer observer:registeredUser){
			observer.update(message);
		}
	}
	public static void main(String[] args) {
		Observable observable = new WeChatServer();
		User1 user1 = new User1();
		User2 user2 = new User2();
		observable.registerObserver(user1);
		observable.registerObserver(user2);
		observable.notifyObserver("Hello It's me!");
		user1.loadMessage();
		user2.loadMessage();
		observable.deleteObserver(user2);
		observable.notifyObserver("Hello again!");
		user1.loadMessage();
		user2.loadMessage();
	}
}
