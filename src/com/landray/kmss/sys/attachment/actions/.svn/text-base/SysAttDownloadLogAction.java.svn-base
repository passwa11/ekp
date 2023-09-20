package com.landray.kmss.sys.attachment.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttDownloadLog;
import com.landray.kmss.sys.attachment.service.ISysAttDownloadLogService;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class SysAttDownloadLogAction extends ExtendAction {

	private ISysAttDownloadLogService sysAttDownloadLogService;
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysAttDownloadLogService == null) {
			sysAttDownloadLogService = (ISysAttDownloadLogService) getBean(
					"sysAttDownloadLogService");
		}
		return sysAttDownloadLogService;
	}

	protected ISysAttMainCoreInnerService sysAttMainService;

	protected ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) getBean(
					"sysAttMainService");
		}
		return sysAttMainService;
	}

	/**
	 * 跳转到“访问统计”的“附件下载记录”标签页
	 */
	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		return mapping.findForward("view");
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			int pageNo = 0;
			int rowSize = 0;
			if (s_pageno != null && s_pageno.length() > 0) {
				pageNo = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowSize = Integer.parseInt(s_rowsize);
			}
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			if (isReserve) {
                orderby += " desc";
            }
			String modelId = request.getParameter("modelId");
			String modelName = request.getParameter("modelName");
			Page page = null;
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setRowSize(rowSize);
			hqlInfo.setPageNo(pageNo);

			changeFindPageHQLInfo(request, hqlInfo);

			if (StringUtil.isNotNull(modelId)
					&& StringUtil.isNotNull(modelName)) {
				String whereBlock = hqlInfo.getWhereBlock();
				if (StringUtil.isNull(whereBlock)) {
                    whereBlock = "";
                } else {
                    whereBlock = "(" + whereBlock + ") and ";
                }
				String tableName = ModelUtil.getModelTableName(
						getServiceImp(request).getModelName());
				whereBlock += tableName + ".fdModelName = :fdModelName and "
						+ tableName + ".fdModelId = :fdModelId";
				hqlInfo.setWhereBlock(whereBlock);
				((ISysAttDownloadLogService) getServiceImp(request))
						.includeRecord(hqlInfo);
				hqlInfo.setParameter("fdModelName", modelName);
				hqlInfo.setParameter("fdModelId", modelId);
			}

			page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
			if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FINDALL,
					null)) {
				UserOperHelper.logFindAll(page.getList(),
						SysAttDownloadLog.class.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("list");
	}

	/**
	 * 单个附件下载新增下载日志
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addDownlodLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String attId = request.getParameter("fdId");
		if(StringUtil.isNotNull(attId)) {
			SysAttBase attModel = (SysAttBase) getSysAttMainService()
					.findByPrimaryKey(attId);
			((ISysAttDownloadLogService) getServiceImp(request))
					.addDownloadLogByAtt(attModel, new RequestContext(request));
		}
		return null;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttDownloadLog.class);
		String tableName = ModelUtil.getModelTableName(getServiceImp(
				request).getModelName());
		String whereBlock = hqlInfo.getWhereBlock();

		String fileName = cv.poll("fdFileName");
		if (StringUtil.isNotNull(fileName)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					tableName + ".fdFileName like :fdFileName");
			hqlInfo.setParameter("fdFileName", "%" + fileName + "%");
		}
		String docCreatorId = cv.poll("docCreator");
		if(StringUtil.isNotNull(docCreatorId)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					tableName + ".docCreatorId = :docCreatorId");
			hqlInfo.setParameter("docCreatorId", docCreatorId);
		}
		hqlInfo.setWhereBlock(whereBlock);
	}
	
	/**
	 * 下载数量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward count(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-count", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			JSONObject json = new JSONObject();

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setGettingCount(true);

			String type = request.getParameter("type");

			// 我下载的
			if ("create".equals(type)) {
				hqlInfo.setWhereBlock("sysAttDownloadLog.docCreatorId =:docCreatorId");
				hqlInfo.setParameter("docCreatorId",
						UserUtil.getUser().getFdId());
			}

			hqlInfo.setSelectBlock("count(distinct sysAttDownloadLog.fdId)");

			List<Long> count = this.getServiceImp(request).findValue(hqlInfo);
			json.put("count", count.get(0));

			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-count", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

}
