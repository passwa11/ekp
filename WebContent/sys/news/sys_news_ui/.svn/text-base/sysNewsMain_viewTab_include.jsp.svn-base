<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<c:if test="${param.approveModel eq 'right'}">
		<c:choose>
			<c:when test="${sysNewsMainForm.docStatus>='30' || sysNewsMainForm.docStatus=='00'}">
				<%--流程机制 --%>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysNewsMainForm" />
					<c:param name="fdKey" value="newsMainDoc" />
					<c:param name="approveType" value="right" />
					<c:param name="needInitLbpm" value="true" />
					<c:param name="order" value="${sysNewsMainForm.docStatus>=30 ? 15 : 10}" />
				</c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysNewsMainForm" />
					<c:param name="fdKey" value="newsMainDoc" />
					<c:param name="approveType" value="right" />
					<c:param name="order" value="${sysNewsMainForm.docStatus>=30 ? 15 : 10}" />
				</c:import>
			</c:otherwise>
		</c:choose>
	</c:if>
	<%--收藏机制 --%>
	<c:import url="/sys/bookmark/import/bookmark_bar.jsp" charEncoding="UTF-8">
		<c:param name="fdSubject" value="${sysNewsMainForm.docSubject}" />
		<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
		<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
	</c:import>
	<c:if test="${sysNewsMainForm.fdCanComment ne 'false'}">
		<%--点评机制 --%>
		<c:import url="/sys/evaluation/import/sysEvaluationMain_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysNewsMainForm" />
			<c:param name="order" value="${sysNewsMainForm.docStatus>=30 ? 5 : 10}" />
		</c:import>
	</c:if>
	<%--阅读机制 --%>
	<c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="sysNewsMainForm" />
		<c:param name="order" value="${sysNewsMainForm.docStatus>=30 ? 6 : 10}" />
	</c:import>
	<!-- 匿名机制 -->
	<kmss:authShow roles="ROLE_SYSNEWS_ANONYMPUBLISH">
		<c:if test="${sysNewsMainForm.docStatus eq '30'}">
			<c:import url="/sys/anonym/import/sysAnonym_view.jsp" charEncoding="UTF-8" >
				<c:param name="formName" value="sysNewsMainForm" />
				<c:param name="fdKey" value="sysNewsMainAnonym"/>
			</c:import>
		</c:if>
	</kmss:authShow>
	<%--权限机制 --%>
	<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="sysNewsMainForm" />
		<c:param name="moduleModelName"
			value="com.landray.kmss.sys.news.model.SysNewsMain" />
		<c:param name="order" value="${sysNewsMainForm.docStatus>=30 ? 16 : 10}" />
	</c:import>
	
	<c:if test="${param.approveModel ne 'right'}">
		<%--流程机制 --%>
		<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysNewsMainForm" />
			<c:param name="fdKey" value="newsMainDoc" />
		</c:import>
	</c:if>
	<!-- 分享 -->
	<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewContent.jsp" charEncoding="UTF-8">
			<c:param name="contentType" value="share" />
	</c:import>
			
