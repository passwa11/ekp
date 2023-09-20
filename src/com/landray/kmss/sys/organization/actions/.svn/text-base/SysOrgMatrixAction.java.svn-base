package com.landray.kmss.sys.organization.actions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixDataCateForm;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixForm;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixRelationForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixCate;
import com.landray.kmss.sys.organization.model.SysOrgMatrixVersion;
import com.landray.kmss.sys.organization.service.*;
import com.landray.kmss.sys.organization.service.spring.SysOrgMatrixMainDataService;
import com.landray.kmss.sys.xform.maindata.model.SysFormMainDataCustom;
import com.landray.kmss.sys.xform.maindata.model.SysFormMainDataInsystem;
import com.landray.kmss.sys.xform.maindata.service.ISysFormMainDataCustomService;
import com.landray.kmss.sys.xform.maindata.service.ISysFormMainDataInsystemService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

/**
 * 组织矩阵
 *
 * @author 潘永辉 2019年6月4日
 */
public class SysOrgMatrixAction extends ExtendAction {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgMatrixAction.class);

    private ISysOrgMatrixService sysOrgMatrixService;

    private ISysOrgMatrixCateService sysOrgMatrixCateService;

    private ISysOrgMatrixRelationService sysOrgMatrixRelationService;

    private ISysOrgMatrixVersionService sysOrgMatrixVersionService;

    private SysOrgMatrixMainDataService sysOrgMatrixMainDataService;

    // 系统内数据
    private ISysFormMainDataInsystemService sysFormMainDataInsystemService;

    // 自定义数据
    private ISysFormMainDataCustomService sysFormMainDataCustomService;

    // 矩阵数据分组
    private ISysOrgMatrixDataCateService sysOrgMatrixDataCateService;

    public ISysOrgMatrixDataCateService getSysOrgMatrixDataCateService() {
        if (sysOrgMatrixDataCateService == null) {
            sysOrgMatrixDataCateService = (ISysOrgMatrixDataCateService) getBean("sysOrgMatrixDataCateService");
        }
        return sysOrgMatrixDataCateService;
    }

    @Override
    protected ISysOrgMatrixService getServiceImp(HttpServletRequest request) {
        if (sysOrgMatrixService == null) {
            sysOrgMatrixService = (ISysOrgMatrixService) getBean("sysOrgMatrixService");
        }
        return sysOrgMatrixService;
    }

    public ISysOrgMatrixVersionService getSysOrgMatrixVersionService() {
        if (sysOrgMatrixVersionService == null) {
            sysOrgMatrixVersionService = (ISysOrgMatrixVersionService) getBean("sysOrgMatrixVersionService");
        }
        return sysOrgMatrixVersionService;
    }

    public ISysOrgMatrixCateService getSysOrgMatrixCateService() {
        if (sysOrgMatrixCateService == null) {
            sysOrgMatrixCateService = (ISysOrgMatrixCateService) getBean("sysOrgMatrixCateService");
        }
        return sysOrgMatrixCateService;
    }

    public ISysOrgMatrixRelationService getSysOrgMatrixRelationService() {
        if (sysOrgMatrixRelationService == null) {
            sysOrgMatrixRelationService = (ISysOrgMatrixRelationService) getBean("sysOrgMatrixRelationService");
        }
        return sysOrgMatrixRelationService;
    }

    public SysOrgMatrixMainDataService getSysOrgMatrixMainDataService() {
        if (sysOrgMatrixMainDataService == null) {
            sysOrgMatrixMainDataService = (SysOrgMatrixMainDataService) getBean("sysOrgMatrixMainDataService");
        }
        return sysOrgMatrixMainDataService;
    }

    public ISysFormMainDataInsystemService getSysFormMainDataInsystemService() {
        if (sysFormMainDataInsystemService == null) {
            sysFormMainDataInsystemService = (ISysFormMainDataInsystemService) getBean("sysFormMainDataInsystemService");
        }
        return sysFormMainDataInsystemService;
    }

    public ISysFormMainDataCustomService getSysFormMainDataCustomService() {
        if (sysFormMainDataCustomService == null) {
            sysFormMainDataCustomService = (ISysFormMainDataCustomService) getBean("sysFormMainDataCustomService");
        }
        return sysFormMainDataCustomService;
    }

    @Override
    protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysOrgMatrixForm matrixForm = (SysOrgMatrixForm) form;
        matrixForm.reset(mapping, request);
        String parent = request.getParameter("parentCate");
        if (!StringUtil.isNull(parent)) {
            SysOrgMatrixCate category = (SysOrgMatrixCate) getSysOrgMatrixCateService().findByPrimaryKey(parent);
            matrixForm.setFdCategoryId(category.getFdId());
            matrixForm.setFdCategoryName(category.getFdName());
        }
        // 开启分组，默认填充一个分组名称
        request.setAttribute("defCate", ResourceUtil.getString("sys-organization:sysOrgMatrix.dataCate.def"));
        return matrixForm;
    }

    @Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        form.reset(mapping, request);
        SysOrgMatrixForm rtnForm = null;
        String id = request.getParameter("fdId");
        if (!StringUtil.isNull(id)) {
            SysOrgMatrix matrix = (SysOrgMatrix) getServiceImp(request).findByPrimaryKey(id, null, true);
            if (matrix != null) {
                // 条件排序
                Collections.sort(matrix.getFdRelationConditionals());
                // 结果排序
                Collections.sort(matrix.getFdRelationResults());
                rtnForm = (SysOrgMatrixForm) getServiceImp(request).convertModelToForm((IExtendForm) form, matrix, new RequestContext(request));
                JSONObject count = null;
                // 获取每列的数据量
                if ("edit".equals(request.getParameter("method"))) {
                    count = getServiceImp(request).countByColumns(id);
                }
                // 处理主数据(条件)
                for (Object obj : rtnForm.getFdRelationConditionals()) {
                    SysOrgMatrixRelationForm relation = (SysOrgMatrixRelationForm) obj;
                    if (StringUtil.isNotNull(relation.getFdMainDataType())) {
                        String mainDataType = relation.getFdMainDataType();
                        String type = relation.getFdType();
                        // 适应页面排版
                        relation.setFdType(mainDataType);
                        relation.setFdMainDataType(type);
                        if ("sys".equals(relation.getFdType())) {
                            // 主数据（系统内数据）
                            SysFormMainDataInsystem insystem = (SysFormMainDataInsystem) getSysFormMainDataInsystemService().findByPrimaryKey(type, null, true);
                            if (insystem != null) {
                                relation.setFdMainDataText(insystem.getDocSubject());
                            }
                        } else if ("cust".equals(relation.getFdType())) {
                            // 主数据（自定义数据）
                            SysFormMainDataCustom custom = (SysFormMainDataCustom) getSysFormMainDataCustomService().findByPrimaryKey(type, null, true);
                            if (custom != null) {
                                relation.setFdMainDataText(custom.getDocSubject());
                            }
                        }
                    }
                    if (count != null && count.containsKey(relation.getFdId())) {
                        relation.setFdValueCount(count.getIntValue(relation.getFdId()));
                    }
                }
                for (Object obj : rtnForm.getFdRelationResults()) {
                    SysOrgMatrixRelationForm relation = (SysOrgMatrixRelationForm) obj;
                    if (count != null && count.containsKey(relation.getFdId())) {
                        relation.setFdValueCount(count.getIntValue(relation.getFdId()));
                    }
                }
                UserOperHelper.logFind(matrix);

                // 获取分组信息（需要根据权限获取相关分组数据）
                AutoArrayList dataCates = getSysOrgMatrixDataCateService().getDataCates(matrix.getFdId());
                rtnForm.setFdDataCates(dataCates);
                // 开启分组，默认填充一个分组名称
                request.setAttribute("defCate", ResourceUtil.getString("sys-organization:sysOrgMatrix.dataCate.def"));
                // 检测是否有已经删除的字段
                com.alibaba.fastjson.JSONArray fields = getSysOrgMatrixRelationService().checkField(id);
                int delCount = 0;
                for (int i = 0; i < fields.size(); i++) {
                    com.alibaba.fastjson.JSONObject field = fields.getJSONObject(i);
                    if (field.getBooleanValue("isDel")) {
                        delCount++;
                    }
                }
                if (delCount > 0) {
                    request.setAttribute("delCount", delCount);
                    request.setAttribute("delCountDesc", ResourceUtil.getString("sysOrgMatrix.field.check.warn", "sys-organization", null, delCount));
                }
            }
        }
        if (rtnForm == null) {
            throw new NoRecordException();
        }
        request.setAttribute(getFormName(rtnForm, request), rtnForm);
    }

    @Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
        super.changeFindPageHQLInfo(request, hqlInfo);
        StringBuilder whereBlock = new StringBuilder();
        if (StringUtil.isNull(hqlInfo.getWhereBlock())) {
            whereBlock.append("1 = 1");
        } else {
            whereBlock.append(hqlInfo.getWhereBlock());
        }
        String parentCate = request.getParameter("parentCate");
        if (StringUtil.isNotNull(parentCate)) {
            String joinBlock = hqlInfo.getJoinBlock();
            if (StringUtil.isNull(joinBlock) || !joinBlock.contains("sysOrgMatrix.hbmCategory")) {
                hqlInfo.setJoinBlock(StringUtil.linkString(joinBlock, " ", " left join sysOrgMatrix.hbmCategory hbmCategory"));
            }
            whereBlock.append(" and hbmCategory.fdId = :fdId ");
            hqlInfo.setParameter("fdId", parentCate);
        }
        hqlInfo.setWhereBlock(whereBlock.toString());
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysOrgMatrix.class);
    }

    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        SysOrgMatrixForm rtnForm = (SysOrgMatrixForm) form;
        boolean exist = getServiceImp(request).getBaseDao().isExist(SysOrgMatrix.class.getName(), rtnForm.getFdId());
        if (exist) {
            return update(mapping, rtnForm, request, response);
        }
        return super.save(mapping, form, request, response);
    }

    @Override
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        SysOrgMatrixForm rtnForm = (SysOrgMatrixForm) form;
        boolean s1 = BooleanUtils.isTrue(rtnForm.getFdIsEnabledCate());
        SysOrgMatrix model = (SysOrgMatrix) getServiceImp(request).findByPrimaryKey(rtnForm.getFdId(), null, true);
        boolean s2 = BooleanUtils.isTrue(model.getFdIsEnabledCate());
        ActionForward af = super.update(mapping, form, request, response);
        // 如果是从禁用到启用，需要做数据迁移
        if (s1 && !s2) {
            getServiceImp(request).updateDefDataCate(model.getFdId());
        }
        // 从启用到禁用，删除矩阵数据分组
        if (!s1 && s2) {
            getServiceImp(request).updateDefDataCate(model.getFdId());
            getSysOrgMatrixDataCateService().deleteByNotMatrix();
        }
        return af;
    }

    /**
     * AJAX保存矩阵字段
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public ActionForward saveMatrixField(ActionMapping mapping,
                                         ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        String toData = request.getParameter("toData");
        KmssMessages messages = new KmssMessages();
        SysOrgMatrixForm rtnForm = (SysOrgMatrixForm) form;
        try {
            SysOrgMatrix model = (SysOrgMatrix) getServiceImp(request).findByPrimaryKey(rtnForm.getFdId(), null, true);
            if (model == null) {
                boolean conEmpty = false;
                boolean resEmpty = false;
                // 校验条件和结果是否有问题
                if (CollectionUtils.isNotEmpty(rtnForm.getFdRelationConditionals())) {
                    for (Object obj : rtnForm.getFdRelationConditionals()) {
                        SysOrgMatrixRelationForm RelationForm = (SysOrgMatrixRelationForm) obj;
                        RelationForm.setFdWidth(rtnForm.getWidth());
                        if (StringUtil.isNull(RelationForm.getFdType())) {
                            conEmpty = true;
                            break;
                        }
                    }
                } else {
                    conEmpty = true;
                }
                if (CollectionUtils.isNotEmpty(rtnForm.getFdRelationResults())) {
                    for (Object obj : rtnForm.getFdRelationResults()) {
                        SysOrgMatrixRelationForm RelationForm = (SysOrgMatrixRelationForm) obj;
                        RelationForm.setFdWidth(rtnForm.getWidth());
                        if (StringUtil.isNull(RelationForm.getFdType())) {
                            resEmpty = true;
                            break;
                        }
                    }
                } else {
                    resEmpty = true;
                }
                if (conEmpty) {
                    throw new RuntimeException(
                            ResourceUtil.getString("sys-organization:sysOrgMatrix.calculation.conditional.empty"));
                }
                if (resEmpty) {
                    throw new RuntimeException(
                            ResourceUtil.getString("sys-organization:sysOrgMatrix.calculation.result.empty"));
                }
                rtnForm.setMatrixType("2");//2 人工创建的
                super.save(mapping, form, request, response);
            } else {
                boolean s1 = BooleanUtils.isTrue(rtnForm.getFdIsEnabledCate());
                boolean s2 = BooleanUtils.isTrue(model.getFdIsEnabledCate());
                //更新列宽
                if (CollectionUtils.isNotEmpty(rtnForm.getFdRelationConditionals())) {
                    for (Object obj : rtnForm.getFdRelationConditionals()) {
                        SysOrgMatrixRelationForm RelationForm = (SysOrgMatrixRelationForm) obj;
                        RelationForm.setFdWidth(rtnForm.getWidth());
                    }
                }
                if (CollectionUtils.isNotEmpty(rtnForm.getFdRelationResults())) {
                    for (Object obj : rtnForm.getFdRelationResults()) {
                        SysOrgMatrixRelationForm RelationForm = (SysOrgMatrixRelationForm) obj;
                        RelationForm.setFdWidth(rtnForm.getWidth());
                    }
                }

                super.update(mapping, form, request, response);
                // 如果是从禁用到启用，需要做数据迁移
                if (s1 && !s2) {
                    getServiceImp(request).updateDefDataCate(model.getFdId());
                }
                // 从启用到禁用，删除矩阵数据分组
                if (!s1 && s2) {
                    getServiceImp(request).updateDefDataCate(model.getFdId());
                    getSysOrgMatrixDataCateService().deleteByNotMatrix();
                }
            }
            SysOrgMatrix matrix = (SysOrgMatrix) getServiceImp(request).findByPrimaryKey(rtnForm.getFdId(), null, true);
            if (matrix != null) {
                // 条件排序
                Collections.sort(matrix.getFdRelationConditionals());
                // 结果排序
                Collections.sort(matrix.getFdRelationResults());
                rtnForm = (SysOrgMatrixForm) getServiceImp(request).convertModelToForm((IExtendForm) form, matrix, new RequestContext(request));
                JSONObject count = null;
                // 获取每列的数据量
                if ("edit".equals(request.getParameter("method"))) {
                    count = getServiceImp(request).countByColumns(rtnForm.getFdId());
                }
                // 处理主数据(条件)
                for (Object obj : rtnForm.getFdRelationConditionals()) {
                    SysOrgMatrixRelationForm relation = (SysOrgMatrixRelationForm) obj;
                    if (StringUtil.isNotNull(relation.getFdMainDataType())) {
                        String mainDataType = relation.getFdMainDataType();
                        String type = relation.getFdType();
                        // 适应页面排版
                        relation.setFdType(mainDataType);
                        relation.setFdMainDataType(type);
                        if ("sys".equals(relation.getFdType())) {
                            // 主数据（系统内数据）
                            SysFormMainDataInsystem insystem = (SysFormMainDataInsystem) getSysFormMainDataInsystemService().findByPrimaryKey(type, null, true);
                            if (insystem != null) {
                                relation.setFdMainDataText(insystem.getDocSubject());
                            }
                        } else if ("cust".equals(relation.getFdType())) {
                            // 主数据（自定义数据）
                            SysFormMainDataCustom custom = (SysFormMainDataCustom) getSysFormMainDataCustomService().findByPrimaryKey(type, null, true);
                            if (custom != null) {
                                relation.setFdMainDataText(custom.getDocSubject());
                            }
                        }
                    }
                    if (count != null && count.containsKey(relation.getFdId())) {
                        relation.setFdValueCount(count.getIntValue(relation.getFdId()));
                    }
                    // 名称防止XSS
                    relation.setFdName(StringEscapeUtils.escapeHtml4(relation.getFdName()));
                }
                for (Object obj : rtnForm.getFdRelationResults()) {
                    SysOrgMatrixRelationForm relation = (SysOrgMatrixRelationForm) obj;
                    if (count != null && count.containsKey(relation.getFdId())) {
                        relation.setFdValueCount(count.getIntValue(relation.getFdId()));
                    }
                    // 名称防止XSS
                    relation.setFdName(StringEscapeUtils.escapeHtml4(relation.getFdName()));
                }
                UserOperHelper.logFind(matrix);
                // 获取分组信息（需要根据权限获取相关分组数据）
                AutoArrayList dataCates = getSysOrgMatrixDataCateService().getDataCates(matrix.getFdId());
                for (Object obj : dataCates) {
                    SysOrgMatrixDataCateForm cateForm = (SysOrgMatrixDataCateForm) obj;
                    // 查询分组数量
                    long cateCount = getServiceImp(request).countByType(matrix.getFdId(), cateForm.getFdId());
                    cateForm.setCount(cateCount);
                    // 分类名称防止XSS
                    cateForm.setFdName(StringEscapeUtils.escapeHtml4(cateForm.getFdName()));
                }
                rtnForm.setFdDataCates(dataCates);
            }
        } catch (Exception e) {
            messages.addError(e);
        }
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            if ("true".equalsIgnoreCase(toData)) {
                // 跳到数据配置页面
                response.sendRedirect(request.getContextPath() + "/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=editMatrixData&fdId=" + rtnForm.getFdId());
                return null;
            } else {
                KmssReturnPage.getInstance(request).addMessages(messages)
                        .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
                return getActionForward("success", mapping, form, request,
                        response);
            }
        }
    }

    /**
     * 获取矩阵配置信息（字段，版本，分组）
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editMatrixData(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-editMatrixData", true, getClass());
        KmssMessages messages = new KmssMessages();

        try {
            form.reset(mapping, request);
            SysOrgMatrixForm rtnForm = null;
            String id = request.getParameter("fdId");
            if (!StringUtil.isNull(id)) {
                SysOrgMatrix matrix = (SysOrgMatrix) getServiceImp(request).findByPrimaryKey(id, null, true);
                rtnForm = buildMatrixConfig(request, form, matrix);
                //查询列宽
                SysOrgMatrixForm sysOrgMatrixForm = (SysOrgMatrixForm) form;
                SysOrgMatrix sysOrgMatrix = (SysOrgMatrix) getServiceImp(request).findByPrimaryKey(sysOrgMatrixForm.getFdId());
                sysOrgMatrixForm.setWidth(sysOrgMatrix.getFdRelationConditionals().get(0).getFdWidth());
            }
            if (rtnForm == null) {
                throw new NoRecordException();
            }
            request.setAttribute(getFormName(rtnForm, request), rtnForm);
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-editMatrixData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("editMatrixData", mapping, form, request,
                    response);
        }
    }

    private SysOrgMatrixForm buildMatrixConfig(HttpServletRequest request, ActionForm form, SysOrgMatrix matrix) throws Exception {
        SysOrgMatrixForm rtnForm = null;
        if (matrix != null) {
            // 版本排序
            Collections.sort(matrix.getFdVersions());
            // 条件排序
            Collections.sort(matrix.getFdRelationConditionals());
            // 结果排序
            Collections.sort(matrix.getFdRelationResults());
            rtnForm = (SysOrgMatrixForm) getServiceImp(request).convertModelToForm((IExtendForm) form, matrix, new RequestContext(request));
            rtnForm.setWidth(matrix.getFdRelationConditionals().get(0).getFdWidth());
            // 开启分组，默认填充一个分组名称
            request.setAttribute("defCate", ResourceUtil.getString("sys-organization:sysOrgMatrix.dataCate.def"));

            JSONObject result = new JSONObject();
            JSONArray conditionals = new JSONArray();
            for (Object obj : rtnForm.getFdRelationConditionals()) {
                conditionals.add(JSONObject.toJSON(obj));
            }
            result.put("conditionals", conditionals);
            JSONArray results = new JSONArray();
            for (Object obj : rtnForm.getFdRelationResults()) {
                results.add(JSONObject.toJSON(obj));
            }
            result.put("results", results);
            JSONArray versions = new JSONArray();
            for (Object obj : rtnForm.getFdVersions()) {
                versions.add(JSONObject.toJSON(obj));
            }
            result.put("versions", versions);
            // 获取分组信息（需要根据权限获取相关分组数据）
            AutoArrayList dataCates = getSysOrgMatrixDataCateService().getDataCates(matrix.getFdId());
            rtnForm.setFdDataCates(dataCates);
            JSONArray cates = new JSONArray();
            for (Object obj : dataCates) {
                cates.add(JSONObject.toJSON(obj));
            }
            result.put("cates", cates);
            request.setAttribute("MatrixConfig", result);
        }
        return rtnForm;
    }

    /**
     * 批量保存矩阵数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveMatrixDataForm(ActionMapping mapping,
                                            ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdId");
            String data = request.getParameter("data");
            if (StringUtil.isNull(fdId)) {
                throw new KmssRuntimeException(new KmssMessage("errors.required", "fdId"));
            }
            if (StringUtil.isNotNull(data)) {
                JSONObject json = JSONObject.parseObject(data);
                if (json != null && !json.isEmpty()) {
                    for (Object key : json.keySet()) {
                        String version = key.toString();
                        JSONArray array = json.getJSONArray(version);
                        for (int i = 0; i < array.size(); i++) {
                            getServiceImp(request).addMatrixData(fdId, version, array.getJSONObject(i));
                        }
                    }
                }
            }
            // 更新列宽，需要widths参数
            getSysOrgMatrixRelationService().updateWidth(new RequestContext(request));
        } catch (Exception e) {
            messages.addError(e);
        }

        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("success", mapping, form, request,
                    response);
        }
    }

    /**
     * 下载模板
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward downloadTemplate(ActionMapping mapping,
                                          ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdId");
            if (StringUtil.isNull(fdId)) {
                throw new KmssRuntimeException(new KmssMessage("errors.required", "fdId"));
            }
            // 模板名称
            String templetName = getDownloadFileName(fdId);
            // 构建模板文件
            Workbook workbook = getServiceImp(request).downloadTemplate(fdId);
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName="
                    + encodeFileName(request, templetName));
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            return null;
        } catch (IOException e) {
            messages.addError(e);
        }

        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("success", mapping, form, request,
                    response);
        }
    }

    /**
     * 矩阵数据导出
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportMatrixData(ActionMapping mapping,
                                          ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdId");
            String type = request.getParameter("type");
            if (StringUtil.isNull(fdId)) {
                throw new KmssRuntimeException(new KmssMessage("errors.required", "fdId"));
            }
            // 模板名称
            String templetName = getDownloadFileName(fdId);
            // 构建模板文件
            Workbook workbook = null;
            if ("error".equals(type)) {
                String datas = request.getParameter("datas");
                workbook = getServiceImp(request).exportErrorMatrixData(fdId, JSONObject.parseObject(datas));
            } else {
                workbook = getServiceImp(request).exportMatrixData(fdId);
            }

            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName="
                    + encodeFileName(request, templetName));
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            return null;
        } catch (IOException e) {
            messages.addError(e);
        }

        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("success", mapping, form, request,
                    response);
        }
    }

    private String getDownloadFileName(String fdId) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) getServiceImp(null).findByPrimaryKey(fdId);
        // 模板名称
        return ResourceUtil.getString("sysOrgMatrix.download.file.name", "sys-organization", null, matrix.getFdName());
    }

    /**
     * 跳转到导入数据页面
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        super.edit(mapping, form, request, response);
        return getActionForward("importData", mapping, form, request,
                response);
    }

    /**
     * 导入矩阵数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveMatrixData(ActionMapping mapping,
                                        ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        boolean isErr = false;
        JSONObject result = new JSONObject();
        try {
            String type = request.getParameter("type");
            if ("compensate".equals(type)) {
                // 表单上传
                String fdId = request.getParameter("fdId");
                String datas = request.getParameter("datas");
                // 封装数据
                result = getServiceImp(request).saveMatrixData(fdId, JSONObject.parseObject(datas));
            } else {
                // 导入文件
                result = getServiceImp(request).saveMatrixData((SysOrgMatrixForm) form);
            }
            request.setAttribute("result", result);
            int sucCount = 0;
            int failCount = 0;
            if (result.containsKey("datas")) {
                JSONObject _datas = result.getJSONObject("datas");
                for (Object key : _datas.keySet()) {
                    String ver = key.toString();
                    JSONObject __datas = _datas.getJSONObject(ver);
                    JSONArray datas = __datas.getJSONArray("datas");
                    if (datas != null && !datas.isEmpty()) {
                        isErr = true;
                        failCount += datas.size();
                    }
                    sucCount += __datas.getIntValue("success");
                }
            }
            request.setAttribute("sucCount", sucCount);
            request.setAttribute("failCount", failCount);
        } catch (Exception e) {
            isErr = true;
            logger.error("导入数据失败：", e);
            request.setAttribute("error", e.getMessage());
            result.put("matrixId", ((SysOrgMatrixForm) form).getFdId());
            request.setAttribute("result", result);
        }
        if (isErr) {
            return getActionForward("importError", mapping, form, request, response);
        } else {
            return getActionForward("importSuccess", mapping, form, request, response);
        }
    }

    /**
     * 批量保存矩阵数据（按分组进行保存，用于页面版本或分组切换实时保存）
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveMatrixDataByCate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-saveMatrixDataByCate", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            // 矩阵ID
            String matrixId = request.getParameter("matrixId");
            // 版本号
            String version = request.getParameter("version");
            // 分组ID
            String cateId = request.getParameter("cateId");
            // 矩阵数据：[{'id':'value', 'id':'value'}, {'fdId':'value', 'id':'value', 'id':'value'}]
            String matrixData = request.getParameter("matrixData");
            getServiceImp(request).saveMatrixDataByCate(matrixId, version, cateId, JSONArray.parseArray(matrixData));
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-saveMatrixDataByCate", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
                    .save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

    private String encodeFileName(HttpServletRequest request,
                                  String oldFileName) throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent").toUpperCase();
        if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("TRIDENT") > -1
                || userAgent.indexOf("EDGE") > -1) {// ie情况处理
            oldFileName = URLEncoder.encode(oldFileName, "UTF-8");
            // 这里的编码后，空格会被解析成+，需要重新处理
            oldFileName = oldFileName.replace("+", "%20");
        } else {
            oldFileName = new String(oldFileName.getBytes("UTF-8"),
                    "ISO8859-1");
        }
        return oldFileName;
    }

    /**
     * 获取矩阵数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findMatrixPage(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-findMatrixPage", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String fdId = request.getParameter("fdId");
            // 版本
            String fdVersion = request.getParameter("fdVersion");
            // 分组ID
            String fdDataCateId = request.getParameter("fdDataCateId");
            // 数据筛选
            String filter = request.getParameter("filter");

            int pageno = 0;
            int rowsize = 10;
            if (s_pageno != null && s_pageno.length() > 0
                    && Integer.parseInt(s_pageno) > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0
                    && Integer.parseInt(s_rowsize) > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            // 查询关联数据类别的矩阵信息
            Page page = getServiceImp(request).findMatrixPageByType(fdId, fdVersion, pageno, rowsize, fdDataCateId,
                    filter);

            UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-findMatrixPage", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("data", mapping, form, request,
                    response);
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
    public ActionForward invalidated(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
        KmssMessages messages = new KmssMessages();
        String fdId = request.getParameter("fdId");
        try {
            if (StringUtil.isNotNull(fdId)) {
                getServiceImp(request).updateInvalidated(fdId, new RequestContext(request));
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(
                KmssReturnPage.BUTTON_RETURN).save(request);
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
        TimeCounter.logCurrentTime("Action-invalidatedAll", true, getClass());
        KmssMessages messages = new KmssMessages();
        String[] ids = request.getParameterValues("List_Selected");
        try {
            if (ids != null) {
                getServiceImp(request).updateInvalidated(ids, new RequestContext(request));
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
        TimeCounter.logCurrentTime("Action-invalidatedAll", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 编辑矩阵数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editData(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-editData", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdId");
            String id = request.getParameter("id");
            SysOrgMatrixForm rtnForm = null;
            SysOrgMatrix matrix = getServiceImp(request).getMatrixData(fdId, id);
            if (matrix != null) {
                // 条件排序
                Collections.sort(matrix.getFdRelationConditionals());
                // 结果排序
                Collections.sort(matrix.getFdRelationResults());
                rtnForm = (SysOrgMatrixForm) getServiceImp(request)
                        .convertModelToForm((IExtendForm) form, matrix, new RequestContext(request));
            }
            if (rtnForm == null) {
                throw new NoRecordException();
            }

            if (StringUtil.isNotNull(id)) {
                // 更新
                rtnForm.setMethod_GET("edit");
                request.setAttribute("dataId", id);
            } else {
                // 新增
                rtnForm.setMethod_GET("add");
            }
            request.setAttribute(getFormName(rtnForm, request), rtnForm);
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-editData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("editData", mapping, form, request,
                    response);
        }
    }

    /**
     * 新增矩阵数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addData(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-addData", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdMatrixId");
            String fdVersion = request.getParameter("fdVersion");
            String matrixData = request.getParameter("matrixData");
            getServiceImp(request).addMatrixData(fdId, fdVersion, JSONObject.parseObject(matrixData));
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-addData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
    }

    /**
     * 更新矩阵数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateData(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-updateData", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdMatrixId");
            String id = request.getParameter("dataId");
            String matrixData = request.getParameter("matrixData");
            getServiceImp(request).updateMatrixData(fdId, id, JSONObject.parseObject(matrixData));
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-updateData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
    }

    /**
     * 批量新增或更新矩阵数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveAllData(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-saveAllData", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdMatrixId");
            String matrixData = request.getParameter("matrixData");
            String cateId = request.getParameter("cateId");
            // {"V1": [{'id':'value', 'id':'value'}, {'fdId':'value', 'id':'value', 'id':'value'}]}
            getServiceImp(request).saveAllMatrixData(fdId, cateId, JSONObject.parseObject(matrixData));
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-saveAllData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
    }

    /**
     * 删除矩阵数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteData(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-deleteData", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdId");
            String[] ids = request.getParameterValues("List_Selected");
            getServiceImp(request).deleteMatrixDatas(fdId, ids);
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-deleteData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
    }

    /**
     * 删除矩阵版本
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteVersion(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-deleteVersion", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdId");
            String fdVersion = request.getParameter("fdVersion");
            getServiceImp(request).deleteVersion(fdId, fdVersion);
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-deleteVersion", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
    }

    /**
     * 主数据（系统数据）
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward mainData(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-mainData", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            Page page = getSysOrgMatrixMainDataService().findMainDataInsystem(new RequestContext(request));
            request.setAttribute("page", page);
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-mainData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("mainDataList", mapping, form, request,
                    response);
        }
    }

    /**
     * 矩阵模拟器
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward simulation(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-simulation", true, getClass());
        JSONObject result = new JSONObject();
        try {
            JSONObject data = new JSONObject();
            // {'id': '矩阵ID', 'results': '结果1ID;结果2ID', 'option': 1, 'conditionals':
            // [{'id':'条件1ID', 'type': 'fdId/fdName', 'value': '条件值1'},
            // {'id':'条件2ID', 'type': 'fdId/fdName', 'value': '条件值2'}]}
            JSONObject json = JSONObject.parseObject(request.getParameter("data"));
            String[] results = json.getString("results").split("[;,]");
            List<List<SysOrgElement>> list = getServiceImp(request).matrixCalculationByGroup(json);
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    JSONArray array = new JSONArray();
                    for (SysOrgElement elem : list.get(i)) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", elem.getFdId());
                        obj.put("type", elem.getFdOrgType());
                        obj.put("name", elem.getFdName());
                        if (elem.getFdParent() != null) {
                            String pName = elem.getFdParent().getFdParentsName();
                            if (StringUtil.isNotNull(pName)) {
                                obj.put("pname", pName + "_" + elem.getFdParent().getFdName());
                            } else {
                                obj.put("pname", elem.getFdParent().getFdName());
                            }
                        } else {
                            obj.put("pname", "");
                        }
                        array.add(obj);
                    }
                    data.put(results[i], array);
                }
            }
            result.put("success", true);
            result.put("data", data);
        } catch (Exception e) {
            KmssMessages error = new KmssMessages().addError(e);
            result.put("success", false);
            result.put("msg", ResourceUtil.getString(error.getMessages().get(0).getMessageKey()));
            //日志输出
            e.printStackTrace();
        }

        TimeCounter.logCurrentTime("Action-simulation", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result);
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 获取所有版本信息
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getVersions(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-getVersions", true, getClass());
        JSONArray result = new JSONArray();
        try {
            String fdId = request.getParameter("fdId");
            result = getServiceImp(request).getVersions(fdId);
        } catch (Exception e) {
            logger.error("获取矩阵版本信息失败：", e);
        }

        TimeCounter.logCurrentTime("Action-getVersions", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result);
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 进入编辑页
     */
    @Override
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionForward forward = super.edit(mapping, form, request, response);
        SysOrgMatrixForm sysOrgMatrixForm = (SysOrgMatrixForm) form;
        SysOrgMatrixRelationForm sysOrgMatrixRelationForm = (SysOrgMatrixRelationForm) sysOrgMatrixForm.getFdRelationConditionals().get(0);
        String width = StringUtil.isNull(sysOrgMatrixRelationForm.getFdWidth()) ? "100" : sysOrgMatrixRelationForm.getFdWidth();
        sysOrgMatrixForm.setWidth(width);
        String fdId = request.getParameter("fdId");
        JSONArray list = getServiceImp(request).getVersions(fdId);
        request.setAttribute("allVersions", list);
        int lastVersion = 0;
        if (list != null && !list.isEmpty()) {
            lastVersion = list.getJSONObject(list.size() - 1).getIntValue("fdVersion");
        }
        request.setAttribute("lastVersion", lastVersion);
        return forward;
    }

    /**
     * 进入查看页
     */
    @Override
    public ActionForward view(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionForward forward = super.view(mapping, form, request, response);
        SysOrgMatrixForm sysOrgMatrixForm = (SysOrgMatrixForm) form;
        SysOrgMatrixRelationForm relationForm = (SysOrgMatrixRelationForm) sysOrgMatrixForm.getFdRelationConditionals().get(0);
        String width = StringUtil.isNull(relationForm.getFdWidth()) ? "100" : relationForm.getFdWidth();
        request.setAttribute("width",width);
        sysOrgMatrixForm.setWidth(width);
        String fdId = request.getParameter("fdId");
        JSONArray list = getServiceImp(request).getVersions(fdId);
        request.setAttribute("allVersions", list);
        return forward;
    }

    /**
     * 批量导入
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addMoreDatas(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-setTemplateVersion", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String fdId = request.getParameter("fdId");
            //当前批量新增数据的version
            String curVersion = request.getParameter("curVersion");
            SysOrgMatrix matrix = (SysOrgMatrix) getServiceImp(request).findByPrimaryKey(fdId, null, true);
            if (matrix != null) {
                loadActionForm(mapping, form, request, response);
                request.setAttribute("curVersion", curVersion);
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-setTemplateVersion", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("addDatas", mapping, form, request,
                    response);
        }
    }

    /**
     * 批量替换矩阵数据
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchReplace(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-batchReplace", true, getClass());
        JSONObject result = new JSONObject();
        try {
            String fdId = request.getParameter("fdId");
            String version = request.getParameter("version");
            String json = request.getParameter("json");
            getServiceImp(request).batchReplace(fdId, version, JSONObject.parseObject(json));
            result.put("success", true);
        } catch (Exception e) {
            logger.error("批量替换矩阵数据失败：", e);
            result.put("success", false);
            result.put("msg", e.getMessage());
        }

        TimeCounter.logCurrentTime("Action-batchReplace", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result);
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 矩阵数据维护
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward dataCate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        String fdId = request.getParameter("fdId");
        // 获取所有版本和分组
        JSONArray list = getServiceImp(request).getVersions(fdId);
        request.setAttribute("allVersions", list);
        int lastVersion = 0;
        if (list != null && !list.isEmpty()) {
            lastVersion = list.getJSONObject(list.size() - 1).getIntValue("fdVersion");
        }
        String id = request.getParameter("fdId");
        if (!StringUtil.isNull(id)) {
            SysOrgMatrix matrix = (SysOrgMatrix) getServiceImp(request).findByPrimaryKey(id, null, true);
            buildMatrixConfig(request, form, matrix);
        }
        request.setAttribute("lastVersion", lastVersion);
        loadActionForm(mapping, form, request, response);
        return getActionForward("dataCate", mapping, form, request, response);
    }

    /**
     * 获取一个ID
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-getId", true, getClass());
        String id = IDGenerator.generateID();
        TimeCounter.logCurrentTime("Action-getId", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(id);
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 数据筛选
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward filterData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-filterData", true, getClass());
        JSONObject data = getServiceImp(request).filterData(new RequestContext(request));
        TimeCounter.logCurrentTime("Action-filterData", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(data.toJSONString());
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 创建版本（实时增加版本）
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-addVersion", true, getClass());

        JSONObject data = new JSONObject();
        try {
            String fdMatrixId = request.getParameter("fdMatrixId");
            if (StringUtil.isNull(fdMatrixId)) {
                data.put("success", false);
                data.put("msg", "fdMatrixId is NULL");
            } else {
                SysOrgMatrix matrix = (SysOrgMatrix) getServiceImp(request).findByPrimaryKey(fdMatrixId);
                SysOrgMatrixVersion version = getSysOrgMatrixVersionService().addVersion(matrix);
                data.put("version", version.getFdName());
                data.put("success", true);
            }
        } catch (Exception e) {
            logger.error("创建版本失败：", e);
            data.put("success", false);
            data.put("msg", e.getMessage());
        }
        TimeCounter.logCurrentTime("Action-addVersion", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(data.toJSONString());
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 版本复制
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward copyVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-copyVersion", true, getClass());
        JSONObject data = new JSONObject();
        try {
            String fdMatrixId = request.getParameter("fdMatrixId");
            String fdVersion = request.getParameter("fdVersion");
            // 版本复制
            SysOrgMatrixVersion newVersion = getServiceImp(request).copyMatrixData(fdMatrixId, fdVersion);
            data.put("version", newVersion.getFdName());
            data.put("success", true);
        } catch (Exception e) {
            logger.error("复制版本失败：", e);
            data.put("success", false);
            data.put("msg", e.getMessage());
        }

        TimeCounter.logCurrentTime("Action-copyVersion", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(data.toJSONString());
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 修改版本状态（激活、禁用）
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateVerState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-updateVerState", true, getClass());
        JSONObject data = new JSONObject();
        try {
            getSysOrgMatrixVersionService().updateEnable(new RequestContext(request));
            data.put("success", true);
        } catch (Exception e) {
            logger.error("激活版本失败：", e);
            data.put("success", false);
            data.put("msg", e.getMessage());
        }

        TimeCounter.logCurrentTime("Action-updateVerState", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(data.toJSONString());
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    @Override
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.add(mapping, form, request, response);
        SysOrgMatrixForm sysOrgMatrixForm = (SysOrgMatrixForm) form;
        SysOrgMatrixRelationForm sysOrgMatrixRelationForm = (SysOrgMatrixRelationForm) sysOrgMatrixForm.getFdRelationConditionals().get(0);
        String width = StringUtil.isNull(sysOrgMatrixRelationForm.getFdWidth()) ? "100" : sysOrgMatrixRelationForm.getFdWidth();
        sysOrgMatrixForm.setWidth(width);
        return forward;
    }
}
