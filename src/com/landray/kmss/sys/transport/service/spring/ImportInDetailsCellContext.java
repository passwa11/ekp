package com.landray.kmss.sys.transport.service.spring;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.util.KmssMessages;

/**
 * 明细表导入单元格的上下文
 * 
 * @author Administrator
 *
 */
public class ImportInDetailsCellContext {

	ImportInDetailsContext detailsContext;

	SysDictCommonProperty property;

	String propertyName;

	Cell cell;

	String cellString;

	KmssMessages contentMessage = new KmssMessages();

	int index;

	Map<String, String> temp = new HashMap<String, String>();

	SysDictCommonProperty modelPro;

	public ImportInDetailsContext getDetailsContext() {
		return detailsContext;
	}

	public void setDetailsContext(ImportInDetailsContext detailsContext) {
		this.detailsContext = detailsContext;
	}

	public SysDictCommonProperty getProperty() {
		return property;
	}

	public void setProperty(SysDictCommonProperty property) {
		this.property = property;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public String getCellString() {
		return cellString;
	}

	public void setCellString(String cellString) {
		this.cellString = cellString;
	}

	public KmssMessages getContentMessage() {
		return contentMessage;
	}

	public void setContentMessage(KmssMessages contentMessage) {
		this.contentMessage = contentMessage;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Map<String, String> getTemp() {
		return temp;
	}

	public void setTemp(Map<String, String> temp) {
		this.temp = temp;
	}

	public SysDictCommonProperty getModelPro() {
		return modelPro;
	}

	public void setModelPro(SysDictCommonProperty modelPro) {
		this.modelPro = modelPro;
	}

}
