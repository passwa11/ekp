package com.landray.kmss.sys.oms.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;

public interface ISysOmsTempDpService extends IExtendDataService {
	List<SysOmsTempDp> findListByTrxId(String trxId) throws Exception;
	
	List<SysOmsTempDp> findFailListByTrxId(String trxId) throws Exception;
}
