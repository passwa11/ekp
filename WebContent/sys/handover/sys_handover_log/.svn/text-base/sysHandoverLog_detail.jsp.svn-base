<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content"> 
		<%--筛选器--%>
		<div style="margin-top:10px">
			<list:criteria id="criteria">
				<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-handover:sysHandoverConfigMain.docSubject') }">
				</list:cri-ref>
			</list:criteria>
		</div>	
		<%--数据显示--%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
						{url:'/sys/handover/sys_handover_config_main/sysHandoverConfigMain.do?method=detailLog&type=${JsParam.type}&fdMainId=${JsParam.fdMainId}&moduleName=${JsParam.moduleName}&item=${JsParam.item}&state=${JsParam.state}'}
				</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
				rowHref="!{url}"
				name="columntable">
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
		</list:listview>
		<list:paging></list:paging>
	</template:replace>
</template:include>