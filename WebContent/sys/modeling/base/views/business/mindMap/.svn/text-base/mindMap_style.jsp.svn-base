<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@ include file="/sys/ui/jsp/common.jsp" %>
    <%@ include file="/sys/ui/jsp/jshead.jsp" %>
    <link type="text/css" rel="stylesheet"
          href="${LUI_ContextPath}/sys/modeling/base/views/business/res/mindMap.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/showFilters.css"/>
</head>
<body>
<div class="mindMap_style_box">
    <ul>
        <li class="mindMap_style_item mindMap_style_shadow " value="0"
            onclick="changeStyle(this);">
            <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-steady@2x.png">
            <span>${lfn:message('sys-modeling-base:modelingMindMap.root.node')}</span>
        </li>
        <li class="mindMap_style_item mindMap_style_shadow " value="1"
            onclick="changeStyle(this);">
            <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-simple@2x.png">
            <span>${lfn:message('sys-modeling-base:modelingMindMap.simplicity')}</span>
        </li>
        <li class="mindMap_style_item mindMap_style_shadow " value="2"
            onclick="changeStyle(this);">
            <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-Colorful@2x.png">
            <span>${lfn:message('sys-modeling-base:modelingMindMap.colorful')}</span>
        </li>
        <li class="mindMap_style_item mindMap_style_shadow " value="3"
            onclick="changeStyle(this);">
            <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-blueSky.png">
            <span>${lfn:message('sys-modeling-base:modelingMindMap.blue.sky')}</span>
        </li>
        <li class="mindMap_style_item mindMap_style_shadow " value="4"
            onclick="changeStyle(this);">
            <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-sea.png">
            <span>${lfn:message('sys-modeling-base:modelingMindMap.deep.sea')}</span>
        </li>
        <li class="mindMap_style_item mindMap_style_shadow " value="5"
            onclick="changeStyle(this);">
            <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-shadow@2x.png">
            <span>${lfn:message('sys-modeling-base:modelingMindMap.shadow')}</span>
        </li>
    </ul>
</div>
<div class="lui_custom_list_box_content_col_btn">
    <a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="ok()">${lfn:message('sys-modeling-base:modeling.button.ok')}</a>
    <a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="cancle()">${lfn:message('sys-modeling-base:modeling.Cancel')}</a>
</div>
<script type="text/javascript">

    function changeStyle(obj) {
        $(obj).siblings().removeClass("active");
        if ($(obj).hasClass("active")) {
            return;
        }
        $(obj).addClass("active");
    };


        seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {
            var interval = setInterval(beginInit, "10");
            function init(){
                var params = $dialog.___params || {};
               $("li[value='"+params+"']").addClass("active");
            }

            window.ok=function (){
            var cfg = {};
            cfg["defaultSkin"] = $(".active").val();
            cfg["src"] = $(".active img").attr("src");
            if (typeof (cfg) != "undefind") {
                $dialog.hide(cfg);
            }
            }


            function beginInit() {
                if (!window['$dialog'])
                    return;
                clearInterval(interval);
                init();
            }

        });




    function cancle() {
        $dialog.hide();
    }

</script>
</body>

</html>
