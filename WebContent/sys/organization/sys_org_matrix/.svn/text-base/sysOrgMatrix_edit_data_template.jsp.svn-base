<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <script language="JavaScript">
            var versions = parent.MatrixResult.fdVersions;
            var curVersion = parent.curVersion;
            var templateId = undefined;
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {

                window.getVersionSelect = function (ver) {
                    ver = ver || curVersion;
                    var versionTemplate = [];
                    versionTemplate.push("<div class='matrixSysOrgUpdateVersionTemp'>");
                    versionTemplate.push("   <div class='matrixSysOrgUpdateVersionSelect'>");
                    versionTemplate.push("     <span><bean:message bundle='sys-organization' key='sysOrgMatrix.selectVersion'/></span>");
                    versionTemplate.push("     <select name='selectVer'>");
                    for (var i = 0; i < versions.length; i++) {
                        var verName = versions[i].fdName;
                        var enabled = versions[i].fdIsEnable;
                        var disabled = "";
                        if (verName === ver) {
                            disabled += " selected ";
                        }
                        if (!enabled) {
                            disabled += " disabled='disabled'";
                            verName += " (<span style='color:red;'><bean:message bundle='sys-organization' key='sysOrgMatrix.version.nonactivated'/></span>)";
                        }
                        versionTemplate.push("         <option value='" + versions[i].fdName + "' " + disabled + ">" + verName + "</option>");
                    }
                    versionTemplate.push("     </select>");
                    versionTemplate.push("     </div>");
                    versionTemplate.push("     <div class='matrixSysOrgUpdateVersionBtn'>");
                    versionTemplate.push("          <span onclick='cancel();'><bean:message key='button.cancel'/></span>");
                    versionTemplate.push("          <span class='primary' onclick='ok();'><bean:message key='button.ok'/></span>");
                    versionTemplate.push("     </div>");
                    versionTemplate.push("</div>");
                    return versionTemplate.join("");
                }


                window.sync = function (elem, tplId, version) {
                    event.stopPropagation();
                    templateId = tplId;
                    showVersion($(elem), version);
                }
            });
        </script>
        <link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/matrixData.css">
        <style>
            html, body {
                height: 100%
            }

            .matrix_nonactivateds {
                display: initial;
                width: 100px;
                border: 1px solid #4285f4;
                height: 30px;
                line-height: 30px;
                margin: 30px auto;
                padding: 4px 10px;
                text-align: center;
                cursor: pointer;
                position: relative;
                color: #4285f4;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <div style="margin: 10px;">
            <!-- 操作栏 -->
            <div class="lui_list_operation">
                <!-- 全选 -->
                <div class="lui_list_operation_order_btn">
                    <list:selectall/>
                </div>
                <!-- 操作按钮 -->
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <span class="matrix_nonactivateds">
                            <bean:message bundle='sys-organization' key='sysOrgMatrix.updateAllVersion'/>
                        </span>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation"></ui:fixed>
            <div class="lui_matrix_card_wrap">
                <!-- 内容列表 -->
                <list:listview id="listview">
                    <ui:source type="AjaxJson">
                        {'url':'/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.do?method=list&matrixId=${ JsParam.fdId}&isNew=true'}
                    </ui:source>
                    <list:colTable isDefault="true" layout="sys.ui.listview.columntable">
                        <list:col-checkbox/>
                        <list:col-serial/>
                        <list:col-auto/>
                    </list:colTable>
                </list:listview>
                <br>
                <!-- 分页 -->
                <list:paging/>
            </div>
        </div>

        <script>
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {
                $('.matrix_nonactivateds').on('click', function () {
                    event.stopPropagation();
                    showVersion($(this));
                });

                showVersion = function (self, ver) {
                    var _len = self.has('.matrixSysOrgUpdateVersionTemp').length;
                    if (_len > 0) {
                        $(".matrixSysOrgUpdateVersionTemp").remove();
                    } else {
                        self.append($(getVersionSelect(ver)).on("click", function (event) {
                            event.stopPropagation();
                        }));
                    }
                }

                /* 取消 */
                cancel = function () {
                    event.stopPropagation();
                    $(".matrixSysOrgUpdateVersionTemp").remove();
                    templateId = undefined;
                }

                /* 确定 */
                ok = function () {
                    event.stopPropagation();

                    var ver = $("[name=selectVer]").val();
                    var values = [];
                    if (templateId) {
                        values.push(templateId);
                    } else {
                        $("input[name='List_Selected']:checked").each(function () {
                            values.push($(this).val());
                        });
                    }
                    if (values.length == 0) {
                        dialog.alert('<bean:message key="page.noSelect"/>');
                        return;
                    }

                    var self = $(this);
                    var _len = self.has('.matrixSysOrgUpdateVersionTemp').length;
                    if (_len > 0) {
                        $(".matrixSysOrgUpdateVersionTemp").remove();
                    }

                    var msg = "<bean:message bundle='sys-organization' key='sysOrgMatrix.updateVersionConfirm'/>";
                    msg = msg.replace("{0}", ver);
                    dialog.confirm(msg, function (value) {
                        if (value == true) {
                            $.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.do?method=updateTemplateVersion" />', {
                                'ids': values.join(','),
                                'version': ver,
                                'fdId': '${JsParam.fdId}'
                            }, function (res) {
                                if (res.success) {
                                    dialog.success('<bean:message key="return.optSuccess"/>');
                                    topic.publish("list.refresh");
                                    $(".matrixSysOrgUpdateVersionTemp").remove();
                                } else {
                                    dialog.failure(res.msg);
                                }
                            }, "json");
                        }
                    });
                }

                /* 筛选-隐藏条件部分 */
                $(document).click(function () {
                    $(".matrixSysOrgUpdateVersionTemp").remove();
                    templateId = undefined;
                });
            });
        </script>
    </template:replace>
</template:include>
