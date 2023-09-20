package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年7月9日
*@Description             渠道响应数据（转换后的数据）
*/

@SuppressWarnings({"rawtypes","unchecked"})
public class ElecChannelResponseMessage<T> implements IElecChannelResponseMessage {

	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String desc;
	
	private T data;
	

	public ElecChannelResponseMessage() {
		super();
	}
	

	public ElecChannelResponseMessage(String code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}
	
	public ElecChannelResponseMessage(ElecChannelErrorCodeEnum errorCode) {
		super();
		this.code = errorCode.getCode();
		this.desc = errorCode.getDesc();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	
	public static ElecChannelResponseMessage fail(String code, String desc){
		return new ElecChannelResponseMessage(code, desc); 
	}
	
	public static ElecChannelResponseMessage fail(ElecChannelErrorCodeEnum errorCode){
		return new ElecChannelResponseMessage(errorCode); 
	}
	
	
	public static ElecChannelResponseMessage success(Object data){
		
		ElecChannelResponseMessage respMsg = new ElecChannelResponseMessage(ElecChannelErrorCodeEnum.SUCCESS);
		respMsg.setData(data);
		
		return respMsg;
	}
	
	public static ElecChannelResponseMessage success(){
		return new ElecChannelResponseMessage(ElecChannelErrorCodeEnum.SUCCESS);
	}
	
	public static boolean hasError(ElecChannelResponseMessage resMsg){
		return !ElecChannelErrorCodeEnum.SUCCESS.getCode().equals(resMsg.getCode());
	}
	
	public  boolean hasError(){
		return !ElecChannelErrorCodeEnum.SUCCESS.getCode().equals(this.getCode());
	}

}
