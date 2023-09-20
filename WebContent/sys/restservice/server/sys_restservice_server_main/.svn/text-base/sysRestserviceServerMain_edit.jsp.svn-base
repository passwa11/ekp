<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<style>
.message{
	color: #003366; 
}
</style>
<html:form action="/sys/restservice/server/sys_restservice_server_main/sysRestserviceServerMain.do">
<div id="optBarDiv">
    <input type=button value="<bean:message key="button.update"/>"
		onclick="Com_Submit(document.sysRestserviceServerMainForm, 'update');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-restservice-server" key="table.sysRestserviceServerMain"/></p>

<center>
<table class="tb_normal" width=95%>
	<%-- 名称 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" showStatus="view" />
		</td>
	</tr>
	<%-- 路径 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdUriPrefix"/>
		</td><td width="35%" colspan="3">
			<xform:text property="fdUriPrefix" style="width:85%" showStatus="view" />
		</td>
	</tr>
	<%-- 服务标识 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdServiceName"/>
		</td><td width="35%" colspan="3">
			<xform:text property="fdServiceName" style="width:85%" showStatus="view" />
		</td>
	</tr>
	<%-- 服务接口类名 --%>
	<tr>				
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdServiceClass"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdServiceClass" style="width:85%" showStatus="view" />
		</td>
	</tr>
	<%-- 服务状态 启动类型 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdServiceStatus"/>
		</td><td width="35%">
			<c:if test="${sysRestserviceServerMainForm.fdServiceStatus == 1}"> 
                <bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdServiceStatus.start"/>
            </c:if> 
			<c:if test="${sysRestserviceServerMainForm.fdServiceStatus == 0}"> 
                <bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdServiceStatus.stop"/>
            </c:if> 						
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdStartupType"/>
		</td><td width="35%">
            <xform:radio property="fdStartupType">
				<xform:enumsDataSource enumsType="sys_restservice_server_main_fd_startup_type" />
			</xform:radio> 
		</td>
	</tr>
	<%-- 最大连接数  服务超时预警 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdMaxConn"/>
		</td><td width="35%">
			<xform:text property="fdMaxConn" style="width:25%"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdTimeOut"/>
		</td><td width="35%">
			<xform:text property="fdTimeOut" style="width:25%" />
		</td>
	</tr>
	<%-- 最大消息体长度 访问策略--%>
    <tr>
    	<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdMaxBodySize"/>
		</td><td width="35%">
			<xform:text property="fdMaxBodySize" style="width:10%" />
		</td>
	    <td class="td_normal_title" width=15%>
		    <bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdPolicy"/>
	    </td><td width="35%">
          	<xform:checkbox property="fdPolicyIds" onValueChange="_onPolicyChange">
                <xform:customizeDataSource className="com.landray.kmss.sys.restservice.server.service.spring.SysRestserviceServerPolicyDataService"></xform:customizeDataSource>
            </xform:checkbox> 	   
	    </td>	
    </tr>
	<%-- 备注 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerMain.fdDescription"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdDescription" style="width:85%" />
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
	function _onPolicyChange(){
		$("input[name='fdAnonymous']").val('false');
	}
	$KMSSValidation();
	Com_AddEventListener(window,'load',function(){
			if($("input[name='fdPolicyIds']").val()!=''){
				_onPolicyChange();
			}
		});
</script>
<%@ include file="/sys/restservice/server/sys_restservice_server_main/sysRestserviceServerMain_script.jsp"%>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
