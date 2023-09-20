<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
    
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdSubject" ref="criterion.sys.docSubject" title="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdSubject')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdMethod" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdRepeatHandle" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdTodoId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdDingUserId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdUser" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingNotifyQueueError.fdRepeatHandle" text="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdRepeatHandle')}" group="sort.list" />
                            <list:sort property="thirdDingNotifyQueueError.fdSendTime" text="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdSendTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

							<kmss:auth requestURL="/third/ding/notify/third_ding_notify_queue_error/thirdDingNotifyQueueError.do?method=resend">
                                <ui:button text="${lfn:message('third-ding-notify:button.resend')}" onclick="resend()" order="3" id="resend" />
                            </kmss:auth>
                            
                            <kmss:auth requestURL="/third/ding/notify/third_ding_notify_queue_error/thirdDingNotifyQueueError.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/ding/notify/third_ding_notify_queue_error/thirdDingNotifyQueueError.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/notify/third_ding_notify_queue_error/thirdDingNotifyQueueError.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdSubject;fdErrorMsg;fdSendTime;fdRepeatHandle;fdTodoId;fdUser.name" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'notify_queue_error',
                modelName: 'com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError',
                templateName: '',
                basePath: '/third/ding/notify/third_ding_notify_queue_error/thirdDingNotifyQueueError.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-ding-notify:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
        
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function($, dialog, topic, dialogCommon) {
                var option = window.listOption;
	            function resend() {
	                var selected = [];
	                $("input[name='List_Selected']:checked").each(function() {
	                    selected.push($(this).val());
	                });
	                if(selected.length == 0) {
	                    dialog.alert(option.lang.noSelect);
	                    return;
	                }
	                
	                        var del_load = dialog.loading();
	                        var param = {
	                            "List_Selected": selected
	                        };
	                       
	                        $.ajax({
	                            url: option.contextPath + option.basePath + '?method=resend',
	                            data: $.param(param, true),
	                            dataType: 'json',
	                            type: 'POST',
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
	                                    if(data.message[0]){
	                                    	dialog.failure(data.message[0].msg);
	                                    }else{
	                                    	dialog.failure(data.title);
	                                    }
	                                } else {
	                                    dialog.failure('操作失败');
	                                }
	                                del_load.hide();
	                            }
	                        });
	            }
	            window.resend = resend;
            }
            );
        </script>
    </template:replace>
</template:include>