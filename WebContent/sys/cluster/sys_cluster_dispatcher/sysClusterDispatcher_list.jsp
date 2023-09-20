<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script>
function onServerClick(value, items){
	if(!${dynamicEnabled}){
		return;
	}
	var target = Com_GetEventObject();
	target = target.target || target.srcElement;
	value = target.value;
	for(var i=0; i<items.length; i++){
		var item = items[i];
		if(item.value==value){
			continue;
		}
		if(isSameDynamicServer(item.value,value) && item.checked!=target.checked){
			item.click();
		}
	}
}
function isSameDynamicServer(server1, server2) {
	var index = server1.length;
	if (index < 3 || index != server2.length) {
		return false;
	}
	index = index - 3;
	if (server1.substring(0, index)!=server2.substring(0, index)) {
		return false;
	}
	for (var i = 0; i < 3; i++) {
		var c = server1.charAt(index + i);
		if (c < '0' || c > '9') {
			return false;
		}
		c = server2.charAt(index + i);
		if (c < '0' || c > '9') {
			return false;
		}
	}
	return true;
}
function submitForm(){
	var dispatchers = document.getElementsByName("fdDispatchers");
	outloop:
	for(var i=0; i<dispatchers.length; i++){
		var servers = document.getElementsByName("fdServer."+dispatchers[i].value);
		if(servers.length>0){
			if(servers[0].type=="radio"){
				for(var j=0; j<servers.length; j++){
					if(servers[j].checked && servers[j].value!=""){
						continue outloop;
					}
				}
			}else{
				if(servers[0].value!=""){
					continue outloop;
				}
			}
		}
		alert('<kmss:message key="errors.required" argKey0="sys-cluster:sysClusterDispatcher.server" />');
		return;
	}
	if(confirm('<bean:message bundle="sys-cluster" key="sysClusterDispatcher.submit"/>'))
		Com_Submit(document.forms[0], 'update');
}
</script>
<style type="text/css">
	.serversTD {font-size: 1px;}
	.serversTD label {width:120px; display:inline-block; line-height: 25px;font-size:12px;}
	.serversTD label input {margin-right: 2px;}
</style>
<form method="post" action="<c:url value="/sys/cluster/sys_cluster_dispatcher/sysClusterDispatcher.do" />">

<div id="optBarDiv">
	<input type=button value="<bean:message key="button.update"/>"
		onclick="submitForm();">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-cluster" key="sysClusterDispatcher.setting"/></p>
<center>
<table class="tb_normal" style="width:900px;">
	<tr class="tr_normal_title">
		<td width="25%">
			<bean:message bundle="sys-cluster" key="sysClusterDispatcher.dispatcher"/>
		</td>
		<td width="75%">
			<bean:message bundle="sys-cluster" key="sysClusterDispatcher.server"/>
		</td>
	</tr>
<xform:config isLoadDataDict="false">
<c:forEach items="${dispatcherList}" var="dispatcher">
	<tr>
		<td>
			<c:if test="${dispatcher.local=='true'}"><b style="color: red;"></c:if>
			<c:if test="${dispatcher.multi=='true'}">★</c:if>
			<c:if test="${dispatcher.multi!='true'}">☆</c:if>
			<c:out value="${dispatcher.name}" />
			<c:if test="${dispatcher.local=='true'}"></b></c:if>
			<xform:text property="fdDispatchers" value="${dispatcher.id}" showStatus="noShow"/>
		</td>
		<td class="serversTD"> 
			<xform:checkbox property="fdServer.${dispatcher.id}" value="${dispatcher.server}" showStatus="edit" alignment="H" onValueChange="onServerClick">
				<xform:customizeDataSource className="com.landray.kmss.sys.cluster.interfaces.ServerDataSource" />
			</xform:checkbox>
		</td>
	</tr>
</c:forEach>
	<tr>
		<td>
			<bean:message bundle='sys-cluster' key='sysClusterDispatcher.instruction'/>
		</td>
		<td> 
			<bean:message bundle='sys-cluster' key='sysClusterDispatcher.instruction.detail' arg0='${localServerKey}'/>
		</td>
	</tr>
</xform:config>
</table>
</center>
</form>
<%@ include file="/resource/jsp/edit_down.jsp"%>