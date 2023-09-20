package com.landray.kmss.sys.attachment.borrow.service;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import java.util.List;

/**
 * 业务模块借阅权限校验器
 * 
 * @author xuyz
 * @date 2022/02/18
 *
 */
public interface ISysAttBorrowBusinessModuleValidator {

	/**
	 * 附件借阅权限校验
	 * @param validatorContext
	 * @param attMain
	 * @return
	 * @throws Exception
	 */
	boolean validate(ValidatorRequestContext validatorContext, SysAttMain attMain) throws Exception;
}
