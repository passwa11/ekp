<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>

<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckSupport.jsp"%>

<%@ include file="sysAttMain_OCX.jsp"%>
<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
</script>
<script>Com_IncludeFile("jg_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<script type="text/javascript">
var jg_attachmentObject_${JsParam.fdKey} = new JG_AttachmentObject("${attachmentId}", "${JsParam.fdKey}", "${JsParam.fdModelName}", "${JsParam.fdModelId}", "${JsParam.fdAttType}", "view");
jg_attachmentObject_${JsParam.fdKey}.userId = "<%=com.landray.kmss.util.UserUtil.getUser().getFdId()%>";
jg_attachmentObject_${JsParam.fdKey}.userName = "<%=com.landray.kmss.util.UserUtil.getUser().getFdName()%>";
<c:if test="${not empty param.isTemplate && param.isTemplate == 'true'}">
	jg_attachmentObject_${JsParam.fdKey}.isTemplate = true;
</c:if>
<c:if test="${not empty param.editMode}">
	jg_attachmentObject_${JsParam.fdKey}.editMode = "${JsParam.editMode}";
</c:if>
<c:if test="${not empty param.buttonDiv}">
	jg_attachmentObject_${JsParam.fdKey}.buttonDiv = "${JsParam.buttonDiv}";
</c:if>
<c:if test="${not empty param.bookMarks}">
	jg_attachmentObject_${JsParam.fdKey}.bookMarks = "${JsParam.bookMarks}"; 
</c:if>
<c:if test="${JsParam.showToolBar =='false'}">
jg_attachmentObject_${JsParam.fdKey}.showToolBar =false;
</c:if>
//得到文档状态，用于控制留痕按钮在发布状态中不显示
<c:if test="${_docStatus=='30'}">
	jg_attachmentObject_${JsParam.fdKey}.hiddenRevisions=false;
</c:if>
<c:if test="${not empty param.protectstatus && param.protectstatus == 'false'}">
jg_attachmentObject_${JsParam.fdKey}.protectstatus = false;
</c:if>

<c:if test="${not empty attachmentId}">

<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attachmentId}" requestMethod="GET">
    jg_attachmentObject_${JsParam.fdKey}.canPrint=true;
</kmss:auth>

<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attachmentId}" requestMethod="GET">
	jg_attachmentObject_${JsParam.fdKey}.canDownload=true;
</kmss:auth>

<c:if test="${JsParam.editable =='true'}">
	jg_attachmentObject_${JsParam.fdKey}.protectstatus = false;
</c:if>

//公文中如果文档已经发布，则正文的下载打印权限需根据是否有下载打印角色控制
<c:if test="${_docStatus=='30'}">
	<% if(!com.landray.kmss.util.UserUtil.getKMSSUser().isAdmin()){%>
	<c:if test="${not empty param.contentFlag}">
		jg_attachmentObject_${JsParam.fdKey}.canPrint=false;
		<c:set var="printContent" value="false"/>	
		<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=printContent&fdModelName=${param.fdModelName}&fdId=${attachmentId}" requestMethod="GET">
			<c:set var="printContent" value="true"/>		
		</kmss:auth>
		<c:if test="${printContent eq 'true'}">
			<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attachmentId}" requestMethod="GET">
				jg_attachmentObject_${JsParam.fdKey}.canPrint=true;
			</kmss:auth>
		</c:if>
	</c:if>
	<%}%>
	<% if(!com.landray.kmss.util.UserUtil.getKMSSUser().isAdmin()){%>
	<c:if test="${not empty param.contentFlag}">
		jg_attachmentObject_${JsParam.fdKey}.canDownload=false;
		<c:set var="downloadContent" value="false"/>
		<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=downloadContent&fdModelName=${param.fdModelName}&fdId=${attachmentId}" requestMethod="GET">
			 <c:set var="downloadContent" value="true"/>	
		</kmss:auth>
		<c:if test="${downloadContent eq 'true'}">
			<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attachmentId}" requestMethod="GET">
				jg_attachmentObject_${JsParam.fdKey}.canDownload=true;
			</kmss:auth>
		</c:if>
	</c:if>
	<%}%>
