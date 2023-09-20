<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" spa="true">
    <template:replace name="title">
       title
    </template:replace>
    <template:replace name="body">
        <div style="margin: 5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797; float: left; padding-top: 1px;'>
                        ${ lfn:message('list.orderType') }：</div>
                <div style="float: left">
                    <div style="display: inline-block; vertical-align: middle;">
                    </div>
                </div>
                <div style="float: left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float: right">

                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/modeling/base/modelingFormMapping.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false"
                               rowHref="/sys/modeling/base/modelingFormMapping.do?method=view&fdId=!{fdId}"
                               name="columntable">
                    <list:col-checkbox />
                    <list:col-serial />
                    <list:col-auto
                            props="fdId;fdComClass;fdComName;docCreateTime;docCreator.fdName" />
                </list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.modeling.base.model.ModelingFormMapping',
                templateName: '',
                basePath: '/sys/modeling/base/modelingFormMapping.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '',
                customOpts: {
                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
        </script>
    </template:replace>
</template:include>