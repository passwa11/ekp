package com.landray.kmss.eop.basedata.actions;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataBusinessForm;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.service.IEopBasedataBusinessService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPullAndPushService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 财务基础档案公共Action，处理常用业务
 *
 * @author wangjinman
 */
public class EopBasedataBusinessAction extends ExtendAction {

    private Logger logger = LoggerFactory.getLogger(EopBasedataBusinessAction.class);

    @Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
        String modelName = request.getParameter("modelName");
        String bean = SysDataDict.getInstance().getModel(modelName).getServiceBean();
        return (IBaseService) SpringBeanUtil.getBean(bean);
    }

    protected IEopBasedataBusinessService eopBasedataBusinessService;

    protected IEopBasedataBusinessService getEopBasedataBusinessService() {
        if (eopBasedataBusinessService == null) {
            eopBasedataBusinessService = (IEopBasedataBusinessService) SpringBeanUtil.getBean("eopBasedataBusinessService");
        }
        return eopBasedataBusinessService;
    }

    /**
     * 启用档案
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward enable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ids = request.getParameter("ids");
        String[] idarr = ids.split("&");
        String idstr = "";
        String id = "";
        for (int i = 0; i < idarr.length; i++) {
            id = idarr[i].replace("List_Selected=", "").trim();
            if (i < idarr.length) {
                idstr += id + ";";
            } else {
                idstr += id;
            }
        }
        String modelName = request.getParameter("modelName");
        try {
            List<String> list = new ArrayList<String>();
            if (SysDataDict.getInstance().getModel(modelName).getPropertyMap().containsKey("fdCode")) {//包含fdCode属性才需要校验
                list = ((IEopBasedataBusinessService) getServiceImp(request)).checkEnable(ids, modelName);
            }
            if (list.size() > 0) {
                return getActionForward("failure", mapping, form, request, response);
            }
            ((IEopBasedataBusinessService) getServiceImp(request)).saveEnable(idstr, modelName);
        } catch (Exception e) {
            return getActionForward("failure", mapping, form, request, response);
        }
        return getActionForward("success", mapping, form, request, response);
    }

    /**
     * 禁用档案
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward disable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ids = request.getParameter("ids");
        String[] idarr = ids.split("&");
        String idstr = "";
        String id = "";
        for (int i = 0; i < idarr.length; i++) {
            id = idarr[i].replace("List_Selected=", "").trim();
            if (i < idarr.length) {
                idstr += id + ";";
            } else {
                idstr += id;
            }
        }
        String modelName = request.getParameter("modelName");
        try {
            ((IEopBasedataBusinessService) getServiceImp(request)).saveDisable(idstr, modelName);
        } catch (Exception e) {
            return getActionForward("failure", mapping, form, request, response);
        }
        return getActionForward("success", mapping, form, request, response);
    }

    /**
     * 复制档案
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ids = request.getParameter("ids");
        String modelName = request.getParameter("modelName");
        String fdCompanyIds = request.getParameter("fdCompanyIds");
        try {
            ((IEopBasedataBusinessService) getServiceImp(request)).saveCopy(ids, modelName, fdCompanyIds);
        } catch (Exception e) {
            e.printStackTrace();
            return getActionForward("failure", mapping, form, request, response);
        }
        return getActionForward("success", mapping, form, request, response);
    }

    /**
     * 导入档案
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveImport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataBusinessForm uploadForm = (EopBasedataBusinessForm) form;
        List<EopBasedataImportMessage> messages = null;
        KmssMessages msgs = new KmssMessages();
        try {
            messages = ((IEopBasedataBusinessService) getServiceImp(request)).saveImport(uploadForm.getFdFile(), uploadForm.getModelName());
            request.setAttribute("messages", messages);
        } catch (Exception e) {
            msgs.addError(e);
        }
        if (msgs.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(msgs).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, uploadForm, request, response);
        } else if (ArrayUtil.isEmpty(messages)) {
            return getActionForward("success", mapping, uploadForm, request, response);
        }
        return getActionForward("importResult", mapping, uploadForm, request, response);
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
    public ActionForward downloadTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String modelName = request.getParameter("modelName");
        KmssMessages msgs = new KmssMessages();
        try {
            ((IEopBasedataBusinessService) getServiceImp(request)).downloadTemplate(response, modelName);
        } catch (Exception e) {
            msgs.addError(e);
        }
        if (msgs.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(msgs).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return null;
        }
    }

    /**
     * 导出
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String modelName = request.getParameter("modelName");
        String fdCompanyId = request.getParameter("fdCompanyId");
        KmssMessages msgs = new KmssMessages();
        try {
            ((IEopBasedataBusinessService) getServiceImp(request)).exportData(response, modelName, fdCompanyId);
        } catch (Exception e) {
            msgs.addError(e);
        }
        if (msgs.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(msgs).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return null;
        }
    }

    /**
     * 导入档案
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward detailImport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataBusinessForm uploadForm = (EopBasedataBusinessForm) form;
        String properties = request.getParameter("properties");
        String fdCompanyId = request.getParameter("fdCompanyId");
        String docTemplateId = request.getParameter("docTemplateId");
        JSONArray data = null;
        JSONObject cols = null;
        List<EopBasedataImportMessage> messages = null;
        KmssMessages msgs = new KmssMessages();
        try {
            Map<String, Object> rtn = getEopBasedataBusinessService().detailImport(uploadForm.getFdFile(), uploadForm.getModelName(), properties, fdCompanyId, docTemplateId);
            data = (JSONArray) rtn.get("data");
            cols = (JSONObject) rtn.get("cols");
            messages = (List<EopBasedataImportMessage>) rtn.get("messages");
            response.setCharacterEncoding("UTF-8");
            request.setAttribute("messages", messages);
            request.setAttribute("data", data.toString());
            request.setAttribute("cols", cols.toString());
        } catch (Exception e) {
            msgs.addError(e);
        }
        if (msgs.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(msgs).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, uploadForm, request, response);
        } else {
            return getActionForward("importResultDetail", mapping, uploadForm, request, response);
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
    public ActionForward downloadDetailTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String modelName = request.getParameter("modelName");
        String properties = request.getParameter("properties");
        KmssMessages msgs = new KmssMessages();
        try {
            getEopBasedataBusinessService().downloadDetailTemplate(response, modelName, properties);
        } catch (Exception e) {
            msgs.addError(e);
        }
        if (msgs.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(msgs).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return null;
        }
    }

    /**
     * 检查是否存在向业务模块推送（同步）基础数据的扩展点
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward checkSyncExtendPoint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String modelName = request.getParameter("func");

        List<IEopBasedataPullAndPushService> ppServiceList = EopBasedataUtil.getPullAndPushService(modelName);
        RestResponse resp = RestResponse.ok(null);
        if (CollectionUtils.isEmpty(ppServiceList)) {
            logger.error("{}不存在对就在的扩展点", modelName);
            resp = RestResponse.error("0001", "不存在对就在的扩展点");
        }

        request.setAttribute("lui-source", JSON.toJSONString(resp));
        return getActionForward("lui-source", mapping, form, request, response);
    }
}
