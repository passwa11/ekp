package com.landray.kmss.hr.staff.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

public class ExcelParseCache {

	private static Map<String, ExcelParseCache> excelParseCaches = new HashMap<>();

	private Map<String, Object> params;
	private List<Map<String, Object>> rows;

	public static ExcelParseCache newCache(String key) {
		ExcelParseCache cache = excelParseCaches.get(key);
		if (cache == null) {
			cache = new ExcelParseCache();
			excelParseCaches.put(key, cache);
		} else {
			cache.clear();
		}
		return cache;
	}

	public static ExcelParseCache getCache(String key) {
		ExcelParseCache cache = excelParseCaches.get(key);

		return cache;
	}

	public static void clearCache(String key) {
		ExcelParseCache cache = excelParseCaches.get(key);
		if (cache != null) {
			cache.clear();
		}
		excelParseCaches.remove(key);
	}

	private void clear() {
		this.rows.clear();
		this.getParams().clear();
	}

	public ExcelParseCache addRow(Map<String, Object> row) {
		if (rows == null) {
			rows = new ArrayList<>();
		}
		if (row != null) {
			rows.add(row);
		}
		return this;
	}

	public List<Map<String, Object>> getRows() {
		return this.rows;
	}

	public ExcelParseCache setParam(String key, Object value) {
		if (getParams() == null) {
			params = new HashMap<>();
		}
		Assert.notNull(key, "key is null");
		Assert.notNull(value, "value is null");
		getParams().put(key, value);
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

}
