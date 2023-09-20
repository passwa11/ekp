package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixForm;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 组织矩阵
 * 
 * @author 潘永辉 2019年6月4日
 *
 */
public class SysOrgMatrix extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 矩阵名称
	 */
	private String fdName;

	/**
	 * 1 内置生成的
	 * 2 人工创建的
	 */
	private String matrixType;
	
	/**
	 * 矩阵描述
	 */
	private String fdDesc;

	/**
	 * 矩阵分类
	 */
	private SysOrgMatrixCate fdCategory;

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	/**
	 * 是否禁用
	 */
	private Boolean fdIsAvailable;

	/**
	 * 子表名
	 */
	private String fdSubTable;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	/**
	 * 修改时间
	 */
	private Date fdAlterTime = new Date();

	/**
	 * 可阅读者
	 */
	private List<SysOrgElement> authReaders = new ArrayList<SysOrgElement>();

	/**
	 * 可编辑者
	 */
	private List<SysOrgElement> authEditors = new ArrayList<SysOrgElement>();

	@Override
	public void recalculateFields() {
		super.recalculateFields();
		if (ArrayUtil.isEmpty(getAuthReaders())) {
			// 生态组织内外隔离
			SysOrgPerson creator = UserUtil.getUser();
			if (BooleanUtils.isTrue(creator.getFdIsExternal())) {
				if (creator.getFdParent() != null) {
					getAuthReaders().add(creator.getFdParent());
				}
			}
		}
	}

	/**
	 * 所有人可阅读标记
	 */
	private Boolean authReaderFlag;

	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", fdName);
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	public SysOrgMatrixCate getFdCategory() {
		return getHbmCategory();
	}

	public void setFdCategory(SysOrgMatrixCate fdCategory) {
		this.setHbmCategory(fdCategory);
	}

	public SysOrgMatrixCate getHbmCategory() {
		return fdCategory;
	}

	public void setHbmCategory(SysOrgMatrixCate category) {
		this.fdCategory = category;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public Boolean getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public Date getFdAlterTime() {
		return fdAlterTime;
	}

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	public List<SysOrgElement> getAuthReaders() {
		return authReaders;
	}

	public void setAuthReaders(List<SysOrgElement> authReaders) {
		this.authReaders = authReaders;
	}

	public List<SysOrgElement> getAuthEditors() {
		return authEditors;
	}

	public void setAuthEditors(List<SysOrgElement> authEditors) {
		this.authEditors = authEditors;
	}

	public Boolean getAuthReaderFlag() {
		if (ArrayUtil.isEmpty(getAuthReaders())) {
            authReaderFlag = new Boolean(true);
        } else {
            authReaderFlag = new Boolean(false);
        }
		return authReaderFlag;
	}

	public void setAuthReaderFlag(Boolean authReaderFlag) {
	}

	public String getFdSubTable() {
		return fdSubTable;
	}

	public void setFdSubTable(String fdSubTable) {
		this.fdSubTable = fdSubTable;
	}

	/**
	 * 关系信息(条件数据)
	 */
	private List<SysOrgMatrixRelation> fdRelationConditionals;

	public void setFdRelationConditionals(
			List<SysOrgMatrixRelation> fdRelationConditionals) {
		this.fdRelationConditionals = fdRelationConditionals;
	}

	public List<SysOrgMatrixRelation> getFdRelationConditionals() {
		if (fdRelationConditionals == null && getFdRelations() != null) {
			fdRelationConditionals = new ArrayList<SysOrgMatrixRelation>();
			for (SysOrgMatrixRelation relation : getFdRelations()) {
				if (!BooleanUtils.isTrue(relation.getFdIsResult())) {
					fdRelationConditionals.add(relation);
				}
			}
		}
		return fdRelationConditionals;
	}

	/**
	 * 关系信息(结果数据)
	 */
	private List<SysOrgMatrixRelation> fdRelationResults;

	public void setFdRelationResults(List<SysOrgMatrixRelation> fdRelationResults) {
		this.fdRelationResults = fdRelationResults;
	}

	public List<SysOrgMatrixRelation> getFdRelationResults() {
		if (fdRelationResults == null && getFdRelations() != null) {
			fdRelationResults = new ArrayList<SysOrgMatrixRelation>();
			for (SysOrgMatrixRelation relation : getFdRelations()) {
				if (BooleanUtils.isTrue(relation.getFdIsResult())) {
					fdRelationResults.add(relation);
				}
			}
		}
		return fdRelationResults;
	}

	@Override
	public Class<?> getFormClass() {
		return SysOrgMatrixForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
			toFormPropertyMap.put("fdCategory.fdName", "fdCategoryName");
			toFormPropertyMap.put("authReaders",
					new ModelConvertor_ModelListToString(
							"authReaderIds:authReaderNames", "fdId:fdName"));
			toFormPropertyMap.put("authEditors",
					new ModelConvertor_ModelListToString(
							"authEditorIds:authEditorNames", "fdId:fdName"));
			toFormPropertyMap.put("fdRelationConditionals",
					new ModelConvertor_ModelListToFormList("fdRelationConditionals"));
			toFormPropertyMap.put("fdRelationResults",
					new ModelConvertor_ModelListToFormList("fdRelationResults"));
			toFormPropertyMap.put("fdDataCates", new ModelConvertor_ModelListToFormList("fdDataCates"));
			toFormPropertyMap.put("fdVersions", new ModelConvertor_ModelListToFormList("fdVersions"));
		}
		return toFormPropertyMap;
	}

	/**
	 * 关系信息
	 */
	private List<SysOrgMatrixRelation> fdRelations;

	public List<SysOrgMatrixRelation> getFdRelations() {
		return fdRelations;
	}

	public void setFdRelations(List<SysOrgMatrixRelation> relations) {
		this.fdRelations = relations;
	}

	public String getFdNameOri() {
		return fdName;
	}

	public String getMatrixType() {
		return matrixType;
	}

	public void setMatrixType(String matrixType) {
		this.matrixType = matrixType;
	}

	/**
	 * 矩阵版本
	 */
	private List<SysOrgMatrixVersion> fdVersions;

	public List<SysOrgMatrixVersion> getFdVersions() {
		return fdVersions;
	}

	public void setFdVersions(List<SysOrgMatrixVersion> fdVersions) {
		this.fdVersions = fdVersions;
	}

	/**
	 * 是否开启分组
	 */
	private Boolean fdIsEnabledCate;

	public Boolean getFdIsEnabledCate() {
		if (fdIsEnabledCate == null) {
			if (CollectionUtils.isEmpty(fdDataCates)) {
				fdIsEnabledCate = false;
			} else {
				fdIsEnabledCate = true;
			}
		}
		return fdIsEnabledCate;
	}

	public void setFdIsEnabledCate(Boolean fdIsEnabledCate) {
		this.fdIsEnabledCate = fdIsEnabledCate;
	}

	/**
	 * 矩阵数据分组
	 */
	private List<SysOrgMatrixDataCate> fdDataCates;

	public List<SysOrgMatrixDataCate> getFdDataCates() {
		return fdDataCates;
	}

	public void setFdDataCates(List<SysOrgMatrixDataCate> fdDataCates) {
		this.fdDataCates = fdDataCates;
	}

}
