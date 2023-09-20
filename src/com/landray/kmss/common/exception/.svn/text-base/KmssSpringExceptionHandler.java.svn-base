package com.landray.kmss.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.landray.kmss.sys.config.loader.KmssSpringDispatcherServlet;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.config.GlobalModuleConfig;

/**
 * 
 * 默认的异常处理器， 最后被{@link KmssSpringDispatcherServlet}执行
 * @author 陈进科
 * @since 1.0  2018年12月20日
 *
 */
public class KmssSpringExceptionHandler implements HandlerExceptionResolver,Ordered{

    private  GlobalModuleConfig globalModuleConfig;
    
    private int order = Ordered.LOWEST_PRECEDENCE;
    public void setGlobalModuleConfig(GlobalModuleConfig globalModuleConfig) {
        this.globalModuleConfig = globalModuleConfig;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        KmssMessages messages = new KmssMessages();
        messages.addError(ex);
        KmssReturnPage.getInstance(request).addMessages(messages).setTitle(
                new KmssMessage("errors.unknown")).save(request);
		return globalModuleConfig.transActionForwardToModelAndView(
				GlobalModuleConfig.GLOBAL_FORWARDKEY_FAILURE);
    }

	@Override
	public int getOrder() {
		return this.order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
