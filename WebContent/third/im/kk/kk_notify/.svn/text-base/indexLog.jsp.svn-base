<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
<template:replace name="content">
		<div style="padding: 15px">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		    <list:cri-ref key="fdSearchName" ref="criterion.sys.docSubject" title="${lfn:message('third-im-kk:kkNotifyLog.fdSubject') }">
			</list:cri-ref>
			<!-- 时间筛选 -->
			<%-- 
			<list:cri-ref key="sendTime" ref="criterion.sys.calendar" title="${lfn:message('third-im-kk:kkNotifyLog.sendTime') }">
				<list:varParams type="CriterionDateTimeDatas"></list:varParams>
			</list:cri-ref>
			<list:cri-ref key="fdDate" ref="criterion.sys.calendar" title="${lfn:message('km-imeeting:kmImeetingResUse.fdDate') }">
				<list:varParams type="CriterionDateTimeDatas"></list:varParams>
			</list:cri-ref>
			--%>
			<list:cri-auto modelName="com.landray.kmss.third.im.kk.model.KkNotifyLog" property="sendTime" />
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
							<list:sort property="sendTime" text="${lfn:message('third-im-kk:kkNotifyLog.sendTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="rtnTime" text="${lfn:message('third-im-kk:kkNotifyLog.rtnTime') }" group="sort.list" ></list:sort>
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
						<kmss:auth requestURL="/third/im/kk/kkNotifyLog.do?method=deleteall">
						    <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
		<ui:source type="AjaxJson">
				{url:'/third/im/kk/kkNotifyLog.do?method=list&fdId=${fdId}'}
		</ui:source>
			<list:colTable isDefault="true" 
			rowHref="/third/im/kk/kkNotifyLog.do?method=view&fdId=!{fdId}"
			layout="sys.ui.listview.columntable" >
				<list:col-checkbox></list:col-checkbox>	
				<list:col-auto props="fdSubject,sendTime,rtnTime,operations"></list:col-auto>			
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	</div>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				 topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
			 	// 增加
		 		/* window.add = function() {
		 			Com_OpenWindow('<c:url value="/third/im/kk/kkNotifyLog.do" />?method=add');
		 		}; */
		 		//编辑
		 		/* window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/third/im/kk/kkNotifyLog.do" />?method=edit&fdId=' + id);
		 		}; */
		 		// 删除
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
							$.post('<c:url value="/third/im/kk/kkNotifyLog.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
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