<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-lbpmservice-support:table.lbpmEmbeddedSubFlow') }</template:replace>
	<template:replace name="content">
		<%-- 筛选器 --%>
		<list:criteria>
			<list:tab-criterion title="" key="fdType">
				<list:box-select>
					<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-defaultValue="current" cfg-required="true">
						<ui:source type="Static">
							[{text:'当前引用', value:'current'},
							{text:'所有引用',value:'all'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:tab-criterion>
		</list:criteria>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=listRef&fdEmbeddedId=${JsParam.fdEmbeddedId}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=viewTemp&fdProcessTemplateId=!{fdProcessTemplateId}&fdProcessDefinitionId=!{fdProcessDefinitionId}&fdType=!{fdType}">
				<list:col-serial></list:col-serial>
				<list:col-auto props="subject,fdVersion,fdNodeId,fdCreatorName,fdCreateTime,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
		<style>
			.criteria-selected-wapper{
				display: none;
			}
		</style>
		<script type="text/javascript">
			seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
				window.editParam = function(fdProcessTemplateId,fdNodeId,nodeParams) {
					var content = $("textarea[name='fdContent']",parent.document).val();
					var url='/sys/lbpmservice/support/lbpm_embeddedSubFlow/lbpmEmbeddedSubFlow_param.jsp';
					var param = {
						fdProcessTemplateId:fdProcessTemplateId,
						fdNodeId:fdNodeId,
						content:content,
						nodeParams:nodeParams
					};
					parent.Com_Parameter.Dialog = param;
					dialog.iframe(url,"${ lfn:message('sys-lbpmservice-support:lbpm.embeddedsubflow.paramsConfig')}",function(rtn){
						if(rtn){
							topic.publish("list.refresh");
						}
					},{width:500,height:400,close:false,params:param});
				};
			});
		</script>
	</template:replace>
</template:include>