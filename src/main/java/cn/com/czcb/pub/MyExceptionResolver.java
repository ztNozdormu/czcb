package cn.com.czcb.pub;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 异常处理器
 * @author User
 *
 */
public class MyExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = super.doResolveException(request, response, handler, ex);
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (null != stackTrace && stackTrace.length > 0) {
            modelAndView.addObject("errorMsg", stackTrace[0]);
        }
        return modelAndView;
    }

}
