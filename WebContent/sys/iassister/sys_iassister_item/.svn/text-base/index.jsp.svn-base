<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.list">
	<template:replace name="head">
		<script type="text/javascript">
			var frontEnd = null;
			var frontEndJs = 'sys/iassister/sys_iassister_item/js/index.js';
			function luiReady() {
				seajs.use([ frontEndJs ], function(front) {
					front.init({
						categoryId : "${param.categoryId}",
						ctxPath : "${LUI_ContextPath}"
					});
					frontEnd = front;
				})
			}
			LUI.ready(luiReady);
		</script>
	</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criHere">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject"
				title="${lfn:message('sys-iassister:sysIassisterItem.fdName') }"
				style="width: 280px;"></list:cri-ref>
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
				<div class="lui_list_operation_order_text">${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							<list:sort property="fdOrder"
								text="${lfn:message('sys-iassister:sysIassisterItem.fdOrder')}"
								group="sort.list" value="up" />
							<list:sort property="fdName"
								text="${lfn:message('sys-iassister:sysIassisterItem.fdName')}"
								group="sort.list" />
							<list:sort property="rule.fdName"
								text="${lfn:message('sys-iassister:sysIassisterItem.rule')}"
								group="sort.list" />
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float: right">
				<div style="display: inline-block; vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="3">
						<!-- 增加 -->
						<kmss:auth
							requestURL="/sys/iassister/sys_iassister_item/sysIassisterItem.do?method=add&categoryId=${param.categoryId}"
							requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}"
								onclick="frontEnd.add()" order="1"></ui:button>
						</kmss:auth>
						<!-- 批量删除 -->
						<kmss:auth
							requestURL="/sys/iassister/sys_iassister_item/sysIassisterItem.do?method=deleteall"
							requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}"
								onclick="frontEnd.batchDel()" order="2"></ui:button>
						</kmss:auth>
						<!-- 快速排序 -->
						<c:import url="/sys/profile/common/change_order_num.jsp"
							charEncoding="UTF-8">
							<c:param name="modelName"
								value="com.landray.kmss.sys.iassister.model.SysIassisterItem"></c:param>
							<c:param name="property" value="fdOrder"></c:param>
						</c:import>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview id="lvHere">
			<ui:source type="AjaxJson">
                    {url:'/sys/iassister/sys_iassister_item/sysIassisterItem.do?method=data&q.docCategory=${param.categoryId }'}
                </ui:source>
			<!-- 列表视图 -->
			<list:colTable isDefault="false"
				rowHref="/sys/iassister/sys_iassister_item/sysIassisterItem.do?method=view&fdId=!{fdId}"
				name="columntable">
				<list:col-checkbox />
				<list:col-auto props="fdOrder;fdName;categoryName;ruleSetName"
					url="" />
			</list:colTable>
		</list:listview>
		<!-- 翻页 -->
		<list:paging />
	</template:replace>
</template:include>