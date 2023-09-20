package com.landray.kmss.hr.ratify.transfer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.hr.ratify.model.HrRatifyLeave;
import com.landray.kmss.hr.ratify.service.IHrRatifyLeaveService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;


/**
 * 人员离职状态的同步-待离职 到已离职的数据处理
 * @author 王京
 *
 */
public class HrRatifyLeaveStatusTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	private final Log logger = LogFactory
			.getLog(HrRatifyLeaveStatusTransferTask.class);

	
	private IHrRatifyLeaveService hrRatifyLeaveService;
	public IHrRatifyLeaveService getHrRatifyLeaveService() {
		if (hrRatifyLeaveService == null) {
			hrRatifyLeaveService = (IHrRatifyLeaveService) SpringBeanUtil.getBean("hrRatifyLeaveService");
		}
		return hrRatifyLeaveService;
	}
	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			//检测 离职信息表中 实际离职日期小于当前日期的数据
			List<HrRatifyLeave> lists = getHrRatifyLeaveService().getLevelLists();
			if(ArrayUtil.isEmpty(lists)) {
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			// 修改离职流程中人员的离职信息
			//检测 离职信息表中 实际离职日期小于当前日期的数据
			List<HrRatifyLeave> lists = getHrRatifyLeaveService().getLevelLists();
			if(!ArrayUtil.isEmpty(lists)) {
				for (HrRatifyLeave hrRatifyLeave : lists) {
					 getHrRatifyLeaveService().updateStaffLeavelStatus(hrRatifyLeave);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("人事流程，待离职状态修改为离职状态同步操作失败：" + e.getMessage(), e);
		}
		return SysAdminTransferResult.OK;
	} 
}
