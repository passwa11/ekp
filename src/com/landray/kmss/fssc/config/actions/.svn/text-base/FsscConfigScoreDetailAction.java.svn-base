package com.landray.kmss.fssc.config.actions;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.util.List;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.util.KmssReturnPage;
import java.net.URLDecoder;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONValue;
import net.sf.json.JSONObject;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.fssc.config.service.IFsscConfigScoreDetailService;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.fssc.config.util.FsscConfigUtil;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.fssc.config.model.FsscConfigScoreDetail;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 积分使用记录 Action
  */
public class FsscConfigScoreDetailAction extends ExtendAction {

    private IFsscConfigScoreDetailService fsscConfigScoreDetailService;

    private ISysOrgElementService sysOrgElementService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscConfigScoreDetailService == null) {
            fsscConfigScoreDetailService = (IFsscConfigScoreDetailService) getBean("fsscConfigScoreDetailService");
        }
        return fsscConfigScoreDetailService;
    }

    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscConfigScoreDetail.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.fssc.config.util.FsscConfigUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.fssc.config.model.FsscConfigScoreDetail.class);
        com.landray.kmss.fssc.config.util.FsscConfigUtil.buildHqlInfoModel(hqlInfo, request);
    }

    public ActionForward data(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String iden = request.getParameter("iden");
        if (StringUtil.isNull(iden)) {
            iden = "data";
        }
        TimeCounter.logCurrentTime("Action-list", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String orderby = request.getParameter("orderby");
            String ordertype = request.getParameter("ordertype");
            boolean isReserve = false;
            if (ordertype != null && ordertype.equalsIgnoreCase("down")) {
                isReserve = true;
            }
            int pageno = 0;
            int rowsize = com.landray.kmss.sys.config.model.SysConfigParameters.getRowSize();
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
            changeFindPageHQLInfo(request, hqlInfo);
            com.sunbor.web.tag.Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("Action-list", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward(iden, mapping, form, request, response);
        }
    }

    /**
     * 列表数据导出
     */
    public ActionForward listExport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            if (!request.getMethod().equals("POST")) {
                throw new UnexpectedRequestException();
            }
            String json = request.getParameter("json");
            json = URLDecoder.decode(json, "UTF-8");
            String ths = request.getParameter("ths");
            ths = URLDecoder.decode(ths, "UTF-8");
            String[] Headers = ths.split(",");
            JSONArray jsonData = JSONArray.fromObject(json);
            List dataList = jsonData.subList(0, jsonData.size());
            for (Object data : dataList) {
                List lvl1 = (List) ((List) data).get(1);
                List seq = (List) lvl1.get(0);
                String seqValue = String.valueOf(seq.get(1));
                List subject = (List) lvl1.get(1);
                String subjectValue = String.valueOf(subject.get(1));
            }
            String exportName = ResourceUtil.getString("detail.transport.listExport", "fssc-config") + ".xls";
            HSSFWorkbook workbook = FsscConfigUtil.exportWorkBook(Headers, dataList);
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + FsscConfigUtil.encodeFileName(request, exportName));
            OutputStream out = (OutputStream) response.getOutputStream();
            workbook.write(out);
            return null;
        } catch (Exception e) {
            messages.addError((Throwable) e);
            if (messages.hasError()) {
                KmssReturnPage.getInstance(request).addMessages(messages).save(request);
                return this.getActionForward("failure", mapping, form, request, response);
            }
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(0).save(request);
            return this.getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 下载导入模板
     */
    public ActionForward templateDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            if (!request.getMethod().equals("POST")) {
                throw new UnexpectedRequestException();
            }
            String ths = request.getParameter("thsvalue");
            ths = URLDecoder.decode(ths, "UTF-8");
            String[] Headers = ths.split(",");
            String exportName = ResourceUtil.getString("detail.transport.templateDownload", "fssc-config") + ".xls";
            HSSFWorkbook workbook = FsscConfigUtil.exportWorkBook(Headers, null);
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + FsscConfigUtil.encodeFileName(request, exportName));
            OutputStream out = (OutputStream) response.getOutputStream();
            workbook.write(out);
            return null;
        } catch (Exception e) {
            messages.addError((Throwable) e);
            if (messages.hasError()) {
                KmssReturnPage.getInstance(request).addMessages(messages).save(request);
                return this.getActionForward("failure", mapping, form, request, response);
            }
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(0).save(request);
            return this.getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 导入数据上传地址
     */
    public void uploadActionUrl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        com.landray.kmss.fssc.config.forms.FsscConfigScoreDetailForm fileForm = (com.landray.kmss.fssc.config.forms.FsscConfigScoreDetailForm) form;
        FormFile file = fileForm.getFile();
        String fdImportType = fileForm.getFdImportType();
        JSONObject job = new JSONObject();
        job.element("importType", fdImportType);
        org.json.simple.JSONArray jsArray = new org.json.simple.JSONArray();
        jsArray.add(job);
        jsArray = FsscConfigUtil.importData(file, jsArray);
        response.setContentType("text/html;charset=GBK");
        response.getWriter().print(jsArray);
        response.getWriter().flush();
        response.getWriter().close();
    }

    public ISysOrgElementService getSysOrgElementService(HttpServletRequest request) {
        if (sysOrgElementService == null) {
            sysOrgElementService = (com.landray.kmss.sys.organization.service.ISysOrgElementService) getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    /**
     * 检查组织架构对象是否有误
     */
    public void checkSysOrgElement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fdId = request.getParameter("fdId");
        String fdName = request.getParameter("fdName");
        if (StringUtil.isNotNull(fdId) && StringUtil.isNotNull(fdName)) {
            SysOrgElement sysOrgElement = (SysOrgElement) getSysOrgElementService(request).findByPrimaryKey(fdId, null, true);
            if (sysOrgElement != null && sysOrgElement.getFdName().equals(fdName)) {
                response.getWriter().print("true");
            } else {
                response.getWriter().print("false");
            }
        } else {
            response.getWriter().print("false");
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
