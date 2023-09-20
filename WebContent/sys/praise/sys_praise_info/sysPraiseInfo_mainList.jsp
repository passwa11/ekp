<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
<template:replace name="content">
<list:criteria id="criteria1" multi="false">
	<list:cri-criterion title="${lfn:message('sys-praise:sysPraiseInfo.fdType') }" key="fdType" multi="false">
		<list:box-select>
			<list:item-select >
				<ui:source type="Static">
					[{text:"${lfn:message('sys-praise:sysPraiseInfo.fdType.praise') }", value: '1'},
					{text:"${lfn:message('sys-praise:sysPraiseInfo.fdType.rich') }", value:'2'},
					{text:"${lfn:message('sys-praise:sysPraiseInfo.fdType.unPraise') }", value:'3'}]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	<list:cri-auto modelName="com.landray.kmss.sys.praise.model.SysPraiseInfo" property="fdPraisePerson" />
	<list:cri-auto modelName="com.landray.kmss.sys.praise.model.SysPraiseInfo" property="fdTargetPerson" />
</list:criteria>
<div class="lui_list_operation" style="padding: 1px 0px 1px 0;">
	<!-- 全选 -->
	<div class="lui_list_operation_order_btn" style="margin-left:10px;">
		<list:selectall></list:selectall>
	</div>
	<div style="float:right">
		<div style="display: inline-block;vertical-align: middle;">
			<ui:toolbar count="4" >
			<kmss:authShow roles="ROLE_SYSPRAISEINFO_MAINTAINER">
				<c:import url="/sys/simplecategory/import/doc_cate_change_button.jsp" charEncoding="UTF-8">
					<c:param name="modelName" value="com.landray.kmss.sys.praise.model.SysPraiseInfo" />
					<c:param name="docFkName" value="docCategory" />
					<c:param name="cateModelName" value="com.landray.kmss.sys.praise.model.SysPraiseInfoCategory" />
				</c:import>
			</kmss:authShow>
			</ui:toolbar>
		</div>
	</div>
</div>
<list:listview id="listview">
	<ui:source type="AjaxJson">
		{url:'/sys/praise/sys_praise_info/sysPraiseInfo.do?method=data&fdModelId=${param.fdModelId}&categoryId=${param.categoryId}'}
	</ui:source>
	<list:colTable name="columntable" rowHref="/sys/praise/sys_praise_info/sysPraiseInfo.do?method=view&fdId=!{fdId}">
	<list:col-checkbox name="List_Selected" style="width:5%"/>
	<list:col-serial />
	<list:col-auto props="fdTargetPersonName;fdRich;docCategoryName"></list:col-auto>
	<list:col-html title="${lfn:message('sys-praise:sysPraiseInfo.fdReason')}" style="width:45%;padding:0 8px">
		{$
			<span class="com_subject">{%row['fdReason']%}</span> 
		$}
	</list:col-html>
	<list:col-auto props="fdPraisePersonName;fdType;docCreateTime"></list:col-auto>
	</list:colTable>
</list:listview> 
<list:paging></list:paging>
</template:replace>
</template:include>