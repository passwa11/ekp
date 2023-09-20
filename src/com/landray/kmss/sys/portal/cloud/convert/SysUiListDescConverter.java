package com.landray.kmss.sys.portal.cloud.convert;

import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * sys.ui.listdesc --> data.list.digest
 * 
 * @author chao
 *
 */
public class SysUiListDescConverter implements IDataSourceConverter {

	@Override
	public Object convert(Object obj) {
		if (obj instanceof JSONArray) {
			JSONArray array = (JSONArray) obj;
			for (int i = 0; i < array.size(); i++) {
				JSONObject json = array.getJSONObject(i);
				CloudPortalUtil.replaceKey(json, "description", "desc");
			}
			return array;
		}
		return obj;
	}
}
