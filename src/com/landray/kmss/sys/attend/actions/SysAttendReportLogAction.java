package com.landray.kmss.sys.attend.actions;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.attend.model.SysAttendReportLog;
import com.landray.kmss.sys.attend.service.ISysAttendReportLogService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SysAttendReportLogAction extends ExtendAction {

    private ISysAttendReportLogService sysAttendReportLogService;
    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    protected ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if(sysNotifyMainCoreService ==null){
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
                    .getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }


    @Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
        if (sysAttendReportLogService == null) {
            sysAttendReportLogService = (ISysAttendReportLogService) getBean("sysAttendReportLogService");
        }
        return sysAttendReportLogService;
    }

    /**
     * 获取正在导出的数量
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getDownLoadNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-exportDetail", true, getClass());
        KmssMessages messages = new KmssMessages();

        String fdType = request.getParameter("fdType");
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        if(StringUtil.isNotNull(fdType)){
            Integer number =((ISysAttendReportLogService)getServiceImp(request)).getExportNumber(fdType);
            json.put("exportNumber",number);
        } else {
            json.put("exportNumber",0);
        }
        TimeCounter.logCurrentTime("Action-exportDetail", false, getClass());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(json.toString());
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }

    /**
     * 获取正在导出的数量
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getDownLoadList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-getDownLoadList", true, getClass());

        String fdType = request.getParameter("fdType");
        com.alibaba.fastjson.JSONArray jsonArray = new com.alibaba.fastjson.JSONArray();
        if(StringUtil.isNotNull(fdType)){
            HQLInfo hqlInfo=new HQLInfo();
            hqlInfo.setWhereBlock(" sysAttendReportLog.docCreator.fdId=:userId and sysAttendReportLog.fdType =:fdType");
            hqlInfo.setOrderBy(" sysAttendReportLog.docCreateTime desc ");
            hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
            hqlInfo.setParameter("fdType", fdType);
            hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
            //获取所有的下载记录列表
            List<SysAttendReportLog> list =((ISysAttendReportLogService)getServiceImp(request)).findList(hqlInfo);

            for (SysAttendReportLog log: list ) {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("fileName",log.getFdName());
                jsonObject.put("fileId",log.getFdFileName());
                jsonObject.put("fdId",log.getFdId());
                jsonObject.put("docCreateTime", DateUtil.convertDateToString(log.getDocCreateTime(),"yyyy-MM-dd HH:mm"));
                jsonObject.put("fdStatus",log.getFdStatus());
                jsonObject.put("desc",log.getFdDesc());
                jsonArray.add(jsonObject);
            }
        }
        TimeCounter.logCurrentTime("Action-getDownLoadList", false, getClass());

        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(jsonArray.toString());
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }



    /**
     * 获取正在导出的数量
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward downloadExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-downloadExcel", true, getClass());

        KmssMessages messages = new KmssMessages();

        try {
            String fdId = request.getParameter("fdId");
            if(StringUtil.isNotNull(fdId)){
              SysAttendReportLog reportLog = (SysAttendReportLog) getServiceImp(request).findByPrimaryKey(fdId);
              if(reportLog !=null) {
                  //删除待办
                  getSysNotifyMainCoreService().getTodoProvider().removePerson(reportLog, "sysAttendReportHandel",reportLog.getDocCreator());
                  //转发跳转 下载文件
                  String url = String.format("%s/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=%s&downloadType=manual&downloadFlag=%s", request.getContextPath(),reportLog.getFdFileName(), System.currentTimeMillis());
                  response.sendRedirect(url);
                  return null;
              }
            }
            TimeCounter.logCurrentTime("Action-downloadExcel", false, getClass());
        } catch (Exception e) {
            messages.addError(e);
        }
        messages.addError(new Exception("File not find"));
        KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE) .save(request);
        return getActionForward("failure", mapping, form, request, response);
    }

    /**
     * 获取正在导出的数量
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-deleteAjax", true, getClass());
        JSONObject  jsonObject = new JSONObject();
        jsonObject.put("data","error");
        try {
            String fdId = request.getParameter("fdId");
            if(StringUtil.isNotNull(fdId)){
                getServiceImp(request).delete(fdId);
                jsonObject.put("data","success");
            }
            TimeCounter.logCurrentTime("Action-deleteAjax", false, getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(jsonObject.toString());
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }



}
