<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include ref="default.list" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-lbpmperson:lbpmperson.title') }"></c:out>
	</template:replace>
	
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-lbpmperson:lbpmperson.title') }" />
			<ui:varParam name="button">[{
						"text": "",
						"icon": "sys_lbpmperson"
					}]
			</ui:varParam>				
		</ui:combin>
	<div id="menu_nav" class="lui_list_nav_frame">
	  <ui:accordionpanel>
			<ui:content title="${ lfn:message('sys-lbpmperson:lbpmperson.nprocessCenter') }" expand="true">
				<ui:combin ref="menu.nav.simple">
	  				<ui:varParam name="source">
	  					<ui:source type="Static">
	  					[{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.create') }",
							"href" :  "/createDoc",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_review_create"
		  				}
		  				,
		  				{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.approvaling') }",
							"href" :  "/approve",
							"router" : true,  					
		  					"icon" : "lui_iconfont_navleft_com_my_beapproval"
		  				}
		  				,
		  				{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.approvaled') }",
							"href" :  "/approved",
							"router" : true,  					
		  					"icon" : "lui_iconfont_navleft_com_my_approvaled"
		  				}
		  				,
		  				{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.trackprocess') }",
							"href" :  "/track",
							"router" : true,  					
		  					"icon" : "lui_iconfont_navleft_review_track"
		  				}
		  				,
		  				{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.submitprocess') }",
							"href" :  "/create",
							"router" : true,  					
		  					"icon" : "lui_iconfont_navleft_com_my_drafted"
		  				}
		  				,
		  				{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.processAuthorization') }",
							"href" :  "/auth",
							"router" : true,  					
		  					"icon" : "lui_iconfont_navleft_person_grant"
		  				}
		  				,
		  				{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.processdaft') }",
							"href" :  "/draft",
							"router" : true,  					
		  					"icon" : "lui_iconfont_navleft_com_my_temporary"
		  				}
		  				,
		  				{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.abandon') }",
							"href" :  "/abandon",
							"router" : true,  					
		  					"icon" : "lui_iconfont_navleft_com_discard"
		  				}
		  				]
	  					</ui:source>
	  				</ui:varParam>
	  			</ui:combin>
			</ui:content>
			<ui:content title="${ lfn:message('sys-lbpmperson:lbpmperson.efficiencyCenter') }" expand="true">
				<ui:combin ref="menu.nav.simple">
	  				<ui:varParam name="source">
	  					<ui:source type="Static">
	  					[{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.dataCount') }",
							"href" :  "/createSummary",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_statistics"
		  				}
		  				]
	  					</ui:source>
	  				</ui:varParam>
	  			</ui:combin>
			</ui:content>
			<ui:content title="${ lfn:message('sys-lbpmperson:lbpmperson.personset') }" expand="true">
				<ui:combin ref="menu.nav.simple">
	  				<ui:varParam name="source">
	  					<ui:source type="Static">
	  					[{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.usageNote') }",
							"href" :  "/usage",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_review_usage"
		  				},
		  				{
		  					"text" : "${ lfn:message('sys-lbpmperson:lbpmperson.defaultIndentity') }",
							"href" :  "/lbpmIdentity",
							"router" : true,  					
		  					"icon" : "lui_iconfont_navleft_review_process_identity"
		  				}
		  				]
	  					</ui:source>
	  				</ui:varParam>
	  			</ui:combin>
			</ui:content>
		</ui:accordionpanel>
	</div>
	</template:replace>
	<template:replace name="script">
		<!-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 -->
		<script type="text/javascript">
			seajs.use(['lui/framework/module'],function(Module){
				Module.install('sysLbpmPerson',{
					//模块变量
					$var : {},
					//模块多语言
					$lang : {
						
					},
					//搜索标识符
					$search : ''
				});
			});
		</script>
		<!-- 引入JS -->
		<script type="text/javascript" src="${LUI_ContextPath}/sys/lbpmperson/resource/js/index.js"></script>
	</template:replace>
</template:include>
