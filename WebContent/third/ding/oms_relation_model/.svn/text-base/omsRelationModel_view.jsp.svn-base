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
			<kmss:auth requestURL="/third/ding/oms_relation_model/omsRelationModel.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('omsRelationModel.do?method=edit&fdId=${param.fdId}&type=${param.type }','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/third/ding/oms_relation_model/omsRelationModel.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.delete') }" onclick="if(!confirmDelete())return;Com_OpenWindow('omsRelationModel.do?method=delete&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<br><p class="txttitle"><bean:message bundle="third-ding" key="table.omsRelationModel"/></p><br>

<center>
<table class="tb_normal" width=95%>
	<c:if test="${param.type=='dept' }">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId.org"/>
			</td><td width="35%">
				<xform:text property="fdEkpName" style="width:85%" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId.Id.org"/>
			</td><td width="35%">
				<xform:text property="fdEkpId" style="width:85%" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId.org"/>
			</td><td width="35%">
				<xform:text property="fdAppPkId" style="width:85%" />
			</td>
			<td class="td_normal_title" width=15%>
			</td><td width="35%"></td>
		</tr>
	</c:if>
	<c:if test="${param.type=='person' }">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId"/>
			</td><td width="35%">
				<xform:text property="fdEkpName" style="width:85%" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-ding" key="omsRelationModel.fdEkpLoginName"/>
			</td><td width="35%">
				<xform:text property="fdEkpLoginName" style="width:85%" />
			</td>
		 </tr>
		 <tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId"/>
			</td><td width="35%">
				<xform:text property="fdAppPkId" style="width:85%" />
			</td>
			<td class="td_normal_title" width=15%>
				UnionId
			</td>
			 <td width="35%">
				 <xform:text property="fdUnionId" style="width:85%" />
			 </td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-ding" key="omsRelationModel.fdAccountType"/>
			</td>
			<td width="35%">
				<%--<xform:text property="fdAccountType" style="width:85%" />--%>
				<c:if test="${omsRelationModelForm.fdAccountType=='common'}">
					普通账号
				</c:if>
				<c:if test="${omsRelationModelForm.fdAccountType=='dingtalk' }">
					专属账号(dingtalk)
				</c:if>
				<c:if test="${omsRelationModelForm.fdAccountType=='sso' }">
					专属账号(sso)
				</c:if>
		    </td>
		</tr>
	</c:if>
</table>
</center>

	</template:replace>
</template:include>