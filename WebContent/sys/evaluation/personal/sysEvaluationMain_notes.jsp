<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="lui_list_operation">
	<div class="lui_list_operation_order_text">${lfn:message('sys-evaluation:sysEvaluation.order')}：</div>
	<%--排序按钮  --%>
	<div class="lui_list_operation_order_btn">
		<ui:toolbar layout="sys.ui.toolbar.sort" channel="notes">
			<list:sortgroup>
				<list:sort property="fdEvaluationTime" text="${lfn:message('sys-evaluation:sysEvaluationMain.fdEvaluationTime')}" group="sort.list" ></list:sort>
			</list:sortgroup>
		</ui:toolbar>
	</div>
	
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top" channel="notes">
		</list:paging>
	</div>
</div>

<ui:fixed elem=".lui_list_operation"></ui:fixed>

<list:listview id="notes_overView" channel="notes">
	<ui:source type="AjaxJson">
		{url:'/sys/evaluation/sys_evaluation_main/sysEvaluationNotes.do?method=listOverView&orderby=fdEvaluationTime&ordertype=down&userId=${userId}&rowsize=8'}
	</ui:source>
	<%--列表形式--%>
	<list:colTable layout="sys.ui.listview.columntable" name="columntable" rowHref="">
		<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"></list:col-serial>
		<list:col-html title="${lfn:message('sys-evaluation:sysEvaluation.evalDoc') }" style="width:15%;text-align:left;padding:0 8px">
			var json = {};
			json['evalId'] = row['fdId'];
			json['modelId'] = row['fdModelId'];
			json['modelName'] = row['fdModelName'];
			if(!LUI("notes_overView").evalDocSubject){
				LUI("notes_overView").evalDocSubject = [];
			}
			LUI("notes_overView").evalDocSubject.push(JSON.stringify(json));
									
			{$
				{%row['evalDocSubject']%}
			$}
		</list:col-html>
		<list:col-auto props="fdEvaluatorName;fdEvaluationTime;"></list:col-auto>
		<list:col-html title="${lfn:message('sys-evaluation:sysEvaluation.evalContent')}" style="width:20%;text-align:left;padding:0 8px">
			{$
				<span title="{%row['fdEvaluationContent']%}">
					{% strutil.textEllipsis(row['fdEvaluationContent'], 100) %}
				</span>
			$}
		</list:col-html>
		<list:col-html title="${lfn:message('sys-evaluation:sysEvaluationNotes.fragment')}" style="width:20%;text-align:left;padding:0 8px">
			{$
				<span title="{%row['docSubject']%}">
					{% strutil.textEllipsis(row['docSubject'], 100) %}
				</span>
			$}
		</list:col-html>
		<list:col-auto props="docPraiseCount;fdReplyCount"></list:col-auto>
	</list:colTable>
	<ui:event topic="list.loaded" args="vt"> 
		var evalDocSubject = LUI("notes_overView").evalDocSubject;
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
<list:paging channel="notes"></list:paging>

<script>
//查看被点评的文档
function viewDoc(fdModelId,fdModelName){
	LUI.$.ajax({
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
}
</script>
