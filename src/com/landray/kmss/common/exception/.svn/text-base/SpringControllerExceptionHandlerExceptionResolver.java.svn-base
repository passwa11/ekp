package com.landray.kmss.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.landray.kmss.web.util.RequestUtils;

/**
 * 
 * 只处理使用SpringMvc注解的标识的Controller产生的异常，可能是/api/**,或者是/data/**，或者其它路径格式。
 * 它实际调用@ControllerAdvice
 * @author 陈进科
 * @since 1.0  2018年12月20日
 *
 */
public class SpringControllerExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver{
	
    @Override
    protected boolean shouldApplyTo(HttpServletRequest request, Object handler) {
        if(RequestUtils.isApiRequest(request)
                ||RequestUtils.isDataRequest(request)
                ||isAnnotatedRequest(handler)){
            return true;
        }
        //返回false，可以让其后的KmssSpringExceptionHandler得到运行的机会
        return false;
    }
    
	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request,
			HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {
	    if(logger.isInfoEnabled()) {
            logger.info("Occurs exception when access a annotated request "+request.getRequestURI());
        }
        ModelAndView mv = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
        if(isAnnotatedRequest(handlerMethod)){
            //如果是Controller，则阻止异常继续传递
            if(mv==null) {
                mv = new ModelAndView();
            }
        }
        return mv;
	}
	
	private boolean isAnnotatedRequest(Object handlerMethod) {
		if(handlerMethod==null
		        || !(handlerMethod instanceof HandlerMethod)) {
			//为空表示请求还没有经过KmssAnnocatedMappingHandlerAdapter
			return false;
		}
		Class<?> beanType = ((HandlerMethod)handlerMethod).getBeanType();
		boolean isAnnotatedRequest = RequestUtils.isAnnotatedRequest(beanType);
		return isAnnotatedRequest;
	}
}
