package com.landray.kmss.sys.oms.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;

public interface ISysOmsTempPpService extends IExtendDataService {
	List<SysOmsTempPp> findListByTrxId(String trxId) throws Exception;
	
	List<SysOmsTempDp> findFailListByTrxId(String trxId) throws Exception;
}
