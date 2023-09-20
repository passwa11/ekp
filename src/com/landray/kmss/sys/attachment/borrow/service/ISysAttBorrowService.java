package com.landray.kmss.sys.attachment.borrow.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface ISysAttBorrowService extends IExtendDataService {

	/**
	 * 定时更新借阅状态
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void updateBorrowFdStatus(SysQuartzJobContext context)
			throws Exception;

	/**
	 * 关闭借阅
	 * 
	 * @param fdIds
	 * @throws Exception
	 */
	public void updateCloseStatus(String[] fdIds) throws Exception;
}
