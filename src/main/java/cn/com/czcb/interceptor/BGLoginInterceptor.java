package cn.com.czcb.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import cn.com.czcb.model.Admin;
import cn.com.czcb.model.session.AdminSession;
import cn.com.czcb.pub.AppContext;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.pub.RedisUtil;

/**
 * 后台登录的拦截器
 * @author Administrator
 *
 */
public class BGLoginInterceptor extends PubInterceptor {
	/**  */
	public static final String BG_LOGIN = "/manage/login";
	
    /**
     * redis
     */
    @Autowired
    private RedisUtil   redisUtil;

	/** 
	 * @see cn.com.czcb.interceptor.PubInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//获取当前登陆的管理员
		String adminId = (String) request.getSession().getAttribute("admin");
		if (ObjectUtils.isEmpty(adminId)) {
			//用户没有登陆
			response.sendRedirect(getContextPath(request)+BG_LOGIN);
			return false;
		}
		return super.preHandle(request, response, handler);
	}


	/** 
	 * @see cn.com.czcb.interceptor.PubInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
}
