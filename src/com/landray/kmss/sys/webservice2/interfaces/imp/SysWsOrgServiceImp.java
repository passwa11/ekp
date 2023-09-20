package com.landray.kmss.sys.webservice2.interfaces.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.util.ResourceUtil;

public class SysWsOrgServiceImp extends BaseServiceImp implements
		ISysWsOrgService {

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
    public SysOrgElement findSysOrgElement(String jsonObjStr) throws Exception {
		JSONObject jsonObj = JSONObject.fromObject(jsonObjStr);

		return findSysOrgElement(jsonObj);
	}

	@Override
    public List<SysOrgElement> findSysOrgList(String jsonArrStr)
			throws Exception {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<SysOrgElement> sysOrgList = new ArrayList<SysOrgElement>();
		Map<String, List<String>> orgMap = new HashMap<String, List<String>>();

		for (Object value : jsonArr) {
			JSONObject jsonObj = (JSONObject) value;
			Set<Entry<String, String>> entrySet = jsonObj.entrySet();
			Entry<String, String> entry = entrySet.iterator().next();
			String key = entry.getKey().trim();
			String v = entry.getValue().trim();
			if (!orgMap.containsKey(key)) {
				orgMap.put(key, new ArrayList<String>());
			}
			List<String> values = orgMap.get(key);
			values.add(v);
		}
		for (String key : orgMap.keySet()) {
			List<String> values = orgMap.get(key);
			List<SysOrgElement> orgList = findSysOrgElements(key, values);
			if (orgList != null && !orgList.isEmpty()) {
				sysOrgList.addAll(orgList);
			}
		}

		return sysOrgList;
	}

	/**
	 * 根据Json对象查找人员组织
	 * 
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	private SysOrgElement findSysOrgElement(JSONObject jsonObj)
			throws Exception {

		SysOrgElement sysOrgElement = null;
		Set<Entry<String, String>> entrySet = jsonObj.entrySet();
		Entry<String, String> entry = entrySet.iterator().next();
		String key = entry.getKey().trim();
		String value = entry.getValue().trim();

		// 根据KEY值调用相应的方法进行查找
		try {
			sysOrgElement = (SysOrgElement) SysWsUtil.invokeMethod(
					"sysWsOrgService", "findSysOrgBy" + key,
					new String[] { value });
		} catch (Exception e) {
			throw new Exception(ResourceUtil.getString(
					"sysWs.errMsg.no.org.element", "sys-webservice2", null,
					jsonObj.toString()));
		}

		return sysOrgElement;
	}

	private List<SysOrgElement> findSysOrgElements(String key,
			List<String> values) throws Exception {
		String[] valueArr = new String[values.size()];
		values.toArray(valueArr);
		List<SysOrgElement> orgList = null;

		// 根据KEY值调用相应的方法进行查找
		try {
			orgList = (List<SysOrgElement>) SysWsUtil.invokeMethod(
					"sysWsOrgService", "findSysOrgBy" + key + "s",
					new Object[] { valueArr });
		} catch (Exception e) {
			throw new Exception(ResourceUtil.getString(
					"sysWs.errMsg.no.org.element", "sys-webservice2", null,
					values.toString()));
		}
		return orgList;
	}

	@Override
    public SysOrgElement findSysOrgById(String value) throws Exception {
		return sysOrgCoreService.findByPrimaryKey(value);
	}

	@Override
    public SysOrgElement findSysOrgByLoginName(String value) throws Exception {
		return sysOrgCoreService.findByLoginName(value);
	}

	@Override
    public SysOrgElement findSysOrgByKeyword(String value) throws Exception {
		return sysOrgCoreService.findByKeyword(value);
	}

	@Override
    public SysOrgElement findSysOrgByPersonNo(String value) throws Exception {
		return sysOrgCoreService
				.findByNo(value, SysOrgConstant.ORG_TYPE_PERSON);
	}

	@Override
    public SysOrgElement findSysOrgByDeptNo(String value) throws Exception {
		return sysOrgCoreService.findByNo(value, SysOrgConstant.ORG_TYPE_DEPT);
	}

	@Override
    public SysOrgElement findSysOrgByOrgNo(String value) throws Exception {
		return sysOrgCoreService.findByNo(value, SysOrgConstant.ORG_TYPE_ORG);
	}

	@Override
    public SysOrgElement findSysOrgByPostNo(String value) throws Exception {
		return sysOrgCoreService.findByNo(value, SysOrgConstant.ORG_TYPE_POST);
	}

	@Override
    public SysOrgElement findSysOrgByGroupNo(String value) throws Exception {
		return sysOrgCoreService.findByNo(value, SysOrgConstant.ORG_TYPE_GROUP);
	}

	@Override
    public SysOrgElement findSysOrgByLdapDN(String value) throws Exception {
		return sysOrgCoreService.findByLdapDN(value);
	}

	@Override
	public List<SysOrgElement> findSysOrgByIds(String[] values)
			throws Exception {
		return sysOrgCoreService.findByPrimaryKeys(values);
	}

	@Override
	public List<SysOrgElement> findSysOrgByLoginNames(String[] values)
			throws Exception {
		return sysOrgCoreService.findByLoginName(values);
	}

	@Override
	public List<SysOrgElement> findSysOrgByKeywords(String[] values)
			throws Exception {
		return sysOrgCoreService.findByKeyword(values);
	}

	@Override
	public List<SysOrgElement> findSysOrgByPersonNos(String[] values)
			throws Exception {
		return sysOrgCoreService.findByNo(values,
				SysOrgConstant.ORG_TYPE_PERSON);
	}

	@Override
	public List<SysOrgElement> findSysOrgByDeptNos(String[] values)
			throws Exception {
		return sysOrgCoreService.findByNo(values, SysOrgConstant.ORG_TYPE_DEPT);
	}

	@Override
	public List<SysOrgElement> findSysOrgByOrgNos(String[] values)
			throws Exception {
		return sysOrgCoreService.findByNo(values, SysOrgConstant.ORG_TYPE_ORG);
	}

	@Override
	public List<SysOrgElement> findSysOrgByPostNos(String[] values)
			throws Exception {
		return sysOrgCoreService.findByNo(values, SysOrgConstant.ORG_TYPE_POST);
	}

	@Override
	public List<SysOrgElement> findSysOrgByGroupNos(String[] values)
			throws Exception {
		return sysOrgCoreService.findByNo(values,
				SysOrgConstant.ORG_TYPE_GROUP);
	}

	@Override
	public List<SysOrgElement> findSysOrgByLdapDNs(String[] values)
			throws Exception {
		return sysOrgCoreService.findByLdapDN(values);
	}

}
