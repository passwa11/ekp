package com.landray.kmss.third.feishu.service;

import java.util.Locale;
import java.util.Map;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.feishu.model.ThirdFeishuDeptMapping;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping;
import com.landray.kmss.third.feishu.model.ThirdFeishuToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IThirdFeishuService {

	public ThirdFeishuToken getToken(int tokenType) throws Exception;

	public String getTokenStr(int tokenType) throws Exception;

	public void syncDept(SysOrgElement dept, boolean isAdd,
			Map<String, String> deptMapping)
			throws Exception;

	public void addDept(SysOrgElement dept, Map<String, String> deptMapping)
			throws Exception;

	public void updateDept(SysOrgElement dept, ThirdFeishuDeptMapping mapping,
			Map<String, String> deptMapping)
			throws Exception;

	public void delDept(SysOrgElement dept)
			throws Exception;

	public void delDept(ThirdFeishuDeptMapping mapping) throws Exception;

	public void syncPerson(SysOrgPerson person, Map<String, String> deptMapping)
			throws Exception;

	public void delPerson(ThirdFeishuPersonMapping mapping) throws Exception;

	public JSONArray getDepts(String department_id, boolean fetch_child)
			throws Exception;

	public JSONArray getUsers(String department_id, boolean fetch_child)
			throws Exception;

	public JSONObject getUser(String employee_id) throws Exception;

	public String getSsoOpenId(String code) throws Exception;

	public String getSsoUserId(String code) throws Exception;

	public void delPerson(String employee_id)
			throws Exception;

	public void delDept(String feishDdeptId)
			throws Exception;

	public void sendMessage(String subject, String docSubject, String content,
			String urlPath,
			String modelname, String creator, String createTime,
			JSONArray toUserList, Locale locale, ThirdFeishuNotifyLog log)
			throws Exception;

	public void sendMessage(JSONObject msg, ThirdFeishuNotifyLog log)
			throws Exception;

	public JSONArray getUserDetails(String department_id, boolean fetch_child)
			throws Exception;

	public void updatePersonDepartment(String employee_id, String departmentId)
			throws Exception;

	public void sendCardMessage(String subject, String docSubject,
			String urlPath,
			String modelname, String creator, String createTime,
			String toUser, Locale locale, int todoType,
			ThirdFeishuNotifyLog log)
			throws Exception;

	public void updateCardMessage(String message_id, String content,
			int notifyType,
			Locale locale,
			ThirdFeishuNotifyLog log)
			throws Exception;

	public void updateCardMessage(String url, JSONObject msg,
			ThirdFeishuNotifyLog log)
			throws Exception;

	public void sendCardMessage(JSONObject msg, ThirdFeishuNotifyLog log)
			throws Exception;

	public String getJsapiTicket(boolean forceRefresh) throws Exception;

	public String getConfig(String urlString, String queryString);

	public void resetToken() throws Exception;

	public void sendCardMessageV1(String subject, String docSubject,
								  String urlPath,
								  String modelname, String creator, String createTime,
								  String toUser, Locale locale, int todoType,
								  ThirdFeishuNotifyLog log)
			throws Exception;

	public void sendCardMessageV1(JSONObject msg, ThirdFeishuNotifyLog log)
			throws Exception;

	public void updateCardMessageV1(String url, JSONObject msg,
									ThirdFeishuNotifyLog log)
			throws Exception;

	public void updateCardMessageV1(String message_id, String content,
									int notifyType,
									Locale locale,
									ThirdFeishuNotifyLog log, String status)
			throws Exception;

	public void disablePerson(ThirdFeishuPersonMapping mapping) throws Exception;

	public Map<String,String> getAllSyncRootIdsMap() throws Exception;

	public JSONObject getDept(String department_id, String department_id_type)
			throws Exception;

	public String getPcScanUserId(String code)
			throws Exception;

	public JSONArray getDeptsChildren(String department_id, boolean fetch_child)
			throws Exception;

	/**
	 * 同步审批到飞书
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public String syncApproval(String requestBody) throws Exception;
}
