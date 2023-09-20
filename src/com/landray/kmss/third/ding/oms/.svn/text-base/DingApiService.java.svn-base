package com.landray.kmss.third.ding.oms;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.*;
import com.dingtalk.api.response.OapiUserListbypageResponse.Userlist;

import com.landray.kmss.third.ding.dto.DingToDoList;
import com.landray.kmss.third.ding.dto.DingCalendarParam;
import com.landray.kmss.third.ding.model.api.DingCalendars;
import com.landray.kmss.third.ding.model.api.TodoCard;
import com.landray.kmss.third.ding.model.api.TodoTask;
import net.sf.json.JSONObject;

/**
 * 钉钉API的Service
 */
public interface DingApiService extends DingApiV2Service{

	/**
	 * 获取access_token, 不强制刷新access_token,
	 * AccessToken有效期为7200秒，有效期内重复获取返回相同结果，并自动续期。
	 * 
	 * @see #getAccessToken(boolean)
	 * @return
	 */
	String getAccessToken() throws Exception;

	String getAccessToken(String agentID) throws Exception;

	/**
	 * <pre>
	 * 获取access_token，本方法线程安全
	 * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
	 * 另：本service的所有方法都会在access_token过期是调用此方法
	 * 程序员在非必要情况下尽量不要主动调用此方法
	 * 详情请见: https://oapi.dingtalk.com/gettoken?corpid=id&amp;corpsecret=secrect
	 * </pre>
	 * 
	 * @param forceRefresh
	 *            强制刷新
	 * @return
	 */
	String getAccessToken(boolean forceRefresh)
			throws Exception;

	String getAccessToken(boolean forceRefresh, String agentID)
			throws Exception;

	/**
	 * <pre>
	 * 部门管理接口 - 创建部门
	 * 详情请见: https://oapi.dingtalk.com/department/create?access_token=ACCESS_TOKEN
	 * </pre>
	 * 
	 * @param depart
	 *            部门
	 * @return 部门id
	 */
	JSONObject departCreate(JSONObject depart) throws Exception;

	/**
	 * <pre>
	 * 部门管理接口 - 查询指定部门
	 * 详情请见: https://oapi.dingtalk.com/department/get?access_token=ACCESS_TOKEN
	 * </pre>
	 * 
	 * @return
	 */
	JSONObject departGet(Long departId) throws Exception;

	/**
	 * <pre>
	 * 部门管理接口 - 查询所有部门
	 * 详情请见: https://oapi.dingtalk.com/department/list?access_token=ACCESS_TOKEN
	 * </pre>
	 * 
	 * @return
	 */
	JSONObject departGet() throws Exception;
	
	JSONObject departsGet(String departId) throws Exception;
	
	JSONObject departsSubGet(String departId) throws Exception;

	String getDingCustomInfo() throws Exception;

	/**
	 * <pre>
	 * 部门管理接口 - 修改部门名
	 * 详情请见: https://oapi.dingtalk.com/department/update?access_token=ACCESS_TOKEN
	 * </pre>
	 * 
	 * @param group
	 *            要更新的group，group的id,name必须设置
	 */
	String departUpdate(JSONObject group) throws Exception;

	/**
	 * <pre>
	 * 部门管理接口 - 删除部门
	 * https://oapi.dingtalk.com/department/delete?access_token=ACCESS_TOKEN&amp;id=ID
	 * </pre>
	 * 
	 * @param departId
	 */
	String departDelete(String departId) throws Exception;

	/**
	 * 新建用户 https://oapi.dingtalk.com/user/create?access_token=ACCESS_TOKEN
	 * 
	 * @param user
	 */
	JSONObject userCreate(JSONObject user) throws Exception;

	/**
	 * 更新用户 https://oapi.dingtalk.com/user/update?access_token=ACCESS_TOKEN
	 * 
	 * @param user
	 */
	String userUpdate(JSONObject user) throws Exception;

	/**
	 * 删除用户
	 * https://oapi.dingtalk.com/user/delete?access_token=ACCESS_TOKEN&userid=ID
	 * 
	 * @param userid
	 */
	String userDelete(String userid) throws Exception;

