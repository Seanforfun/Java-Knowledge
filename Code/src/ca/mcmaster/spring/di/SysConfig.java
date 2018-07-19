package ca.mcmaster.spring.di;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 9:16:46 AM
 * @version 1.0
 */
public class SysConfig {
	private Integer maxTimeOut;
	private Integer connections;
	public void initSysConfig(){
		this.maxTimeOut = 20;
		this.connections = 10;
	}
	public Integer getMaxTimeOut() {
		return maxTimeOut;
	}
	public Integer getConnections() {
		return connections;
	}
}
