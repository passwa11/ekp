<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ page import="
	com.landray.kmss.util.KmssMessageWriter,com.landray.kmss.sys.xform.util.SysFormDingUtil,
	com.landray.kmss.util.KmssReturnPage,
	com.landray.kmss.util.StringUtil" %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<% 
	response.addHeader("X-UA-Compatible", "IE=edge");
	request.setAttribute("_dingxform", SysFormDingUtil.getEnableDing());
%>
<script type="text/javascript">
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_Parameter.dingXForm = "${_dingxform}";
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("docutil.js|optbar.js|validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js", null, "js");
</script>
</head>
<body class="${_dingxform=='true' ? 'lui_body_main_dingsuit':''}">
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn_old.jsp" charEncoding="UTF-8">
		<c:param name="helpBtnOrder" value="${JsParam.helpBtnOrder}"></c:param>
	</c:import>
</kmss:ifModuleExist>
<br>
<% if(request.getAttribute("KMSS_RETURNPAGE")==null && StringUtil.isNull(request.getParameter("msg_success"))){ %>
<logic:messagesPresent>
	<table align=center><tr><td>
		<font class="txtstrong"><bean:message key="errors.header.vali"/></font>
		<bean:message key="errors.header.correct"/>
		<html:messages id="error">
			<br><img src='${KMSS_Parameter_StylePath}msg/dot.gif'>&nbsp;&nbsp;<bean:write name="error"/>
		</html:messages>
	</td></tr></table>
	<hr />
</logic:messagesPresent>
<% }else{
	KmssMessageWriter msgWriter = new KmssMessageWriter(request, (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE"));
%>
<script>
Com_IncludeFile("msg.js", "style/"+Com_Parameter.Style+"/msg/");
function showMoreErrInfo(index, srcImg){
	var obj = document.getElementById("moreErrInfo"+index);
	if(obj!=null){
		if(obj.style.display=="none"){
			obj.style.display="block";
			srcImg.src = Com_Parameter.StylePath + "msg/minus.gif";
		}else{
			obj.style.display="none";
			srcImg.src = Com_Parameter.StylePath + "msg/plus.gif";
		}
	}
}
<%
if(StringUtil.isNotNull(request.getParameter("msg_success"))) {
%>
	(function() {
		if(window.history && window.history.replaceState) {
			var url = location.href;
			url = Com_SetUrlParameter(url, "msg_success", null);
			window.history.replaceState(null, null, url);
		}
	})()
<%
}
%>

</script>
<table align=center  width="100%">
<tr>
<td width="100%" align="center">
	<%= msgWriter.DrawTitle() %>
	<br style="font-size:10px;">
	<%= msgWriter.DrawMessages() %>
</td>
</tr></table>
<hr />
<% } %>