</c:if>
<kmss:auth	
	requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=read&fdId=${attachmentId}"
			requestMethod="GET">
	jg_attachmentObject_${JsParam.fdKey}.canRead=true;		
</kmss:auth>
<kmss:auth	
	requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=${attachmentId}"
			requestMethod="GET">
	jg_attachmentObject_${JsParam.fdKey}.canCopy=true;	
</kmss:auth>
<kmss:auth	
	requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=edit&fdId=${attachmentId}"
			requestMethod="GET">
	jg_attachmentObject_${JsParam.fdKey}.canEdit=true;
</kmss:auth>
</c:if>
function OnToolsClick(vIndex,vCaption){
	if(vIndex=='-1'&&vCaption=='全屏_BEGIN'){
        if(jg_attachmentObject_${JsParam.fdKey}.canCopy){
      	  jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
      	  jg_attachmentObject_${JsParam.fdKey}.ocxObj.ShowToolBar = 2;
      	  jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "4,1";
  	  }else{
  		  jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
  		  jg_attachmentObject_${JsParam.fdKey}.ocxObj.ShowToolBar = 2;
  		  jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "0,1";
        }
    }
}
Com_AddEventListener(window, "unload", function() {
	jg_attachmentObject_${JsParam.fdKey}.unLoad();
});
</script>
<%
    boolean isExpand = "true".equals(request.getParameter("isExpand"));
if(isExpand){
%>
<script>
   if(jg_attachmentObject_${JsParam.fdKey}){
	   setTimeout(function(){
			jg_attachmentObject_${JsParam.fdKey}.load(encodeURIComponent("${fdFileName}"), "");
			jg_attachmentObject_${JsParam.fdKey}.show();
			if(jg_attachmentObject_${JsParam.fdKey}.ocxObj){
				if(!jg_attachmentObject_${JsParam.fdKey}.canCopy){
					jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
					jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "0,1";
				}else{
					jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
					jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "4,1";
				}
				if(Com_Parameter.IE)
					jg_attachmentObject_${JsParam.fdKey}.activeObj.setAttribute("OnToolsClick","OnToolsClick(vIndex,vCaption);");
				else
					jg_attachmentObject_${JsParam.fdKey}.activeObj.setAttribute("event_OnToolsClick","OnToolsClick");
			}
		},500);
	}
</script>
<%}else{%>
<ui:event event="show">
		if(jg_attachmentObject_${JsParam.fdKey}){
			setTimeout(function(){
			jg_attachmentObject_${JsParam.fdKey}.load(encodeURIComponent("${fdFileName}"), "");
			jg_attachmentObject_${JsParam.fdKey}.show();
			if(!jg_attachmentObject_${JsParam.fdKey}.canCopy){
				jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
				jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "0,1";
			}else{
				jg_attachmentObject_${JsParam.fdKey}.ocxObj.CopyType = "1";
				jg_attachmentObject_${JsParam.fdKey}.ocxObj.EditType = "4,1";
			}
			if(Com_Parameter.IE)
				jg_attachmentObject_${JsParam.fdKey}.activeObj.setAttribute("OnToolsClick","OnToolsClick(vIndex,vCaption);");
			else
				jg_attachmentObject_${JsParam.fdKey}.activeObj.setAttribute("event_OnToolsClick","OnToolsClick");
			},500);
		}

		if("${JsParam.loadJg}" == 'false'){
			Attachment_ObjectInfo['${JsParam.fdKey}'].unLoad();
			Attachment_ObjectInfo['${JsParam.fdKey}'].load(encodeURIComponent("${fdFileName}"), "");
			Attachment_ObjectInfo['${JsParam.fdKey}'].show();
		}
</ui:event>
<%}%>