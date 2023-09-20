<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.km.calendar.model.KmCalendarPersonGroup" %>
<%@page import="java.util.List" %>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmCalendarPersonGroup" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdOrder" title="${lfn:message('km-calendar:kmCalendarPersonGroup.fdOrder') }" >
		</list:data-column>
		<list:data-column col="docSubject" title="${ lfn:message('km-calendar:kmCalendarPersonGroup.docSubject') }" escape="false" style="text-align:center;min-width:150px;">
			<span class="com_subject" >${kmCalendarPersonGroup.docSubject}</div>
		</list:data-column>
		<list:data-column col="fdPersonGroupNames" title="${lfn:message('km-calendar:kmCalendarPersonGroup.fdPersonGroupNames')}" escape="false">
			<%
				KmCalendarPersonGroup personGroup = (KmCalendarPersonGroup)pageContext.getAttribute("kmCalendarPersonGroup");
				List<SysOrgElement> persons = personGroup.getFdPersonGroup();
				String personGroupNames = "";
				for(SysOrgElement person : persons){
					personGroupNames += person.getFdName()+";";
				}
				if(personGroupNames.length() > 1)
					personGroupNames = personGroupNames.substring(0, personGroupNames.length()-1);
				pageContext.setAttribute("personGroupNames", personGroupNames);
			%>
			<c:out value="${personGroupNames }" />
		</list:data-column>
		<list:data-column col="authEditorNames" title="${lfn:message('km-calendar:kmCalendarPersonGroup.authEditorNames')}" escape="false">
			<%
				KmCalendarPersonGroup personGroup = (KmCalendarPersonGroup)pageContext.getAttribute("kmCalendarPersonGroup");
				List<SysOrgElement> editors = personGroup.getAuthEditors();
				String editorNames = "";
				for(SysOrgElement editor : editors){
					editorNames += editor.getFdName()+";";
				}
				if(editorNames.length() > 1)
					editorNames = editorNames.substring(0, editorNames.length()-1);
				pageContext.setAttribute("editorNames", editorNames);
			%>
			<c:out value="${editorNames }" />
		</list:data-column>
		<list:data-column property="fdDescription" title="${lfn:message('km-calendar:kmCalendarPersonGroup.fdDescription')}">
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${kmCalendarPersonGroup.fdId}')">${lfn:message('button.edit')}</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:deleteAll('${kmCalendarPersonGroup.fdId}')">${lfn:message('button.delete')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>	
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>