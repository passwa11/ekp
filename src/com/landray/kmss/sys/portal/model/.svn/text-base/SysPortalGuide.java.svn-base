package com.landray.kmss.sys.portal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import net.sf.json.JSONObject;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.forms.SysPortalGuideForm;
import com.landray.kmss.util.StringUtil;

/**
 * 自定义引导页
 */
public class SysPortalGuide extends BaseModel implements InterceptFieldEnabled {

	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	protected String fdName;

	/**
	 * @return 名称
	 */
	public String getFdNameOri() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdNameOri(String fdName) {
		this.fdName = fdName;
	}
	
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}
	
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	/**
	 * 引导页面类型：RTF或者URL
	 */
	protected String fdType;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
		JSONObject json = new JSONObject();
		if (StringUtil.isNotNull(cfgJson)) {
			json = JSONObject.fromObject(cfgJson);
			json.put("type", fdType);
			this.setCfgJson(json.toString());
		}
	}

	/**
	 * RTF内容
	 */
	protected String fdContent;

	/**
	 * @return 内容
	 */
	public String getFdContent() {
		String cfgJson = getCfgJson();
		if (StringUtil.isNotNull(cfgJson)) {
			JSONObject json = JSONObject.fromObject(cfgJson);
			if (json != null && json.containsKey("content")) {
				return json.getString("content");
			}
		}
		return fdContent;
	}

	/**
	 * @param fdContent
	 *            内容
	 */
	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
		JSONObject json = new JSONObject();
		if (StringUtil.isNotNull(cfgJson)) {
			json = JSONObject.fromObject(cfgJson);
		}
		json.put("content", fdContent);
		this.setCfgJson(json.toString());
	}

	/**
	 * 引导页链接
	 */
	protected String fdLink;

	public String getFdLink() {
		String cfgJson = getCfgJson();
		if (StringUtil.isNotNull(cfgJson)) {
			JSONObject json = JSONObject.fromObject(cfgJson);
			if (json != null && json.containsKey("link")) {
				return json.getString("link");
			}
		}
		return fdLink;
	}

	public void setFdLink(String fdLink) {
		this.fdLink = fdLink;
		JSONObject json = new JSONObject();
		if (StringUtil.isNotNull(cfgJson)) {
			json = JSONObject.fromObject(cfgJson);
		}
		json.put("link", fdLink);
		this.setCfgJson(json.toString());
	}

	protected String cfgJson = null;

	public String getCfgJson() {
		return (String) readLazyField("cfgJson", cfgJson);
	}

	public void setCfgJson(String cfgJson) {
		this.cfgJson = (String) writeLazyField("cfgJson", this.cfgJson, cfgJson);
	}

	/**
	 * 创建时间
	 */
	protected Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 最后修改时间
	 */
	protected Date docAlterTime;

	/**
	 * @return 最后修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 创建人
	 */
	protected SysOrgElement docCreator;

	/**
	 * @return 创建人
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建人
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 修改者
	 */
	protected SysOrgElement docAlteror;

	/**
	 * @return 修改者
	 */
	public SysOrgElement getDocAlteror() {
		return docAlteror;
	}

	/**
	 * @param docAlteror
	 *            修改者
	 */
	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}

	/*
	 * 可维护人员
	 */
	private List fdEditors = new ArrayList();

	public List getFdEditors() {
		return fdEditors;
	}

	public void setFdEditors(List fdEditors) {
		this.fdEditors = fdEditors;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("fdEditors",
					new ModelConvertor_ModelListToString(
							"fdEditorIds:fdEditorNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysPortalGuideForm.class;
	}

}
