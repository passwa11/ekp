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
                <div class="model-mask-panel-table detailQueryTargetView">
                    <table class="tb_simple modeling_form_table " mdlng-rltn-mrk="regionTable">
                        <tbody>
                        <tr id="modelTarget_tr">
                            <td class="td_normal_title" width="15%">
                                    选择明细表
                            </td>
                            <td width="85%">
                                    <%-- 明细表--%>
                                <div id="_xform_detailId" class="detailSelect" _xform_type="text">

                                </div>
                            </td>
                        </tr>
                        <tr class="detail_query_where">
                            <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.query.conditions')}</td>
                            <td width="85%">
                                <div style="margin-left:15px;margin-top: 5px;" class="detailQueryWhereTypediv">
                                    <label class="PreWhereTypeinput1"><input type="radio" value="0" name="fdDetailQueryWhereType"/>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}</label>
                                    <label class="PreWhereTypeinput2"><input type="radio" value="1" name="fdDetailQueryWhereType" />${lfn:message('sys-modeling-base:relation.meet.any.conditions')}</label>
                                    <label class="PreWhereTypeinput3" ><input type="radio" value="3" name="fdDetailQueryWhereType" />${lfn:message('sys-modeling-base:behavior.no.query.conditions')}</label>
                                </div>
                                <div class="model-mask-panel-table-base view_field_detail_query_where_div">
                                    <table class="tb_normal field_table view_field_detail_query_where_table" width="100%">
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
                                    <table class="tb_normal field_table view_detail_query_where_table" width="100%">

                                    </table>
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
            seajs.use(["lui/dialog", "lui/topic","sys/modeling/base/relation/trigger/behavior/js/detailQueryTarget",
                "sys/modeling/base/formlog/res/mark/behaviorFMMark"], function (dialog, topic,detailQueryTarget,behaviorFMMark) {
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
                    window.detailQueryTargetInstance = new detailQueryTarget.DetailQueryTarget({
                        "container": $(".detailQueryTargetView"),
                        "xformId":_param.xformId,
                        "sourceData":_param.sData,
                        "detailContainer":$("#_xform_detailId"),
                        "detailIds":_param.detailIds,
                        "detailId":_param.detailId
                    });
                    window.detailQueryTargetInstance.startup();

                    if (!$.isEmptyObject(data)) {
                        window.detailQueryTargetInstance.initByStoreData(data);
                    }
                    window.fmmark = new behaviorFMMark.BehaviorFMMark({fdId:_param.behaviorId});
                    fmmark.startup();
                }

                window.cancle=function(){
                    $dialog.hide();
                }

                window.ok=function(){
                    var validate = window.detailQueryTargetInstance.validators();
                    if(validate){
                        var keyData = window.detailQueryTargetInstance.getKeyData();
                        $dialog.hide(keyData);
                    }
                }

            });
        </script>
    </template:replace>
</template:include>