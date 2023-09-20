package com.landray.kmss.hr.staff.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel导出<br/>
 * 支持office07和office03版
 * 
 * @author
 */
public class ExcelExportUtil {
	/**
	 * excel 后缀，用于区分excel版本
	 */
	private PostfixEnum postfix;

	/**
	 * 单元格宽度
	 */
	private int columnWidth;

	/**
	 * 单元格高度
	 */
	private int columnHeight;

	/**
	 * excel的名称
	 */
	private String excelName;

	/**
	 * 页签名称
	 */
	private String sheetName;

	/**
	 * 抬头名称
	 */
	private String headName;

	/**
	 * 标题名称数组
	 */
	private String[] titles;

	/**
	 * 内容集合
	 */
	private List<String[]> contentList;

	/**
	 * 导出IDs add by jiangxk
	 */
	private String exportIds;

	public String getExportIds() {
		return exportIds;
	}

	public void setExportIds(String exportIds) {
		this.exportIds = exportIds;
	}

	/**
	 * 当前会话的response
	 */
	private HttpServletResponse response;

	/**
	 * @param columnWidth
	 *            the columnWidth to set
	 */
	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	/**
	 * @param columnHeight
	 *            the columnHeight to set
	 */
	public void setColumnHeight(int columnHeight) {
		this.columnHeight = columnHeight;
	}

	/**
	 * @return the excelName
	 */
	public String getExcelName() {
		return excelName;
	}

	/**
	 * @param excelName
	 *            the excelName to set
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @param sheetName
	 *            the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * @return the headName
	 */
	public String getHeadName() {
		return headName;
	}

	/**
	 * @param headName
	 *            the headName to set
	 */
	public void setHeadName(String headName) {
		this.headName = headName;
	}

	/**
	 * @param titles
	 *            the titles to set
	 */
	public void setTitles(String[] titles) {
		this.titles = titles.clone();
	}

