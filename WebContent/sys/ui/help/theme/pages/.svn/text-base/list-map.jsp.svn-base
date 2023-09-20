<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>
<c:set var="kmsKnowledgeIndex" value="${lfn:message('kms-knowledge:kmsKnowledge.index') }"></c:set>
<c:set var="kmsKnowledgeOverview" value="${lfn:message('kms-knowledge:kmsKnowledge.overview') }"></c:set>
<c:set var="kmsKnowledgeMyTemplate" value="${lfn:message('kms-knowledge:kmsKnowledge.my.attention.template') }"></c:set>
<template:include ref="default.list">
	<template:replace name="head">
		<%@ include file="../jsp/nav.jsp" %>
		<%@ include file="../jsp/changeheader.jsp" %>
		<%@ include file="../jsp/changelist.jsp" %>
		<link rel="stylesheet" href="./../css/help-theme.css">
	</template:replace>
	<template:replace name="title">样例-知识地图</template:replace>
	<template:replace name="path">
		<%-- 路径 --%>
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="首页" icon="lui_icon_s_home" href="#" target="_self" />
			<ui:menu-item text="知识中心" href="#" target="_self" />
			<ui:menu-source autoFetch="true" target="_self" href="#">
				<ui:source type="AjaxJson">
					{"url":"/sys/ui/help/menu/pathdata-example.jsp?currId=!{value}"}
				</ui:source>
			</ui:menu-source>
		</ui:menu>
	</template:replace>
	<template:replace name="nav">
		<%-- 头部导航 --%>
		<ui:combin ref="menu.nav.title">
			<ui:varParam name="infonew">
				<ui:source type="AjaxJson">
					{url:'/kms/kmaps/kms_kmaps_ui/info.jsp'}
				</ui:source>
			</ui:varParam>
			<ui:varParam name="operation">
				<ui:source type="AjaxJson">
					{url:'/kms/kmaps/kms_kmaps_ui/operation.jsp'}
				</ui:source>
			</ui:varParam>
		</ui:combin>
		<div id="map_btn">
			<span id="map_btn_span" onclick="addMap()" class="mapAddBtn">
				<img alt="" src="${LUI_ContextPath}/kms/kmaps/kms_kmaps_ui/style/images/add_icon_mini.png"
					width="17px" height="17px">
				${lfn:message('kms-kmaps:kmsKmapsTemplate.addbtn')}
			</span>
		</div>
		<ui:accordionpanel>
			<ui:content title="${ lfn:message('km-review:kmReview.nav') }">
				<ui:combin ref="menu.nav.simple">
					<ui:varParam name="source">
						<ui:source type="Static">
							[{
							"text" : "${ lfn:message('km-review:kmReview.nav.all') }",
							"href" : "/listAll",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_all"
							},
							<kmss:authShow roles="ROLE_KMREVIEW_CREATE">
								{
								"text" : "${ lfn:message('km-review:kmReview.nav.create') }",
								"href" : "/create",
								"router" : true,
								"icon" : "lui_iconfont_navleft_review_create"
								},
							</kmss:authShow>
							{
							"text" : "${ lfn:message('km-review:kmReview.nav.examine') }",
							"href" : "/listExamine",
							"router" : true,
							"icon" : "lui_iconfont_navleft_review_approval"
							},{
							"text" : "${ lfn:message('km-review:kmReview.nav.follow') }",
							"href" : "/listFollow",
							"router" : true,
							"icon" : "lui_iconfont_navleft_review_track"
							},{
							"text" : "${ lfn:message('km-review:kmReview.nav.feedback') }",
							"href" : "/listFeedback",
							"router" : true,
							"icon" : "lui_iconfont_navleft_review_feedback"
							},{
							"text" : "${ lfn:message('km-review:kmReview.nav.search') }",
							"href" : "/search",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_query"
							}
							<kmss:ifModuleExist path="/dbcenter/echarts/">
								<kmss:authShow roles="ROLE_DBCENTERECHARTS_DEFAULT">
									<xform:Show bean="dbEchartsNavTreeShowService"
										mainModelName="com.landray.kmss.km.review.model.KmReviewTemplate"
										fdKey="kmReviewMainDoc">
										,{
										"text" : "${ lfn:message('dbcenter-echarts:module.dbcenter.dataChart') }",
										"href" : "/dbNavTree",
										"router" : true,
										"icon" : "lui_iconfont_navleft_com_statistics"
										}
									</xform:Show>
								</kmss:authShow>
							</kmss:ifModuleExist>
							<% if (com.landray.kmss.sys.subordinate.util.SubordinateUtil.getInstance().getModelByModuleAndUser("km-review:module.km.review").size() > 0) { %>
							,{
							"text" : "${lfn:message('km-review:subordinate.kmReviewMain') }",
							"href" : "/sys/subordinate",
							"router" : true,
							"icon" : "lui_iconfont_navleft_subordinate"
							}
							<% } %>
							]
						</ui:source>
					</ui:varParam>
				</ui:combin>
			</ui:content>
			<%-- 分类索引 --%>
			<ui:content title="分类导航">
				<%-- <ui:combin ref="menu.nav.simplecategory.flat.all">
					<ui:varParams modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
					<ui:varParams isHasSearch="true" />
				</ui:combin> --%>
				<template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.cate"
					render="sys.ui.cate.default" />
				<ui:operation
					href="/sys/sc/categoryPreivew.do?method=forward&service=kmsKnowledgeCategoryPreManagerService"
					name="设置" target="_rIframe" vertical="top" />
			</ui:content>

			<ui:content title="后台配置">
				<ui:combin ref="menu.nav.simple">
					<ui:varParam name="source">
						<ui:source type="Static">
							[{
							"text" : "${ lfn:message('km-review:kmReview.nav.filed') }",
							"href" : "/listFiling",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_file"
							},{
							"text" : "${ lfn:message('km-review:kmReview.nav.discard') }",
							"href" : "/listDiscard",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_discard"
							}
							<%-- 关闭回收站功能时，模块首页不显示“回收站” --%>
							<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.km.review.model.KmReviewMain")) { %>
							,{
							"text" : "${ lfn:message('km-review:kmReview.nav.recycle') }",
							"href" : "/recover",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_recycle"
							}
							<% } %>
							<kmss:authShow roles="ROLE_KMREVIEW_BACKSTAGE_MANAGER">
								,{
								"text" : "${ lfn:message('list.manager') }",
								"href" : "/management",
								"router" : true,
								"icon" : "lui_iconfont_navleft_com_background"
								}
							</kmss:authShow>
							]
						</ui:source>
					</ui:varParam>
				</ui:combin>
			</ui:content>
			<%--会议统计--%>
			<ui:content title="${ lfn:message('km-imeeting:kmImeeting.tree.stat') }">
				<ui:menu layout="sys.ui.menu.ver.default">
					<%--部门会议统计--%>
					<ui:menu-popup text="${ lfn:message('km-imeeting:kmImeeting.tree.stat.dept')}" align="right-top"
						borderWidth="2" icon="lui_icon_s iconfont lui_iconfont_navleft_com_statistics">
						<div style="width: 500px;">
							<ui:dataview>
								<ui:source type="Static">
									[{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.stat.thrught')}",
									"children":[{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.dept.stat')}",
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=dept.stat');",
									"target": "_self"
									},{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.dept.statMon')}",
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=dept.statMon')",
									"target": "_self"
									}]
									}]
								</ui:source>
								<ui:render ref="sys.ui.treeMenu2.cate"></ui:render>
							</ui:dataview>
						</div>
					</ui:menu-popup>
					<%--个人会议统计--%>
					<ui:menu-popup text="${ lfn:message('km-imeeting:kmImeeting.tree.stat.person')}" align="right-top"
						borderWidth="2" icon="lui_icon_s iconfont lui_iconfont_navleft_com_statistics">
						<div style="width: 500px;">
							<ui:dataview>
								<ui:source type="Static">
									[{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.stat.thrught')}",
									"children":[{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.person.stat')}",
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=person.stat')",
									"target": "_self"
									},{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.person.statMon')}",
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=person.statMon')",
									"target": "_self"
									}]
									}]
								</ui:source>
								<ui:render ref="sys.ui.treeMenu2.cate"></ui:render>
							</ui:dataview>
						</div>
					</ui:menu-popup>
					<%--会议室资源统计--%>
					<ui:menu-popup text="${ lfn:message('km-imeeting:kmImeeting.tree.stat.res')}" align="right-top"
						borderWidth="2" icon="lui_icon_s iconfont lui_iconfont_navleft_com_statistics">
						<div style="width: 500px;">
							<ui:dataview>
								<ui:source type="Static">
									[{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.stat.thrught')}",
									"children":[{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.resource.stat')}",
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=resource.stat')",
									"target": "_self"
									},{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.resource.statMon')}",
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=resource.statMon')",
									"target": "_self"
									}]
									}]
								</ui:source>
								<ui:render ref="sys.ui.treeMenu2.cate"></ui:render>
							</ui:dataview>
						</div>
					</ui:menu-popup>
				</ui:menu>
			</ui:content>
		</ui:accordionpanel>
	</template:replace>
	<template:replace name="content">
		<%-- 筛选器 --%>
		<list:criteria>
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="标题" />
			<list:cri-ref key="docStatus" ref="criterion.sys.docStatus" title="文档状态" multi="true" />
			<list:cri-ref key="docCreator" ref="criterion.sys.person" title="创建者" />
			<list:cri-ref key="docDept" ref="criterion.sys.dept" title="所属部门" />
			<list:cri-ref key="docCreated" ref="criterion.sys.calendar" title="创建时间" />
			<list:cri-ref key="fdNumber" ref="criterion.sys.num" title="数字" />
			<list:cri-ref key="fdString" ref="criterion.sys.string" title="字符" />
			<list:cri-group title="其它选项">
				<list:group-ref key="pdocStatus" ref="criterion.sys.docStatus.popup" title="文档状态" multi="true" />
				<list:group-ref key="pdocCreator" ref="criterion.sys.person.popup" title="创建者" />
				<list:group-ref key="pdocDept" ref="criterion.sys.dept.popup" title="所属部门" />
				<list:group-ref key="pdocCreated" ref="criterion.sys.calendar.popup" title="创建时间" />
				<list:group-ref key="pfdNumber" ref="criterion.sys.num.popup" title="数字" />
				<list:group-ref key="pfdString" ref="criterion.sys.string.popup" title="字符" />
			</list:cri-group>
		</list:criteria>

		<%-- 操作条 --%>
		<div class="lui_list_operation">
			<table width="100%">
				<tr>
					<td style='width: 70px;'>
						排序方式：
					</td>
					<td>
						<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							<list:sortgroup>
								<list:sort property="docCreateTime" text="创建时间" value="down" group="sort.list" />
								<list:sort property="docPublishTime" text="发布时间" group="sort.list" />
							</list:sortgroup>
						</ui:toolbar>
					</td>
					<td align="right">
						<ui:toolbar count="3">
							<ui:togglegroup order="0">
								<ui:toggle icon="lui_icon_s_zaiyao" selected="true" group="tg_1" text="列表"
									value="columntable" onclick="LUI('listview').switchType(this.value);" />
								<ui:toggle icon="lui_icon_s_liebiao" value="rowtable" group="tg_1" text="摘要"
									onclick="LUI('listview').switchType(this.value);" />
								<ui:toggle icon="lui_icon_s_tuwen" value="gridtable" group="tg_1" text="图文"
									onclick="LUI('listview').switchType(this.value);" />
							</ui:togglegroup>
							<ui:button text="操作确认" onclick="confirm()" order="2"></ui:button>
							<ui:button text="成功提示" onclick="success()" order="2"></ui:button>
							<ui:button text="失败提示" onclick="failure()" order="2"></ui:button>
						</ui:toolbar>
					</td>
				</tr>
			</table>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>

		<%-- 视图 --%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/ui/help/listview/listdata-example.jsp'}
			</ui:source>
			<list:colTable rowHref="#" name="columntable" target="_self" layout="sys.ui.listview.columntable">
				<list:col-checkbox />
				<list:col-serial />
				<list:col-html title="标题" style="text-align:left">
					{$ <span class="com_subject">{%row['docSubject']%}</span> $}
				</list:col-html>
				<list:col-auto props="docAuthor.fdName;docDept.fdName;docPublishTime;docStatus" />
			</list:colTable>
			<list:rowTable rowHref="#" name="rowtable" target="_self">
				<list:row-template ref="sys.ui.listview.rowtable" />
			</list:rowTable>
			<list:gridTable gridHref="#" name="gridtable" target="_self">
				<list:row-template ref="sys.ui.listview.gridtable" />
			</list:gridTable>
		</list:listview>

		<list:paging></list:paging>

		<script type="text/javascript">
			SYS_SEARCH_MODEL_NAME = "KmReviewMain";

			function confirm() {
				seajs.use(['lui/dialog'], function (dialog) {
					dialog.confirm('操作提示');
				});
			}

			function success() {
				seajs.use(['lui/dialog'], function (dialog) {
					var loading = dialog.loading();
					setTimeout(function () {
						loading.hide();
						dialog.success('操作成功');
					}, 1000);
				});
			}

			function failure() {
				seajs.use(['lui/dialog'], function (dialog) {
					var loading = dialog.loading();
					setTimeout(function () {
						loading.hide();
						dialog.failure('操作失败');
					}, 1000);
				});
			}
		</script>
	</template:replace>
</template:include>