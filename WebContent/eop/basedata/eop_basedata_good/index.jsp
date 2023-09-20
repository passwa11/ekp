<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list" spa="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('eop-basedata:module.eop.basedata') }-${ lfn:message('eop-basedata:table.eopBasedataGood') }" />
    </template:replace>
    <template:replace name="content">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref ref="criterion.sys.docSubject" title="${lfn:message('eop-basedata:eopBasedataGood.fdName')}" expand="true" key="fdName" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataGood"  property="fdCode" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_good/eopBasedataGood.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1" />
                                <ui:button text="${lfn:message('eop-basedata:eopBasedataGood.btn.initData')}" onclick="window.open('${LUI_ContextPath}/eop/basedata/eop_basedata_good/eopBasedataGood_initData.xls');" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_good/eopBasedataGood.do?method=deleteall">
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
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_good/eopBasedataGood.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_good/eopBasedataGood.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'main',
                modelName: 'com.landray.kmss.eop.basedata.model.eopBasedataGood',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_good/eopBasedataGood.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("eop-basedata:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
            //列表页面切换树形显示
            function switchTree(){
                var url = Com_Parameter.ContextPath+"eop/basedata/eop_basedata_good/eopBasedataGood.do?method=list";
                window.location.href = url;
            }
        </script>
    </template:replace>
</template:include>