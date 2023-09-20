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
			<kmss:auth requestURL="/third/weixin/mutil/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('wxworkOmsRelation.do?method=edit&fdId=${param.fdId}&type=${param.type }','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/third/weixin/mutil/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.delete') }" onclick="if(!confirmDelete())return;Com_OpenWindow('wxworkOmsRelation.do?method=delete&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<br><p class="txttitle"><bean:message bundle="third-weixin-work" key="table.wxOmsRelation"/></p><br>

<center>
<table class="tb_normal" width=95%>
	<c:if test="${param.type=='dept' }">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-weixin-mutil" key="wxOmsRelation.fdEkpId.org"/>
			</td><td width="35%">
				<xform:text property="fdEkpName" style="width:85%" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-weixin-mutil" key="wxOmsRelation.fdEkpId.Id.org"/>
			</td><td width="35%">
				<xform:text property="fdEkpId" style="width:85%" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-weixin-mutil" key="wxOmsRelation.fdAppPkId.org"/>
			</td><td width="35%">
				<xform:text property="fdAppPkId" style="width:85%" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-weixin-mutil" key="thirdWxWorkConfig.fdKey.title"/>
			</td><td width="35%">
				<xform:text property="fdWxKey" style="width:85%" onValueChange="checkThird"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${param.type=='person' }">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-weixin-mutil" key="wxOmsRelation.fdEkpId"/>
			</td><td width="35%">
				<xform:text property="fdEkpName" style="width:85%" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-weixin-mutil" key="wxOmsRelation.fdEkpLoginName"/>
			</td><td width="35%">
				<xform:text property="fdEkpLoginName" style="width:85%" />
			</td>
		 </tr>
		 <tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-weixin-mutil" key="wxOmsRelation.fdAppPkId"/>
			</td><td width="35%">
				<xform:text property="fdAppPkId" style="width:85%" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="third-weixin-mutil" key="thirdWxWorkConfig.fdKey.title"/>
			</td><td width="35%">
				<xform:text property="fdWxKey" style="width:85%" onValueChange="checkThird"/>
			</td>
		</tr>
	</c:if>
</table>
</center>

	</template:replace>
</template:include>