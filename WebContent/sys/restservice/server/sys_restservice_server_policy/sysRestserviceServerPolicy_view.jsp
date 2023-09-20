<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
	function confirmDelete(msg){
	var del = confirm('<bean:message key="page.comfirmDelete"/>');
	return del;
}
</script>
<c:set var="_fdPolicy" value="${(sysRestserviceServerPolicyForm.fdPolicy==null)?'0':(sysRestserviceServerPolicyForm.fdPolicy)}"/>
<div id="optBarDiv">
	<c:if test="${_fdPolicy=='0'}">
		<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_policy/sysRestserviceServerPolicy.do?method=editPassword&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message bundle="sys-restservice-server" key="button.password"/>"
				onclick="Com_OpenWindow('sysRestserviceServerPolicy.do?method=editPassword&fdId=${JsParam.fdId}','_blank');">
		</kmss:auth>
	</c:if>
	<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_policy/sysRestserviceServerPolicy.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('sysRestserviceServerPolicy.do?method=edit&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_policy/sysRestserviceServerPolicy.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('sysRestserviceServerPolicy.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>	
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-restservice-server" key="table.sysRestserviceServerPolicy"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:35%" value="${(sysRestserviceServerPolicyForm.fdName==null)?(sysRestserviceServerPolicyForm.fdUserName):(sysRestserviceServerPolicyForm.fdName)}"/>
		</td>
	</tr>		
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdPolicy"/>
		</td><td width="85%" colspan="3">
			<xform:select property="fdPolicy"
				value="${(sysRestserviceServerPolicyForm.fdPolicy==null)?'0':(sysRestserviceServerPolicyForm.fdPolicy)}">
				<xform:enumsDataSource enumsType="sys_restservice_server_policy_fd_policy"></xform:enumsDataSource>
			</xform:select>
		</td>
	</tr>	
	<c:if test="${_fdPolicy=='0'}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdLoginId"/>
			</td><td width="85%" colspan="3">
				<xform:text property="fdLoginId" style="width:85%" />
			</td>
		</tr>
	</c:if>
	<c:if test="${_fdPolicy=='2'}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdHeadername"/>
			</td>
			<td width="85%" colspan="3">
				<xform:text property="fdHeadername" style="width:35%" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdSecretKey"/>
			</td>
			<td width="85%" colspan="3">
				<xform:text property="fdSecretKey" style="width:85%" />
			</td>
		</tr>
	</c:if>
		<%-- 认证方式 --%>
		<%-- <tr>					
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdAuthType"/>
			</td><td width="85%" colspan="3">			
				<xform:radio property="fdAuthType"
					value="${(sysRestserviceServerPolicyForm.fdAuthType==null)?'0':(sysRestserviceServerPolicyForm.fdAuthType)}">
					<xform:enumsDataSource enumsType="sys_restservice_server_policy_fd_auth"></xform:enumsDataSource>
				</xform:radio>
			</td>	
	    </tr> --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdAccessIp"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdAccessIp" style="width:85%" />
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdService"/>
		</td><td width="85%" colspan="3">
            <xform:checkbox property="fdServiceIds">
                <xform:beanDataSource serviceBean="sysRestserviceServerMainService"></xform:beanDataSource>
            </xform:checkbox> 	
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdDescription"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdDescription" style="width:85%" />
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.docCreator"/>
		</td><td width="35%">
			<c:out value="${sysRestserviceServerPolicyForm.docCreatorName}" />
		</td>	
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" />
		</td>		
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
