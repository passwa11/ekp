package com.landray.kmss.third.ekp.java.notify.queue.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ekp.java.notify.EkpNotifyJavaTodoProviderSimple;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoRemoveContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoSendContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoUpdateContext;
import com.landray.kmss.third.ekp.java.notify.queue.constant.ThirdEkpNotifyQueueErrorConstants;
import com.landray.kmss.third.ekp.java.notify.queue.model.ThirdEkpNotifyQueueError;
import com.landray.kmss.third.ekp.java.notify.queue.service.IThirdEkpNotifyQueueErrorService;
import com.landray.kmss.util.DateUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;

public class ThirdEkpNotifyQueueErrorServiceImp extends BaseServiceImp
		implements IThirdEkpNotifyQueueErrorService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdEkpNotifyQueueErrorServiceImp.class);

	private static boolean runErrorQueueLocked = false;

	private EkpNotifyJavaTodoProviderSimple ekpNotifyJavaTodoProvider;

	@Override
	public String add(Object todoContext, int opt, String errorMsg,
			String notifyId)
			throws Exception {
		try {
			ThirdEkpNotifyQueueError queueError = null;
			switch (opt) {
			case 1:
				NotifyTodoSendContext sendContext = (NotifyTodoSendContext) todoContext;
				queueError = getThirdEkpNotifyQueueError(sendContext, errorMsg);
				break;
			case 2:
			case 3:
				NotifyTodoRemoveContext removeContext = (NotifyTodoRemoveContext) todoContext;
				queueError = getThirdEkpNotifyQueueError(removeContext, opt,
						errorMsg, notifyId);
				break;
			case 4:
				NotifyTodoUpdateContext updateContext = (NotifyTodoUpdateContext) todoContext;
				queueError = getThirdEkpNotifyQueueError(updateContext,
						errorMsg);
				break;
			default:
				break;
			}
			if (queueError == null) {
				return null;
			}
			ThirdEkpNotifyQueueError oldError = findThirdEkpNotifyQueueError(
					queueError.getFdMD5());
			if (oldError != null) {
				oldError.setFdFlag(
						ThirdEkpNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_ERROR);
				this.update(oldError);
				return oldError.getFdId();
			}
			return this.add(queueError);
		} catch (Exception e) {
			logger.error("add ThirdEkpNotifyQueueError fail:", e);
		}
		return null;
	}

	private ThirdEkpNotifyQueueError findThirdEkpNotifyQueueError(String fdMD5)
			throws Exception {
		return (ThirdEkpNotifyQueueError) this
				.findFirstOne("thirdEkpNotifyQueueError.fdMD5='" + fdMD5 + "'",
						null);
	}

	@Override
	public void updateRunErrorQueue(SysQuartzJobContext jobContext) {
		if (runErrorQueueLocked) {
            return;
        }
		runErrorQueueLocked = true;
		try {
			HQLInfo countHql = new HQLInfo();
			countHql.setWhereBlock(
					"thirdEkpNotifyQueueError.fdFlag='1' and thirdEkpNotifyQueueError.fdRepeatHandle!='0'");
			countHql.setGettingCount(true);
			List<Object> resultList = findValue(countHql);
			Object result = resultList.get(0);
			long count = Long
					.parseLong(result != null ? result.toString() : "0");
			logger.debug("【EKP-JAVA】消息发送出错重复执行开始,出错队列消息条数为:" + count);
			if (count == 0) {
				return;
			}
			int groupCount = 100;
			int len = (int) count / groupCount;
			for (int k = 0; k <= len; k++) {
				Page page = this.findPage(
						"thirdEkpNotifyQueueError.fdFlag='1' and thirdEkpNotifyQueueError.fdRepeatHandle!='0'",
						null, 0, groupCount);
				List errorList = page.getList();
				if (errorList.isEmpty()) {
					logger.debug("【EKP-JAVA】消息发送出错重复执行完成:出错队列消息为空");
					return;
				}
				for (int i = 0; i < errorList.size(); i++) {
					ThirdEkpNotifyQueueError queueError = (ThirdEkpNotifyQueueError) errorList
							.get(i);
					String fdRepeatHandle = queueError.getFdRepeatHandle();
					if ("0".equals(fdRepeatHandle)) {
						continue;
					}
					// 更新
					Integer handle = Integer
							.valueOf(queueError.getFdRepeatHandle());
					handle = handle - 1;
					queueError.setFdRepeatHandle(handle.toString());
					queueError.setFdSendTime(new Date());
					queueError.setFdFlag(
							ThirdEkpNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_SEND);
					this.update(queueError);
					// 重新发送
					resend(queueError);
				}
			}
			logger.debug("【EKP-JAVA】消息发送出错重复执行成功！");
		} catch (Exception e) {
			logger.error("【EKP-JAVA】消息发送出错重复执行失败！", e);
		} finally {
			runErrorQueueLocked = false;
		}

	}

	private void resend(ThirdEkpNotifyQueueError queueError) throws Exception {
		JSONObject json = JSONObject.fromObject(queueError.getFdJson());
		if ("sendTodo".equals(queueError.getFdMethod())) {
			this.ekpNotifyJavaTodoProvider.add(json);
		}
		if ("setTodoDone".equals(queueError.getFdMethod())) {
			this.ekpNotifyJavaTodoProvider.todoDoneOrRemove(json, false);
		}
		if ("deleteTodo".equals(queueError.getFdMethod())) {
			this.ekpNotifyJavaTodoProvider.todoDoneOrRemove(json, true);
		}
		if ("updateTodo".equals(queueError.getFdMethod())) {
			this.ekpNotifyJavaTodoProvider.updateTodo(json);
		}

	}

	private ThirdEkpNotifyQueueError
			getThirdEkpNotifyQueueError(NotifyTodoSendContext sendContext,
					String fdErrorMsg) {
		JSONObject json = sendContext.toJson();
		ThirdEkpNotifyQueueError error = new ThirdEkpNotifyQueueError();
		error.setFdCreateTime(new Date());
		error.setFdAppName(sendContext.getAppName());
		error.setFdErrorMsg(fdErrorMsg);
		error.setFdJson(json.toString());
		error.setFdKey(sendContext.getKey());
		error.setFdMD5(sendContext.generateMD5());
		error.setFdMethod("sendTodo");
		error.setFdModelId(sendContext.getModelId());
		error.setFdModelName(sendContext.getModelName());
		error.setFdRepeatHandle(
				ThirdEkpNotifyQueueErrorConstants.NOTIFY_ERROR_REPEAT);
		error.setFdSubject(sendContext.getSubject());
		error.setFdFlag(
				ThirdEkpNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_ERROR);
		return error;
	}

	private ThirdEkpNotifyQueueError
			getThirdEkpNotifyQueueError(NotifyTodoRemoveContext removeContext,
					int opt,
					String fdErrorMsg, String notifyId) {
		JSONObject json = removeContext.toJson(opt);

		ThirdEkpNotifyQueueError error = new ThirdEkpNotifyQueueError();
		error.setFdCreateTime(new Date());
		error.setFdAppName(removeContext.getAppName());
		error.setFdErrorMsg(fdErrorMsg);
		error.setFdJson(json.toString());
		error.setFdKey(removeContext.getKey());
		error.setFdMD5(removeContext.generateMD5(opt, notifyId));
		error.setFdMethod(opt == 2 ? "setTodoDone" : "deleteTodo");
		error.setFdModelId(removeContext.getModelId());
		error.setFdModelName(removeContext.getModelName());
		error.setFdRepeatHandle(
				ThirdEkpNotifyQueueErrorConstants.NOTIFY_ERROR_REPEAT);
		error.setFdFlag(
				ThirdEkpNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_ERROR);
		return error;
	}

	private ThirdEkpNotifyQueueError
			getThirdEkpNotifyQueueError(NotifyTodoUpdateContext updateContext,
					String fdErrorMsg) {
		JSONObject json = updateContext.toJson();

		ThirdEkpNotifyQueueError error = new ThirdEkpNotifyQueueError();
		error.setFdCreateTime(new Date());
		error.setFdAppName(updateContext.getAppName());
		error.setFdErrorMsg(fdErrorMsg);
		error.setFdJson(json.toString());
		error.setFdKey(updateContext.getKey());
		error.setFdMD5(updateContext.generateMD5());
		error.setFdMethod("updateTodo");
		error.setFdModelId(updateContext.getModelId());
		error.setFdModelName(updateContext.getModelName());
		error.setFdRepeatHandle(
				ThirdEkpNotifyQueueErrorConstants.NOTIFY_ERROR_REPEAT);
		error.setFdFlag(
				ThirdEkpNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_ERROR);
		error.setFdSubject(updateContext.getSubject());
		return error;
	}

	@Override
	public void clearNotifyQueueError(SysQuartzJobContext jobContext) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"thirdEkpNotifyQueueError.fdCreateTime<:fdCreateTime");
			hqlInfo.setParameter("fdCreateTime", DateUtil.getDate(-30));

			List errorList = this.findList(hqlInfo);
			if (errorList.isEmpty()) {
				logger.debug("【ekp-java】错误队列为空,清理已完成.");
				return;
			}

			for (int i = 0; i < errorList.size(); i++) {
				ThirdEkpNotifyQueueError queueError = (ThirdEkpNotifyQueueError) errorList
						.get(i);
				this.delete(queueError);
			}
			logger.debug(
					"【EKP-JAVA】错误队列清理执行成功！清理出错队列消息条数为:" + errorList.size());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【ekp-java】错误队列清理失败:" + e.getMessage(), e);
		}

	}

	public void setEkpNotifyJavaTodoProvider(
			EkpNotifyJavaTodoProviderSimple ekpNotifyJavaTodoProvider) {
		this.ekpNotifyJavaTodoProvider = ekpNotifyJavaTodoProvider;
	}

}
