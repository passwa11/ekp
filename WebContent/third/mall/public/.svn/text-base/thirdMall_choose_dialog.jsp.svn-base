<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <script type="text/javascript">
            seajs.use(['theme!profile']);
            seajs.use(['theme!iconfont']);
            Com_IncludeFile("common.css", "${LUI_ContextPath}/third/mall/resource/css/", "css", true);
            var allRadioName=[];
            $(document).ready(function () {
                allRadioName=[];
                var applicationParams = $("input[name='applicationParams']", window.top.document);
                if (applicationParams && applicationParams.val()) {
                    var obj = JSON.parse(applicationParams.val());
                    if (obj && obj.length > 0) {
                        for (var i = 0; i < obj.length; i++) {
                            var row = obj[i];
                            if (row.choose) {
                                var applicationRow = "<div class=\"fy-newVersion-install-title\">"
                                    +(obj.length > 1 ?(i + 1)+'、':'')
                                    + "${lfn:message('third-mall:kmReuseCommon.install.type.tip1')}" + row.fdAppName
                                    + "${lfn:message('third-mall:kmReuseCommon.install.type.tip2')}" + row.fdVersionText
                                    + "${lfn:message('third-mall:kmReuseCommon.install.type.tip3')}"
                                    + "</div>"
                                    + "<div class=\"fy-newVersion-install-desc\">";
                                for (var key in row.choose) {
                                    applicationRow = applicationRow + "<div class=\"radioClass\"><label><input name=\"application_" + row.fdKey + "\" type=\"radio\" value=\"" + key + "\" />" + row.choose[key] + " </label></div>"
                                }
                                allRadioName.push("application_" + row.fdKey);
                                applicationRow = applicationRow + "</div>";

                                $("#newVersionDiv").append(applicationRow);
                            }
                        }
                    }
                }
            });
            //获取所有选中
            function getChoose(){
                var checkAlLValue=[];
                seajs.use( ['lui/dialog' ], function(dialog) {
                    //必填项验证
                    if (allRadioName && allRadioName.length > 0) {
                        for (var i = 0; i < allRadioName.length; i++) {
                            var checkRadio = $("input[name='"+allRadioName[i]+"']:checked").val()
                            if(checkRadio){
                                checkAlLValue.push(checkRadio);
                            }else{
                                dialog.alert('${lfn:message('third-mall:kmReuseCommon.install.type')}！');
                                return false;
                            }
                        }
                    }
                });
                return checkAlLValue;
            }
        </script>

    </template:replace>
    <template:replace name="content">
        <div id="newVersionDiv">

        </div>
    </template:replace>
</template:include>
