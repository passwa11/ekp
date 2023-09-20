<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/relation/trigger/behavior/css/behavior.css"/>
    </template:replace>
    <template:replace name="content" >

        <div class="model-mask-panel medium" style="padding-bottom: 72px">
            <div>
                <div class="model-mask-panel-table preQueryTargetView">
                    <table class="tb_simple modeling_form_table " mdlng-rltn-mrk="regionTable">
                        <tbody>
                        <tr id="modelTarget_tr">
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('sys-modeling-base:sysModelingBehavior.modelTarget')}
                            </td>
                            <td width="85%">
                                    <%-- 目标表单--%>
                                <div id="_xform_modelTargetId" _xform_type="text">
                                    <input type="hidden" name="modelTargetId"
                                           value=""/>
                                    <div onclick="selectModel()"
                                         class="model-mask-panel-table-show">
                                        <p class="modelTargetNameBox"></p>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr class="pre_query_where">
                            <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.query.conditions')}</td>
                            <td width="85%">
                                <div style="margin-left:15px;margin-top: 5px;" class="preQueryWhereTypediv">
                                    <label class="PreWhereTypeinput1"><input type="radio" value="0" name="fdPreQueryWhereType"/>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}</label>
                                    <label class="PreWhereTypeinput2"><input type="radio" value="1" name="fdPreQueryWhereType" />${lfn:message('sys-modeling-base:relation.meet.any.conditions')}</label>
                                    <label class="PreWhereTypeinput3" ><input type="radio" value="3" name="fdPreQueryWhereType" />${lfn:message('sys-modeling-base:behavior.no.query.conditions')}</label>
                                </div>
                                <div class="model-mask-panel-table-base view_field_pre_query_where_div">
                                    <table class="tb_normal field_table view_field_pre_query_where_table" width="100%">
                                        <thead>
                                        <tr>
                                            <td width="35%">${lfn:message('sys-modeling-base:behavior.field.name')}</td>
                                            <td width="10%">${lfn:message('sys-modeling-base:modelingAppViewincpara.fdOperator')}</td>
                                            <td width="15%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                            <td width="30%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
                                            <td width="10%">
                                                    ${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}
                                            </td>
                                        </tr>
                                        </thead>
                                    </table>
                                    <div class="model-mask-panel-table-create table_opera" style="margin-left: 0px">
                                        <div> ${lfn:message('sys-modeling-base:button.add')}</div>
                                    </div>
                                </div>
                                <div class="model-mask-panel-table-base">
                                    <table class="tb_normal field_table view_pre_query_where_table" width="100%">

                                    </table>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:modeling.behavior.preQuery.returnValue')}</td>
                            <td width="85%">
                                <div class="view_pre_query_back_value">
                                    <div>
                                        <input type="checkbox" name="count" value="1">${lfn:message('sys-modeling-base:modeling.behavior.preQuery.totalRows')}
                                    </div>
                                    <div>
                                        <input type="checkbox" name="firstRow" value="2">${lfn:message('sys-modeling-base:modeling.behavior.preQuery.firstRow')}
                                    </div>
                                    <div>
                                        <div class="behaviorCheckBox">
                                            <input type="checkbox" name="sum" value="3">${lfn:message('sys-modeling-base:modeling.behavior.preQuery.sum')}
                                        </div>
                                        <div class="muiPerformanceDropdownBox">
                                    <div class="input">
                                        <div class="selectNone">
                                                ${lfn:message('sys-modeling-base:relation.please.choose')}
                                        </div>
                                        <span class="selectedItem"  style='display: none'></span>
                                    </div>
                                    <i class="muiPerformanceDropdownBoxIcon"></i>
                                            <div class="select_view"></div>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="behaviorCheckBox">
                                            <input type="checkbox" name="max" value="4">${lfn:message('sys-modeling-base:modeling.behavior.preQuery.max')}
                                        </div>
                                        <div class="muiPerformanceDropdownBox">
                                        <div class="input">
                                            <div class="selectNone">
                                                    ${lfn:message('sys-modeling-base:relation.please.choose')}
                                            </div>
                                            <span class="selectedItem"  style='display: none'></span>
                                        </div>
                                        <i class="muiPerformanceDropdownBoxIcon"></i>
                                            <div class="select_view"></div>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="behaviorCheckBox">
                                            <input type="checkbox" name="min" value="5">${lfn:message('sys-modeling-base:modeling.behavior.preQuery.min')}
                                        </div>
                                        <div class="muiPerformanceDropdownBox">
                                        <div class="input">
                                            <div class="selectNone">
                                                    ${lfn:message('sys-modeling-base:relation.please.choose')}
                                            </div>
                                            <span class="selectedItem"  style='display: none'></span>
                                        </div>
                                        <i class="muiPerformanceDropdownBoxIcon"></i>
                                            <div class="select_view"></div>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="behaviorCheckBox">
                                            <input type="checkbox" name="avg" value="6">${lfn:message('sys-modeling-base:modeling.behavior.preQuery.avg')}
                                        </div>
                                        <div class="muiPerformanceDropdownBox">
                                        <div class="input">
                                            <div class="selectNone">
                                                    ${lfn:message('sys-modeling-base:relation.please.choose')}
                                            </div>
                                            <span class="selectedItem"  style='display: none'></span>
                                        </div>
                                        <i class="muiPerformanceDropdownBoxIcon"></i>
                                            <div class="select_view"></div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="toolbar-bottom">
                <ui:button styleClass="lui_toolbar_btn_gray" text="${ lfn:message('button.close') }" order="5"
                           onclick="cancle();"/>
                <ui:button text="${ lfn:message('button.ok') }"
                           onclick="ok();"/>
            </div>
        </div>
        <script type="text/javascript">
            Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
            seajs.use(["lui/dialog", "lui/topic","sys/modeling/base/relation/trigger/behavior/js/preQueryTarget",
                "sys/modeling/base/formlog/res/mark/behaviorFMMark"], function (dialog, topic,preQueryTarget,behaviorFMMark) {
                window.targetInfo_load = dialog.loading();
                //监听数据传入
                var _param;
                var intervalEndCount = 10;
                var interval = setInterval(__interval, "50");

                function __interval() {
                    if (intervalEndCount == 0) {
                        console.error("数据解析超时。。。");
                        clearInterval(interval);
                        if(window.targetInfo_load){
                            window.targetInfo_load.hide();
                        }
                    }
                    intervalEndCount--;
                    if (!window['$dialog']) {
                        if(window.targetInfo_load){
                            window.targetInfo_load.hide();
                        }
                        return;
                    }
                    _param = $dialog.___params;
                    init(_param);
                    clearInterval(interval);
                    if(window.targetInfo_load){
                        window.targetInfo_load.hide();
                    }
                }

                function init(_param) {
                    var data = _param.data;
                    window.preQueryTargetInstance = new preQueryTarget.PreQueryTarget({
                        "container": $(".preQueryTargetView"),
                        "xformId":_param.xformId
                    });
                    window.preQueryTargetInstance.startup();

                    if (!$.isEmptyObject(data)) {
                        if (!$.isEmptyObject(data.target)) {
                            initTargetInfo(data.target.value);
                        }
                        window.preQueryTargetInstance.initByStoreData(data);
                    }
                    <%--window.fmmark = new behaviorFMMark.BehaviorFMMark({fdId:"${sysModelingBehaviorForm.fdId}"});--%>
                    <%--fmmark.startup();--%>
                }

                window.cancle=function(){
                    $dialog.hide();
                }

                window.ok=function(){
                    var validate = window.preQueryTargetInstance.validators();
                    if(validate){
                        var keyData = window.preQueryTargetInstance.getKeyData();
                        $dialog.hide(keyData);
                    }
                }

                window.selectModel = function () {
                    dialog.iframe("/sys/modeling/base/relation/import/model_select.jsp?appId="+_param.appId, "${lfn:message('sys-modeling-base:behavior.select.form')}",
                        function (value) {
                            if (value) {
                                $(".modelTargetNameBox").html(value.fdName);
                                $("[name='modelTargetId']").val(value.fdId);
                                var fdName = $(".modelTargetNameBox").text();
                                initTargetInfo(value.fdId,fdName);
                            }
                        }, {
                            width: 1010,
                            height: 600
                        });
                };

                //初始化模块
                window.initTargetInfo = function (modelId,modelName) {
                    var url = "${LUI_ContextPath}/sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
                    $.ajax({
                        url: url,
                        type: "get",
                        async: false,
                        success: function (data, status) {
                            if (data) {
                                topic.channel("modelingBehavior").publish("preQuerySoureData.load", {
                                    "data": JSON.parse(data),
                                    "modelId": modelId
                                });
                            }
                        }
                    });
                }

            });
        </script>
    </template:replace>
</template:include>