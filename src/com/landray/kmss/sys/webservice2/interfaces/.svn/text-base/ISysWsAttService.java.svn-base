package com.landray.kmss.sys.webservice2.interfaces;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.webservice2.forms.AttachmentForm;

/**
 * WebService附件接口
 * 
 * @author Jeff
 * 
 */
public interface ISysWsAttService extends IBaseService {
	/**
	 * 保存多个附件
	 */
	public List<String> save(List<AttachmentForm> attForms, String modelId,
			String modelName) throws Exception;

	/**
	 * 保存单个附件
	 */
	public String save(AttachmentForm attForm, String modelId, String modelName)
			throws Exception;

	/**
	 * 删除附件
	 */
	public void deleteByIds(String[] ids) throws Exception;
	
	
	/**
	 * 校验附件的大小，包括单个附件的长度和文档所有附件的长度
	 */
	public void validateAttSize(List<AttachmentForm> attForms) throws Exception;
}
