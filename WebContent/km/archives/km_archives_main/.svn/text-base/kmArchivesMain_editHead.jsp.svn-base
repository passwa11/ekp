<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:replace name="head">
	<style type="text/css">
		.lui_paragraph_title {
			font-size: 15px;
			color: #15a4fa;
			padding: 15px 0px 5px 0px;
		}
		
		.lui_paragraph_title span {
			display: inline-block;
			margin: -2px 5px 0px 0px;
		}
		#kmArchivesAtrSelect {
			display: inline;
			max-width: 230px;
		}
	</style>
	<script type="text/javascript">
		var editOption = {
			formName : 'kmArchivesMainForm',
			modelName : 'com.landray.kmss.km.archives.model.KmArchivesMain',
			templateName : 'com.landray.kmss.km.archives.model.KmArchivesCategory',
			subjectField : 'docSubject',
			mode : 'main_scategory'

		};
		Com_IncludeFile("security.js");
		Com_IncludeFile("domain.js");
		Com_IncludeFile("main_edit.js",
				"${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
	</script>
</template:replace>
<c:if test="${kmArchivesMainForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='') || 'true' eq newEdition}">
	<template:replace name="title">
		<c:choose>
			<c:when test="${kmArchivesMainForm.method_GET == 'add' }">
				<c:out
					value="${ lfn:message('operation.create') } - ${ lfn:message('km-archives:table.kmArchivesMain') }" />
			</c:when>
			<c:otherwise>
				<c:out value="${kmArchivesMainForm.docSubject} - " />
				<c:out value="${ lfn:message('km-archives:table.kmArchivesMain') }" />
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<c:if test="${kmArchivesMainForm.docDeleteFlag ==1}">
			<ui:toolbar id="toolbar" style="display:none;"></ui:toolbar>
		</c:if>
		<c:if test="${kmArchivesMainForm.docDeleteFlag !=1}">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
				<c:choose>
					<c:when test="${ kmArchivesMainForm.method_GET == 'edit' }">
						<c:if
							test="${ kmArchivesMainForm.docStatus=='10' || kmArchivesMainForm.docStatus=='11' }">
							<ui:button text="${ lfn:message('button.savedraft') }"
								onclick="submitForm('10','update');" />
						</c:if>
						<c:if test="${ kmArchivesMainForm.docStatus=='10' || kmArchivesMainForm.docStatus=='11' || kmArchivesMainForm.docStatus=='20' }">
							<c:choose>
								<c:when test="${ param.approveModel eq 'right' }">
									<ui:button text="${ lfn:message('button.submit') }" styleClass="lui_widget_btn_primary" isForcedAddClass="true"
										onclick="submitForm('20','update');" />
								</c:when>
								<c:otherwise>
									<ui:button text="${ lfn:message('button.submit') }" onclick="submitForm('20','update');" />
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:when>
					<c:when test="${ kmArchivesMainForm.method_GET == 'add' }">
						<ui:button text="${ lfn:message('button.savedraft') }" order="2"
							onclick="submitForm('10','save',true);" />
						<c:choose>
							<c:when test="${ param.approveModel eq 'right' }">
								<ui:button text="${ lfn:message('button.submit') }" styleClass="lui_widget_btn_primary" isForcedAddClass="true"
									order="2" onclick="submitForm('20','save');" />
							</c:when>
							<c:otherwise>
								<ui:button text="${ lfn:message('button.submit') }" order="2"
									onclick="submitForm('20','save');" />
							</c:otherwise>
						</c:choose>
					</c:when>
				</c:choose>
				<ui:button text="${ lfn:message('button.close') }" order="5"
					onclick="Com_CloseWindow();" />
			</ui:toolbar>
		</c:if>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }"
				icon="lui_icon_s_home" />
			<ui:menu-item
				text="${ lfn:message('km-archives:table.kmArchivesMain') }" />
			<ui:menu-source autoFetch="false">
				<ui:source type="AjaxJson">
					{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.archives.model.KmArchivesCategory&categoryId=${kmArchivesMainForm.docTemplateId}&currId=!{value}&authType=2&pAdmin=!{pAdmin}"}
				</ui:source>
			</ui:menu-source>
		</ui:menu>
	</template:replace>
</c:if>