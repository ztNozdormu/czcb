package cn.com.czcb.pub;


import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.com.czcb.model.session.UserSession;

/**
 * 
 * @author AJiong
 * @version $Id: AppContext.java, v 0.1 2018年2月1日 上午10:02:43 Ajiong Exp $
 */
@Component
public class AppContext implements ApplicationContextAware{
	/**  */
	private static ApplicationContext springContext;
	/**
	 * 当前线程
	 */
	private static ThreadLocal<UserSession> current = new ThreadLocal<UserSession>();
	
	/**
	 * 系统初始化
	 */
	@javax.annotation.PostConstruct
	public void init(){
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxsyetem initedxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	}
	/**
	 * 系统关闭
	 */
	@javax.annotation.PreDestroy
	public void release(){
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxsystem shutdownxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	}
	
	/** 
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
    public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		springContext = context;
		
	}

	/**
	 * 
	 * @return ApplicationContext
	 */
	public static ApplicationContext getSpringContext() {
		return springContext;
	}
	
	/**
	 * 获取当前UserSession
	 * @return UserSession
	 */
	public static UserSession getCurrUserSession(){
	    return current.get();
	}
	/**
	 * 将userSession放入到当前线程中
	 * @param userSession
	 */
	public static void setCurrUserSession(UserSession userSession){
	    current.set(userSession);
	}
	/**
	 * 
	 * @param key
	 * @param params
	 * @return string
	 */
	public static String getAppMessage(String key,Object... params){
		String appMessage = key;
		try{
			appMessage = springContext.getMessage(key, params, Locale.getDefault());
		}catch (Exception e) {
			ObjectUtils.logError("", e);
		}
		return appMessage;
	}
}
