package com.landray.kmss.sys.filestore.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.filestore.forms.SysAttCatalogForm;

/**
 * 目录配置
 * 
 * @author 李衡
 * @version 1.0 2012-08-29
 */
public class SysAttCatalog extends BaseModel {

	/**
	 * 名称
	 */
	protected String fdName;
	
	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}
	
	/**
	 * @param fdName 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 路径
	 */
	protected String fdPath;
	
	/**
	 * @return 路径
	 */
	public String getFdPath() {
		return fdPath;
	}
	
	/**
	 * @param fdPath 路径
	 */
	public void setFdPath(String fdPath) {
		this.fdPath = fdPath;
	}
	
	/**
	 * 是否为当前
	 */
	protected Boolean fdIsCurrent;
	
	/**
	 * @return 是否为当前
	 */
	public Boolean getFdIsCurrent() {
		return fdIsCurrent;
	}
	
	/**
	 * @param fdIsCurrent 是否为当前
	 */
	public void setFdIsCurrent(Boolean fdIsCurrent) {
		this.fdIsCurrent = fdIsCurrent;
	}
	
	@Override
    public Class getFormClass() {
		return SysAttCatalogForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
