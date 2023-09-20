<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/praise/sys_praise_info/sysPraiseInfo.do">
<script>
Com_IncludeFile("dialog.js|plugin.js");
</script>
 
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable" style="word-break : break-all;">
		<tr>
			<td width="5%">
				<bean:message key="page.serial"/>
			</td>
			<td width="15%">
				<bean:message bundle="sys-praise" key="sysPraiseInfo.fdPraisePerson"/>
			</td>
			<td width="15%">
				<bean:message bundle="sys-praise" key="sysPraiseInfo.docCreateTime"/>
			</td>
			<td width="10%">
				<bean:message bundle="sys-praise" key="sysPraiseInfo.fdType"/>
			</td>
			<td width="10%">
				<bean:message bundle="sys-praise" key="sysPraiseInfo.fdRich"/>
			</td>
			<td width="15%">
				<bean:message bundle="sys-praise" key="sysPraiseInfo.fdTargetPerson"/>
			</td>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysPraiseInfo" varStatus="vstatus">
			<tr>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysPraiseInfo.fdPraisePerson.fdName}" />
				</td>
				<td>
					<kmss:showDate value="${sysPraiseInfo.docCreateTime}" />
				</td>
				<td>
					<c:choose>
					 	<c:when test="${sysPraiseInfo.fdType == 1}">
					 		<c:out value="${lfn:message('sys-praise:sysPraiseInfo.fdType.praise') }"/>
					 	</c:when>
					 	<c:when test="${sysPraiseInfo.fdType == 2}">
					 		<c:out value="${lfn:message('sys-praise:sysPraiseInfo.fdType.rich') }"/>
					 	</c:when>
					 	<c:when test="${sysPraiseInfo.fdType == 3}">
					 		<c:out value="${lfn:message('sys-praise:sysPraiseInfo.fdType.unPraise') }"/>
					 	</c:when>
					 </c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty sysPraiseInfo.fdRich}">
							<c:out value="0"/>
						</c:when>
						<c:otherwise>
							<c:out value="${sysPraiseInfo.fdRich }"/> 
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:out value="${sysPraiseInfo.fdTargetPerson.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
 
<link href="${KMSS_Parameter_ResPath}style/common/list/list.css" rel="stylesheet" type="text/css" />
<div class="pageBox">
	<sunbor:page name="queryPage" rowsizeText="rowsizeText2" pagenoText="pagenoText2" pageListSize="5" pageListSplit="" textStyleClass="pagenav_input" >
	<table width="100%" height="22" cellspacing="0" cellpadding="0" border="0" class="pageNav_tb">
		<tr>
			<td width="2%" valign="top">&nbsp;</td>
			<td width="55%" valign="top">
				<div class="page_box">
				<sunbor:leftPaging><bean:message key="page.thePrev"/></sunbor:leftPaging>
				{11}
				<sunbor:rightPaging><bean:message key="page.theNext"/></sunbor:rightPaging>
				</div>
			</td>
			<td width="15%" valign="top">
				<bean:message key="page.to"/>&nbsp;<bean:message key="page.the"/>{9}<bean:message key="page.page"/><a href="javascript:void(0);" class="btn_go" title="<bean:message key="page.changeTo"/>"
				 onclick="{10}">Go</a>
			</td>
			<td width="10%" valign="top">
				<bean:message key="page.total"/>&nbsp;{3}&nbsp;<bean:message key="page.row"/>
			</td>
			<td width="5%" valign="top">
				<c:if test="${pageNavJsFunction==null}">
					<a class="refresh" href="javascript:location.reload();"><bean:message key="button.refresh"/></a>
				</c:if>
				<c:if test="${pageNavJsFunction!=null}">
					<a class="refresh" href="javascript:${pageNavJsFunction}(location.href);"><bean:message key="button.refresh"/></a>
				</c:if>
			</td>
			<td width="2%" valign="top">&nbsp;</td>
		</tr>
	</table>
	</sunbor:page>
</div>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>