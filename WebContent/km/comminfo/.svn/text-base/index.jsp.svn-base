<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('km-comminfo:module.km.comminfo') }"></c:out>
	</template:replace>
	<template:replace name="path">
			<ui:combin ref="menu.path.simplecategory">
			<ui:varParams 
				id="simplecategoryId"
				moduleTitle="${ lfn:message('km-comminfo:module.km.comminfo') }" 
				modelName="com.landray.kmss.km.comminfo.model.KmComminfoCategory" 
				categoryId="${param.categoryId }" />
		</ui:combin>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('km-comminfo:module.km.comminfo') }" />
			<ui:varParam name="button">
				[
					{
						"text": "",
						"href": "javascript:void(0);",
						"icon": "km_comminfo"
					}
				]
			</ui:varParam>
		</ui:combin>
		
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<ui:content style="padding:0px;" title="${lfn:message('sys-category:menu.sysCategory.index') }">
					<ui:combin ref="menu.nav.simplecategory.flat.all">
						<ui:varParams
							modelName="com.landray.kmss.km.comminfo.model.KmComminfoCategory"
							spa="true" />
					</ui:combin>
				</ui:content>
				<kmss:authShow roles="ROLE_COMMINFO_BACKSTAGE_MANAGER">
				<ui:content title="${ lfn:message('list.otherOpt') }" toggle="true" expand="false" >
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
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
			</list:cri-ref>
			<!-- 创建人，创建时间 -->
			<list:cri-auto modelName="com.landray.kmss.km.comminfo.model.KmComminfoMain" 
				property="docCreator;docCreateTime" />
		</list:criteria>
		<%@ include file="/km/comminfo/km_comminfo_ui/kmComminfoMain_listview.jsp" %>
	</template:replace> 
	<template:replace name="script">
		<!-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 -->
		<script type="text/javascript">
			seajs.use(['lui/framework/module'],function(Module){
				Module.install('kmComminfo',{
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
		<script type="text/javascript" src="${LUI_ContextPath}/km/comminfo/resource/js/index.js"></script>
	</template:replace>
</template:include>
