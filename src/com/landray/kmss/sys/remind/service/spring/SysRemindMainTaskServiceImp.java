package com.landray.kmss.sys.remind.service.spring;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.scheduler.ISysQuartzJobExecutor;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.sys.remind.model.SysRemindMainTask;
import com.landray.kmss.sys.remind.service.ISysRemindMainTaskService;

/**
 * 提醒任务
 * 
 * @author panyh
 * @date Jun 28, 2020
 */
public class SysRemindMainTaskServiceImp extends BaseServiceImp implements ISysRemindMainTaskService {

	private ISysQuartzJobExecutor sysQuartzJobExecutor;

	private ISysQuartzJobService sysQuartzJobService;

	public void setSysQuartzJobExecutor(ISysQuartzJobExecutor sysQuartzJobExecutor) {
		this.sysQuartzJobExecutor = sysQuartzJobExecutor;
	}

	public void setSysQuartzJobService(ISysQuartzJobService sysQuartzJobService) {
		this.sysQuartzJobService = sysQuartzJobService;
	}

	@Override
	public void runTask(String taskId) throws Exception {
		// 查询定时任务
		SysQuartzJob job = getJob(taskId);
		if (job != null) {
			// 执行任务
			sysQuartzJobExecutor.execute(job.getFdId());
			SysRemindMainTask task = (SysRemindMainTask) findByPrimaryKey(taskId);
			task.setFdRunTime(new Date());
			update(task);
		}
	}

	private SysQuartzJob getJob(String taskId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("fdModelId = :taskId and fdModelName = :modelName and fdEnabled = true and fdIsSysJob = false");
		hql.setParameter("taskId", taskId);
		hql.setParameter("modelName", SysRemindMainTask.class.getName());
		List<SysQuartzJob> list = sysQuartzJobService.findValue(hql);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<SysRemindMainTask> getByModel(String fdModelId, String fdModelName, String fdKey) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("fdModelId = :modelId and fdModelName = :modelName and fdKey = :key");
		hql.setParameter("modelId", fdModelId);
		hql.setParameter("modelName", fdModelName);
		hql.setParameter("key", fdKey);
		return findList(hql);
	}

}
