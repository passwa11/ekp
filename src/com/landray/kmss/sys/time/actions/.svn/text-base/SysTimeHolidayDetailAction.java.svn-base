package com.landray.kmss.sys.time.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.time.service.ISysTimeHolidayDetailService;
import com.landray.kmss.util.StringUtil;

/**
 * 节假日明细设置 Action
 * 
 * @author
 * @version 1.0 2017-09-26
 */
public class SysTimeHolidayDetailAction extends ExtendAction {
	protected ISysTimeHolidayDetailService sysTimeHolidayDetailService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysTimeHolidayDetailService == null) {
			sysTimeHolidayDetailService = (ISysTimeHolidayDetailService) getBean(
					"sysTimeHolidayDetailService");
		}
		return sysTimeHolidayDetailService;
	}

	@Override
	protected String getFindPageWhereBlock(HttpServletRequest request)
			throws Exception {
		String where = super.getFindPageWhereBlock(request);
		String type = request.getParameter("type");
		String fdHolidayId = request.getParameter("fdHolidayId");
		if (StringUtil.isNull(where)) {
			where = "1=1";
		}
		if (StringUtil.isNotNull(fdHolidayId)) {
			where += " and sysTimeHolidayDetail.fdHoliday.fdId='" + fdHolidayId + "'";
		}
		if (StringUtil.isNotNull(type)&&"pachwork".equals(type)) {
			where += " and sysTimeHolidayDetail.fdId in (select p.fdDetail.fdId from SysTimeHolidayPach p )";
		}
		return where;
	}
	
	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
			String curOrderBy) throws Exception {
		String order =  super.getFindPageOrderBy(request, curOrderBy);
		String type = request.getParameter("type");
		if (StringUtil.isNotNull(type)&&"pachwork".equals(type)) {
			if(StringUtil.isNotNull(order)){
				order = "fdPatchDay,"+order;
			}else{
				order = "fdPatchDay";
			}
		}else{
			if(StringUtil.isNotNull(order)){
				order = "fdStartDay,"+order;
			}else{
				order = "fdStartDay";
			}
		}
		return order;
	}
}
