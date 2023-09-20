package com.landray.kmss.fssc.fee.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.fssc.fee.model.FsscFeeLedger;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONObject;

public interface IFsscFeeLedgerService extends IExtendDataService {
	public JSONObject getLedgerMoney(String fdLedgerId,String fdModelId) throws Exception;

	public Map<String, Map<String,String>> processListData(List<FsscFeeLedger> list)throws Exception;
}
