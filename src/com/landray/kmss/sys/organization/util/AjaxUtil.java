/**
 * 
 */
package com.landray.kmss.sys.organization.util;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.landray.kmss.util.KmssMessageWriter;
import com.landray.kmss.util.KmssReturnPage;

/**
 * @author 傅游翔
 * 
 */
public class AjaxUtil {

	public static boolean requiredJson(HttpServletRequest request) {
		// Accept application/json, text/javascript, */*; q=0.01
		String accept = request.getHeader("Accept");
		return (accept != null && accept.indexOf("/json") > -1);
	}

	public static JSONObject messagesToJson(HttpServletRequest request,
			KmssReturnPage rtnPage) {
		KmssMessageWriter writer = new KmssMessageWriter(request, rtnPage);
		return writer.DrawJsonMessage(true);
	}

	public static void saveMessagesToJson(HttpServletRequest request,
			KmssReturnPage rtnPage) {
		JSONObject msg = messagesToJson(request, rtnPage);
		request.setAttribute("lui-source", msg);
	}

	public static void saveMessagesToJson(HttpServletRequest request,
			String message) {
		JSONObject msg = new JSONObject();
		msg.put("msg", message);
		request.setAttribute("lui-source", msg);
	}
}
