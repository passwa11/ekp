<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <style type="text/css">

        </style>
    </template:replace>
    <template:replace name="content">
        <div>
            <div class="model-mask-panel-edit">
                <div class="model-mask-panel-edit-title">
                    <p>${lfn:message('sys-modeling-base:relation.editing.area.below')}
                        <span style="color: red">${lfn:message('sys-modeling-base:relation.enter')}</span>
                            ${lfn:message('sys-modeling-base:relation.text.or.parameter.area')}
                        <span style="color: red">${lfn:message('sys-modeling-base:relation.click.on')}</span>
                            ${lfn:message('sys-modeling-base:relation.select.parameters.to.add')}</p><i></i>
                </div>
                <div class="model-mask-panel-edit-content" ondragover="allowDrop(event)" ondrop="drop(event)"
                     onclick="onEdit()">
                    <textarea name="input" placeholder="${lfn:message('sys-modeling-base:relation.enter.or.click.button.area')}"></textarea>
                </div>
            </div>
            <div class="model-mask-panel-flag">
                <div class="model-mask-panel-flag-title">${lfn:message('sys-modeling-base:relation.parameter.area')}</div>
                <div class="model-mask-panel-flag-content" id="paramSelect">
                </div>
            </div>
        </div>
        </div>
        <div class="toolbar-bottom">
            <ui:button text="${lfn:message('button.cancel') }" onclick="cancel();" styleClass="lui_toolbar_btn_gray"/>
            <ui:button text="${lfn:message('button.select')}" onclick="doSubmit();"/> <%--取消--%>

        </div>
        <script type="text/javascript">
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/str'], function ($, dialog, topic, str) {
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

                //参数计数
                var idxP = 0;
                var paramArray = [];

                function initData() {
                    console.debug("initData", _param)
                    //数据展现
                    if (_param.multi) {
                        $("#paramInput").show()
                    }
                    //选择框生成
                    for (var k in _param.data) {
                        var d = _param.data[k];
                        //过滤附件
                        if(d.type && d.type.toLowerCase().indexOf("attachment")>-1)
                            continue;
                        //Clob不处理
                        if(d.isClob)
                            continue;
                        //过滤明细表
                        if ((_param.isDetail && d.name && d.name.indexOf(".") > 0 && d.name.indexOf(_param.targetDetail)<0))
                            continue;
                        var div = getItem(d);
                        $("#paramSelect").append(div)
                    }
                    //历史数据
                    if (_param.oldData) {
                        if (typeof _param.oldData === "string") {
                            _param.oldData = JSON.parse(_param.oldData);
                        }
                        var str = "";
                        $.each(_param.oldData, function (i, v) {
                            //#119913 自动同步最新label
                            if (v.name&&_param.data[v.name]){
                                v.label = _param.data[v.name].fullLabel || _param.data[v.name].label;
                            }
                            if(v.type === "_inputType"){
                                str +=v.label;
                            }else{
                                str +="$"+v.label+"$";
                            }
                            if(str != ""){
                                onEdit();
                                $("[name=\"input\"]").val(str)
                            }

                        })
                    }
                }

                function getItem(data) {
                    //#164143 当字段过长时，label会缩略为xxx...,当多个字段的缩略都为xxx...时，会导致字段解析错误，故使用fullLabel
                    let label = data.fullLabel || data.label;
                    _hashParam[label]=data;
                    var $dom = $("<div class=\"model-mask-flag-item\" draggable=\"true\">\n" +
                        "          <div>  <p class='model-mask-flag-item-title'>" + label + "</p>\n" +
                        "       </div> </div>");
                    let $title = $dom.find(".model-mask-flag-item-title");
                    $title.attr("title",label);
                    $dom.attr("item-id", data.id)
                    $dom.bind('click', function (e) {
                        clickItem($(this), data, e);
                    });
                    return $dom;
                }

                function clickItem($e, data, e) {
                    onEdit();
                    var value = $("[name=\"input\"]").val();
                    value = value + "$" + (data.fullLabel || data.label) + "$";
                    $("[name=\"input\"]").val(value)
                }



                window.onEdit = function () {
                    $("[name=\"input\"]").css("opacity", 1)
                };

                //根据传入值生成常量对象
                function generateConstantObj(val){
                    return {
                        label: val,
                        name: val,
                        type: "_inputType",
                        businessType: "_inputType"
                    }
                }
                function formatParam(str) {
                    var lastReturnArr = [],
                        operateStr = str,
                        pattern = /\\$([^\\$]*)\\$/g,
                        index_ = 0;
                    while (/\\$[^\\$]+\\$/.test(operateStr)) {//检测字符串中是否存在变量
                        index_ = operateStr.search(/\\$[^\\$]+\\$/);//拿到字符串中变量index
                        if(index_ !== 0) {//数组中推入常量（有的话）
                            var constObj = generateConstantObj(operateStr.substring(0, index_));//常量对象
                            lastReturnArr.push(constObj);
                        }
                        var variableObj = _hashParam[pattern.exec(str)[1]];
                        if(!variableObj){
                            return ;
                        }
                        lastReturnArr.push(variableObj);//数组中推入变量
                        operateStr = str.substring(pattern.lastIndex);//截取判断字符串
                    }
                    operateStr !== "" && lastReturnArr.push(generateConstantObj(operateStr));
                    return lastReturnArr;
                }
                window.doSubmit = function () {
                    var orgVal = $("[name=\"input\"]").val();
                    var  returnParam=formatParam(orgVal);
                    if(returnParam){
                        $dialog.hide(returnParam);
                    }else {
                        alert("${lfn:message('sys-modeling-base:relation.return.value.input.abnormal')}");
                    }
                };
                window.cancel = function () {
                    $dialog.hide(null);
                };
            });
        </script>
    </template:replace>
</template:include>