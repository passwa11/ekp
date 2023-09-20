package com.landray.kmss.third.ding.service;

import java.util.Map;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 考勤日志相关处理
 * 
 * @author 唐有炜
 *
 */
public interface IThirdDingLeavelogService extends IExtendDataService {
	/**
	 * 请假同步到钉钉考勤失败重试
	 * 
	 * @param fdId
	 *            主键id
	 */
	boolean updateLeaveSync(String fdId) throws Exception;

	/**
	 * 请假通知钉钉
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	JSONObject leaveNotifyDing(Map<String, Object> paramMap, String ekpUserId)
			throws Exception;

	/**
	 * 钉钉数据回写到Ekp
	 * 
	 * @param baseModel
	 * @param params
	 *            入参映射
	 * @param result
	 *            钉钉返回的数据
	 * @throws Exception
	 */
	void updateDingInfoToEkp(IBaseModel baseModel, JSONObject params,
			JSONObject result) throws Exception;

	/**
	 * 写入请假明细
	 * 
	 * @param jsArr
	 *            请假明细
	 * @throws Exception
	 */
	void writeLeaveDetail(ThirdDingLeavelog docMain, JSONArray jsArr)
			throws Exception;

	/**
	 * 外出同步到钉钉考勤失败重试
	 * 
	 * @param fdId
	 *            主键id
	 */
	boolean updateBussSync(String fdId) throws Exception;

	/**
	 * 外出通知钉钉
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	JSONObject bussNotifyDing(Map<String, Object> dataMap, String ekpUserId)
			throws Exception;

	/**
	 * 写入外出明细
	 * 
	 * @param jsArr
	 *            外出明细
	 * @throws Exception
	 */
	void writeBussDetail(ThirdDingLeavelog docMain, JSONArray jsArr)
			throws Exception;

	/**
	 * 补卡同步到钉钉考勤失败重试
	 * 
	 * @param fdId
	 *            主键id
	 */
	boolean updateCheckSync(String fdId) throws Exception;

	/**
	 * 通知钉钉补卡
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	JSONObject checkNotifyDing(Map<String, Object> dataMap, String ekpUserId)
			throws Exception;

	/**
	 * 换班同步到钉钉考勤失败重试
	 * 
	 * @param fdId
	 *            主键id
	 */
	boolean updateSwitchSync(String fdId) throws Exception;

	/**
	 * 换班流通知钉钉
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	JSONObject switchNotifyDing(Map<String, Object> dataMap, String ekpUserId)
			throws Exception;

	/**
	 * 加班同步到钉钉考勤失败重试
	 * 
	 * @param fdId
	 *            主键id
	 */
	boolean updateOvertimeSync(String fdId) throws Exception;

	/**
	 * 加班通知钉钉
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	JSONObject overtimeNotifyDing(Map<String, Object> dataMap, String ekpUserId)
			throws Exception;

	/**
	 * 加班通知钉钉(钉钉套件)
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	JSONObject attendenceOvertimeNotifyDing(Map<String, Object> dataMap,
			String ekpUserId)
			throws Exception;

	/**
	 * 写入加班明细
	 * 
	 * @param jsArr
	 *            外出明细
	 * @throws Exception
	 */
	void writeOvertimeDetail(ThirdDingLeavelog docMain, JSONArray jsArr)
			throws Exception;
}
