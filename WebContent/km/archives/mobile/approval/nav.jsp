<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
[ 
	{
		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=approval&orderby=fdLibrary&ordertype=down",
		<c:if test="${JsParam.type eq 'main'}">
		selected : true,
		</c:if>
		text: "${lfn:message('km-archives:mui.kmArchivesMain.list')}"
	},
	{
		url : "/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=data&q.mydoc=approval&orderby=fdBorrowDate&ordertype=down",
		<c:if test="${JsParam.type eq 'borrow'}">
		selected : true,
		</c:if>
		text: "${lfn:message('km-archives:mui.kmArchivesBorrow.list')}"
	}
]
