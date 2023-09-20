package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataContactForm;
import com.landray.kmss.eop.basedata.model.EopBasedataContact;
import com.landray.kmss.eop.basedata.service.IEopBasedataContactService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author wangwh
 * @description:供应商联系人action
 * @date 2021/5/7
 */
public class EopBasedataContactAction extends ExtendAction {

    private IEopBasedataContactService eopBasedataContactService;

    private ISysOrgElementService sysOrgElementService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataContactService == null) {
            eopBasedataContactService = (IEopBasedataContactService) getBean("eopBasedataContactService");
        }
        return eopBasedataContactService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataContact.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, EopBasedataContact.class);
        EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
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
            if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
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

    public ActionForward listExport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        HSSFWorkbook workbook = null;
        try {
            if (!"POST".equals(request.getMethod())) {
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
            String exportName = ResourceUtil.getString("detail.transport.listExport", "eop-basedata") + ".xls";
            workbook = EopBasedataUtil.exportWorkBook(Headers, dataList);
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + EopBasedataUtil.encodeFileName(request, exportName));
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
        }finally {
            workbook.close();
        }
    }

    public ActionForward templateDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        HSSFWorkbook workbook = null;
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            String ths = request.getParameter("thsvalue");
            ths = URLDecoder.decode(ths, "UTF-8");
            String[] Headers = ths.split(",");
            String exportName = ResourceUtil.getString("detail.transport.templateDownload", "eop-basedata") + ".xls";
            workbook = EopBasedataUtil.exportWorkBook(Headers, null);
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + EopBasedataUtil.encodeFileName(request, exportName));
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
        }finally {
            workbook.close();
        }
    }

    public void uploadActionUrl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataContactForm fileForm = (EopBasedataContactForm) form;
        FormFile file = fileForm.getFile();
        String fdImportType = fileForm.getFdImportType();
        JSONObject job = new JSONObject();
        job.element("importType", fdImportType);
        org.json.simple.JSONArray jsArray = new org.json.simple.JSONArray();
        jsArray.add(job);
        jsArray = EopBasedataUtil.importData(file, jsArray);
        response.setContentType("text/html;charset=GBK");
        response.getWriter().print(jsArray);
        response.getWriter().flush();
        response.getWriter().close();
    }

    public ISysOrgElementService getSysOrgElementService(HttpServletRequest request) {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

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
