package com.landray.kmss.tic.jdbc.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcRelationService;


/**
 * 映射关系 Action
 * 
 * @author 
 * @version 1.0 2013-07-24
 */
public class TicJdbcRelationAction extends ExtendAction {
	protected ITicJdbcRelationService ticJdbcRelationService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(ticJdbcRelationService == null) {
			ticJdbcRelationService = (ITicJdbcRelationService)getBean("ticJdbcRelationService");
		}
		return ticJdbcRelationService;
	}
}

