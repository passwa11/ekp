package com.landray.kmss.sys.portal.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.portal.forms.SysPortalMapTplNavCustomForm;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 自定义导航数据源
 * 
 * @author
 *
 */
public class SysPortalMapTplNavCustom extends BaseModel
		implements InterceptFieldEnabled {

	private static final long serialVersionUID = 9022499762720968828L;

	@Override
	public Class<SysPortalMapTplNavCustomForm> getFormClass() {
		return SysPortalMapTplNavCustomForm.class;
	}

	/**
	 * 标签名
	 */
	private String fdName;

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	/**
	 * 附件主键
	 */
	private String fdAttachmentId;

	/**
	 * 所属地图
	 */
	protected SysPortalMapTpl fdMain;

	public SysPortalMapTpl getFdMain() {
		return fdMain;
	}

	public void setFdMain(SysPortalMapTpl fdMain) {
		this.fdMain = fdMain;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdAttachmentId() {
		return fdAttachmentId;
	}

	public void setFdAttachmentId(String fdAttachmentId) {
		this.fdAttachmentId = fdAttachmentId;
	}

	/**
	 * 内容
	 */
	protected String fdContent;

	/**
	 * @return 内容
	 */
	public String getFdContent() {
		return (String) readLazyField("fdContent", fdContent);
	}

	/**
	 * @param fdContent
	 *            内容
	 */
	public void setFdContent(String fdContent) {
		this.fdContent =
				(String) writeLazyField("fdContent", this.fdContent, fdContent);
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdMain.fdId", "fdMainId");
		}
		return toFormPropertyMap;
	}

}
