
package com.landray.kmss.sys.attachment.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.print.forms.SysPrintLogForm;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogForm;
import com.landray.kmss.web.action.ActionMapping;


/**
 * 创建日期 2006-九月-04
 * @author 叶中奇
 */
public class SysAttMainForm extends ExtendForm implements ISysPrintLogForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 模型ID
	 */
    private String fdModelId = null;
	/*
	 * 模型
	 */
    private String fdModelName = null;
	/*
	 * KEY
	 */
    private String fdKey = null;
	/*
	 * 文件名
	 */
    private String fdFileName = null;
	/*
	 * 文件类型
	 */
    private String fdContentType = null;
	/*
	 * 附件类型
	 */
    private String fdAttType = null;
	/*
	 * 创建时间
	 */
    private String docCreateTime = null;
    /**
     * 文件id
     */
    private String fdFileId = null;
	/*
	 * 创建人Id
	 */
    private String  fdCreatorId ;
	/*
	 * 下载量
	 */
    private Integer  downloadSum ;

	// 原来这段代码本身就不起作用，应该是已经不会用的代码
	// private List formFiles = new AutoArrayList(DiskFile.class);
	private List formFiles = new ArrayList();
	
	
    /*
	 * 图片需压缩大小
	 */
	protected String width;
	
	protected String height;
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

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return 返回 模型ID
	 */
	public String getFdModelId() {
		return fdModelId;
	}
	
	/**
	 * @param fdModelId 要设置的 模型ID
	 */
	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}
	/**
	 * @return 返回 模型
	 */
	public String getFdModelName() {
		return fdModelName;
	}
	
	/**
	 * @param fdModelName 要设置的 模型
	 */
	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	/**
	 * @return 返回 KEY
	 */
	public String getFdKey() {
		return fdKey;
	}
	
	/**
	 * @param fdKey 要设置的 KEY
	 */
	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}
	/**
	 * @return 返回 文件名
	 */
	public String getFdFileName() {
		return fdFileName;
	}
	
	/**
	 * @param fdFileName 要设置的 文件名
	 */
	public void setFdFileName(String fdFileName) {
		this.fdFileName = fdFileName;
	}
	/**
	 * @return 返回 文件类型
	 */
	public String getFdContentType() {
		return fdContentType;
	}
	
	/**
	 * @param fdContentType 要设置的 文件类型
	 */
	public void setFdContentType(String fdContentType) {
		this.fdContentType = fdContentType;
	}
	/**
	 * @return 返回 附件类型
	 */
	public String getFdAttType() {
		return fdAttType;
	}
	
	/**
	 * @param fdAttType 要设置的 附件类型
	 */
	public void setFdAttType(String fdAttType) {
		this.fdAttType = fdAttType;
	}
	/**
	 * @return 返回 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 要设置的 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
    
	/*
	 *  （非 Javadoc）
	 * @see com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
	    fdModelId = null;
	    fdModelName = null;
	    fdKey = null;
	    fdFileName = null;
	    fdContentType = null;
	    fdAttType = null;
	    docCreateTime = null;
	    width = null;
	    height = null;
	    proportion = null;
	    downloadSum = 0;
	    fdCreatorId=null ;
	    fdFileId = null;
	    fdBorrowCount = null;
	    fdSize = null;
	    fdOriginId = null;
	    fdVersion = null;
	    fdUploaderId = null;
	    fdUploadTime = null;	    
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
		return SysAttMain.class;
	}

	public List getFormFiles() {
		return formFiles;
	}


	public void setFormFiles(List formFiles) {
		this.formFiles = formFiles;
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

	// 打印机制日志
	private SysPrintLogForm sysPrintLogForm = new SysPrintLogForm();

	@Override
	public SysPrintLogForm getSysPrintLogForm() {
		return sysPrintLogForm;
	}

	@Override
	public void setSysPrintLogForm(SysPrintLogForm sysPrintLogForm) {
		this.sysPrintLogForm = sysPrintLogForm;
	}
	
	/**
	 * 借阅次数
	 */
	private String fdBorrowCount;

	public String getFdBorrowCount() {
		return fdBorrowCount;
	}

	public void setFdBorrowCount(String fdBorrowCount) {
		this.fdBorrowCount = fdBorrowCount;
	}

	/**
	 * 文件大小
	 */
	private String fdSize;

	public String getFdSize() {
		return fdSize;
	}

	public void setFdSize(String fdSize) {
		this.fdSize = fdSize;
	}
	
	/**
	 * 源附件id
	 */
	private String fdOriginId;
	
	public String getFdOriginId() {
		return fdOriginId;
	}

	public void setFdOriginId(String fdOriginId) {
		this.fdOriginId = fdOriginId;
	}
	
	/**
	 * 历史附件版本
	 */
	private Integer fdVersion;
	
	public Integer getFdVersion() {
		return fdVersion;
	}

	public void setFdVersion(Integer fdVersion) {
		this.fdVersion = fdVersion;
	}
	
	/**
	 * 上传者
	 */
	private String fdUploaderId;
	
	public String getFdUploaderId() {
		return fdUploaderId;
	}

	public void setFdUploaderId(String fdUploaderId) {
		this.fdUploaderId = fdUploaderId;
	}
	
	/**
	 * 上传时间
	 */
	private String fdUploadTime = null;
	
	public String getFdUploadTime() {
		return fdUploadTime;
	}

	public void setFdUploadTime(String fdUploadTime) {
		this.fdUploadTime = fdUploadTime;
	}

}
	
