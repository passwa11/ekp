<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="mobile.list">
    <template:replace name="title">
        <c:if test="${param.moduleName!=null && param.moduleName!=''}">
            <c:out value="${param.moduleName}"></c:out>
        </c:if>
        <c:if test="${param.moduleName==null || param.moduleName==''}">
            <c:out value="${ lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
        </c:if>
    </template:replace>
    <template:replace name="head">
        <%-- 自定义js或css --%>
        <script type="text/javascript">
            var listOption = {

            };
        </script>
        <script type="text/javascript" src="${LUI_ContextPath}/fssc/loan/resource/js/mobile_list.js?s_cache=${MUI_Cache}"></script>
    </template:replace>
    <template:replace name="content">
        <c:choose>
            <c:when test="${ param.filter == '1' }">
                <div data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
                    <div data-dojo-type="mui/header/HeaderItem" data-dojo-mixins="mui/folder/_Folder,mui/back/HrefBackMixin" data-dojo-props="href:'/fssc/loan/fssc_loan_repayment/mobile/index.jsp'">
                    </div>
                    <div data-dojo-type="mui/header/HeaderItem" data-dojo-props="label:'${lfn:escapeHtml(JsParam.moduleName)}',referListId:'_filterDataList'">
                    </div>

                    <div data-dojo-type="mui/header/HeaderItem" data-dojo-mixins="mui/folder/_Folder,mui/simplecategory/SimpleCategoryDialogMixin" data-dojo-props="icon:'mui mui-ul', modelName:'com.landray.kmss.fssc.loan.model.FsscLoanReCategory', redirectURL:'/fssc/loan/fssc_loan_repayment/mobile/index.jsp?moduleName=!{curNames}&filter=1', filterURL:'/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=data&q.docTemplate=!{curIds}&orderby=docCreateTime&ordertype=down'">
                    </div>
                </div>
                <div id="scroll" data-dojo-type="mui/list/StoreScrollableView">
                    <div data-dojo-type="mui/search/SearchBar" data-dojo-props="modelName:'com.landray.kmss.fssc.loan.model.FsscLoanRepayment',needPrompt:false,height:'3.8rem'">
                    </div>
                    <ul id="_filterDataList" data-dojo-type="mui/list/JsonStoreList" data-dojo-mixins="mui/list/ProcessItemNewListMixin" data-dojo-props="url:'${lfn:escapeHtml(JsParam.queryStr)}', lazy:false">
                    </ul>
                </div>
            </c:when>
            <c:otherwise>
                <div data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
                    <div data-dojo-type="mui/nav/MobileCfgNavBar" data-dojo-props="defaultUrl:'/fssc/loan/fssc_loan_repayment/mobile/nav.jsp', modelName:'com.landray.kmss.fssc.loan.model.FsscLoanRepayment'">
                    </div>
                    <div data-dojo-type="mui/header/HeaderItem" data-dojo-mixins="mui/folder/Folder" data-dojo-props="tmplURL:'/fssc/loan/fssc_loan_repayment/mobile/query.jsp'"></div>
                </div>
                <div data-dojo-type="mui/list/NavSwapScrollableView">
                    <ul data-dojo-type="mui/list/JsonStoreList" data-dojo-mixins="mui/list/ProcessItemNewListMixin">
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>

        <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
            <li data-dojo-type="mui/back/BackButton">
            </li>
        <%--     <kmss:auth requestURL="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=add">
                <li data-dojo-type="mui/tabbar/CreateButton" data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin" data-dojo-props="icon1:'',createUrl:'/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=add&i.docTemplate=!{curIds}',modelName:'com.landray.kmss.fssc.loan.model.FsscLoanReCategory'">
                    ${lfn:message('button.add')}
                </li>
            </kmss:auth> --%>
            <li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'mui mui-more'">
                <div data-dojo-type="mui/back/HomeButton">
                </div>
                <div data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'mui mui-ul', href:'/fssc/loan/fssc_loan_main/mobile/index.jsp', label:'${lfn:message('fssc-loan:table.fsscLoanMain')}'">
                </div>
                <div data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'mui mui-ul', href:'/fssc/loan/fssc_loan_transfer/mobile/index.jsp', label:'${lfn:message('fssc-loan:table.fsscLoanTransfer')}'">
                </div>
            </li>
        </ul>
    </template:replace>
</template:include>
