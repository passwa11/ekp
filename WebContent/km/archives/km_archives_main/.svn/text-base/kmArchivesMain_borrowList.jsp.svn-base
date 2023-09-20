<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:listview>
	<ui:source type="AjaxJson">
		{"url":"/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=borrowList&fdId=${JsParam.fdId}"}
	</ui:source>
	<list:colTable isDefault="true">
	    <list:col-serial></list:col-serial>
		<list:col-auto props="fdBorrower.fdName;fdAuthorityRange;fdStatus;fdReturnDate;fdRemindDate"></list:col-auto>
	</list:colTable>
</list:listview>
<list:paging />