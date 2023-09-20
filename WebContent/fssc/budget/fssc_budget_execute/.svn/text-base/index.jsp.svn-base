<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-budget:module.fssc.budget') }-${ lfn:message('fssc-budget:table.fsscBudgetExecute') }" />
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_top" />
            <ui:menu-item text="${ lfn:message('fssc-budget:table.fsscBudgetExecute') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.create">
            <ui:varParam name="title" value="${ lfn:message('fssc-budget:table.fsscBudgetExecute') }" />
            <ui:varParam name="button">
                [ {"text": "","href": "javascript:void(0);","icon": "/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do"} ]
            </ui:varParam>
        </ui:combin>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>

                <ui:content title="${ lfn:message('list.search') }">
                    <ul class='lui_list_nav_list'>

                        <li><a href="javascript:void(0)" onclick="openQuery();LUI('criteria1').clearValue();">${ lfn:message('list.alldoc') }</a>
                        </li>
                    </ul>
                </ui:content>

                <ui:content title="${ lfn:message('list.otherOpt') }">
                    <ul class='lui_list_nav_list'>
                        <li><a href="${LUI_ContextPath}/fssc/budget/fssc_budget_main/index.jsp${j_iframe}">${lfn:message('fssc-budget:table.fsscBudgetMain')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/budget/fssc_budget_adjust_main/index.jsp${j_iframe}">${lfn:message('fssc-budget:table.fsscBudgetAdjustMain')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/budget/fssc_budget_data/index.jsp${j_iframe}">${lfn:message('fssc-budget:table.fsscBudgetData')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/budget/fssc_budget_adjust_log/index.jsp${j_iframe}">${lfn:message('fssc-budget:table.fsscBudgetAdjustLog')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/fssc/budget" target="_blank">${ lfn:message('list.manager') }</a>
                        </li>
                    </ul>
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->

            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdModelName;fdMoney;fdCompanyId;fdCostCenterId;fdBudgetItemId;fdModelId;fdType;fdBudgetId;fdDetailId;fdCompanyGroupId;fdProjectId;fdInnerOrderId;fdWbsId;fdPersonId;fdCompanyGroupCode;fdCompanyCode;fdCostCenterGroupId;fdCostCenterCode;fdCostCenterGroupCode;fdBudgetItemCode;fdProjectCode;fdInnerOrderCode;fdWbsCode;fdPersonCode;fdCurrency"
                    /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.budget.model.FsscBudgetExecute',
                templateName: '',
                basePath: '/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-budget:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>
