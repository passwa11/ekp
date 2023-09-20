<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=start">
		<input type="button" value="<bean:message key="button.startservice" bundle="sys-webservice2"/>"
			onclick="Com_OpenWindow('sysWebserviceMain.do?method=start&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>	
	<kmss:auth requestURL="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=stop">
		<input type="button" value="<bean:message key="button.stopservice" bundle="sys-webservice2"/>"
			onclick="Com_OpenWindow('sysWebserviceMain.do?method=stop&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>  
	<kmss:auth requestURL="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('sysWebserviceMain.do?method=edit&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=download">
		<input type="button" value="<bean:message key="button.download.client" bundle="sys-webservice2"/>"
			onclick="Com_OpenWindow('sysWebserviceMain.do?method=download&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>		
	<input type="button" 
	       value="<bean:message key="button.wsdl" bundle="sys-webservice2"/>" 
	       onclick="Com_OpenWindow('${requestScope.wsdlUrl}','_blank');">	
	<input type="button" 
	       value="<bean:message key="button.help" bundle="sys-webservice2"/>" 
	       onclick="openHelpWindow('${sysWebserviceMainForm.fdName}');">		
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
		
</div>
<script>
	function openHelpWindow(name) {
		var url = "<%=request.getContextPath()%>${sysWebserviceMainForm.fdServiceParam}?name="+name;
		url = encodeURI(url);
		Com_OpenWindow(url,'_blank');
	}
</script>
<p class="txttitle"><bean:message bundle="sys-webservice2" key="table.sysWebserviceMain"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdName"/>
		</td><td width="85%"  colspan="3">
			<xform:text property="fdName" style="width:85%" />
		</td>
	</tr>
	<tr>			
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdServiceClass"/>
		</td><td width="85%"  colspan="3">
			<xform:text property="fdServiceClass" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdServiceBean"/>
		</td><td width="35%">
			<xform:text property="fdServiceBean" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			&nbsp;
		</td><td width="35%">
			&nbsp;
		</td>			
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdServiceStatus"/>
		</td><td width="35%">		
			<c:if test="${sysWebserviceMainForm.fdServiceStatus == 1}"> 
                <bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdServiceStatus.start"/>
            </c:if> 
			<c:if test="${sysWebserviceMainForm.fdServiceStatus == 0}"> 
                <bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdServiceStatus.stop"/>
            </c:if> 			
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdStartupType"/>
		</td><td width="35%">
            <xform:radio property="fdStartupType">
				<xform:enumsDataSource enumsType="sys_webservice_main_fd_startup_type" />
			</xform:radio> 
		</td>			
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdMaxConn"/>
		</td><td width="35%">
			<xform:text property="fdMaxConn" style="width:25%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdTimeOut"/>
		</td><td width="35%">
			<xform:text property="fdTimeOut" style="width:25%" />
		</td>
	</tr>
	<tr>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdMaxBodySize"/>
		</td><td width="35%">
			<xform:text property="fdMaxBodySize" style="width:25%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdUser"/>
		</td><td width="85%">
            <xform:checkbox property="fdUserIds">
                <xform:customizeDataSource className="com.landray.kmss.sys.webservice2.service.spring.SysWebservicePolicyDataService"></xform:customizeDataSource>
            </xform:checkbox> 	 
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdSoapMsgLogging"/>
		</td><td width="85%" colspan="3">
            <xform:radio property="fdSoapMsgLogging">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>  
		</td>			
	</tr>			
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdDescription"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdDescription" style="width:85%" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/sys/webservice2/sys_webservice_main/sysWebserviceMain_script.jsp"%>
<%@ include file="/resource/jsp/view_down.jsp"%>