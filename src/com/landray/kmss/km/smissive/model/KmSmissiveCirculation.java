package com.landray.kmss.km.smissive.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseCreateInfoModel;
import com.landray.kmss.km.smissive.forms.KmSmissiveCirculationForm;

/**
 * 
 * @author 张鹏xn 传阅记录
 */
@SuppressWarnings("serial")
public class KmSmissiveCirculation extends BaseCreateInfoModel {
	protected KmSmissiveMain fdSmissiveMain;
	protected String docSubject;
	protected String fdCirculationIds;
	protected String fdCirculationNames;

	public KmSmissiveCirculation() {
		super();
	}

	public KmSmissiveMain getFdSmissiveMain() {
		return fdSmissiveMain;
	}

	public void setFdSmissiveMain(KmSmissiveMain fdSmissiveMain) {
		this.fdSmissiveMain = fdSmissiveMain;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public String getFdCirculationIds() {
		return fdCirculationIds;
	}

	public void setFdCirculationIds(String fdCirculationIds) {
		this.fdCirculationIds = fdCirculationIds;
	}

	public String getFdCirculationNames() {
		return fdCirculationNames;
	}

	public void setFdCirculationNames(String fdCirculationNames) {
		this.fdCirculationNames = fdCirculationNames;
	}

	@Override
    public Class<KmSmissiveCirculationForm> getFormClass() {
		return KmSmissiveCirculationForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdSmissiveMain.fdId", "fdSmissiveMainId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}
}
