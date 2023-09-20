<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:criteria expand="false" channel="main">
	<c:if test="${empty TA}">
		<c:set value="ta" var="TA"/>
	</c:if>		
	<!-- 全文点评（搜索范围） --> 
	<list:cri-criterion title="${lfn:message('sys-evaluation:sysEvaluation.search.range')}" key="fdmodelName" multi="false" >
		<list:box-select>
			<list:item-select>
				<ui:source type="AjaxJson">
					{"url":"/sys/evaluation/personal/sysEvaluationMain_other_area_main.jsp"} 
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
</list:criteria>

<div class="lui_list_operation">
	<div class="lui_list_operation_order_text">${lfn:message('sys-evaluation:sysEvaluation.order')}：</div>
	<%--排序按钮  --%>
	<div class="lui_list_operation_order_btn">
		<ui:toolbar layout="sys.ui.toolbar.sort" channel="main">
			<list:sortgroup>
				<list:sort property="fdEvaluationTime" text="${lfn:message('sys-evaluation:sysEvaluationMain.fdEvaluationTime')}" group="sort.list" ></list:sort>
				<list:sort property="fdEvaluationScore" text="${lfn:message('sys-evaluation:sysEvaluationMain.fdEvaluationScore')}" group="sort.list"></list:sort>
			</list:sortgroup>
		</ui:toolbar>
	</div>
	
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top" channel="main">
		</list:paging>
	</div>
</div>

<ui:fixed elem=".lui_list_operation"></ui:fixed>


<list:listview id="main_overView" channel="main">
	<ui:source type="AjaxJson">
		{url:'/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=listOverView&orderby=fdEvaluationTime&ordertype=down&userId=${userId}&rowsize=8'}
	</ui:source>
	<%--列表形式--%>
	<list:colTable layout="sys.ui.listview.columntable" name="columntable" rowHref="">
			<%@ include file="/sys/evaluation/import/sysEvaluationMain_col_tmpl.jsp"  %>
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
<list:paging channel="main"></list:paging>

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
