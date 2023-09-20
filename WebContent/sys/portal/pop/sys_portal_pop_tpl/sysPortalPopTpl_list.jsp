<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<%-- 查询栏 --%>
	<template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->

            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="sysPortalPopTpl.docSubject" text="${lfn:message('sys-portal:sysPortalPopTpl.docSubject')}" group="sort.list" />
                            <list:sort property="sysPortalPopTpl.docCreateTime" text="${lfn:message('sys-portal:sysPortalPopTpl.docCreateTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=deleteall">
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
                    {url:appendQueryParameter('/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=data&categoryId=${JsParam.categoryId }')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="docSubject;fdCategory.name;fdIsAvailable.name;docCreator.name;docCreateTime" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.portal.model.SysPortalPopTpl',
                templateName: '',
                basePath: '/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do',
                canDelete: '${canDelete}',
                mode: '',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                },
                categoryId: '${JsParam.categoryId }'

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/portal/pop/resource/js/", 'js', true);
        </script>

	</template:replace>
</template:include>