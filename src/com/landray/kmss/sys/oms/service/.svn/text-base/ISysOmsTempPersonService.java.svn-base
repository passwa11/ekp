package com.landray.kmss.sys.oms.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;

public interface ISysOmsTempPersonService extends IExtendDataService {
	List<SysOmsTempPerson> findListByTrxId(String trxId) throws Exception;
	
	List<SysOmsTempPerson> findFailListByTrxId(String trxId) throws Exception;
	public SysOmsTempPerson findByDp(SysOmsTempDp dp) throws Exception;
	public SysOmsTempPerson findByPp(SysOmsTempPp pp) throws Exception;
	public SysOmsTempPerson findByPersonId(String fdPersonId, String fdTrxId) throws Exception;
}
