<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%-- 加载扩展信息 --%>
<% com.landray.kmss.sys.attachment.util.PluginUtil.getInstance().setViewOnline(request); %>
<c:set var="fdAttMainId" value="${sysAttMainForm.fdId}" scope="request"></c:set>
<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckSupport.jsp"%>
<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%=UserUtil.getKMSSUser(request).getLocale().toString()
					.toLowerCase().replace('_', '-')%>";
</script>
<script>
	 function closeWin(){
		if(jg_attachmentObject_editonline.ocxObj){
			if(jg_attachmentObject_editonline.ocxObj.WebClose()){
				 window.close();
			}
		}else{
			window.close();
		}
	}
</script>
<script>Com_IncludeFile("jg_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<c:if test="${empty HtmlParam.inner }">
<div id="optBarDiv">
		<table class="tb_noborder">
			<tr>
				<c:if test="${empty param.hiddenButton}">
					<td id="_button_${sysAttMainForm.fdKey}_JG_Attachment_TD"></td>
				</c:if>			
			</tr>
		</table>
		<input type="button" value="<bean:message key="button.close"/>"
			onclick="Com_Parameter.CloseInfo=null;closeWin();">
</div>
</c:if>
<c:if test="${not empty HtmlParam.inner }">
	<div id="jg_${sysAttMainForm.fdKey}_buttonDiv" style="float:right;padding-bottom: 5px"></div>
</c:if>
<c:set var="canPrint" value="" />
<c:set var="canCopy" value="" />
<c:set var="canDownload" value="" />
<kmss:auth
	requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${sysAttMainForm.fdId}"
	requestMethod="GET">
	<c:set var="canPrint" value="1" />
</kmss:auth>
<kmss:auth
	requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=${sysAttMainForm.fdId}"
	requestMethod="GET">
	<c:set var="canCopy" value="1" />
</kmss:auth>
<kmss:auth
	requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${sysAttMainForm.fdId}"
	requestMethod="GET">
	<c:set var="canDownload" value="1" />
</kmss:auth>
<c:if test="${canPrint!='1'}">
  <style>
    @media print { 
      #sysAttMain_jg_table { display:none; } 
    } 
</style>
</c:if>
<%-- 加载扩展JSP片断 --%>
<c:forEach items="${viewOnlineMap['jsp']}" var="jsp">
	<c:import url="${jsp}" charEncoding="UTF-8">
		<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
		<c:param name="fdAttType" value="office" />
		<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
		<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
		<c:param name="editMode" value="3" />
		<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
		<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
	</c:import>
</c:forEach>
<table class="tb_normal" width=100% height="95%" id="sysAttMain_jg_table" style="margin-top: -10px;">
	<tr>
		<td><c:import
				url="/sys/attachment/sys_att_main/jg/sysAttMain_OCX.jsp"
				charEncoding="UTF-8">
				<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
				<c:param name="fdAttType" value="office" />
				<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
				<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
				<c:param name="editMode" value="3" />
				<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
				<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
				<c:param name="canPrint" value="${canPrint}" />
				<c:param name="canCopy" value="${canCopy}" />
				<c:param name="attHeight" value="${not empty HtmlParam.attHeight?HtmlParam.attHeight:'100%'}" />
			</c:import></td>
	</tr>
</table>
<script type="text/javascript">
	Com_SetWindowTitle("${sysAttMainForm.fdFileName}");
	var url = window.location.href;
	var fdId = Com_GetUrlParameter(url,"fdId");
	var fdKey ="${sysAttMainForm.fdKey}";
	var jg_attachmentObject_editonline = new JG_AttachmentObject(fdId,fdKey, "${sysAttMainForm.fdModelName}", "", "office", "view");
	jg_attachmentObject_editonline.userId = "<%=com.landray.kmss.util.UserUtil.getUser().getFdId()%>";
	jg_attachmentObject_editonline.userName = "<%=com.landray.kmss.util.UserUtil.getUser().getFdName()%>";
	<c:if test="${docStatus!='' && docStatus=='30'}">
	jg_attachmentObject_editonline.hiddenRevisions=false;
	</c:if>
	<c:if test="${canPrint=='1'}">
	jg_attachmentObject_editonline.canPrint = true;
	</c:if>
	<c:if test="${not empty HtmlParam.inner}">
		jg_attachmentObject_editonline.buttonDiv = "jg_${sysAttMainForm.fdKey}_buttonDiv";
	</c:if>
	<c:if test="${canCopy=='1'}">
	jg_attachmentObject_editonline.canCopy = true;
	</c:if>
	<c:if test="${canDownload=='1'}">
	jg_attachmentObject_editonline.canDownload = true;
	</c:if>
	
	<%-- 加载扩展JS片断 --%>
	<c:forEach items="${viewOnlineMap['script']}" var="script">
		<c:import url="${script}" charEncoding="UTF-8">
			<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
			<c:param name="fdAttType" value="office" />
			<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
			<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
			<c:param name="editMode" value="3" />
			<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
			<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
		</c:import>
	</c:forEach>
	
	Com_AddEventListener(window, "load", function() {
		setTimeout(function(){
			jg_attachmentObject_editonline
					.load(encodeURIComponent("${sysAttMainForm.fdFileName}"), "");
			jg_attachmentObject_editonline.show();
			if(jg_attachmentObject_editonline.ocxObj){
				//<c:if test="${isPrint}">
				//	jg_attachmentObject_editonline.ocxObj.WebOpenPrint();
				//</c:if>
				if (!jg_attachmentObject_editonline.canCopy) {
					jg_attachmentObject_editonline.ocxObj.CopyType = "0";
					jg_attachmentObject_editonline.ocxObj.EditType = "0,1";
				} else {
					jg_attachmentObject_editonline.ocxObj.CopyType = "1";
					jg_attachmentObject_editonline.ocxObj.EditType = "4,1";
				}
				if (Com_Parameter.IE)
					jg_attachmentObject_editonline.activeObj.setAttribute("OnToolsClick",
							"OnToolsClick(vIndex,vCaption);");
				else
					jg_attachmentObject_editonline.activeObj.setAttribute(
							"event_OnToolsClick", "OnToolsClick");
			}
		},1000);
		if("${JsParam.attHeight}" == ""){
			var winHeight = 550;
			if (window.innerHeight) {
				winHeight = window.innerHeight;
				winHeight = (winHeight-80);
			}
			//else if ((document.body) && (document.body.clientHeight)) {
			//	winHeight = document.body.clientHeight;
			//}
			var obj1 = document.getElementById("JGWebOffice_${sysAttMainForm.fdKey}");
			if(obj1){
				obj1.setAttribute("height", winHeight+"px");
			}
		}
	});
	function OnToolsClick(vIndex, vCaption) {
		if (vIndex == '-1' && vCaption == '全屏_BEGIN') {
			if(jg_attachmentObject_editonline.ocxObj){
				if (jg_attachmentObject_editonline.canCopy) {
					jg_attachmentObject_editonline.ocxObj.CopyType = "1";
					jg_attachmentObject_editonline.ocxObj.ShowToolBar = 2;
					jg_attachmentObject_editonline.ocxObj.EditType = "4,1";
				} else {
					jg_attachmentObject_editonline.ocxObj.CopyType = "1";
					jg_attachmentObject_editonline.ocxObj.ShowToolBar = 2;
					jg_attachmentObject_editonline.ocxObj.EditType = "0,1";
				}
			}
		}
	}
	Com_AddEventListener(window, "unload", function() {
		jg_attachmentObject_editonline.unLoad();
	});
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
