<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list" >
<c:set var="categoryType" value="attend" />
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
	</template:replace>
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="nav">
		<script>
			function addDoc(){
				Com_OpenWindow("${LUI_ContextPath}/sys/attend/sys_attend_category/sysAttendCategory.do?method=add");
			}
		</script>
		<ui:combin ref="menu.nav.title">
			<ui:varParam name="operation">
				<ui:source type="Static">
					[
					{ 
					  "text":"${ lfn:message('sys-attend:sysAttend.nav.item.title') }", 
					  "href":"/myAbnormal", 
					  "icon":"lui_iconfont_navleft_attend_abnormal",
					  "router" : true
					},
					{ 
					  "text":"${ lfn:message('list.approval') }", 
					  "href":"/approval", 
					  "icon":"lui_iconfont_navleft_com_my_beapproval",
					  "router" : true
					},
					{ 
					  "text":"${ lfn:message('sys-attend:sysAttend.nav.item.attendCalendar') }", 
					  "href":"/attendCalendar", 
					  "icon":"lui_iconfont_navleft_attend_attendCalendar",
					  "router" : true
					},
					{ 
					  "text":"${ lfn:message('sys-attend:sysAttend.nav.item.custCalendar') }", 
					  "href":"/signCalendar", 
					  "icon":"lui_iconfont_navleft_attend_signCalendar",
					  "router" : true
					}
				    ]
				</ui:source>
			</ui:varParam>
		</ui:combin>
		
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
			 	<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
				   <c:param name="key" value="calendar"></c:param>
				   <c:param name="criteria" value="calendarCriteria"></c:param>
				   <c:param name="categoryType" value="${categoryType}"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel id="sysAttendPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
			<ui:content id="sysAttendContent" title="${ lfn:message('sys-attend:sysAttend.nav.item.attendCalendar') }" cfg-route="{path:'/attendCalendar'}">
				<%@ include file="/sys/attend/sys_attend_main/sysAttendMain_calendar_conent.jsp"%>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
			seajs.use(['lui/framework/module'],function(Module){
				Module.install('sysAttend',{
					//模块变量
					$var : {
						appKey:'${meeting_modelKey }',
						appName:'${meeting_moduleName }'
					},
					//模块多语言
					$lang : {
						'attendCalendar' : "${ lfn:message('sys-attend:sysAttend.nav.item.attendCalendar') }",
						'signCalendar' : "${ lfn:message('sys-attend:sysAttend.nav.item.custCalendar') }",
					},
					//搜索标识符
					$search : ''
				});
			});
		</script>
		<!-- 引入JS -->
		<script type="text/javascript" src="${LUI_ContextPath}/sys/attend/resource/js/index.js"></script>
	</template:replace>
</template:include>