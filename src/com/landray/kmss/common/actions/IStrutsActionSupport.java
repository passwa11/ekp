package com.landray.kmss.common.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.IActionConfig;
import com.landray.kmss.web.config.ModuleConfig;

/**
 * StrutsAction旧接口的支持接口，提供一些便捷API
 * 提供业务相关的两个实现类{@link KmssMultiActionController}和{@link KmssSimpleActionController}
 * @author Kilery.Chen
 * @see IActionConfig
 * @see ModuleConfig
 */
public interface IStrutsActionSupport extends IActionConfig,ModuleConfig{
    
    public static final String REDIRECT_URL_PREFIX = UrlBasedViewResolver.REDIRECT_URL_PREFIX;
    public static final String FORWARD_URL_PREFIX = UrlBasedViewResolver.FORWARD_URL_PREFIX;
    
    
    public static final String SPRING_DEFAULT_ENTRANCE_METHOD_NAME = "handleRequest";
    public static final String STRUTS_METHOD_EXECUTE = "execute";
    public static final String STRUTS_METHOD_DISPATCHMETHOD = "dispatchMethod";
    public static final String STRUTS_METHOD_UNSPECIFIED = "unspecified";
    public static final String STRUTS_METHOD_GETMETHODNAME = "getMethodName";
    public static final String STRUTS_METHOD_GETLOOKUPMAPNAME = "getLookupMapName";
    
    /**
     * 旧的Struts接口返回值（ActionForward对象）在request属性中的key
     */
    public static final String ORIGINAL_FORWARD_KEY = IStrutsActionSupport.class.getName()+".originalforward";
    
    /**
     * 在request属性中存放actionMapping实例的键值
     */
    public static final String ACTIONMAPPING_ATTRIBUTE_KEY = IStrutsActionSupport.class.getName()+".actionmapping";
    
    public static final String[] DEFAULT_METHOD_NAMES={
            SPRING_DEFAULT_ENTRANCE_METHOD_NAME,
            STRUTS_METHOD_EXECUTE,
            STRUTS_METHOD_DISPATCHMETHOD,
            STRUTS_METHOD_UNSPECIFIED,
            STRUTS_METHOD_GETMETHODNAME,
            STRUTS_METHOD_GETLOOKUPMAPNAME
        };
    public static final Class<?>[] StrutsStyleMethodTypes = {
            ActionMapping.class,
            ActionForm.class,
            HttpServletRequest.class,
            HttpServletResponse.class
        };
    /**
     * 设置本Action对应的ForwardMapping
     * @param mapping
     */
    public void setForwards(Map<String, String> map);
    
    /**
     * 获取Action对应的FormBean的类
     * @return
     * @throws Exception
     */
    public Class<?> getFormClass() throws Exception;
    
    /**
     * 根据url获取Action对应的FormBean的类，如果没有Action，则返回null
     * @param url
     * @return
     * @throws Exception
     */
    public Class<?> getFormClassByUrl(String url) throws Exception;
    
    /**
     * 获取当前Action配置实例
     * @return
     */
    public IActionConfig getActionConfig();
    
    /**
     * 原struts的Action入口方法
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * 动态创建ActionForm实例
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForm createActionForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 动态创建ActionMapping实例，默认是当前ActionConfig+GlobalModuleConfig的只读版本，单例可重用的
     * 子类可以按需扩展（不建议）
     * @param request
     * @param response
     * @return
     */
    public ActionMapping createActionMapping(HttpServletRequest request, HttpServletResponse response) ;
}
