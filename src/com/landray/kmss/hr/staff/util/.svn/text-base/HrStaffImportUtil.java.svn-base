package com.landray.kmss.hr.staff.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrganizationDeptService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 导入数据工具
 * 
 * @author 潘永辉 2017-1-9
 * 
 */
public class HrStaffImportUtil {

	private static final String EXCEPT_STRING = "¤§°±·×àáèéêìíòó÷ùúü";

	private static DecimalFormat formatter = new DecimalFormat("####################.#########");

	/**
	 * 获取Excel单元格的值，并且自动转换类型
	 * 
	 * @param cell
	 * @param property
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public static Object getCellValue(Cell cell, SysDictCommonProperty property, Locale locale) throws Exception {
		String value = getCellValue(cell);
		if (value == null) {
            return null;
        }
		if (!StringUtil.isNull(property.getEnumType())) {

			value = EnumerationTypeUtil.getColumnValueByLabel(property.getEnumType(), value, locale);
			if (value == null) {
                throw new Exception(
                        ResourceUtil.getString("sys-transport:sysTransport.import.dataError.enumNotExistVal", null,
                                locale, new Object[] { ResourceUtil.getString(property.getMessageKey(), locale),
                                        StringEscapeUtils.escapeHtml(getCellValue(cell)) }));
            }
			return transTypeFromString(value, property, locale);
		}
		String type = property.getType();
		try {
			if ("Integer".equals(type)) {
                return new Integer((int) Math.round(cell.getNumericCellValue()));
            }
			if ("Long".equals(type)) {
                return new Long(Math.round(cell.getNumericCellValue()));
            }
			if ("Double".equals(type)) {
                return new Double(cell.getNumericCellValue());
            }
			if ("Date".equals(type)) {
				return DateUtil.convertStringToDate(getCellValue(cell), DateUtil.PATTERN_DATE);
			}
			if ("DateTime".equals(type)) {
				String cellValue = getCellValue(cell);
				// 没有时分秒 默认为00:00:00
				if (cellValue.length() <= 10) {
					return DateUtil.convertStringToDate(cellValue, DateUtil.PATTERN_DATE);
				}
				return DateUtil.convertStringToDate(cellValue, DateUtil.PATTERN_DATETIME);

			}
			if ("Time".equals(type)) {
				return DateUtil.convertStringToDate(getCellValue(cell), "HH:mm:ss");
			}
		} catch (Exception e) {
			throw new Exception(ResourceUtil.getString("sys-transport:sysTransport.import.dataError.typeError", null,
					locale, new Object[] { ResourceUtil.getString(property.getMessageKey(), locale), getCellValue(cell),
							type }));
		}

		// 如果是字符串，需要判断长度是否超过限制
		if ("String".equals(type) && property instanceof SysDictSimpleProperty) {
			SysDictSimpleProperty simpleProp = (SysDictSimpleProperty) property;
			if (value.length() > simpleProp.getLength()) {
				throw new Exception(ResourceUtil.getString("sys-transport:sysTransport.import.dataError.tooLong2", null,
						locale, new Object[] { ResourceUtil.getString(property.getMessageKey(), locale),
								simpleProp.getLength() }));
			}
		}
		return transTypeFromString(getCellValue(cell), property, locale);
	}

	/**
	 * 将字符串类型转换为指定类型
	 * 
	 * @param value
	 * @param property
	 * @param locale
	 * @return
	 */
	private static Object transTypeFromString(String value, SysDictCommonProperty property, Locale locale) {
		if (StringUtil.isNull(value)) {
            return null;
        }
		String type = property.getType();
		if ("Boolean".equals(type)) {
			if ("1".equals(value)) {
                return new Boolean(true);
            }
			if ("0".equals(value)) {
                return new Boolean(false);
            }
			return new Boolean(value);
		}
		if ("Integer".equals(type)) {
            return new Integer(value);
        }
		if ("Long".equals(type)) {
            return new Long(value);
        }
		if ("Double".equals(type)) {
            return new Double(value);
        }
		if ("Date".equals(type)) {
            return DateUtil.convertStringToDate(value, DateUtil.TYPE_DATE, locale);
        }
		if ("DateTime".equals(type)) {
            return DateUtil.convertStringToDate(value, DateUtil.TYPE_DATETIME, locale);
        }
		if ("Time".equals(type)) {
            return DateUtil.convertStringToDate(value, DateUtil.TYPE_TIME, locale);
        }
		return value;
	}

