<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list" spa="true" rwd="true">
	<template:replace name="title">
		${ lfn:message('sys-time:module.sys.time')}
	</template:replace>
	<template:replace name="head">
	</template:replace>
	<template:replace name="nav">
		<div class="lui_list_noCreate_frame">
		<ui:combin ref="menu.nav.create">
		<ui:varParam name="title" value="${ lfn:message('sys-time:module.sys.time')}" />
			<ui:varParam name="button">
				[
					{
						"text": "",
						"href": "javascript:void(0);",
						"icon": "sys_time"
					}
				]
			</ui:varParam>			
		</ui:combin>
		</div>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<ui:content title="${ lfn:message('sys-time:table.sysTimeLeaveRule')}"  expand="true">
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('sys-time:sysTimeLeaveRule.leaveAmount')}",
		  						"href" :  "/leaveAmount",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_time_amount"
		  					},
		  					{
		  						"text" : "${ lfn:message('sys-time:sysTimeLeaveRule.leaveDetail')}",
		  						"href" :  "/leaveDetail",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_time_detail"
		  					}
		  					]
		  					</ui:source>
		  				</ui:varParam>
		  			</ui:combin>
		  		</ui:content>
		  		<ui:content title="${ lfn:message('list.otherOpt')}" expand="true">
		  			<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[
		  						{
									"text" : "${ lfn:message('list.manager') }",
									"icon" : "lui_iconfont_navleft_com_background",
									"router" : true,
									"href" : "/management"
								}
		  					]
		  					</ui:source>
		  				</ui:varParam>
		  			</ui:combin>
		  		</ui:content>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel id="leavePanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
			<ui:content id="leaveAmountContent" title="${ lfn:message('sys-time:sysTimeLeaveRule.leaveAmount')}" cfg-route="{path:'/leaveAmount'}">
				<ui:iframe id="leaveAmount" src="${LUI_ContextPath}/sys/time/sys_time_leave_amount/index.jsp"></ui:iframe>
			</ui:content>
			<ui:content id="leaveDetailContent" title="${ lfn:message('sys-time:sysTimeLeaveRule.leaveDetail')}" cfg-route="{path:'/leaveDetail'}">
				<ui:iframe id="leaveDetail" src="${LUI_ContextPath}/sys/time/sys_time_leave_detail/index.jsp"></ui:iframe>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
	<template:replace name="script">
      	<script type="text/javascript">
      		seajs.use(['lui/framework/module'],function(Module){
      			Module.install('sysTime',{
  				});
      		});
      	</script>
      	<script type="text/javascript" src="${LUI_ContextPath}/sys/time/resource/js/index.js"></script>
	</template:replace>
</template:include>