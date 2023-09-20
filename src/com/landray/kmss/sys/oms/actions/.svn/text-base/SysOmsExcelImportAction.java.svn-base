package com.landray.kmss.sys.oms.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.oms.forms.SysOmsExcelImportForm;
import com.landray.kmss.sys.oms.service.ISysOmsExcelImportService;
import com.landray.kmss.sys.oms.temp.OmsTempSynModel;
import com.landray.kmss.sys.profile.util.OrgImportExportUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 * 组织机构基本信息导入
 * 
 * @author 潘永辉 2016-10-31
 * 
 */
public class SysOmsExcelImportAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	
	
	private ISysOmsExcelImportService sysOmsExcelImportService;

	public ISysOmsExcelImportService getSysOmsExcelImportService() {
		if (sysOmsExcelImportService == null) {
			sysOmsExcelImportService = (ISysOmsExcelImportService) SpringBeanUtil
					.getBean("sysOmsExcelImportService");
		}

		return sysOmsExcelImportService;
	}
	

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}



	/**
	 * 内置基本信息导入模板
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
		TimeCounter.logCurrentTime("Action-importData", true, getClass());

		SysOmsExcelImportForm excelImportForm = (SysOmsExcelImportForm) form;
		FormFile file = excelImportForm.getFile();
		if (file == null || file.getFileSize() < 1) {
			request.setAttribute("resultMsg", ResourceUtil.getString(
					"sys.profile.orgImport.empty.file", "sys-profile"));
		} else {
			KmssMessages messages = null;
			try {
				messages = getSysOmsExcelImportService().importData(excelImportForm);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.toString());
				messages = new KmssMessages();
				messages.addError(new KmssMessage(e.getMessage()));
			}
			StringBuffer error = new StringBuffer();
			for (KmssMessage message : messages.getMessages()) {
				error.append(message.getMessageKey()).append("</br>");
			}
			request.setAttribute("resultMsg", error.toString());
			
		}

		TimeCounter.logCurrentTime("Action-importData", false, getClass());
		return getActionForward("importData", mapping, form, request, response);
	}
	
	
	/**
	 * 模板下载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String exportName = ResourceUtil
					.getString(
							"sys-oms:sys.oms.template.import.fileName");
			String isTest = request.getParameter("isTest");
			HSSFWorkbook workbook = getSysOmsExcelImportService().buildWorkBook();
			if("1".equals(isTest)) {
				String identy = request.getParameter("identy");
				workbook = getSysOmsExcelImportService().buildWorkBookNotEmpty(identy);
			}else {
				workbook = getSysOmsExcelImportService().buildWorkBook();
			}

			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ OrgImportExportUtil.encodeFileName(request, exportName));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			return null;
		} catch (IOException e) {
			logger.error(e.toString());
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}
	
	
	public ActionForward checkData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-checkData", true, getClass());
		SysOmsExcelImportForm excelImportForm = (SysOmsExcelImportForm) form;
		JSONObject fileData = new JSONObject();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
	        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// 
	        Date date = new Date();// 获取当前时间 
			logger.info("---excel上传检查数据开始时间---"+ sdf.format(date));
			fileData = getSysOmsExcelImportService().checkData(excelImportForm);
			int modelType = fileData.getInteger("modelType");
			jsonObject.put("modelType", modelType);
			//模式
			if (OmsTempSynModel.OMS_TEMP_SYN_MODEL_100.getValue() == modelType) { //获取部门人员异常数据
				JSONArray deptFileDatajsonArray = fileData.getJSONArray("deptFileData");
				JSONArray personFileDatajsonArray = fileData.getJSONArray("personFileData");
				jsonObject.put("deptFileData", deptFileDatajsonArray);
				jsonObject.put("personFileData", personFileDatajsonArray);
				
			}else if (OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue() == modelType) {
				JSONArray deptFileDatajsonArray = fileData.getJSONArray("deptFileData");
				JSONArray personFileDatajsonArray = fileData.getJSONArray("personFileData");
				JSONArray deptPersonFileDatajsonArray = fileData.getJSONArray("personDeptFileData");
				jsonObject.put("deptFileData", deptFileDatajsonArray);
				jsonObject.put("personFileData", personFileDatajsonArray);
				jsonObject.put("personDeptFileData", deptPersonFileDatajsonArray);
				
			}else if (OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue() == modelType) {
				JSONArray deptFileDatajsonArray = fileData.getJSONArray("deptFileData");
				JSONArray personFileDatajsonArray = fileData.getJSONArray("personFileData");
				JSONArray postFileDatajsonArray = fileData.getJSONArray("postFileData");
				JSONArray postPersonFileDatajsonArray = fileData.getJSONArray("personPostFileData");
				jsonObject.put("deptFileData", deptFileDatajsonArray);
				jsonObject.put("personFileData", personFileDatajsonArray);
				jsonObject.put("postFileData", postFileDatajsonArray);
				jsonObject.put("personPostFileData", postPersonFileDatajsonArray);	
				
			}else if (OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue() == modelType) {
				JSONArray deptFileDatajsonArray = fileData.getJSONArray("deptFileData");
				JSONArray personFileDatajsonArray = fileData.getJSONArray("personFileData");
				JSONArray postFileDatajsonArray = fileData.getJSONArray("postFileData");
				JSONArray postPersonFileDatajsonArray = fileData.getJSONArray("personPostFileData");
				JSONArray deptPersonFileData = fileData.getJSONArray("personDeptFileData");
				jsonObject.put("deptFileData", deptFileDatajsonArray);
				jsonObject.put("personFileData", personFileDatajsonArray);
				jsonObject.put("postFileData", postFileDatajsonArray);
				jsonObject.put("personPostFileData", postPersonFileDatajsonArray);	
				jsonObject.put("personDeptFileData", deptPersonFileData);	
				
			}
			jsonObject.put("success", true);
			printWriter.print(jsonObject.toString());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("msg", e.getMessage());
			printWriter.print(jsonObject.toString());
			e.printStackTrace();
		}finally {
			SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
	        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// 
	        Date date = new Date();// 获取当前时间 
			logger.info("---excel上传检查数据结束时间---"+ sdf.format(date));
			printWriter.close();
		}
		return null ;
	}

}
