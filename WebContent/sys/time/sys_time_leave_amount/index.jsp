<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService,com.landray.kmss.util.SpringBeanUtil,java.util.List,com.landray.kmss.sys.time.model.SysTimeLeaveRule" %>
<%
	ISysTimeLeaveAmountService sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil.getBean("sysTimeLeaveAmountService");
	List<SysTimeLeaveRule> leaveRuleList = sysTimeLeaveAmountService.getAllLeaveRule();
	String leaveNames = "";
	for(SysTimeLeaveRule leaveRule : leaveRuleList) {
		leaveNames += leaveRule.getFdSerialNo() + ";";
	}
	pageContext.setAttribute("leaveNames", leaveNames);
%>
<template:include ref="default.simple" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-time:table.sysTimeLeaveAmount') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-time:table.sysTimeLeaveRule') }"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-time:table.sysTimeLeaveAmount') }"></ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-time:module.sys.time')}" />
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
			<list:cri-criterion  key="_fdYear" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdYear') }" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=getYearCriterion'}
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
						<list:sort property="sysTimeLeaveAmount.docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
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
						<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=add">
						<ui:button text="${lfn:message('button.add')}" onclick="addDoc();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=fileUpload">
						<ui:button text="${lfn:message('sys-time:sysTimeLeaveAmount.import.batch')}" onclick="importFile();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=deleteall">
						<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
						<kmss:authShow roles="ROLE_SYS_TIME_LEAVEAMOUNT_MANAGER">
							<ui:button text="${lfn:message('button.export')}" onclick="listExportExOperation('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.sys.time.model.sysTimeLeaveAmount&fileNameKey=sys-time:sysTimeLeaveDetail.remainder.exportFileName')" order="2" ></ui:button>
						</kmss:authShow>
						<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=batchExport">
							<ui:button text="${lfn:message('sys-time:sysTimeLeaveAmount.button.exportAll') }" onclick="batchExport()" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=list'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
				rowHref="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="fdPerson.fdParentsName,fdPerson.fdName;fdPerson.fdLoginName;fdYear;${leaveNames}totalRest;operation;"></list:col-auto>
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		
		<c:import url="/sys/time/sys_time_leave_amount/index_script.jsp" charEncoding="UTF-8">
		</c:import>
	</template:replace>
</template:include>