package cn.com.czcb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.com.czcb.pub.InitConfig;

/**
 * 拦截器公共父类，设置编码和固定前置url及端口（用于nginx）
 * @author Administrator
 *
 */
public class PubInterceptor extends HandlerInterceptorAdapter{
	/**  */
	static final String ENCODING = "UTF-8";

	/** 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding(ENCODING);
		response.setCharacterEncoding(ENCODING);
		request.setAttribute("contextPath", InitConfig.getwReturnPrefix()+request.getContextPath());
		return super.preHandle(request, response, handler);
	}

	/** 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		response.setCharacterEncoding(ENCODING);
		request.setCharacterEncoding(ENCODING);
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
