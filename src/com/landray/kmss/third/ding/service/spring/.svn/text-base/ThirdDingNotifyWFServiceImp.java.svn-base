package com.landray.kmss.third.ding.service.spring;

import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse.TaskTopVo;
import com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.PageResult;
import com.dingtalk.api.response.OapiWorkrecordGetbyuseridResponse.WorkRecordVo;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
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
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.*;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThirdDingNotifyWFServiceImp extends ExtendDataServiceImp implements IThirdDingNotifyService, DingConstant {

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

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingNotifyWFServiceImp.class);

	private static boolean locked = false;

	private Map<String, String> relationMap = null;
	
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

	private CountDownLatch countDownLatch;
	
	@Override
	public void synchroError(SysQuartzJobContext context) {
		this.context = context;
		String temp = "";
		if (locked) {
			temp = "异常任务同步已经在运行，当前任务中断...";
			logger.warn(temp);
			log(temp);
			return;
		}
		try {
			locked = true;
			long alltime = System.currentTimeMillis();
			init();
			handlePersonSync();
			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
			logger.warn(temp);
			log(temp);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
		} finally {
			count = 0L;
			locked = false;
			relationMap = null;
			oldNotify.getAndSet(true);
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

	private String getAppKey() {
		return StringUtil.isNull(DING_OMS_APP_KEY) ? "default" : DING_OMS_APP_KEY;
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

	private void handleDingNotify() throws Exception {
		List<SysOrgPerson> persons = getSysOrgPersonService().findList(null, null);
		String userid = null;
		if ("true".equals(config.getDingEnabled())) {
			String token = DingUtils.getDingApiService().getAccessToken();
			for (SysOrgPerson person : persons) {
				if (!relationMap.containsKey(person.getFdId())) {
                    continue;
                }
				userid = relationMap.get(person.getFdId());
				if (StringUtil.isNotNull(userid) && StringUtil.isNotNull(token)) {
					getNotify(token, userid, 0L, person.getFdId());
				}
			}
		} else {
			logger.debug("钉钉集成未开启，不执行钉钉待办的更新操作");
		}
	}

	private void getNotify(String token, String userid, long page,
			String ekpUserId) throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/workrecord/getbyuserid"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiWorkrecordGetbyuseridRequest req = new OapiWorkrecordGetbyuseridRequest();
		req.setUserid(userid);
		req.setOffset(page * 50);
		req.setLimit(50L);
		req.setStatus(0L);
		OapiWorkrecordGetbyuseridResponse rsp = client.execute(req, token);
		if (rsp.getErrcode() == 0) {			
			PageResult pr = rsp.getRecords();
			List<WorkRecordVo> vos = pr.getList();
			String fdId = "";
			SysNotifyTodo todo = null;
			List<SysOrgElement> todoTargets = null;
			if (vos != null) {
				for (WorkRecordVo vo : vos) {
					if (vo == null || StringUtil.isNull(vo.getUrl())) {
                        continue;
                    }

					if (StringUtil.isNotNull(vo.getUrl())
							&& !DingUtil.checkUrlByDomain(vo.getUrl())) {
						log("待办任务的url和本系统域名不一致，不做处理");
						logger.warn("待办任务的url和本系统域名不一致，不做处理");
						continue;
					}
					fdId = StringUtil.getParameter(vo.getUrl(), "fdId");
					if(StringUtil.isNull(fdId)) {
                        continue;
                    }
					todo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(fdId, null, true);
					if (todo == null || todo.getHbmTodoTargets() == null || todo.getHbmTodoTargets().size() == 0) {
						logger.debug("EKP中待办找不到(" + fdId + "|" + userid + ")，直接删除钉钉中的待办(" + vo.getRecordId() + ")【钉钉老待办接口】");
						delDingNotify(userid, vo.getRecordId(), ekpUserId);
						continue;
					} else {
						todoTargets = todo.getHbmTodoTargets();
						boolean flag = true;
						for (SysOrgElement ele : todoTargets) {
							if (userid.equals(relationMap.get(ele.getFdId()))) {
								flag = false;
								break;
							}
						}
						if (flag) {
							// 更新调用钉钉的更新接口来结束待办(待办列表中无此人的待办)
							delDingNotify(userid, vo.getRecordId(), ekpUserId);
							logger.debug("EKP中待办已经处理(" + fdId + "|" + userid + ")，直接删除钉钉中的待办(" + vo.getRecordId() + ")【钉钉老待办接口】");
						}
					}
				}
				if (pr.getHasMore()) {
					getNotify(token, userid, page + 1, ekpUserId);
				}
			}
		}else if(rsp.getErrcode()==88 && rsp.getBody().indexOf("60011")>0){
			oldNotify.getAndSet(false);
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

	private void handleNotifyNew(SysQuartzJobContext context) throws Exception {
		// ThirdDingDtemplate template =
		// getThirdDingDtemplateService().updateCommonTemplate();
		List<ThirdDingDtemplate> list = getThirdDingDtemplateService().findList(
				"fdType=1", "docCreateTime desc");

		if (list == null || list.isEmpty()) {
			logger.debug("============待办模板为空或者ProcessCode为空，直接跳过不进行待办清洗============");
			context.logMessage("============待办模板为空或者ProcessCode为空，直接跳过不进行待办清洗============");
			return;
		}
		Map<String, ThirdDingDinstance> map = new HashMap<String, ThirdDingDinstance>();
		String token = DingUtils.getDingApiService().getAccessToken();
		for (ThirdDingDtemplate template : list) {
			if (StringUtil.isNull(template.getFdProcessCode())) {
				continue;
			}
			getAllInstances(map, token, template.getFdProcessCode(), 0);
		}
		logger.debug("获取钉钉流程实例数量为：" + map.size());
		context.logMessage("获取钉钉流程实例数量为：" + map.size());
		// 获取数据库中的所有审批实例
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("docCreateTime>=:startTime and docCreateTime<=:endTime");
		info.setParameter("startTime", date);
		info.setParameter("endTime", lastDate);
		List<ThirdDingDinstance> dlist = getThirdDingDinstanceService().findList(info);
		logger.debug("获取EKP流程实例数量为：" + dlist.size());
		context.logMessage("获取EKP流程实例数量为：" + dlist.size());
		for (ThirdDingDinstance dingDinstance : dlist) {
			map.put(dingDinstance.getFdInstanceId(), dingDinstance);
		}
		logger.debug("获取所有流程实例数量为：" + map.size()+",时间区域为："+date.toLocaleString()+"---"+lastDate.toLocaleString());
		context.logMessage("获取所有流程实例数量为：" + map.size()+",时间区域为："+date.toLocaleString()+"---"+lastDate.toLocaleString());
		long atime = System.currentTimeMillis();
		for (String pid : map.keySet()) {
			updateTask(token, pid, map, context);
		}
		logger.debug("处理不消失的钉钉消息总耗时："+(System.currentTimeMillis()-atime)/1000+"秒,处理的条数为：" + count);
		context.logMessage("处理不消失的钉钉消息总耗时："+(System.currentTimeMillis()-atime)/1000+"秒,处理的条数为：" + count);
		String clearDay = DateUtil.convertDateToString(lastDate, DateUtil.PATTERN_DATE);
		config.setNotifyClearDay(clearDay);
		config.save();
		logger.debug("处理不消失的钉钉消息更新成功，已经处理的时间点为："+clearDay);
		context.logMessage("处理不消失的钉钉消息更新成功，已经处理的时间点为："+clearDay);
	}

	private void getAllInstances(Map<String, ThirdDingDinstance> map, String token, String template, long page)
			throws Exception {
		// 根据待办模板code获取所有的审批实例
		String url = DingConstant.DING_PREFIX
				+ "/topapi/processinstance/listids"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiProcessinstanceListidsRequest req = new OapiProcessinstanceListidsRequest();
		req.setProcessCode(template);
		req.setStartTime(date.getTime());
		req.setEndTime(lastDate.getTime());
		req.setSize(20L);
		req.setCursor(page);
		// req.setUseridList("manager1896");
		OapiProcessinstanceListidsResponse response = client.execute(req, token);
		if (response.getErrcode() == 0) {
			if (response.getResult() != null) {
				List<String> list = response.getResult().getList();
				for (String pid : list) {
					map.put(pid, null);
				}
				if (response.getResult().getNextCursor() != null) {
					getAllInstances(map, token, template, response.getResult().getNextCursor());
				}
			}
		} else {
			logger.error("根据待办模板code获取所有的审批实例失败，详情：" + response.getBody());
		}
	}

	private void updateTask(String token, String instanceid, Map<String, ThirdDingDinstance> map,SysQuartzJobContext context) throws Exception {
		logger.debug("定时任务调用钉钉接口：/topapi/processinstance/get");
		String url = DingConstant.DING_PREFIX + "/topapi/processinstance/get"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
		request.setProcessInstanceId(instanceid);
		OapiProcessinstanceGetResponse response = client.execute(request, token);
		String fdId = null;
		SysNotifyTodo todo = null;
		List<SysOrgElement> todoTargets = null;
		if (response.getErrcode() == 0) {
			Long agentid = Long.parseLong(config.getDingAgentid());
			// 直接判断未结束的任务
			List<TaskTopVo> tasks = response.getProcessInstance().getTasks();
			if (tasks != null) {
				for (TaskTopVo vo : tasks) {
					if (vo == null) {
                        continue;
                    }
					boolean flag = true;
					// 获取所有未结束任务
					if (!"COMPLETED".equals(vo.getTaskStatus())) {
						// 任务实例结束则直接结束对应任务
						if ("COMPLETED".equals(response.getProcessInstance().getStatus())) {
							logger.debug("任务实例结束，则直接删除钉钉中的待办,TaskStatus="+vo.getTaskStatus());
						} else {
							// 获取任务的url对应的EKP待办，如果待办处理则直接结束
							fdId = StringUtil.getParameter(vo.getUrl(), "fdId");
							todo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(fdId, null, true);
							if (todo == null || todo.getHbmTodoTargets() == null || todo.getHbmTodoTargets().size() == 0) {
								logger.debug("EKP中待办已经删除或者未办人员为空，则直接删除钉钉中的待办");
							} else {
								todoTargets = todo.getHbmTodoTargets();
								for (SysOrgElement ele : todoTargets) {
									if (vo.getUserid().equals(relationMap.get(ele.getFdId()))) {
										flag = false;
										break;
									}
								}
								if (flag) {
									logger.debug("当前处理人不在此EKP待办中，则直接删除钉钉中的待办id=" + fdId);
								}
							}
						}
						if (flag) {
							OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
							OapiProcessWorkrecordTaskUpdateResponse res = DingNotifyUtil.updateTask(req, token, instanceid,
											Long.parseLong(vo.getTaskid()),
											agentid, null);
							if (res.getErrcode() == 0) {
								++count;
								logger.debug("消除不消失的待办成功，待办id=" + fdId + ",钉钉任务Id=" + vo.getTaskid());
								context.logMessage("消除不消失的待办成功，待办id=" + fdId + ",钉钉任务Id=" + vo.getTaskid());
								getBaseDao().getHibernateSession() .createQuery("update ThirdDingDtask set fdStatus='22' where fdTaskId =:fdTaskId")
									.setString("fdTaskId", vo.getTaskid()).executeUpdate();
							} else {
								logger.error("更新待办失败，详情：" + res.getBody());
								context.logMessage("更新待办失败，详情：" + res.getBody());
							}
						}
					}
				}
			}
		} else {
			logger.error("获取流程实例失败，详情：" + response.getBody());
			context.logMessage("获取流程实例失败，详情：" + response.getBody());
		}
	}
	
	private CopyOnWriteArrayList<String> ids = null;
	
	private void handlePersonSync() throws Exception {
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
			countDownLatch = new CountDownLatch(count);
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
				countDownLatch.await(3, TimeUnit.HOURS);
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
					SysOrgPerson person = (SysOrgPerson) getSysOrgPersonService()
							.findByPrimaryKey(ekpid, null, true);
					// 处理待处理的待办数据
					getQueryWorkFinished(context, token, relationMap.get(ekpid),
                            0L, 0L, person);
					// 处理手动移除的待办数据
					getQueryWorkFinished(context, token, relationMap.get(ekpid),
                            0L, -1L, person);
					// 处理老待办接口
					if (oldNotify.get()) {
						getNotify(token, relationMap.get(ekpid), 0L, ekpid);
					}
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
	
	public void getQueryWorkFinished(SysQuartzJobContext context, String token,
			String userid, long page, long status, SysOrgPerson person)
			throws Exception {
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/task/query"
				+ DingUtil.getDingAppKeyByEKPUserId("?", person.getFdId());
		// logger.debug("钉钉接口：" + dingUrl);
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
				// log("钉钉人员：" + userid + "(EKP ID:" + person.getFdId() +
				// ",名称:+"
				// + person.getFdName() + ") 有" + vos.size()
				// + "条待办数据需要验证");
				logger.debug(
						"钉钉人员：" + userid + " 有" + vos.size() + "条待办数据需要验证");
				Map<String,List<com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo>> pgMap = new HashMap<String,List<com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo>>();
				List<com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo> pgvos = null;
				for(com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo vo:vos){
					logger.debug("处理待办：" + vo.getTitle() + " 待办url:"
							+ vo.getUrl() + "  待办taskId:" + vo.getTaskId());
					ThirdDingDtask task = getThirdDingDtaskService()
							.findByTaskId(vo.getInstanceId(), vo.getTaskId());
					if (task != null) {
						Date createTime = task.getDocCreateTime();
						logger.debug("current:" + System.currentTimeMillis());
						if (createTime != null && (System.currentTimeMillis()
								- createTime.getTime()) < 10000L) {
							logger.warn("10秒内刚推送过去的待办，暂不处理，等下次任务再处理。"
									+ vo.getTitle());
							continue;
						}
					} else {
						logger.warn("找不到对应的task映射，taskId:" + vo.getTaskId()
								+ ",instanceId:" + vo.getInstanceId());
						continue;
					}
					if (!DingUtil.checkUrlByDomain(vo.getUrl())) {
						log("待办任务的url和本系统域名不一致，不做处理:" + vo.getUrl());
						logger.warn("待办任务的url和本系统域名不一致，不做处理");
						continue;
					}
					String url = vo.getUrl().replace("?", "&");
					fdId = StringUtil.getParameter(url, "fdId");
					logger.debug("fdId：" + fdId);
					if(StringUtil.isNull(fdId)){
						fdId = StringUtil.getParameter(url, "fdTodoId");
						logger.debug("fdTodoId：" + fdId);
					}
					if (StringUtil.isNull(fdId) && StringUtil.isNotNull(url)
							&& url.contains("/sso_redirect.jsp")) {

						String base64_url = StringUtil.getParameter(url, "url");
						logger.debug("base64_url：" + base64_url);
						fdId = StringUtil.getParameter(
								SecureUtil.BASE64Decoder(base64_url)
										.replace("?", "&"),
								"fdId");
						logger.debug("sso_redirect  fdId:" + fdId);
					}
					// http://chenhw.myekp.com/resource/jsp/sso_redirect.jsp?url=aHR0cDovL2NoZW5ody5teWVrcC5jb20vc3lzL25vdGlmeS9zeXNfbm90aWZ5X3RvZG8vc3lzTm90aWZ5VG9kby5kbz9tZXRob2Q9dmlldyZmZElkPTE3M2JlNzM0MDc1ZTgwYWNmYzc3NGI0NDk2NGIyMTQyJm9hdXRoPWVrcCZkaW5nT3V0PXRydWU=

					if (StringUtil.isNull(fdId)) {
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
								pgvos.add(vo);
								pgMap.put(pgid, pgvos);
							}else if(StringUtil.isNotNull(StringUtil.getParameter(vo.getUrl(), "toUrl"))&&vo.getUrl().contains("pcpg.jsp")){
								// 不存在fdId则直接更新待办不管是否已经处理
								delDingNotifyNew("fdId为空|数据修复", token, userid,
										agentid, vo, context, "",
										person.getFdId());
							}
						}
						continue;
					}
					todo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(fdId, null, true);
					if (todo == null || todo.getHbmTodoTargets() == null || todo.getHbmTodoTargets().size() == 0) {
						logname = "EKP中待办找不到(待办id=" + fdId + ",钉钉用户userId=" + userid + ")，直接删除钉钉中的待办(" + vo.getTaskId() + ")";
						delDingNotifyNew(fdId, token, userid, agentid, vo,
								context, logname, person.getFdId());
						continue;
					} else {
						logger.debug("待办任务中有对应的数据");
						todoDones = todo.getHbmDoneInfos();
						for (SysNotifyTodoDoneInfo done : todoDones) {
							if (person.getFdId().equals(done.getOrgElement().getFdId())) {
								// 更新调用钉钉的更新接口来结束待办(已办列表中有此人的待办)
								logname = "EKP中待办已经处理(" + fdId + "|" + userid + ")，直接删除钉钉中的待办(" + vo.getTaskId() + ")";
								delDingNotifyNew(fdId, token, userid, agentid,
										vo, context, logname, person.getFdId());
								break;
							}
						}
						if (todoDones == null || todoDones.isEmpty()) {
							// log("待办已处理列表中没有处理人记录，不清除该待办记录");
							logger.debug("待办已处理列表中没有处理人记录，不清除该待办记录");
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
							List rlist = session.createSQLQuery(sql + " and td.fd_model_id='" + key + "' and tt.fd_orgid='" + person.getFdId() + "'").setMaxResults(1).list();
							// 待办中找不到直接删除(多条只留一条pgflag)
							if (rlist == null || rlist.isEmpty() || pgflag) {
								logname = "EKP中待办已处理(" + key + "|" + userid + ")，直接删除钉钉中的待办(" + pgvos.get(0).getTaskId() + ")|数据修复";
								delDingNotifyNew(key, token, userid, agentid,
										pgvos.get(i), context, logname,
										person.getFdId());
							} else {
								pgflag = true;
							}
						}
					}
				}
				if(result.getHasMore()){
					getQueryWorkFinished(context, token,userid,page+1,status,person);
				}
			}
		}else{
			logger.error("待办清理异常，详细错误："+response.getBody());
		}
	}
	
	private void delDingNotifyNew(String fdId, String token, String userid,
			long agentId,
			com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo vo,
			SysQuartzJobContext context, String name, String personId)
			throws Exception {
		OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
		OapiProcessWorkrecordTaskUpdateResponse res = DingNotifyUtil.updateTask(req, token, vo.getInstanceId(),
				Long.parseLong(vo.getTaskId()), agentId, personId);
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

	@Override
	public void cleaningAllNotify() throws Exception {
		try {
			init();
			handleNotifyQuery(null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("清除所有异常待办出现异常：",e);
		}		
	}

	@Override
	public String updateCleaningNotify(String userId) throws Exception {
		init();
		String msg = updateHandleNotifyQuery(userId);
		return msg;
	}	
	
	private String updateHandleNotifyQuery(String userId) throws Exception {
		String msg = "";
		if ("true".equals(config.getDingEnabled())) {
			ids = new CopyOnWriteArrayList<String>();
			SysOrgPerson person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(userId,null,true);
			String userid = null;
			String token = DingUtils.getDingApiService().getAccessToken();
			OmsRelationModel model = (OmsRelationModel) getOmsRelationService().findFirstOne("fdEkpId='" + userId + "'", null);
			if (model!=null&&person!=null){
				userid = model.getFdAppPkId();
				if (StringUtil.isNotNull(userid) && StringUtil.isNotNull(token)) {
					// 处理待处理的待办数据
					getQueryWorkFinished(context, token, userid, 0L, 0L, person);
					// 处理手动移除的待办数据
					getQueryWorkFinished(context, token, userid, 0L, -1L, person);
					// 更新对应的待办任务
					updateTask(context);
				}
			}else{
				logger.debug("EKP人员("+userId+")钉钉映射人员无法找到！");
				msg = "EKP人员("+userId+")钉钉映射人员无法找到！";
			}
		} else {
			logger.debug("钉钉集成未开启，不执行钉钉待办清除操作！");
			msg = "钉钉集成未开启，不执行钉钉待办清除操作！";
		}
		return msg;
	}
	
	private void log(String log) {
		if(StringUtil.isNotNull(log)){
			if(context != null){
				context.logMessage(log);
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
				countDownLatch.countDown();
			}
		}
	}
	// ========================================新待办接口历史数据处理结束=====================================================//

	@Override
	public String cleaningBytool(JSONArray data) throws Exception {
		logger.debug("---------------【旧接口待办清理】------------------");
		return null;
	}
}
