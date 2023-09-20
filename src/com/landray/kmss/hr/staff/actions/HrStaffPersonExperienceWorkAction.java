package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceWork;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceWorkService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 工作经历
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceWorkAction extends
		HrStaffPersonExperienceBaseAction {
	private IHrStaffPersonExperienceWorkService hrStaffPersonExperienceWorkService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonExperienceWorkService == null) {
            hrStaffPersonExperienceWorkService = (IHrStaffPersonExperienceWorkService) getBean("hrStaffPersonExperienceWorkService");
        }
		return hrStaffPersonExperienceWorkService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 解析其它筛选属性
		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffPersonExperienceWork.class);
		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		}
		whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonExperienceWork", hqlInfo);
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public JSONArray handleJSONArray(List<HrStaffPersonExperienceBase> list) {
		JSONArray array = new JSONArray();
		for (HrStaffPersonExperienceBase _info : list) {
			HrStaffPersonExperienceWork info = (HrStaffPersonExperienceWork) _info;
			JSONObject obj = new JSONObject();
			obj.put("fdId", info.getFdId());
			obj.put("fdCompany", StringUtil.XMLEscape(info.getFdCompany()));
			obj.put("fdPosition", StringUtil.XMLEscape(info.getFdPosition()));
			obj.put("fdDescription", StringUtil.XMLEscape(info
					.getFdDescription()));
			obj.put("fdReasons", StringUtil.XMLEscape(info.getFdReasons()));
			obj.put("fdBeginDate", DateUtil.convertDateToString(info
					.getFdBeginDate(), DateUtil.PATTERN_DATE));
			obj.put("fdEndDate", DateUtil.convertDateToString(info
					.getFdEndDate(), DateUtil.PATTERN_DATE));
			array.add(obj);
		}
		return array;
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffPersonExperience.work.templetName");
	}

}
