<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="./../ThirdMallConfig_common.jsp" %>
<script>
    function preview(fdId, attId, vId, fdSubType) {
        seajs.use(['lui/dialog'], function (dialog) {
            /**预览*/
            var info = $('#picList_' + fdId);
            if (info && info.html()) {
                var x = document.createElement("input");
                x.setAttribute("type", "hidden");
                x.setAttribute("name", "mainParams");
                x.setAttribute("value", info.html());
                window.top.document.body.appendChild(x);

                var y = document.createElement("input");
                y.setAttribute("type", "hidden");
                y.setAttribute("name", "mainParamsType");
                y.setAttribute("value", "${fdType}");
                window.top.document.body.appendChild(y);

                var url = "/third/mall/public/thirdMall_preview.jsp";
                dialog.iframe(url, '${lfn:message("third-mall:thirdMall.preview")}', function (rt) {
                    window.top.document.body.removeChild(x)
                    window.top.document.body.removeChild(y)
                }, {
                    width: 920, height: 680, buttons: [
                        {
                            name: "${lfn:message('third-mall:thirdMall.use')}", value: true, focus: true,
                            fn: function (value, _dialog) {
                                useMallInfo(fdId, attId, vId, _dialog, fdSubType);
                            }
                        }, {
                            name: "${lfn:message('button.cancel')}", value: false, styleClass: 'lui_toolbar_btn_gray',
                            fn: function (value, dialog) {
                                dialog.hide(value);
                            }
                        }
                    ],
                });
                $.ajax({
                    type: "post",
                    url: '${LUI_ContextPath}/third/mall/thirdMallPublic.do?method=readMallLog',
                    data: {fdId:fdId,fdType:"${fdType}",fdKeyType:"${fdKeyType}"},
                    async: true,
                    dataType: 'json',
                    success: function (data) {

                    }
                });
            } else {
                dialog.alert("${lfn:message('third-mall:kmReuseCommon.preview.tip')}");
            }
        });
    }

    function goOpenMall(dialog) {
        dialog.confirm('${lfn:message("third-mall:thirdMall.tip.sysMain.name.noOpen")}', function (flag, d) {
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
    }

    function useMallInfo(fdId, attId, vId, __dialog, fdSubType) {
        seajs.use(['lui/dialog'], function (dialog) {
            <c:if test="${ (__fdMallEnable=='1' && fn:contains(__fdBusKeys,'sys_portal'))==false }">
            if (1 == 1) {
                goOpenMall(dialog);
                return;
            }
            </c:if>
            var curTYpe = "${fdType}";
            if (curTYpe == "component"
                || curTYpe == "login"
                || curTYpe == "theme"
            ) {
                //部件的子类型进行使用
                if (fdSubType) {
                    curTYpe = fdSubType;
                }
                seajs.use(['third/mall/resource/js/thirdPortalUse'], function (thirdPortalUse) {
                    thirdPortalUse.useTplNew(fdId, curTYpe, function (val) {

                    }, false);
                });
                return;
            }
            let useLoding = null;

            useLoding = dialog.loading();
            var param = {
                fdCurrentVersion: vId,
                fdAttMainId: attId,
                fdId: fdId,
                fdKeyType: '${fdKeyType}',
                fdType: '${fdType}'
            };
            $.ajax({
                type: "post",
                url: '${LUI_ContextPath}/third/mall/thirdMallPublic.do?method=useMallAjax',
                data: param,
                async: true,
                dataType: 'json',
                success: function (data) {
                    useLoding.hide();

                    if (data) {
                        if (data.status == 'noRight') {
                            goOpenMall(dialog);
                            return;
                        } else if (data.status === 'success') {

                            if (__dialog) {
                                __dialog.hide();
                            }
                            dialog.confirm('${lfn:message("third-mall:kmReuseCommon.use.success.tip3")}', function (flag, d) {
                                if (flag) {
                                    Com_OpenWindow('<c:url value="/sys/portal/sys_portal_main/sysPortalMain.do" />?method=edit&fdId=' + data.fdId + '&fdAnonymous=0');
                                }
                            }, null, [{
                                name: "${lfn:message('third-mall:kmReuseCommon.use.success.go.portal')}",
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
                        } else {
                            dialog.alert(data.msg);
                        }
                    }
                }
            });
        });

    }
</script>
<!--图片类型的展示列表-->
<c:forEach items="${queryPage.list}" var="dataBean" varStatus="status">
    <li class="fy-process-list-item">

        <div class="itemDiv">
            <div class="item-mask">
                <div class="mask-content">
                    <a href="javascript:void(0);"
                       onclick="preview('${dataBean.fdId}','${dataBean.attMainId}','${dataBean.fdCurrentVersion}','${dataBean.fdSubType}')">
                        ${lfn:message('third-mall:thirdMall.preview')}
                    </a>
                    <a href="javascript:void(0);"
                       onclick="useMallInfo('${dataBean.fdId}','${dataBean.attMainId}','${dataBean.fdCurrentVersion}',null,'${dataBean.fdSubType}')">
                       ${lfn:message('third-mall:thirdMall.use')}
                    </a>

                    <div style="display: none" id="picList_${dataBean.fdId}">${dataBean.picList}</div>
                </div>
            </div>
            <div class="fy-process-list-img-wrap">
                <c:choose>
                    <c:when test="${ dataBean.pic eq '' }">
                        <img
                                src="${mallDomMain}/km/reuse/km_reuse_page/images/kmReuseRedevelopImage.png"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${mallDomMain}/km/reuse/mobile/kmReusePublicMobileAction.do?method=downloadPic&fdId=${dataBean.pic}"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="fy-process-list-item-title"><c:out value="${dataBean.fdName}"/></div>
            <div class="fy-process-list-item-desc">

                <div class="fy-process-list-item-read">
                    <i class="fy-icon fy-icon-download"></i>
                    <c:choose>
                        <c:when test="${dataBean.fdDownloadCount > 999}">
                            999+
                        </c:when>
                        <c:otherwise>
                            <c:out value="${dataBean.fdDownloadCount}"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="fy-process-list-item-read">
                    <i class="fy-icon fy-icon-eye"></i>
                    <c:choose>
                        <c:when test="${dataBean.fdReadCount > 999}">
                            999+
                        </c:when>
                        <c:otherwise>
                            <c:out value="${dataBean.fdReadCount}"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </li>
</c:forEach>

