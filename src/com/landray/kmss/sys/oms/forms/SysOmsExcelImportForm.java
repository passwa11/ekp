package com.landray.kmss.sys.oms.forms;

import com.landray.kmss.web.upload.FormFile;

import com.landray.kmss.common.forms.ExtendForm;

public class SysOmsExcelImportForm extends ExtendForm {
	private static final long serialVersionUID = -384224618178288173L;
	private FormFile file; // 上传的文件
	private String type; // 上传的类型
	
	 /**
     * 部门是否正序
     */
    private String fdDeptIsAsc;
    
    public String getFdDeptIsAsc() {
		return fdDeptIsAsc;
	}

	public void setFdDeptIsAsc(String fdDeptIsAsc) {
		this.fdDeptIsAsc = fdDeptIsAsc;
	}
	/**
     * 人员是否正序
     */
    private String fdPersonIsAsc;
    
    public String getFdPersonIsAsc() {
		return fdPersonIsAsc;
	}

	public void setFdPersonIsAsc(String fdPersonIsAsc) {
		this.fdPersonIsAsc = fdPersonIsAsc;
	}
	
	 private String fdPersonIsMainDept;
	 private String fdPersonDeptIsFull;
	 private String fdPersonPostIsFull;
	
	
	public String getFdPersonIsMainDept() {
		return fdPersonIsMainDept;
	}

	public void setFdPersonIsMainDept(String fdPersonIsMainDept) {
		this.fdPersonIsMainDept = fdPersonIsMainDept;
	}

	public String getFdPersonDeptIsFull() {
		return fdPersonDeptIsFull;
	}

	public void setFdPersonDeptIsFull(String fdPersonDeptIsFull) {
		this.fdPersonDeptIsFull = fdPersonDeptIsFull;
	}

	public String getFdPersonPostIsFull() {
		return fdPersonPostIsFull;
	}

	public void setFdPersonPostIsFull(String fdPersonPostIsFull) {
		this.fdPersonPostIsFull = fdPersonPostIsFull;
	}

	@Override
	public Class getModelClass() {
		return null;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