	/**
	 * 获取用户 https://oapi.dingtalk.com/user/get?access_token=ACCESS_TOKEN&userid=
	 * zhangsan
	 * 
	 * @param userid
	 * @return
	 */
	JSONObject userGet(String userid, String ekpUserId) throws Exception;

	JSONObject userGet_v2(String userid, String ekpUserId) throws Exception;

	/**
	 * <pre>
	 * 获取部门成员(详情)
	 * ttps://oapi.dingtalk.com/user/list?access_token=ACCESS_TOKEN&amp;department_id=1&amp;fetch_child=0
	 * </pre>
	 * 
	 * @param departId
	 *            必填。部门id
	 * @param fetchChild
	 *            非必填。1/0：是否递归获取子部门下面的成员
	 * @return
	 */
	public JSONObject userListOld(Integer departId, Boolean fetchChild) throws Exception;

	/**
	 * <pre>
	 * 获取部门成员
	 * https://oapi.dingtalk.com/user/simplelist?access_token=ACCESS_TOKEN&amp;department_id=1&amp;fetch_child=0
	 * </pre>
	 * 
	 * @param departId
	 *            必填。部门id
	 * @param fetchChild
	 *            非必填。1/0：是否递归获取子部门下面的成员
	 * @return
	 */
	JSONObject departGetUsers(Integer departId, Boolean fetchChild)
			throws Exception;

	/**
	 * 
	 * @param text_content
	 *            DING内容
	 * @param receiver
	 *            接收者fdId集合
	 * @param appName
	 *            在ekp的钉钉集成配置的应用名称，可为null,为空则取钉钉配置的默认应用
	 * @return
	 * @throws Exception
	 */
	String sendDING(String text_content,
			List<String> receiver, String appName) throws Exception;

	/**
	 * 获取JS Ticket
	 */
	public String getJsapiTicket() throws Exception;

	public String getJsapiTicket(boolean forceRefresh) throws Exception;

	public String sign(String ticket, String nonceStr, long timeStamp,
			String url) throws Exception;

	public String getConfig(String urlString, String queryString);

	public JSONObject getUserInfo(String code) throws Exception;
	
	public JSONObject getUserInfoByDingAppKey(String code, String dingAppKey)
			throws Exception;

	public JSONObject upload(String type, File file) throws Exception;
	
	public JSONObject appCreate(JSONObject app) throws Exception;
	
	public JSONObject getAdmin() throws Exception;
	
	public JSONObject getApps() throws Exception;
	
	public JSONObject appUpdate(JSONObject app) throws Exception;
	
	public JSONObject appDelete(String agentId) throws Exception;
	
	public JSONObject appVisible(String agentId) throws Exception;
	
	public void expireAccessToken(String agentId);
	
	public boolean delCallBackEvent(String token) throws Exception;
	
	public OapiCallBackGetCallBackFailedResultResponse getCallBackFailedResult(String token) throws Exception;
	
	/**
	 * @throws Exception
	 * 事件管理，如果开启钉钉到EKP同步则注册事件，如果开启EKP到钉钉集成则删除已经注册的事件
	 */
	public void eventManager() throws Exception;
	
	public String messageSend(Map<String, String> content, String userid,
			String deptid, boolean toall, Long agentId, String ekpUserId)
			throws Exception;
	
	public String messageSend(String content, String userid, String deptid,
			boolean toall, Long agentId, String ekpUserId) throws Exception;
	
	public void messageSendOld(JSONObject message) throws Exception;
	
	/**
	 * 
	 * @param msg
	 *            消息内容（参考链接https://ding-doc.dingtalk.com/doc#/serverapi2/ye8tup/97f7b081），类型有
	 *            8种消息类型
	 * @param userid_list
	 *            ekp的人员fdId列表，最大用户列表长度：100
	 * @param dept_id_list
	 *            ekp的部门fdId列表，最大列表长度：20
	 * @param to_all_user
	 *            是否全员发送 ，一天最多3次
	 * @param agentId
	 *            发送消息的应用(目前只能支持待办配置的应用)，传null默认取待办配置应用的agentId
	 */
	public String sendWorkNotify(
			OapiMessageCorpconversationAsyncsendV2Request.Msg msg,
			List userid_list,
			List dept_id_list, boolean to_all_user, String agentId)
			throws Exception;

