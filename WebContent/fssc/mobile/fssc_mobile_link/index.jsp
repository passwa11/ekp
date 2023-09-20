<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.fssc.mobile.model.FsscMobileLink" property="docAlterTime" />
                <list:cri-auto modelName="com.landray.kmss.fssc.mobile.model.FsscMobileLink" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.fssc.mobile.model.FsscMobileLink" property="fdIsAvailable" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="add()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=deleteall">
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
                    {url:appendQueryParameter('/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=data&fdType=${param.fdType}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=view&fdType=${param.fdType}&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdIsAvailable.name;docCreateTime;docCreator.name" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.mobile.model.FsscMobileLink',
                templateName: '',
                basePath: '/fssc/mobile/fssc_mobile_link/fsscMobileLink.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-mobile:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
        	
        	
        	function add(){
        		Com_OpenWindow("<c:url value='/fssc/mobile/fssc_mobile_link/fsscMobileLink.do' />?method=add&fdType=${param['fdType']}");
        	}
        	
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>
