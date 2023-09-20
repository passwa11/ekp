package com.landray.kmss.sys.oms.service;

import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempTrx;
import com.landray.kmss.sys.oms.temp.OmsTempSynResult;
import com.landray.kmss.sys.oms.temp.SyncLog;

public interface ISysOmsTempTrxSynFailService extends ISysOmsTempTrxBaseService {
	
	public void doSync(SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log) throws Exception;
	
	public void syncDept(SysOmsTempDept omsTempDept) throws Exception;
	
	public void syncPost(SysOmsTempPost sysOmsTempPost) throws Exception;
	
	public void syncPerson(SysOmsTempPerson sysOmsTempPerson) throws Exception;
}
