package com.landray.kmss.common.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Action拦截器接口，可在action调用前后添加业务逻辑
 * 
 * @author 叶中奇
 * @modified by Kilery.Chen in EKP V15
 * <pre>
 * 因为移除了struts，action的拦截点直接继承spring提供的{@link org.springframework.web.servlet.HandlerInterceptor}，
 * 并入spring的流转控制
 * </pre>
 * @since EKP V15
 * @description 接口转换说明
 * <pre>
 * 原接口是在execute方法里完成业务逻辑，逻辑的位置根据调用chain.execute的前后分成2部分，对应于新接口的方法
 * public ActionForward execute(IActionChain chain, ActionMapping mapping,
 *      ActionForm form, HttpServletRequest request,HttpServletResponse response){
 *      //preHandle do something
 *      ActionForward forward = chain.execute(mapping, form, request, response)
 *      //postHandle do something
 *      //afterComplete do something
 *  }
 *  <ul>
 *  <li>原来chain.execute返回的ActionForward对象转换成了 ModelAndView对象</li>
 *  <li>新的接口不是用返回的方式，比如：
 *  forward = chain.execute(mapping, form, request, response);
 *  forward = doSomething();
 *  return forward;
 *  而是用修改ModelAndView的name的方式来完成新的跳转，比如：
 *  ForwardConfig fc = doSomething();
 *  ModelAndViewUtils.replaceViewName(modelAndView,fc.getName());
 *  </li>
 *  <li>forward.getName() ==> ModelAndViewUtils.stripPrefix(modelAndView.getViewName())</li>
 *  <ul>
 * </pre>
 * 
 * @see KmssDefaultActionInterceptor
 */
public interface IActionInterceptor extends HandlerInterceptor {
    
    /**
     * 如果实现逻辑写在ActionForward forward = chain.execute(mapping, form, request, response)之前，
     * 那么对应使用({@link #preHandle(HttpServletRequest, HttpServletResponse, Object)};
     * 注意返回值如果是false，那么controller是不会被执行的
     */
    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

    /**
     * 如果实现逻辑写在ActionForward forward = chain.execute(mapping, form, request, response)之后，
     * 那么对应使用({@link #postHandle(HttpServletRequest, HttpServletResponse, Object, ModelAndView)};
     */
    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception;

    @Override
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception;
}
