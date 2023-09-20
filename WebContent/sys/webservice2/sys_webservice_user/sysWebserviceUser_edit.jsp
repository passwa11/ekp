<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/webservice2/sys_webservice_user/sysWebserviceUser.do">
<div id="optBarDiv">
	<c:if test="${sysWebserviceUserForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysWebserviceUserForm, 'update');">
	</c:if>
	<c:if test="${sysWebserviceUserForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysWebserviceUserForm, 'save');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-webservice2" key="table.sysWebserviceUser"/></p>

<center>
<table class="tb_normal" width=95% id="user_table">
    <span class="txtstrong">${alertPassword}</span>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:35%" value="${(sysWebserviceUserForm.fdUserName==null)?'':(sysWebserviceUserForm.fdUserName)}"/>
		</td>
	</tr>		
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdPolicy"/>
		</td><td width="85%" colspan="3">
			<xform:radio property="fdPolicy" onValueChange="changePoricy(this.value);" 
				value="${(sysWebserviceUserForm.fdPolicy==null)?'0':(sysWebserviceUserForm.fdPolicy)}">
				<xform:enumsDataSource enumsType="sys_webservice_user_fd_policy"></xform:enumsDataSource>
			</xform:radio>
		</td>
	</tr>	
	<c:if test="${sysWebserviceUserForm.method_GET=='edit'}">
	  <tr tr_policy="group_0">	
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdLoginId"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdLoginId" style="width:35%" showStatus="view"/>
		</td>
	  </tr>			
	</c:if>	
	<c:if test="${sysWebserviceUserForm.method_GET=='add'}">
	  <tr tr_policy="group_0">	 	
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdLoginId"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdLoginId" style="width:35%" />
		</td>
	  </tr>
	  <tr tr_policy="group_0">					
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdPassword"/>
		</td><td width="85%" colspan="3">			
			<xform:text property="fdPassword" style="width:35%" />
		</td>	
	  </tr>		                
	</c:if>
	<tr tr_policy="group_0">					
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAuthType"/>
		</td><td width="85%" colspan="3">			
			<xform:radio property="fdAuthType"
				value="${(sysWebserviceUserForm.fdAuthType==null)?'0':(sysWebserviceUserForm.fdAuthType)}">
				<xform:enumsDataSource enumsType="sys_webservice_user_fd_auth"></xform:enumsDataSource>
			</xform:radio>
		</td>	
    </tr>		
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAccessIp"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdAccessIp" style="width:85%" /><br>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.fdAccessIp.desc"/>
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

	<c:if test="${sysWebserviceUserForm.method_GET=='edit'}">	
	  <tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>	
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceUser.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" showStatus="view" />
		</td>	
	  </tr>
	</c:if>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script type="text/javascript">
	var validate = $KMSSValidation();
	function changePoricy(value){
		$("#user_table tr[tr_policy]").hide();
		validate.removeElements($("#user_table tr[tr_policy]"),"required");
		if(value=='0'){//启用账号控制
			$("#user_table tr[tr_policy='group_0']").show();
			validate.addElements($("#user_table tr[tr_policy='group_0']"),"required");
		}
	}

	Com_AddEventListener(window,'load',function(){
		var selVal=$("#user_table input[name='fdPolicy']:checked").val();
		changePoricy(selVal);
	});
	
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>