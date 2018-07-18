package ca.mcmaster.spring.di;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 18, 2018 11:53:09 AM
 * @version 1.0
 */
public class CarFactoryBean implements FactoryBean<Car> {
	private String carInfo;
	public String getCarInfo() {
		return carInfo;
	}
	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}
	@Override
	public Car getObject() throws Exception {
		Car car = new Car();
		car.setBrand("TESLA");
		car.setColor("white");
		return car;
	}
	@Override
	public Class<?> getObjectType() {
		return Car.class;
	}
	@Override
	public boolean isSingleton() {
		return false;
	}
}
