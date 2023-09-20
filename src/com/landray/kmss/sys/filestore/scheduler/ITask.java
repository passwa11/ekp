package com.landray.kmss.sys.filestore.scheduler;

import java.util.Date;

public interface ITask {

	public String getFdId();

	public String getFdFileId();

	public String getFdAttMainId();

	public String getFdFileName();

	public String getFdConverterKey();

	public String getFdConverterParam();

	public Integer getFdConvertNumber();

	public Boolean getFdIsFinish();

	public Integer getFdConvertStatus();

	public String getFdDispenser();

	public Date getFdStatusTime();

	public Date getFdCreateTime();

	public String getFilePath();

	public String getCryptionMode();
}
