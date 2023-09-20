<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('km-smissive:table.kmSmissiveMain') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
			<ui:varParams 
				id="simplecategoryId"
				moduleTitle="${ lfn:message('km-smissive:table.kmSmissiveMain') }" 
				modelName="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" 
				extkey="mydoc;docStatus;type;other" />
		</ui:combin>
	</template:replace>
	<template:replace name="nav">
		<!-- 所有分类 -->
		<%-- <ui:combin ref="menu.nav.simplecategory.all"> 
			<ui:varParams
				modelName="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" 
				href="/km/smissive/" />
		</ui:combin> --%>
		
		<ui:combin ref="menu.nav.title">
			<ui:varParam name="operation">
				<ui:source type="Static">
					[
					{ 
						  text:"${ lfn:message('km-smissive:smissive.tree.myJob.alldoc') }",
						  href:"/listAll",
						  router : true,
						  icon:"lui_iconfont_navleft_com_all"
					 },
					{ 
						 text:"${ lfn:message('km-smissive:smissive.create.my') }",
						 href:"/listCreate",
						 router : true,
						 icon:"lui_iconfont_navleft_com_my_drafted"
					},
					{ 
						 text:"${ lfn:message('km-smissive:smissive.approval.my') }", 
						 href: "/listApproval",
						 router : true,
						 icon:"lui_iconfont_navleft_com_my_beapproval"
					},
					{ 
						 text:"${ lfn:message('km-smissive:smissive.approved.my') }", 
						 href:"/listApproved",
						 router : true,
						 icon:"lui_iconfont_navleft_com_my_approvaled"
					}
					]
				</ui:source>
			</ui:varParam>				
		</ui:combin>
		
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<ui:combin ref="menu.nav.favorite.category">
					<ui:varParams 
						modelName="com.landray.kmss.km.smissive.model.KmSmissiveTemplate"/>
				</ui:combin>
				<ui:content style="padding:0px;" title="${lfn:message('sys-category:menu.sysCategory.index') }">
					<ui:combin ref="menu.nav.simplecategory.flat.all">
						<ui:varParams modelName="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" spa="true"
						criProps="{'cri.q':'docStatus:30'}" />
					</ui:combin>
				</ui:content>
				<ui:content title="${ lfn:message('list.otherOpt') }" expand="false" >
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[
		  					<%-- 关闭回收站功能时，模块首页不显示“回收站” --%>
		  					<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.km.smissive.model.KmSmissiveMain")) { %>
		  					{
		  						"text" : "${ lfn:message('sys-recycle:module.sys.recycle') }",
		  						"href" :  "/recover",
								"router" : true,
								"icon" : "lui_iconfont_navleft_com_recycle"
		  					}
		  					<kmss:authShow roles="ROLE_KMSMISSIVE_BACKSTAGE_MANAGER">
		  					,
		  					{
								"text" : "${ lfn:message('list.manager') }",
								"icon" : "lui_iconfont_navleft_com_background",
								"router" : true,
								"href" : "/management"
							}
		  					</kmss:authShow>
		  					<% }else{ %>
		  					<kmss:authShow roles="ROLE_KMSMISSIVE_BACKSTAGE_MANAGER">
		  					{
								"text" : "${ lfn:message('list.manager') }",
								"icon" : "lui_iconfont_navleft_com_background",
								"router" : true,
								"href" : "/management"
							}
		  					</kmss:authShow>
		  					<% } %>
		  					]
		  					</ui:source>
		  				</ui:varParam>	
					</ui:combin>
				</ui:content>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">  
		<list:criteria id="criteria1">
	     	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"> 
			</list:cri-ref>
			<list:cri-criterion title="${ lfn:message('km-smissive:kmSmissiveMain.docStatus')}" key="docStatus"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('status.draft')}', value:'10'},{text:'${ lfn:message('status.examine')}',value:'20'},{text:'${ lfn:message('status.refuse')}',value:'11'},{text:'${ lfn:message('status.discard')}',value:'00'},{text:'${ lfn:message('status.publish')}',value:'30'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.km.smissive.model.KmSmissiveMain" property="docAuthor;fdMainDept;docCreateTime;fdFileNo;docProperties" />
		</list:criteria>
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
						<list:sortgroup>
							<list:sort property="docCreateTime" text="${lfn:message('km-smissive:kmSmissiveMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="docPublishTime" text="${lfn:message('km-smissive:kmSmissiveMain.docPublishTime') }" group="sort.list"></list:sort>
					    </list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="3">
						<%-- 收藏 --%>
						<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp"
							charEncoding="UTF-8">
							<c:param name="fdTitleProName" value="docSubject" />
							<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
						</c:import>
						<kmss:authShow roles="ROLE_KMSMISSIVE_CREATE">
						<kmss:auth requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=add" requestMethod="GET">
						<ui:button text="${lfn:message('button.add')}" onclick="window.moduleAPI.kmSmissive.addDoc()" order="2"></ui:button>
						</kmss:auth>
						</kmss:authShow>
						<kmss:auth requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=deleteall&status=${param.status}&categoryId=${param.categoryId}&nodeType=${param.nodeType}" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="window.moduleAPI.kmSmissive.delDoc()" order="3"></ui:button>
						</kmss:auth>
						
						<%--  导出 --%>	
						<kmss:authShow roles="ROLE_KMSMISSIVE_TRANSPORT_EXPORT">
						<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.smissive.model.KmSmissiveMain')" order="2" ></ui:button>
						</kmss:authShow>
						<%-- 修改权限 --%>
						<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
							<c:param name="modelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
							<c:param name="authReaderNoteFlag" value="2" />
							<c:param name="spa" value="true" />
						</c:import>							
						<%-- 分类转移 --%>
						<%-- 如果用户有“分类权限扩充”角色，则允许转移到所有的分类 --%>
						<% 
							if(com.landray.kmss.util.UserUtil.checkRole("ROLE_KMSMISSIVE_OPTALL")) {
								request.setAttribute("authType", "00");
							} 
						%>
						<c:import url="/sys/simplecategory/import/doc_cate_change_button.jsp" charEncoding="UTF-8">
							<c:param name="modelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
							<c:param name="docFkName" value="fdTemplate" />
							<c:param name="cateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
							<c:param name="authType" value="${authType}" />
							<c:param name="spa" value="true" />
						</c:import>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/km/smissive/km_smissive_main/kmSmissiveMainIndex.do?method=listChildren'}
			</ui:source>
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.smissive.model.KmSmissiveMain" isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto ></list:col-auto>
			</list:colTable>
			<list:rowTable isDefault="false"
				rowHref="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId=!{fdId}" name="rowtable" >
				<list:row-template ref="sys.ui.listview.rowtable">
				</list:row-template>
			</list:rowTable>			
		</list:listview>
	 	<list:paging></list:paging>	 
		</template:replace>
		<template:replace name="script">
   		<%-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 --%>
      	<script type="text/javascript">
      		seajs.use(['lui/framework/module'],function(Module){
      			Module.install('kmSmissive',{
					//模块变量
					$var : {},
 					//模块多语言
 					$lang : {
 						pageNoSelect : '${lfn:message("page.noSelect")}'
 						
 					},
 					//搜索标识符
 					$search : 'com.landray.kmss.km.smissive.model.KmSmissiveMain'
  				});
      		});
      	</script>
      	<script type="text/javascript" src="${LUI_ContextPath}/km/smissive/resource/js/index.js"></script>
	</template:replace>
</template:include>
