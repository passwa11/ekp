package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingOmsPostForm;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;



/**
 * 岗位
 * 
 * @author chenl
 * @version 1.0 2018-02-06
 */
public class ThirdDingOmsPost  extends BaseModel implements InterceptFieldEnabled{

	/**
	 * 名称
	 */
	private String fdName;
	
	/**
	 * @return 名称
	 */
	public String getFdName() {
		return this.fdName;
	}
	
	/**
	 * @param fdName 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 内容
	 */
	private String fdContent;
	
	/**
	 * @return 内容
	 */
	public String getFdContent() {
		return this.fdContent;
	}
	
	/**
	 * @param fdContent 内容
	 */
	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}
	
	protected String docContent;
	
	/**
	 * @return 返回 文档内容
	 */
	public java.lang.String getDocContent() {
		return (String) readLazyField("docContent", docContent);
	}

	/**
	 * @param docContent
	 *            要设置的 文档内容
	 */
	public void setDocContent(java.lang.String docContent) {
		this.docContent = (String) writeLazyField("docContent",
				this.docContent, docContent);
	}

	//机制开始
	//机制结束

	@Override
    public Class<ThirdDingOmsPostForm> getFormClass() {
		return ThirdDingOmsPostForm.class;
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
