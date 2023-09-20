package com.landray.kmss.sys.organization.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.forms.SysOrgElementExternalForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementHideRangeForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementRangeForm;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.service.ISysOrgElementExtPropService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgOrgService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外部组织类型扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public class SysOrgElementExternalAction extends ExtendAction {

    private ISysOrgElementExternalService sysOrgElementExternalService;

    private ISysOrgOrgService sysOrgOrgService;

    private ISysOrgElementExtPropService sysOrgElementExtPropService;

    private ISysOrgElementService sysOrgElementService;

    private IOrgRangeService orgRangeService;

    @Override
    protected ISysOrgElementExternalService getServiceImp(HttpServletRequest request) {
        if (sysOrgElementExternalService == null) {
            sysOrgElementExternalService = (ISysOrgElementExternalService) getBean("sysOrgElementExternalService");
        }
        return sysOrgElementExternalService;
    }

    protected ISysOrgOrgService geSysOrgOrgServiceImp(HttpServletRequest request) {
        if (sysOrgOrgService == null) {
            sysOrgOrgService = (ISysOrgOrgService) getBean("sysOrgOrgService");
        }
        return sysOrgOrgService;
    }

    protected ISysOrgElementExtPropService geSysOrgElementExtPropService(HttpServletRequest request) {
        if (sysOrgElementExtPropService == null) {
            sysOrgElementExtPropService = (ISysOrgElementExtPropService) getBean("sysOrgElementExtPropService");
        }
        return sysOrgElementExtPropService;
    }

    protected ISysOrgElementService getSysOrgElementServiceImp(HttpServletRequest request) {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    public IOrgRangeService getOrgRangeService() {
        if (orgRangeService == null) {
            orgRangeService = (IOrgRangeService) getBean("orgRangeService");
        }
        return orgRangeService;
    }

    @Override
    protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        super.changeFindPageHQLInfo(request, hqlInfo);
        StringBuilder whereBlock = new StringBuilder();
        whereBlock.append("sysOrgElementExternal.fdElement.fdIsExternal = true");
        // 过滤“无组织”
        whereBlock.append(" and sysOrgElementExternal.fdId != :unorg");
        hqlInfo.setParameter("unorg", SysOrgConstant.UNORGANIZED_ID);

        // 移动端不显示禁用的组织类型
        int type = MobileUtil.getClientType(request);
        if (type != MobileUtil.PC) {
            whereBlock.append(" and sysOrgElementExternal.fdElement.fdIsAvailable = true ");
        } else {
            CriteriaValue cv = new CriteriaValue(request);
            String[] isAvailables = cv.get("isAvailable");
            if (isAvailables != null && isAvailables.length > 0) {
                for (int i = 0; i < isAvailables.length; i++) {
                    String isAvailable = isAvailables[i];
                    if (i == 0) {
                        whereBlock.append(" and (sysOrgElementExternal.fdElement.fdIsAvailable = :isAvailable" + i);
                    } else {
                        whereBlock.append(" or sysOrgElementExternal.fdElement.fdIsAvailable = :isAvailable" + i);
                    }
                    hqlInfo.setParameter("isAvailable" + i, Boolean.valueOf(isAvailable));
                }
                whereBlock.append(") ");
            }
        }

        hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", whereBlock.toString()));
        changeOrgFindPageHQLInfo(request, hqlInfo);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysOrgElementExternal.class);
        // 列表页面的排序与左则菜单树保持一样
        hqlInfo.setOrderBy("sysOrgElementExternal.fdElement.fdOrgType desc, sysOrgElementExternal.fdElement.fdOrder, sysOrgElementExternal.fdElement."  + SysLangUtil.getLangFieldName("fdName"));
    }

    protected void changeOrgFindPageHQLInfo(HttpServletRequest request,
                                            HQLInfo hqlInfo) throws Exception {
        String whereBlock = hqlInfo.getWhereBlock();
        // 新UED查询参数
        String fdName = request.getParameter("q.fdSearchName");
        if (StringUtil.isNull(fdName)) {
            // 兼容旧UI查询方式
            fdName = request.getParameter("fdSearchName");
        }
        String whereBlockOrg = "";

        boolean isLangSupport = SysLangUtil.isLangEnabled();
        String currentLocaleCountry = null;
        if (isLangSupport) {
            currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
        }

        if (StringUtil.isNotNull(fdName)) {
            // 为兼容数据库对大小写敏感，在搜索拼音时，按小写搜索
            String fdPinyinName = fdName.toLowerCase();

            // 机构名称查询
            whereBlockOrg = StringUtil.linkString(whereBlockOrg, " or ",
                    "sysOrgElementExternal.fdElement.fdName like :fdName");
            if (StringUtil.isNotNull(currentLocaleCountry)) {
                whereBlockOrg = StringUtil.linkString(whereBlockOrg,
                        " or ",
                        "sysOrgElementExternal.fdElement.fdName" + currentLocaleCountry
                                + " like :fdName ");
            }
            hqlInfo.setParameter("fdName", "%" + fdName + "%");
            // 编号查询
            whereBlockOrg = StringUtil.linkString(whereBlockOrg, " or ",
                    "sysOrgElementExternal.fdElement.fdNo like :fdName ");
            hqlInfo.setParameter("fdName", "%" + fdName + "%");
            // 名称拼音查询
            whereBlockOrg = StringUtil.linkString(whereBlockOrg, " or ",
                    "sysOrgElementExternal.fdElement.fdNamePinYin like :pinyin ");
            hqlInfo.setParameter("pinyin", "%" + fdPinyinName + "%");
            // 名称简拼查询
            whereBlockOrg = StringUtil.linkString(whereBlockOrg, " or ",
                    "sysOrgElementExternal.fdElement.fdNameSimplePinyin like :simplePinyin ");
            hqlInfo.setParameter("simplePinyin", "%" + fdPinyinName + "%");

            whereBlock = StringUtil.linkString(whereBlock, " and ", "( "
                    + whereBlockOrg + " )");
        }
        String fdFlagDeleted = request.getParameter("fdFlagDeleted");
        if (StringUtil.isNotNull(fdFlagDeleted)) {
            whereBlock = StringUtil.linkString(whereBlock, " and ",
                    "sysOrgElementExternal.fdElement.fdFlagDeleted = :fdFlagDeleted ");
            hqlInfo.setParameter("fdFlagDeleted", StringUtil
                    .isNull(fdFlagDeleted) ? false : true);
        }
        String fdImportInfo = request.getParameter("fdImportInfo");
        if (StringUtil.isNotNull(fdImportInfo)) {
            whereBlock = StringUtil.linkString(whereBlock, " and ",
                    "sysOrgElementExternal.fdElement.fdImportInfo like :fdImportInfo ");
            hqlInfo.setParameter("fdImportInfo", fdImportInfo + "%");
        }
        hqlInfo.setWhereBlock(whereBlock);
    }

    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-save", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            getServiceImp(request).add((IExtendForm) form, new RequestContext(request));
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-save", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).save(request);
            return getActionForward("edit", mapping, form, request, response);
        } else {
            // 禁用自动关闭
            request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
            KmssReturnPage returnPage = KmssReturnPage.getInstance(request).addMessages(messages);
            returnPage.addButton(KmssReturnPage.BUTTON_CLOSE);
            returnPage.addButton("sys-organization:sysOrgElement.authRole.templateName",
                    "/sys/authorization/sys_auth_role/sysAuthRole.do?method=add&type=0&fdTemplate=1&s_css=default", false);
            returnPage.save(request);
            return getActionForward("success", mapping, form, request, response);
        }
    }

    @Override
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-update", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            return super.update(mapping, form, request, response);
        } catch (ExistChildrenException existChildren) {
            SysOrgElementExternalForm externalForm = (SysOrgElementExternalForm) form;
            messages.addError(new KmssMessage("global.message", "<a href='" + request.getContextPath()
                            + "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds=" + externalForm.getFdId()
                            + "' target='_blank'>"
                            + ResourceUtil.getString("sysOrgElementExternal.error.existChildren", "sys-organization")
                            + ResourceUtil.getString("sysOrgElementExternal.error.existChildren.msg", "sys-organization") + "</a>"),
                    existChildren);
        } catch (Exception e) {
            messages.addError(e);
        }
        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
        TimeCounter.logCurrentTime("Action-update", false, getClass());
        return getActionForward("failure", mapping, form, request, response);
    }

    /**
     * 修复扩展属性
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward repair(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
        KmssMessages messages = new KmssMessages();
        String[] ids = request.getParameterValues("List_Selected");
        try {
            if (ids != null) {
                getServiceImp(request).repair(ids, new RequestContext(request));
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
        TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 置为无效
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward invalidated(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
        KmssMessages messages = new KmssMessages();
        String id = request.getParameter("fdId");
        try {
            if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).updateInvalidated(id, new RequestContext(request));
            }
        } catch (ExistChildrenException existChildren) {
            messages.addError(new KmssMessage("global.message", "<a href='" + request.getContextPath()
                            + "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds=" + id
                            + "' target='_blank'>"
                            + ResourceUtil.getString("sysOrgElementExternal.error.existChildren", "sys-organization")
                            + ResourceUtil.getString("sysOrgElementExternal.error.existChildren.msg", "sys-organization") + "</a>"),
                    existChildren);
        } catch (Exception e) {
            messages.addError(e);
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
        TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 批量置为无效
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward invalidatedAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
        KmssMessages messages = new KmssMessages();
        String[] ids = request.getParameterValues("List_Selected");
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            if (ids != null) {
                getServiceImp(request).updateInvalidated(ids, new RequestContext(request));
            }
        } catch (ExistChildrenException existChildren) {
            messages.addError(new KmssMessage("global.message", "<a href='" + request.getContextPath()
                            + "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds="
                            + StringUtil.escape(ArrayUtil.concat(ids, ',')) + "' target='_blank'>"
                            + ResourceUtil.getString("sysOrgElementExternal.error.existChildren", "sys-organization")
                            + ResourceUtil.getString("sysOrgElementExternal.error.existChildren.msg", "sys-organization") + "</a>"),
                    existChildren);
        } catch (Exception e) {
            messages.addError(e);
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
        TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 改变属性状态
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward changePropStatus(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-changePropStatus", true, getClass());
        KmssMessages messages = new KmssMessages();
        String id = request.getParameter("fdId");
        String status = request.getParameter("status");
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                geSysOrgElementExtPropService(request).updatePropStatus(id, Boolean.valueOf(status));
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(
                KmssReturnPage.BUTTON_CLOSE).save(request);
        TimeCounter.logCurrentTime("Action-changePropStatus", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 删除属性
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteProp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-deleteProp", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            String propId = request.getParameter("propId");
            if (StringUtil.isNull(propId)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).deleteProp(propId);
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
        TimeCounter.logCurrentTime("Action-deleteProp", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 开启/禁用组织类型
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward changeElemStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-changeElemStatus", true, getClass());
        KmssMessages messages = new KmssMessages();
        String[] ids = request.getParameterValues("ids");
        String status = request.getParameter("status");
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            if (ids != null && ids.length > 0) {
                if ("true".equalsIgnoreCase(status)) {
                    // 开启组织类型
                    geSysOrgOrgServiceImp(request).updateValidated(ids, new RequestContext(request));
                } else {
                    // 禁用组织类型
                    geSysOrgOrgServiceImp(request).updateInvalidated(ids, new RequestContext(request));
                }
            }
        } catch (ExistChildrenException existChildren) {
            messages.addError(new KmssMessage("global.message", "<a href='" + request.getContextPath()
                            + "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds="
                            + StringUtil.escape(ArrayUtil.concat(ids, ',')) + "' target='_blank'>"
                            + ResourceUtil.getString("sysOrgElementExternal.error.existChildren", "sys-organization")
                            + ResourceUtil.getString("sysOrgElementExternal.error.existChildren.msg", "sys-organization") + "</a>"),
                    existChildren);
        } catch (Exception e) {
            messages.addError(e);
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
        TimeCounter.logCurrentTime("Action-changeElemStatus", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 手机端页眉上面的人数统计数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @description:
     * @return: com.landray.kmss.web.action.ActionForward
     * @author: wangjf
     * @time: 2021/9/25 3:29 下午
     */
    @SuppressWarnings("rawtypes")
    public ActionForward getMobileHeaderData(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
        KmssMessages messages = new KmssMessages();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        try {

            long totalNum = 0, addNum = 0;
            HQLInfo hqlInfo = new HQLInfo();
            String whereBlock = "sysOrgElementExternal.fdElement.fdOrgType =:fdOrgType and sysOrgElementExternal.fdElement.fdIsAvailable =:fdIsAvailable and sysOrgElementExternal.fdElement.fdIsExternal =:fdIsExternal ";
            hqlInfo.setWhereBlock(whereBlock);
            hqlInfo.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_ORG);
            hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
            hqlInfo.setParameter("fdIsExternal", Boolean.TRUE);
            // 所有机构(类别)
            List<SysOrgElementExternal> allOrgList = getServiceImp(request).findList(hqlInfo);
            if (!CollectionUtils.isEmpty(allOrgList)) {
                //所有人员
                Map<String, Date> allMap = new HashMap<>();
                for (SysOrgElementExternal sysOrgElement : allOrgList) {
                    Map<String, Date> personMapByOrgDept = SysOrgUtil.getPersonMapByOrgDept(sysOrgElement.getFdElement());
                    if (!CollectionUtils.isEmpty(personMapByOrgDept)) {
                        allMap.putAll(personMapByOrgDept);
                    }
                }
                if (!CollectionUtils.isEmpty(allMap)) {
                    totalNum = allMap.size();
                    final Date startDate = DateUtil.getDate(-1);
                    final Date endDate = DateUtil.getDate(0);
                    addNum = allMap.entrySet().stream().filter(map -> map.getValue().getTime() >= startDate.getTime() && map.getValue().getTime() < endDate.getTime()).count();
                }
            }
            jsonObject.put("totalNum", totalNum);
            jsonObject.put("addNum", addNum);
            out.println(jsonObject.toString());

        } catch (Exception e) {
            messages.addError(e);
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 手机端首页人数显示
     * @description:
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return: com.landray.kmss.web.action.ActionForward
     * @author: wangjf
     * @time: 2021/9/25 4:14 下午
     */
    @SuppressWarnings("rawtypes")
    public ActionForward getMobileIndexHeaderData(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
        KmssMessages messages = new KmssMessages();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();

        try {
            int ecoTotal=0;
            HQLInfo hqlInfo = new HQLInfo();
            String whereBlock = " sysOrgElementExternal.fdElement.fdOrgType =:fdOrgType and sysOrgElementExternal.fdElement.fdIsAvailable =:fdIsAvailable and sysOrgElementExternal.fdElement.fdIsExternal =:fdIsExternal";
            hqlInfo.setWhereBlock(whereBlock);
            hqlInfo.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_ORG);
            hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
            hqlInfo.setParameter("fdIsExternal", Boolean.TRUE);
            // 生态组织所有机构（类别）
            List<SysOrgElementExternal> allOrgList = getServiceImp(request).findList(hqlInfo);
            // 所有机构(类别)
            if (!CollectionUtils.isEmpty(allOrgList)) {
                //所有人员
                Map<String, Date> allMap = new HashMap<>();
                for (SysOrgElementExternal sysOrgElement : allOrgList) {
                    Map<String, Date> personMapByOrgDept = SysOrgUtil.getPersonMapByOrgDept(sysOrgElement.getFdElement());
                    if (!CollectionUtils.isEmpty(personMapByOrgDept)) {
                        allMap.putAll(personMapByOrgDept);
                    }
                }
                if(!CollectionUtils.isEmpty(allMap)){
                    ecoTotal = allMap.size();
                }
            }

            HQLInfo totalHqlInfo = new HQLInfo();
            whereBlock = " fdOrgType =:fdOrgType and fdIsAvailable =:fdIsAvailable and fdIsExternal =:fdIsExternal and fdIsBusiness =:fdIsBusiness";
            totalHqlInfo.setWhereBlock(whereBlock);
            totalHqlInfo.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_PERSON);
            totalHqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
            totalHqlInfo.setParameter("fdIsBusiness", Boolean.TRUE);
            totalHqlInfo.setParameter("fdIsExternal", Boolean.FALSE);
            totalHqlInfo.setSelectBlock("count(*)");
            // 组织架构总人数
            List total = getSysOrgElementServiceImp(request).findList(totalHqlInfo);

            jsonObject.put("ecoTotal", ecoTotal);
            jsonObject.put("total", total.get(0));
            out.println(jsonObject.toString());

        } catch (Exception e) {
            messages.addError(e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        super.loadActionForm(mapping, form, request, response);

        SysOrgElementExternalForm rtnForm = (SysOrgElementExternalForm) form;
        // 防止某些情况下清除组织查看范围后页面报错
        if (rtnForm.getFdElement().getFdRange() == null) {
            SysOrgElementRangeForm rangeForm = new SysOrgElementRangeForm();
            rangeForm.setFdIsOpenLimit("true");
            rangeForm.setFdViewType("1");
            rtnForm.getFdElement().setFdRange(rangeForm);
        }
        if (rtnForm.getFdElement().getFdHideRange() == null) {
            SysOrgElementHideRangeForm rangeForm = new SysOrgElementHideRangeForm();
            rangeForm.setFdIsOpenLimit("true");
            rangeForm.setFdViewType("0");
            rtnForm.getFdElement().setFdHideRange(rangeForm);
        }
    }

}
