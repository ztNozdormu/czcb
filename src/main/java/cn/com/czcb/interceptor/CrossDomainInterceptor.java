package cn.com.czcb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author jiangbo
 *
 */
public class CrossDomainInterceptor extends HandlerInterceptorAdapter{

    /**日志工具  */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化拦截器
     */
    @SuppressWarnings("unused")
    private void init() {
        org.apache.ibatis.logging.LogFactory.useLog4JLogging();
        if (logger.isInfoEnabled()) {
            logger.info("CrossDomainInterceptor init success");
        }
    }

    /** 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token,adminId,session_token");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        return super.preHandle(request, response, handler);

    }
    
}
