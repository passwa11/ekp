package com.landray.kmss.tic.jdbc.iface;

import java.util.Map;

import net.sf.json.JSONObject;

import com.landray.kmss.tic.jdbc.model.TicJdbcRelation;

/**
 * JDBC任务同步
 * @author qiujh
 */
public interface ITicJdbcTaskSync {
	
	/**
	 * 执行同步任务 
	 * @param ticJdbcRelation
	 * @param json
	 * @return					返回日志记录key为message代表成功错误条数，
	 * 							key为errorDetail代表记录错误的id
	 * @throws Exception
	 */
	public Map<String, String> run(JSONObject json) throws Exception;
	
}
