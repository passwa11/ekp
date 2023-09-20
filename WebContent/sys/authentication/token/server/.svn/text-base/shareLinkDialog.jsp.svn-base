<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
    <template:replace name="title">分享设置</template:replace>
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/authentication/token/server/resource/css/shareLinkDialog.css?cache=${LUI_Cache}"/>

    </template:replace>
    <template:replace name="body">
        <kmss:authShow roles="ROLE_SYSTOKEN_SETTING">
            <div class="linkContent">
                <div class="link">
                    <div class="link-text">
                    </div>
                        <%--                <i class="copy-icon"></i>--%>
                        <%--                <i class="jump-icon"></i>--%>
                </div>
                <div class="buttonContent">
                    <div class="copyButton">
                        复制
                    </div>
                    <div class="jumpButton">
                        访问
                    </div>
                </div>
            </div>
        </kmss:authShow>
<script>
    seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
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
            init(_param);
            clearInterval(interval);
        }

        function init(param) {
            var token = param.token;
            var locationHref = window.location.href;
            var headPath = locationHref.substring(0,locationHref.indexOf(Com_Parameter.ContextPath));
            var linkUrl = headPath + Com_Parameter.ContextPath + "sys/anonymous/enter/token.do?method=visitToken&token="+token;
            $(".link-text").text(linkUrl);

            $(".copyButton").on('click', function () {
                var flag = copyText($(this),linkUrl); //传递文本
                dialog.alert(flag ? "复制成功！" : "复制失败！")
            })

            $(".jumpButton").on('click', function () {
                Com_OpenWindow( $(".link-text").text(), "_blank");
            })
        }


        function copyText($this, text) {
            var $input = $("<input style='position: absolute;' />");//创建input对象
            $this.after($input);//添加元素
            $input.val(text);
            $input.select();
            try {
                var flag = document.execCommand("copy");//执行复制
            } catch (eo) {
                var flag = false;
            }
            $input.remove()//删除元素
            return flag;
        }

    })

</script>
    </template:replace>
</template:include>