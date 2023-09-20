<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<script src="${KMSS_Parameter_ResPath}js/jquery.js"></script>

<script>
	Com_IncludeFile("dialog.js", null, "js");

	function confirmDelete(msg){
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
	
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/tic/rest/connector/tic_rest_setting/ticRestSetting.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('ticRestSetting.do?method=edit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/tic/rest/connector/tic_rest_setting/ticRestSetting.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticRestSetting.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-rest-connector" key="tree.ticRestSetting.register"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.docSubject"/>
		</td><td  width="85%" colspan="3">
			<xform:text property="docSubject" style="width:85%" />
		</td>
		<!--
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.settCategory"/>
			</td><td  width="35%">
				<xform:dialog required="true" propertyId="settCategoryId" propertyName="settCategoryName" dialogJs="categoryJs()">
				</xform:dialog>
			</td>		
		-->
	</tr>
	<tr>
		 <td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdConnectionRequestTimeout"/>
		</td><td  width="35%" >
			<xform:text property="fdConnRequestTimeout" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdConnectionTimeout"/>
		</td><td width="35%">
			<xform:text property="fdConnTimeout" style="width:85%" />
		</td>
	</tr>
	<tr>
		 <td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdSoTimeout"/>
		</td><td width="35%"  colspan="3" >
			<xform:text property="fdSoTimeout" style="width:85%" />
	</tr>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdHttpProxy"/>
		</td><td colspan="3" width="85%">
			<xform:radio property="fdHttpProxy" showStatus="readOnly">
				<xform:enumsDataSource  enumsType="rest_httpProxy_yesno" />
			</xform:radio>
		</td>
	</tr>
	
	<tr id="fdHttpProxyTr1" <c:if test="${ticRestSettingForm.fdHttpProxy eq 'false'}">style="display: none"</c:if> >
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdHttpProxyHost"/>
		</td><td width="35%">
			<xform:text property="fdHttpProxyHost" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.fdHttpProxyPort"/>
		</td><td width="35%">
			<xform:text property="fdHttpProxyPort" style="width:85%" />
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
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime"  showStatus="view" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestSetting.docAlterTime"/>
		</td><td colspan="3" width="85%">
			<xform:datetime property="docAlterTime" />
		</td>
	</tr>
	
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
