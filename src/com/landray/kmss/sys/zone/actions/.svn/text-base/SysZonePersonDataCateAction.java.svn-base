package com.landray.kmss.sys.zone.actions;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.model.SysZonePersonDataCate;
import com.landray.kmss.sys.zone.service.ISysZonePersonDataCateService;
import com.landray.kmss.sys.zone.service.ISysZonePersonDataService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;

/**
 * 个人资料目录设置 Action
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonDataCateAction extends ExtendAction {
	protected ISysZonePersonDataCateService sysZonePersonDataCateService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysZonePersonDataCateService == null){
			sysZonePersonDataCateService = (ISysZonePersonDataCateService) getBean("sysZonePersonDataCateService");
		}return sysZonePersonDataCateService;
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

		CriteriaUtil.buildHql(cv, hqlInfo, SysZonePersonDataCate.class);
	}

	/**
	 * 校验能否删除某个目录/模版
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validateDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding(SysZoneConstant.COMMON_ENCODING);
		PrintWriter writer = response.getWriter();
		ISysZonePersonDataService dataService = (ISysZonePersonDataService) this
				.getBean("sysZonePersonDataService");
		// 目录名
		String fdName = request.getParameter("fdName");
		// 目录模版Id
		String fdTempId = request.getParameter("fdTempId");
		if (StringUtil.isNotNull(fdName)) {
			// 校验能否删除某个目录
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" sysZonePersonData.fdName=:fdName ");
			hqlInfo.setParameter("fdName", fdName);
			if (dataService.findList(hqlInfo).size() > 0) {
				writer.print("true");
			}
		}
		if (StringUtil.isNotNull(fdTempId)) {
			// 校验能否删除某个模版
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" sysZonePersonData.fdDataCate.fdId=:fdTempId ");
			hqlInfo.setParameter("fdTempId", fdTempId);
			if (dataService.findList(hqlInfo).size() > 0) {
				writer.print("true");
			}
		}
		return null;
	}

	/**
	 * 校验能否新建
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validateAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding(SysZoneConstant.COMMON_ENCODING);
		PrintWriter writer = response.getWriter();
		if (this.getServiceImp(request).findList(" 1=1 ", null).size() > 0) {
			writer.print("false");
		} else {
			writer.print("true");
		}
		return null;
	}

}
