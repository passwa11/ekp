<%@ page import="com.landray.kmss.kms.knowledge.service.spring.KmsKnowledgeCompareProvider" %>
<%@ page import="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCompareConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 文档属性 -->
<c:if test="${not empty kmsMultidocKnowledgeForm.extendFilePath}">
	<ui:content title="${lfn:message('kms-multidoc:kmsMultidocKnowledge.fdProperty') }">
		<table class="tb_simple"  width="100%" >
			<c:import url="/sys/property/include/sysProperty_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsMultidocKnowledgeForm" />
				<c:param name="fdDocTemplateId" value="${kmsMultidocKnowledgeForm.docCategoryId}" />
			</c:import>
		</table>
	</ui:content>
</c:if>

<!-- 点评 -->
<!-- 判断是否有外部作者，如果是外部作者不需要发通知给作者，是内部作者就需要发通知给作者-->
<c:choose>
	<c:when test="${not empty kmsMultidocKnowledgeForm.docAuthorId}">
		<c:import url="/sys/evaluation/import/sysEvaluationMain_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmsMultidocKnowledgeForm" />
			<c:param name="areaName" value="docContent" />
			<c:param name="notifyOtherName" value="fdDocAuthorList" />
			<c:param name="key" value="sysEvaluationMain.isNotifyAuthorName" />
			<c:param name="bundel" value="sys-evaluation" />
		</c:import>
	</c:when>
	<c:otherwise>
		<c:import url="/sys/evaluation/import/sysEvaluationMain_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmsMultidocKnowledgeForm" />
			<c:param name="areaName" value="docContent" />
		</c:import>
	</c:otherwise>
</c:choose>

<!-- 推荐 -->
<c:import url="/sys/introduce/import/sysIntroduceMain_view.jsp" charEncoding="UTF-8">
    <c:param name="fdCateModelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
    <c:param name="fdCateModelId" value="${kmsMultidocKnowledgeForm.docCategoryId}" />
	<c:param name="formName" value="kmsMultidocKnowledgeForm" />
	<c:param name="fdKey" value="mainDoc" />
	<c:param name="toEssence" value="true" />
	<c:param name="toNews" value="true" />
	<c:param name="docSubject" value="${fn:escapeXml(kmsMultidocKnowledgeForm.docSubject)}" />
	<c:param name="docCreatorName" value="${kmsMultidocKnowledgeForm.docCreatorName}" />
	<c:param name="fdModelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
	<c:param name="fdModelId" value="${kmsMultidocKnowledgeForm.fdId}" />
</c:import>
<!-- 阅读记录 -->
<c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmsMultidocKnowledgeForm" />
</c:import>
<!-- 权限 -->
<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmsMultidocKnowledgeForm" />
	<c:param name="moduleModelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
</c:import>
<c:if test="${kms_professional}">
	<c:if test="${kmsMultidocKnowledgeForm.docStatus>='30' && isHasNewVersion != true }">
		<kmss:ifModuleExist path="/kms/learn">
			<c:import url="/kms/learn/kms_learn_courseware/import/KmsLearnCoursewarePublishMain_view.jsp" charEncoding="UTF-8">
				<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
				<c:param name="modelId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
				<c:param name="authorIds" value="${authorIds}"></c:param>
				<c:param name="authorNames" value="${authorNames}"></c:param>
				<c:param name="outerAuthorName" value="${outerAuthorName}"></c:param>
				<c:param name="toolbarOrder" value="4" />
			</c:import>
		</kmss:ifModuleExist>
	</c:if>
</c:if>
<c:if test="${kmsMultidocKnowledgeForm.docDeleteFlag !=1}">
	<c:choose>
		<c:when test="${JsParam.approveModel eq 'right'}">
			<%--版本 --%>
			<c:import url="/sys/edition/import/sysEditionMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsMultidocKnowledgeForm" />
			</c:import>
			<c:if test="${kmsMultidocKnowledgeForm.docIsNewVersion!=false}">
				<%--发布机制 --%>
				<c:import url="/sys/news/import/sysNewsPublishMain_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
				</c:import>
				<!-- 收藏 -->
				<c:import url="/sys/bookmark/import/bookmark_bar.jsp" charEncoding="UTF-8">
					<c:param name="fdSubject" value="${fn:escapeXml(kmsMultidocKnowledgeForm.docSubject)}" />
					<c:param name="fdModelId" value="${kmsMultidocKnowledgeForm.fdId}" />
					<c:param name="toolbarOrder" value="1" />
					<c:param name="fdModelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
				</c:import>
   			</c:if>
		</c:when>
		<c:otherwise>
			<%--版本 --%>
			<c:import url="/sys/edition/import/sysEditionMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsMultidocKnowledgeForm" />
				<c:param name="toolbarOrder" value="1" />
			</c:import>
			<c:if test="${kmsMultidocKnowledgeForm.docIsNewVersion!=false}">
				<%--发布机制 --%>
				<c:import url="/sys/news/import/sysNewsPublishMain_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
				</c:import>
				<!-- 收藏 -->
				<c:import url="/sys/bookmark/import/bookmark_bar.jsp" charEncoding="UTF-8">
					<c:param name="fdSubject" value="${fn:escapeXml(kmsMultidocKnowledgeForm.docSubject)}" />
					<c:param name="fdModelId" value="${kmsMultidocKnowledgeForm.fdId}" />
					<c:param name="toolbarOrder" value="2" />
					<c:param name="fdModelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
				</c:import>
			</c:if>
		</c:otherwise>
	</c:choose>
