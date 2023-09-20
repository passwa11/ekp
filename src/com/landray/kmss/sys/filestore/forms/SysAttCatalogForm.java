package com.landray.kmss.sys.filestore.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.util.AutoArrayList;


/**
 * 目录配置 Form
 * 
 * @author 李衡
 * @version 1.0 2012-08-29
 */
public class SysAttCatalogForm extends ExtendForm {

	/**
	 * 名称
	 */
	protected String fdName = null;
	
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
	protected String fdPath = null;
	
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
	protected String fdIsCurrent = null;
	
	/**
	 * @return 是否为当前
	 */
	public String getFdIsCurrent() {
		return fdIsCurrent;
	}
	
	/**
	 * @param fdIsCurrent 是否为当前
	 */
	public void setFdIsCurrent(String fdIsCurrent) {
		this.fdIsCurrent = fdIsCurrent;
	}
	
	/**
	 * 转移的前后id
	 */
	protected List checkIds = new AutoArrayList(String.class);
	
	protected List changeIds =new AutoArrayList(String.class);
	
	public List getCheckIds() {
		return checkIds;
	}

	public void setCheckIds(List checkIds) {
		this.checkIds = checkIds;
	}

	public List getChangeIds() {
		return changeIds;
	}

	public void setChangeIds(List changeIds) {
		this.changeIds = changeIds;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdPath = null;
		fdIsCurrent = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysAttCatalog.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
