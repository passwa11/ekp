<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/core/mapping/tic_core_mapping_func/ticCoreMappingFunc.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/core/mapping/tic_core_mapping_func/ticCoreMappingFunc.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/core/mapping/tic_core_mapping_func/ticCoreMappingFunc.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/core/mapping/tic_core_mapping_func/ticCoreMappingFunc.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticCoreMappingFuncForm, 'deleteall');">
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
				<sunbor:column property="ticCoreMappingFunc.fdTemplateId">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdTemplateId"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdInvokeType">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdInvokeType"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdOrder">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdOrder"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdFuncMark">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdFuncMark"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdRfcImport">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdRfcImport"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdRfcExport">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdRfcExport"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdJspSegmen">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdJspSegmen"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdQuartzId">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdQuartzId"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdUse">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdUse"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdQuartzTime">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdQuartzTime"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdRfcSetting.docSubject">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdRfcSetting"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingFunc.fdMain.docSubject">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdMain"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticCoreMappingFunc" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/core/mapping/tic_core_mapping_func/ticCoreMappingFunc.do" />?method=view&fdId=${ticCoreMappingFunc.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticCoreMappingFunc.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdTemplateId}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdInvokeType}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdOrder}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdFuncMark}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdRfcImport}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdRfcExport}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdJspSegmen}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdQuartzId}" />
				</td>
				<td>
					<sunbor:enumsShow value="${ticCoreMappingFunc.fdUse}" enumsType="common_yesno" />
				</td>
				<td>
					<kmss:showDate value="${ticCoreMappingFunc.fdQuartzTime}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdRfcSetting.docSubject}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingFunc.fdMain.docSubject}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
