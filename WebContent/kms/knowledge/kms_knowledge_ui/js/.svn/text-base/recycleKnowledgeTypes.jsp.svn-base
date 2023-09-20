<%@ page language="java" contentType="text/javascript; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="com.landray.kmss.sys.recycle.util.SysRecycleUtil" %>
<%@ page import="java.util.*,java.lang.reflect.*,com.landray.kmss.util.ResourceUtil"%>
define(function(require, exports, module) {
<%
	

	JSONArray array  = new JSONArray();
	Map<String, String>  map = new HashMap();
	if(SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge")){
		map.put("1", "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge");
	}
	
	if(SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.kms.wiki.model.KmsWikiMain")){
		map.put("2", "com.landray.kmss.kms.wiki.model.KmsWikiMain");
	}
	for(Map.Entry<String, String> entry : map.entrySet()) {
		String value = entry.getValue();
		array.add(value);
	}          
	out.print("module.exports = " + array.toString() + ";");
	
%>
});
