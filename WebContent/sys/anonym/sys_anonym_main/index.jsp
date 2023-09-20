<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.profile.list" spa="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('sys-anonym:module.sys.anonym') }-${ lfn:message('sys-anonym:table.sysAnonymMain') }" />
    </template:replace>
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-anonym:sysAnonymMain.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.sys.anonym.model.SysAnonymMain" property="docCreateTime"/>
                <list:cri-auto modelName="com.landray.kmss.sys.anonym.model.SysAnonymMain" property="docPublishTime"/>
                <list:cri-auto modelName="com.landray.kmss.sys.anonym.model.SysAnonymMain" property="docAlterTime"/>
                <%--提交人--%>
				<list:cri-ref ref="criterion.sys.person"
						  key="docCreator" multi="false"
						  title="${lfn:message('sys-anonym:sysAnonymMain.submitor') }" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="sysAnonymMain.docCreateTime" text="${lfn:message('sys-anonym:sysAnonymMain.docCreateTime')}" group="sort.list" value="down"/>
                        </ui:toolbar>
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="sysAnonymMain.docPublishTime" text="${lfn:message('sys-anonym:sysAnonymMain.docPublishTime')}" group="sort.list" value="down"/>
                        </ui:toolbar>
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="sysAnonymMain.docAlterTime" text="${lfn:message('sys-anonym:sysAnonymMain.docAlterTime')}" group="sort.list" value="down"/>
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <kmss:authShow roles="ROLE_SYSANONYM_DELETE">
                                <c:set var="canDelete" value="true" />
                                 <!--deleteall-->
                            	<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                            </kmss:authShow>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/anonym/sys_anonym_main/sysAnonymMain.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/sys/anonym/sys_anonym_main/sysAnonymMain.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;docCreateTime;docPublishTime;docAlterTime" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'main',
                modelName: 'com.landray.kmss.sys.anonym.model.SysAnonymMain',
                templateName: '',
                basePath: '/sys/anonym/sys_anonym_main/sysAnonymMain.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-anonym:treeModel.alert.templateAlert")}',
                customOpts: {
                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/anonym/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>