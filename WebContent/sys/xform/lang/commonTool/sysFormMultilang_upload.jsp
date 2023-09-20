<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@page
	import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
Com_IncludeFile("jquery.js");
</script>
<style>
<!--
.covered {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 9999;
}

.btn {
	text-align:left;
	width: 95%;
	margin: 5px;
	height: 200;
	border: 1px solid #e6e6e6;
}
-->
</style>
<div class="txttitle">
	<title><bean:message bundle="sys-xform"
			key="sysFormMultiLang.multi.imporexp" /></title>
</div>
<div id="optBarDiv">
	<%if(LangUtil.isEnableAdminDoMultiLang()){ %>
	<input type="button"
		value="<bean:message bundle="sys-xform" key="sysFormMultiLang.import"/>"
		onclick="Com_OpenWindow(Com_Parameter.ContextPath + 'sys/xform/lang/sysFormMultilang_upload.jsp')">
	<input type="button"
		value="<bean:message bundle="sys-xform" key="sysFormMultiLang.export"/>"
		onclick="documentSubmit()">
	<%}%>
</div>
<form
	action='<c:url value="sys/mutillang/import.do?method=excelBatchExport"/>'
	method="POST">
	<center>
	<div class="btn">
		<bean:message bundle="sys-xform" key="sysFormMultiLang.multi.model.enable.export"/><br/><br/>
		<%
			IExtension[] extensionsModelName = Plugin.getExtensions("com.landray.kmss.sys.formMultiLang.import.log", "*", "item");
				for (IExtension extension : extensionsModelName) {
		%>
		<label> <input type='checkbox' name='modelNames'
			id='uu_checkbox'
			value='<%=Plugin.getParamValueString(extension, "uuid")%>' /> <%=Plugin.getParamValue(extension, "name")%>
		</label>
		<%
			}
		%>
	</div>

	<div class="btn">
		<bean:message bundle="sys-xform" key="sysFormMultiLang.multi.language.enable.export"/><br/><br/>
		<%
			IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.sys.formMultiLang", "*", "item");
				for (IExtension extension : extensions) {
		%>
		<label> <input type='checkbox' name='languages'
			value='<%=Plugin.getParamValueString(extension, "uuid")%>' /> <bean:message
				bundle='<%=Plugin.getParamValueString(extension, "bundle")%>'
				key='<%=Plugin.getParamValueString(extension, "key")%>' />
		</label>
		<%
			}
		%>
	</div>
	</center>
</form>



<script>

	
	var url = Com_Parameter.ContextPath
			+ "sys/mutillang/import.do?method=excelBatchExport";

	function getCheckBoxVal(checkboxName, spilt) {
		var result = "";
		$("input[name='" + checkboxName + "']").each(function() {
			if (this.checked) {
				result += $(this).val() + spilt;
			}
		});
		return result.substr(0, result.length - 1);
	}
	
	function checkModel(){
		if(getCheckBoxVal("modelNames", ";").length == 0){
			alert('<bean:message bundle="sys-xform" key="sysFormMultiLang.multi.please.select.import.model"/>')
			return false;
		}
		return true;
	}
	
	function checkLang(){
		if(getCheckBoxVal("languages", ";").length == 0){
			alert('<bean:message bundle="sys-xform" key="sysFormMultiLang.multi.please.select.import.language"/>')
			return false;
		}
		return true;
	}
	
	function documentSubmit() {
		if(checkModel()&&checkLang()){
			Com_OpenWindow(url + "&languages=" + getCheckBoxVal("languages", ";") + "&modelNames=" + getCheckBoxVal("modelNames", ";"));	
		}
	}
	
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>
