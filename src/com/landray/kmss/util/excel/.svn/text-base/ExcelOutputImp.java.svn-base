package com.landray.kmss.util.excel;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.sso.client.util.StringUtil;
import com.sunbor.web.tag.enums.ValueLabel;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

public class ExcelOutputImp implements ExcelOutput {

	private HSSFCellStyle unLockCellStyle;

	private HSSFCellStyle unLockCellStyle_content;

	private static String DEFAULT_NUMBER_FORMAT = "0.00";

	@Override
	@SuppressWarnings("unchecked")
	public void output(WorkBook workbook, OutputStream outputStream)
			throws Exception {
		output(workbook, outputStream, null, null);
	}

	private static DataValidation setDataValidation(org.apache.poi.ss.usermodel.Sheet sheet, String[] textList,
			int firstRow, int endRow, int firstCol, int endCol) {

		DataValidationHelper helper = sheet.getDataValidationHelper();
		// 加载下拉列表内容
		DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
		// DVConstraint constraint = new DVConstraint();
		constraint.setExplicitListValues(textList);

		// 设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol,
				(short) endCol);

		// 数据有效性对象
		DataValidation data_validation = helper.createValidation(constraint, regions);
		// DataValidation data_validation = new DataValidation(regions, constraint);

		return data_validation;
	}
	/**
	 * 导出数据
	 * @param workbook
	 * @param outputStream
	 * @param sheetMerge 合并单元格的信息
	 * @param styleMap 自定义的样式
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void output(WorkBook workbook, OutputStream outputStream, List<List<Integer []>> sheetMerge,
			Map<String, List<CellStyleEnum>> styleMap)
			throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle titleStyle = buildStyle(wb, "title");
		HSSFCellStyle columnTitleStyle = buildStyle(wb, "columnTitle");
		HSSFCellStyle contentStyle = buildStyleByMap(wb, "content", styleMap);
		HSSFCellStyle tipStyle = buildStyle(wb, "tip");
		HSSFCellStyle linkStyle = buildStyle(wb, "link");
		unLockCellStyle_content = buildStyle(wb, "content");
		int index = 0;
		int sheetIndex = 0;
		for (Iterator iter = workbook.getSheetList().iterator(); iter.hasNext();) {
			Sheet sheet = (Sheet) iter.next();
			Map<Integer, String[]> selectContentMap = sheet.getSelectContentMap();
			String sheetName = getSheetName(workbook, sheet);
			String tip = sheet.getTip();
			HSSFSheet sheetHSSF = wb.createSheet();
			// 如果当前表单存在密码，那么构建锁定样式
			if (sheet.getPassword() != null) {
				sheetHSSF.protectSheet(sheet.getPassword());
			}
			// 如果存在隐藏设置，那么启用
			if (sheet.getColumHidenIndex() != null) {
				for (int hideIndex = 0; hideIndex < sheet.getColumHidenIndex().length; ++hideIndex) {
					sheetHSSF.setColumnHidden(
							sheet.getColumHidenIndex()[hideIndex], true);
				}
			}

			String sName = sheetName;
        	if(StringUtil.isNotNull(sName)) {
        		sName = sName.replaceAll("\\\\|\\?|\\/|\\[|\\]|:|\\*", "_");
        	}
			
			wb.setSheetName(index++, sName);
			HSSFRow row;
			HSSFCell cell;
			int rowNO = 0;
			int columnCount = sheet.getColumnList().size(); // 计算总列数
			if (sheet.isIfCreateSheetTipLine()) {
				/* 创建提示行 BEGIN */
				row = sheetHSSF.createRow((short) rowNO++); // 标题行
				cell = row.createCell(0);
				cell.setCellValue(tip);
				cell.setCellStyle(tipStyle);
				CellRangeAddress range = null;
				// 列数少于等于0，合并单元格会有问题
				if (columnCount <= 0) {
					range = new CellRangeAddress(0, 0, 0, 0);
				} else {
					range = new CellRangeAddress(0, 0, 0, columnCount - 1);
				}			
				com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheetHSSF, range);
				/* 创建提示行 END */
			}
			if (sheet.isIfCreateSheetTitleLine()) {
				/* 创建标题行 BEGIN */
				row = sheetHSSF.createRow((short) rowNO++); // 标题行
				cell = row.createCell(0);
				cell.setCellValue(sheetName);
				cell.setCellStyle(titleStyle);
				CellRangeAddress range = null;
				// 列数少于等于0，合并单元格会有问题
				if (columnCount <= 0) {
					range = new CellRangeAddress(0, 0, 0, 0);
				} else {
					range = new CellRangeAddress(0, 0, 0, columnCount - 1);
				}		
				com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheetHSSF, range);
				/* 创建标题行 END */
			}

			if (null != sheet.getHeadContentList()) {
				unLockCellStyle = wb.createCellStyle();
				for (Iterator<?> it = sheet.getHeadContentList().iterator(); it
						.hasNext();) {
					int firstRow = rowNO++; // 主行在所有行中行号

					Object obj = it.next();
					// 创建行中内容
					SheetRowBuilder rowBuilder = new SheetRowBuilder();
					rowBuilder.workbook = workbook;
					rowBuilder.contentStyle = contentStyle;
					rowBuilder.linkStyle = linkStyle;
					rowBuilder.sheet = sheet;
					rowBuilder.sheetHSSF = sheetHSSF;

					rowBuilder.firstRow = firstRow;
					rowBuilder.rowObj = obj;
					rowBuilder.wb = wb;
					rowBuilder.build();

					rowNO = rowBuilder.rowSpan + firstRow;
				}
				rowNO++;
			}

			boolean isDownTemplate = sheet
					.getTitle()
					.contains(
							ResourceUtil
									.getString("sys-transport:sysTransport.import.data"));
			/* 创建列标题行 BEGIN */
			row = sheetHSSF.createRow((short) rowNO++); // 列标题行
			for (int i = 0; i < columnCount; i++) {
				Column column = (Column) sheet.getColumnList().get(i);
				column.setStyle(wb.createCellStyle());
				cell = row.createCell(i);
				String title = getColumnName(workbook, sheet, column);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				if (sheet.getLockColum() - 2 < i) {
					sheetHSSF.setColumnWidth(i, 2740);
					unLockCellStyle = wb.createCellStyle();
					// 标红代表必填，加(*)
					if (column.isRedFont()) {
						unLockCellStyle.cloneStyleFrom(buildStyle(wb,
								"columnTitle", "red", null));
						cell.setCellStyle(buildStyle(wb, "columnTitle"));
						HSSFFont font = wb.createFont();
						HSSFFont bFont = wb.createFont();
						font.setColor(HSSFFont.COLOR_RED);
						bFont.setBold(true);
						HSSFRichTextString ts = new HSSFRichTextString(
								title + "(*)");
						ts.applyFont(title.length() + 1, title.length() + 2,
								font);
						ts.applyFont(title.length() + 2, title.length() + 3,
								bFont);
						cell.setCellValue(ts);
					} else {
						unLockCellStyle.cloneStyleFrom(columnTitleStyle);
						cell.setCellStyle(columnTitleStyle);
						cell.setCellValue(title);
					}
					unLockCellStyle.setLocked(false);
				} else {
					// 标红代表必填，加(*)
					if (column.isRedFont()) {
						cell.setCellStyle(buildStyle(wb, "columnTitle"));
						HSSFFont font = wb.createFont();
						HSSFFont bFont = wb.createFont();
						font.setColor(HSSFFont.COLOR_RED);
						bFont.setBold(true);
						HSSFRichTextString ts = new HSSFRichTextString(
								title + "(*)");
						ts.applyFont(title.length() + 1, title.length() + 2,
								font);
						ts.applyFont(title.length() + 2, title.length() + 3,
								bFont);
						cell.setCellValue(ts);
					} else {
						cell.setCellStyle(columnTitleStyle);
						cell.setCellValue(title);
					}

				}
				if (!isDownTemplate) {
					continue;
				}
				// 下载导入的模板时，对枚举字段添加批注
				KmssFormat format = column.getFormat();
				if (null != format && format instanceof KmssEnumFormat) {
					HSSFPatriarch patr = sheetHSSF.createDrawingPatriarch();
					// 设置注释内容
					List<ValueLabel> list = EnumerationTypeUtil
							.getColumnEnumsByType(((KmssEnumFormat) format)
									.getEnumType());
					HSSFComment comment = patr
							.createComment(
									new HSSFClientAnchor(125, 125, 511,
									255, (short) i, i, (short) (i + 1), i + 1
											+ list.size()));
					String textStr = ResourceUtil.getString(
							"sysTransportImport.cell.enum.comment",
							"sys-transport")
							+ "\r\n";
					for (ValueLabel vl : list) {
						textStr += vl.getLabel() + "\r\n";
					}
					comment.setString(new HSSFRichTextString(textStr));
					cell.setCellComment(comment);
				}

			}
			/* 创建列标题行 END */

			/* 创建内容 BEGIN */
			Iterator<?> it = sheet.getContentList().iterator();
			if (null != it) {
				while (it.hasNext()) {
					int firstRow = rowNO++; // 主行在所有行中行号

					Object obj = it.next();

					// 创建行中内容
					SheetRowBuilder rowBuilder = new SheetRowBuilder();
					rowBuilder.workbook = workbook;
					rowBuilder.contentStyle = contentStyle;
					rowBuilder.linkStyle = linkStyle;

					rowBuilder.sheet = sheet;
					rowBuilder.sheetHSSF = sheetHSSF;

					rowBuilder.firstRow = firstRow;
					rowBuilder.rowObj = obj;
					rowBuilder.wb = wb;
					rowBuilder.build();

					rowNO = rowBuilder.rowSpan + firstRow;
				}
			}
			// for (int j = 0, n = sheet.getContentList().size(); j < n; j++) {
			// // j为行号
			// Object obj = sheet.getContentList().get(j);
			// row = sheetHSSF.createRow(j + rowNO);
			// if (obj instanceof Object[] || obj instanceof List) {
			// Object[] oneRow = (obj instanceof List) ? ((List) obj)
			// .toArray() : (Object[]) obj;
			//
			// int colOffset = 0;
			// int maxRowOffset = rowNO;
			//
			// for (int i = 0; i < oneRow.length; i++) { // i为列号，从0开始
			// obj = oneRow[i];
			// int[] rowAndColl = createDetailCell(wb, sheetHSSF,
			// columnTitleStyle, contentStyle, workbook,
			// sheet, row, obj, j, i, i + colOffset, rowNO);
			// maxRowOffset = rowAndColl[0] > maxRowOffset ? rowAndColl[0]
			// : maxRowOffset;
			// // 首行数据时，对标题做出调整
			// /*
			// * if (j == 0 && rowAndColl[1] > 0) {
			// * autoMoveColumnTitle(sheetHSSF, rowNO - 1, i +
			// * colOffset, rowAndColl[1], columnTitleStyle); }
			// */
			// colOffset = colOffset + rowAndColl[1];
			// }
			// if (rowNO != maxRowOffset) {
			// // 自动调整合并单元格
			// autoMergedRegion(sheetHSSF, j + rowNO,
			// j + maxRowOffset,
			// colOffset + oneRow.length - 1, contentStyle);
			// rowNO = maxRowOffset;
			// }
			// } else {
			// createCell(contentStyle, workbook, sheet, row, obj, j);
			// }
			// }
			/* 创建内容 END */
			addSheetMerge(sheetHSSF, sheetMerge, sheetIndex);
			if (selectContentMap != null && selectContentMap.size() > 0) {
				for (Integer key : selectContentMap.keySet()) {// keySet获取map集合key的集合 然后在遍历key即可
					String[] value = selectContentMap.get(key);//
					sheetHSSF.addValidationData(setDataValidation(sheetHSSF, value, 1, Short.MAX_VALUE, key, key));
				}
			}
			sheetIndex++;
		}
		if (outputStream != null) {
            wb.write(outputStream);
        } else if (response != null) {
            wb.write(response.getOutputStream());
        } else {
            throw new IllegalArgumentException(
                    "outputStream和response不能同时为NULL！");
        }
	}

	/**
	 * 合并单元格
	 * @param sheetHSSF
	 * @param sheetMerge
	 * @param sheetIndex
	 */
	private void addSheetMerge(HSSFSheet sheetHSSF, List<List<Integer []>> sheetMerge, int sheetIndex) {
		
		try {
			if (sheetMerge == null){
				return;
			}
			List<Integer[]> sheetMergeData = sheetMerge.get(sheetIndex);
			if (sheetMergeData == null || sheetMergeData.isEmpty()){
				return;
			}
			CellRangeAddress range;
			for (Integer[] datas : sheetMergeData) {
				range = new CellRangeAddress(datas[0], datas[1], datas[2], datas[3]);
				com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheetHSSF, range);
			}
		} catch (Exception e) {
		}
	}
	/**
	 * 判断是否是数字，包括正负、整数和小数
	 * @param str
	 * @return: boolean
	 * @author: wangjf
	 * @time: 2022/5/24 10:19 上午
	 */
	private boolean isNumber(String str) {
		if(com.landray.kmss.util.StringUtil.isNull(str)){
			return false;
		}
		//可以判断正负、整数小数
		boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
		boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
		return isInt || isDouble;
	}

	/**
	 * 同一sheet缓存单元格样式，防止excel单元格样式过多导致样式无效的问题（wps不存在该问题）
	 */
	private Map<String, HSSFCellStyle> hssfCellStyleMap = new HashMap<String, HSSFCellStyle>();
	/**
	 * 设置单元格格式
	 * 
	 * @param cell
	 */
	private void setCellFormat(HSSFWorkbook wb, HSSFCell cell, Column column,
			String cellValue) {
		HSSFCellStyle style = column.getStyle();
		String type = column.getType();
		//如果列上没有类型
		if (StringUtil.isNull(type)) {
			cell.setCellValue(cellValue);
			//如果不是数字类型，则格化式成文本类型，如果是数字类型则原封不动
			if(!isNumber(cellValue)) {
				style.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
				type = "String";
			}else{
				//说明是数字类型
				type = "Number";
			}
			//防止excel单元格样式无效的问题
			if(hssfCellStyleMap.get(type) != null){
				style = hssfCellStyleMap.get(type);
			}else{
				hssfCellStyleMap.put(type, style);
			}
			try {
				cell.setCellStyle(style);
			} catch (IllegalArgumentException iArgEx) {
				/**
				 * 问题单：#168630 【统一印章管理】后台配置-参数设置-印章主体设置，导出无序号的印章主体，打开文件报错
				 * .setCellStyle()方法不是同一个wookbook时会报错，需要使用.cloneStyleFrom()方法进行设置
				 * Caused by: java.lang.IllegalArgumentException: This Style does not belong to the supplied Workbook.
				 * Are you trying to assign a style from one workbook to the cell of a differnt workbook?
				 */
				logger.warn("设置单元格出错,使用cloneStyleFrom方法再次进行设置,详情请开启DEBUG级别");
				if (logger.isDebugEnabled()) {
					logger.debug("设置单元格出错,使用cloneStyleFrom方法再次进行设置", iArgEx);
				}
				cell.getCellStyle().cloneStyleFrom(style);
			}
			return;
		}
		Locale locale = new Locale(ResourceUtil.getLocaleStringByUser());
		HSSFDataFormat format = wb.createDataFormat();
		// Boolean Integer Long Double String RTF Date Time DateTime Blob
		try {
			if (type.startsWith("Integer")) {
				if (StringUtil.isNotNull(cellValue)) {
                    cell.setCellValue(Integer.parseInt(cellValue));
                }
				style.setDataFormat(format.getFormat("0"));
			} else if (type.startsWith("Long")) {
				if (StringUtil.isNotNull(cellValue)) {
                    cell.setCellValue(Long.parseLong(cellValue));
                }
				style.setDataFormat(format.getFormat("0"));
			} else if (type.startsWith("Double")) {
				if (StringUtil.isNotNull(cellValue)) {
                    cell.setCellValue(Double.parseDouble(cellValue));
                }
				style.setDataFormat(format.getFormat(
								getScaleFormatStr(type.substring(6))));
			} else if (type.startsWith("BigDecimal")) {
				if (StringUtil.isNotNull(cellValue)) {
                    cell.setCellValue(Double.parseDouble(cellValue));
                }
				style.setDataFormat(format.getFormat(
								getScaleFormatStr(type.substring(10))));
			} else if (type.startsWith("DateTime")) { // 日期也有可能会有小数位，类似DateTime-1，DateTime判断一定要在Date之前
				if (StringUtil.isNotNull(cellValue)) {
					Date d = null;
					// 没有时分秒 默认为00:00:00
					if (cellValue.length() <= 10) {
						d = DateUtil.convertStringToDate(cellValue,
								DateUtil.TYPE_DATE, locale);
					} else {
						d = DateUtil.convertStringToDate(cellValue,
								DateUtil.TYPE_DATETIME, locale);
					}
					cell.setCellValue(d);
				}
				// "yyyy-m-d h:mm"
				style.setDataFormat(
						format.getFormat(DateUtil.PATTERN_DATETIME));
			} else if (type.startsWith("Date")) {
				if (StringUtil.isNotNull(cellValue)) {
					Date d = DateUtil.convertStringToDate(cellValue,
							DateUtil.TYPE_DATE, locale);
					cell.setCellValue(d);
				}
				// "yyyy-m-d"
				style.setDataFormat(format.getFormat(DateUtil.PATTERN_DATE));
			} else if (type.startsWith("Time")) {
				if (StringUtil.isNotNull(cellValue)) {
					Date d = DateUtil.convertStringToDate(cellValue, DateUtil.TYPE_TIME,
							locale);
					cell.setCellValue(d);
				}
				style.setDataFormat(format.getFormat("h:mm:ss"));
			} else {
				cell.setCellValue(cellValue);
				// 默认为文本类型
				style.setDataFormat(format.getFormat("@"));
				type = "String";
			}
			//防止excel单元格样式无效的问题
			if(hssfCellStyleMap.get(type) != null){
				style = hssfCellStyleMap.get(type);
			}else{
				hssfCellStyleMap.put(type, style);
			}
			cell.setCellStyle(style);
		} catch (Exception e) {
			cell.setCellValue(cellValue);
			try {
				cell.setCellStyle(style);
			} catch (IllegalArgumentException iArgEx) {
				/**
				 * 问题单：#168630 【统一印章管理】后台配置-参数设置-印章主体设置，导出无序号的印章主体，打开文件报错
				 * .setCellStyle()方法不是同一个wookbook时会报错，需要使用.cloneStyleFrom()方法进行设置
				 * Caused by: java.lang.IllegalArgumentException: This Style does not belong to the supplied Workbook.
				 * Are you trying to assign a style from one workbook to the cell of a differnt workbook?
				 */
				logger.warn("设置单元格出错,使用cloneStyleFrom方法再次进行设置,详情请开启DEBUG级别");
				if (logger.isDebugEnabled()) {
					logger.debug("设置单元格出错,使用cloneStyleFrom方法再次进行设置", iArgEx);
				}
				cell.getCellStyle().cloneStyleFrom(style);
			}
			// e.printStackTrace();
			// logger.error("excel导出数据格式转换失败 Column:" + column.getTitle()
			// + " , Value:" + cellValue + " , Type:" + type);
		}
	}

	/**
	 * 获得数字显示小数格式字符串
	 * 
	 * @param scale
	 * @return
	 */
	private String getScaleFormatStr(String scaleStr) {
		if (StringUtil.isNull(scaleStr)) {
			return DEFAULT_NUMBER_FORMAT;
		}
		int scale = Integer.parseInt(scaleStr);
		String formatStr = "0";
		if (scale > 0) {
			formatStr += ".";
			while (scale-- > 0) {
				formatStr += "0";
			}
			return formatStr;
		} else if (scale == 0) {
			return formatStr;
		} else {
			return DEFAULT_NUMBER_FORMAT;
		}
	}

	private int[] createDetailCell(HSSFWorkbook wb, HSSFSheet sheetHSSF,
			HSSFCellStyle columnTitleStyle, HSSFCellStyle contentStyle,
			HSSFCellStyle linkStyle, WorkBook workbook, Sheet sheet,
			HSSFRow row, Object obj, int j, int i, int currCol, int rowNO)
			throws Exception {
		int colOffset = 0;
		if (isSheetCell(obj)) {
			Sheet subTable = (Sheet) obj;
			int currRow = j + rowNO;
			int lasRowIndex = createSheetCell(wb, sheetHSSF, columnTitleStyle,
					contentStyle, linkStyle, workbook, row, subTable, currCol,
					currRow);
			colOffset = subTable.getColumnList().size() - 1;
			rowNO = rowNO + lasRowIndex - currRow;
		} else {
			createCell(wb, contentStyle, linkStyle, workbook, sheet, row, obj,
					currCol, i);
		}
		return new int[] { rowNO, colOffset };
	}

	private boolean isSheetCell(Object obj) {
		return (obj instanceof Sheet);
	}

	private void autoMoveColumnTitle(HSSFSheet sheetHSSF, int firstRow,
			int firstCol, int offset, HSSFCellStyle columnTitleStyle) {
		HSSFRow row = sheetHSSF.getRow(firstRow);
		int lastCol = row.getLastCellNum();
		for (int index = lastCol + offset - 1; index > firstCol; index--) {
			HSSFCell cell = row.getCell(index);
			if (cell == null) {
				cell = row.createCell(index);
				cell.setCellStyle(columnTitleStyle);
			}
			HSSFCell preCell = row.getCell(index - offset);
			if (preCell != null) {
				cell.setCellValue(preCell.getStringCellValue());
			} else {
				cell.setCellValue("");
			}
		}
		CellRangeAddress range = new CellRangeAddress(firstRow, firstRow,
				firstCol, firstCol + offset);
		com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheetHSSF, range);
	}

	private void autoMergedRegion(HSSFSheet sheetHSSF, int firstRow,
			int lastRow, int lastCol, HSSFCellStyle contentStyle) {
		HSSFRow row = sheetHSSF.getRow(firstRow);
		for (int colIndex = 0; colIndex <= lastCol; colIndex++) {
			HSSFCell cell = row.getCell(colIndex);
			if (cell != null && contentStyle.equals(cell.getCellStyle())) {
				for (int rangeRow = firstRow; rangeRow <= lastRow; rangeRow++) {
					HSSFRow emptyRow = sheetHSSF.getRow(rangeRow);
					HSSFCell emptyCell = emptyRow.getCell(colIndex);
					if (emptyCell == null) {
						emptyCell = emptyRow.createCell(colIndex);
					}
					emptyCell.setCellStyle(contentStyle);
				}
				CellRangeAddress range = new CellRangeAddress(firstRow,
						lastRow, colIndex, colIndex);
				com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheetHSSF, range);
			}
		}
	}

	// 返回值为当前已经占用到的行号
	@SuppressWarnings("unchecked")
	private int createSheetCell(HSSFWorkbook wb, HSSFSheet sheetHSSF,
			HSSFCellStyle columnTitleStyle, HSSFCellStyle contentStyle,
			HSSFCellStyle linkStyle, WorkBook workbook, HSSFRow row,
			Sheet subTable, int currCol, int currRow) throws Exception {
		int startRowNo = currRow;
		int currRowIndex = startRowNo;
		int startColNo = currCol;
		int columnCount = subTable.getColumnList().size();

		/*
		 * HSSFRow titleRow = row; // sheetHSSF.createRow(startRowNo++); // 列标题行
		 * for (int i = 0; i < columnCount; i++) { Column column = (Column)
		 * subTable.getColumnList().get(i); HSSFCell cell =
		 * titleRow.createCell(i + startColNo); if (column.isRedFont())
		 * cell.setCellStyle(buildStyle(wb, "columnTitle", "red")); else
		 * cell.setCellStyle(columnTitleStyle);
		 * cell.setCellValue(getColumnName(workbook, subTable, column)); }
		 * 
		 * startRowNo++;
		 */
		for (int rowIndex = 0, n = subTable.getContentList().size(); rowIndex < n; rowIndex++) { // j为行号
			currRowIndex++;
			Object obj = subTable.getContentList().get(rowIndex);
			HSSFRow contentRow = sheetHSSF.getRow(rowIndex + startRowNo);
			if (contentRow == null) {
				contentRow = sheetHSSF.createRow(rowIndex + startRowNo);
			}
			if (obj instanceof Object[]) {
				Object[] oneRow = (Object[]) obj;
				for (int colIndex = 0; colIndex < oneRow.length; colIndex++) { // i为列号，从0开始
					obj = oneRow[colIndex];
					createCell(wb, contentStyle, linkStyle, workbook, subTable,
							contentRow, obj, colIndex + startColNo, colIndex);
				}
			} else if (obj instanceof List) {
				List oneRow = (List) obj;
				for (int colIndex = 0; colIndex < oneRow.size(); colIndex++) { // i为列号，从0开始
					obj = oneRow.get(colIndex);
					createCell(wb, contentStyle, linkStyle, workbook, subTable,
							contentRow, obj, colIndex + startColNo, colIndex);
				}
			} else {
				createCell(wb, contentStyle, linkStyle, workbook, subTable,
						contentRow, obj, startColNo);
			}
		}

		return currRowIndex;
	}

	private void createCell(HSSFWorkbook wb, HSSFCellStyle contentStyle,
			HSSFCellStyle linkStyle, WorkBook workbook, Sheet sheet,
			HSSFRow row, Object obj, int cellIndex, int dataIndex)
			throws Exception {
		String cellValue;
		int size = sheet.getColumnList().size();
		Column column;
		if (size >= dataIndex + 1) {
			column = (Column) sheet.getColumnList().get(dataIndex);
		} else {
			column = new Column();
		}
		HSSFCell cell = row.createCell(cellIndex);
		HSSFCellStyle cellStyle = column.getStyle();
		if (cellStyle == null) {
			cellStyle = buildStyle(wb, "content");
			column.setStyle(cellStyle);
		} else {
			cellStyle.cloneStyleFrom(contentStyle);
		}
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		// 锁定内容
		if (sheet.getLockColum() - 1 < cellIndex) {
			unLockCellStyle_content.cloneStyleFrom(contentStyle);
			unLockCellStyle_content.setLocked(false);
			// cell.setCellStyle(unLockCellStyle_content);
			cellStyle.setLocked(false);
		}
		KmssFormat kmssFormat = column.getFormat();
		if (kmssFormat == null && obj instanceof Date
				&& workbook.getDateFormat() != null) {
            kmssFormat = workbook.getDateFormat();
        }
		if (kmssFormat == null
				&& workbook.getNumberFormat() != null
				&& (obj instanceof Double || obj instanceof Float
						|| obj instanceof Long || obj instanceof Integer
						|| obj instanceof Short || obj instanceof Byte)) {
            kmssFormat = workbook.getNumberFormat();
        }
		Object value = null;
		if (obj instanceof JSONObject) {
			JSONObject jobj = (JSONObject) obj;
			value = jobj.get("value");
			HSSFCreationHelper helper = wb.getCreationHelper();
			HSSFHyperlink link = helper.createHyperlink(org.apache.poi.common.usermodel.HyperlinkType.URL);
			link.setAddress((String) jobj.get("fdLink"));
			// HSSFWorkbook wb = new HSSFWorkbook();
			// HSSFCellStyle hlink_style = wb.createCellStyle();
			// HSSFFont hlink_font = wb.createFont();
			// hlink_font.setUnderline(HSSFFont.U_SINGLE);
			// hlink_font.setColor(HSSFColor.BLUE.index);
			// hlink_style.setFont(hlink_font);
			// cell.setCellStyle(hlink_style);
			// cell.setCellStyle(linkStyle);
			cellStyle.cloneStyleFrom(linkStyle);
			cell.setHyperlink(link);
		} else {
			value = obj;
		}
		
		if (kmssFormat != null) {
			cellValue = kmssFormat.format(value);
		} else {
			if (value != null) {
				cellValue = value.toString();
				/**
				 * 超出excel单元格最大长度则截取并追加提示说明
				 */
				if (cellValue.getBytes().length >= 32767) {
					cellValue = cellValue.substring(0, 10000)
							+ "（内容过长请直接到系统中查看）";
				}
			} else {
                cellValue = null;
            }
		}
		setCellFormat(wb, cell, column, cellValue);
	}

	private void createCell(HSSFWorkbook wb, HSSFCellStyle contentStyle,
			HSSFCellStyle linkStyle, WorkBook workbook, Sheet sheet,
			HSSFRow row, Object obj, int i) throws Exception {
		createCell(wb, contentStyle, linkStyle, workbook, sheet, row, obj, i,
				i);
	}

	private HSSFCellStyle buildStyle(HSSFWorkbook wb, String type,
			String color, Map<String, List<CellStyleEnum>> styleMap) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.index);
		style.setBottomBorderColor(IndexedColors.BLACK.index);
		style.setLeftBorderColor(IndexedColors.BLACK.index);
		style.setRightBorderColor(IndexedColors.BLACK.index);
		HSSFFont font = wb.createFont();
		font.setFontName("微软雅黑");
		if ("red".equals(color)) {
            font.setColor(HSSFFont.COLOR_RED);
        }
		if ("title".equals(type)) { // 表格标题，粗体，居中，12号字
			style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			font.setFontHeightInPoints((short) 12);
			font.setBold(true);
			style.setFont(font);
		} else if ("columnTitle".equals(type)) { // 表格列标题，粗体，居中，10号字
			style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			font.setFontHeightInPoints((short) 10);
			font.setBold(true);
			style.setFont(font);
		} else if ("content".equals(type)) { // 内容，10号字
			buildConfigStyle(style, "content", styleMap);
			font.setFontHeightInPoints((short) 10);
			style.setFont(font);
		} else if ("tip".equals(type)) { // 提示,居左，10号字,红色
			font.setColor(HSSFFont.COLOR_RED);
			style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT);
			font.setFontHeightInPoints((short) 10);
			font.setBold(true);
			style.setFont(font);
		} else if ("link".equals(type)) {
			font.setUnderline(HSSFFont.U_SINGLE);
			font.setColor(IndexedColors.BLUE.index);
			style.setFont(font);
		}
		return style;
	}
	/**
	 * 添加自定义样式
	 * @param style
	 * @param part
	 * @param styleMap
	 */
	private void buildConfigStyle(HSSFCellStyle style, String part,
			Map<String, List<CellStyleEnum>> styleMap) {
		try {
			if (styleMap == null){
				return;
			}
			List<CellStyleEnum> cellStyles = styleMap.get(part);
			if (cellStyles == null){
				return;
			}
			for (CellStyleEnum cellStyleEnum : cellStyles) {
				CellStyleEnum.geneCellStyle(style, cellStyleEnum);
			}
		} catch (Exception e) {
		}
	}
	/**
	 * 添加自定义样式
	 * @param wb
	 * @param type
	 * @param styleMap
	 * @return
	 */
	private HSSFCellStyle buildStyleByMap(HSSFWorkbook wb, String type, Map<String, List<CellStyleEnum>> styleMap) {
		return buildStyle(wb, type, null, styleMap);
	}
	private HSSFCellStyle buildStyle(HSSFWorkbook wb, String type) {
		return buildStyle(wb, type, null, null);
	}

	private String getColumnName(WorkBook workbook, Sheet sheet, Column column)
			throws Exception {
		String columnName;
		if (column.getTitleKey() != null) { // 如果设置了TitleKey，则优先使用
			String bundle = column.getBundle();
			if (bundle == null) {
                bundle = sheet.getBundle();
            }
			if (bundle == null) {
                bundle = workbook.getBundle();
            }
			if (bundle == null) {
                throw new IllegalArgumentException(
                        "如果要使用资源文件，则Column、Sheet和WorkBook的bundle属性不能同时为空！");
            }
			Locale locale = workbook.getLocale();
			columnName = ResourceUtil.getString(column.getTitleKey(), bundle,
					locale);
			if (columnName == null) {
                throw new Exception("获取资源失败，Key:" + column.getTitleKey()
                        + " Bundle:" + bundle + " Locale:" + locale);
            }
		} else {
            columnName = column.getTitle();
        }
		if (columnName == null) {
            throw new IllegalArgumentException(
                    "Column对象的title属性和titleKey属性不能同时为空！");
        }
		return columnName;
	}

	private String getSheetName(WorkBook workbook, Sheet sheet) {
		String sheetName;
		if (sheet.getTitleKey() != null) { // 如果设置了TitleKey，则优先使用
			String bundle = sheet.getBundle();
			if (bundle == null) // 如果Sheet没有设置bundle，则使用WorkBook的
            {
                bundle = workbook.getBundle();
            }
			if (bundle == null) {
                throw new IllegalArgumentException(
                        "如果要使用资源文件，则Sheet和WorkBook的bundle属性不能同时为空！");
            }
			Locale locale = workbook.getLocale();
			sheetName = ResourceUtil.getString(sheet.getTitleKey(), bundle,
					locale);
		} else {
            sheetName = sheet.getTitle();
        }
		if (sheetName == null) {
            throw new IllegalArgumentException(
                    "Sheet对象的title属性和titleKey属性不能同时为空！");
        }
		/* 替换掉一些特殊字符（ /\?*[] ） */
		String pattern = "[/\\\\\\?\\*\\[\\]]+";
		sheetName = sheetName.replaceAll(pattern, "");
		/* 如果长度超过31，则截掉，加上三个句点“.” */
		if (sheetName.length() > 31) {
            sheetName = sheetName.substring(0, 28) + "...";
        }
		return sheetName;
	}

	private HttpServletResponse response;

	public void output(WorkBook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.reset();
		response.setContentType("application/vnd.ms-excel;");
		if (workbook == null) {
            return;
        }
		String filename = workbook.getFilename();
		if (filename == null) {
			logger.warn("workbook中未设置filename属性，当前使用缺省值“Noname”。");
			filename = "Noname";
		}
		filename += ".xls";
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.contains("MSIE")
				|| (userAgent.contains("rv") && userAgent.contains("Trident"))
				|| userAgent.contains("Edge")) {
			filename = URLEncoder.encode(filename, "UTF-8");
		} else if (userAgent.contains("Mozilla")) {
			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
		} else {
			filename = URLEncoder.encode(filename, "UTF-8");
		}
		logger.debug("filename=" + filename);
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ filename + "\"");
		this.response = response;
		OutputStream outputStream = null;
		output(workbook, outputStream);
	}
	
	@Override
	public void output(WorkBook workbook, HttpServletResponse response)
			throws Exception {
		outputByMerge(workbook, response, null, null);
	}
	
	/**
	 * 导出含有合并单元格和自定义样式的文件
	 */
	@Override
	public void outputByMerge(WorkBook workbook, HttpServletResponse response,
			List<List<Integer []>> sheetMerge, Map<String, List<CellStyleEnum>> styleMap) throws Exception {
		output(workbook, response, "GBK", sheetMerge, styleMap);
	}
	
	public void output(WorkBook workbook, HttpServletResponse response,
			String coding) throws Exception {
		output(workbook, response, coding, null, null);
	}
	public void output(WorkBook workbook, HttpServletResponse response,
			String coding, List<List<Integer []>> sheetMerge, Map<String, List<CellStyleEnum>> styleMap) throws Exception {
		response.reset();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		if (workbook == null) {
            return;
        }
		String filename = workbook.getFilename();
		if (filename == null) {
			logger.warn("workbook中未设置filename属性，当前使用缺省值“Noname”。");
			filename = "Noname";
		}

		String userAgent = Plugin.currentRequest().getHeader("User-Agent").toUpperCase();
		if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("TRIDENT") > -1
				|| userAgent.indexOf("EDGE") > -1
				|| (MobileUtil.DING_ANDRIOD == MobileUtil
				.getClientType(Plugin.currentRequest()))) {// ie情况处理
			filename = URLEncoder.encode(filename, "UTF-8");
			// 这里的编码后，空格会被解析成+，需要重新处理
			filename = filename.replace("+", "%20");
		} else {
			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
		}
		filename = filename + ".xls";
		// safari的情况下，coding的解析类型必须是UTF-8才不会导致导出的名字乱码
		//filename = new String(filename.getBytes(coding), "ISO-8859-1") + ".xls";
		logger.debug("filename=" + filename);
		// 火狐导出excel的时候，如果文件名含有空格，会截断后面的字符串，导致下载下来的文件无法解析 by zhugr 2017-11-09
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ filename + "\"");
		this.response = response;
		OutputStream outputStream = null;
		output(workbook, outputStream, sheetMerge, styleMap);
	}

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	class SheetRowBuilder {
		int firstRow = 0; // 主行在所有行中行号
		int colOffset = 0; // 列偏移
		int rowSpan = 1;
		int rowOffset = 0;
		Object rowObj = null;

		HSSFSheet sheetHSSF;
		HSSFRow[] rows;

		HSSFCellStyle contentStyle;
		HSSFCellStyle linkStyle;
		WorkBook workbook;
		HSSFWorkbook wb;
		Sheet sheet;

		boolean isListRowObj() {
			return (rowObj instanceof Object[] || rowObj instanceof List);
		}

		Object[] getOneRow() {
			return (rowObj instanceof List) ? ((List) rowObj).toArray()
					: (Object[]) rowObj;
		}

		void setRowSpan() {
			// 确定最大跨行数
			if (isListRowObj()) {
				Object[] oneRow = getOneRow();
				for (int i = 0; i < oneRow.length; i++) {
					Object oneCol = oneRow[i];
					if (oneCol instanceof Sheet) {
						int size = ((Sheet) oneCol).getContentList().size();
						if (size > rowSpan) {
							rowSpan = size;
						}
					}
				}
			}
		}

		void createRows() {
			// 创建所有行
			if (rows != null) {
                return;
            }
			rows = new HSSFRow[rowSpan];
			for (int i = 0; i < rowSpan; i++) {
				rows[i] = sheetHSSF.createRow(firstRow + i);
			}
		}

		void build() throws Exception {
			setRowSpan();
			createRows();

			if (isListRowObj()) {
				Object[] oneRow = getOneRow();

				for (int i = 0; i < oneRow.length; i++) {
					Object oneCol = oneRow[i];
					if (oneCol instanceof Sheet) {
						Sheet colSheet = (Sheet) oneCol;
						int lastColOffset = colOffset;
						for (Iterator<?> it = colSheet.getContentList()
								.iterator(); it.hasNext();) {
							Object colRowObj = it.next();

							SheetRowBuilder rowBuilder = new SheetRowBuilder();
							rowBuilder.workbook = workbook;
							rowBuilder.contentStyle = contentStyle;
							rowBuilder.linkStyle = linkStyle;
							rowBuilder.sheet = sheet;

							rowBuilder.rowObj = colRowObj;
							rowBuilder.colOffset = colOffset;
							rowBuilder.rowOffset = rowOffset;
							rowBuilder.rows = rows;
							rowBuilder.wb = wb;
							rowBuilder.build();

							rowOffset++;
							lastColOffset = rowBuilder.colOffset;
						}
						colOffset = lastColOffset;
						rowOffset = 0;
					} else {
						createCell(wb, contentStyle, linkStyle, workbook, sheet,
								rows[rowOffset], oneCol, colOffset);
						if (rowSpan > 1) {
							CellRangeAddress range = new CellRangeAddress(
									firstRow, firstRow + rowSpan - 1,
									colOffset, colOffset);
							com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheetHSSF, range);
						}
						colOffset++;
					}
				}
			} else {
				// 单列数据
				createCell(wb, contentStyle, linkStyle, workbook, sheet,
						rows[rowOffset], rowObj, colOffset);
			}
		}
	}

}
