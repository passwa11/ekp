package com.landray.kmss.sys.transport.service.parse;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSimpleProperty;
import com.landray.kmss.sys.transport.service.ISysTransportImportPropertyParse;
import com.landray.kmss.sys.transport.service.spring.ImportInDetailsCellContext;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.sys.transport.service.spring.SysTransportTableUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class SysTransportImportDateParse
		implements ISysTransportImportPropertyParse {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private final static String DATE_YEAR_PATTERN = "yyyy";
	private final static String DATE_YEAR_M_PATTERN = "yyyy-MM";
	@Override
	public boolean parse(ImportInDetailsCellContext detailsCellContext)
			throws Exception {
		Locale locale = ResourceUtil.getLocaleByUser();

		//日期格式
		String datePattern = ResourceUtil.getString("date.format.date", locale);

		//日期时间格式
		String dateTimePattern = ResourceUtil.getString("date.format.datetime", locale);


		SysDictCommonProperty property = detailsCellContext.getProperty();
		String propertyName = detailsCellContext.getPropertyName();
		Cell cell = detailsCellContext.getCell();
		KmssMessages contentMessage = detailsCellContext.getContentMessage();
		Map<String, String> temp = detailsCellContext.getTemp();
		int i = detailsCellContext.getIndex();

		String cellString = ImportUtil.getCellValue(cell);

		// 格式错误标志
		JSONObject dateJSON = new JSONObject();
		boolean isDate = true;
		if ("Time"
				.equalsIgnoreCase(property.getType())) {
			dateJSON = detailWithDateCell(
					"HH:mm", cell, cellString);
		} else if ("Date"
				.equalsIgnoreCase(property.getType())) {
			// 增加日期维度年月以及年的日期解析
			if (property instanceof SysDictExtendSimpleProperty) {
				String dimension = ((SysDictExtendSimpleProperty) property).getDimension();
				if ("yearMonth".equals(dimension)) {
					dateJSON = detailWithDateCell(
							DATE_YEAR_M_PATTERN, cell,
							cellString);
				} else if ("year".equals(dimension)) {
					dateJSON = detailWithDateCell(
							DATE_YEAR_PATTERN, cell,
							cellString);
				}else{
					dateJSON = detailWithDateCell(
							datePattern, cell,
							cellString);
				}
			}else{
				dateJSON = detailWithDateCell(
						datePattern, cell,
						cellString);
			}

		} else if ("DateTime"
				.equalsIgnoreCase(property.getType())) {
			dateJSON = detailWithDateCell(
					dateTimePattern, cell,
					cellString);
		} else {
			KmssMessage message = new KmssMessage(
					"sys-transport:sysTransport.import.dataError.notDate",
					i + 1,
					SysTransportTableUtil
							.getSysSimpleOrExtendPropertyMessage(
									property, locale));
			contentMessage.addError(message);
			temp.put(propertyName, "");
			return false;
		}
		cellString = dateJSON
				.getString("cellString");
		detailsCellContext.setCellString(cellString);
		isDate = dateJSON.getBoolean("isDate");
		if (!isDate) {
			// 把值清空，不然还是可以导入
			cellString = "";
			KmssMessage message = new KmssMessage(
					"sys-transport:sysTransport.import.dataError.dateFormatFalse",
					i + 1,
					SysTransportTableUtil
							.getSysSimpleOrExtendPropertyMessage(
									property, locale));
			contentMessage.addError(message);
			temp.put(propertyName, "");
			return false;
		}
		return true;
	}

	/**
	 * 处理日期单元格
	 *
	 * @param type
	 * @param cell
	 * @return
	 */
	private JSONObject detailWithDateCell(String pattern, Cell cell,
										  String oldCellString) {
		JSONObject result = new JSONObject();
		String cellString = oldCellString;
		boolean isDate = true;
		try {
			if(DATE_YEAR_M_PATTERN.equals(pattern) || DATE_YEAR_PATTERN.equals(pattern)){
				cellString = getDateString(pattern, pattern,cell);
			}else{
				cellString = getDateString(pattern, cell);
			}
			if (!isDate(pattern, cellString)) {
				isDate = false;
			}
		} catch (Exception e) {
			isDate = false;
		}
		result.accumulate("cellString", cellString);
		result.accumulate("isDate", isDate);
		return result;
	}

	/**
	 * 获取日期维度单元格的值
	 *
	 * @param pattern
	 *
	 * @param string
	 * @param cell
	 * @param dimension 日期维度
	 * @return
	 * @throws Exception
	 */
	private String getDateString(String pattern, String dimension,Cell cell) throws Exception {
		if (cell == null) {
            return null;
        }
		String rtnStr = "";

		// 日期维度年
		String dateYearPattern = "yyyy";

		// 日期维度年月
		String dateYearMPattern = "yyyy-MM";

		switch (cell.getCellType()) {
			case NUMERIC: {
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell) || dateYearPattern.equals(dimension) || dateYearMPattern.equals(dimension)) {// 处理日期、时间格式
					// 统一格式化为最长的，后面根据格式类型截取
					SimpleDateFormat sdf = new SimpleDateFormat(dimension);
					// 处理日期转化会17：30：00会转换为17：29：59的情况
					Date cellDate = cell.getDateCellValue();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(cellDate);
					int sec = calendar.get(Calendar.SECOND);
					if (sec >= 30) {
						calendar.add(Calendar.MINUTE, 1);
					}
					calendar.set(Calendar.SECOND, 0);
					rtnStr = sdf.format(calendar.getTime());
				}
				break;
			}
			case STRING:
				rtnStr = cell.getRichStringCellValue().getString();
				break;
			default:
				throw new Exception();
		}
		return ImportUtil.formatString(rtnStr.trim());
	}

	/**
	 * 获取日期单元格的值
	 *
	 * @param pattern
	 *
	 * @param string
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	private String getDateString(String pattern, Cell cell) throws Exception {
		if (cell == null) {
            return null;
        }
		String rtnStr = "";

		//日期格式
		String datePattern = ResourceUtil.getString("date.format.date", ResourceUtil.getLocaleByUser());

		//日期时间格式
		String dateTimePattern = ResourceUtil.getString("date.format.datetime", ResourceUtil.getLocaleByUser());

		switch (cell.getCellType()) {
			case NUMERIC: {
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {// 处理日期、时间格式
					// 统一格式化为最长的，后面根据格式类型截取
					SimpleDateFormat sdf = new SimpleDateFormat(dateTimePattern);
					// 处理日期转化会17：30：00会转换为17：29：59的情况
					Date cellDate = cell.getDateCellValue();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(cellDate);
					int sec = calendar.get(Calendar.SECOND);
					if (sec >= 30) {
						calendar.add(Calendar.MINUTE, 1);
					}
					calendar.set(Calendar.SECOND, 0);
					rtnStr = sdf.format(calendar.getTime());
					// Excel单元格的类型
					short dataFormat = cell.getCellStyle()
							.getDataFormat();
					String format = HSSFDataFormat.getBuiltinFormat(dataFormat);
					boolean canRecognite = true;
					if (StringUtil.isNull(format)) {
						canRecognite = false;
					}
					// 如果无法识别格式，则不校验格式，直接根据模板配置的格式截取
					if ((!canRecognite || "h:mm".equals(format)
							|| "h:mm:ss".equals(format))
							&& "HH:mm".equals(pattern)) {
						rtnStr = rtnStr.substring(11,
								16);
					} else if ((!canRecognite || "m/d/yy".equals(format))
							&& pattern.equals(datePattern)) {
						rtnStr = rtnStr.substring(0,
								10);
					} else if ((!canRecognite || "m/d/yy h:mm".equals(format))
							&& pattern.equals(dateTimePattern)) {

					} else {
						throw new Exception();
					}
				}
				break;
			}
			case STRING:
				rtnStr = cell.getRichStringCellValue().getString();
				break;
			default:
				throw new Exception();
		}
		return ImportUtil.formatString(rtnStr.trim());
	}

	/**
	 * 日期校验
	 *
	 * @param pattern
	 * @param cellString
	 * @return
	 */
	private boolean isDate(String pattern, String cellString) {
		boolean isDate = true;
		if (pattern.length() != cellString.length()) {
			return false;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(false);
			// 此处的校验只对前面的数据解析，如果是2017-7-12jflkjlkdsjlkfas，校验还是能够通过的，故上面加多了一个长度匹配的校验
			// by zhugr 2017-07-24
			sdf.parse(cellString);
		} catch (ParseException e) {
			logger.warn("日期时间(" +
					cellString + ")不是标准格式(" + pattern + ")！");
			isDate = false;
		}

		return isDate;
	}
}

