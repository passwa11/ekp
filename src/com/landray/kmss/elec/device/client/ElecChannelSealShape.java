package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2020年6月9日
*@Description                     印章形状
*/
public class ElecChannelSealShape implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	private String sealShapeId;
	
	private String sealShapeName;
	
	public ElecChannelSealShape() {
		super();
	}
	
	public ElecChannelSealShape(String sealShapeId, String sealShapeName) {
		super();
		this.sealShapeId = sealShapeId;
		this.sealShapeName = sealShapeName;
	}

	public String getSealShapeId() {
		return sealShapeId;
	}

	public ElecChannelSealShape setSealShapeId(String sealShapeId) {
		this.sealShapeId = sealShapeId;
		return this;
	}

	public String getSealShapeName() {
		return sealShapeName;
	}

	public ElecChannelSealShape setSealShapeName(String sealShapeName) {
		this.sealShapeName = sealShapeName;
		return this;
	}

	@Override
	public String toString() {
		return "ElecChannelSealShape [sealShapeId=" + sealShapeId
				+ ", sealShapeName=" + sealShapeName + "]";
	}
}
