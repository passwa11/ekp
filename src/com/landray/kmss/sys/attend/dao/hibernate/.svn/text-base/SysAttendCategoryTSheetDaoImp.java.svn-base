package com.landray.kmss.sys.attend.dao.hibernate;

import java.util.List;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryTSheetDao;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-06-12
 */
public class SysAttendCategoryTSheetDaoImp extends BaseDaoImp
		implements ISysAttendCategoryTSheetDao {

	/**
	 * 设置班次有效或者无效
	 * 
	 * @param category
	 */
	public void setWorkTimeAvail(SysAttendCategoryTimesheet tSheet) {
		if (tSheet != null) {
			List<SysAttendCategoryWorktime> fdWorkTime = tSheet.getFdWorkTime();
			if (fdWorkTime != null && !fdWorkTime.isEmpty()) {
				for (int i = 0; i < fdWorkTime.size(); i++) {
					SysAttendCategoryWorktime work = fdWorkTime.get(i);
					if (work.getFdStartTime() == null
							|| work.getFdEndTime() == null) {
						work.setFdIsAvailable(false);
					}
				}
			}
		}
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		TimeCounter.logCurrentTime("Dao-add", true, getClass());
		modelObj.recalculateFields();
		SysAttendCategoryTimesheet tSheet = (SysAttendCategoryTimesheet) modelObj;
		setWorkTimeAvail(tSheet);
		getHibernateTemplate().save(tSheet);
		TimeCounter.logCurrentTime("Dao-add", false, getClass());
		return tSheet.getFdId();
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		modelObj.recalculateFields();
		SysAttendCategoryTimesheet tSheet = (SysAttendCategoryTimesheet) modelObj;
		setWorkTimeAvail(tSheet);
		getHibernateTemplate().saveOrUpdate(tSheet);
	}

}
