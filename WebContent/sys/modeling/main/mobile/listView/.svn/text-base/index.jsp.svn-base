<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/modeling/modeling.tld" prefix="modeling" %>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser" %>
<c:set var="tiny" value="true" scope="request"/>
<template:include ref="mobile.list" canHash="true">
    <template:replace name="title">
        <c:out value="${appName}"></c:out>
    </template:replace>
    <template:replace name="head">
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/list.css?s_cache=${MUI_Cache}">

        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/cardList.css?s_cache=${MUI_Cache}">

        <mui:cache-file name="mui-nav.js" cacheType="md5"/>
        <mui:cache-file name="mui-sysCate.js" cacheType="md5"/>
    </template:replace>
    <template:replace name="content">
        <c:if test="${jumpFlowSelect}">
            <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom"
                style="position: fixed !important;bottom: 0;display: none">
                <c:forEach items="${operList }" var="oper">
                    <c:if test="${oper['fdDefType'] == 0}">
                        <modeling:mauthurl fdOprId="${oper['fdOperId']}">
                            <kmss:auth requestURL="${modelingAuthUrl}" requestMethod="GET">
                                <li data-dojo-type="mui/tabbar/CreateButton" id="button"
                                    data-dojo-mixins="sys/modeling/main/resources/js/mobile/listView/buttonMixin"
                                    data-dojo-props="icon1:'',fdAppModelId:'${fdAppModelId }',createUrl:'/sys/modeling/main/modelingAppModelMain.do?method=add&fdAppModelId=${fdAppModelId }&fdAppFlowId=!{curIds}'">
                                    新建
                                </li>
                            </kmss:auth>
                        </modeling:mauthurl>
                    </c:if>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${!jumpFlowSelect}">
            <c:import url="/sys/modeling/main/mobile/listView/template/normal.jsp" charEncoding="UTF-8">
                <c:param name="listViewId" value="${param.listViewId}"></c:param>
                <c:param name="tabId" value="${param.tabId}"></c:param>
                <c:param name="fdModelId" value="${param.fdModelId}"></c:param>
                <c:param name="viewTabId" value="${param.viewTabId}"></c:param>
                <c:param name="nodeType" value="${param.nodeType}"></c:param>
            </c:import>
            <%--  新建按钮  createOperId --%>
            <div data-dojo-type="mui/tabbar/TabBarGroup" fixed="bottom"
                 style="position: fixed !important;bottom: 0;width: 100%" class="modeling_list_bottom_tab_bar">
                <ul data-dojo-type="sys/modeling/main/resources/js/mobile/formview/OperTabBar"
                    data-dojo-props='fill:"grid",operations:${empty jsonOperList ? [] : jsonOperList }, isFlow: "false", fdId:"${param.fdId }",listviewId:"${param.listviewId }",isListView:"true"'>
                    <li data-dojo-type="mui/tabbar/CreateButton"
                        data-dojo-mixins="sys/modeling/main/resources/js/mobile/listView/buttonMixin"
                        data-dojo-props="icon1:''">${lfn:message('sys-modeling-main:modeling.new.create') }</li>
                </ul>
            </div>
        </c:if>
        <script>
            require(['dojo/topic', 'dojo/dom', "dojo/query", "dojo/dom-class", 'dojo/dom-style', 'dojo/dom-construct'
                    , "dojo/query", "dijit/registry", 'dojo/ready'],
                function (topic, dom, query, domClass, domStyle, domConstruct, query, registry, ready) {
                    ready(function () {
                        var button = registry.byId("button");
                        if (button) {
                            button._onClick();
                            setTimeout(function () {
                                query(".muiCateClearBtn").on("click", function () {
                                    window.history.back();
                                })
                            }, 500)
                        }
                    })


                })
        </script>
    </template:replace>
</template:include>
