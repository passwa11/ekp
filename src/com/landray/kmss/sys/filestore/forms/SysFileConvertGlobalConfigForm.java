package com.landray.kmss.sys.filestore.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;

public class SysFileConvertGlobalConfigForm extends ExtendForm {
	private static final long serialVersionUID = 5203636019451921244L;
	private String attConvertEnable;
	private String attConvertOldSuccessUseHTML;
	private String attConvertHighFidelityDoc;
	private String attConvertHighFidelityDocx;
	private String attConvertHighFidelityPpt;
	private String attConvertHighFidelityPptx;
	private String attConvertHighFidelityPdf;
	private String attConvertHighFidelityWps;
	private String hideNames;
	private String unsignedTaskGetNum;
	private String distributeThreadSleepTime;
	private String longTaskSize;
	private String converter_aspose; //ASPOES服务
	private String converter_yozo; //永中服务
	private String converter_wps; //WPS服务
	private String converter_skofd; //数科OFD转换服务
	private String converter_wps_center; //WPS中台转
	private String converter_dianju; // 点聚转换
	private String converter_foxit; // 福昕转换

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		attConvertEnable = null;
		attConvertHighFidelityDoc = null;
		attConvertHighFidelityDocx = null;
		attConvertOldSuccessUseHTML = null;
		attConvertHighFidelityPdf = null;
		attConvertHighFidelityPpt = null;
		attConvertHighFidelityPptx = null;
		attConvertHighFidelityWps = null;
		hideNames = null;
		super.reset(mapping, request);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getModelClass() {
		return null;
	}

	public String getAttConvertHighFidelityWps() {
		return attConvertHighFidelityWps;
	}

	public void setAttConvertHighFidelityWps(String attConvertHighFidelityWps) {
		this.attConvertHighFidelityWps = attConvertHighFidelityWps;
	}

	public String getAttConvertEnable() {
		return attConvertEnable;
	}

	public void setAttConvertEnable(String attConvertEnable) {
		this.attConvertEnable = attConvertEnable;
	}

	public String getAttConvertOldSuccessUseHTML() {
		return attConvertOldSuccessUseHTML;
	}

	public void setAttConvertOldSuccessUseHTML(String attConvertOldSuccessUseHTML) {
		this.attConvertOldSuccessUseHTML = attConvertOldSuccessUseHTML;
	}

	public String getAttConvertHighFidelityDoc() {
		return attConvertHighFidelityDoc;
	}

	public void setAttConvertHighFidelityDoc(String attConvertHighFidelityDoc) {
		this.attConvertHighFidelityDoc = attConvertHighFidelityDoc;
	}

	public String getAttConvertHighFidelityDocx() {
		return attConvertHighFidelityDocx;
	}

	public void setAttConvertHighFidelityDocx(String attConvertHighFidelityDocx) {
		this.attConvertHighFidelityDocx = attConvertHighFidelityDocx;
	}

	public String getAttConvertHighFidelityPpt() {
		return attConvertHighFidelityPpt;
	}

	public void setAttConvertHighFidelityPpt(String attConvertHighFidelityPpt) {
		this.attConvertHighFidelityPpt = attConvertHighFidelityPpt;
	}

	public String getAttConvertHighFidelityPptx() {
		return attConvertHighFidelityPptx;
	}

	public void setAttConvertHighFidelityPptx(String attConvertHighFidelityPptx) {
		this.attConvertHighFidelityPptx = attConvertHighFidelityPptx;
	}

	public String getAttConvertHighFidelityPdf() {
		return attConvertHighFidelityPdf;
	}

	public void setAttConvertHighFidelityPdf(String attConvertHighFidelityPdf) {
		this.attConvertHighFidelityPdf = attConvertHighFidelityPdf;
	}

	public String getHideNames() {
		return hideNames;
	}

	public void setHideNames(String hideNames) {
		this.hideNames = hideNames;
	}

	public String getUnsignedTaskGetNum() {
		return unsignedTaskGetNum;
	}

	public void setUnsignedTaskGetNum(String unsignedTaskGetNum) {
		this.unsignedTaskGetNum = unsignedTaskGetNum;
	}

	public String getDistributeThreadSleepTime() {
		return distributeThreadSleepTime;
	}

	public void setDistributeThreadSleepTime(String distributeThreadSleepTime) {
		this.distributeThreadSleepTime = distributeThreadSleepTime;
	}

	public String getLongTaskSize() {
		return longTaskSize;
	}

	public void setLongTaskSize(String longTaskSize) {
		this.longTaskSize = longTaskSize;
	}

	public String getConverter_aspose() {
		return converter_aspose;
	}

	public void setConverter_aspose(String converter_aspose) {
		this.converter_aspose = converter_aspose;
	}


	public String getConverter_wps() {
		return converter_wps;
	}

	public void setConverter_wps(String converter_wps) {
		this.converter_wps = converter_wps;
	}

	public String getConverter_skofd() {
		return converter_skofd;
	}

	public void setConverter_skofd(String converter_skofd) {
		this.converter_skofd = converter_skofd;
	}

	public String getConverter_wps_center() {
		return converter_wps_center;
	}

	public void setConverter_wps_center(String converter_wps_center) {
		this.converter_wps_center = converter_wps_center;
	}

	public String getConverter_dianju() {
		return converter_dianju;
	}

	public void setConverter_dianju(String converter_dianju) {
		this.converter_dianju = converter_dianju;
	}

	public String getConverter_foxit() {
		return converter_foxit;
	}

	public void setConverter_foxit(String converter_foxit) {
		this.converter_foxit = converter_foxit;
	}

	public String getConverter_yozo() {
		return converter_yozo;
	}

	public void setConverter_yozo(String converter_yozo) {
		this.converter_yozo = converter_yozo;
	}
}
