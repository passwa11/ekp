package com.landray.kmss.sys.filestore.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.config.design.SysCfgModuleInfo;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;

/**
 * 附件所属模块
 * @author 林剑文
 * @date  2020/10/23
 *
 */
public class SysModulsInfosTreeService implements IXMLDataBean {

	
	@Override
	public List getDataList(RequestContext xmlContext) throws Exception {
		String parent = xmlContext.getParameter("parent"); //父类ID二级信息
		String type = xmlContext.getParameter("type");
		if(com.landray.kmss.util.StringUtil.isNotNull(type) &&  "search".equalsIgnoreCase(type)) {
			String key = xmlContext.getParameter("key");
			return searchModulInfos(key);
		} else {
			if(StringUtil.isNull(parent)) {
				return modulInfos();
			} else {
				return subModulInfos(parent);
			}
		}
	
	}

	/**
	 * 模块信息
	 * 
	 * @return
	 */
	public List modulInfos() {
		List rtnList = new ArrayList();
		Map<String, String> moduls = new HashMap<String,String>();
		moduls.put(ResourceUtil.getString("sysFilestore.conversion.modul.sociality", "sys-filestore"), "*.sns.*");//社区类模块
		moduls.put(ResourceUtil.getString("sysFilestore.conversion.modul.seal", "sys-filestore"), "*.elec.*");//印章类模块
		moduls.put(ResourceUtil.getString("sysFilestore.conversion.modul.cost", "sys-filestore"), "*.fssc.*");//费控类模块
		moduls.put(ResourceUtil.getString("sysFilestore.conversion.modul.kms", "sys-filestore"), "*.kms.*");//KMS模块
		moduls.put(ResourceUtil.getString("sysFilestore.conversion.modul.hr", "sys-filestore"), "*.hr.*");//人事类模块
		moduls.put(ResourceUtil.getString("sysFilestore.conversion.modul.sys", "sys-filestore"), "*.sys.*");//系统类模块
		moduls.put(ResourceUtil.getString("sysFilestore.conversion.modul.km", "sys-filestore"), "*.km.*"); //KM模块
		for(Map.Entry<String, String> modul : moduls.entrySet()) {
			HashMap map = new HashMap();
			map.put("text", modul.getKey());
			map.put("value", modul.getValue());
			map.put("isAutoFetch", "0");
			rtnList.add(map);
		}
		return rtnList;
	}
	
	/**
	 * 模块对应的子模块信息
	 * @param parent
	 * @return
	 */
	public List subModulInfos(String parent) {
		List rtnList = new ArrayList();
		List<Map<String, String>> moduleList = getModuleList();
		
		for (Map<String, String> module : moduleList) {
			//dataMap.put(module.get("value"), module.get("name"));
			String value = module.get("value");
			String name = module.get("name");
			String formatModulVale = formatModulVale(value);
			if(formatModulVale.equals(parent)) {
				Object[] object = new Object[3];
				object[0] = value;  //隐藏值
				object[1] = name;  //显示名称
				object[2] = value; //说明
				rtnList.add(object);
			}
		}
		//Collections.sort(rtnList, new ModuleComparator());
		return rtnList;
	}

	/**
	 * 模块对应的子模块信息
	 * @return
	 */
	public List searchModulInfos(String key) {
		List rtnList = new ArrayList();
		if(com.landray.kmss.util.StringUtil.isNull(key)) {
			return rtnList;
		}
		List<Map<String, String>> moduleList = getModuleList();

		for (Map<String, String> module : moduleList) {

			String value = module.get("value");
			String name = module.get("name");
			if(value.contains(key) || name.contains(key)) {
				Object[] object = new Object[3];
				object[0] = value;  //隐藏值
				object[1] = name;  //显示名称
				object[2] = value; //说明
				rtnList.add(object);
			}

		}

		return rtnList;
	}
	
	
	
	/**
	 * 封装模块的子模块信息
	 * @return
	 */
	private List<Map<String, String>> getModuleList() {
		List<Map<String, String>> moduleList = new ArrayList<Map<String, String>>();
		List<?> moduleInfoList = SysConfigs.getInstance().getModuleInfoList();
		for (int i = 0; i < moduleInfoList.size(); i++) {
			SysCfgModuleInfo sysCfgModuleInfo = (SysCfgModuleInfo) moduleInfoList.get(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", formatModelValue(sysCfgModuleInfo.getUrlPrefix()));
			String messageKey = sysCfgModuleInfo.getMessageKey();
			if (StringUtil.isNotNull(messageKey) && StringUtil.isNotNull(ResourceUtil.getString(messageKey))) {
				map.put("name", ResourceUtil.getString(messageKey));
				moduleList.add(map);
			}
		}
		Collections.sort(moduleList, new ModuleComparator());
		return moduleList;
	}

	private String formatModelValue(String urlPrefix) {
		StringBuffer resultSB = new StringBuffer("*");
		if (StringUtil.isNotNull(urlPrefix)) {
			resultSB.append(urlPrefix.replace("/", ".")).append("*");
		}
		return resultSB.toString();
	}
	
	/**
	 *     处理字段
	 * @param urlPrefix
	 * @return
	 */
	private String formatModulVale(String urlPrefix) {
		StringBuffer resultSB = new StringBuffer("*");
		String  result = "";
		if(StringUtil.isNotNull(urlPrefix)) {
			
			String[] elements = urlPrefix.split("[.]");
			if(elements.length >= 2) {
				result = elements[0] + "." + elements[1] + ".*";
			}
		}
		
		return result;
	}
	
	
	class ModuleComparator implements Comparator<Map<String, String>> {

		@Override
        public int compare(Map<String, String> s1, Map<String, String> s2) {
			if (s1 == null || s2 == null) {
				return 0;
			}
			if (s1.containsKey("name") && s2.containsKey("name")) {
				return ChinesePinyinComparator.compare(s1.get("name"), s2.get("name"));
			}
			if (s1.containsKey("value") && s2.containsKey("value")) {
				return ChinesePinyinComparator.compare(s1.get("value"), s2.get("value"));
			}
			return 0;
		}
	}
	
}
