package com.landray.kmss.sys.mportal.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.mportal.xml.SysMportalMportlet;
import com.landray.kmss.util.StringUtil;


/**
 * 与部件相关的工具类
 * @author 
 *
 */
public class MportalMportletUtil {

	private static Map<String, SysMportalMportlet> mportlets 
		= new HashMap<String, SysMportalMportlet>();

	public static Map<String, SysMportalMportlet> getMportals() {
		return mportlets;
	}

	public static void registerMportlet(SysMportalMportlet mportlet) {
		mportlets.put(mportlet.getId(), mportlet);
	}

	public static SysMportalMportlet getPortletById(String id) {
		if (StringUtil.isNull(id)) {
			return null;
		}
		return getMportals().get(id);
	}

	public static List<SysMportalMportlet> getMportletListByModule(String module) {
		boolean all = false;
		if (StringUtil.isNull(module)) {
			all = true;
		}
		ArrayList<SysMportalMportlet> list = new ArrayList<SysMportalMportlet>();
		if (!mportlets.isEmpty()) {
			for (Map.Entry<String, SysMportalMportlet> entry : mportlets
					.entrySet()) {
				SysMportalMportlet mportlet = entry.getValue();
				if (all) {
					list.add(mportlet);
				} else {
					if (mportlet.getFdModule().equals(module)) {
						list.add(mportlet);
					}
				}
			}
		}
		return list;
	}

	public static void clear() {
		getMportals().clear();
	}

	public static List<String> getModules() {
		List<SysMportalMportlet> mpList = getMportletListByModule(null);
		ArrayList<String> rtnList = new ArrayList<String>();
		for (SysMportalMportlet mp : mpList) {
			String module = mp.getFdModule();
			if (StringUtil.isNotNull(module) && !rtnList.contains(module)) {
				rtnList.add(module);
			}
		}
		return rtnList;
	}

	
	
}
