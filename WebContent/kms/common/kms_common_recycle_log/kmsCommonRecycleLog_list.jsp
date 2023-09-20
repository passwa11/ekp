<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器  -->
        <list:criteria id="criteria">
            <!-- 自动过滤的内容 -->
	        <list:cri-auto modelName="com.landray.kmss.kms.common.model.KmsCommonRecycleLog" 
	        			property="fdOperateName;fdOperator;fdOperateTime" />
	    </list:criteria>
        <!-- 操作栏 -->
		<div class="lui_list_operation">
			<%--排序按钮  --%>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
				<ui:toolbar layout="sys.ui.toolbar.sort" >
					<list:sort property="fdOperateTime" text="${lfn:message('kms-common:kmsCommonRecycleLog.fdOperateTime')}"></list:sort>
				</ui:toolbar>
				</div>
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
				<ui:toolbar layout="sys.ui.toolbar.sort" >
					<list:sort property="fdOperator.fdName" text="${lfn:message('kms-common:kmsCommonRecycleLog.fdOperatorName')}"></list:sort>
				</ui:toolbar>
				</div>
			</div>
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div  class="lui_table_toolbar_inner">
                    <ui:toolbar count="2">
                       	<!-- 批量删除 -->
                       	<kmss:auth requestURL="/kms/common/kms_common_recycle_log/kmsCommonRecycleLog.do?method=deleteall">
                        	<ui:button text="${lfn:message('button.deleteall')}" onclick="del()"></ui:button>
						</kmss:auth>
                    </ui:toolbar>
                </div>
			</div>
		</div>
        <ui:fixed elem=".lui_list_operation"></ui:fixed>
        
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                    {url:'/kms/common/kms_common_recycle_log/kmsCommonRecycleLog.do?method=data&q.fdDelFlag=0'}
            </ui:source>
            
            <!-- 列表视图 -->
            <list:colTable isDefault="false"
                rowHref="/kms/common/kms_common_recycle_log/kmsCommonRecycleLog.do?method=view&fdId=!{fdId}" name="columntable">
                <list:col-checkbox></list:col-checkbox>
                <list:col-serial></list:col-serial>
                <list:col-auto props="fdOperateName;operateDocSubject;fdOperateTime;fdOperator.fdName;"></list:col-auto>
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
		var url  = '<c:url value="/kms/common/kms_common_recycle_log/kmsCommonRecycleLog.do?method=deleteall&fdModelName=${JsParam.fdModelName}"/>';
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