<%@page import="com.landray.kmss.sys.lbpmext.businessauth.util.LbpmExtBusinessAuthUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
var businessAuth = {};

businessAuth.controlType = {};
businessAuth.controlType.money = "<%=LbpmExtBusinessAuthUtil.CONTROL_TYPE_MONEY%>"; //定额
businessAuth.controlType.number = "<%=LbpmExtBusinessAuthUtil.CONTROL_TYPE_NUMBER%>"; //定量
businessAuth.controlType.qualitative = "<%=LbpmExtBusinessAuthUtil.CONTROL_TYPE_QUALITATIVE%>"; //定性
businessAuth.controlType.determined = "<%=LbpmExtBusinessAuthUtil.CONTROL_TYPE_DETERMINED%>"; //定规
businessAuth.controlType.none = "<%=LbpmExtBusinessAuthUtil.CONTROL_TYPE_NONE%>"; //无

businessAuth.controlTypeJson = <%=LbpmExtBusinessAuthUtil.getAuthControlTypeJsonStr()%>;

businessAuth.getControlTypeName = function(type){
	for(var i=0;i<businessAuth.controlTypeJson.length;i++){
		if(businessAuth.controlTypeJson[i].value==type){
			return businessAuth.controlTypeJson[i].name;
		}
	}
	return "";
}

businessAuth.getScaleLengthName = function(type){
	for(var i=0;i<businessAuth.controlTypeJson.length;i++){
		if(businessAuth.controlTypeJson[i].value==type){
			return businessAuth.controlTypeJson[i].scaleLength;
		}
	}
	return '2';
}
</script>