package com.landray.kmss.hr.staff.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;

/**
 * 考勤管理基类
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public abstract class HrStaffAttendanceManageBaseAction extends
		HrStaffImportAction {

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		String _fdDept = cv.poll("_fdDept");

		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}

		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			whereBlock
					.append(" and (fdPersonInfo.fdName like :fdKey or fdPersonInfo.fdOrgPerson.fdName like :fdKey");
			whereBlock
					.append(" or fdPersonInfo.fdOrgPerson.fdLoginName like :fdKey");
			whereBlock
					.append(" or fdPersonInfo.fdMobileNo like :fdKey or fdPersonInfo.fdOrgPerson.fdMobileNo like :fdKey");
			whereBlock
					.append(" or fdPersonInfo.fdEmail like :fdKey or fdPersonInfo.fdOrgPerson.fdEmail like :fdKey)");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}
		// 员工状态
		String[] _fdStatus = cv.polls("_fdStatus");
		if (_fdStatus != null && _fdStatus.length > 0) {
			List<String> fdStatus = new ArrayList<String>();
			boolean isNull = false;
			for (String _fdStatu : _fdStatus) {
				if ("official".equals(_fdStatu)) {
					isNull = true;
				}
				fdStatus.add(_fdStatu);
			}
			whereBlock.append(" and (fdPersonInfo.fdStatus in (:fdStatus)");
			if (isNull) {
				whereBlock.append(" or fdPersonInfo.fdStatus is null");
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdStatus", fdStatus);
		}
		// 部门
		if (StringUtil.isNotNull(_fdDept)) {
			whereBlock
					.append(" and (fdPersonInfo.fdOrgParent.fdId = :fdDept or fdPersonInfo.fdOrgPerson.hbmParent.fdId = :fdDept)");
			hqlInfo.setParameter("fdDept", _fdDept);
		}

		hqlInfo.setWhereBlock(whereBlock.toString());
	}

}
