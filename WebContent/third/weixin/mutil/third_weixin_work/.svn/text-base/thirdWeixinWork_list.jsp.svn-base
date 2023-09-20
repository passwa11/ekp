<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%
	String fdWxKey = (String)request.getAttribute("fdWxKey");
	request.setAttribute("fdWxKey", fdWxKey);
%>
<style type="text/css">
.txtlistpath {
    display: none !important;
    background: url(dot.gif) repeat-x left bottom;
    height: 20px;
    padding: 0 0 5px 0;
    color: #2792C6;
    line-height: 20px;
    margin: 10px 0 0;
    text-align: left;
}
</style>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%" count="5">
				<kmss:authShow roles="ROLE_THIRDWXWORK_ADMIN">
					<ui:button text="${ lfn:message('third-weixin:domain.check') }"
						onclick="Com_OpenWindow('${LUI_ContextPath}/third/weixin/upload.jsp','_self');">
					</ui:button>
					<ui:button text="${ lfn:message('third-weixin:url.code') }"
						onclick="Com_OpenWindow('${LUI_ContextPath}/third/weixin/mutil/urlCode.jsp','_self');">
					</ui:button>
				</kmss:authShow>
				
				<kmss:auth requestURL="/third/weixin/mutil/third_weixin_work/thirdWeixinWork.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/third/weixin/mutil/third_weixin_work/thirdWeixinWork.do?method=add&fdWxKey=${fdWxKey }');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/third/weixin/mutil/third_weixin_work/thirdWeixinWork.do?method=deleteall">
					<ui:button text="${ lfn:message('button.delete') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.thirdWeixinWorkForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/third/weixin/mutil/third_weixin_work/thirdWeixinWork.do">
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
				<sunbor:column property="thirdWeixinWork.fdName">
					<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdName"/>
				</sunbor:column>
				<sunbor:column property="thirdWeixinmutil.fdAgentid">
					<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdAgentid"/>
				</sunbor:column>
					<sunbor:column property="thirdWeixinWork.fdModelName">
					<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdModelName"/>
				</sunbor:column>
				<%-- <sunbor:column property="thirdWeixinWork.fdSecret">
					<bean:message bundle="third-weixin-work" key="thirdWeixinWork.fdSecret"/>
				</sunbor:column>
				<sunbor:column property="thirdWeixinWork.fdType">
					<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdType"/>
				</sunbor:column>
				<sunbor:column property="thirdWeixinWork.fdSystemUrl">
					<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdSystemUrl"/>
				</sunbor:column>
				<sunbor:column property="thirdWeixinWork.docCreateTime">
					<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.docCreateTime"/>
				</sunbor:column>
				<sunbor:column property="thirdWeixinWork.docCreator.fdName">
					<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.docCreator"/>
				</sunbor:column> --%>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="thirdWeixinWork" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/third/weixin/mutil/third_weixin_work/thirdWeixinWork.do" />?method=view&fdId=${thirdWeixinWork.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${thirdWeixinWork.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${thirdWeixinWork.fdName}" />
				</td>
				<td>
					<c:out value="${thirdWeixinWork.fdAgentid}" />
				</td>
				<td>
					<c:out value="${thirdWeixinWork.fdModelName}" />
				</td>
				<%-- <td>
					<c:out value="${thirdWeixinWork.fdSecret}" />
				</td>
				<td>
					<xform:radio property="fdType" value="${thirdWeixinWork.fdType}">
			       		<xform:enumsDataSource enumsType="third_weixin_work_type" />
			      	</xform:radio>
				</td>
				<td>
					<c:out value="${thirdWeixinWork.fdSystemUrl}" />
				</td>
				<td>
					<kmss:showDate value="${thirdWeixinWork.docCreateTime}" />
				</td>
				<td>
					<c:out value="${thirdWeixinWork.docCreator.fdName}" />
				</td> --%>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>