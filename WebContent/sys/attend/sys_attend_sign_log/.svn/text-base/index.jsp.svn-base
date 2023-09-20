<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-attend:table.sysAttendSignLog') }</template:replace>
	<template:replace name="content">
		<ui:tabpanel>
			<ui:content title='${_info2}'>
				<list:criteria channel="sysAttendSignLog">
					<list:cri-ref ref="criterion.sys.person" key="fdOperatorId" title="${ lfn:message('sys-attend:sysAttendSignLog.docCreator') }"></list:cri-ref>
					<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendSignLog" property="docCreateTime" />
				</list:criteria>
				<!-- 操作栏 -->
				<div class="lui_list_operation">
					<!-- 分割线 -->
					<div class="lui_list_operation_line"></div>
					<!-- 排序 -->
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
								${ lfn:message('list.orderType') }：
						</div>
						<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysAttendSignLog">
								<list:sortgroup>
									<list:sort channel="sysAttendSignLog" property="docCreateTime" text="${lfn:message('sys-attend:sysAttendSignLog.docCreateTime') }" group="sort.list" value="down"></list:sort>
								</list:sortgroup>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top" channel="sysAttendSignLog">
						</list:paging>
					</div>
				</div>

				<!-- 内容列表 -->
				<list:listview id="sysAttendSignLog" channel="sysAttendSignLog">
					<ui:source type="AjaxJson">
						{url:'/sys/attend/sys_attend_sign_log/sysAttendSignLog.do?method=list'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable"  channel="sysAttendSignLog">
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdOperator,fdOperatorDept,docCreateTime,fdSignType,fdAddress"></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
						Dropdown.init();
					</ui:event>
				</list:listview>
				<br>
				<!-- 分页 -->
				<list:paging channel="sysAttendSignLog"/>

			</ui:content>
			<ui:content title='${_info1}'>
				<list:criteria channel="sysAttendSignBak">
					<list:cri-ref ref="criterion.sys.person" key="fdOperatorId" title="${ lfn:message('sys-attend:sysAttendSignLog.docCreator') }"></list:cri-ref>
					<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendSignBak" property="docCreateTime" />
				</list:criteria>
				<!-- 操作栏 -->
				<div class="lui_list_operation">
					<!-- 分割线 -->
					<div class="lui_list_operation_line"></div>
					<!-- 排序 -->
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
								${ lfn:message('list.orderType') }：
						</div>
						<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysAttendSignBak">
								<list:sortgroup>
									<list:sort channel="sysAttendSignBak" property="docCreateTime" text="${lfn:message('sys-attend:sysAttendSignLog.docCreateTime') }" group="sort.list" value="down"></list:sort>
								</list:sortgroup>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top" channel="sysAttendSignBak">
						</list:paging>
					</div>
				</div>

				<!-- 内容列表 -->
				<list:listview id="sysAttendSignBak" channel="sysAttendSignBak">
					<ui:source type="AjaxJson">
						{url:'/sys/attend/sys_attend_sign_bak/sysAttendSignBak.do?method=list'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable"  channel="sysAttendSignBak">
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdOperator,fdOperatorDept,docCreateTime,fdSignType,fdAddress"></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
						Dropdown.init();
					</ui:event>
				</list:listview>
				<br>
				<!-- 分页 -->
				<list:paging channel="sysAttendSignBak"/>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>
