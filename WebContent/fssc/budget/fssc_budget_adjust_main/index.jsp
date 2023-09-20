<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-budget:module.fssc.budget') }-${ lfn:message('fssc-budget:table.fsscBudgetAdjustMain') }" />
    </template:replace>
    <template:replace name="content">
    	<ui:tabpanel id="fsMainPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
			<ui:content id="fsMainContent" title="${lfn:message('fssc-budget:table.fsscBudgetAdjustMain')}">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
             <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docSubject')}" />
                <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" property="docNumber" expand="true" />
                <list:cri-criterion expand="false" title="${lfn:message('fssc-budget:lbpm.my')}" key="mydoc" multi="false">
                    <list:box-select>
                        <list:item-select>
                            <ui:source type="Static">
                                [{text:'${ lfn:message('fssc-budget:lbpm.create.my') }', value:'create'},{text:'${ lfn:message('fssc-budget:lbpm.approval.my') }',value:'approval'}, {text:'${ lfn:message('fssc-budget:lbpm.approved.my') }', value: 'approved'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory" />
                </list:cri-ref>
				<list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCompany')}" key="fdCompanyName">
                    <list:box-select>
                        <list:item-select type="lui/criteria/criterion_input!TextInput">
                            <ui:source type="Static">
                                [{placeholder:'${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCompany')}'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" property="docNumber" expand="false" />
                <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" property="docStatus" />
				<list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" property="docCreateTime" />
                
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscBudgetAdjustMain.docCreateTime" text="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docCreateTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=add">
                            	<c:if test="${HtmlParam.fdSchemeId!='null'}">
                                	<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                                </c:if>
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAllByAuth()" order="4" id="btnDelete" />
                            <c:import  url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
                                <c:param name="modelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" />
                                <c:param name="authReaderNoteFlag" value="2" />
                            </c:import>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=data&fdSchemeId=${HtmlParam.fdSchemeId }')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=view&fdId=!{fdId}&i.fdBudgetScheme=${HtmlParam.fdSchemeId }" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="docSubject;docNumber;fdCompany.name;docStatus.name;docTemplate.name;docCreator.name;docCreateTime;lbpm_main_listcolumn_node;lbpm_main_listcolumn_handler" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        </ui:content>
        </ui:tabpanel>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain',
                templateName: 'com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory',
                basePath: '/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do',
                canDelete: '${canDelete}',
                mode: 'main_scategory',
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
