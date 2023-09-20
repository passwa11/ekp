<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@page
	import="com.landray.kmss.sys.person.interfaces.PersonImageService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%><template:include
	ref="person.cfg">
	<template:replace name="content">
		<ui:dataview format="sys.ui.iframe">
			<ui:source type="Static">
				<%
					PersonImageService personImageService = (PersonImageService) SpringBeanUtil
											.getBean("kk5PersonImageBean");
									String chgKk5PhotoUrl = personImageService
											.getHeadimageChangeUrl();
				%>
				{
					"src":"<%=chgKk5PhotoUrl%>&LUIID=!{lui.element.id}"
				}
			</ui:source>
		</ui:dataview>
	</template:replace>
</template:include>