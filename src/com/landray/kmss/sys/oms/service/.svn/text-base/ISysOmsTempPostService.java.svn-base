package com.landray.kmss.sys.oms.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;

public interface ISysOmsTempPostService extends IExtendDataService {
	List<SysOmsTempPost> findListByTrxId(String trxId) throws Exception;
	
	List<SysOmsTempPost> findFailListByTrxId(String trxId) throws Exception;

	public SysOmsTempPost findByPp(SysOmsTempPp sysOmsTempPp) throws Exception;
	
	public SysOmsTempPost findByPostId(String fdPostId,String fdTrxId) throws Exception;
}
