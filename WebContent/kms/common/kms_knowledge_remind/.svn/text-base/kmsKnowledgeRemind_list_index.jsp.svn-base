<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="noteCategory" value="${param.noteCategory}" scope="request" />
<style type="text/css">
.logTxt {
	position: static;
	float: center;
	display: inline-block;
	cursor: pointer;
	text-decoration: underline;
	color:blue;
}

</style>
<script type="text/javascript">
	//打开新页面显示详细内容
	function openModuleInfo(moduleKey,noteCategory) {
		var url = "";
		if (moduleKey == '10') {
			if (noteCategory == '00') {
				url = "kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_list.jsp?methodName=data&orderby=docPublishTime&ordertype=down&status=40&isAllDoc=false&menuType=byStatusFailure";
			} else {
				url = "kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc_list.jsp?methodName=data&orderby=docPublishTime&ordertype=down&status=60&isAllDoc=false&menuType=byStatusFailure";
			}
		} else if(moduleKey == '20'){
			url = "kms/kem/kms_kem_base_doc/kmsKemBaseDoc_list.jsp?methodName=data&orderby=docPublishTime&ordertype=down&status=40&isAllDoc=false&menuType=byStatusFailure";
		}else if(moduleKey == '30'){
			url = "kms/iso/#j_path=%2Fall";
		}

		window.open(Com_Parameter.ContextPath + url,'_blank');
	}

	//打开新页面显示详细内容
	function openDocInfo(docId,docType) {
		var url = "";
		if (docType == '00') {
			url = "kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId=";
		} else if (docType == '10') {
			url = "kms/wiki/kms_wiki_main/kmsWikiMain.do?method=view&fdId=";
		} else if (docType == '30') {
			url = "kms/iso/kms_iso_doc/kmsIsoDoc.do?method=view&fdId=";
		} else {
			url = "kms/kem/kms_kem_main/kmsKemMain.do?method=view&fdId=";
		}
		window.open(Com_Parameter.ContextPath + url + docId,'_blank');
	}



</script>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">
		<c:if test="${noteCategory == '00'}">
			${ lfn:message('kms-common:table.kmsKnowledgeRemindInfo') }
		</c:if>
		<c:if test="${noteCategory == '10'}">
		    ${ lfn:message('kms-common:table.kmsKnowledgeRemindInfo1') }
		</c:if>
	</template:replace>
	<template:replace name="content">
		
 		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- mini分页 
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div> 
			-->
			<!-- 操作按钮 -->
 			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" >
						<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow();"></ui:button> 
			 		</ui:toolbar>
			 	</div>
			 </div>
		</div> 
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<br>
		<p class="txttitle" style="font-size: 22px;color:#3e9ece">
		<c:if test="${noteCategory == '00'}">
			<bean:message  bundle="kms-common" key="table.kmsKnowledgeRemindInfo"/>
	    </c:if>
	    <c:if test="${noteCategory == '10'}">
			<bean:message  bundle="kms-common" key="table.kmsKnowledgeRemindInfo1"/>
		</c:if>
		</p>
		<br>
		<!-- 内容列表 -->
		<list:listview >
			<ui:source type="AjaxJson">
				{url:'/kms/common/kms_knowledge_remind_info/kmsKnowledgeRemindInfo.do?method=list&moduleKey=${param.moduleKey}&batchId=${param.batchId}&userId=${param.userId}&noteCategory=${param.noteCategory}&docId=${param.docId}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="moduleKey,docSubject,docCreateTime,docExpireTime,docDescription,docRemindTime"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>

	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
		 	});
	 	</script>
	</template:replace>
</template:include>
