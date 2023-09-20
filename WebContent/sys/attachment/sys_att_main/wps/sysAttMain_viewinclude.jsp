<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.TagUtils,com.landray.kmss.sys.attachment.forms.*,java.util.*"%>
<%@ page import="com.landray.kmss.sys.attachment.service.*"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
<style>
#office-iframe{
	width:100%;
	height:600px!important;
}
</style>
<script>Com_IncludeFile("jquery.js");</script>
<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("web-office-sdk-1.1.1.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("wps_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<div id="WPSWebOffice_${param.fdKey}" class="wps-container">
</div>
<script>
	function loadWpsAtt(fdAttMainId){
		if(fdAttMainId != ""){
			wps_${param.fdKey} = new WPS_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","read");
			wps_${param.fdKey}.bookMarks = "${JsParam.bookMarks}";
			wps_${param.fdKey} .load();
		}
	}
</script>
<%
    boolean isExpand = "true".equals(request.getParameter("isExpand"));
	if(isExpand){
%>
<script>
	var wps_${param.fdKey};
	$(document).ready(function(){
		loadWpsAtt("${attachmentId}");
	});
</script>
<%}else{%>
<ui:event event="show">
	loadWpsAtt("${attachmentId}");
</ui:event>
<%}%>


