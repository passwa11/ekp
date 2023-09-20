<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService" %>
<%@ page import="com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil" %>
<%
    ISysFileLocationDirectService directService =
            SysFileLocationUtil.getDirectService();
    request.setAttribute("methodName", directService.getMethodName());
    request.setAttribute("uploadUrl", directService.getUploadUrl(request.getHeader("User-Agent")));
    request.setAttribute("isSupportDirect", directService.isSupportDirect(request.getHeader("User-Agent")));
    request.setAttribute("fileVal", directService.getFileVal());
%>
<template:include ref="default.dialog">
    <template:replace name="head">
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/sys/modeling/base/material/source/cropper/cropper.css"/>
        <link rel="stylesheet" type="text/css"
              href="${LUI_ContextPath}/sys/modeling/base/material/source/material_main.css"/>
        <script type="text/javascript">
            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("data.js|dialog.js|jquery.js");
            Com_IncludeFile("swf_attachment.js", "${LUI_ContextPath}/sys/attachment/js/", "js", true);
            Com_IncludeFile("cropper_material.js", "${LUI_ContextPath}/sys/modeling/base/material/source/", "js", true);
            Com_IncludeFile("cropper.js", "${LUI_ContextPath}/sys/modeling/base/material/source/cropper/", "js", true);
            Com_IncludeFile("material_main.js", "${LUI_ContextPath}/sys/modeling/base/material/source/", 'js',
                true);
        </script>
        <script type="text/javascript">
            Com_IncludeFile("data.js|dialog.js|jquery.js");
        </script>

    </template:replace>
    <template:replace name="content">
        <div class="lui_material_edit_container">
            <div class="lui_material_edit_left">
                <div class="lui_material_edit_l_img">
                    <img id="lui_material_crop"
                         src="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=downloadPic&fdId=${main.fdAttId }&open=1"
                         alt="" >
                </div>
                <div class="lui_material_edit_l_option">

                    <span class="lui_text_primary lui_material_btn_plus" onclick="resizeImg(true)">+</span>
                    <input id="imgSize" type="text" value="100%" onchange="setZoom()">
                    <span class="lui_text_primary lui_material_btn_minus" onclick="resizeImg(false)">-</span>
                    <span class="lui_text_primary" onclick="reset()">${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.originSize')}</span>
                    <span class="lui_material_mark_split "></span>
                    <span class="lui_text_primary" onclick="imgRotate(true)">${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.turnLeft')}</span>
                    <span class="lui_text_primary" onclick="imgRotate(false)">${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.turnRight')}</span>
                    <span class="lui_material_mark_split "></span>
                    <span>${ lfn:message('sys-modeling-base:modelingMaterialMain.clippingScale')}</span>
                    <select name="ratio" onchange="setRatio()">
                        <option value=1>1:1</option>
                        <option value=3>3:1</option>
                        <option value=0.75>3:4</option>
                        <option value=1.3334 selected="selected">4:3</option>
                        <option value=0.5625>9:16</option>
                        <option value=1.7778>16:9</option>
                    </select>
                </div>

            </div>
            <div class="lui_material_edit_right">
                <div class="lui_material_edit_r_img ">
                    <div class="lui_material_edit_r_review">
                    </div>
                </div>
                <div class="lui_material_edit_r_table">
                    <table>
                        <tbody>
                        <tr>
                            <td>${ lfn:message('sys-modeling-base:modelingMaterialMain.fdName')}</td>
                            <td>&emsp;<input type="text" name="fdName" value="${lfn:escapeHtml(main.fdName) }"
                                             onchange="validXss(event)"></td>
                        </tr>
                        <tr>
                            <td>${ lfn:message('sys-modeling-base:modelingMaterialMain.fdSize')}</td>
                            <td>&emsp;<span name="size">${main.fdSize }</span></td>
                        </tr>
                        <tr>
                            <td>${ lfn:message('sys-modeling-base:modelingMaterialMain.ppi')}</td>
                            <td>&emsp;
                                <span name="width">${main.fdWidth }</span>X<span name="length">${main.fdLength }</span>
                            </td>
                        </tr>
                        <tr>
                            <td style="dsiplay:none">
                                <input id="att_id" type="hidden" value="">
                                <span id="att_btn"></span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="material_dlg_btn_bar">
            <span class="material_dlg_btn gary" onclick="Com_CloseWindow();">${lfn:message('button.cancel') }</span>
            <span class="material_dlg_btn" onclick="onSureCut();">${lfn:message('button.ok') }</span>
        </div>

    </template:replace>

</template:include>
<script>
    /****
     * init
     * *****/
    var attachmentConfig = {
        // 上传路径
        uploadurl: '${uploadUrl}',
        // 上传方法名
        methodName: '${methodName}',
        // 是否支持直连模式
        isSupportDirect: ${isSupportDirect},
        // 文件key
        fileVal: '${fileVal}' || null,
        //注册before-send-file事件
        beforeSendFile: true
    }
    var att1 = new cropper_Att_Material("att_btn", "", "");
    att1.uploadObj = WebUploader.create(att1.uploadConfig);
    att1.initUploadEvent();
    var $image = $('#lui_material_crop');
    var percent = 1;
    $image.cropper({
        aspectRatio: 1.3,
        preview: ".lui_material_edit_r_review",
        crop: function (event) {
            $("span[name='size']").html(parseInt(event.detail.width));
            $("span[name='width']").html(event.detail.width.toFixed(2))
            $("span[name='length']").html(event.detail.height.toFixed(2))
        }
    });
    var cropper = $image.data('cropper');

    /****
     * 图片编辑功能
     * *****/
    //转向
    function imgRotate(type) {
        $image.cropper("rotate", type ? 45 : -45);
    }

    //大小-加减
    function resizeImg(type) {
        $image.cropper("zoom", type ? 0.1 : -0.1);
        percent = type ? percent + 0.1 : percent - 0.1;
        var p = percent * 100;
        p = parseInt(p);
        $("#imgSize").val(p + "%")
    }

    //重置
    function reset() {
        $image.cropper("reset");
        $("#imgSize").val("100%")
    }

    //大小-输入
    function setZoom() {
        var v = $("#imgSize").val().replace('%', '');
        var p = parseInt(v);
        cropper.zoomTo(p / 100)
    }

    //比例
    function setRatio() {
        var val = $("select[name='ratio']").val();
        $image.cropper("setAspectRatio", parseFloat(val));
        $(".lui_material_edit_r_review").attr("id", "lui_material_ratio_" + val.replace('\.', '_'));
    }

    var doUp = false;

    function onSureCut() {
        //校验
        var fdName = $("input[name='fdName']").val();
        if (validStrXss(fdName)) {
            alert("名称中含有非法字符");
            return false;
        }

        if ($image.attr("src") == null) {
            doUp = false;
            return false;
        } else {
            if (doUp) {
                return;
            }
            var cas = $image.cropper('getCroppedCanvas'); // 获取被裁剪后的canvas
            var base64 = cas.toDataURL('image/jpeg'); // 转换为base64
            doUp = true;
            att1.uploadObj.addFiles(dataURLtoFile(base64, "hermite"))
            var num = 0;
            var doEditSaveInterval = setInterval(function () {
                if (att1.finished) {
                    console.log("doEditSave上传..")
                    var issave = doEditSave('${main.fdId}', att1.getFinishedId());
                    clearInterval(doEditSaveInterval);
                    if (issave == false) {
                        return;
                    }

                } else {
                    console.log("等待上传.." + num)
                }
                num++;
            }, 100);

        }
    }


    function dataURLtoFile(dataurl, filename) {
        var arr = dataurl.split(',');
        var mime = arr[0].match(/:(.*?);/)[1];
        var bstr = atob(arr[1]);
        var n = bstr.length;
        var u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        //转换成file对象
        return new File([u8arr], filename, {
            type: mime
        });
        //转换成成blob对象
        //return new Blob([u8arr],{type:mime});
    }
</script>