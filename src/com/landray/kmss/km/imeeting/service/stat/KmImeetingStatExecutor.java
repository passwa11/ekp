package com.landray.kmss.km.imeeting.service.stat;

import java.util.Map;

import net.sf.json.JSON;

import com.landray.kmss.km.imeeting.util.StatResult;

/**
 * 统计执行器接口。
 */
public interface KmImeetingStatExecutor {

	public StatResult executeStatChart(Map<String, Object> parameters)
			throws Exception;

	public JSON executeStatList(Map<String, Object> parameters)
			throws Exception;

	public JSON executeStatListDetail(Map<String, Object> parameters)
			throws Exception;

}
