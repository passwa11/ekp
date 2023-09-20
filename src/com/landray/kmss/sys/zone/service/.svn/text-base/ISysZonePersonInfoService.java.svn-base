/**
 * 
 */
package com.landray.kmss.sys.zone.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.zone.model.SysZoneNavigation;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 傅游翔
 * 
 */
public interface ISysZonePersonInfoService extends IBaseService {
	
	public Page queryPersonInfo(int pageno, int rowsize, String searchValue,
			String tagNames, RequestContext request) throws Exception;

	public Page getNewPersonInfo(HttpServletRequest request) throws Exception;

	public List getTagList() throws Exception;

	public void updatePersonLastModifyTime(SysOrgPerson person);

	/**
	 * 获取个人资料目录
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPersonDatas(String personId)
			throws Exception;

	public String getoOrganization(SysOrgPerson person) throws Exception;

	public JSONArray updateOfGetTeam(String personId)  throws Exception;

	public JSONArray updateOfGetTeam(String personId, HttpServletRequest reuest)
			throws Exception;

	public JSONArray updateOfGetLeaders(String personId) throws Exception;

	public boolean isSelf(String zonePersonId) throws Exception;

	public List<SysZoneNavigation> getZonePersonNav(String fdShowType) throws Exception;

	public String getPersonTagMianId(String personId) throws Exception;

	public void updatePersonTags(IBaseModel model) throws Exception;

	public boolean isSelfNoPower(String zonePersonId) throws Exception;
	
	public void updateOrgInfo(SysOrgPerson sysOrgPerson) throws Exception;
	
	/**
	 * 保存个人信息，包括隐私
	 * @param request
	 * @throws Exception
	 */
	public void updatePersonInfo(RequestContext request ) throws Exception;
	
	public JSONObject updateOrgLang(HttpServletRequest reuest) throws Exception;
	
	public void updateFdSignature(String fdId, String fdSignature)
			throws Exception;
	/**
	 * 保存简历
	 * 
	 * @param personId
	 * @param fdKey
	 * @param attId
	 * @return
	 * @throws Exception
	 */
	public String saveResume(String personId, String fileName, String fileId)
			throws Exception;
	

	public Page getPersonInfoByTags(Map<String, String> parameters,
			String personId) throws Exception;
	
	public SysZonePersonInfo updateGetPerson(String fdId) throws Exception;

	public String getTagNamesByPersonId(String personId) throws Exception;

	/**
	 * 访问记录
	 * @param request
	 * @param personId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Page listReadLog(HttpServletRequest request,
			String personId, Integer type) throws Exception;
	
	public Page obtainPersons(HQLInfo hqlInfo, String parentId, String fdSearchName)
			throws Exception;
	
	public String nullLastHQL(String oriStr, String proName, Boolean down);
	
	public JSONArray getTagsByUserId(String userId) throws Exception;

	Page querySearchPersonInfo(int pageno, int rowsize, String searchValue, String tagNames, RequestContext request)
			throws Exception;
	public JSONArray getTeam(String personId)  throws Exception;

	public JSONArray getTeam(String personId, HttpServletRequest reuest)
			throws Exception;

	public JSONArray getLeaders(String personId) throws Exception;
}
