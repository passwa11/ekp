<%@ page language="java" contentType="text/javascript; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeConstantUtil" %>
<%@ page import="java.util.*,java.lang.reflect.*,com.landray.kmss.util.ResourceUtil"%>
define(function(require, exports, module) {
<%
	

	JSONArray array  = new JSONArray();
	Map<String, String>  map = KmsKnowledgeConstantUtil.getKnowledgeModelNames();
	
	for(Map.Entry<String, String> entry : map.entrySet()) {
		String value = entry.getValue();
		array.add(value);
	}          
	out.print("module.exports = " + array.toString() + ";");
	
%>
});
