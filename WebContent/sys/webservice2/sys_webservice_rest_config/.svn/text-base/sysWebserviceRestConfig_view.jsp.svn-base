<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
			<kmss:auth requestURL="/sys/webservice2/sys_webservice_rest_config/sysWebserviceRestConfig.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('sysWebserviceRestConfig.do?method=edit&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/webservice2/sys_webservice_rest_config/sysWebserviceRestConfig.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.delete') }" onclick="if(!confirmDelete())return;Com_OpenWindow('sysWebserviceRestConfig.do?method=delete&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<p class="txttitle"><bean:message bundle="sys-webservice2" key="table.sysWebserviceRestConfig"/></p>

<center>
<table class="tb_normal" id="Label_Tabel" width=95%>

	<tr LKS_LabelName='${ lfn:message("config.baseinfo") }'>
		<td>
			<table class="tb_normal" width=100%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdOrder"/>
		</td><td width="35%">
			<xform:text property="fdOrder" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdPrefix"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdPrefix" style="width:35%"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdDes"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdDes" style="width:85%" />
		</td>
	</tr>
	<c:if test="${not empty sysWebserviceRestConfigForm.docCreatorName}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docCreator"/>
		</td><td width="35%">
			<c:out value="${sysWebserviceRestConfigForm.docCreatorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" />
		</td>
	</tr>
	</c:if>
	<c:if test="${not empty sysWebserviceRestConfigForm.docAlterorName}">
	<tr>

		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docAlteror"/>
		</td><td width="35%">
			<c:out value="${sysWebserviceRestConfigForm.docAlterorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docAlterTime"/>
		</td><td width="35%">
			<xform:datetime property="docAlterTime" />
		</td>		
	</tr>
	</c:if>
	
		<tr>
			<%-- 文档类型列表 --%>
			<td colspan="4" class="td_normal_title" width=100%>
				<table class="tb_normal" width="100%">
					<tr>
						<td class="td_normal_title" width="30px;"> 
							<bean:message key="page.serial" />
						</td>
						<td class="td_normal_title">
							<bean:message bundle="sys-webservice2" key="sysWebserviceDictConfig.fdName"/>
						</td>
					</tr>
					<c:forEach items="${sysWebserviceRestConfigForm.fdDictItems}" var="sysWebserviceDictConfigForm" varStatus="vstatus">
						<tr>
							<td>${vstatus.index+1}</td>
							<td><c:out value="${sysWebserviceDictConfigForm.fdName}" /></td>
						</tr>
					</c:forEach>
					</table>
			</td>
		</tr>
			</table>
		</td>
	</tr>
</table>
</center>

	</template:replace>
</template:include>