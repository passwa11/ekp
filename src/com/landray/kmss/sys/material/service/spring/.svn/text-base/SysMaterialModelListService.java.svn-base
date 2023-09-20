package com.landray.kmss.sys.material.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysMaterialModelListService implements IXMLDataBean {

	@Override
	public List<Map<String, String>> getDataList(RequestContext requestInfo)
			throws Exception {
		SysDataDict dict = SysDataDict.getInstance();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String modules = ResourceUtil.getString("sysMaterialMain.modules", "sys-material");
		JSONArray objArr = JSONArray.fromObject(modules);
		if(null != objArr && objArr.size() > 0){
			for (int i = 0; i < objArr.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject obj = (JSONObject) objArr.get(0);
				String modelName = obj.getString("modelName");
				String msg = dict.getModel(modelName).getMessageKey();
				if(StringUtil.isNull(msg)){
					continue;
				}
				String[] msgs = msg.split("\\:");
				map.put("name", ResourceUtil.getString(msgs[1], msgs[0]));
				map.put("id", modelName);
				list.add(map);
			}
		}
		return list;
	}

}
