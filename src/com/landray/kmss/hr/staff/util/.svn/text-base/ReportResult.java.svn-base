package com.landray.kmss.hr.staff.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

/**
 * 统计报表结果集
 * 
 * @author 潘永辉 2017-1-17
 * 
 */
public class ReportResult {
	// 题头相关信息
	private Map<String, Object> title = new HashMap<String, Object>();

	// X轴坐标信息
	private List<Map<String, Object>> xAxis = new ArrayList<Map<String, Object>>();

	// Y轴坐标信息
	private List<Map<String, Object>> yAxis = new ArrayList<Map<String, Object>>();

	// 统计data
	private List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();
	// 统计数据
	private JSONArray statistics = new JSONArray();

	public JSONArray getStatistics() {
		return statistics;
	}

	public void setStatistics(JSONArray statistics) {
		this.statistics = statistics;
	}

	public Map<String, Object> getTitle() {
		return title;
	}

	public void setTitle(Map<String, Object> title) {
		this.title = title;
	}

	public List<Map<String, Object>> getxAxis() {
		return xAxis;
	}

	public List<Map<String, Object>> getyAxis() {
		return yAxis;
	}

	public List<Map<String, Object>> getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(List<Map<String, Object>> seriesData) {
		this.seriesData = seriesData;
	}

	public void addXAxis(String name, String type, String data) {
		if (this.xAxis == null) {
			this.xAxis = new ArrayList<Map<String, Object>>();
		}
		this.xAxis.add(getAxis(name, type, data));
	}

	public void addYAxis(String name, String type, String data) {
		if (this.yAxis == null) {
			this.yAxis = new ArrayList<Map<String, Object>>();
		}
		this.yAxis.add(getAxis(name, type, data));
	}

	private Map<String, Object> getAxis(String name, String type, String data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("type", type);
		map.put("data", data);
		return map;
	}
}
