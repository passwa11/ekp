<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.third.im.kk.*"%>
<template:include ref="person.cfg">
	<template:replace name="content">
		<ui:dataview format="sys.ui.iframe">
			<ui:source type="Static">
				<% 
					String kkUrl = new KkConfig().getValue("third.im.kk.serverIp");
					if(!kkUrl.startsWith("http://") || !kkUrl.startsWith("https://")) {
						kkUrl = "http://" + kkUrl;
					}
				%>
				{
					"src":"<%=kkUrl%>${param.url }?LUIID=!{lui.element.id}"
				}
			</ui:source>
		</ui:dataview>
	</template:replace>
</template:include>