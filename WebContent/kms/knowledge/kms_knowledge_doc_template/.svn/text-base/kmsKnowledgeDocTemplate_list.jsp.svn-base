<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">

	<template:replace name="content">
	
		<!-- 筛选器  -->
        <list:criteria id="criteria">
            <!-- 自动过滤的内容 -->
	        <list:cri-auto modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeDocTemplate" 
	                property="docCreateTime" />
	    </list:criteria>
	    
        <!-- 操作栏 -->
		<div class="lui_list_operation">
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			
			<div class="lui_list_operation_sort_btn">
				<!-- 排序 -->
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" >
						<list:sort property="docCreateTime" text="${lfn:message('kms-knowledge:kmsKnowledgeDocTemplate.docCreateTime') }"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			
			<!-- mini分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>
		
			<!-- 操作按钮 -->
			<div style="float:right">
				<div  class="lui_table_toolbar_inner">
                    <ui:toolbar count="2">
                        <kmss:auth requestURL="/kms/knowledge/kms_knowledge_doc_template/kmsKnowledgeDocTemplate.do?method=add">
                        	<!-- 新增 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
                        </kmss:auth>
                        <kmss:auth requestURL="/kms/knowledge/kms_knowledge_doc_template/kmsKnowledgeDocTemplate.do?method=deleteall">
                        	<!-- 批量删除 -->
                        	<ui:button text="${lfn:message('button.deleteall')}" onclick="del()"></ui:button>
                        </kmss:auth>
                    </ui:toolbar>
                </div>
			</div>
		</div>
        
        <ui:fixed elem=".lui_list_operation"></ui:fixed>
        
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                    {url:'/kms/knowledge/kms_knowledge_doc_template/kmsKnowledgeDocTemplate.do?method=data&q.fdDelFlag=0'}
            </ui:source>
            
            <!-- 列表视图 -->
            <list:colTable isDefault="false"
                rowHref="/kms/knowledge/kms_knowledge_doc_template/kmsKnowledgeDocTemplate.do?method=view&fdId=!{fdId}" name="columntable">
                <list:col-checkbox></list:col-checkbox>
                <list:col-serial></list:col-serial>
                <list:col-auto props="fdName;fdOrder;docCreateTime;docCreator.fdName;"></list:col-auto>
            </list:colTable>
        </list:listview> 
        
        <list:paging></list:paging>
        
        <c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
		<c:if test="${frameShowTop=='yes' }">
		<ui:top id="top"></ui:top>
			<kmss:ifModuleExist path="/sys/help">
				<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
			</kmss:ifModuleExist>
		</c:if>
        
	</template:replace>

</template:include>	

<script type="text/javascript">
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	// 监听新建更新等成功后刷新
	topic.subscribe('successReloadPage', function() {
		topic.publish("list.refresh");
	});
 	// 增加
	window.add = function() {
		Com_OpenWindow('<c:url value="/kms/knowledge/kms_knowledge_doc_template/kmsKnowledgeDocTemplate.do" />?method=add');
	};
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
		var url  = '<c:url value="/kms/knowledge/kms_knowledge_doc_template/kmsKnowledgeDocTemplate.do?method=deleteall&fdModelName=${JsParam.fdModelName}"/>';
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
							topic.publish("list.refresh");
						}
						dialog.result(data);
					}
			   });
			}
		});
	};
});
</script>