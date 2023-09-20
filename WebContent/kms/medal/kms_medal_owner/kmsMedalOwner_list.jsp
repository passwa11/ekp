<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}//kms/medal/kms_medal_owner/kmsMedalOwner.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=deleteall">
					<ui:button text="${ lfn:message('button.deleteall') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.kmsMedalOwnerForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/kms/medal/kms_medal_owner/kmsMedalOwner.do">
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="kmsMedalOwner.docHonoursTime">
					<bean:message bundle="kms-medal" key="kmsMedalOwner.docHonoursTime"/>
				</sunbor:column>
				<sunbor:column property="kmsMedalOwner.docElement.fdName">
					<bean:message bundle="kms-medal" key="kmsMedalOwner.docElement"/>
				</sunbor:column>
				<sunbor:column property="kmsMedalOwner.fdMedal.fdName">
					<bean:message bundle="kms-medal" key="kmsMedalOwner.fdMedal"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="kmsMedalOwner" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/kms/medal/kms_medal_owner/kmsMedalOwner.do" />?method=view&fdId=${kmsMedalOwner.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${kmsMedalOwner.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<kmss:showDate value="${kmsMedalOwner.docHonoursTime}" />
				</td>
				<td>
					<c:out value="${kmsMedalOwner.docElement.fdName}" />
				</td>
				<td>
					<c:out value="${kmsMedalOwner.fdMedal.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>
