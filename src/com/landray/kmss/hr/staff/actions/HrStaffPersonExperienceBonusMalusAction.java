package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBonusMalus;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBonusMalusService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 奖惩信息
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceBonusMalusAction extends
		HrStaffPersonExperienceBaseAction {
	private IHrStaffPersonExperienceBonusMalusService hrStaffPersonExperienceBonusMalusService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonExperienceBonusMalusService == null) {
            hrStaffPersonExperienceBonusMalusService = (IHrStaffPersonExperienceBonusMalusService) getBean("hrStaffPersonExperienceBonusMalusService");
        }
		return hrStaffPersonExperienceBonusMalusService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 解析其它筛选属性
		CriteriaUtil.buildHql(cv, hqlInfo,
				HrStaffPersonExperienceBonusMalus.class);
		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		}
		whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonExperienceBonusMalus", hqlInfo);
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public JSONArray handleJSONArray(List<HrStaffPersonExperienceBase> list) {
		JSONArray array = new JSONArray();
		for (HrStaffPersonExperienceBase _info : list) {
			HrStaffPersonExperienceBonusMalus info = (HrStaffPersonExperienceBonusMalus) _info;
			JSONObject obj = new JSONObject();
			obj.put("fdId", info.getFdId());
			obj.put("fdBonusMalusName", StringUtil.XMLEscape(info
					.getFdBonusMalusName()));
			obj.put("fdBonusMalusDate", DateUtil.convertDateToString(info
					.getFdBonusMalusDate(), DateUtil.PATTERN_DATE));
			obj.put("fdBonusMalusType", info.getFdBonusMalusType());
			obj.put("fdMemo", StringUtil.XMLEscape(info.getFdMemo()));
			array.add(obj);
		}
		return array;
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffPersonExperience.bonusMalus.templetName");
	}
}
