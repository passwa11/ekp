package com.landray.kmss.km.archives.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseCoreInnerModel;
import com.landray.kmss.km.archives.forms.KmArchivesFileTemplateForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 归档模板
 * 
 * @author
 * @version 1.0
 */
public class KmArchivesFileTemplate extends BaseCoreInnerModel implements InterceptFieldEnabled {
	
	/**
	 * 模板XML
	 */
	protected String fdTmpXml;
	
	/**
	 * @return 模板XML
	 */
	public String getFdTmpXml() {
		return (String) readLazyField("fdTmpXml", fdTmpXml);
	}
	
	/**
	 * @param fdTmpXml 模板XML
	 */
	public void setFdTmpXml(String fdTmpXml) {
		this.fdTmpXml = (String) writeLazyField("fdTmpXml",
				this.fdTmpXml, fdTmpXml);
	}

	@Override
    public Class getFormClass() {
		return KmArchivesFileTemplateForm.class;
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
	 * 创建者
	 */
	protected SysOrgPerson docCreator;

	/**
	 * @return 创建者
	 */
	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	// 归档存放路径（档案分类）
	private KmArchivesCategory category;
	// 如何选择归档人
	private String selectFilePersonType = "org";
	// 归档人
	private SysOrgPerson fdFilePerson;
	// 公式定义器的公式
	private String fdFilePersonFormula;
	private String fdFilePersonFormulaName;
	// 保存审批意见
	private Boolean fdSaveApproval = Boolean.FALSE;
	// 预归档
	private Boolean fdPreFile = Boolean.FALSE;

	// 保留原始文件
	private Boolean fdSaveOldFile = Boolean.FALSE;
	// 档案名称 对应对应字段
	private String docSubjectMapping;
	// 所属卷库 对应字段
	private String fdLibraryMapping;
	// 保管期限 对应字段
	private String fdPeriodMapping;
	// 组卷年度 对应字段
	private String fdVolumeYearMapping;
	// 保管单位 对应字段
	private String fdUnitMapping;
	// 保管员对应字段
	private String fdKeeperMapping;
	// 有效期 对应字段
	private String fdValidityDateMapping;
	// 密级程度 对应字段
	private String fdDenseLevelMapping;
	// 归档日期 对应字段
	private String fdFileDateMapping;

	public String getFdFilePersonFormula() {
		return fdFilePersonFormula;
	}

	public void setFdFilePersonFormula(String fdFilePersonFormula) {
		this.fdFilePersonFormula = fdFilePersonFormula;
	}

	public String getFdFilePersonFormulaName() {
		return fdFilePersonFormulaName;
	}

	public void setFdFilePersonFormulaName(String fdFilePersonFormulaName) {
		this.fdFilePersonFormulaName = fdFilePersonFormulaName;
	}

	public String getSelectFilePersonType() {
		return selectFilePersonType;
	}

	public void setSelectFilePersonType(String selectFilePersonType) {
		this.selectFilePersonType = selectFilePersonType;
	}

	public KmArchivesCategory getCategory() {
		return category;
	}

	public void setCategory(KmArchivesCategory category) {
		this.category = category;
	}

	public SysOrgPerson getFdFilePerson() {
		return fdFilePerson;
	}

	public void setFdFilePerson(SysOrgPerson fdFilePerson) {
		this.fdFilePerson = fdFilePerson;
	}

	public Boolean getFdSaveApproval() {
		return fdSaveApproval;
	}

	public void setFdSaveApproval(Boolean fdSaveApproval) {
		this.fdSaveApproval = fdSaveApproval;
	}

	public Boolean getFdPreFile() {
		return fdPreFile;
	}

	public void setFdPreFile(Boolean fdPreFile) {
		this.fdPreFile = fdPreFile;
	}

	public String getDocSubjectMapping() {
		return docSubjectMapping;
	}

	public void setDocSubjectMapping(String docSubjectMapping) {
		this.docSubjectMapping = docSubjectMapping;
	}

	public String getFdLibraryMapping() {
		return fdLibraryMapping;
	}

	public void setFdLibraryMapping(String fdLibraryMapping) {
		this.fdLibraryMapping = fdLibraryMapping;
	}

	public String getFdPeriodMapping() {
		return fdPeriodMapping;
	}

	public void setFdPeriodMapping(String fdPeriodMapping) {
		this.fdPeriodMapping = fdPeriodMapping;
	}

	public String getFdVolumeYearMapping() {
		return fdVolumeYearMapping;
	}

	public void setFdVolumeYearMapping(String fdVolumeYearMapping) {
		this.fdVolumeYearMapping = fdVolumeYearMapping;
	}

	public String getFdUnitMapping() {
		return fdUnitMapping;
	}

	public void setFdUnitMapping(String fdUnitMapping) {
		this.fdUnitMapping = fdUnitMapping;
	}

	public String getFdKeeperMapping() {
		return fdKeeperMapping;
	}

	public void setFdKeeperMapping(String fdKeeperMapping) {
		this.fdKeeperMapping = fdKeeperMapping;
	}

	public String getFdValidityDateMapping() {
		return fdValidityDateMapping;
	}

	public void setFdValidityDateMapping(String fdValidityDateMapping) {
		this.fdValidityDateMapping = fdValidityDateMapping;
	}

	public String getFdDenseLevelMapping() {
		return fdDenseLevelMapping;
	}

	public void setFdDenseLevelMapping(String fdDenseLevelMapping) {
		this.fdDenseLevelMapping = fdDenseLevelMapping;
	}

	public String getFdFileDateMapping() {
		return fdFileDateMapping;
	}

	public void setFdFileDateMapping(String fdFileDateMapping) {
		this.fdFileDateMapping = fdFileDateMapping;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdFilePerson.fdId", "fdFilePersonId");
			toFormPropertyMap.put("fdFilePerson.fdName", "fdFilePersonName");
			toFormPropertyMap.put("category.fdName", "categoryName");
			toFormPropertyMap.put("category.fdId", "categoryId");
		}
		return toFormPropertyMap;
	}

	public Boolean getFdSaveOldFile() {
		return fdSaveOldFile;
	}

	public void setFdSaveOldFile(Boolean fdSaveOldFile) {
		this.fdSaveOldFile = fdSaveOldFile;
	}
}
