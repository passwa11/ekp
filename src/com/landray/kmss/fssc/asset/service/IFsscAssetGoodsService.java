package com.landray.kmss.fssc.asset.service;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONObject;

/**
  * 资产物资 服务接口
  */
public interface IFsscAssetGoodsService extends IExtendDataService {
	
	public abstract JSONObject checkGoodsNum(HttpServletRequest request)  throws Exception ;

}
