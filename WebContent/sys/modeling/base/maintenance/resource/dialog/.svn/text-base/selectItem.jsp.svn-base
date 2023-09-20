<%--
  Created by IntelliJ IDEA.
  User: 96581
  Date: 2020/11/27
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog" sidebar="no">
    <template:replace name="content">
        <script>
            Com_IncludeFile("maintenance.css", "${LUI_ContextPath}/sys/modeling/base/maintenance/resource/", "css", true);
        </script>
        <!-- 只复制该内容区域即可 starts -->
        <div class="maintenance-toolbox-dialog">
            <div class="toolbox-dialog-container">
                <div data-lui-mark="step_0" class="form-item-select">
                    <p style="color: #998080;font-size: 12px;margin: 5px 0px;"> ${lfn:message("sys-modeling-base:modeling.maintenance.fix.history.data")}</p>
                    <table class="tb-simple item-select-table">
                        <tr>
                            <th class="td-left">${lfn:message("sys-modeling-base:modeling.maintenance.fix.item")}</th>
                            <th class="td-left">${lfn:message("sys-modeling-base:modeling.maintenance.fix.desc")}</th>
                        </tr>
                        <tr>
                            <td class="td-left">
                                <label style="cursor: pointer">
                                    <input type="checkbox" name="selectItem" value="CreatorBeingModified" checked="checked">
                                    1、${lfn:message("sys-modeling-base:modeling.maintenance.fix.creator.data")}
                                </label>

                            </td>
                            <td class="td-left">
                                    ${lfn:message("sys-modeling-base:modeling.maintenance.fix.reason")}
                            </td>
                        </tr>
                    </table>
                </div>
                <div data-lui-mark="step_1" class="form-item-promotion">
                    <div class="item-status-result success">
                            ${lfn:message("sys-modeling-base:modeling.maintenance.check.exception")}
                    </div>
                    <div class="form-item-content">
                    </div>
                </div>
            </div>
            <!-- 正在检测/升级：detecting;
                检测/升级完成：finish；
                检测/升级未完成：incomplete；
                选择要升级的配置项：promotion； -->
            <div class="toolbox-dialog-status">
                <div class="state promotion" data-lui-mark="step_0">
                    <span class="bar"></span>
                    <div class="state-content">
                        <div class="continue statusBtn">${lfn:message("sys-modeling-base:modeling.start.testing")}</div>
                    </div>
                </div>
                <div class="state promotion" data-lui-mark="step_1">
                    <span class="bar"></span>
                    <div class="state-content">
                        <div class="desc">
                            <i class="selectAll"></i>
                            <span>${lfn:message("ui.listview.selectall")}</span>
                        </div>
                        <div class="continue statusBtn">${lfn:message("sys-modeling-base:modeling.maintenance.start.fix")}</div>
                        <div class="cancel statusBtn">${lfn:message("button.back")}</div>
                    </div>
                </div>

            </div>
        </div>
        <!-- 只复制该内容区域即可 ends -->
        <script type="text/javascript">
            seajs.use(["lui/jquery", "lui/dialog", "sys/modeling/base/maintenance/resource/dialog/selectItem.js"], function ($, dialog, selectItem) {
                //监听数据传入
                var _param;
                var _hashParam={};
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
                    initData();
                    clearInterval(interval);
                }

                function initData() {
                    var cfg = {"element": $(".maintenance-toolbox-dialog"),"_param":_param};
                    var si = new selectItem.SelectItem(cfg);
                }


            })
        </script>
    </template:replace>
</template:include>