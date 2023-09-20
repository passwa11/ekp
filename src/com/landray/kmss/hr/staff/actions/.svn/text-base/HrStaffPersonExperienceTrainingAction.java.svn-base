package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceTraining;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceTrainingService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 培训记录
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceTrainingAction extends
		HrStaffPersonExperienceBaseAction {
	private IHrStaffPersonExperienceTrainingService hrStaffPersonExperienceTrainingService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonExperienceTrainingService == null) {
            hrStaffPersonExperienceTrainingService = (IHrStaffPersonExperienceTrainingService) getBean("hrStaffPersonExperienceTrainingService");
        }
		return hrStaffPersonExperienceTrainingService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 解析其它筛选属性
		CriteriaUtil.buildHql(cv, hqlInfo,
				HrStaffPersonExperienceTraining.class);
		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		}
		whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonExperienceTraining", hqlInfo);
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public JSONArray handleJSONArray(List<HrStaffPersonExperienceBase> list) {
		JSONArray array = new JSONArray();
		for (HrStaffPersonExperienceBase _info : list) {
			HrStaffPersonExperienceTraining info = (HrStaffPersonExperienceTraining) _info;
			JSONObject obj = new JSONObject();
			obj.put("fdId", info.getFdId());
			obj.put("fdTrainingName", StringUtil.XMLEscape(info
					.getFdTrainingName()));
			obj.put("fdTrainingUnit", StringUtil.XMLEscape(info
					.getFdTrainingUnit()));
			obj.put("fdBeginDate", DateUtil.convertDateToString(info
					.getFdBeginDate(), DateUtil.PATTERN_DATE));
			obj.put("fdEndDate", DateUtil.convertDateToString(info
					.getFdEndDate(), DateUtil.PATTERN_DATE));
			obj.put("fdMemo", StringUtil.XMLEscape(info.getFdMemo()));
			obj.put("fdCertificate", StringUtil.XMLEscape(info.getFdCertificate()));
			array.add(obj);
		}
		return array;
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffPersonExperience.training.templetName");
	}

}
