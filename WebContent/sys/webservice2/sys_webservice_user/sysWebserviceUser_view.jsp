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
<c:set var="_fdPolicy" value="${(sysWebserviceUserForm.fdPolicy==null)?'0':(sysWebserviceUserForm.fdPolicy)}"/>
<div id="optBarDiv">
	<c:if test="${_fdPolicy=='0'}">
		<kmss:auth requestURL="/sys/webservice2/sys_webservice_user/sysWebserviceUser.do?method=editPassword&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message bundle="sys-webservice2" key="button.password"/>"
				onclick="Com_OpenWindow('sysWebserviceUser.do?method=editPassword&fdId=${JsParam.fdId}','_blank');">
		</kmss:auth>
	</c:if>
	<kmss:auth requestURL="/sys/webservice2/sys_webservice_user/sysWebserviceUser.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('sysWebserviceUser.do?method=edit&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/webservice2/sys_webservice_user/sysWebserviceUser.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('sysWebserviceUser.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>	
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-webservice2" key="table.sysWebserviceUser"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:35%" value="${(sysWebserviceUserForm.fdName==null)?(sysWebserviceUserForm.fdUserName):(sysWebserviceUserForm.fdName)}"/>
		</td>
	</tr>		
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdPolicy"/>
		</td><td width="85%" colspan="3">
			<xform:radio property="fdPolicy"
				value="${(sysWebserviceUserForm.fdPolicy==null)?'0':(sysWebserviceUserForm.fdPolicy)}">
				<xform:enumsDataSource enumsType="sys_webservice_user_fd_policy"></xform:enumsDataSource>
			</xform:radio>
		</td>
	</tr>	
	<c:if test="${_fdPolicy=='0'}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdLoginId"/>
			</td><td width="85%" colspan="3">
				<xform:text property="fdLoginId" style="width:85%" />
			</td>
		</tr>
		<tr>					
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAuthType"/>
			</td><td width="85%" colspan="3">			
				<xform:radio property="fdAuthType"
					value="${(sysWebserviceUserForm.fdAuthType==null)?'0':(sysWebserviceUserForm.fdAuthType)}">
					<xform:enumsDataSource enumsType="sys_webservice_user_fd_auth"></xform:enumsDataSource>
				</xform:radio>
			</td>	
	    </tr>		
	</c:if>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAccessIp"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdAccessIp" style="width:85%" />
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdService"/>
		</td><td width="85%" colspan="3">
            <xform:checkbox property="fdServiceIds">
                <xform:beanDataSource serviceBean="sysWebserviceMainService"></xform:beanDataSource>
            </xform:checkbox> 	
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdDescription"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdDescription" style="width:85%" />
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.docCreator"/>
		</td><td width="35%">
			<c:out value="${sysWebserviceUserForm.docCreatorName}" />
		</td>	
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" />
		</td>		
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>