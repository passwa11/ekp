<%@page import="com.landray.kmss.sys.restservice.client.cloud.EkpCloudConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<% request.setAttribute("cloudAccessable", EkpCloudConstants.CLOUD_ACCESSABLE); %> 
<html:form action="/sys/restservice/server/sys_restservice_server_policy/sysRestserviceServerPolicy.do">
<div id="optBarDiv">
	<c:if test="${sysRestserviceServerPolicyForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysRestserviceServerPolicyForm, 'update');">
	</c:if>
	<c:if test="${sysRestserviceServerPolicyForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysRestserviceServerPolicyForm, 'save');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-restservice-server" key="table.sysRestserviceServerPolicy"/></p>

<center>
<table class="tb_normal" width=95% id="user_table">
	<span class="txtstrong">${alertPassword}</span>
	<%-- 策略名称 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:35%"/>
		</td>
	</tr>	
    <%-- 策略模式 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdPolicy"/>
		</td><td width="85%" colspan="3">
			<xform:radio property="fdPolicy" onValueChange="changePoricy(this.value);" required="true"
				value="${(sysRestserviceServerPolicyForm.fdPolicy==null)?'0':(sysRestserviceServerPolicyForm.fdPolicy)}">
				<xform:simpleDataSource value="0"><bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdPolicy.user" /></xform:simpleDataSource>
				<xform:simpleDataSource value="1"><bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdPolicy.anonymous" /></xform:simpleDataSource>
				<c:if test="${cloudAccessable || sysRestserviceServerPolicyForm.fdPolicy == '2'}">
					<xform:simpleDataSource value="2"><bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdPolicy.ekpCloud" /></xform:simpleDataSource>
				</c:if>
			</xform:radio>
		</td>
	</tr>
    <%-- 账号访问 编辑 --%>
	<c:if test="${sysRestserviceServerPolicyForm.method_GET=='edit'}">
    <%-- 账号 --%>
	  <tr tr_policy="group_0">	
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdLoginId"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdLoginId" style="width:35%"/>
		</td>
	  </tr>			
	</c:if>
    <%-- 账号访问 新建 --%>
	<c:if test="${sysRestserviceServerPolicyForm.method_GET=='add'}">
    <%-- 账号 --%>
	  <tr tr_policy="group_0">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdLoginId"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdLoginId" style="width:35%" />
		</td>
	  </tr>
    <%-- 密码 --%>
	  <tr tr_policy="group_0">					
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdPassword"/>
		</td><td width="85%" colspan="3">			
			<xform:text property="fdPassword" style="width:35%" />
		</td>	
	  </tr>
	</c:if>
	<%-- 固定密钥访问 --%>
    <%-- Header Name --%>
	<tr tr_policy="group_2">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdHeadername"/>
		</td>
		<td width="85%" colspan="3">
			<xform:text property="fdHeadername" style="width:35%" />
		</td>
	</tr>	
    <%-- 密钥 --%>
	<tr tr_policy="group_2">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdSecretKey"/>
		</td>
		<td width="85%" colspan="3">
			<xform:text property="fdSecretKey" style="width:35%" />
		</td>
	</tr>
	<%-- 认证方式 --%>
	<%--
	<tr tr_policy="group_0">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdAuthType"/>
		</td><td width="85%" colspan="3">			
			<xform:radio property="fdAuthType"
				value="${(sysRestserviceServerPolicyForm.fdAuthType==null)?'0':(sysRestserviceServerPolicyForm.fdAuthType)}">
				<xform:enumsDataSource enumsType="sys_restservice_server_policy_fd_auth"></xform:enumsDataSource>
			</xform:radio>
		</td>	
    </tr>
    --%>	
    <%-- 允许的客户端IP地址 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdAccessIp"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdAccessIp" style="width:85%" /><br>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdAccessIp.desc"/>
		</td>
	</tr>	
    <%-- 主服务 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdService"/>
		</td><td width="85%" colspan="3">		
            <xform:checkbox property="fdServiceIds">
                <xform:beanDataSource serviceBean="sysRestserviceServerMainService"></xform:beanDataSource>
            </xform:checkbox>
		</td>
	</tr>	
    <%-- 备注 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.fdDescription"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdDescription" style="width:85%" />
		</td>
	</tr>
    <%-- 创建人 创建时间 --%>
	<c:if test="${sysRestserviceServerPolicyForm.method_GET=='edit'}">	
	  <tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>	
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerPolicy.docCreateTime"/>
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
		else if(value=='2'){//启动固定密钥访问
			$("#user_table tr[tr_policy='group_2']").show();
			validate.addElements($("#user_table tr[tr_policy='group_2']"),"required");
		}
	}

	Com_AddEventListener(window,'load',function(){
		var selVal=$("#user_table input[name='fdPolicy']:checked").val();
		changePoricy(selVal);
	});
	
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
