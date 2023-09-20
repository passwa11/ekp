<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="mobile.list">
    <template:replace name="title">
        <c:if test="${param.moduleName!=null && param.moduleName!=''}">
            <c:out value="${param.moduleName}"></c:out>
        </c:if>
        <c:if test="${param.moduleName==null || param.moduleName==''}">
            <c:out value="${ lfn:message('fssc-budget:table.fsscBudgetMain') }" />
        </c:if>
    </template:replace>
    <template:replace name="head">
        <%-- 自定义js或css --%>
            <script type="text/javascript">
                var listOption = {

                };
            </script>
            <script type="text/javascript" src="${LUI_ContextPath}/fssc/budget/resource/js/mobile_list.js?s_cache=${MUI_Cache}"></script>
    </template:replace>
    <template:replace name="content">
        <c:choose>
            <c:when test="${ param.filter == '1' }">
                <div data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
                    <div data-dojo-type="mui/header/HeaderItem" data-dojo-mixins="mui/folder/_Folder,mui/back/HrefBackMixin" data-dojo-props="href:'/fssc/budget/fssc_budget_main/mobile/index.jsp'">
                    </div>
                    <div data-dojo-type="mui/header/HeaderItem" data-dojo-props="label:'${lfn:escapeHtml(JsParam.moduleName)}',referListId:'_filterDataList'">
                    </div>

                </div>
                <div id="scroll" data-dojo-type="mui/list/StoreScrollableView">
                    <div data-dojo-type="mui/search/SearchBar" data-dojo-props="modelName:'com.landray.kmss.fssc.budget.model.FsscBudgetMain',needPrompt:false,height:'3.8rem'">
                    </div>
                    <ul id="_filterDataList" data-dojo-type="mui/list/JsonStoreList" data-dojo-mixins="mui/list/ProcessItemNewListMixin" data-dojo-props="url:'${lfn:escapeHtml(JsParam.queryStr)}', lazy:false">
                    </ul>
                </div>
            </c:when>
            <c:otherwise>
                <div data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
                    <div data-dojo-type="mui/nav/MobileCfgNavBar" data-dojo-props="defaultUrl:'/fssc/budget/fssc_budget_main/mobile/nav.jsp', modelName:'com.landray.kmss.fssc.budget.model.FsscBudgetMain'">
                    </div>
                    <div data-dojo-type="mui/header/HeaderItem" data-dojo-mixins="mui/folder/Folder" data-dojo-props="tmplURL:'/fssc/budget/fssc_budget_main/mobile/query.jsp'"></div>
                </div>
                <div data-dojo-type="mui/list/NavSwapScrollableView">
                    <ul data-dojo-type="mui/list/JsonStoreList" data-dojo-mixins="mui/list/ProcessItemNewListMixin">
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>

        <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
            <kmss:auth requestURL="/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=add">
                <li data-dojo-type="mui/tabbar/CreateButton" data-dojo-props="_onClick:function(){location='${LUI_ContextPath}/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=add'}">
                    ${lfn:message('button.add')}
                </li>
            </kmss:auth>
        </ul>
    </template:replace>
</template:include>
