package com.landray.kmss.sys.filestore.location.log.model;

import java.io.Serializable;
import java.util.Date;

import com.landray.kmss.util.UserUtil;

/**
 * 日志对象
 */
public class SysFileLocationLog implements Serializable {

	private static final long serialVersionUID = 1L;

	public SysFileLocationLog() {
		super();
		this.setFdLoginName(UserUtil.getUser().getFdLoginName());
		this.setFdNow(new Date());
	}

	/**
	 * 操作信息
	 */
	private String fdName;

	/**
	 * 操作者
	 */
	private String fdLoginName;

	/**
	 * 操作时间
	 */
	private Date fdNow;

	/**
	 * 请求参数
	 */
	private String fdReq;

	/**
	 * 返回结果
	 */
	private String fdResp;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdLoginName() {
		return fdLoginName;
	}

	public void setFdLoginName(String fdLoginName) {
		this.fdLoginName = fdLoginName;
	}

	public Date getFdNow() {
		return fdNow;
	}

	public void setFdNow(Date fdNow) {
		this.fdNow = fdNow;
	}

	public void setFdReq(String... fdReq) {

		StringBuilder builder = new StringBuilder();

		builder.append("[");

		for (int i = 0; i < fdReq.length; i++) {

			if (i > 0) {
				builder.append(",");
			}
			builder.append(fdReq[i]);

		}
		builder.append("]");
		this.fdReq = builder.toString();

	}

	public String getFdReq() {
		return fdReq;
	}

	public String getFdResp() {
		return fdResp;
	}

	public void setFdResp(String fdResp) {
		this.fdResp = fdResp;
	}

}
