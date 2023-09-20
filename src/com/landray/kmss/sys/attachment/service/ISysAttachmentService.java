package com.landray.kmss.sys.attachment.service;

import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.lbpmservice.support.forms.LbpmProcessMainForm;

public interface ISysAttachmentService {

	/**
	 * model转为form处理
	 * 
	 * @param form
	 * @param model
	 * @param requestContext
	 * @throws Exception
	 */
	public abstract void convertModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception;

	/**
	 * form转为model处理
	 * 
	 * @param form
	 * @param model
	 * @param requestContext
	 * @throws Exception
	 */
	public abstract void convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception;

	/**
	 * 将model的所有附件信息，对应增加到form中
	 * 
	 * @param form
	 * @param model
	 * @throws Exception
	 */
	public abstract void addAttachment(IAttachment form, IAttachment model)
			throws Exception;

	/**
	 * 将model中对应key的所有附件信息，增加到form的对应key中
	 * 
	 * @param form
	 * @param model
	 * @param fdKey
	 * @throws Exception
	 */
	public abstract void addAttachment(IAttachment form, IAttachment model,
			String fdKey) throws Exception;

	/**
	 * 将attachment中附件信息，复制增加到新的附件对象中，返回附件map
	 * 
	 * @param attachment
	 * @param targetModelName
	 * @return
	 * @throws Exception
	 */
	public Map getCloneAttachmentMap(IAttachment attachment,
			String targetModelName) throws Exception;

	/**
	 * 将model中对应所有附件信息，复制增加到form中，返回附件map
	 * 
	 * @param attachment
	 * @param targetModelName
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public Map getCloneAttachmentMapForModel(IAttachment attachment,
			String targetModelName, IBaseModel model) throws Exception;

	/**
	 * 将model中对应key的所有附件信息，复制增加到form中，返回附件map
	 * 
	 * @param attachment
	 * @param targetModelName
	 * @param model
	 * @param fdKey
	 * @return
	 * @throws Exception
	 */
	public Map getCloneAttachmentMapForModel(IAttachment attachment,
			String targetModelName, IBaseModel model, String fdKey)
			throws Exception;

	/**
	 * 更新attachment对象中的附件信息中modelName，modelId为该对象的className和id
	 * 
	 * @param attachment
	 * @throws Exception
	 */
	public void updateCloneAttachmentMap(IAttachment attachment)
			throws Exception;

	/**
	 * 流程单独提交时调用
	 * 
	 * @param lbpmProcessForm
	 * @throws Exception
	 */
	public void saveByLbpmForm(LbpmProcessMainForm lbpmProcessForm)
			throws Exception;

    public void saveApi(IBaseModel model) throws Exception;
}
