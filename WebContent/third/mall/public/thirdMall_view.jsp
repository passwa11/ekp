<%@ page import="com.landray.kmss.third.mall.util.MallUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="./../ThirdMallConfig_common.jsp" %>
<%
    request.setAttribute("mallDomMain", MallUtil.MALL_DOMMAIN);
%>

<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <script type="text/javascript">
            seajs.use(['theme!profile']);
            seajs.use(['theme!iconfont']);
            Com_IncludeFile("common.css", "${LUI_ContextPath}/third/mall/resource/css/", "css", true);

            function goback() {
                window.parent.location.reload();
            }
            //前往体验
            function tryUse(fdId){
                var url = '${LUI_ContextPath}/third/mall/thirdMallPublic.do?method=tryUse&fdKeyType=${fdKeyType}&fdId='+fdId;
                Com_OpenWindow(url);
            }
            //安装使用
            function useMallInfo(fdId, attId, vId, strategys) {
                let useLoding = null;
                seajs.use(['lui/dialog'], function (dialog) {
                    useLoding = dialog.loading();
                    var param = {
                        fdCurrentVersion: vId,
                        fdAttMainId: attId,
                        fdId: fdId,
                        fdKeyType: '${fdKeyType}',
                        strategys: strategys
                    };
                    $("#processInfo").show();
                    $("#downLoad").hide();
                    $.ajax({
                        type: "post",
                        url: '${LUI_ContextPath}/third/mall/thirdMallPublic.do?method=useMallAjax',
                        data: param,
                        async: true,
                        dataType: 'json',
                        success: function (data) {
                            useLoding.hide();
                            if (data) {
                                if (data.status == 'noDownRight') {
                                    $("#processInfo").hide();
                                    $("#downLoad").show();
                                    dialog.alert('${lfn:message("third-mall:kmReuseCommon.noDownRight")}');
                                }else   if (data.status == 'noRight') {
                                    $("#processInfo").hide();
                                    $("#downLoad").show();
                                    dialog.confirm('${lfn:message("third-mall:thirdMall.tip.sysApplication.name.noOpen")}', function (flag, d) {
                                        if (flag) {
                                            Com_OpenWindow('<c:url value="/sys/profile/index.jsp" />' + '#integrate/saas/mall');
                                        }
                                    }, null, [{
                                        name: "${lfn:message('third-mall:kmReuseCommon.goopen')}",
                                        value: true,
                                        focus: true,
                                        fn: function (value, dialog) {
                                            dialog.hide(value);
                                        }
                                    }, {
                                        name: "${lfn:message('third-mall:kmReuseCommon.cancel')}",
                                        value: false,
                                        styleClass: 'lui_toolbar_btn_gray',
                                        fn: function (value, dialog) {
                                            dialog.hide(value);
                                        }
                                    }]);
                                    return;
                                } else if (data.status == 'success') {
                                    $("#processInfo").html(data.msg);
                                    getProcessInfo();
                                } else if (data.status == 'error') {
                                    $("#processInfo").hide();
                                    $("#downLoad").show();
                                    dialog.alert(data.msg);
                                } else if (data.status == 'choose') {
                                    //已安装，弹出重复安装
                                    $("#processInfo").hide();
                                    $("#downLoad").show();
                                    /**选择重新安装*/
                                    var x = document.createElement("input");
                                    x.setAttribute("type", "hidden");
                                    x.setAttribute("name", "applicationParams");
                                    x.setAttribute("value", JSON.stringify(data.checkSrategy));
                                    window.top.document.body.appendChild(x);
                                    var url = "/third/mall/public/thirdMall_choose_dialog.jsp";
                                    dialog.iframe(url, '${lfn:message("third-mall:kmReuseCommon.tip")}', function (rt) {
                                        window.top.document.body.removeChild(x)
                                        if (rt && rt.result === "true") {
                                            // console.info('成功');
                                        }
                                    }, {
                                        width: 500, height: 260, buttons: [
                                            {
                                                name: "${lfn:message('button.ok')}", value: true, focus: true,
                                                fn: function (value, _dialog) {
                                                    var getChoose = _dialog.element.find("iframe").get(0).contentWindow.getChoose();
                                                    if (getChoose && getChoose.length > 0) {
                                                        useMallInfo(fdId, attId, vId, getChoose.join(";"));
                                                        _dialog.hide(value);
                                                    }
                                                }
                                            }, {
                                                name: "${lfn:message('button.cancel')}",
                                                value: false,
                                                styleClass: 'lui_toolbar_btn_gray',
                                                fn: function (value, dialog) {
                                                    dialog.hide(value);
                                                    window.top.document.body.removeChild(x)
                                                }
                                            }
                                        ]
                                    });
                                } else {
                                    $("#processInfo").hide();
                                    $("#downLoad").show();
                                    dialog.alert('${lfn:message("third-mall:kmReuseCommon.use.error")}');
                                }
                            }
                        }
                    });
                });
            }

            var installProcessTime = null;

            function getProcessInfo() {
                //每秒更新一次进度
                installProcessTime = window.setTimeout(function () {
                    $.ajax({
                        type: "post",
                        url: '${LUI_ContextPath}/third/mall/thirdMallPublic.do?method=processRuntime',
                        async: true,
                        data: {fdKeyType: '${fdKeyType}'},
                        dataType: 'json',
                        success: function (data) {

                            var fdId=null;
                            if(data.appMap){
                                var keys =Object.keys(data.appMap);
                                if(keys && keys.length > 0){
                                    fdId = keys[0];
                                }
                            }
                            /*/!**
                             <0 完成状态：-3|已终止，-2|失败，-1|已完成
                             =0 准备状态，
                             >0 进行中：1|开始中（在校验包），2|导入中，3|中止中
                             *!/*/
                            if (data.status < 0) {
                                seajs.use(['lui/dialog'], function (dialog) {
                                    if (data.failureCount == 0 && data.successCount > 0) {
                                        useSuccess(dialog, '${lfn:message('third-mall:kmReuseCommon.use.success.tip1')}',fdId);
                                    } else if (data.ignoreCount == data.failureCount && data.failureCount > 0) {
                                        dialog.alert('${lfn:message('third-mall:kmReuseCommon.use.error')}');
                                    } else {
                                        var msg = '${lfn:message('third-mall:kmReuseCommon.use.success.tip1')}'
                                            + '${lfn:message('third-mall:kmReuseCommon.use.success.tip4')}' + data.ignoreCount
                                            + '${lfn:message('third-mall:kmReuseCommon.use.success.tip7')}'
                                            + '${lfn:message('third-mall:kmReuseCommon.use.success.tip5')}' + data.successCount
                                            + '${lfn:message('third-mall:kmReuseCommon.use.success.tip7')}'
                                            + '${lfn:message('third-mall:kmReuseCommon.use.success.tip6')}' + data.failureCount
                                            + '${lfn:message('third-mall:kmReuseCommon.use.success.tip7')}';
                                        useSuccess(dialog, msg,fdId);
                                    }
                                });
                                clearTimeout(installProcessTime);
                                $("#processInfo").hide();
                                $("#downLoad").show();
                            } else {
                                $("#processInfo").html('${lfn:message('third-mall:kmReuseCommon.use.process')}：' + data.process + '%');
                                getProcessInfo();
                            }
                        }
                    });
                }, 1000);
            }

            /**
             * 插入成功以后的提示语
             * @param dialog
             * @param msg
             */
            function useSuccess(dialog, msg,fdId) {
                dialog.confirm(msg, function (flag, d) {
                    if (flag) {
                        var url ="${LUI_ContextPath}/sys/profile/index.jsp#modeling/application";
                        if(fdId){
                            url ="${LUI_ContextPath}/sys/modeling/base/modelingApplication.do?method=appIndex&fdId="+fdId+"&s_css=default";
                        }
                        Com_OpenWindow(url);
                    }
                }, null, [{
                    name: "${lfn:message('third-mall:kmReuseCommon.use.success.go.application')}",
                    value: true,
                    focus: true,
                    fn: function (value, dialog) {
                        dialog.hide(value)
                    }
                }, {
                    name: "${lfn:message('third-mall:kmReuseCommon.cancel')}",
                    value: false,
                    styleClass: 'lui_toolbar_btn_gray',
                    fn: function (value, dialog) {
                        dialog.hide(value);
                    }
                }]);
            }

            $(document).ready(function () {
                setTimeout(function(){
                    var iframe= window.parent.document.getElementById("main_show_iframe").children[0];
                    iframe.style.height=($(".fy-process-details")[0].scrollHeight+80)+'px';
                },200)

            });
            //

        </script>

    </template:replace>
    <template:replace name="content">
        <div class="lui_mall_content lui_mall_content_app">
        <div class="lui_mall_content_main aside_main" >
        <!-- 购买面板 start -->
        <div class="lui_mall_app_detail_head">
            <span class="lui_mall_app_detail_back">
                <i></i>
                <a onclick="goback()" href="javascript:void(0)"> ${lfn:message('third-mall:kmReuseCommon.backList')}   </a>
            </span>
                <span class="lui_mall_app_detail_head_title">
                 <bean:message bundle="third-mall" key="thirdMall.tab.${fdKeyType}.desc"/>
            </span>
        </div>
        <div class="fy-purchase-main-container fy-process-details">
        <div class="fy-purchase-main-center">
            <c:choose>
                <c:when test="${ dataInfo.fdUse eq 'true' }">
                    <div class="fd-insert-over-tip">
                            ${lfn:message('third-mall:kmReuseCommon.status.over')}
                    </div>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
            <div class="fy-purchase-main-pic-wrap">
                <div class="imgCenter">
                    <c:choose>
                        <c:when test="${null != dataInfo.pic && '' !=  dataInfo.pic}">
                            <img src="${mallDomMain}/km/reuse/mobile/kmReusePublicMobileAction.do?method=downloadPic&fdId=${ dataInfo.pic}"
                            />
                        </c:when>
                        <c:otherwise>
                            <img src="./resource/images/application.png"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:choose>
            <c:when test="${dataInfo.fdPrice == null || dataInfo.fdPrice == 0}">
            <div class="fy-purchase-main-detail purchased">
                </c:when>
                <c:otherwise>
                <div class="fy-purchase-main-detail">
                    </c:otherwise>
                    </c:choose>
                    <h3 class="fy-purchase-title"><c:out value="${dataInfo.docSubject}"/></h3>
                    <p class="fy-purchase-tip">
                        <span>${lfn:message('third-mall:kmReuseCommon.read')}：<c:out
                                value="${dataInfo.fdReadCount}"/></span>
                        <span>${lfn:message('third-mall:kmReuseCommon.download')}：<c:out
                                value="${dataInfo.xzCount}"/></span>
                        <span>${lfn:message('third-mall:kmReuseCommon.focus')}：<c:out
                                value="${dataInfo.gzCount}"/></span>
                    </p>

                    <div class="fy-purchase-price">
                        <c:choose>
                            <c:when test="${(dataInfo.fdPrice == null ? 0 : dataInfo.fdPrice) eq 0}">
                                <span style="font-size:18px;font-weight: bold;color:#f6aa0b;margin-right: 4px;">${lfn:message('third-mall:kmReuseCommon.free')}</span>
                            </c:when>
                            <c:otherwise>
                                <span style="font-size:18px;font-weight: bold;color:#DF2D2D;margin-right: 4px;">¥${dataInfo.fdPrice}</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <!-- 当前是应用，应用是否开启了 授权-->
                    <div class="fy-purchase-btn-wrap" id="downLoad">
                        <button class="download-btn"
                                onclick="useMallInfo('${dataInfo.fdId}','${dataInfo.attMainId}','${dataInfo.fdCurrentVersion}')">${lfn:message('third-mall:kmReuseCommon.use.info')}</button>
                    </div>
                    <div class="fy-purchase-btn-wrap" id="processInfo" style="display: none">
                            ${lfn:message('third-mall:kmReuseCommon.use.ing')}
                    </div>
                    <c:if test="${not empty dataInfo.fdExpUsername && not empty dataInfo.fdProcessPath}">
                    <!-- 体验应用 -->
                    <div class="fy-purchase-btn-wrap" id="tryUse">
                        <button class="download-btn" onclick="tryUse('${dataInfo.fdId}')">${lfn:message('third-mall:kmReuseCommon.use.try')}</button>
                    </div>
                    </c:if>

                </div>
            </div>
            <!-- 购买面板 end -->

            <div class="fy-purchase-msg-container">
                <div class="fy-purchase-msg-center">
                    <!-- 页签面板 -->
                    <div class="fy-purchase-msg-left">
                        <ul class="fy-purchase-tab-list" id="fyPurchaseTabs">
                            <li class="fy-purchase-tab-item active" id="xqjs1">
                                <span>${lfn:message('third-mall:kmReuseCommon.introduce')}</span>
                            </li>
                        </ul>
                        <div class="fy-purchase-panel-list">
                            <!-- 详情介绍 start -->
                            <div class="fy-purchase-tab-panel active" id="xqjs2">
                                <!-- 面板 Starts -->
                                <div class="fy-detail-panel">
                                    <div class="fy-detail-panel-heading">
                                        <h4 class="fy-detail-panel-heading-title">${lfn:message('third-mall:kmReuseCommon.basicInformation')}</h4>
                                        <!-- <a class="heading-btn" href="javascript:void(0)">更多参数</a> -->
                                    </div>
                                    <div class="fy-detail-panel-body">
                                        <ul class="fy-info-tip-list">
                                            <li>
                                                <span class="title">${lfn:message('third-mall:kmReuseCommon.fdNo')}：</span>
                                                <span class="txt"><c:out value="${dataInfo.fdNo }"/></span>
                                            </li>
                                            <li>
                                                <span class="title">${lfn:message('third-mall:kmReuseCommon.applicationName')}：</span>
                                                <span class="txt"><c:out value="${dataInfo.docSubject }"/></span>
                                            </li>
                                            <li>
                                                <span class="title">${lfn:message('third-mall:kmReuseCommon.fdCurrentVersion')}：</span>
                                                <span class="txt">
                                                <c:out value="${dataInfo.fdCurrentVersion}"/>
                                            </span>
                                                <c:if test="${dataInfo.fdUse=='true' && dataInfo.fdUseAttMainId != dataInfo.attMainId  && dataInfo.fdUseVersion != dataInfo.fdCurrentVersion}">
                                                <span class="fy-newVersion">
                                                    <span class="fy-newVersion-font">
                                                            ${lfn:message('third-mall:kmReuseCommon.use.new.version')}
                                                    </span>
                                                </span>
                                                </c:if>
                                            </li>
                                            <li>
                                                <span class="title">${lfn:message('third-mall:kmReuseCommon.fdIndustry')}：</span>
                                                <span class="txt">
                  		<c:out value="${dataInfo.fdIndustryName }"/>
                    </span>
                                            </li>
                                            <li>
                                                <span class="title">${lfn:message('third-mall:kmReuseCommon.fdArea')}：</span>
                                                <span class="txt"><c:out value="${dataInfo.fdAreaName }"/></span>
                                            </li>
                                            <li>
                                                <span class="title">${lfn:message('third-mall:kmReuseCommon.fdKeyWord')}：</span>
                                                <span class="txt"><c:out value="${dataInfo.fdKeyWord}"/></span>
                                            </li>

                                            <%--<li style="width:100%">
                                                <span class="title">${lfn:message('third-mall:kmReuseCommon.prodVersion')}：</span>
                                                <span class="txt"><c:out
                                                        value="${dataInfo.fd_apply_prod_versionNames}"/></span>
                                            </li>--%>
                                        </ul>
                                    </div>
                                </div>
                                <!-- 面板 Ends -->
                                <div class="fy-detail-panel-heading">
                                    <h4 class="fy-detail-panel-heading-title">${lfn:message('third-mall:kmReuseCommon.applicationContent')}</h4>
                                </div>
                                    <%-- <img src="${LUI_ContextPath}/km/reuse/km_reuse_page/images/img-03.png" alt=""> --%>
                                <div id="_xform_docContent" _xform_type="rtf" class="timeaxis-content"
                                     style="word-break:break-all">
                                    <xform:rtf property="docContent" showStatus="view"/>
                                </div>
                            </div>
                            <!-- 详情介绍 end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </template:replace>
</template:include>
