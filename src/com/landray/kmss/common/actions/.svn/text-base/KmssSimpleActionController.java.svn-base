package com.landray.kmss.common.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * <pre>
 * 普通的SpringController，用于对接Struts的Action类，建议曾经继承org.apache.struts.action.Action的类改为继承该类
 * 注意：
 * <b>该类不具备dispatchMethod的功能，如果需要可选择{@link KmssMultiActionController}</b>
 * <b>该类不具备执行Spring风格的处理函数</b>
 * </pre>
 * @author Kilery.Chen
 */
public abstract class KmssSimpleActionController extends AbstractActionController{

    @Override
    protected final ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Method executeMethod = this.getClass().getMethod(STRUTS_METHOD_EXECUTE, StrutsStyleMethodTypes);
        return invokeMethod(executeMethod,request,response);
    }
    
    @Override
    public abstract ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception ;


    private ModelAndView invokeMethod(Method method, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            Object[] params = new Object[]{
                    createActionMapping(request, response),
                    createActionForm(request, response),
                    request,response
            };
            Object returnValue = method.invoke(this, params);
			return messageReturnValueIfNecessary(request, returnValue);
        } catch (InvocationTargetException ex) {
            handleException(request, response, ex.getTargetException());
        } catch (Exception ex) {
            handleException(request, response, ex);
        }
        return null;
    }
}
