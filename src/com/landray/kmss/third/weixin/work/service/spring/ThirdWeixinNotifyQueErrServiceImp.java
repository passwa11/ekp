package com.landray.kmss.third.weixin.work.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinNotifyQueErrDao;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyQueErr;
import com.landray.kmss.third.weixin.work.notify.WxNotifyProvider;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinNotifyLogService;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinNotifyQueErrService;
import com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;
import org.springframework.transaction.TransactionStatus;

public class ThirdWeixinNotifyQueErrServiceImp extends ExtendDataServiceImp implements IThirdWeixinNotifyQueErrService {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(ThirdWeixinNotifyQueErrServiceImp.class);

	public static final int NOTIFY_ERROR_REPEAT = 5;

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinNotifyQueErr) {
            ThirdWeixinNotifyQueErr thirdWeixinNotifyQueErr = (ThirdWeixinNotifyQueErr) model;
            thirdWeixinNotifyQueErr.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinNotifyQueErr thirdWeixinNotifyQueErr = new ThirdWeixinNotifyQueErr();
        thirdWeixinNotifyQueErr.setDocCreateTime(new Date());
        thirdWeixinNotifyQueErr.setDocAlterTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinNotifyQueErr, requestContext);
        return thirdWeixinNotifyQueErr;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinNotifyQueErr thirdWeixinNotifyQueErr = (ThirdWeixinNotifyQueErr) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
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

			HQLInfo countHql = new HQLInfo();
			countHql.setWhereBlock(
					"thirdWeixinNotifyQueErr.fdFlag='1' and thirdWeixinNotifyQueErr.fdRepeatHandle<"
							+ NOTIFY_ERROR_REPEAT);
			countHql.setGettingCount(true);
			List<Object> resultList = findValue(countHql);
			Object result = resultList.get(0);
			long count = Long
					.parseLong(result != null ? result.toString() : "0");
			logger.warn("【third-weixin-work】消息发送出错重复执行开始,出错队列消息条数为:" + count);
			if (count == 0) {
				return;
			}
			int groupCount = 100;
			int len = (int) count / groupCount;
			for (int k = 0; k <= len; k++) {
				Page page = this.findPage(
						"thirdWeixinNotifyQueErr.fdFlag='1' and thirdWeixinNotifyQueErr.fdRepeatHandle<"
								+ NOTIFY_ERROR_REPEAT,
						"thirdWeixinNotifyQueErr.docCreateTime", 0,
						groupCount);
				List errorList = page.getList();
				if (errorList.isEmpty()) {
					logger.debug("【third-weixin-work】消息发送出错重复执行完成:出错队列消息为空");
					return;
				}
				for (int i = 0; i < errorList.size(); i++) {
					ThirdWeixinNotifyQueErr queueError = (ThirdWeixinNotifyQueErr) errorList
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
						queueError.setFdFlag("0");
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					} finally {
						this.update(queueError);
					}
				}
			}
			logger.debug("【third-weixin-work】消息发送出错重复执行成功！");
		} catch (Exception e) {
			logger.error("【third-weixin-work】消息发送出错重复执行失败！", e);
		} finally {
			runErrorQueueLocked = false;
		}
	}

	private void resend(ThirdWeixinNotifyQueErr queueError) throws Exception {
		String method = queueError.getFdMethod();
		JSONObject json = JSONObject.parseObject(queueError.getFdData());
		TransactionStatus status = null;
		Throwable t = null;
		if ("send".equals(method)) {
			try {
				status = TransactionUtils.beginNewTransaction();
				getWxNotifyProvider().sendMessage(queueError.getFdNotifyId(),
						queueError.getFdSubject(), json, queueError.getFdCorpId());
				TransactionUtils.commit(status);
			}
			catch(Exception e){
				t = e;
				throw e;
			}
			finally {
				if (t != null && status != null && !status.isCompleted()) {
					TransactionUtils.rollback(status);
				}
			}
		} else if ("update_taskcard".equals(method)) {
			try {
				status = TransactionUtils.beginNewTransaction();
				getWxNotifyProvider().updateTaskcard(queueError.getFdNotifyId(),
					queueError.getFdSubject(), json, queueError.getFdCorpId());
				TransactionUtils.commit(status);
			}
			catch(Exception e){
				t = e;
				throw e;
			}
			finally {
				if (t != null && status != null && !status.isCompleted()) {
					TransactionUtils.rollback(status);
				}
			}
		}
	}

	@Override
	public void clearNotifyQueueError(SysQuartzJobContext jobContext) {
		try {
			((IThirdWeixinNotifyQueErrDao) getBaseDao()).clear(60);
			IThirdWeixinNotifyLogService thirdWeixinNotifyLogService = (IThirdWeixinNotifyLogService) SpringBeanUtil
					.getBean("thirdWeixinNotifyLogService");
			thirdWeixinNotifyLogService.clear(60);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【third-weixin-work】错误队列清理失败:" + e.getMessage(), e);
		}

	}

	private WxNotifyProvider wxNotifyProvider = null;

	public WxNotifyProvider getWxNotifyProvider() {
		if (wxNotifyProvider == null) {
			wxNotifyProvider = (WxNotifyProvider) SpringBeanUtil
					.getBean("wxworkNotifyProvider");
		}
		return wxNotifyProvider;
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
					"thirdWeixinNotifyQueErr.fdFlag='1' and thirdWeixinNotifyQueErr.fdId in ("
							+ buildInBlock(ids) + ")",
					"thirdWeixinNotifyQueErr.docCreateTime asc");
			if (errorList.isEmpty()) {
				logger.debug("【third-weixin-work】消息发送出错重复执行完成:出错队列消息为空");
				return;
			}
			for (int i = 0; i < errorList.size(); i++) {
				ThirdWeixinNotifyQueErr queueError = (ThirdWeixinNotifyQueErr) errorList
						.get(i);
				// 更新
				Integer handle = queueError.getFdRepeatHandle();
				handle = handle + 1;
				queueError.setFdRepeatHandle(handle);
				queueError.setDocAlterTime(new Date());
				try {
					// 重新发送
					resend(queueError);
					queueError.setFdFlag("0");
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				} finally {
					this.update(queueError);
				}
			}
			logger.debug("【third-weixin-work】消息发送出错重复执行成功！");
		} catch (Exception e) {
			throw e;
		} finally {
			runErrorQueueLocked = false;
		}
	}


	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil
					.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}

}
