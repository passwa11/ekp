<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="lblTemplateinfo" value="${ lfn:message('kms-multidoc:kmsMultidocTemplate.lbl.templateinfo') }"></c:set>
<c:if test="${kms_professional}">
	<%
		KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
		String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled)) {
	%>
		<c:set var="kmsCategoryEnabled" value="true"></c:set>
		<c:set var="lblTemplateinfo" value="${ lfn:message('kms-multidoc:kmsMultidocTemplate.lbl.templateinfo.categoryTrue') }"></c:set>

	<%
		}
	%>
</c:if>
<ui:ajaxtext>
	<%-- 此处为标题 --%>
	<div data-dojo-block="title">
		<c:out value="${ kmsMultidocKnowledgeForm.docCategoryName }" />
	</div>
	<%-- 此处为内容 --%>
	<div data-dojo-block="content">
	<div data-dojo-type="mui/view/DocScrollableView" id="scrollView">
			<div class="muiDocFrame">
			
				<c:import url="/kms/multidoc/mobile/banner.jsp" charEncoding="UTF-8">
					<c:param name="formBeanName" value="kmsMultidocKnowledgeForm"></c:param>
				</c:import>
				<c:if test="${kmsMultidocKnowledgeForm.docContent != null && kmsMultidocKnowledgeForm.docContent != ''}">
								<span class="muiDocContent"> <xform:rtf property="docContent" 
										mobile="true"></xform:rtf>
								</span>
				</c:if>
				<%-- 附件 --%>
				<c:if test="${ownAttachment }">
				<div class="muiTabStyle">
					${ lfn:message('kms-multidoc:kmsMultidoc.attList') }
				</div>
				</c:if>
				<c:import url="/sys/attachment/mobile/import/view.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm"></c:param>
					<c:param name="fdKey" value="attachment"></c:param>
					<c:param name="fdViewType" value="simple"></c:param>
				</c:import>
				<%-- 基本信息 --%>
				<div class="muiTabStyle">
					<span>${ lfn:message('kms-multidoc:kmsMultidocKnowledge.docInfo') }</span>
							<div class="mulWikiCate" style="margin-top: 10px;">
								<p>${lfn:message('kms-multidoc:kmsMultidocTemplate.docCategory')}
								 <span style="margin-left: 180px;">
									<c:out value="${kmsMultidocKnowledgeForm.docCategoryName}"></c:out>
								 </span>
								</p>
							</div>
				</div>

					<%-- 分类模板 --%>
					<%--<c:choose>
						<c:when test="${kmsCategoryEnabled}">
							<c:import url="/kms/multidoc/mobile/category.jsp" charEncoding="utf-8">
								<c:param name="docSecondCategoriesIds" value="${kmsMultidocKnowledgeForm.docSecondCategoriesIds}"></c:param>
								<c:param name="docSecondCategoriesNames" value="${kmsMultidocKnowledgeForm.docSecondCategoriesNames}"></c:param>
								<c:param name="kmsCategoryIds" value="${kmsMultidocKnowledgeForm.kmsCategoryKnowledgeRelForm.kmsCategoryIds}"></c:param>
								<c:param name="kmsCategoryNames" value="${kmsMultidocKnowledgeForm.kmsCategoryKnowledgeRelForm.kmsCategoryNames}"></c:param>
							</c:import>
						</c:when>
						<c:otherwise>
								<c:import url="/kms/multidoc/mobile/category.jsp" charEncoding="utf-8">
								<c:param name="docSecondCategoriesIds" value="${kmsMultidocKnowledgeForm.docSecondCategoriesIds}"></c:param>
								<c:param name="docSecondCategoriesNames" value="${kmsMultidocKnowledgeForm.docSecondCategoriesNames}"></c:param>
								<c:param name="kmsCategoryIds" value=""></c:param>
								<c:param name="kmsCategoryNames" value=""></c:param>
								</c:import>
						</c:otherwise>
					</c:choose>--%>
					<%-- 标签 --%>
				<c:if test="${not empty  kmsMultidocKnowledgeForm.sysTagMainForm.fdTagNames}">
					<div class="muiTabTagName">${ lfn:message('kms-multidoc:kmsMultidocKnowledge.tags') }</div>
					<div>
							<c:import url="/sys/tag/mobile/import/view.jsp"
								charEncoding="UTF-8">
								<c:param name="formName" value="kmsMultidocKnowledgeForm" />
							</c:import>
					</div>
				</c:if>
				<c:if test="${ empty  kmsMultidocKnowledgeForm.sysTagMainForm.fdTagNames}">
					<div class="muiTabTagName">${ lfn:message('kms-multidoc:kmsMultidocKnowledge.tags') }</div>
					<div>
						<div class="muiTabTagName">${ lfn:message('kms-multidoc:kmsMultidocKnowledge.notSet') }</div>
					</div>
				</c:if>
				<c:if test="${kms_professional}">
					<%--知识属性 --%>
					<c:if test="${not empty kmsMultidocKnowledgeForm.extendFilePath}">
						<div class="muiTabStyle">
						${ lfn:message('kms-multidoc:kmsMultidocKnowledge.fdProperty') }
						</div>
						<div>
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<c:import url="/sys/property/include/sysProperty_pda.jsp"
									charEncoding="UTF-8">
									<c:param name="formName" value="kmsMultidocKnowledgeForm" />
								</c:import>
							</table>
						</div>
					</c:if>
				</c:if>
			</div>

			<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp"
				allowReview="true" editUrl="javascript:building();"
				viewName="lbpmView" formName="kmsMultidocKnowledgeForm">
				<template:replace name="flowArea">
					<c:import url="/sys/relation/mobile/import/view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmsMultidocKnowledgeForm"></c:param>
					</c:import>
				</template:replace>

				<template:replace name="publishArea">
					<!-- 点评 -->					
					<c:choose>
						<c:when test="${fn:length(kmsMultidocKnowledgeForm.fdDocAuthorList)  > 0}">
							<c:import url="/sys/evaluation/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmsMultidocKnowledgeForm" />
								<c:param name="notifyOtherName" value="fdDocAuthorList" />
								<c:param name="docFlag" value="true" />
								<c:param name="notifyOtherNameText" value="${lfn:message('kms-multidoc:kmsMultidoc.Author') }"/>
							</c:import>
						</c:when>
						<c:otherwise>
							<c:import url="/sys/evaluation/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmsMultidocKnowledgeForm" />
							</c:import>
						</c:otherwise>
					</c:choose>
					
					<!-- 推荐 -->
					<c:import url="/sys/introduce/mobile/import/view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmsMultidocKnowledgeForm"></c:param>
						<c:param name="docFlag" value="true" />
					</c:import>
					<!-- 收藏 -->
					<c:import url="/sys/bookmark/mobile/import/view.jsp"
						charEncoding="UTF-8">
						<c:param name="fdSubject"
							value="${kmsMultidocKnowledgeForm.docSubject}" />
						<c:param name="fdModelId" value="${kmsMultidocKnowledgeForm.fdId}" />
						<c:param name="fdModelName"
							value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
					</c:import>
					<c:import url="/sys/relation/mobile/import/view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmsMultidocKnowledgeForm"></c:param>
						<c:param name="isButtonGroup" value="false"></c:param>
					</c:import>
				</template:replace>
			</template:include>
		</div>
	</div>
</ui:ajaxtext>