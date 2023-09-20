package com.landray.kmss.km.imeeting.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingVicePlaceDetailForm;

public class KmImeetingVicePlaceDetail extends BaseModel {

	private static ModelToFormPropertyMap toFormPropertyMap;

	private KmImeetingMain fdMeeting;

	private KmImeetingRes fdPlace;

	private String fdOtherPlace;

	private String fdOtherPlaceCoordinate;
	
	private Integer meetingIndex;

	public Integer getMeetingIndex() {
		return meetingIndex;
	}

	public void setMeetingIndex(Integer meetingIndex) {
		this.meetingIndex = meetingIndex;
	}

	public KmImeetingMain getFdMeeting() {
		return fdMeeting;
	}

	public void setFdMeeting(KmImeetingMain fdMeeting) {
		this.fdMeeting = fdMeeting;
	}

	public KmImeetingRes getFdPlace() {
		return fdPlace;
	}

	public void setFdPlace(KmImeetingRes fdPlace) {
		this.fdPlace = fdPlace;
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
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdMeeting.fdId", "fdMeetingId");
			toFormPropertyMap.put("fdPlace.fdId", "fdPlaceId");
			toFormPropertyMap.put("fdPlace.fdName", "fdPlaceName");
			toFormPropertyMap.put("fdPlace.fdUserTime", "fdPlaceUserTime");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class<KmImeetingVicePlaceDetailForm> getFormClass() {
		return KmImeetingVicePlaceDetailForm.class;
	}

}
