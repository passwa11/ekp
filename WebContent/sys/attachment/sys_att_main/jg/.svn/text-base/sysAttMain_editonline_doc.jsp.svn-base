<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.forms.SysAttMainForm"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<!-- 加载扩展信息 -->
<% com.landray.kmss.sys.attachment.util.PluginUtil.getInstance().setEditOnline(request); %>
<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckEdit_js.jsp"%>

<c:set var="fdAttMainId" value="${sysAttMainForm.fdId}" scope="request"></c:set>
<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckSupport.jsp"%>

<%
SysAttMainForm attMainFrm = (SysAttMainForm)request.getAttribute("sysAttMainForm");
String fdFileName = attMainFrm.getFdFileName();
String p_fdFileName = StringUtil.escape(fdFileName);
pageContext.setAttribute("p_fdFileName", p_fdFileName);
pageContext.setAttribute("p_currentPerson", UserUtil.getKMSSUser().getUserId());
pageContext.setAttribute("fdFileName", fdFileName);
%>

<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined") {
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
	}
	
	var clauseMsg = {
		'msg1':"<bean:message bundle='sys-attachment' key='sysAttachment.agreement.parent.window.close'/>",
		"msg2":"<bean:message key='message.closeWindow'/>",
		"msg3":"${p_fdFileName}",
		"msg4":"JGWebOffice_${sysAttMainForm.fdKey}",
		"msg5":"<bean:message bundle='km-agreement' key='table.kmAgreementApply'/>",
		"msg6":"<bean:message bundle='km-agreement' key='py.FanBenQiCao'/>",
		"curUserId":"${p_currentPerson}",
		"fileName":"${fdFileName}"
		
	};
		
</script>
<script>Com_IncludeFile("jg_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<script>Com_IncludeFile("data.js|json2.js");</script>
<script>Com_IncludeFile("jg_editdoc.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
	<div id="optBarDiv">
		<table class="tb_noborder">
		<tr><td id="_button_${sysAttMainForm.fdKey}_JG_Attachment_TD"></td>
		</tr>
		</table>
		<input type=button value="<bean:message key="button.update"/>"
				onclick="return Attach_EditOnlineSubmit();"/>
		<input type="button" value="<bean:message key="button.close"/>"
			onclick="closeWin();">
	</div>
	<!--  加载扩展JSP片断 -->
	<c:forEach items="${editOnlineMap['jsp']}" var="jsp">
		<c:import url="${jsp}" charEncoding="UTF-8">
			<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
			<c:param name="fdAttType" value="office" />
			<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
			<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
			<c:param name="editMode" value="2" />		
			<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
			<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
		</c:import>	
	</c:forEach>
	<table id = "jg_tab1" class="tb_normal" width="100%" height="95%" style="margin-top: -10px;float: left">
		<tr>
		<td valign="top">
		  <div id="warnDiv">
          </div>
		<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_OCX.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
			<c:param name="fdAttType" value="office" />
			<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
			<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
			<c:param name="editMode" value="2" />		
			<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
			<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
			<c:param name="canPrint" value="${canPrint}" />
			<c:param name="attHeight" value="${not empty HtmlParam.attHeight?HtmlParam.attHeight:'100%'}" />
			<c:param name="trackRevisions" value="1" />			
		</c:import>			
		</td>		
		</tr>
	</table>
	
	<script type="text/javascript">
		Com_SetWindowTitle("${fdFileName}");
  		var url = window.location.href;
  		var fdId = Com_GetUrlParameter(url,"fdId");
  		var fdKey = '${sysAttMainForm.fdKey}';
  		var fdModelId = '${sysAttMainForm.fdModelId}';
  		var fdModelName = '${sysAttMainForm.fdModelName}';
  		if(fdModelId == null){
  			fdModelId = "";
  		}
  		if(fdModelName == null){
  			fdModelName = "";
  		}
  		var jg_attachmentObject_editonline = new JG_AttachmentObject(fdId,fdKey, fdModelName, fdModelId, "office", "edit");
  		jg_attachmentObject_editonline.userId = "<%=com.landray.kmss.util.UserUtil.getUser().getFdId()%>";
  		jg_attachmentObject_editonline.userName = "<%=com.landray.kmss.util.UserUtil.getUser().getFdName()%>";
  		<c:if test="${canPrint=='1'}">
  			jg_attachmentObject_editonline.canPrint = true;
  		</c:if>
  		<c:if test="${canCopy=='1'}">
  			jg_attachmentObject_editonline.canCopy = true;	
  		</c:if>	
  	    //在线编辑打开，默认显示留痕
  		jg_attachmentObject_editonline.showRevisions = false;

  	    <!-- 加载扩展JSP片断 -->
  		<c:forEach items="${editOnlineMap['script']}" var="script">
  			<c:import url="${script}" charEncoding="UTF-8">
				<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
				<c:param name="fdAttType" value="office" />
				<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
				<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
				<c:param name="editMode" value="2" />		
				<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
				<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
			</c:import>	
  		</c:forEach>


  </script>
	
	<!-- 条款引用页面 -->
	<table id = "jg_tab2" class="tb_normal" width="26%" height="95%" style="margin-top: -10px;float: left;display: none">
		<tr>
		<td valign="top" id = "panelTd">
		<c:import url="/km/agreement/jg/taskPane/clause/clauseIndex.jsp" charEncoding="UTF-8">
		</c:import>
		</td>
		</tr>
	</table>

<%@ include file="/resource/jsp/edit_down.jsp"%>
