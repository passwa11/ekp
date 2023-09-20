<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticSoapMain" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column col="fdName" title="${ lfn:message('tic-soap-connector:ticSoapMain.docSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticSoapMain.fdName}" /></span>
		</list:data-column>
		<list:data-column col="wsBindFunc" title="${ lfn:message('tic-soap-connector:ticSoapMain.wsBindFunc') }" escape="false" style="text-align:center;">
			<c:out value="${ticSoapMain.wsBindFunc}" />
		</list:data-column>
		<list:data-column col="ticSoapSetting.docSubject" title="${ lfn:message('tic-soap-connector:ticSoapMain.wsServerSetting') }" escape="false" style="text-align:center;">
			<c:out value="${ticSoapMain.ticSoapSetting.docSubject}" />
		</list:data-column>
		<list:data-column col="fdCategory.fdName" title="${ lfn:message('tic-soap-connector:ticSoapMain.docCategory') }" escape="false" style="text-align:center;">
			<c:out value="${ticSoapMain.fdCategory.fdName}" />
		</list:data-column>
		<list:data-column col="fdIsAvailable" title="${ lfn:message('tic-soap-connector:ticSoapMain.wsEnable') }" escape="false" style="text-align:center;">
			<sunbor:enumsShow value="${ticSoapMain.fdIsAvailable}" enumsType="common_yesno"/>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
