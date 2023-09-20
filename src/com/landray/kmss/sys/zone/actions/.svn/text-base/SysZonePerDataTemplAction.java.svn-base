package com.landray.kmss.sys.zone.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.zone.model.SysZonePerDataTempl;
import com.landray.kmss.sys.zone.service.ISysZonePerDataTemplService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;

/**
 * 个人资料目录模版设置 Action
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePerDataTemplAction extends ExtendAction {
	protected ISysZonePerDataTemplService sysZonePerDataTemplService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysZonePerDataTemplService == null) {
			sysZonePerDataTemplService = (ISysZonePerDataTemplService) getBean("sysZonePerDataTemplService");
		}
		return sysZonePerDataTemplService;
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);

		CriteriaValue cv = new CriteriaValue(request);

		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}

		CriteriaUtil.buildHql(cv, hqlInfo, SysZonePerDataTempl.class);
	}

}
