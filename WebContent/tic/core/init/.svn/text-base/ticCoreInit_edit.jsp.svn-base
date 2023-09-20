<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script language="JavaScript">
	Com_IncludeFile("jquery.js|dialog.js|data.js");
	Com_IncludeFile("ticCoreInit.js", "${KMSS_Parameter_ContextPath}tic/core/init/", "js", true);
</script>

<html:messages id="messages" message="true">
	<table style="margin:0 auto" align="center">
		<tr>
			<td>
				<img src='${KMSS_Parameter_ContextPath}resource/style/default/msg/dot.gif'>&nbsp;&nbsp;
				<font color='red'><bean:write name="messages" /></font>
			</td>
		</tr>
	</table><hr />
</html:messages> 
 
<html:form action="/tic/core/init/ticCoreInit.do">
<div id="optBarDiv">
	<input type=button value="${lfn:message('home.help')}"
			onclick="Com_OpenWindow(Com_Parameter.ContextPath+'tic/core/init/help/InitHelp.html','_blank');"/>
	<input type=button value="<bean:message bundle="tic-core-init" key="init.testConn"/>"
			onclick="if(!submitBefore())return;Com_Submit(document.ticCoreInitForm, 'testConn');">
	<input type="button" value="<bean:message key="init.importStandardPacket" bundle="tic-core-init"/>" onclick="if(!submitBefore())return;Com_Submit(document.ticCoreInitForm, 'importInitStandData');">
</div>

<p class="txttitle"><bean:message bundle="tic-core-init" key="module.init.data"/></p>
	<label>&nbsp;&nbsp;&nbsp;<%-- ${initTitle} --%></label>
<center>
<table class="tb_normal" width="95%" id="outterTableId">
	<tr>
		<td>
			<c:forEach items="${jspList}" var="jspPath">
				<jsp:include page="${jspPath }"></jsp:include>
			</c:forEach>
		</td>
	</tr>
</table>

</center>

<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();	
	function submitBefore() {
/* 		var moduleType = $("input[name='moduleType']:checked").val();
		if (moduleType == "" || moduleType == undefined) {
			alert("<bean:message key="init.emptyModuleType" bundle="tic-core-init"/>");
			return false;
		} else {
			return true;
		} */
		return true;
	}
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
