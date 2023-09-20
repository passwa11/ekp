package com.landray.kmss.third.feishu.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.feishu.constant.ThirdFeishuConstant;
import com.landray.kmss.third.feishu.dao.IThirdFeishuNotifyQueueErrDao;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyQueueErr;
import com.landray.kmss.third.feishu.notify.IThirdFeishuApprovalResendProvider;
import com.landray.kmss.third.feishu.notify.IThirdFeishuTodoResendProvider;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyLogService;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyQueueErrService;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;

public class ThirdFeishuNotifyQueueErrServiceImp extends ExtendDataServiceImp implements IThirdFeishuNotifyQueueErrService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuNotifyQueueErrServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private IThirdFeishuTodoResendProvider thirdFeishuTodoMessageProvider;

	private IThirdFeishuApprovalResendProvider thirdFeishuApprovalResendProvider;

	public IThirdFeishuApprovalResendProvider getThirdFeishuApprovalResendProvider() {
		if (thirdFeishuApprovalResendProvider == null) {
			thirdFeishuApprovalResendProvider = (IThirdFeishuApprovalResendProvider) SpringBeanUtil
					.getBean("thirdFeishuApprovalResendProvider");
		}
		return thirdFeishuApprovalResendProvider;
	}

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdFeishuNotifyQueueErr) {
            ThirdFeishuNotifyQueueErr thirdFeishuNotifyQueueErr = (ThirdFeishuNotifyQueueErr) model;
            thirdFeishuNotifyQueueErr.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdFeishuNotifyQueueErr thirdFeishuNotifyQueueErr = new ThirdFeishuNotifyQueueErr();
        thirdFeishuNotifyQueueErr.setDocCreateTime(new Date());
        thirdFeishuNotifyQueueErr.setDocAlterTime(new Date());
        thirdFeishuNotifyQueueErr.setFdSendTarget(Integer.valueOf("1"));
        ThirdFeishuUtil.initModelFromRequest(thirdFeishuNotifyQueueErr, requestContext);
        return thirdFeishuNotifyQueueErr;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdFeishuNotifyQueueErr thirdFeishuNotifyQueueErr = (ThirdFeishuNotifyQueueErr) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }



	private void resend(ThirdFeishuNotifyQueueErr queueError) throws Exception {
		String method = queueError.getFdMethod();
		JSONObject json = JSONObject.fromObject(queueError.getFdData());
		if("syncApproval".equals(method)){
			getThirdFeishuApprovalResendProvider().resend(queueError);
		}
		else{
			getThirdFeishuTodoMessageProvider().resend(json, method);
		}
	}

	public IThirdFeishuTodoResendProvider getThirdFeishuTodoMessageProvider() {
		if (thirdFeishuTodoMessageProvider == null) {
			thirdFeishuTodoMessageProvider = (IThirdFeishuTodoResendProvider) SpringBeanUtil
					.getBean("thirdFeishuTodoMessageProvider");
		}
		return thirdFeishuTodoMessageProvider;
	}

	private static boolean runErrorQueueLocked = false;

	@Override
	public void updateRunErrorQueue(SysQuartzJobContext jobContext) {
		if (runErrorQueueLocked) {
            return;
        }
		runErrorQueueLocked = true;
		try {
			HQLInfo countHql = new HQLInfo();
			countHql.setWhereBlock(
					"thirdFeishuNotifyQueueErr.fdFlag=1 and thirdFeishuNotifyQueueErr.fdRepeatHandle<"
							+ ThirdFeishuConstant.NOTIFY_ERROR_REPEAT);
			countHql.setGettingCount(true);
			List<Object> resultList = findValue(countHql);
			Object result = resultList.get(0);
			long count = Long
					.parseLong(result != null ? result.toString() : "0");
			logger.warn("【third-feishu】消息发送出错重复执行开始,出错队列消息条数为:" + count);
			if (count == 0) {
				return;
			}
			int groupCount = 100;
			int len = (int) count / groupCount;
			for (int k = 0; k <= len; k++) {
				Page page = this.findPage(
						"thirdFeishuNotifyQueueErr.fdFlag=1 and thirdFeishuNotifyQueueErr.fdRepeatHandle<"
								+ ThirdFeishuConstant.NOTIFY_ERROR_REPEAT,
						"thirdFeishuNotifyQueueErr.docCreateTime asc", 0,
						groupCount);
				List errorList = page.getList();
				if (errorList.isEmpty()) {
					logger.debug("【third-feishu】消息发送出错重复执行完成:出错队列消息为空");
					return;
				}
				for (int i = 0; i < errorList.size(); i++) {
					ThirdFeishuNotifyQueueErr queueError = (ThirdFeishuNotifyQueueErr) errorList
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
								ThirdFeishuConstant.NOTIFY_ERROR_FDFLAG_SEND);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					} finally {
						this.update(queueError);
					}
				}
			}
			logger.debug("【third-feishu】消息发送出错重复执行成功！");
		} catch (Exception e) {
			logger.error("【third-feishu】消息发送出错重复执行失败！", e);
		} finally {
			runErrorQueueLocked = false;
		}
	}

	@Override
	public void clearNotifyQueueError(SysQuartzJobContext jobContext) {
		try {
			((IThirdFeishuNotifyQueueErrDao) getBaseDao()).clear(60);
			IThirdFeishuNotifyLogService thirdFeishuNotifyLogService = (IThirdFeishuNotifyLogService) SpringBeanUtil
					.getBean("thirdFeishuNotifyLogService");
			thirdFeishuNotifyLogService.clear(30);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【third-feishu】错误队列清理失败:" + e.getMessage(), e);
		}

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
					"thirdFeishuNotifyQueueErr.fdFlag=1 and thirdFeishuNotifyQueueErr.fdId in ("
							+ buildInBlock(ids) + ")",
					"thirdFeishuNotifyQueueErr.docCreateTime asc");
			if (errorList.isEmpty()) {
				logger.debug("【third-feishu】消息发送出错重复执行完成:出错队列消息为空");
				return;
			}
			for (int i = 0; i < errorList.size(); i++) {
				ThirdFeishuNotifyQueueErr queueError = (ThirdFeishuNotifyQueueErr) errorList
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
							ThirdFeishuConstant.NOTIFY_ERROR_FDFLAG_SEND);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				} finally {
					this.update(queueError);
				}
			}
			logger.debug("【third-feishu】消息发送出错重复执行成功！");
		} catch (Exception e) {
			throw e;
		} finally {
			runErrorQueueLocked = false;
		}
	}

}
