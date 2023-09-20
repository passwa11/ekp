package com.landray.kmss.tic.jdbc.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.tic.core.util.RecursionUtil;
import com.landray.kmss.tic.jdbc.forms.TicJdbcMappManageForm;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.model.TicJdbcMappCategory;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcMappCategoryService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcMappManageService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONSerializer;

/**
 * 映射配置 Action
 * 
 * @author
 * @version 1.0 2013-07-24
 */
public class TicJdbcMappManageAction extends ExtendAction {
	protected ITicJdbcMappManageService ticJdbcMappManageService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (ticJdbcMappManageService == null) {
			ticJdbcMappManageService = (ITicJdbcMappManageService) getBean("ticJdbcMappManageService");
		}
		return ticJdbcMappManageService;
	}

	public ActionForward getData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getData", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			RequestContext requestInfo = new RequestContext(request);
			Map<String,Object> resultData = ((ITicJdbcMappManageService) getServiceImp(request))
					.getTableData(requestInfo);
			request.setAttribute("titleList", resultData.get("titleList"));
			request.setAttribute("resultList", resultData.get("resultList"));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			String contextPath = request.getContextPath();
			request.setAttribute("KMSS_Parameter_StylePath", contextPath
					+ "/resource/style/default/");
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);

			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("dataList", mapping, form, request,
					response);
		}
	}

	@Override
    protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicJdbcMappManageForm mainForm = (TicJdbcMappManageForm) form;

		String fdTemplateId = request.getParameter("fdtemplatId");

		if (StringUtil.isNull(fdTemplateId)) {
			return mainForm;
		}

		ITicJdbcMappCategoryService service = (ITicJdbcMappCategoryService) SpringBeanUtil
				.getBean("ticJdbcMappCategoryService");
		TicJdbcMappCategory category = (TicJdbcMappCategory) service
				.findByPrimaryKey(fdTemplateId);
		mainForm.setDocCategoryId(fdTemplateId);
		mainForm.setDocCategoryName(category.getFdName());
		return mainForm;
	}

	@Override
    public ActionForward list(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		String forward = request.getParameter("forward");
		String rowNum = request.getParameter("rowNum");
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String fdtemplatId = request.getParameter("fdtemplatId");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
				orderby += " desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			List list = page.getList();
			Map map = ticJdbcMappManageService.getDataSource();
			request.setAttribute("dataSoure", map);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else if (StringUtils.isNotEmpty(forward)) {
			request.setAttribute("rowNum", rowNum);
			return getActionForward(forward, mapping, form, request, response);
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String hql = hqlInfo.getWhereBlock();
		if (!StringUtil.isNull(categoryId)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticJdbcMappManage.docCategory.fdId like :categoryId ");
			hqlInfo.setParameter("categoryId", "%" + categoryId + "%");
		}
		hqlInfo.setWhereBlock(hql);

	}

	@SuppressWarnings("unchecked")
	public void queryObjectById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List list = new ArrayList();
		String funcBaseId = request.getParameter("funcBaseId");
		if (StringUtils.isNotEmpty(funcBaseId)) {
			ITicJdbcDataSetService service = (ITicJdbcDataSetService) SpringBeanUtil
					.getBean("ticJdbcDataSetService");
			TicJdbcDataSet ticJdbcDataSet = (TicJdbcDataSet) service
					.findByPrimaryKey(funcBaseId);
			String dbId = ticJdbcDataSet.getFdDataSource();
			String fdDataSourceSql = RecursionUtil
					.frommatSql(ticJdbcDataSet.getFdSqlExpression());
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			list.add(dbId);
			// 去掉sql结尾的分号
			if (fdDataSourceSql.indexOf(";") != -1) {
				fdDataSourceSql = fdDataSourceSql.substring(0, fdDataSourceSql
						.length() - 1);
			}
			if (fdDataSourceSql.indexOf("[") != -1) {
				fdDataSourceSql = fdDataSourceSql.replaceAll("[\\[\\]]", "");
			}
			Map paraMap = new HashMap();
			paraMap.put("dbId", dbId);
			paraMap.put("sourceSql", fdDataSourceSql);

			// 时间戳下拉框
			List fieldList = ((ITicJdbcMappManageService) getServiceImp(
					request))
							.getTableFieldData(paraMap);
			if (fieldList != null && fieldList.size() > 0) {
				list.add(fieldList);
			}
			out.print(JSONSerializer.toJSON(list).toString());
		}

	}

	@Override
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		String type = request.getParameter("type");
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			// return getActionForward("editPage", mapping, form, request,
			// response);
			request.setAttribute("type", type);
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	public ActionForward getTabFieldInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		IXMLDataBean ticJdbcLoadTableFieldService = (IXMLDataBean) SpringBeanUtil
				.getBean("ticJdbcLoadTableFieldService");
		RequestContext requestInfo = new RequestContext(request);
		List list = ticJdbcLoadTableFieldService.getDataList(requestInfo);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(list);
		return null;
	}

	public ActionForward getTargetDBTab(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getTargetDBTab", true, getClass());
		KmssMessages messages = new KmssMessages();
		IXMLDataBean ticJdbcLoadDBTablService = (IXMLDataBean) SpringBeanUtil
				.getBean("ticJdbcLoadDBTablService");
		RequestContext requestInfo = new RequestContext(request);
		List list = ticJdbcLoadDBTablService.getDataList(requestInfo);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(JSONSerializer.toJSON(list).toString());
		return null;
	}

	/**
	 * 用于默认选择日志表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLogTabName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		List list = new ArrayList();
		try {
			IXMLDataBean ticJdbcLoadDBTablService = (IXMLDataBean) SpringBeanUtil
					.getBean("ticJdbcLoadDBTablService");
			RequestContext requestInfo = new RequestContext(request);
			list = ticJdbcLoadDBTablService.getDataList(requestInfo);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("");
			return null;
		}
		out.print(JSONSerializer.toJSON(list).toString());
		return null;
	}

}
