package com.landray.kmss.sys.attend.transfer;

import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.time.transfer.SysTimeAmountTransferTask;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 考勤组的历史数据迁移
 * @author wj
 * @date 2021-09-27
 */
public class SysAttendCategoryTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {
	private final Logger logger = LoggerFactory.getLogger(SysTimeAmountTransferTask.class);
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
		//考勤组转成历史
		boolean isError =false;
		StringBuffer message=new StringBuffer();
		try {
			//所有考勤组 fdType=1
			Map<String, SysAttendCategory> categoryMap = getSysAttendCategoryService().getCategoryMap();
			TransactionStatus status =null;
			for (Map.Entry<String, SysAttendCategory> categoryInfo:categoryMap.entrySet()) {
				boolean isExcetion=false;
				String categoryId = categoryInfo.getKey();
				try {
					status =TransactionUtils.beginNewTransaction();
					//不重复执行，
					SysAttendHisCategory hisCategory = getSysAttendHisCategoryService().getLastVersionFdId(categoryId);
					if (hisCategory == null) {
						hisCategory = getSysAttendCategoryService().addHisCategory(categoryInfo.getValue());
					}else{
						continue;
					}
					if(hisCategory==null){
						continue;
					}
					//修改历史考勤组数据
					getSysAttendHisCategoryService().updateHisAttendMain(categoryId,hisCategory.getFdId(),true);

					TransactionUtils.commit(status);
				}catch (Exception e){
					isExcetion =true;
					isError =true;
					e.printStackTrace();
					String msg ="迁移任务失败,考勤组ID："+categoryId+" 错误信息"+e.getMessage();
					logger.error(msg);
					message.append(msg).append(" ;");
				}finally {
					if(isExcetion && status !=null){
						TransactionUtils.rollback(status);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String msg ="迁移任务失败,获取考勤组异常 错误信息"+e.getMessage();
			logger.error(msg);
			isError =true;
			message.append(msg).append(" ;");
		}
		if(isError){
			return new SysAdminTransferResult(
					ISysAdminTransferConstant.TASK_RESULT_ERROR, message.toString());
		}
		//sys_attend_remind_log 待办通知
		return SysAdminTransferResult.OK;
	}

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");

			String uuid = sysAdminTransferCheckContext.getUUID();
			List list = new ArrayList();
			list = sysAdminTransferTaskService.getBaseDao().findValue(null,
					"sysAdminTransferTask.fdUuid='" + uuid + "'", null);

			if (list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list
						.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			}
			//没有执行就永远是未执行状态。
			return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}
}