	/**
	 * 获取Excel单元格的字符串值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null) {
            return null;
        }
		String rtnStr;
		switch (cell.getCellType()) {
		case BOOLEAN:
			rtnStr = new Boolean(cell.getBooleanCellValue()).toString();
			break;
		case FORMULA: {
			rtnStr = formatter.format(cell.getNumericCellValue());
			break;
		}
		case NUMERIC: {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期、时间格式
				SimpleDateFormat sdf = null;
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				rtnStr = sdf.format(cell.getDateCellValue());
			} else {
				//BigDecimal decimal = new BigDecimal(0);
				Double d = cell.getNumericCellValue();
				cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
				rtnStr = cell.getRichStringCellValue().getString();
				//cell.setCellValue(d);
				//cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			}
			break;
		}
		case BLANK:
		case ERROR:
			rtnStr = "";
			break;
		default:
			rtnStr = cell.getRichStringCellValue().getString();
		}
		return formatString(rtnStr.trim());
	}

	/**
	 * 去除字符串中的无法辨认的字符
	 * 
	 * @param s
	 * @return
	 */
	public static String formatString(String s) {
		StringBuffer rtnStr = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 128 && c <= 255 && EXCEPT_STRING.indexOf(c) == -1) {
                continue;
            }
			rtnStr.append(c);
		}
		return rtnStr.toString();
	}

	/**
	 * 检查导入数据是否正确
	 * 
	 * @param property
	 * @param value
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	public static boolean checkValue(SysDictCommonProperty property,
			Object value, KmssMessages messages) throws Exception {
		boolean isOk = true;
		String fdFieldLabel = ResourceUtil.getString(property.getMessageKey());
		if (property instanceof SysDictSimpleProperty) {
			SysDictSimpleProperty simpleProperty = (SysDictSimpleProperty) property;
			// 判断字符长度
			if (!checkLength(simpleProperty, value, messages, fdFieldLabel)) {
				isOk = false;
			}

		}
		return isOk;
	}

	/**
	 * 校验非空字段
	 * 
	 * @param context
	 * @param row
	 * @param rowNumber
	 */
	public static void validateNotNullProperties(
			Map<String, SysDictCommonProperty> map, String[] headers, Row row,
			KmssMessages messages) {
		SysDictCommonProperty property = null;
		for (int i = 0; i < headers.length; i++) {
			property = map.get(headers[i]);
			if (null != property && property.isNotNull()) {
				String value = null;
				try {
					value = getCellValue(row.getCell(i + 3));
				} catch (Exception e) {
				}
				if (StringUtil.isNull(value)) {
					messages.addError(new KmssMessage(ResourceUtil.getString(
							"hrStaff.import.error.notNull", "hr-staff", null,
							ResourceUtil.getString(property.getMessageKey()))));
				}
			}
		}
	}

	/**
	 * 判断字符长度
	 * 
	 * @param simpleProperty
	 * @param value
	 * @param messages
	 * @param fdFieldLabel
	 * @return
	 */
	private static boolean checkLength(SysDictSimpleProperty simpleProperty,
			Object value, KmssMessages messages, String fdFieldLabel) {
		boolean isOk = true;
		int length = simpleProperty.getLength();
		if (value != null
				&& "String".equalsIgnoreCase(simpleProperty.getType())
				&& getValueLength(value.toString()) > length) {
			isOk = false;
			messages.addError(new KmssMessage(ResourceUtil.getString(
					"hrStaff.import.error.length", "hr-staff", null,
					new Object[] { fdFieldLabel, length })));
		}
		if("Date".equalsIgnoreCase(simpleProperty.getType()) && value == null){
			isOk = false;
		}
		return isOk;
	}

	/**
	 * 获取字符长度，1个中文等于3个英文
	 * 
	 * @param val
	 * @return
	 */
	private static int getValueLength(String val) {
		return val.replaceAll("[^\\x00-\\xff]", "***").length();
	}

	public static List<String> getEntryItemNode() {
		List<String> item = new ArrayList<String>();
		item.add(ResourceUtil
				.getString("hr-staff:hrEntry.import.sheet2.item.node0"));
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.import.sheet2.item.node1"));
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.import.sheet2.item.node3"));
		item.add(ResourceUtil
				.getString("hr-staff:hrEntry.import.sheet2.item.node4"));
		item.add(ResourceUtil
				.getString("hr-staff:hrEntry.import.sheet2.item.node5"));
		return item;
	}

	public static HSSFWorkbook
			buildEntryTempletWorkBook(List<String> itemNodes) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(
				ResourceUtil.getString("hr-staff:hrStaff.import.sheet1.title"));
		sheet.setDefaultColumnWidth(25); // 设置宽度
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		// 第四步，创建单元格
		HSSFCell cell = null;

		// 定义必填字体效果
		HSSFFont font1 = wb.createFont();
		font1.setBold(true); // 字体增粗

		HSSFFont font2 = wb.createFont();
		font2.setBold(true); // 字体增粗
		font2.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色

		// 备注
		HSSFPatriarch patr = sheet.createDrawingPatriarch();
		HSSFCellStyle style = null;

		/********** 设置头部内容 **********/
		// 第一列为：姓名
		cell = row.createCell(0);
		cell.setCellValue(
				ResourceUtil.getString("hr-staff:hrStaffEntry.fdName"));
		style = getStyle(wb);
		style.setFont(font2);
		cell.setCellStyle(style);
		// 第二列为：手机号码
		cell = row.createCell(1);
		cell.setCellValue(
				ResourceUtil.getString("hr-staff:hrStaffEntry.fdMobileNo"));
		style = getStyle(wb);
		style.setFont(font2);
		cell.setCellStyle(style);
		// 第三列为：拟入职部门
		cell = row.createCell(2);
		cell.setCellValue(ResourceUtil
				.getString("hr-staff:hrStaffEntry.fdPlanEntryDept"));
		style = getStyle(wb);
		style.setFont(font2);
		cell.setCellStyle(style);
		// 第四列为：拟入职日期
		cell = row.createCell(3);
		cell.setCellValue(ResourceUtil
				.getString("hr-staff:hrStaffEntry.fdPlanEntryTime"));
		style = getStyle(wb);
		style.setFont(font2);
		cell.setCellStyle(style);
		// 第五列为：拟入职岗位
		cell = row.createCell(4);
		cell.setCellValue(
				ResourceUtil.getString("hr-staff:hrStaffEntry.fdOrgPosts"));
		style = getStyle(wb);
		style.setFont(font1);
		cell.setCellStyle(style);
		// 注意事项
		if (itemNodes != null && !itemNodes.isEmpty()) {
			HSSFSheet sheet2 = wb.createSheet(ResourceUtil
					.getString("hr-staff:hrStaff.import.sheet2.title"));
			sheet2.setColumnWidth(0, 35 * 80); // 第一列宽度
			sheet2.setColumnWidth(1, 35 * 500); // 第二列宽度
			HSSFRow row2 = null;
			HSSFCell cell2 = null;
			style = getStyle(wb);
			style.setFont(font1);

			row2 = sheet2.createRow((int) 0);
			row2.setHeight((short) (20 * 20));
			cell2 = row2.createCell(0);
			cell2.setCellValue(ResourceUtil
					.getString("hr-staff:hrStaff.import.sheet2.serial"));
			cell2.setCellStyle(style);

			cell2 = row2.createCell(1);
			cell2.setCellValue(ResourceUtil
					.getString("hr-staff:hrStaff.import.sheet2.item"));
			cell2.setCellStyle(style);

			// 单元格样式
			HSSFCellStyle style2_1 = wb.createCellStyle();
			style2_1.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平居中
			style2_1.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
			HSSFCellStyle style2_2 = wb.createCellStyle();
			style2_2.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
			for (int i = 0; i < itemNodes.size(); i++) {
				row2 = sheet2.createRow((int) (i + 1));
				row2.setHeight((short) (20 * 20));
				row.setHeight((short) (20 * 20));
				cell2 = row2.createCell(0);
				cell2.setCellValue(String.valueOf(i + 1));
				cell2.setCellStyle(style2_1);

				cell2 = row2.createCell(1);
				cell2.setCellValue(itemNodes.get(i));
				cell2.setCellStyle(style2_2);
			}
		}
		return wb;
	}

	public static List<String> getItemNode() {
		List<String> item = new ArrayList<String>();
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.import.sheet2.item.node1"));
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.import.sheet2.item.node2"));
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.import.sheet2.item.node3"));
		return item;
	}

	/**
	 * 构建模板文件
	 * 
	 * @param modelName
	 * @param importFields
	 * @return
	 */
	public static HSSFWorkbook buildTempletWorkBook(String modelName,
			String[] importFields, String[] fieldComments,
			List<String> itemNodes) {
		HSSFWorkbook wb = new HSSFWorkbook();
		return buildTempletWorkBook(wb, modelName, importFields, fieldComments,
				itemNodes, null);
	}

	/*
	 * 
	 */
	public static HSSFWorkbook buildTempletWorkBook(HSSFWorkbook wb,
			String modelName,
			String[] importFields, String[] fieldComments,
			List<String> itemNodes, String resource) {
		// 第一步，创建一个webbook，对应一个Excel文件
		// HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(ResourceUtil
				.getString("hr-staff:hrStaff.import.sheet1.title"));
		sheet.setDefaultColumnWidth(25); // 设置宽度
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		// 第四步，创建单元格
		HSSFCell cell = null;

		// 定义必填字体效果
		HSSFFont font1 = wb.createFont();
		font1.setBold(true); // 字体增粗

		HSSFFont font2 = wb.createFont();
		font2.setBold(true); // 字体增粗
		font2.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色

		// 备注
		HSSFPatriarch patr = sheet.createDrawingPatriarch();
		HSSFCellStyle style = null;
		style = getStyle(wb);
		style.setFont(font1);
		HSSFCellStyle style2 = getStyle(wb);
		style2.setFont(font2);
		// 需要跳过前面几个列
		int skipColumn = 3;
		/********** 设置头部内容 **********/
		// 第零列为：组织架构人员编号（非必填）
		cell = row.createCell(0);
		cell.setCellValue(ResourceUtil
				.getString("hr-staff:hrStaffPersonInfo.template.fdNo"));
		cell.setCellStyle(style);
		// 第一列为：登录账号
		cell = row.createCell(1);
		cell.setCellValue(ResourceUtil
				.getString("hr-staff:hrStaffPersonInfo.fdLoginName"));
		// 如果导入来源不为人事组织
		if (resource == null) {
			cell.setCellComment(buildComment(patr, ResourceUtil
					.getString("hr-staff:hrStaff.import.templet.required")));
			cell.setCellStyle(style2);
		} else {
			cell.setCellStyle(style);
		}
		// 第二列为：工号
		cell = row.createCell(2);
		cell.setCellValue(ResourceUtil
				.getString("hr-staff:hrStaffPersonInfo.fdStaffNo"));
		cell.setCellStyle(style2);
		cell.setCellComment(buildComment(patr, ResourceUtil
				.getString("hr-staff:hrStaff.import.templet.required")));
		Map<String, SysDictCommonProperty> map = SysDataDict.getInstance()
				.getModel(modelName).getPropertyMap();
		String val = null;
		StringBuffer comments = null;

		for (int i = 0; i < importFields.length; i++) {
			cell = row.createCell(i + skipColumn);
			val = importFields[i];
			SysDictCommonProperty property = map.get(val);
			comments = new StringBuffer();
			if (property.isNotNull()) {
				// 必填字段，字体设置为红色
				comments.append(ResourceUtil
						.getString("hr-staff:hrStaff.import.templet.required"));
				cell.setCellStyle(style2);
			} else {
				cell.setCellStyle(style);
			}

			// 如果有描述，就增加
			if (fieldComments != null && fieldComments.length > i) {
				if (StringUtil.isNotNull(comments.toString())
						&& StringUtil.isNotNull(fieldComments[i])) {
					comments.append(", ");
				}
				comments.append(fieldComments[i]);
			}

			if (StringUtil.isNotNull(comments.toString())) {
				cell.setCellComment(buildComment(patr, comments.toString()));
			}

			cell.setCellValue(ResourceUtil.getString(property.getMessageKey()));

		}

		// 注意事项
		if (itemNodes != null && !itemNodes.isEmpty()) {
			HSSFSheet sheet2 = wb.createSheet(ResourceUtil
					.getString("hr-staff:hrStaff.import.sheet2.title"));
			sheet2.setColumnWidth(0, 35 * 80); // 第一列宽度
			sheet2.setColumnWidth(1, 35 * 500); // 第二列宽度
			HSSFRow row2 = null;
			HSSFCell cell2 = null;
			style = getStyle(wb);
			style.setFont(font1);

			row2 = sheet2.createRow((int) 0);
			row2.setHeight((short) (20 * 20));
			cell2 = row2.createCell(0);
			cell2.setCellValue(ResourceUtil
					.getString("hr-staff:hrStaff.import.sheet2.serial"));
			cell2.setCellStyle(style);

			cell2 = row2.createCell(1);
			cell2.setCellValue(ResourceUtil
					.getString("hr-staff:hrStaff.import.sheet2.item"));
			cell2.setCellStyle(style);

			// 单元格样式
			HSSFCellStyle style2_1 = wb.createCellStyle();
			style2_1.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平居中
			style2_1.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
			HSSFCellStyle style2_2 = wb.createCellStyle();
			style2_2.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
			for (int i = 0; i < itemNodes.size(); i++) {
				row2 = sheet2.createRow((int) (i + 1));
				row2.setHeight((short) (20 * 20));
				row.setHeight((short) (20 * 20));
				cell2 = row2.createCell(0);
				cell2.setCellValue(String.valueOf(i + 1));
				cell2.setCellStyle(style2_1);

				cell2 = row2.createCell(1);
				cell2.setCellValue(itemNodes.get(i));
				cell2.setCellStyle(style2_2);
			}
		}

		return wb;
	}

	/**
	 * 获取带背景和居中的样式
	 * 
	 * @param wb
	 * @return
	 */
	private static HSSFCellStyle getStyle(HSSFWorkbook wb) {
		// 单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平布局：居中
		style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		// 背景色
		style.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index);
		return style;
	}

	/**
	 * 构建批注
	 * 
	 * @param patr
	 * @param value
	 * @return
	 */
	private static HSSFComment buildComment(HSSFPatriarch patr, String value) {
		// 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
		HSSFClientAnchor anrhor = null;
		if (value.length() < 50) {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 2,
					4);
		} else if (value.length() > 150) {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 3,
					7);
		} else {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 3,
					5);
		}
		HSSFComment comment = patr.createComment(anrhor);
		comment.setString(new HSSFRichTextString(value));
		return comment;
	}

	/**
	 * 模板下载时的文件名如果是中文，需要转码
	 * 
	 * @param request
	 * @param oldFileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeFileName(HttpServletRequest request,
			String oldFileName)
			throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("TRIDENT") > -1
				|| userAgent.indexOf("EDGE") > -1) {// ie情况处理
			oldFileName = URLEncoder.encode(oldFileName, "UTF-8");
			// 这里的编码后，空格会被解析成+，需要重新处理
			oldFileName = oldFileName.replace("+", "%20");
		} else {
			oldFileName = new String(oldFileName.getBytes("UTF-8"),
					"ISO8859-1");
		}
		return oldFileName;
	}

	private static IHrOrganizationDeptService getHrOrgDeptService() {
		return (IHrOrganizationDeptService) SpringBeanUtil.getBean("hrOrganizationDeptService");
	}

	public static HrOrganizationElement findDeptByPath(String path) throws Exception {
		String[] pathArray = path.split("/");
		if (pathArray.length == 1) {
			List listOrgs = getHrOrgDeptService().findList("fdName='" + pathArray[0] + "'", "fdId");
			if (listOrgs != null && listOrgs.size() > 0) {
				for (int i = 0; i < listOrgs.size(); i++) {
					HrOrganizationElement org = (HrOrganizationElement) listOrgs.get(i);
					//返回有效的部门
					if (org.getFdIsAvailable() && null == org.getFdParent()) {
						return org;
					}
				}
			}
		} else {
			HrOrganizationElement dept = null;
			HrOrganizationElement tempdept = null;
			int length = pathArray.length - 1;
			List listOrgs = getHrOrgDeptService().findList("fdName='" + pathArray[length] + "'", "fdId");
			if (listOrgs != null && listOrgs.size() > 0) {
				for (int j = 0; j < listOrgs.size(); j++) {
					tempdept = (HrOrganizationElement) listOrgs.get(j);
					dept = (HrOrganizationElement) listOrgs.get(j);
					for (int i = pathArray.length - 2; i >= 0; i--) {
						if (null != tempdept.getFdParent()
								&& pathArray[i].trim().equals(tempdept.getFdParent().getFdName())) {
							tempdept = tempdept.getFdParent();
							if (i == 0) {
								return dept;
							}
						} else {
							continue;
						}
					}
				}
			}
		}
		return null;
	}
}
