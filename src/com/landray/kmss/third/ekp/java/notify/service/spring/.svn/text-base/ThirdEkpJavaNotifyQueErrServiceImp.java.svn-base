package com.landray.kmss.third.ekp.java.notify.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.notify.EkpNotifyJavaTodoProviderEkpj;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoClearContext;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoSendContext;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoUpdateContext;
import com.landray.kmss.third.ekp.java.notify.dao.IThirdEkpJavaNotifyQueErrDao;
import com.landray.kmss.third.ekp.java.notify.interfaces.EkpNotifyJavaTodoConstant;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyQueErr;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyLogService;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyQueErrService;
import com.landray.kmss.third.ekp.java.notify.util.ThirdEkpJavaNotifyUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.sunbor.web.tag.Page;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdEkpJavaNotifyQueErrServiceImp extends ExtendDataServiceImp
		implements IThirdEkpJavaNotifyQueErrService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdEkpJavaNotifyQueErrServiceImp.class);

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private EkpNotifyJavaTodoProviderEkpj ekpNotifyJavaTodoProviderEkpj;

	private ISysOrgPersonService sysOrgPersonService;

	@Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model,
											ConvertorContext context) throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof ThirdEkpJavaNotifyQueErr) {
			ThirdEkpJavaNotifyQueErr thirdEkpJavaNotifyQueErr = (ThirdEkpJavaNotifyQueErr) model;
			thirdEkpJavaNotifyQueErr.setDocAlterTime(new Date());
		}
		return model;
	}

	@Override
	public IBaseModel initBizModelSetting(RequestContext requestContext)
			throws Exception {
		ThirdEkpJavaNotifyQueErr thirdEkpJavaNotifyQueErr = new ThirdEkpJavaNotifyQueErr();
		thirdEkpJavaNotifyQueErr.setDocCreateTime(new Date());
		thirdEkpJavaNotifyQueErr.setDocAlterTime(new Date());
		ThirdEkpJavaNotifyUtil.initModelFromRequest(thirdEkpJavaNotifyQueErr,
				requestContext);
		return thirdEkpJavaNotifyQueErr;
	}

	@Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model,
										   RequestContext requestContext) throws Exception {
		ThirdEkpJavaNotifyQueErr thirdEkpJavaNotifyQueErr = (ThirdEkpJavaNotifyQueErr) model;
	}

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
					.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	private boolean runErrorQueueLocked = false;

	@Override
	public void updateRunErrorQueue(SysQuartzJobContext jobContext) {
		if (runErrorQueueLocked) {
            return;
        }
		runErrorQueueLocked = true;
		try {
			getEkpNotifyJavaTodoProviderEkpj().clearNotifyIds();

			HQLInfo countHql = new HQLInfo();
			countHql.setWhereBlock(
					"thirdEkpJavaNotifyQueErr.fdFlag=1 and thirdEkpJavaNotifyQueErr.fdRepeatHandle<"
							+ EkpNotifyJavaTodoConstant.NOTIFY_ERROR_REPEAT);
			countHql.setGettingCount(true);
			List<Object> resultList = findValue(countHql);
			Object result = resultList.get(0);
			long count = Long
					.parseLong(result != null ? result.toString() : "0");
			logger.warn("【third-ekp-java】消息发送出错重复执行开始,出错队列消息条数为:" + count);
			if (count == 0) {
				return;
			}
			int groupCount = 100;
			int len = (int) count / groupCount;
			for (int k = 0; k <= len; k++) {
				Page page = this.findPage(
						"thirdEkpJavaNotifyQueErr.fdFlag=1 and thirdEkpJavaNotifyQueErr.fdRepeatHandle<"
								+ EkpNotifyJavaTodoConstant.NOTIFY_ERROR_REPEAT,
						"thirdEkpJavaNotifyQueErr.docCreateTime", 0,
						groupCount);
				List errorList = page.getList();
				if (errorList.isEmpty()) {
					logger.debug("【third-ekp-java】消息发送出错重复执行完成:出错队列消息为空");
					return;
				}
				for (int i = 0; i < errorList.size(); i++) {
					ThirdEkpJavaNotifyQueErr queueError = (ThirdEkpJavaNotifyQueErr) errorList
							.get(i);
					int fdRepeatHandle = queueError.getFdRepeatHandle();
					if (fdRepeatHandle >= 5) {
						continue;
					}
					// 更新
					Integer handle = queueError.getFdRepeatHandle();
					handle = handle + 1;
					queueError.setFdRepeatHandle(handle);
					queueError.setDocAlterTime(new Date());
					try {
						// 重新发送
						resend(queueError);
						queueError.setFdFlag(
								EkpNotifyJavaTodoConstant.NOTIFY_ERROR_FDFLAG_SEND);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					} finally {
						this.update(queueError);
					}
				}
			}
			logger.debug("【third-ekp-java】消息发送出错重复执行成功！");
		} catch (Exception e) {
			logger.error("【third-ekp-java】消息发送出错重复执行失败！", e);
		} finally {
			runErrorQueueLocked = false;
		}
	}

	private void resend(ThirdEkpJavaNotifyQueErr queueError) throws Exception {
		int method = queueError.getFdMethod();
		JSONObject json = JSONObject.fromObject(queueError.getFdData());
		Object context = null;
		switch (method) {
		case EkpNotifyJavaTodoConstant.METHOD_ADD:
			context = JSONObject.toBean(json, NotifyTodoSendContext.class);
			break;
		case EkpNotifyJavaTodoConstant.METHOD_REMOVE:
			context = JSONObject.toBean(json, NotifyTodoClearContext.class);
			break;
		case EkpNotifyJavaTodoConstant.METHOD_CLEARTODOPERSONS:
			context = JSONObject.toBean(json, NotifyTodoClearContext.class);
			break;
		case EkpNotifyJavaTodoConstant.METHOD_REMOVEDONEPERSON:
			context = JSONObject.toBean(json, NotifyTodoClearContext.class);
			break;
		case EkpNotifyJavaTodoConstant.METHOD_SETPERSONSDONE:
			context = JSONObject.toBean(json, NotifyTodoClearContext.class);
			break;
		case EkpNotifyJavaTodoConstant.METHOD_SETTODODONE:
			context = JSONObject.toBean(json, NotifyTodoClearContext.class);
			break;
		case EkpNotifyJavaTodoConstant.METHOD_UPDATETODO:
			context = JSONObject.toBean(json, NotifyTodoUpdateContext.class);
			break;
		}
		getEkpNotifyJavaTodoProviderEkpj().invoke(method, context,
				queueError.getDocSubject());

	}

	@Override
	public void clearNotifyQueueError(SysQuartzJobContext jobContext) {
		try {
			((IThirdEkpJavaNotifyQueErrDao) getBaseDao()).clear(60);
			IThirdEkpJavaNotifyLogService thirdEkpJavaNotifyLogService = (IThirdEkpJavaNotifyLogService) SpringBeanUtil
					.getBean("thirdEkpJavaNotifyLogService");
			thirdEkpJavaNotifyLogService.clear(60);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【third-ekp-java】错误队列清理失败:" + e.getMessage(), e);
		}

	}



	public EkpNotifyJavaTodoProviderEkpj getEkpNotifyJavaTodoProviderEkpj() {
		if (ekpNotifyJavaTodoProviderEkpj == null) {
			ekpNotifyJavaTodoProviderEkpj = (EkpNotifyJavaTodoProviderEkpj) SpringBeanUtil
					.getBean("ekpNotifyJavaTodoProviderEkpj");
		}
		return ekpNotifyJavaTodoProviderEkpj;
	}

	public static String buildInBlock(String[] ids) {
		String idStr = "";
		for (String fdId : ids) {
			idStr += "'" + fdId + "',";
		}
		return idStr.substring(0, idStr.length() - 1);
	}

	@Override
	public void updateResend(String[] ids) throws Exception {
		int loop = 0;
		while (runErrorQueueLocked && loop < 6) {
			loop++;
			Thread.sleep(500);
		}
		if (loop >= 6) {
			throw new Exception("已经有重发任务在执行");
		}
		runErrorQueueLocked = true;
		try {
			List errorList = this.findList(
					"thirdEkpJavaNotifyQueErr.fdFlag=1 and thirdEkpJavaNotifyQueErr.fdId in ("
							+ buildInBlock(ids) + ")",
					"thirdEkpJavaNotifyQueErr.docCreateTime asc");
			if (errorList.isEmpty()) {
				logger.debug("【third-ekp-java】消息发送出错重复执行完成:出错队列消息为空");
				return;
			}
			for (int i = 0; i < errorList.size(); i++) {
				ThirdEkpJavaNotifyQueErr queueError = (ThirdEkpJavaNotifyQueErr) errorList
						.get(i);
				// 更新
				Integer handle = queueError.getFdRepeatHandle();
				handle = handle + 1;
				queueError.setFdRepeatHandle(handle);
				queueError.setDocAlterTime(new Date());
				try {
					// 重新发送
					resend(queueError);
					queueError.setFdFlag(
							EkpNotifyJavaTodoConstant.NOTIFY_ERROR_FDFLAG_SEND);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				} finally {
					this.update(queueError);
				}
			}
			logger.debug("【third-ekp-java】消息发送出错重复执行成功！");
		} catch (Exception e) {
			throw e;
		} finally {
			runErrorQueueLocked = false;
		}
	}

	long count = 0L;

	public boolean isSynchroNotifyEnable() throws Exception {
		EkpJavaConfig config = new EkpJavaConfig();
		String synchroNotify = config
				.getValue("kmss.notify.todoExtend.java.enabled");
		if ("true".equals(synchroNotify)) {
			return true;
		}
		return false;
	}

	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil
					.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}

	@Override
	public void synchroError(SysQuartzJobContext context) {

		updateRunErrorQueue(context);

		String temp = "";
		if (runErrorQueueLocked) {
			temp = "异常任务同步已经在运行，当前任务中断...";
			logger.warn(temp);
			return;
		}
		runErrorQueueLocked = true;
		try {
			clear(null);
		} catch (Exception ex) {
			logger.error(ex.toString());
		} finally {
			count = 0L;
			runErrorQueueLocked = false;
		}
	}

	@Override
	public void clear(String personIds) throws Exception {
		List<SysOrgPerson> list = null;
		if (StringUtil.isNull(personIds)) {
			TransactionStatus status = null;
			try {
				status = TransactionUtils
						.beginNewTransaction();
				updateRunErrorQueue(null);
				TransactionUtils.getTransactionManager().commit(status);
			} catch (Exception e) {
				if (status != null) {
					TransactionUtils.getTransactionManager()
							.rollback(status);
				}
			}
		} else {
			String[] ids = personIds.split(";");
			list = getSysOrgPersonService().findByPrimaryKeys(ids);
		}
		long start = System.currentTimeMillis();
		int loop = 0;
		int pageno = 1;
		int pageCount = 1;
		while (loop < 1000) {
			loop++;
			if (pageno <= pageCount) {
				JSONObject todosObj = getEkpNotifyJavaTodoProviderEkpj()
						.getAllTodo(13, pageno, 1000, list);
				logger.warn("处理第" + pageno + "批待办数据");
				JSONArray docs = todosObj.getJSONArray("docs");
				if (docs.size() == 0) {
					break;
				}
				updateNotifys(docs);
				pageno = todosObj.getInt("pageno");
				pageCount = todosObj.getInt("pageCount");
				pageno++;
			} else {
				break;
			}
		}
		String temp = "整个任务总耗时(秒)："
				+ (System.currentTimeMillis() - start) / 1000;
		logger.warn(temp);
	}

	private void updateNotifys(JSONArray docs) {
		for (int i = 0; i < docs.size(); i++) {
			JSONObject o = docs.getJSONObject(i);
			String id = o.getString("id");
			String subject = o.getString("subject");
			// Integer type = o.getInt("type");
			// String key = o.getString("key");
			// String modelName = o.getString("modelName");
			// String modelId = o.getString("modelId");
			// String createTime = o.getString("createTime");
			String targets = o.getString("targets");
			String link = o.getString("link");
			if (StringUtil.isNull(targets)) {
				logger.warn("待办成员为空，不处理。id:" + id + ",subject:" + subject);
				continue;
			}
			if (StringUtil.isNull(link)) {
				logger.warn("链接为空，不处理。id:" + id + ",subject:" + subject);
				continue;
			}
			if (!link.contains(
					"/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view")) {
				logger.warn("链接格式不对，不处理。id:" + id + ",subject:" + subject
						+ ",link:" + link);
				continue;
			}
			String urlPrefix = ResourceUtil
					.getKmssConfigString("kmss.urlPrefix");
			if (!link.startsWith(
					urlPrefix)) {
				logger.warn("非本系统的待办，不处理。id:" + id + ",subject:" + subject
						+ ",link:" + link);
				continue;
			}
			String todoId = StringUtil.getParameter(link, "fdId");
			SysNotifyTodo todo = null;
			try {
				todo = (SysNotifyTodo) getSysNotifyTodoService()
						.findByPrimaryKey(todoId, null, true);
			} catch (Exception e) {
				logger.error(e.toString());
			}
			if (todo == null || "1".equals(todo.getFdDeleteFlag())) {
				logger.warn("EKP中待办找不到待办" + todoId + "," + subject + ",执行删除操作");
				TransactionStatus status = null;
				try {
					status = TransactionUtils
							.beginNewTransaction();
					getEkpNotifyJavaTodoProviderEkpj().remove(id, subject);
					TransactionUtils.getTransactionManager().commit(status);
					logger.info("删除成功");
				} catch (Exception e) {
					if (status != null) {
						TransactionUtils.getTransactionManager()
								.rollback(status);
					}
					logger.error("删除失败", e);
				}
				continue;
			}
			if (todo.getHbmTodoTargets() == null
					|| todo.getHbmTodoTargets().size() == 0) {
				logger.warn("EKP中待办成员为空(" + todoId + "," + subject
						+ ")，直接置为已办");
				TransactionStatus status = null;
				try {
					status = TransactionUtils
							.beginNewTransaction();
					getEkpNotifyJavaTodoProviderEkpj().setTodoDone(id,
							subject);
					TransactionUtils.getTransactionManager().commit(status);
					logger.info("置为已办成功");
				} catch (Exception e) {
					if (status != null) {
						TransactionUtils.getTransactionManager()
								.rollback(status);
					}
					logger.error("置为已办失败", e);
				}
			} else {
				String[] targetArray = targets.split(";");
				Set<String> loginNames_remote = new HashSet<String>();
				for (String loginName : targetArray) {
					loginNames_remote.add(loginName);
				}
				Set<String> loginNames_this = new HashSet<String>();
				List<SysOrgElement> todoTargets = todo.getHbmTodoTargets();
				for (SysOrgElement ele : todoTargets) {
					if (ele instanceof SysOrgPerson) {
						SysOrgPerson person = (SysOrgPerson) ele;
						String loginName = person.getFdLoginName();
						loginNames_this.add(loginName);
					} else {
						logger.error("待办成员不是用户，待办ID：" + todo.getFdId()
								+ ",成员：" + ele.getFdId());
					}
				}
				loginNames_remote.removeAll(loginNames_this);
				if (loginNames_remote.size() > 0) {
					TransactionStatus status = null;
					try {
						status = TransactionUtils
								.beginNewTransaction();
						JSONArray array = new JSONArray();
						for (String loginName : loginNames_remote) {
							JSONObject jsonObj = new JSONObject();
							jsonObj.accumulate("LoginName", loginName);
							array.element(jsonObj);
						}
						logger.warn("部分成员置为已办(" + todoId + "," + subject
								+ ")，" + array.toString());
						getEkpNotifyJavaTodoProviderEkpj().setPersonsDone(
								id,
								subject, array.toString());
						TransactionUtils.getTransactionManager().commit(status);
						logger.info("置为已办成功");
					} catch (Exception e) {
						if (status != null) {
							TransactionUtils.getTransactionManager()
									.rollback(status);
						}
						logger.error("置为已办失败", e);
					}
				}
			}
		}
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	@Override
	public void resend(String personIds) throws Exception {
		List<SysOrgPerson> list = null;
		if (StringUtil.isNull(personIds)) {
			TransactionStatus status = null;
			try {
				status = TransactionUtils
						.beginNewTransaction();
				updateRunErrorQueue(null);
				TransactionUtils.getTransactionManager().commit(status);
			} catch (Exception e) {
				if (status != null) {
					TransactionUtils.getTransactionManager()
							.rollback(status);
				}
			}
		} else {
			String[] ids = personIds.split(";");
			list = getSysOrgPersonService().findByPrimaryKeys(ids);
		}
		long start = System.currentTimeMillis();

		String todoIds = getEkpNotifyJavaTodoProviderEkpj()
				.getAllTodoId(13, list);


		Set<String> todoIdsSet_remote = new HashSet<String>();
		if (StringUtil.isNotNull(todoIds)) {
			String[] todoIdArray = todoIds.split(";");
			for (String todoId : todoIdArray) {
				todoIdsSet_remote.add(todoId);
			}
		}

		List<String> todoIds_this = getTodoIds(personIds);
		System.out.println("todoIds_this:" + todoIds_this);
		System.out.println("todoIdsSet_remote:" + todoIdsSet_remote);
		todoIds_this.removeAll(todoIdsSet_remote);
		System.out.println("todoIds_this:" + todoIds_this);
		for (String todoId : todoIds_this) {
			TransactionStatus status = null;
			try {
				status = TransactionUtils
						.beginNewTransaction();
				getEkpNotifyJavaTodoProviderEkpj().addTodo(todoId);
				TransactionUtils.getTransactionManager().commit(status);
			} catch (Exception e) {
				if (status != null) {
					TransactionUtils.getTransactionManager()
							.rollback(status);
				}
			}
		}
		String temp = "整个任务总耗时(秒)："
				+ (System.currentTimeMillis() - start) / 1000;
		logger.warn(temp);
	}

	private List<String> getTodoIds(String personIds) throws Exception {
		String whereBlock = "sysNotifyTodo.fdDeleteFlag is null";
		HQLInfo info = new HQLInfo();

		List<Integer> _typeList = new ArrayList<Integer>();
		_typeList.add(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		_typeList.add(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL_SUSPEND);
		whereBlock += " and "
				+ HQLUtil.buildLogicIN("sysNotifyTodo.fdType ",
						_typeList);

		List targetsList = new ArrayList();
		if (StringUtil.isNotNull(personIds)) {
			String[] personIdArray = personIds.split(";");
			targetsList = Arrays.asList(personIdArray);
		}
		if (targetsList != null && !targetsList.isEmpty()) {
			whereBlock += " and "
					+ HQLUtil.buildLogicIN(
							"sysNotifyTodo.hbmTodoTargets.fdId ", targetsList);
		} else {
			info.setJoinBlock(
					"inner join sysNotifyTodo.hbmTodoTargets targets");
		}
		info.setSelectBlock("distinct sysNotifyTodo.fdId");
		info.setWhereBlock(whereBlock);

		List list = getSysNotifyTodoService().findValue(info);
		return list;
	}

}
