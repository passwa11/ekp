package com.landray.kmss.sys.remind.service;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseCoreInnerService;
import com.landray.kmss.sys.remind.model.SysRemindTemplate;

/**
 * 提醒中心模板
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public interface ISysRemindTemplateService extends IBaseCoreInnerService {

	/**
	 * 根据业务模板获取提醒模板
	 * 
	 * @param fdTemplateId
	 * @param fdTemplateName
	 * @return
	 */
	public abstract SysRemindTemplate findByTemplateNameAndId(String fdTemplateId, String fdTemplateName)
			throws Exception;
	
	/**
	 * 根据业务模板删除提醒模板
	 * 
	 * @param fdTemplateId
	 * @param fdTemplateName
	 * @throws Exception
	 */
	public abstract void deleteByTemplateNameAndId(String fdTemplateId, String fdTemplateName) throws Exception;

	/**
	 * 根据业务主文档获取提醒
	 * 
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	public abstract SysRemindTemplate findByModelName(String fdModelName) throws Exception;

	/**
	 * 创建提醒任务
	 * 
	 * @param tmplModel
	 * @param model
	 * @throws Exception
	 */
	public abstract void createRemindTask(SysRemindTemplate tmplModel, IBaseModel model) throws Exception;

}
