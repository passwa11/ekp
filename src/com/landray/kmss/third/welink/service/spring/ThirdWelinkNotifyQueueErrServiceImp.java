package com.landray.kmss.third.welink.service.spring;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.welink.constant.ThirdWelinkConstant;
import com.landray.kmss.third.welink.dao.IThirdWelinkNotifyQueueErrDao;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyQueueErr;
import com.landray.kmss.third.welink.notify.ThirdWelinkTodoMessageProvider;
import com.landray.kmss.third.welink.notify.ThirdWelinkTodoTaskProvider;
import com.landray.kmss.third.welink.service.IThirdWelinkNotifyLogService;
import com.landray.kmss.third.welink.service.IThirdWelinkNotifyQueueErrService;
import com.landray.kmss.third.welink.util.ThirdWelinkUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public class ThirdWelinkNotifyQueueErrServiceImp extends ExtendDataServiceImp implements IThirdWelinkNotifyQueueErrService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWelinkNotifyQueueErr) {
            ThirdWelinkNotifyQueueErr thirdWelinkNotifyQueueErr = (ThirdWelinkNotifyQueueErr) model;
            thirdWelinkNotifyQueueErr.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWelinkNotifyQueueErr thirdWelinkNotifyQueueErr = new ThirdWelinkNotifyQueueErr();
        thirdWelinkNotifyQueueErr.setDocCreateTime(new Date());
        thirdWelinkNotifyQueueErr.setDocAlterTime(new Date());
        ThirdWelinkUtil.initModelFromRequest(thirdWelinkNotifyQueueErr, requestContext);
        return thirdWelinkNotifyQueueErr;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWelinkNotifyQueueErr thirdWelinkNotifyQueueErr = (ThirdWelinkNotifyQueueErr) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkNotifyQueueErrServiceImp.class);

	private static boolean runErrorQueueLocked = false;

	private ThirdWelinkTodoTaskProvider todoTaskProvider;

	private ThirdWelinkTodoMessageProvider todoMessageProvider;

	public ThirdWelinkTodoTaskProvider getTodoTaskProvider() {
		if (todoTaskProvider == null) {
			todoTaskProvider = (ThirdWelinkTodoTaskProvider) SpringBeanUtil
					.getBean("thirdWelinkTodoTaskProvider");
		}
		return todoTaskProvider;
	}

	public ThirdWelinkTodoMessageProvider getTodoMessageProvider() {
		if (todoMessageProvider == null) {
			todoMessageProvider = (ThirdWelinkTodoMessageProvider) SpringBeanUtil
					.getBean("thirdWelinkTodoMessageProvider");
		}
		return todoMessageProvider;
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
					"thirdWelinkNotifyQueueErr.fdFlag=1 and thirdWelinkNotifyQueueErr.fdRepeatHandle<"
							+ ThirdWelinkConstant.NOTIFY_ERROR_REPEAT);
			countHql.setGettingCount(true);
			List<Object> resultList = findValue(countHql);
			Object result = resultList.get(0);
			long count = Long
					.parseLong(result != null ? result.toString() : "0");
			logger.warn("【third-welink】消息发送出错重复执行开始,出错队列消息条数为:" + count);
			if (count == 0) {
				return;
			}
			int groupCount = 100;
			int len = (int) count / groupCount;
			for (int k = 0; k <= len; k++) {
				Page page = this.findPage(
						"thirdWelinkNotifyQueueErr.fdFlag=1 and thirdWelinkNotifyQueueErr.fdRepeatHandle<"
								+ ThirdWelinkConstant.NOTIFY_ERROR_REPEAT,
						"thirdWelinkNotifyQueueErr.docCreateTime", 0,
						groupCount);
				List errorList = page.getList();
				if (errorList.isEmpty()) {
					logger.debug("【third-welink】消息发送出错重复执行完成:出错队列消息为空");
					return;
				}
				for (int i = 0; i < errorList.size(); i++) {
					ThirdWelinkNotifyQueueErr queueError = (ThirdWelinkNotifyQueueErr) errorList
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
								ThirdWelinkConstant.NOTIFY_ERROR_FDFLAG_SEND);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					} finally {
						this.update(queueError);
					}
				}
			}
			logger.debug("【third-welink】消息发送出错重复执行成功！");
		} catch (Exception e) {
			logger.error("【third-welink】消息发送出错重复执行失败！", e);
		} finally {
			runErrorQueueLocked = false;
		}
	}

	private void resend(ThirdWelinkNotifyQueueErr queueError) throws Exception {
		int sendType = queueError.getFdSendType();
		JSONObject json = JSONObject.fromObject(queueError.getFdData());

		if (sendType == 1) {
			getTodoMessageProvider().sendNotify(json);
			return;
		}

		int method = queueError.getFdMethod();
		switch (method) {
		case 1:
			getTodoTaskProvider().addTodoTask(json);
			break;
		case 2:
			// getTodoTaskProvider().updateTodoTask(json);
			throw new Exception("数据有问题");
		case 3:
			getTodoTaskProvider().deleteTodoTask(json);
			break;
		}
	}

	@Override
	public void clearNotifyQueueError(SysQuartzJobContext jobContext) {
		try {
			((IThirdWelinkNotifyQueueErrDao) getBaseDao()).clear(60);
			IThirdWelinkNotifyLogService thirdWelinkNotifyLogService = (IThirdWelinkNotifyLogService) SpringBeanUtil
					.getBean("thirdWelinkNotifyLogService");
			thirdWelinkNotifyLogService.clear(30);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【third-welink】错误队列清理失败:" + e.getMessage(), e);
		}

	}

	@Override
	public void updateResend(String[] ids) throws Exception {
		for (String id : ids) {
			ThirdWelinkNotifyQueueErr queueError = (ThirdWelinkNotifyQueueErr) findByPrimaryKey(
					id, null, true);
			// 更新
			Integer handle = queueError.getFdRepeatHandle();
			handle = handle + 1;
			queueError.setFdRepeatHandle(handle);
			queueError.setDocAlterTime(new Date());
			try {
				// 重新发送
				resend(queueError);
				queueError.setFdFlag(
						ThirdWelinkConstant.NOTIFY_ERROR_FDFLAG_SEND);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				// throw e;
			} finally {
				this.update(queueError);
			}
		}

	}

}
