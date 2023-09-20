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
	min-height:560px;
}
</style>
<script>Com_IncludeFile("jquery.js");</script>
<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("web-office-sdk-v1.1.16.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
<script>Com_IncludeFile("wps_center_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
<div id="WPSCenterOffice_${param.fdKey}" class="wps-container">
</div>
<script>
	var wps_center_${param.fdKey};
	function loadWpsAtt(fdAttMainId){
		if(fdAttMainId != ""){
			wps_center_${param.fdKey} = new WPSCenterOffice_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","read");
			if("${param.contentFlag}" != "" && "${param.contentFlag}" == "true" && "${_docStatus}" == "30"){
				wps_center_${param.fdKey}.contentFlag = true;
			}
			wps_center_${param.fdKey}.load();
		}
	}
</script>
<%
    boolean isExpand = "true".equals(request.getParameter("isExpand"));
	if(isExpand){
%>
<script>
	$(document).ready(function(){
		loadWpsAtt("${attachmentId}");
	});
</script>
<%}else{%>
<ui:event event="show">
	loadWpsAtt("${attachmentId}");
</ui:event>
<%}%>


