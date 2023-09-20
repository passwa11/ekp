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

%>
<c:set var="fdKey" value="${param.fdKey}" />
<c:set var="fdModelId" value="${param.fdModelId}" />
<c:set var="fdModelName" value="${param.fdModelName}" />
<c:set var="attForms" value="${_formBean.attachmentForms[fdKey]}" />
<!-- 引用编辑页面，但用查看打开wps，类型：write(编辑)，read(只读) -->
<c:set var="isWrite" value="${param.fdMode}" />
<c:if test="${empty isWrite}">
	<c:set var="isWrite" value="write" />
</c:if>
<c:set var="attachmentId" value=""/>
<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
</c:forEach>
<style>
#office-iframe{
	width:100%;
	height:600px!important;;
}
</style>

<script>Com_IncludeFile("jquery.js");</script>
<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("web-office-sdk-1.1.1.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("wps_cloud_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/cloud/js/","js",true);</script>
<div id="WPSCloudOffice_${param.fdKey}" class="wps-container">
</div>

<script>
	var wps_cloud_${param.fdKey};
	
	$(document).ready(function(){		
		var fdAttMainId = "${attachmentId}";
		if("${attachmentId}" == ""){
		   //请求在线编辑附件的id
		   var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=addCloudOnlineFile"; 
		   $.ajax({     
		 	     type:"post",     
		 	     url:url,     
		 	     data:{fdTemplateModelId:"${param.fdTemplateModelId}",fdTemplateModelName:"${param.fdTemplateModelName}",fdTemplateKey:"${param.fdTemplateKey}",fdModelName:"${param.fdModelName}",fdKey:"${param.fdKey}",fdTempKey:"${param.fdTempKey}",fdModelId:"${param.fdModelId}",fdFileExt:"${param.fdFileExt}",isPrint:"${param.isPrint}"},    
		 	     async:false,    //用同步方式 
		 	     success:function(data){
		 	    	if (data){
		 	    		var results =  eval("("+data+")");
					    if(results['editOnlineAttId']!=null){
					    	fdAttMainId = results['editOnlineAttId'];
					    	wps_cloud_${param.fdKey} = new WPSCloudOffice_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","${isWrite}");
					    	if("${param.load}" != 'false'){
					    		wps_cloud_${param.fdKey}.load();
					    	}
						}
					}
		 		 }   
		    });
		}else{
			wps_cloud_${param.fdKey} = new WPSCloudOffice_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","${isWrite}");
			if("${param.load}" != 'false'){
				wps_cloud_${param.fdKey}.load();
			}
		}
	});
</script>

