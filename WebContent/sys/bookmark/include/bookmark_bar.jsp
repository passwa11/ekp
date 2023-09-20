<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>

<div id="bookmarkBtn" style="display:none;">
	<input type="button" value="<bean:message key="button.bookmark" bundle="sys-bookmark"/>" 
		onclick="ShowBookmarkDialog();">
</div>

<script language="JavaScript">
OptBar_AddOptBar("bookmarkBtn");
function ShowBookmarkDialog() {
	var openurl = '<c:url value="/sys/bookmark/include/bookmark_edit.jsp" />';
	var width = 600;
	var height = 380;
	// for IE and FF3
	Bookmark_PopupWindow(openurl, width, height, {
		'url': GetBookmarkUrl(), 
		'subject': GetBookmarkSubject(), 
		'fdModelId': '${JsParam.fdModelId}',
		'fdModelName': '${JsParam.fdModelName}'
	});
}
function GetBookmarkSubject() {
	var subject = "<c:out value="${param.fdSubject}" />";
	if (subject.length < 1) {
		var title = document.getElementsByTagName("title");
		if (title != null && title.length > 0) {
			subject = title[0].text;
			if (subject == null) 
				subject = "";
			else
				subject = subject.replace(/(^\s*)|(\s*$)/g, "");
		}
	}
	return subject;
}
function GetBookmarkUrl() {
	var url = "<c:out value="${param.fdUrl}" />";
	var context = "<%=request.getContextPath() %>";
	if (url.length < 1) {
		url = window.location.href;
		url = url.substring(url.indexOf('//') + 2, url.length);
		url = url.substring(url.indexOf('/'), url.length);
		if (context.length > 1) {
			url = url.substring(context.length, url.length);
		}
	}
	// #132105 由于后台匹配收藏链接是完全匹配，访问修订版本时需要去掉后缀
	if (url.indexOf("viewPattern=edition") > -1) {
		url = url.replace("&viewPattern=edition", "");
	}
	return url;
}
var param = {};
function Bookmark_PopupWindow(url,width, height, parameter){
	width = width==null?640:width;
	height = height==null?820:height;
	var left = (screen.width-width)/2;
	var top = (screen.height-height)/2;
	param = parameter;
	var winStyle = "height="+height+",width="+width+",top="+top+",left="+left+",toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=0";
	url = "<c:url value="/resource/jsp/frame.jsp?url=" />" + encodeURIComponent(url);
	return window.open(url,"_blank",winStyle);
}
</script>
