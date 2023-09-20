package com.landray.kmss.common.actions;

import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.datainit.forms.UploadInitForm;
import com.landray.kmss.web.util.RequestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import com.landray.kmss.common.exception.NoSuchRequestHandlingMethodException;

import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * <pre>
 * 曾经在Struts框架下所有继承com.landray.kmss.common.actions.BaseAction的Action都改为继承此类
 * 该类
 * <b>execute方法的默认功能是dispatchMethod，子类可按需扩展</b>
 * <b>部分支持Spring2.5.6风格的方法调用:
 * public (ModelAndView | Map | String | void) actionName(HttpServletRequest request, HttpServletResponse response,Object... obj);
 * 前提是需用{@link RequestHandleMethod}标注，存在自定义入参需要自行扩展bind</b>
 * 比如:
 * <code>
 *  \@SpringMethod
 *  public String demo(HttpServletRequest request, HttpServletResponse response, 
 *      \@RequestParam(value="userName") String userName,Integer score)
 *          throws Exception {
 *      System.out.println("userName:"+userName+", score:"+score);
 *      //do something...
 *      return "moduleindex";
 *  }
 *  </code>
 * </pre>
 * 
 * @author Kilery.Chen
 *
 */
abstract class KmssMultiActionController extends AbstractActionController{

    private final Map<String, Method> strutsStyleHandleMethods = new HashMap<String, Method>();
    private final Map<String, Method> springStyleHandleMethods = new HashMap<String, Method>();
    
    protected KmssMultiActionController() {
        scanStrutsHandlerMethod();
        scanSpringHandlerMethod();
    }


