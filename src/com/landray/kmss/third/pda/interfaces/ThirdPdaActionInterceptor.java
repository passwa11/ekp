package com.landray.kmss.third.pda.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.landray.kmss.common.actions.KmssDefaultActionInterceptor;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.config.ForwardConfig;
import com.landray.kmss.web.config.ModuleConfig;
import com.landray.kmss.web.util.ModelAndViewUtils;

public final class ThirdPdaActionInterceptor extends KmssDefaultActionInterceptor {

    private ModuleConfig globalModuleConfig = null;
    
    public static final String THIRD_4M = ".4m";
    public static final String THIRD_PDA = ".4pda";
    public static final String THIRD_4OFFLINE = ".4offline";
    public static final String OFFLINE_TYPE = "offline";
    public void setGlobalModuleConfig(ModuleConfig globalModuleConfig) {
        this.globalModuleConfig = globalModuleConfig;
    }

    private ModuleConfig getActualModuleConfig(Object handler) {
        if(handler instanceof ModuleConfig){
            return (ModuleConfig)handler;
        }
        return globalModuleConfig;
    }

    
    /**
     * 当且仅当controller的执行结果是以配置的名称作为跳转时有效，否则透传
     * ModelAndViewUtils.replaceViewName(modelAndView,fc.getName());相当于struts的return actionForward;
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        ModuleConfig actualModuleConfig = getActualModuleConfig(handler);
        if(actualModuleConfig==null){
            return;
        }
		if ((PdaFlagUtil.checkClientIsPda(request)
				|| PdaFlagUtil.checkIsMobilePriview(request))
				&& modelAndView != null) {
            String viewName = modelAndView.getViewName();
            
            
            if (StringUtil.isNotNull(viewName)) {
                //对应于struts的ActionForward.getName();
                String stripPrefix = ModelAndViewUtils.stripPrefix(viewName);
                //跳转配置
                ForwardConfig fc = null;
                /*
                 * 只命中其中一个if即return
                 */
                if(actualModuleConfig.findForwardConfig(stripPrefix)!=null){
                    
                    String lResponseType = request.getHeader("Landray-Resonponse-Type");
                    if (OFFLINE_TYPE.equals(lResponseType)) {
                        request.setAttribute("LandrayOffline", "true");
                    }
                    if (OFFLINE_TYPE.equals(lResponseType) 
                            && !stripPrefix.toLowerCase().endsWith(THIRD_4OFFLINE)) {
                        fc = actualModuleConfig.findForwardConfig(stripPrefix + THIRD_4OFFLINE);
                        if (fc != null) {
                            ModelAndViewUtils.replaceViewName(modelAndView,fc.getName());
                        }
                    }
                    
                    /*
                     * 如果在配置中找到了这个viewname对应的ForwardConfig，表示这是一个name而不是一个path
                     * 这种情况下才去判断是否跳转
                     */
                    if (fc==null
                            &&!stripPrefix.toLowerCase().endsWith(THIRD_4M)) {
                        fc = actualModuleConfig.findForwardConfig(stripPrefix+ THIRD_4M);
                        if (fc != null) {
                            ModelAndViewUtils.replaceViewName(modelAndView,fc.getName());
                        }
                    }
                    if (fc==null
                            &&!stripPrefix.toLowerCase().endsWith(THIRD_PDA)) {
                        fc = actualModuleConfig.findForwardConfig(stripPrefix + THIRD_PDA);
                        if (fc != null) {
                            request.setAttribute("Pda_PrevServletPath",
                                    request.getServletPath());
                        }
                    }
                    //--------------------------------------------------------------------------------
                    String checkMobilePage = request.getParameter("checkMobilePage");
                    if ("true".equals(checkMobilePage)) {
                        if (fc != null
                                && fc.getName().endsWith(THIRD_4M)) {
                            request.setAttribute("mobilePageEnable", true);
                        } else {
                            request.setAttribute("mobilePageEnable", false);
                        }
                        fc = actualModuleConfig.findForwardConfig("mobilePageEnable");
                        if(fc!=null){
                            ModelAndViewUtils.replaceViewName(modelAndView,fc.getName());
                        }
                    }
                }
            }
        }
		//针对移动优化做的处理#139414
        if (PdaFlagUtil.checkClientIsPda(request) && StringUtil.isNull(request.getParameter("_data"))) {
            String method = request.getParameter("method");
            if (StringUtil.isNotNull(method)) {
                String url = ThirdPdaSeparateService.formatDataUrl(request,method);
                // 获取映射的视图
                String viewName = ThirdPdaSeparateService.getViewName(url);
                String mobileUrl = ThirdPdaSeparateService.getMobileUrl(url, request);
                if (StringUtil.isNotNull(viewName)) {
                    ForwardConfig forwardConfig = actualModuleConfig.findForwardConfig(viewName);
                    if(forwardConfig != null){
                        ModelAndViewUtils.replaceViewName(modelAndView, viewName);
                    }else {
                        ModelAndViewUtils.replaceViewName(modelAndView, mobileUrl);
                    }
                }
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        
    }
}
