<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>

<template:include ref="default.list" spa="true" rwd="true" spa-groups="[ ['fdIsTop','mydoc','docCategory' ,'docStatus'] ]">
	<template:replace name="title">
		<c:out value="${ lfn:message('dbcenter-echarts:module.dbcenter.piccenter') }"></c:out>
	</template:replace>
	
		
	<!DOCTYPE html>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('dbcenter-echarts:module.dbcenter.piccenter') }" />
			<ui:varParam name="button">
				[
					{
						"text": "",
						"href": "javascript:void(0);",
						"icon": "dbcenter_echarts"
					}
				]
			</ui:varParam>
		</ui:combin>
		
		<style>
			.lui_listview_rowtable_summary_content_box:hover{background-color:#f4f4f4}
		</style>

		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<!-- 图表中心 -->
				<ui:content
						title="${ lfn:message('dbcenter-echarts:module.echarts.all') }">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
							<ui:source type="Static">
								[
									{
									"text" : "${ lfn:message('dbcenter-echarts:module.echarts.index') }",
									"href" :  "/echart_index",
									"router" : true
									}
								]
							</ui:source>
						</ui:varParam>
					</ui:combin>
				</ui:content>

				<!-- 分类索引 -->
				<ui:content
						title="${lfn:message('dbcenter-echarts:module.echarts.menu.sysSimpleCategory') }">
					<ui:combin ref="menu.nav.simplecategory.flat.all">
						<ui:varParams
								modelName="com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate" 
								spa="true"
								/>
					</ui:combin>
				</ui:content>

				<!-- 图表管理 -->
				<ui:content
						title="${lfn:message('dbcenter-echarts:module.echarts.manage') }">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
							<ui:source type="Static">
								[
									{
									"text" : "${ lfn:message('dbcenter-echarts:module.echarts.configuration.wizard') }",
									"href" :  "/overview",
									"router" : true
									},
									{
									"text" : "${ lfn:message('dbcenter-echarts:table.dbEchartsCustom') }",
									"href" :  "/custom",
								     "router" : true
									},
									{
									"text" : "${ lfn:message('dbcenter-echarts:table.dbEchartsChart') }",
									"href" :  "/index",
									"router" : true
									},
									{
									"text" : "${ lfn:message('dbcenter-echarts:table.dbEchartsTable') }",
									"href" :  "/list",
									"router" : true
									},
									{
									"text" : "${ lfn:message('dbcenter-echarts:table.dbEchartsChartSet') }",
									"href" :  "/atlas",
									"router" : true
									}
								]
							</ui:source>
						</ui:varParam>
					</ui:combin>

				</ui:content>


				<ui:content title="${ lfn:message('list.otherOpt') }"  expand="false" >
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
							<ui:source type="Static">
								[
								<%-- 关闭回收站功能时，模块首页不显示“回收站” --%>
								<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.dbcenter.echarts.model.DbEchartsTotal")) { %>
								{
								"text" : "${ lfn:message('sys-recycle:module.sys.recycle') }",
								"href" :  "/recover",
								"icon" : "lui_iconfont_navleft_com_recycle",
								"router" : true
								},
								<%} %>
								<kmss:authShow roles="ROLE_DBCENTERECHARTS_DEFAULT">
									{
										"text" : "${ lfn:message('list.manager') }",
										"icon" : "lui_iconfont_navleft_com_background",
										"router" : true,
										"href" : "/management"
									}
								</kmss:authShow>
								]
							</ui:source>
						</ui:varParam>
					</ui:combin>
				</ui:content>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content" >
		
		<template:replace name="script">
			<script type="text/javascript">
			
				
				
                seajs.use(['lui/framework/module'],function(Module){
                    Module.install('dbEcharts',{
                        //模块变量
                        $var : {
                            categoryId : '${JsParam.categoryId}'
                        },
                        //模块多语言
                        $lang : {
                            pageNoSelect : '${lfn:message("page.noSelect")}',
                            optSuccess : '${lfn:message("return.optSuccess")}',
                            optFailure : '${lfn:message("return.optFailure")}',
                            buttonDelete : '{lfn:message("button.delete")}',
                            comfirmDelete : '${lfn:message("page.comfirmDelete")}'
                        },
                        //搜索标识符
                        $search : ''
                    });
                });
			</script>
			<script type="text/javascript" src="${LUI_ContextPath}/dbcenter/echarts/common/index.js"></script>
			
		</template:replace>

	</template:replace>
</template:include>
