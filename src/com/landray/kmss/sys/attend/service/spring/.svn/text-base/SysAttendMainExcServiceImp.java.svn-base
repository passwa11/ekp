package com.landray.kmss.sys.attend.service.spring;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.attend.dao.ISysAttendSynDingDao;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.lbpm.engine.builder.ProcessInstance;
import com.landray.kmss.sys.lbpm.engine.manager.operation.OperationParameters;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmNode;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.service.ExecuteParameters;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;
/**
 * 签到异常表业务接口实现
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendMainExcServiceImp extends BaseServiceImp
		implements ISysAttendMainExcService, IEventMulticasterAware,
		ApplicationListener<ApplicationEvent> {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendMainExcServiceImp.class);
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private ISysAttendStatJobService sysAttendStatJobService;
	private ProcessExecuteService processExecuteService;
	private ILbpmProcessService lbpmProcessService;
	private ISysAttendCategoryService sysAttendCategoryService;

	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;

	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	public void setSysAttendStatJobService(
			ISysAttendStatJobService sysAttendStatJobService) {
		this.sysAttendStatJobService = sysAttendStatJobService;
	}

	public void
			setProcessExecuteService(
					ProcessExecuteService processExecuteService) {
		this.processExecuteService = processExecuteService;
	}

	public void setLbpmProcessService(ILbpmProcessService lbpmProcessService) {
		this.lbpmProcessService = lbpmProcessService;
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysAttendMainExc mainExc = (SysAttendMainExc) modelObj;
		mainExc.setDocCreateTime(new Date());
		mainExc.setDocCreator(UserUtil.getUser());
		SysAttendMain main = mainExc.getFdAttendMain();
		if (SysDocConstant.DOC_STATUS_DRAFT.equals(mainExc.getDocStatus())) {
			mainExc.setFdStatus(0);
			main.setFdState(0);
		} else {
			mainExc.setFdStatus(1);
			main.setFdState(1);
			// 提交异常单时，更新缺卡待办为已办
			sysNotifyMainCoreService.getTodoProvider().removeTodo(main,
					"sendUnSignNotify");
		}
		String fdId = super.add(modelObj);
		return fdId;
	}

	private void sendNotifyTodo(SysAttendMainExc mainExc) throws Exception {
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("sys-attend:sysAttendMainExc.notify");
		notifyContext.setNotifyType("todo");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		notifyContext.setKey("sysAttendMainExcHandle");
		if (mainExc.getFdHandler() == null) {
			return;
		}
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		list.add(mainExc.getFdHandler());
		notifyContext.setNotifyTarget(list);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("docCreator",
				mainExc.getFdAttendMain().getDocCreator().getFdName());
		sysNotifyMainCoreService.send(mainExc, notifyContext, hashMap);
	}

	@Override
	@Deprecated
	public void updateStatus(String[] ids, int fdStatus) throws Exception {
		List list = getBaseDao().findByPrimaryKeys(ids);
		for (Iterator it = list.iterator(); it.hasNext();) {
			SysAttendMainExc mainExc = (SysAttendMainExc) it.next();
			Integer oldStatus = mainExc.getFdStatus();
			mainExc.setFdStatus(fdStatus);
			mainExc.setFdHandler(UserUtil.getUser());
			mainExc.setDocHandleTime(new Date());
			final SysAttendMain main = mainExc.getFdAttendMain();
			main.setFdState(fdStatus);
			if (mainExc.getFdAttendTime() != null
					&& mainExc.getFdStatus() == 2) {
				Date oldTime = main.getDocCreateTime();
				if (mainExc.getFdAttendTime().getDate() > oldTime.getDate()) {
					main.setFdIsAcross(true);
				}
				main.setDocCreateTime(mainExc.getFdAttendTime());
			}
			getBaseDao().update(mainExc);
			if (oldStatus == 1) {
				// 发送待阅
				sendNotify(mainExc);
				// 删除待办
				sysNotifyMainCoreService.getTodoProvider().removeTodo(mainExc,
						"sysAttendMainExcHandle");
			}
			// 考勤异常审批通过后 重新统计用户数据
			getBaseDao().flushHibernateSession();
			multicaster.attatchEvent(
					new EventOfTransactionCommit(StringUtils.EMPTY),
					new IEventCallBack() {
						@Override
						public void execute(ApplicationEvent arg0)
								throws Throwable {
							sysAttendStatJobService.stat(
									main.getDocCreator(),
									main.getDocCreateTime(),null);
						}
					});

		}
	}

	private void sendNotify(SysAttendMainExc mainExc) throws Exception {
		int fdStatus = mainExc.getFdStatus();
		String key = "sys-attend:sysAttendMainExc.notify.done";
		if (fdStatus == 3) {
			key = "sys-attend:sysAttendMainExc.notify.refuse";
		}
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext(key);
		notifyContext.setNotifyType("todo");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setKey("sysAttendMainExcHandled");
		if (mainExc.getFdAttendMain() == null
				|| mainExc.getFdAttendMain().getDocCreator() == null) {
			return;
		}
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		list.add(mainExc.getFdAttendMain().getDocCreator());
		notifyContext.setNotifyTarget(list);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		sysNotifyMainCoreService.send(mainExc, notifyContext, hashMap);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof SysAttendMainExc)) {
			return;
		}
		SysAttendMainExc mainExc = (SysAttendMainExc) obj;
		// 监听流程结束事件和废弃事件
		if (event instanceof Event_SysFlowFinish
				|| event instanceof Event_SysFlowDiscard) {
			try {
				Integer fdStatus = event instanceof Event_SysFlowFinish ? 2 : 3;
				mainExc.setFdStatus(fdStatus);
				mainExc.setFdHandler(UserUtil.getUser());
				mainExc.setDocHandleTime(new Date());
				final SysAttendMain main = mainExc.getFdAttendMain();
				main.setFdState(fdStatus);
				boolean statAttend =false;
				if (mainExc.getFdAttendTime() != null
						&& mainExc.getFdStatus() == 2) {
					// 考勤异常通过
					Date oldAttendTime = main.getDocCreateTime();
					if (mainExc.getFdAttendTime().getDate() > oldAttendTime
							.getDate()) {
						main.setFdIsAcross(true);
					}
					main.setDocCreateTime(mainExc.getFdAttendTime());
					main.setFdOutside(false);
					// 用户打卡时间恢复为上下班时间(缺卡,迟到,早退)
					Integer oldStatus = main.getFdStatus();
					if (Integer.valueOf(0).equals(oldStatus)
							|| Integer.valueOf(2).equals(oldStatus)
							|| Integer.valueOf(3).equals(oldStatus)) {
						List signList = sysAttendCategoryService.getAttendSignTimes(CategoryUtil.getFdCategoryInfo(main),oldAttendTime, main.getDocCreator());
						SysAttendCategoryWorktime workTime = this.sysAttendCategoryService
								.getWorkTimeByRecord(signList,
										main.getDocCreateTime(),
										main.getFdWorkType());
						if (workTime != null) {
							Date _signTime = workTime.getFdStartTime();
							if (Integer.valueOf(1)
									.equals(main.getFdWorkType())) {
								_signTime = workTime.getFdEndTime();
							}
							Date newAttendTime = AttendUtil
									.joinYMDandHMS(oldAttendTime, _signTime);
							// 允许用户提交异常时更新时间
							if (Integer.valueOf(1)
									.equals(main.getFdWorkType())) {
								if (mainExc.getFdAttendTime()
										.after(newAttendTime)) {
									newAttendTime = mainExc.getFdAttendTime();
								}
							} else {
								if (mainExc.getFdAttendTime()
										.before(newAttendTime)) {
									newAttendTime = mainExc.getFdAttendTime();
								}
							}
							main.setDocCreateTime(newAttendTime);
							logger.warn(
									"用户考勤异常状态置为正常操作通过!打卡时间调整为:" + oldAttendTime
											+ "--->" + main.getDocCreateTime()
											+ ";userName:"
											+ main.getDocCreator());
						} else {
							logger.warn(
									"用户考勤异常状态置为正常操作通过!获取用户班次打卡时间为空,打卡时间调整为:"
											+ oldAttendTime
											+ "--->" + main.getDocCreateTime()
											+ ";userName:"
											+ main.getDocCreator());
						}
					}
					main.setFdAppName(ResourceUtil.getString("sysAttendMain.fdAppName.ekp","sys-attend"));
					statAttend =true;
				}
				getBaseDao().update(mainExc);
				if(Boolean.TRUE.equals(statAttend)) {
					ISysAttendSynDingDao sysAttendSynDingDao = (ISysAttendSynDingDao) SpringBeanUtil
							.getBean("sysAttendSynDingDao");
					sysAttendSynDingDao.addRecord(mainExc.getFdAttendMain());
					multicaster.attatchEvent(
							new EventOfTransactionCommit(StringUtils.EMPTY),
							new IEventCallBack() {
								@Override
								public void execute(ApplicationEvent arg0)
										throws Throwable {
									sysAttendStatJobService.stat(
											main.getDocCreator(),
											main.getDocCreateTime(),null);
								}
							});
				}
			} catch (Exception e) {
				throw new KmssRuntimeException(e);
			}
		}
	}

	@Override
	public void passByAdmin(String id) throws Exception {
		LbpmProcess process = (LbpmProcess) lbpmProcessService
				.findByPrimaryKey(id);
		if (process == null
				|| !ProcessInstance.ACTIVATED.equals(process.getFdStatus())) {
			throw new Exception("流程已废弃");
		}
		List<LbpmNode> nodes = process.getFdNodes();
		LbpmNode fdTask = null;
		if (fdTask == null) {
			fdTask = nodes.get(0);
		}
		JSONObject params = new JSONObject();
		params.put("auditNote", "");
		params.put("notifyType", "todo");
		// 操作参数
		OperationParameters operParameters = new OperationParameters(id,
				"admin_pass",
				fdTask.getFdNodeType(),
				fdTask.getFdId(),
				params.toString());
		// 运行参数
		ExecuteParameters exeParameters = new ExecuteParameters(
				process.getFdModelName(), id);
		processExecuteService.execute(operParameters, exeParameters, null);
	}

	@Override
	public Map<String, Integer> getNumByPerson(List<String> personIds,Date fdEndDate) throws Exception {
		if(personIds==null||personIds.size()<=0){
			return null;
		}
		 Map<String, Integer> result=new HashMap<>();
		 String inSQL=HQLUtil.buildLogicIN("doc_creator_id", personIds);
		 Date startDate=DateUtil.getBeginDayOfMonthByDate(fdEndDate);
		 String sql="select c.doc_creator_id,sum(num) from (SELECT doc_creator_id,if(ifnull(count(*),0)=2,1,ifnull(count(*),0))num FROM sys_attend_main_exc WHERE  "+ inSQL 
		 		+ " and fd_status=2 and fd_attend_time>=:startDate and fd_attend_time<=:fdEndDate GROUP BY doc_creator_id ,DATE(fd_attend_time),fd_desc having fd_desc='忘带工牌'"
		 		+ " union all SELECT doc_creator_id,if(ifnull(count(*),0)=2,1,ifnull(count(*),0))num FROM sys_attend_main_exc WHERE  "+ inSQL 
		 		+ " and fd_status=2 and fd_attend_time>=:startDate and fd_attend_time<=:fdEndDate GROUP BY doc_creator_id ,DATE(fd_attend_time),fd_desc having fd_desc='工牌丢失'"
		 		+ " union all SELECT doc_creator_id,ifnull(count(*),0)num FROM sys_attend_main_exc WHERE  "+ inSQL 
		 		+ " and fd_status=2 and fd_attend_time>=:startDate and fd_attend_time<=:fdEndDate GROUP BY doc_creator_id ,DATE(fd_attend_time),fd_desc having fd_desc='忘记打卡')c group by c.doc_creator_id";
		 DateUtil.getDate(0);
		 System.out.println(sql);
		 Query query= this.getBaseDao().getHibernateSession().createSQLQuery(sql);
		 Date endDate = AttendUtil.getDate(fdEndDate,1);
		 query.setDate("startDate", startDate);
		 query.setDate("fdEndDate", endDate);
		 List<Object[]> list=query.list();
		 for (Object[] strs : list) {
			 BigDecimal countNum=(BigDecimal)strs[1];
			 if(countNum!=null)
			 result.put((String)strs[0], countNum.intValue());
		}
		return result;
	}
}
