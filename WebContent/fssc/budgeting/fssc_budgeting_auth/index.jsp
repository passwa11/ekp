<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list" spa="true" rwd="true">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.fssc.budgeting.model.FsscBudgetingAuth" property="fdIsAvailable" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscBudgetingAuth.docCreateTime" text="${lfn:message('fssc-budgeting:fsscBudgetingAuth.docCreateTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="4">

                            <kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_auth/fsscBudgetingAuth.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_auth/fsscBudgetingAuth.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="2" id="btnDelete" />
							<kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_auth/fsscBudgetingAuth.do?method=add">
                                <ui:button text="${lfn:message('fssc-budgeting:button.template.download')}" onclick="downTemplate('budgeting')" order="3" />
                                <ui:button text="${lfn:message('fssc-budgeting:button.excel.import')}" onclick="importData('budgeting')" order="4" />
                            </kmss:auth>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/budgeting/fssc_budgeting_auth/fsscBudgetingAuth.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/budgeting/fssc_budgeting_auth/fsscBudgetingAuth.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdIsAvailable.name;docCreator.name;docCreateTime;operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.budgeting.model.FsscBudgetingAuth',
                templateName: '',
                basePath: '/fssc/budgeting/fssc_budgeting_auth/fsscBudgetingAuth.do',
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
            Com_IncludeFile("budgeting_auth.js", "${LUI_ContextPath}/fssc/budgeting/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>
