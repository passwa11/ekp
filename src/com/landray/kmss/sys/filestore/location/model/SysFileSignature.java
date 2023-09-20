package com.landray.kmss.sys.filestore.location.model;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.util.StringUtil;

/**
 * 上传signature对象
 */

public class SysFileSignature {

	/**
	 * signature放置header
	 */
	private Map<String, String> header = new HashMap<>();

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	/**
	 * signature放置body
	 */
	private Map<String, String> body = new HashMap<>();

	public Map<String, String> getBody() {
		return body;
	}

	public void setBody(Map<String, String> body) {
		this.body = body;
	}

	/**
	 * 文件路径
	 */
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public SysFileSignature() {
		super();
	}

	/**
	 * sysAttFile主键
	 */
	private String fileId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * 转换为map，兼容旧接口
	 */
	public Map<String, Object> toMap() {

		Map<String, Object> map = new HashMap<>();

		if (!this.getHeader().isEmpty()) {
			map.put("header", this.getHeader());
		}

		if (!this.getBody().isEmpty()) {
			map.put("body", this.getBody());
		}

		if (StringUtil.isNotNull(getPath())) {
			map.put("path", getPath());
		}

		if (StringUtil.isNotNull(getFileId())) {
			map.put("fileId", getFileId());
		}

		return map;

	}

	@Override
	public String toString() {
		return "SysFileSignature [header=" + header + ", body=" + body
				+ ", path=" + path + ", fileId=" + fileId + "]";
	}

}
