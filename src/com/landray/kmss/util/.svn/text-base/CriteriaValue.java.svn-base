package com.landray.kmss.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
/**
 * hqlhelper已覆盖该类所有功能
 */
@Deprecated 
public class CriteriaValue implements Map<String, String[]> {
	private static final String prefix = "q.";
	private Map<String, String[]> map = new HashMap<String, String[]>();

	public CriteriaValue(HttpServletRequest request) {
		Enumeration enume = request.getParameterNames();
		while (enume.hasMoreElements()) {
			String name = (String) enume.nextElement();
			if (name != null && name.trim().startsWith(prefix)) {
				map.put(name.trim().substring(prefix.length()),
						request.getParameterValues(name));
			}
		}
	}

	@Override
    public void clear() {
		this.map.clear();
	}

	@Override
    public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
    public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
    public Set<Entry<String, String[]>> entrySet() {
		return map.entrySet();
	}

	public String poll(String key) {
		String[] v = map.get(key);
		if (v != null && v.length > 0) {
			map.remove(key);
			return v[0];
		} else {
			return null;
		}
	}

	public String[] polls(String key) {
		String[] v = map.get(key);
		if (v != null) {
			map.remove(key);
			return v;
		} else {
			return null;
		}
	}

	@Override
    public String[] get(Object key) {
		return map.get(key);
	}

	@Override
    public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
    public Set<String> keySet() {
		return map.keySet();
	}

	@Override
    public String[] put(String key, String[] value) {
		return map.put(key, value);
	}

	@Override
    public void putAll(Map<? extends String, ? extends String[]> m) {
		map.putAll(m);
	}

	@Override
    public String[] remove(Object key) {
		return map.remove(key);
	}

	@Override
    public int size() {
		return map.size();
	}

	@Override
    public Collection<String[]> values() {
		return map.values();
	}

}
