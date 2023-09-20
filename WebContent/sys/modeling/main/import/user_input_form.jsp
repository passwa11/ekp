<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style type="text/css">
            #userInputTable {
                margin-top: 65px;
                margin-bottom: 30px;

            }

            .userVarTrClass td input {
                width: 90%;
                line-height: 22px;
                border-top: none;
                border-left: none;
                border-right: none;
                border-bottom: 1px solid #333;
            }
        </style>
        <script type="text/javascript">
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("jquery.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("xform.js");
            Com_IncludeFile("xml.js");
            Com_IncludeFile("data.js");
            Com_IncludeFile("formula.js");
            Com_IncludeFile("dialog.js");
            Com_IncludeFile("address.js");
            Com_IncludeFile("treeview.js");
            Com_IncludeFile("calendar.js");
            Com_IncludeFile("jquery-ui/jquery.ui");


        </script>
    </template:replace>
    <template:replace name="content">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <ui:button text="${lfn:message('button.save')}" onclick="doSubmit();"/>
            <ui:button text="${lfn:message('button.cancel') }" onclick="cancel();"/>
        </ui:toolbar>


        <table class="tb_normal" id="userInputTable" width="95%" id="TB_MainTable">

        </table>


        <script type="text/javascript">
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/str'], function ($, dialog, topic, str) {

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

                    initData();
                    clearInterval(interval);
                }

                function initData() {
                    var $container = $("#userInputTable");
                    var userVars = _param.userVars;
                    for (var i = 0; i < userVars.length; i++) {
                        $container.append(getALine(userVars[i]));
                    }

                }

                //常量
                /**
                 "params":{"userVars":[{
	        					"triggerId":"16e1006793a211fb30d35f9408790054",
	        					"triggerName":"无流程2",
	        					"type":"target",
	        					"inputType":1,
	        					"detailName":"",
	        					"detailId":"",
	        					"triggerInputType":"5",
	        					"widgetId":"fd_37d858588e7b46",
	        					"widgetType":"String",
	        					"widgetName":"文本2"
        					}]}
                 **/
                var CONST_USERVAR_TRCLASS = "userVarTrClass";

                function getALine(data) {
                    var $tr = $("<tr/>");
                    $tr.addClass(CONST_USERVAR_TRCLASS);
                    var $td0 = $("<td width='30%' class=\"td_normal_title\"/>");
                    if(data.detailName){
                        $td0.html("["+data.detailName+"]"+data.widgetName);
                    }else{
                        $td0.html(data.widgetName);
                    }
                    $tr.append($td0)
                    var $td1 = $("<td/>");
                    var $input = "";
                    if(data.widgetType.indexOf("com.landray.kmss.sys.organization.model.SysOrg")>-1){
                        //组织架构额外处理
                        $input = getOrgAddress(data);
                    }else if(data.widgetType.indexOf("Date")>-1){
                        //时间
                        $input = getDate(data);
                    }else{
                        $input = getNormalInput(data);
                    }

                    $td1.append($input)
                    $tr.append($td1)
                    $tr.data("orgin", data);
                    return $tr;
                }

                function getNormalInput(data) {
                    return $("<input type='text' />");
                }
                // 返回地址本
                function getOrgAddress (data) {
                    var name = data.triggerId + "_" + data.widgetId;
                    var html = $("<div class='modeling_org_address'/>");
                    html.append("<input type='hidden' name='" + name + "' />");
                    html.append("<input type='text' name='" + name + "_name' class='inputsgl' style='width:150px' readonly/>");
                    var $address = $("<span class='highLight'><a href='javascrip:void(0);'>${lfn:message('sys-modeling-main:sysModeling.btn.choose') }</a></span>");
                    var mulSelect = data.widgetType.indexOf("[]")>-1;
                    var type = "";
                    if(data.widgetType.indexOf("com.landray.kmss.sys.organization.model.SysOrgElement")>-1){
                        type = ORG_TYPE_ALLORG;
                    }
                    $address.on("click", function (e) {
                        Dialog_Address(mulSelect, name, name + "_name", ";", type || ORG_TYPE_PERSON);
                    });
                    html.append($address);
                    return html;
                }
                // 返回时间选择
                function  getDate (data) {
                    var name = data.triggerId + "_" + data.widgetId;
                    var type = data.widgetType;
                    var html = $("<div class='modeling_data_select inputselectsgl'/>");
                    var $input = $(" <div  style='width:85%;min-height:20px;float: left;' />")
                    $input.append("<input type='text' name='" + name + "' style='width:150px' readonly/>");

                    html.append($input);

                    var icon =$("<div class='inputdatetime'/>");
                    //区分日期，时间、时间日期
                    if (type === "Date") {
                        html.on("click", function (e) {
                            selectDate(name, null, function (c) {
                                __CallXFormDateTimeOnValueChange(c);
                            });
                        });
                    } else if (type === "DateTime") {
                        html.on("click", function (e) {
                            selectDateTime(name, null, function (c) {
                                __CallXFormDateTimeOnValueChange(c);
                            });
                        });
                    } else if (type === "Time") {
                        icon = $("<div class='inputtime'/>");
                        html.on("click", function (e) {
                            selectTime(name, null, function (c) {
                                __CallXFormDateTimeOnValueChange(c);
                            });
                        });
                    }
                    html.append(icon);
                    return html;
                }

                /**
                 return ：
                 {
                    "params": {
                        "fdOprId": "16e11434cd3d793aceb499f40519a9b5",
                        "fdId": "16e1149cecd03c1a5fec1bc4dbdaff60",
                        "userVars": [
                            {
                                "triggerId": "16e1006793a211fb30d35f9408790054",
                                "triggerName": "æ— æµç¨‹2",
                                "type": "target",
                                "inputType": 1,
                                "triggerInputType": "5",
                                "widgetId": "fd_37d858588e7b46",
                                "widgetType": "String",
                                "widgetName": "æ–‡æœ¬2"
                            }
                        ]
                    }
                }
                 **/
                window.doSubmit = function () {
                    var userInputVars = [];
                    $.each($("." + CONST_USERVAR_TRCLASS), function (idx, ele) {
                        var value = $(ele).find("input").val();
                        var data = $(ele).data("orgin");
                        data.widgetValue=value;
                        userInputVars.push(data);
                    })

                    var returnParam = {
                        "fdOprId": _param.fdOprId,
                        "fdId": _param.fdId,
                        "ids": _param.ids,
                        "inputVars": userInputVars
                    }
                    var url = "${LUI_ContextPath}/sys/modeling/main/sysModelingOperation.do?method=doOperation";
                    url += "&userInput=true";
                    url += "&fdOprId=" + _param.fdOprId;
                    if ( _param.fdId )
                        url += "&fdId=" + _param.fdId;
                    // if ( _param.ids )
                    //     url += "&List_Selected=" + _param.ids;
                    //url +="&inputVars="+JSON.stringify(userInputVars);
                    var reqData = {
                        "inputVars": userInputVars,
                        "List_Selected":_param.ids
                    }
                    console.debug("reqData",reqData)
                    $.ajax({
                        type: 'POST',
                        url: url,
                        contentType: "application/json",
                        data: JSON.stringify(reqData),
                        // data:$.param(reqData,true),
                        dataType: "json",
                        success: function (result, status) {
                            if (status) {
                                $dialog.hide(JSON.stringify(result));
                            }
                        },

                   });
                };
                window.cancel = function () {
                    $dialog.hide(null);
                };
            });
        </script>
    </template:replace>
</template:include>