<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="title">
		<c:if test="${!empty param.fdName}">
			<%=java.net.URLDecoder.decode(request.getParameter("fdName"), "UTF-8")%>
		</c:if>
	</template:replace>
	<template:replace name="body">
		<c:if test="${!empty param.fdName}">
			<center><p style="font-size: 18px;line-height: 30px;color: #3e9ece;font-weight: bold;margin: 10px 0;"><%=java.net.URLDecoder.decode(request.getParameter("fdName"), "UTF-8")%></p></center>
		</c:if>

		<%--list页面--%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize/lbpmAuthorizeLog.do?method=list&targetId=${JsParam.fdId}'}
				</ui:source>
			<%--列表视图--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" rowHref="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize/lbpmAuthorizeLog.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				var browserVersion = window.navigator.userAgent.toUpperCase();
				var isOpera = false, isFireFox = false, isChrome = false, isSafari = false, isIE = false;
				var iframe, interval;
				reinitIframe = function(minHeight) {
				    try {
				        if(window.frameElement != null && window.frameElement.tagName == "IFRAME") {
							iframe = window.frameElement;
						}
				        var bHeight = 0;
				        if (isChrome == false && isSafari == false)
				            bHeight = iframe.contentWindow.document.body.scrollHeight;
				
				        var dHeight = 0;
				        if (isFireFox == true)
				            dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
				        else if (isIE == false && isOpera == false)
				            dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				        else if (isIE == true && ! -[1, ] == false) { } //ie9+
				        else
				            bHeight += 3;
				
				        var height = Math.max(bHeight, dHeight);
				        if(height > 0) clearInterval(interval);
				        if (height < minHeight) height = minHeight;
				        iframe.style.height = (height + 30) + "px";
				    } catch (ex) { }
				}
				startInit = function(minHeight) {
				    isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
				    isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
				    isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
				    isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
				    if (!!window.ActiveXObject || "ActiveXObject" in window)
				        isIE = true;
				   interval = window.setInterval("reinitIframe(" + minHeight + ")", 100);
				}
				
				startInit(450);
			</ui:event>
		</list:listview>
		<list:paging></list:paging>
	</template:replace>
</template:include>