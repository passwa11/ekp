package com.landray.kmss.sys.zone.forms;

import java.util.List;

import com.landray.kmss.sys.category.forms.SysCategoryMainForm;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.util.AutoArrayList;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SysZoneAddressCateForm extends SysCategoryMainForm {

	private static final long serialVersionUID = 8265426874981348396L;

	private List cateRelations = new AutoArrayList(SysZoneOrgRelationForm.class);
	private String cateType;

	private String fdItemType;// 事项类型

	public String getFdItemType() {
		return fdItemType;
	}

	public void setFdItemType(String fdItemType) {
		this.fdItemType = fdItemType;
	}

	@Override
	public Class getModelClass() {
		return SysCategoryMain.class;
	}

	public List getCateRelations() {
		return cateRelations;
	}

	public void setCateRelations(List cateRelations) {
		this.cateRelations = cateRelations;
	}

	public String getCateType() {
		return cateType;
	}

	public void setCateType(String cateType) {
		this.cateType = cateType;
	}

	public void addOrgRelation(SysZoneOrgRelationForm orgRelationForm) {
		cateRelations.add(orgRelationForm);
	}

	protected String docCreatorId = null;
	protected String docAlterorId = null;

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}
}
