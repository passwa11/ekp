package com.landray.kmss.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.landray.kmss.common.actions.KmssDefaultActionInterceptor;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.web.config.ModuleConfig;
import com.landray.kmss.web.util.ModelAndViewUtils;
import com.landray.sso.client.util.StringUtil;

/**
 * <pre>
 * 替代原KmssStrutsRequestProcessor的类，必须作为第一个interceptor设置到BeanNameUrlHandlerMapping
 * 它负责在入口处校验权限，出口处翻译ModelAndView.getViewName()的语义
 * </pre>
 * @author Kilery.Chen
 */
public final class KmssDefaultRequestHandleInterceptor extends KmssDefaultActionInterceptor {

    private ModuleConfig globalModuleConfig = null;

    private boolean noCache = false;
    
    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }

    public boolean getNoCache() {
        return noCache;
    }

    public void setGlobalModuleConfig(ModuleConfig globalModuleConfig) {
        this.globalModuleConfig = globalModuleConfig;
    }

    private ModuleConfig getActualModuleConfig(Object handler) {
        if (handler instanceof ModuleConfig) {
            return (ModuleConfig) handler;
        }
        return globalModuleConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(getNoCache()){
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
            response.setDateHeader("Expires", 1);
        }
        // 为了避免权限漏洞，url的forward参数不运行带“.4”这个字符串，如：view.4pda，
        // 这个页面系统认为应该是pda专用的页面，不允许forward过去
        String forward = request.getParameter("forward");
        if (StringUtil.isNotNull(forward) && forward.indexOf(".4") > -1) {
            throw new UnexpectedRequestException();
        }
        return true;
    }

    
    /**
     * viewName优先当做struts的forwardname处理（在spring-mvc里配置的），其次是path
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if(modelAndView==null){
            return;
        }
        ModuleConfig actualModuleConfig = getActualModuleConfig(handler);
        if(actualModuleConfig==null){
            return;
        }
        String viewName = modelAndView.getViewName();
        if (StringUtil.isNotNull(viewName)) {
            String strippedViewName = ModelAndViewUtils.stripPrefix(viewName);
            if(actualModuleConfig.findForwardConfig(strippedViewName)!=null){
                //表示是一个name，需要把它转义成path
                String path = actualModuleConfig.findForwardConfig(strippedViewName).getPath();
                ModelAndViewUtils.replaceViewName(modelAndView, path);
            }
        }
    }
}
