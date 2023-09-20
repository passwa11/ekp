package com.landray.kmss.fssc.voucher.service.spring;

import com.landray.kmss.fssc.voucher.service.IFsscVoucherMainService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

import java.util.Date;

/**
 * 【财务共享】凭证中心--凭证自动记账定时任务
 */
public class FsscVoucherAutoBookkeeping {

	private IFsscVoucherMainService fsscVoucherMainService;

	/**
	 * 【财务共享】凭证中心--凭证自动记账定时任务
	 * 
	 * @throws Exception
	 */
	public void updateAutoBookkeeping(SysQuartzJobContext context) throws Exception {
		context.logMessage(fsscVoucherMainService.updateAutoBookkeeping(new Date()));
	}

	public void setFsscVoucherMainService(IFsscVoucherMainService fsscVoucherMainService) {
		this.fsscVoucherMainService = fsscVoucherMainService;
	}
}
