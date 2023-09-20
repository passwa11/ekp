package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;


/**
 * 归档模板 Form
 * 
 * @author
 * @version 1.0
 */
public class KmArchivesFileTemplateForm extends BaseCoreInnerForm {
	
	/**
	 * 模板XML
	 */
	protected String fdTmpXml = null;
	
	/**
	 * @return 模板XML
	 */
	public String getFdTmpXml() {
		return fdTmpXml;
	}
	
	/**
	 * @param fdTmpXml 模板XML
	 */
	public void setFdTmpXml(String fdTmpXml) {
		this.fdTmpXml = fdTmpXml;
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

	// 归档存放路径（档案分类）
	private String categoryId;
	private String categoryName;
	// 如何选择归档人
	private String selectFilePersonType = "org";
	// 归档人
	private String fdFilePersonId;
	private String fdFilePersonName;
	// 公式定义器的公式
	private String fdFilePersonFormula;
	private String fdFilePersonFormulaName;
	// 保存审批意见
	private String fdSaveApproval;
	// 预归档
	private String fdPreFile;

	// 保存旧文件
	private String fdSaveOldFile;
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getFdFilePersonId() {
		return fdFilePersonId;
	}

	public void setFdFilePersonId(String fdFilePersonId) {
		this.fdFilePersonId = fdFilePersonId;
	}

	public String getFdFilePersonName() {
		return fdFilePersonName;
	}

	public void setFdFilePersonName(String fdFilePersonName) {
		this.fdFilePersonName = fdFilePersonName;
	}

	public String getFdSaveApproval() {
		return fdSaveApproval;
	}

	public void setFdSaveApproval(String fdSaveApproval) {
		this.fdSaveApproval = fdSaveApproval;
	}


	public String getFdPreFile() {
		return fdPreFile;
	}

	public void setFdPreFile(String fdPreFile) {
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

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdTmpXml = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		categoryId = null;
		categoryName = null;
		fdFilePersonId = null;
		fdFilePersonName = null;
		fdFilePersonFormula = null;
		fdSaveApproval = null;
		fdPreFile = null;
		fdSaveOldFile = null;
		docSubjectMapping = null;
		fdLibraryMapping = null;
		fdPeriodMapping = null;
		fdVolumeYearMapping = null;
		fdUnitMapping = null;
		fdKeeperMapping = null;
		fdValidityDateMapping = null;
		fdDenseLevelMapping = null;
		fdFileDateMapping = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmArchivesFileTemplate.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
							SysOrgPerson.class));
			toModelPropertyMap.put("fdFilePersonId",
					new FormConvertor_IDToModel("fdFilePerson",
							SysOrgPerson.class));
			toModelPropertyMap.put("categoryId",
					new FormConvertor_IDToModel("category",
							KmArchivesCategory.class));
		}
		return toModelPropertyMap;
	}

	public String getFdSaveOldFile() {
		return fdSaveOldFile;
	}

	public void setFdSaveOldFile(String fdSaveOldFile) {
		this.fdSaveOldFile = fdSaveOldFile;
	}
}
