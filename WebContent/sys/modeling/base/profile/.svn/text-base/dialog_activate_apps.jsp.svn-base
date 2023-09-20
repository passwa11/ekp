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
        <html:form styleId="uploadInitForm" action="sys/modeling/base/modelingApplication.do?method=activateApps"  method="post" enctype="multipart/form-data" >
            <div class="model-step-content-wrap">
                <div class="model-download">
                    <ul class="model-download-opt">
                        <li class="model-download-opt-item1">
                            <div class="model-download-opt-detail">
                                <p>${lfn:message('sys-modeling-base:modelingLicense.upload.activation.code.file')}
                                    <span class="active_code_format">(只能上传txt格式文件)</span></p>
                                <a id="upload_file_title" href="javascript:clickFile();">${lfn:message('sys-modeling-base:modeling.select.file')}</a>
                                <div id="uploadBtn" style="display: none;">
                                    <input id="upload_file_temp" type="text" placeholder="请将填写好的Excel上传"
                                           readonly="readonly"/>
                                    <input id="upload_file" type="file" multiple  name="file" accept=".txt" value="浏览"
                                           onchange="change();">
                                </div>
                                <ul id="upload_file_msg" style="display: none">
                                    <li></li>
                                </ul>


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
                <kmss:auth requestURL="/sys/modeling/base/modelingApplication.do?method=activateApps">
                    <ui:button text="${ lfn:message('button.ok')}" onclick="activateApp()" order="2" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" onclick="closeDialog()" order="2" />
            </div>
        </div>
        <script>
            Com_IncludeFile("jquery.js");
        </script>
        <script>
            var files = [];
            var isUpdate = false;
            function clickFile() {
                $("#upload_file").trigger($.Event("click"))
            }
            seajs.use(["lui/dialog", "lui/topic"], function(dialog, topic) {

                window.change = function(){
                    files =document.getElementById("upload_file").files;
                   /* for (var i = 0; i<newfiles.length;i++){
                        var file = newfiles[i];
                        console.log("file",file);
                        files.push(file);
                    }
                    console.log("newfiles",newfiles);
                    console.log("files",files);*/
                    $("#upload_file_msg").show();
                    $("#upload_file_title").html("${lfn:message('sys-modeling-base:modeling.re-upload')}");
                    displayFilesName();
                    //upload();
                };
                window.displayFilesName = function (){
                    $("#upload_file_msg").empty();
                    for (var i = 0; i<files.length;i++){
                        var file = files[i].name;
                        var html = $("<li><span class='upload_file_name' title='"+file+"'>"+file+"</span><p class='activateFlag'></p></li>");
                       /* html.find(".deleteFile").on("click",function (){
                            var file1 =  $(this).prev().html();
                            files = files.filter(function(item) {
                                return item.name != file1;
                            });
                            console.log("ddfile",files);
                            $(this).parent("li").empty();

                        })*/
                        $("#upload_file_msg").append(html);
                    }
                }

                //文件上传
                window.activateApp= function(){
                    var value =  document.getElementById("upload_file").value;
                    if (!value){
                        dialog.alert("${lfn:message('sys-modeling-base:modelingLicense.upload.activation.file.first')}");
                        return;
                    }
                    document.forms[0].action = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=activateApps" ;
                    $.ajax({
                        url: Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=activateApps",
                        type: 'POST',
                        async: false,
                        data: new FormData($('#uploadInitForm')[0]),
                        processData: false,
                        contentType: false,
                        dataType: 'json',
                        success: function (res) {
                            console.log("result",res);
                            setTimeout(function(){
                                getActivateStatus(res);
                            },200);
                        }
                    });

                }
                window.getActivateStatus = function (datas){
                 $(".upload_file_name").each(function (){
                     var name = $(this).html();
                     for(var i=0;i<datas.length;i++){
                         var data = datas[i];
                         console.log("data.name",data.name);
                         if (name === data.name){
                             if (data.status){
                             isUpdate = true;
                             $(this).next().html("<span class='model-suc-img'></span>  ${lfn:message('sys-modeling-base:modelingLicense.success')}");
                             $(this).next().css("color", "#2CCC74");
                             return;
                             }
                         }
                     }
                     $(this).next().html("<span class='model-fail-img'></span>  ${lfn:message('sys-modeling-base:modelingLicense.fail')}");
                     $(this).next().css("color", "#F25643");

                   })
                }
                window.closeDialog = function (){
                    $dialog.hide(isUpdate);
                }
            })
        </script>

    </template:replace>
</template:include>