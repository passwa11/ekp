/**
 * 
 */
package com.landray.kmss.tic.jdbc.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.core.util.RecursionUtil;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetService;
import com.landray.kmss.tic.jdbc.util.JdbcSqlUtil;

/**
 * @author qiujh
 * @version 1.0 2014-5-5
 */
public class TicJdbcDataSetJsonBean implements IXMLDataBean {

	private ITicJdbcDataSetService ticJdbcDataSetService;
	
	public void setTicJdbcDataSetService(
			ITicJdbcDataSetService ticJdbcDataSetService) {
		this.ticJdbcDataSetService = ticJdbcDataSetService;
	}

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		String funcId = requestInfo.getParameter("funcId");
		TicJdbcDataSet ticJdbcDataSet = (TicJdbcDataSet) ticJdbcDataSetService
				.findByPrimaryKey(funcId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("dataSetSql", JdbcSqlUtil.frommatSqlRemoveLimitCondition(ticJdbcDataSet.getFdSqlExpression()));
		map.put("fdDataSourceSql",
				RecursionUtil.frommatSql(ticJdbcDataSet.getFdSqlExpression()));
		map.put("inParam", JSON.parseObject(ticJdbcDataSet.getFdData()).getJSONArray("in").toJSONString());
		rtnList.add(map);
		return rtnList;
	}
	
	public static void main(String[] args) {
		String sql = "select fd_name,doc_create_time,fd_para_in from tic_core_func_base where 1=1 [and fd_func_type = '{fdfunctype}'] [and fd_app_type = '{fdapptype}'] [limit {startIndex},{pageSize}] ";
		System.out.println(JdbcSqlUtil.frommatSqlRemoveLimitCondition(sql));
	}

}
