package com.landray.kmss.sys.attend.actions;

import com.google.common.base.Joiner;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryForm;
import com.landray.kmss.sys.attend.forms.SysAttendHisCategoryForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class SysAttendHisCategoryAction extends ExtendAction {

    private ISysAttendHisCategoryService sysAttendHisCategoryService;

    @Override
    public ISysAttendHisCategoryService getServiceImp(HttpServletRequest request) {
        if (sysAttendHisCategoryService == null) {
            sysAttendHisCategoryService = (ISysAttendHisCategoryService) getBean("sysAttendHisCategoryService");
        }
        return sysAttendHisCategoryService;
    }

    private ISysAttendCategoryService sysAttendCategoryService;

    public IBaseService getSysAttendCategoryService() {
        if (sysAttendCategoryService == null) {
            sysAttendCategoryService = (ISysAttendCategoryService) getBean("sysAttendCategoryService");
        }
        return sysAttendCategoryService;
    }
    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
//        CriteriaValue cv = new CriteriaValue(request);
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" sysAttendHisCategory.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
        String fdName = request.getParameter("q.fdName");
        String[] fdBeginTime = request.getParameterValues("q.fdBeginTime");
        if (StringUtil.isNotNull(fdName)) {
            whereSql.append(" and sysAttendHisCategory.fdName like :fdName");
            hqlInfo.setParameter("fdName", "%" + fdName + "%");
        }
        if(fdBeginTime !=null){
            String beginTime = fdBeginTime[0];
            String endTime = fdBeginTime[1];
            if(StringUtil.isNotNull(beginTime) && StringUtil.isNotNull(endTime) ){
                whereSql.append(" and sysAttendHisCategory.fdBeginTime <= :fdBeginTime ");
                whereSql.append(" and sysAttendHisCategory.fdEndTime > :endTime ");
                hqlInfo.setParameter("fdBeginTime", DateUtil.convertStringToDate(beginTime));
                hqlInfo.setParameter("endTime", DateUtil.convertStringToDate(endTime));
            } else if(StringUtil.isNotNull(beginTime)){
                whereSql.append(" and sysAttendHisCategory.fdBeginTime >= :beginTime ");
                hqlInfo.setParameter("beginTime", DateUtil.convertStringToDate(beginTime));
            } else if(StringUtil.isNotNull(endTime)){
                whereSql.append(" and sysAttendHisCategory.fdEndTime <= :beginTime ");
                hqlInfo.setParameter("beginTime", DateUtil.convertStringToDate(endTime));
            }
        }
        whereSql.append(" and sysAttendHisCategory.fdEndTime < :lastEnd ");
        Calendar date = Calendar.getInstance();
        date.set(2099,11,30);
        hqlInfo.setParameter("lastEnd", date.getTime());
        hqlInfo.setWhereBlock(whereSql.toString());
    }

    @Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-update", true, getClass());
        KmssMessages messages = new KmssMessages();
        TimeCounter.logCurrentTime("Action-update", false, getClass());
        SysAttendHisCategoryForm sysAttendHisCategoryForm= (SysAttendHisCategoryForm)form;
        try {
            //执行修改
            getServiceImp(request).updateHisCategory(sysAttendHisCategoryForm);
        } catch (Exception e) {
            messages.addError(e);
        }
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton("sys-attend:sysAttend.tree.config.stat.title", "/sys/attend/#j_path=%2Fmanagement", false)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);

            return getActionForward("success", mapping, form, request, response);
        }
    }
    @Override
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-edit", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            //loadActionForm(mapping,form,request,response);
            SysAttendHisCategoryForm sysAttendHisCategoryForm=(SysAttendHisCategoryForm)form;
            String fdId=request.getParameter("fdId");
            SysAttendHisCategory his = (SysAttendHisCategory) getServiceImp(request).findByPrimaryKey(fdId);
            if(his !=null) {
                sysAttendHisCategoryForm.setFdId(his.getFdId());
                sysAttendHisCategoryForm.setFdName(his.getFdName());
                sysAttendHisCategoryForm.setFdCategoryId(his.getFdCategoryId());
                sysAttendHisCategoryForm.setFdBeginTime(his.getFdBeginTime());
                sysAttendHisCategoryForm.setFdEndTime(his.getFdEndTime());
                SysAttendCategory category = CategoryUtil.getCategoryById(fdId);
                //排除人员
                //变更人员+考勤对象人员
                Set<SysOrgElement> changeOrgList = getServiceImp(request).getChangeOrgByHisId(his.getFdId());
                Set<SysOrgElement> targetOrgList =  getServiceImp(request).getTargetOrgByHisId(his.getFdId());
                Set<SysOrgElement> excOrgList =  getServiceImp(request).getExcOrgByHisId(his.getFdId());
                List<SysOrgElement> allTargetOrgList =new ArrayList<>();
                if(CollectionUtils.isNotEmpty(changeOrgList)){
                    allTargetOrgList.addAll(changeOrgList);
                }
                if(CollectionUtils.isNotEmpty(targetOrgList)){
                    allTargetOrgList.addAll(targetOrgList);
                }
                if (category != null) {
                    SysAttendCategoryForm sysAttendCategoryForm = new SysAttendCategoryForm();

                    category.setDocCreator(UserUtil.getKMSSUser().getPerson());
                    getSysAttendCategoryService().convertModelToForm(sysAttendCategoryForm, category, new RequestContext());

                    if(StringUtil.isNotNull(sysAttendCategoryForm.getFdMinHour()) && sysAttendCategoryForm.getFdMinOverTime() ==null){
                        sysAttendCategoryForm.setFdMinOverTime(Integer.valueOf(sysAttendCategoryForm.getFdMinHour())*60);
                    }
                    if(CollectionUtils.isNotEmpty(allTargetOrgList)){
                        //签到对象列表
                        List<String> idList=new ArrayList<>();
                        List<String> nameList=new ArrayList<>();
                        for (SysOrgElement ele :allTargetOrgList) {
                            idList.add(ele.getFdId());
                            nameList.add(ele.getFdName());
                        }
                        sysAttendCategoryForm.setFdTargetIds(Joiner.on(";").join(idList));
                        sysAttendCategoryForm.setFdTargetNames(Joiner.on(";").join(nameList));
                    }
                    if(CollectionUtils.isNotEmpty(excOrgList)){
                        //排除对象列表
                        List<String> idList=new ArrayList<>();
                        List<String> nameList=new ArrayList<>();
                        for (SysOrgElement ele :excOrgList) {
                            idList.add(ele.getFdId());
                            nameList.add(ele.getFdName());
                        }
                        sysAttendCategoryForm.setFdExcTargetIds(Joiner.on(";").join(idList));
                        sysAttendCategoryForm.setFdExcTargetNames(Joiner.on(";").join(nameList));
                    }
                    sysAttendHisCategoryForm.setSysAttendCategoryForm(sysAttendCategoryForm);
                    request.setAttribute("sysAttendHisCategoryForm", form);
                    if(his.getFdManager() !=null) {
                        sysAttendCategoryForm.setFdManagerId(his.getFdManager().getFdId());
                        sysAttendCategoryForm.setFdManagerName(his.getFdManager().getFdName());
                    }
                }
            }
            // 判断是否集成启用kk
            boolean isEnableKKConfig = AttendUtil.isEnableKKConfig();
            request.setAttribute("isEnableKKConfig", isEnableKKConfig);
            // 判断是否继承启动钉钉
            boolean isEnableDingConfig = AttendUtil.isEnableDingConfig();
            request.setAttribute("isEnableDingConfig", isEnableDingConfig);
            // 判断是否继承启动企业微信
            boolean isEnableWxConfig = AttendUtil.isEnableWx();
            request.setAttribute("isEnableWxConfig", isEnableWxConfig);


        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("Action-edit", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("edit", mapping, form, request, response);
        }
    }

    @Override
    public ActionForward list(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-list", true, getClass());
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
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0
                    && Integer.parseInt(s_pageno) > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0
                    && Integer.parseInt(s_rowsize) > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }

            // 按多语言字段排序
            if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
                Class<?> modelClass = ((IExtendForm) form).getModelClass();
                if (modelClass != null) {
                    String langFieldName = SysLangUtil
                            .getLangFieldName(modelClass.getName(), orderby);
                    if (StringUtil.isNotNull(langFieldName)) {
                        orderby = langFieldName;
                    }
                }
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
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-list", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("list", mapping, form, request, response);
        }
    }
}
