<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器  -->
        <list:criteria id="criteria">
	         <list:cri-ref key="fdName" ref="criterion.sys.docSubject">
			</list:cri-ref>
	    </list:criteria>
	    
	   
        <!-- 操作栏 -->
		<div class="lui_list_operation">
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<%--排序按钮  --%>
			<div class="lui_list_operation_order_text">
				<ui:toolbar layout="sys.ui.toolbar.sort" >
					<list:sort property="docCreateTime" text="${lfn:message('kms-common:kmsHomeKnowledgeIntro.docCreateTime') }" value="down"></list:sort>
				</ui:toolbar>
			</div>
			<div  class="lui_list_operation_page_top">
                <list:paging layout="sys.ui.paging.top" ></list:paging>
            </div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div  class="lui_table_toolbar_inner">
                    <ui:toolbar count="3">
                        <kmss:auth requestURL="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do?method=add&categoryId=${HtmlParam.categoryId}">
                        	<!-- 新增 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
                        </kmss:auth>
                        <kmss:auth requestURL="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do?method=deleteall&categoryId=${HtmlParam.categoryId}">
                        	<!-- 批量删除 -->
                        	<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteall()"></ui:button>
                        </kmss:auth>
                    </ui:toolbar>
                </div>
			</div>
		</div>
        
        <ui:fixed elem=".lui_list_operation"></ui:fixed>
        
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                    {url:'/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do?method=${JsParam.method}&categoryId=${JsParam.categoryId}&forword=data'}
            </ui:source>
            
            <!-- 列表视图 -->
            <list:colTable isDefault="false" name="columntable">
                <list:col-checkbox></list:col-checkbox>
                <list:col-serial></list:col-serial>
                <list:col-auto props="fdName;docCreator.fdName;docCreateTime;operations;"></list:col-auto>
            </list:colTable>
        </list:listview> 
        
        <list:paging></list:paging>
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
		Com_OpenWindow('<c:url value="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do" />?method=add&docCategoryId=${JsParam.categoryId}');
	};
 	// 编辑
	window.edit = function(id) {
	    Com_OpenWindow('<c:url value="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do" />?method=edit&fdId='+id);
	};
	// 删除
	window.del = function(id) {
		if(!id) {
			return;
		}
		var url  = '<c:url value="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do?method=delete&fdId=' + id + '&fdModelName=${JsParam.fdModelName}"/>';
		dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
			if(value == true) {
				window.del_load = dialog.loading();
				$.ajax({
					url : url,
					type : 'GET',
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
	// 批量删除
	window.deleteall = function(id) {
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
		var url  = '<c:url value="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do?method=deleteall&fdModelName=${JsParam.fdModelName}&categoryId=${JsParam.categoryId}"/>';
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