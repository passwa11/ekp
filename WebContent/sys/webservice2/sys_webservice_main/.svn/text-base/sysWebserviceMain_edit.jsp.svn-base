<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<style>
.message{
	color: #003366; 
}
</style>
<html:form action="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do">
<div id="optBarDiv">
    <input type=button value="<bean:message key="button.update"/>"
		onclick="Com_Submit(document.sysWebserviceMainForm, 'update');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-webservice2" key="table.sysWebserviceMain"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" showStatus="view" />
		</td>
	</tr>
	<tr>				
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdServiceClass"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdServiceClass" style="width:85%" showStatus="view" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdServiceBean"/>
		</td><td width="35%">
			<xform:text property="fdServiceBean" style="width:85%" showStatus="view" />
		</td>
		<td class="td_normal_title" width=15%>
			&nbsp;
		</td><td width="35%">
			&nbsp;<html:hidden property="fdAnonymous" value="${sysWebserviceMainForm.fdAnonymous}"/>
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
			<c:set var="_defaultConn" value="1000"/>
			<c:if test="${ sysWebserviceMainForm.fdMaxConn!=null && sysWebserviceMainForm.fdMaxConn!=''}">
				<c:set var="_defaultConn" value="${ sysWebserviceMainForm.fdMaxConn}"/>
			</c:if>
			<xform:text property="fdMaxConn" style="width:25%" value="${_defaultConn}"/>
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
          	<xform:checkbox property="fdUserIds" onValueChange="_onPolicyChange">
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
			<span class="message"><bean:message bundle="sys-webservice2" key="sysWebserviceMain.fdSoapMsgLogging.tip"/></span>
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
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
	function _onPolicyChange(){
		$("input[name='fdAnonymous']").val('false');
	}
	$KMSSValidation();
	Com_AddEventListener(window,'load',function(){
			if($("input[name='fdUserIds']").val()!=''){
				_onPolicyChange();
			}
		});
</script>
<%@ include file="/sys/webservice2/sys_webservice_main/sysWebserviceMain_script.jsp"%>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>