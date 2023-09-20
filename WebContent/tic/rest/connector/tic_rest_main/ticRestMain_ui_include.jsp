<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/tic/core/tic_ui_list.jsp">
	<%-- 右边框内容 --%>
	<template:replace name="content">
		<%-- 筛选器 --%>
		<list:criteria>
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${ lfn:message('tic-rest-connector:ticRestMain.docSubject') }">
			</list:cri-ref>
			<list:cri-ref ref="criterion.sys.simpleCategory" key="fdCategory" multi="false" title="分类导航" expand="true">
			  <list:varParams modelName="${JsParam.modelName}"/>
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.tic.rest.connector.model.TicRestMain" property="fdIsAvailable"/>
		</list:criteria>
		
		<%-- 显示列表按钮行 --%>
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float: right">
				<div style="display: inline-block; vertical-align: middle;">
						<ui:toolbar>
							<kmss:auth requestURL="/tic/rest/connector/tic_rest_main/ticRestMain.do?method=add">
								<ui:button text="${lfn:message('button.add')}" onclick="addDocByCate();" order="2" ></ui:button>
							</kmss:auth>
							<kmss:auth requestURL="/tic/rest/connector/tic_rest_main/ticRestMain.do?method=deleteall">
								<ui:button text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc('${LUI_ContextPath}/tic/rest/connector/tic_rest_main/ticRestMain.do?method=deleteall')"></ui:button>
							</kmss:auth>
						</ui:toolbar>						
					</div>
				</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/tic/rest/connector/tic_rest_main/ticRestMainIndex.do?method=list&categoryId=${param.categoryId }&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}'}
			</ui:source>
			<%--列表形式--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/tic/rest/connector/tic_rest_main/ticRestMain.do?method=edit&fdId=!{fdId}&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>   
		</list:listview> 
		<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) {
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				setTimeout(function(){
						topic.publish('list.refresh');
				}, 100);
			});
			
			var cateId = "${param.categoryId}";
			window.addDocByCate = function(){
				window.open(Com_Parameter.ContextPath+"tic/rest/connector/tic_rest_main/ticRestMain.do?method=add&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}");
			};

			//根据筛选器分类异步校验权限
			topic.subscribe('criteria.changed',function(evt){
				//每次都重置分类id的值,因为可能存在直接点叉清除分类筛选项
				cateId = parent.getCateId(evt, cateId);
			});
		 	//删除
	 		window.delDoc = function(url){
	 			var values = [];
				$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
					if(value==true){
						window.del_load = dialog.loading();
						$.post('<c:url value="'+ url +'"/>',
								$.param({"List_Selected":values},true),delCallback,'json');
					}
				});
			};
			window.delCallback = function(data){
				if(window.del_load!=null) {
					window.del_load.hide();
				}
				if(data!=null && data.status==true){
					topic.publish("list.refresh");
					dialog.success('<bean:message key="return.optSuccess" />');
				}else{
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			};
	 	});
		</script>
		<br>
	 	<list:paging></list:paging>
		<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	</template:replace>
</template:include>