package com.landray.kmss.sys.attend.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.service.IBaseService;

import net.sf.json.JSONArray;
/**
 * 人员统计详情业务对象接口
 * 
 * @author 
 * @version 1.0 2017-07-27
 */
public interface ISysAttendStatDetailService extends IBaseService {

	/**
	 * 导出每日汇总数据
	 * 
	 * @param list
	 * @param maxWorkTimeCount
	 * @param worksMap 用户班次信息
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook buildWorkBook(List list, int maxWorkTimeCount , Map<String, List<List<JSONObject>>> worksMap)
			throws Exception;

	public JSONArray renderAttendRecord(List list, int fdType);

	/**
	 * 更新每日汇总汇总状态，以及打卡记录的状态
	 * 
	 * @param fdId
	 * @param fdStatus
	 * @throws Exception
	 */
	public void updateStatus(String fdId, String fdStatus) throws Exception;

	/**
	 * 根据日统计数据获取一天的打卡记录(statid-->records)
	 * 
	 * @param detailList
	 * @return
	 * @throws Exception
	 */
	public Map<String, JSONArray> getRecordsMap(List detailList)
			throws Exception;

	/**
	 * 每日汇总数据格式化
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> formatStatDetail(List list) throws Exception;
}
