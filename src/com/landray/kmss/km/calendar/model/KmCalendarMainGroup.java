package com.landray.kmss.km.calendar.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarMainGroupForm;

public class KmCalendarMainGroup extends BaseModel {

	private KmCalendarPersonGroup fdGroup;

	public KmCalendarPersonGroup getFdGroup() {
		return fdGroup;
	}

	public void setFdGroup(KmCalendarPersonGroup fdGroup) {
		this.fdGroup = fdGroup;
	}

	private List<KmCalendarMain> fdMainList;

	public List<KmCalendarMain> getFdMainList() {
		return fdMainList;
	}

	public void setFdMainList(List<KmCalendarMain> fdMainList) {
		this.fdMainList = fdMainList;
	}

	@Override
	public Class getFormClass() {
		return KmCalendarMainGroupForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdMainList",
					new ModelConvertor_ModelListToString(
							"fdMainIds:fdMainNames", "fdId:docSubject"));
		}
		return toFormPropertyMap;
	}
}
