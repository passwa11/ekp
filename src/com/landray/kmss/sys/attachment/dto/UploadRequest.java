package com.landray.kmss.sys.attachment.dto;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlMimeType;

/**
 * 附件上传请求体
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
public class UploadRequest {
	//请求的方法 上传为upload
	private String gettype;

	//返回值类型 xml/json
	private String format = "json";

	//附件上传密钥，来源：/data/sys-attachment/sysAttMain/handleAttUpload => (gettype=getuserkey)
	private String userkey;

	/*
	 * id在EKP原前端js中的说明如下：
	 * 在新建页面有可能是模板带过来的附件，这个时候附件id不能相同，新建的时候附件id是在提交的时候由后台生成，
	 * 这里以WU_FILE开头来标识是新上传的，所以由模板带过来的附件id也必须以WU_FILE开头，但是阅读和下载必须要有附件原始id，
	 * 所以在这里定个规则，由模板带过来的附件id用“|”隔开，“|”后面的就是附件原始id，使用fdTemplateAttId来存储，用来点击阅读、下载等针对附件的操作，
	 * “|”前面就是以WU_FILE开头的模拟id,标识是新上传的
	 * （暂时只有文档知识库使用）
	 */
	private String id;
	//文件信息
	private String name;
	private String type;
	private String lastModifiedDate;
	private String size;

	//文件
	@XmlMimeType("application/octet-stream")
	private DataHandler file;

	public String getGettype() {
		return gettype;
	}

	public void setGettype(String gettype) {
		this.gettype = gettype;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public DataHandler getFile() {
		return file;
	}

	public void setFile(DataHandler file) {
		this.file = file;
	}
}
