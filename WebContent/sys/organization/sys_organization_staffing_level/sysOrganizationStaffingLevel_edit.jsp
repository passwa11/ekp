<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do">
<div id="optBarDiv">
	<c:if test="${sysOrganizationStaffingLevelForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysOrganizationStaffingLevelForm, 'update');">
	</c:if>
	<c:if test="${sysOrganizationStaffingLevelForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysOrganizationStaffingLevelForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysOrganizationStaffingLevelForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-organization" key="table.sysOrganizationStaffingLevel"/></p>

<center>
<table class="tb_normal" width=95%>
	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" validators="uniqueName" style="width:80%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdLevel"/>
		</td><td width="35%">
			<xform:text property="fdLevel" style="width:95%" validators="min(1)" />
			<!-- 
			<select name="fdLevel" class="interval">
                   <c:forEach  begin="1" end="10" varStatus="index" >
                   		<option value="${index.count}" <c:if test="${sysOrganizationStaffingLevelForm.fdLevel == index.count}">selected</c:if> >${index.count}</option>
                   </c:forEach>
			</select>
			 -->
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdIsDefault"/>
		</td><td width="85%" colspan="3">
			<xform:checkbox property="fdIsDefault">
				<xform:simpleDataSource value="true"><bean:message key="message.yes"/></xform:simpleDataSource>
			</xform:checkbox>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPost.fdPersons"/>
		</td><td colspan=3>
			<html:hidden property="fdPersonIds" />
			<html:text style="width:90%" property="fdPersonNames" readonly="true" styleClass="inputsgl"/>
			<a href="#" onclick="Dialog_Address(true, 'fdPersonIds', 'fdPersonNames', ';', ORG_TYPE_PERSON|ORG_FLAG_BUSINESSALL, null, null, null, null, null, null, null, null, false);">
				<bean:message key="dialog.selectOrg"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdDescription"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdDescription" style="width:95%" />
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	Com_IncludeFile("dialog.js");
	var _validation = $KMSSValidation();
	var NameValidators = {
			'uniqueName' : {
				error : "<bean:message key='sysOrganizationStaffingLevel.error.fdName.mustUnique' bundle='sys-organization' />",
				test : function (value) {
					var fdId = document.getElementsByName("fdId")[0].value;
					var result = checkNameUnique("sysOrganizationStaffingLevelService", value, fdId);
					if (!result) 
						return false;
					return true;
			      }
			}
 	};
	_validation.addValidators(NameValidators);
	//校验名称是否唯一
	function checkNameUnique(bean, fdName, fdId) {
		var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
				+ bean + "&fdName=" + fdName + "&fdId=" + fdId + "&date=" + new Date());
		var xmlHttpRequest;
		if (window.XMLHttpRequest) { // Non-IE browsers
			xmlHttpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					xmlHttpRequest = false;
				}
			}
		}
		if (xmlHttpRequest) {
			xmlHttpRequest.open("GET", url, false);
			xmlHttpRequest.send();
			var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
			if (result != "") {
				return false;
			}
		}
		return true;
	}
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>