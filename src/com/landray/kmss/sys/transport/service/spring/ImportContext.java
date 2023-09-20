package com.landray.kmss.sys.transport.service.spring;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.context.ApplicationContext;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.transport.model.SysTransportImportConfig;
import com.landray.kmss.sys.transport.model.SysTransportImportProperty;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class ImportContext {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BaseServiceImp.class);

	private int currentRowNum;

	private KmssMessages messages = new KmssMessages();

	private String modelName;

	private List properties = new ArrayList();

	private ImportProperty keyProperty;

	private Locale locale;

	private boolean check;

	private boolean currentRowError;

	private int columnSize;

	private List columnTitles;

	private List notNullPropertyList = new ArrayList();

	private Sheet sheet;

	/**
	 * 存储主数据关键字属性-入表，以防新增导入了主数据属性没有入表(eg.导入时已选列表中没有选择关键字属性)，而在新增或更新的时候根据关键字查不到原主数据而再次入表  fix #152413
	 */
	private Map<String,Object> keyProMap = new ConcurrentHashMap<>();

	public ImportContext(SysTransportImportConfig importConfig,
			Row firstRow, Locale locale,
			ApplicationContext applicationContext) {
		this.locale = locale;
		modelName = importConfig.getFdModelName();
		columnTitles = new ArrayList();
		for (int i = 0; i < 1000; i++) {
			Cell cell = firstRow.getCell((short) i);
			if (cell == null) {
				break;
			}
			String value = ImportUtil.getCellValue(cell);
			if (StringUtil.isNull(value)) {
				break;
			}
			columnTitles.add(value);
		}
		columnSize = columnTitles.size();
		keyProperty = new ImportProperty(importConfig, columnTitles, locale,
				applicationContext);
		for (int i = 0; i < importConfig.getPropertyList().size(); i++) {
			SysTransportImportProperty importProperty = (SysTransportImportProperty) importConfig
					.getPropertyList().get(i);
			ImportProperty importPropertyContext = new ImportProperty(importProperty, columnTitles,
					locale, applicationContext);
			properties.add(importPropertyContext);
			if (importPropertyContext.getProperty().isNotNull()) {
				notNullPropertyList.add(importPropertyContext);
			}
		}
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public ImportProperty getKeyProperty() {
		return keyProperty;
	}

	public List getProperties() {
		return properties;
	}

	public String getModelName() {
		return modelName;
	}

	public Locale getLocale() {
		return locale;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public KmssMessages getMessages() {
		return messages;
	}
	
	public void setMessages(KmssMessages messages) {
		this.messages = messages;
	}

	public int getCurrentRowNum() {
		return currentRowNum;
	}

	public void setCurrentRowNum(int currentRowNum) {
		currentRowError = false;
		this.currentRowNum = currentRowNum;
	}

	public boolean isCurrentRowError() {
		return currentRowError;
	}

	public void logErrorCell(int cellIndex, Exception e) {
		logger.error(
				"导入的数据错误：第" + (currentRowNum + 1) + "行，第" + (cellIndex + 1)
						+ "列："
				+ e.toString(), e);
		JSONObject obj = new JSONObject();
		obj.put("errRowNumber", String.valueOf(currentRowNum + 1)); // 错误行号
		obj.put("errColNumber", String.valueOf(cellIndex)); // 错误列号
		obj.put("errInfo", e.getMessage()); // 错误信息
		if(!obj.containsKey("errInfo")) {
			obj.put("errInfo", e.toString());
		}
		messages.addError(new KmssMessage(
				"sys-transport:sysTransport.import.dataError", obj));
		currentRowError = true;
	}

	public void logErrorRow(String errorMsg) {
		int errRowNum = currentRowNum + 1;
		logger.error("导入的数据错误：第" + errRowNum + "行：" + errorMsg);
		JSONObject obj = new JSONObject();
		obj.put("errRowNumber", String.valueOf(errRowNum)); // 错误行号
		obj.put("errColNumber", String.valueOf(-1)); // 错误列号
		obj.put("errInfo", errorMsg); // 错误信息
		messages.addError(new KmssMessage(null, obj));
	}

	public int getColumnSize() {
		return columnSize;
	}

	public List getColumnTitles() {
		return columnTitles;
	}

	public List getNotNullPropertyList() {
		return notNullPropertyList;
	}

	public Map<String, Object> getKeyProMap() {
		return keyProMap;
	}

	public void setKeyProMap(Map<String, Object> keyProMap) {
		this.keyProMap = keyProMap;
	}
}
