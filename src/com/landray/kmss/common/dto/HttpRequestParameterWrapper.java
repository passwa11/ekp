package com.landray.kmss.common.dto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 将HttpServletRequest请求体包装为可修改parameter的对象
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
public class HttpRequestParameterWrapper extends HttpServletRequestWrapper {

	private final Map<String, String[]> params = new HashMap<>();
	private String method;

	public HttpRequestParameterWrapper(HttpServletRequest request) {
		super(request);
		params.putAll(request.getParameterMap());
		method = request.getMethod();
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String getMethod() {
		return method;
	}

	@Override
	public String getParameter(String name) {
		String[] values = params.get(name);
		return values == null || values.length == 0 ? null : values[0];
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return new IterEnumeration<>(params.keySet().iterator());
	}

	@Override
	public String[] getParameterValues(String key) {
		return params.get(key);
	}

	public void putParameter(String key, String value) {
		if (value == null) {
            return;
        }
		params.put(key, new String[]{value});
	}

	public void putParameter(String key, String[] value) {
		if (value == null) {
            return;
        }
		params.put(key, value);
	}

	public void putParameter(String key, Object value) {
		if (value == null) {
            return;
        }
		params.put(key, new String[]{String.valueOf(value)});
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return params;
	}

	/**
	 * 用于返回ParameterNames
	 */
	private static class IterEnumeration<E> implements Enumeration<E> {
		private final Iterator<E> iterator;

		public IterEnumeration(Iterator<E> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasMoreElements() {
			return iterator.hasNext();
		}

		@Override
		public E nextElement() {
			return iterator.next();
		}
	}
}