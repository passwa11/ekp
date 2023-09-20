package com.landray.kmss.third.weixin.mutil.spi.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.weixin.mutil.spi.model.WxworkOmsRelationMutilModel;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 * 中间映射表 Form
 * 
 * @author
 * @version 1.0 2017-06-20
 */
public class WxworkOmsRelationModelForm extends ExtendForm {

	/**
	 * EKPID
	 */
	private String fdEkpId;

	/**
	 * @return EKPID
	 */
	public String getFdEkpId() {
		return this.fdEkpId;
	}

	/**
	 * @param fdEkpId
	 *            EKPID
	 */
	public void setFdEkpId(String fdEkpId) {
		this.fdEkpId = fdEkpId;
	}

	/**
	 * 第三方ID
	 */
	private String fdAppPkId;

	/**
	 * @return 第三方ID
	 */
	public String getFdAppPkId() {
		return this.fdAppPkId;
	}

	/**
	 * @param fdAppPkId
	 *            第三方ID
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

	// 机制开始
	// 机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdEkpId = null;
		fdAppPkId = null;
		fdAppKey = null;
		fdEkpName = null;
		fdEkpLoginName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class<WxworkOmsRelationMutilModel> getModelClass() {
		return WxworkOmsRelationMutilModel.class;
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

	/**
	 * 所属企业微信标识
	 */
	public String fdWxKey;

	public String getFdWxKey() {
		return fdWxKey;
	}

	public void setFdWxKey(String fdWxKey) {
		this.fdWxKey = fdWxKey;
	}

}
