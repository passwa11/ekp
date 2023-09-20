<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<title><bean:message key="ui.tools.title" bundle="sys-ui"/></title>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");            
</script>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="ui.tools.download" bundle="sys-ui"/>" onclick="Com_SubmitNoEnabled(document.forms[0],'download');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<center>
<p class="txttitle"><bean:message key="ui.tools.title" bundle="sys-ui"/></p>
<form action="${KMSS_Parameter_ContextPath}sys/ui/sys_ui_tool/sysUiTools.do?method=download" method="post" enctype="application/x-www-form-urlencoded" >
<table class="tb_normal" width=85%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="ui.tools.type" bundle="sys-ui"/>
		</td>
		<td width="85%">
			<input type="text" name="fdFileType"  class="inputsgl" style="width:85%;" value="html,js,css,jpg,png,gif,jpg,tmpl,eot,svg,ttf,woff"/><span class="txtstrong">*</span><br/>
			<span><bean:message key="ui.tools.value" bundle="sys-ui"/></span>
		</td>
	</tr>	
</table>
</form>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>