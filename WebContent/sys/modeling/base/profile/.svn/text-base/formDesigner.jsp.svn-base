<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<!-- 已废弃 -->
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<div style="padding: 5px 10px;">
			<!-- 筛选器 -->
			<list:criteria id="criteria1" multi="false">
				<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="标题"></list:cri-ref>
			</list:criteria>
			
			<!-- 操作栏 -->
			<div class="lui_list_operation">
				<!-- mini分页 -->
				<div style="float:left;">	
					<list:paging layout="sys.ui.paging.top" > 		
					</list:paging>
				</div>
				<!-- 操作按钮 -->
				<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar id="btntoolbar" count="5">
							<!-- 新建 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="2" ></ui:button>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
			<!-- 内容列表 -->
			<list:listview>
				<ui:source type="AjaxJson">
					{url:'/sys/modeling/base/modelingAppMechanism.do?method=list'}
				</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
					rowHref="/sys/modeling/base/modelingAppMechanism.do?method=view&fdId=!{fdId}">
					<list:col-checkbox></list:col-checkbox>
					<list:col-auto></list:col-auto>
				</list:colTable>
			</list:listview>
			<br>
			<!-- 分页 -->
		 	<list:paging/>
		 	<script>
			 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			 		window.add = function(){
			 			var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppMechanism.do?method=add";
			 			Com_OpenWindow(url,"_blank");
			 		}
			 		
			 		 // 编辑
			 		window.edit = function(id) {
				 		if(id)
			 				Com_OpenWindow('<c:url value="/sys/modeling/base/modelingAppMechanism.do" />?method=edit&fdId=' + id);
			 		};
			 		
			 		window.deleteDoc = function(id){
			 			var url = '<c:url value="/sys/modeling/base/modelingAppMechanism.do?method=delete"/>';
						dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
							if(value==true){
								window.del_load = dialog.loading();
								$.ajax({
									url: url,
									type: 'GET',
									data:{fdId:id},
									dataType: 'json',
									error: function(data){
										if(window.del_load!=null){
											window.del_load.hide(); 
										}
										dialog.result(data.responseJSON);
									},
									success: delCallback
							   });
							}
						});
			 		};
			 		
			 		window.delCallback = function(data){
						if(window.del_load!=null){
							window.del_load.hide();
							topic.publish("list.refresh");
						}
						dialog.result(data);
					};
			 	})
		 		
		 	</script>
	 	</div>
	</template:replace>
</template:include>
