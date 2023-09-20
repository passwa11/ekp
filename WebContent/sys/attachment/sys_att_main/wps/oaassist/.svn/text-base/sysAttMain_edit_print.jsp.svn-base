<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.TagUtils,com.landray.kmss.sys.attachment.forms.*,java.util.*"%>
<%@ page import="com.landray.kmss.sys.attachment.service.*"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
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
<c:set var="fdFileName" value="" />
<c:forEach items="${attForms.attachments}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
	<c:set var="fdFileName" value="${sysAttMain.fdFileName}" />
</c:forEach>

<script>Com_IncludeFile("jquery.js");</script>
<script>Com_IncludeFile("wps_linux_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/oaassist/linux/js/","js",true);</script>
<div style="width: 100%;height: 100%">
	<div id="wpsLinux_${param.fdKey}" style="width: 100%;height: 700px;">
	</div>
</div>

<script>
	var wps_linux_${param.fdKey};

	$(document).ready(function(){

		var fdAttMainId = "${attachmentId}";
		var fdFileName = '${fdFileName}';
		if("${attachmentId}" == "") {

			//请求在线编辑附件的id
			var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=addWpsOaassistOnlineFile";
			$.ajax({
				type:"post",
				url:url,
				data:{fdTemplateModelId:"${param.fdTemplateModelId}",fdTemplateModelName:"${param.fdTemplateModelName}",fdTemplateKey:"${param.fdTemplateKey}",fdModelName:"${param.fdModelName}",fdKey:"${param.fdKey}",fdTempKey:"${param.fdTempKey}",fdModelId:"${param.fdModelId}"},
				async:false,    //用同步方式
				success:function(data){
					if (data){
						var results =  eval("("+data+")");
						if(results['editOnlineAttId']!=null){
							fdAttMainId = results['editOnlineAttId'];
							wps_linux_${param.fdKey}  = new WPSLinuxOffice_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","write","editonline.docx");
							if("${param.load}" != 'false'){
								wps_cloud_${param.fdKey}.load();
							}
						}
					}
				}
			});

		}else{
			wps_linux_${param.fdKey}  = new WPSLinuxOffice_AttachmentObject(fdAttMainId,"${param.fdKey}","${param.fdModelId}","${param.fdModelName}","write",fdFileName);
			if("${param.contentFlag}" != "" && "${param.contentFlag}" == "true" && "${_docStatus}" == "30"){
				wps_linux_${param.fdKey}.contentFlag = true;
			}
			if("${param.load}" != 'false'){
				wps_linux_${param.fdKey}.load();
			}

		}
	});

</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>