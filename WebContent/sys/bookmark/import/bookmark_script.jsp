<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<script src='${ KMSS_Parameter_ContextPath }sys/bookmark/import/bookmark.js'></script>
<script>

function openBookDia(){
	seajs.use([ 'sys/ui/js/dialog','lang!sys-bookmark:sysBookmark.mechanism','theme!form'],function(dialog,lang) {
		//if(!CheckBooked(GetBookmarkUrl())){
		 ___BookmarkDialog({'url': GetBookmarkUrl(), 'subject': GetBookmarkSubject(), 'fdModelId': '${JsParam.fdModelId}','fdModelName': '${JsParam.fdModelName}'});
		//}else{
		 //  dialog.failure(lang["sysBookmark.mechanism.exsit"]);
		//}
   }); 
}
function GetBookmarkSubject() {
	var subject = "${lfn:escapeJs(param.fdSubject)}";
	if (subject.length < 1) {
		var title = document.getElementsByTagName("title");
		if (title != null && title.length > 0) {
			subject = title[0].text;
			if (subject == null) {
				subject = "";
			} else {
				subject = subject.replace(/(^\s*)|(\s*$)/g, "");
			}
		}
	}
	return subject;
}
function GetBookmarkUrl() {
	var url = "${lfn:escapeJs(param.fdUrl)}";
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
</script>