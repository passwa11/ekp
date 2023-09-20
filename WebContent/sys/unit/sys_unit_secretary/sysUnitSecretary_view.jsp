<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit"  sidebar="auto">
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<kmss:auth requestURL="/sys/unit/sys_unit_secretary/sysUnitSecretary.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			 <ui:button text="${ lfn:message('button.edit') }" order="2" onclick="Com_OpenWindow('sysUnitSecretary.do?method=edit&fdId=${param.fdId}','_self');">
			 </ui:button>
		</kmss:auth>
		<kmss:auth requestURL="/sys/unit/sys_unit_secretary/sysUnitSecretary.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			 <ui:button text="${ lfn:message('button.delete') }" order="2" onclick="Delete();">
			 </ui:button>
		</kmss:auth>
		<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
		 </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">
<script>
seajs.use(['sys/ui/js/dialog'], function(dialog) {
	window.dialog=dialog;
});
function Delete(){
	dialog.confirm("${lfn:message('page.comfirmDelete')}",function(flag){
    	if(flag==true){
    		Com_OpenWindow('sysUnitSecretary.do?method=delete&fdId=${param.fdId}','_self');
    	}else{
    		return false;
	    }
    },"warn");
};
	
</script>
<p class="txttitle"><bean:message bundle="sys-unit" key="table.sysUnitSecretary"/></p> 
<center>
<table class="tb_normal" width=100%>
		<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdPerson"/>
		</td>
		<td width=35%>
			<c:out value="${sysUnitSecretaryForm.fdPersonName }"></c:out>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdDept"/>
		</td>
		<td width=35%>
		   <c:out value="${deptName}"></c:out>
		</td>
	</tr>

	<%--<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdSupervisePerson"/>
		</td>
		<td width=35%>
			<c:out value="${sysUnitSecretaryForm.fdSupervisePersonName }"></c:out>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdDept"/>
		</td>
		<td width=35%>
			<c:out value="${supervisePersonDeptName}"></c:out>
		</td>
	</tr>--%>
	<tr>
		<td class="td_normal_title" width=15% >
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdBelongUnit"/>
		</td>
		<td width=85% colspan="3">
		   <c:out value="${sysUnitSecretaryForm.fdBelongUnitName}"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdUnits"/>
		</td>
		<td width=85% colspan="3">
			<c:out value="${sysUnitSecretaryForm.fdUnitNames }"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="sysUnitSecretary.fdContent"/>
		</td><td width=85% colspan='3'>
			<c:out value="${sysUnitSecretaryForm.fdContent }"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdOrder"/>
		</td>
		<td width=35%>
			<c:out value="${sysUnitSecretaryForm.fdOrder }"></c:out>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdIsAvailable"/>
		</td>
		<td width=35%>
			<sunbor:enumsShow value="${sysUnitSecretaryForm.fdIsAvailable}" enumsType="common_yesno" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.docCreateId"/>
		</td>
		<td width=35%>
			<c:out value="${sysUnitSecretaryForm.docCreatorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.docCreateTime"/>
		</td>
		<td width=35%>
			<c:out value="${sysUnitSecretaryForm.docCreateTime}" />
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>

</template:replace>
</template:include>

