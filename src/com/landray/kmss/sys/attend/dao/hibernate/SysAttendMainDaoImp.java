package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.dao.ISysAttendMainDao;
import com.landray.kmss.sys.attend.dao.ISysAttendMainLogDao;
import com.landray.kmss.sys.attend.dao.ISysAttendSynDingDao;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainLog;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import java.util.Date;

/**
 * 签到表数据访问接口实现
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendMainDaoImp extends BaseDaoImp
		implements ISysAttendMainDao {

	private ISysAttendMainLogDao sysAttendMainLogDao;
	private ISysAttendSynDingDao sysAttendSynDingDao;

	public void
			setSysAttendMainLogDao(ISysAttendMainLogDao sysAttendMainLogDao) {
		this.sysAttendMainLogDao = sysAttendMainLogDao;
	}


	public void
			setSysAttendSynDingDao(ISysAttendSynDingDao sysAttendSynDingDao) {
		this.sysAttendSynDingDao = sysAttendSynDingDao;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysAttendMain main = (SysAttendMain) modelObj;
		//如果签到组数据，则不进行考勤组的设置
		SysAttendCategory category = main.getFdCategory();
		//打卡时所处的考勤组
		if(category ==null) {
			category = CategoryUtil.getFdCategoryInfo(main);
			if(category ==null){
				//没有考勤组不允许打卡
				throw new Exception(" user is not in attendance group ");
			}
			if(main.getFdHisCategory() ==null){
				main.setFdHisCategory(CategoryUtil.getHisCategoryById(category.getFdId()));
			}
		}
		timeAreaRender(main,category.getFdShiftType());
		Date fdBaseWorkTime = main.getFdBaseWorkTime();
		main.setFdBaseWorkTime(fdBaseWorkTime);
		SysOrgPerson person = main.getDocCreator();
		main.setAuthArea(SysTimeUtil.getUserAuthArea(person.getFdId()));
		setFdDataType(main,category.getFdType());
		main.setDocStatus(0);
		String id = super.add(modelObj);
		addLog(main, "add");
		if (logger.isDebugEnabled()) {
			logger.debug(
					"add sysAttendMain:" + main.getFdId() + ";docCreatorName:"
							+ main.getDocCreator().getFdName() + ";fdStatus:"
							+ main.getFdStatus() + ";docCreateTime:"
							+ main.getDocCreateTime());
		}
		if (CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(category.getFdType())) {
			// 保存原始记录
			sysAttendSynDingDao.addRecord(main);
		}
		return id;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysAttendMain main = (SysAttendMain) modelObj;
		SysAttendCategory category = main.getFdCategory();
		if(category ==null){
			category =CategoryUtil.getFdCategoryInfo(main);
			if(category ==null){
				//没有考勤组不允许打卡
				throw new Exception(" user is not in attendance group ");
			}else{
				main.setFdCategory(null);
				main.setFdHisCategory(CategoryUtil.getHisCategoryById(category.getFdId()));
			}
		} else {
			main.setFdHisCategory(null);
		}
		timeAreaRender(main,category.getFdShiftType());
		Date fdBaseWorkTime = main.getFdBaseWorkTime();
		main.setFdBaseWorkTime(fdBaseWorkTime);
		setFdDataType(main,category.getFdType());
		if(main.getDocStatus() ==null){
			main.setDocStatus(0);
		}
		super.update(modelObj);
		addLog(main, "update");
		if (logger.isDebugEnabled()) {
			logger.debug(
					"update sysAttendMain:" + main.getFdId()
							+ ";docCreatorName:"
							+ main.getDocCreator().getFdName() + ";fdStatus:"
							+ main.getFdStatus() + ";docCreateTime:"
							+ main.getDocCreateTime());
		}
		if(StringUtil.isNull(main.getFdAlterRecord())){
			main.setFdAlterRecord("有效考勤记录更新，记录对应的时间到原始记录！");
		}
		if (CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(category.getFdType())) {
			// 保存原始记录
			sysAttendSynDingDao.addRecord(main);
		}
	}


	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		SysAttendMain main = (SysAttendMain) modelObj;
		addLog(main, "delete");
		super.delete(modelObj);
	}

	private void setFdDataType(SysAttendMain main,Integer fdType) throws Exception {
		//考勤组的才处理
		if (CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(fdType)
				&& Integer.valueOf(AttendConstant.FD_DATE_TYPE[1])
						.equals(main.getFdDateType())) {
			ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
					.getBean("sysAttendCategoryService");
			// 休息日、节假日区分
			SysAttendCategory category = CategoryUtil.getFdCategoryInfo(main);
			boolean isTimeArea = Integer.valueOf(1)
					.equals(category.getFdShiftType());
			boolean isHoliday = sysAttendCategoryService.isHoliday(
					category.getFdId(), main.getFdWorkDate(),
					main.getDocCreator(), isTimeArea);
			if (isHoliday) {
				main.setFdDateType(
						Integer.valueOf(AttendConstant.FD_DATE_TYPE[2]));
			}
		}
	}

	/**
	 * 排班数据格式化
	 * 
	 * @param main
	 */
	private void timeAreaRender(SysAttendMain main,Integer fdShipFtye) throws Exception {

		if (Integer.valueOf(1).equals(fdShipFtye)) {
			main.setWorkTime(null);
		} else {
			main.setFdWorkKey(null);
		}
	}

	private void addLog(SysAttendMain main, String method) throws Exception {
		SysAttendMainLog mainLog = new SysAttendMainLog();
		mainLog.setFdCreateTime(new Date());
		mainLog.setFdAddress(main.getFdAddress());
		mainLog.setFdAlterRecord(main.getFdAlterRecord());
		mainLog.setFdCategoryId(main.getFdHisCategory() == null ? null : main.getFdHisCategory().getFdId());
		mainLog.setFdClientInfo(main.getFdClientInfo());
		mainLog.setFdDateType(main.getFdDateType());
		mainLog.setFdDesc(main.getFdDesc());
		mainLog.setFdDeviceId(main.getFdDeviceId());
		mainLog.setFdDeviceInfo(main.getFdDeviceInfo());
		mainLog.setFdIsAcross(main.getFdIsAcross());
		mainLog.setFdLat(main.getFdLat());
		mainLog.setFdLatLng(main.getFdLatLng());
		mainLog.setFdLng(main.getFdLng());
		mainLog.setFdLocation(main.getFdLocation());
		mainLog.setFdMethod(method);
		mainLog.setFdOutside(main.getFdOutside());
		mainLog.setFdState(main.getFdState());
		mainLog.setFdStatus(main.getFdStatus());
		mainLog.setFdWifiMacIp(main.getFdWifiMacIp());
		mainLog.setFdWifiName(main.getFdWifiName());
		if (main.getWorkTime() != null) {
			mainLog.setFdWorkId(main.getWorkTime().getFdId());
		} else {
			mainLog.setFdWorkId(null);
		}
		mainLog.setFdWorkType(main.getFdWorkType());
		mainLog.setFdWorkKey(main.getFdWorkKey());
		mainLog.setDocAlterorId(main.getDocAlteror() != null
				? main.getDocAlteror().getFdId() : null);
		mainLog.setDocCreateTime(main.getDocCreateTime());
		mainLog.setDocAlterTime(main.getDocAlterTime());
		mainLog.setDocCreatorId(main.getDocCreator() != null
				? main.getDocCreator().getFdId() : null);
		sysAttendMainLogDao.add(mainLog);
	}


}
