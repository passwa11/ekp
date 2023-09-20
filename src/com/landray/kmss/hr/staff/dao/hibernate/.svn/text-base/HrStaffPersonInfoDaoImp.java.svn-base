package com.landray.kmss.hr.staff.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.hr.staff.dao.IHrStaffPersonInfoDao;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 员工信息
 * 
 * @author 潘永辉 2016-12-27
 * 
 */
public class HrStaffPersonInfoDaoImp extends BaseDaoImp implements
		IHrStaffPersonInfoDao {

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		// 不能删除引用组织机构员工 
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) modelObj;
		//if (personInfo.getFdOrgPerson() == null) {//edit by huyh 手动以及无效的组织均能删除
			super.delete(modelObj);
		//}
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		return super.add(handlePersonInfo(modelObj));
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		super.update(handlePersonInfo(modelObj));
	}

	private IBaseModel handlePersonInfo(IBaseModel modelObj) {
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) modelObj;
		// 处理层级ID
		if (personInfo.getFdOrgPerson() != null) {
			personInfo.setFdHierarchyId(personInfo.getFdOrgPerson()
					.getFdHierarchyId());
		} else if (personInfo.getFdOrgParent() != null) {
			personInfo.setFdHierarchyId(personInfo.getFdOrgParent()
					.getFdHierarchyId()
					+ personInfo.getFdId()
					+ BaseTreeConstant.HIERARCHY_ID_SPLIT);
		}else if(personInfo.getFdParent()!=null) {
			personInfo.setFdHierarchyId(personInfo.getFdParent()
					.getFdHierarchyId()
					+ personInfo.getFdId()
					+ BaseTreeConstant.HIERARCHY_ID_SPLIT);
		}

		// 处理手机号
		String fdMobileNo = personInfo.getFdMobileNo();
		if (StringUtil.isNotNull(fdMobileNo)) {
			// 如果手机号是以+86开头，则强制去掉+86
			if (fdMobileNo.startsWith("+86")) {
				personInfo.setFdMobileNo(fdMobileNo.substring(3));
			}
		}
		//最后更新时间
		personInfo.setFdAlterTime(getDate(1));
		return personInfo;
	}

	private Date getDate(int sec) {
		Calendar cal = DateUtil.getCalendar(new Date());
		cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + sec);
		return cal.getTime();
	}

}
