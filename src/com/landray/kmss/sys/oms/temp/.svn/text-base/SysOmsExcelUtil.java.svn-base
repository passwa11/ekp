package com.landray.kmss.sys.oms.temp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.property.custom.DynamicAttributeConfig;
import com.landray.kmss.sys.property.custom.DynamicAttributeField;
import com.landray.kmss.sys.property.custom.DynamicAttributeFieldEnum;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;

public class SysOmsExcelUtil{
	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOmsExcelUtil.class);
	public static final int EXCEL_SHEET_DEPT = 0; // 部门
	public static final int EXCEL_SHEET_POST = 1; // 岗位
	public static final int EXCEL_SHEET_PERSON = 2; // 人员
	public static final int EXCEL_SHEET_PERSON_DEPT = 3; // 人员部门关系
	public static final int EXCEL_SHEET_PERSON_POST = 4; // 人员岗位关系
	public static final int EXCEL_SHEET_EXPLAIN = 5; // 说明
	
	
	public static final String EXCEL_SHEET_DEPT_NAME = "部门信息"; // 部门
	public static final String EXCEL_SHEET_POST_NAME = "岗位信息"; // 岗位
	public static final String EXCEL_SHEET_PERSON_NAME = "人员信息"; // 人员
	public static final String EXCEL_SHEET_PERSON_DEPT_NAME = "人员部门关系信息"; // 人员部门关系
	public static final String EXCEL_SHEET_PERSON_POST_NAME = "人员岗位关系信息"; // 人员岗位关系
	
	Map<String, String> personExpandMap  =  new HashMap<>();//人员拓展字段
	Map<String, String> personExpandExcelFieldMap  =  new HashMap<>();//excel人员拓展字段与名称映射
	
	/**
	 * 模板字段分类
	 * <p>
	 * 基本信息，数据多语言 ，职位信息，身份验证信息，其他选填字段
	 */
	public static String[] fieldGroup() {
		return new String[] { "base", "other", "expand"};
	};
	
	/**
	 * 获取模板字段
	 * 
	 * @param type
	 *            1:部门，2:人员
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String[]> getTemplateFields(int type) throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		if (type == EXCEL_SHEET_DEPT) {
			map.put("base", new String[] { "fdDeptId", "fdName","fdAlterTime","fdIsAvailable" });
			map.put("other", new String[] { "fdParentid", "fdOrder"});
		} else if (type == EXCEL_SHEET_POST) {
			map.put("base", new String[] { "fdPostId", "fdName","fdAlterTime","fdIsAvailable" });
			map.put("other", new String[] { "fdParentid", "fdOrder"});
		}else if (type == EXCEL_SHEET_PERSON) {
			map.put("base", new String[] { "fdPersonId", "fdName","fdAlterTime","fdIsAvailable","fdLoginName" });
			map.put("other", new String[] { "fdParentid", "fdOrder","fdMobileNo","fdEmail","fdSex","fdNo","fdWorkPhone","fdDesc"});
			map.put("expand", getLDingDynamicAttribute());
		}else if (type == EXCEL_SHEET_PERSON_DEPT) {
			map.put("base", new String[] { "fdPersonId", "fdDeptId"});
			map.put("other", new String[] { "fdIsAvailable","fdAlterTime", "fdOrder"});
		}else if (type == EXCEL_SHEET_PERSON_POST) {
			map.put("base", new String[] { "fdPersonId", "fdPostId"});
			map.put("other", new String[] { "fdIsAvailable","fdAlterTime"});
		}
		
		if (SysLangUtil.isLangEnabled()) {
			Map<String, String> langMaps = SysLangUtil.getSupportedLangs();
			String officialLang = SysLangUtil.getOfficialLang();
			List<String> langs = new ArrayList<String>();
			for (String key : langMaps.keySet()) {
				if (!officialLang.equals(key)) // 要排除官方语言
                {
                    langs.add(key);
                }
			}
			map.put("lang", langs.toArray(new String[] {}));
		}
		return map;
	}
	
	public Map<String, String> getSheetMap() {
		Map<String,String> sheetMap = new HashMap<String,String>();
		sheetMap.put("部门信息", "0");
		sheetMap.put("岗位信息", "1");
		sheetMap.put("人员信息", "2");
		sheetMap.put("人员部门关系信息", "3");
		sheetMap.put("人员岗位关系信息", "4");
		sheetMap.put("说明", "5");
		return sheetMap;
	}
	
	private Map<String, String> getTemplateFieldsMap(int sheetDept) {
		Map<String,String> fieldsMap = new HashMap<String,String>();//字段映射
    	if (sheetDept == EXCEL_SHEET_DEPT) { //部门
    		fieldsMap.put("fdDeptId", "部门ID");
    		fieldsMap.put("fdName", "部门名称");
    		fieldsMap.put("fdAlterTime", "修改时间");
    		fieldsMap.put("fdIsAvailable", "是否有效");
    		fieldsMap.put("fdParentid", "上级部门ID");
    		fieldsMap.put("fdOrder", "部门排序号");
		}else if (sheetDept == EXCEL_SHEET_POST) {//岗位模版字段
			fieldsMap.put("fdPostId", "岗位ID");
			fieldsMap.put("fdName", "岗位名称");
			fieldsMap.put("fdAlterTime", "修改时间");
			fieldsMap.put("fdIsAvailable", "是否有效");
			fieldsMap.put("fdParentid", "所属部门ID");
			fieldsMap.put("fdOrder", "岗位排序号");
		}else if (sheetDept == EXCEL_SHEET_PERSON) {//人员模版字段
			fieldsMap.put("fdPersonId", "人员ID");
			fieldsMap.put("fdName", "人员名称");
			fieldsMap.put("fdAlterTime", "修改时间");
			fieldsMap.put("fdIsAvailable", "是否有效");
			fieldsMap.put("fdParentid", "主部门ID");
			fieldsMap.put("fdOrder", "人员在主部门的排序号");
			fieldsMap.put("fdMobileNo", "手机号");
			fieldsMap.put("fdLoginName", "登录名");
			fieldsMap.put("fdEmail", "邮箱");
			fieldsMap.put("fdSex", "性别");
			fieldsMap.put("fdNo", "工号");
			fieldsMap.put("fdWorkPhone", "分机号（办公电话）");
			fieldsMap.put("fdDesc", "备注");
			fieldsMap.putAll(personExpandMap);
		}else if (sheetDept == EXCEL_SHEET_PERSON_DEPT) { //部门人员关系模版字段
			fieldsMap.put("fdPersonId", "人员ID");
			fieldsMap.put("fdDeptId", "部门ID");
			fieldsMap.put("fdIsAvailable", "是否有效");
			fieldsMap.put("fdAlterTime", "修改时间");
			fieldsMap.put("fdOrder", "排序号");
		}else if (sheetDept == EXCEL_SHEET_PERSON_POST) { //岗位人员关系模版字段
			fieldsMap.put("fdPersonId", "人员ID");
			fieldsMap.put("fdPostId", "岗位ID");
			fieldsMap.put("fdIsAvailable", "是否有效");//关系类型
			fieldsMap.put("fdAlterTime", "修改时间");
		}
		return fieldsMap;
	}
	
	
	public Map<String, String> getExcelTemplateFieldsMap(int sheetDept) throws Exception {
    	Map<String,String> excelFieldsMap = new HashMap<String,String>();
    	if (sheetDept == EXCEL_SHEET_DEPT) { //部门
    		excelFieldsMap.put("部门ID(*)", "fdDeptId");
    		excelFieldsMap.put("部门名称(*)", "fdName");
    		excelFieldsMap.put("修改时间(*)", "fdAlterTime");
    		excelFieldsMap.put("是否有效(*)", "fdIsAvailable");
    		excelFieldsMap.put("上级部门ID", "fdParentid");
    		excelFieldsMap.put("部门排序号", "fdOrder");
		}else if (sheetDept == EXCEL_SHEET_POST) {//岗位模版字段
			excelFieldsMap.put("岗位ID(*)", "fdPostId");
			excelFieldsMap.put("岗位名称(*)", "fdName");
			excelFieldsMap.put("修改时间(*)", "fdAlterTime");
			excelFieldsMap.put("是否有效(*)", "fdIsAvailable");
			excelFieldsMap.put("所属部门ID", "fdParentid");
			excelFieldsMap.put("岗位排序号", "fdOrder");
		}else if (sheetDept == EXCEL_SHEET_PERSON) {//人员模版字段
			excelFieldsMap.put("人员ID(*)", "fdPersonId");
			excelFieldsMap.put("人员名称(*)", "fdName");
			excelFieldsMap.put("登录名(*)", "fdLoginName");
			excelFieldsMap.put("修改时间(*)", "fdAlterTime");
			excelFieldsMap.put("是否有效(*)", "fdIsAvailable");
			excelFieldsMap.put("主部门ID", "fdParentid");
			excelFieldsMap.put("人员在主部门的排序号", "fdOrder");
			excelFieldsMap.put("手机号", "fdMobileNo");
			excelFieldsMap.put("邮箱", "fdEmail");
			excelFieldsMap.put("性别", "fdSex");
			excelFieldsMap.put("工号", "fdNo");
			excelFieldsMap.put("分机号（办公电话）", "fdWorkPhone");
			excelFieldsMap.put("备注", "fdDesc");
			getLDingDynamicAttribute();
			excelFieldsMap.putAll(personExpandExcelFieldMap);
		}else if (sheetDept == EXCEL_SHEET_PERSON_DEPT) { //部门人员关系模版字段
			excelFieldsMap.put("人员ID(*)", "fdPersonId");
			excelFieldsMap.put("部门ID(*)", "fdDeptId");
			excelFieldsMap.put("是否有效", "fdIsAvailable");
			excelFieldsMap.put("修改时间", "fdAlterTime");
			excelFieldsMap.put("排序号", "fdOrder");
		}else if (sheetDept == EXCEL_SHEET_PERSON_POST) { //岗位人员关系模版字段
			excelFieldsMap.put("人员ID(*)", "fdPersonId");
			excelFieldsMap.put("岗位ID(*)", "fdPostId");
			excelFieldsMap.put("是否有效", "fdIsAvailable");
			excelFieldsMap.put("修改时间", "fdAlterTime");
		}
		return excelFieldsMap;
	}

	/**
	 * 构建导入数据字段信息
	 * 
	 * @param wb
	 * @param sheet
	 * @param type
	 * @throws Exception 
	 */
	public void buildTemplate(HSSFWorkbook wb, HSSFSheet sheet, int type) throws Exception {
		Map<String, String[]> templateTitle = getTemplateFields(type);
		Map<String,String> fieldsMap = getTemplateFieldsMap(type);
		HSSFRow row1 = null;
		HSSFRow row2 = null;
		HSSFCell cell = null;
		HSSFCellStyle style = null;
		int rangeIndex = 0;

		// 默认列宽
		sheet.setDefaultColumnWidth(20);

		String _type =getSheetType(type);
				
//		// 处理第一行
//		row1 = sheet.createRow(0);
		// 处理第二行
		row2 = sheet.createRow(0);
		int cellIndex = 0;
		for (String _field : fieldGroup()) {
			String[] fields = templateTitle.get(_field);
			if (fields != null) {
				boolean isRed = "base".equals(_field);
				for (int i = 0; i < fields.length; i++) {
					cell = row2.createCell(cellIndex++);
					style = getCellStyle(wb, true);
					setBackgroundColor(style, true);
					if (fieldsMap.get(fields[i]).contains("(*)")) {
						isRed = true;
						fieldsMap.put(fields[i], fieldsMap.get(fields[i]).replace("(*)", ""));	
					}
					style.setFont(getTitleFont(wb, isRed));
					cell.setCellStyle(style);
					cell.setCellValue(fieldsMap.get(fields[i])+ (isRed ? "(*)" : ""));
					
				}
			}
		}

		// 获取色板，并替换之前设置的颜色
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index, (byte) 91,
				(byte) 155, (byte) 213);
		palette.setColorAtIndex(org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT.index, (byte) 217,
				(byte) 217, (byte) 217);
	}
	
	
	/**
	 * 构建说明信息
	 * 
	 * @param wb
	 * @param sheet
	 * @param type
	 */
	public void buildItemNodes(HSSFWorkbook wb, HSSFSheet sheet, int type) {
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle style = null;

		sheet.setColumnWidth(0, 35 * 80); // 第一列宽度
		sheet.setColumnWidth(1, 35 * 800); // 第二列宽度

		// 构建标题
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue(ResourceUtil
				.getString("sys-oms:sys.oms.template.itemNode.serial"));
		style = getCellStyle(wb, true);
		setBackgroundColor(style, true);
		style.setFont(getTitleFont(wb, false));
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue(ResourceUtil.getString(
				"sys-oms:sys.oms.template.itemNode.comment"));
		style = getCellStyle(wb, true);
		setBackgroundColor(style, true);
		style.setFont(getTitleFont(wb, false));
		cell.setCellStyle(style);

		// 构建导入说明
		List<String> itemNodes = getItemNodes();
		for (int i = 1; i <= itemNodes.size(); i++) {
			row = sheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue(i);
			style = getCellStyle(wb, true);
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue(itemNodes.get(i - 1));
			style = getCellStyle(wb, false);
			cell.setCellStyle(style);
		}
		
		//扩展字段填写
		String note = getEKPDynamicAttributeNote();
		row = sheet.createRow(itemNodes.size()+1);
		cell = row.createCell(0);
		cell.setCellValue(itemNodes.size()+1);
		style = getCellStyle(wb, true);
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue(note);
		style = getCellStyle(wb, false);
		cell.setCellStyle(style);
	}
	
	/**
	 * 获取注意事项
	 * 
	 * @param type
	 *            1:部门，2:人员
	 * @return
	 */
	private List<String> getItemNodes() {
		List<String> list = new ArrayList<String>();
		list.add(ResourceUtil.getString("sys-oms:sys.oms.template.attention1"));
		list.add(ResourceUtil.getString("sys-oms:sys.oms.template.attention2"));
		list.add(ResourceUtil.getString("sys-oms:sys.oms.template.attention3"));
		list.add(ResourceUtil.getString("sys-oms:sys.oms.template.attention4"));
		list.add(ResourceUtil.getString("sys-oms:sys.oms.template.attention5"));
		list.add(ResourceUtil.getString("sys-oms:sys.oms.template.attention6"));
		list.add(ResourceUtil.getString("sys-oms:sys.oms.template.attention7"));
		

		return list;
	}
	
	private String getSheetType(int type) {
		String sheetType="";
		if(type == EXCEL_SHEET_DEPT){
			return sheetType="dept";
		}else if(type == EXCEL_SHEET_POST){
			return sheetType="post";
		}else if(type == EXCEL_SHEET_PERSON){
			return sheetType="person";
		}else if (type == EXCEL_SHEET_PERSON_DEPT) {
			return sheetType="person.dept";
		}else if (type == EXCEL_SHEET_PERSON_POST) {
			return sheetType="person.post";
		}
		return sheetType;
	}

	/**
	 * 设置单元格
	 * 
	 * @param wb
	 * @param isCenter
	 * @return
	 */
	private HSSFCellStyle getCellStyle(HSSFWorkbook wb, boolean isCenter) {
		HSSFCellStyle style = wb.createCellStyle();
		if (isCenter) {
			style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平布局：居中
			style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		}
		style.setWrapText(true); // 设置自动换行

		// 边框
		style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 下边框
		style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 左边框
		style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 上边框
		style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 右边框
		return style;
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param style
	 * @param isMain
	 */
	private void setBackgroundColor(HSSFCellStyle style, boolean isMain) {
		// 背景色
		style.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		if (isMain) {
            style.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index); // 蓝色
        } else {
            style.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT.index); // 灰色
        }
	}
	
	/**
	 * 设置字体
	 * 
	 * @param wb
	 * @param isRed
	 * @return
	 */
	private HSSFFont getTitleFont(HSSFWorkbook wb, boolean isRed) {
		HSSFFont font = wb.createFont();
		font.setBold(true); // 字体增粗
		if (isRed) {
			font.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色
		}
		return font;
	}
	
	
	/**
	 * 获取蓝桥动态字段
	 * @return
	 * @throws Exception 
	 */
	private String[]  getLDingDynamicAttribute() throws Exception{
		String [] rtn = new String [] {};
		HQLInfo info = new HQLInfo();
		info.setRowSize(1);
		ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService)SpringBeanUtil.getBean("sysOrgPersonService");
		Page page = sysOrgPersonService.findPage(info);
		List<SysOrgPerson> persons = page.getList();
		if (persons != null && persons.size() == 1) {
			rtn = getEKPDynamicAttribute(persons.get(0));
		} else {
			logger.error("无法获取EKP的人员数据，导致无法获取人员的动态字段");
		}
		return rtn;
	}
	
	private String[] getEKPDynamicAttribute(SysOrgPerson person){
		String [] arr = null;
		try{
			String modelName = "com.landray.kmss.sys.organization.model.SysOrgPerson";
			DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil.getDynamicAttributeConfig(modelName);
			if(dynamicConfig != null) {
				List<DynamicAttributeField> _list = dynamicConfig.getEnabledFields();
				arr = new String[_list.size()];
				for(int i=0; i<_list.size(); i++) {
					DynamicAttributeField field = _list.get(i);
					arr[i] =field.getFieldName();
					if ("true".equals(field.getRequired())) {
						personExpandMap.put(field.getFieldName(), field.getFieldTexts().get("def")+"[拓展](*)");
						personExpandExcelFieldMap.put(field.getFieldTexts().get("def")+"[拓展](*)", field.getFieldName());
					}else{
						personExpandMap.put(field.getFieldName(), field.getFieldTexts().get("def")+"[拓展]");
						personExpandExcelFieldMap.put(field.getFieldTexts().get("def")+"[拓展]", field.getFieldName());
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("无法获取EKP的动态字段异常：", e);
		}
		return arr;
	}
	
	public static JSONArray getLandingExtendFields() {
		JSONArray returnJson = new JSONArray();
		try{
			String modelName = "com.landray.kmss.sys.organization.model.SysOrgPerson";
			DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil.getDynamicAttributeConfig(modelName);
			if(dynamicConfig != null) {
				JSONObject object = null;
				List<DynamicAttributeField> _list = dynamicConfig.getEnabledFields();
				for(int i=0; i<_list.size(); i++) {
					object = new JSONObject();
					DynamicAttributeField field = _list.get(i);
					object.put("field", field.getFieldName());
					object.put("fieldName", field.getFieldTexts().get("def"));
					returnJson.add(object);
				}
			}
		} catch (Exception e) {
			logger.error("无法获取EKP的动态字段异常：", e);
		}
		return returnJson;
	}
	
	/**
	 * 获取EKP动态字段
	 * @return
	 * @throws Exception 
	 */
	
	public static Map<String,String> getEKPDynamicAttribute(){
		Map<String,String> rtnMap = new HashMap<String,String>();
		try{
			String modelName = "com.landray.kmss.sys.organization.model.SysOrgPerson";
			DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil.getDynamicAttributeConfig(modelName);
			if(dynamicConfig != null) {
				List<DynamicAttributeField> _list = dynamicConfig.getEnabledFields();
				for(int i=0; i<_list.size(); i++) {
					DynamicAttributeField field = _list.get(i);
					rtnMap.put(field.getFieldName(), field.getFieldType().substring(field.getFieldType().lastIndexOf(".")+1));
				}
			}			
		} catch (Exception e) {
			logger.error("无法获取EKP的动态字段异常：", e);
		}
		return rtnMap;
	}
	
	private String getEKPDynamicAttributeNote(){
		String rtn = "";
		try{
			String modelName = "com.landray.kmss.sys.organization.model.SysOrgPerson";
			DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil.getDynamicAttributeConfig(modelName);
			if(dynamicConfig != null) {
				List<DynamicAttributeField> _list = dynamicConfig.getEnabledFields();
				StringBuffer content = new StringBuffer();
				StringBuffer cenum = new StringBuffer();
				for(int i=0; i<_list.size(); i++) {
					DynamicAttributeField field = _list.get(i);
					content.append("扩展字段("+field.getFieldTexts().get("def")+")数据填写说明：");
					if("radio".equalsIgnoreCase(field.getDisplayType()) ||"select".equalsIgnoreCase(field.getDisplayType())){
						cenum.setLength(0);
						List<DynamicAttributeFieldEnum> denums = field.getFieldEnums();
						for(DynamicAttributeFieldEnum denum:denums){
							cenum.append(denum.getText("def")+"填"+denum.getValue()+",");
						}
						content.append(cenum.toString());
					}else if("checkbox".equalsIgnoreCase(field.getDisplayType())){
						cenum.setLength(0);
						List<DynamicAttributeFieldEnum> denums = field.getFieldEnums();
						for(DynamicAttributeFieldEnum denum:denums){
							cenum.append(denum.getText("def")+"填"+denum.getValue()+",");
						}
						content.append(cenum.toString()+"多选请使用;号隔开");
					}else if("time".equalsIgnoreCase(field.getDisplayType())){
						content.append("类型为时间，数据填写格式必须为：HH:mm");
					}else if("date".equalsIgnoreCase(field.getDisplayType())){
						content.append("类型为日期，数据填写格式必须为：yyyy-MM-dd");
					}else if("datetime".equalsIgnoreCase(field.getDisplayType())){
						content.append("类型为日期时间，数据填写格式必须为：yyyy-MM-dd HH:mm");
					}else{
						content.append("类型为文本，数据填写无要求");
					}
					if(i!=_list.size()-1) {
                        content.append("\n\r");
                    }
				}
				rtn = content.toString();
			}
		} catch (Exception e) {
			logger.error("无法获取EKP的动态字段异常：", e);
		}
		return rtn;
	}
	
	
	public static Map<String, JSONObject> getEKPDynamicAttributeChecked(){
		Map<String, JSONObject> rtn = new HashMap<>();
		try{
			String modelName = "com.landray.kmss.sys.organization.model.SysOrgPerson";
			DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil.getDynamicAttributeConfig(modelName);
			if(dynamicConfig != null) {
				List<DynamicAttributeField> _list = dynamicConfig.getEnabledFields();
				for(int i=0; i<_list.size(); i++) {
					DynamicAttributeField field = _list.get(i);
					JSONObject jo  = new JSONObject();
					jo.put("req", field.getRequired());
					jo.put("display", field.getDisplayType());
					jo.put("name", field.getFieldTexts().get("def"));
					jo.put("denum", "");
					jo.put("fieldType", field.getFieldType());
					if("radio".equalsIgnoreCase(field.getDisplayType()) || "checkbox".equalsIgnoreCase(field.getDisplayType()) || "select".equalsIgnoreCase(field.getDisplayType())){
						List<DynamicAttributeFieldEnum> denums = field.getFieldEnums();
						String denumVal="";
						for(DynamicAttributeFieldEnum denum:denums){
							if ("checkbox".equalsIgnoreCase(field.getDisplayType())) {
								denumVal +=denum.getValue()+";";
							}else{
								denumVal +=denum.getValue()+",";
							}
						}
						if (denumVal.length()>0) {
							denumVal = denumVal.substring(0, denumVal.length()-1);
						}
						jo.put("denum", denumVal);
					}
				rtn.put(field.getFieldName(), jo);
			}
			}
		} catch (Exception e) {
			logger.error("无法获取EKP的动态字段异常：", e);
		}
		return rtn;
	}
}
