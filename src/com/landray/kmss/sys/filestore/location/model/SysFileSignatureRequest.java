package com.landray.kmss.sys.filestore.location.model;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * signature请求参数对象
 */
public class SysFileSignatureRequest {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileSignatureRequest.class);

	private String fileSize;

	private String fileMd5;

	private String fileName;

	private String attId;

	private String path;

	private String fileId;
	
	private String userAgent;

	public String getPath() {

		if (StringUtil.isNull(path)) {
			try {
				path = ((ISysAttUploadService) SpringBeanUtil
						.getBean("sysAttUploadService"))
								.generatePath(getFileId());
			} catch (Exception e) {
				logger.error("生成存储链接失败:", e);
			}
		}

		return path;
	}

	/**
	 * 文件大小
	 * 
	 * @return
	 */
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 文件md5
	 * 
	 * @return
	 */
	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	/**
	 * 文件原始名称
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 附件sysAttMain主键
	 * 
	 * @return
	 */
	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}

	public String getFileId() {
		if (StringUtil.isNull(fileId)) {
			fileId = IDGenerator.generateID();
		}
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public SysFileSignatureRequest(HttpServletRequest request) {

		this.setAttId(request.getParameter("fdAttMainId"));
		this.setFileMd5(request.getParameter("fileMd5"));
		this.setFileSize(request.getParameter("fileSize"));
		this.setFileName(request.getParameter("fileName"));
		this.setUserAgent(request.getHeader("User-Agent"));
	}

	public SysFileSignature createSignature() {

		SysFileSignature sysFileSignature = new SysFileSignature();

		sysFileSignature.setPath(getPath());
		sysFileSignature.setFileId(getFileId());

		return sysFileSignature;

	}

	@Override
	public String toString() {
		return "SysFileSignatureRequest [fileSize=" + fileSize + ", fileMd5="
				+ fileMd5 + ", fileName=" + fileName + ", attId=" + attId
				+ ", path=" + path + ", fileId=" + fileId + "]";
	}

}
