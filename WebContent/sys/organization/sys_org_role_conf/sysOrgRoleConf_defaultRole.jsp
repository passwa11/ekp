<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<%@page import="com.landray.kmss.sys.organization.forms.SysOrgRoleLineDefaultRoleForm"%><html:form action="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do">
<div id="optBarDiv">
	<input type=button value="<bean:message key="button.update"/>"
		onclick="Com_Submit(document.sysOrgRoleConfForm, 'updateRepeatRole');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message  bundle="sys-organization" key="sysOrgRoleConf.repeat.check"/></p>
<center>
<table class="tb_normal" width="600px">
	<c:if test="${empty (sysOrgRoleConfForm.defaultRoleList) && empty (invalidElement)}">
		<tr>
			<td class="td_normal_title">
				<bean:message  bundle="sys-organization" key="sysOrgRoleConf.repeat.check.ok"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${not empty (sysOrgRoleConfForm.defaultRoleList)}">
		<tr>
			<td class="td_normal_title">
				<bean:message  bundle="sys-organization" key="sysOrgRoleConf.repeat.check.prompt"/>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" class="tb_normal" id="mainTable">
					<tr align="center">
						<td class="td_normal_title"><bean:message key="page.serial"/></td>
						<td class="td_normal_title" width=30%>
							<bean:message  bundle="sys-organization" key="sysOrgRoleConf.repeat.check.person"/>
						</td>
						<td class="td_normal_title" width=35%>
							<bean:message  bundle="sys-organization" key="sysOrgRoleConf.repeat.check.belong.role"/>
						</td>
						<td class="td_normal_title" width=35%>
							<bean:message  bundle="sys-organization" key="sysOrgRoleConf.repeat.check.default.role"/>
						</td>
					</tr>
					<c:forEach items="${sysOrgRoleConfForm.defaultRoleList}" var="defaultRoleForm" varStatus="vstatus">
						<tr KMSS_IsContentRow="1">
							<td align="center">
								${vstatus.index + 1}
								<input type="hidden" name="defaultRoleList[${vstatus.index}].fdId" value="${defaultRoleForm.fdId }"/>
								<input type="hidden" name="defaultRoleList[${vstatus.index}].fdRoleLineConfId" value="${sysOrgRoleConfForm.fdId }"/>
								<input type="hidden" name="defaultRoleList[${vstatus.index}].fdRoleLineConfName" value="${sysOrgRoleConfForm.fdName }"/>
							</td>
							<td align="center">
								<input type="hidden" name="defaultRoleList[${vstatus.index}].fdPersonId" value="${defaultRoleForm.fdPersonId }"/>
								<input type="hidden" name="defaultRoleList[${vstatus.index}].fdPersonName" value="${defaultRoleForm.fdPersonName }"/>
								${defaultRoleForm.fdPersonName }
							</td>
							<td align="center">
								<input type="hidden" name="defaultRoleList[${vstatus.index}].fdPostIds" value="${defaultRoleForm.fdPostIds }"/>
								<input type="hidden" name="defaultRoleList[${vstatus.index}].fdPostNames" value="${defaultRoleForm.fdPostNames }"/>
								<%
									SysOrgRoleLineDefaultRoleForm form = (SysOrgRoleLineDefaultRoleForm)pageContext.getAttribute("defaultRoleForm");
									String[] names2 = form.getFdPostNames().split(";");
									for(int i=0; i<names2.length; i++){
										out.write(names2[i]+"<br>");
									}
								%>
								
							</td>
							<td align="center">
								<select name="defaultRoleList[${vstatus.index}].fdPostId">
								<%
									SysOrgRoleLineDefaultRoleForm form2 = (SysOrgRoleLineDefaultRoleForm)pageContext.getAttribute("defaultRoleForm");
									String[] ids = form2.getFdPostIds().split(";");
									String[] names = form2.getFdPostNames().split(";");
									for(int i=0; i<ids.length; i++){
										out.write("<option value=\""+ids[i]+"\"");
										if(ids[i].equals(form.getFdPostId())){
											out.write(" selected");
										}
										out.write(">"+names[i]+"</option>");
									}
								%>
								</select>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</c:if>
	
	<c:if test="${not empty (invalidElement)}">
		<tr>
			<td class="td_normal_title">
				<bean:message  bundle="sys-organization" key="sysOrgRoleConf.invalid.check.prompt"/>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" class="tb_normal" id="mainTable">
					<tr align="center">
						<td class="td_normal_title" width=10%><bean:message key="page.serial"/></td>
						<td class="td_normal_title" width=30%>
							<bean:message  bundle="sys-organization" key="organization.fdOrgType"/>
						</td>
						<td class="td_normal_title" width=30%>
							<bean:message  bundle="sys-organization" key="sysOrgRoleConf.fdName"/>
						</td>
						<td class="td_normal_title" width=30%>
							<bean:message  bundle="sys-organization" key="sysOrgRoleConf.fdIsAvailable"/>
						</td>
					</tr>
					<c:forEach items="${invalidElement}" var="element" varStatus="vstatus">
						<tr KMSS_IsContentRow="1">
							<td align="center">
								${vstatus.index + 1}
							</td>
							<td align="center">
								<c:if test="${element.fdOrgType == 1}">
									<bean:message  bundle="sys-organization" key="sysOrgElement.org"/>
								</c:if>
								<c:if test="${element.fdOrgType == 2}">
									<bean:message  bundle="sys-organization" key="sysOrgElement.dept"/>
								</c:if>
								<c:if test="${element.fdOrgType == 4}">
									<bean:message  bundle="sys-organization" key="sysOrgElement.post"/>
								</c:if>
								<c:if test="${element.fdOrgType == 8}">
									<bean:message  bundle="sys-organization" key="sysOrgElement.person"/>
								</c:if>
								<c:if test="${element.fdOrgType == 10}">
									<bean:message  bundle="sys-organization" key="sysOrgRole.common.group"/>
								</c:if>
							</td>
							<td align="center">
								${element.fdName }
							</td>
							<td align="center">
								<bean:message  bundle="sys-organization" key="sysOrg.address.info.disable"/>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</c:if>
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId" value="${sysOrgRoleConfForm.fdId }"/>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>