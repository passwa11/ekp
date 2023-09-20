package com.landray.kmss.sys.remind.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.remind.model.SysRemindTemplate;
import com.landray.kmss.sys.remind.model.SysRemindTemplateRelation;

/**
 * 提醒模板关系
 * 
 * @author panyh
 * @date Jun 30, 2020
 */
public interface ISysRemindTemplateRelationService extends IBaseService {

	/**
	 * 保存模板关系
	 * 
	 * @param template
	 * @throws Exception
	 */
	public abstract void saveTemplateRelation(SysRemindTemplate template) throws Exception;

	/**
	 * 根据业务主文档获取关系
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract SysRemindTemplateRelation getRelationByModel(String modelName) throws Exception;

	/**
	 * 根据模块路径获取关系
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract List<SysRemindTemplateRelation> getRelationByModule(String moduleUrl) throws Exception;

}
