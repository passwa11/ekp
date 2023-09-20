package com.landray.kmss.third.im.kk.queue.service.spring;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.im.kk.provider.Kk5NotifyTodoProvider;
import com.landray.kmss.third.im.kk.queue.model.KkNotifyQueueError;
import com.landray.kmss.third.im.kk.queue.model.KkNotifyQueueLock;
import com.landray.kmss.third.im.kk.queue.service.IKkNotifyQueueErrorService;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;
import org.slf4j.Logger;

import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;

import java.util.List;

public class KkNotifyQueueErrorServiceImp extends ExtendDataServiceImp
		implements IKkNotifyQueueErrorService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KkNotifyQueueErrorServiceImp.class);

	private static boolean runErrorQueueLocked = false;

	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil
					.getBean("componentLockService");
		}
		return componentLockService;
	}

	private Kk5NotifyTodoProvider kk5NotifyTodoProvider;

	private Kk5NotifyTodoProvider getKk5NotifyTodoProvider(){
		if(kk5NotifyTodoProvider==null){
			kk5NotifyTodoProvider = (Kk5NotifyTodoProvider)SpringBeanUtil.getBean("kk5NotifyTodoProvider");
		}
		return kk5NotifyTodoProvider;
	}

	@Override
	public void updateRunErrorQueue(SysQuartzJobContext jobContext) {
		KkNotifyQueueLock handleModel = new KkNotifyQueueLock();
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
					"kkNotifyQueueError.fdFlag='1' and kkNotifyQueueError.fdRepeatHandle>0");
			countHql.setGettingCount(true);
			List<Object> resultList = findValue(countHql);
			Object result = resultList.get(0);
			long count = Long
					.parseLong(result != null ? result.toString() : "0");
			logger.warn("【third-im-kk】消息发送出错重复执行开始,出错队列消息条数为:" + count);
			if (count == 0) {
				return;
			}
			int groupCount = 100;
			int len = (int) count / groupCount;
			for (int k = 0; k <= len; k++) {
				Page page = this.findPage(
						"kkNotifyQueueError.fdFlag='1' and kkNotifyQueueError.fdRepeatHandle>0",
						"kkNotifyQueueError.fdCreateTime", k,
						groupCount);
				List errorList = page.getList();
				if (errorList.isEmpty()) {
					logger.debug("【third-im-kk】消息发送出错重复执行完成:出错队列消息为空");
					return;
				}
				for (int i = 0; i < errorList.size(); i++) {
					KkNotifyQueueError queueError = (KkNotifyQueueError) errorList.get(i);
					int fdRepeatHandle = queueError.getFdRepeatHandle();
					if (fdRepeatHandle <= 0) {
						continue;
					}
					getKk5NotifyTodoProvider().resendExecutePostDataKK5(queueError.getFdId(),queueError.getFdUrl());
				}
			}
			logger.debug("【third-im-kk】消息发送出错重复执行成功！");
			getComponentLockService().unLock(handleModel);
		} catch (ConcurrencyException e) {
			String temp = "异常任务同步  或者 待办推送失败消息重发 已经在运行，当前任务中断...";
			logger.warn(temp);
			jobContext.logMessage(temp);
		} catch (Exception e) {
			logger.error("【third-im-kk】消息发送出错重复执行失败！", e);
		} finally {
			runErrorQueueLocked = false;
			getComponentLockService().unLock(handleModel);
		}
	}
}
