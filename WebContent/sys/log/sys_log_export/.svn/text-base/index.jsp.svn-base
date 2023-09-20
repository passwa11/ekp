<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">下载导出日志</template:replace>
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.sys.log.model.SysLogExport" property="fdExportDate" />
                <list:cri-auto modelName="com.landray.kmss.sys.log.model.SysLogExport" property="fdState" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="sysLogExport.fdExportDate" text="${lfn:message('sys-log:sysLogExport.fdExportDate')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <kmss:auth requestURL="/sys/log/sys_log_export/sysLogExport.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/log/sys_log_export/sysLogExport.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdOperator.name;fdExportDate;fdLogType.name;fdCount;fdDownloadExpire;fdState.name;operation" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.log.model.SysLogExport',
                templateName: '',
                basePath: '/sys/log/sys_log_export/sysLogExport.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-log:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/log/resource/js/", 'js', true);

            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function($, dialog, topic, dialogCommon) {
                var option = window.listOption;
                
	            function download(fdId){
	            	Com_OpenWindow(option.contextPath + option.basePath + "?method=download&fdId=" + fdId, "_blank");
	            }
	            
	            function deleteOne(fdId){
	                dialog.confirm(option.lang.comfirmDelete, function(ok) {
	                    if(ok == true) {
	                        var del_load = dialog.loading();
	                        var param = {
	                            "fdId": fdId
	                        };
	                        $.ajax({
	                            url: option.contextPath + option.basePath + '?method=delete',
	                            data: $.param(param, true),
	                            dataType: 'json',
	                            type: 'GET',
	                            success: function(data) {
	                                if(del_load != null) {
	                                    del_load.hide();
	                                    topic.publish("list.refresh");
	                                }
	                                dialog.result(data);
	                            },
	                            error: function(req) {
	                                if(req.responseJSON) {
	                                    var data = req.responseJSON;
	                                    dialog.failure(data.title);
	                                } else {
	                                    dialog.failure('操作失败');
	                                }
	                                del_load.hide();
	                            }
	                        });
	                    }
	                });
	            }
	            window.download = download;
	            window.deleteOne = deleteOne;
            });
        </script>
    </template:replace>
</template:include>