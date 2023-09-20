package com.landray.kmss.fssc.expense.service;

import com.landray.kmss.fssc.expense.model.FsscExpenseMain;

public interface IFsscExpenseMainRobotNodeService {

	
	/**
	 * 唤醒报销流程
	 * @param model
	 * @throws Exception
	 */
	public void wake(FsscExpenseMain model) throws Exception ;
	
	
}
