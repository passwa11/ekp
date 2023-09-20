package com.landray.kmss.km.imeeting.model;

import java.util.Date;

/**
 * 会议室资源占用情况
 */
public class KmImeetingUse {

	private String fdId;

	private String fdPlaceId;
	 
	private String fdPlace;

	private String fdName;

	private Date fdHoldDate;

	private Date fdFinishDate;

	private String personName;

	private String docStatus;

	private Boolean isMeeting;

	private Boolean fdHasExam;

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getFdPlaceId() {
		return fdPlaceId;
	}

	public void setFdPlaceId(String fdPlaceId) {
		this.fdPlaceId = fdPlaceId;
	}

	public String getFdPlace() {
		return fdPlace;
	}

	public void setFdPlace(String fdPlace) {
		this.fdPlace = fdPlace;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public Date getFdHoldDate() {
		return fdHoldDate;
	}

	public void setFdHoldDate(Date fdHoldDate) {
		this.fdHoldDate = fdHoldDate;
	}

	public Date getFdFinishDate() {
		return fdFinishDate;
	}

	public void setFdFinishDate(Date fdFinishDate) {
		this.fdFinishDate = fdFinishDate;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public Boolean getIsMeeting() {
		return isMeeting;
	}

	public void setIsMeeting(Boolean isMeeting) {
		this.isMeeting = isMeeting;
	}

	public Boolean getFdHasExam() {
		return fdHasExam;
	}

	public void setFdHasExam(Boolean fdHasExam) {
		this.fdHasExam = fdHasExam;
	}

}
