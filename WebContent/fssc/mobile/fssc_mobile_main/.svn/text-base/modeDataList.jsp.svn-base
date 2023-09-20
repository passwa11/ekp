<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<c:forEach items="${array}" var="list">
	<li class="ld-tab-list-main-list" onclick="viewFlow('${list.fdId}','${list.fdKey}','${list.url}','${list.templateId}')">
		<div style="border:none;">
			<div style="width:60%;word-break: break-all;">
				<c:if test="${fn:length(list.docSubject)>40 }">${fn:substring(list.docSubject,0,37)}...</c:if>
				<c:if test="${fn:length(list.docSubject)<=40 }">${list.docSubject}</c:if>
			</div>
			<div style="width:40%;text-align:right;">
					${list.fdNo}
			</div>
		</div>
		<div>
			<div style="width:40%;float:left;">${list.createTime}</div>
			<div style="width:40%;text-align:right;float:right;">
				<span class="ld-list-status ld-list-status-${list.clazz}">${list.status}</span>
			</div>
		</div>
	</li>
	<div class="ld-line20px"></div>
</c:forEach>
<input name="pageno" value="${queryPage.pageno}" type="hidden"/>
<input name="rowsize" value="${queryPage.rowsize}" type="hidden"/>
<input name="totalrows" value="${queryPage.totalrows}" type="hidden"/>
<input name="type" value="${type}" type="hidden"/>