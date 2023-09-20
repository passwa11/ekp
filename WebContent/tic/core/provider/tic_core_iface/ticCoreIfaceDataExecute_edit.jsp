<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.tic.core.provider.plugins.TicCoreProviderPlugins" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib
	uri="/tic/core/provider/resource/tld/tic-sys-provider.tld"
	prefix="tic"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>


<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="com.landray.kmss.tic.core.util.TicCoreUtil"%>
<html:form
	action="/tic/core/provider/tic_core_iface/ticCoreIface.do">
	<div id="optBarDiv">
		<!-- 数据执行 -->
		<input type=button value="<bean:message bundle="tic-core-provider" key="ticCoreIface.execute.dataExecute"/>"
			onclick="submit_before('dataExecuteAndBack');">
		<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();"></div>

	<p class="txttitle">${ticCoreIfaceForm.fdIfaceName}</p>

<center>
<table id="Label_Tabel" width="95%" >
	<tr LKS_LabelName="<bean:message bundle="tic-core-provider" key="ticCoreIface.execute.dataFill"/>">
		<td>
			<table class="tb_normal" width="100%">
				<!-- 数据填写 -->
				<tr>
					<td width="100%">
						<textarea name="ticDataFill" id="ticDataFill" 
								style='overflow:scroll;overflow-y:hidden;;overflow-x:hidden;width:100%;'
								onfocus="window.activeobj=this;this.clock=setInterval(function(){activeobj.style.height=activeobj.scrollHeight+'px';},200);" 
								onblur="clearInterval(this.clock);">${ticDataFill}</textarea>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<c:if test="${param.method != 'dataExecute'}" >
	<tr LKS_LabelName="<bean:message bundle="tic-core-provider" key="ticCoreIface.execute.result"/>">
		<td>
			<table class="tb_normal" width="100%">
				<!-- 执行结果 -->
				<tr>
					<td width="100%">
						<textarea name="ticDataResult" id="ticDataResult" 
								style='overflow:scroll;overflow-y:hidden;;overflow-x:hidden;width:100%;'
								onfocus="window.activeobj=this;this.clock=setInterval(function(){activeobj.style.height=activeobj.scrollHeight+'px';},200);" 
								onblur="clearInterval(this.clock);"></textarea>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</c:if>
</table>
</center>
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />

<script type="text/javascript">
	Com_IncludeFile("dialog.js|jquery.js");
	Com_IncludeFile("tic_sys_util.js","${KMSS_Parameter_ContextPath}tic/core/provider/resource/js/","js",true);
	var ticValidation = $KMSSValidation();
</script>
<script>
	$(function(){
		//var tagdb = "<bean:message bundle="tic-core-provider" key="ticCore.lang.tagdb"/>";
		//var headDefinition = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"; 
		var ticDataFillObj = document.getElementById("ticDataFill");
		// 用于长度自动变化 
		window.activeobj = ticDataFillObj;
		ticDataFillObj.clock=setInterval(function(){activeobj.style.height=activeobj.scrollHeight+'px';},10);
		var fdIfaceXml = "${fdIfaceXml}";
		<c:if test="${empty ticDataFill}">
			$("#ticDataFill").text(TIC_SysUtil.formatXml(fdIfaceXml, "    "));
		</c:if>
	});

	<c:if test="${param.method == 'dataExecuteAndBack'}">
	$(function(){
		// 默认展现第二个页签
		$("#Label_Tabel").attr("LKS_LabelDefaultIndex", "2");
		var headDefinition = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; 
		var executeBackXml = "${executeBackXml}";
		var fomatBackXml = TIC_SysUtil.formatXml(executeBackXml, "    ");
		var ticDataResultObj = document.getElementById("ticDataResult");
		// 用于长度自动变化
		window.activeobj = ticDataResultObj;
		ticDataResultObj.clock=setInterval(function(){activeobj.style.height=activeobj.scrollHeight+'px';},10);
		if (executeBackXml.indexOf(headDefinition) != -1) {
			$("#ticDataResult").text(fomatBackXml);
		} else {
			$("#ticDataResult").text((headDefinition +"\r"+ fomatBackXml));
		}
	});
	</c:if>

	function submit_before(method) {
		var ticDataResult = document.getElementById("ticDataResult");
		if (ticDataResult != null) {
			$(ticDataResult).text("");
		}
		Com_Submit(document.ticCoreIfaceForm, method);
	}
</script>



</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>