package com.landray.kmss.tic.soap.connector.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.sys.edition.interfaces.ISysEditionMainModel;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.util.xml.ParseSoapXmlUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * WEBSERVCIE服务函数业务接口实现
 * 
 * @author
 * @version 1.0 2012-08-06
 */
public class TicSoapMainServiceImp extends BaseCoreInnerServiceImp implements
		ITicSoapMainService {

	public void saveLockedFlag(ISysEditionMainModel model, boolean lockFlag)
			throws Exception {
		model.setDocIsLocked(new Boolean(lockFlag));
		saveMainModel(model);
	}
	
	@Override
    public TicSoapMain findEnableServiceById(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo ();
		hqlInfo.setWhereBlock(" fdId=:fdId and wsEnable=1 ");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setOrderBy(" docCreateTime desc");
		return (TicSoapMain) getBaseDao().findFirstOne(hqlInfo);
	}

	@Override
	public void updateImportExportJson(String fdId) throws Exception {
		// TODO Auto-generated method stub
		TicSoapMain setting = (TicSoapMain) this
				.findByPrimaryKey(fdId);
		String xml = setting.getWsMapperTemplate();
		// 移除禁用的节点
		xml = ParseSoapXmlUtil.disableFilter(xml);

		JSONObject o = ParseSoapXmlUtil.functionXmlToJson(xml);

		// JSONObject in = new JSONObject();
		// in.put("in", o.getJSONArray("import"));
		//
		// JSONObject out = new JSONObject();
		// in.put("out", o.getJSONArray("import"));
		setting.setFdParaIn(o.getJSONArray("import")
				.toString());
		// Map<JSONObject, JSONObject> map = new LinkedHashMap<JSONObject,
		// JSONObject>();
		JSONArray export = o.getJSONArray("export");
		// getIiemNodes(export, null, map);
		// removeIiemNodes(export, map);
		setting.setFdParaOut(export.toString());
		this.update(setting);
	}

	private void removeIiemNodes(JSONArray array,
			Map<JSONObject, JSONObject> map) {
		for (JSONObject o : map.keySet()) {
			JSONObject parent = map.get(o);
			parent.put("type", o.getString("type"));
			parent.put("isoptional", o.getString("isoptional"));
			parent.put("children", o.getJSONArray("children"));
			parent.remove("item");
		}
	}

	private void getIiemNodes(JSONArray array, JSONObject parent,
			Map<JSONObject, JSONObject> map) {
		// JSONObject itemObj = null;

		for (int i = 0; i < array.size(); i++) {
			Object o = array.get(i);
			if (o instanceof JSONObject) {
				JSONObject obj = (JSONObject) o;
				String name = obj.getString("name");
				String type = obj.getString("type");
				if ("item".equals(name) && "arrayObject".equals(type)) {
					map.put(obj, parent);
					// continue;
				}
				if (obj.containsKey("children")) {
					getIiemNodes(obj.getJSONArray("children"), obj, map);
				}
			}
		}
	}
}
