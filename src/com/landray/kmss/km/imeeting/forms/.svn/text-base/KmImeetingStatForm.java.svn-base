package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.imeeting.model.KmImeetingStat;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.BaseAuthForm;


/**
 * 会议统计 Form
 */
public class KmImeetingStatForm extends BaseAuthForm {

	/**
	 * 统计名称
	 */
	protected String fdName = null;
	
	/**
	 * @return 统计名称
	 */
	public String getFdName() {
		return fdName;
	}
	
	/**
	 * @param fdName 统计名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 统计类型
	 */
	protected String fdType = null;
	
	/**
	 * @return 统计类型
	 */
	public String getFdType() {
		return fdType;
	}
	
	/**
	 * @param fdType 统计类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
	
	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;
	
	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 查询条件IDs
	 */
	protected String queryCondIds = null;

	/**
	 * 查询条件Names
	 */
	protected String queryCondNames = null;

	public String getQueryCondIds() {
		return queryCondIds;
	}

	public void setQueryCondIds(String queryCondIds) {
		this.queryCondIds = queryCondIds;
	}

	public String getQueryCondNames() {
		return queryCondNames;
	}

	public void setQueryCondNames(String queryCondNames) {
		this.queryCondNames = queryCondNames;
	}

	private String fdTemplateId;

	private String fdTemplateName;

	public String getFdTemplateId() {
		return fdTemplateId;
	}

	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
	}

	public String getFdTemplateName() {
		return fdTemplateName;
	}

	public void setFdTemplateName(String fdTemplateName) {
		this.fdTemplateName = fdTemplateName;
	}

	/**
	 * 条件JSON
	 */
	protected String fdContiditionJson = null;

	/**
	 * @return 条件JSON
	 */
	public String getFdContiditionJson() {
		return fdContiditionJson;
	}

	/**
	 * @param fdContiditionJson
	 *            条件JSON
	 */
	public void setFdContiditionJson(String fdContiditionJson) {
		this.fdContiditionJson = fdContiditionJson;
	}
	
	/**
	 * 统计周期
	 */
	protected String fdDateType = null;
	
	/**
	 * @return 统计周期
	 */
	public String getFdDateType() {
		return fdDateType;
	}
	
	/**
	 * @param fdDateType 统计周期
	 */
	public void setFdDateType(String fdDateType) {
		this.fdDateType = fdDateType;
	}
	
	/**
	 * 开始日期
	 */
	protected String fdStartDate = null;
	
	/**
	 * @return 开始日期
	 */
	public String getFdStartDate() {
		return fdStartDate;
	}
	
	/**
	 * @param fdStartDate 开始日期
	 */
	public void setFdStartDate(String fdStartDate) {
		this.fdStartDate = fdStartDate;
	}
	
	/**
	 * 结束日期
	 */
	protected String fdEndDate = null;
	
	/**
	 * @return 结束日期
	 */
	public String getFdEndDate() {
		return fdEndDate;
	}
	
	private String fdProdStartDate;

	private String fdProdEndDate;

	/**
	 * @param fdEndDate 结束日期
	 */
	public void setFdEndDate(String fdEndDate) {
		this.fdEndDate = fdEndDate;
	}
	
	public String getFdProdStartDate() {
		if ("7".equals(this.fdDateType)) {
            return "";
        } else {
            return this.fdStartDate;
        }
	}

	public void setFdProdStartDate(String fdProdStartDate) {
		this.fdProdStartDate = fdProdStartDate;
	}

	public String getFdProdEndDate() {
		if ("7".equals(this.fdDateType)) {
            return "";
        } else {
            return this.fdEndDate;
        }
	}

	public void setFdProdEndDate(String fdProdEndDate) {
		this.fdProdEndDate = fdProdEndDate;
	}

	/**
	 * 创建者的ID
	 */
	protected String docCreatorId = null;
	
	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}
	
	/**
	 * @param docCreatorId 创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
	
	/**
	 * 创建者的名称
	 */
	protected String docCreatorName = null;
	
	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}
	
	/**
	 * @param docCreatorName 创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdType = null;
		docCreateTime = null;
		fdContiditionJson = null;
		fdDateType = null;
		fdStartDate = null;
		fdEndDate = null;
		fdProdStartDate = null;
		fdProdEndDate = null;
		docCreatorId = null;
		docCreatorName = null;
		
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return KmImeetingStat.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
