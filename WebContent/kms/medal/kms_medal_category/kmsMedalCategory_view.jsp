<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-medal:table.kmsMedalCategory') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<script>
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			window.confirmDelete=function(){
				
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						
						if(value==true){
							Com_OpenWindow('kmsMedalCategory.do?method=deleteCate&modelName=com.landray.kmss.kms.medal.model.KmsMedalCategory&fdId=${param.fdId}','_self');
						}
				})
					}})
			
		</script>
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
		<kmss:auth requestURL="/kms/medal/kms_medal_category/kmsMedalCategory.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('kmsMedalCategory.do?method=edit&fdId=${param.fdId}','_self');">
			</ui:button>
		</kmss:auth>
		<kmss:auth requestURL="/kms/medal/kms_medal_category/kmsMedalCategory.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<ui:button text="${ lfn:message('button.delete') }" onclick="confirmDelete()">
			</ui:button>
		</kmss:auth>
		<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
	</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<p class="txttitle"><bean:message bundle="kms-medal" key="table.kmsMedalCategory"/></p>

<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.fdOrder"/>
		</td><td width="35%">
			<xform:text property="fdOrder" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.authDefenders"/>
		</td><td width="35%" colspan="3">
			<c:out value="${kmsMedalCategoryForm.authDefenderNames}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.fdDescription"/>
		</td><td width="35%" colspan="3">
			<xform:textarea property="fdDescription" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.docCreator"/>
		</td><td width="35%">
			<c:out value="${kmsMedalCategoryForm.docCreatorName}" />
		</td>	
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" />
		</td>
	</tr>
	<c:if test="${not empty kmsMedalCategoryForm.docAlterorId}">	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.docAlteror"/>
		</td><td width="35%">
			<c:out value="${kmsMedalCategoryForm.docAlterorName}" />
		</td>		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.docAlterTime"/>
		</td><td width="35%">
			<xform:datetime property="docAlterTime" />
		</td>
	</tr>
	</c:if>	
</table>
	</template:replace>
</template:include>
