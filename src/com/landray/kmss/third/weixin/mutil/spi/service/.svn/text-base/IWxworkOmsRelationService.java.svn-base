package com.landray.kmss.third.weixin.mutil.spi.service;

import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

public interface IWxworkOmsRelationService extends IBaseService {
	public void deleteByKey(String fdEkpId, String appKey, String key) throws Exception;
	public Page getPage(HQLInfo hqlInfo, String type) throws Exception;

	public boolean checkThird(String fdId, String fdAppPkId, String fdWxKey) throws Exception;

	public boolean checkEKP(String fdId, String fdEkpId) throws Exception;

	public Map<String, String> handle(String fdId, String fdAppPkId,
			String type, String fdWxKey,String fdEkpId) throws Exception;

	public JSONArray addExcel(FormFile file) throws Exception;
}
