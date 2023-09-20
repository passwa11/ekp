package com.landray.kmss.sys.filestore.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.filestore.model.SysFileConvertConfig;
import com.landray.kmss.util.StringUtil;

public class SysFileConvertConfigForm extends ExtendForm {

	private static final long serialVersionUID = -3890179405789992024L;
	private String fdFileExtName;
	private String fdModelName;
	private String fdConverterKey;

	private String fdDispenser;

	private String fdStatus;

	private String fdConverterType;

	private String fdHighFidelity;

	private String fdPicResolution;

	private String fdPicRectangle;
	
	private String converter_aspose; //ASPOSE服务
	private String converter_yozo; //永中服务
	private String converter_wps; //wps服务
	private String converter_skofd; //数科OFD转换服务
	private String converter_wpsCenter; //wps中台
	private String converter_dianju; // 点聚OFD转换
	private String converter_foxit; // 福昕OFD转换
	private String fdModul; //模块信息
	private String allModuls;//所模块，即不限模块
	private String fdFileExtNameId; //扩展名信息

	public String getFdStatus() {
		return StringUtil.isNull(fdStatus) ? "0" : fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdDispenser() {
		return fdDispenser;
	}

	public void setFdDispenser(String fdDispenser) {
		this.fdDispenser = fdDispenser;
	}

	public String getFdFileExtName() {
		return fdFileExtName;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public void setFdFileExtName(String fdFileExtName) {
		this.fdFileExtName = fdFileExtName;
	}

	public String getFdConverterKey() {
		return fdConverterKey;
	}

	public void setFdConverterKey(String fdConverterKey) {
		this.fdConverterKey = fdConverterKey;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdConverterKey = null;
		this.fdDispenser = null;
		this.fdFileExtName = null;
		this.fdModelName = null;
		super.reset(mapping, request);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getModelClass() {
		return SysFileConvertConfig.class;
	}

	public String getFdConverterType() {
		//return StringUtil.isNotNull(fdConverterType) ? fdConverterType : "aspose";
		return fdConverterType;
	}

	public void setFdConverterType(String fdConverterType) {
		this.fdConverterType = fdConverterType;
	}

	public String getFdHighFidelity() {
		return fdHighFidelity;
	}

	public void setFdHighFidelity(String fdHighFidelity) {
		this.fdHighFidelity = fdHighFidelity;
	}

	public String getFdPicResolution() {
		return StringUtil.isNotNull(fdPicResolution) ? fdPicResolution : "96";
	}

	public void setFdPicResolution(String fdPicResolution) {
		this.fdPicResolution = fdPicResolution;
	}

	public String getFdPicRectangle() {
		return StringUtil.isNotNull(fdPicRectangle) ? fdPicRectangle : "A3";
	}

	public void setFdPicRectangle(String fdPicRectangle) {
		this.fdPicRectangle = fdPicRectangle;
	}

	public String getConverter_aspose() {
		return converter_aspose;
	}

	public void setConverter_aspose(String converter_aspose) {
		this.converter_aspose = converter_aspose;
	}

	public String getConverter_yozo() {
		return converter_yozo;
	}

	public void setConverter_yozo(String converter_yozo) {
		this.converter_yozo = converter_yozo;
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

	public String getFdModul() {
		return fdModul;
	}

	public void setFdModul(String fdModul) {
		this.fdModul = fdModul;
	}

	public String getAllModuls() {
		return allModuls;
	}

	public void setAllModuls(String allModuls) {
		this.allModuls = allModuls;
	}

	public String getFdFileExtNameId() {
		return fdFileExtNameId;
	}

	public void setFdFileExtNameId(String fdFileExtNameId) {
		this.fdFileExtNameId = fdFileExtNameId;
	}

	public String getConverter_wpsCenter() {
		return converter_wpsCenter;
	}

	public void setConverter_wpsCenter(String converter_wpsCenter) {
		this.converter_wpsCenter = converter_wpsCenter;
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
}
