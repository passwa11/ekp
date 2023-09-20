package com.landray.kmss.sys.transport.service.spring;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;

/**
 * 明细表excel导入的上下文
 * 
 * @author Administrator
 *
 */
public class ImportInDetailsContext {

	// 如果有对象属性，需要记录，以便校验的时候加上id属性
	Map dictComMapToDictSim = new HashMap();

	// 用Map来保存字段
	Map<String, String> detailTableMap = new HashMap<String, String>();

	// name和id的映射
	Map<String, String> nameToIdMap = new HashMap<String, String>();

	// name属性和name的Form字段的映射
	Map<SysDictCommonProperty, String> namePropertyToNameForm = new HashMap<SysDictCommonProperty, String>();

	// id和input的类型的映射
	Map<String, String> idToType = new HashMap<String, String>();

	public Map getDictComMapToDictSim() {
		return dictComMapToDictSim;
	}

	public void setDictComMapToDictSim(Map dictComMapToDictSim) {
		this.dictComMapToDictSim = dictComMapToDictSim;
	}

	public Map<String, String> getDetailTableMap() {
		return detailTableMap;
	}

	public void setDetailTableMap(Map<String, String> detailTableMap) {
		this.detailTableMap = detailTableMap;
	}

	public Map<String, String> getNameToIdMap() {
		return nameToIdMap;
	}

	public void setNameToIdMap(Map<String, String> nameToIdMap) {
		this.nameToIdMap = nameToIdMap;
	}

	public Map<SysDictCommonProperty, String> getNamePropertyToNameForm() {
		return namePropertyToNameForm;
	}

	public void setNamePropertyToNameForm(
			Map<SysDictCommonProperty, String> namePropertyToNameForm) {
		this.namePropertyToNameForm = namePropertyToNameForm;
	}

	public Map<String, String> getIdToType() {
		return idToType;
	}

	public void setIdToType(Map<String, String> idToType) {
		this.idToType = idToType;
	}

}
