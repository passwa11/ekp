package com.landray.kmss.hr.staff.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.util.StringUtil;

/**
 * 薪酬福利明细
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public class HrStaffEmolumentWelfareDetaliedAction extends ExtendAction {
	private IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService;

	@Override
	protected IHrStaffEmolumentWelfareDetaliedService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffEmolumentWelfareDetaliedService == null) {
			hrStaffEmolumentWelfareDetaliedService = (IHrStaffEmolumentWelfareDetaliedService) getBean("hrStaffEmolumentWelfareDetaliedService");
		}
		return hrStaffEmolumentWelfareDetaliedService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String personInfoId = request.getParameter("personInfoId");
		StringBuffer whereBlock = new StringBuffer();
		String _whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNotNull(_whereBlock)) {
			whereBlock.append(_whereBlock);
		} else {
			whereBlock.append("1 = 1");
		}
		if (StringUtil.isNotNull(personInfoId)) {
			whereBlock
					.append(" and hrStaffEmolumentWelfareDetalied.fdPersonInfo.fdId = :personInfoId");
			hqlInfo.setParameter("personInfoId", personInfoId);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

}
