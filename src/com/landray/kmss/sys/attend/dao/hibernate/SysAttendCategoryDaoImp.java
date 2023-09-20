package com.landray.kmss.sys.attend.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.NativeQuery;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryDao;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.StringUtil;


/**
 * 签到事项数据访问接口实现
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryDaoImp extends BaseDaoImp
		implements ISysAttendCategoryDao {

	@SuppressWarnings("unchecked")
	@Override
	public Object[] findExistCategory(String hierarchyId, String exceptCategoryId) throws Exception {
		if (StringUtil.isNull(hierarchyId)) {
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysAttendCategory.fdType = 1 and sysAttendCategory.fdStatus = 1");
		List<SysAttendCategory> categories = this.findList(hqlInfo);
		List<SysAttendCategory> needcategories = new ArrayList<SysAttendCategory>();
		for (SysAttendCategory category : categories) {
			if (category.getFdId().equals(exceptCategoryId)) {
				continue;
			}
			List<SysOrgElement> excTargets = category.getFdExcTargets();
			for (SysOrgElement element : excTargets) {
				if (element.getFdHierarchyId().indexOf(hierarchyId) > -1) {
					continue;
				}
			}
			needcategories.add(category);
		}

		if (needcategories != null && needcategories.size() > 0) {
			StringBuffer sb2 = new StringBuffer();
			sb2.append(
					"select sys_org_element.fd_hierarchy_id ,sys_attend_category.fd_name from sys_attend_category_target "
							+ " left join sys_org_element on sys_attend_category_target.fd_org_id = sys_org_element.fd_id  "
							+ "left join sys_attend_category on sys_attend_category.fd_id =sys_attend_category_target.fd_category_id "
							+ "where sys_attend_category_target.fd_category_id in (");

			for (int i = 0; i < needcategories.size(); i++) {
				sb2.append("'").append(needcategories.get(i).getFdId()).append("'");
				if (i != needcategories.size() - 1) {
					sb2.append(",");
				}
			}
			sb2.append(")");
			NativeQuery _query = getHibernateSession().createNativeQuery(sb2.toString());
			List<Object[]> objs = _query.list();
			for (Object[] obj : objs) {
				String myHierarchyId = (String) obj[0];
				Object[] result = new Object[2];
				result[0] = obj[1];
				if (hierarchyId.equals(myHierarchyId)) {
					result[1] = "is";
					return result;
				}
				if (myHierarchyId.indexOf(hierarchyId) > -1) {
					result[1] = "part";
					return result;
				}
				if (hierarchyId.indexOf(myHierarchyId) > -1) {
					result[1] = "all";
					return result;
				}
			}
		}
		return null;
	}

	/**
	 * 设置班次有效或者无效
	 * 
	 * @param category
	 */
	public void setWorkTimeAvail(SysAttendCategory category) {
		if (category != null) {
			List<SysAttendCategoryWorktime> fdWorkTime = category
					.getFdWorkTime();
			if (fdWorkTime != null && !fdWorkTime.isEmpty()) {
				boolean isNotSameWorktime = Integer.valueOf(0)
						.equals(category.getFdShiftType())
						&& Integer.valueOf(1)
								.equals(category.getFdSameWorkTime());
				for (int i = 0; i < fdWorkTime.size(); i++) {
					SysAttendCategoryWorktime work = fdWorkTime.get(i);
					if (work.getFdStartTime() == null
							|| work.getFdEndTime() == null
							|| isNotSameWorktime) {
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
		SysAttendCategory category = (SysAttendCategory) modelObj;
		setWorkTimeAvail(category);
		getHibernateTemplate().save(category);
		TimeCounter.logCurrentTime("Dao-add", false, getClass());
		return category.getFdId();
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		modelObj.recalculateFields();
		SysAttendCategory category = (SysAttendCategory) modelObj;
		setWorkTimeAvail(category);
		getHibernateTemplate().saveOrUpdate(category);
	}

}
