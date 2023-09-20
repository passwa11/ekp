<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${lfn:message('fssc-config:fsscConfigScore.fdYear_fdMonth')}" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigScore" property="fdScoreInit" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigScore" property="fdScoreRemain" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigScore" property="fdScoreUse" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigScore" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigScore" property="fdPerson" />
                <list:cri-auto modelName="com.landray.kmss.fssc.config.model.FsscConfigScore" property="docCreateTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscConfigScore.fdMonth" text="${lfn:message('fssc-config:fsscConfigScore.fdMonth')}" group="sort.list" />
                            <list:sort property="fsscConfigScore.fdScoreInit" text="${lfn:message('fssc-config:fsscConfigScore.fdScoreInit')}" group="sort.list" />
                            <list:sort property="fsscConfigScore.fdScoreRemain" text="${lfn:message('fssc-config:fsscConfigScore.fdScoreRemain')}" group="sort.list" />
                            <list:sort property="fsscConfigScore.fdScoreUse" text="${lfn:message('fssc-config:fsscConfigScore.fdScoreUse')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/config/fssc_config_score/fsscConfigScore.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/config/fssc_config_score/fsscConfigScore.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                            <kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.config.model.FsscConfigScore">
                                <ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.config.model.FsscConfigScore')">
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
                    {url:appendQueryParameter('/fssc/config/fssc_config_score/fsscConfigScore.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/config/fssc_config_score/fsscConfigScore.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdMonth.name;fdYear.name;fdPerson.name;fdScoreInit;fdScoreRemain;fdScoreUse;docCreator.name;docCreateTime" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'score',
                modelName: 'com.landray.kmss.fssc.config.model.FsscConfigScore',
                templateName: '',
                basePath: '/fssc/config/fssc_config_score/fsscConfigScore.do',
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
	        		dialog.iframe("/fssc/config/common/fsscConfigScore_import.jsp", 
	        				'导入',function(){
	        					LUI("listview").source.get();
	        				},{width:550,height:320});
	        	}
            });
        </script>
    </template:replace>
</template:include>