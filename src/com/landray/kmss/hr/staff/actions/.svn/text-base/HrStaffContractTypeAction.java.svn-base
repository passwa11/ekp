package com.landray.kmss.hr.staff.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.hr.staff.service.IHrStaffContractTypeService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;

public class HrStaffContractTypeAction extends ExtendAction {

	private IHrStaffContractTypeService hrStaffContractTypeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffContractTypeService == null) {
            hrStaffContractTypeService = (IHrStaffContractTypeService) getBean(
                    "hrStaffContractTypeService");
        }
		return hrStaffContractTypeService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		// 新UED查询方式
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffContractType.class);
	}

}
