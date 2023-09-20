package com.landray.kmss.sys.ui.taglib.api;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

@SuppressWarnings("serial")
public class ResponseTag extends BaseTag {

	/**
	 * code ResponseCode.SUCCESS 0 表示成功，其他表示错误具体请参见 ResponseCode说明
	 */
	private String code = ResponseCode.SUCCESS;

	private String msg = ResponseConstant.EMTYP_MSG_STR;

	private JSONObject data = null;


	@Override
	public int doStartTag() throws JspException {
		data = new JSONObject();

		// 设置自己到页面上下文
		pageContext.getRequest().setAttribute(
				ResponseConstant.PAGE_ATTRIBUTE_KEY_RESPONSE_TAG, this);

		return EVAL_BODY_INCLUDE;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JSONObject getData() {
		return data;
	}

	/**
	 * 直接设置 data JSON数据对象， 忽略为空的data设置操作
	 * 
	 * @param data
	 */
	public void setData(JSONObject data) {
		if (data != null) {
			this.data = data;
		}
	}

	/**
	 * 添加数据区的子数据对象如： base/lbpm/xform/actions
	 * 
	 * @param name
	 * @param dataObject
	 */
	public void addDataObject(String name, JSONObject dataObject) {
		if (data == null) {
			data = new JSONObject();
		}
		if (name != null) {
			data.put(name, dataObject);
		}
	}

	@Override
	public void release() {
		data = null;
		code = ResponseCode.SUCCESS;
		msg = ResponseConstant.EMTYP_MSG_STR;
		super.release();
	}

	public JSONObject bulidJsonDatas() {
		JSONObject response = new JSONObject();
		response.put(ResponseConstant.KEY_CODE, code);
		response.put(ResponseConstant.KEY_MSG, msg);
		if (data != null) {
			response.put(ResponseConstant.KEY_DATA, data);
		}
		
		return response;
	}

	@Override
    public int doEndTag() throws JspException {
		try {
			String body = bulidJsonDatas().toString();
			pageContext.getOut().append(body);

		} catch (Exception e) {
			logger.error(e.toString());
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}
}
