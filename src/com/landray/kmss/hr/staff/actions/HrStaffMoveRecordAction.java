package com.landray.kmss.hr.staff.actions;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.forms.HrStaffMoveRecordForm;
import com.landray.kmss.hr.staff.model.HrStaffMoveRecord;
import com.landray.kmss.hr.staff.report.*;
import com.landray.kmss.hr.staff.service.IHrStaffMoveRecordService;
import com.landray.kmss.hr.staff.util.Excel02Util;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.hr.staff.util.WordUtil;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.report.AttendMonthDeptOver40Report;
import com.landray.kmss.sys.attend.report.AttendMonthDeptReport;
import com.landray.kmss.sys.attend.report.AttendMonthReport;
import com.landray.kmss.sys.attend.service.ISysAttendStatDetailService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 异动信息 Action
 */
public class HrStaffMoveRecordAction extends HrStaffImportAction {

    private IHrStaffMoveRecordService hrStaffMoveRecordService;

    @Override
    public IHrStaffMoveRecordService getServiceImp(HttpServletRequest request) {
        if (hrStaffMoveRecordService == null) {
            hrStaffMoveRecordService = (IHrStaffMoveRecordService) getBean("hrStaffMoveRecordService");
        }
        return hrStaffMoveRecordService;
    }

    private SysAttendPersonReportService sysAttendPersonReportService;

    public SysAttendPersonReportService getSysAttendPersonReportService() {
        if (sysAttendPersonReportService == null) {
            sysAttendPersonReportService = (SysAttendPersonReportService) getBean("sysAttendPersonReportService");
        }
        return sysAttendPersonReportService;
    }
    private String _fdKey ;
    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrStaffMoveRecord.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        CriteriaValue cv = new CriteriaValue(request);

