package com.landray.kmss.sys.attachment.borrow.service.spring;

import com.landray.kmss.sys.attachment.borrow.service.ISysAttBorrowValidator;

/**
 * 打印权限校验器
 * 
 * @author
 *
 */
public class SysAttBorrowCopyValidator extends ISysAttBorrowValidator {

	@Override
	public String getType() {
		return "print";
	}

	@Override
	public String getProp() {
		return "fdPrintEnable";
	}

}
