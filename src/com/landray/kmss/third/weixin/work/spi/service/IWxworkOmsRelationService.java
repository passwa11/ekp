package com.landray.kmss.third.weixin.work.spi.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

public interface IWxworkOmsRelationService extends IBaseService {
	public void deleteByKey(String fdEkpId, String appKey) throws Exception;
	public Page getPage(HQLInfo hqlInfo, String type) throws Exception;

	public boolean checkThird(String fdId, String fdAppPkId,String type) throws Exception;

	public boolean checkEKP(String fdId, String fdEkpId) throws Exception;

	public Map<String, String> handle(String fdId, String fdAppPkId,
			String type,String fdEkpId) throws Exception;

	public JSONArray addExcel(FormFile file) throws Exception;

	public WxworkOmsRelationModel findByEkpId(String ekpId) throws Exception;

	public WxworkOmsRelationModel findByUserId(String userId) throws Exception;

	/*
	 * type:person/dept 根据类型获取对照表信息
	 */
	public List<Map> getListByType(String type) throws Exception;

	public String findEkpfdIdByWxId(String wxId, String type) throws Exception;

	public WxworkOmsRelationModel updateOpenIdByEkpId(String ekpId) throws Exception;

	public WxworkOmsRelationModel findByOpenId(String openId) throws Exception;

	public WxworkOmsRelationModel updateOpenIdByOpenId(String openId) throws Exception;

}