		_fdKey = cv.poll("_fdKey");
        String hqlWhere = hqlInfo.getWhereBlock();
        StringBuffer whereBlock;
        if (StringUtil.isNotNull(hqlWhere)) {
            whereBlock = new StringBuffer(hqlWhere);
        } else {
            whereBlock = new StringBuffer("1 = 1");
        }
        // 员工状态
        String[] _fdStatus = cv.polls("_fdStatus");
        if (_fdStatus != null && _fdStatus.length > 0) {
            List<String> fdStatus = new ArrayList<String>();
            boolean isNull = false;
            for (String _fdStatu : _fdStatus) {
                if ("official".equals(_fdStatu)) {
                    isNull = true;
                }
                fdStatus.add(_fdStatu);
            }
            whereBlock.append(" and (fdPersonInfo.fdStatus in (:fdStatus)");
            if (isNull) {
                whereBlock.append(" or fdPersonInfo.fdStatus is null");
            }

            whereBlock.append(")");
            hqlInfo.setParameter("fdStatus", fdStatus);
        }
        if (StringUtil.isNotNull(_fdKey)) {
			// 查询组织机构人员信息
        	whereBlock.append(" and  (fdStaffName like :fdKey or fdStaffNumber like :fdKey) ");
        	hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}
        hqlInfo.setWhereBlock(whereBlock.toString());
    }

    @Override
    public String getTempletName() {
        return "异动批量导入模板.xlsx";
    }

    @Override
    public ActionForward downloadTemplet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmssMessages messages = new KmssMessages();
        try {
            // 模板名称
            String templetName = getTempletName();
            // 构建模板文件
            String filePath = WordUtil.class.getResource("").getPath() + "/template/move.xlsx";
            FileInputStream fileIn = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fileIn);

            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName="
                    + HrStaffImportUtil.encodeFileName(request, templetName));
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            // 添加日志信息
            if (UserOperHelper.allowLogOper("downloadTemplet",
                    getServiceImp(request).getModelName())) {
                UserOperHelper.setEventType(ResourceUtil
                        .getString("hr-staff:hrStaff.import.button.download"));
                UserOperContentHelper.putFind("", templetName,
                        getServiceImp(request).getModelName());
            }
            return null;
        } catch (IOException e) {
            messages.addError(e);
        }

        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).save(
                    request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("success", mapping, form, request, response);
        }
    }

    /**
     * 上传文件
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public ActionForward fileUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-fileUpload", true, getClass());
        KmssMessage message;
        HrStaffMoveRecordForm baseForm = (HrStaffMoveRecordForm) form;
        FormFile file = baseForm.getFile();
        String resultMsg;
        boolean state = false;
        if (file == null || file.getFileSize() < 1) {
            resultMsg = ResourceUtil.getString("hrStaff.import.noFile", "hr-staff");
        } else {
            try {
                message = getServiceImp(request).saveImportData(baseForm);
                if (message.getMessageType() == KmssMessage.MESSAGE_COMMON) {
                    state = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = new KmssMessage(e.getMessage());
            }
            resultMsg = message.getMessageKey();
        }
        // 保存导入信息
        request.setAttribute("resultMsg", resultMsg);
        // 状态
        request.setAttribute("state", state);
        // 保存form请求路径
        request.setAttribute("uploadActionUrl", request.getParameter("uploadActionUrl"));
        //保存下载模版请求路径
        request.setAttribute("downLoadUrl", request.getParameter("downLoadUrl"));
        // 添加日志信息
        if (UserOperHelper.allowLogOper("fileUpload", getServiceImp(request).getModelName())) {
            UserOperHelper.setEventType(ResourceUtil.getString("hr-staff:hrStaff.import.button.submit"));
        }
        return getActionForward("hrStaffFileUpload", mapping, form, request, response);
    }

    /**
     * 获取所有异动信息
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getMoveAllData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-Budget", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            List<String[]> listNew = getServiceImp(request).findMoveAllData(request);
            request.setAttribute("queryPage", listNew);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("Action-Budget", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("allInfo", mapping, form, request, response);
        }
    }

    /**
     * 获取当月异动信息
     */
    public ActionForward getMoveMonthData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-getMonthMoveInfo", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            List<String[]> listNew = getServiceImp(request).findMoveMonthData(request);
            request.setAttribute("queryPage", listNew);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("Action-getMonthMoveInfo", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("monthInfo", mapping, form, request, response);
        }
    }

    /**
     * 导出所有异动信息
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportAllData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            List<String[]> list = getServiceImp(request).findMoveAllData(request);
            String value = "在职期间人员异动记录表";
            String filename = value + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            String[] title = {"序号", "工号", "姓名", "性别", "是否·最新状态", "异动日期", "一级部门", "二级部门", "三级部门", "岗位", "职级"};
            XSSFRow row1 = sheet.createRow(0);
            row1.setHeight((short) (100 * 6));
            for (int i = 0; i < title.length; i++) {
                sheet.setColumnWidth(i, 3000);
                Excel02Util.setCell(row1, titleStyle, i, title[i]);
            }

            for (int j = 0; j < list.size(); j++) {
                XSSFRow row = sheet.createRow(j + 1);
                row.setHeight((short) (100 * 6));
                String[] str = list.get(j);
                for (int k = 0; k < str.length; k++) {
                    Excel02Util.setCell(row, bodyStyle, k, str[k]);
                }
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
    }

    /**
     * 当月异动信息导出
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward exportMonthChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            List<String[]> list = getServiceImp(request).findMoveMonthData(request);
            String filename = "每月异动信息" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "每月异动信息";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(2);
            row.setHeight((short) (100 * 3));

            Excel02Util.insertMerge(sheet, titleStyle, "序号", 0, 0, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "工号", 1, 1, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "姓名", 2, 2, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "是否考察", 3, 3, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "见习开始日期", 4, 4, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "见习结束日期", 5, 5, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "是否跨一级部门异动", 6, 6, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "异动类型", 7, 7, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "变动生效日期", 8, 8, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "异动前", 9, 14, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "异动后", 15, 20, 1, 1);
            String[] names = {"一级部门", "二级部门", "三级部门", "职级", "岗位","直接上级"};
            int count = 9;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < names.length; j++) {
                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
                }
                count += names.length;
            }
            for (int j = 0; j < list.size(); j++) {
                XSSFRow row1 = sheet.createRow(j + 3);
                row.setHeight((short) (100 * 3));
                String[] str = list.get(j);
                for (int k = 0; k < str.length; k++) {
                    Excel02Util.setCell(row1, bodyStyle, k, str[k]);
                }
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    public ActionForward exportLeaveLevelMonthChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String year = request.getParameter("y");
        	String month = request.getParameter("m");
        	PersonLeaveLevelMonthReport service = new PersonLeaveLevelMonthReport();
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		list = service.leaveLevelMonthReport(year, month);
            String filename = "按职位类别统计离职率" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "按职位类别统计离职率";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(3);
            row.setHeight((short) (100 * 3));

            Excel02Util.insertMerge(sheet, titleStyle, "序号", 0, 0, 1, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "部门名称", 1, 1, 1, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "当月值", 2, 17, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "累计值", 18, 25, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "备注", 26, 26, 1, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "M类", 2, 5, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "S类", 6, 9, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "P类", 10, 13, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "O类", 14, 17, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "M类", 18, 19, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "S类", 20, 21, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "P类", 22, 23, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "O类", 24, 25, 2, 2);
          
//            Excel02Util.insertMerge(sheet, titleStyle, "原部门", 5, 9, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "现部门", 10, 14, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "异动情形", 15, 15, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "是否跨一级部门", 16, 16, 1, 2);
            	String[] names = {"月初总数", "月末总数", "流失人数", "流失率%"};
            	String[] names1 = {"累计离职人数", "累计流失率%"};
            int count = 2;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < names.length; j++) {
                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
                }
            	count += names.length;
        	}
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < names1.length; j++) {
                    Excel02Util.setCell(row, titleStyle, j + count, names1[j]);
                }
            	count += names1.length;
        	}
            for (int j = 0; j < list.size(); j++) {
                XSSFRow row1 = sheet.createRow(j + 4);
                row.setHeight((short) (100 * 3));
                Excel02Util.setCell(row1, bodyStyle, 0, 1+j);
                Excel02Util.setCell(row1, bodyStyle, 1, list.get(j).get("fd_dept_name"));
                Excel02Util.setCell(row1, bodyStyle, 2, list.get(j).get("fd_shangyue_work_m"));
                Excel02Util.setCell(row1, bodyStyle, 3, list.get(j).get("fd_benyue_work_m"));
                Excel02Util.setCell(row1, bodyStyle, 4, list.get(j).get("fd_benyue_leave_m"));
                Excel02Util.setCell(row1, bodyStyle, 5, ""+list.get(j).get("fd_benyue_liu_lv_m")+"%");
                Excel02Util.setCell(row1, bodyStyle, 6, list.get(j).get("fd_shangyue_work_s"));
                Excel02Util.setCell(row1, bodyStyle, 7, list.get(j).get("fd_benyue_work_s"));
                Excel02Util.setCell(row1, bodyStyle, 8, list.get(j).get("fd_benyue_leave_s"));
                Excel02Util.setCell(row1, bodyStyle, 9, ""+list.get(j).get("fd_benyue_liu_lv_s")+"%");
                Excel02Util.setCell(row1, bodyStyle,10, list.get(j).get("fd_shangyue_work_p"));
                Excel02Util.setCell(row1, bodyStyle, 11, list.get(j).get("fd_benyue_work_p"));
                Excel02Util.setCell(row1, bodyStyle, 12, list.get(j).get("fd_benyue_leave_p"));
                Excel02Util.setCell(row1, bodyStyle, 13, ""+list.get(j).get("fd_benyue_liu_lv_p")+"%");
                Excel02Util.setCell(row1, bodyStyle, 14, list.get(j).get("fd_shangyue_work_o"));
                Excel02Util.setCell(row1, bodyStyle, 15, list.get(j).get("fd_benyue_work_o"));
                Excel02Util.setCell(row1, bodyStyle, 16, list.get(j).get("fd_benyue_leave_o"));
                Excel02Util.setCell(row1, bodyStyle, 17, ""+list.get(j).get("fd_benyue_liu_lv_o")+"%");
                Excel02Util.setCell(row1, bodyStyle, 18, list.get(j).get("fd_all_year_leiji_m"));
                Excel02Util.setCell(row1, bodyStyle, 19, ""+list.get(j).get("fd_all_year_leiji_lv_m")+"%");
                Excel02Util.setCell(row1, bodyStyle, 20, list.get(j).get("fd_all_year_leiji_s"));
                Excel02Util.setCell(row1, bodyStyle, 21, ""+list.get(j).get("fd_all_year_leiji_lv_s")+"%");
                Excel02Util.setCell(row1, bodyStyle, 22, list.get(j).get("fd_all_year_leiji_p"));
                Excel02Util.setCell(row1, bodyStyle, 23, ""+list.get(j).get("fd_all_year_leiji_lv_p")+"%");
                Excel02Util.setCell(row1, bodyStyle, 24, list.get(j).get("fd_all_year_leiji_o"));
                Excel02Util.setCell(row1, bodyStyle, 25, ""+list.get(j).get("fd_all_year_leiji_lv_o")+"%");
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    public ActionForward exportPersonalStructureMonthReportChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String year = request.getParameter("y");
        	String month = request.getParameter("m");
        	PersonStructureMonthReport service = new PersonStructureMonthReport();
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		list = service.structureMonthReport(year, month);
            String filename = "人员结构分布表" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "人员结构分布表";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(3);
            row.setHeight((short) (100 * 3));

            Excel02Util.insertMerge(sheet, titleStyle, "序号", 0, 0, 1, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "部门名称", 1, 1, 1, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "人数小计", 2, 2, 1, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "婚姻状况", 3, 5, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "未婚", 3, 3, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "已婚", 4, 4, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "已育", 5, 5, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "性别", 6, 7, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "男性", 6, 6, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "女性", 7, 7, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "学历", 8, 12, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "大专以下", 8, 8, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "大专", 9, 9, 2, 3);
          
            Excel02Util.insertMerge(sheet, titleStyle, "本科	", 10, 10, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "硕士", 11, 11, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "博士", 12, 12, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "年龄结构", 13, 24, 1, 1);
          
            Excel02Util.insertMerge(sheet, titleStyle, "18-20岁", 13, 14, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "21-25岁", 15, 16, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "26-35岁", 17, 18, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "36-45岁", 19, 20, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "46-50岁", 21, 22, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "50岁以上", 23, 24, 2, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "司龄", 25, 30, 1, 1);
          
            Excel02Util.insertMerge(sheet, titleStyle, "1年以内", 25, 25, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "1-3年以下", 26, 26, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "3-5年以下", 27, 27, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "5-8年以下", 28, 28, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "8-10年以下", 29, 29, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "10年以上", 30, 30, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "职务类别（不含实习生临时工）", 31, 34, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "M类", 31, 31, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "P类", 32, 32, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "O类", 33, 33, 2, 3);
            Excel02Util.insertMerge(sheet, titleStyle, "S类", 34, 34, 2, 3);
            	String[] names = {"男", "女"};
//            	String[] names1 = {"累计离职人数", "累计流失率%"};
            int count = 13;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < names.length; j++) {
                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
                }
            	count += names.length;
        	}
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names1.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names1[j]);
//                }
//            	count += names1.length;
//        	}
            for (int j = 0; j < list.size(); j++) {
                XSSFRow row1 = sheet.createRow(j + 4);
                row.setHeight((short) (100 * 3));
                Excel02Util.setCell(row1, bodyStyle, 0, 1+j);
                Excel02Util.setCell(row1, bodyStyle, 1, list.get(j).get("fd_dept_name"));
                Excel02Util.setCell(row1, bodyStyle, 2, list.get(j).get("fd_dept_person_count"));
                Excel02Util.setCell(row1, bodyStyle, 3, list.get(j).get("fd_marital_status_weihun"));
                Excel02Util.setCell(row1, bodyStyle, 4, list.get(j).get("fd_marital_status_yihun"));
                Excel02Util.setCell(row1, bodyStyle, 5, list.get(j).get("fd_marital_status_yiyu"));
                Excel02Util.setCell(row1, bodyStyle, 6, list.get(j).get("fd_sex_f"));
                Excel02Util.setCell(row1, bodyStyle, 7, list.get(j).get("fd_sex_m"));
                Excel02Util.setCell(row1, bodyStyle, 8, list.get(j).get("fd_highest_education_dazhuanyixia"));
                Excel02Util.setCell(row1, bodyStyle, 9, list.get(j).get("fd_highest_education_dazhuan"));
                Excel02Util.setCell(row1, bodyStyle,10, list.get(j).get("fd_highest_education_benke"));
                Excel02Util.setCell(row1, bodyStyle, 11, list.get(j).get("fd_highest_education_shuoshi"));
                Excel02Util.setCell(row1, bodyStyle, 12, list.get(j).get("fd_highest_education_boshi"));
                Excel02Util.setCell(row1, bodyStyle, 13, list.get(j).get("fd_age_18_20_f"));
                Excel02Util.setCell(row1, bodyStyle, 14, list.get(j).get("fd_age_18_20_m"));
                Excel02Util.setCell(row1, bodyStyle, 15, list.get(j).get("fd_age_21_25_f"));
                Excel02Util.setCell(row1, bodyStyle, 16, list.get(j).get("fd_age_21_25_m"));
                Excel02Util.setCell(row1, bodyStyle, 17, list.get(j).get("fd_age_26_35_f"));
                Excel02Util.setCell(row1, bodyStyle, 18, list.get(j).get("fd_age_26_35_m"));
                Excel02Util.setCell(row1, bodyStyle, 19, list.get(j).get("fd_age_36_45_f"));
                Excel02Util.setCell(row1, bodyStyle, 20, list.get(j).get("fd_age_36_45_m"));
                Excel02Util.setCell(row1, bodyStyle, 21, list.get(j).get("fd_age_46_50_f"));
                Excel02Util.setCell(row1, bodyStyle, 22, list.get(j).get("fd_age_46_50_m"));
                Excel02Util.setCell(row1, bodyStyle, 23, list.get(j).get("fd_age_50_100_f"));
                Excel02Util.setCell(row1, bodyStyle, 24, list.get(j).get("fd_age_50_100_m"));
                Excel02Util.setCell(row1, bodyStyle, 25, list.get(j).get("fd_siling_0_1"));
                Excel02Util.setCell(row1, bodyStyle, 26, list.get(j).get("fd_siling_1_3"));
                Excel02Util.setCell(row1, bodyStyle, 27, list.get(j).get("fd_siling_3_5"));
                Excel02Util.setCell(row1, bodyStyle, 28, list.get(j).get("fd_siling_5_8"));
                Excel02Util.setCell(row1, bodyStyle, 29, list.get(j).get("fd_siling_8_10"));
                Excel02Util.setCell(row1, bodyStyle, 30, list.get(j).get("fd_siling_10"));
                Excel02Util.setCell(row1, bodyStyle, 31, list.get(j).get("fd_rank_m"));
                Excel02Util.setCell(row1, bodyStyle, 32, list.get(j).get("fd_rank_p"));
                Excel02Util.setCell(row1, bodyStyle, 33, list.get(j).get("fd_rank_o"));
                Excel02Util.setCell(row1, bodyStyle, 34, list.get(j).get("fd_rank_s"));
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    public ActionForward exportPersonalMonthChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String year = request.getParameter("y");
        	String month = request.getParameter("m");
        	PersonMonthReport service = new PersonMonthReport();
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		list = service.monthReport(year, month);
            String filename = "人事月报-编制人员进出" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "人事月报-编制人员进出";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(2);
            row.setHeight((short) (100 * 3));

            Excel02Util.insertMerge(sheet, titleStyle, "序号", 0, 0, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "部门名称", 1, 1, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "正编", 2, 11, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "派遣", 12, 21, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "实习生", 22, 31, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "小计", 32, 41, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "已育", 5, 5, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "性别", 6, 7, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "男性", 6, 6, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "女性", 7, 7, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "学历", 8, 12, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "大专以下", 8, 8, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "大专", 9, 9, 2, 3);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "本科	", 10, 10, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "硕士", 11, 11, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "博士", 12, 12, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "年龄结构", 13, 18, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "18-20岁", 13, 14, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "21-25岁", 15, 16, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "26-35岁", 17, 18, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "36-45岁", 19, 20, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "46-50岁", 21, 22, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "50岁以上", 23, 24, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "司龄", 25, 30, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "1年以内", 25, 25, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "1-3年以下", 26, 26, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "3-5年以下", 27, 27, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "5-8年以下", 28, 28, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "8-10年以下", 29, 29, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "10年以上", 30, 30, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "职务类别（不含实习生临时工）", 31, 34, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "M类", 31, 31, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "P类", 32, 32, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "O类", 33, 33, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "S类", 34, 34, 2, 3);
            	String[] names = {"月初总数", "月末总数","当月新进员工人数","当月离职员工人数","当月调入人数","当月调出人数","当月流动率%","当月离职率%","累计离职人数","累计离职率%"};
//            	String[] names1 = {"累计离职人数", "累计流失率%"};
            int count = 2;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < names.length; j++) {
                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
                }
            	count += names.length;
        	}
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names1.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names1[j]);
//                }
//            	count += names1.length;
//        	}
            for (int j = 0; j < list.size(); j++) {
                XSSFRow row1 = sheet.createRow(j + 3);
                row.setHeight((short) (100 * 3));
                Excel02Util.setCell(row1, bodyStyle, 0, 1+j);
                Excel02Util.setCell(row1, bodyStyle, 1, list.get(j).get("fd_dept_name"));
                Excel02Util.setCell(row1, bodyStyle, 2, list.get(j).get("fd_begin_month_count"));
                Excel02Util.setCell(row1, bodyStyle, 3, list.get(j).get("fd_end_month_count"));
                Excel02Util.setCell(row1, bodyStyle, 4, list.get(j).get("fd_new_month_count"));
                Excel02Util.setCell(row1, bodyStyle, 5, list.get(j).get("fd_leave_month_count"));
                Excel02Util.setCell(row1, bodyStyle, 6, list.get(j).get("fd_in_month_count"));
                Excel02Util.setCell(row1, bodyStyle, 7, list.get(j).get("fd_out_month_count"));
                Excel02Util.setCell(row1, bodyStyle, 8, ""+list.get(j).get("fd_flow_month_rate")+"%");
                Excel02Util.setCell(row1, bodyStyle, 9, ""+list.get(j).get("fd_leave_month_rate")+"%");
                Excel02Util.setCell(row1, bodyStyle,10, list.get(j).get("fd_leave_count"));
                Excel02Util.setCell(row1, bodyStyle, 11, ""+list.get(j).get("fd_leave_rate")+"%");
                Excel02Util.setCell(row1, bodyStyle, 12, list.get(j).get("fd_begin_month_count1"));
                Excel02Util.setCell(row1, bodyStyle, 13, list.get(j).get("fd_end_month_count1"));
                Excel02Util.setCell(row1, bodyStyle, 14, list.get(j).get("fd_new_month_count1"));
                Excel02Util.setCell(row1, bodyStyle, 15, list.get(j).get("fd_leave_month_count1"));
                Excel02Util.setCell(row1, bodyStyle, 16, list.get(j).get("fd_in_month_count1"));
                Excel02Util.setCell(row1, bodyStyle, 17, list.get(j).get("fd_out_month_count1"));
                Excel02Util.setCell(row1, bodyStyle, 18, ""+list.get(j).get("fd_flow_month_rate1")+"%");
                Excel02Util.setCell(row1, bodyStyle, 19, ""+list.get(j).get("fd_leave_month_rate1")+"%");
                Excel02Util.setCell(row1, bodyStyle, 20, list.get(j).get("fd_leave_count1"));
                Excel02Util.setCell(row1, bodyStyle, 21, ""+list.get(j).get("fd_leave_rate1")+"%");
                Excel02Util.setCell(row1, bodyStyle, 22, list.get(j).get("fd_begin_month_count2"));
                Excel02Util.setCell(row1, bodyStyle, 23, list.get(j).get("fd_end_month_count2"));
                Excel02Util.setCell(row1, bodyStyle, 24, list.get(j).get("fd_new_month_count2"));
                Excel02Util.setCell(row1, bodyStyle, 25, list.get(j).get("fd_leave_month_count2"));
                Excel02Util.setCell(row1, bodyStyle, 26, list.get(j).get("fd_in_month_count2"));
                Excel02Util.setCell(row1, bodyStyle, 27, list.get(j).get("fd_out_month_count2"));
                Excel02Util.setCell(row1, bodyStyle, 28, ""+list.get(j).get("fd_flow_month_rate2")+"%");
                Excel02Util.setCell(row1, bodyStyle, 29, ""+list.get(j).get("fd_leave_month_rate2")+"%");
                Excel02Util.setCell(row1, bodyStyle, 30, list.get(j).get("fd_leave_count2"));
                Excel02Util.setCell(row1, bodyStyle, 31, ""+list.get(j).get("fd_leave_rate2")+"%");
                Excel02Util.setCell(row1, bodyStyle, 32, list.get(j).get("fd_begin_month_count3"));
                Excel02Util.setCell(row1, bodyStyle, 33, list.get(j).get("fd_end_month_count3"));
                Excel02Util.setCell(row1, bodyStyle, 34, list.get(j).get("fd_new_month_count3"));
                Excel02Util.setCell(row1, bodyStyle, 35, list.get(j).get("fd_leave_month_count3"));
                Excel02Util.setCell(row1, bodyStyle, 36, list.get(j).get("fd_in_month_count3"));
                Excel02Util.setCell(row1, bodyStyle, 37, list.get(j).get("fd_out_month_count3"));
                Excel02Util.setCell(row1, bodyStyle, 38, ""+list.get(j).get("fd_flow_month_rate3")+"%");
                Excel02Util.setCell(row1, bodyStyle, 39, ""+list.get(j).get("fd_leave_month_rate3")+"%");
                Excel02Util.setCell(row1, bodyStyle, 40, list.get(j).get("fd_leave_count3"));
                Excel02Util.setCell(row1, bodyStyle, 41, ""+list.get(j).get("fd_leave_rate3")+"%");
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    public ActionForward exportLeaveMonthChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String year = request.getParameter("y");
        	String month = request.getParameter("m");
        	PersonLeaveMonthReport service = new PersonLeaveMonthReport();
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		list = service.leaveMonthReport(year, month);
            String filename = "人员入离职率汇总" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "人员入离职率汇总";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(2);
            row.setHeight((short) (100 * 3));

            Excel02Util.insertMerge(sheet, titleStyle, "类别", 0, 0, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "人数", 1, 2, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "差异", 3, 3, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "离职率", 4, 5, 1, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "年至今累计离职率", 6, 6, 1,2);
            Excel02Util.insertMerge(sheet, titleStyle, "年度目标值（离职率）", 7, 7, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "截止本月离职率目标", 8, 8, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "离职目标达成率", 9, 9, 1, 2);
            Excel02Util.setCell(row, titleStyle, 1, "本月");
            Excel02Util.setCell(row, titleStyle, 2, "上月");
            Excel02Util.setCell(row, titleStyle, 4, "本月");
            Excel02Util.setCell(row, titleStyle, 5, "上月");
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "本科	", 10, 10, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "硕士", 11, 11, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "博士", 12, 12, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "年龄结构", 13, 18, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "18-20岁", 13, 14, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "21-25岁", 15, 16, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "26-35岁", 17, 18, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "36-45岁", 19, 20, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "46-50岁", 21, 22, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "50岁以上", 23, 24, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "司龄", 25, 30, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "1年以内", 25, 25, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "1-3年以下", 26, 26, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "3-5年以下", 27, 27, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "5-8年以下", 28, 28, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "8-10年以下", 29, 29, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "10年以上", 30, 30, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "职务类别（不含实习生临时工）", 31, 34, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "M类", 31, 31, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "P类", 32, 32, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "O类", 33, 33, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "S类", 34, 34, 2, 3);
//            	String[] names = {"月初总数", "月末总数","当月新进员工人数","当月离职员工人数","当月调入人数","当月调出人数","当月流动率%","当月离职率%","累计离职人数","累计离职率%"};
//            	String[] names1 = {"累计离职人数", "累计流失率%"};
//            int count = 2;
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
//                }
//            	count += names.length;
//        	}
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names1.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names1[j]);
//                }
//            	count += names1.length;
//        	}
            for (int j = 0; j < list.size(); j++) {
                XSSFRow row1 = sheet.createRow(j + 3);
                row.setHeight((short) (100 * 3));
                Excel02Util.setCell(row1, bodyStyle, 0, list.get(j).get("fd_type"));
                Excel02Util.setCell(row1, bodyStyle, 1, list.get(j).get("benyue_31_work"));
                Excel02Util.setCell(row1, bodyStyle, 2, list.get(j).get("shangyue_31_work"));
                Excel02Util.setCell(row1, bodyStyle, 3, list.get(j).get("chayi_size"));
                Excel02Util.setCell(row1, bodyStyle, 4, ""+list.get(j).get("benyue_leave_lv")+"%");
                Excel02Util.setCell(row1, bodyStyle, 5, ""+list.get(j).get("shangyue_leave_lv")+"%");
                Excel02Util.setCell(row1, bodyStyle, 6, ""+list.get(j).get("leiji_leave_lv")+"%");
                Excel02Util.setCell(row1, bodyStyle, 7, ""+list.get(j).get("mubiao_year")+"%");
                Excel02Util.setCell(row1, bodyStyle, 8, ""+list.get(j).get("mubiao_lv")+"%");
                Excel02Util.setCell(row1, bodyStyle, 9, ""+list.get(j).get("mubiao_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 8, );
//                Excel02Util.setCell(row1, bodyStyle, 9, );
//                Excel02Util.setCell(row1, bodyStyle,10, list.get(j).get("fd_leave_count"));
//                Excel02Util.setCell(row1, bodyStyle, 11, ""+list.get(j).get("fd_leave_rate")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 12, list.get(j).get("fd_begin_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 13, list.get(j).get("fd_end_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 14, list.get(j).get("fd_new_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 15, list.get(j).get("fd_leave_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 16, list.get(j).get("fd_in_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 17, list.get(j).get("fd_out_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 18, ""+list.get(j).get("fd_flow_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 19, ""+list.get(j).get("fd_leave_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 20, list.get(j).get("fd_leave_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 21, ""+list.get(j).get("fd_leave_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 22, list.get(j).get("fd_begin_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 23, list.get(j).get("fd_end_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 24, list.get(j).get("fd_new_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 25, list.get(j).get("fd_leave_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 26, list.get(j).get("fd_in_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 27, list.get(j).get("fd_out_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 28, ""+list.get(j).get("fd_flow_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 29, ""+list.get(j).get("fd_leave_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 30, list.get(j).get("fd_leave_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 31, ""+list.get(j).get("fd_leave_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 32, list.get(j).get("fd_begin_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 33, list.get(j).get("fd_end_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 34, list.get(j).get("fd_new_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 35, list.get(j).get("fd_leave_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 36, list.get(j).get("fd_in_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 37, list.get(j).get("fd_out_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 38, ""+list.get(j).get("fd_flow_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 39, ""+list.get(j).get("fd_leave_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 40, list.get(j).get("fd_leave_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 41, ""+list.get(j).get("fd_leave_rate3")+"%");
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    public ActionForward exportLeaveMonth1Change(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String year = request.getParameter("y");
        	String month = request.getParameter("m");
        	AttendMonthReport service = new AttendMonthReport();
    		List<List<Map<String, Object>>> list = new ArrayList();
    		list = service.monthReport(year, month);
            String filename = "考勤月报-总表数据汇总" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "考勤月报-总表数据汇总";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(2);
            row.setHeight((short) (100 * 3));

            Excel02Util.insertMerge(sheet, titleStyle, "项目|部门", 0, 0, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "合计", 1, 3, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "本月实际值", 1, 1, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "上月实际值率", 2, 2, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "环比", 3, 3, 2,2);
//            Excel02Util.insertMerge(sheet, titleStyle, "年度目标值（离职率）", 7, 7, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "截止本月离职率目标", 8, 8, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "离职目标达成率", 9, 9, 1, 2);
            int o = 4;
    		if (list.size() > 0) {
    			for (int i = 1; i < list.get(0).size(); i++) {
    				Map<String, Object> map = list.get(0).get(i);
    	            Excel02Util.insertMerge(sheet, titleStyle, map.get("fd_dept_name").toString(), o, o+2, 1, 1);	
    	            Excel02Util.setCell(row, titleStyle, o, "本月实际值");
    	            Excel02Util.setCell(row, titleStyle, o+1, "上月实际值");
    	            Excel02Util.setCell(row, titleStyle, o+2, "环比");
    	            o+=3;
    			}
    		}
            Excel02Util.setCell(row, titleStyle, 1, "本月实际值");
            Excel02Util.setCell(row, titleStyle, 2, "上月实际值");
            Excel02Util.setCell(row, titleStyle, 3, "环比");
//            Excel02Util.setCell(row, titleStyle, 5, "上月");
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "本科	", 10, 10, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "硕士", 11, 11, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "博士", 12, 12, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "年龄结构", 13, 18, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "18-20岁", 13, 14, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "21-25岁", 15, 16, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "26-35岁", 17, 18, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "36-45岁", 19, 20, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "46-50岁", 21, 22, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "50岁以上", 23, 24, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "司龄", 25, 30, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "1年以内", 25, 25, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "1-3年以下", 26, 26, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "3-5年以下", 27, 27, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "5-8年以下", 28, 28, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "8-10年以下", 29, 29, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "10年以上", 30, 30, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "职务类别（不含实习生临时工）", 31, 34, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "M类", 31, 31, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "P类", 32, 32, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "O类", 33, 33, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "S类", 34, 34, 2, 3);
//            	String[] names = {"月初总数", "月末总数","当月新进员工人数","当月离职员工人数","当月调入人数","当月调出人数","当月流动率%","当月离职率%","累计离职人数","累计离职率%"};
//            	String[] names1 = {"累计离职人数", "累计流失率%"};
//            int count = 2;
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
//                }
//            	count += names.length;
//        	}
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names1.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names1[j]);
//                }
//            	count += names1.length;
//        	}

            XSSFRow row1 = sheet.createRow(3);
            Excel02Util.setCell(row1, bodyStyle, 0, month+"月加班时间/小时");
            int  nm = 1;
            for (int j = 0; j < list.get(0).size(); j++) {
//                row.setHeight((short) (100 * 3));
                Map<String, Object> map = list.get(0).get(j);
                Excel02Util.setCell(row1, bodyStyle, nm, map.get("hourTxt"));
                Excel02Util.setCell(row1, bodyStyle, nm+1, map.get("shangyueHourTxt"));
                Excel02Util.setCell(row1, bodyStyle, nm+2, map.get("huanbi"));
                nm+=3;
//                Excel02Util.setCell(row1, bodyStyle, 3, list.get(j).get("chayi_size"));
//                Excel02Util.setCell(row1, bodyStyle, 4, ""+list.get(j).get("benyue_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 5, ""+list.get(j).get("shangyue_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 6, ""+list.get(j).get("leiji_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 7, ""+list.get(j).get("mubiao_year")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 8, ""+list.get(j).get("mubiao_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 9, ""+list.get(j).get("mubiao_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 8, );
//                Excel02Util.setCell(row1, bodyStyle, 9, );
//                Excel02Util.setCell(row1, bodyStyle,10, list.get(j).get("fd_leave_count"));
//                Excel02Util.setCell(row1, bodyStyle, 11, ""+list.get(j).get("fd_leave_rate")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 12, list.get(j).get("fd_begin_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 13, list.get(j).get("fd_end_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 14, list.get(j).get("fd_new_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 15, list.get(j).get("fd_leave_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 16, list.get(j).get("fd_in_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 17, list.get(j).get("fd_out_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 18, ""+list.get(j).get("fd_flow_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 19, ""+list.get(j).get("fd_leave_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 20, list.get(j).get("fd_leave_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 21, ""+list.get(j).get("fd_leave_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 22, list.get(j).get("fd_begin_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 23, list.get(j).get("fd_end_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 24, list.get(j).get("fd_new_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 25, list.get(j).get("fd_leave_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 26, list.get(j).get("fd_in_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 27, list.get(j).get("fd_out_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 28, ""+list.get(j).get("fd_flow_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 29, ""+list.get(j).get("fd_leave_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 30, list.get(j).get("fd_leave_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 31, ""+list.get(j).get("fd_leave_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 32, list.get(j).get("fd_begin_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 33, list.get(j).get("fd_end_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 34, list.get(j).get("fd_new_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 35, list.get(j).get("fd_leave_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 36, list.get(j).get("fd_in_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 37, list.get(j).get("fd_out_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 38, ""+list.get(j).get("fd_flow_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 39, ""+list.get(j).get("fd_leave_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 40, list.get(j).get("fd_leave_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 41, ""+list.get(j).get("fd_leave_rate3")+"%");
            }
            
            int p = 1;
            XSSFRow row2 = sheet.createRow(4);
            Excel02Util.setCell(row2, bodyStyle, 0, month+"人均加班时间/小时");
            for (int j = 0; j < list.get(1).size(); j++) {
//                row.setHeight((short) (100 * 3));
                Map<String, Object> map = list.get(1).get(j);
                Excel02Util.setCell(row2, bodyStyle, p, map.get("hourTxt"));
                Excel02Util.setCell(row2, bodyStyle, p+1, map.get("shangyueHourTxt"));
                Excel02Util.setCell(row2, bodyStyle, p+2, map.get("huanbi"));
                p+=3;
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    public ActionForward exportLeaveMonth2Change(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String year = request.getParameter("y");
        	String month = request.getParameter("m");
        	AttendMonthDeptReport service = new AttendMonthDeptReport();
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		list = service.monthReport(year, month);
            String filename = "考勤月报-各部门总加班" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "考勤月报-各部门总加班";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(2);
            row.setHeight((short) (100 * 3));

            Excel02Util.insertMerge(sheet, titleStyle, "部门", 0, 0, 1, 2);
            int nm = 1;
            for(int i=1;i<=Integer.parseInt(month);i++){
            	Excel02Util.insertMerge(sheet, titleStyle, i+"月", nm, nm+2, 1, 1);
                Excel02Util.setCell(row, titleStyle, nm, "总加班小时");
                Excel02Util.setCell(row, titleStyle, nm+1, "加班人数");
                Excel02Util.setCell(row, titleStyle, nm+2, "人均加班时间	");
            	nm+=3;
            }
            if (list.size() > 0) {
        		for (int i = 0; i < list.size(); i++) {
        			XSSFRow row5 = sheet.createRow(i+3);
        			Map<String, Object> map = list.get(i);
        			Excel02Util.setCell(row5, bodyStyle, 0, map.get("fd_dept_name"));
                    int p = 1;
        			for (int j = 1; j <= Integer.parseInt(month); j++) {
	        			Excel02Util.setCell(row5, bodyStyle, p, map.get("hourTxt"+j));
	        			Excel02Util.setCell(row5, bodyStyle, p+1, map.get("personCount"+j));
	        			Excel02Util.setCell(row5, bodyStyle, p+2, map.get("avg"+j));
	        			p+=3;
        			}
        		}
            }
//            Excel02Util.insertMerge(sheet, titleStyle, "本月实际值", 1, 1, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "上月实际值率", 2, 2, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "环比", 3, 3, 2,2);
//            Excel02Util.insertMerge(sheet, titleStyle, "年度目标值（离职率）", 7, 7, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "截止本月离职率目标", 8, 8, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "离职目标达成率", 9, 9, 1, 2);
//            int o = 4;
//    		if (list.size() > 0) {
//    			for (int i = 1; i < list.get(0).size(); i++) {
//    				Map<String, Object> map = list.get(0).get(i);
//    	            Excel02Util.insertMerge(sheet, titleStyle, map.get("fd_dept_name").toString(), o, o+2, 1, 1);	
//    	            Excel02Util.setCell(row, titleStyle, o, "本月实际值");
//    	            Excel02Util.setCell(row, titleStyle, o+1, "上月实际值");
//    	            Excel02Util.setCell(row, titleStyle, o+2, "环比");
//    	            o+=3;
//    			}
//    		}
//            Excel02Util.setCell(row, titleStyle, 1, "本月实际值");
//            Excel02Util.setCell(row, titleStyle, 2, "上月实际值");
//            Excel02Util.setCell(row, titleStyle, 3, "环比");
//            Excel02Util.setCell(row, titleStyle, 5, "上月");
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "本科	", 10, 10, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "硕士", 11, 11, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "博士", 12, 12, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "年龄结构", 13, 18, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "18-20岁", 13, 14, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "21-25岁", 15, 16, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "26-35岁", 17, 18, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "36-45岁", 19, 20, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "46-50岁", 21, 22, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "50岁以上", 23, 24, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "司龄", 25, 30, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "1年以内", 25, 25, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "1-3年以下", 26, 26, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "3-5年以下", 27, 27, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "5-8年以下", 28, 28, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "8-10年以下", 29, 29, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "10年以上", 30, 30, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "职务类别（不含实习生临时工）", 31, 34, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "M类", 31, 31, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "P类", 32, 32, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "O类", 33, 33, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "S类", 34, 34, 2, 3);
//            	String[] names = {"月初总数", "月末总数","当月新进员工人数","当月离职员工人数","当月调入人数","当月调出人数","当月流动率%","当月离职率%","累计离职人数","累计离职率%"};
//            	String[] names1 = {"累计离职人数", "累计流失率%"};
//            int count = 2;
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
//                }
//            	count += names.length;
//        	}
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names1.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names1[j]);
//                }
//            	count += names1.length;
//        	}

//            XSSFRow row1 = sheet.createRow(3);
//            Excel02Util.setCell(row1, bodyStyle, 0, month+"月加班时间/小时");
//            int  nm = 1;
//            for (int j = 0; j < list.get(0).size(); j++) {
////                row.setHeight((short) (100 * 3));
//                Map<String, Object> map = list.get(0).get(j);
//                Excel02Util.setCell(row1, bodyStyle, nm, map.get("hourTxt"));
//                Excel02Util.setCell(row1, bodyStyle, nm+1, map.get("shangyueHourTxt"));
//                Excel02Util.setCell(row1, bodyStyle, nm+2, map.get("huanbi"));
//                nm+=3;
//                Excel02Util.setCell(row1, bodyStyle, 3, list.get(j).get("chayi_size"));
//                Excel02Util.setCell(row1, bodyStyle, 4, ""+list.get(j).get("benyue_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 5, ""+list.get(j).get("shangyue_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 6, ""+list.get(j).get("leiji_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 7, ""+list.get(j).get("mubiao_year")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 8, ""+list.get(j).get("mubiao_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 9, ""+list.get(j).get("mubiao_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 8, );
//                Excel02Util.setCell(row1, bodyStyle, 9, );
//                Excel02Util.setCell(row1, bodyStyle,10, list.get(j).get("fd_leave_count"));
//                Excel02Util.setCell(row1, bodyStyle, 11, ""+list.get(j).get("fd_leave_rate")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 12, list.get(j).get("fd_begin_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 13, list.get(j).get("fd_end_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 14, list.get(j).get("fd_new_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 15, list.get(j).get("fd_leave_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 16, list.get(j).get("fd_in_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 17, list.get(j).get("fd_out_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 18, ""+list.get(j).get("fd_flow_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 19, ""+list.get(j).get("fd_leave_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 20, list.get(j).get("fd_leave_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 21, ""+list.get(j).get("fd_leave_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 22, list.get(j).get("fd_begin_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 23, list.get(j).get("fd_end_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 24, list.get(j).get("fd_new_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 25, list.get(j).get("fd_leave_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 26, list.get(j).get("fd_in_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 27, list.get(j).get("fd_out_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 28, ""+list.get(j).get("fd_flow_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 29, ""+list.get(j).get("fd_leave_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 30, list.get(j).get("fd_leave_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 31, ""+list.get(j).get("fd_leave_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 32, list.get(j).get("fd_begin_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 33, list.get(j).get("fd_end_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 34, list.get(j).get("fd_new_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 35, list.get(j).get("fd_leave_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 36, list.get(j).get("fd_in_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 37, list.get(j).get("fd_out_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 38, ""+list.get(j).get("fd_flow_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 39, ""+list.get(j).get("fd_leave_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 40, list.get(j).get("fd_leave_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 41, ""+list.get(j).get("fd_leave_rate3")+"%");
//            }
            
//            int p = 1;
//            XSSFRow row2 = sheet.createRow(4);
//            Excel02Util.setCell(row2, bodyStyle, 0, month+"人均加班时间/小时");
//            for (int j = 0; j < list.get(1).size(); j++) {
////                row.setHeight((short) (100 * 3));
//                Map<String, Object> map = list.get(1).get(j);
//                Excel02Util.setCell(row2, bodyStyle, p, map.get("hourTxt"));
//                Excel02Util.setCell(row2, bodyStyle, p+1, map.get("shangyueHourTxt"));
//                Excel02Util.setCell(row2, bodyStyle, p+2, map.get("huanbi"));
//                p+=3;
//            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    public ActionForward exportLeaveMonth3Change(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String year = request.getParameter("y");
        	String month = request.getParameter("m");
        	AttendMonthDeptOver40Report service = new AttendMonthDeptOver40Report();
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		list = service.monthReport(year, month);
            String filename = "考勤月报-加班超40小时" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "考勤月报-加班超40小时";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
//            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(1);
            row.setHeight((short) (100 * 3));
            Excel02Util.setCell(row, titleStyle, 0, "一级部门");
            Excel02Util.setCell(row, titleStyle, 1, "总人数");
            Excel02Util.setCell(row, titleStyle, 2, "加班人数");
            Excel02Util.setCell(row, titleStyle, 3, "超过40小时人数");
            Excel02Util.setCell(row, titleStyle, 4, "超过40小时占加班人数比");
            Excel02Util.setCell(row, titleStyle, 5, "法定加班(h)");
            Excel02Util.setCell(row, titleStyle, 6, "周末加班(h)");
            Excel02Util.setCell(row, titleStyle, 7, "工作日加班(h)");
            Excel02Util.setCell(row, titleStyle, 8, ">40小时汇总");
            Excel02Util.setCell(row, titleStyle, 9, month+"月总加班小时");
//            Excel02Util.insertMerge(sheet, titleStyle, "部门", 0, 0, 1, 2);
//            int nm = 1;
//            for(int i=1;i<=Integer.parseInt(month);i++){
//            	Excel02Util.insertMerge(sheet, titleStyle, i+"月", nm, nm+2, 1, 1);
//                Excel02Util.setCell(row, titleStyle, nm, "总加班小时");
//                Excel02Util.setCell(row, titleStyle, nm+1, "加班人数");
//                Excel02Util.setCell(row, titleStyle, nm+2, "人均加班时间	");
//            	nm+=3;
//            }
            if (list.size() > 0) {
        		for (int i = 0; i < list.size(); i++) {
        			XSSFRow row5 = sheet.createRow(i+2);
        			Map<String, Object> map = list.get(i);
        			Excel02Util.setCell(row5, bodyStyle, 0, map.get("fd_dept_name"));
        			Excel02Util.setCell(row5, bodyStyle, 1, map.get("personCount"));
        			Excel02Util.setCell(row5, bodyStyle, 2, map.get("jiabanCount"));
        			Excel02Util.setCell(row5, bodyStyle, 3, map.get("over40"));
        			Excel02Util.setCell(row5, bodyStyle, 4, map.get("jiabanlv"));
        			Excel02Util.setCell(row5, bodyStyle, 5, map.get("holiday"));
        			Excel02Util.setCell(row5, bodyStyle, 6, map.get("off"));
        			Excel02Util.setCell(row5, bodyStyle, 7, map.get("work"));
        			Excel02Util.setCell(row5, bodyStyle, 8, map.get("countover40"));
        			Excel02Util.setCell(row5, bodyStyle, 9, map.get("monthcount"));
//                    int p = 1;
//        			for (int j = 1; j <= Integer.parseInt(month); j++) {
//	        			Excel02Util.setCell(row5, bodyStyle, p, map.get("hourTxt"+j));
//	        			Excel02Util.setCell(row5, bodyStyle, p+1, map.get("personCount"+j));
//	        			Excel02Util.setCell(row5, bodyStyle, p+2, map.get("avg"+j));
//	        			p+=3;
//        			}
        		}
            }
//            Excel02Util.insertMerge(sheet, titleStyle, "本月实际值", 1, 1, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "上月实际值率", 2, 2, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "环比", 3, 3, 2,2);
//            Excel02Util.insertMerge(sheet, titleStyle, "年度目标值（离职率）", 7, 7, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "截止本月离职率目标", 8, 8, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "离职目标达成率", 9, 9, 1, 2);
//            int o = 4;
//    		if (list.size() > 0) {
//    			for (int i = 1; i < list.get(0).size(); i++) {
//    				Map<String, Object> map = list.get(0).get(i);
//    	            Excel02Util.insertMerge(sheet, titleStyle, map.get("fd_dept_name").toString(), o, o+2, 1, 1);	
//    	            Excel02Util.setCell(row, titleStyle, o, "本月实际值");
//    	            Excel02Util.setCell(row, titleStyle, o+1, "上月实际值");
//    	            Excel02Util.setCell(row, titleStyle, o+2, "环比");
//    	            o+=3;
//    			}
//    		}
//            Excel02Util.setCell(row, titleStyle, 1, "本月实际值");
//            Excel02Util.setCell(row, titleStyle, 2, "上月实际值");
//            Excel02Util.setCell(row, titleStyle, 3, "环比");
//            Excel02Util.setCell(row, titleStyle, 5, "上月");
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "本科	", 10, 10, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "硕士", 11, 11, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "博士", 12, 12, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "年龄结构", 13, 18, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "18-20岁", 13, 14, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "21-25岁", 15, 16, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "26-35岁", 17, 18, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "36-45岁", 19, 20, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "46-50岁", 21, 22, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "50岁以上", 23, 24, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "司龄", 25, 30, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "1年以内", 25, 25, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "1-3年以下", 26, 26, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "3-5年以下", 27, 27, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "5-8年以下", 28, 28, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "8-10年以下", 29, 29, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "10年以上", 30, 30, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "职务类别（不含实习生临时工）", 31, 34, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "M类", 31, 31, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "P类", 32, 32, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "O类", 33, 33, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "S类", 34, 34, 2, 3);
//            	String[] names = {"月初总数", "月末总数","当月新进员工人数","当月离职员工人数","当月调入人数","当月调出人数","当月流动率%","当月离职率%","累计离职人数","累计离职率%"};
//            	String[] names1 = {"累计离职人数", "累计流失率%"};
//            int count = 2;
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
//                }
//            	count += names.length;
//        	}
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names1.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names1[j]);
//                }
//            	count += names1.length;
//        	}

//            XSSFRow row1 = sheet.createRow(3);
//            Excel02Util.setCell(row1, bodyStyle, 0, month+"月加班时间/小时");
//            int  nm = 1;
//            for (int j = 0; j < list.get(0).size(); j++) {
////                row.setHeight((short) (100 * 3));
//                Map<String, Object> map = list.get(0).get(j);
//                Excel02Util.setCell(row1, bodyStyle, nm, map.get("hourTxt"));
//                Excel02Util.setCell(row1, bodyStyle, nm+1, map.get("shangyueHourTxt"));
//                Excel02Util.setCell(row1, bodyStyle, nm+2, map.get("huanbi"));
//                nm+=3;
//                Excel02Util.setCell(row1, bodyStyle, 3, list.get(j).get("chayi_size"));
//                Excel02Util.setCell(row1, bodyStyle, 4, ""+list.get(j).get("benyue_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 5, ""+list.get(j).get("shangyue_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 6, ""+list.get(j).get("leiji_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 7, ""+list.get(j).get("mubiao_year")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 8, ""+list.get(j).get("mubiao_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 9, ""+list.get(j).get("mubiao_leave_lv")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 8, );
//                Excel02Util.setCell(row1, bodyStyle, 9, );
//                Excel02Util.setCell(row1, bodyStyle,10, list.get(j).get("fd_leave_count"));
//                Excel02Util.setCell(row1, bodyStyle, 11, ""+list.get(j).get("fd_leave_rate")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 12, list.get(j).get("fd_begin_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 13, list.get(j).get("fd_end_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 14, list.get(j).get("fd_new_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 15, list.get(j).get("fd_leave_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 16, list.get(j).get("fd_in_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 17, list.get(j).get("fd_out_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 18, ""+list.get(j).get("fd_flow_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 19, ""+list.get(j).get("fd_leave_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 20, list.get(j).get("fd_leave_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 21, ""+list.get(j).get("fd_leave_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 22, list.get(j).get("fd_begin_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 23, list.get(j).get("fd_end_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 24, list.get(j).get("fd_new_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 25, list.get(j).get("fd_leave_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 26, list.get(j).get("fd_in_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 27, list.get(j).get("fd_out_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 28, ""+list.get(j).get("fd_flow_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 29, ""+list.get(j).get("fd_leave_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 30, list.get(j).get("fd_leave_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 31, ""+list.get(j).get("fd_leave_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 32, list.get(j).get("fd_begin_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 33, list.get(j).get("fd_end_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 34, list.get(j).get("fd_new_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 35, list.get(j).get("fd_leave_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 36, list.get(j).get("fd_in_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 37, list.get(j).get("fd_out_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 38, ""+list.get(j).get("fd_flow_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 39, ""+list.get(j).get("fd_leave_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 40, list.get(j).get("fd_leave_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 41, ""+list.get(j).get("fd_leave_rate3")+"%");
//            }
            
//            int p = 1;
//            XSSFRow row2 = sheet.createRow(4);
//            Excel02Util.setCell(row2, bodyStyle, 0, month+"人均加班时间/小时");
//            for (int j = 0; j < list.get(1).size(); j++) {
////                row.setHeight((short) (100 * 3));
//                Map<String, Object> map = list.get(1).get(j);
//                Excel02Util.setCell(row2, bodyStyle, p, map.get("hourTxt"));
//                Excel02Util.setCell(row2, bodyStyle, p+1, map.get("shangyueHourTxt"));
//                Excel02Util.setCell(row2, bodyStyle, p+2, map.get("huanbi"));
//                p+=3;
//            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    public ActionForward exportPersonalIncomeTaxChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String year = request.getParameter("y");
        	String month = request.getParameter("m");
        	PersonInfoReport service = new PersonInfoReport();
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
    		ca.setTime(new Date()); // 设置时间为当前时间
    		String y = "";

    		if (request.getParameter("y") == null) {
    			y = ca.get(Calendar.YEAR) + "";
    		} else {
    			y = request.getParameter("y");
    		}

    		String m = "";
    		if (request.getParameter("m") == null) {
    			m = (ca.get(Calendar.MONTH) + 1) + "";
    		} else {
    			m = request.getParameter("m");
    		}
    		String company = request.getParameter("c");

    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    		ca.set(Calendar.YEAR, Integer.valueOf(y));
    		ca.set(Calendar.MONTH, Integer.valueOf(m) - 1);
    		ca.set(Calendar.DAY_OF_MONTH, 25);
    		String benyue_25 = sdf.format(ca.getTime());

    		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
    		ca.set(Calendar.DAY_OF_MONTH, 26);
    		String shangyue_26 = sdf.format(ca.getTime());
    		service.incomeTaxGetCompany(company);
    		list = service.incomeTaxReport(shangyue_26, benyue_25,company);
            String filename = "个税人员信息采集表" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "个税人员信息采集表";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(1);
            row.setHeight((short) (100 * 3));
//
//            Excel02Util.insertMerge(sheet, titleStyle, "类别", 0, 0, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "人数", 1, 2, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "差异", 3, 3, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "离职率", 4, 5, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "年至今累计离职率", 6, 6, 1,2);
//            Excel02Util.insertMerge(sheet, titleStyle, "年度目标值（离职率）", 7, 7, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "截止本月离职率目标", 8, 8, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "离职目标达成率", 9, 9, 1, 2);
            Excel02Util.setCell(row, titleStyle, 0, "序号");
            Excel02Util.setCell(row, titleStyle, 1, "工号");
            Excel02Util.setCell(row, titleStyle, 2, "*姓名");
            Excel02Util.setCell(row, titleStyle, 3, "*证件类型");
            Excel02Util.setCell(row, titleStyle, 4, "*证件号码");
            Excel02Util.setCell(row, titleStyle, 5, "*国籍(地区)");
            Excel02Util.setCell(row, titleStyle, 6, "*性别");
            Excel02Util.setCell(row, titleStyle, 7, "*出生日期");
            Excel02Util.setCell(row, titleStyle, 8, "*人员状态");
            Excel02Util.setCell(row, titleStyle, 9, "*任职受雇从业类型");
            Excel02Util.setCell(row, titleStyle, 10, "入职年度就业情形");
            Excel02Util.setCell(row, titleStyle, 11, "手机号码");
            Excel02Util.setCell(row, titleStyle, 12, "任职受雇从业日期");
            Excel02Util.setCell(row, titleStyle, 13, "离职日期");
            Excel02Util.setCell(row, titleStyle, 14, "所属公司");
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "本科	", 10, 10, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "硕士", 11, 11, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "博士", 12, 12, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "年龄结构", 13, 18, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "18-20岁", 13, 14, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "21-25岁", 15, 16, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "26-35岁", 17, 18, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "36-45岁", 19, 20, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "46-50岁", 21, 22, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "50岁以上", 23, 24, 2, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "司龄", 25, 30, 1, 1);
//          
//            Excel02Util.insertMerge(sheet, titleStyle, "1年以内", 25, 25, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "1-3年以下", 26, 26, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "3-5年以下", 27, 27, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "5-8年以下", 28, 28, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "8-10年以下", 29, 29, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "10年以上", 30, 30, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "职务类别（不含实习生临时工）", 31, 34, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "M类", 31, 31, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "P类", 32, 32, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "O类", 33, 33, 2, 3);
//            Excel02Util.insertMerge(sheet, titleStyle, "S类", 34, 34, 2, 3);
//            	String[] names = {"月初总数", "月末总数","当月新进员工人数","当月离职员工人数","当月调入人数","当月调出人数","当月流动率%","当月离职率%","累计离职人数","累计离职率%"};
//            	String[] names1 = {"累计离职人数", "累计流失率%"};
//            int count = 2;
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
//                }
//            	count += names.length;
//        	}
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < names1.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names1[j]);
//                }
//            	count += names1.length;
//        	}
            for (int j = 0; j < list.size(); j++) {
                XSSFRow row1 = sheet.createRow(j + 2);
                row.setHeight((short) (100 * 3));
                Excel02Util.setCell(row1, bodyStyle, 0, j + 1);
                Excel02Util.setCell(row1, bodyStyle, 1, list.get(j).get("fd_staff_no"));
                Excel02Util.setCell(row1, bodyStyle, 2, list.get(j).get("fd_name"));
                Excel02Util.setCell(row1, bodyStyle, 3, "居民身份证");
                Excel02Util.setCell(row1, bodyStyle, 4, list.get(j).get("fd_id_card"));
                Excel02Util.setCell(row1, bodyStyle, 5, "中国");
                Excel02Util.setCell(row1, bodyStyle, 6, list.get(j).get("fd_sex").equals("F") ? '男' : '女');
                Excel02Util.setCell(row1, bodyStyle, 7, service.dateToString(list.get(j).get("fd_date_of_birth")));
                Excel02Util.setCell(row1, bodyStyle, 8, "正常");
                Excel02Util.setCell(row1, bodyStyle, 9, "雇员");
                Excel02Util.setCell(row1, bodyStyle, 10, "");
                Excel02Util.setCell(row1, bodyStyle, 11, list.get(j).get("fd_mobile_no"));
                Excel02Util.setCell(row1, bodyStyle, 12, "");
                Excel02Util.setCell(row1, bodyStyle, 13, list.get(j).get("fd_resignation_date"));
                Excel02Util.setCell(row1, bodyStyle, 14, list.get(j).get("fd_affiliated_company"));
//                Excel02Util.setCell(row1, bodyStyle, 8, );
//                Excel02Util.setCell(row1, bodyStyle, 9, );
//                Excel02Util.setCell(row1, bodyStyle,10, list.get(j).get("fd_leave_count"));
//                Excel02Util.setCell(row1, bodyStyle, 11, ""+list.get(j).get("fd_leave_rate")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 12, list.get(j).get("fd_begin_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 13, list.get(j).get("fd_end_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 14, list.get(j).get("fd_new_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 15, list.get(j).get("fd_leave_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 16, list.get(j).get("fd_in_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 17, list.get(j).get("fd_out_month_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 18, ""+list.get(j).get("fd_flow_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 19, ""+list.get(j).get("fd_leave_month_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 20, list.get(j).get("fd_leave_count1"));
//                Excel02Util.setCell(row1, bodyStyle, 21, ""+list.get(j).get("fd_leave_rate1")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 22, list.get(j).get("fd_begin_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 23, list.get(j).get("fd_end_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 24, list.get(j).get("fd_new_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 25, list.get(j).get("fd_leave_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 26, list.get(j).get("fd_in_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 27, list.get(j).get("fd_out_month_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 28, ""+list.get(j).get("fd_flow_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 29, ""+list.get(j).get("fd_leave_month_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 30, list.get(j).get("fd_leave_count2"));
//                Excel02Util.setCell(row1, bodyStyle, 31, ""+list.get(j).get("fd_leave_rate2")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 32, list.get(j).get("fd_begin_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 33, list.get(j).get("fd_end_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 34, list.get(j).get("fd_new_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 35, list.get(j).get("fd_leave_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 36, list.get(j).get("fd_in_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 37, list.get(j).get("fd_out_month_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 38, ""+list.get(j).get("fd_flow_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 39, ""+list.get(j).get("fd_leave_month_rate3")+"%");
//                Excel02Util.setCell(row1, bodyStyle, 40, list.get(j).get("fd_leave_count3"));
//                Excel02Util.setCell(row1, bodyStyle, 41, ""+list.get(j).get("fd_leave_rate3")+"%");
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    public ActionForward exportEntryMonthChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String year = request.getParameter("y");
        	String month = request.getParameter("m");
        	PersonInfoReport service = new PersonInfoReport();
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		list = service.entryMonthReport(year, month);
            String filename = "当月新员工入职统计" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 16; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            sheet.setColumnWidth(16, 5000);
            sheet.createRow(0).setHeight((short) (100 * 3));
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            String title = "当月新员工入职统计";
            Excel02Util.insertMerge(sheet, titleStyle, title, 0, 16, 0, 0);
            sheet.createRow(1).setHeight((short) (100 * 3));
            XSSFRow row = sheet.createRow(2);
            row.setHeight((short) (100 * 3));

            Excel02Util.insertMerge(sheet, titleStyle, "序号", 0, 0, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "一级部门", 1, 1, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "二级部门", 2, 2, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "三级部门", 3, 3, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "人员类别", 4, 4, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "所属公司", 5, 5, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "人员编号", 6, 6, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "姓名", 7, 7, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "岗位名称", 8, 8, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "职务", 9, 9, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "职类", 10, 10, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "职级", 11, 11, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "入职日期", 12, 12, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "拟转正日期", 13, 13, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "转正日期", 14, 14, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "本企业司龄", 15, 15, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "最高学历", 16, 16, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "合同开始日期", 17, 17, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "合同结束日期", 18, 18, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "合同期限", 19, 19, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "身份证号", 20, 20, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "出生日期", 21, 21, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "年龄", 22, 22, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "性别", 23, 23, 1, 2);
            Excel02Util.insertMerge(sheet, titleStyle, "婚姻状况", 24, 24, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "原部门", 5, 9, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "现部门", 10, 14, 1, 1);
//            Excel02Util.insertMerge(sheet, titleStyle, "异动情形", 15, 15, 1, 2);
//            Excel02Util.insertMerge(sheet, titleStyle, "是否跨一级部门", 16, 16, 1, 2);
//            String[] names = {"一级部门", "二级部门", "三级部门", "职级", "职位"};
            int count = 5;
//            for (int i = 0; i < 2; i++) {
//                for (int j = 0; j < names.length; j++) {
//                    Excel02Util.setCell(row, titleStyle, j + count, names[j]);
//                }
//                count += names.length;
//            }
            for (int j = 0; j < list.size(); j++) {
                XSSFRow row1 = sheet.createRow(j + 3);
                row.setHeight((short) (100 * 3));
                Excel02Util.setCell(row1, bodyStyle, 0, 1+j);
                Excel02Util.setCell(row1, bodyStyle, 1, list.get(j).get("fd_first_level_department"));
                Excel02Util.setCell(row1, bodyStyle, 2, list.get(j).get("fd_second_level_department"));
                Excel02Util.setCell(row1, bodyStyle, 3, list.get(j).get("fd_third_level_department"));
                Excel02Util.setCell(row1, bodyStyle, 4, list.get(j).get("fd_staff_type"));
                Excel02Util.setCell(row1, bodyStyle, 5, list.get(j).get("fd_affiliated_company"));
                Excel02Util.setCell(row1, bodyStyle, 6, list.get(j).get("fd_staff_no"));
                Excel02Util.setCell(row1, bodyStyle, 7, list.get(j).get("fd_name"));
                Excel02Util.setCell(row1, bodyStyle, 8, list.get(j).get("fd_post_name"));
                Excel02Util.setCell(row1, bodyStyle, 9, list.get(j).get("fd_staffinglevel_name"));
                Excel02Util.setCell(row1, bodyStyle,10, list.get(j).get("fd_category"));
                Excel02Util.setCell(row1, bodyStyle, 11, list.get(j).get("fd_rank_name"));
                Excel02Util.setCell(row1, bodyStyle, 12, service.dateToString(list.get(j).get("fd_entry_time")));
                Excel02Util.setCell(row1, bodyStyle, 13, service.dateToString(list.get(j).get("fd_proposed_employment_confirmation_date")));
                Excel02Util.setCell(row1, bodyStyle, 14, service.dateToString(list.get(j).get("fd_positive_time")));
                Excel02Util.setCell(row1, bodyStyle, 15, list.get(j).get("fd_work_in_this_company"));
                Excel02Util.setCell(row1, bodyStyle, 16, list.get(j).get("fd_highest_education"));
                Excel02Util.setCell(row1, bodyStyle, 17, service.dateToString(list.get(j).get("cont_fd_begin_date")));
                Excel02Util.setCell(row1, bodyStyle, 18, service.dateToString(list.get(j).get("cont_fd_end_date")));
                Excel02Util.setCell(row1, bodyStyle, 19, ""+(list.get(j).get("fd_contract_year") != null ? list.get(j).get("fd_contract_year") : "0")+'年'+(list.get(j).get("fd_contract_month") != null ? list.get(j).get("fd_contract_month") : "0")+'月');
                Excel02Util.setCell(row1, bodyStyle, 20, list.get(j).get("fd_id_card"));
                Excel02Util.setCell(row1, bodyStyle, 21, service.dateToString(list.get(j).get("fd_date_of_birth")));
                Excel02Util.setCell(row1, bodyStyle, 22, list.get(j).get("fd_age"));
                Excel02Util.setCell(row1, bodyStyle, 23, list.get(j).get("fd_sex").equals("F") ? '男' : '女');
                Excel02Util.setCell(row1, bodyStyle, 24, list.get(j).get("fd_marital_status"));
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
        return null;
    }
    /**
     * 获取招聘信息
     */
    public ActionForward getRecruitData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-getRecruithData", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            List<String[]> listNew = getServiceImp(request).findRecruitData(request);
            request.setAttribute("queryPage", listNew);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("Action-getRecruithData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("recruitInfo", mapping, form, request, response);
        }
    }

    public ActionForward exportRecruitData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            List<String[]> list = getServiceImp(request).findRecruitData(request);
            String value = "招聘需求报表";
            String filename = value + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            String[] title = {"序号", "入职日期", "人员编号", "姓名", "人员类别", "性别", "岗位名称", "所属公司", "职级", "一级部门", "二级部门","三级部门"};
            XSSFRow row1 = sheet.createRow(0);
            row1.setHeight((short) (100 * 6));
            for (int i = 0; i < title.length; i++) {
                sheet.setColumnWidth(i, 3000);
                Excel02Util.setCell(row1, titleStyle, i, title[i]);
            }

            for (int j = 0; j < list.size(); j++) {
                XSSFRow row = sheet.createRow(j + 1);
                row.setHeight((short) (100 * 6));
                String[] str = list.get(j);
                for (int k = 0; k < str.length; k++) {
                    Excel02Util.setCell(row, bodyStyle, k, str[k]);
                }
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
    }

    /**
     * 获取流程流转信息
     */
    public ActionForward getApprovalData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        TimeCounter.logCurrentTime("Action-getRecruithData", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String begin = request.getParameter("begin");
            String end = request.getParameter("end");
            if(StringUtil.isNull(begin) || StringUtil.isNull(end)){
                throw new Exception("开始时间或者结束时间未填写");
            }
            Date beginTime = DateUtil.convertStringToDate(begin,"yyyy-MM-dd");
            Date endTime = DateUtil.convertStringToDate(end,"yyyy-MM-dd");
            LbpmAnalysisFlowService lbpmAnalysisFlowService = (LbpmAnalysisFlowService)SpringBeanUtil.getBean("lbpmAnalysisFlowService");
            List<String[]> listNew = lbpmAnalysisFlowService.getApprovalDataByTempleteIds(beginTime,AttendUtil.getEndDate(endTime, 0));
            request.setAttribute("queryPage", listNew);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("Action-getApprovalData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("approvalInfo", mapping, form, request, response);
        }
    }

    /**
     * 导出流程流转信息
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportApprovalData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String begin = request.getParameter("begin");
            String end = request.getParameter("end");
            if(StringUtil.isNull(begin) || StringUtil.isNull(end)){
                throw new Exception("开始时间或者结束时间未填写");
            }
            Date beginTime = DateUtil.convertStringToDate(begin,"yyyy-MM-dd");
            Date endTime = DateUtil.convertStringToDate(end,"yyyy-MM-dd");
            LbpmAnalysisFlowService lbpmAnalysisFlowService = (LbpmAnalysisFlowService)SpringBeanUtil.getBean("lbpmAnalysisFlowService");
            List<String[]> list = lbpmAnalysisFlowService.getApprovalDataByTempleteIds(beginTime,AttendUtil.getEndDate(endTime, 0));
            String value = "流程流转时间分析表";
            String filename = value + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            XSSFRow row1 = sheet.createRow(0);
            row1.setHeight((short) (100 * 6));

            XSSFRow row2 = sheet.createRow(1);
            row2.setHeight((short) (100 * 6));

            Excel02Util.insertMerge(sheet, titleStyle, "序号", 0, 0, 0, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "一级部门", 1, 1, 0, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "二级部门", 2, 2, 0, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "三级部门", 3, 3, 0, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "人员编号", 4, 4, 0, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "审批人", 5, 5, 0, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "岗位", 6, 6, 0, 1);
            Excel02Util.insertMerge(sheet, titleStyle, "差旅流程", 7, 8, 0, 0);
            Excel02Util.insertMerge(sheet, titleStyle, "假期流程", 9, 10, 0, 0);
            Excel02Util.insertMerge(sheet, titleStyle, "签卡流程", 11, 12, 0, 0);
            Excel02Util.insertMerge(sheet, titleStyle, "外出流程", 13, 14, 0, 0);
            Excel02Util.insertMerge(sheet, titleStyle, "加班流程", 15, 16, 0, 0);
            Excel02Util.insertMerge(sheet, titleStyle, "合计", 17, 18, 0, 0);
            for(int i = 7 ; i <= 18; i++){
                if(i % 2 == 1){
                    Excel02Util.setCell(row2, titleStyle, i, "审批次数");
                }else{
                    Excel02Util.setCell(row2, titleStyle, i, "平均用时");
                }
            }

            for (int j = 0; j < list.size(); j++) {
                XSSFRow row = sheet.createRow(j + 2);
                row.setHeight((short) (100 * 6));
                String[] str = list.get(j);
                for (int k = 0; k < str.length; k++) {
                    if(k == 0){
                        Excel02Util.setCell(row, bodyStyle, k, j+1);
                    }
                    Excel02Util.setCell(row, bodyStyle, k + 1, str[k]);
                }
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
    }

    /**
     * 异常信息报表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statListDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-statListDetail", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            //开始时间
            String begin = request.getParameter("begin");
            // 结束时间
            String end = request.getParameter("end");
            // 查询对象
            String fdTargetId = request.getParameter("fdTargetId");
            if (StringUtil.isNull(end) && StringUtil.isNull(fdTargetId) && StringUtil.isNull(end)) {
                throw new Exception("开始时间或者结束时间未填写");
            }

            Date beginTime = DateUtil.convertStringToDate(begin,"yyyy-MM-dd");
            Date endTime = DateUtil.convertStringToDate(end,"yyyy-MM-dd");
            SysAttendPersonReportService sysAttendPersonReportService = (SysAttendPersonReportService)SpringBeanUtil.getBean("sysAttendPersonReportService");
            List<String[]> listNew = sysAttendPersonReportService.getAttendDataByTempleteIds(beginTime,AttendUtil.getEndDate(endTime, 0), Arrays.asList(fdTargetId.split(";")));
            request.setAttribute("queryPage", listNew);
        } catch (Exception e) {
            messages.addError(e);
            logger.error(e.getMessage(), e);
        }
        TimeCounter.logCurrentTime("Action-statListDetail", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("statListDetail", mapping, form, request, response);
        }
    }

    /**
     * 导出流程流转信息
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportAttendData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            //开始时间
            String begin = request.getParameter("begin");
            // 结束时间
            String end = request.getParameter("end");
            // 查询对象
            String fdTargetId = request.getParameter("fdTargetId");
            if (StringUtil.isNull(end) && StringUtil.isNull(fdTargetId) && StringUtil.isNull(end)) {
                throw new Exception("开始时间或者结束时间未填写");
            }
            Date beginTime = DateUtil.convertStringToDate(begin,"yyyy-MM-dd");
            Date endTime = DateUtil.convertStringToDate(end,"yyyy-MM-dd");
            SysAttendPersonReportService sysAttendPersonReportService = (SysAttendPersonReportService)SpringBeanUtil.getBean("sysAttendPersonReportService");
            List<String[]> list = sysAttendPersonReportService.getAttendDataByTempleteIds(beginTime,AttendUtil.getEndDate(endTime, 0), Arrays.asList(fdTargetId.split(";")));
            String value = "异常考勤预警名单";
            String filename = value + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            String[] title = {"序号", "一级部门", "二级部门", "三级部门", "姓名", "人员编号", "缺卡天数", "无刷卡次数", "事假天数","病假天数"};
            XSSFRow row1 = sheet.createRow(0);
            row1.setHeight((short) (100 * 6));
            for (int i = 0; i < title.length; i++) {
                sheet.setColumnWidth(i, 3000);
                Excel02Util.setCell(row1, titleStyle, i, title[i]);
            }

            for (int j = 0; j < list.size(); j++) {
                XSSFRow row = sheet.createRow(j + 1);
                row.setHeight((short) (100 * 6));
                String[] str = list.get(j);
                for (int k = 0; k < str.length; k++) {
                    if(k == 0){
                        Excel02Util.setCell(row, bodyStyle, k, j+1);
                    }
                    Excel02Util.setCell(row, bodyStyle, k + 1, str[k]);
                }
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
    }

    /**
     * 异常信息报表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward overListDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-statListDetail", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            //开始时间
            String begin = request.getParameter("begin");
            // 结束时间
            String end = request.getParameter("end");
            // 查询对象
            String fdTargetId = request.getParameter("fdTargetId");
            if (StringUtil.isNull(end) && StringUtil.isNull(fdTargetId) && StringUtil.isNull(end)) {
                throw new Exception("开始时间或者结束时间未填写");
            }

            Date beginTime = DateUtil.convertStringToDate(begin,"yyyy-MM-dd");
            Date endTime = DateUtil.convertStringToDate(end,"yyyy-MM-dd");
            SysAttendOverDetailService sysAttendOverDetailService = (SysAttendOverDetailService)SpringBeanUtil.getBean("sysAttendOverDetailService");
            List<String[]> listNew = sysAttendOverDetailService.getAttendDataByTempleteIds(beginTime,AttendUtil.getEndDate(endTime, 0), Arrays.asList(fdTargetId.split(";")));
            request.setAttribute("queryPage", listNew);
        } catch (Exception e) {
            messages.addError(e);
            logger.error(e.getMessage(), e);
        }
        TimeCounter.logCurrentTime("Action-statListDetail", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("overListDetail", mapping, form, request, response);
        }
    }

    /**
     * 导出流程流转信息
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportOverData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-report", false, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            //开始时间
            String begin = request.getParameter("begin");
            // 结束时间
            String end = request.getParameter("end");
            // 查询对象
            String fdTargetId = request.getParameter("fdTargetId");
            if (StringUtil.isNull(end) && StringUtil.isNull(fdTargetId) && StringUtil.isNull(end)) {
                throw new Exception("开始时间或者结束时间未填写");
            }
            Date beginTime = DateUtil.convertStringToDate(begin,"yyyy-MM-dd");
            Date endTime = DateUtil.convertStringToDate(end,"yyyy-MM-dd");
            SysAttendOverDetailService sysAttendOverDetailService = (SysAttendOverDetailService)SpringBeanUtil.getBean("sysAttendOverDetailService");
            List<String[]> list = sysAttendOverDetailService.getAttendDataByTempleteIds(beginTime,AttendUtil.getEndDate(endTime, 0), Arrays.asList(fdTargetId.split(";")));
            String value = "加班明细表";
            String filename = value + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet();
            XSSFCellStyle bodyStyle = Excel02Util.getBodyStyle(wb);
            XSSFCellStyle titleStyle = Excel02Util.getTitleStyle(wb);
            String[] title = {"序号", "人员编号", "姓名", "一级部门", "二级部门",
                    "三级部门", "加班类型", "加班日期", "申请加班开始时间","实际开始时间",
                    "申请加班结束时间","实际结束时间","加班计划时长","加班实际时长","加班原因",
                    "用餐时间(分钟)","是否转加班费"};
            XSSFRow row1 = sheet.createRow(0);
            row1.setHeight((short) (100 * 6));
            for (int i = 0; i < title.length; i++) {
                sheet.setColumnWidth(i, 3000);
                Excel02Util.setCell(row1, titleStyle, i, title[i]);
            }

            for (int j = 0; j < list.size(); j++) {
                XSSFRow row = sheet.createRow(j + 1);
                row.setHeight((short) (100 * 6));
                String[] str = list.get(j);
                for (int k = 0; k < str.length; k++) {
                    if(k == 0){
                        Excel02Util.setCell(row, bodyStyle, k, j+1);
                    }
                    Excel02Util.setCell(row, bodyStyle, k + 1, str[k]);
                }
            }
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setContentType("application/ms-excel");
            filename = new String(filename.getBytes("UTF-8"), "iso8859-1") + ".xlsx";
            response.setHeader("Cache-Control", "max-age=0");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            messages.addError(e);
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        }
    }
}