<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<link href="${LUI_ContextPath}/sys/ui/js/yearcalendar/calendar.css"
	rel="stylesheet">
<link href="resource/css/calendar_style.css"
	rel="stylesheet">

<script type="text/javascript">

var calendarContent = $(window.parent.document).find('iframe#calendarContent');

window.pageResize = function(){	
	calendarContent.height($(document.body).height());
};

$(window).resize(function(){
	pageResize();
});

</script>

<table width="100%" align="center" class="tb_simple">

	<c:if test="${works != null || fn:length(works) > 0}">

	<tr>
		<td width="10px" style="white-space: nowrap; vertical-align: top; position: relative; top: 2px;">
			<bean:message  bundle="sys-time" key="calendar.worktime"/>
		</td>
		<td align="left">
			<c:forEach items="${works }" var="work">
				<div class="tag" title="${work.date}">
					<span class="tag-flag" style="background: ${work.color};"></span><span	class="tag-label">${work.time}</span>
				</div>
			</c:forEach>
		</td>
	</tr>
	
	</c:if>
	
	<tr>
		<td width="10px" style="white-space: nowrap; vertical-align: top; position: relative; top: 2px;">
			<c:if test="${works == null || fn:length(works) == 0}">
				<bean:message  bundle="sys-time" key="calendar.worktime"/>
			</c:if>
		</td>
		<td align="left">		
			<div class="tag">
				<span class="tag-flag-2" style="border-top-color: #E60000;"></span><span class="tag-label"><bean:message  bundle="sys-time" key="calendar.data.list.holiday"/></span>
			</div>
			<div class="tag">
				<span class="tag-flag-2" style="border-top-color: #2B9AE0;"></span><span class="tag-label"><bean:message  bundle="sys-time" key="calendar.data.list.pach"/></span>
			</div>
		</td>
	</tr>
	
	<!--<tr>
		<td><bean:message  bundle="sys-time" key="calendar.vacation"/></td>
		<td>
			<c:forEach items="${vacations }" var="vacation">
				<div class="tag">
					<span class="tag-flag" style="background: ${vacation.color};"></span><span	class="tag-label">${vacation.time}</span>
				</div>
			</c:forEach>
		</td>
	</tr>
	<tr>
		<td><bean:message  bundle="sys-time" key="calendar.workpach"/></td>
		<td>
			<c:forEach items="${patchworks }" var="patchwork">
				<div class="tag">
					<span class="tag-flag" style="background: ${patchwork.color};"></span><span class="tag-label">${patchwork.time}</span>
				</div>
			</c:forEach>
		</td>
	</tr>-->
	
	
	
</table>
<br>
<div class="main">
	<ui:dataview>
		<ui:source type="AjaxJson" cfg-commitType="post" cfg-params="colors=${workColors }">
          {url:'/sys/time/sys_time_area/sysTimeArea.do?method=calendarJson&fdId=${JsParam.fdId}'}
        </ui:source>
		<ui:render type="Javascript">
			<c:import url="resource/tmpl/calendar_render.js" charEncoding="UTF-8">
			</c:import>
		</ui:render>
		<ui:event topic="calendar.load">
			pageResize();
		</ui:event>
	</ui:dataview>
</div>

<%@ include file="/resource/jsp/view_down.jsp"%>