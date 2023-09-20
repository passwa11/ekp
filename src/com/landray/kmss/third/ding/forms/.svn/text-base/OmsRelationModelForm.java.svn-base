package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 * 钉钉中间映射表 Form
 * 
 * @author
 * @version 1.0 2017-06-29
 */
public class OmsRelationModelForm extends ExtendForm {

	/**
	 * EKP人员姓名
	 */
	private String fdEkpId;

	/**
	 * @return EKP人员姓名
	 */
	public String getFdEkpId() {
		return this.fdEkpId;
	}

	/**
	 * @param fdEkpId
	 *            EKP人员姓名
	 */
	public void setFdEkpId(String fdEkpId) {
		this.fdEkpId = fdEkpId;
	}

	/**
	 * 企业号人员账号
	 */
	private String fdAppPkId;

	/**
	 * @return 企业号人员账号
	 */
	public String getFdAppPkId() {
		return this.fdAppPkId;
	}

	/**
	 * @param fdAppPkId
	 *            企业号人员账号
	 */
	public void setFdAppPkId(String fdAppPkId) {
		this.fdAppPkId = fdAppPkId;
	}

	/**
	 * 集成标识
	 */
	private String fdAppKey;

	/**
	 * @return 集成标识
	 */
	public String getFdAppKey() {
		return this.fdAppKey;
	}

	/**
	 * @param fdAppKey
	 *            集成标识
	 */
	public void setFdAppKey(String fdAppKey) {
		this.fdAppKey = fdAppKey;
	}

	/**
	 * @param fdAvatar
	 *            头像
	 */
	private String fdAvatar;

	public String getFdAvatar() {
		return fdAvatar;
	}

	public void setFdAvatar(String fdAvatar) {
		this.fdAvatar = fdAvatar;
	}

	private String fdUnionId;

	public String getFdUnionId() {
		return fdUnionId;
	}

	public void setFdUnionId(String fdUnionId) {
		this.fdUnionId = fdUnionId;
	}

	// 机制开始
	// 机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdEkpId = null;
		fdAppPkId = null;
		fdAppKey = null;
		fdAvatar = null;
		fdUnionId=null;
		super.reset(mapping, request);
	}

	@Override
    public Class<OmsRelationModel> getModelClass() {
		return OmsRelationModel.class;
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

	private String fdEkpName;
	private String fdEkpLoginName;

	public String getFdEkpName() {
		return fdEkpName;
	}

	public String getFdEkpLoginName() {
		return fdEkpLoginName;
	}

	public void setFdEkpName(String fdEkpName) {
		this.fdEkpName = fdEkpName;
	}

	public void setFdEkpLoginName(String fdEkpLoginName) {
		this.fdEkpLoginName = fdEkpLoginName;
	}
	
	/**
	 * @param file
	 *            上传文件
	 */
	protected FormFile file = null;

	/**
	 * @return file
	 */
	public FormFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            要设置的 file
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}
	
	private String fdType;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	private String fdAccountType;

	public String getFdAccountType() {
		return fdAccountType;
	}

	public void setFdAccountType(String fdAccountType) {
		this.fdAccountType = fdAccountType;
	}
}
