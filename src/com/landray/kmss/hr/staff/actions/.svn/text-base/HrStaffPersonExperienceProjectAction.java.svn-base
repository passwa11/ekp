package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceProject;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceProjectService;
import com.landray.kmss.util.DateUtil;

/**
 * 项目经历
 * 
 * @author 朱湖强 2017-01-09
 * 
 */
public class HrStaffPersonExperienceProjectAction extends
		HrStaffPersonExperienceBaseAction {
	private IHrStaffPersonExperienceProjectService hrStaffPersonExperienceProjectService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonExperienceProjectService == null) {
            hrStaffPersonExperienceProjectService = (IHrStaffPersonExperienceProjectService) getBean("hrStaffPersonExperienceProjectService");
        }
		return hrStaffPersonExperienceProjectService;
	}

	@Override
	public JSONArray handleJSONArray(List<HrStaffPersonExperienceBase> list) {
		JSONArray array = new JSONArray();
		for (HrStaffPersonExperienceBase _info : list) {
			HrStaffPersonExperienceProject info = (HrStaffPersonExperienceProject) _info;
			JSONObject obj = new JSONObject();
			obj.put("fdId", info.getFdId());
			obj.put("fdName", info.getFdName());
			obj.put("fdRole", info.getFdRole());
			obj.put("fdMemo", info.getFdMemo());
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
		
		return "";
	}

}
