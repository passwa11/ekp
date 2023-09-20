package com.landray.kmss.sys.simplecategory.pda;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.actions.BaseAction;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 * PDA简单分类 专供移动组件使用的简单分类Action
 * 
 * @author menglei
 */
public class PdaSimpleCategoryAction extends BaseAction {

	/**
	 * 功能区分类实现 专供PDA扩展使用的简单分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward pdaSimpleCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listTemplate", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String modelName = request.getParameter("templateClass");

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
			changeFindPageHQLInfoForPda(request, hqlInfo, modelName);
			Page page = getServiceImp(modelName).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		return mapping.findForward("pdaSimpleCategory");
	}

	/**
	 * 用于字段扩展
	 * 
	 * @param request
	 * @param hqlInfo
	 * @param tableName
	 */
	protected void buildValue(HttpServletRequest request, HQLInfo hqlInfo,
			String tableName) {
		String prefix = "ext.";
		Enumeration enume = request.getParameterNames();
		String whereBlock = hqlInfo.getWhereBlock();
		while (enume.hasMoreElements()) {
			String name = (String) enume.nextElement();
			if (name != null && name.trim().startsWith(prefix)) {
				String value = request.getParameter(name);
				if (StringUtil.isNotNull(value)) {
					name = name.trim().substring(prefix.length());
					String[] ___val = value.split("[;；,，]");

					String ___block = "";
					for (int i = 0; i < ___val.length; i++) {
						String param = "kmss_ext_props_"
								+ HQLUtil.getFieldIndex();
						___block = StringUtil.linkString(___block, " or ",
								tableName + "." + name + " =:" + param);
						hqlInfo.setParameter(param, ___val[i]);
					}
					whereBlock += " and (" + ___block + ")";
				}
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	/**
	 * 功能区分类实现 专供PDA扩展使用的简单分类(私有方法，构建HQLInfo)
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HQLInfo
	 *            hqlInfo
	 * @param String
	 *            modelName
	 * @return void
	 * @throws Exception
	 */
	private void changeFindPageHQLInfoForPda(HttpServletRequest request,
			HQLInfo hqlInfo, String modelName) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		String strPara = request.getParameter("parentId");
		String tableName = ModelUtil.getModelTableName(getServiceImp(modelName)
				.getModelName());
		if (strPara != null) {
			whereBlock += " and " + tableName + ".hbmParent.fdId = :strPara ";
			hqlInfo.setParameter("strPara", strPara);
		} else {
			whereBlock += " and " + tableName + ".hbmParent is null";
		}
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		hqlInfo.setWhereBlock(whereBlock);
		buildValue(request, hqlInfo, tableName);
	}

	/**
	 * 功能区分类实现 通过模块名称，根据数据字典获取扩展的业务service对象实例
	 * 
	 * @param String
	 *            modelName
	 * @return IBaseService
	 * @throws
	 */
	private IBaseService getServiceImp(String modelName) {
		SysDictModel templateModel = SysDataDict.getInstance().getModel(
				modelName);
		IBaseService iBaseService = (IBaseService) SpringBeanUtil
				.getBean(templateModel.getServiceBean());
		return iBaseService;
	}

}
