package com.landray.kmss.sys.organization.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.forms.*;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import edu.emory.mathcs.backport.java.util.Collections;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

/**
 * 外部组织扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public class SysOrgElementExternalDeptAction extends ExtendAction {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private ISysOrgElementExternalService sysOrgElementExternalService;
    private ISysOrgElementExternalDeptService sysOrgElementExternalDeptService;
    private ISysOrgElementService sysOrgElementService;
    private IOrgRangeService orgRangeService;

    @Override
    protected ISysOrgElementExternalDeptService getServiceImp(HttpServletRequest request) {
        if (sysOrgElementExternalDeptService == null) {
            sysOrgElementExternalDeptService = (ISysOrgElementExternalDeptService) getBean("sysOrgElementExternalDeptService");
        }
        return sysOrgElementExternalDeptService;
    }

    protected ISysOrgElementExternalService getSysOrgElementExternalService() {
        if (sysOrgElementExternalService == null) {
            sysOrgElementExternalService = (ISysOrgElementExternalService) getBean("sysOrgElementExternalService");
        }
        return sysOrgElementExternalService;
    }

    public ISysOrgElementService getSysOrgElementService() {
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

    private ISysOrgCoreService sysOrgCoreService;

    public ISysOrgCoreService getSysOrgCoreService() {
        if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
        }
        return sysOrgCoreService;
    }

    @Override
    protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        super.changeFindPageHQLInfo(request, hqlInfo);
        // 设置为外部组织，用来过滤器跳过组织可见性校验(AuthOrgVisibleFilter)
        hqlInfo.setExternal(true);
        //外部组织独立过滤器类型
        hqlInfo.setAuthCheckType("EXTERNAL_READER");
        StringBuilder whereBlock = new StringBuilder();
        // 只查询外部组织
        whereBlock.append("fdIsExternal = true");
        // 根据上级查询
        String parent = request.getParameter("parent");
        if (StringUtil.isNotNull(parent)) {
            //在组织类型下面查询子组织时的处理方式
            String subType = request.getParameter("subType");
            if(getParentOrgTypeIsOrg(parent) && "4".equals(subType)){
                whereBlock.append(" and fdHierarchyId like :fdHierarchyId");
                hqlInfo.setParameter("fdHierarchyId","%"+parent+"%");
            }else {
                whereBlock.append(" and hbmParent.fdId = :parent");
                hqlInfo.setParameter("parent", parent);
            }
        }
        // 移动端不显示禁用的组织
        if (MobileUtil.getClientType(request) == MobileUtil.PC) {
            String[] isAvailables = request.getParameterValues("q.isAvailable");
            if (isAvailables != null && isAvailables.length > 0) {
                for (int i = 0; i < isAvailables.length; i++) {
                    String isAvailable = isAvailables[i];
                    if (i == 0) {
                        whereBlock.append(" and (fdIsAvailable = :isAvailable" + i);
                    } else {
                        whereBlock.append(" or fdIsAvailable = :isAvailable" + i);
                    }
                    hqlInfo.setParameter("isAvailable" + i, Boolean.valueOf(isAvailable));
                }
                whereBlock.append(") ");
            }
        } else {
            whereBlock.append(" and fdIsAvailable = true ");
        }
        hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", whereBlock.toString()));
        changeDeptFindPageHQLInfo(request, hqlInfo);
        // 生态组织权限过滤
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "EXTERNAL_READER");
    }

    protected void changeDeptFindPageHQLInfo(HttpServletRequest request,
                                             HQLInfo hqlInfo) throws Exception {
        String whereBlock = hqlInfo.getWhereBlock();

        // 新UED查询参数
        String fdName = request.getParameter("q.fdSearchName");
        if (StringUtil.isNull(fdName)) {
            // 兼容旧UI查询方式
            fdName = request.getParameter("fdSearchName");
        }

        boolean isLangSupport = SysLangUtil.isLangEnabled();
        String currentLocaleCountry = null;
        if (isLangSupport) {
            currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
            if (StringUtil.isNotNull(currentLocaleCountry) && currentLocaleCountry.equals(SysLangUtil.getOfficialLang())) {
                currentLocaleCountry = null;
            }
        }

        if (StringUtil.isNotNull(fdName)) { // 层级查询子部门，当fdName不为空时显示子部门及其下级部门
            // 为兼容数据库对大小写敏感，在搜索拼音时，按小写搜索
            String fdPinyinName = fdName.toLowerCase();
            String whereBlockDept = "";

            // 部门名称查询
            whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
                    "sysOrgDept.fdName like :fdName ");
            if (StringUtil.isNotNull(currentLocaleCountry)) {
                whereBlockDept = StringUtil.linkString(whereBlockDept,
                        " or ",
                        "sysOrgDept.fdName" + currentLocaleCountry
                                + " like :fdName ");
            }
            hqlInfo.setParameter("fdName", "%" + fdName + "%");
            // 编号查询
            whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
                    "sysOrgDept.fdNo like :fdName ");
            hqlInfo.setParameter("fdName", "%" + fdName + "%");
            // 名称拼音查询
            whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
                    "sysOrgDept.fdNamePinYin like :pinyin ");
            hqlInfo.setParameter("pinyin", "%" + fdPinyinName + "%");
            // 名称简拼查询
            whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
                    "sysOrgDept.fdNameSimplePinyin like :simplePinyin ");
            hqlInfo.setParameter("simplePinyin", "%" + fdPinyinName + "%");

            if (StringUtil.isNotNull(whereBlockDept)) {
                whereBlock = StringUtil.linkString(whereBlock, " and ", "( "
                        + whereBlockDept + " )");
            }

        }
        //查询组织和子组织的区别
        String subType = request.getParameter("subType");
        if(StringUtil.isNotNull(subType)){
            if("2".equals(subType)){
                whereBlock = StringUtil.linkString(whereBlock, " and ","sysOrgDept.hbmParent.fdId in (:deptTypeIds) ");
                hqlInfo.setParameter("deptTypeIds",getDeptTypeList());
            }else if("4".equals(subType)){
                whereBlock = StringUtil.linkString(whereBlock, " and ","sysOrgDept.hbmParent.fdId not in (:deptTypeIds) ");
                hqlInfo.setParameter("deptTypeIds",getDeptTypeList());
            }
        }

        whereBlock = StringUtil.linkString(whereBlock, " and ",
                "sysOrgDept.fdOrgType = :orgType ");
        hqlInfo.setParameter("orgType", SysOrgConstant.ORG_TYPE_DEPT);
        String fdFlagDeleted = request.getParameter("fdFlagDeleted");
        if (StringUtil.isNotNull(fdFlagDeleted)) {
            whereBlock = StringUtil.linkString(whereBlock, " and ",
                    "sysOrgDept.fdFlagDeleted = :fdFlagDeleted ");
            hqlInfo.setParameter("fdFlagDeleted", StringUtil
                    .isNull(fdFlagDeleted) ? false : true);
        }
        String fdImportInfo = request.getParameter("fdImportInfo");
        if (StringUtil.isNotNull(fdImportInfo)) {
            whereBlock = StringUtil.linkString(whereBlock, " and ",
                    "sysOrgDept.fdImportInfo like :fdImportInfo ");
            hqlInfo.setParameter("fdImportInfo", fdImportInfo + "%");
        }
        //筛选是否与业务相关
        String fdIsBusiness = request.getParameter("q.fdIsBusiness");
        if (StringUtil.isNotNull(fdIsBusiness)) {
            whereBlock = StringUtil.linkString(whereBlock, " and ",
                    "sysOrgDept.fdIsBusiness = :fdIsBusiness ");
            Boolean fdIsBusinessNext = false;
            if (Integer.parseInt(fdIsBusiness) == 1) {
                fdIsBusinessNext = true;
            }

            hqlInfo.setParameter("fdIsBusiness", fdIsBusinessNext);
        }

        hqlInfo.setWhereBlock(whereBlock);
    }

    /**
     * 父节点的组织类型
     * @description:
     * @param parentId
     * @return: boolean
     * @author: wangjf
     * @time: 2021/10/11 11:42 上午
     */
    private boolean getParentOrgTypeIsOrg(String parentId) throws Exception{
        SysOrgElement sysOrgElement = (SysOrgElement)getSysOrgElementService().findByPrimaryKey(parentId, SysOrgElement.class, true);
        return SysOrgConstant.ORG_TYPE_ORG == sysOrgElement.getFdOrgType();
    }

    /**
     * 查询所有的组织类型
     * @description:
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2021/9/27 5:23 下午
     */
    private List<String> getDeptTypeList() throws Exception{
        HQLInfo hqlInfo = new HQLInfo();
        String where = "sysOrgElement.fdOrgType =:fdOrgType and fdIsExternal=:fdIsExternal";
        hqlInfo.setWhereBlock(where);
        hqlInfo.setParameter("fdOrgType",SysOrgConstant.ORG_TYPE_ORG);
        hqlInfo.setParameter("fdIsExternal",Boolean.TRUE);
        hqlInfo.setSelectBlock("fdId");
        List<String> list = getSysOrgElementService().findList(hqlInfo);
        return list;
    }

    @Override
    protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        SysOrgDeptForm deptForm = (SysOrgDeptForm) super.createNewForm(mapping, form, request, response);
        deptForm.getFdRange().setFdIsOpenLimit("true");
        deptForm.getFdRange().setFdViewType("1");
        deptForm.getFdRange().setFdViewSubType("1");
        if (deptForm.getFdHideRange() == null) {
            // 默认开启隐藏属性
            deptForm.setFdHideRange(new SysOrgElementHideRangeForm());
            deptForm.getFdHideRange().setFdIsOpenLimit("true");
            deptForm.getFdHideRange().setFdViewType("0");
        }
        String parent = request.getParameter("parent");
        if (StringUtil.isNotNull(parent)) {
            SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(parent);
            deptForm.setFdParentId(parent);
            deptForm.setFdParentName(element.getFdName());
            // 继承父级的查看权限
            SysOrgElementForm elementForm = null;
            if (element.getFdOrgType() == 1) {
                elementForm = new SysOrgOrgForm();
            } else {
                elementForm = new SysOrgDeptForm();
            }
            getSysOrgElementService().convertModelToForm(elementForm, element, new RequestContext(request));
            if (elementForm.getFdRange() == null) {
                SysOrgElementRangeForm rangeForm = new SysOrgElementRangeForm();
                rangeForm.setFdIsOpenLimit("true");
                rangeForm.setFdViewType("1");
                elementForm.setFdRange(rangeForm);
            } else {
                if (!"2".equals(elementForm.getFdRange().getFdViewType())) {
                    elementForm.getFdRange().setFdViewSubType("1");
                }
            }
            SysOrgElementRangeForm fdRange = elementForm.getFdRange();
            fdRange.setFdId(IDGenerator.generateID());
            deptForm.setFdRange(fdRange);
            // 继承父级的隐藏属性
            if (elementForm.getFdHideRange() == null) {
                SysOrgElementHideRangeForm rangeForm = new SysOrgElementHideRangeForm();
                rangeForm.setFdIsOpenLimit("true");
                rangeForm.setFdViewType("0");
                elementForm.setFdHideRange(rangeForm);
            } else {
                SysOrgElementHideRangeForm rangeForm = elementForm.getFdHideRange();
                // 如果父级关闭隐藏属性，这里默认需要开启
                if("false".equals(rangeForm.getFdIsOpenLimit())) {
                    rangeForm.setFdIsOpenLimit("true");
                    rangeForm.setFdViewType("0");
                    elementForm.setFdHideRange(rangeForm);
                }
            }
            SysOrgElementHideRangeForm fdHideRange = elementForm.getFdHideRange();
            fdHideRange.setFdId(IDGenerator.generateID());
            deptForm.setFdHideRange(fdHideRange);
            SysOrgUtil.getHideRangeTip(request, element);
        }
        return deptForm;
    }

    @Override
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        initProp(request);
        return super.add(mapping, form, request, response);
    }

    @Override
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        initProp(request);
        return super.edit(mapping, form, request, response);
    }

    @Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        super.loadActionForm(mapping, form, request, response);
        // 外部组织入口只能查询生态组织
        if (SysOrgEcoUtil.IS_ENABLED_ECO) {
            SysOrgElementForm elementForm = (SysOrgElementForm) form;
            if (!"true".equals(elementForm.getFdIsExternal())) {
                throw new NoRecordException();
            }
        }

        SysOrgDeptForm deptForm = (SysOrgDeptForm) form;
        SysOrgElementRangeForm rangeForm = deptForm.getFdRange();
        if (rangeForm == null) {
            rangeForm = new SysOrgElementRangeForm();
            rangeForm.setFdIsOpenLimit("true");
            rangeForm.setFdViewType("1");
            deptForm.setFdRange(rangeForm);
        } else if (!"true".equals(deptForm.getFdRange().getFdIsOpenLimit())) {
            rangeForm.setFdIsOpenLimit("true");
            rangeForm.setFdViewType("1");
        }
        initProp(request);

        // 获取父级隐藏设置
        SysOrgElement model = (SysOrgElement) getServiceImp(request).findByPrimaryKey(deptForm.getFdId());
        if (model != null && model.getFdParent() != null) {
            SysOrgUtil.getHideRangeTip(request, model.getFdParent());
        }
    }

    @Override
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        ActionForward af = super.list(mapping, form, request, response);
        // 根据上级查询
        String parent = request.getParameter("parent");
        if (StringUtil.isNotNull(parent)) {
            SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(parent);
            if (element != null) {
                String cateId = element.getFdId();
                SysOrgElement org = element.getFdParentOrg();
                if (org != null) {
                    cateId = org.getFdId();
                }
                SysOrgElementExternal external = (SysOrgElementExternal) getSysOrgElementExternalService().findByPrimaryKey(cateId);
                request.setAttribute("props", external.getFdDeptProps());
                Page page = (Page) request.getAttribute("queryPage");
                List<SysOrgDept> list = page.getList();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (SysOrgDept dept : list) {
                        // 查询自定义属性
                        Map<String, String> map = getServiceImp(request).getExtProp(external, dept.getFdId(), true);
                        dept.setDynamicMap(map);
                    }
                }
            }
        }
        return af;
    }

    /**
     * 初始化
     *
     * @param request
     */
    private void initProp(HttpServletRequest request) throws Exception {
        String fdId = request.getParameter("fdId");
        // parent可能是组织分类，也可能是上级组织
        String parent = request.getParameter("parent");
        String cateId = null;
        if (StringUtil.isNull(fdId) && StringUtil.isNull(parent)) {
            throw new RuntimeException("组织ID或上级组织ID不能为空！");
        }
        // 用于判断是组织还是子组织
        SysOrgElement parentElement = null;
        if (StringUtil.isNotNull(parent)) {
            SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(parent, null, true);
            if (element == null) {
                throw new RuntimeException("没有找到上级组织！");
            }
            cateId = element.getFdId();
            SysOrgElement org = element.getFdParentOrg();
            if (org != null) {
                cateId = org.getFdId();
            }
            parentElement = element;
        } else {
            SysOrgDept dept = (SysOrgDept) getServiceImp(request).findByPrimaryKey(fdId, null, true);
            cateId = dept.getFdParentOrg().getFdId();
            parentElement = dept.getFdParent();
        }
        request.setAttribute("fdOtgType", parentElement.getFdOrgType());
        if (StringUtil.isNull(cateId)) {
            throw new RuntimeException("没有找到外部组织类型！");
        }
        SysOrgElementExternal external = (SysOrgElementExternal) getSysOrgElementExternalService().findByPrimaryKey(cateId, null, true);

        // 排序
        List<SysOrgElementExtProp> props = new ArrayList<SysOrgElementExtProp>(external.getFdDeptProps());
        Iterator<SysOrgElementExtProp> iterator = props.iterator();
        while (iterator.hasNext()) {
            SysOrgElementExtProp prop = iterator.next();
            if (!BooleanUtils.isTrue(prop.getFdStatus())) {
                // 移除禁用的属性
                iterator.remove();
            }
        }
        Collections.sort(props, new Comparator<SysOrgElementExtProp>() {
            @Override
            public int compare(SysOrgElementExtProp o1, SysOrgElementExtProp o2) {
                Integer num1 = o1.getFdOrder();
                Integer num2 = o2.getFdOrder();
                if (num2 == null) {
                    return 0;
                }
                if (num1 == null) {
                    return -1;
                }
                if (num2 < num1) {
                    return 0;
                } else if (num1.equals(num2)) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        request.setAttribute("props", props);
        request.setAttribute("cateId", cateId);
    }

    /**
     * 修改负责人
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ActionForward editAdmin(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        String ids = request.getParameter("ids");
        String id = request.getParameter("id");
        if (StringUtil.isNotNull(ids) && StringUtil.isNotNull(id)) {
            String[] arrIds = ids.split(";");
            SysOrgElement sysOrgElement = (SysOrgElement) getServiceImp(request).findByPrimaryKey(id);
            List personList = getSysOrgElementService().findByPrimaryKeys(arrIds);
            sysOrgElement.setAuthElementAdmins(personList);
            getServiceImp(request).add(sysOrgElement);
        }

        return null;
    }

    public ActionForward getDeptAuth(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();

        boolean isAuth = false;

        if (UserUtil.checkRole("ROLE_SYSORG_ECO_ADMIN")
                || UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_ADMIN")
                || UserUtil.checkRole("ROLE_SYSORG_ECO_ORG_ADMIN")) { // 生态组织管理员
            isAuth = true;
        } else { // 判断是否管理员、可使用者权限
            String fdId = request.getParameter("fdId");
            if (StringUtil.isNotNull(fdId)) {
                SysOrgElement sysElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdId);
                if (sysElement != null) {
                    isAuth = getSysOrgCoreService().isElemCreator(sysElement, UserUtil.getUser());
                    if (!isAuth) {
                        isAuth = getSysOrgCoreService().checkPersonIsOrgAdmin(UserUtil.getUser(), sysElement);
                    }
                }
            }
        }
        jsonObject.put("isAuth", isAuth);
        out.println(jsonObject.toString());
        return null;
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
                    + ResourceUtil.getString("sysOrgElementExternal.error.existChildren.msg", "sys-organization")
                    + "</a>"), existChildren);
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
                    + ResourceUtil.getString("sysOrgElementExternal.error.existChildren.msg", "sys-organization")
                    + "</a>"), existChildren);
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
     * 可维护的组织list
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listMaintainableDept(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-listMaintainableDept", true, getClass());
        KmssMessages messages = new KmssMessages();
        String s_pageno = request.getParameter("pageno");
        String s_rowsize = request.getParameter("rowsize");
        int pageno = 0;
        int rowsize = 1000; // 默认先给1000
        if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
            pageno = Integer.parseInt(s_pageno);
        }
        if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
            rowsize = Integer.parseInt(s_rowsize);
        }
        try {
            String keyword = request.getParameter("keyword");

            HQLInfo hqlInfo = new HQLInfo();
            StringBuffer whereBf = new StringBuffer();
            if (StringUtil.isNotNull(keyword)) {
                String[] keys = keyword.replaceAll("'", "''").split("\\s*[,;，；]\\s*");
                if (keys.length > 0) {
                    for (int i = 0; i < keys.length; i++) {
                        String key = keys[i];
                        if (StringUtil.isNull(key)) {
                            continue;
                        }
                        key = key.toLowerCase();
                        whereBf.append(" or lower(sysOrgDept.fdName) like :key_" + i)
                                .append(" or lower(sysOrgDept.fdNameSimplePinyin) like :key_" + i)
                                .append(" or lower(sysOrgDept.fdNamePinYin) like :key_" + i);
                        hqlInfo.setParameter("key_" + i, "%" + key + "%");
                    }
                    if (whereBf.length() > 0) {
                        whereBf.delete(0, 4);
                        whereBf.insert(0, "(");
                        whereBf.append(") and ");
                    }
                }
            }

            String commonWhere = "sysOrgDept.fdOrgType = 2 and sysOrgDept.fdIsAvailable = true and sysOrgDept.fdIsExternal = true";
            whereBf.append(commonWhere);
            hqlInfo.setWhereBlock(whereBf.toString());
            hqlInfo.setAuthCheckType("OPERATION_READER");
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("Action-listMaintainableDept", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("listMaintainableDept", mapping, form, request, response);
        }
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
            logger.error(e.getMessage(), e);
        }

        TimeCounter.logCurrentTime("Action-save", false, getClass());
        if (messages.hasError()) {
            // 发现异常，需要重新加载扩展属性
            initProp(request);
            KmssReturnPage.getInstance(request).addMessages(messages).save(request);
            return getActionForward("edit", mapping, form, request, response);
        } else {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
                    .save(request);
            return getActionForward("success", mapping, form, request, response);
        }
    }

    @Override
    public ActionForward saveadd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            getServiceImp(request).add((IExtendForm) form, new RequestContext(request));
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
        KmssReturnPage.getInstance(request).addMessages(messages).save(request);
        if (messages.hasError()) {
            // 发现异常，需要重新加载扩展属性
            initProp(request);
            return getActionForward("edit", mapping, form, request, response);
        } else {
            return add(mapping, form, request, response);
        }
    }

    @Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-update", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            getServiceImp(request).update((IExtendForm) form,
                    new RequestContext(request));
        } catch (Exception e) {
            if (MobileUtil.getClientType(request) != MobileUtil.PC) {
                if (e instanceof RuntimeException) {
                    response(response, e.getMessage());
                    return null;
                }
            }
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-update", false, getClass());
        if (messages.hasError()) {
            // 发现异常，需要重新加载扩展属性
            initProp(request);
            KmssReturnPage.getInstance(request).addMessages(messages).save(
                    request);
            return getActionForward("edit", mapping, form, request, response);
        } else {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            // 返回按钮
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton("button.back",
                            "/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=view&fdId="
                                    + ((IExtendForm) form).getFdId(),
                            false)
                    .save(request);
            return getActionForward("success", mapping, form, request, response);
        }
    }

    private void response(HttpServletResponse response, String message)
            throws Exception {
        response.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();
        json.put("message", message);
        response.getOutputStream().write(json.toString().getBytes("UTF-8"));
    }

    /**
     * 用于判断用户是外部组织还是内部组织人员，移动端地址本是否现实公共组群
     */
    public ActionForward getUserType(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PrintWriter out = response.getWriter();

        if (SysOrgEcoUtil.IS_ENABLED_ECO) { // 开启生态组织
            if (SysOrgEcoUtil.isExternal(UserUtil.getUser())) { // 外部人员
                out.println(false);
                return null;
            }
        }
        out.println(true);
        return null;
    }

    /**
     * 转换成外部部门
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @description:
     * @return: com.landray.kmss.web.action.ActionForward
     * @author: wangjf
     * @time: 2021/9/24 2:27 下午
     */
    public ActionForward transformOut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-transformOut", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            if (!SysOrgEcoUtil.IS_ENABLED_ECO || !"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            String fdId = request.getParameter("fdId");
            String parentId = request.getParameter("parentId");
            if (StringUtil.isNotNull(fdId)) {
                SysOrgElement parentSysElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(parentId);
                SysOrgElement sysElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdId);
                getServiceImp(request).updateTransformOut(parentSysElement, sysElement);
                getSysOrgElementService().updateRelation();
            }
        } catch (Exception e) {
            messages.addError(e);
        }
        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
        TimeCounter.logCurrentTime("Action-transformOut", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }
}
