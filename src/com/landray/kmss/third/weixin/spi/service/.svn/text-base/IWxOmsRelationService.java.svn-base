package com.landray.kmss.third.weixin.spi.service;

import java.util.Map;

import com.landray.kmss.web.upload.FormFile;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

public interface IWxOmsRelationService extends IBaseService {
	public void deleteByKey(String fdEkpId, String appKey) throws Exception;

	public Page getPage(HQLInfo hqlInfo, String type) throws Exception;

	public boolean checkThird(String fdId, String fdAppPkId) throws Exception;

	public boolean checkEKP(String fdId, String fdEkpId) throws Exception;

	public Map<String, String> handle(String fdId, String fdAppPkId,
			String type) throws Exception;
	
	public JSONArray addExcel(FormFile file) throws Exception;
}
