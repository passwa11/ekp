<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"
			var-navwidth="95%">
			<kmss:auth
				requestURL="/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do?method=add">
				<ui:button text="${ lfn:message('button.add') }"
					onclick="Com_OpenWindow('${LUI_ContextPath}/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do?method=add');">
				</ui:button>
			</kmss:auth>
			<kmss:auth
				requestURL="/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do?method=deleteall">
				<ui:button text="${ lfn:message('button.delete') }"
					onclick="if(!List_ConfirmDel())return;Com_Submit(document.dbEchartsTemplateForm, 'deleteall');">
				</ui:button>
			</kmss:auth>
		</ui:toolbar>
	</template:replace>

	<template:replace name="content">

		<html:form
			action="/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do">
			<c:if test="${queryPage.totalrows==0}">
				<%@ include file="/resource/jsp/list_norecord.jsp"%>
			</c:if>
			<c:if test="${queryPage.totalrows>0}">
				<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
				<table id="List_ViewTable">
					<tr>
						<sunbor:columnHead htmlTag="td">
							<td width="10pt"><input type="checkbox" name="List_Tongle">
							</td>
							<td width="40pt"><bean:message key="page.serial" /></td>
							<sunbor:column property="dbEchartsTemplate.fdName">
								<bean:message bundle="dbcenter-echarts"
									key="dbEchartsTemplate.fdName" />
							</sunbor:column>
							<sunbor:column property="dbEchartsTemplate.docCreateTime">
								<bean:message bundle="dbcenter-echarts"
									key="dbEchartsTemplate.docCreateTime" />
							</sunbor:column>
							<sunbor:column property="dbEchartsTemplate.fdParent.fdName">
								<bean:message bundle="dbcenter-echarts"
									key="dbEchartsTemplate.fdParent" />
							</sunbor:column>
							<sunbor:column property="dbEchartsTemplate.docCreator.fdName">
								<bean:message bundle="dbcenter-echarts"
									key="dbEchartsTemplate.docCreator" />
							</sunbor:column>
						</sunbor:columnHead>
					</tr>
					<c:forEach items="${queryPage.list}" var="dbEchartsTemplate"
						varStatus="vstatus">
						<tr
							kmss_href="<c:url value="/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do" />?method=view&fdId=${dbEchartsTemplate.fdId}">
							<td><input type="checkbox" name="List_Selected"
								value="${dbEchartsTemplate.fdId}"></td>
							<td>${vstatus.index+1}</td>
							<td><c:out value="${dbEchartsTemplate.fdName}" /></td>
							<td><kmss:showDate
									value="${dbEchartsTemplate.docCreateTime}" /></td>
							<td><c:out value="${dbEchartsTemplate.fdParent.fdName}" />
							</td>
							<td><c:out value="${dbEchartsTemplate.docCreator.fdName}" />
							</td>
						</tr>
					</c:forEach>
				</table>
				<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
			</c:if>
		</html:form>

	</template:replace>
</template:include>