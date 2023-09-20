<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-portal:sysPortalMapTpl.fdName') }">
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
						<list:sort property="docCreateTime" text="${lfn:message('sys-portal:sysPortalMapTpl.docCreateTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-portal:sysPortalMapTpl.fdName') }" group="sort.list"></list:sort>
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
					<ui:toolbar id="Btntoolbar">
						<kmss:ifModuleExist path="/kms/kmaps">
							<c:set var="hasKmsKmaps" value="true"></c:set>
						</kmss:ifModuleExist>
						<c:choose>
							<c:when test="${hasKmsKmaps == true }">
								<ui:button text="${lfn:message('sys-portal:sysPortalMapTpl.function.alert') }" onclick="prompt();" order="2" ></ui:button>
							</c:when>
							<c:otherwise>
								<kmss:auth requestURL="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do?method=add">
							    	<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
								</kmss:auth>
							</c:otherwise>
						</c:choose>
						<kmss:auth requestURL="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do?method=deleteall">
						    <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:'/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do?method=list&config=${param.config}'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false"  name="columntable">
                 <list:col-checkbox></list:col-checkbox>
				 <list:col-serial></list:col-serial>
                 <list:col-auto props="fdName,docCreateTime,docCreator.name,operations" /></list:colTable>
                 <ui:event topic="list.loaded">
					Dropdown.init();
				</ui:event>
            </list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		window.prompt = function(){
		 			dialog.alert('<bean:message bundle="sys-portal" key="sysPortalMapTpl.prompt1" />' +'<br><div style="margin-top:5px;color: red;font-size: 12px;"><bean:message bundle="sys-portal" key="sysPortalMapTpl.prompt2" /><span style="color:blue;font-size:12px;"><bean:message bundle="sys-portal" key="sysPortalMapTpl.prompt3" /></span><bean:message bundle="sys-portal" key="sysPortalMapTpl.prompt4" />"<span style="color:blue;font-size:12px;"><bean:message bundle="sys-portal" key="sysPortalMapTpl.prompt5" /></span>"<bean:message bundle="sys-portal" key="sysPortalMapTpl.prompt6" />。</div>');
		 		}
		 		
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do"/>?method=add&fdType=${param['fdType']}');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do" />?method=edit&fdId=' + id);
		 		};
		 		window.deleteAll = function(id){
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
							$.post('<c:url value="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				
		 		window.deleteById = function(id){
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
							$.get('<c:url value="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do?method=delete"/>',
									$.param({"fdId":id},true),delCallback,'json');
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
		 	});
	 	</script>
	</template:replace>
</template:include>
