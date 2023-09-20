package com.landray.kmss.sys.attachment.model;

import com.landray.kmss.sys.attachment.forms.SysAttMainForm;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogModel;
import com.landray.kmss.sys.print.model.SysPrintLog;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 创建日期 2006-九月-04
 * 
 * @author 叶中奇 附件
 */
public class SysAttMain extends SysAttMainBase
		implements InterceptFieldEnabled, ISysPrintLogModel {
	// 打印机制日志
	private SysPrintLog sysPrintLog = null;

	@Override
	public SysPrintLog getSysPrintLog() {
		return sysPrintLog;
	}

	@Override
	public void setSysPrintLog(SysPrintLog sysPrintLog) {
		this.sysPrintLog = sysPrintLog;
	}

}
