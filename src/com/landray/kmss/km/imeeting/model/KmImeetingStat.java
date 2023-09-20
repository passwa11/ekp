package com.landray.kmss.km.imeeting.model;

import java.util.Date;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.imeeting.forms.KmImeetingStatForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;

/**
 * 会议统计
 */
public class KmImeetingStat extends BaseAuthModel implements
		InterceptFieldEnabled {

	/**
	 * 统计名称
	 */
	protected String fdName;

	/**
	 * @return 统计名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            统计名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 统计类型
	 */
	protected String fdType;

	/**
	 * @return 统计类型
	 */
	public String getFdType() {
		return fdType;
	}

	/**
	 * @param fdType
	 *            统计类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * 创建时间
	 */
	protected Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	@Override
    public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	@Override
    public void setDocCreateTime(Date docCreateTime) {
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
	protected String fdContiditionJson;

	/**
	 * @return 条件JSON
	 */
	public String getFdContiditionJson() {
		return (String) readLazyField("fdContiditionJson", fdContiditionJson);
	}

	/**
	 * @param fdContiditionJson
	 *            条件JSON
	 */
	public void setFdContiditionJson(String fdContiditionJson) {
		this.fdContiditionJson = (String) writeLazyField("fdContiditionJson",
				this.fdContiditionJson, fdContiditionJson);
	}

	/**
	 * 统计周期
	 */
	protected String fdDateType;

	/**
	 * @return 统计周期
	 */
	public String getFdDateType() {
		return fdDateType;
	}

	/**
	 * @param fdDateType
	 *            统计周期
	 */
	public void setFdDateType(String fdDateType) {
		this.fdDateType = fdDateType;
	}

	/**
	 * 开始日期
	 */
	protected String fdStartDate;

	/**
	 * @return 开始日期
	 */
	public String getFdStartDate() {
		return fdStartDate;
	}

	/**
	 * @param fdStartDate
	 *            开始日期
	 */
	public void setFdStartDate(String fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	/**
	 * 结束日期
	 */
	protected String fdEndDate;

	/**
	 * @return 结束日期
	 */
	public String getFdEndDate() {
		return fdEndDate;
	}

	/**
	 * @param fdEndDate
	 *            结束日期
	 */
	public void setFdEndDate(String fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	/**
	 * 创建者
	 */
	protected SysOrgPerson docCreator;

	/**
	 * @return 创建者
	 */
	@Override
    public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	@Override
    public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	@Override
    public Class getFormClass() {
		return KmImeetingStatForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}

	@Override
	protected void recalculateReaderField() {
		super.recalculateReaderField();
		// 为空所有人可阅读
		if (authReaders != null && !authReaders.isEmpty()) {
			authAllReaders.addAll(authReaders);
		}
	}

	@Override
	public String getDocSubject() {
		return fdName;
	}

}
