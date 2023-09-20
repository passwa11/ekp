package com.landray.kmss.sys.attend.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.attend.forms.SysAttendSynDingForm;
import com.landray.kmss.sys.attend.model.SysAttendImportLog;
import com.landray.kmss.sys.attend.model.SysAttendSynDing;
import com.landray.kmss.sys.attend.service.ISysAttendImportLogService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingService;
import com.landray.kmss.sys.attend.util.UploadResultBean;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public class SysAttendSynDingAction extends ExtendAction {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendSynDingAction.class);

	private ISysAttendSynDingService sysAttendSynDingService;

	@Override
	public IBaseService getServiceImp(HttpServletRequest request) {
		if (sysAttendSynDingService == null) {
			sysAttendSynDingService = (ISysAttendSynDingService) getBean("sysAttendSynDingService");
		}
		return sysAttendSynDingService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
	
	private ISysAttendImportLogService sysAttendImportLogService;

	public ISysAttendImportLogService getSysAttendImportLogService() {
		if (sysAttendImportLogService == null) {
			sysAttendImportLogService = (ISysAttendImportLogService) getBean(
					"sysAttendImportLogService");
		}
		return sysAttendImportLogService;
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request, String curOrderBy) throws Exception {
		String order = super.getFindPageOrderBy(request, curOrderBy);
		order = order.indexOf("sysAttendSynDing.") >= 0 ? order
				: "sysAttendSynDing." + order;
		return order;
	}

	@Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		StringBuffer sb = new StringBuffer();
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			sb.append(" 1=1 ");
		} else {
			sb.append(where);
		}
		String[] fdStatusArr = cv.polls("fdStatus");
		if (fdStatusArr != null && fdStatusArr.length > 0) {
			List<String> fdStatusList = ArrayUtil
					.convertArrayToList(fdStatusArr);
			if (!fdStatusList.isEmpty()) {
				sb.append(" and (1=2");
				if (fdStatusList.contains("1")) {
					sb.append(
							" or ( sysAttendSynDing.fdTimeResult='Normal' and sysAttendSynDing.fdLocationResult not in ('Outside') )");
				}
				if (fdStatusList.contains("11")) {
					sb.append(
							" or (sysAttendSynDing.fdTimeResult='Normal' and sysAttendSynDing.fdLocationResult='Outside') ");
				}
				if (fdStatusList.contains("2")) {
					sb.append(
							" or sysAttendSynDing.fdTimeResult='Late' ");
				}
				if (fdStatusList.contains("3")) {
					sb.append(
							" or sysAttendSynDing.fdTimeResult='Early' ");
				}
				if (fdStatusList.contains("4")) {
					sb.append(
							" or sysAttendSynDing.fdTimeResult='Trip' ");
				}
				if (fdStatusList.contains("5")) {
					sb.append(
							" or sysAttendSynDing.fdTimeResult='Leave' ");
				}
				if (fdStatusList.contains("6")) {
					sb.append(
							" or sysAttendSynDing.fdTimeResult='Outgoing' ");
				}
				if (fdStatusList.contains("7")) {
					sb.append(
							" or (sysAttendSynDing.fdInvalidRecordType='Other'  or sysAttendSynDing.fdInvalidRecordType='Security') ");
				}
				sb.append(")");
			}
		}
		String docCreatorDept = cv.poll("fdPersonDept");
		if (StringUtil.isNotNull(docCreatorDept)) {
			List<String> deptIds = new ArrayList<String>();
			deptIds.add(docCreatorDept);
			List personIds = getSysOrgCoreService()
					.expandToPostPersonIds(deptIds);
			if (!personIds.isEmpty()) {
				sb.append(" and " + HQLUtil.buildLogicIN(
						"sysAttendSynDing.fdPersonId", personIds));
			} else {
				sb.append(" and 1=2");
			}
		}
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendSynDing.class);
	}

	@Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		SysAttendSynDingForm sysAttendSynDingForm = (SysAttendSynDingForm) super.createNewForm(mapping, form, request,
				response);
		((ISysAttendSynDingService) getServiceImp(request)).initFormSetting((IExtendForm) form,
				new RequestContext(request));
		return sysAttendSynDingForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = null;
			model = getServiceImp(request).findByPrimaryKey(id, null, true);
			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model,
						new RequestContext(request));
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
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
	public ActionForward importExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAttendSynDingForm areaForm = (SysAttendSynDingForm) form;
		
		FormFile file = areaForm.getFile();
		if (file == null) {
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setOrderBy(" docCreateTime desc");
			hqlInfo.setPageNo(0);
			hqlInfo.setRowSize(1);
			Page page =getSysAttendImportLogService().findPage(hqlInfo);
			List logList= page.getList();
			UploadResultBean result = new UploadResultBean();// 操作结果集
			if(logList!=null &&!logList.isEmpty()) {
				SysAttendImportLog sysAttendImportLog=(SysAttendImportLog) logList.get(0);
				String resultString=sysAttendImportLog.getFdResultMessage();
				if(StringUtil.isNotNull(resultString)) {
					JSONObject json=JSONObject.fromObject(resultString);
					if(json.containsKey("successCount")) {
						String successCount=json.getString("successCount");
						result.setSuccessCount(Integer.valueOf(successCount));
					}
					if(json.containsKey("ignoreCount")) {
						String ignoreCount=json.getString("ignoreCount");
						result.setIgnoreCount(Integer.valueOf(ignoreCount));
					}
					if(json.containsKey("failCount")) {
						String failCount=json.getString("failCount");
						result.setFailCount(Integer.valueOf(failCount));
					}
					if(json.containsKey("duplicateCount")) {
						String duplicateCount=json.getString("duplicateCount");
						result.setDuplicateCount(Integer.valueOf(duplicateCount));
					}
				}
				request.setAttribute("fdStatus", sysAttendImportLog.getFdStatus());
				request.setAttribute("result", result);
			}
			if (UserOperHelper.allowLogOper("Base_UrlParam", SysAttendSynDing.class.getName())) {  
				UserOperHelper.setEventType(ResourceUtil.getString("sys-attend:sysAttend.oper.batch.import"));
				UserOperHelper.setModelNameAndModelDesc(SysAttendSynDing.class.getName(), ResourceUtil.getString("sys-attend:table.sysAttendSynDing")); 
			}
			request.setAttribute("sysAttendSynDingForm", areaForm);
			ActionForward forward = mapping.findForward("importExcel");
			return forward;
		}else {
			SysAttendImportLog sysAttendImportLog=new SysAttendImportLog();
			sysAttendImportLog.setFdStatus(0);
			sysAttendImportLogService.add(sysAttendImportLog);
			((ISysAttendSynDingService)getServiceImp(request)).addImportData(file.getInputStream(),request,sysAttendImportLog);
			if (UserOperHelper.allowLogOper("Base_UrlParam", SysAttendSynDing.class.getName())) {  
				UserOperHelper.setEventType(ResourceUtil.getString("sys-attend:sysAttend.import.title"));
				UserOperHelper.setModelNameAndModelDesc(SysAttendSynDing.class.getName(), ResourceUtil.getString("sys-attend:table.sysAttendSynDing")); 
			}
		}
		return mapping.findForward("importExcel");
	}
	
	
	/**
	 * 导出excel模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fileName = ResourceUtil
				.getString("sysAttend.import.excel.title","sys-attend");
		response.setContentType(
				"application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition",
				"attachment;filename=\""
						+ new String(fileName.getBytes("GBK"), "ISO-8859-1")
						+ ".xls\"");
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, fileName);
			int colIndex = 0;
			sheet.setColumnWidth(colIndex++, 4000);
			sheet.setColumnWidth(colIndex++, 4000);
			sheet.setColumnWidth(colIndex++, 6000);
			int colNum = colIndex;

			int rowIndex = 0;
			/* 标题行 */
			HSSFRow titlerow = sheet.createRow(rowIndex++);
			titlerow.setHeight((short) 400);
			HSSFCellStyle titleCellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			titleCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			titleCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
			font.setBold(true);
			font.setColor(IndexedColors.RED.getIndex());
			titleCellStyle.setFont(font);
			HSSFCell[] titleCells = new HSSFCell[colNum];
			for (int i = 0; i < titleCells.length; i++) {
				titleCells[i] = titlerow.createCell(i);
				titleCells[i].setCellStyle(titleCellStyle);
			}

			int titleIndex = 0;
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sysAttend.import.fdName","sys-attend")+"(*)");
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sysAttend.import.fdLoginName","sys-attend")+"(*)");
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sysAttend.import.signTime","sys-attend")+"(*)");
			String dateFormat=ResourceUtil.getString("sysAttend.date.format.datetime", "sys-attend", request.getLocale());
			HSSFCellStyle textStyle = workbook.createCellStyle();
			HSSFDataFormat format = workbook.createDataFormat();
			textStyle.setDataFormat(format.getFormat(dateFormat));
			sheet.setDefaultColumnStyle(2, textStyle);
			HSSFCellStyle contentCellStyle = workbook.createCellStyle();
			contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
			HSSFRow contentrow = sheet.createRow(rowIndex++);
			contentrow.setHeight((short) 400);
			HSSFCell[] contentcells = new HSSFCell[colNum];
			for (int j = 0; j < contentcells.length; j++) {
				contentcells[j] = contentrow.createCell(j);
				contentcells[j].setCellStyle(contentCellStyle);
			}
			contentcells[0].setCellValue("zhangsan");
			contentcells[1].setCellValue("zhs001");
			contentcells[2].setCellValue(DateUtil.convertDateToString(new Date(), dateFormat));
			HSSFSheet conditionSheet = workbook.createSheet();
			workbook.setSheetName(workbook.getSheetIndex(conditionSheet),
					ResourceUtil
							.getString("sysAttend.import.condition","sys-attend"));

			// 注意事项行
			CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 20);
			short height = 400;
			// 在sheet里增加合并单元格
			com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(conditionSheet, cra);
			HSSFRow conditionrow = conditionSheet.createRow(0);
			HSSFCellStyle conditionCellStyle = workbook.createCellStyle();
			HSSFFont conditionFont = workbook.createFont();
			conditionFont.setBold(true);
			conditionFont.setColor(Font.COLOR_RED);
			conditionCellStyle.setFont(conditionFont);
			conditionCellStyle.setWrapText(true);
			conditionrow.setHeight((short) (height));
			HSSFCell conditionCell = conditionrow.createCell(0);
			String condition = ResourceUtil.getString("sysAttend.import.condition.content", "sys-attend")+dateFormat;
			conditionCell.setCellValue(new HSSFRichTextString(condition));
			conditionCell.setCellStyle(conditionCellStyle);
			workbook.write(response.getOutputStream());
			
			if (UserOperHelper.allowLogOper("Base_UrlParam", SysAttendSynDing.class.getName())) {  
				UserOperHelper.setEventType(ResourceUtil.getString("sys-attend:sysAttendReport.export")+ResourceUtil.getString("sys-attend:sysAttend.import.excel.title"));
				UserOperHelper.setModelNameAndModelDesc(SysAttendSynDing.class.getName(), ResourceUtil.getString("sys-attend:table.sysAttendSynDing")); 
			}
			
		}catch(Exception e) {
			logger.error("原始记录导出模板报错", e);
		}finally {
			workbook.close();
		}
	}
	
	
	public ActionForward batchExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-batchExport", true,
				getClass());
		KmssMessages messages = new KmssMessages();

		HQLInfo hqlInfo = new HQLInfo();
		changeFindPageHQLInfo(request, hqlInfo);
		hqlInfo.setOrderBy("sysAttendSynDing.fdUserCheckTime desc");
		// findList不做权限过滤，这里手动处理
		if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		}
		List list = getServiceImp(request).findList(hqlInfo);
		// 添加日志信息
		UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
		String filename = ResourceUtil.getString(
				"sysAttendMain.export.filename.synDing",
				"sys-attend");
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ new String(filename.getBytes("GBK"), "ISO-8859-1")
				+ ".xls\"");
		ServletOutputStream out = response.getOutputStream();
		try {
			HSSFWorkbook workbook = null;
			workbook = ((ISysAttendSynDingService)getServiceImp(request))
					.buildWorkBook(list);
			workbook.write(out);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		if (UserOperHelper.allowLogOper("Base_UrlParam", SysAttendSynDing.class.getName())) {  
			UserOperHelper.setEventType(ResourceUtil.getString("sys-attend:sysAttendReport.export"));
			UserOperHelper.setModelNameAndModelDesc(SysAttendSynDing.class.getName(), ResourceUtil.getString("sys-attend:table.sysAttendSynDing")); 
		}
		TimeCounter.logCurrentTime("Action-batchExport", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return null;
		}
	}
}
