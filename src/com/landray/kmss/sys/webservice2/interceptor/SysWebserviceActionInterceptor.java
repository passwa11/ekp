package com.landray.kmss.sys.webservice2.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.landray.kmss.common.actions.KmssDefaultActionInterceptor;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.config.ForwardConfig;
import com.landray.kmss.web.config.ModuleConfig;
import com.landray.kmss.web.taglib.xform.TagUtils;
import com.landray.kmss.web.util.ModelAndViewUtils;

public final class SysWebserviceActionInterceptor extends KmssDefaultActionInterceptor {
	
	private ModuleConfig globalModuleConfig = null;
	
    public static final String JSON_TYPE = ".json";
    public static final String THIRD_4JSON = ".4json";
	
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
        String jType = (String)request.getParameter("j_dataType");
        
        if (modelAndView != null) {
            String viewName = modelAndView.getViewName();
            
            
            if (JSON_TYPE.equals(jType) && StringUtil.isNotNull(viewName)) {
            	request.setAttribute("form", request.getAttribute(TagUtils.FORM_BEAN));
                //对应于struts的ActionForward.getName();
                String stripPrefix = ModelAndViewUtils.stripPrefix(viewName);
                //跳转配置
                ForwardConfig fc = null;
                /*
                 * 只命中其中一个if即return
                 */
                if(actualModuleConfig.findForwardConfig(stripPrefix)!=null){
                    
                	
        			if (!stripPrefix.toLowerCase().endsWith(THIRD_4JSON)) {
        				
                        fc = actualModuleConfig.findForwardConfig(stripPrefix + THIRD_4JSON);
                        if (fc != null) {
                            ModelAndViewUtils.replaceViewName(modelAndView,fc.getName());
                        }else{
        					if("view".equals(stripPrefix)||"stylepage".equals(stripPrefix)||"edit".equals(stripPrefix)||"add".equals(stripPrefix)||"saveadd".equals(stripPrefix)){

        						ForwardConfig viewFc = actualModuleConfig.findForwardConfig("jsonView");
                                if (fc != null) {
                                    ModelAndViewUtils.replaceViewName(modelAndView,viewFc.getName());
                                }
        					}else if ("list".equals(stripPrefix)||"listChildren".equals(stripPrefix)||("listTodo".equals(stripPrefix))){

        						ForwardConfig listFc = actualModuleConfig.findForwardConfig("jsonList");
                                if (fc != null) {
                                    ModelAndViewUtils.replaceViewName(modelAndView,listFc.getName());
                                }
        					}else if("success".equals(stripPrefix)){
        						ForwardConfig successFc = actualModuleConfig.findForwardConfig("jsonSuccess");
                                if (fc != null) {
                                    ModelAndViewUtils.replaceViewName(modelAndView,successFc.getName());
                                }
        					}else if("failure".equals(stripPrefix)){
        						ForwardConfig failureFc = actualModuleConfig.findForwardConfig("jsonFailure");
                                if (fc != null) {
                                    ModelAndViewUtils.replaceViewName(modelAndView,failureFc.getName());
                                }
        					}else if("redirect".equals(stripPrefix)){
        						ForwardConfig ridrectFc = actualModuleConfig.findForwardConfig("jsonRedirect");
                                if (fc != null) {
                                    ModelAndViewUtils.replaceViewName(modelAndView,ridrectFc.getName());
                                }
        					}else if("e403".equals(stripPrefix)){
        						request.setAttribute("type", "e403");
        						ForwardConfig failureFc = actualModuleConfig.findForwardConfig("jsonFailure");
                                if (fc != null) {
                                    ModelAndViewUtils.replaceViewName(modelAndView,failureFc.getName());
                                }
        					}else if("e404".equals(stripPrefix)){
        						request.setAttribute("type", "e404");
        						ForwardConfig failureFc = actualModuleConfig.findForwardConfig("jsonFailure");
                                if (fc != null) {
                                    ModelAndViewUtils.replaceViewName(modelAndView,failureFc.getName());
                                }
        					}else if("e500".equals(stripPrefix)){
        						request.setAttribute("type", "e500");
        						ForwardConfig failureFc = actualModuleConfig.findForwardConfig("jsonFailure");
                                if (fc != null) {
                                    ModelAndViewUtils.replaceViewName(modelAndView,failureFc.getName());
                                }
        					}else if("e503".equals(stripPrefix)){
        						request.setAttribute("type", "e503");
        						ForwardConfig failureFc = actualModuleConfig.findForwardConfig("jsonFailure");
                                if (fc != null) {
                                    ModelAndViewUtils.replaceViewName(modelAndView,failureFc.getName());
                                }
        					}
        				}
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
