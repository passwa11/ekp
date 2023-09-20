package com.landray.kmss.third.welink.service;

import java.util.List;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyLog;
import com.landray.kmss.third.welink.model.ThirdWelinkToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IThirdWelinkService {

	public ThirdWelinkToken getToken() throws Exception;

	public String getTokenStr() throws Exception;

	JSONObject getUser(String corpUserId) throws Exception;

	JSONArray getUsers(String deptCode) throws Exception;

	JSONArray getDepts(String deptCode, boolean recursiveflag) throws Exception;

	public String syncPerson(SysOrgPerson person) throws Exception;

	public String syncDept(SysOrgElement dept, boolean isAdd) throws Exception;

	void syncDepts(List<SysOrgElement> depts) throws Exception;

	public void updateWelinkUserIds() throws Exception;

	public JSONArray getUserSyncStatus(List<String> corpUserIds)
			throws Exception;

	public JSONArray getDeptSyncStatus(List<String> corpDeptIds)
			throws Exception;

	public String getSsoAccessToken(String code, String redirect_uri,
			String state)
			throws Exception;

	public String getSsoUserId(String accessToken)
			throws Exception;

	public String getClientUserId(String code)
			throws Exception;

	public void sendMessage(String subject, String content, String urlPath,
			String owner, JSONArray toUserList, ThirdWelinkNotifyLog log)
			throws Exception;

	public void sendMessage(JSONObject msg, ThirdWelinkNotifyLog log)
			throws Exception;

	public void addTodoTask(String notifyId, String subject,
			SysOrgPerson toUser,
			SysOrgPerson fromUser, String urlPath,
			String modelName, ThirdWelinkNotifyLog log)
			throws Exception;

	public void updateTodoTask(String notifyId, String toUserId,
			ThirdWelinkNotifyLog log)
			throws Exception;

	public void delTodoTask(String taskId,
			ThirdWelinkNotifyLog log)
			throws Exception;

	public void addTodoTask(JSONObject o, ThirdWelinkNotifyLog log)
			throws Exception;

	public void updateTodoTask(JSONObject o,
			ThirdWelinkNotifyLog log)
			throws Exception;

	public void delTodoTask(JSONObject o,
			ThirdWelinkNotifyLog log)
			throws Exception;

	public JSONObject getUserByMobileno(String mobileNo)
			throws Exception;

}
