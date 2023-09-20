<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
    <template:replace name="content">
        <%--spa初始化，影响日期的默认值设置--%>
        <div data-lui-type="lui/spa!Spa" style="display: none;">
            <script type="text/config">
                {"groups": "${param['spa-groups']}" }


            </script>
        </div>
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.sys.modeling.base.model.ModelingOperLog"
                               property="fdCreateTime" expand="true" cfg-defaultValue="1"/>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                        ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="modelingOperLog.fdCreateTime"
                                       text="${lfn:message('sys-modeling-base:modelingOperLog.fdCreateTime')}"
                                       group="sort.list" value="down"/>
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top"/>
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <kmss:auth requestURL="/sys/modeling/base/modelingOperLog.do?method=exportData">
                                <!--导出数据-->
                                <ui:button text="${lfn:message('sys-modeling-base:btn.exportData')}"
                                           onclick="exportData()" order="5"/>
                            </kmss:auth>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation"/>
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/modeling/base/modelingOperLog.do?method=data&fdAppId=${param.fdAppId}')}
                </ui:source>
                <list:colTable isDefault="false" name="columntable">
                    <list:col-auto props="fdCreator.name;fdIp;fdOperItemName;subject;fdCreateTime"/>
                </list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging/>
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.modeling.base.model.ModelingOperLog',
                templateName: '',
                basePath: '/sys/modeling/base/modelingOperLog.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-modeling:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);

            // #117162
            function __refreshList() {
                seajs.use(["lui/jquery", "lui/topic"], function ($, topic) {
                    topic.publish("list.refresh");
                });
            }

            function exportData() {
                seajs.use('lui/dialog', function (dialog) {
                    if(LUI('listview').table._data.page.totalSize <=0){
                        let title = '<bean:message key="sys-modeling-base:page.comfirmExport.fail.nodata"/>';
                        dialog.alert(title);
                    }else{
                        let title = '<bean:message key="sys-modeling-base:page.comfirmExport.part1"/>';
                        if (LUI('listview').table._data.page.totalSize > 5000) {
                            title += '<bean:message key="sys-modeling-base:page.comfirmExport.part2"/>';
                        }
                        title += '<bean:message key="sys-modeling-base:page.comfirmExport.part3"/>';
                        dialog.confirm(title, function (value) {
                            if (value == true) {
                                window.__exportLoading = dialog.loading();
                                let listview = LUI('listview');
                                let url = "${LUI_ContextPath}" + listview.table._resolveUrls(listview.cacheEvt);
                                url = url.replace("method=data", "method=exportData");
                                if ($('#exportDownloadIframe').length > 0) {
                                    $('#exportDownloadIframe')[0].src = url;
                                } else {
                                    var elemIF = document.createElement("iframe");
                                    elemIF.id = "exportDownloadIframe";
                                    elemIF.src = url;
                                    elemIF.style.display = "none";
                                    document.body.appendChild(elemIF);
                                }
                                setDownloadTimer();
                            }
                        });
                    }
                });
            }

            var downloadTimer;

            function setDownloadTimer() {
                downloadTimer = setInterval(function () {
                    var iframe = document.getElementById('exportDownloadIframe');
                    var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
                    if (iframeDoc.readyState == 'complete' || iframeDoc.readyState == 'interactive') {
                        //隐藏loading
                        setTimeout("downloadComplete()", 500);
                    }
                }, 1000);
            }

            function downloadComplete() {
                var iframe = document.getElementById('exportDownloadIframe');
                var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
                window.__exportLoading.hide();
                if (!iframeDoc.body.innerHTML) {
                    //开始下载则显示成功弹框
                    seajs.use('lui/dialog', function (dialog) {
                        dialog.success({
                            status: true,
                            title: '<bean:message key="sys-modeling-base:page.exportFinished"/>'
                        });
                    });
                }
                clearInterval(downloadTimer);
            }

            //刷新iframe高度
            $(function () {
                seajs.use(["lui/jquery", "sys/ui/js/dialog", "lui/topic"], function ($, dialog, topic) {
                    topic.subscribe("list.loaded", setIframeHeight);
                });

                let interval = setInterval(function () {
                    let btnName = "${lfn:message('sys-modeling-base:table.modelingOperLog')}";
                    let btn = $("body", parent.document).find('input[value="' + btnName + '"]');
                    btn.on("click", setIframeHeight);
                    if (btn.length > 0) {
                        clearInterval(interval);
                    }
                }, 200);
            })

            function setIframeHeight() {
                var bodyHeight = $(document.body).outerHeight(true) + 70;
                $("body", parent.document).find('#operlogIframe').animate({
                    height: bodyHeight
                }, "fast");
            }

        </script>
    </template:replace>
</template:include>