	/**
	 * @param contentList
	 *            the contentList to set
	 */
	public void setContentList(List<String[]> contentList) {
		this.contentList = contentList;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * excel 的文件输出流
	 */
	private OutputStream outputStream;

	/**
	 * 获取excel 的文件输出流
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * 赋值excel 的文件输出流
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * 内容集合
	 */
	private List<Object[]> formatedContentList;

	public List<Object[]> getFormatedContentList() {
		return formatedContentList;
	}

	public void setFormatedContentList(List<Object[]> formatedContentList) {
		this.formatedContentList = formatedContentList;
	}

	/**
	 * 对Excel操作的对象
	 */
	private Workbook workbook;

	/**
	 * 私有构造器，保证不能创建空对象
	 */
	@SuppressWarnings("unused")
	private ExcelExportUtil() {}

	public ExcelExportUtil(String excelName, String[] titles,
			List<String[]> contentList, PostfixEnum postfix,
			OutputStream outputStream) throws Exception {
		super();
		this.titles = titles.clone();
		this.contentList = contentList;
		this.postfix = postfix;
		this.outputStream = outputStream;
		validationDown();
		/**
		 * 设置默认值，可通过set方式进行修改
		 */
		this.columnWidth = 5000;
		this.columnHeight = 800;
		this.sheetName = excelName;
		this.headName = excelName;
	}

	/**
	 * @param response
	 * @param excelName
	 * @param titles
	 * @param contentList
	 */
	public ExcelExportUtil(HttpServletResponse response, String excelName,
			String[] titles, List<String[]> contentList, PostfixEnum postfix)
			throws Exception {
		super();
		this.response = response;
		this.excelName = excelName;
		this.titles = titles.clone();
		this.contentList = contentList;
		this.postfix = postfix;
		validationDown();
		/**
		 * 设置默认值，可通过set方式进行修改
		 */
		this.columnWidth = 5000;
		this.columnHeight = 800;
		this.sheetName = excelName;
		this.headName = excelName;
	}

	public ExcelExportUtil(HttpServletResponse response, String excelName,
			String[] titles, List<String[]> contentList, PostfixEnum postfix,
			Boolean isDecode) throws Exception {
		super();
		this.response = response;
		this.excelName = excelName;
		this.titles = titles.clone();
		this.contentList = contentList;
		this.postfix = postfix;
		validationDown();
		/**
		 * 设置默认值，可通过set方式进行修改
		 */
		this.columnWidth = 5000;
		this.columnHeight = 800;
		this.sheetName = URLDecoder.decode(excelName, "utf-8");
		this.headName = URLDecoder.decode(excelName, "utf-8");
	}

	/**
	 * @param response
	 * @param excelName
	 * @param titles
	 * @param contentList
	 */
	public ExcelExportUtil(HttpServletResponse response, String excelName,
			String[] titles, List<Object[]> formatedContentList,
			PostfixEnum postfix, String nothing) throws Exception {
		super();
		this.response = response;
		this.excelName = excelName;
		this.titles = titles.clone();
		this.formatedContentList = formatedContentList;
		this.postfix = postfix;
		validationDown();
		/**
		 * 设置默认值，可通过set方式进行修改
		 */
		this.columnWidth = 4000;
		this.columnHeight = 800;
		this.sheetName = excelName;
		this.headName = excelName;
	}

	/**
	 * 设置html的头为excel文件类型
	 * 
	 * @throws Exception
	 */
	private void contentType() throws Exception {
		if (this.outputStream == null) {
			// 设置导出文件名称
			String filename = new String(this.excelName.getBytes("UTF-8"),
					"ISO-8859-1")
					+ postfix.getPostfix();
			this.response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			this.response.addHeader("Content-Disposition",
					"attachment;filename=" + filename);
			this.outputStream = response.getOutputStream();
		}
	}

	/**
	 * @param inputStream
	 *            excel 的文件输入流
	 */
	public ExcelExportUtil(InputStream inputStream) throws Exception {
		if (inputStream == null) {
			throw new Exception("参数不允许为空！");
		}
		try {
			this.workbook = WorkbookFactory.create(inputStream);
		} finally {
			inputStream.close();
		}
	}

	/**
	 * 验证抬头是否是对应的excel模板
	 * 
	 * @param headName
	 *            抬头的名称
	 * @return
	 * @throws Exception
	 */
	public boolean checkHeadName(String headName) throws Exception {
		if (headName == null) {
			throw new Exception("参数不允许为空！");
		}
		this.headName = constrain(
				this.workbook.getSheetAt(0).getRow(0).getCell(0)).toString();
		return headName.equals(this.headName);
	}

	/**
	 * 读一行信息放到数组中
	 * 
	 * @param row
	 * @return
	 */
	private String[] readRow(Row row) throws Exception {
		return this.readRow(row, 0);
	}

	/**
	 * 读一行信息放到特定长度的数组中
	 * 
	 * @param row
	 * @return
	 */
	private String[] readRow(Row row, int length) throws Exception {
		int len = length;
		if (len < 0) {
			throw new Exception("len 的长度不能为负数。");
		} else if (len == 0) {
			len = row.getLastCellNum();
			if (len == 0) {
				throw new Exception("标题行为空，请检查上传的excel模板是否正确！");
			}
		}
		String cloumns[] = new String[len];
		for (int i = 0; i < len; i++) {
			cloumns[i] = constrain(row.getCell(i));
		}
		return cloumns;
	}

	/**
	 * 获取excel中的信息集合
	 * 
	 * @return the contentList
	 */
	public List<String[]> getContentList() throws Exception {
		if (this.contentList == null && this.workbook != null) {
			Sheet sheet = this.workbook.getSheetAt(0);
			int len = sheet.getLastRowNum();
			// 读取标题数组的长度
			int titleLen = getTitles().length;
			Row row;
			String cloumnsProduct[];
			this.contentList = new ArrayList<String[]>();
			for (int i = 2; i <= len; i++) {
				row = sheet.getRow(i);
				if (isValid(row, 5)) {
					cloumnsProduct = readRow(sheet.getRow(i), titleLen);
					contentList.add(cloumnsProduct);
				}
			}
		}
		return contentList;
	}

	/**
	 * 获取excel中的标题数组
	 * 
	 * @return the titles
	 */
	public String[] getTitles() throws Exception {
		if (this.titles == null && this.workbook != null) {
			Sheet sheet = workbook.getSheetAt(0);
			this.titles = readRow(sheet.getRow(1));
		}
		if (titles == null) {
			return new String[0];
		} else {
			return titles.clone();
		}
	}

	/**
	 * 验证是否创建有效对象
	 */
	private void validationDown() throws Exception {
		if (this.outputStream == null
				&& (this.response == null || this.excelName == null
						|| "".equals(this.excelName) || this.titles == null || (this.contentList == null && this.formatedContentList == null))) {
			throw new Exception("Excel构造器参数不允许为空！");
		}
		if (titles.length < 1) {
			throw new Exception("标题内容不允许为空！");
		}
		if (this.response != null) {
			this.response.reset();
		}
		switch (this.postfix) {
			case ENUM_POSTFIX_03:
				this.workbook = new HSSFWorkbook();
				break;
			case ENUM_POSTFIX_07:
				this.workbook = new XSSFWorkbook();
				break;
			default:
				throw new Exception("模板不正确，请使用07或03版的excel");
		}
		// if (contentList.isEmpty()) {
		// throw new Exception("导出内容不允许为空！");
		// }
	}

	public void export() throws Exception {
		try {
			contentType();
			// 获得标题数组
			String[] cloumns = this.titles;
			// 清空输出流
			Sheet sheet = this.workbook.createSheet(sheetName);
			// 合并第一行
			// rowhead(sheet, this.workbook, cloumns, headName);
			// 设置单元格宽度
			for (int i = 0; i < cloumns.length; i++) {

				sheet.setColumnWidth(i, columnWidth);

				if (i == 1) {
					sheet.setColumnWidth(i, 9000);
				}
				if (i == 2) {
					sheet.setColumnWidth(i, 3000);
				}
				if (i == 6) {
					sheet.setColumnWidth(i, 9000);
				}
				if (i == 7) {
					sheet.setColumnWidth(i, 4000);
				}
				if (i == 8) {
					sheet.setColumnWidth(i, 5000);
				}
				if (i == 9) {
					sheet.setColumnWidth(i, 5000);
				}
			}
			// 创建标题样式
			rowTitle(sheet, this.workbook, cloumns);
			// 创建单元格样式
			CellStyle style = style(this.workbook);
			style.setDataFormat(this.workbook.createDataFormat().getFormat("@"));
			Row valueRow;
			Cell cell;
			String[] content;
			StringBuilder ids = new StringBuilder();// add by jiangxk
			for (int i = 0; i < contentList.size(); i++) {
				content = contentList.get(i);
				valueRow = sheet.createRow(i + 1);
				valueRow.setHeight((short) columnHeight);
				for (int j = 0; j < content.length; j++) {
					if (j == 0) {// add by jiangxk
						ids.append(content[j]).append(";");
						continue;
					}
					cell = valueRow.createCell(j - 1);
					cell.setCellStyle(style);
					// 填值
					cell.setCellValue(content[j]);
				}
			}
			this.setExportIds(ids.toString());
			this.workbook.write(this.outputStream);
		} finally {
			if (this.outputStream != null) {
				this.outputStream.flush();// 操作结束，关闭文件
				this.outputStream.close();
			}
		}
	}

	public void exportFormated() throws Exception {
		try {
			contentType();
			// 获得标题数组
			String[] cloumns = this.titles;
			// 清空输出流
			Sheet sheet = this.workbook.createSheet(sheetName);
			// 合并第一行
			// rowhead(sheet, this.workbook, cloumns, headName);
			// 设置单元格宽度
			for (int i = 0; i < cloumns.length; i++) {
				sheet.setColumnWidth(i, columnWidth);
			}
			// 创建标题样式
			rowTitle(sheet, this.workbook, cloumns);
			// 创建单元格样式
			CellStyle style = style(this.workbook);
			Row valueRow;
			Cell cell;
			Object[] content;
			for (int i = 0; i < formatedContentList.size(); i++) {
				content = formatedContentList.get(i);
				valueRow = sheet.createRow(i + 1);
				valueRow.setHeight((short) columnHeight);
				for (int j = 0; j < content.length; j++) {
					cell = valueRow.createCell(j);
					cell.setCellStyle(style);
					// 填值
					if (content[j] instanceof Double) {
						cell.setCellValue((Double) content[j]);
					} else if (content[j] instanceof Integer) {
						cell.setCellValue((Integer) content[j]);
					} else if (content[j] instanceof String) {
						cell.setCellValue((String) content[j]);
					} else if (content[j] instanceof Long) {
						cell.setCellValue((Long) content[j]);
					} else if (content[j] instanceof Float) {
						cell.setCellValue((Float) content[j]);
					}
				}
			}
			this.workbook.write(this.outputStream);
		} finally {
			if (this.outputStream != null) {
				this.outputStream.flush();// 操作结束，关闭文件
				this.outputStream.close();
			}
		}
	}

	/**
	 * 单元格样式
	 * 
	 * @param workBook
	 * @return
	 */
	private CellStyle style(Workbook workBook) {
		CellStyle style = workBook.createCellStyle();
		style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		style.setWrapText(true);// 设置自动换行
		style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT);// 设置单元格字体显示左对齐
		style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);// 设置单元格字体显示居中(上下方向)
		return style;
	}

	/**
	 * 标题样式
	 * 
	 * @param sheet
	 * @param workBook
	 * @param cloumns
	 */
	private void rowTitle(Sheet sheet, Workbook workBook, String[] cloumns) {
		CellStyle styleTitle = workBook.createCellStyle();
		styleTitle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		styleTitle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		styleTitle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		styleTitle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
		Font font = workBook.createFont();// 创建一个字体对象
		font.setFontHeightInPoints((short) 10);// 设置字体的高度
		font.setBold(false);
		styleTitle.setFont(font);// 设置style1的字体
		styleTitle.setWrapText(true);// 设置自动换行
		styleTitle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);// 设置单元格字体显示居中（左右方向）
		styleTitle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);// 设置单元格字体显示居中(上下方向)
		Row rowTitle = sheet.createRow(0);// 第一行 标题
		styleTitle.setFillForegroundColor((short) 3);// 设置背景色
		styleTitle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		rowTitle.setHeight((short) 600);
		for (int i = 0; i < cloumns.length; i++) {
			Cell cell = rowTitle.createCell(i);
			cell.setCellStyle(styleTitle);
			cell.setCellValue(cloumns[i]);
		}
	}

	/**
	 * 判断当前行是否为空
	 * 
	 * @param row
	 * @param cellLength
	 * @return
	 */
	private Boolean isValid(Row row, int cellLength) {
		for (int i = 0; i < cellLength; i++) {
			Cell cell = row.getCell(i);
			if (cell != null && constrain(cell) != null
					&& !"".equals(constrain(cell))) {
				return true;
			}
		}
		return false;
	}

	private String constrain(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		switch (cell.getCellType()) {
			case STRING:
				cellValue = typeStr(cell);
				break;
			case NUMERIC:
				cellValue = typeNumeric(cell);
				break;
			case FORMULA:
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			case BLANK:
				cellValue = " ";
				break;
			case BOOLEAN:
				break;
			case ERROR:
				break;
			default:
				break;
		}
		return cellValue;
	}

	private String typeStr(Cell cell) {
		String cellValue = cell.getStringCellValue().trim();
		if ("".equals(cellValue) || cellValue.length() <= 0) {
			cellValue = " ";
		}
		return cellValue;
	}

	private String typeNumeric(Cell cell) {
		DateFormat simpleDateFormat = SimpleDateFormat.getDateTimeInstance();
		String cellValue = null;
		if (DateUtil.isCellDateFormatted(cell)) {
			// 用于转化为日期格式
			Date d = cell.getDateCellValue();
			cellValue = simpleDateFormat.format(d);
		} else {
			cellValue = String.valueOf(cell.getNumericCellValue());
		}
		return cellValue;
	}

	/**
	 * excel的版本格式 分别是office07版的“.xlsx”和office03版的“.xls”
	 * 
	 * @author Maple
	 * @since 2014年10月25日
	 */
	public enum PostfixEnum {
		ENUM_POSTFIX_03(".xls"), ENUM_POSTFIX_07(".xlsx");
		PostfixEnum(String postfix) {
			this.postfix = postfix;
		}

		private String postfix;

		public String getPostfix() {
			return postfix;
		}

		public void setPostfix(String postfix) {
			this.postfix = postfix;
		}
	}
}
