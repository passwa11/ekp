package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceEducation;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceEducationService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 教育记录
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceEducationAction extends
		HrStaffPersonExperienceBaseAction {
	private IHrStaffPersonExperienceEducationService hrStaffPersonExperienceEducationService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonExperienceEducationService == null) {
            hrStaffPersonExperienceEducationService = (IHrStaffPersonExperienceEducationService) getBean("hrStaffPersonExperienceEducationService");
        }
		return hrStaffPersonExperienceEducationService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 解析其它筛选属性
		CriteriaUtil.buildHql(cv, hqlInfo,
				HrStaffPersonExperienceEducation.class);
		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		}
		whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonExperienceEducation", hqlInfo);
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public JSONArray handleJSONArray(List<HrStaffPersonExperienceBase> list) {
		JSONArray array = new JSONArray();
		for (HrStaffPersonExperienceBase _info : list) {
			HrStaffPersonExperienceEducation info = (HrStaffPersonExperienceEducation) _info;
			JSONObject obj = new JSONObject();
			obj.put("fdId", info.getFdId());
			obj.put("fdSchoolName", StringUtil
					.XMLEscape(info.getFdSchoolName()));
			obj.put("fdMajor", StringUtil.XMLEscape(info.getFdMajor()));
			obj.put("fdDegree", StringUtil.XMLEscape(info.getFdDegree()));
			obj.put("fdEducation", StringUtil.XMLEscape(info.getFdEducation()));
			obj.put("fdBeginDate", DateUtil.convertDateToString(info
					.getFdBeginDate(), DateUtil.PATTERN_DATE));
			obj.put("fdEndDate", DateUtil.convertDateToString(info
					.getFdEndDate(), DateUtil.PATTERN_DATE));
			obj.put("fdMemo", StringUtil.XMLEscape(info.getFdMemo()));
			array.add(obj);
		}
		return array;
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffPersonExperience.education.templetName");
	}

}
