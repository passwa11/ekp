<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.kms.common.model.KmsCourseNotes"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" 
	prefix="person"%>

<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdNotesContent" title="${lfn:message('kms-common:kmsCourseNotes.fdNotesContent')}" escape="false">
		</list:data-column>
		<list:data-column property="docCreator.fdName" title="${lfn:message('kms-common:kmsCourseNotes.fdRecorder')}">
		</list:data-column>
		<list:data-column property="docCreator.fdId">
		</list:data-column>
		<list:data-column property="docCreateTime" title="${lfn:message('kms-common:kmsCourseNotes.fdNotesTime')}">
		</list:data-column>
		<list:data-column property="isShare" title="${lfn:message('kms-common:kmsCourseNotes.isShare')}">
		</list:data-column>

	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
