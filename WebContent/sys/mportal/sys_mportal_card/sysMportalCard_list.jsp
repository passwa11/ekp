<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/sys/mportal/sys_mportal_card/sysMportalCard.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/sys/mportal/sys_mportal_card/sysMportalCard.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/mportal/sys_mportal_card/sysMportalCard.do?method=deleteall">
					<ui:button text="${ lfn:message('button.deleteall') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysMportalCardForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/sys/mportal/sys_mportal_card/sysMportalCard.do">
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="20pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="sysMportalCard.fdName" >
					<bean:message bundle="sys-mportal" key="sysMportalCard.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysMportalCard.fdEnabled">
					<bean:message bundle="sys-mportal" key="sysMportalCard.fdEnabled"/>
				</sunbor:column>
				<sunbor:column property="sysMportalCard.docCreator.fdName">
					<bean:message bundle="sys-mportal" key="sysMportalCard.docCreator"/>
				</sunbor:column>
				<sunbor:column property="sysMportalCard.docCreateTime">
					<bean:message bundle="sys-mportal" key="sysMportalCard.docCreateTime"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysMportalCard" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/mportal/sys_mportal_card/sysMportalCard.do" />?method=edit&fdId=${sysMportalCard.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysMportalCard.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td width="40%">
					<c:out value="${sysMportalCard.fdName}" />
				</td>
				<td>
					<sunbor:enumsShow 
							enumsType="common_yesno" 
							value="${sysMportalCard.fdEnabled}" >
					</sunbor:enumsShow>
				</td>
				<td>
					<c:out value="${sysMportalCard.docCreator.fdName}" />
				</td>
				<td>
					<kmss:showDate value="${sysMportalCard.docCreateTime}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
	</template:replace>
</template:include>