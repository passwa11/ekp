package com.landray.kmss.sys.zone.util;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.cluster.interfaces.ClusterDiscover;
import com.landray.kmss.sys.cluster.model.SysClusterGroup;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Collections;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysZoneConfigUtil {
	
	public static final String TYPE_PC_KEY = "pc";

	public static final String TYPE_MOBILE_KEY = "mobile";
	
	public static final String SEPARATOR = "://"; 
	
	public  static final String OTHER_INFO_MAP_EXTENSION_POINT_ID 
				= "com.landray.kmss.sys.zone.otherinfo";
	private static final String INFO_ITEM = "zone_info";
	
	private static  Map<String , Map> otherInfos 
						=  new HashMap<String, Map>();
	
	private static Boolean infoInitFlag = false;
	
	private static Boolean communicateInitFlag = false;
	

	public static Map <String , Map> getOtherInfosMap() {
		if(infoInitFlag == false) {
			synchronized(otherInfos) {
				if(!infoInitFlag) { 
					initInfos();
					infoInitFlag = true;
				}
			}
		}
		return otherInfos;
	}
	
	
	
	
	private static Map initInfos() {
		IExtensionPoint point = Plugin
				.getExtensionPoint(OTHER_INFO_MAP_EXTENSION_POINT_ID);
		IExtension[] extensions = point.getExtensions();
		for (IExtension extension : extensions) {
			if (INFO_ITEM.equals(extension.getAttribute("name"))) {
				Map<String, String> object = new HashMap<String, String>();
				object.put("infoId", Plugin.getParamValueString(extension,
						"infoId"));
				object.put("source_url", Plugin.getParamValueString(extension,
						"source_url"));
				object.put("render_url_pc", Plugin.getParamValueString(
						extension, "render_url_pc"));
				String renderType = Plugin.getParamValueString(extension,
						"renderType");
				object.put("renderType", renderType);
				//所属服务器的key
				object.put("server", getCurrentServerGroupKey());
				otherInfos.put(object.get("infoId"), object);
			}
		}
		return otherInfos;
	}
	
	
	public static final String COMMUNICATE_MAP_EXTENSION_POINT_ID 
		= "com.landray.kmss.sys.zone.communicate";
	
	private static JSONArray  _communicates
							= new JSONArray();
	
	public static JSONArray getCommnicateList() { 
		if(communicateInitFlag == false) {
			synchronized(_communicates) {
				if(!communicateInitFlag) { 
					initCommnicateList();
					communicateInitFlag =  true;
				}
			}
		}
		return _communicates;
	} 
	
	private static JSONArray initCommnicateList() {
		IExtensionPoint point = Plugin
			.getExtensionPoint(COMMUNICATE_MAP_EXTENSION_POINT_ID);
		IExtension[] extensions = point.getExtensions();
		for (IExtension extension : extensions) {
			if ("communicate".equals(extension.getAttribute("name"))) {
				JSONObject object = new JSONObject();
				object.put("showType", "communicate");
				object.put("unid", Plugin.getParamValueString(extension,
						"unid"));
				object.put("js_class", Plugin.getParamValueString(
						extension, "js_class"));
				object.put("js_src", Plugin.getParamValueString(extension,
											"js_src"));
				object.put("icon", Plugin.getParamValueString(extension,
									"icon"));
				object.put("order", Integer.valueOf(Plugin.getParamValueString(extension,
								"order")));
				
				object.put("title", Plugin.getParamValueString(extension,
					"title"));
				object.put("authUrl", Plugin.getParamValueString(extension,
						"authUrl"));
				object.put("bean", Plugin.getParamValueString(extension, "bean"));
				//所属服务器的key
				object.put("server", getCurrentServerGroupKey());
				_communicates.add(object);
			} 
			else if ("communicate_mobile".equals(extension.getAttribute("name"))) { 
				JSONObject object = new JSONObject();
				object.put("showType", "communicate_mobile");
				object.put("unid", Plugin.getParamValueString(extension,
						"unid"));
				object.put("href", Plugin.getParamValueString(
						extension, "href"));
				
				object.put("icon", Plugin.getParamValueString(extension,
									"icon"));
				object.put("text", Plugin.getParamValueString(extension,
				"text"));
				object.put("order", Integer.valueOf(Plugin.getParamValueString(extension,
								"order")));
				//所属服务器的key
				object.put("server", getCurrentServerGroupKey());
				_communicates.add(object);
			}
		}
		sortCommunicate(_communicates);
		return _communicates;
	}
	
	public static void sortCommunicate(JSONArray list) {
		if(!ArrayUtil.isEmpty(list)) {
			Collections.sort(list, new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject m1, JSONObject m2) {
					if ((Integer)m1.get("order") < (Integer)m2.get("order")) {
						return -1;
					} else {
						return 1;
					}
				}
			});
		}
	}
	
	
	
	
	public static String getServerUrl(String code) {
		SysClusterGroup group = null;
		if(StringUtil.isNull(code)) {
			group = ClusterDiscover.getInstance().getLocalGroup();
		} else {
		    group = ClusterDiscover.getInstance().getGroup(code);
		}
		if (group != null) {
			String url = group.getFdUrl();
			if (url != null) {
				if (url.endsWith("/")) {
                    return url.substring(0, url.length() - 1);
                }
			}
			return url;
		}
		return null;
	}
	
	public static String getCurrentServerGroupKey() {
		SysClusterGroup group = ClusterDiscover.getInstance().getLocalGroup();
		if(group != null) {
            return group.getFdKey();
        } else {
            return null;
        }
	}

	

	/**
	 * 获取服务器名称
	 * @param code
	 * @return
	 */
	public static String getServerName(String code) {
		//本服务器
		if(StringUtil.isNull(code)) {
			return ResourceUtil.getString("sysZoneNavLink.local", "sys-zone");
		}
		SysClusterGroup group = ClusterDiscover.getInstance().getGroup(code);
		if (group != null) {
			return group.getFdName();
		}
		return null;
	}
	
	public static boolean isMultiServer() {
		return ClusterDiscover.getInstance().isMultiSystemEnabled() 
					&& getCurrentServerGroupKey() != null;
	}
	
	/**
	 * 根据模块路径判断模块是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean moduleExist(String path) {
		return new File(PluginConfigLocationsUtil.getKmssConfigPath()
					+ path).exists();
	}
}
