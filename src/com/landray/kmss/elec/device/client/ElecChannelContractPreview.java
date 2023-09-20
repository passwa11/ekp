package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2020年1月7日
*@Description                    合同预览
*/

public class ElecChannelContractPreview implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	private String contractId;
	
	private Integer docOrder;
	
	private Integer pageNum;
	
	//图片dpi
	private Number dpi;
	
	//图片质量
	private Number quality;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Integer getDocOrder() {
		return docOrder;
	}

	public void setDocOrder(Integer docOrder) {
		this.docOrder = docOrder;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Number getDpi() {
		return dpi;
	}

	public void setDpi(Number dpi) {
		this.dpi = dpi;
	}

	public Number getQuality() {
		return quality;
	}

	public void setQuality(Number quality) {
		this.quality = quality;
	}
}
