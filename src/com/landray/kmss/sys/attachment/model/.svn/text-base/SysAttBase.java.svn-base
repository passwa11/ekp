package com.landray.kmss.sys.attachment.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Date;

import com.landray.kmss.common.model.BaseCoreInnerModel;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 创建日期 2006-九月-04
 * 
 * @author 叶中奇 附件
 */
public class SysAttBase extends BaseCoreInnerModel implements Cloneable,
		InterceptFieldEnabled {

    public static final String MECHANISM_NAME = "attachment";
    
    public static final String HISTORY_NAME = "historyVersionAttachment";

	public static final String WPS_CENTER_TEMP_NAME = "WPSCenterSelfAttachment";
    
    public static final String LOG_FIELD_NAME = "attachments";
    
	public static String ATTACHMENT_LOCATION_FILE = "file";

	public static String ATTACHMENT_LOCATION_DB = "db";

	public static String ATTACHMENT_LOCATION_FTP = "ftp";

	public static final String ATTACHMENT_LOCATION_SERVER = "server";
	
	public static final String ALiYun_SERVER = "aliyun";

	public static final String F4OSS_SERVER = "f4oss";

	public static final String wpsCenterLockKey = "wpsCenterSaveMarkLock";

	public static final String wpsCenterCacheKey = "wpsCenterSaveMark";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String ATTACHMENT_TYPE_PIC = "pic";

	public static String ATTACHMENT_TYPE_BYTE = "byte";
	
	public static String ATTACHMENT_TYPE_OFFICE = "office";

	protected InputStream in = null;

	protected OutputStream out = null;

	Blob fdData;

	/*
	 * 临时的附件key值（移动端KK和PC端WPS新建时，需要创建一个临时的文件，改附件保存一个临时的key值，文档提交之后再更新清除）
	 */
	protected java.lang.String fdTempKey;
	
	public java.lang.String getFdTempKey() {
		return fdTempKey;
	}

	public void setFdTempKey(java.lang.String fdTempKey) {
		this.fdTempKey = fdTempKey;
	}

	/*
	 * 原附件id
	 */
	protected String fdOriginId;
	
	public String getFdOriginId() {
		return fdOriginId;
	}

	public void setFdOriginId(String fdOriginId) {
		this.fdOriginId = fdOriginId;
	}
	
	/*
	 * 历史附件版本
	 */
	protected Integer fdVersion;
	
	public Integer getFdVersion() {
		return fdVersion;
	}

	public void setFdVersion(Integer fdVersion) {
		this.fdVersion = fdVersion;
	}
	
	/*
	 * 上传者
	 */
	protected String fdUploaderId;
	
	public String getFdUploaderId() {
		if(StringUtil.isNull(fdUploaderId)) {
            return fdCreatorId;
        }
		return fdUploaderId;
	}

	public void setFdUploaderId(String fdUploaderId) {
		this.fdUploaderId = fdUploaderId;
	}
	
	/*
	 * 上传时间
	 */
	protected Date fdUploadTime;
	
	public Date getFdUploadTime() {
		if(fdUploadTime==null) {
            return docCreateTime;
        }
		return fdUploadTime;
	}

	public void setFdUploadTime(Date fdUploadTime) {
		this.fdUploadTime = fdUploadTime;
	}
	
	/*
	 * 文件名
	 */
	protected java.lang.String fdFileName;

	/*
	 * 文件类型
	 */
	protected java.lang.String fdContentType;

	/*
	 * 附件类型
	 */
	protected java.lang.String fdAttType = ATTACHMENT_TYPE_BYTE;

	/*
	 * 创建时间
	 */
	protected java.util.Date docCreateTime;

	/*
	 * 文件保存路径
	 */
	protected java.lang.String fdFilePath;

	/*
	 * 文件大小
	 */
	protected java.lang.Double fdSize;

	protected Long fdDataId;

	protected String fdAttLocation;

	/*
	 * 图片需压缩大小
	 */
	protected Integer width;

	protected Integer height;
	/*
	 * 是否按高宽比例压缩
	 */
	protected String proportion;

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	/*
	 * 创建人Id
	 */
	protected String fdCreatorId;

	protected String fdFileId;

	/*
	 * 下载量
	 */
	protected Integer downloadSum = 0;

	/*
	 * 当前在线编辑用户
	 */
	protected String fdPersonId;
	/*
	 * 最后打开时间
	 */
	protected Date fdLastOpenTime;

	public Date getFdLastOpenTime() {
		return fdLastOpenTime;
	}

	public void setFdLastOpenTime(Date fdLastOpenTime) {
		this.fdLastOpenTime = fdLastOpenTime;
	}

	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		if(width == null) {
            this.width = 0;
        } else {
            this.width = width;
        }
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		if(height == null) {
            this.height = 0;
        } else {
            this.height = height;
        }
	}

	public String getFdAttLocation() {
		return fdAttLocation;
	}

	public void setFdAttLocation(String fdAttLocation) {
		this.fdAttLocation = fdAttLocation;
	}

	public Long getFdDataId() {
		return fdDataId;
	}

	public void setFdDataId(Long fdDataId) {
		this.fdDataId = fdDataId;
	}

	public SysAttBase() {
		super();
	}

	/*
	 * 排序号
	 */
	protected Integer fdOrder = null;

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * @return 返回 文件名
	 */
	public java.lang.String getFdFileName() {
		return fdFileName;
	}

	/**
	 * @param fdFileName
	 *            要设置的 文件名
	 */
	public void setFdFileName(java.lang.String fdFileName) {
		this.fdFileName = fdFileName;
	}

	/**
	 * @return 返回 文件类型
	 */
	public java.lang.String getFdContentType() {
		return fdContentType;
	}

	/**
	 * @param fdContentType
	 *            要设置的 文件类型
	 */
	public void setFdContentType(java.lang.String fdContentType) {
		this.fdContentType = fdContentType;
	}

	/**
	 * @return 返回 附件类型
	 */
	public java.lang.String getFdAttType() {
		return fdAttType;
	}

	/**
	 * @param fdAttType
	 *            要设置的 附件类型
	 */
	public void setFdAttType(java.lang.String fdAttType) {
		this.fdAttType = fdAttType;
	}

	/**
	 * @return 返回 创建时间
	 */
	public java.util.Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	public void setDocCreateTime(java.util.Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * @return 返回 文件保存路径
	 */
	public java.lang.String getFdFilePath() {
		return fdFilePath;
	}

	/**
	 * @param fdFilePath
	 *            要设置的 文件保存路径
	 */
	public void setFdFilePath(java.lang.String fdFilePath) {
		this.fdFilePath = fdFilePath;
	}

	/**
	 * @return 返回 文件大小
	 */
	public java.lang.Double getFdSize() {
		return fdSize;
	}

	/**
	 * @param fdSize
	 *            要设置的 文件大小
	 */
	public void setFdSize(java.lang.Double fdSize) {
		this.fdSize = fdSize;
	}

	public Blob getFdData() {
		if (ATTACHMENT_LOCATION_DB.equalsIgnoreCase(getFdAttLocation())) {
			return (Blob) readLazyField("fdData", fdData);
		} else {
			return fdData;
		}
	}

	public void setFdData(Blob fdData) {
		if (ATTACHMENT_LOCATION_DB.equalsIgnoreCase(getFdAttLocation())) {
			this.fdData = (Blob) writeLazyField("fdData", this.fdData, fdData);
		} else {
			this.fdData = fdData;
		}
	}

	@Override
	public Class getFormClass() {
		return null;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		SysAttBase at = (SysAttBase) super.clone();
		at.setFdId(IDGenerator.generateID());
		return at;
	}

	public InputStream getInputStream() {
		return in;
	}

	public void setInputStream(InputStream in) {
		this.in = in;
	}

	public OutputStream getOutputStream() {
		return out;
	}

	public void setOutputStream(OutputStream out) {
		this.out = out;
	}

	public String getFdCreatorId() {
		return fdCreatorId;
	}

	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId;
	}

	public Integer getDownloadSum() {
		return downloadSum;
	}

	public void setDownloadSum(Integer downloadSum) {
		this.downloadSum = downloadSum;
	}

	public String getFdFileId() {
		return fdFileId;
	}

	public void setFdFileId(String fdFileId) {
		this.fdFileId = fdFileId;
	}

	@Override 
	public String getMechanismName(){
	    return MECHANISM_NAME;
	}
}
