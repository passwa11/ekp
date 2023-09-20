package com.landray.kmss.common.actions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTPServletRequest的上下文信息
 * 
 * @author 叶中奇
 */
public class RequestContext {
	private String contextPath = null;

	private Locale locale;

	private String method = null;

	private Map<String, String[]> parameterMap = null;

	private Map<String, Object> attributes = null;

	private String remoteAddr = null;

	private HttpServletRequest request = null;

	// private Map<String, Object> requestBodyParams;

	private boolean isCloud = false;

	/**
	 * 无参数构造器，需手工模拟request的值
	 */
	public RequestContext() {
	}

	/**
	 * 通过HttpServletRequest构造，大部分的值直接从HttpServletRequest获取
	 * 
	 * @param request
	 */
	public RequestContext(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 当使用{@code RequestBody}注解时，参数保存在requestBodyParams中
	 * 
	 * @param request
	 * @param requestBodyParams
	 */
	/*
	 * public RequestContext(HttpServletRequest request, Map<String, Object>
	 * requestBodyParams) { this.request = request; this.requestBodyParams =
	 * requestBodyParams; }
	 */

	public RequestContext(HttpServletRequest request, boolean isCloud) {
		this.request = request;
		this.isCloud = isCloud;
	}

	public boolean isCloud() {
		return this.isCloud;
	}

	/**
	 * @return request.getContextPath()，如：/kmss2
	 */
	public String getContextPath() {
		if (contextPath == null && request != null) {
            contextPath = request.getContextPath();
        }
		return contextPath;
	}

	/**
	 * @return request.getLocale()
	 */
	public Locale getLocale() {
		if (locale == null && request != null) {
            locale = request.getLocale();
        }
		return locale;
	}

	/**
	 * @return request.getMethod()
	 */
	public String getMethod() {
		if (method == null && request != null) {
            method = request.getMethod();
        }
		return method;
	}

	/**
	 * @param key
	 * @return request.getParameter(key)
	 */
	public String getParameter(String key) {
		if (getParameterMap() == null) {
            return null;
        }
		// if (this.requestBodyParams != null) {
		// String value = getParamFromRequestBody(key);
		// if (value != null) {
		// return value;
		// }
		// }
		if (this.parameterMap != null) {
			String[] para = parameterMap.get(key);
			if (para == null) {
                return null;
            }
			if (para.length == 0) {
                return null;
            }
			return para[0];
		}
		return null;
	}

	// private String getParamFromRequestBody(String key) {
	// Object obj = this.requestBodyParams.get(key);
	// if (obj != null) {
	// if (obj.getClass().isArray()) {
	// Object[] objs = (Object[]) obj;
	// if (objs.length == 0) {
	// return null;
	// }
	// return objs[0].toString();
	// } else if (obj instanceof Collection) {
	// Collection collection = (Collection) obj;
	// if (collection.isEmpty()) {
	// return null;
	// }
	// return collection.toArray()[0].toString();
	// } else {
	// return obj.toString();
	// }
	// }
	// return null;
	// }
	//
	// public Map<String, Object> getRequestBodyParams() {
	// return this.requestBodyParams;
	// }

	/**
	 * @return request.getParameterMap()
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String[]> getParameterMap() {
		if (parameterMap == null && request != null) {
			parameterMap = new HashMap<String, String[]>();
			parameterMap.putAll(request.getParameterMap());
		}
		return parameterMap;
	}

	/**
	 * @param paraName
	 * @return request.getParameterValues(paraName)
	 */
	public String[] getParameterValues(String paraName) {
		if (getParameterMap() == null) {
            return null;
        }
		return parameterMap.get(paraName);
	}

	/**
	 * @return request.getRemoteAddr()
	 */
	public String getRemoteAddr() {
		if (remoteAddr == null && request != null) {
            remoteAddr = request.getRemoteAddr();
        }
		return remoteAddr;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setParameter(String key, String value) {
		if (getParameterMap() == null) {
            parameterMap = new HashMap<String, String[]>();
        }
		String[] para = parameterMap.get(key);
		if (para == null) {
			parameterMap.put(key, new String[] { value });
		} else {
			String[] newPara = new String[para.length + 1];
			System.arraycopy(para, 0, newPara, 0, para.length);
			parameterMap.put(key, newPara);
		}
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public Object getAttribute(String key) {
		if (request != null) {
			return request.getAttribute(key);
		}
		if (attributes == null) {
			return null;
		}
		return attributes.get(key);
	}

	public void setAttribute(String key, Object value) {
		if (request != null) {
			request.setAttribute(key, value);
			return;
		}
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		attributes.put(key, value);
	}
}
