package com.landray.kmss.sys.organization.actions;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmToolsService;
import com.landray.kmss.sys.organization.model.SysOrgMatrixTemplate;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixTemplateService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysOrgMatrixTemplateAction extends ExtendAction {

    private ISysOrgMatrixTemplateService sysOrgMatrixTemplateService;

    @Override
    protected ISysOrgMatrixTemplateService getServiceImp(HttpServletRequest request) {
        if (sysOrgMatrixTemplateService == null) {
            sysOrgMatrixTemplateService = (ISysOrgMatrixTemplateService) getBean("sysOrgMatrixTemplateService");
        }
        return sysOrgMatrixTemplateService;
    }

    @Override
    protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        super.changeFindPageHQLInfo(request, hqlInfo);
        String matrixId = request.getParameter("matrixId");
        StringBuffer whereBlock = new StringBuffer();
        if (StringUtil.isNotNull(matrixId)) {
            whereBlock.append("sysOrgMatrixTemplate.fdMatrixId = :matrixId");
            hqlInfo.setParameter("matrixId", matrixId);
        }

        hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", whereBlock.toString()));
        request.setAttribute("matrixId", matrixId);
    }

    @Override
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        ActionForward forward = super.list(mapping, form, request, response);
        Page page = (Page) request.getAttribute("queryPage");
        // 处理流程模板标题
        List<SysOrgMatrixTemplate> list = page.getList();
        Map<String, String> subjectMap = new HashMap<String, String>();
        Map<String, String> urlMap = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysOrgMatrixTemplate tpl : list) {
                String[] values = getTplSubject(tpl);
                subjectMap.put(tpl.getFdId(), values[0]);
                urlMap.put(tpl.getFdId(), values[1]);
            }
        }
        request.setAttribute("subjectMap", subjectMap);
        request.setAttribute("urlMap", urlMap);
        return forward;
    }

    private String[] getTplSubject(SysOrgMatrixTemplate tpl) throws Exception {
        if (StringUtil.isNotNull(tpl.getFdTemplateId()) && StringUtil.isNotNull(tpl.getFdTemplateName())) {
            SysDictModel model = SysDataDict.getInstance().getModel(tpl.getFdTemplateName());
            if (model != null && StringUtil.isNotNull(model.getServiceBean())
                    && StringUtil.isNotNull(model.getDisplayProperty())) {
                String property = model.getDisplayProperty();
                String url = model.getUrl();
                IBaseService service = (IBaseService) SpringBeanUtil.getBean(model.getServiceBean());
                if (service != null) {
                    IBaseModel temp = service.findByPrimaryKey(tpl.getFdTemplateId(), tpl.getFdTemplateName(), true);
                    if (temp != null) {
                        String href = "";
                        String subject = "";
                        if (StringUtil.isNotNull(url)) {
                            href = url.replace("${fdId}", temp.getFdId());
                        }
                        if (PropertyUtils.isReadable(temp, property)) {
                            subject = BeanUtils.getProperty(temp, property);
                        }
                        return new String[]{subject, href};
                    }
                }
            }
        }
        return new String[]{"", ""};
    }

    /**
     * 更新所有模板
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateTemplateVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-updateTemplateVersion", true, getClass());
        JSONObject data = new JSONObject();
        try {
            getServiceImp(request).updateTemplateVersion(new RequestContext(request));
            data.put("success", true);
        } catch (Exception e) {
            data.put("success", false);
            data.put("msg", e.getMessage());
        }

        TimeCounter.logCurrentTime("Action-updateTemplateVersion", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(data.toJSONString());
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

}
