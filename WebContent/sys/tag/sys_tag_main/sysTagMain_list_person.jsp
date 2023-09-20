<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.sys.tag.service.ISysTagMainService"%>

<template:include ref="person.home">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-tag:sysTag.tree.person') }"/>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${lfn:message('sys-tag:sysTag.tags.own.created') }">
				<c:import url="/sys/tag/sys_tag_main/myTagsInformation/sysTagTags_list_content.jsp" charEncoding="UTF-8"/>
			</ui:content>
			<ui:content title="${lfn:message('sys-tag:sysTag.tags.own.used') }">
				<c:import url="/sys/tag/sys_tag_main/myTagsInformation/sysTagMain_list_content.jsp?fdModelName=${JsParam.fdModelName}&fdTagName=${JsParam.fdTagName}" charEncoding="UTF-8"/>
			</ui:content>
			<ui:content title="${lfn:message('sys-tag:sysTag.tags.own.followed') }">
				<c:import url="/sys/tag/sys_tag_main/myTagsInformation/sysFollowPersonDocRelated_person.jsp" charEncoding="UTF-8"/>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>
