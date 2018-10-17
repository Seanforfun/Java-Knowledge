package ca.mcmaster.zuul.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Oct 17, 2018 12:21:03 PM
 * @version 1.0
 */
@Component
public class MyFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(MyFilter.class);
	@Override
	public Object run() throws ZuulException {
		//通过Zuul的Context环境获取parameter
		RequestContext ctx = RequestContext.getCurrentContext();
		String token = ctx.getRequest().getParameter("token");
		if(token == null){
			logger.warn("token is empty!");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(404);
			try {
				ctx.getResponse().getWriter().write("Token is empty!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("Pass!");
		return null;
	}
	@Override
	public boolean shouldFilter() {
		return true;
	}
	@Override
	public String filterType() {
		return "pre";
	}
	@Override
	public int filterOrder() {
		return 0;
	}
}
