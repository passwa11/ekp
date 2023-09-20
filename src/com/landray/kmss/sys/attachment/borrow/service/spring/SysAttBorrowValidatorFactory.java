package com.landray.kmss.sys.attachment.borrow.service.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.landray.kmss.sys.attachment.borrow.service.ISysAttBorrowValidator;

public class SysAttBorrowValidatorFactory {

	private static List<ISysAttBorrowValidator> beans = new ArrayList<>();

	static {
		beans.add(new SysAttBorrowCopyValidator());
		beans.add(new SysAttBorrowPrintValidator());
		beans.add(new SysAttBorrowViewValidator());
		beans.add(new SysAttBorrowDownloadValidator());
	}

	/**
	 * 借阅验权
	 * 
	 * @param fdType
	 * @param fdAttId
	 * @return
	 * @throws Exception
	 */
	public static Boolean auth(String fdType, String fdAttId) throws Exception {

		ISysAttBorrowValidator validator = getValidator(fdType);

		if (validator == null) {
			return false;
		}

		return validator.auth(fdAttId);

	}

	/**
	 * 根据类型获取校验器
	 * 
	 * @param fdType
	 * @return
	 */
	private static ISysAttBorrowValidator getValidator(String fdType) {

		Iterator<ISysAttBorrowValidator> iter = beans.iterator();

		while (iter.hasNext()) {
			ISysAttBorrowValidator validator = iter.next();
			if (validator.getType().equals(fdType)) {
				return validator;
			}
		}

		return null;
	}

}