	/**
	 * @return
	 * @throws Exception
	 * 返回企业id（corpid）和考勤打卡的应用Id（agentid）
	 */
	public Map<String,String> attendInfo() throws Exception;
	
	public void userList(List<Userlist> users, Long departId, Long page) throws Exception;

	public void userList_v2(List<OapiV2UserListResponse.ListUserResponse> users, Long departId, Long page) throws Exception;

	// =================================================
	// EKP考勤同步到钉钉相关接口
	// =================================================
	/**
	 * <pre>
	 * 根据考勤排班计算时长
	 * https://open.taobao.com/api.htm?docId=45693&docType=2
	 * </pre>
	 * 
	 * @param userid
	 *            员工的user_id
	 * @param bizType
	 *            审批单类型2：出差，3：请假
	 * @param fromTime
	 *            开始时间，支持的时间格式 2019-08-15/2019-08-15 AM/2019-08-15
	 *            12:43。开始时间不能早于当前时间前31天
	 * @param toTime
	 *            结束时间，支持的时间格式 2019-08-15/2019-08-15 AM/2019-08-15
	 *            12:43。结束时间减去开始时间的天数不能超过31天
	 * @param durationUnit
	 *            时长单位，支持的day,halfDay,hour。时间格式必须与时长单位对应，2019-08-15对应day，2019-08-15
	 *            AM对应halfDay，2019-08-15 12:43对应hour
	 * @param calculateModel
	 *            计算方法，0：按自然日计算，1：按工作日计算
	 * @return
	 * @throws Exception
	 */
	JSONObject preCalcuateDate(JSONObject param, String ekpUserId)
			throws Exception;

	/**
	 * <pre>
	 * 审批通过时通知考勤修改打卡结果 
	 * https://open.taobao.com/api.htm?docId=45692&docType=2
	 * </pre>
	 * 
	 * @param userid
	 *            员工的user_id
	 * @param bizType
	 *            审批单类型2：外出、出差，3：请假
	 * @param fromTime
	 *            开始时间，支持的时间格式 2019-08-15/2019-08-15 AM/2019-08-15
	 *            12:43。开始时间不能早于当前时间前31天
	 * @param toTime
	 *            结束时间，支持的时间格式 2019-08-15/2019-08-15 AM/2019-08-15
	 *            12:43。结束时间减去开始时间的天数不能超过31天
	 * @param durationUnit
	 *            时长单位，支持的day,halfDay,hour。时间格式必须与时长单位对应，2019-08-15对应day，2019-08-15
	 *            AM对应halfDay，2019-08-15 12:43对应hour
	 * @param calculateModel
	 *            计算方法，0：按自然日计算，1：按工作日计算计算方法，0：按自然日计算，1：按工作日计算
	 * @param tagName
	 *            审批单类型名称，最大长度20个字符
	 * @param subType
	 *            子类型名称，最大长度20个字符
	 * @param approve_id
	 *            审批单全局唯一id，最大长度100个字符
	 * @param jump_url
	 *            审批单跳转地址，最大长度100个字符
	 * @return
	 * @throws Exception
	 */
	JSONObject approveFinish(JSONObject param, String ekpUserId)
			throws Exception;

	/**
	 * <pre>
	 * 审批撤销时通知考勤修改打卡结果
	 * https://open.taobao.com/api.htm?docId=45692&docType=2
	 * </pre>
	 * 
	 * @param userid
	 *            员工的user_id
	 * @param approve_id
	 *            审批单全局唯一id，最大长度100个字符
	 * @return
	 * @throws Exception
	 */
	JSONObject approveCanel(JSONObject param, String ekpUserId)
			throws Exception;

	/**
	 * 查询一天的排班
	 * 
	 * @param op_user_id
	 *            操作者userId
	 * @param user_id
	 *            用户userId
	 * @param date_time
	 *            查询那天的数据
	 * @return
	 * @throws Exception
	 */
	JSONObject scheduleByDay(JSONObject param, String ekpUserId)
			throws Exception;

	

