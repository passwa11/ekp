<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>

<template:replace name="title">
	新闻管理
</template:replace>
<template:replace name="head">
	<%@ include file="../jsp/nav.jsp" %>
	<%@ include file="../jsp/changeform.jsp" %>
	<script>
		seajs.use("sys/evaluation/import/resource/eval.css")
		seajs.use("sys/attachment/view/img/upload.css")
		seajs.use("sys/attachment/view/img/dnd.css")
	</script>
</template:replace>
<template:replace name="toolbar">
	<c:if test="${sysNewsMainForm.docDeleteFlag ==1}">
		<div id="toolbar" style="display: none"></div>
	</c:if>
	<c:if test="${sysNewsMainForm.docDeleteFlag !=1}">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<c:if test="${sysNewsMainForm.docStatus!='00' }">
				<kmss:auth
					requestURL="/sys/news/sys_news_main/sysNewsMain.do?method=edit&fdId=${param.fdId}"
					requestMethod="GET">
					<ui:button text="${lfn:message('button.edit')}"
						onclick="Com_OpenWindow('sysNewsMain.do?method=edit&fdId=${JsParam.fdId}','_self');"
						order="3">
					</ui:button>
				</kmss:auth>
			</c:if>
			<c:if test="${sysNewsMainForm.docStatus == '30'}">
				<kmss:auth
					requestURL="/sys/news/sys_news_main/sysNewsMain.do?method=setTop&categoryId=${sysNewsMainForm.fdTemplateId}&fdId=${param.fdId}"
					requestMethod="GET">
					<ui:button id="setTop"
						text="${lfn:message('sys-news:news.button.setTop')}"
						onclick="setTop(true,'${JsParam.fdId}')"></ui:button>
					<ui:button id="unSetTop"
						text="${lfn:message('sys-news:news.button.unSetTop')}"
						onclick="setTop(false,'${JsParam.fdId}')"></ui:button>
				</kmss:auth>

			</c:if>
			<kmss:auth
				requestURL="/sys/news/sys_news_main/sysNewsMain.do?method=delete&fdId=${param.fdId}"
				requestMethod="GET">
				<ui:button text="${lfn:message('button.delete')}"
					onclick="deleteDoc('sysNewsMain.do?method=delete&fdId=${JsParam.fdId}');"
					order="3">
				</ui:button>
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="5"
				onclick="Com_CloseWindow();">
			</ui:button>
			<c:if test="${sysNewsMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true' && yqqFlag=='true'&&sysNewsMainForm.fdSignEnable=='true'}">
	     	 <%-- 集成了易企签、勾选了附件选项 --%>
	      	 <ui:button text="${lfn:message('sys-news:sysNews.sign.executionSignature')}" onclick="yqq()" order="2" />
	      	 </c:if>
		</ui:toolbar>
	</c:if>
</template:replace>
<template:replace name="path">
	<ui:combin ref="menu.path.simplecategory">
		<ui:varParams
			moduleTitle="${ lfn:message('sys-news:news.moduleName') }"
			modulePath="/sys/news/"
			modelName="com.landray.kmss.sys.news.model.SysNewsTemplate"
			autoFetch="false" href="/sys/news/"
			categoryId="${sysNewsMainForm.fdTemplateId}" />
	</ui:combin>
</template:replace>