<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list" spa="true" rwd="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attachment:sysAttMain.manager') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }"
				icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item
				text="${ lfn:message('sys-attachment:sysAttMain.manager') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="nav">

		<ui:combin ref="menu.nav.title">
			<ui:varParam name="info">
				<ui:source type="Static">
					[
						{ text:"${lfn:message('sys-attachment:sysAttMain.button.upload')}",count_url:'/sys/attachment/sys_att_main/sysAttMain.do?method=count&type=create','href':'/upload',router:true }, 
						{ text:"${lfn:message('sys-attachment:sysAttMain.button.download')}",count_url:'/sys/attachment/sys_att_download_log/sysAttDownloadLog.do?method=count&type=create','href':'/download',router:true},
						{ text:"${lfn:message('sys-attachment-borrow:sysAttBorrow.count')}", count_url:'/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=count&type=create','href':'/borrow',router:true} 
					]
				</ui:source>
			</ui:varParam>
		</ui:combin>

		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<ui:content
					title="${lfn:message('sys-attachment:sysAttMain.list') }">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
							<ui:source type="Static">
							[{
		  						"text" : "${ lfn:message('sys-attachment:sysAttMain.all') }",
		  						"href" :  "/all",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_com_all"
		  					}]
							</ui:source>
						</ui:varParam>
					</ui:combin>

				</ui:content>
				<ui:content
					title="${lfn:message('sys-attachment:sysAttMain.list.my') }">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
							<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('sys-attachment:sysAttMain.myUpload') }",
		  						"href" :  "/upload",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_com_my_drafted"
		  					},{
		  						"text" : "${ lfn:message('sys-attachment:sysAttMain.myDownload') }",
		  						"href" :  "/download",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_oitems_input"
		  					}
		  					<kmss:authShow roles="ROLE_SYSATTACHMENT_BORROW_DEFAULT">
							,{
		  						"text" : "${ lfn:message('sys-attachment:sysAttMain.myBorrow') }",
		  						"href" :  "/borrow",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_com_reserve"
		  					}
							</kmss:authShow>
		  					]
		  					</ui:source>
						</ui:varParam>
					</ui:combin>
				</ui:content>

				<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
					<ui:content title="${ lfn:message('list.otherOpt')}">

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
				</kmss:authShow>
			</ui:accordionpanel>
		</div>
	</template:replace>

	<template:replace name="content">
		<c:import
			url="/sys/attachment/sys_att_main/sysAttMain_list_include.jsp"
			charEncoding="utf-8">
			<c:param name="hideNum" value="true" />
		</c:import>
	</template:replace>

	<template:replace name="script">

		<script>
			seajs.use('sys/attachment/sys_att_main/resource/js/subscribe');
		</script>
	</template:replace>

</template:include>