	/**
	 * 审批通过后进行补卡操作
	 * 
	 * @param userid
	 *            员工的user_id
	 * @param work_date
	 *            要补哪一天的卡，注意这个日期不是实际要补的日期，而是班次的日期。例如用户要补卡的时间是2019-08-16
	 *            00:20，排班时间是2019-08-15 23：50，那么这里要传的日期是2019-08-15
	 * @param punch_id
	 *            要补的排班id
	 * @param punch_check_time
	 *            排班时间
	 * @param user_check_time
	 *            用户打卡时间
	 * @param approve_id
	 *            审批单id，全局唯一
	 * @param jump_url
	 *            审批单跳转地址
	 * @param tag_name
	 *            审批单名称
	 * @return
	 * @throws Exception
	 */
	JSONObject approveCheck(JSONObject param, String ekpUserId)
			throws Exception;

	/**
	 * 发起换班
	 * 
	 * @param apply_userid
	 *            申请人
	 * @param switch_date
	 *            换班的日期，仅支持日期格式如：2019-08-15
	 * @param target_userid
	 *            替班的人
	 * @param reback_date
	 *            还班的日期，仅支持日期格式如：2019-08-15
	 * @param tag_name
	 *            审批单名称
	 * @param approve_id
	 *            三方审批单id，全局唯一
	 * @return
	 */
	JSONObject approveSwitch(JSONObject param, String ekpUserId)
			throws Exception;
	
	/**
	 * @param deptId
	 * @return
	 * @throws Exception
	 *             根据部门Id获取当前部门所有的上级
	 */
	public JSONObject getDeptParents(String deptId) throws Exception;

	/**
	 * @param userId
	 * @return
	 * @throws Exception
	 *             根据用户Id获取当前用户在所有部门的上级
	 */
	public JSONObject getPersonParents(String userId) throws Exception;

	public JSONObject getOrgUserCount(boolean onlyActive) throws Exception;

	/**
	 * 
	 * @param param包括opUserid(操作者dingId)
	 * @param ekpUserId
	 *            ekp中用户的fdId,用于F4获取corpId（oms_relation_model的fdAppKey）用，非F4可传null
	 * @return json格式的字符串
	 * @throws Exception
	 *             (钉钉套件)获取钉钉假期类型
	 */
	public String getAttendanceHolidayTypes(JSONObject param, String ekpUserId)
			throws Exception;
	
	/**
	 * 
	 * @param param包括opUserid(操作者dingId)、userids(待查用户dingId)、leaveCode(假期标识)
	 * 
	 * @param ekpUserId
	 *            ekp中用户的fdId,用于F4获取corpId（oms_relation_model的fdAppKey）用，非F4可传null
	 * 
	 * @return json格式的字符串
	 * @throws Exception
	 *             (钉钉套件)获取假期余额
	 */
	String getAttendanceQuota(JSONObject param, String ekpUserId)
			throws Exception;

	/**
	 * 
	 * @param param包含userid(员工在企业内的UserID)
	 * 
	 * @param ekpUserId
	 *            ekp中用户的fdId,用于F4获取corpId（oms_relation_model的fdAppKey）用，非F4可传null
	 * 
	 * @return json格式的字符串
	 * @throws Exception
	 *             (钉钉套件)获取用户考勤组
	 */
	String getUserGroup(JSONObject param, String ekpUserId) throws Exception;

	/**
	 * 
	 * 
	 * 
	 * @param ekpUserId
	 *            ekp中用户的fdId,用于F4获取corpId（oms_relation_model的fdAppKey）用，非F4可传null
	 * 
	 * @return json格式的字符串
	 * @throws Exception
	 *             (钉钉套件)更新余额
	 */
	String updateQuota(JSONObject param, String ekpUserId) throws Exception;

	/**
	 * 
	 * 
	 * @param param含有
	 *            corpId
	 * @param ekpUserId
	 *            ekp中用户的fdId,用于F4获取corpId（oms_relation_model的fdAppKey）用，非F4可传null
	 * 
	 * @return json格式的字符串
	 * @throws Exception
	 *             获取钉钉审批分组信息
	 */
	JSONObject getDirFrom(JSONObject param) throws Exception;

