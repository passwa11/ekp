package com.landray.kmss.km.imeeting.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingVicePlaceDetail;

public class KmImeetingVicePlaceDetailForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	private String fdMeetingId;

	private String fdPlaceId;

	private String fdPlaceName;

	private String fdPlaceUserTime;

	private String fdOtherPlace;

	private String fdOtherPlaceCoordinate;

	public String getFdMeetingId() {
		return fdMeetingId;
	}

	public void setFdMeetingId(String fdMeetingId) {
		this.fdMeetingId = fdMeetingId;
	}

	public String getFdPlaceId() {
		return fdPlaceId;
	}

	public void setFdPlaceId(String fdPlaceId) {
		this.fdPlaceId = fdPlaceId;
	}

	public String getFdPlaceName() {
		return fdPlaceName;
	}

	public void setFdPlaceName(String fdPlaceName) {
		this.fdPlaceName = fdPlaceName;
	}

	public String getFdPlaceUserTime() {
		return fdPlaceUserTime;
	}

	public void setFdPlaceUserTime(String fdPlaceUserTime) {
		this.fdPlaceUserTime = fdPlaceUserTime;
	}

	public String getFdOtherPlace() {
		return fdOtherPlace;
	}

	public void setFdOtherPlace(String fdOtherPlace) {
		this.fdOtherPlace = fdOtherPlace;
	}

	public String getFdOtherPlaceCoordinate() {
		return fdOtherPlaceCoordinate;
	}

	public void setFdOtherPlaceCoordinate(String fdOtherPlaceCoordinate) {
		this.fdOtherPlaceCoordinate = fdOtherPlaceCoordinate;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdMeetingId", new FormConvertor_IDToModel(
					"fdMeeting", KmImeetingMain.class));
			toModelPropertyMap.put("fdPlaceId", new FormConvertor_IDToModel(
					"fdPlace", KmImeetingRes.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class<KmImeetingVicePlaceDetail> getModelClass() {
		return KmImeetingVicePlaceDetail.class;
	}

}
