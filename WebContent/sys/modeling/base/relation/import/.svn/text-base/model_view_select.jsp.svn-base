<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style type="text/css">
        </style>
    </template:replace>
    <template:replace name="content">
        <p class="txttitle" style="margin: 10px 0;">${lfn:message('sys-modeling-base:relation.select.view')}</p>
        <table class="tb_normal" width="98%">
            <tr>
                <td colspan="4">
                    <div style="max-height: 270px; overflow-y: auto;">
                            <%--列表--%>
                        <list:listview id="listview">
                            <ui:source type="AjaxJson">
                                {url:'/sys/modeling/base/pcAndMobileView.do?method=findAllSelect&fdModelId=${param.fdModelId}&fdDevice=${param.fdDevice }'}
                            </ui:source>
                            <!-- 列表视图 -->
                            <list:colTable isDefault="false" onRowClick="selectView('!{fdId}','!{fdName}','!{fdType}');"
                                           name="columntable">
                                <list:col-radio/>
                                <list:col-serial/>
                                <list:col-auto props="fdName;fdTypeIcon;modelMainName;docCreateTime;docCreator"
                                               url=""/></list:colTable>
                            <ui:event topic="list.loaded">
                                initSelected()
                            </ui:event>
                        </list:listview>
                        <list:paging></list:paging>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="4" style="text-align: center;">
                        <%--选择--%>
                    <ui:button text="${lfn:message('button.select')}${lfn:message('sys-modeling-base:relation.default.view')}" onclick="doSubmitSelectDef();"/>
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
                        var selected = {};
                        //预览加载完毕事件
                        window.initSelected = function () {
                            selected.fdId = "${param.fdId}";
                            $('[name="List_Selected"][value="${param.fdId}"]').prop('checked', true);
                            var selectedName = $("[kmss_fdid='${param.fdId}']").find("td").get(2);
                            selected.fdName = $(selectedName).html();
                            var selectedType = $("[kmss_fdid='${param.fdId}']").find(".propertySpanFdType");
                            selected.fdType = $(selectedType).html();
                        };

                        window.selectView = function (fdId, fdName, fdType) {
                            selected = {
                                "fdId": fdId,
                                "fdName": fdName,
                                "fdType": fdType
                            };

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
                                var selectedName = $("[kmss_fdid='" + fdId + "']").find("td").get(2);
                                selected.fdName = $(selectedName).html();
                                var selectedType = $("[kmss_fdid='" + fdId + "']").find(".propertySpanFdType");
                                selected.fdType = $(selectedType).html();
                            }

                            $dialog.hide(selected);
                        };
                        window.doSubmitSelectDef = function () {
                            $dialog.hide({
                                "fdId": "_def",
                                "fdName": "${lfn:message('sys-modeling-base:listview.default.view')}",
                                "fdType": "0"
                            });
                        };

                        window.cancel = function () {
                            $dialog.hide(null);
                        }

                    });
        </script>
    </template:replace>
</template:include>