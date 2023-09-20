<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="lblTemplateinfo" value="${ lfn:message('kms-multidoc:kmsMultidocTemplate.lbl.templateinfo') }"></c:set>
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
<ui:ajaxtext>
	<%-- 此处为标题 --%>
	<div data-dojo-block="title">
		<c:out value="${ kmsMultidocKnowledgeForm.docCategoryName }" />
	</div>
	<%-- 此处为内容 --%>
	<div data-dojo-block="content">
	<div data-dojo-type="mui/view/DocScrollableView"
					data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
			<script type="text/javascript">
			   window.docSubject = "${lfn:escapeHtml(kmsMultidocKnowledgeForm.docSubject)}"; <%-- 标题  --%>
			   window.headerItemDatas = [
			        {name: '<bean:message bundle="kms-multidoc" key="kmsMultidoc.docAuthor" />', value: '${kmsMultidocKnowledgeForm.docAuthorName==null?kmsMultidocKnowledgeForm.outerAuthor:kmsMultidocKnowledgeForm.docAuthorName}'}, <%-- 作者  --%>
			        {name: '<bean:message bundle="kms-multidoc" key="kmsMultidoc.form.main.docDeptId" />', value: '${kmsMultidocKnowledgeForm.docDeptName}'}, <%-- 部门  --%>
			        {name: '<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.createTime" />', value: '${kmsMultidocKnowledgeForm.docCreateTime}'} <%-- 创建时间  --%>
			   ];
			</script>
			<%-- 公共头部信息 --%>
			<div data-dojo-type="mui/header/DocViewHeader" 
					 data-dojo-props='subject:window.docSubject,
					 userId:"${kmsMultidocKnowledgeForm.docCreatorId}",
					 userName:"${lfn:escapeJs(kmsMultidocKnowledgeForm.docCreatorName)}",
					 docStatus:"${kmsMultidocKnowledgeForm.docStatus}",
					 itemDatas:window.headerItemDatas'></div>		
					
			 <div data-dojo-type="mui/panel/NavPanel" data-dojo-props="fixedOrder:2">
			 		<div data-dojo-type="mui/panel/Content"
							data-dojo-props="title:'${lfn:message('kms-multidoc:kmsMultidoc.4m.content')}'">
					<%-- 基本信息 --%>
							<div class="muiTabStyle">
								基本信息
							</div>
								<%-- 分类模板 --%>
								<c:choose>
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
								</c:choose>
								<%-- 标签 --%>
							<c:if test="${not empty  kmsMultidocKnowledgeForm.sysTagMainForm.fdTagNames}">
								<div class="muiTabTagName">知识标签</div>
								<div>
										<c:import url="/sys/tag/mobile/import/view.jsp"
											charEncoding="UTF-8">
											<c:param name="formName" value="kmsMultidocKnowledgeForm" />
										</c:import>
								</div>
							</c:if>
					<%-- 文档内容 --%>
							<div class="muiTabStyle" style="margin-top: 1rem;">
								文档内容
							</div>
								<%-- 摘要 --%>
								<c:if
									test="${kmsMultidocKnowledgeForm.fdDescription!=null && kmsMultidocKnowledgeForm.fdDescription!='' }">
									<div class="muiDocSummary muiDocSummaryBottom" style="margin-top: 1rem;">
										<div class="muiDocSummarySign muiSummaryView">${lfn:message('kms-multidoc:kmsMultidocKnowledge.fdDescription') }</div>
										<div class="muiSummaryMain"><c:out value="${ kmsMultidocKnowledgeForm.fdDescription }" /></div>
									</div>
								</c:if>
								<!-- 正文 -->
								<span class="muiDocContent"><xformflag flagtype='xform_rtf' flagid='fd_3861ea18622baa'> <xform:rtf property="docContent" 
										mobile="true" needFilter="true" toolbarSet="Default"></xform:rtf></xformflag>
								</span>
								<%-- 附件 --%>
								<c:import url="/sys/attachment/mobile/import/view.jsp"
									charEncoding="UTF-8">
									<c:param name="formName" value="kmsMultidocKnowledgeForm"></c:param>
									<c:param name="fdKey" value="attachment"></c:param>
									<c:param name="fdViewType" value="simple"></c:param>
								</c:import>
								<%-- 知识属性 --%>
								<c:if test="${not empty kmsMultidocKnowledgeForm.extendFilePath}">
									<div class="muiTabStyle" >
										知识属性
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
					</div>
					<div data-dojo-type="mui/panel/Content"
							data-dojo-props="title:'${lfn:message('kms-multidoc:kmsMultidoc.4m.lbpmlog') }'">
						<div data-dojo-type="mui/panel/Content"
							data-dojo-props="title:'${lfn:message('kms-multidoc:kmsMultidoc.4m.lbpmlog') }'">
							<c:import
								url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp"
								charEncoding="UTF-8">
								<c:param name="fdModelId"
									value="${kmsMultidocKnowledgeForm.fdId }"></c:param>
								<c:param name="fdModelName"
									value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
								<c:param name="formBeanName" value="kmsMultidocKnowledgeForm"></c:param>
							</c:import>
						</div>
						
					</div>
			 </div>
			 
			<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp"
				allowReview="true" editUrl="javascript:building();" 
				viewName="lbpmView" formName="kmsMultidocKnowledgeForm">
				<template:replace name="flowArea">
					<c:import url="/sys/relation/mobile/import/view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmsMultidocKnowledgeForm"></c:param>
						 <c:param name="showOption" value="label"></c:param>
						 <c:param name="showType" value="dialog"></c:param>
					</c:import>
				</template:replace>
			</template:include>
		</div>

			<c:import url="/sys/lbpmservice/mobile/import/view.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="kmsMultidocKnowledgeForm" />
				<c:param name="fdKey" value="mainDoc" />
				<c:param name="lbpmViewName" value="lbpmView" />
				<c:param name="docViewName" value="scrollView"></c:param>
			</c:import>
	</div>
</ui:ajaxtext>