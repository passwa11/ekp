<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.sys.evaluation.service.ISysEvaluationMainService"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${lfn:message('sys-evaluation:sysEvaluationMain.overView') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1" channel="main">
			<list:cri-ref key="fdEvaluationContent" ref="criterion.sys.docSubject" title="${lfn:message('sys-evaluation:sysEvaluation.evalContent') }"></list:cri-ref>
			<list:cri-criterion title="${lfn:message('sys-evaluation:sysEvaluationMain.modelName') }" key="fdModelName"> 
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas">
						<ui:source type="AjaxJson">
							{url:'/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=getAppModules'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
		</list:criteria>
		
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall channel="main"></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left"  channel="main">
					<list:sortgroup>
						<list:sort property="fdEvaluationTime" text="${ lfn:message('sys-evaluation:sysEvaluationMain.fdEvaluationTime')}" group="sort.list" value="down"></list:sort>
						<list:sort property="docPraiseCount" text="${ lfn:message('sys-evaluation:sysEvaluation.praise.count')}"></list:sort>
						<list:sort property="fdReplyCount" text="${ lfn:message('sys-evaluation:sysEvaluationMain.fdReplyCount')}"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" channel="main"> 		
				</list:paging >
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="5">
						<kmss:auth requestURL="/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=deleteall" requestMethod="POST">
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.delete')}" onclick="del()" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview id="main_overView" channel="main">
			<ui:source type="AjaxJson">
				{url:'/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=listOverView&fdModelName=${JsParam.fdModelName}'}
			</ui:source>
				<%--列表形式--%>
			<list:colTable layout="sys.ui.listview.columntable" name="columntable"
					rowHref="">
					<list:col-checkbox></list:col-checkbox>
					<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"></list:col-serial>
					<list:col-html title="${lfn:message('sys-evaluation:sysEvaluation.evalDoc') }" style="width:20%;text-align:left;padding:0 8px">
						var json = {};
						json['evalId'] = row['fdId'];
						json['modelId'] = row['fdModelId'];
						json['modelName'] = row['fdModelName'];
						if(!LUI("main_overView").evalDocSubject){
							LUI("main_overView").evalDocSubject = [];
						}
						LUI("main_overView").evalDocSubject.push(JSON.stringify(json));
												
						{$
							{%row['evalDocSubject']%}
						$}
					</list:col-html>
					<list:col-auto props="fdEvaluatorName;fdEvaluationTime;"></list:col-auto>
					<list:col-html title="${lfn:message('sys-evaluation:sysEvaluation.evalContent')}" style="width:25%;text-align:left;padding:0 8px">
						{$	
							<span title="{%row['fdEvaluationContent']%}">
								{% strutil.textEllipsis(row['fdEvaluationContent'], 100) %}
							</span>
						$}
					</list:col-html>
					<list:col-auto props="fdEvaluationScore;docPraiseCount;fdReplyCount"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded" args="vt"> 
				var evalDocSubject = LUI("main_overView").evalDocSubject;
				if(evalDocSubject){
					var evalModels = evalDocSubject.join(";");
					LUI.$.ajax({
						url: Com_Parameter.ContextPath + 'sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=getEvalDocNames',
						type: 'POST',
						dataType: 'json',
						async : false,
						data: {
								'evalModels':evalModels
								},
						success: function(data) {
							var docSubjects = data["docSubjects"];
							for(var i=0;i<docSubjects.length;i++){
								var evalId = docSubjects[i]['evalId'];
								var docModelId = docSubjects[i]['docModelId'];
								var docModelName = docSubjects[i]['docModelName'];
								var docSubject = docSubjects[i]['docSubject'];
								docSubject=docSubject.replace(/</g,"&lt;").replace(/>/g,"&gt;");
								var _comSubject = '<span class="com_subject" onclick="viewDoc(\'' + docModelId 
													+ '\',\'' + docModelName + '\')" style="cursor:pointer">' + docSubject + '</span>';
								var _parent = $("#"+evalId).parent();
								_parent.empty();
								_parent.append(_comSubject);
							}
							LUI("main_overView").evalDocSubject = [];
						},
						error: function() {
						}
					});
				}
			</ui:event>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging channel="main"/>
	 	<script type="text/javascript">
		 	seajs.use( 	['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
		 		// 删除
		 		window.del = function(id) {
		 			var values = [];
		 			if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url  = '<c:url value="/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=deleteall"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide(); 
										topic.channel("main").publish("list.refresh");
									}
									dialog.result(data);
								}
						   });
						}
					});
				};
				
				//查看被点评的文档
				window.viewDoc = function(fdModelId,fdModelName){
					$.ajax({
						url: Com_Parameter.ContextPath + 'sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=getEvalDocUrl',
						type: 'POST',
						dataType: 'json',
						async : false,
						data: {'fdModelId':fdModelId,
								'fdModelName':fdModelName},
						success: function(data) {
							window.open(data['docUrl'], "_blank");
						},
						error: function() {
						}
					});
				};
		 	});
	 	</script>
	</template:replace>
</template:include>
