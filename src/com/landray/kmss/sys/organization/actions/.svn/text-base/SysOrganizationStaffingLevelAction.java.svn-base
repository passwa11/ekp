package com.landray.kmss.sys.organization.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONArray;
import org.springframework.dao.DataIntegrityViolationException;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.forms.SysOrganizationStaffingLevelForm;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * 职级配置 Action
 * 
 * @author
 * @version 1.0 2015-07-23
 */
public class SysOrganizationStaffingLevelAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	protected ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrganizationStaffingLevelService == null) {
            sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService) getBean("sysOrganizationStaffingLevelService");
        }
		return sysOrganizationStaffingLevelService;
	}

	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = 0;
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
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
			// 记录日志
			if (UserOperHelper.allowLogOper("Action_FindAll", getServiceImp(request).getModelName())) {
				UserOperContentHelper.putFinds(page.getList());
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-select", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("select", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");

			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
                    getServiceImp(request).delete(authIds);
                }
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (DataIntegrityViolationException e) {
			messages
					.addError(
							new KmssMessage(
									"sys-organization:sysOrganizationStaffingLevel.delete.fail"),
							e);
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = request.getParameter("fdId");
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).delete(id);
            }
		} catch (DataIntegrityViolationException e) {
			SysOrganizationStaffingLevel sysOrganizationStaffingLevel = (SysOrganizationStaffingLevel) getServiceImp(
					request).findByPrimaryKey(id);
			messages
					.addError(
							new KmssMessage(
									"sys-organization:sysOrganizationStaffingLevel.delete.fail"),
							e);
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	public ActionForward listJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "";
		String name = request.getParameter("name");
		String level = request.getParameter("level");
		String sortType = request.getParameter("sortType");
		if (StringUtil.isNotNull(name)) {
			name = name.trim();
		}
		if (StringUtil.isNotNull(level)) {
			level = level.trim();
		}
		if (StringUtil.isNotNull(name)) {
			String where_lang = "";
			String currentLocaleCountry = null;
			if (SysLangUtil.isLangEnabled()) {
				currentLocaleCountry = SysLangUtil
						.getCurrentLocaleCountry();
				if(StringUtil.isNotNull(currentLocaleCountry)&&currentLocaleCountry.equals(SysLangUtil.getOfficialLang())){
					currentLocaleCountry = null;
				}
			}
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				where_lang = " or sysOrganizationStaffingLevel.fdName"
						+ currentLocaleCountry + " like :fdName";
			}
			whereBlock += "sysOrganizationStaffingLevel.fdName like :fdName"
					+ where_lang;
			hqlInfo.setParameter("fdName", "%" + name + "%");
		} else {
			whereBlock += " 1=1 ";
		}
		if (StringUtil.isNotNull(level)) {
			whereBlock += " and sysOrganizationStaffingLevel.fdLevel = :fdLevel";
			hqlInfo.setParameter("fdLevel", Integer.parseInt(level));
		}
		hqlInfo.setWhereBlock(whereBlock);
		if ("1".equals(sortType)) {
			hqlInfo.setOrderBy(" sysOrganizationStaffingLevel.fdLevel asc");
		} else if ("2".equals(sortType)) {
			hqlInfo.setOrderBy(" sysOrganizationStaffingLevel.fdLevel desc");
		}

		// hqlInfo.setOrderBy(" sysOrganizationStaffingLevel.fdLevel asc");
		List staffingLevelList = getServiceImp(request).findList(hqlInfo);
		// 记录日志
		if (UserOperHelper.allowLogOper("Action_FindAll", getServiceImp(request).getModelName())) {
			UserOperContentHelper.putFinds(staffingLevelList);
		}
		JSONArray jsonArray = new JSONArray();
		if (staffingLevelList != null && staffingLevelList.size() > 0) {

			for (int i = 0; i < staffingLevelList.size(); i++) {
				SysOrganizationStaffingLevel sysOrganizationStaffingLevel = (SysOrganizationStaffingLevel) staffingLevelList
						.get(i);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", sysOrganizationStaffingLevel.getFdId());
				jsonObject.put("name", sysOrganizationStaffingLevel.getFdName());
				jsonObject.put("level", sysOrganizationStaffingLevel
						.getFdLevel());
				jsonArray.add(jsonObject);
			}
		}
		response.setContentType("text/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		// System.out.println(jsonArray.toString());
		response.getWriter().write(jsonArray.toString());
		return null;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo, SysOrganizationStaffingLevel.class);
	}
	
	/**
	 * 下载模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responses
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			// 模板名称
			String templetName = ResourceUtil
					.getString(
							"sys-organization:sysOrganizationStaffingLevel.templetName");
			// 构建模板文件
			HSSFWorkbook workbook = ((ISysOrganizationStaffingLevelService) getServiceImp(
					request)).buildTempletWorkBook();

			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;fileName="
							+ SysOrgUtil.encodeFileName(request, templetName));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			return null;
		} catch (IOException e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 导入
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fileUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-fileUpload", true, getClass());
		KmssMessage message = null;

		SysOrganizationStaffingLevelForm staffingLevelForm = (SysOrganizationStaffingLevelForm) form;
		FormFile file = staffingLevelForm.getFile();
		String resultMsg = null;
		boolean state = false;
		if (file == null || file.getFileSize() < 1) {
			resultMsg = ResourceUtil.getString(
					"sysOrganizationStaffingLevel.import.noFile",
					"sys-organization");
		} else {
			try {
				message = ((ISysOrganizationStaffingLevelService) getServiceImp(
						request)).saveImportData(staffingLevelForm);
				state = message.getMessageType() == KmssMessage.MESSAGE_COMMON;
			} catch (Exception e) {
				message = new KmssMessage(e.getMessage());
				logger.error(e.toString());
			}
			resultMsg = message.getMessageKey();
		}
		// 保存导入信息
		request.setAttribute("resultMsg", resultMsg);
		// 状态
		request.setAttribute("state", state);
		return getActionForward("fileUpload", mapping, form, request,
				response);
	}
}
