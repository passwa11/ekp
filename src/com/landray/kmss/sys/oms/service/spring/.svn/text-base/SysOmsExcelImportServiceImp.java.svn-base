package com.landray.kmss.sys.oms.service.spring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.oms.forms.SysOmsExcelImportForm;
import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.sys.oms.service.ISysOmsExcelImportService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxSynService;
import com.landray.kmss.sys.oms.temp.OmsTempSynModel;
import com.landray.kmss.sys.oms.temp.OmsTempSynResult;
import com.landray.kmss.sys.oms.temp.SysOmsExcelUtil;
import com.landray.kmss.sys.oms.temp.SysOmsSynConfig;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 组织结构接入-excel
 *
 * @author
 *
 */
public class SysOmsExcelImportServiceImp implements ISysOmsExcelImportService,
		SysOrgConstant {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysOmsExcelImportServiceImp.class);
	private ISysOmsTempTrxSynService sISysOmsTempTrxSynService;
	public ISysOmsTempTrxSynService getSysOmsTempTrxService() {
		if (sISysOmsTempTrxSynService == null) {
			sISysOmsTempTrxSynService = (ISysOmsTempTrxSynService) SpringBeanUtil
					.getBean("sysOmsTempTrxSynService");
		}

		return sISysOmsTempTrxSynService;
	}

	Map<String, String> personExtendFile = null;

	/**
	 * 创建一个新的excel文件
	 */
	@Override
	public HSSFWorkbook buildWorkBook() throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		SysOmsExcelUtil excelUtil = new SysOmsExcelUtil();
		// 第1个sheet为部门导入数据
		HSSFSheet sheet1 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet1"));
		excelUtil.buildTemplate(wb, sheet1, SysOmsExcelUtil.EXCEL_SHEET_DEPT);
		// 第2个sheet为职位导入数据
		HSSFSheet sheet2 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet2"));
		excelUtil.buildTemplate(wb, sheet2, SysOmsExcelUtil.EXCEL_SHEET_POST);
		// 第3个sheet为人员导入数据
		HSSFSheet sheet3 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet3"));
		excelUtil.buildTemplate(wb, sheet3, SysOmsExcelUtil.EXCEL_SHEET_PERSON);
		// 第4个sheet为人员部门关系导入数据
		HSSFSheet sheet4 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet4"));
		excelUtil.buildTemplate(wb, sheet4, SysOmsExcelUtil.EXCEL_SHEET_PERSON_DEPT);
		// 第5个sheet为人员职位关系导入数据
		HSSFSheet sheet5 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet5"));
		excelUtil.buildTemplate(wb, sheet5, SysOmsExcelUtil.EXCEL_SHEET_PERSON_POST);
		// 第6个sheet为人员职位关系导入数据
		HSSFSheet sheet6 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet6"));
		excelUtil.buildItemNodes(wb, sheet6, SysOmsExcelUtil.EXCEL_SHEET_EXPLAIN);
		return wb;
	}


	//导入数据
	@Override
	public KmssMessages importData(SysOmsExcelImportForm excelImportForm) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONArray deptJsonArray = new JSONArray();
		JSONArray postJsonArray = new JSONArray();
		JSONArray personJsonArray = new JSONArray();
		JSONArray personDeptJsonArray = new JSONArray();
		JSONArray personPostJsonArray = new JSONArray();
		//获取 人员  部门排序方式 
		boolean fdDeptIsAsc = "1".equals(excelImportForm.getFdDeptIsAsc())?true:false;
		boolean fdPersonIsAsc = "1".equals(excelImportForm.getFdPersonIsAsc())?true:false;
		boolean fdPersonIsMainDept = "1".equals(excelImportForm.getFdPersonIsMainDept())?true:false;
		boolean fdPersonDeptIsFull = "1".equals(excelImportForm.getFdPersonDeptIsFull())?true:false;
		boolean fdPersonPostIsFull = "1".equals(excelImportForm.getFdPersonPostIsFull())?true:false;
		//先获取excel 里面的数据
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(excelImportForm.getFile().getInputStream());
			int sheets = wb.getNumberOfSheets();
			if (sheets < 1) {
				KmssMessage retMsgBufmessage = new KmssMessage("Excel模版错误");
				messages.addMsg(retMsgBufmessage);
				return messages;
			} else {
				for (int i = 1; i <= sheets; i++) {
					Sheet sheet = wb.getSheetAt(i - 1);
					String sheetName = sheet.getSheetName();
					if ("部门信息".equals(sheetName)) {
						//部门
						deptJsonArray = parseDeptSheetData(sheet);
					} else if ("岗位信息".equals(sheetName)) {
						//岗位
						postJsonArray = parsePostSheetData(sheet);
					} else if ("人员信息".equals(sheetName)) {
						//人员
						personJsonArray = parsePersonSheetData(sheet);
					} else if ("人员部门关系信息".equals(sheetName)) {
						//部门人员关系
						personDeptJsonArray = parseDPRSheetData(sheet);
					} else if ("人员岗位关系信息".equals(sheetName)) {
						//岗位人员关系
						personPostJsonArray = parsePPRSheetData(sheet);
					}
				}
			}
			int type = getModelType(deptJsonArray, postJsonArray, personJsonArray, personDeptJsonArray, personPostJsonArray);
			OmsTempSynResult<Object> synResult = null;
			StringBuffer retMsgBuf = new StringBuffer();
			SysOmsSynConfig synConfig = new SysOmsSynConfig();
			synConfig.setFdDeptIsAsc(fdDeptIsAsc);
			synConfig.setFdPersonIsAsc(fdPersonIsAsc);
			synConfig.setFdPersonIsMainDept(fdPersonIsMainDept);
			synConfig.setFdPersonDeptIsFull(fdPersonDeptIsFull);
			synConfig.setFdPersonPostIsFull(fdPersonPostIsFull);
			if (type == OmsTempSynModel.OMS_TEMP_SYN_MODEL_100.getValue()) {
				synResult = getSysOmsTempTrxService().begin(OmsTempSynModel.OMS_TEMP_SYN_MODEL_100, synConfig);
				if (synResult.getCode() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL) {
					KmssMessage message = new KmssMessage(ResourceUtil.getString(synResult.getMsg()));
					messages.addError(message);
					return messages;
				}
				retMsgBuf.append(handleModel1(deptJsonArray, personJsonArray, synResult.getTrxId()));
				KmssMessage retMsgBufmessage = new KmssMessage(retMsgBuf.toString());
				messages.addMsg(retMsgBufmessage);

			} else if (type == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()) {
				synResult = getSysOmsTempTrxService().begin(OmsTempSynModel.OMS_TEMP_SYN_MODEL_200, synConfig);
				if (synResult.getCode() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL) {
					KmssMessage message = new KmssMessage(ResourceUtil.getString(synResult.getMsg()));
					messages.addError(message);
					return messages;
				}
				retMsgBuf.append(handleModel2(deptJsonArray, personJsonArray, personDeptJsonArray, synResult.getTrxId()));
				KmssMessage retMsgBufmessage = new KmssMessage(retMsgBuf.toString());
				messages.addMsg(retMsgBufmessage);

			} else if (type == OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()) {
				synResult = getSysOmsTempTrxService().begin(OmsTempSynModel.OMS_TEMP_SYN_MODEL_300, synConfig);
				if (synResult.getCode() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL) {
					KmssMessage message = new KmssMessage(ResourceUtil.getString(synResult.getMsg()));
					messages.addError(message);
					return messages;
				}
				retMsgBuf.append(handleModel3(deptJsonArray, personJsonArray, postJsonArray, personPostJsonArray, synResult.getTrxId()));
				KmssMessage retMsgBufmessage = new KmssMessage(retMsgBuf.toString());
				messages.addMsg(retMsgBufmessage);
				return messages;

			} else if (type == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()) {
				synResult = getSysOmsTempTrxService().begin(OmsTempSynModel.OMS_TEMP_SYN_MODEL_400, synConfig);
				if (synResult.getCode() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL) {
					KmssMessage message = new KmssMessage(ResourceUtil.getString(synResult.getMsg()));
					messages.addError(message);
					return messages;
				}
				retMsgBuf.append(handleModel40(deptJsonArray, personJsonArray, postJsonArray, personPostJsonArray, personDeptJsonArray, synResult.getTrxId()));
				KmssMessage retMsgBufmessage = new KmssMessage(retMsgBuf.toString());
				messages.addMsg(retMsgBufmessage);
			}
			return messages;
		}catch (Exception e){
			throw e;
		}finally {
			if(wb!=null){
				wb.close();
			}
		}
	}



	private String handleModel1(JSONArray deptJsonArr,JSONArray personJsonArr,String trxId) throws Exception{
		//获取到部门 人员 职位 人员职位关系数据转成modellist
		List<SysOmsTempDept> deptLits = getDeptModelList(deptJsonArr,trxId);
		logger.debug("模式100,转换部门对象集合："+deptLits.toArray().toString());
		List<SysOmsTempPerson> personLits = getPersonModelList(personJsonArr,trxId);
		logger.debug("模式100,转换人员对象集合："+personLits.toArray().toString());

		StringBuffer returnMsg = new StringBuffer();
		boolean isFail;
		//调用方法写入数据库
		OmsTempSynResult omsTempSynResult = null;
		omsTempSynResult = getSysOmsTempTrxService().addTempDept(trxId, deptLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			logger.error("模式100存储部门信息到临时表失败："+omsTempSynResult.getMsg());
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));
		omsTempSynResult = getSysOmsTempTrxService().addTempPerson(trxId, personLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			logger.error("模式100存储人员信息到临时表失败："+omsTempSynResult.getMsg());
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));
		omsTempSynResult = getSysOmsTempTrxService().end(trxId);
		if (omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL) {
			returnMsg.append("导入数据异常，请到'组织接入-接入日志' 对应事务详情页查看日志！");
			returnMsg.append("</br>");
		}else if(omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN){
			returnMsg.append("部分数据导入成功,请到'组织接入-接入日志' 对应事务详情页查看日志！");
		}else {
			returnMsg.append("导入成功");
		}
		return returnMsg.toString();

	}

	private String handleModel2(JSONArray deptJsonArr,JSONArray personJsonArr,JSONArray dpJsonArr,String trxId) throws Exception{
		//获取到部门 人员 人员部门关系数据转成modellist
		List<SysOmsTempDept> deptLits = getDeptModelList(deptJsonArr,trxId);
		logger.debug("模式200,转换部门对象集合："+deptLits.toArray().toString());
		List<SysOmsTempPerson> personLits = getPersonModelList(personJsonArr,trxId);
		logger.debug("模式200,转换人员对象集合："+personLits.toArray().toString());
		List<SysOmsTempDp> deptPersonList = getDeptPersonModelList(dpJsonArr,trxId);
		logger.debug("模式200,转换人员部门对象集合："+deptPersonList.toArray().toString());


		StringBuffer returnMsg = new StringBuffer();
		boolean isFail;
		//调用方法写入数据库
		OmsTempSynResult omsTempSynResult = null;
		omsTempSynResult = getSysOmsTempTrxService().addTempDept(trxId, deptLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			logger.error("模式200存储部门信息到临时表失败："+omsTempSynResult.getMsg());
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().addTempPerson(trxId, personLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			logger.error("模式200存储人员信息到临时表失败："+omsTempSynResult.getMsg());
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().addTempDeptPerson(trxId, deptPersonList);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			logger.error("模式200存储人员部门信息到临时表失败："+omsTempSynResult.getMsg());
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().end(trxId);
		if (omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL) {
			returnMsg.append("导入数据异常，请到'组织接入-接入日志' 对应事务详情页查看日志！");
			returnMsg.append("</br>");
		}else if(omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN){
			returnMsg.append("部分数据导入成功,请到'组织接入-接入日志' 对应事务详情页查看日志！");
		}else {
			returnMsg.append("导入成功");
		}
		return returnMsg.toString();
	}

	private String handleModel3(JSONArray deptJsonArr,JSONArray personJsonArr,JSONArray postJsonArr,JSONArray postPersonJsonArr,String trxId) throws Exception{
		//获取到部门 人员 职位 人员职位关系数据转成modellist
		List<SysOmsTempDept> deptLits = getDeptModelList(deptJsonArr,trxId);
		logger.debug("模式300,转换部门对象集合："+deptLits.toArray().toString());
		List<SysOmsTempPerson> personLits = getPersonModelList(personJsonArr,trxId);
		logger.debug("模式300,转换人员对象集合："+personLits.toArray().toString());
		List<SysOmsTempPost> postLits = getPostModelMap(postJsonArr,trxId);
		logger.debug("模式300,转换职位对象集合："+postLits.toArray().toString());
		List<SysOmsTempPp> postPersonList = getPostPersonModelList(postPersonJsonArr,trxId);
		logger.debug("模式300,转换人员职位对象集合："+postPersonList.toArray().toString());


		StringBuffer returnMsg = new StringBuffer();
		boolean isFail;
		//调用方法写入数据库
		OmsTempSynResult omsTempSynResult = null;
		omsTempSynResult = getSysOmsTempTrxService().addTempDept(trxId, deptLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			logger.error("模式300存储部门信息到临时表失败："+omsTempSynResult.getMsg());
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().addTempPost(trxId,postLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			logger.error("模式300存储职位信息到临时表失败："+omsTempSynResult.getMsg());
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().addTempPostPerson(trxId, postPersonList);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			logger.error("模式300存储职位人员信息到临时表失败："+omsTempSynResult.getMsg());
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().addTempPerson(trxId, personLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			logger.error("模式300存储人员信息到临时表失败："+omsTempSynResult.getMsg());
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().end(trxId);
		if (omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL) {
			returnMsg.append("导入数据异常，请到'组织接入-接入日志' 对应事务详情页查看日志！");
			returnMsg.append("</br>");
		}else if(omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN){
			returnMsg.append("部分数据导入成功,请到'组织接入-接入日志' 对应事务详情页查看日志！");
		}else {
			returnMsg.append("导入成功");
		}
		return returnMsg.toString();
	}

	private String handleModel40(JSONArray deptJsonArr,JSONArray personJsonArr,JSONArray postJsonArr,JSONArray postPersonJsonArr,JSONArray dpJsonArr,String trxId) throws Exception{
		//获取到部门 人员 职位 人员职位关系 部门人员关系数据转成modellist
		List<SysOmsTempDept> deptLits = getDeptModelList(deptJsonArr,trxId);
		logger.debug("模式400,转换部门对象集合："+deptLits.toArray().toString());
		List<SysOmsTempPerson> personLits = getPersonModelList(personJsonArr,trxId);
		logger.debug("模式400,转换人员对象集合："+personLits.toArray().toString());
		List<SysOmsTempPost> postLits = getPostModelMap(postJsonArr,trxId);
		logger.debug("模式400,转换职位对象集合："+postLits.toArray().toString());
		List<SysOmsTempPp> postPersonList = getPostPersonModelList(postPersonJsonArr,trxId);
		logger.debug("模式400,转换人员职位对象集合："+postPersonList.toArray().toString());
		List<SysOmsTempDp> deptPersonList = getDeptPersonModelList(dpJsonArr,trxId);
		logger.debug("模式400,转换人员部门对象集合："+deptPersonList.toArray().toString());
		StringBuffer returnMsg = new StringBuffer();
		boolean isFail;
		//调用方法写入数据库
		OmsTempSynResult omsTempSynResult = null;
		omsTempSynResult = getSysOmsTempTrxService().addTempDept(trxId, deptLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			logger.error("模式400存储部门信息到临时表失败："+omsTempSynResult.getMsg());
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().addTempPerson(trxId, personLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			logger.error("模式400存储人员信息到临时表失败："+omsTempSynResult.getMsg());
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().addTempPost(trxId,postLits);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			logger.error("模式400存储职位信息到临时表失败："+omsTempSynResult.getMsg());
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().addTempPostPerson(trxId, postPersonList);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			logger.error("模式400存储职位人员信息到临时表失败："+omsTempSynResult.getMsg());
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().addTempDeptPerson(trxId, deptPersonList);
		isFail = omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL;
		if (isFail) {
			returnMsg.append(omsTempSynResult.getMsg());
			returnMsg.append("</br>");
			logger.error("模式400存储职位人员信息到临时表失败："+omsTempSynResult.getMsg());
			return returnMsg.toString();
		}
		returnMsg.append(omsTempSynResult.getIllegalDataMsg("</br>"));

		omsTempSynResult = getSysOmsTempTrxService().end(trxId);
		if (omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL) {
			returnMsg.append("导入数据异常，请到'组织接入-接入日志' 对应事务详情页查看日志！");
			returnMsg.append("</br>");
		}else if(omsTempSynResult.getCode() ==SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN){
			returnMsg.append("部分数据导入成功,请到'组织接入-接入日志' 对应事务详情页查看日志！");
		}else {
			returnMsg.append("导入成功");
		}
		return returnMsg.toString();

	}


	/**
	 * 转换部门信息为导入临时表所需的数据格式
	 *
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private JSONArray parseDeptSheetData(Sheet sheet) throws Exception {
		// 取模板字段
		SysOmsExcelUtil util = new SysOmsExcelUtil();
		Map<String, String> templateTitle = util.getExcelTemplateFieldsMap(SysOmsExcelUtil.EXCEL_SHEET_DEPT);
		Map<Integer, String> deptMapping = new HashMap<>();
		JSONArray sheetDataList = new JSONArray();
		JSONObject dept = null;
		//获取最大列数
		int rsColumn=0;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue; // 跳过空行
			}
			if (i ==0) { //第一行
				int rsColumns = row.getLastCellNum();
				rsColumn=rsColumns;
				for (int j = 0; j < rsColumns; j++) {
					String value = getCellFormatValue(row.getCell((short) j));
					if (templateTitle.containsKey(value)) {
						deptMapping.put(j, templateTitle.get(value));
					}
				}
			}else {
				dept = new JSONObject();
				// 取单元格的值i
				boolean flag =false;
				for (int j = 0; j < rsColumn; j++) {
					if (deptMapping.containsKey(j)) {
						String value = getCellFormatValue(row.getCell((short) j));
						//第二行数据第一列是空则直接跳过
						//第二行第一列为空则返回空的array
						if ("fdDeptId".equals(deptMapping.get(j)) && i == 1 && StringUtil.isNull(value)) {
							return sheetDataList;
						}
						dept.put(deptMapping.get(j), value);
						if(StringUtil.isNotNull(value)){
							flag = true;
						}
					}else {
						continue;
					}
				}
				if (flag) {
					dept.put("lineNum", i+1);
					sheetDataList.add(dept);
				}
			}
		}
		return sheetDataList;
	}



	/**
	 * 转换岗位信息为导入临时表所需的数据格式
	 *
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private JSONArray parsePostSheetData(Sheet sheet) throws Exception {
		// 取模板字段
		SysOmsExcelUtil util = new SysOmsExcelUtil();
		Map<String, String> templateTitle = util.getExcelTemplateFieldsMap(SysOmsExcelUtil.EXCEL_SHEET_POST);
		Map<Integer, String> postMapping = new HashMap<>();
		JSONArray sheetDataList = new JSONArray();
		JSONObject post = null;
		//获取最大列数
		int rsColumn=0;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue; // 跳过空行
			}
			//列总数
			if (i ==0) { //第一行
				int rsColumns = row.getLastCellNum();
				rsColumn = rsColumns;
				for (int j = 0; j < rsColumns; j++) {
					String value = getCellFormatValue(row.getCell((short) j));
					if (templateTitle.containsKey(value)) {
						postMapping.put(j, templateTitle.get(value));
					}
				}
			}else {
				post = new JSONObject();
				// 取单元格的值
				boolean flag =false;
				for (int j = 0; j < rsColumn; j++) {
					if (postMapping.containsKey(j)) {
						String value = getCellFormatValue(row.getCell((short) j));
						//第二行第一列为空则返回空的array
						if ("fdPostId".equals(postMapping.get(j)) && i == 1 && StringUtil.isNull(value)) {
							return sheetDataList;
						}
						post.put(postMapping.get(j), value);
						if(StringUtil.isNotNull(value)){
							flag = true;
						}
					}else {
						continue;
					}

				}
				if (flag) {
					post.put("lineNum", i+1);
					sheetDataList.add(post);
				}
			}
		}
		return sheetDataList;
	}

	/**
	 * 导入人员信息 转换人员sheet数据为导入服务所需要的数据格式
	 *
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private JSONArray parsePersonSheetData(Sheet sheet) throws Exception {
		// 取模板字段
		personExtendFile= new HashMap<>();
		SysOmsExcelUtil util = new SysOmsExcelUtil();
		Map<String, String> templateTitle = util.getExcelTemplateFieldsMap(SysOmsExcelUtil.EXCEL_SHEET_PERSON);
		Map<Integer, String> personMapping = new HashMap<>();
		JSONArray sheetDataList = new JSONArray();
		JSONObject person = null;
		//获取最大列数
		int rsColumn=0;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue; // 跳过空行
			}
			//列总数
			if (i ==0) { //第一行
				int rsColumns = row.getLastCellNum();
				rsColumn=rsColumns;
				for (int j = 0; j < rsColumns; j++) {
					String value = getCellFormatValue(row.getCell((short) j));
					if (templateTitle.containsKey(value)) {
						personMapping.put(j, templateTitle.get(value));
					}
					if (value.contains("[拓展]")) {
						personExtendFile.put(value, templateTitle.get(value));
					}
				}
			}else {
				person = new JSONObject();
				// 取单元格的值
				boolean flag = false;
				for (int j = 0; j < rsColumn; j++) {
					if (personMapping.containsKey(j)) {
						String value = getCellFormatValue(row.getCell((short) j));
						//第二行第一列为空则返回空的array
						if ("fdPersonId".equals(personMapping.get(j)) && i == 1 && StringUtil.isNull(value)) {
							return sheetDataList;
						}
						person.put(personMapping.get(j), value);
						if (StringUtil.isNotNull(value)) {
							flag = true;
						}
					}else {
						continue;
					}
				}
				if (flag) {
					person.put("lineNum", i+1);
					sheetDataList.add(person);
				}
			}
		}

		return sheetDataList;
	}


	/**
	 * 转换部门人员关系
	 *
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private JSONArray parseDPRSheetData(Sheet sheet) throws Exception {
		// 取模板字段
		SysOmsExcelUtil util = new SysOmsExcelUtil();
		Map<String, String> templateTitle = util.getExcelTemplateFieldsMap(SysOmsExcelUtil.EXCEL_SHEET_PERSON_DEPT);
		Map<Integer, String> DPRMapping = new HashMap<>();
		JSONArray sheetDataList = new JSONArray();
		JSONObject deptPersonR  = null;
		//获取最大列数
		int rsColumn=0;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue; // 跳过空行
			}
			//列总数
			if (i ==0) { //第一行
				int rsColumns = row.getLastCellNum();
				rsColumn = rsColumns;
				for (int j = 0; j < rsColumns; j++) {
					String value = getCellFormatValue(row.getCell((short) j));
					if (templateTitle.containsKey(value)) {
						DPRMapping.put(j, templateTitle.get(value));
					}
				}
			}else {
				deptPersonR = new JSONObject();
				// 取单元格的值
				boolean flag = false;
				for (int j = 0; j < rsColumn; j++) {
					if (DPRMapping.containsKey(j)) {
						String value = getCellFormatValue(row.getCell((short) j));
						//第二行第一列为空则返回空的array
						if ("fdPersonId".equals(DPRMapping.get(j)) && i == 1 && StringUtil.isNull(value)) {
							return sheetDataList;
						}
						deptPersonR.put(DPRMapping.get(j), value);
						if (StringUtil.isNotNull(value)) {
							flag = true;
						}
					}else {
						continue;
					}
				}
				if (flag) {
					deptPersonR.put("lineNum", i+1);
					sheetDataList.add(deptPersonR);
				}
			}
		}
		return sheetDataList;
	}


	/**
	 * 转换岗位人员关系
	 *
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private JSONArray parsePPRSheetData(Sheet sheet) throws Exception {
		// 取模板字段
		SysOmsExcelUtil util = new SysOmsExcelUtil();
		Map<String, String> templateTitle = util.getExcelTemplateFieldsMap(SysOmsExcelUtil.EXCEL_SHEET_PERSON_POST);
		Map<Integer, String> PPRRMapping = new HashMap<>();
		JSONArray sheetDataList = new JSONArray();
		JSONObject postPersonR = null;
		int rsColumn=0;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue; // 跳过空行
			}
			//列总数
			if (i ==0) { //第一行
				int rsColumns = row.getLastCellNum();
				rsColumn = rsColumns;
				for (int j = 0; j < rsColumns; j++) {
					String value = getCellFormatValue(row.getCell((short) j));
					if (templateTitle.containsKey(value)) {
						PPRRMapping.put(j, templateTitle.get(value));
					}
				}
			}else {
				postPersonR = new JSONObject();
				// 取单元格的值
				boolean flag = false;
				for (int j = 0; j < rsColumn; j++) {
					if (PPRRMapping.containsKey(j)) {
						String value = getCellFormatValue(row.getCell((short) j));
						//第二行第一列为空则返回空的array
						if ("fdPersonId".equals(PPRRMapping.get(j)) && i == 1 && StringUtil.isNull(value)) {
							return sheetDataList;
						}
						postPersonR.put(PPRRMapping.get(j), value);
						if (StringUtil.isNotNull(value)) {
							flag = true;
						}
					}else {
						continue;
					}
				}
				if (flag) {
					postPersonR.put("lineNum", i+1);
					sheetDataList.add(postPersonR);
				}
			}
		}
		return sheetDataList;
	}

	private List<SysOmsTempDept> getDeptModelList(JSONArray deptJsonArr,String trxId) {
		List<SysOmsTempDept> deptList = new ArrayList<>();
		SysOmsUtil util = new SysOmsUtil();
		SysOmsTempDept dept = null;
		JSONObject deptJsonObj = null;
		for (Object deptObj : deptJsonArr) {
			dept = new SysOmsTempDept();
			deptJsonObj = (JSONObject) deptObj;
			dept.setFdDeptId(deptJsonObj.getString("fdDeptId"));
			dept.setFdName(deptJsonObj.getString("fdName"));
			dept.setFdParentid(deptJsonObj.getString("fdParentid"));
			dept.setFdAlterTime(util.getTimestampByStr(deptJsonObj.getString("fdAlterTime")));
			dept.setFdCreateTime(new Date());
			if (StringUtil.isNull(deptJsonObj.getString("fdIsAvailable"))) {
				dept.setFdIsAvailable(null);
			}else {
				dept.setFdIsAvailable(getAvailable(deptJsonObj.getString("fdIsAvailable")));
			}
			if (StringUtil.isNotNull(deptJsonObj.getString("fdOrder"))) {
				dept.setFdOrder(Integer.valueOf(deptJsonObj.getString("fdOrder")));
			}else {
				dept.setFdOrder(null);
			}
			dept.setFdTrxId(trxId);
			deptList.add(dept);
		}
		return deptList;
	}

	private List<SysOmsTempPost> getPostModelMap(JSONArray postJsonArr,String trxId) {
		List<SysOmsTempPost> postList = new ArrayList<>();
		SysOmsUtil util = new SysOmsUtil();
		SysOmsTempPost post = null;
		JSONObject postJsonObj = null;
		for (Object postObj : postJsonArr) {
			post = new SysOmsTempPost();
			postJsonObj = (JSONObject) postObj;
			post.setFdPostId(postJsonObj.getString("fdPostId"));
			post.setFdName(postJsonObj.getString("fdName"));
			post.setFdAlterTime(util.getTimestampByStr(postJsonObj.getString("fdAlterTime")));
			post.setFdCreateTime(new Date());
			if(StringUtil.isNull(postJsonObj.getString("fdIsAvailable"))) {
				post.setFdIsAvailable(null);
			}else {
				post.setFdIsAvailable(getAvailable(postJsonObj.getString("fdIsAvailable")));
			}
			post.setFdParentid(postJsonObj.getString("fdParentid"));
			if (StringUtil.isNotNull(postJsonObj.getString("fdOrder"))) {
				post.setFdOrder(Integer.valueOf(postJsonObj.getString("fdOrder")));
			}else {
				post.setFdOrder(null);
			}
			post.setFdTrxId(trxId);
			postList.add(post);
		}
		return postList;
	}

	private List<SysOmsTempPerson> getPersonModelList(JSONArray personJsonArr,String trxId) {
		List<SysOmsTempPerson> personList = new ArrayList<>();
		SysOmsUtil util = new SysOmsUtil();
		SysOmsTempPerson person = null;
		JSONObject personJsonObj = null;
		for (Object personObj : personJsonArr) {
			person = new SysOmsTempPerson();
			personJsonObj = (JSONObject) personObj;
			person.setFdPersonId(personJsonObj.getString("fdPersonId"));
			person.setFdName(personJsonObj.getString("fdName"));
			person.setFdAlterTime(util.getTimestampByStr(personJsonObj.getString("fdAlterTime")));
			if (StringUtil.isNull(personJsonObj.getString("fdIsAvailable"))) {
				person.setFdIsAvailable(null);
			}else {
				person.setFdIsAvailable(getAvailable(personJsonObj.getString("fdIsAvailable")));
			}
			person.setFdParentid(personJsonObj.getString("fdParentid"));
			if (StringUtil.isNotNull(personJsonObj.getString("fdOrder"))) {
				person.setFdOrder(Integer.valueOf(personJsonObj.getString("fdOrder")));
			}else {
				person.setFdOrder(null);
			}
			person.setFdMobileNo(personJsonObj.getString("fdMobileNo"));
			person.setFdLoginName(personJsonObj.getString("fdLoginName"));
			person.setFdEmail(personJsonObj.getString("fdEmail"));
			person.setFdSex(personJsonObj.getString("fdSex"));
			person.setFdNo(personJsonObj.getString("fdNo"));
			person.setFdWorkPhone(personJsonObj.getString("fdWorkPhone"));
			person.setFdDesc(personJsonObj.getString("fdDesc"));
			//拓展字段
			JSONObject jsonObject = new JSONObject();
			if (!personExtendFile.isEmpty()) {
				Collection values = personExtendFile.values();    //获取Map集合的value集合
				for (Object object : values) {
					if(object == null) {
                        continue;
                    }
					jsonObject.put(object.toString(), personJsonObj.getString(object.toString()));
				}
			}
			person.setFdExtra(jsonObject.toString());
			person.setFdTrxId(trxId);
			personList.add(person);
		}
		personExtendFile.clear();
		return personList;
	}

	private List<SysOmsTempDp> getDeptPersonModelList(JSONArray deptPersonJsonArr,String trxId) {
		List<SysOmsTempDp> deptPersonList = new ArrayList<>();
		SysOmsUtil util = new SysOmsUtil();
		SysOmsTempDp deptPerson = null;
		JSONObject deptPersonJsonObj = null;
		for (Object deptPersonObj : deptPersonJsonArr) {
			deptPerson = new SysOmsTempDp();
			deptPersonJsonObj = (JSONObject) deptPersonObj;
			deptPerson.setFdPersonId(deptPersonJsonObj.getString("fdPersonId"));
			deptPerson.setFdDeptId(deptPersonJsonObj.getString("fdDeptId"));
			if (StringUtil.isNull(deptPersonJsonObj.getString("fdIsAvailable"))) {
				deptPerson.setFdIsAvailable(null);
			}else {
				deptPerson.setFdIsAvailable(getAvailable(deptPersonJsonObj.getString("fdIsAvailable")));
			}
			deptPerson.setFdAlterTime(util.getTimestampByStr(deptPersonJsonObj.getString("fdAlterTime")));
			if (StringUtil.isNotNull(deptPersonJsonObj.getString("fdOrder"))) {
				deptPerson.setFdOrder(Integer.valueOf(deptPersonJsonObj.getString("fdOrder")));
			}else {
				deptPerson.setFdOrder(null);
			}
			deptPerson.setFdTrxId(trxId);
			deptPersonList.add(deptPerson);
		}
		return deptPersonList;
	}

	private List<SysOmsTempPp> getPostPersonModelList(JSONArray postPersonJsonArr,String trxId) {
		List<SysOmsTempPp> postPersonList = new ArrayList<>();
		SysOmsUtil util = new SysOmsUtil();
		SysOmsTempPp postPerson = null;
		JSONObject postPersonJsonObj = null;
		for (Object postPersonObj : postPersonJsonArr) {
			postPerson = new SysOmsTempPp();
			postPersonJsonObj = (JSONObject) postPersonObj;
			postPerson.setFdPersonId(postPersonJsonObj.getString("fdPersonId"));
			postPerson.setFdPostId(postPersonJsonObj.getString("fdPostId"));
			if (StringUtil.isNull(postPersonJsonObj.getString("fdIsAvailable"))) {
				postPerson.setFdIsAvailable(null);
			}else {
				postPerson.setFdIsAvailable(getAvailable(postPersonJsonObj.getString("fdIsAvailable")));
			}
			postPerson.setFdAlterTime(util.getTimestampByStr(postPersonJsonObj.getString("fdAlterTime")));
			postPerson.setFdTrxId(trxId);
			postPersonList.add(postPerson);
		}
		return postPersonList;
	}





	/**
	 * 根据HSSFCell类型设置数据
	 *
	 * @param cell
	 * @return
	 */
	protected static String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
				// 如果当前Cell的Type为NUMERIC
				case NUMERIC:
					// 判断当前的cell是否为Date
					if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						if(String.valueOf(date).contains("00:00:00")){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							cellvalue = sdf.format(date);
						}else {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							cellvalue = sdf.format(date);
						}
					}
					// 如果是纯数字
					else {
						// 取得当前Cell的数值
						cellvalue = new BigDecimal(cell.getNumericCellValue()).toPlainString();
					}
					break;
				case FORMULA:
					cellvalue =  cell.getStringCellValue();
					break;
				// 如果当前Cell的Type为STRIN
				case STRING:
					// 取得当前的Cell字符串
					cellvalue = cell.getRichStringCellValue().getString();
					break;
				case BOOLEAN :
					cellvalue= Boolean.toString(cell.getBooleanCellValue());
					break;
				// 默认的Cell值
				default:
					cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	public String linkString(String leftStr, String linkStr, String rightStr) {
		if (StringUtils.isBlank(leftStr)) {
            return rightStr;
        }
		if (StringUtils.isBlank(rightStr)) {
            return leftStr;
        }
		return leftStr + linkStr + rightStr;
	}





	private Boolean getAvailable(String isAvaiable) {
		boolean isavaiable=false;
		if ("1".equals(isAvaiable) || "是".equals(isAvaiable)) {
			isavaiable= true;
		}
		return isavaiable;
	}

	@Override
	public JSONObject checkData(SysOmsExcelImportForm excelImportForm) throws Exception {
		JSONArray deptJsonArray = new JSONArray();
		JSONArray postJsonArray = new JSONArray();
		JSONArray personJsonArray = new JSONArray();
		JSONArray personDeptJsonArray = new JSONArray();
		JSONArray personPostJsonArray = new JSONArray();
		//先获取excel 里面的数据
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(excelImportForm.getFile().getInputStream());

			int sheets = wb.getNumberOfSheets();
			if (sheets < 1) {
				logger.error("Excel模版错误");
			} else {
				for (int i = 1; i <= sheets; i++) {
					Sheet sheet = wb.getSheetAt(i - 1);
					String sheetName = sheet.getSheetName();
					if ("部门信息".equals(sheetName)) {
						//部门
						deptJsonArray = parseDeptSheetData(sheet);
					} else if ("岗位信息".equals(sheetName)) {
						//岗位
						postJsonArray = parsePostSheetData(sheet);
					} else if ("人员信息".equals(sheetName)) {
						//人员
						personJsonArray = parsePersonSheetData(sheet);
					} else if ("人员部门关系信息".equals(sheetName)) {
						//部门人员关系
						personDeptJsonArray = parseDPRSheetData(sheet);
					} else if ("人员岗位关系信息".equals(sheetName)) {
						//岗位人员关系
						personPostJsonArray = parsePPRSheetData(sheet);
					}
				}
			}
			logger.info("部门列表数据：" + deptJsonArray.size());
			logger.info("岗位列表数据：" + postJsonArray.size());
			logger.info("人员列表数据：" + personJsonArray.size());
			logger.info("部门人员列表数据：" + personDeptJsonArray.size());
			logger.info("岗位人员列表数据：" + personPostJsonArray.size());
			// 先根据数据判断模式
			int modelType = getModelType(deptJsonArray, postJsonArray, personJsonArray, personDeptJsonArray, personPostJsonArray);
			if (modelType == 0) {
				logger.error("模版数据未匹配到对应模式");
				throw new Exception("模版数据未匹配到对应模式");
			}
			// 开始检测数据
			JSONObject fileData = new JSONObject();
			JSONArray deptfileDataarry = getdeptFileData(deptJsonArray);
			fileData.put("deptFileData", deptfileDataarry);
			JSONArray postfileDataarry = getpostFileData(postJsonArray);
			fileData.put("postFileData", postfileDataarry);
			JSONArray personfileDataarry = getpersonFileData(personJsonArray);
			fileData.put("personFileData", personfileDataarry);
			JSONArray persondeptfileDataarry = getpersondeptFileData(personDeptJsonArray);
			fileData.put("personDeptFileData", persondeptfileDataarry);
			JSONArray personpostfileDataarry = getpersonpostFileData(personPostJsonArray);
			fileData.put("personPostFileData", personpostfileDataarry);
			fileData.put("modelType", modelType);
			logger.debug("Excel上传检查数据结果数据量共：" + fileData.toJSONString());
			return fileData;
		}catch (Exception e){
			throw e;
		}finally {
			if(wb!=null){
				wb.close();
			}
		}
	}

	private JSONArray getdeptFileData(JSONArray deptJsonArr) {
		SysOmsUtil util = new SysOmsUtil();
		JSONArray deptFileJsonList = new JSONArray();
		JSONObject deptJsonObj = null;
		JSONObject deptIdJson = null;
		JSONObject deptNameJson = null;
		JSONObject alterTimeJson = null;
		JSONObject isAvailableJson = null;
		JSONObject parentidJson = null;
		JSONObject orderJson = null;
		boolean isSuc = false;
		for (Object deptObj : deptJsonArr) {
			deptJsonObj = (JSONObject) deptObj;
			isSuc = true;
			//判断部门ID字段
			deptIdJson = new JSONObject();
			deptIdJson.put("value", deptJsonObj.getString("fdDeptId"));
			deptIdJson.put("errMsg", "");
			deptIdJson.put("flag", true);
			deptJsonObj.put("fdDeptId", deptIdJson);
			if (StringUtil.isNull(deptIdJson.getString("value"))) {
				isSuc = false;
				deptIdJson.put("errMsg", "不能为空");
				deptIdJson.put("flag", false);
				deptJsonObj.put("fdDeptId", deptIdJson);
			}
			deptNameJson = new JSONObject();
			deptNameJson.put("value", deptJsonObj.getString("fdName"));
			deptNameJson.put("errMsg", "");
			deptNameJson.put("flag", true);
			deptJsonObj.put("fdName", deptNameJson);
			if(StringUtil.isNull(deptNameJson.getString("value"))){
				isSuc = false;
				deptNameJson.put("errMsg", "不能为空");
				deptNameJson.put("flag", false);
				deptJsonObj.put("fdName", deptNameJson);
			}
			alterTimeJson = new JSONObject();
			alterTimeJson.put("value", deptJsonObj.getString("fdAlterTime"));
			alterTimeJson.put("errMsg", "");
			alterTimeJson.put("flag", true);
			deptJsonObj.put("fdAlterTime", alterTimeJson);
			if(StringUtil.isNull(alterTimeJson.getString("value"))){
				isSuc = false;
				alterTimeJson.put("errMsg", "不能为空");
				alterTimeJson.put("flag", false);
				deptJsonObj.put("fdAlterTime", alterTimeJson);
			}else {
				Long time = util.getTimestampByStr(alterTimeJson.getString("value"));
				if (time == null) {
					isSuc = false;
					alterTimeJson.put("errMsg", "时间格式不正确");
					alterTimeJson.put("flag", false);
					deptJsonObj.put("fdAlterTime", alterTimeJson);
				}
			}
			isAvailableJson = new JSONObject();
			isAvailableJson.put("value", deptJsonObj.getString("fdIsAvailable"));
			isAvailableJson.put("errMsg", "");
			isAvailableJson.put("flag", true);
			deptJsonObj.put("fdIsAvailable", isAvailableJson);
			if(StringUtil.isNull(isAvailableJson.getString("value"))){
				isSuc = false;
				isAvailableJson.put("errMsg", "不能为空");
				isAvailableJson.put("flag", false);
				deptJsonObj.put("fdIsAvailable", isAvailableJson);
			}else {
				if (!"1".equals(isAvailableJson.getString("value")) && !"0".equals(isAvailableJson.getString("value"))) {
					isSuc = false;
					isAvailableJson.put("errMsg", "值为1或0");
					isAvailableJson.put("flag", false);
					deptJsonObj.put("fdIsAvailable", isAvailableJson);
				}
			}
			parentidJson = new JSONObject();
			parentidJson.put("value", deptJsonObj.getString("fdParentid"));
			parentidJson.put("errMsg", "");
			parentidJson.put("flag", true);
			deptJsonObj.put("fdParentid", parentidJson);
			orderJson = new JSONObject();
			orderJson.put("value", deptJsonObj.getString("fdOrder"));
			orderJson.put("errMsg", "");
			orderJson.put("flag", true);
			deptJsonObj.put("fdOrder", orderJson);
			if (!isSuc) {
				deptFileJsonList.add(deptJsonObj);
			}
		}
		return deptFileJsonList;
	}

	private JSONArray getpostFileData(JSONArray postJsonArr) {
		SysOmsUtil util = new SysOmsUtil();
		JSONArray postFileJsonList = new JSONArray();
		JSONObject postJsonObj= null;
		JSONObject postIdObj= null;
		JSONObject nameObj= null;
		JSONObject alterTimeObj= null;
		JSONObject parentidObj= null;
		JSONObject orderObj= null;
		JSONObject isAvailableObj= null;
		boolean isSuc = false;
		for (Object postObj : postJsonArr) {
			postJsonObj = (JSONObject) postObj;
			isSuc = true;
			postIdObj = new JSONObject();
			postIdObj.put("value", postJsonObj.getString("fdPostId"));
			postIdObj.put("errMsg", "");
			postIdObj.put("flag", true);
			postJsonObj.put("fdPostId", postIdObj);
			if (StringUtil.isNull(postIdObj.getString("value"))) {
				isSuc = false;
				postIdObj.put("errMsg", "不能为空");
				postIdObj.put("flag", false);
				postJsonObj.put("fdPostId", postIdObj);
			}

			nameObj = new JSONObject();
			nameObj.put("value", postJsonObj.getString("fdName"));
			nameObj.put("errMsg", "");
			nameObj.put("flag", true);
			postJsonObj.put("fdName", nameObj);
			if (StringUtil.isNull(nameObj.getString("value"))) {
				isSuc = false;
				nameObj.put("errMsg", "不能为空");
				nameObj.put("flag", false);
				postJsonObj.put("fdName", nameObj);
			}

			alterTimeObj = new JSONObject();
			alterTimeObj.put("value", postJsonObj.getString("fdAlterTime"));
			alterTimeObj.put("errMsg", "");
			alterTimeObj.put("flag", true);
			postJsonObj.put("fdAlterTime", alterTimeObj);
			if (StringUtil.isNull(alterTimeObj.getString("value"))) {
				isSuc = false;
				alterTimeObj.put("errMsg", "不能为空");
				alterTimeObj.put("flag", false);
				postJsonObj.put("fdAlterTime", alterTimeObj);
			}else {
				Long time = util.getTimestampByStr(alterTimeObj.getString("value"));
				if (time == null) {
					isSuc = false;
					alterTimeObj.put("errMsg", "时间格式不正确");
					alterTimeObj.put("flag", false);
					postJsonObj.put("fdAlterTime", alterTimeObj);
				}
			}

			isAvailableObj = new JSONObject();
			isAvailableObj.put("value", postJsonObj.getString("fdIsAvailable"));
			isAvailableObj.put("errMsg", "");
			isAvailableObj.put("flag", true);
			postJsonObj.put("fdIsAvailable", isAvailableObj);
			if (StringUtil.isNull(isAvailableObj.getString("value"))) {
				isSuc = false;
				isAvailableObj.put("errMsg", "不能为空");
				isAvailableObj.put("flag", false);
				postJsonObj.put("fdIsAvailable", isAvailableObj);
			}else {
				if (!"1".equals(isAvailableObj.getString("value")) && !"0".equals(isAvailableObj.getString("value"))) {
					isSuc = false;
					isAvailableObj.put("errMsg", "值为1或0");
					isAvailableObj.put("flag", false);
					postJsonObj.put("fdIsAvailable", isAvailableObj);
				}
			}
			if (!isSuc) {
				parentidObj = new JSONObject();
				parentidObj.put("value", postJsonObj.getString("fdParentid"));
				parentidObj.put("errMsg", "");
				parentidObj.put("flag", true);
				postJsonObj.put("fdParentid", parentidObj);

				orderObj = new JSONObject();
				orderObj.put("value", postJsonObj.getString("fdOrder"));
				orderObj.put("errMsg", "");
				orderObj.put("flag", true);
				postJsonObj.put("fdOrder", orderObj);
				postFileJsonList.add(postJsonObj);
			}
		}
		return postFileJsonList;
	}

	private JSONArray getpersonFileData(JSONArray personJsonArr) {
		SysOmsUtil util = new SysOmsUtil();
		JSONArray personFileJsonList = new JSONArray();
		JSONObject personJsonObj = null;
		JSONObject personIdObj = null;
		JSONObject nameObj = null;
		JSONObject alterTimeObj = null;
		JSONObject isAvailableObj = null;
		JSONObject parentidObj = null;
		JSONObject orderObj = null;
		JSONObject mobileNoObj = null;
		JSONObject loginNameObj = null;
		JSONObject emailObj = null;
		JSONObject sexObj = null;
		boolean isSuc = false;
		for (Object personObj : personJsonArr) {
			personJsonObj = (JSONObject) personObj;
			isSuc = true;
			personIdObj = new JSONObject();
			personIdObj.put("value", personJsonObj.getString("fdPersonId"));
			personIdObj.put("errMsg", "");
			personIdObj.put("flag", true);
			personJsonObj.put("fdPersonId", personIdObj);
			if (StringUtil.isNull(personIdObj.getString("value"))) {
				isSuc = false;
				personIdObj.put("errMsg", "不能为空");
				personIdObj.put("flag", false);
				personJsonObj.put("fdPersonId", personIdObj);
			}
			nameObj = new JSONObject();
			nameObj.put("value", personJsonObj.getString("fdName"));
			nameObj.put("errMsg", "");
			nameObj.put("flag", true);
			personJsonObj.put("fdName", nameObj);
			if (StringUtil.isNull(nameObj.getString("value"))) {
				isSuc = false;
				nameObj.put("errMsg", "不能为空");
				nameObj.put("flag", false);
				personJsonObj.put("fdName", nameObj);
			}
			alterTimeObj = new JSONObject();
			alterTimeObj.put("value", personJsonObj.getString("fdAlterTime"));
			alterTimeObj.put("errMsg", "");
			alterTimeObj.put("flag", true);
			personJsonObj.put("fdAlterTime", alterTimeObj);
			if (StringUtil.isNull(alterTimeObj.getString("value"))) {
				isSuc = false;
				alterTimeObj.put("errMsg", "不能为空");
				alterTimeObj.put("flag", false);
				personJsonObj.put("fdAlterTime", alterTimeObj);
			}else {
				Long time = util.getTimestampByStr(alterTimeObj.getString("value"));
				if (time == null) {
					isSuc = false;
					alterTimeObj.put("errMsg", "时间格式不正确");
					alterTimeObj.put("flag", false);
					personJsonObj.put("fdAlterTime", alterTimeObj);
				}
			}
			loginNameObj = new JSONObject();
			loginNameObj.put("value", personJsonObj.getString("fdLoginName"));
			loginNameObj.put("errMsg", "");
			loginNameObj.put("flag", true);
			personJsonObj.put("fdLoginName", loginNameObj);
			if (StringUtil.isNull(loginNameObj.getString("value"))) {
				isSuc = false;
				loginNameObj.put("errMsg", "不能为空");
				loginNameObj.put("flag", false);
				personJsonObj.put("fdLoginName", loginNameObj);
			}

			isAvailableObj = new JSONObject();
			isAvailableObj.put("value", personJsonObj.getString("fdIsAvailable"));
			isAvailableObj.put("errMsg", "");
			isAvailableObj.put("flag", true);
			personJsonObj.put("fdIsAvailable", isAvailableObj);
			if (StringUtil.isNull(isAvailableObj.getString("value"))) {
				isSuc = false;
				isAvailableObj.put("errMsg", "不能为空");
				isAvailableObj.put("flag", false);
				personJsonObj.put("fdIsAvailable", isAvailableObj);
			}else {
				if (!"1".equals(isAvailableObj.getString("value")) && !"0".equals(isAvailableObj.getString("value"))) {
					isSuc = false;
					isAvailableObj.put("errMsg", "值为1或0");
					isAvailableObj.put("flag", false);
					personJsonObj.put("fdIsAvailable", isAvailableObj);
				}
			}

			sexObj = new JSONObject();
			sexObj.put("value", personJsonObj.getString("fdSex"));
			sexObj.put("errMsg", "");
			sexObj.put("flag", true);
			personJsonObj.put("fdSex", sexObj);
			if (StringUtil.isNotNull(sexObj.getString("value"))) {
				if (!"1".equals(sexObj.getString("value")) && !"0".equals(sexObj.getString("value")) && !"男".equals(sexObj.getString("value")) && !"女".equals(sexObj.getString("value")) ) {
					isSuc = false;
					sexObj.put("errMsg", "值为男/女，1/0");
					sexObj.put("flag", false);
					personJsonObj.put("fdSex", sexObj);
				}
			}

			//校验人员的推展字段
			JSONArray extandFieldarr = new JSONArray();
			Map<String, JSONObject> extandFieldmap = SysOmsExcelUtil.getEKPDynamicAttributeChecked();
			if (!extandFieldmap.isEmpty()) {
				for(String key : extandFieldmap.keySet()){
					JSONObject extand = new JSONObject();
					extand.put("value", personJsonObj.getString(key));
					extand.put("errMsg", "");
					extand.put("flag", true);
					JSONObject value = extandFieldmap.get(key);
					extand.put("name", value.getString("name"));
					if(StringUtil.isNull(extand.getString("value")) && "true".equals(value.getString("req"))) {
						isSuc = false;
						extand.put("errMsg", "不能为空");
						extand.put("flag", false);
					}else if (StringUtil.isNotNull(extand.getString("value"))) {
						if ("radio".equals(value.getString("display"))  || "select".equals(value.getString("display"))) {
							if (!value.getString("denum").contains(extand.getString("value"))) {
								isSuc = false;
								extand.put("errMsg", "值为："+value.getString("denum"));
								extand.put("flag", false);
							}
						}else if ("checkbox".equals(value.getString("display"))) {
							if (!value.getString("denum").contains(extand.getString("value"))) {
								isSuc = false;
								extand.put("errMsg", "数据或格式不对");
								extand.put("flag", false);
							}
						}else if ("time".equals(value.getString("display"))) { //时间
							if(!isValidDate(extand.getString("value"),"HH:mm")){
								isSuc = false;
								extand.put("errMsg", "类型为时间，格式必须为：HH:mm");
								extand.put("flag", false);
							}
						}else if ("date".equals(value.getString("display"))) {//日期
							if(isValidDate(extand.getString("value"),"yyyy-MM-dd") || isValidDate(extand.getString("value"),"yyyy/MM/dd") || isValidDate(extand.getString("value"),"yyyy年MM月dd日")){

							}else {
								isSuc = false;
								extand.put("errMsg", "类型为日期，格式必须为：yyyy-MM-dd");
								extand.put("flag", false);
							}
						}else if ("datetime".equals(value.getString("display"))) {//日期时间
							if(!isValidDate(extand.getString("value"),"yyyy-MM-dd HH:mm") || !isValidDate(extand.getString("value"),"yyyy/MM/dd HH:mm") || !isValidDate(extand.getString("value"),"yyyy年MM月dd日 HH:mm")){
								isSuc = false;
								extand.put("errMsg", "类型为日期时间，格式必须为：yyyy-MM-dd HH:mm");
								extand.put("flag", false);
							}
						}else if ("text".equals(value.getString("display")) && "java.lang.Integer".equals(value.getString("fieldType"))) {//整型
							if (!isNumeric(extand.getString("value"))) {
								isSuc = false;
								extand.put("errMsg", "类型为整型");
								extand.put("flag", false);
							}
						}else if ("text".equals(value.getString("display")) && "java.lang.Double".equals(value.getString("fieldType"))) {
							if (!isDoubleOrFloat(extand.getString("value"))) {
								isSuc = false;
								extand.put("errMsg", "类型为浮点");
								extand.put("flag", false);
							}
						}
					}
					extandFieldarr.add(extand);
				}

			}
			personJsonObj.put("extandFieldarr", extandFieldarr);
			if (!isSuc) {
				parentidObj = new JSONObject();
				parentidObj.put("value", personJsonObj.getString("fdParentid"));
				parentidObj.put("errMsg", "");
				parentidObj.put("flag", true);
				personJsonObj.put("fdParentid", parentidObj);

				orderObj = new JSONObject();
				orderObj.put("value", personJsonObj.getString("fdOrder"));
				orderObj.put("errMsg", "");
				orderObj.put("flag", true);
				personJsonObj.put("fdOrder", orderObj);

				mobileNoObj = new JSONObject();
				mobileNoObj.put("value", personJsonObj.getString("fdMobileNo"));
				mobileNoObj.put("errMsg", "");
				mobileNoObj.put("flag", true);
				personJsonObj.put("fdMobileNo", mobileNoObj);

				emailObj = new JSONObject();
				emailObj.put("value", personJsonObj.getString("fdEmail"));
				emailObj.put("errMsg", "");
				emailObj.put("flag", true);
				personJsonObj.put("fdEmail", emailObj);


				personFileJsonList.add(personJsonObj);
			}
		}
		return personFileJsonList;
	}


	private JSONArray getpersondeptFileData(JSONArray personDeptJsonArr) {
		JSONArray personDeptFileJsonList = new JSONArray();
		JSONObject personDeptJsonObj = null;
		JSONObject personIdObj = null;
		JSONObject deptIdJson = null;
		JSONObject isAvailableObj = null;
		JSONObject alterTimeObj = null;
		JSONObject orderObj = null;
		boolean isSuc = false;
		for (Object personDeptObj : personDeptJsonArr) {
			isSuc = true;
			personDeptJsonObj = (JSONObject) personDeptObj;
			personIdObj = new JSONObject();
			personIdObj.put("value", personDeptJsonObj.getString("fdPersonId"));
			personIdObj.put("errMsg", "");
			personIdObj.put("flag", true);
			personDeptJsonObj.put("fdPersonId", personIdObj);
			if (StringUtil.isNull(personIdObj.getString("value"))) {
				isSuc = false;
				personIdObj.put("errMsg", "不能为空");
				personIdObj.put("flag", false);
				personDeptJsonObj.put("fdPersonId", personIdObj);
			}
			deptIdJson = new JSONObject();
			deptIdJson.put("value", personDeptJsonObj.getString("fdDeptId"));
			deptIdJson.put("errMsg", "");
			deptIdJson.put("flag", true);
			personDeptJsonObj.put("fdDeptId", deptIdJson);
			if (StringUtil.isNull(deptIdJson.getString("value"))) {
				isSuc = false;
				deptIdJson.put("errMsg", "不能为空");
				deptIdJson.put("flag", false);
				personDeptJsonObj.put("fdDeptId", deptIdJson);
			}
			isAvailableObj = new JSONObject();
			isAvailableObj.put("value", personDeptJsonObj.getString("fdIsAvailable"));
			isAvailableObj.put("errMsg", "");
			isAvailableObj.put("flag", true);
			personDeptJsonObj.put("fdIsAvailable", isAvailableObj);
//			if (StringUtil.isNull(isAvailableObj.getString("value"))) {
//				isSuc = false;
//				isAvailableObj.put("errMsg", "不能为空");
//				isAvailableObj.put("flag", false);
//				personDeptJsonObj.put("fdIsAvailable", isAvailableObj);
//			}else {
//				if (!"1".equals(isAvailableObj.getString("value")) && !"0".equals(isAvailableObj.getString("value"))) {
//					isSuc = false;
//					isAvailableObj.put("errMsg", "值为1或0");
//					isAvailableObj.put("flag", false);
//					personDeptJsonObj.put("fdIsAvailable", isAvailableObj);
//				}
//			}
			if (!isSuc) {
				alterTimeObj = new JSONObject();
				alterTimeObj.put("value", personDeptJsonObj.getString("fdAlterTime"));
				alterTimeObj.put("errMsg", "");
				alterTimeObj.put("flag", true);
				personDeptJsonObj.put("fdAlterTime", alterTimeObj);

				orderObj = new JSONObject();
				orderObj.put("value", personDeptJsonObj.getString("fdOrder"));
				orderObj.put("errMsg", "");
				orderObj.put("flag", true);
				personDeptJsonObj.put("fdOrder", orderObj);
				personDeptFileJsonList.add(personDeptJsonObj);
			}
		}
		return personDeptFileJsonList;
	}

	private JSONArray getpersonpostFileData(JSONArray personPostJsonArr) {
		JSONArray personPostFileJsonList = new JSONArray();
		JSONObject personPostJsonObj = null;
		JSONObject personIdObj = null;
		JSONObject postIdObj = null;
		JSONObject isAvailableObj = null;
		JSONObject orderObj = null;
		JSONObject alterTimeObj = null;
		boolean isSuc = false;
		for (Object psrsonPostObj : personPostJsonArr) {
			personPostJsonObj = (JSONObject) psrsonPostObj;
			isSuc = true;
			personIdObj = new JSONObject();
			personIdObj.put("value", personPostJsonObj.getString("fdPersonId"));
			personIdObj.put("errMsg", "");
			personIdObj.put("flag", true);
			personPostJsonObj.put("fdPersonId", personIdObj);
			if (StringUtil.isNull(personIdObj.getString("value"))) {
				isSuc = false;
				personIdObj.put("errMsg", "不能为空");
				personIdObj.put("flag", false);
				personPostJsonObj.put("fdPersonId", personIdObj);
			}
			postIdObj = new JSONObject();
			postIdObj.put("value", personPostJsonObj.getString("fdPostId"));
			postIdObj.put("errMsg", "");
			postIdObj.put("flag", true);
			personPostJsonObj.put("fdPostId", postIdObj);
			if (StringUtil.isNull(postIdObj.getString("value"))) {
				isSuc = false;
				postIdObj.put("errMsg", "不能为空");
				postIdObj.put("flag", false);
				personPostJsonObj.put("fdPostId", postIdObj);
			}

			isAvailableObj = new JSONObject();
			isAvailableObj.put("value", personPostJsonObj.getString("fdIsAvailable"));
			isAvailableObj.put("errMsg", "");
			isAvailableObj.put("flag", true);
			personPostJsonObj.put("fdIsAvailable", isAvailableObj);
//			if (StringUtil.isNull(isAvailableObj.getString("value"))) {
//				isSuc = false;
//				isAvailableObj.put("errMsg", "不能为空");
//				isAvailableObj.put("flag", false);
//				personPostJsonObj.put("fdIsAvailable", isAvailableObj);
//			}else {
//				if (!"1".equals(isAvailableObj.getString("value")) && !"0".equals(isAvailableObj.getString("value"))) {
//					isSuc = false;
//					isAvailableObj.put("errMsg", "值为1或0");
//					isAvailableObj.put("flag", false);
//					personPostJsonObj.put("fdIsAvailable", isAvailableObj);
//				}
//			}
			if (!isSuc) {
				orderObj = new JSONObject();
				orderObj.put("value", personPostJsonObj.getString("fdOrder"));
				orderObj.put("errMsg", "");
				orderObj.put("flag", true);
				personPostJsonObj.put("fdOrder", orderObj);

				alterTimeObj = new JSONObject();
				alterTimeObj.put("value", personPostJsonObj.getString("fdAlterTime"));
				alterTimeObj.put("errMsg", "");
				alterTimeObj.put("flag", true);
				personPostJsonObj.put("fdAlterTime", alterTimeObj);

				personPostFileJsonList.add(personPostJsonObj);
			}
		}
		return personPostFileJsonList;
	}

	private int getModelType(JSONArray deptJsonArr,JSONArray postJsonArr,JSONArray personJsonArr,JSONArray personDeptJsonArr,JSONArray personPostJsonArr) throws Exception {
		// 模式4 部门 人员 岗位 部门人员关系 岗位人员关系
		if (!deptJsonArr.isEmpty() && !personJsonArr.isEmpty() && !postJsonArr.isEmpty() && !personDeptJsonArr.isEmpty()
				&& !personPostJsonArr.isEmpty()) {
			return OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue();
			// 模式3 部门 岗位 人员 岗位人员关系
		} else if (!deptJsonArr.isEmpty() && !personJsonArr.isEmpty() && !postJsonArr.isEmpty()
				&& !personPostJsonArr.isEmpty() && personDeptJsonArr.isEmpty()) {
			return OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue();
			//判断人员岗位关系是否全量
			// 模式2 部门 人员 部门人员关系
		} else if (!deptJsonArr.isEmpty() && !personJsonArr.isEmpty() && !personDeptJsonArr.isEmpty()
				&& postJsonArr.isEmpty() && personPostJsonArr.isEmpty()) {
			return OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue();
		} else if (!deptJsonArr.isEmpty() && !personJsonArr.isEmpty() && postJsonArr.isEmpty()
				&& personDeptJsonArr.isEmpty() && personPostJsonArr.isEmpty()) {
			return OmsTempSynModel.OMS_TEMP_SYN_MODEL_100.getValue();
		}else {
			return 0;
		}
	}


	public static boolean isValidDate(String str,String formatType) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(formatType);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	//是否为整型
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	public static boolean isDoubleOrFloat(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}



	/**
	 * 构建部门数据
	 */
	private List<SysOmsTempDept> buildDeptList(int newDeptNum,String identy) {
		//构建部门
		List<SysOmsTempDept> tempDepts = new ArrayList<SysOmsTempDept>();
		//本次构建部门每个层级部门总数，按照下标大小分别为1级部门数量，2级部门数据，3级部门数量，4级部门数量，5级别部门数量
		//多余的部门全部放在6级部门中
		int deptLevelNum[] = {3,30,300,2000,10000};
		SysOmsTempDept sysOmsTempDept = new SysOmsTempDept();
		List<SysOmsTempDept> dept1 = new ArrayList<SysOmsTempDept>();
		List<SysOmsTempDept> dept2 = new ArrayList<SysOmsTempDept>();
		List<SysOmsTempDept> dept3 = new ArrayList<SysOmsTempDept>();
		List<SysOmsTempDept> dept4 = new ArrayList<SysOmsTempDept>();
		List<SysOmsTempDept> dept5 = new ArrayList<SysOmsTempDept>();
		for (int i = 0; i < newDeptNum; i++) {
			sysOmsTempDept = new SysOmsTempDept();
			sysOmsTempDept.setFdOrder(i);
			if(i < deptLevelNum[0]) {
				//构建1级别部门
				sysOmsTempDept.setFdName(identy+"一级部门"+i);
				sysOmsTempDept.setFdParentid(null);
				dept1.add(sysOmsTempDept);
			}else if(i < deptLevelNum[0] + deptLevelNum[1]){
				//构建2级别部门
				//父部门在上级部门中随机选一个
				int parentIdIndex = new Random().nextInt(dept1.size());
				SysOmsTempDept parentDept = dept1.get(parentIdIndex);
				String parentId = parentDept.getFdId();
				sysOmsTempDept.setFdName(identy+"二级部门"+i);
				sysOmsTempDept.setFdParentid(parentId);
				dept2.add(sysOmsTempDept);
			}else if(i < deptLevelNum[0] + deptLevelNum[1] + deptLevelNum[2]) {
				//构建3级别部门
				//父部门在上级部门中随机选一个
				int parentIdIndex = new Random().nextInt(dept2.size());
				SysOmsTempDept parentDept = dept2.get(parentIdIndex);
				String parentId = parentDept.getFdId();
				sysOmsTempDept.setFdName(identy+"三级部门"+i);
				sysOmsTempDept.setFdParentid(parentId);
				dept3.add(sysOmsTempDept);
			}else if(i < deptLevelNum[0] + deptLevelNum[1] + deptLevelNum[2]+ deptLevelNum[3]) {
				//构建4级别部门
				//父部门在上级部门中随机选一个
				int parentIdIndex = new Random().nextInt(dept3.size());
				SysOmsTempDept parentDept = dept3.get(parentIdIndex);
				String parentId = parentDept.getFdId();
				sysOmsTempDept.setFdName(identy+"四级部门"+i);
				sysOmsTempDept.setFdParentid(parentId);
				dept4.add(sysOmsTempDept);
			}else if( i < deptLevelNum[0] + deptLevelNum[1] + deptLevelNum[2] + deptLevelNum[3] + deptLevelNum[4]) {
				//构建5级别部门
				//父部门在上级部门中随机选一个
				int parentIdIndex = new Random().nextInt(dept4.size());
				SysOmsTempDept parentDept = dept4.get(parentIdIndex);
				String parentId = parentDept.getFdId();
				sysOmsTempDept.setFdName(identy+"五级部门"+i);
				sysOmsTempDept.setFdParentid(parentId);
				dept5.add(sysOmsTempDept);
			}else {
				//其他部门放进6级别部门中
				int parentIdIndex = new Random().nextInt(dept5.size());
				SysOmsTempDept parentDept = dept5.get(parentIdIndex);
				String parentId = parentDept.getFdId();
				sysOmsTempDept.setFdName(identy+"六级部门"+i);
				sysOmsTempDept.setFdParentid(parentId);
			}
			tempDepts.add(sysOmsTempDept);

		}
		return tempDepts;
	}

	/**
	 * 构建人员数据
	 */
	private List<SysOmsTempPerson> buildPersonList(List<SysOmsTempDept> deptList,int newPersonNum,String identy) {
		//构建部门
		List<SysOmsTempPerson> tempPersons = new ArrayList<SysOmsTempPerson>();
		SysOmsTempPerson sysOmsTempPerson = new SysOmsTempPerson();
		//先构建
		List<String> phoneNumList = new ArrayList<String>();
		while (phoneNumList.size() < newPersonNum) {
			String phone = getRandomPhone();
			if(!phoneNumList.contains(phone)) {
				phoneNumList.add(phone);
			}
		}
		for (int i = 0; i < newPersonNum; i++) {
			sysOmsTempPerson = new SysOmsTempPerson();
			sysOmsTempPerson.setFdOrder(i);
			sysOmsTempPerson.setFdName(identy+"人员"+i);
			sysOmsTempPerson.setFdLoginName(identy+"test"+i);
			sysOmsTempPerson.setFdMobileNo(phoneNumList.get(i));
			sysOmsTempPerson.setFdParentid(deptList.get(new Random().nextInt(deptList.size())).getFdId());
			tempPersons.add(sysOmsTempPerson);
		}
		return tempPersons;
	}

	/**
	 * 随机手机号
	 * @return
	 */
	private String getRandomPhone(){
		int [] arr = {
				134,135,136,137,138,139,147,148,150,151,152,157,158,159,165,172,178,182,183,184,187,188,198,
				130,131,132,145,146,155,156,166,171,175,176,185,186,
				133,149,153,173,174,177,180,181,189,199,170
		};
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(arr[getRandom(0,arr.length-1)]);
		stringBuffer.append(getRandom(1000,9999));
		stringBuffer.append(getRandom(1000,9999));
		return stringBuffer.toString();
	}

	/**
	 * 获取一定范围内的随机数[min,max]
	 * @param min
	 * @param max
	 * @return
	 */
	private int getRandom(int min,int max){
		return new Random().nextInt(max - min + 1) + min;
	}

	/**
	 * 构建人员sheet
	 * @param excelUtil
	 * @param wb
	 * @param deptList
	 * @return 返回部门人员关系列表 数据格式：人员ID:部门ID:排序号
	 * @throws Exception
	 */
	private List<SysOmsTempPerson> buildPersonSheet(SysOmsExcelUtil excelUtil,HSSFWorkbook wb,List<SysOmsTempDept> deptList,String altertime,int newPersonNum,String identy) throws Exception{
		HSSFRow row = null;
		HSSFCell cell = null;
		int rowIndex = 1;
		long userStarttime = System.currentTimeMillis();
		logger.info("开始加载人员");
		HSSFSheet sheet2 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet3"));
		excelUtil.buildTemplate(wb, sheet2, SysOmsExcelUtil.EXCEL_SHEET_PERSON);
		List<SysOmsTempPerson> tempPersonList = buildPersonList(deptList,newPersonNum,identy);
		for (SysOmsTempPerson person : tempPersonList) {
			row = sheet2.createRow(rowIndex++);
			for (int j = 0; j < 12; j++) {
				cell = row.createCell(j);
				if(j  == 0){
					cell.setCellValue(person.getFdId()); //用户ID
				}else if(j == 1){
					cell.setCellValue(person.getFdName()); //人员名称
				}else if(j == 2){
					cell.setCellValue(altertime); //修改时间
				}else if(j == 3){
					cell.setCellValue(1);//是否有效
				}else if(j == 4){
					cell.setCellValue(person.getFdLoginName());//登录名
				}else if(j == 5){
					cell.setCellValue(person.getFdParentid());//主部门ID
				}else if(j == 6){
					cell.setCellValue(person.getFdOrder());//人员在主部门的排序号
				}else if(j == 7){
					cell.setCellValue(person.getFdMobileNo()); //手机号
				}else if(j == 8){
					cell.setCellValue(person.getFdEmail()); //email
				}else if(j == 9){
					cell.setCellValue("");//性别
				}else if(j == 10){
					cell.setCellValue(person.getFdNo());//工号
				}else if(j ==11){
					cell.setCellValue("");//分机号
				}else if(j ==12){
					cell.setCellValue("");//描述
				}

			}
		}
		logger.info("加载人员结束，总共耗时："+ (System.currentTimeMillis() - userStarttime )+"ms");
		return tempPersonList;

	}

	/**
	 * 构建人员数据
	 */
	private List<SysOmsTempPost> buildPostList(List<SysOmsTempDept> deptList,int newPostNum,String identy) {
		//构建部门
		List<SysOmsTempPost> tempPosts = new ArrayList<SysOmsTempPost>();
		SysOmsTempPost sysOmsTempPost = null;
		for (int i = 0; i < newPostNum; i++) {
			sysOmsTempPost = new SysOmsTempPost();
			sysOmsTempPost.setFdName(identy+"岗位"+i);
			sysOmsTempPost.setFdOrder(i);
			sysOmsTempPost.setFdParentid(deptList.get(new Random().nextInt(deptList.size())).getFdId());
			tempPosts.add(sysOmsTempPost);
		}
		return tempPosts;
	}

	/**
	 * 构建人员sheet
	 * @param excelUtil
	 * @param wb
	 * @param deptList
	 * @return 返回部门人员关系列表 数据格式：人员ID:部门ID:排序号
	 * @throws Exception
	 */
	private List<SysOmsTempPost> buildPostSheet(SysOmsExcelUtil excelUtil,HSSFWorkbook wb,List<SysOmsTempDept> deptList,String altertime,int newPostNum,String identy) throws Exception{
		HSSFRow row = null;
		HSSFCell cell = null;
		int rowIndex = 1;
		long userStarttime = System.currentTimeMillis();
		logger.info("开始加载人员");
		HSSFSheet sheet2 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet2"));
		excelUtil.buildTemplate(wb, sheet2, SysOmsExcelUtil.EXCEL_SHEET_POST);
		List<SysOmsTempPost> tempPostList = buildPostList(deptList,newPostNum,identy);
		for (SysOmsTempPost post : tempPostList) {
			row = sheet2.createRow(rowIndex++);
			for (int j = 0; j < 5; j++) {
				cell = row.createCell(j);
				if(j  == 0){
					cell.setCellValue(post.getFdId()); //用户ID
				}else if(j == 1){
					cell.setCellValue(post.getFdName()); //人员名称
				}else if(j == 2){
					cell.setCellValue(altertime); //修改时间
				}else if(j == 3){
					cell.setCellValue(1);//是否有效
				}else if(j == 4){
					cell.setCellValue(post.getFdParentid());//主部门ID
				}

			}
		}
		logger.info("加载岗位结束，总共耗时："+ (System.currentTimeMillis() - userStarttime )+"ms");
		return tempPostList;

	}

	/**
	 * 构建人员sheet
	 * @throws Exception
	 */
	private void buildPpSheet(SysOmsExcelUtil excelUtil,HSSFWorkbook wb,List<SysOmsTempPost> postList,List<SysOmsTempPerson> personList,String altertime,int newPpNum) throws Exception{
		HSSFRow row = null;
		HSSFCell cell = null;
		int rowIndex = 1;
		logger.info("开始加载岗位人员关系");
		long starttime = System.currentTimeMillis();
		HSSFSheet sheet2 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet5"));
		excelUtil.buildTemplate(wb, sheet2, SysOmsExcelUtil.EXCEL_SHEET_PERSON_POST);
		for (int i = 0; i < newPpNum; i++) {
			row = sheet2.createRow(rowIndex++);
			for (int j = 0; j < 4; j++) {
				cell = row.createCell(j);
				if(j  == 0){
					cell.setCellValue(personList.get(new Random().nextInt(personList.size())).getFdId()); //人员ID
				}else if(j == 1){
					cell.setCellValue(postList.get(new Random().nextInt(postList.size())).getFdId()); //岗位ID
				}else if(j == 2){
					cell.setCellValue("");//是否有效
				}else if(j == 3){
					cell.setCellValue(altertime); //修改时间
				}

			}
		}
		logger.info("加载岗位人员关系结束，总共耗时："+ (System.currentTimeMillis() - starttime )+"ms");

	}

	@Override
	public HSSFWorkbook buildWorkBookNotEmpty(String identy) throws Exception {
		logger.info("开始构建数据");
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		SysOmsExcelUtil excelUtil = new SysOmsExcelUtil();
		long starttime = System.currentTimeMillis();
		String altertime = DateUtil.convertDateToString(new Date(), null);
		List<SysOmsTempDept> departmentList = buildDeptSheet(excelUtil,wb,altertime,20000,identy);
		List<SysOmsTempPerson> personList =  buildPersonSheet(excelUtil,wb,departmentList,altertime,60000,identy);
		List<SysOmsTempPost> tempPostList = buildPostSheet(excelUtil,wb,departmentList,altertime,20000,identy);
		buildPpSheet(excelUtil,wb,tempPostList,personList,altertime,30000);
		logger.info("构建数据结束，总共耗时："+ (System.currentTimeMillis() - starttime )+"ms");

		return wb;
	}

	/**
	 * 构建部门sheet
	 * @param excelUtil
	 * @param wb
	 * @param altertime
	 * @return 返回所有用户ID
	 * @throws Exception
	 */
	private List<SysOmsTempDept> buildDeptSheet(SysOmsExcelUtil excelUtil,HSSFWorkbook wb,String altertime,int newDeptNum,String identy) throws Exception{
		// 第1个sheet为部门导入数据
		HSSFRow row = null;
		HSSFCell cell = null;
		int rowIndex = 1;
		long deptStarttime = System.currentTimeMillis();
		logger.info("开始构建部门");
		HSSFSheet sheet1 = wb.createSheet(ResourceUtil.getString("sys-oms:sys.oms.template.sheet1"));
		excelUtil.buildTemplate(wb, sheet1, SysOmsExcelUtil.EXCEL_SHEET_DEPT);
		List<SysOmsTempDept> departmentList = buildDeptList(newDeptNum,identy);
		for (SysOmsTempDept department : departmentList) {
			logger.debug("部门名称："+department.getFdName()+"，部门ID："+department.getFdDeptId() + "，父部门ID："+department.getFdParentid());
			row = sheet1.createRow(rowIndex++);
			for (int j = 0; j < 8; j++) {
				cell = row.createCell(j);
				if(j  == 0){
					cell.setCellValue(department.getFdId()); //部门ID
				}else if(j == 1){
					cell.setCellValue(department.getFdName()); //部门名称
				}else if(j == 2){
					cell.setCellValue(altertime); //修改时间
				}else if(j == 3){
					cell.setCellValue(1); //有效状态
				}else if(j == 4){
					cell.setCellValue(department.getFdParentid()); //父部门ID
				}else if(j == 5){
					cell.setCellValue(department.getFdOrder());//部门排序号
				}
			}
		}
		logger.info("总共获取"+departmentList.size()+"个部门");
		logger.info("构建部门结束，总共耗时："+ (System.currentTimeMillis() - deptStarttime )+"ms");
		return departmentList;
	}

}
