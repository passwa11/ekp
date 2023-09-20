package com.landray.kmss.tic.rest.executor;

import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;

public interface IRestDataExecutor {
	public String doTest(String params, TicRestMain restMain)
			throws Exception;
	
	public String doSyncRest(String params, TicRestMain restMain,
			TicCoreLogMain ticLog)
			throws Exception;
}
