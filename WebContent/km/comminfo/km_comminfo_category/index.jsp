<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-comminfo:kmComminfoCategory.fdName') }">
			</list:cri-ref>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
			  <list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
			    	${ lfn:message('list.orderType') }：
			  	</div>
			  	<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					    <list:sortgroup>
						    <list:sort property="fdOrder" text="${lfn:message('km-comminfo:kmComminfoMain.fdOrder') }" group="sort.list" value="up"></list:sort>
							<list:sort property="docCreateTime" text="${lfn:message('km-comminfo:kmComminfoCategory.docCreateTime') }" group="sort.list"></list:sort>
							<list:sort property="fdName" text="${lfn:message('km-comminfo:kmComminfoCategory.fdName') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar">
					    <kmss:auth requestURL="/km/comminfo/km_comminfo_category/kmComminfoCategory.do?method=add" requestMethod="GET">
					        <ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<%-- <kmss:auth requestURL="/km/comminfo/km_comminfo_category/kmComminfoCategory.do?method=transfer">
						    <ui:button text="${lfn:message('km-comminfo:button.change')}" onclick="selectCategory();" order="3" ></ui:button>
						</kmss:auth> --%>
						<kmss:auth requestURL="/km/comminfo/km_comminfo_category/kmComminfoCategory.do?method=deleteall" requestMethod="GET">
						    <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.comminfo.model.KmComminfoCategory"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/km/comminfo/km_comminfo_category/kmComminfoCategory.do?method=list&modelName=${JsParam.modelName}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/km/comminfo/km_comminfo_category/kmComminfoCategory.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				
				<list:col-auto props="fdOrder,fdName,docCreator.fdName,docCreateTime,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<script type="text/javascript">
			 	
		</script>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/km/comminfo/km_comminfo_category/kmComminfoCategory.do" />?method=add');
		 		};
		 	// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/km/comminfo/km_comminfo_category/kmComminfoCategory.do" />?method=edit&fdId=' + id);
		 		};
		 		window.deleteAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
					    $("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							var url = '<c:url value="/km/comminfo/km_comminfo_category/kmComminfoCategory.do?method=deleteall"/>';
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
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

				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				window.selectCategory =function (){
					var cBox=document.getElementsByName("List_Selected");
					var cateIds="";
					
					for(var i=0;i<cBox.length;i++){
						if(!(cBox[i].checked)) continue;
						if(cateIds=="") cateIds=cBox[i].value;
						else cateIds+=";"+cBox[i].value;
					}
					
					if(cateIds=="" || cateIds==null) 
					{
						dialog.alert('<bean:message  bundle="km-comminfo" key="kmComminfoCategory.change.select"/>');
						return;
					}
					
					dialog.tree('kmComminfoCategoryTreeService&parentId=!{value}&cateIds='+cateIds,'<bean:message  bundle="km-comminfo" key="kmComminfoCategory.fdId"/>',afterSelectCategory);  
					//Dialog_Tree(false,null,null,null,'kmComminfoCategoryTreeService&parentId=!{value}&cateIds='+cateIds,
					//		'<bean:message  bundle="km-comminfo" key="kmComminfoCategory.fdId"/>',null,afterSelectCategory,null); 
					
				};
			
				window.afterSelectCategory = function(rtnVal){
					if(rtnVal!=null&&rtnVal.value!=null){
						var fdTemplateId=rtnVal.value;
						var values = [];
					    $("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
						
					    window.del_load = dialog.loading();
			           setTimeout(function(){
			        	   $.post('<c:url value="/km/comminfo/km_comminfo_category/kmComminfoCategory.do?method=transfer"/>',
									$.param({"cateIds":values,"cateId":fdTemplateId},true),delCallback,'json');
			           }, 100);
					} 
				};
		 	});
	 	</script>
	</template:replace>
</template:include>
