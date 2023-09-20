<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit"  sidebar="auto">
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<kmss:auth requestURL="/sys/unit/km_imissive_brunchleader/kmImissiveUnitBrunchLeader.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			 <ui:button text="${ lfn:message('button.edit') }" order="2" onclick="Com_OpenWindow('kmImissiveUnitBrunchLeader.do?method=edit&fdId=${param.fdId}','_self');">
			 </ui:button>
		</kmss:auth>
		<kmss:auth requestURL="/sys/unit/km_imissive_brunchleader/kmImissiveUnitBrunchLeader.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
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
    		Com_OpenWindow('kmImissiveUnitBrunchLeader.do?method=delete&fdId=${param.fdId}','_self');
    	}else{
    		return false;
	    }
    },"warn");
};
	
</script>
<p class="txttitle">分管领导设置</p>
<center>
<table class="tb_normal" width=100%>
		<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdLeader"/>
		</td>
		<td width=35%>
			<c:out value="${kmImissiveUnitBrunchLeaderForm.fdLeaderName }"></c:out>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdDept"/>
		</td>
		<td width=35%>
		   <c:out value="${deptName}"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdUnits"/>
		</td>
		<td width=85% colspan="3">
			<c:out value="${kmImissiveUnitBrunchLeaderForm.fdUnitNames }"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdOrder"/>
		</td>
		<td width=35%>
			<c:out value="${kmImissiveUnitBrunchLeaderForm.fdOrder }"></c:out>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdIsAvailable"/>
		</td>
		<td width=35%>
			<sunbor:enumsShow value="${kmImissiveUnitBrunchLeaderForm.fdIsAvailable}" enumsType="common_yesno" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.docCreateId"/>
		</td>
		<td width=35%>
			<c:out value="${kmImissiveUnitBrunchLeaderForm.docCreatorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.docCreateTime"/>
		</td>
		<td width=35%>
			<c:out value="${kmImissiveUnitBrunchLeaderForm.docCreateTime}" />
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>

</template:replace>
</template:include>

