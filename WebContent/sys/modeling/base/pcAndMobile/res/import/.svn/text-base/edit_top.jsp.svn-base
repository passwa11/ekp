<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/pcAndMobile.css?s_cache=${LUI_Cache}"/>
<div class="modeling-pam-top">
    <div class="modeling-pam-nav">
        <div onclick="returnListPagePam()">
            <i></i>
            <p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back') }</p>
        </div>
    </div>
    <div class="modeling-pam-top-left">
        <ul>
            <li class="active" onclick="changeContentView('pc')">${lfn:message('sys-modeling-base:sysform.PC') }</li>
            <li onclick="changeContentView('m')">${lfn:message('sys-modeling-base:sysform.mobile') }</li>
        </ul>
    </div>
    <div class="modeling-pam-top-right">
        <ul>
            <li onclick="doSubmit()" class="active">${lfn:message('sys-modeling-base:modeling.save') }</li>
            <li onclick="changeContentBase()"> ${lfn:message('sys-modeling-base:modeling.app.baseinfo') }</li>
        </ul>
    </div>
</div>
<script>
    var topType = "${param.topType}";
    var action = topType === "list"?"pcAndMobileListView.do":"pcAndMobileView.do";
    _topParam={
        returnUrl:Com_Parameter.ContextPath + "sys/modeling/base/pcAndMobile/"+topType+"/index_body.jsp?fdModelId=${param.modelMainId}&method=${param.method}",
        dialogParams_list:{
            authReaderIds: "${param.authReaderIds}",
            authReaders: [],
            fdName: "${param.fdName}",
            fdIsDefault: "${param.fdIsDefault}"
        },
        dialogParams_view:{
            noReaders:true,
            authReaders: [],
            fdName: "${param.fdName}",
            fdIsDefault: "${param.fdIsDefault}"
        },
        dialog_title:"${ lfn:message('sys-modeling-base:listview.basic.informationedit') }",
        dialog_height:topType === "list"?400:270,
        ajaxUpdateBase:Com_Parameter.ContextPath + "sys/modeling/base/"+action+"?method=ajaxUpdateBase",
        editUrl:Com_Parameter.ContextPath + "sys/modeling/base/"+action+"?method=edit&fdId=${param.fdId}"
    };
    function returnListPagePam() {
        var url = _topParam.returnUrl.replace(/\s+/g, "");
        var iframe = window.parent.document.getElementById("trigger_iframe");
        $(iframe).attr("src", url);
    }

    function changeContentView(type) {
        $(".modeling-pam-top-left li").removeClass("active");
        $(event.target).addClass("active");
        var context;
        if (type === "pc") {
            context = $("#modeling-pam-content-pc");
            context.show();
            $("#modeling-pam-content-mobile").hide();
        } else {
            $("#modeling-pam-content-pc").hide();
            context = $("#modeling-pam-content-mobile")
            context.show();
            context = $("#modeling-pam-content-mobile")[0].contentWindow.document;
            var buildInWidth = $(".model-businessTag-content", context).closest("tr").width();
            $(".model-businessTag-content", context).width(buildInWidth);
        }
    }

    function seriFormToJson(form) {
        var seriArray = form.serializeArray();
        var jsonSeri = {};
        $.each(seriArray, function () {
            if (jsonSeri[this.name]) {
                if (!jsonSeri[this.name].push) {
                    jsonSeri[this.name] = [jsonSeri[this.name]];
                }
                jsonSeri[this.name].push(this.value || '');
            } else {
                jsonSeri[this.name] = this.value || '';
            }
        });
        return jsonSeri;
    }
    //
    seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {
        window.changeContentBase = function () {
            var url = '/sys/modeling/base/pcAndMobile/res/import/create_dialog.jsp';
            var dialogParams = _topParam["dialogParams_"+topType];
            if (topType === "list" && dialogParams.authReaders.length == 0){
                var authReaderIds = "${param.authReaderIds}";
                var authReaderNames = "${param.authReaderNames}";
                if (authReaderIds && authReaderNames) {
                    var idsArr = authReaderIds.split(";");
                    var namesArr = authReaderNames.split(";");
                    if (idsArr.length > 0) {
                        for (var i = 0; i < idsArr.length; i++) {
                            if (namesArr.length > i) {
                                var auth = {
                                    id: idsArr[i],
                                    name: namesArr[i]
                                }
                                dialogParams.authReaders.push(auth);
                            }
                        }
                    }
                }
            }

            dialog.iframe(url, _topParam.dialog_title, function (data) {
                if (data == null)
                    return;
                //回调
                $.ajax({
                    url: _topParam.ajaxUpdateBase,
                    dataType: 'json',
                    type: 'post',
                    data: {
                        authReaders: data.authReaders,
                        fdId: "${param.fdId}",
                        fdName: data.fdName,
                        fdIsDefault: data.fdIsDefault
                    },
                    async: false,
                    success: function (result) {
                        if (result.status === '200') {
                            dialog.success('<bean:message key="return.optSuccess"/>');
                            var url = _topParam.editUrl;
                            var iframe = window.parent.document.getElementById("trigger_iframe");
                            $(iframe).attr("src", url);

                        } else {
                            dialog.failure(result.error);
                        }
                    }
                });
            }, {width: 540, height: _topParam.dialog_height, params: dialogParams});
        };
    });
</script>
