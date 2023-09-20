package com.landray.kmss.km.imeeting.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainCancelForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainHastenForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 会议安排业务对象接口
 */
public interface IKmImeetingMainService extends IExtendDataService {

	/**
	 * 发送会议通知
	 * 
	 * @param kmImeetingMain
	 *            会议安排主文档
	 */
	public void saveSendMeetingNotify(KmImeetingMain kmImeetingMain)
			throws Exception;

	/**
	 * 催办会议
	 * 
	 * @param kmImeetingMain
	 *            会议
	 * @param kmImeetingMainHastenForm
	 *            催办会议Form
	 */
	public void saveHastenMeeting(KmImeetingMain kmImeetingMain,
			KmImeetingMainHastenForm kmImeetingMainHastenForm) throws Exception;

	/**
	 * 取消会议
	 * 
	 * @param kmImeetingMain
	 *            会议
	 * @param kmImeetingMainCancelForm
	 *            取消会议Form
	 */
	public void updateCancelMeeting(KmImeetingMain kmImeetingMain,
			KmImeetingMainCancelForm kmImeetingMainCancelForm) throws Exception;

	/**
	 * 取需要参加会议的所有人，包括主持人及记录人
	 * 
	 * @param kmImeetingMain
	 *            会议
	 */
	public List<SysOrgElement> getAllAttendPersons(KmImeetingMain kmImeetingMain)
			throws Exception;

	/**
	 * 过期会议待办清理定时任务（定时任务）
	 * 
	 * @param context
	 */
	public void deleteExpiredTodo(SysQuartzJobContext context) throws Exception;

	/**
	 * 催办材料上传人上传材料（定时任务）
	 * 
	 * @param context
	 */
	public void sendHastenUploadAttTodo(SysQuartzJobContext context)
			throws Exception;

	/**
	 * 如果检测是否自动取消会议(定时任务)
	 */
	public void checkIsCancel(SysQuartzJobContext context) throws Exception;

	/**
	 * 同步接入定时器(定时任务)
	 */
	public void synchroInQuart(SysQuartzJobContext context) throws Exception;

	/**
	 * 删除指定会议待办(定时器)
	 */
	public void deleteMeetingExpiredTodo(SysQuartzJobContext context)
			throws Exception;

	/**
	 * 分类转移
	 * 
	 * @param ids
	 * @param templateId
	 */
	public int updateDucmentTemplate(String ids, String templateId)
			throws Exception;

	/**
	 * 检查潜在与会者的忙闲状态
	 */
	public List<Map<String, Object>> checkFree(RequestContext request)
			throws Exception;

	/**
	 * 组织人确认承接会议
	 */
	public void updateEmcc(KmImeetingMainForm kmImeetingMainForm, RequestContext requestContext) throws Exception;

	/**
	 * 提前结束会议
	 */
	public void updateEarlyEndMeeting(KmImeetingMain kmImeetingMain)
			throws Exception;
	/**
	 * 扫码参加会议
	 */
	public String putToKmImeetingList(HttpServletRequest request)
			throws Exception;
	/**
	 * 扫码参加会议人员
	 */
	public List getKmImeetingList(HttpServletRequest request) throws Exception;
	
	public void setCache(String key,String value) throws Exception;
	
	public Object getCache(String key) throws Exception;
	
	public void cleanCacheByKey(String key) throws Exception;

	/**
	 * 构建获取会议HQL
	 */
	public HQLInfo buildImeetingHql(RequestContext requestContext)
			throws Exception;

	public HQLInfo buildImeetingRangeHql(RequestContext requestContext)
			throws Exception;

	public List<KmImeetingMain> findNormalMain(RequestContext requestContext,
			Boolean isMyCalendar)
			throws Exception;

	public List<KmImeetingMain> findRangeMain(RequestContext requestContext,
			Boolean isMyCalendar)
			throws Exception;

	public List<KmImeetingMain> findMyNormalMain(RequestContext requestContext)
			throws Exception;

	public List<KmImeetingMain> findMyRangeMain(RequestContext requestContext)
			throws Exception;

	/**
	 * 会议门户部件数据源
	 * 
	 * @param request
	 *            请求
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> listPortlet(RequestContext request) throws Exception;

	public List<KmImeetingMain> findKmIMeetingMain(RequestContext request,
			Boolean isMyCalendar) throws Exception;

	public List<KmImeetingMain> findKmIMeetingListMain(RequestContext request)
			throws Exception;

	public List findFeedBackByAgenda(String fdMeetingId, String fdAgendaId, Boolean isGetId) throws Exception;

	/**
	 * 与会数量
	 * 
	 * @param mydoc
	 * @return
	 * @throws Exception
	 */
	public Integer getAttendStatCount(String myType, String mydoc, Date start,
			Date end)
			throws Exception;

	/* 铂恩会议接口开始 */
	public void addSyncToBoen(KmImeetingMain kmImeetingMain) throws Exception;

	public void updateSyncToBoen(KmImeetingMain kmImeetingMain) throws Exception;

	// 获取批注文件
	public String addAttFromBoen(KmImeetingMain kmImeetingMain) throws Exception;

	public String addAttFromBoenTest(HttpServletRequest request) throws Exception;

	public void syncAttFromBoen(SysQuartzJobContext context) throws Exception;

	// 开启会议
	public String updateBeginMeeting(KmImeetingMain kmImeetingMain) throws Exception;

	public void syncMeetingToBoen(SysQuartzJobContext context) throws Exception;
	/* 铂恩会议接口结束 */

	/* kk对接阿里视频会议接口开始 */
	public String addSyncToKk(KmImeetingMain kmImeetingMain) throws Exception;

	public void syncMeetingToKk(SysQuartzJobContext context) throws Exception;

	public Boolean canEnterKkVedio(KmImeetingMain kmImeetingMain) throws Exception;
	/* kk对接阿里视频会议接口结束 */
	
	
	/*===================================== 阿里云视频会议对接 ================================*/

	/**
	 * 同步	EKP会议参会人员到阿里云
	 * 
	 * @param kmImeetingMain
	 * @throws Exception
	 */
	public void addSyncMeetingPersonToAliyun(KmImeetingMain kmImeetingMain) throws Exception;
	
	/**
	 * 同步	EKP会议参会人员到阿里云，并创建视频会议
	 * 
	 * @param kmImeetingMain
	 * @throws Exception
	 */
	public void addSyncMeetingInfoToAliyun(KmImeetingMain kmImeetingMain) throws Exception;
	
	/**
	 * 同步	EKP会议参会人员到阿里云 （定时任务）
	 * 
	 * @param kmImeetingMain
	 * @throws Exception
	 */
	public void syncMeetingPersonAliyunQuartz(SysQuartzJobContext context) throws Exception;
	
	/**
	 * 同步	EKP会议参会人员到阿里云，并创建视频会议 （定时任务）
	 * 
	 * @param kmImeetingMain
	 * @throws Exception
	 */
	public void syncMeetingInfoAliyunQuartz(SysQuartzJobContext context) throws Exception;
	
	/**
	 * 当前登录人是否有权限查看进入阿里云视频会议
	 */
	public Boolean canEnterAliMeeting(KmImeetingMain kmImeetingMain) throws Exception;
	
	/*===================================== 阿里云视频会议对接 ================================*/
	public void updateMeetingFlag(KmImeetingMain kmImeetingMain) throws Exception;

}

