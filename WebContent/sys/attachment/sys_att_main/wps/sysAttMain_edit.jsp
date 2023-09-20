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
<script>Com_IncludeFile("web-office-sdk-1.1.1.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("wps_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<c:if test="${param.method == 'add' }">
<div style="text-align:right;">
	<a href="javascript:void(0);" name="editonline_openLocal" class="attopenLocal" onclick="openLocalFile();">打开本地初稿</a>
	<div style="display:none">
		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${param.fdKey}" />
			<c:param name="fdModelId" value="${fdModelId }" />
			<c:param name="fdMulti" value="false" />
			<c:param name="fdModelName" value="${fdModelName }" />
			<c:param name="enabledFileType" value=".doc;.docx;.wps;" />
			<c:param name="uploadAfterSelect" value="true" />
		</c:import>
	</div>
</div>
</c:if>

<div id="WPSWebOffice_${param.fdKey}" class="wps-container">
</div>

<script>
	function openLocalFile(){
		$("#uploader_${param.fdKey}_container label").click();
	}
	
	var wps_${param.fdKey};
	
	
	
	$(document).ready(function(){	
		var fdAttMainId = "${attachmentId}";
		var flag = false;
		if("${attachmentId}" == ""){
		   //请求在线编辑附件的id
		   var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=addOnlineFile"; 
		   $.ajax({     
		 	     type:"post",     
		 	     url:url,     
		 	     data:{fdTemplateModelId:"${param.fdTemplateModelId}",fdTemplateModelName:"${param.fdTemplateModelName}",fdTemplateKey:"${param.fdTemplateKey}",fdModelName:"${param.fdModelName}",fdKey:"${param.fdKey}",fdTempKey:"${param.fdTempKey}",fdModelId:"${param.fdModelId}"},    
		 	     async:false,    //用同步方式 
			     dataType:"json",
			     success:function(results){
		 	    	if (results){
					    if(results['editOnlineAttId']!=null){
					    	fdAttMainId = results['editOnlineAttId'];
					    	wps_${param.fdKey} = new WPS_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","write");
					    	if("${param.load}" != 'false'){
					    		wps_${param.fdKey}.load();
					    	}
						}
					}
		 		 }   
		    });
		   
		   attachmentObject_${param.fdKey}.uploadAfterCustom = function(id){
			   if(flag){
				   wps_${param.fdKey}.fdId = id;
	    		   wps_${param.fdKey}.load();
	    		   fdAttMainId = id;
			   }else{
				   if(id){
						var delUrl="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=delete"; 
						$.ajax({     
					 	     type:"post",     
					 	     url:delUrl,     
					 	     data:{fdId:fdAttMainId,format:'json'},    
					 	     async:false,    //用同步方式 
							 dataType:"json",
							 success:function(results){
					 	    	 if(results){
					 	    		if(results['status'] && results['status'] == "1"){
					 	    			wps_${param.fdKey}.fdId = id;
					 	    			wps_${param.fdKey}.load();
					 	    			fdAttMainId = id;
					 	    			flag = true;
						 	    	 }
					 	    	 }
					 		 }   
					    });
					}
			   }
				
			}
		}else{
			wps_${param.fdKey} = new WPS_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","write");
			if("${param.load}" != 'false'){
				wps_${param.fdKey}.load();
			}
		}
	});
</script>