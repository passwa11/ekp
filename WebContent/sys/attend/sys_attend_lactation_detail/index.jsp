<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-attend:module.sys.attend') }" />
			<ui:varParam name="button">
				[
					{
						"text": "${ lfn:message('sys-attend:module.sys.attend') }",
						"href": "javascript:void(0)",
						"icon": "lui_icon_l_icon_89"
					}
				]
			</ui:varParam>				
		</ui:combin>
		<div class="lui_list_nav_frame">
			 <ui:accordionpanel>
			 	<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
				   <c:param name="key" value="sysAttendLactationDetail"></c:param>
				   <c:param name="criteria" value="sysAttendLactationDetailCriteria"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${ lfn:message('sys-attend:table.sysAttendLactationDetail') }">
				<!-- 查询条件  -->
				<list:criteria id="sysAttendLactationDetailCriteria">
					<list:cri-ref key="docCreator" ref="criterion.sys.person" multi="false" title="${lfn:message('sys-attend:sysAttendLactationDetail.docCreator') }" />
					<list:cri-criterion title="${ lfn:message('sys-attend:sysAttendLactationDetail.fdType')}" key="fdType"> 
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'哺乳假期', value:'10'},
									{text:'产前工间休息', value:'6'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</list:criteria>
				<!-- 列表工具栏 -->
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
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							   <list:sort property="fdStartTime" text="${ lfn:message('sys-attend:sysAttendLactationDetail.fdStartTime') }" group="sort.list" value="down"></list:sort>
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
				 
				 
			 	<list:listview id="listview">
					<ui:source type="AjaxJson">
							{url:'/sys/attend/sys_attend_lactation_detail/sysAttendLactationDetail.do?method=list'}
					</ui:source>
					<list:colTable isDefault="false" name="columntable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="docCreator;fdDate;fdStartTime;fdEndTime;fdCountHour"></list:col-auto>
					</list:colTable>
				</list:listview> 
				 
			 	<list:paging></list:paging>	 
			</ui:content>
		</ui:tabpanel>
		
	 	
	 	<script>
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});	
				
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				window.switchAttendPage = function(url,hash){
					url = Com_SetUrlParameter(url,'j_iframe','true');
					url = Com_SetUrlParameter(url,'j_aside','false');
					if(hash){
						url = url + hash;
					}
					LUI.pageOpen(url,'_rIframe');
				}
			});
		</script>
	</template:replace>
</template:include>
