package com.landray.kmss.sys.oms.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;

public interface ISysOmsTempDeptService extends IExtendDataService {
	
	List<SysOmsTempDept> findListByTrxId(String trxId) throws Exception;
	
	List<SysOmsTempDept> findFailListByTrxId(String trxId) throws Exception;
	
	public SysOmsTempDept finDept(SysOmsTempDp dp) throws Exception;
	
	public SysOmsTempDept findByDeptId(String fdDeptId,String fdTrxId) throws Exception;
}
