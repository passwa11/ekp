<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>

<template:replace name="title">
	<c:out value="${sysNewsMainForm.docSubject} - ${ lfn:message('sys-news:news.moduleName') }"></c:out>
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
					<!-- 如果没置顶显示置顶按钮 -->
					<c:if test="${ sysNewsMainForm.fdIsTop eq 'false'}">
						<ui:button id="setTop"
							text="${lfn:message('sys-news:news.button.setTop')}"
							onclick="setTop(true,'${JsParam.fdId}')"></ui:button>
						</c:if>
						<!-- 如果置顶显示取消置顶按钮 -->
						<c:if test="${ sysNewsMainForm.fdIsTop eq 'true'}">
							<ui:button id="unSetTop"
								text="${lfn:message('sys-news:news.button.unSetTop')}"
								onclick="setTop(false,'${JsParam.fdId}')"></ui:button>
					</c:if>
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
			<kmss:ifModuleExist path="/third/ding/">	
			   <kmss:auth
				requestURL="/third/ding/third_ding_share/send_ding_message.jsp"
				requestMethod="GET">
				    <!-- docSubject 展示的标题名称  选填，默认 docSubject-->
				    <!-- fdModelName 模块名称 ，必填-->
				    <!-- contentMap 工作通知展示的内容-->
				    <c:import url="/third/ding/third_ding_share/send_ding_message.jsp" charEncoding="UTF-8">
					     <c:param name="docSubject">docSubject</c:param>  
					     <c:param name="fdModelName">com.landray.kmss.sys.news.model.SysNewsMain</c:param>  
					     <c:param name="contentMap">docCreator.fdName;docCreateTime</c:param>  
			        </c:import>
				    				
			   </kmss:auth>   
			</kmss:ifModuleExist>
					
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