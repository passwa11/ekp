package com.landray.kmss.sys.attachment.borrow.service.spring;

import com.landray.kmss.sys.attachment.borrow.service.ISysAttBorrowValidator;

/**
 * 下载权限校验器
 * 
 * @author
 *
 */
public class SysAttBorrowDownloadValidator extends ISysAttBorrowValidator {

	@Override
	public String getType() {
		return "download";
	}

	@Override
	public String getProp() {
		return "fdDownloadEnable";
	}

}
