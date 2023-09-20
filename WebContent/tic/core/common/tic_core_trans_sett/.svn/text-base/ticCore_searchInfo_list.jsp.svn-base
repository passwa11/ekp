<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticCoreTransSettForm, 'deleteall');">
		</kmss:auth>
	</div>

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
				<sunbor:column property="fdName">
					${lfn:message('tic-core-common:ticCoreBusiCate.fdName')}
				</sunbor:column>
				<td>
					${lfn:message('tic-core-common:table.ticCoreBusiCate')}
				</td>
				<sunbor:column property="fdIsAvailable">
					${lfn:message('tic-core-common:ticCoreBusiCate.isEnable')}
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticCoreTransSett" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do" />?method=edit&fdId=${ticCoreTransSett.fdId}&fdAppType=${ticCoreTransSett.fdAppType}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticCoreTransSett.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticCoreTransSett.fdName}" />
				</td>
				<td>
					<c:out value="${ticCoreTransSett.fdCategory.fdName}" />
				</td>
				<td>
					<sunbor:enumsShow value="${ticCoreTransSett.fdIsAvailable}" enumsType="common_yesno" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
