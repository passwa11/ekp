<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div style="text-align: right;">
	<div style="display:inline-block;">
		<input type="checkbox" name="_compareOrder"/>
		${lfn:message('kms-knowledge:kmsKnowledgeCompare.tab.order')}
		<span style="color:red;">${lfn:message('kms-knowledge:kmsKnowledgeCompare.tab.order.des')}</span>
	</div>	
	<ui:button text="${lfn:message('kms-knowledge:kmsKnowledgeCompare.tab.compare.do')}" onclick="_doCompare();"></ui:button>
	<ui:button text="${lfn:message('kms-knowledge:kmsKnowledgeCompare.tab.compare.refresh')}" onclick="_refreshFile();"></ui:button>
</div>
<iframe name="file_frame" style="display:none;"></iframe>

<list:listview id="listview_compare" channel="listview_compare">
	<ui:source type="AjaxJson">
		{url:"/kms/knowledge/kms_knowledge_compare/kmsKnowledgeCompare.do?method=listAllVersion&fdModelName=${HtmlParam.fdModelName}&fdModelId=${HtmlParam.fdId}"}
	</ui:source>
	<list:rowTable layout="sys.ui.listview.rowtable" name="rowtable" onRowClick="" style="" target="_blank" >
		<list:row-template>
			<c:import url="/kms/knowledge/kms_knowledge_compare/kmsKnowledgeCompare_tmpl.jsp" charEncoding="UTF-8"></c:import>
		</list:row-template>
	</list:rowTable>
</list:listview>

<c:if test="${HtmlParam.provider eq 'fagg'}">
	<kmss:ifModuleExist path="/third/fagg">
		<c:set var="_fdCompareUrl" value="/third/fagg/comparison/thirdFagg_Doc_Comparison.jsp"/>
		<c:set var="_fdCompareKeys" value="mainOnline"/>
	</kmss:ifModuleExist>
<script>
	Com_IncludeFile("thirdFagg_Doc_Comparison.js","${KMSS_Parameter_ContextPath}third/fagg/comparison/","js",true);
    var comparison = "${lfn:message('third-fagg:third.fagg.comparison')}";
	// 比对操作，法狗狗
	window._doCompare = function() {
		var fagg_${HtmlParam.fdKey}_comparison= new ThirdFagg_Doc_Comparison("${HtmlParam.fdKeys}","${HtmlParam.fdModelId}","${HtmlParam.fdModelName}","");		
		var selectedDocs = $("#listview_compare").find("input[name='List_Selected']:checked");
		if(selectedDocs.length > 2 || selectedDocs.length <= 1){
			fagg_${HtmlParam.fdKey}_comparison.dialog.alert("${lfn:message("kms-knowledge:kmsKnowledgeCompare.tab.compare.choosetwo")}");
			return;
		}
		var originId = "";
		var targetId = "";
		var ids = [];
		selectedDocs.each(function(index,item){
			ids.push($(item).parent().parent().find("input[name='sysAttMainId']").val());
		})
		originId=ids[1];
		targetId=ids[0];
		if($("input[name='_compareOrder']").is(':checked')){
			originId=ids[0];
			targetId=ids[1];
		} 
		window._loading = fagg_${HtmlParam.fdKey}_comparison.dialog.loading();
		fagg_${HtmlParam.fdKey}_comparison.compare(originId,targetId);
	}
</script>
</c:if>
<c:if test="${HtmlParam.provider eq 'intsig'}">
	<kmss:ifModuleExist path="/third/intsig">
		<c:set var="_fdCompareUrl" value="/third/intsig/comparison/third_intsig_doc_comparison.jsp"/>
		<c:set var="_fdCompareKeys" value="attachment,processDoc"/>
	</kmss:ifModuleExist>
<script>
	Com_IncludeFile("third_intsig_doc_comparison.js","${KMSS_Parameter_ContextPath}third/intsig/comparison/","js",true);

    var comparison = "${lfn:message('third-intsig:intsig.comparison')}";
	// 比对操作，合合
	window._doCompare = function() {
		var intsig_${HtmlParam.fdKey}_comparison= new third_intsig_doc_comparison("${HtmlParam.fdKey}","${HtmlParam.fdModelId}","${HtmlParam.fdModelName}");
		var selectedDocs = $("#listview_compare").find("input[name='List_Selected']:checked");
		if(selectedDocs.length > 2 || selectedDocs.length <= 1){
			intsig_${HtmlParam.fdKey}_comparison.dialog.alert('${lfn:message("kms-knowledge:kmsKnowledgeCompare.tab.compare.choosetwo")}');
			return;
		}
		var originId = "";
		var targetId = "";
		var ids = [];
		selectedDocs.each(function(index,item){
			ids.push($(item).parent().parent().find("input[name='sysAttMainId']").val());
		})
		originId=ids[1];
		targetId=ids[0];
		if($("input[name='_compareOrder']").is(':checked')){
			originId=ids[0];
			targetId=ids[1];
		} 
		window._loading = intsig_${HtmlParam.fdKey}_comparison.dialog.loading();
		intsig_${HtmlParam.fdKey}_comparison.compare(originId,targetId);
	}
</script>
</c:if>
<c:if test="${not empty _fdCompareUrl}">
	<c:import url="${_fdCompareUrl}" charEncoding="UTF-8">
	   <c:param name="fdKeys" value="${_fdCompareKeys}" />
	   <c:param name="fdModelId" value="${HtmlParam.fdModelId}" />
	   <c:param name="fdModelName" value="${HtmlParam.fdModelName}" />
	</c:import>
</c:if>
<script type="text/javascript" >
	seajs.use(['lui/jquery','lui/dialog', 'lui/topic'], function($, dialog, topic) {
		// 接收比对事件
		topic.subscribe('WebDocumentCompare', function (evt) {
			if(window._loading) {
				window._loading.hide();
			}
			if(evt && evt.success) {
				dialog.success("${lfn:message('kms-knowledge:kmsKnowledgeCompare.tab.compare.success')}");
				window._refreshFile();
			} else {
				dialog.failure("${lfn:message('kms-knowledge:kmsKnowledgeCompare.tab.compare.failure')}");
			}
		});

		// 下载原始附件
		window.downloadOriFile = function(fileId) {
			Com_OpenWindow(downloadOriFileFormAction + fileId, "_blank");
		}

		// 下载比对结果
		window.downloadResultFile = function(fileId) {
			Com_OpenWindow(downloadResultFileFormAction + fileId, "_blank");
		}

		window._refreshFile = function() {
			if(window._loading) {
				window._loading.hide();
			}
			// 刷新列表
			topic.channel('listview_compare').publish('list.refresh');
		}
	});
</script>
