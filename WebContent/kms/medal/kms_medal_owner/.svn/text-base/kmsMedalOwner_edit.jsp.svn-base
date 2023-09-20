<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
		<c:choose>
			<c:when test="${kmsMedalOwnerForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('kms-medal:module.kms.medal') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${kmsMedalOwnerForm.docSubject} - ${ lfn:message('kms-medal:module.kms.medal') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ kmsMedalOwnerForm.method_GET == 'add' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.kmsMedalOwnerForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ kmsMedalOwnerForm.method_GET == 'edit' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.kmsMedalOwnerForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.kmsMedalOwnerForm, 'saveadd');"></ui:button>
				</c:when>
			</c:choose>	
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">			
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('kms-medal:module.kms.medal') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<html:form action="/kms/medal/kms_medal_owner/kmsMedalOwner.do">
			<c:if test="${!empty kmsMedalOwnerForm.docSubject}">
				<p class="txttitle" style="display: none;">${kmsMedalOwnerForm.docSubject }</p>
			</c:if> 
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_simple" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalOwner.docHonoursTime"/>
						</td>
						<td width="35%">
							<xform:datetime property="docHonoursTime" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalOwner.docElement"/>
						</td>
						<td width="35%">
							<xform:address propertyId="docElementId" propertyName="docElementName" orgType="ORG_TYPE_ALL" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalOwner.fdMedal"/>
						</td>
						<td width="35%">
							<xform:select property="fdMedalId">
								<xform:beanDataSource serviceBean="kmsMedalMainService" selectBlock="fdId,fdName" orderBy="" />
							</xform:select>
						</td>
						<td class="td_normal_title" width=15%>&nbsp;</td><td width=35%>&nbsp;</td>
					</tr>
				</table>
			</div>
			<%--
			<ui:tabpage expand="false">
				<!--权限机制 -->
				<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMedalOwnerForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.kms.medal.model.KmsMedalOwner" />
				</c:import>
				
				<!--流程机制 -->
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMedalOwnerForm" />
					<c:param name="fdKey" value="mainDoc" />
				</c:import>
			</ui:tabpage>
		--%>
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />
		</html:form>
		<script>
			$KMSSValidation(document.forms['kmsMedalOwnerForm']);
		</script>
	</template:replace>
	<%--
	<template:replace name="nav">
		<div style="min-width:200px;"></div>
		<ui:accordionpanel style="min-width:200px;"> 
			<ui:content title="${ lfn:message('sys-doc:kmDoc.kmDocKnowledge.docInfo') }" toggle="false">
				<c:import url="/sys/evaluation/import/sysEvaluationMain_view_star.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMedalOwnerForm" />
				</c:import>
				<ul class='lui_form_info'>
					<li><bean:message bundle="kms-medal" key="kmsMedalOwner.docCreator" />：
					<ui:person personId="${kmsMedalOwnerForm.docCreatorId}" personName="${kmsMedalOwnerForm.docCreatorName}"></ui:person></li>
					<li><bean:message bundle="kms-medal" key="kmsMedalOwner.docDept" />：${kmsMedalOwnerForm.docDeptName}</li>
					<li><bean:message bundle="kms-medal" key="kmsMedalOwner.docStatus" />：<sunbor:enumsShow value="${kmsMedalOwnerForm.docStatus}" enumsType="common_status" /></li>
					<li><bean:message bundle="kms-medal" key="kmsMedalOwner.docCreateTime" />：${kmsMedalOwnerForm.docCreateTime }</li>				
				</ul>
			</ui:content>
		</ui:accordionpanel>
	</template:replace>
	--%>
</template:include>
