<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="status" value="${param.status}" scope="request" />
<template:include file="/sys/profile/resource/template/list.jsp">

	<template:replace name="content">
	
		<!-- 筛选器  -->
        <list:criteria id="criteria">
            <!-- 自动过滤的内容 -->
	        <list:cri-auto modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" 
	                property="docCreateTime" />
	                
	         <c:if test="${status == '40'}">
	                <list:cri-auto modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" 
	                               property="docExpireTime" />
	         </c:if>
	         <c:if test="${status == '60'}">
	               <list:cri-auto modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" 
	                              property="docFailureTime" />
	         </c:if>
	    </list:criteria>
	    
        <!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序按钮 --> 
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" >
						<list:sort property="docCreateTime" text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCreateTime') }"></list:sort>
						<c:if test="${status == '40'}">
							<list:sort property="docExpireTime" text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docExpireTime') }"></list:sort>
						</c:if>
						<c:if test="${status == '60'}">
							<list:sort property="docFailureTime" text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docFailureTime') }"></list:sort>
						</c:if>
					</ui:toolbar>
				</div>
			</div>
			
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		</div>
        
        <ui:fixed elem=".lui_list_operation"></ui:fixed>
        
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                    {url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=${JsParam.methodName}&q.fdDelFlag=0&categoryId=${JsParam.categoryId}&status=${JsParam.status}&forwordPage=data&categoryId=${categoryId}&orderby=${JsParam.orderby}&ordertype=${JsParam.ordertype}&isAllDoc=${JsParam.isAllDoc}&menuType=${JsParam.menuType}'}
            </ui:source>
            <!-- 列表视图 -->
            <list:colTable isDefault="false"
                rowHref="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}" name="columntable">
                <list:col-serial></list:col-serial>
                <c:if test="${status == '40'}">
                	<list:col-auto props="docSubject;docCreator.fdName;docCreateTime;docExpireTime;docCategory.fdName;"></list:col-auto>
                </c:if>
                <c:if test="${status == '60'}">
	                <list:col-html>
		                {$
							{%row['icon']%}
							<span class="com_subject">{%row['docSubject']%}</span>
						$}
	                </list:col-html>
                	<list:col-auto props="docCreator.fdName;docCreateTime;docFailureTime;docCategory.fdName;"></list:col-auto>
                </c:if>
                
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
 	// 恢复
	window.recoverAll = function(id) {
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
		var html =['<div style="padding: 10px;">',
					'<div style="margin: 5px 0 10px;">${lfn:message("kms-knowledge:kmsKnowledge.reason.fill")}</div>',
					'<table class="lui_reason_text" width="95%"><tr><td>',
						'<textarea rows  style="width:100%;height:150px; border:1px solid #b4b4b4; overflow-y: auto;vertical-align: top;" validate="required maxLength(800)"></textarea><span class="txtstrong">*</span>',
					'</td></tr></table>',
				'</div>'].join(" ");
		dialog.build(
			{
				id:'recoverDialog',
				config: {
					height: 300,
					width: 600,
					lock: true,
					title: "${lfn:message('kms-knowledge:kmsKnowledge.dialog.recover')}",
					content: {
						type: "html",
						html: html,
						buttons :  [{
							name : "${lfn:message('sys-ui:ui.dialog.button.ok')}",
							value : true,
							focus : true,
							fn : function(value, _dialog) {
								var validator = $KMSSValidation($('.lui_reason_text')[0]);
								if(!validator.validate())
									return;
								var reason = $('.lui_reason_text textarea').val(),
									loading = dialog.loading();
								_dialog.hide(value);
								var ids = values;
								$.ajax(
									{
									    type: 'post',
										url: "${ LUI_ContextPath}/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=recoverall&fdId=${JsParam.fdId}",
										data: {reason: reason, ids: ids.join(";")} ,
										async : false,
										success: function(data) {
											loading.hide();
											if(data.flag) {
												dialog.success("${lfn:message('return.optSuccess')}");
												setTimeout(function(){window.location.reload();}, 500);
											}
											else 	dialog.failure("${lfn:message('return.optFailure')}");
										}
									}
								);
							}
						}, {
							name : "${lfn:message('sys-ui:ui.dialog.button.cancel')}",
							value : false,
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						}]
					}
				},
				callback:function() {
					
				}
			}
		).show();
	};
	// 彻底删除
	window.deleteAll = function(id) {
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
		var url  = '<c:url value="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=deleteall&fdModelName=${JsParam.fdModelName}"/>';
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
<script>
	Com_IncludeFile('jquery.js|plugin.js');
</script>
