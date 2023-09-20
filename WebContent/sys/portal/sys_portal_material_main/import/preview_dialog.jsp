<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog">
	<template:replace name="content">
  	<link rel="stylesheet" type="text/css"
            href="${LUI_ContextPath}/sys/portal/sys_portal_material_main/source/material_main.css" />
     <div id="lui_material_preview_box" style="width:100%" align="center">
   
        <img  src="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${param.imgUrl}&open=1" alt="">
        
        <div class="lui_material_preview_option_box">
            <div class="lui_material_preview_option">
                <span class="lui_text_primary lui_material_btn_plus" onclick="resizeImg('plus')">+</span>
                <input id="imgSize" type="text" value="100%" onchange="resizeImg('input')">
                <span class="lui_text_primary lui_material_btn_minus" onclick="resizeImg('minus')">-</span>
                <span class="lui_text_primary" onclick="resizeImg('origin')">${ lfn:message('sys-portal:sysPortalMaterialMain.btn.originSize')}</span>
                 <span class="lui_text_primary" onclick="imgRotate('left')">${ lfn:message('sys-portal:sysPortalMaterialMain.btn.turnLeft')}</span>
                  <span class="lui_material_mark_split "></span>
                <span class="lui_text_primary" onclick="imgRotate('right')">${ lfn:message('sys-portal:sysPortalMaterialMain.btn.turnRight')}</span>
            </div>
        </div>
    </div>
    
<script>
 var oriPercent = 100;
    var percent = 100;
    var picRealWidth, picRealHeight;
    window.onload = function () {
        var _image = $("#lui_material_preview_box").find('img')[0];
        if (typeof _image.naturalWidth == "undefined") {
            var i = new Image();
            i.src = _image.src;
            picRealWidth = i.width;
            picRealHeight = i.height;
        }
        else {
            picRealWidth = _image.naturalWidth;
            picRealHeight = _image.naturalHeight;
        }
        var boxWidth = $("#lui_material_preview_box").width();
        oriPercent = picRealWidth / boxWidth * 100;
        oriPercent = parseInt(oriPercent)
        setImgCss(oriPercent);
    }

    function resizeImg(type) {
        if (type == "origin") {
            setImgCss(oriPercent);
            return;
        }
        var pstr = $("#imgSize").val().replace('%', '');
        percent = parseInt(pstr);
        var val = percent;
        if (type == "plus") {
            val += 10;
        } else if (type == "minus") {
            val -= 10;
        }
        if (val < 30) {
            val = 30;
        } else if (val > 300) {
            val = 300;
        }
        setImgCss(val);

    }

    function setImgCss(p) {
        var width = p + "%"
        $("#imgSize").val(width);
        $("#lui_material_preview_box img").css("width", width)
        var martop = document.documentElement.clientHeight - $("img").height();
        if (martop > 0) {
            martop = martop / 2;
            $("img").css("margin-top", martop + "px")
        }
    }
    var rotate = 0;
    function imgRotate(type) {
        if (type == "left") {
            rotate += 90;
        } else {
            rotate -= 90;
        }
        if (rotate < 0 || rotate > 360) {
            rotate = 0
        }
        $("#lui_material_preview_box img").css("transform", "rotate(" + rotate + "deg)");
    }
</script>
</template:replace>
</template:include>