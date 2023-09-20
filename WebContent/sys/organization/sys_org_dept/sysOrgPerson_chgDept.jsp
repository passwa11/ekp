<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String fdIds = (String)request.getAttribute("fdIds"); 
	String fdNames = (String)request.getAttribute("fdNames"); 
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept"/></span>
		</h2>
		
		<center>
			<form action="#">
				<table class="tb_normal" width="95%">
					<tr>
						<td width="15%" class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.fromPerson"/>
						</td>
						<td width="85%">
							<xform:address propertyId="fdIds" propertyName="fdNames" idValue="<%=fdIds %>" nameValue="<%=fdNames %>" mulSelect="true" showStatus="edit" 
								orgType="${HtmlParam.orgType}" style="width:95%" textarea="true" isExternal="false">
							</xform:address>
							<span class="txtstrong">*</span>
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.toDept"/>
						</td>
						<td width="85%">
							<xform:address propertyId="deptId" propertyName="deptName" showStatus="edit" 
								orgType="ORG_TYPE_ORGORDEPT" style="width:95%" onValueChange="deptChange" isExternal="false">
							</xform:address>
							<span class="txtstrong">*</span>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<c:choose>
								<c:when test="${'ORG_TYPE_PERSON' eq param.orgType}">
								<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.person.desc"/>
								</c:when>
								<c:when test="${'ORG_TYPE_POST' eq param.orgType}">
								<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.post.desc"/>
								</c:when>
							</c:choose>
						</td>
					</tr>
				</table>
			</form>
		</center>
		
	 	<script type="text/javascript">
/* 	 		function personChange(data) {
	 			$dialog.fdIds = data[0];
	 		} */
	 		function deptChange(data) {
	 			$dialog.deptId = data[0];
	 		}
	 	</script>
	</template:replace>
</template:include>
