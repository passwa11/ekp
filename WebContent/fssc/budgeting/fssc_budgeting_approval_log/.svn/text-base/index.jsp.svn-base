<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-budgeting:module.fssc.budgeting') }-${ lfn:message('fssc-budgeting:table.fsscBudgetingApprovalLog') }" />
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_top" />
            <ui:menu-item text="${ lfn:message('fssc-budgeting:table.fsscBudgetingApprovalLog') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.create">
            <ui:varParam name="title" value="${ lfn:message('fssc-budgeting:table.fsscBudgetingApprovalLog') }" />
            <ui:varParam name="button">
                [ {"text": "","href": "javascript:void(0);","icon": "/fssc/budgeting/fssc_budgeting_approval_log/fsscBudgetingApprovalLog.do"} ]
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
                        <li><a href="${LUI_ContextPath}/fssc/budgeting/fssc_budgeting_main/index.jsp${j_iframe}">${lfn:message('fssc-budgeting:table.fsscBudgetingMain')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/fssc/budgeting" target="_blank">${ lfn:message('list.manager') }</a>
                        </li>
                    </ul>
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsPeriodPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
		<ui:content id="fsPeriodContent" title="${lfn:message('fssc-budgeting:table.fsscBudgetingPeriod')}">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalLog" property="docSubject" />
                <list:cri-auto modelName="com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalLog" property="fdApprovalType" />
                <list:cri-auto modelName="com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalLog" property="fdOperator" />
                <list:cri-auto modelName="com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalLog" property="fdApprovalTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscBudgetingApprovalLog.fdApprovalTime" text="${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.fdApprovalTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_approval_log/fsscBudgetingApprovalLog.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_approval_log/fsscBudgetingApprovalLog.do?method=deleteall">
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
                    {url:appendQueryParameter('/fssc/budgeting/fssc_budgeting_approval_log/fsscBudgetingApprovalLog.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/budgeting/fssc_budgeting_approval_log/fsscBudgetingApprovalLog.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="docSubject;fdOperator.name;fdApprovalTime;fdApprovalType" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalLog',
                templateName: '',
                basePath: '/fssc/budgeting/fssc_budgeting_approval_log/fsscBudgetingApprovalLog.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-budgeting:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/budgeting/resource/js/", 'js', true);
        </script>
        </ui:content>
     </ui:tabpanel>
    </template:replace>
</template:include>