</c:if>

<c:choose>
	<c:when test="${JsParam.approveModel eq 'right'}">
		<c:choose>
			<c:when test="${kmsMultidocKnowledgeForm.docStatus>='30' || kmsMultidocKnowledgeForm.docStatus=='00'}">
				<%-- 流程 --%>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="fdKey" value="mainDoc" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="needInitLbpm" value="true" />
				</c:import>
			</c:when>
			<c:otherwise>
				<%-- 流程 --%>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="fdKey" value="mainDoc" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<%--流程 --%>
		<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmsMultidocKnowledgeForm" />
			<c:param name="fdKey" value="mainDoc" />
		</c:import>
	</c:otherwise>
</c:choose>
<%
	boolean _providerExist = KmsKnowledgeCompareProvider.queryExist();
	request.setAttribute("_providerExist", _providerExist);
	KmsKnowledgeCompareConfig compareCfg = new KmsKnowledgeCompareConfig();
	boolean _compareEnable = compareCfg.queryEnabled();
	request.setAttribute("_compareEnable", _compareEnable);
	String _compareProvider = compareCfg.queryProvider();
	request.setAttribute("_compareProvider", _compareProvider);
%>
<c:if test="${kms_professional && _providerExist && _compareEnable && (not empty _compareProvider) && kmsMultidocKnowledgeForm.docStatus =='30'}">
	<ui:content title="${lfn:message('kms-knowledge:kmsKnowledgeCompare.tab.name') }">
		<c:import url="/kms/knowledge/kms_knowledge_compare/kmsKnowledgeCompare.jsp" charEncoding="UTF-8">
			<c:param name="fdModelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
			<c:param name="fdModelId" value="${kmsMultidocKnowledgeForm.fdId}" />
			<c:param name="fdKey" value="mainOnline" />
			<c:param name="formBeanName" value="kmsMultidocKnowledgeForm" />
			<c:param name="provider" value="${_compareProvider}" />
			<c:param name="period" value="apply" />
		</c:import>
	</ui:content>
</c:if>
<c:if test="${kms_professional}">
	<!-- 纠错 -->
	<c:if test="${kmsMultidocKnowledgeForm.docStatus == '30'}">
		<kmss:auth requestURL="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=list&fdModelId=${param.fdId}&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc">
			<ui:content
					title="<div id='error_correction_record_title'>${lfn:message('kms-common:kmsCommonDocErrorCorrection.notes') }${correctionCount}</div>">
				<list:listview id="all_error" channel="all_error">
					<ui:source type="AjaxJson">
						{url:'/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=list&fdModelId=${param.fdId}&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge&ordertype=down&rowsize=10'}
					</ui:source>
					<list:colTable layout="sys.ui.listview.columntable"
								   rowHref="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=viewinfo&fdId=!{fdId}"
								   style="" target="_blank" cfg-norecodeLayout="simple">
						<%-- <list:col-checkbox name="List_Selected" style="width:5%"></list:col-checkbox> --%>
						<list:col-serial title="${ lfn:message('page.serial') }"
										 headerStyle="width:5%"></list:col-serial>
						<list:col-html style="text-align:left" headerStyle="width:40%"
									   title="${lfn:message('kms-common:kmsCommonDocErrorCorrection.fdCorrectionOpinions') }">
							{$ {%row['docDescription']%} $}
						</list:col-html>
						<list:col-html headerStyle="width:10%"
									   title="${lfn:message('sys-doc:sysDocBaseInfo.docStatus') }">
							{$ {%row['docStatus']%} $}
						</list:col-html>
						<list:col-html headerStyle="width:10%"
									   title="${lfn:message('kms-common:kmsCommonDocErrorCorrection.creatorName') }">
							{$ {%row['docCreator.fdName']%} $}
						</list:col-html>
						<list:col-html headerStyle="width:14%"
									   title="${lfn:message('kms-common:kmsCommonDocErrorCorrection.correctionTime') }">
							{$ {%row['docCreateTime']%} $}
						</list:col-html>
						<kmss:auth
								requestURL="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=delete&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc&categoryId=${kmsMultidocKnowledgeForm.docCategoryId}&fdModelId=${kmsMultidocKnowledgeForm.fdId}"
								requestMethod="GET">
							<list:col-html headerStyle="width:10%" title="">
								{$  <a href="javascript:void(0)"
									   onclick="delErrorCorrection( '{%row['fdId']%}' );">${lfn:message('button.delete')}</a> $}
							</list:col-html>
						</kmss:auth>
					</list:colTable>
				</list:listview>
				<div style="height: 15px;"></div>
				<list:paging layout="sys.ui.paging.simple" channel="all_error"></list:paging>
			</ui:content>
		</kmss:auth>
	</c:if>
</c:if>
