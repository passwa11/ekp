package com.landray.kmss.third.ding.service.spring;

import com.dingtalk.api.request.OapiProcessWorkrecordTaskQueryRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest;
import com.dingtalk.api.request.OapiWorkrecordGetbyuseridRequest;
import com.dingtalk.api.request.OapiWorkrecordUpdateRequest;
import com.dingtalk.api.response.*;
import com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.FormItemVo;
import com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.PageResult;
import com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.WorkRecordVo;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.model.SysNotifyTodoDoneInfo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.*;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyHandleTaskModel;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyWorkrecord;
import com.landray.kmss.third.ding.notify.queue.constant.ThirdDingNotifyQueueErrorConstants;
import com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError;
import com.landray.kmss.third.ding.notify.queue.service.IThirdDingNotifyQueueErrorService;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyLogService;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyWorkrecordService;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.*;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.*;
import com.taobao.api.ApiException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThirdDingNotifyWRServiceImp extends ExtendDataServiceImp implements IThirdDingNotifyService, DingConstant {

	@Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof ThirdDingNotify) {
			ThirdDingNotify thirdDingNotify = (ThirdDingNotify) model;
		}
		return model;
	}

	@Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		ThirdDingNotify thirdDingNotify = new ThirdDingNotify();
		ThirdDingUtil.initModelFromRequest(thirdDingNotify, requestContext);
		return thirdDingNotify;
	}

	@Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		ThirdDingNotify thirdDingNotify = (ThirdDingNotify) model;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingNotifyWRServiceImp.class);

	private static boolean locked = false;

	private Map<String, String> relationMap = null;
	
	private IThirdDingNotifyQueueErrorService thirdDingNotifyQueueErrorService;

	public IThirdDingNotifyQueueErrorService
			getThirdDingNotifyQueueErrorService() {
		if (thirdDingNotifyQueueErrorService == null) {
			thirdDingNotifyQueueErrorService = (IThirdDingNotifyQueueErrorService) SpringBeanUtil
					.getBean("thirdDingNotifyQueueErrorService");
		}
		return thirdDingNotifyQueueErrorService;
	}

	public void setThirdDingNotifyQueueErrorService(
			IThirdDingNotifyQueueErrorService thirdDingNotifyQueueErrorService) {
		this.thirdDingNotifyQueueErrorService = thirdDingNotifyQueueErrorService;
	}

	// 当前完成处理时间
	private Date date = null;
	// 更新时间，默认一次湖区2天的数据
	private Date lastDate = null;

	private DingConfig config = null;
	
	private long count = 0L;
	
	private SysQuartzJobContext context;
	
	private AtomicBoolean oldNotify = new AtomicBoolean(true);
	
	private ThreadPoolTaskExecutor taskExecutor;

	public ThreadPoolTaskExecutor getTaskExecutor() {
		if(taskExecutor==null){
			taskExecutor = (ThreadPoolTaskExecutor) SpringBeanUtil
					.getBean("dingTaskExecutor");
		}
		return taskExecutor;
	}

	private CountDownLatch countDownLatchWR;

	private CountDownLatch countDownLatchWF;
	
	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil
					.getBean("componentLockService");
		}
		return componentLockService;
	}

	@Override
	public void synchroError(SysQuartzJobContext context) {
		this.context = context;
		String temp = "";
		ThirdDingNotifyHandleTaskModel model = new ThirdDingNotifyHandleTaskModel();
		try {
			getComponentLockService().tryLock(model, "notify", 3600000L);
			if (locked) {
				temp = "异常任务同步已经在运行，当前任务中断...";
				logger.warn(temp);
				log(temp);
				return;
			}
			locked = true;
			long alltime = System.currentTimeMillis();
			init();
			handlePersonSyncWR();
			// 增加开关
			// if ("true".equals(config.getNotifySynchroErrorWF())) {
			// logger.debug("已开启旧接口数据清理");
			// handlePersonSyncWF();
			// }
			handlePersonSyncWF();
			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
			logger.warn(temp);
			log(temp);
			getComponentLockService().unLock(model);
		} catch (ConcurrencyException e) {
			temp = "异常任务同步  或者 待办推送失败消息重发 已经在运行，当前任务中断...";
			logger.warn(temp);
			log(temp);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			getComponentLockService().unLock(model);
		} finally {
			count = 0L;
			relationMap = null;
			oldNotify.getAndSet(true);
			locked = false;

		}
	}

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	private IOmsRelationService omsRelationService;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}

	private IThirdDingNotifyWorkrecordService thirdDingNotifyWorkrecordService;

	public IThirdDingNotifyWorkrecordService
			getThirdDingNotifyWorkrecordService() {
		if (thirdDingNotifyWorkrecordService == null) {
			thirdDingNotifyWorkrecordService = (IThirdDingNotifyWorkrecordService) SpringBeanUtil
					.getBean("thirdDingNotifyWorkrecordService");
		}
		return thirdDingNotifyWorkrecordService;
	}

	private String getAppKey() {
		return StringUtil.isNull(DING_OMS_APP_KEY) ? "default" : DING_OMS_APP_KEY;
	}

	private IThirdDingDinstanceXformService thirdDingDinstanceXformService;

	public IThirdDingDinstanceXformService getThirdDingDinstanceXformService() {
		if (thirdDingDinstanceXformService == null) {
			thirdDingDinstanceXformService = (IThirdDingDinstanceXformService) SpringBeanUtil
					.getBean("thirdDingDinstanceXformService");
		}
		return thirdDingDinstanceXformService;
	}

	private void init() throws Exception {
		count = 0L;
		config = DingConfig.newInstance();
		relationMap = new HashMap<String, String>(2000);
		List<SysOrgPerson> persons = getSysOrgPersonService().findList("fdIsAvailable=1", null);
		Map<String,String> personMap = new HashMap<String,String>(persons.size());
		for(SysOrgPerson person:persons){
			personMap.put(person.getFdId(), person.getFdId());
		}
		List<OmsRelationModel> models = getOmsRelationService().findList("fdAppKey='" + getAppKey() + "'", null);
		for (OmsRelationModel model:models) {
			// 只获取EKP的人员映射信息
			if(personMap.containsKey(model.getFdEkpId())){
				relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
			}
		}		
	}


	@Override
	public void handle(boolean del) throws Exception {
		boolean flag = true;
		String where = null;
		if (del) {
			where = "fdEKPDel='1'";
		}
		List<SysNotifyTodo> tdlist = null;
		List<ThirdDingNotify> notifys = findList(where, null);
		for (ThirdDingNotify notify : notifys) {
			if (StringUtil.isNotNull(notify.getFdModelId()) && StringUtil.isNotNull(notify.getFdEkpUserId())) {
				tdlist = getSysNotifyTodoService().findList("fdId='" + notify.getFdModelId()
						+ "' and hbmTodoTargets.fdId='" + notify.getFdEkpUserId() + "'", null);
				if (tdlist.isEmpty()) {
					flag = delDingNotify(notify.getFdDingUserId(),
							notify.getFdRecordId(), notify.getFdEkpUserId());
					if (flag) {
						super.delete(notify);
					}
				}
			}
		}
	}

	private boolean delDingNotify(String userid, String recordid,
			String ekpUserId) throws Exception {
		if (StringUtil.isNull(userid) || StringUtil.isNull(recordid)) {
			logger.debug("更新钉钉待办的用户Id和待办Id不完整，故无法更新");
			return true;
		}
		DingApiService dingService = DingUtils.getDingApiService();
		String url = DingConstant.DING_PREFIX + "/topapi/workrecord/update"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
		req.setUserid(userid);
		req.setRecordId(recordid);
		OapiWorkrecordUpdateResponse rsp = client.execute(req, dingService.getAccessToken());
		logger.error("钉钉待办删除详细：" + rsp.getBody());
		if (rsp.getErrcode() == 0) {
			return true;
		} else {
			return false;
		}
	}

	// ========================================新待办接口历史数据处理开始=====================================================//
	private IThirdDingDinstanceService thirdDingDinstanceService = null;

	public IThirdDingDinstanceService getThirdDingDinstanceService() {
		if (thirdDingDinstanceService == null) {
			thirdDingDinstanceService = (IThirdDingDinstanceService) SpringBeanUtil
					.getBean("thirdDingDinstanceService");
		}
		return thirdDingDinstanceService;
	}

	private IThirdDingDtemplateService thirdDingDtemplateService = null;

	public IThirdDingDtemplateService getThirdDingDtemplateService() {
		if (thirdDingDtemplateService == null) {
			thirdDingDtemplateService = (IThirdDingDtemplateService) SpringBeanUtil
					.getBean("thirdDingDtemplateService");
		}
		return thirdDingDtemplateService;
	}

	private IThirdDingDtaskService thirdDingDtaskService = null;

	public IThirdDingDtaskService getThirdDingDtaskService() {
		if (thirdDingDtaskService == null) {
			thirdDingDtaskService = (IThirdDingDtaskService) SpringBeanUtil.getBean("thirdDingDtaskService");
		}
		return thirdDingDtaskService;
	}

	private CopyOnWriteArrayList<String> ids = null;
	
	private void handlePersonSyncWR() throws Exception {
		if ("true".equals(config.getDingEnabled())) {
			ids = new CopyOnWriteArrayList<String>();
			List<String> ekpids = new ArrayList<String>(relationMap.keySet());
			String size = DingConfig.newInstance().getDingSize();
			if (StringUtil.isNull(size)) {
                size = "2000";
            }
			int rowsize = Integer.parseInt(size);
			int count = ekpids.size() % rowsize == 0 ? ekpids.size() / rowsize
					: ekpids.size() / rowsize + 1;
			List<String> allpersons = new ArrayList<String>(ekpids);
			log("本次清理的人员总数据:" + allpersons.size() + "条,将执行" + count
					+ "次人员待办清理动作,每次" + rowsize + "条");
			logger.warn("本次清理的人员总数据:" + allpersons.size() + "条,将执行" + count
					+ "次人员待办清理动作,每次" + rowsize + "条");
			countDownLatchWR = new CountDownLatch(count);
			List<String> temppersons = null;
			for (int i = 0; i < count; i++) {
				log("执行第" + (i + 1) + "批");
				logger.warn("执行第" + (i + 1) + "批");
				if (ekpids.size() > rowsize * (i + 1)) {
					temppersons = allpersons.subList(rowsize * i,
							rowsize * (i + 1));
				} else {
					temppersons = allpersons.subList(rowsize * i,
							ekpids.size());
				}
				if (temppersons == null || temppersons.size() == 0) {
                    continue;
                }
				getTaskExecutor().execute(new PersonRunnerWR(temppersons));
			}
			try {
				countDownLatchWR.await(3, TimeUnit.HOURS);
			} catch (InterruptedException exc) {
				exc.printStackTrace();
				logger.error("清理钉钉待办不消失多线程结束异常：", exc);
			}
		} else {
			log("钉钉集成未开启，不执行钉钉待办的更新操作");
			logger.warn("钉钉集成未开启，不执行钉钉待办的更新操作");
		}
	}
	
	private void handlePersonSyncWF() throws Exception {
		if ("true".equals(config.getDingEnabled())) {
			ids = new CopyOnWriteArrayList<String>();
			List<String> ekpids = new ArrayList<String>(relationMap.keySet());
			String size = DingConfig.newInstance().getDingSize();
			if (StringUtil.isNull(size)) {
                size = "2000";
            }
			int rowsize = Integer.parseInt(size);
			int count = ekpids.size() % rowsize == 0 ? ekpids.size() / rowsize : ekpids.size() / rowsize + 1;
			List<String> allpersons = new ArrayList<String>(ekpids);
			log("本次清理的人员总数据:" + allpersons.size() + "条,将执行" + count + "次人员待办清理动作,每次" + rowsize + "条");
			logger.warn("本次清理的人员总数据:" + allpersons.size() + "条,将执行" + count
					+ "次人员待办清理动作,每次" + rowsize + "条");
			countDownLatchWF = new CountDownLatch(count);
			List<String> temppersons = null;
			for (int i = 0; i < count; i++) {
				log("执行第" + (i + 1) + "批");
				logger.warn("执行第" + (i + 1) + "批");
				if (ekpids.size() > rowsize * (i + 1)) {
					temppersons = allpersons.subList(rowsize * i, rowsize * (i + 1));
				} else {
					temppersons = allpersons.subList(rowsize * i, ekpids.size());
				}
				if (temppersons == null || temppersons.size() == 0) {
                    continue;
                }
				getTaskExecutor().execute(new PersonRunner(temppersons));
			}
			try {
				countDownLatchWF.await(3, TimeUnit.HOURS);
			} catch (InterruptedException exc) {
				exc.printStackTrace();
				logger.error("清理钉钉待办不消失多线程结束异常：",exc);
			}
			updateTask(context);
		} else {
			log("钉钉集成未开启，不执行钉钉待办的更新操作");
			logger.warn("钉钉集成未开启，不执行钉钉待办的更新操作");
		}
	}
	
	
	private void handleNotifyQuery(List<String> ekpids) throws Exception {
		if (ekpids == null) {
			ekpids = new ArrayList<String>(relationMap.keySet());
		}
		String token = DingUtils.getDingApiService().getAccessToken();
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			for (String ekpid : ekpids) {
				// log("即将清理EKP|钉钉员工：" + ekpid+"|"+ relationMap.get(ekpid) + "
				// 不消失的待办");
				logger.debug("即将清理EKP|钉钉员工：" + ekpid + "|"
						+ relationMap.get(ekpid) + " 不消失的待办");
				if (StringUtil.isNotNull(ekpid) && StringUtil.isNotNull(relationMap.get(ekpid)) && StringUtil.isNotNull(token)) {
					// 处理待处理的待办数据
					getQueryWorkFinished(context, token, relationMap.get(ekpid), 0L, 0L, ekpid);
					// 处理手动移除的待办数据
					getQueryWorkFinished(context, token, relationMap.get(ekpid), 0L, -1L, ekpid);
					// 处理老待办接口
					// if (oldNotify.get()) {
					// getNotify(token, relationMap.get(ekpid), 0l);
					// }
				}
				// log("完成EKP|钉钉员工的待办清理：" + ekpid+"|"+ relationMap.get(ekpid) +
				// " 不消失的待办");
				logger.debug("完成EKP|钉钉员工的待办清理：" + ekpid + "|"
						+ relationMap.get(ekpid) + " 不消失的待办");
			}
			TransactionUtils.getTransactionManager().commit(status);
		} catch (Exception e) {
			if (status != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		}
		updateTask(context);
	}
	
	public void getQueryWorkFinished(SysQuartzJobContext context, String token, String userid,long page,long status,String personId)
			throws Exception {
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/task/query"
				+ DingUtil.getDingAppKeyByEKPUserId("?", personId);
		logger.debug("钉钉接口，新待办接口清理：" + dingUrl);
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		OapiProcessWorkrecordTaskQueryRequest req = new OapiProcessWorkrecordTaskQueryRequest();
		req.setUserid(userid);
		req.setOffset(page*20L);
		req.setCount(20L);
		req.setStatus(status);
		OapiProcessWorkrecordTaskQueryResponse response = client.execute(req, token);
		if(response.getErrcode()==0){
			Long agentid = Long.parseLong(config.getDingAgentid());
			com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.PageResult result = response.getResult();
			List<com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo> vos = result.getList();
			String fdId = "";
			SysNotifyTodo todo = null;
			List<SysNotifyTodoDoneInfo> todoDones = null;
			String logname = null;
			String pg = null;
			String pgid = null;
			if (vos != null) {
				// log("钉钉人员："+userid+" 有"+vos.size()+"条待办数据需要验证");
				logger.debug(
						"钉钉人员：" + userid + " 有" + vos.size() + "条待办数据需要验证");
				Map<String,List<com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo>> pgMap = new HashMap<String,List<com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo>>();
				List<com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo> pgvos = null;
				for(com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo vo:vos){
					// log("处理待办：" + vo.getTitle() + "," + vo.getUrl());
					
					if (!DingUtil.checkUrlByDomain(vo.getUrl())) {
						log("待办任务的url和本系统域名不一致，不做处理:" + vo.getUrl());
						logger.warn("待办任务的url和本系统域名不一致，不做处理");
						continue;
					}
					logger.debug("处理待办：" + vo.getTitle() + "," + vo.getUrl());
					fdId = StringUtil.getParameter(vo.getUrl(), "fdId");
					String fdTodoId = StringUtil.getParameter(
							vo.getUrl().replace("?fdTodoId", "&fdTodoId"),
							"fdTodoId");
					logger.debug("fdTodoId:" + fdTodoId);
					if (StringUtil.isNull(fdId)) {
						fdId = fdTodoId;
					}
					logger.debug("fdId:" + fdId);
					if(StringUtil.isNull(fdId)){
						// 异常数据修复处理
						pg = StringUtil.getParameter(vo.getUrl(), "pg");
						if(StringUtil.isNotNull(pg)){
							pgid = StringUtil.getParameter(SecureUtil.BASE64Decoder(pg), "fdId");
							if(StringUtil.isNotNull(pgid)){
								if(pgMap.containsKey(pgid)){
									pgvos = pgMap.get(pgid);
								}else{
									pgvos = new ArrayList<com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo>();
								}
								logger.warn("处理待办：" + vo.getTitle() + ","
										+ vo.getUrl() + ",添加到map中");
								pgvos.add(vo);
								pgMap.put(pgid, pgvos);
							}else if(StringUtil.isNotNull(StringUtil.getParameter(vo.getUrl(), "toUrl"))&&vo.getUrl().contains("pcpg.jsp")){
								// 不存在fdId则直接更新待办不管是否已经处理
								delDingNotifyNew("fdId为空|数据修复", token, userid,
										agentid, vo, context, "", personId);
							}
						}
						continue;
					}
					todo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(fdId, null, true);
					if (todo == null || todo.getHbmTodoTargets() == null || todo.getHbmTodoTargets().size() == 0) {
						logname = "EKP中待办找不到(待办id=" + fdId + ",钉钉用户userId=" + userid + ")，直接删除钉钉中的待办(" + vo.getTaskId() + ")";
						delDingNotifyNew(fdId, token, userid, agentid, vo,
								context, logname, personId);
						continue;
					} else {
						todoDones = todo.getHbmDoneInfos();
						boolean deleteFlag = false;
						for (SysNotifyTodoDoneInfo done : todoDones) {
							if (personId.equals(done.getOrgElement().getFdId())) {
								// 更新调用钉钉的更新接口来结束待办(已办列表中有此人的待办)
								logname = "EKP中待办已经处理(" + fdId + "|" + userid + ")，直接删除钉钉中的待办(" + vo.getTaskId() + ")";
								deleteFlag = true;
								delDingNotifyNew(fdId, token, userid, agentid,
										vo, context, logname, personId);
								break;
							}
						}
						if (!deleteFlag) {
							List<SysOrgElement> targets = todo
									.getHbmTodoTargets();
							if (targets == null || targets.isEmpty()) {
								logname = "EKP中待办处理人不包含该用户(" + fdId + "|"
										+ userid + ")，直接删除钉钉中的待办("
										+ vo.getTaskId() + ")";
								delDingNotifyNew(fdId, token, userid, agentid,
										vo, context, logname, personId);
								break;
							}
						}
					}
				}
				List<String> pgkeys = new ArrayList<String>(pgMap.keySet());
				String sql = "select td.fd_id from sys_notify_todotarget tt,sys_notify_todo td where tt.fd_todoid=td.fd_id and td.fd_type=1";
				Session session = getSysNotifyTodoService().getBaseDao().getHibernateSession();
				for (String key : pgkeys) {
					pgvos = pgMap.get(key);
					if (StringUtil.isNotNull(key) && pgvos != null) {
						boolean pgflag = false;
						for (int i = 0; i < pgvos.size(); i++) {
							List rlist = session.createNativeQuery(sql + " and td.fd_model_id='" + key + "' and tt.fd_orgid='" + personId + "'").setMaxResults(1).list();
							// 待办中找不到直接删除(多条只留一条pgflag)
							if (rlist == null || rlist.isEmpty() || pgflag) {
								logname = "EKP中待办已处理(" + key + "|" + userid + ")，直接删除钉钉中的待办(" + pgvos.get(0).getTaskId() + ")|数据修复";
								delDingNotifyNew(key, token, userid, agentid,
										pgvos.get(i), context, logname,
										personId);
							} else {
								pgflag = true;
							}
						}
					}
				}
				if(result.getHasMore()){
					getQueryWorkFinished(context, token,userid,page+1,status,personId);
				}
			}
		}else{
			logger.error("待办清理异常，详细错误："+response.getBody());
		}
	}
	
	private void delDingNotifyNew(String fdId, String token, String userid, long agentId,
			com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo vo,
			SysQuartzJobContext context, String name, String personId)
			throws Exception {
		OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
		OapiProcessWorkrecordTaskUpdateResponse res = DingNotifyUtil.updateTask(req, token, vo.getInstanceId(),
				Long.parseLong(vo.getTaskId()), agentId, null);
		if (res.getErrcode() == 0) {
			++count;
			log(name);
			logger.warn(name);
			ids.add(vo.getTaskId());
		} else {
			log("更新待办失败，详情：" + res.getBody());
			logger.warn("更新待办失败，详情：" + res.getBody());
		}
	}
	
	private void updateTask(SysQuartzJobContext context) throws Exception {
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			if (ids != null && ids.size() > 0) {
				int count = 0;
				int rowsize = 100;
				int pc = ids.size() % rowsize == 0 ? ids.size() / rowsize : ids.size() / rowsize + 1;
				log("需更新待办任务数据:" + ids.size() + "条,将执行" + pc + "次清除,每次" + rowsize + "条");
				logger.warn("需更新待办任务数据:" + ids.size() + "条,将执行" + pc + "次清除,每次"
						+ rowsize + "条");
				List<String> tempdids = null;
				for (int i = 0; i < pc; i++) {
					log("执行第" + (i + 1) + "批清除");
					logger.warn("执行第" + (i + 1) + "批清除");
					if (ids.size() > rowsize * (i + 1)) {
						tempdids = ids.subList(rowsize * i, rowsize * (i + 1));
					} else {
						tempdids = ids.subList(rowsize * i, ids.size());
					}
					count += getBaseDao().getHibernateSession()
							.createQuery("update ThirdDingDtask set fdStatus='22' where " + HQLUtil.buildLogicIN("fdTaskId", tempdids))
							.executeUpdate();
					int count_old = count;
					// 高级审批
					count += getBaseDao().getHibernateSession()
							.createQuery(
									"update ThirdDingDtaskXform set fdStatus='22' where "
											+ HQLUtil.buildLogicIN("fdTaskId",
													tempdids))
							.executeUpdate();
					if (count != count_old) {
						// 高级审批：检查并更新实例
						updateInstance(tempdids);
					}

				}
				log("更新待办任务(条)："+count);
				logger.warn("更新待办任务(条)：" + count);
				ids.clear();
			}
			TransactionUtils.getTransactionManager().commit(status);
		} catch (Exception e) {
			if (status != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		}		
	}

	@SuppressWarnings("unchecked")
	private void updateInstance(List<String> tempdids) {
		try {
			List<ThirdDingDtaskXform> list = getBaseDao()
					.getHibernateSession().createQuery(
							"from ThirdDingDtaskXform where "
									+ HQLUtil.buildLogicIN(
											"fdTaskId",
											tempdids))
					.list();
			for (ThirdDingDtaskXform xform : list) {
				String notifyId = xform.getFdEkpTaskId();
				SysNotifyTodo todo = (SysNotifyTodo) getSysNotifyTodoService()
						.findByPrimaryKey(notifyId, null, true);
				String modelName = todo.getFdModelName();
				if (StringUtil.isNotNull(modelName)
						&& "com.landray.kmss.km.review.model.KmReviewMain"
								.equals(modelName)) {
					// 判断状态
					IBaseService obj = (IBaseService) SpringBeanUtil
							.getBean("kmReviewMainService");
					Object kmReviewMainObject = obj
							.findByPrimaryKey(todo.getFdModelId());
					Class clazz = kmReviewMainObject.getClass();
					Method method = clazz.getMethod("getDocStatus");
					String docStatus = (String) method
							.invoke(kmReviewMainObject);
					if ("20".equals(docStatus)) {
						continue;
					} else if ("00".equals(docStatus)) {
						// 废弃
						updateInstance(todo.getFdModelId(), false);
					} else if ("30".equals(docStatus)) {
						// 完成
						updateInstance(todo.getFdModelId(), true);
					}
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	// 高级审批 更新实例
	@SuppressWarnings("unchecked")
	private void updateInstance(String reviewMainId, boolean isAgree) {
		try {

			logger.debug("流程主文档fdId=>" + reviewMainId);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdEkpInstanceId =:fdEkpInstanceId and fdStatus=:fdStatus");
			hqlInfo.setParameter("fdEkpInstanceId", reviewMainId);
			hqlInfo.setParameter("fdStatus", "20");
			List<ThirdDingDinstanceXform> list = getThirdDingDinstanceXformService()
					.findList(hqlInfo);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ThirdDingDinstanceXform dinstanceXform = list.get(i);
					OapiProcessWorkrecordUpdateResponse response = DingNotifyUtil
							.updateInstanceState(
									DingUtils.dingApiService.getAccessToken(),
									dinstanceXform.getFdInstanceId(),
									Long.valueOf(list.get(i).getFdTemplate()
											.getFdAgentId()),
									dinstanceXform.getFdEkpUser().getFdId(),
									true);

					if (response != null && response.getErrcode() == 0) {
						logger.debug("更新实例成功");
						if (isAgree) {
							dinstanceXform.setFdStatus("30");
						} else {
							dinstanceXform.setFdStatus("00");
						}

						getThirdDingDinstanceXformService()
								.update(dinstanceXform);
					} else {
						logger.warn(
								"更新实例失败！" + "   reviewMainId:" + reviewMainId);
					}
				}

			} else {
				logger.warn("没有找到需要更新的实例！    主题：" + "   reviewMainId:"
						+ reviewMainId);
			}

			return;

		} catch (Exception e) {
			logger.error("更新实例状态失败", e);
		}
	}

	@Override
	public void cleaningAllNotify() throws Exception {
		// try {
		// init();
		// handleNotifyQuery(null);
		// } catch (Exception e) {
		// e.printStackTrace();
		// logger.error("清除所有异常待办出现异常：",e);
		// }
	}

	@Override
	public String updateCleaningNotify(String userId) throws Exception {
		String temp = "";
		ThirdDingNotifyHandleTaskModel handleModel = new ThirdDingNotifyHandleTaskModel();
		try {
			getComponentLockService().tryLock(handleModel, "notify", 3600000L);
			if (locked) {
				temp = "异常任务同步已经在运行，当前任务中断...";
				return temp;
			}
			locked = true;
			long alltime = System.currentTimeMillis();
			count = 0L;
			config = DingConfig.newInstance();
			relationMap = new HashMap<String, String>(2000);
			OmsRelationModel model = (OmsRelationModel) getOmsRelationService()
					.findFirstOne("fdEkpId='" + userId + "'", null);
			if (model != null) {
				relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
			}
			handlePersonSyncWR();
			// 增加开关
			// if ("true".equals(config.getNotifySynchroErrorWF())) {
			// handlePersonSyncWF();
			// }
			handlePersonSyncWF();
			temp = "整个任务总耗时(秒)："
					+ (System.currentTimeMillis() - alltime) / 1000;
			getComponentLockService().unLock(handleModel);
			return temp;
		} catch (ConcurrencyException e) {
			temp = "异常任务同步  或者 待办推送失败消息重发 已经在运行，当前任务中断...";
			logger.warn(temp);
			log(temp);
			return e.getMessage();
		} catch (Exception ex) {
			getComponentLockService().unLock(handleModel);
			logger.error(ex.getMessage(), ex);
			return ex.getMessage();
		} finally {
			count = 0L;
			relationMap = null;
			oldNotify.getAndSet(true);
			locked = false;
		}

	}	
	


	private void log(String log){
		if(StringUtil.isNotNull(log)){
			// logger.error(log);
			if(context != null){
				context.logMessage(log);
			} else {
				// logger.warn(log);
			}
		}
	}
	
	class PersonRunner implements Runnable {
		private final List<String> persons;

		public PersonRunner(List<String> persons) {
			this.persons = persons;
		}

		@Override
		public void run() {
			try {
				handleNotifyQuery(persons);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("清理钉钉待办不消失的问题出现异常：",e);
			} finally {
				countDownLatchWF.countDown();
			}
		}
	}

	class PersonRunnerWR implements Runnable {

		private final List<String> persons;

		public PersonRunnerWR(List<String> persons) {
			this.persons = persons;
		}

		@Override
		public void run() {
			try {
				handleNotifyQueryWR(persons);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("清理钉钉待办不消失的问题出现异常：", e);
			} finally {
				countDownLatchWR.countDown();
			}
		}
	}

	private void handleNotifyQueryWR(List<String> ekpids) throws Exception {
		if (ekpids == null) {
			ekpids = new ArrayList<String>(relationMap.keySet());
		}
		String token = DingUtils.getDingApiService().getAccessToken();
		try {
			for (String ekpid : ekpids) {

				logger.debug(
						"即将清理EKP|钉钉员工：" + ekpid + "|" + relationMap.get(ekpid)
								+ " 不消失的待办");
				if (StringUtil.isNotNull(ekpid)
						&& StringUtil.isNotNull(relationMap.get(ekpid))
						&& StringUtil.isNotNull(token)) {
					handleNotifyWR(token, relationMap.get(ekpid), 0L, ekpid);
				}

				logger.debug("完成EKP|钉钉员工的待办清理：" + ekpid + "|"
						+ relationMap.get(ekpid)
						+ " 不消失的待办");
			}
		} catch (Exception e) {

		}
	}

	private void handleNotifyWR(String token, String userid, long page,
			String ekpUserId)
			throws ApiException {

		String url = DingConstant.DING_PREFIX + "/topapi/workrecord/getbyuserid"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		// logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiWorkrecordGetbyuseridRequest req = new OapiWorkrecordGetbyuseridRequest();
		req.setUserid(userid);
		req.setOffset(page * 50);
		req.setLimit(50L);
		req.setStatus(0L);
		logger.warn("调用getbyuserid接口，userid:" + userid + ",page:" + page);
		OapiWorkrecordGetbyuseridResponse rsp = client.execute(req, token);
		if (rsp.getErrcode() == 0) {
			PageResult pr = rsp.getRecords();
			List<WorkRecordVo> vos = pr.getList();
			SysNotifyTodo todo = null;
			List<SysOrgElement> todoTargets = null;
			if (vos != null && !vos.isEmpty()) {
				for (WorkRecordVo vo : vos) {
					if (vo == null) {
                        continue;
                    }
					String recordId = vo.getRecordId();
					List<FormItemVo> froms = vo.getForms();
					logger.debug("标题：" + froms.get(0).getContent() + ","
									+ vo.getUrl());
					ThirdDingNotifyWorkrecord record = null;
					try {
						record = getThirdDingNotifyWorkrecordService()
								.findByRecordId(recordId);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						continue;
					}
					if (record == null) {
						continue;
					}
					String vo_url = vo.getUrl();
					logger.debug("【新待办接口】url:" + vo.getUrl());
					if (StringUtil.isNotNull(vo_url)
							&& !DingUtil.checkUrlByDomain(vo_url)) {
						log("待办任务的url和本系统域名不一致，不做处理:" + vo_url);
						logger.warn("待办任务的url和本系统域名不一致，不做处理");
						continue;
					}

					String todoId = record.getFdNotifyId();
					if (StringUtil.isNull(todoId)) {
                        continue;
                    }
					TransactionStatus updateStatus = null;
					try {
						updateStatus = TransactionUtils
								.beginNewTransaction();
						try {
							todo = (SysNotifyTodo) getSysNotifyTodoService()
									.findByPrimaryKey(todoId, null, true);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							// continue;
						}
						if (todo == null || todo.getHbmTodoTargets() == null
								|| todo.getHbmTodoTargets().size() == 0) {
							logger.warn("EKP中待办找不到(" + todoId + "|" + userid
									+ ")，直接删除钉钉中的待办(" + vo.getRecordId()
									+ ")【待办接口】");
							delNotifyWR(userid, vo.getRecordId(), todo, record,
									vo.getTitle());
							// continue;
						} else {
							todoTargets = todo.getHbmTodoTargets();
							boolean flag = true;
							for (SysOrgElement ele : todoTargets) {
								if (userid.equals(
										relationMap.get(ele.getFdId()))) {
									flag = false;
									break;
								}
							}
							if (flag) {
								// 更新调用钉钉的更新接口来结束待办(待办列表中无此人的待办)
								delNotifyWR(userid, vo.getRecordId(), todo,
										record, vo.getTitle());
								logger.warn(
										"EKP中待办已经处理(" + todoId + "|" + userid
												+ ")，直接删除钉钉中的待办("
												+ vo.getRecordId()
												+ ")【待办接口】");
							}
						}
						TransactionUtils.getTransactionManager()
								.commit(updateStatus);
					} catch (Exception e) {
						if (updateStatus != null) {
							try {
								TransactionUtils.getTransactionManager()
										.rollback(updateStatus);
							} catch (Exception ex) {
								logger.error("---事务回滚出错---", ex);
							}
						}
					}
				}
				if (pr.getHasMore()) {
					handleNotifyWR(token, userid, page + 1, ekpUserId);
				}
			}
		} else if (rsp.getErrcode() == 88
				&& rsp.getBody().indexOf("60011") > 0) {
			oldNotify.getAndSet(false);
		}
	}

	private void delNotifyWR(String userid, String recordId, SysNotifyTodo todo,
			ThirdDingNotifyWorkrecord record, String title) {
		OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
		req.setUserid(userid);
		req.setRecordId(recordId);
		callDingdingUpdate(
				DingConstant.DING_PREFIX + "/topapi/workrecord/update",
				todo,
				req, record, title);
	}

	private JSONObject buildUpdateRequestJson(OapiWorkrecordUpdateRequest req,
			String ekpUserId, SysNotifyTodo todo) {
		JSONObject data = new JSONObject();
		data.put("ekpUserId", ekpUserId);
		if (todo != null) {
			data.put("todoId", todo.getFdId());
			data.put("subject", todo.getFdSubject());
		}
		data.put("userId", req.getUserid());
		data.put("recordId", req.getRecordId());
		return data;
	}

	private void callDingdingUpdate(String url, SysNotifyTodo todo,
			OapiWorkrecordUpdateRequest req,
			ThirdDingNotifyWorkrecord record, String title) {
		// Thread.dumpStack();
		String dingUrl = url + DingUtil.getDingAppKeyByEKPUserId("?",
				todo.getDocCreator() == null ? null
						: todo.getDocCreator().getFdId());
		logger.debug("钉钉接口：" + dingUrl);
		DingApiService dingService = DingUtils.getDingApiService();
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		ThirdDingNotifyLog log = new ThirdDingNotifyLog();
		log.setFdUrl(client.getRequestUrl());
		if (todo != null) {
			log.setFdNotifyId(todo.getFdId());
			log.setDocSubject(todo.getFdSubject());
		} else {
			log.setDocSubject("【待办清理】找不到对应的ekp待办：" + title);
		}
		Date start = new Date();
		log.setFdSendTime(start);
		log.setFdNotifyData(
				buildUpdateRequestJson(req, null, todo).toString());
		OapiWorkrecordUpdateResponse rsp;
		try {
			// TransactionStatus deleteStatus = TransactionUtils
			// .beginNewTransaction();
			rsp = client.execute(req,
					dingService.getAccessToken());
			log.setFdRtnMsg(rsp.getBody());
			JSONObject jo = JSONObject.fromObject(rsp.getBody());
			String requestId = null;
			if (jo.containsKey("request_id")) {
				requestId = jo.getString("request_id");
			}
			log.setFdRequestId(requestId);
			logger.debug("result:" + jo);
			if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
				log.setFdResult(true);
				logger.info("待办更新到钉钉详细：" + jo.toString());
				try {
					if (record != null) {
						thirdDingNotifyWorkrecordService
								.delete(record.getFdId());
					}

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				log.setFdResult(false);
				logger.warn("待办更新到钉钉创建失败。详细：" + jo.toString());
			}
			// TransactionUtils.getTransactionManager().commit(deleteStatus);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			log.setFdResult(false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			log.setFdResult(false);
			log.setFdRtnMsg(e.getMessage());

		} finally {
			Date end = new Date();
			log.setFdRtnTime(end);
			log.setFdExpireTime(end.getTime() - start.getTime());
			try {
				getThirdDingNotifyLogService().add(log);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private IThirdDingNotifyLogService thirdDingNotifyLogService;

	public IThirdDingNotifyLogService getThirdDingNotifyLogService() {
		if (thirdDingNotifyLogService == null) {
			thirdDingNotifyLogService = (IThirdDingNotifyLogService) SpringBeanUtil
					.getBean("thirdDingNotifyLogService");
		}
		return thirdDingNotifyLogService;
	}

	@Override
	public String cleaningBytool(JSONArray data) throws Exception {
		logger.debug("---------------【新接口待办清理】------------------");
		if (data == null || data.size() == 0) {
			return null;
		}
		if (ids == null) {
			ids = new CopyOnWriteArrayList<String>();
		}
		for (int i = 0; i < data.size(); i++) {
			JSONObject curTodo = data.getJSONObject(i);
			logger.debug("准备清理待办：" + curTodo);
			String type = curTodo.getString("type");
			String dingUserid_ekpid = curTodo.getString("ids");
			logger.debug("ids:" + ids);
			if ("工作流接口".equals(type)) {
				logger.debug("---工作流接口待办处理---");
				// 实例id
				String instanceId = curTodo.getString("dingInstanceId");
				// 待办任务id
				String taskId = curTodo.getString("dingNotifyId");

				OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
				OapiProcessWorkrecordTaskUpdateResponse res = DingNotifyUtil
						.updateTask(req,
								DingUtils.dingApiService.getAccessToken(),
								instanceId,
								Long.parseLong(taskId),
								Long.parseLong(DingConfig.newInstance()
										.getDingAgentid()),
								dingUserid_ekpid.split(";")[1]);
				if (res.getErrcode() == 0) {
					ids.add(taskId);
					logger.debug("更新待办结果详情：" + res.getBody());
				} else {
					logger.warn("更新待办失败，详情：" + res.getBody());
				}
			} else if ("待办任务接口".equals(type)) {
				logger.debug("---待办任务接口待办处理---");
				String userid = curTodo.getString("userid");
				String recordid = curTodo.getString("dingNotifyId");
				boolean flag = delDingNotify(userid, recordid,
						dingUserid_ekpid.split(";")[1]);
				if (flag) {
					logger.warn("------删除成功-----");
					TransactionStatus status = null;
					try {
						status = TransactionUtils.beginNewTransaction();
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setSelectBlock("fdId");
						hqlInfo.setWhereBlock("fdRecordId=:fdRecordId");
						hqlInfo.setParameter("fdRecordId", recordid);
						String fdId = (String) getThirdDingNotifyWorkrecordService()
								.findFirstOne(hqlInfo);
						if (StringUtils.isNotBlank(fdId)) {
							getThirdDingNotifyWorkrecordService()
									.delete(fdId);
							TransactionUtils.getTransactionManager()
									.commit(status);
						}

						// 删除消息队列里错误的信息
						hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock(
								"fdTodoId=:fdTodoId and fdDingUserId=:fdDingUserId and fdMethod=:fdMethod");
						hqlInfo.setParameter("fdTodoId",
								curTodo.getString("dingNotifyId"));
						hqlInfo.setParameter("fdDingUserId",
								curTodo.getString("userid"));
						hqlInfo.setParameter("fdMethod", "update");
						List<ThirdDingNotifyQueueError> errorsList = getThirdDingNotifyQueueErrorService()
								.findList(hqlInfo);
						if (errorsList != null && errorsList.size() > 0) {
							for (ThirdDingNotifyQueueError error : errorsList) {
								error = errorsList.get(0);
								error.setFdFlag(
										ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_SEND);
								error.setFdErrorMsg("工具已清理该待办");
								getThirdDingNotifyQueueErrorService()
										.update(error);
							}

						}

					} catch (Exception e) {
						if (status != null) {
							try {
								TransactionUtils.getTransactionManager()
										.rollback(status);
							} catch (Exception ex) {
								logger.error("---事务回滚出错---", ex);
							}
						}
					}

				} else {
					logger.warn("------删除失败-----");
				}
			}
		}

		// 更新ekp待办状态记录
		updateTask(null);

		return null;
	}

}
