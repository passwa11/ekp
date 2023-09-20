package com.landray.kmss.sys.attend.actions;

import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.attend.model.SysAttendMapConfig;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 地图服务
 * 
 * @author linxiuxian
 *
 */
public class SysAttendMapConfigAction extends SysAppConfigAction {

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAttendMapConfig config = new SysAttendMapConfig();
		ActionForward actionForward = super.update(mapping, form, request,
				response);
		return actionForward;
	}

	public ActionForward getCurrentMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			SysAttendMapConfig config = new SysAttendMapConfig();
			Map map = config.getDataMap();
			JSONObject json = new JSONObject();
			String mapType = (String) map.get("fdMapServiceType");
			String mapKey = "";
			String bMapVer = "";
			String mapKeyPc = "";
			String mapKeyName ="";
			String mapKeyPcSecurityKey = "";
			if ("bmap".equals(mapType)) {
				mapKey = (String) map.get("fdMapServiceBMapKey");
				bMapVer = (String) map.get("fdMapServiceBMapVer");
			}
			if ("qmap".equals(mapType)) {
				mapKey = (String) map.get("fdMapServiceQMapKey");
				mapKeyName = (String) map.get("fdMapServiceQMapName");
			}
			if ("amap".equals(mapType)) {
				mapKey = (String) map.get("fdMapServiceAMapKey");
				mapKeyPc = (String) map.get("fdMapServiceAMapPcKey");
				mapKeyPcSecurityKey = (String) map.get("fdMapServiceAMapPcSecurityKey");
			}
			json.put("mapKeyName", mapKeyName);
			json.put("mapKeyPc", mapKeyPc);
			json.put("mapType", mapType);
			json.put("mapKey", mapKey);
			json.put("bMapVer", bMapVer);
			json.put("mapKeyPcSecurityKey", mapKeyPcSecurityKey);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

}
