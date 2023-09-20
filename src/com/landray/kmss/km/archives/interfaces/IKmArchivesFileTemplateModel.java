package com.landray.kmss.km.archives.interfaces;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;

/**
 * 归档模板对外接口
 */
public interface IKmArchivesFileTemplateModel extends IBaseModel {

	/**
	 * 获取归档模板信息
	 * 
	 * @return
	 */
	public abstract KmArchivesFileTemplate getKmArchivesFileTemplate();

	/**
	 * 设置归档模板信息
	 * 
	 * @param sysPrintTemplate
	 */
	public abstract void setKmArchivesFileTemplate(
			KmArchivesFileTemplate kmArchivesFileTemplate);

}
