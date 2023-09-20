package com.landray.kmss.sys.attend.transfer;

import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 历史考勤组的负责人迁移
 * @author wj
 * @date 2022-02-15
 */
public class SysAttendCategoryManagerTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {
	private final Logger logger = LoggerFactory.getLogger(SysAttendCategoryManagerTransferTask.class);
	private ISysAttendCategoryService sysAttendCategoryService;

	private ISysAttendCategoryService getSysAttendCategoryService() {
		if(sysAttendCategoryService ==null){
			sysAttendCategoryService= (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}


	private ISysAttendHisCategoryService sysAttendHisCategoryService;
	private ISysAttendHisCategoryService getSysAttendHisCategoryService(){
		if(sysAttendHisCategoryService ==null){
			sysAttendHisCategoryService = (ISysAttendHisCategoryService) SpringBeanUtil.getBean("sysAttendHisCategoryService");
		}
		return sysAttendHisCategoryService;
	}
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			//将历史考勤组中负责人为空的数据中的负责人字段的值。从原始考勤组中的负责人读取。
			getSysAttendHisCategoryService().getBaseDao().getSession().createQuery("update SysAttendHisCategory sysAttendHisCategory set sysAttendHisCategory.fdManager=(select sac.fdManager from SysAttendCategory sac where sac.fdId=sysAttendHisCategory.fdCategoryId) where sysAttendHisCategory.fdManager is null and sysAttendHisCategory.fdIsAvailable=:fdIsAvailable ")
					.setParameter("fdIsAvailable",true).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysAdminTransferResult.OK;
	}

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			String uuid = sysAdminTransferCheckContext.getUUID();
			List list = sysAdminTransferTaskService.getBaseDao().findValue(null,
					"sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			if (CollectionUtils.isNotEmpty(list)) {
				SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			}
			/*//查找所有历史考勤组中考勤负责人为空的历史考勤组
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(" sysAttendHisCategory.fdCategoryId ");
			hqlInfo.setWhereBlock(" sysAttendHisCategory.fdIsAvailable=:fdIsAvailable and sysAttendHisCategory.fdManager is null ");
			hqlInfo.setParameter("fdIsAvailable", true);
			List<Object> attendHisList = getSysAttendHisCategoryService().findValue(hqlInfo);
			//未查询到数据则无须执行
			if(CollectionUtils.isEmpty(attendHisList)){
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}
}
