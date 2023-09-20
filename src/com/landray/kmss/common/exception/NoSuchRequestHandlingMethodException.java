package com.landray.kmss.common.exception;

import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.style.StylerUtils;
import org.springframework.web.util.UrlPathHelper;

/**
 * Exception thrown when there is no handler method ("action" method)
 * for a specific HTTP request.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see MethodNameResolver#getHandlerMethodName(javax.servlet.http.HttpServletRequest)
 * @deprecated as of 4.3, in favor of annotation-driven handler methods
 */
@Deprecated
@SuppressWarnings("serial")
public class NoSuchRequestHandlingMethodException extends ServletException {

	private String methodName;


	/**
	 * Create a new NoSuchRequestHandlingMethodException for the given request.
	 * @param request the offending HTTP request
	 */
	public NoSuchRequestHandlingMethodException(HttpServletRequest request) {
		this(new UrlPathHelper().getRequestUri(request), request.getMethod(), request.getParameterMap());
	}

	/**
	 * Create a new NoSuchRequestHandlingMethodException.
	 * @param urlPath the request URI that has been used for handler lookup
	 * @param method the HTTP request method of the request
	 * @param parameterMap the request's parameters as map
	 */
	public NoSuchRequestHandlingMethodException(String urlPath, String method, Map<String, String[]> parameterMap) {
		super("No matching handler method found for servlet request: path '" + urlPath +
				"', method '" + method + "', parameters " + StylerUtils.style(parameterMap));
	}

	/**
	 * Create a new NoSuchRequestHandlingMethodException for the given request.
	 * @param methodName the name of the handler method that wasn't found
	 * @param controllerClass the class the handler method was expected to be in
	 */
	public NoSuchRequestHandlingMethodException(String methodName, Class<?> controllerClass) {
		super("No request handling method with name '" + methodName +
				"' in class [" + controllerClass.getName() + "]");
		this.methodName = methodName;
	}


	/**
	 * Return the name of the offending method, if known.
	 */
	public String getMethodName() {
		return this.methodName;
	}

}
