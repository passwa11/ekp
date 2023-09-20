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
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryContentDao;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendHisCategoryContent;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.time.transfer.SysTimeAmountTransferTask;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 考勤组的配置迁移
 * @author wj
 * @date 2022-08-11
 */
public class SysAttendCategoryContentTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {
	private final Logger logger = LoggerFactory.getLogger(SysTimeAmountTransferTask.class);
	private ISysAttendCategoryService sysAttendCategoryService;

	private ISysAttendCategoryService getSysAttendCategoryService() {
		if(sysAttendCategoryService ==null){
			sysAttendCategoryService= (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}





	private ISysAttendCategoryContentDao sysAttendCategoryContentDao;
	private ISysAttendCategoryContentDao getSysAttendCategoryContentDao(){
		if(sysAttendCategoryContentDao ==null){
			sysAttendCategoryContentDao = (ISysAttendCategoryContentDao) SpringBeanUtil.getBean("sysAttendCategoryContentDao");
		}
		return sysAttendCategoryContentDao;
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

		boolean isError =false;
		StringBuffer message=new StringBuffer();
		try {
			 //查询所有的历史表数据
			List<SysAttendHisCategory> hisCategories = getSysAttendHisCategoryService().findList("","");
			if(CollectionUtils.isNotEmpty(hisCategories)){
				for (SysAttendHisCategory hisCategory: hisCategories) {
					String contentJson = hisCategory.getFdCategoryContent();
					SysAttendHisCategoryContent categoryContent=new SysAttendHisCategoryContent();
					categoryContent.setFdId(hisCategory.getFdId());
					categoryContent.setFdCategoryContent(contentJson);
					getSysAttendCategoryContentDao().add(categoryContent);

					hisCategory.setFdCategoryContentNew(categoryContent);
					hisCategory.setFdCategoryContent(null);
					getSysAttendHisCategoryService().update(hisCategory);
				}
				//清理历史考勤组 缓存
				CategoryUtil.HIS_CATEGORY_CACHE_MAP.clear();
				//清理考勤组的缓存 缓存
				CategoryUtil.CATEGORY_CACHE_MAP.clear();
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
