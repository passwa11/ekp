package com.landray.kmss.hr.organization.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.hr.organization.model.HrOrganizationGrade;
import com.landray.kmss.util.*;
import org.springframework.dao.DataIntegrityViolationException;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant.AuthCheck;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.hr.organization.forms.HrOrganizationRankForm;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.organization.service.IHrOrganizationRankService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrOrganizationRankAction extends ExtendAction {

    private IHrOrganizationRankService hrOrganizationRankService;

    @Override
    public IHrOrganizationRankService getServiceImp(HttpServletRequest request) {
        if (hrOrganizationRankService == null) {
            hrOrganizationRankService = (IHrOrganizationRankService) getBean("hrOrganizationRankService");
        }
        return hrOrganizationRankService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrOrganizationRank.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.organization.model.HrOrganizationRank.class);
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoModel(hqlInfo, request);
        String whereBlock = StringUtil.isNull(hqlInfo.getWhereBlock()) ? "1=1" : hqlInfo.getWhereBlock();
        CriteriaValue cv = new CriteriaValue(request);
        String fdRank = cv.poll("fdRank");
        if (StringUtil.isNotNull(fdRank)) {
            whereBlock += " and hrOrganizationRank.fdName like :fdRank";
            hqlInfo.setParameter("fdRank", "%" + fdRank + "%");
        }
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_READER);
        hqlInfo.setOrderBy(
                "hrOrganizationRank.fdGrade.fdWeight desc,hrOrganizationRank.fdWeight desc");
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrOrganizationRankForm hrOrganizationRankForm = (HrOrganizationRankForm) super.createNewForm(mapping, form, request, response);
        ((IHrOrganizationRankService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return hrOrganizationRankForm;
    }

    public ActionForward updateRankPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdId");
            if (StringUtil.isNotNull(fdId)) {
                IExtendForm rtnForm = null;
                IBaseModel model = getServiceImp(request).findByPrimaryKey(fdId, null, true);
                if (model != null) {
                    rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model,
                            new RequestContext(request));
                    HrOrganizationRankForm rankForm = (HrOrganizationRankForm) rtnForm;
                    rankForm.setMethod_GET("edit");
                }
                if (rtnForm == null) {
                    throw new NoRecordException();
                }
                request.setAttribute(getFormName(rtnForm, request), rtnForm);

            } else {
                ActionForm newForm = createNewForm(mapping, form, request, response);
                HrOrganizationRankForm rankForm = (HrOrganizationRankForm) newForm;
                rankForm.setMethod_GET("add");
                if (newForm != form) {
                    request.setAttribute(getFormName(newForm, request), newForm);
                }
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
                    .save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("updateRankPage", mapping, form, request, response);
        }
    }

    public ActionForward deleteRankPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            String ids = request.getParameter("fdIds");
            List<HrOrganizationRank> list = getServiceImp(request).findByPrimaryKeys(ids.split(";"));
            request.setAttribute("list", list);

        } catch (Exception e) {
            messages.addError(e);
        }

        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
                    .save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("deleteRankPage", mapping, form, request, response);
        }
    }

    /**
     * <p>导入职级</p>
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author sunj
     */
    public ActionForward importRank(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        JSONObject importResult = new JSONObject();
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            importResult.put("otherErrors", new JSONArray());
            HrOrganizationRankForm mainForm = (HrOrganizationRankForm) form;
            FormFile file = mainForm.getFile();
            importResult = getServiceImp(request).addImportData(file.getInputStream(), request.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
            importResult.put("hasError", 1);
            importResult.put("importMsg", ResourceUtil.getString("hr-organization:hr.organization.import.fail"));
            importResult.getJSONArray("otherErrors").add(ResourceUtil.getString("hr-organization:hr.organization.import.failErrormsg") + e.getMessage());
        }
        String result = HrOrgUtil.replaceCharacter(importResult.toString());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("<script>parent.callback(" + result + ");</script>");
        return null;
    }

    /**
     * <p>导出</p>
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author sunj
     */
    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            HQLInfo hqlInfo = new HQLInfo();
            WorkBook wb = getServiceImp(request).export(hqlInfo, request);
            ExcelOutput output = new ExcelOutputImp();
            output.output(wb, response);

        } catch (Exception e) {
            messages.addError(e);
        }
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }

    /**
     * <p>下载导入模板</p>
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author sunj
     */
    public ActionForward downloadTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            WorkBook wb = getServiceImp(request).buildTemplateWorkbook(request);
            ExcelOutput output = new ExcelOutputImp();
            output.output(wb, response);
        } catch (Exception e) {
            messages.addError(e);
        }
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-delete", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            String id = request.getParameter("fdId");
            if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).delete(id);
            }
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                messages.setHasError();
                KmssMessage message = new KmssMessage("hr-organization:hr.organization.delete.message");
                message.setMessageType(KmssMessage.MESSAGE_ERROR);
                messages.addError(message, e);
            } else {
                messages.addError(e);
            }
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
        if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
    }

    public void checkNameUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) {
        KmssMessages messages = new KmssMessages();
        try {
            JSONObject json = new JSONObject();
            String fdName = request.getParameter("fdName");
            String fdId = request.getParameter("fdId");
            String fdGradeId = request.getParameter("fdGradeId");
            boolean result = getServiceImp(request).checkNameUnique(fdName,
                    fdId, fdGradeId);
            json.put("result", result);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(json.toString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            messages.addError(e);
            e.printStackTrace();
        }
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @description: 通过职等ID获取职级List
     * @return: void
     * @author: wangjf
     * @time: 2021/5/27 3:43 下午
     */
    public void ajaxFindList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                             HttpServletResponse response) {
        KmssMessages messages = new KmssMessages();
        try {
            String fdGradeId = request.getParameter("fdGradeId");
            HrOrganizationGrade hrOrganizationGrade = new HrOrganizationGrade();
            hrOrganizationGrade.setFdId(fdGradeId);
            List<HrOrganizationRank> list = getServiceImp(request).findByFdGrade(hrOrganizationGrade);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(toJsonArray(list));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            messages.addError(e);
            e.printStackTrace();
        }
    }

    private JSONArray toJsonArray(List<HrOrganizationRank> list){
        JSONArray jsonArray = new JSONArray();
        if(ArrayUtil.isEmpty(list)){
            return jsonArray;
        }
        for(HrOrganizationRank hrOrganizationRank :list){

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("fdId",hrOrganizationRank.getFdId());
            jsonObject.accumulate("fdName",hrOrganizationRank.getFdName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    
    
	public ActionForward dialog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String keyword = request.getParameter("q._keyword");

			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
				orderby += " desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			String whereBlock=" 1=1 ";
			if(StringUtil.isNotNull(keyword)){
			      whereBlock += " and hrOrganizationRank.fdName like :keyword";
			      hqlInfo.setParameter("keyword", "%" + keyword + "%");
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("dialog", mapping, form, request, response);
		}
	}
}
