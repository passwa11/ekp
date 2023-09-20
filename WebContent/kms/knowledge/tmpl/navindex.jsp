<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:dataview format="sys.ui.nav.simple">
    ${varParams.source}
    <ui:render ref="sys.ui.nav.simple"/>
</ui:dataview>
