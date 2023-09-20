package com.landray.kmss.third.ding.notify.queue.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyHandleTaskModel;
import com.landray.kmss.third.ding.notify.provider.ThirdDingTodoProvider;
import com.landray.kmss.third.ding.notify.provider.ThirdDingTodoTaskProvider;
import com.landray.kmss.third.ding.notify.provider.ThirdDingWorkrecordProvider;
import com.landray.kmss.third.ding.notify.queue.constant.ThirdDingNotifyQueueErrorConstants;
import com.landray.kmss.third.ding.notify.queue.dao.IThirdDingNotifyQueueErrorDao;
import com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError;
import com.landray.kmss.third.ding.notify.queue.service.IThirdDingNotifyQueueErrorService;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyLogService;
import com.landray.kmss.third.ding.service.IThirdDingCallbackLogService;
import com.landray.kmss.third.ding.service.IThirdDingCardLogService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;

public class ThirdDingNotifyQueueErrorServiceImp extends ExtendDataServiceImp
		implements IThirdDingNotifyQueueErrorService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingNotifyQueueErrorServiceImp.class);

	private static boolean runErrorQueueLocked = false;

	private ThirdDingWorkrecordProvider workrecordProvider;

	public ThirdDingWorkrecordProvider getWorkrecordProvider() {
		if (workrecordProvider == null) {
			workrecordProvider = (ThirdDingWorkrecordProvider) SpringBeanUtil
					.getBean("thirdDingWorkrecordProvider");
		}
		return workrecordProvider;
	}

	private ThirdDingTodoTaskProvider thirdDingTodoTaskProvider;

	public ThirdDingTodoTaskProvider getThirdDingTodoTaskProvider() {
		if (thirdDingTodoTaskProvider == null) {
			thirdDingTodoTaskProvider = (ThirdDingTodoTaskProvider) SpringBeanUtil
					.getBean("thirdDingTodoTaskProvider");
		}
		return thirdDingTodoTaskProvider;
	}


	private ThirdDingTodoProvider messageProvider;

	public ThirdDingTodoProvider getMessageProvider() {
		if (messageProvider == null) {
			messageProvider = (ThirdDingTodoProvider) SpringBeanUtil
					.getBean("thirdDingTodoProvider");
		}
		return messageProvider;
	}

	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil
					.getBean("componentLockService");
		}
		return componentLockService;
	}

	private IThirdDingNotifyQueueErrorService thirdDingNotifyQueueErrorService;

	public IThirdDingNotifyQueueErrorService
	getThirdDingNotifyQueueErrorService() {
		if (thirdDingNotifyQueueErrorService == null) {
			thirdDingNotifyQueueErrorService = (IThirdDingNotifyQueueErrorService) SpringBeanUtil
					.getBean("thirdDingNotifyQueueErrorService");
		}
		return thirdDingNotifyQueueErrorService;
	}

	@Override
	public void updateRunErrorQueue(SysQuartzJobContext jobContext) {
		ThirdDingNotifyHandleTaskModel handleModel = new ThirdDingNotifyHandleTaskModel();
		try {
			getComponentLockService().tryLock(handleModel, "notify", 3600000L);
			if (runErrorQueueLocked) {
				String temp = "异常任务同步  或者 待办推送失败消息重发 已经在运行，当前任务中断...";
				logger.warn(temp);
				jobContext.logMessage(temp);
				return;
			}
			runErrorQueueLocked = true;
			HQLInfo countHql = new HQLInfo();
			countHql.setWhereBlock(
					"thirdDingNotifyQueueError.fdFlag='1' and thirdDingNotifyQueueError.fdRepeatHandle>0");
			countHql.setGettingCount(true);
			List<Object> resultList = findValue(countHql);
			Object result = resultList.get(0);
			long count = Long
					.parseLong(result != null ? result.toString() : "0");
			logger.warn("【third-ding】消息发送出错重复执行开始,出错队列消息条数为:" + count);
			if (count == 0) {
				return;
			}
			int groupCount = 100;
			int len = (int) count / groupCount;
			for (int k = 0; k <= len; k++) {
				Page page = this.findPage(
						"thirdDingNotifyQueueError.fdFlag='1' and thirdDingNotifyQueueError.fdRepeatHandle>0",
						"thirdDingNotifyQueueError.fdCreateTime", 0,
						groupCount);
				List errorList = page.getList();
				if (errorList.isEmpty()) {
					logger.debug("【third-ding】消息发送出错重复执行完成:出错队列消息为空");
					return;
				}
				for (int i = 0; i < errorList.size(); i++) {
					ThirdDingNotifyQueueError queueError = (ThirdDingNotifyQueueError) errorList
							.get(i);
					int fdRepeatHandle = queueError.getFdRepeatHandle();
					if (fdRepeatHandle == 0) {
						continue;
					}
					// 更新
					Integer handle = queueError.getFdRepeatHandle();
					handle = handle - 1;
					queueError.setFdRepeatHandle(handle);
					queueError.setFdSendTime(new Date());
					try {
						// 重新发送
						resend(queueError);
						queueError.setFdFlag(
								ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_SEND);
					} catch (Exception e) {
						logger.error("", e);
						if ("找不到对应的record记录".equals(e.getMessage())) {
							queueError.setFdRepeatHandle(0);
						}
						queueError.setFdErrorMsg(e.getMessage());
					} finally {
						this.update(queueError);
					}
				}
			}
			logger.debug("【third-ding】消息发送出错重复执行成功！");
			getComponentLockService().unLock(handleModel);
		} catch (ConcurrencyException e) {
			String temp = "异常任务同步  或者 待办推送失败消息重发 已经在运行，当前任务中断...";
			logger.warn(temp);
			jobContext.logMessage(temp);
		} catch (Exception e) {
			logger.error("【third-ding】消息发送出错重复执行失败！", e);
			getComponentLockService().unLock(handleModel);
		} finally {
			runErrorQueueLocked = false;
		}
	}

	private void resend(ThirdDingNotifyQueueError queueError) throws Exception {
		// System.out.println("11:" + queueError.getFdJson());
		JSONObject json = null;
		if(StringUtil.isNotNull(queueError.getFdJson())){
			json = JSONObject.fromObject(queueError.getFdJson());
		}
		String method = queueError.getFdMethod();
		if ("add".equals(method)) {
			getWorkrecordProvider().add(json, queueError.getFdUser() == null ? null
					: queueError.getFdUser().getFdId());
		} else if ("update".equals(method)) {
			getWorkrecordProvider().update(json, queueError.getFdUser() == null ? null
					: queueError.getFdUser().getFdId());
		}else if("asyncsend_v2".equals(method)){
			getMessageProvider().sendNotify(com.alibaba.fastjson.JSONObject
					.parseObject(queueError.getFdJson()));
		}else if((ThirdDingTodoTaskProvider.PREFIX_API+"add").equals(method)){
			logger.warn("-----新待办新增重发----");
			getThirdDingTodoTaskProvider().add(json,queueError.getFdDingUserId(),queueError.getFdTodoId(),queueError.getFdUser().getFdId(),queueError.getFdSubject());
		}else if((ThirdDingTodoTaskProvider.PREFIX_API+"update").equals(method)){
			logger.warn("-----新待办更新待办----");
			getThirdDingTodoTaskProvider().update(json,queueError.getFdDingUserId(),queueError.getFdTodoId(),queueError.getFdUser().getFdId(),queueError.getFdSubject());
		}else if((ThirdDingTodoTaskProvider.PREFIX_API+"delete").equals(method)){
			logger.warn("-----新待办删除待办----");
			getThirdDingTodoTaskProvider().deleteTask(queueError.getFdDingUserId(),queueError.getFdTodoId());
		}else if((ThirdDingTodoTaskProvider.PREFIX_API+"update_executorStatus").equals(method)){
			logger.warn("-----新待办 更新部分人员----");
			getThirdDingTodoTaskProvider().updateExecutorStatus(json,queueError.getFdDingUserId(),queueError.getFdTodoId(),queueError.getFdUser().getFdId(),queueError.getFdSubject());
		}else if("messageUpdate".equals(method)){
			getMessageProvider().updateMessage(com.alibaba.fastjson.JSONObject
					.parseObject(queueError.getFdJson()),queueError.getFdDeleteMapping());
		}else if("asyncsend_v2_status".equals(method)){
			getMessageProvider().sendNotifyStatus(com.alibaba.fastjson.JSONObject
					.parseObject(queueError.getFdJson()));
		}
	}


	@Override
	public void clearNotifyQueueError(SysQuartzJobContext jobContext) {
		try {
			((IThirdDingNotifyQueueErrorDao) getBaseDao()).clear(60);
			//清理待办日志
			IThirdDingNotifyLogService thirdDingNotifyLogService = (IThirdDingNotifyLogService) SpringBeanUtil
					.getBean("thirdDingNotifyLogService");
			thirdDingNotifyLogService.clear(DingConfig.newInstance().getDingLogDaysParseInt());
			//清理回调日志
			IThirdDingCallbackLogService thirdDingCallbackLogService = (IThirdDingCallbackLogService) SpringBeanUtil
					.getBean("thirdDingCallbackLogService");
			thirdDingCallbackLogService.clear(DingConfig.newInstance().getDingLogDaysParseInt());

			//清理卡片日志
			IThirdDingCardLogService thirdDingCardLogService = (IThirdDingCardLogService) SpringBeanUtil
					.getBean("thirdDingCardLogService");
			thirdDingCardLogService.clear(DingConfig.newInstance().getDingLogDaysParseInt());

		} catch (Exception e) {
			logger.error("【third-ding】错误队列清理失败:" + e.getMessage(), e);
		}

	}

	public static String buildInBlock(String[] ids) {
		String idStr = "";
		for (String fdId : ids) {
			idStr += "'" + fdId + "',";
		}
		return idStr.substring(0, idStr.length() - 1);
	}

	private boolean getLock(ThirdDingNotifyHandleTaskModel handleModel) {
		try {
			getComponentLockService().tryLock(handleModel, "notify", 3600000L);
			return true;
		} catch (ConcurrencyException e) {
			return false;
		} catch (Exception e) {
			getComponentLockService().unLock(handleModel);
		}
		return false;
	}

	@Override
	public void updateResend(String[] ids) throws Exception {
		int loop = 0;
		ThirdDingNotifyHandleTaskModel handleModel = new ThirdDingNotifyHandleTaskModel();

		while (loop < 6 && !getLock(handleModel)) {
			loop++;
			Thread.sleep(500);
		}
		if (loop >= 6) {
			throw new Exception("已经有重发任务在执行");
		}
		try {
			if (runErrorQueueLocked) {
				throw new Exception("已经有重发任务在执行");
			}
			runErrorQueueLocked = true;
			List errorList = this.findList(
					"thirdDingNotifyQueueError.fdFlag='1' and thirdDingNotifyQueueError.fdId in ("
							+ buildInBlock(ids) + ")",
					"thirdDingNotifyQueueError.fdCreateTime asc");
			if (errorList.isEmpty()) {
				logger.debug("【third-ding】消息发送出错重复执行完成:出错队列消息为空");
				return;
			}
			for (int i = 0; i < errorList.size(); i++) {
				ThirdDingNotifyQueueError queueError = (ThirdDingNotifyQueueError) errorList
						.get(i);
				// 更新
				Integer handle = queueError.getFdRepeatHandle();
				handle = handle - 1;
				queueError.setFdRepeatHandle(handle);
				queueError.setFdSendTime(new Date());
				try {
					// 重新发送
					resend(queueError);
					queueError.setFdFlag(
							ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_SEND);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					queueError.setFdErrorMsg(e.getMessage());
				} finally {
					this.update(queueError);
				}
			}
			logger.debug("【third-ding】消息发送出错重复执行成功！");
			getComponentLockService().unLock(handleModel);
		} catch (ConcurrencyException e) {
			throw e;
		} catch (Exception e) {
			getComponentLockService().unLock(handleModel);
			throw e;
		} finally {
			runErrorQueueLocked = false;
		}
	}

	@Override
	public String updateResendByCleaningTool(JSONArray data) {
		ThirdDingNotifyHandleTaskModel handleModel = new ThirdDingNotifyHandleTaskModel();
		String errMsg = null;
		try {
			getComponentLockService().tryLock(handleModel, "notify", 3600000L);
			if (runErrorQueueLocked) {
				String temp = "【注意】后台有重发任务在处理，请稍后再试！！！";
				logger.warn(temp);
				return errMsg;
			}
			runErrorQueueLocked = true;
			logger.warn("【手动清理待办数据】:" + data);
			if (data == null || data.isEmpty()) {
                return "重发的数据为空";
            }

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdTodoId=:fdTodoId and fdDingUserId=:fdDingUserId");

			for (int i = 0; i < data.size(); i++) {
				JSONObject curData = data.getJSONObject(i);
				//取到当前选择得待办版本
				String apiType = DingConfig.newInstance().getNotifyApiType();
				logger.warn("钉钉待办接口类型:{}",apiType);
				boolean flag =false;
				if("WR".equals(apiType)  ){
					 flag = getWorkrecordProvider().addTodoByTool(curData);
				}else if("TODO".equals(apiType)){
					flag = getThirdDingTodoTaskProvider().addTodoByTool(curData);
				}
				if (flag) {
					hqlInfo.setParameter("fdTodoId",
							curData.getString("notifyFdId"));
					if("WR".equals(apiType)  ){
						hqlInfo.setParameter("fdDingUserId",
								curData.getString("ids").split(";")[0]);
					}else {
						hqlInfo.setParameter("fdDingUserId",
								DingUtil.getIdUnionidByDingId(curData.getString("ids").split(";")[0]));
					}

					ThirdDingNotifyQueueError queueError = (ThirdDingNotifyQueueError) this
							.findFirstOne(hqlInfo);
					if (queueError != null) {
						queueError.setFdFlag(
								ThirdDingNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_SEND);
						this.update(queueError);
					}
				} else {
					if (StringUtil.isNull(errMsg)) {
						errMsg = "【注意】有重发失败的待办：\n" + curData.getString("title");
					} else {
						errMsg += "\n" + curData.getString("title");
					}
				}
			}
			getComponentLockService().unLock(handleModel);
		} catch (ConcurrencyException e) {
			String temp = "【注意】后台有重发任务在处理，请稍后再试！！！";
			logger.warn(temp);
		} catch (Exception e) {
			errMsg = e.getMessage();
			logger.error(e.getMessage(), e);
			getComponentLockService().unLock(handleModel);
		} finally {
			runErrorQueueLocked = false;
		}
		return errMsg;
	}

}