    /**
     * <pre>
     * 如果方法名参数为空，默认{@link #unspecified(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
     * 将被调用 。子类可以重写{@link #dispatchMethod(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse, String)}
     * 的逻辑自定义分发规则
     * </pre>
     */
    @Override
    protected final ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //String methodName = getMethodNameResolver().getHandlerMethodName(request);
        String methodName = getMethodName(createActionMapping(request,response), 
                createActionForm(request,response), request, response, getParameter());
        if(StringUtil.isNull(methodName)){
            /*
             * 此处不直接调用unspecified方法是因为子类可以自定义方法的来源
             */
            Method executeMethod = strutsStyleHandleMethods.get(STRUTS_METHOD_EXECUTE);
            return invokeMethod(executeMethod, StrutsStyleMethodTypes, request, response);
        }else{
            return chooseStyle(methodName, request, response);
        }
    }

    /**
     * <pre>
     *  重写DispatchAction的入口函数，该方法在用户访问Struts风格的方法或方法名参数为空的情况下被调用。
     *  为兼容旧代码，默认的内部逻辑与{{@link #handleRequestInternal(HttpServletRequest, HttpServletResponse)}
     *  一致，仅起到按方法名分发处理的作用。
     *  子类请谨慎Override
     * </pre>
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String methodName = getMethodName(mapping, form, request, response, getParameter());
        if (isDefaultMethodName(methodName)) {
            String msg = "Can not dispatch to default method:" + methodName;
            throw new IllegalArgumentException(msg);
        }
        return dispatchMethod(mapping, form, request, response, methodName);
    }

    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnexpectedRequestException();
    }

    /**
     * 重写原struts跳转方法
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param name
     * @return
     * @throws Exception
     */
    protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response, String name) throws Exception {
        if (StringUtil.isNull(name)) {
            return this.unspecified(mapping, form, request, response);
        }
        if (isDefaultMethodName(name)) {
            String msg = "Can not dispatch to default method:" + name;
            throw new IllegalArgumentException(msg);
        }
        // 因为dispatchMethod只是兼容Struts风格用的，所以此处只允许struts风格的方法被调用
        Method method = this.strutsStyleHandleMethods.get(name);
        if (method == null) {
            throw new NoSuchRequestHandlingMethodException(name, getClass());
        }
        ActionForward forward = (ActionForward) method.invoke(this, mapping, form, request, response);
        return forward;
    }

    

    /**
     * 兼容struts DispatchAction获取方法名的接口，交给子类去实现，因此不采用spring的
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param parameter
     * @return
     * @throws Exception
     */
    protected abstract String getMethodName(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            String parameter) throws Exception;

    protected abstract String getLookupMapName(HttpServletRequest request,
            String keyName, ActionMapping mapping) throws ServletException ;
    
    /**
     * 方法的代码风格分水岭，如果是struts类型的，默认调用execute
     * 
     * @param methodName
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private ModelAndView chooseStyle(String methodName, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        boolean isStrutsMethod = strutsStyleHandleMethods.containsKey(methodName);
        boolean isSpringMethod = springStyleHandleMethods.containsKey(methodName);
        ModelAndView mav = null;
        if (isStrutsMethod) {
            mav = processStrutsAction(methodName, request, response);
        } else if (isSpringMethod) {
            mav = processSpringController(methodName, request, response);
        }
        return mav;
    }

    /**
     * 该方法用于校验方法是不是spring标准的request处理函数，目前只支持spring2.5.6的风格 <br/>
     * 即doSomething(HttpServletRequest request, HttpServletResponse response,
     * Object ...)
     * 
     * @param method
     * @return
     */
    protected boolean isSpringHandlerMethod(Method method) {
        RequestHandleMethod annotation = method.getAnnotation(RequestHandleMethod.class);
        if (annotation == null) {
            return false;
        }
        Class<?> returnType = method.getReturnType();
        if (ModelAndView.class.equals(returnType) || Map.class.equals(returnType) || String.class.equals(returnType)
                || void.class.equals(returnType)) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            return (parameterTypes.length >= 2 && HttpServletRequest.class.equals(parameterTypes[0])
                    && HttpServletResponse.class.equals(parameterTypes[1])
                    && !(SPRING_DEFAULT_ENTRANCE_METHOD_NAME.equals(method.getName()) && parameterTypes.length == 2));
        }
        return false;
    }

    /*
     * 此处并没有就返回值类型做判断
     */
    private void scanStrutsHandlerMethod() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            String name = method.getName();
            try {
                method = this.getClass().getMethod(name, StrutsStyleMethodTypes);
                strutsStyleHandleMethods.put(name, method);
            } catch (Exception e) {
                // 同名，但是不同参数类型方法也不算
            }
        }
    }

    private void scanSpringHandlerMethod() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (isSpringHandlerMethod(method)) {
                springStyleHandleMethods.put(method.getName(), method);
            }
        }
    }

    /**
     * 处理struts风格的Action方法，即调用execute方法，如果子类没有重写它的话，默认按方法名分发
     * 
     * @param methodName
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private ModelAndView processStrutsAction(String methodName, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if (isDefaultMethodName(methodName)) {
            String msg = "Can not invoke default method by request: " + methodName;
            throw new IllegalArgumentException(msg);
        }
        // Pre-test
        Method method = this.strutsStyleHandleMethods.get(methodName);
        if (method == null) {
            throw new NoSuchRequestHandlingMethodException(methodName, getClass());
        }
        Method executeMethod = strutsStyleHandleMethods.get(STRUTS_METHOD_EXECUTE);
        return invokeMethod(executeMethod, StrutsStyleMethodTypes, request, response);
    }

    /**
     * 判断方法名是否是内置方法
     * 
     * @param methodName
     * @return
     */
    private boolean isDefaultMethodName(String methodName) {
        for (String name : DEFAULT_METHOD_NAMES) {
            if (name.equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    private ModelAndView processSpringController(String methodName, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Method method = (Method) this.springStyleHandleMethods.get(methodName);
        if (method == null) {
            throw new NoSuchRequestHandlingMethodException(methodName, getClass());
        }
        Class<?>[] paramTypes = method.getParameterTypes();
        return invokeMethod(method, paramTypes, request, response);
    }

    private ModelAndView invokeMethod(Method method, Class<?>[] paramTypes, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            /*
             *  如果访问的方法是父类的，获取不到parameterNames
             */
            String[] parameterNames = getParameterNameDiscoverer().getParameterNames(method);
            Object[] params = new Object[paramTypes.length];

            for (int i = 0; i < paramTypes.length; i++) {
                if (HttpServletRequest.class.equals(paramTypes[i])) {
                    params[i] = request;
                } else if (HttpServletResponse.class.equals(paramTypes[i])) {
                    params[i] = response;
                } else {
                    if(parameterNames==null){
                        params[i] = autoBind(request, response, null, paramTypes[i]);
                    }else{
                        params[i] = autoBind(request, response, parameterNames[i], paramTypes[i]);
                    }
                }
            }
            Object returnValue = method.invoke(this, params);
            return messageReturnValueIfNecessary(request,returnValue);
        } catch (InvocationTargetException ex) {
            handleException(request, response, ex.getTargetException());
        } catch (Exception ex) {
            handleException(request, response, ex);
        }
        return null;
    }

    /**
     * 自动绑定方法的入参
     * 
     * @param request
     * @param response
     * @param parameterName
     *            入参的名字
     * @param cls
     *            参数类型
     * @return
     * @throws Exception
     */
    private Object autoBind(HttpServletRequest request, HttpServletResponse response, String parameterName,
            Class<?> cls) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Binding request parameters onto " + cls.getCanonicalName());
        }
        Object instance = null;
        if (ActionForm.class.isAssignableFrom(cls)) {
            instance = createActionForm(request, response);
            return instance;
        } else if (ActionMapping.class.equals(cls)) {
            instance = createActionMapping(request, response);
            return instance;
        } else {
            //判断参数是不是基础类型，如果是，就调用简单属性编辑器，否则由调用自定义的编辑器
            PropertyEditor theEditor = getSimpleTypeConverter().getDefaultEditor(cls);
            if (theEditor == null) {
                theEditor = getSimpleTypeConverter().findCustomEditor(cls, null);
            }
            if (theEditor != null) {
                if(StringUtil.isNotNull(parameterName)){
                    instance = getSimpleTypeConverter().convertIfNecessary(
                            getInputParameterFromRequest(parameterName, request),cls);
                }
            } else {
                // 不是基础类型的，直接实例化，如果无法实例化，抛出异常
                instance = BeanUtils.instantiateClass(cls);
                KmssServletRequestDataBinder binder = new KmssServletRequestDataBinder(instance);
                initBinder(request, binder);
                binder.bind(request);
            }
        }
        return instance;
    }

    /**
     * 从request中获取入参的填充值，默认
     * 先request.getParameter(parameterName)，再request.getAttribute(parameterName)<br/>
     * 子类根据需要也可以从request关联的对象取值
     * 
     * @param parameterName
     * @param request
     * @return
     */
    protected String getInputParameterFromRequest(String parameterName, HttpServletRequest request) {
        String parameterValue = request.getParameter(parameterName);
        if (parameterValue == null) {
            Object attribute = request.getAttribute(parameterName);
            if (attribute != null && String.class.isInstance(attribute)) {
                parameterValue = (String) attribute;
            }
        }
        return parameterValue;
    }
}
