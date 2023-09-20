<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.dialog">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/relation/relation/css/relation.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <style>
            .relation_msgText {
                display: none;
            }

            [dataMsgType=add] .msg_add {
                display: inline-block;
            }

            [dataMsgType=remove] .msg_remove {
                display: inline-block;
            }

            #paramArea tr.item,
            #paramArea tr.item:last-child .downBtn,
            #paramArea tr.item:first-child .upBtn {
                display: none;
            }

            .selectedTr {
                display: table-row !important;
            }

            td label,
            td p {
                width: 100%;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <div>


        <p class="txttitle" style="margin: 10px 0;">${lfn:message('sys-modeling-base:behavior.select.trigger.action')}</p>
        <table class="tb_simple" width="98%">
            <tr>
                <td width="44%" valign="top">
                    <table class="tb_normal" width="98%" id="paramSelect">
                        <tr class="table_title">
                            <td class="td_normal_title" colspan="5">${lfn:message('sys-modeling-base:behavior.trigger.to.be.selected')}</td>
                        </tr>
                        <tr class="table_title">
                            <td class="td_normal_title">${lfn:message('sys-modeling-base:modelingExternalShare.fdModel')}</td>
                            <td class="td_normal_title">${lfn:message('sys-modeling-base:behavior.trigger.name')}</td>
                            <td class="td_normal_title" colspan="2">${lfn:message('sys-modeling-base:behavior.type')}</td>
                        </tr>
                        <c:forEach var="item" varStatus="vstatus" items="${data}">
                            <tr lui-mark-data="${ item.fdId}" lui-mark-name="${ item.fdName}"
                                lui-mark-type="${item.fdType }" class="item">
                                <td>
                                    <label style="cursor: pointer;width: 100px;display: inline-block">
                                        <input name="behaviorId" type="checkbox" value="${ item.fdId}">
                                            ${item.modelMain.fdName}
                                    </label>
                                </td>

                                <td>
                                    <p style="width: 120px;">${item.fdName }</p>
                                </td>
                                <td>
                                    <p style="width: 60px;"><sunbor:enumsShow value="${item.fdType }"
                                                                              enumsType="sys_modeling_behavior_type"/></p>
                                </td>
                                <td>
                                    <c:if test="${item.fdType eq '0' }">
                                        <div><label><input class="msgOption" name="${ item.fdId}_msg" type="radio"
                                                           value="add" checked>${lfn:message('sys-modeling-base:behavior.send')}</label>
                                        </div>
                                        <div><label><input class="msgOption" name="${ item.fdId}_msg" type="radio"
                                                           value="remove">${lfn:message('sys-modeling-base:behavior.eliminate')}</label></div>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
                <td width="12%" valign="top">
                    <div class="lui_modeling_nav_opt_content">
                        <div class="lui_modeling_nav_option_add" title="${lfn:message('sys-modeling-base:modelingTransport.button.add')}" onclick="addBehavior();">
                            <div>${lfn:message('sys-modeling-base:modeling.add.right')} </div>
                            <i></i>
                        </div>
                        <div class="lui_modeling_nav_option_delete" title="${lfn:message('sys-modeling-base:modeling.page.delete')}" onclick="removeBehavior();">
                            <i></i>
                            <div>${lfn:message('sys-modeling-base:modeling.add.left')}</div>
                        </div>
                    </div>
                </td>
                <td width="44%" valign="top">
                    <table class="tb_normal" width="98%" id="paramSelected">
                        <tr class="table_title">
                            <td class="td_normal_title" colspan="4">${lfn:message('sys-modeling-base:behavior.selected.trigger')}</td>
                        </tr>
                        <tr class="table_title">
                            <td class="td_normal_title">${lfn:message('sys-modeling-base:modelingExternalShare.fdModel')}</td>
                            <td class="td_normal_title">${lfn:message('sys-modeling-base:behavior.trigger.name')}</td>
                            <td class="td_normal_title">${lfn:message('sys-modeling-base:behavior.type')}</td>
                            <td class="td_normal_title" width="64px">${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</td>
                        </tr>
                    </table>
                    <table style="display: none" id="paramArea">
                        <c:forEach var="item" varStatus="vstatus" items="${data}">
                            <tr lui-mark-data="${item.fdId}" lui-mark-name="${ item.fdName}"
                                lui-mark-type="${item.fdType }" class="item">
                                <td>
                                    <label style="cursor: pointer;width: 100px;display: inline-block">
                                        <input name="behaviorId" type="checkbox" value="${ item.fdId}">
                                            ${item.modelMain.fdName}
                                    </label>
                                </td>

                                <td>
                                    <p style="width: 120px;">${item.fdName }</p>
                                </td>
                                <td>
                                    <p style="width: 80px">
                                        <sunbor:enumsShow value="${item.fdType }"
                                                          enumsType="sys_modeling_behavior_type"/>
                                        <c:if test="${item.fdType eq '0' }">
                                            <span class="relation_msgText msg_add">-${lfn:message('sys-modeling-base:behavior.send')}</span>
                                            <span class="relation_msgText msg_remove">-${lfn:message('sys-modeling-base:behavior.remove')}</span>
                                        </c:if>
                                    </p>

                                </td>
                                <td>
                                    <span class="downBtn" onclick="downParam('${ item.fdId}')"></span>
                                    <span class="upBtn" onclick="upParam('${ item.fdId}')"></span>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>

        </table>
        <div style="height: 60px;width: 100%"></div>
        <div class="toolbar-bottom">
                <%--选择--%>
            <ui:button text="${lfn:message('button.submit')}" onclick="doSubmit();"/> <%--取消--%>
            <ui:button text="${lfn:message('button.cancel') }" onclick="cancel();" styleClass="lui_toolbar_btn_gray"/>
        </div>

        <script type="text/javascript">
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/str'], function ($, dialog, topic, str) {
                var $select = $("#paramSelect");
                var $selected = $("#paramSelected");
                var $param = $("#paramArea");
                window.addBehavior = function () {
                    var ele = $select.find("[name='behaviorId']:checked");
                    ele.each(function (idx, dom) {
                        var fdId = $(dom).val();
                        $(dom).attr("checked", false);
                        var item1 = $select.find("[lui-mark-data='" + fdId + "']");
                        item1.hide();

                        var item2 = $param.find("[lui-mark-data='" + fdId + "']");
                        var type = item1.attr("lui-mark-type");
                        if (type === "0") {
                            var msgOption = item1.find(".msgOption:checked").val();
                            item2.attr("dataMsgType", msgOption);
                        }
                        $selected.append(item2.clone());
                    })

                };
                window.removeBehavior = function () {
                    var ele = $selected.find("[name='behaviorId']:checked");
                    ele.each(function (idx, dom) {
                        var fdId = $(dom).val();
                        var item1 = $select.find("[lui-mark-data='" + fdId + "']");
                        item1.show();

                        var item2 = $selected.find("[lui-mark-data='" + fdId + "']");
                        item2.remove()
                    })
                };

                window.upParam = function (id) {
                    var $ele = $selected.find("[lui-mark-data='" + id + "']");
                    var $pre = $ele.prev("tr");
                    if ($pre.hasClass("table_title")) {
                        return;
                    }
                    $pre.before($ele);
                };
                window.downParam = function (id) {
                    var $ele = $selected.find("[lui-mark-data='" + id + "']");
                    var $next = $ele.next("tr");
                    $next.after($ele);
                };


                window.doSubmit = function () {
                    var names = [];
                    var ids = [];
                    var ext = {};
                    $.each($selected.find(".item"), function (i, e) {
                        names.push($(e).attr("lui-mark-name"));
                        ids.push($(e).attr("lui-mark-data"));
                        var extItem = {
                            doMsgType: $(e).attr("dataMsgType"),
                            type: $(e).attr("lui-mark-type")
                        };
                        ext[$(e).attr("lui-mark-data")] = extItem;
                    });
                    var r = {
                        "names": names,
                        "ids": ids,
                        "ext": ext
                    };
                    $dialog.hide(r);
                };

                window.cancel = function () {
                    $dialog.hide(null);
                }

                //监听数据传入
                var _param;
                var intervalEndCount = 10;
                var interval = setInterval(__interval, "50");

                function __interval() {
                    if (intervalEndCount == 0) {
                        console.error("数据解析超时。。。");
                        clearInterval(interval);
                    }
                    intervalEndCount--;
                    if (!window['$dialog']) {
                        return;
                    }
                    _param = $dialog.___params;
                    onload(_param.oldData, _param.ext)
                    clearInterval(interval);
                }

                function onload(idstr, ext) {
                    var idarr = idstr.split(";");
                    $.each(idarr, function (idx, value) {
                        var fdId =value;
                        var item1 = $select.find("[lui-mark-data='" + fdId + "']");
                        item1.hide();
                        var item2 = $param.find("[lui-mark-data='" + fdId + "']");
                        var type = item1.attr("lui-mark-type");

                        if (type === "0" && ext && ext[fdId]) {
                            item2.attr("dataMsgType", ext[fdId].doMsgType);
                        }
                        $selected.append(item2.clone());
                    })
                }
            });
        </script>
    </template:replace>
</template:include>