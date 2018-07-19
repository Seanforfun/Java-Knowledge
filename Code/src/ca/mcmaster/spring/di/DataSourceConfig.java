package ca.mcmaster.spring.di;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 9:19:10 AM
 * @version 1.0
 */
@Component("dataSourceConfig")
public class DataSourceConfig {
	@Value("#{sysConfig.maxTimeOut}")
	private Integer maxTimeOut;
	@Value("#{sysConfig.connections}")
	private Integer connections;
	public Integer getMaxTimeOut() {
		return maxTimeOut;
	}
	public Integer getConnections() {
		return connections;
	}
}
