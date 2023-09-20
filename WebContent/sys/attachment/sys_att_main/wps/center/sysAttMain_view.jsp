<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.TagUtils,com.landray.kmss.sys.attachment.forms.*,java.util.*"%>
<%@ page import="com.landray.kmss.sys.attachment.service.*"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
<%
	String formBeanName = request.getParameter("formBeanName");
	Object formBean = null;
	if(formBeanName != null && formBeanName.trim().length() != 0){
		formBean = pageContext.getAttribute(formBeanName);
		if(formBean == null){
			formBean = request.getAttribute(formBeanName);
		}
		if(formBean == null){
			formBean = session.getAttribute(formBeanName);
		}
		pageContext.setAttribute("_formBean", formBean);
	}else{
		formBeanName = "com.landray.kmss.web.taglib.FormBean";
	}
	Object originFormBean = pageContext.getAttribute("com.landray.kmss.web.taglib.FormBean");
	pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean", formBean);
	if(formBean == null){
		formBean = com.landray.kmss.web.taglib.TagUtils.getInstance().lookup(pageContext,
				formBeanName, null);
		pageContext.setAttribute("_formBean", formBean);
	}

	//得到文档状态，用于控制留痕按钮在发布状态中不显示
	String docStatus = null;
	try{
		docStatus = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"docStatus");
		pageContext.setAttribute("_docStatus", docStatus);
	}catch(Exception e){
	}
%>
<c:set var="fdKey" value="${param.fdKey}" />
<c:set var="fdModelId" value="${param.fdModelId}" />
<c:set var="fdModelName" value="${param.fdModelName}" />
<c:set var="attForms" value="${_formBean.attachmentForms[fdKey]}" />
<c:set var="attachmentId" value=""/>
<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
</c:forEach>
<style>
#office-iframe{
	width:100%;
	min-height:440px;
}
</style>
<script>Com_IncludeFile("jquery.js");</script>
<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("web-office-sdk-v1.1.16.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
<script>Com_IncludeFile("wps_center_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
<div id="WPSCenterOffice_${param.fdKey}" class="wps-container">
</div>
<ui:event event="show">
	if("${param.load}" == 'false'){
		if((!wps_center_${param.fdKey}.hasLoad && !wps_center_${param.fdKey}.isLoading) || wps_center_${param.fdKey}.forceLoad){
			wps_center_${param.fdKey}.load();
		}
	}
</ui:event>

<script>

	var wps_center_${param.fdKey};
	
	$(document).ready(function(){
		var fdAttMainId = "${attachmentId}";
		if("${attachmentId}" != ""){
			wps_center_${param.fdKey} = new WPSCenterOffice_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","read");
			if("${param.contentFlag}" != "" && "${param.contentFlag}" == "true" && "${_docStatus}" == "30"){
				wps_center_${param.fdKey}.contentFlag = true;
			}

			if("${param.wpsPreview}" != "") {
				wps_center_${param.fdKey}.wpsPreview = "${param.wpsPreview}";
			}
			if("${param.hasHead}" != "") {
				wps_center_${param.fdKey}.hasHead = "${param.hasHead}";
			}
			wps_center_${param.fdKey}.load();
		}
	});
    
</script>