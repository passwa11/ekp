package com.landray.kmss.sys.transport.service.spring;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ImportUtil {
	private static final String EXCEPT_STRING = "¤§°±·×àáèéêìíòó÷ùúü";

	private static DecimalFormat formatter = new DecimalFormat(
			"####################.#########");

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
		case FORMULA:{
			rtnStr = formatter.format(cell.getNumericCellValue());
			break;
		}
		case NUMERIC:{
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {// 处理日期、时间格式
				Date date = cell.getDateCellValue();
                if(String.valueOf(date).contains("00:00:00")){
                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                	rtnStr = sdf.format(date);
                }else {
                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                	rtnStr = sdf.format(date);
				}  
            } else{
				Double d=cell.getNumericCellValue() ;
				cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
				rtnStr = cell.getRichStringCellValue().getString();
				cell.setCellValue(d);
				cell.setCellType(org.apache.poi.ss.usermodel.CellType.NUMERIC) ;
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
	 * 获取Excel单元格的值，并且自动转换类型
	 * 
	 * @param cell
	 * @param property
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public static Object getCellValue(Cell cell,
			SysDictCommonProperty property, Locale locale) throws Exception {
		String value = getCellValue(cell);
		if (value == null) {
            return null;
        }
		if (!StringUtil.isNull(property.getEnumType())) {

			value = EnumerationTypeUtil.getColumnValueByLabel(property
					.getEnumType(), value, locale);
			if (value == null) {
                throw new Exception(ResourceUtil.getString(
                        "sys-transport:sysTransport.import.dataError.enumNotExistVal",
                        null, locale,
                        new Object[] { ResourceUtil.getString(property
                                .getMessageKey(), locale),
                                StringEscapeUtils
                                        .escapeHtml(getCellValue(cell)) }));
            }
			return transTypeFromString(value, property, locale);
		}
		String type = property.getType();
		try {
			if ("Integer".equals(type)) {
                return new Integer(
                        (int) Math.round(cell.getNumericCellValue()));
            }
			if ("Long".equals(type)) {
                return new Long(Math.round(cell.getNumericCellValue()));
            }
			if ("Double".equals(type)) {
                return new Double(cell.getNumericCellValue());
            }
			if ("Date".equals(type)) {
				return DateUtil.convertStringToDate(getCellValue(cell),
						DateUtil.PATTERN_DATE);
			}
			if("BigDecimal".equals(type)){
				return new BigDecimal(cell.getNumericCellValue());
			}
			if ("DateTime".equals(type)) {
				String cellValue = getCellValue(cell);
				// 没有时分秒 默认为00:00:00
				if (cellValue.length() <= 10) {
					return DateUtil.convertStringToDate(cellValue,
							DateUtil.PATTERN_DATE);
				}
				return DateUtil.convertStringToDate(cellValue,
						DateUtil.PATTERN_DATETIME);

			}
			if ("Time".equals(type)) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String time = getCellValue(cell);
				// 只有日期没有时分秒 默认为00:00:00
				if (time.length() <= 10) {
					time = "00:00:00";
				}
				if(time.indexOf("-")>-1) {
					Date date = DateUtil.convertStringToDate(getCellValue(cell),
							"yyyy-MM-dd HH:mm:ss");
					time = sdf.format(date);
				}
				return DateUtil.convertStringToDate(time,
						"HH:mm:ss");
			}
		} catch (Exception e) {
			throw new Exception(ResourceUtil.getString(
					"sys-transport:sysTransport.import.dataError.typeError",
					null, locale,
					new Object[] { ResourceUtil.getString(property
							.getMessageKey(), locale), getCellValue(cell),
							type }));
		}

		// 如果是字符串，需要判断长度是否超过限制
		if ("String".equals(type) && property instanceof SysDictSimpleProperty) {
			SysDictSimpleProperty simpleProp = (SysDictSimpleProperty) property;
			if (value.length() > simpleProp.getLength()) {
				throw new Exception(ResourceUtil.getString(
						"sys-transport:sysTransport.import.dataError.tooLong2",
						null, locale,
						new Object[] { ResourceUtil.getString(property
								.getMessageKey(), locale),
								simpleProp.getLength() }));
			}
		}
		return transTypeFromString(getCellValue(cell), property, locale);
	}

	/**
	 * 获取Excel单元格的多个值，每个值自动转换类型
	 * 
	 * @param cell
	 * @param property
	 * @param splitStr
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public static List getCellValueList(Cell cell,
			SysDictCommonProperty property, String splitStr, Locale locale)
			throws Exception {
		if (cell == null) {
            return null;
        }
		if (splitStr == null) {
            splitStr = "[,;]";
        }
		String[] values = getCellValue(cell).split(splitStr);
		List rtnList = new ArrayList();
		for (int i = 0; i < values.length; i++) {
			if (StringUtil.isNull(values[i])) {
                continue;
            }
			
			String value = values[i].trim();
			// 这里需要处理枚举类型，导入时写“是”和“否”。解析后的数据应该是“true”和“false”
			if (!StringUtil.isNull(property.getEnumType())) {
				String enumType = property.getEnumType();
				value = EnumerationTypeUtil.getColumnValueByLabel(enumType,
						value, locale);
				if (value == null) {
                    throw new Exception(ResourceUtil.getString(
                            "sys-transport:sysTransport.import.dataError.enumNotExistVal",
                            null, locale,
                            new Object[] { ResourceUtil.getString(property
                                    .getMessageKey(), locale), values[i] }));
                }
			}
			rtnList.add(transTypeFromString(value, property, locale));
		}
		return rtnList;
	}

	/**
	 * 将字符串类型转换为指定类型
	 * 
	 * @param value
	 * @param property
	 * @param locale
	 * @return
	 */
	private static Object transTypeFromString(String value,
			SysDictCommonProperty property, Locale locale) {
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
            return DateUtil.convertStringToDate(value, DateUtil.TYPE_DATE,
                    locale);
        }
		if ("DateTime".equals(type)) {
            return DateUtil.convertStringToDate(value, DateUtil.TYPE_DATETIME,
                    locale);
        }
		if ("Time".equals(type)) {
            return DateUtil.convertStringToDate(value, DateUtil.TYPE_TIME,
                    locale);
        }
		return value;
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
			if (c >= 128 && c <= 255 && EXCEPT_STRING.indexOf(c) > -1) {
                continue;
            }
			rtnStr.append(c);
		}
		return rtnStr.toString();
	}

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ImportUtil.class);

	public static boolean isNotNullColumn(int columnIndex,
			ImportContext context, Locale locale) {
		// logger.debug("columnIndex=" + columnIndex);
		if (columnIndex >= context.getColumnTitles().size()) {
            return false;
        }
		// 获取列标题
		String columnTitle = context.getColumnTitles().get(columnIndex)
				.toString();
		String messageKey, propertyName;
		// 循环检查非空属性列表，看columnIndex指定的列是否属于非空列
		for (Iterator iter = context.getNotNullPropertyList().iterator(); iter
				.hasNext();) {
			ImportProperty importProperty = (ImportProperty) iter.next();
			messageKey = importProperty.getProperty().getMessageKey();
			propertyName = ResourceUtil.getString(messageKey, locale);
			if (columnTitle.startsWith(propertyName)) {
                return true;
            }
		}
		return false;
	}
	
}
