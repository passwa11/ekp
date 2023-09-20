<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.ui.util.PcJsOptimizeUtil" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="preloadJs">
	<c:choose>
		<c:when test="${compressSwitch eq 'true' && lfn:jsCompressEnabled('kmReviewCompressExecutor', 'km_review_view_comJs_combined')}">
			<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("kmReviewCompressExecutor","km_review_view_comJs_combined") %>?s_cache=${ LUI_Cache }">
			</script>
		</c:when>
	</c:choose>
	<%--常用多语言缓存--%>
	<c:import url="/km/review/resource/cache/kmReviewMain_view_resource_cache.jsp"></c:import>
</template:replace>
<template:replace name="title">
	<c:out value="${kmReviewMainForm.docSubject}-${ lfn:message('km-review:module.km.review')}"></c:out>
</template:replace>
<template:replace name="toolbar">
<c:if test="${kmReviewMainForm.docDeleteFlag ==1}">
	<ui:toolbar id="toolbar" style="display:none;" count="6"></ui:toolbar>
</c:if>
<c:if test="${kmReviewMainForm.docDeleteFlag !=1}">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6" var-navwidth="90%" style="display:none;">
	<c:if test="${kmReviewMainForm.method_GET=='view' }">
			<c:if test="${kmReviewMainForm.docStatus=='10' || kmReviewMainForm.docStatus=='11'|| kmReviewMainForm.docStatus=='20'}">
				<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
					<ui:button order="2" text="${ lfn:message('button.edit') }" 
						onclick="editDoc();">
					</ui:button>
					<c:set var="isReadOnly" value="false"/>
					<c:set var="editStatus" value="true"/>
				</kmss:auth>
			</c:if>
			<c:if test="${kmReviewMainForm.docStatus=='30' || kmReviewMainForm.docStatus=='31'}">	
				<!-- 实施反馈 -->
				<kmss:auth requestMethod="GET"
					requestURL="/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=add&fdMainId=${param.fdId}&fdCreatorId=${kmReviewMainForm.docCreatorId}">
					<ui:button order="4" text="${ lfn:message('km-review:button.feedback.info') }" 
						onclick="feedback();">
					</ui:button>
				</kmss:auth>
				<c:if test="${kmReviewMainForm.fdFeedbackExecuted!='1' && kmReviewMainForm.fdFeedbackModify=='1'}">
					<kmss:auth requestURL="/km/review/km_review_main/kmReviewChangeFeedback.jsp?fdId=${param.fdId}"	requestMethod="GET">
						<!-- 指定反馈人 -->
						<ui:button order="4" text="${ lfn:message('km-review:button.feedback.people') }" 
							onclick="appointFeedback();">
						</ui:button>
					</kmss:auth>
				</c:if>
				<!-- 修改权限
				<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=editRight&fdId=${param.fdId}" requestMethod="GET">
					<ui:button order="4" text="${ lfn:message('km-review:button.modify.permission') }" 
						onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}km/review/km_review_main/kmReviewMain.do?method=editRight&fdId=${param.fdId }');">
					</ui:button>
				</kmss:auth>
			    -->
			    
			    <c:set var="isReadOnly" value="true"/>
			    <c:set var="editMode" value="view"/>
			    <c:set var="editStatus" value="false"/>
			</c:if>
			<c:if test="${(kmReviewMainForm.docStatus=='30' or kmReviewMainForm.docStatus=='31') and (empty kmReviewMainForm.fdIsFiling or !kmReviewMainForm.fdIsFiling)}">
				<%-- 老的归档机制 废弃 author:ouyu  start --%>
				<!-- 归档 -->
				<%--<c:import url="/km/archives/include/kmArchivesFileButton.jsp" charEncoding="UTF-8">
					<c:param name="fdId" value="${param.fdId}" />
					<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
					<c:param name="serviceName" value="kmReviewMainService" />
					<c:param name="userSetting" value="true" />
					<c:param name="cateName" value="fdTemplate" />
					<c:param name="moduleUrl" value="km/review" />
					<c:param name="enable" value="${enableModule.enableKmArchives eq 'false' ? 'false' : 'true'}"></c:param>
				</c:import>--%>
				<%-- 老的归档机制 废弃 author:ouyu  end --%>
				<%-- 新的归档机制  author:ouyu  start 2022-6-1--%>
				<c:import url="/sys/archives/include/sysArchivesFileButton.jsp" charEncoding="UTF-8">
					<c:param name="fdId" value="${param.fdId}" />
					<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
					<c:param name="serviceName" value="kmReviewMainService" />
					<c:param name="userSetting" value="true" />
					<c:param name="cateName" value="fdTemplate" />
					<c:param name="moduleUrl" value="km/review" />
					<c:param name="enable" value="${enableModule.enableKmArchives eq 'false' ? 'false' : 'true'}"></c:param>
				</c:import>
				<%-- 新的归档机制  author:ouyu  end 2022-6-1--%>
			</c:if>
			<c:if test="${enableModule.enableKmsMultidoc ne 'false' && (kmReviewMainForm.docStatus=='30' or kmReviewMainForm.docStatus=='31')}">
				<!-- 沉淀-->
				<kmss:auth requestURL="/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do?method=fileDoc&modelName=com.landray.kmss.km.review.model.KmReviewMain&fdId=${param.fdId}" requestMethod="GET">
					<%@ include file="/kms/multidoc/kms_multidoc_subside/subsideButton.jsp" %>
				</kmss:auth>
			</c:if>
			<c:if test="${kmReviewMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true' && yqqFlag=='true' && kmReviewMainForm.fdSignEnable=='true'}">
	     	 <%-- 集成了易企签、勾选了附件选项 --%>
	      	 <ui:button text="${lfn:message('km-review:kmReviewMain.sign.executionSignature')}" onclick="yqq()" order="2" />
			</c:if>
		</c:if>
		<!-- 打印 -->
		<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=print&fdId=${param.fdId}&s_xform=${kmReviewMainForm.sysWfBusinessForm.fdSubFormId}" requestMethod="GET">
			<ui:button order="4" text="${ lfn:message('km-review:button.print') }" 
				onclick="printDoc();">
			</ui:button>
		</kmss:auth>
		<%
		String isTstudyEnabled = ResourceUtil.getKmssConfigString("sys.tstudy.enable");
		LbpmSetting lbpmSetting = new LbpmSetting();
		if(lbpmSetting.getIsHandSignatureEnabled().equalsIgnoreCase("true") 
				&& "true".equalsIgnoreCase(isTstudyEnabled)
				&& lbpmSetting.getHandSignatureType().equalsIgnoreCase("tsd")){
		%>
		<!-- 点阵打印 -->
		<ui:button order="4" text="${ lfn:message('sys-lbpmservice-support:lbpmSetting.handSignatureType.print') }" 
			onclick="printLattice();">
		</ui:button>
		<%}%>
	    <%-- 删除 --%>
		<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<ui:button  order="4" text="${ lfn:message('button.delete') }" 
				onclick="deleteDoc('${LUI_ContextPath}/km/review/km_review_main/kmReviewMain.do?method=delete&fdId=${param.fdId}');">
			</ui:button>
		</kmss:auth> 
		<%-- 复制--%>
		<c:if test="${isTempAvailable && 'true' eq kmReviewMainForm.fdIsCopyDoc}">
			<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=add&fdReviewId=${param.fdId}&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('km-review:kmReviewMain.copy') }" order="5" onclick="copyDoc('${LUI_ContextPath}/km/review/km_review_main/kmReviewMain.do?method=add&fdReviewId=${param.fdId}&fdTemplateId=${kmReviewMainForm.fdTemplateId}');">
				</ui:button>
			</kmss:auth>
		</c:if>
		<ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
		</ui:button>
	</ui:toolbar>
	</c:if>
</template:replace>
<template:replace name="path">
	<c:choose>
		<c:when test="${!isTempAvailable}">
			<ui:menu layout="sys.ui.menu.nav">
				<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="javascript:top.open('${LUI_ContextPath }/','_self')" target="_self">
				</ui:menu-item>
				<ui:menu-item text="${ lfn:message('km-review:module.km.review') }" href="/km/review/" target="_self">
				</ui:menu-item>
				<c:forEach var="path" items="${templatePath}">
					<ui:menu-item text="${path}">
					</ui:menu-item>
				</c:forEach>
			</ui:menu>
		</c:when>
		<c:otherwise>
			<ui:combin ref="menu.path.category">
				<ui:varParams moduleTitle="${ lfn:message('km-review:module.km.review') }"
				    modulePath="/km/review/" 
					modelName="com.landray.kmss.km.review.model.KmReviewTemplate"
				    autoFetch="false" 
				    extHash="j_path=/listAll&mydoc=all&cri.q=fdTemplate:!{value}"
				    href="/km/review/"
					categoryId="${kmReviewMainForm.fdTemplateId}" />
			</ui:combin>
		</c:otherwise>
	</c:choose>
</template:replace>	