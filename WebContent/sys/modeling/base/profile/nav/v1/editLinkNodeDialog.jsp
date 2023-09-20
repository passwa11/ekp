<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <script>
            Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
        </script>
        <style>
            .lui_custom_list_boxs {
                border-top: 1px solid #d5d5d5;
                position: fixed;
                bottom: 0;
                width: 100%;
                background-color: #fff;
                z-index: 1000;
                height: 63px;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <form>
            <center>
                <table class="tb_simple model-view-panel-table" style="margin-top:10px;margin-bottom: 65px;" width=95%>
                    <tr>
                        <td class="td_normal_title" width=15%>
                            <span class="title_wrap">${lfn:message("sys-modeling-base:modeling.model.fdName")}</span>
                        </td>
                        <td width=85% class='model-view-panel-table-td'>
                            <div class="inputContainer" style="width:100%">
                                <input name="text" subject="${lfn:message("sys-modeling-base:modeling.model.fdName")}" class="inputsgl" value="" type="text"
                                       validate="required max8 maxLength(200)" style="width:96%"
                                       __validate_serial="_validate_1"><span
                                    class="txtstrong">*</span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width=15%>
                            <span class="title_wrap">${lfn:message("sys-modeling-base:modeling.link.address")}</span>
                        </td>
                        <td width=85% class='model-view-panel-table-td'>
                            <div class="inputContainer" style="width:100%">
                                <input name="value" subject="${lfn:message("sys-modeling-base:modeling.link.address")}" class="inputsgl" value="" type="text"
                                       validate="required maxLength(1000) urlValidation" style="width:96%"
                                       __validate_serial="_validate_2">
                                <span
                                        class="txtstrong">*</span>
                            </div>
                            <div style="color: #999999;">${lfn:message("sys-modeling-base:modeling.system.link.only")}/sys/modeling/base/profile/nav/index.jsp</div>
                        </td>
                    </tr>
                </table>
                <div class="lui_custom_list_boxs" style="margin-top:20px">
                    <center>
                        <div class="lui_custom_list_box_content_col_btn" style="text-align: right;width: 95%">
                            <ui:button styleClass="lui_custom_list_box_content_blue_btn" onclick="submit();"
                                       text="${lfn:message('sys-modeling-base:modeling.button.ok')}">
                            </ui:button>
                            <ui:button styleClass="lui_custom_list_box_content_whith_btn" onclick="cancle();"
                                       text="${ lfn:message('button.cancel') }">
                            </ui:button>
                        </div>
                    </center>
                </div>
            </center>
        </form>
        <script>
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
                $("[name='text']").val(_param.text);
                $("[name='value']").val(_param.value)
                clearInterval(interval);


            }



            seajs.use(["lui/jquery", "lui/dialog", 'lui/topic'], function ($, dialog, topic) {
                var _validation = $KMSSValidation();
                _validation.addValidator('urlValidation',"${lfn:message('sys-modeling-base:modeling.url.error')}",function(v, e, o){
                    var pattern = /^\/[a-zA-Z0-9\w-./?%&=]+/;
                    return pattern.test(v);
                });
                _validation.addValidator('max8',"${lfn:message('sys-modeling-base:modeling.max.8.characters')} ",function(v, e, o){
                    var len = 0;
                    for (var i = 0; i < v.length; i++) {
                        var length = v.charCodeAt(i);
                        if (length >= 0 && length <= 128) {
                            len += 1;
                        } else {
                            len += 3;
                        }
                    }
                    return len<=24;
                });


                window.submit = function () {
                    if (_validation.validate()) {
                        $dialog.hide({
                            type: 'success',
                            data: {
                                'text': $("[name='text']").val(),
                                'value': $("[name='value']").val()
                            }
                        });
                    }


                }
            });

            function cancle() {
                $dialog.hide({type: 'cancle'});
            }
        </script>
    </template:replace>
</template:include>