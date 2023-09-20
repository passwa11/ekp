<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<script language="JavaScript">
	Com_IncludeFile("jquery.js|dialog.js|calendar.js|doclist.js|optbar.js|data.js");
</script>

<script type="text/javascript">

function categoryJs(){
	Dialog_Tree(false, 'settCategoryId', 'settCategoryName', ',', 
		'ticRestSettCategoryTreeService&parentId=!{value}', 
		'<bean:message key="table.ticRestSettingRegister" bundle="tic-rest-connector"/>', 
		null, null, '${ticRestSettCategoryForm.fdId}', null, null, 
		'<bean:message  bundle="tic-rest-connector" key="table.ticRestCategory"/>');
}

</script>

<html:form action="/tic/rest/connector/tic_rest_setting/ticRestSetting.do">
<div id="optBarDiv">
	<input type=button value="${lfn:message('home.help')}"
			onclick="window.open(Com_Parameter.ContextPath+ 'tic/rest/help/rest_setting.html');">
	<c:if test="${ticRestSettingForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="waitSubmit('update');">
	</c:if>
	<c:if test="${ticRestSettingForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="waitSubmit('save');">
		<!-- 
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="waitSubmit('saveadd');">
		 -->
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-rest-connector" key="tree.ticRestSetting.register"/></p>

<center>
<table class="tb_normal" width=95%>
	<!-- 服务名称 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.docSubject"/>
		</td><td  width="85%" colspan="3">
			<xform:text property="docSubject" style="width:85%" required="true" />
		</td>
	</tr>

	<tr>
		 <td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdConnectionRequestTimeout"/>
			<br>
			<p style="color:red">${lfn:message('tic-core-common:ticCoreCommon.pool.connectTimeout.note')}</p>
		</td>
		<td  width="35%">
			<xform:text property="fdConnRequestTimeout" style="width:85%"  htmlElementProperties="placeholder='${ lfn:message('tic-rest-connector:ticRestSetting.fdConnectionRequestTimeout.tip') }' " />
			
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdConnectionTimeout"/>
			<br>
			<p style="color:red">${lfn:message('tic-core-common:ticCoreCommon.server.connectTimeout.note')}</p>
		</td><td width="35%">
			<xform:text property="fdConnTimeout" style="width:85%"   htmlElementProperties="placeholder='${ lfn:message('tic-rest-connector:ticRestSetting.fdConnectionTimeout.tip') }' " />
		</td>
	</tr>
	<tr>
		 <td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdSoTimeout"/>
			<br>
			<p style="color:red">${lfn:message('tic-core-common:ticCoreCommon.dataPacket.intervalTime.note')}</p>
		</td><td  width="35%"  colspan="3" >
			<xform:text property="fdSoTimeout" style="width:85%"   htmlElementProperties="placeholder='${ lfn:message('tic-rest-connector:ticRestSetting.fdSoTimeout.tip') }' "/>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdHttpProxy"/>
		</td><td colspan="3" width="85%">
			<xform:radio property="fdHttpProxy" onValueChange="httpProxyChange();">
				<xform:enumsDataSource  enumsType="rest_httpProxy_yesno" />
			</xform:radio>
		</td>
	</tr>
	
	<tr id="fdHttpProxyTr1" <c:if test="${ticRestSettingForm.fdHttpProxy eq 'false'}">style="display: none"</c:if> >
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdHttpProxyHost" />
		</td><td width="35%">
			<xform:text property="fdHttpProxyHost" style="width:85%"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdHttpProxyPort" />
		</td><td width="35%">
			<xform:text property="fdHttpProxyPort" style="width:85%"/>
		</td>
	</tr>
	<tr id="fdHttpProxyTr2" <c:if test="${ticRestSettingForm.fdHttpProxy eq 'false'}">style="display: none"</c:if> >
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdHttpProxyUsername"/>
		</td><td width="35%">
			<xform:text property="fdHttpProxyUsername" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdHttpProxyPassword"/>
		</td><td width="35%">
			<xform:text property="fdHttpProxyPassword" style="width:85%" />
		</td>
	</tr>
</table>

</center>
<html:hidden property="fdAppType" value="${param.fdAppType}"/>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();	
	// 提示的方法
	function waitSubmit(method) {
		Com_Submit(document.ticRestSettingForm, method);
	}
		
	function httpProxyChange() {
		var fdHttpProxy = document.getElementsByName("fdHttpProxy")[0];
		if (fdHttpProxy.checked) {
			document.getElementById("fdHttpProxyTr1").style.display = "";
			document.getElementById("fdHttpProxyTr2").style.display = "";
			$("[name='fdHttpProxyHost']").attr("validate","required"); 
			$("[name='fdHttpProxyPort']").attr("validate","required"); 
		} else {
			document.getElementsByName("fdHttpProxyHost")[0].value = "";
			document.getElementsByName("fdHttpProxyPort")[0].value = "";
			document.getElementsByName("fdHttpProxyUsername")[0].value = "";
			document.getElementsByName("fdHttpProxyPassword")[0].value = "";
			document.getElementById("fdHttpProxyTr1").style.display = "none";
			document.getElementById("fdHttpProxyTr2").style.display = "none";
			$("[name='fdHttpProxyHost']").removeAttr("validate","required"); 
			$("[name='fdHttpProxyPort']").removeAttr("validate","required"); 
		}
	};

</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
