package com.landray.kmss.sys.mportal.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.mportal.xml.SysMportalMlink;
import com.landray.kmss.util.StringUtil;

public class MportalLinkUtil {
	
	private static Map<String, SysMportalMlink> mlinks
			= new HashMap<String, SysMportalMlink>();
	
	public static void registerMLink(SysMportalMlink mlink) {
		mlinks.put(mlink.getId(), mlink);
	}
	
	public static Map<String, SysMportalMlink> getMlinks() {
		return mlinks;
	}

	public static List<SysMportalMlink> getLinkListByType(String key) {
		boolean all = false;
		if (StringUtil.isNull(key)) {
			all = true;
		}
		ArrayList<SysMportalMlink> list = new ArrayList<SysMportalMlink>();
		if (!mlinks.isEmpty()) {
			for (Map.Entry<String, SysMportalMlink> entry : mlinks
					.entrySet()) {
				SysMportalMlink link = entry.getValue();
				if (all) {
					list.add(link);
				} else {
					if (link.getType() != null && link.getType().equals(key)) {
						list.add(link);
					}
				}
			}
		}
		return list;
	}
}
