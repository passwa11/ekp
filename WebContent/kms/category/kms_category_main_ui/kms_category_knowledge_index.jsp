<%@page
	import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeConstantUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="isHasWiki" value="false"></c:set>
<kmss:ifModuleExist path="/kms/wiki/">
	<c:set var="isHasWiki" value="true"></c:set>
</kmss:ifModuleExist>
<c:set var="isHasKem" value="false"></c:set>
<c:if test="${empty fdSourceType}">
	<c:set var="fdSourceType" value="1"></c:set>
</c:if>	
<kmss:ifModuleExist path="/kms/kem/">
	<c:set var="isHasKem" value="true"></c:set>
</kmss:ifModuleExist>

<c:choose>
	<c:when test="${not empty param.orderBy  }">
		<c:set var="fdOrderBy" value="${param.orderBy}" />
	</c:when>
	<c:otherwise>
		<c:set var="fdOrderBy" value="docPublishTime" />
	</c:otherwise>
</c:choose>

<template:include ref="default.list" spa="true" rwd="true">
	
	<template:replace name="title">${lfn:message('kms-category:module.kms.category') }</template:replace>
	<template:replace name="head">

		<script>
			(function() {

				var toggleView = "${JsParam.toggleView}";
				if (toggleView) {
					var toggleList = [
							"rowtable",
							"gridtable",
							"columntable",
							"attmaintable" ];
					for (var i = 0; i < toggleList.length; i++) {
						if (toggleView === toggleList[i])
							localStorage.setItem("toggle.change", toggleView);
					}
				}

			})();
		</script>


	</template:replace>
	<%-- 当前路径 --%>
	<template:replace name="path"> 
		<ui:combin ref="category.menu.path.simplecategory">
			<ui:varParams
	              modelName="com.landray.kmss.kms.category.model.KmsCategoryMain"
	              moduleTitle="${lfn:message('kms-category:module.kms.category') }"
	              mainHref="/kms/category"
	             extkey="mydoc;docStatus;type;myBookmark" />
			</ui:combin>
	</template:replace>
	<%-- 左边栏 --%>
	<template:replace name="nav">

		<ui:combin ref="menu.nav.title">
			
			<ui:varParam name="total">
				<ui:source type="AjaxJson">
					{url:'/kms/category/kms_category_main_ui/total.jsp'}
				</ui:source>
			</ui:varParam>
			
			<ui:varParam name="title" value="${lfn:message('kms-category:kms.category.knowledge.total') }" />
			
			<ui:varParam name="infonew">
				<ui:source type="AjaxJson">
					{url:'/kms/category/kms_category_main_ui/info.jsp'}
				</ui:source>
			</ui:varParam>
			
			<ui:varParam name="operation">
				<ui:source type="AjaxJson">
					{url:'/kms/category/kms_category_main_ui/operation.jsp'}
				</ui:source>
			</ui:varParam>
		</ui:combin>

		<div class="lui_list_nav_frame">
			<ui:accordionpanel>

				<%-- 分类索引 --%>
				<ui:content
					title="${lfn:message('kms-category:module.kms.category') }">

					<ui:combin ref="menu.nav.simplecategory.flat.all">
						<ui:varParams
							modelName="com.landray.kmss.kms.category.model.KmsCategoryMain"  />
						<ui:varParams
							isHasSearch="true" />
						<%-- <ui:varParams isKeepCriParameter="true" /> --%>
						<ui:varParams criProps="{'cri.q':'template:1'}" />
					</ui:combin>

					<ui:operation
						href="/sys/sc/categoryPreivew.do?method=forward&service=kmsCategoryPreManagerService"
						name="${lfn:message('kms-category:kmsCategory.index.overview') }"
						target="_rIframe" vertical="top" />

				</ui:content>

				<%-- 后台配置 --%>
				<kmss:authShow roles="ROLE_KMSCATEGORY_BACKSTAGE_MANAGER">
					<ui:content title="${ lfn:message('list.otherOpt')}">
						<ui:combin ref="menu.nav.simple">
							<ui:varParam name="source">
								<ui:source type="Static">
				  					[
						  				{
						  					"text" : "${ lfn:message('list.manager') }",
											"href":"/management",
											"router":true,
						  					"icon" : "lui_iconfont_navleft_com_background"
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
	<%-- 右边栏 --%>
	<template:replace name="content">
		<list:criteria>

			<%@ include file="./kms_category_knowledge_index_cri_include.jsp"%>
		</list:criteria>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>

		<%-- 按钮 --%>
		<div class="lui_list_operation">
			<div class="lui_list_operation_order_text">
				${lfn:message('kms-knowledge:kmsKnowledge.list.orderType')}：
			</div>
			<%--排序按钮  --%>
			<div class="lui_list_operation_order_btn">
				<ui:toolbar layout="sys.ui.toolbar.sort">

					<list:sortgroup>
						<c:choose>
							<c:when test="${ param.orderBy=='docReadCount'}">
								<list:sort property="docPublishTime"
									text="${lfn:message('kms-knowledge:kmsKnowledge.docPublishTime') }"
									group="sort.list" />
								<list:sort property="docReadCount"
									text="${lfn:message('kms-knowledge:kmsKnowledge.readCount') }"
									group="sort.list" value="down" />
							</c:when>
							<c:otherwise>

								<list:sort property="docPublishTime"
									text="${lfn:message('kms-knowledge:kmsKnowledge.docPublishTime') }"
									group="sort.list" value="down" />
								<list:sort property="docReadCount"
									text="${lfn:message('kms-knowledge:kmsKnowledge.readCount') }"
									group="sort.list" />

							</c:otherwise>
						</c:choose>

					</list:sortgroup>
				</ui:toolbar>
			</div>

			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>


			<div class="lui_list_operation_toolbar">
				<ui:toolbar count="3" id="knowledge_toolbar">
					<ui:togglegroup order="0" cfg-if="criteria('template')=='1'">
						<ui:toggle icon="lui_icon_s_zaiyao"
							title="${ lfn:message('list.rowTable') }" selected="true"
							group="tg_1" value="rowtable"
							text="${ lfn:message('list.rowTable') }"
							onclick="LUI('listview').switchType(this.value);">
						</ui:toggle>
						<ui:toggle icon="lui_icon_s_tuwen"
							title="${lfn:message('list.gridTable') }" group="tg_1"
							value="gridtable" text="${lfn:message('list.gridTable') }"
							onclick="LUI('listview').switchType(this.value);" >
						</ui:toggle>
						<ui:toggle icon="lui_icon_s_liebiao"
							title="${ lfn:message('list.columnTable') }" group="tg_1"
							value="columntable" text="${ lfn:message('list.columnTable') }"
							onclick="LUI('listview').switchType(this.value);">
						</ui:toggle>
						<ui:toggle icon="lui_icon_s_fujian"
							title="${ lfn:message('kms-knowledge:kmsKnowledge.att')}"
							group="tg_1" value="attmaintable"
							text="${lfn:message('kms-knowledge:kmsKnowledge.att')}"
							onclick="LUI('listview').switchType(this.value);">
						</ui:toggle>
					</ui:togglegroup>
					<ui:togglegroup order="0" cfg-if="criteria('template')=='2'">
						<ui:toggle icon="lui_icon_s_zaiyao"
							title="${ lfn:message('list.rowTable') }" selected="true"
							group="tg_1" value="rowtable"
							text="${ lfn:message('list.rowTable') }"
							onclick="LUI('listview').switchType(this.value);">
						</ui:toggle>
						<ui:toggle icon="lui_icon_s_tuwen"
							title="${lfn:message('list.gridTable') }" group="tg_1"
							value="gridtable" text="${lfn:message('list.gridTable') }"
							onclick="LUI('listview').switchType(this.value);" >
						</ui:toggle>
						<ui:toggle icon="lui_icon_s_liebiao"
							title="${ lfn:message('list.columnTable') }" group="tg_1"
							value="columntable" text="${ lfn:message('list.columnTable') }"
							onclick="LUI('listview').switchType(this.value);">
						</ui:toggle>
					</ui:togglegroup>
					<ui:togglegroup order="0" cfg-if="criteria('template')=='3'">
						<ui:toggle icon="lui_icon_s_zaiyao"
							title="${ lfn:message('list.rowTable') }" selected="true"
							group="tg_1" value="rowtable"
							text="${ lfn:message('list.rowTable') }"
							onclick="LUI('listview').switchType(this.value);">
						</ui:toggle>
						<ui:toggle icon="lui_icon_s_liebiao"
							title="${ lfn:message('list.columnTable') }" group="tg_1"
							value="columntable" text="${ lfn:message('list.columnTable') }"
							onclick="LUI('listview').switchType(this.value);">
						</ui:toggle>
					</ui:togglegroup>

					<%-- 新增删除属性修改--%>
					<%@include file="./kms_category_knowledge_index_button.jsp"%>

				</ui:toolbar>
			</div>
		</div>

		<%--列表视图  --%>
		<list:listview id="listview" style="display:none;">
			<ui:source type="AjaxJson">
				{url:'/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=listChildren&orderby=${fdOrderBy}&ordertype=down&docCategoryId=!{docCategory}'}
			</ui:source>

			<%-- 摘要视图--%>
			<list:rowTable layout="sys.ui.listview.rowtable" name="rowtable"
				onRowClick=""
				rowHref="/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}"
				style="" target="_blank">
				<list:row-template ref="sys.ui.listview.rowtable">
				{
					showOtherProps:"docReadCount;docIntrCount;docEvalCount;docScore"
				}
				</list:row-template>
			</list:rowTable>

			<%-- 列表视图--%>
			<list:colTable layout="sys.ui.listview.columntable"
				name="columntable"
				rowHref="/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}">
				<%@ include
					file="./kms_category_knowledge_col_tmpl.jsp"%>
			</list:colTable>
			<%-- 视图列表 --%>
			<list:gridTable name="gridtable" columnNum="4"
				gridHref="/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}">
				<ui:source type="AjaxJson">
					{url:'/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=listChildren&orderby=${fdOrderBy}&ordertype=down&dataType=pic&rowsize=12&docCategoryId=!{docCategory}'}
				</ui:source>
				<list:row-template ref="sys.ui.listview.landrayblue">
				</list:row-template>
			</list:gridTable>

			<%-- 附件视图--%>
			<list:colTable layout="sys.ui.listview.columntable"
				name="attmaintable"
				rowHref="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}">
				<ui:source type="AjaxJson"> 
					{url:'/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=getSysAttList&orderby=${fdOrderBy}&docCategoryId=!{docCategory}'}
				</ui:source>
				<list:col-serial title="${ lfn:message('page.serial') }"
					headerStyle="width:5%"></list:col-serial>
				<list:col-html
					title="${lfn:message('kms-knowledge:kmsKnowledge.attName')}"
					styleClass="lui_knowledge_attName">
					{$
						<img
						src="${KMSS_Parameter_ResPath}style/common/fileIcon/{% GetIconNameByFileName(row['attName']) %}" />
					<span class="com_subject">{%row['attName']%}</span> 
					$}
				</list:col-html>
				<list:col-html
					title="${lfn:message('kms-knowledge:kmsKnowledge.uploader')}">
					{$
						<span class="com_author">{%row['attCreator']%}</span> 
					$}
				</list:col-html>
				<list:col-auto props="attSize;uploadTime"></list:col-auto>
				<list:col-html
					title="${lfn:message('kms-knowledge:kmsKnowledge.docSubject')}"
					styleClass="lui_knnowledge_subject">
					{$
						<span class="com_subject">{%row['docSubject']%}</span> 
					$}
				</list:col-html>

				<list:col-html style="width:10%">
					{$
						<a class="lui_knowledge_download"
						href="javascript:downloadAttAndLog('{%row['attId']%}');" />
					$}
				</list:col-html>
			</list:colTable>
			<!-- 知识仓库取消推荐用 -->
			<ui:event topic="list.loaded" args="vt">
				var datas = vt.table.kvData;
				window.datas = datas;
				LUI.$("[name='List_Selected']").css("display","none");
			</ui:event>
			
		</list:listview>
		<%-- 列表分页 --%>
		<list:paging></list:paging>

	</template:replace>

	<template:replace name="script">
		<script>
			seajs.use('kms/knowledge/kms_knowledge_ui/style/index.css');

			seajs.use('kms/category/resource/js/button');
			seajs.use('kms/knowledge/kms_knowledge_ui/js/subscribe');

			Com_IncludeFile("fileIcon.js", Com_Parameter.ResPath
					+ "style/common/fileIcon/", "js", true);

			Com_IncludeFile("calendar.js");
			
			
		</script>
	</template:replace>
</template:include>
