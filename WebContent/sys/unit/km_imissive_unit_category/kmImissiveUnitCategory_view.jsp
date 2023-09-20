<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view"  sidebar="auto">
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<kmss:auth requestURL="/sys/unit/km_imissive_unit_category/kmImissiveUnitCategory.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			 <ui:button text="${ lfn:message('button.edit') }" order="2" onclick="Com_OpenWindow('kmImissiveUnitCategory.do?method=edit&fdId=${param.fdId}','_self');">
			 </ui:button>
		</kmss:auth>
		<kmss:auth requestURL="/sys/unit/km_imissive_unit_category/kmImissiveUnitCategory.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
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
    		Com_OpenWindow('kmImissiveUnitCategory.do?method=delete&fdId=${param.fdId}','_self');
    	}else{
    		return false;
	    }
    },"warn");
};
	
</script>
<p class="txttitle"><bean:message  bundle="sys-unit" key="table.kmImissiveUnitCategory"/></p>
<center>
<table class="tb_normal" width=100%>
		<html:hidden name="kmImissiveUnitCategoryForm" property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitCategory.fdParentName"/>
		</td><td width=85% colspan="3">
		    <c:out value="${kmImissiveUnitCategoryForm.fdParentName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnitCategory.fdName"/>
		</td><td width=35% colspan='3'>
			<c:out value="${kmImissiveUnitCategoryForm.fdName}" />
		</td>

	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnitCategory.fdOrder"/>
		</td><td width=85% colspan="3">
			<c:out value="${kmImissiveUnitCategoryForm.fdOrder}" />
		</td>
		<!-- 
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnitCategory.fdIsAvailable"/>
		</td><td width=35%>
			<sunbor:enumsShow value="${kmImissiveUnitCategoryForm.fdIsAvailable}" enumsType="common_yesno" />
		</td>
		 -->
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitCategory.docCreateId"/>
		</td><td width=35%>
			<c:out value="${kmImissiveUnitCategoryForm.docCreatorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnitCategory.docCreateTime"/>
		</td><td width=35%>
			<c:out value="${kmImissiveUnitCategoryForm.docCreateTime}" />
		</td>
	</tr>
</table>
</center>
</template:replace>
</template:include>