	/**
	 * 获取钉钉后台管理免登的用户信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	JSONObject getSSOUserInfo(String code, String corpId) throws Exception;

	/**
	 * 查询钉钉待办模板
	 * 
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	String getDingTemplateList(String userId, Long offSet, Long size)
			throws Exception;

	/**
	 * 删除钉钉待办模板
	 * 
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	String delTemplate(String processCode) throws Exception;

	/**
	 * ISV模式根据corpId换取agentId
	 * 
	 * @param corpId
	 * @return
	 * @throws Exception
	 */
	JSONObject getAgentIdByCorpId(String corpId) throws Exception;

	/**
	 * （请假套件）获取请假的假期类型以及时长、计算请假时长、判断是否能请假
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	JSONObject getBizsuite(JSONObject param) throws Exception;

	String getRoleByGroupId(Long groupId) throws Exception;

	String getSimplelistByRoleId(Long roleId, Long count) throws Exception;

	String getRoleList() throws Exception;

	String queryShift(String ding_optId, Long classId) throws Exception;

	public String getRole(Long role_id) throws Exception;

	public OapiMessageCorpconversationAsyncsendV2Request
			buildMessageSendRequest(Map<String, String> content, String userid,
					String deptid, boolean toall, Long agentId,
					String ekpUserId)
					throws Exception;

	public String messageSend(Long agentId, String ekpUserId,
			OapiMessageCorpconversationAsyncsendV2Request request)
			throws Exception;
			
	String getProcessCodeByName(String name) throws Exception;

	ByteArrayOutputStream download(JSONObject json) throws Exception;

    //--------------------钉钉新接口start----------------------
	/**
	 * 待办新接口
	 * @return
	 */
	JSONObject addTask(String unionId,TodoTask task) throws Exception;

	JSONObject updateTask(String unionId,String taskId,JSONObject req) throws Exception;

	JSONObject updateExecutorStatus(String unionId,String taskId,JSONObject req) throws Exception;

	JSONObject getTask(String unionId,String taskId) throws Exception;

	public JSONObject getTaskList(String unionId)  throws Exception;

	JSONObject deleteTask(String unionId,String taskId) throws Exception;

	/*
	 * 新增卡片
	 */
	JSONObject  addCard(String unionId, TodoCard card) throws Exception;
	JSONObject  updateCard(String unionId,String cardId,TodoCard card) throws Exception;
	JSONObject  getCard(String unionId,String cardId) throws Exception;

	/*
	 *日程新接口
	 */

	JSONObject  addDingCalendars(String unionId, DingCalendars calendars) throws Exception;
	JSONObject  updateDingCalendars(String unionId,String calendarId,DingCalendars calendars) throws Exception;
	JSONObject  getDingCalendars(String unionId,String calendarId) throws Exception;
	JSONObject  deleteDingCalendars(String unionId,String calendarId) throws Exception;
	//--------------------钉钉新接口end----------------------
	//获取钉钉日程列表
	JSONObject getDingCalendarList(String unionId, DingCalendarParam dingCalendarParam) throws Exception;
	//日程旧id替换新id
	JSONObject calendarIdConvert(String unionId, LinkedHashMap<String, List<String>> oldIdsMap) throws Exception;
	//待办新接口
	JSONObject newTodo(String unionId, Map<String, Object> map) throws Exception;

	//查询用户待办列表
	JSONObject getToDoList(String unionId, DingToDoList dingToDoList) throws Exception;

	//根据手机号码查询用户userid
	JSONObject getUserIdByMobile(String mobile) throws Exception;

	OapiCallBackGetCallBackResponse queryCallBackEvent(String token) throws Exception ;

	//发送互动卡片
	JSONObject sendInteractiveCard(JSONObject request) throws Exception;

	//更新互动卡片
	JSONObject updateInteractiveCard(JSONObject request) throws Exception;

	//注册卡片回调地址
	JSONObject registerCardCallback(String callbackUrl,String routeKey,String apiSecret) throws Exception;


}
