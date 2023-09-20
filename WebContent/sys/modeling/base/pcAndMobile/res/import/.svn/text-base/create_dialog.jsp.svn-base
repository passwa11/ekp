<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.profile.edit" sidebar="no">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <script>
            Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
        </script>
    </template:replace>
    <template:replace name="content">
        <center>
            <form>
                    <%-- <h2 align="center" style="margin: 10px 0">
                        <span class="profile_config_title">${lfn:message('sys-modeling-base:modeling.model.createForm')}</span>
                    </h2> --%>
                <table class="tb_simple model-view-panel-table" style="margin-top:20px" width=95%>
                    <tr>
                        <td class="td_normal_title" width=25% style="line-height: 30px;">
                            ${ lfn:message('sys-modeling-base:listview.view.name') }
                        </td>
                        <td width=75% class="model-view-panel-table-td">
                            <input class="inputsgl" placeholder="${ lfn:message('sys-modeling-base:listview.enter.view.name') }"
                                   style="width: 95%;padding-left:8px;font-size:12px;height:27px;border-radius: 2px"
                                   name="fdName" subject="${ lfn:message('sys-modeling-base:listview.view.name') }" type="text" validate="required"/><span
                                class="txtstrong">*</span>
                        </td>
                    </tr>
                    <tr id="fdIsDefaultTr" style="display: none">
                        <td class="model-view-panel-table-td model-view-setting" colspan="2" style="line-height: 30px;">
                            <div class="model-view-is-default">
                                <div>${ lfn:message('sys-modeling-base:listview.set.default.view') }</div>
                                <div class="modeling-viewcover-tip">
                                    <span>${ lfn:message('sys-modeling-base:listview.set.default.view.tips') }</span>
                                </div>
                                <ui:switch property="fdIsDefault" id="fdIsDefault" checkVal="true" unCheckVal="false"></ui:switch>
                            </div>
                        </td>
                    </tr>
                    <tr id="fdOrgElementTr" style="display: none">
                        <td class="td_normal_title" width=25% style="line-height: 52px;">
                            ${ lfn:message('sys-modeling-base:modelingAppListview.authSearchReaders') }
                        </td>
                        <td width=75% class="model-view-panel-table-td">


                            <div class='inputselectmul' style="width: 95%;height:120px;">
                                <input name="fdOrgElementIds" xform-name="fdOrgElementNames" value=""
                                       type="hidden"/>
                                <div class="textarea" style="overflow:auto;">
                                    <textarea style="display:none;" xform-type="newAddressHidden"
                                              xform-name="fdOrgElementNames" name="fdOrgElementNames"
                                              style="height: 60px;"></textarea>
                                    <textarea xform-type="newAddress" xform-name="mf_fdOrgElementNames"
                                              data-propertyId="fdOrgElementIds" data-propertyName="fdOrgElementNames"
                                              data-splitChar=";" data-orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES"
                                              data-isMulti="true"></textarea>
                                </div>
                                <div onclick="Dialog_Address(true,'fdOrgElementIds','fdOrgElementNames',';',ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,null,null,null,null,null,null,null);"
                                     class="orgelement"></div>
                            </div>
                            <br>
                            <div style="color: #999999;">${ lfn:message('sys-modeling-base:respanel.Empty.everyone.operate') }</div>
                        </td>
                    </tr>
                </table>
                <div class="lui_custom_list_boxs" style="margin-top:20px">
                    <center>
                        <div class="lui_custom_list_box_content_col_btn" style="text-align: right;width: 95%">
                            <ui:button styleClass="lui_custom_list_box_content_blue_btn" onclick="doSubmit();"
                                       text="${ lfn:message('button.save') }">
                            </ui:button>
                            <ui:button styleClass="lui_custom_list_box_content_whith_btn" onclick="cancel();"
                                       text="${ lfn:message('button.cancel') }">
                            </ui:button>
                        </div>
                    </center>
                </div>
            </form>
        </center>
        <script>Com_IncludeFile('dialog.js|data.js|jquery.js');</script>
        <script>Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js', 'js/jquery-plugin/manifest/');</script>
        <script>

            var _validation = $KMSSValidation();

            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/str'], function ($, dialog, topic, str) {
                //监听数据传入
                var _param;
                var intervalEndCount = 100;
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
                    initInputs(_param);
                    clearInterval(interval);
                }

                function initInputs(_param) {
                    var authReaders = [];
                    var fdName = "";
                    var fdIsDefault = "";
                    if (_param) {
                        authReaders = _param.authReaders ? _param.authReaders : authReaders;
                        fdName = _param.fdName ? _param.fdName : fdName;
                        fdIsDefault = _param.fdIsDefault ? _param.fdIsDefault : fdIsDefault;
                    }
                    $("[name='fdName']").val(fdName);
                    if(fdIsDefault){
                        $("#fdIsDefaultTr").show();
                        LUI("fdIsDefault").config.checked=fdIsDefault;
                        LUI("fdIsDefault").refresh();
                    }

                    if (!_param.noReaders==true){
                        $("#fdOrgElementTr").show();
                    }
                    Address_QuickSelection("fdOrgElementIds", "fdOrgElementNames",
                        ";", ORG_TYPE_ALL | ORG_FLAG_BUSINESSYES, true, authReaders, null, null, "");
                    if (_param.authReaderIds) {
                        var ids = $("[name=\"fdOrgElementIds\"]").val();
                        if (_param.authReaderIds != ids) {
                            $("[name=\"fdOrgElementIds\"]").val(_param.authReaderIds.trim());
                        }
                    }
                }

                window.doSubmit = function () {
                    if (!_validation.validate()) {
                        return
                    }
                    var returnParam = {
                        authReaders: $("[name=\"fdOrgElementIds\"]").val(),
                        fdName: $("[name=\"fdName\"]").val(),
                        fdIsDefault: $("[name=\"fdIsDefault\"]").val()
                    };
                    if (returnParam) {
                        $dialog.hide(returnParam);
                    } else {
                        alert("${ lfn:message('sys-modeling-base:listview.input.abnormal') }");
                    }
                };
                window.cancel = function () {
                    $dialog.hide(null);
                };
            });

        </script>
    </template:replace>
</template:include>