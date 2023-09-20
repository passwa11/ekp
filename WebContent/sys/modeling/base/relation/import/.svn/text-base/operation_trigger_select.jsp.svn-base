<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>
<%@ page import="com.landray.kmss.sys.modeling.base.constant.ModelingAppVersionConstant" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style type="text/css">
        </style>
    </template:replace>
    <template:replace name="content">
        <c:if test="${param.type=='0'}">
        <p class="txttitle" style="margin: 10px 0;"> ${lfn:message('sys-modeling-base:sysModelingOperation.fdScenes')}</p>
    </c:if>
        <c:if test="${param.type=='1'}">
            <p class="txttitle" style="margin: 10px 0;"> ${lfn:message('sys-modeling-base:sysModelingOperation.fdBehavior')}</p>
        </c:if>
        <table class="tb_normal" width="98%">
                <%--            场景--%>
            <c:if test="${param.type=='0'}">
            <tr>
                <td colspan="4">

                    <div style="max-height: 270px; overflow-y: auto;">
                            <%--列表--%>
                        <list:listview id="listview" style="">
                            <ui:source type="AjaxJson">
                                {url:'/sys/modeling/base/sysModelingScenes.do?method=data&fdType=1&q.modelMain=${param.modelId}'}
                            </ui:source>
                            <list:colTable isDefault="false" onRowClick="selectRdmTask('!{fdId}','!{fdName}');"
                                           rowHref=""
                                           name="columntable">
                                <list:col-radio/>
                                <list:col-html title="${lfn:message('sys-modeling-base:sysModelingBehavior.fdName')}">
                                    {$ <span data-id="{%row['fdId']%}_name"
                                             style="max-width: 500px; display: inline-block;">{%row['fdName']%}</span> $}
                                </list:col-html>
                                <list:col-html title="${lfn:message('sys-modeling-base:sysModelingBehavior.fdType')}">
                                    {$ <span data-id="{%row['fdId']%}">{%row['fdType']%}</span> $}
                                </list:col-html>
                                <list:col-html title="${lfn:message('sys-modeling-base:sysModelingBehavior.docCreator')}">
                                    {$ <span data-id="{%row['fdId']%}">{%row['docCreator.name']%}</span> $}
                                </list:col-html>
                                <list:col-html title="${lfn:message('sys-modeling-base:sysModelingBehavior.docCreateTime')}">
                                    {$ <span data-id="{%row['fdId']%}">{%row['docCreateTime']%}</span> $}
                                </list:col-html>
                            </list:colTable>
                        </list:listview>
                        <list:paging></list:paging>
                    </div>
                </td>
            </tr>
            </c:if>
                    <c:if test="${param.type=='1'}">
                <%--动作--%>
            <tr>
                <td colspan="4">

                    <div style="max-height: 270px; overflow-y: auto;">
                        <list:listview id="listview" style="">
                            <ui:source type="AjaxJson">
                                {url:'/sys/modeling/base/sysModelingBehavior.do?method=data&q.modelMain=${param.modelId}'}
                            </ui:source>
                            <list:colTable isDefault="false" onRowClick="selectRdmTask('!{fdId}','!{fdName}');"
                                           rowHref=""
                                           name="columntable">
                                <list:col-radio/>
                                <list:col-html title="${lfn:message('sys-modeling-base:sysModelingBehavior.fdName')}">
                                    {$ <span data-id="{%row['fdId']%}_name"
                                             style="max-width: 500px; display: inline-block;">{%row['fdName']%}</span> $}
                                </list:col-html>
                                <list:col-html title="${lfn:message('sys-modeling-base:sysModelingBehavior.fdType')}">
                                    {$ <span data-id="{%row['fdId']%}">{%row['fdType']%}</span> $}
                                </list:col-html>
                                <list:col-html title="${lfn:message('sys-modeling-base:sysModelingBehavior.docCreator')}">
                                    {$ <span data-id="{%row['fdId']%}">{%row['docCreator.name']%}</span> $}
                                </list:col-html>
                                <list:col-html title="${lfn:message('sys-modeling-base:sysModelingBehavior.docCreateTime')}">
                                    {$ <span data-id="{%row['fdId']%}">{%row['docCreateTime']%}</span> $}
                                </list:col-html>
                            </list:colTable>
                        </list:listview>
                        <list:paging></list:paging>
                    </div>
                </td>
            </tr>
                    </c:if>
            <tr>
                <td colspan="4" style="text-align: center;">
                        <%--选择--%>
                    <ui:button text="${lfn:message('button.select')}" onclick="doSubmit();"/> <%--取消--%>
                    <ui:button text="${lfn:message('button.cancel') }" onclick="cancel();"/>
                </td>
            </tr>
        </table>

        <script type="text/javascript">
            seajs
                .use(
                    ['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/str'],
                    function ($, dialog, topic, str) {
                        var selected = {}
                        window.selectRdmTask = function (fdId, fdName) {
                            selected = {
                                "fdId": fdId,
                                "fdName": fdName
                            }
                            if ($('[name="List_Selected"][value="' + fdId + '"]').is(':checked')) {
                                $('[name="List_Selected"][value="' + fdId + '"]').prop('checked', false);
                            } else {
                                $('[name="List_Selected"][value="' + fdId + '"]').prop('checked', true);
                            }
                        }

                        window.doSubmit = function () {
                            var fdId = $('[name="List_Selected"]:checked').val();
                            if (selected.fdId || fdId != selected.fdId) {
                                selected.fdId = fdId;
                                var selectedName = $('[data-id="'+fdId+'_name"]').html();
                                selected.fdName = selectedName;
                            }
                            $dialog.hide(selected);
                        };

                        window.cancel = function () {
                            $dialog.hide(null);
                        }

                    });
        </script>
    </template:replace>
</template:include>