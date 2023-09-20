package com.landray.kmss.tic.rest.client.error;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.landray.kmss.util.StringUtil;


/**
 * 微信错误码.
 */
public class RestError implements Serializable {
	private static final long serialVersionUID = 7869786563361406291L;

	/**
	 * 错误代码.
	 */
	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 错误信息. （如果可以翻译为中文，就为中文）
	 */
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * 接口返回的错误原始信息（英文）.
	 */
	private String errorMsgEn;

	public String getErrorMsgEn() {
		return errorMsgEn;
	}

	public void setErrorMsgEn(String errorMsgEn) {
		this.errorMsgEn = errorMsgEn;
	}

	private String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	private RestErrorKeys restErrorKeys ;

	public void setRestErrorKeys(RestErrorKeys restErrorKeys) {
		this.restErrorKeys = restErrorKeys;
	}
	
	public RestErrorKeys getRestErrorKeys() {
		return restErrorKeys;
	}
	
	boolean hasError = false;
	public boolean hasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	public static RestError fromJson(String json,RestErrorKeys restErrorKeys) {
		final RestError restError = new RestError();
		restError.setRestErrorKeys(restErrorKeys);
		restError.setJson(json);
		JSONObject o = null;
		try {
			o = JSONObject.parseObject(json, Feature.OrderedField);
		} catch (Exception e) {
			return restError;
		}
		if(StringUtil.isNotNull(restErrorKeys.getCodeKey())) {
			if(o.containsKey(restErrorKeys.getCodeKey())) {
				String code = o.getString(restErrorKeys.getCodeKey());
				restError.setErrorCode(code);
				if(StringUtil.isNotNull(restErrorKeys.getSuccValue())) {
					if(!restErrorKeys.getSuccValue().equals(code)) {
						restError.setHasError(true);
						if(StringUtil.isNotNull(restErrorKeys.getMsgKey())) {
							if(o.containsKey(restErrorKeys.getMsgKey())) {
								restError.setErrorMsg(o.getString(restErrorKeys.getMsgKey()));
							}
						}
						
					}
				}
			}
		}
		return restError;
	}

	public static RestError fromMsg(String msg) {
		final RestError restError = new RestError();
		restError.setErrorMsg(msg);
		return restError;
	}

	@Override
	public String toString() {
		if (this.json != null) {
			return this.json;
		}
		return "错误: Code=" + this.errorCode + ", Msg=" + this.errorMsg;
	}

}
