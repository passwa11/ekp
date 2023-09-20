<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/listview.css"/>
        <link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css"/>
        <link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/process.css"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/transport/css/transport.css?s_cache=${LUI_Cache }"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/appList.css"/>
    </template:replace>
    <template:replace name="content">
        <div class="model-step-content">
        <html:form styleId="uploadInitForm" action="sys/modeling/base/modelingApplication.do?method=activateApp"  method="post" enctype="multipart/form-data" >
            <div class="model-step-content-wrap">
                <div class="model-download">
                    <ul class="model-download-opt">
                        <li class="model-download-opt-item">
                            <div class="model-download-opt-num">1</div>
                            <div class="model-download-opt-detail">
                                <p>${lfn:message('sys-modeling-base:modelingLicense.first.download.activateInfo')}</p>
                                <a href="javascript:getActivateInfo();">${lfn:message('sys-modeling-base:modelingLicense.download.activateInfo')}</a>
                            </div>
                        </li>
                        <li class="model-download-opt-item">
                            <div class="model-download-opt-num">2</div>
                            <div class="model-download-opt-detail">
                                <p  >${lfn:message('sys-modeling-base:modelingLicense.upload.ctivation.code')}
                                    <span class="active_code_format">(只能上传txt格式文件)</span></p>
                                <div id="uploadBtn" style="display: none;">
                                    <input id="upload_file_temp" type="text" placeholder="请将填写好的Excel上传"
                                           readonly="readonly"/>
                                    <input id="upload_file" type="file"   name="file" accept=".txt" value="浏览"
                                           onchange="change();">
                                </div>
                                <p id="upload_file_msg" style="display: none"><span id="upload_file_name"></span></p>

                                <a id="upload_file_title" href="javascript:clickFile();">${lfn:message('sys-modeling-base:modelingLicense.upload.file')}</a>

                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
            </html:form>
        <%--底部--%>
        <div class="toolbar-bottom">
                <%--当前选择的--%>
            <div id="selectedVersion" class="lui_left_text"></div>
                <%--操作--%>
            <div class="lui_widget_btns">
                <kmss:auth requestURL="/sys/modeling/base/modelingApplication.do?method=activateApp">
                    <ui:button text="${ lfn:message('button.ok')}" onclick="activateApp()" order="2" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" onclick="$dialog.hide();" order="2" />
            </div>
        </div>
        <script>
            Com_IncludeFile("jquery.js");
        </script>
        <script>
            function clickFile() {
                $("#upload_file").trigger($.Event("click"))
            }
            seajs.use(["lui/dialog", "lui/topic"], function(dialog, topic) {
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
                    clearInterval(interval);
                }
                window.change = function(){
                    document.getElementById("upload_file_temp").value=document.getElementById("upload_file").value;
                     var filePath =  document.getElementById("upload_file").value;
                    if (filePath.indexOf('fakepath') !== -1)
                        filePath = filePath.substring(filePath.indexOf('fakepath\\') + 'fakepath\\'.length);
                    $("#upload_file_msg").show();
                    $("#upload_file_name").html(filePath)
                    $("#upload_file_name").attr("title",document.getElementById("upload_file").value);
                    $("#upload_file_title").html("${lfn:message('sys-modeling-base:modelingLicense.re-upload')}")
                    //upload();
                };
                window.getActivateInfo = function (){
                    var fdAppId = _param.fdAppId;
                        var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=getActivateInfo&fdId=" + fdAppId ;
                        var fileName = "testAjaxDownload.txt";
                        var form = $("<form></form>").attr("action", url).attr("method", "post");
                        form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", fileName));
                        form.appendTo('body').submit().remove();
                };
                //文件上传
                window.activateApp= function(){
                    var value =  document.getElementById("upload_file").value;
                    if (!value){
                        dialog.alert("${lfn:message('sys-modeling-base:modelingLicense.upload.activation.file.first')}");
                        return;
                    }
                    var fdAppId = _param.fdAppId;
                    document.forms[0].action = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=activateApp&fdId=" + fdAppId ;
                    $.ajax({
                        url: Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=activateApp&fdId=" + fdAppId,
                        type: 'POST',
                        async: false,
                        data: new FormData($('#uploadInitForm')[0]),
                        processData: false,
                        contentType: false,
                        dataType: 'json',
                        success: function (data) {
                               setTimeout(function(){
                                   if (data.status){
                                       $dialog.hide("true");
                                   }
                                   dialog.result(data);
                               },1000);
                        }
                    });

                }
            })
        </script>

    </template:replace>
</template:include>