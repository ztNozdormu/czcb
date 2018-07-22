package cn.com.czcb.interceptor;

import java.io.PrintWriter;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.com.czcb.controller.BaseController;
import cn.com.czcb.model.session.UserSession;
import cn.com.czcb.pub.AppContext;
import cn.com.czcb.pub.AppException;
import cn.com.czcb.pub.InitConfig;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.pub.RedisUtil;


/**
 * 服务器端session拦截
 * @author AJiong
 * @version $Id: SessionInterceptor.java, v 0.1 2018年1月24日 下午1:59:48 Ajiong Exp $
 */
public class SessionInterceptor extends HandlerInterceptorAdapter{
    /**
     * session_token
     */
    public final static String KEYDOM_SESSION_TOKEN = "keydom_session_token";
    /**
     * openid
     */
    public final static String OPENID = "openid";
    /**
     * jedis池
     */
    @Autowired
    private RedisUtil redisUtil;
	/** 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	    try {
	        String session_token = request.getHeader(KEYDOM_SESSION_TOKEN);
		    /*Enumeration<String> headerNames = request.getHeaderNames();
		    int count = 0;
		    while (headerNames.hasMoreElements()){
		    	String header = headerNames.nextElement();
			    System.out.println(header + " : " + request.getHeader(header));
			    count++;
		    }
		    System.out.println(count);*/
		    if (ObjectUtils.isEmpty(session_token)) {
	            //非法请求
	            throw new AppException("非法请求",-1);
	        }
	        if (redisUtil.get(session_token) == null) {
                //用户session已过期
	            throw new AppException("用户登录过期，请重新登录",-1);
            }
	        UserSession userSession = (UserSession) redisUtil.get(session_token);
	        //将用户信息放入到当前线程中
	        AppContext.setCurrUserSession(userSession);
        } catch (Exception e) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();  
            out.print(BaseController.returnJsonResultWithError(e));//session失效
            out.flush();
            out.close();
            return false;
        }
	    
		return super.preHandle(request, response, handler);
	}

	/** 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	    
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 
	 * @param request
	 * @return ContextPath
	 */
	public static String getContextPath(HttpServletRequest request){
		return InitConfig.getwReturnPrefix()+request.getContextPath();
	}
	

}
