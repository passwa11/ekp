<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.common.service.IXMLDataBean,com.landray.kmss.common.actions.RequestContext"%>
<%@page import="java.util.*,com.landray.kmss.util.*,net.sf.json.*"%>
<%
	IXMLDataBean xdb = (IXMLDataBean)SpringBeanUtil.getBean("modelingAppModelService");
	List<Map<String, String>> modes = xdb.getDataList(new RequestContext(request));
	JSONArray array = new JSONArray();
	for(Map<String, String> mode:modes) {
		array.add(mode.get("value"));
	}
	String ids = array.toString();
%>
<script>
	var _modeIds = <%=ids%>;

	function _checkModelingModel(dataJSON){
		var data = [];
		for(var i=0;i<dataJSON.length;i++){
			if(Com_ArrayGetIndex(_modeIds,dataJSON[i]["fdModelId"])!=-1){
				data.push(dataJSON[i]);
			}
		}
		return data;
	}
</script>
