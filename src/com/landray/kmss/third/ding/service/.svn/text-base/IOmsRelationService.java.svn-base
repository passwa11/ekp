package com.landray.kmss.third.ding.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

public interface IOmsRelationService extends IBaseService {
	public void deleteByKey(String fdEkpId, String appKey) throws Exception;
	public Page getPage(HQLInfo hqlInfo, String type) throws Exception;

	public boolean checkThird(String fdId, String fdAppPkId) throws Exception;

	public boolean checkEKP(String fdId, String fdEkpId) throws Exception;

	public Map<String, String> handle(String fdId, String fdAppPkId,
			String type) throws Exception;

	public JSONArray addExcel(FormFile file) throws Exception;

	/**
	 * 根据ekUserId查找钉钉userid
	 * 
	 * @param ekpUserId
	 * @return
	 * @throws Exception
	 */
	public String getDingUserIdByEkpUserId(String ekpUserId) throws Exception;

	/**
	 * 根据钉钉userid查找ekUserId
	 * 
	 * @param 钉钉userid
	 * @return
	 * @throws Exception
	 */
	public String getEkpUserIdByDingUserId(String dingUserId) throws Exception;

	/**
	 * 钉钉禁用后组织在ekp存在的id记录
	 * 
	 * @param deptList
	 * @param type
	 * @return
	 */
	public List<String> getDeptUserDisable(List<String> deptList,
			String type);

	public void deleteByKey(String fdEkpId) throws Exception; // 根据ekpid删除相关记录

	public void deleteEkpOrg() throws Exception; // 删除ekp多余的org信息（钉钉端不存在）

	public OmsRelationModel findByEkpId(String ekpId) throws Exception;

	public OmsRelationModel findByAppPkId(String fdAppPkId)
			throws Exception;
}
