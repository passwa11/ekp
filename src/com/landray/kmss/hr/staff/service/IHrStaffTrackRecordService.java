package com.landray.kmss.hr.staff.service;

import java.util.Date;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.forms.HrStaffTrackRecordForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.KmssMessage;

/**
 * 任职记录
 * 
 * 
 */
public interface IHrStaffTrackRecordService extends IBaseService {
	/**
	 * 获取任职记录
	 * 
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public String getTrackRecordByPerson(String fdId)
			throws Exception;

	/**
	 * 获取个人信息
	 * 
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public HrStaffPersonInfo getPersonInfo(String fdId)
			throws Exception;

	/**
	 * 报错excel数据据
	 * 
	 */
	public KmssMessage saveImportData(HrStaffTrackRecordForm trackRecordForm)
			throws Exception;

	/**
	 * <p>
	 * 新增调动调薪
	 * </p>
	 * 
	 * @param model
	 * @author sunj
	 */
	public void addTransfer(HrStaffTrackRecord model) throws Exception;

	/**
	 * <p>定时任务更新部门、岗位</p>
	 * @throws Exception
	 * @author sunj
	 */
	public void personSchedulerJob(SysQuartzJobContext context) throws Exception;

	public void updateHrStaffTrackRecord(HrStaffTrackRecord model, String afterEffectTime) throws Exception;

	/**
	 * <p>人将其最后一个任职记录增加结束时间，并将任职状态置为已结束</p>
	 * @param currId  当前新增fdId
	 * @param personInfo 人员对象
	 * @param beforeDate 上条任职记录结束时间（默认为当前时间）
	 * @throws Exception
	 * @author sunj
	 */
	public void updateLastTrackRecord(String currId, HrStaffPersonInfo personInfo, Date beforeDate)
			throws Exception;

	/**
	 * <p>检查同部门同岗位兼岗唯一</p>
	 * @param fdPersonId
	 * @param fdDeptId
	 * @param fdPostId
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public boolean checkUnique(String fdId, String fdPersonId, String fdDeptId, String fdPostId,
			String fdStaffingLevelId, String fdType)
			throws Exception;

}
