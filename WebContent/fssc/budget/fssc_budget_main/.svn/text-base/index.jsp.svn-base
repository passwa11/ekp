<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<%@page import="com.landray.kmss.fssc.budget.util.FsscBudgetUtil" %>
    <template:include ref="default.simple4list" spa="true" rwd="true">
        <template:replace name="title">
            <c:out value="${ lfn:message('fssc-budget:module.fssc.budget') }-${ lfn:message('fssc-budget:table.fsscBudgetMain') }" />
        </template:replace>
        <template:replace name="content">
            <ui:tabpanel id="fsMainPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
			<ui:content id="fsMainContent" title="${lfn:message('fssc-budget:module.fssc.budget')}">
			<div style="margin:5px 10px;">
			<!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetMain.fdCompany')}" key="fdCompanyName">
                    <list:box-select>
                        <list:item-select type="lui/criteria/criterion_input!TextInput">
                            <ui:source type="Static">
                                [{placeholder:'${lfn:message('fssc-budget:fsscBudgetMain.fdCompany')}'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetMain" property="fdYear" />
                <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetMain" property="fdEnableDate" />
                <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetMain" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetMain" property="docCreateTime" />

            </list:criteria>
                <!-- 操作 -->
                <div class="lui_list_operation">

                    <div style='color: #979797;float: left;padding-top:1px;'>
                        ${ lfn:message('list.orderType') }：
                    </div>
                    <div style="float:left">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                                <list:sort property="fsscBudgetMain.docCreateTime" text="${lfn:message('fssc-budget:fsscBudgetMain.docCreateTime')}" group="sort.list" />
                            </ui:toolbar>
                        </div>
                    </div>
                    <div style="float:left;">
                        <list:paging layout="sys.ui.paging.top" />
                    </div>
                    <div style="float:right">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar count="4">

                                <kmss:auth requestURL="/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=add">
                                	<c:if test="${HtmlParam.fdSchemeId!='null'}">
                                    	<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                                    </c:if>
                                </kmss:auth>
                                <kmss:auth requestURL="/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=deleteall">
                                     <c:set var="canDelete" value="true" />
                                </kmss:auth>
                                <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="2" id="btnDelete" />

                            </ui:toolbar>
                        </div>
                    </div>
                </div>
                <ui:fixed elem=".lui_list_operation" />
                <!-- 列表 -->
                <list:listview id="listview" cfg-criteriaInit="true">
                    <ui:source type="AjaxJson">
                        {url:appendQueryParameter('/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=data&fdSchemeId=${HtmlParam.fdSchemeId}')}
                    </ui:source>
                    <!-- 列表视图 -->
                    <list:colTable isDefault="false" rowHref="/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=view&fdId=!{fdId}" name="columntable">
                        <list:col-checkbox />
                        <list:col-serial/>
                        <list:col-auto props="fdCompany.name;fdYear;fdBudgetScheme.name;fdCompanyGroup.name;fdEnableDate;docCreateTime;docCreator.name" /></list:colTable>
                </list:listview>
                <!-- 翻页 -->
                <list:paging />
            </div>
            </ui:content>
            </ui:tabpanel>
        </template:replace>
		<template:replace name="script">
            <script>
                var listOption = {
                    contextPath: '${LUI_ContextPath}',
                    modelName: 'com.landray.kmss.fssc.budget.model.FsscBudgetMain',
                    templateName: '',
                    basePath: '/fssc/budget/fssc_budget_main/fsscBudgetMain.do',
                    canDelete: '${canDelete}',
                    mode: '',
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
