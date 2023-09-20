<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
	th.width30{
		width: 60px;
	}
</style>
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			 <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-auth:sysModelingAuth.ProcessName')}">
			</list:cri-ref>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>-->
			<!-- 分割线 
			<div class="lui_list_operation_line"></div>-->
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sortgroup>
							<list:sort property="docCreateTime" text="${lfn:message('sys-modeling-base:kmReviewTemplate.docCreateTime') }" group="sort.list"></list:sort>
							<list:sort property="fdName" text="${lfn:message('sys-modeling-auth:sysModelingAuth.ProcessName')}" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
			<!-- 内容列表 -->
			<list:listview>
				<ui:source type="AjaxJson">
					{url:'/sys/modeling/base/modelingAppFlow.do?method=list&fdModelId=${JsParam.fdAppModelId}&forward=xformAuthList'}
				</ui:source>
				<list:colTable isDefault="false" layout="sys.ui.listview.columntable">
					<!--<list:col-checkbox></list:col-checkbox>-->
					<list:col-serial></list:col-serial>
					<list:col-auto props="fdName,fdValid,docCreator.fdName,docCreateTime,operations"></list:col-auto>
				</list:colTable>
				<ui:event topic="list.loaded">
					Dropdown.init();
				</ui:event>
			</list:listview>
		<!-- 分页 -->
		</div>
		<list:paging/>
	
	<script type="text/javascript">

	seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
		window.doSetting = function(fdAppFlowId){
			var url='/sys/modeling/auth/xform_auth/sysModelingXformRightConfig.do?method=load&fdAppFlowId=' + fdAppFlowId;
			dialog.iframe(url,'${lfn:message("sys-modeling-auth:sysModelingAuth.FormPermissionSetting")}',null,{
				width : 870,
				height : 500
			});
		}
		
	}); 
	</script>
