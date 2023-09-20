package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.model.SysAttendSynConfig;
import com.landray.kmss.sys.attend.model.SysAttendSynDingQueueError;
import com.landray.kmss.sys.attend.service.ISysAttendSynConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingQueueErrorService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;

public class SysAttendSynDingQueueErrorServiceImp extends BaseServiceImp
		implements ISysAttendSynDingQueueErrorService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendSynDingQueueErrorServiceImp.class);

	private static boolean runErrorQueueLocked = false;
	
	private ISysAttendSynDingService sysAttendSynDingService;
    
    public ISysAttendSynDingService getSysAttendSynDingService() {
        if(sysAttendSynDingService == null){
            sysAttendSynDingService = (ISysAttendSynDingService) SpringBeanUtil.getBean("sysAttendSynDingService");
        }
        return sysAttendSynDingService;
    }

	private ISysAttendSynConfigService sysAttendSynConfigService;

	public ISysAttendSynConfigService getSysAttendSynConfigService() {
		if (sysAttendSynConfigService == null) {
			sysAttendSynConfigService = (ISysAttendSynConfigService) SpringBeanUtil
					.getBean("sysAttendSynConfigService");
		}
		return sysAttendSynConfigService;
	}

	@Override
	public String add(Date fdStartTime, Date fdEndTime, String userIds,
			String fdErrorMsg) throws Exception {
		try {
			SysAttendSynDingQueueError queueError = getSysAttendSynDingQueueError(
					fdStartTime, fdEndTime, userIds, fdErrorMsg);
			SysAttendSynDingQueueError oldError = findThirdEkpNotifyQueueError(
					queueError.getFdMD5());
			if (oldError != null) {
				oldError.setFdFlag(AttendConstant.DING_SYN_ERROR_FDFLAG_ERROR);
				this.update(oldError);
				return oldError.getFdId();
			}
			return this.add(queueError);
		} catch (Exception e) {
			logger.error("add SysAttendSynDingQueueError fail:", e);
		}
		return null;
	}

	private SysAttendSynDingQueueError
			findThirdEkpNotifyQueueError(String fdMd5) throws Exception {
	    HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysAttendSynDingQueueError.fdMD5 = :fdMD5");
		hqlInfo.setParameter("fdMD5", fdMd5);
		SysAttendSynDingQueueError sysAttendSynDingQueueError = (SysAttendSynDingQueueError) this.findFirstOne(hqlInfo);
		if (sysAttendSynDingQueueError == null) {
			return null;
		}
		return sysAttendSynDingQueueError;
	}

	@Override
	public void updateRunErrorQueue(SysQuartzJobContext jobContext)
			throws Exception {
		String temp = "";
		if (!AttendUtil.isEnableDingConfig()) {
			temp = "未开启钉钉集成,钉钉考勤记录失败任务不执行";
			logger.debug(temp);
			return;
		}

		SysAttendSynConfig config = getSysAttendSynConfigService()
				.getSysAttendSynConfig();
		if (null == config || false == config.getFdEnableRecord()) {
			temp = "==========未开启钉钉打卡记录同步,钉钉考勤记录失败任务不执行===============";
			logger.warn(temp);
			return;
		}
		if (runErrorQueueLocked) {
			return;
		}
		runErrorQueueLocked = true;
		try {
			HQLInfo countHql = new HQLInfo();
			countHql.setWhereBlock("sysAttendSynDingQueueError.fdFlag='1' and sysAttendSynDingQueueError.fdRepeatHandle!='0'");
			countHql.setGettingCount(true);
			List<Object> resultList = findValue(countHql);
			Object result = resultList.get(0);
			long count = Long.parseLong(result != null ? result.toString() : "0");
			logger.debug("【签到服务】钉钉考勤数据同步出错重复执行开始,出错队列消息条数为:" + count);
			if (count == 0) {
				return;
			}

			int groupCount = 100;
			int len = (int) count / groupCount;
			for (int k = 0; k <= len; k++) {
				Page page = this.findPage(
						"sysAttendSynDingQueueError.fdFlag='1' and sysAttendSynDingQueueError.fdRepeatHandle!='0'",
						null, 0, groupCount);
				List errorList = page.getList();
				if (errorList.isEmpty()) {
					logger.debug("【签到服务】钉钉考勤数据同步出错重复执行完成:出错队列消息为空");
					return;
				}
				for (int i = 0; i < errorList.size(); i++) {
				    SysAttendSynDingQueueError queueError = (SysAttendSynDingQueueError) errorList
							.get(i);
					Integer fdRepeatHandle = queueError.getFdRepeatHandle();
					if (fdRepeatHandle == 0) {
						continue;
					}
					// 更新
					Integer handle = queueError.getFdRepeatHandle();
					handle = handle - 1;
					queueError.setFdRepeatHandle(handle);
					queueError.setFdSynTime(new Date());
					queueError.setFdFlag(AttendConstant.DING_SYN_ERROR_FDFLAG_SEND);
					this.update(queueError);
					// 重新同步
					getSysAttendSynDingService().reSynchPersonClock(
							queueError.getFdStartTime(),
							queueError.getFdEndTime(),
							queueError.getFdUserIds());
					;
				}
			}
			logger.debug("【签到服务】钉钉考勤数据同步出错重复执行成功！");
		} catch (Exception e) {
			logger.error("【签到服务】钉钉考勤数据同步出错重复执行失败！", e);
		} finally {
			runErrorQueueLocked = false;
		}

	}

	private SysAttendSynDingQueueError getSysAttendSynDingQueueError(
			Date fdStartTime, Date fdEndTime, String fdUserIds,
					String fdErrorMsg) {
		SysAttendSynDingQueueError error = new SysAttendSynDingQueueError();
		error.setFdCreateTime(new Date());
		error.setFdStartTime(fdStartTime);
		error.setFdEndTime(fdEndTime);
		error.setFdUserIds(fdUserIds);
		error.setFdMD5(generateMD5(fdStartTime, fdEndTime, fdUserIds));
		error.setFdErrorMsg(fdErrorMsg);
		error.setFdRepeatHandle(AttendConstant.DING_SYN_ERROR_REPEAT);
		error.setFdFlag(AttendConstant.DING_SYN_ERROR_FDFLAG_ERROR);

		return error;
	}

	@Override
	public void clearNotifyQueueError(SysQuartzJobContext jobContext) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysAttendSynDingQueueError.fdCreateTime<:fdCreateTime");
			hqlInfo.setParameter("fdCreateTime", DateUtil.getDate(-30));

			List errorList = this.findList(hqlInfo);
			if (errorList.isEmpty()) {
				logger.debug("【签到服务】钉钉考勤数据同步错误队列为空,清理已完成.");
				return;
			}

			for (int i = 0; i < errorList.size(); i++) {
			    SysAttendSynDingQueueError queueError = (SysAttendSynDingQueueError) errorList.get(i);
				this.delete(queueError);
			}
			logger.debug(
					"【签到服务】钉钉考勤数据同步错误队列清理执行成功！清理出错队列消息条数为:" + errorList.size());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【签到服务】钉钉考勤数据同步错误队列清理失败:" + e.getMessage(), e);
		}
	}

	public String generateMD5(Date fdStartTime, Date fdEndTime,
			String fdUserIds) {
		return MD5Util.getMD5String(
				StringUtil.getString(DateUtil.convertDateToString(fdStartTime,
						"yyyy-MM-dd HH:mm:ss"))
						+ StringUtil.getString(DateUtil.convertDateToString(
								fdStartTime, "yyyy-MM-dd HH:mm:ss"))
						+ StringUtil.getString(fdUserIds));
	}
}
