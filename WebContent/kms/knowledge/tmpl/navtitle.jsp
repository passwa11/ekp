<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="lui_list_create_frame">
    <div class="lui-navtitle-card">
        <c:if test="${not empty varParams.info}">
            <ui:dataview format="sys.ui.navtitle.info">
                ${varParams.info}
                <ui:render ref="sys.ui.navtitle.info"/>
            </ui:dataview>
        </c:if>
        <c:if test="${not empty varParams.infonew}">
            <ui:dataview format="sys.ui.navtitle.info.new">
                ${varParams.infonew}
                <ui:render ref="sys.ui.navtitle.info.new"/>
            </ui:dataview>
        </c:if>
        <c:if test="${not empty varParams.operation}">
            <ui:dataview format="sys.ui.navtitle.operation">
                ${varParams.operation}
                <ui:render ref="sys.ui.navtitle.operation"/>
            </ui:dataview>
        </c:if>
    </div>
</div>


