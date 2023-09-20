<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-time:table.sysTimeLeaveDetail') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-time:table.sysTimeLeaveRule') }"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-time:table.sysTimeLeaveDetail') }"></ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-time:module.sys.time') }" />
			<ui:varParam name="button">
				[
					{
						"text": "${ lfn:message('sys-time:table.sysTimeLeaveRule')}",
						"href": "javascript:void(0);",
						"icon": "sys_time"
					}
				]
			</ui:varParam>			
		</ui:combin>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<c:import url="/sys/time/nav.jsp" charEncoding="UTF-8">
				</c:import>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		
		<list:criteria id="criteria">
			<list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdKeyword') }" style="width:350px;">
			</list:cri-ref>
			<list:cri-ref key="_fdDept" ref="criterion.sys.dept" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdDept') }" multi="false">
			</list:cri-ref>
			<list:cri-ref key="_fdPerson" ref="criterion.sys.person" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdPerson') }" multi="false">
			</list:cri-ref>
			<list:cri-criterion key="_fdLeaveName" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveName') }" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=getLeaveCriterion'}
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>
			<list:cri-ref key="_fdStartTime" ref="criterion.sys.calendar" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }" multi="false">
			</list:cri-ref>
			<list:cri-ref key="_fdEndTime" ref="criterion.sys.calendar" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }" multi="false">
			</list:cri-ref>
			<list:cri-criterion key="_fdOprType" title="${ lfn:message('sys-time:sysTimeLeaveDetail.source') }" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.review') }', value:'1'},
							{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.manual') }',value:'2'},
							{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.batch') }',value:'3'}]
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>
			<list:cri-criterion key="_fdOprStatus" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprDesc') }" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.no') }', value:'0'},
							{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.success') }', value:'1'},
							{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.fail') }', value:'2'}]
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>
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
						<list:sort property="sysTimeLeaveDetail.docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		
			<div style="float: right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar>
						<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=add">
						<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.add')}" onclick="addDoc();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=fileUpload">
						<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.addAll')}" onclick="importDoc();" order="2" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=deductAll">
						<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.deduct.batch')}" onclick="deductAll();" order="3" ></ui:button>
						</kmss:auth>
						
						<kmss:ifModuleExist path="/sys/attend">
						<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=updateAttendAll">
						<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.updateAttendAll')}" onclick="updateAttendAll();" order="4" ></ui:button>
						</kmss:auth>
						</kmss:ifModuleExist>
						<kmss:authShow roles="ROLE_SYS_TIME_LEAVEAMOUNT_MANAGER"> 
							<!-- 导出按钮 -->
							<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.sys.time.model.SysTimeLeaveDetail&fileNameKey=sys-time:sysTimeLeaveDetail.exportFileName')" order="5" ></ui:button>
						</kmss:authShow>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=list'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
				rowHref="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="fdPerson.fdParentsName;fdPerson.fdName;fdPerson.fdLoginName;fdLeaveName;fdStartTime;fdEndTime;fdLeaveTime"></list:col-auto>
				<list:col-auto props="fdOprType;fdOprDesc;fdReview;operation;"></list:col-auto>
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		
		<c:import url="/sys/time/sys_time_leave_detail/index_script.jsp" charEncoding="UTF-8">
		</c:import>
	</template:replace>
</template:include>