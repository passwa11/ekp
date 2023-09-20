package com.landray.kmss.common.actions;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.datainit.forms.UploadInitForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import com.landray.kmss.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import com.landray.kmss.web.config.ForwardConfig;
import com.landray.kmss.web.config.GlobalModuleConfig;
import com.landray.kmss.web.config.IActionConfig;
import com.landray.kmss.web.config.ModuleConfig;
import com.landray.kmss.web.taglib.xform.TagUtils;
import com.landray.kmss.web.util.KmssSimpleTypeConverter;
import com.landray.kmss.web.util.RequestUtils;

/**
 * 整合StrutsAction与SpringController的抽象类，这个抽象类用于Action的配置，具体的行为逻辑由子类完成
 * 
 * @author Kilery.Chen
 * @see IStrutsActionSupport
 * @see InitializingBean
 * @see IActionConfig
 * @see ModuleConfig
 */
abstract class AbstractActionController extends AbstractController
        implements IStrutsActionSupport, InitializingBean, BeanNameAware {
    protected Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    /**
     * 全局的配置实例，单例
     */
    private GlobalModuleConfig globalModuleConfig = null;

    /**
     * 本Action对应的配置实例
     */
    protected final ActionConfig actionConfig = new ActionConfig();

    /**
     * 只读版本的actionConfig，在afterPropertiesSet构造，在runting时才是线程安全且可重用的
     */
    private ActionMapping localActionMapping;

    private ParameterNameDiscoverer parameterNameDiscoverer = null;

    private KmssSimpleTypeConverter simpleTypeConverter;

    // 用于转换请求的参数，每个Action都存在各自的表单结构，所以需要定制
    private WebBindingInitializer webBindingInitializer;

    
    //  getters & setters
    public final void setGlobalModuleConfig(GlobalModuleConfig globalModuleConfig) {
        this.globalModuleConfig = globalModuleConfig;
    }
    
    public final GlobalModuleConfig getGlobalModuleConfig() {
        return globalModuleConfig;
    }
    
    public final void setSimpleTypeConverter(KmssSimpleTypeConverter simpleTypeConverter) {
        this.simpleTypeConverter = simpleTypeConverter;
    }
    public final KmssSimpleTypeConverter getSimpleTypeConverter() {
        return simpleTypeConverter;
    }
    
    public final void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }
    public final ParameterNameDiscoverer getParameterNameDiscoverer() {
        return parameterNameDiscoverer;
    }
    
    public final WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }
    public final void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }


    /**
     * 如果在Bean里没有定义formName，那么默认使用formType的简单类名
     */
    @Override
    public final String getFormName() {
        return actionConfig.getFormName();
    }

    @Override
    public void setFormName(String formName) {
        actionConfig.setFormName(formName);
    }

    @Override
    public String getPath() {
        return this.actionConfig.getPath();
    }

    @Override
    public void setPath(String path) {
        this.actionConfig.setPath(path);
    }
    
    @Override
    public ActionMapping getMapping(){
        return localActionMapping;
    }
    
    @Override
    public IActionConfig getActionConfig() {
        return localActionMapping;
    }

    @Override
    public void setParameter(String parameter) {
        actionConfig.setParameter(parameter);
    }

    @Override
    public String getParameter() {
        return actionConfig.getParameter();
    }

    @Override
    public String getFormType() {
        return this.actionConfig.getFormType();
    }

    @Override
    public void setFormType(String formType) {
        this.actionConfig.setFormType(formType);
    }

    @Override
    public ForwardConfig findForwardConfig(String name) {
        ForwardConfig rtn = this.actionConfig.findForwardConfig(name);
        if (rtn != null) {
            return rtn;
        } else {
            return globalModuleConfig.findForwardConfig(name);
        }
    }

    /**
     * 只返回本Action以及全局的跳转，无法获取其它Action定义的跳转<br/>
     * 同名跳转以局部优先
     */
    @Override
    public ForwardConfig[] findForwardConfigs() {
        Map<String, ForwardConfig> allMap = findForwardConfigMap();
        ForwardConfig[] arr = new ForwardConfig[allMap.size()];
        arr = allMap.values().toArray(arr);
        return arr;
    }

    @Override
    public IActionConfig findActionConfig(String path) {
        return globalModuleConfig.findActionConfig(path);
    }

    @Override
    public Map<String, ForwardConfig> findForwardConfigMap() {
        Map<String, ForwardConfig> globalBase = globalModuleConfig.findForwardConfigMap();
        ForwardConfig[] local = actionConfig.findForwardConfigs();
        for (ForwardConfig fc : local) {
            if(logger.isWarnEnabled()){
                if(globalBase.containsKey(fc.getName())){
                    logger.warn("There is confliction for forward '"+fc.getName()+"', locally first: "+fc.getPath());
                }
            }
            globalBase.put(fc.getName(), fc);
        }
        return globalBase;
    }

    @Override
    public void addForwardConfig(ForwardConfig config) {
        this.actionConfig.addForwardConfig(config);
    }

    @Override
    public final ModelAndView transActionForwardToModelAndView(ForwardConfig af) {
        return globalModuleConfig.transActionForwardToModelAndView(af);
    }

    @Override
    public final ModelAndView transActionForwardToModelAndView(String name) {
        return transActionForwardToModelAndView(this.findForwardConfig(name));
    }

    @Override
    public void removeForwardConfig(ForwardConfig config) {
        actionConfig.removeForwardConfig(config);
    }

    @Override
    public final void setForwards(Map<String, String> map) {
        if (map != null) {
            Set<Entry<String, String>> entrySet = map.entrySet();
            for (Entry<String, String> entry : entrySet) {
                ActionForward actionForward = new ActionForward(entry.getKey(), entry.getValue(), false);
                addForwardConfig(actionForward);
            }
        }
    }

    @Override
    public Class<?> getFormClass() throws Exception {
        if (this.actionConfig.getFormType() != null) {
            return ClassUtils.forName(this.actionConfig.getFormType());
        }
        return null;
    }

    @Override
    public Class<?> getFormClassByUrl(String url) throws Exception {
        try {
            IStrutsActionSupport kmssAction = (IStrutsActionSupport) getApplicationContext().getBean(url,
                    IStrutsActionSupport.class);
            Class<?> formClass = kmssAction.getFormClass();
            return formClass;
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    /**
     * 该方法由框架调用，且不可重写，业务调用该方法是错误的行为
     */
    @Override
    public final ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(ModuleConfig.MODULE_CONFIG_KEY, downgradeToModuleConfig());
        return super.handleRequest(request, response);
    }

    /**
     * 适配旧Struts的接口方法，子类必须重写该方法
     */
    @Override
    public abstract ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

    /**
     * beanName就对应着该Action的path
     * @param name
     */
    @Override
    public final void setBeanName(String name){
        this.setPath(name);
    }
    /**
     * 默认的后置初始化方法，子类可谨慎重写，或调用super.afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        actionConfig.freeze();
        if (parameterNameDiscoverer == null) {
            parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        }
        if (simpleTypeConverter == null) {
            simpleTypeConverter = new KmssSimpleTypeConverter();
        }
        /*
         * 全局forward没有设置的话（可能性小），创建一个空的实例，但是能通过context获取ActionConfig
         */
        if (globalModuleConfig == null) {
            globalModuleConfig = new GlobalModuleConfig(null);
            globalModuleConfig.setApplicationContext(getApplicationContext());
        }
        localActionMapping = new ActionMapping(this);
    }

    /**
     * Spring处理请求的入口方法，子类必须重写
     */
    @Override
    protected abstract ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception;

    /**
     * 对请求处理函数的返回值进行包装的方法，子类可谨慎重写
     * @param request
     * @param returnValue
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected ModelAndView messageReturnValueIfNecessary(HttpServletRequest request,Object returnValue) {
        if(returnValue==null){
            return null;
        }
        if (returnValue instanceof ModelAndView) {
            return (ModelAndView) returnValue;
        } else if (returnValue instanceof Map) {
            return new ModelAndView().addAllObjects((Map) returnValue);
        } else if (returnValue instanceof String) {
            return new ModelAndView((String) returnValue);
        } else if (ForwardConfig.class.isInstance(returnValue)) {
            //request.setAttribute(ORIGINAL_FORWARD_KEY, returnValue);
            return transActionForwardToModelAndView((ForwardConfig) returnValue);
        } else {
            return null;
        }
    }

    /**
     * 动态创建ActionForm实例，子类可以按需在{@link #extActionForm(HttpServletRequest, HttpServletResponse, ActionForm)}
     * 中添加自己想要的内容
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see {@link KmssSpringRequestHandlerAdapter#handle(HttpServletRequest, HttpServletResponse, Object)}
     */
    @Override
    public final ActionForm createActionForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForm instance = null;
		String formName = getFormName();
		if (formName == null) {
			return null;
		}
		Object attribute = request.getAttribute(formName);
        if (attribute != null && attribute instanceof ActionForm) {
			request.setAttribute(TagUtils.FORM_BEAN, attribute);
            instance = (ActionForm) attribute;
        }else{
            if(getFormClass()!=null){
                instance = (ActionForm) BeanUtils.instantiateClass(getFormClass());
				instance.reset(localActionMapping, request);
                RequestUtils.populate(instance, request);
                if ("GET".equals(request.getMethod())){
                    String method = request.getParameter("method");
                    fillMethodGetToBaseForm(instance,method);
                }
				request.setAttribute(formName, instance);
                request.setAttribute(TagUtils.FORM_BEAN, instance);
            }
        }
        return instance;
    }

    protected void fillMethodGetToBaseForm(Object formInstance, String method) throws Exception{
        try{
            Class<?> formClass = getFormClass();
            Method setMethod_GET = formClass.getMethod("setMethod_GET", String.class);
            setMethod_GET.invoke(formInstance, method);
        }catch(NoSuchMethodException e){
            //ignored
        }catch(Exception e){
            logger.warn("Can not invoke setMethod_GET for formClass:"+getFormClass().getName(),e);
        }
    }
    
    /**
     * 动态创建ActionMapping实例，默认是当前ActionConfig+GlobalModuleConfig的只读版本，单例可重用的
     * 
     * @see {@link KmssSpringRequestHandlerAdapter#handle(HttpServletRequest, HttpServletResponse, Object)}
     * @param request
     * @param response
     * @return
     */
    @Override
    public final ActionMapping createActionMapping(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(IStrutsActionSupport.ACTIONMAPPING_ATTRIBUTE_KEY, localActionMapping);
        return localActionMapping;
    }

    /**
     * 处理action产生的异常，由于KMSS有外围统一的处理方式，所以这里默认透传异常
     * 
     * @param request
     * @param response
     * @param ex
     * @throws Exception
     */
    protected void handleException(HttpServletRequest request, HttpServletResponse response, Throwable ex)
            throws Exception {
        ReflectionUtils.rethrowException(ex);
    }

    /**
     * <pre>
     * 如果使用Spring2.5.6风格的请求函数的形参列表里存在自定义的对象，比如
     * methodName(HttpServletRequest request, HttpServletResponse response, CustomerObject obj)
     * 那么就需要设置webBindingInitializer来注册数据绑定器来构造obj
     * </pre>
     * @param request
     * @param binder
     * @throws Exception
     */
    protected void initBinder(HttpServletRequest request, KmssServletRequestDataBinder binder) throws Exception {
        if (this.webBindingInitializer != null) {
            this.webBindingInitializer.initBinder(binder, new ServletWebRequest(request));
        }
    }
    
    private ModuleConfig downgradeToModuleConfig() {
        return new ActionScopeModuleConfig();
    }
    
    /**
     * 暴露给外部用的ModuleConfig实现类，防止外部强制转换
     * @author Kilery.Chen
     *
     */
    private class ActionScopeModuleConfig implements ModuleConfig{

        @Override
        public void addForwardConfig(ForwardConfig config) {
            AbstractActionController.this.addForwardConfig(config);
        }

        @Override
        public ForwardConfig findForwardConfig(String name) {
            return  AbstractActionController.this.findForwardConfig(name);
        }

        @Override
        public ForwardConfig[] findForwardConfigs() {
            return  AbstractActionController.this.findForwardConfigs();
        }

        @Override
        public void removeForwardConfig(ForwardConfig config) {
            AbstractActionController.this.removeForwardConfig(config);
        }

        @Override
        public IActionConfig findActionConfig(String path) {
            return AbstractActionController.this.findActionConfig(path);
        }

        @Override
        public Map<String, ForwardConfig> findForwardConfigMap() {
            return AbstractActionController.this.findForwardConfigMap();
        }

        @Override
        public ModelAndView transActionForwardToModelAndView(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ModelAndView transActionForwardToModelAndView(ForwardConfig af) {
            throw new UnsupportedOperationException();
        }
    }
}
