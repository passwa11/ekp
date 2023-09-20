package com.landray.kmss.eop.basedata.xml;

import java.util.HashMap;
import java.util.Map;


/**
 * 与部件相关的工具类
 * @author 
 *
 */
public class EopBasedataXmlUtil {

	private static Map<String, EopBasedataFsscxmlModel> mportlets 
		= new HashMap<String, EopBasedataFsscxmlModel>();

	public static Map<String, EopBasedataFsscxmlModel> getMportals() {
		return mportlets;
	}
	
	public static void registerModel(EopBasedataFsscxmlModel fsscxml) {
		mportlets.put(fsscxml.getName(), fsscxml);
	}

	

	
	
}
