<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.portal.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<portal:portlet title="个人头像">
    <ui:dataview format="sys.ui.html">
        <ui:source ref="sys.person.head.tab.source"></ui:source>
        <ui:render ref="sys.ui.html.default"></ui:render>
    </ui:dataview>
</portal:portlet>