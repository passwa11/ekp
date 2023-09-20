<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">  
	 <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
	 <script type="text/javascript">
		seajs.use(['theme!list', 'theme!portal']);	
	 </script>
	 <div style="padding: 10px">
	  <div style="color: red; padding-bottom: 5px;">
	  	${lfn:message('sys-handover:sysHandoverConfigMain.handoverType.tip') }
	  	<c:if test="${'doc' eq JsParam.type}">
	  	${lfn:message('sys-handover:sysHandoverConfigMain.handoverType.doc.desc') }
	  	</c:if>
	  	<c:if test="${'auth' eq JsParam.type}">
	  	${lfn:message('sys-handover:sysHandoverConfigMain.handoverType.auth.desc') }
	  	</c:if>
	  	<c:if test="${'config' eq JsParam.type}">
	  	${lfn:message('sys-handover:sysHandoverConfigMain.handoverType.config.desc') }
	  	</c:if>
	  	<c:if test="${'item' eq JsParam.type}">
	  	${lfn:message('sys-handover:sysHandoverConfigMain.handoverType.item.desc') }
	  	</c:if>
	  </div>
	  <%-- 筛选器 --%>	
	  <list:criteria id="criteria1" multi="false">
		    <!--交接人,接收人 -->
		    <list:cri-ref ref="criterion.sys.person" key="fdFromId" title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }"></list:cri-ref>
		    <list:cri-ref ref="criterion.sys.person" key="fdToId" title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }"></list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.sys.handover.model.SysHandoverConfigMain" property="docCreator;fdState"/>
	  </list:criteria>
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" >
					<list:sortgroup>
						<list:sort property="fdFromName" text="${lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }" group="sort.list"></list:sort>
						<list:sort property="fdToName" text="${lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }" group="sort.list"></list:sort>
						<list:sort property="docCreator.fdName" text="${lfn:message('sys-handover:sysHandoverConfigMain.docCreatorId') }" group="sort.list"></list:sort>
						<list:sort property="docCreateTime" text="${lfn:message('sys-handover:sysHandoverConfigMain.docCreateTime') }" group="sort.list"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
	  		<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="3" >
					   <kmss:authShow roles="ROLE_SYSHANDOVER_CREATE">
						   <ui:button id="add" text="${lfn:message('button.add')}" onclick="addDoc()" order="1"></ui:button>	
					   </kmss:authShow>
					   <kmss:authShow roles="ROLE_SYSHANDOVER_MAINTAIN">
						   <ui:button id="delete" text="${lfn:message('button.deleteall')}" onclick="delDoc()" order="2"></ui:button>	
					   </kmss:authShow>
					   </ui:toolbar>
				</div>
			</div>
		</div>
	    <ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/handover/sys_handover_config_main/sysHandoverConfigMain.do?method=list&type=${JsParam.type}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/handover/sys_handover_config_main/sysHandoverConfigMain.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdFromName;fdToName;fdState;docCreatorName;docCreateTime;operations"></list:col-auto>
			</list:colTable>
		</list:listview> 
	 	<list:paging></list:paging>	 
	 	<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic, toolbar) {
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});

				//新建
				window.addDoc = function() {
					Com_OpenWindow('<c:url value="/sys/handover/sys_handover_config_main/sysHandoverConfigMain.do" />?method=add&type=${JsParam.type}');
				};
				
				//删除
				window.delDoc = function(id){
					var values = [];
					if(id) {
						values.push(id);
					} else {
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
							$.post('<c:url value="/sys/handover/sys_handover_config_main/sysHandoverConfigMain.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				//删除回调	
				window.delCallback = function(data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success('<bean:message key="return.optSuccess" />');
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				};
				
			});
     	</script>	 
     </div>
	</template:replace>
</template:include>
