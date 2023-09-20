<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
[ 
	{
		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=approval&q.docStatus=20&orderby=fdLibrary&ordertype=down",
		<c:if test="${JsParam.type eq 'main'}">
		selected : true,
		</c:if>
		text: "待审档案"
	},
    {
		url : "/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=data&q.mydoc=approval&q.status=20&orderby=fdBorrowDate&ordertype=down",
		<c:if test="${JsParam.type eq 'borrow'}">
		selected : true,
		</c:if>
		text: "待审借阅"
	}
]
