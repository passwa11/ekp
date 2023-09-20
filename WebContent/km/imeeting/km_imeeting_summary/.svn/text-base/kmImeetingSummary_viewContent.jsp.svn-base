<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:import url="/sys/bookmark/import/bookmark_bar.jsp" charEncoding="UTF-8">
	<c:param name="fdSubject" value="${kmImeetingSummaryForm.fdName}" />
	<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}" />
	<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
</c:import>

<!-- 相关任务 -->
<c:if test="${kmImeetingSummaryForm.docStatus=='30'}">
	<kmss:ifModuleExist  path = "/sys/task/">
		<c:import url="/sys/task/import/sysTaskMain_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingSummaryForm" />
			<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
			<c:param name="order" value="1" />
		</c:import>
	</kmss:ifModuleExist>
</c:if>

<!-- 相关督办 -->
<c:if test="${kmImeetingSummaryForm.docStatus eq '30' or kmImeetingSummaryForm.docStatus eq'31'}">
<kmss:ifModuleExist path="/km/supervise/">
	<c:import url="/km/supervise/import/kmSuperviseMain_edit.jsp"
		charEncoding="UTF-8">
		<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}" />
		<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
		<c:param name="order" value="2" />
	</c:import>
</kmss:ifModuleExist>
</c:if>

<%-- 传阅 --%>
<c:if test="${kmImeetingSummaryForm.docStatus != '00'}">
<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmImeetingSummaryForm" />
	<c:param name="order" value="3" />
</c:import>
</c:if>

<%-- 沉淀记录 --%>
<c:if test="${kmImeetingSummaryForm.docStatus=='30'}">
<c:import url="/kms/multidoc/kms_multidoc_subside/subsideRecord.jsp" charEncoding="UTF-8">
	<c:param name="fdId" value="${kmImeetingSummaryForm.fdId }" />
	<c:param name="order" value="4" />
</c:import>
</c:if>

<!-- 流程处理 -->
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<c:choose>
			<c:when test="${kmImeetingSummaryForm.docStatus>='30' || kmImeetingSummaryForm.docStatus=='00' || kmImeetingSummaryForm.docStatus=='10'}">
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingSummaryForm" />
					<c:param name="fdKey" value="ImeetingSummary" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="needInitLbpm" value="true" />
					<c:param name="order" value="5" />
				</c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingSummaryForm" />
					<c:param name="fdKey" value="ImeetingSummary" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="order" value="5" />
				</c:import>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingSummaryForm" />
			<c:param name="fdKey" value="ImeetingSummary" />
			<c:param name="showHistoryOpers" value="true" />
			<c:param name="isExpand" value="true" />
		</c:import>
	</c:otherwise>
</c:choose>

<%-- 权限--%>
<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmImeetingSummaryForm" />
	<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary"/>
	<c:param name="order" value="6" />
</c:import>

<%-- 阅读纪录 --%>
<c:if test="${kmImeetingSummaryForm.docStatus=='30'}">
<c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmImeetingSummaryForm" />
	<c:param name="order" value="7" />
</c:import>
</c:if>