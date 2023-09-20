<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
<template:replace name="toolbar">
<script>
function confirmDelete(){
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
		var url = '<c:url value="/sys/bookmark/sys_bookmark_public_category/sysBookmarkPublicCategory.do" />?method=hasSubCategory';
		dialog.confirm("<bean:message key="page.comfirmDelete"/>",function(val) {
			if(val) {
				$.get(url,{selects:'${JsParam.fdId}'},function(rtnVal) {
					if(rtnVal=='true') {
						dialog.alert('<bean:message bundle="sys-bookmark" key="sysBookmarkCategory.delete.alert"/>');
					}else {
						Com_OpenWindow('sysBookmarkPublicCategory.do?method=delete&fdId=${JsParam.fdId}','_self');
					}
				});
			}
		});
	});
}
</script>

<kmss:windowTitle
	subject="${sysBookmarkPublicCategoryForm.fdName}"
	moduleKey="sys-bookmark:table.sysBookmarkPublicCategory" />

	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
		<kmss:auth requestURL="/sys/bookmark/sys_bookmark_public_category/sysBookmarkPublicCategory.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('sysBookmarkPublicCategory.do?method=edit&fdId=${JsParam.fdId}','_self');" order="1"></ui:button>
		</kmss:auth>
		<kmss:auth requestURL="/sys/bookmark/sys_bookmark_public_category/sysBookmarkPublicCategory.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<ui:button text="${lfn:message('button.delete')}"  onclick="confirmDelete();" order="3" ></ui:button>
		</kmss:auth>
		<ui:button text="${lfn:message('button.close')}"  onclick="Com_CloseWindow();" order="5" ></ui:button>
    </ui:toolbar>
</template:replace>
<template:replace name="content">
<p class="txttitle"><bean:message bundle="sys-bookmark" key="table.sysBookmarkPublicCategory"/></p>
<center>
<table class="tb_normal" width=95%>
		<html:hidden name="sysBookmarkPublicCategoryForm" property="fdId"/>
	<%-- 分类名称 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-bookmark" key="sysBookmarkPublicCategory.fdName"/>
		</td>
		<td colspan="3">
			<c:out value="${sysBookmarkPublicCategoryForm.fdName}" />
		</td>
	</tr>
	<%-- 所属类别 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-bookmark" key="sysBookmarkPublicCategory.fdParentId"/>
		</td>
		<td colspan="3">
			<c:out value="${sysBookmarkPublicCategoryForm.fdParentName}" />
		</td>
	</tr>
	<%-- 排序号 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-bookmark" key="sysBookmarkPublicCategory.fdOrder"/>
		</td>
		<td colspan="3">
			<c:out value="${sysBookmarkPublicCategoryForm.fdOrder}" />
		</td>
	</tr>
	<tr>
	<%-- 创建人 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-bookmark" key="sysBookmarkPublicCategory.docCreatorId"/>
		</td>
		<td width="35%">
			<c:out value="${sysBookmarkPublicCategoryForm.docCreatorName}" />
		</td>
	<%-- 创建时间 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-bookmark" key="sysBookmarkPublicCategory.docCreateTime"/>
		</td width="35%">
		<td>
			<c:out value="${sysBookmarkPublicCategoryForm.docCreateTime}" />
		</td>
	</tr>
	<tr>
	<%-- 修改人 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-bookmark" key="sysBookmarkPublicCategory.docAlterorId"/>
		</td>
		<td width="35%">
			<c:out value="${sysBookmarkPublicCategoryForm.docAlterorName}" />
		</td>
	<%-- 修改时间 --%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-bookmark" key="sysBookmarkPublicCategory.docAlterTime"/>
		</td>
		<td width="35%">
			<c:out value="${sysBookmarkPublicCategoryForm.docAlterTime}" />
		</td>
	</tr>
</table>
<p>&nbsp;</p>
</center>
</template:replace>
</template:include>
