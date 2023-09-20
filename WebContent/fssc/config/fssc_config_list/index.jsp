<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('fssc-config:fsscConfigList.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigList" property="fdCode" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigList" property="fdGoodsType" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigList" property="fdGoodsProperty" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigList" property="fdMinNum" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigList" property="fdPrice" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscConfigList.fdMinNum" text="${lfn:message('fssc-config:fsscConfigList.fdMinNum')}" group="sort.list" />
                            <list:sort property="fsscConfigList.fdPrice" text="${lfn:message('fssc-config:fsscConfigList.fdPrice')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="4">

                            <kmss:auth requestURL="/fssc/config/fssc_config_list/fsscConfigList.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/config/fssc_config_list/fsscConfigList.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                            <kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.config.model.FsscConfigList">
                                <ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.config.model.FsscConfigList')">
                                </ui:button>
                            </kmss:auth>
                            <ui:button text="导入" onclick="importExpert()" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/config/fssc_config_list/fsscConfigList.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/config/fssc_config_list/fsscConfigList.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdCode;fdGoodsType;fdGoodsProperty;fdMinNum;fdUnit;fdPrice;docCreator.name" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'list',
                modelName: 'com.landray.kmss.fssc.config.model.FsscConfigList',
                templateName: '',
                basePath: '/fssc/config/fssc_config_list/fsscConfigList.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-config:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/config/resource/js/", 'js', true);
            seajs.use(['lui/dialog','lui/topic'],function(dialog, topic){
	        	window.importExpert = function(){
	        		dialog.iframe("/fssc/config/common/fsscConfigList_import.jsp", 
	        				'导入',function(){
	        					LUI("listview").source.get();
	        				},{width:550,height:320});
	        	}
            });
        </script>
    </template:replace>
</template:include>