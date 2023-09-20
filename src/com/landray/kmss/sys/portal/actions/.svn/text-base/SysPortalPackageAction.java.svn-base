package com.landray.kmss.sys.portal.actions;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.portal.forms.SysPortalPackageForm;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.service.ISysPortalService;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @description: 门户导入导出
 * @author: wangjf
 * @time: 2021/6/20 7:37 下午
 * @version: 1.0
 */

public class SysPortalPackageAction extends ExtendAction {

    protected ISysPortalMainService sysPortalMainService;

    @Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
        if (sysPortalMainService == null) {
            sysPortalMainService = (ISysPortalMainService) getBean("sysPortalMainService");
        }
        return sysPortalMainService;
    }

    private ISysPortalService getSysPortalService() {
        return (ISysPortalService) getBean("sysPortalService");
    }

    /**
     * 导出门户
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @description:
     * @return: com.landray.kmss.web.action.ActionForward
     * @author: wangjf
     * @time: 2021/6/11 5:41 下午
     */
    public ActionForward exportPortal(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-exportPortal", true, getClass());
        KmssMessages messages = new KmssMessages();
        String portalId = request.getParameter("portalId");

        IBaseModel byPrimaryKey = getServiceImp(request).findByPrimaryKey(portalId);
        if (byPrimaryKey == null) {
            messages.addError(new RuntimeException("未找到需要导出的门户信息"));
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        }
        getSysPortalService().getExport(portalId, request, response);
        TimeCounter.logCurrentTime("Action-exportPortal", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        }
        return null;
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @description: 导入门户
     * @return: com.landray.kmss.web.action.ActionForward
     * @author: wangjf
     * @time: 2021/6/18 9:05 上午
     */
    public ActionForward importPortal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-importPortal", true, getClass());
        KmssMessages messages = new KmssMessages();

        try {
            SysPortalPackageForm sysPortalPackageForm = (SysPortalPackageForm) form;
            getSysPortalService().saveImport(sysPortalPackageForm.getPortalZipPath(),new RequestContext(request));
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-importPortal", false, getClass());
        if (messages.hasError()) {
            request.setAttribute("successMessage",
                    ResourceUtil.getString("sys.portal.import.error", "sys.portal"));
            return getActionForward("uploadMessage", mapping, form, request,
                    response);
        } else {
            request.setAttribute("successMessage",
                    ResourceUtil.getString("sys.portal.upload.success", "sys.portal"));
            return getActionForward("uploadMessage", mapping, form, request,
                    response);
        }
    }

    /**
     * 上传门户压缩包
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @description:
     * @return: com.landray.kmss.web.action.ActionForward
     * @author: wangjf
     * @time: 2021/6/20 6:11 下午
     */
    public ActionForward uploadPortal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        response.setCharacterEncoding("UTF-8");
        try {
            String portalZipPath = getSysPortalService().saveUpload(new RequestContext(request), (SysPortalPackageForm) form);
            JSONObject rtnJson = new JSONObject();
            rtnJson.put("portalZipPath", portalZipPath);
            rtnJson.put("status", "1");
            response.getWriter().print(rtnJson);
        } catch (Exception e) {
            messages.addError(e);
        }

        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @description:
     * @return: com.landray.kmss.web.action.ActionForward
     * @author: wangjf
     * @time: 2021/6/20 8:07 下午
     */
    public ActionForward deletePortal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        response.setCharacterEncoding("UTF-8");
        String tmpFilePath = ResourceUtil.KMSS_RESOURCE_PATH + File.separator + "portalTmp" + File.separator;
        try {
            String portalZipPath = request.getParameter("portalZipPath");
            if (StringUtil.isNotNull(portalZipPath)) {
                // 必须是文件，不允许是文件夹
                boolean file = new File(portalZipPath).isFile();
                //最后修改时间是十分钟内的可以删除，否则是非法操作
                boolean checkTime = (System.currentTimeMillis() - FileUtil.getLastModify(portalZipPath)) < 10*60*1000;
                if(portalZipPath.contains(tmpFilePath) && portalZipPath.endsWith(".zip") && checkTime && file){
                    FileUtil.deleteFile(portalZipPath);
                }
            }

        } catch (Exception e) {
            messages.addError(e);
        }

        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }

        return null;
    }
}