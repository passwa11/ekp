<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.landray.kmss.util.StringUtil,java.util.*" %>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault" %>
<%@ page import="com.landray.kmss.sys.notify.service.ISysNotifyTodoService" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.sys.notify.model.SysNotifyTodo" %>

<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
    request.setAttribute("xxx", Math.random());
    ISysNotifyTodoService sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
%>
<template:include ref="default.simple" sidebar="no">
    <template:replace name="body">
        <META HTTP-EQUIV="pragma" CONTENT="no-cache">
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
        <script>seajs.use(['theme!portal']);</script>
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/notify/resource/css/simplicity.css"/>
        <script src="${KMSS_Parameter_ResPath}js/domain.js"></script>
        <script type="text/javascript">
            Com_IncludeFile("jquery.js");
        </script>
        <div class="lui_notify_todo_list_body">
            <%-- 如果是被KK集成，则显示最后登录时间和IP --%>
            <c:if test="${param.forKK == 'true'}">
                <div class="lui_notify_todo_kk_row clearfloat">
                    <div class="lui_notify_todo_kk_textArea clearfloat">
                        <bean:message bundle="sys-notify" key="sysNotifyTodo.last.login.time"/>
                        <span class="lui_notify_todo_kk_created">
							<kmss:showDate value="${lastLoginInfo.fdCreateTime}" type="datetime"></kmss:showDate>
						</span>
                        <br>
                        <bean:message bundle="sys-notify" key="sysNotifyTodo.last.login.IP"/>
                        <span class="lui_notify_todo_kk_ip">
                            ${lastLoginInfo.fdIp}
                        </span>
                    </div>
                </div>
            </c:if>
            <c:set var="totalrows" value="${queryPage.totalrows}"/>
            <%-- 消息部件顶部提醒内容（  “您有 N 条 需处理事项” 、邮件数量、 刷新图标 、快速审批图标   ）  注：当门户组件为默认类型或图文类型时显示 --%>
            <c:if test="${empty portletType || portletType eq '' || portletType == 'graphic' || (portletType == 'category' && displayMode == 'singleCategory') }">
                <c:if test="${portletType == 'category' && displayMode == 'singleCategory' && fn:length(cateList) > 0}">
                    <c:set var="totalrows" value="${cateList[0].cateCount}"/>
                </c:if>
                <%@ include file="/sys/notify/sys_notify_todo_ui/sysNotifyTodo_home_remind.jsp" %>
            </c:if>

            <%-- 无相关处理数据时的提醒（“您  没有  需处理待办  喝杯咖啡休息一下吧！”） --%>
            <c:if test="${totalrows == 0}">
                <div class="lui_notify_todo_tips">
                    <div class="imgbox"></div>
                    <div class="txt">
                        <p>
                            <bean:message bundle="sys-notify" key="sysNotifyTodo.home.you"/>
                            <em><bean:message bundle="sys-notify" key="sysNotifyTodo.home.notHave"/></em>
                            <bean:message bundle="sys-notify" key="${infoTip}"/>
                        </p>
                        <p><bean:message bundle="sys-notify" key="sysNotifyTodo.home.noData.info"/></p>
                    </div>
                </div>
            </c:if>

            <%-- 待办内容 --%>
            <c:forEach var="model" items="${queryPage.list}">
                <div class="lui_notify_todo_list_item">
                    <%-- 是否显示头像 --%>
                    <c:if test="${param.showAvatar eq '1'}">
                    <span class="lui_notify_todo_list_item_img">
                        <img alt="" src="<person:headimageUrl personId="${model.docCreator.fdId}" contextPath="true" size="s" />">
                    </span>
                    </c:if>
                    <span class="lui_notify_title_icon lui_notify_content_${model.fdType}"></span>
                    <div class="lui_notify_todo_list_item_c clearfloat">
                        <div class="lui_notify_todo_list_item_title">
                        <c:if test="${fn:contains(model.fdExtendContent,'lbpmPress')}">
                            <!-- 催办 -->
                            <span class="lui_notify_todo_list_status lui_notify_todo_list_status_press">${lfn:message('sys-notify:sysNotifyTodo.process.press')}</span>
                        </c:if>
                        <c:if test="${model.fdType == 3}">
                            <!-- 暂挂 -->
                            <span class="lui_notify_todo_list_status lui_notify_todo_list_status_suspend">${lfn:message('sys-notify:sysNotifyTodo.type.suspend') }</span>
                        </c:if>
                        <c:if test="${model.fdLevel == 1}">
                            <!-- 紧急 -->
                            <span class="lui_notify_todo_list_status lui_notify_todo_list_status_urgent">${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}</span>
                        </c:if>
                        <c:if test="${model.fdLevel == 2}">
                            <!-- 急 -->
                            <span class="lui_notify_todo_list_status lui_notify_todo_list_status_imp">${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}</span>
                        </c:if>

                        <c:set var="_readStyle" value="style=''"/>
                        <c:if test="${model.read == true}">
                            <c:set var="_readStyle" value="style='color:#999;'"/>
                        </c:if>
                            <a title='<c:out value="${model.subject4View}"/>'
                               class="lui_notify_list_link" ${_readStyle}
                               target="_blank" onclick="onNotifyClick(this,'${model.fdType}')"
                                    <c:if test="${not empty model.fdLink}">
                                        data-href="<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=${model.fdId}&r=${xxx}"/>"
                                    </c:if>
                            >
                                <c:if test="${showAppHome == 1}">
                                    <c:set var="appName" value="${model.fdAppName}"/>
                                    <c:if test="${appName != null && appName != '' }">
                                        <bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.left"/>
                                        <c:out value="${appName}"/>
                                        <bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.right"/>
                                    </c:if>
                                </c:if>
                                <c:out value="${model.subject4View}"/>
                            </a>

                        </div>
                        <%-- 通用属性 --%>
                        <p class="lui_notify_list_info">
                            <%-- 创建人 --%>
                            <span class="lui_notify_list_info_item lui_notify_list_creator">
                                ${model.docCreator.fdName}
                            </span>
                            <%-- 创建时间 --%>
                            <span class="lui_notify_list_info_item lui_notify_list_created">
                                <kmss:showDate value="${model.fdCreateTime}" type="datetime" />
                            </span>
                            <%-- 来源模块 --%>
                            <%
                                SysNotifyTodo todo = (SysNotifyTodo) pageContext.getAttribute("model");
                                String modelNameText = sysNotifyTodoService.getModelNameText(todo);
                            %>
                            <span class="lui_notify_list_info_item lui_notify_list_module"><%=modelNameText%></span>
                            <%-- 当前处理节点 --%>
                            <kmss:showWfPropertyValues var="nodevalue" idValue="${model.fdModelId}" propertyName="nodeName" />
                            <span class="lui_notify_list_info_item lui_notify_list_node">
                                <bean:message bundle="sys-notify" key="sysNotifyTodo.simplicity.node"/><c:out value="${nodevalue}"></c:out>
                            </span>
                        </p>
                    </div>
                </div>
            </c:forEach>
        </div>

        <script type="text/javascript">
            domain.autoResize();
        </script>

        <script type="text/javascript">
            function onNotifyClick(hrefObj, notifyType) {
                //待阅点击后及时消失
                if (notifyType == '2') {
                    //当前行顶级节点
                    var p = $(hrefObj)[0].parentNode.parentNode;
                    p.style.display = "none";
                    //待阅异步更新
                    setTimeout(function () {
                        reloadDatas();
                    }, 2000);

                    //待阅portlet窗口数量刷新
                    var toViewCount = $('#toViewCount').html();
                    if (!isNaN(toViewCount)) {
                        toViewCount = parseInt(toViewCount, 10);
                        if (toViewCount > 0) {
                            $('#toViewCount').html(toViewCount - 1);
                            updatePortalNotifyTitleCount();
                        }
                    }

                    var countObj = document.getElementById("notifyCount2");
                    var btnObj = document.getElementById("notifyBtn2");
                    if (countObj != null) {
                        if (!isNaN(countObj.innerText)) {
                            var countInt = parseInt(countObj.innerText, 10);
                            if (countInt > 0) {
                                $(countObj).text(countInt - 1);
                            }
                        }
                    }
                    if (btnObj != null) {
                        var oldBtnVal = btnObj.value;
                        if (oldBtnVal.indexOf("(") > -1) {
                            var labelName = oldBtnVal.substring(0, oldBtnVal.indexOf("("));
                            var countVar = oldBtnVal.substring(oldBtnVal.indexOf("(") + 1, oldBtnVal.length - 1);
                            if (!isNaN(countVar)) {
                                var count = parseInt(countVar, 10);
                                if (count > 0) {
                                    btnObj.value = labelName + "(" + (count - 1) + ")";
                                }
                            }
                        }
                    }
                }
                var href = $(hrefObj).data("href");
                if (href) {
                    Com_OpenWindow(href);
                }
            }

            //分类数量
            function onNotifyCountClick(cateId, notifyType) {
                //待阅点击后及时消失
                if (notifyType == '2') {
                    setTimeout(function () {
                        reloadDatas();
                    }, 1000);
                }
            }

            function onPublishPortalRefresh() {
                domain.call(window.parent, "fireEvent", [{
                    type: "topic",
                    name: "portal.notify.refresh"
                }]);
            }

            function updatePortalNotifyTitleCount() {
                var count = $('#toViewCount').html() || 0;
                if (!isNaN(count) && count >= 0) {
                    //跨域事件处理
                    domain.call(window.parent, "fireEvent", [{
                        type: "topic",
                        name: "portal.notify.title.count",
                        luiId: "${lfn:escapeJs(LUI_ID)}",
                        count: count
                    }]);
                }
            }

            function reloadDatas() {
                var pWin = window.parent;
                if (pWin && pWin.refreshNotifyData) {
                    pWin.refreshNotifyData();
                } else {
                    location.reload();
                }
                onPublishPortalRefresh();
            }

            function refreshGlobalNotifyData() {
                var key = '${JsParam.LUIID}';
                if (window.parent && key) {
                    var pWin = window.parent;
                    if (!pWin.notifyEventTargets) {
                        pWin.notifyEventTargets = {};
                        pWin.notifyEventTargets[key] = location;
                    } else if (pWin.notifyEventTargets) {
                        pWin.notifyEventTargets[key] = location;
                    }
                    pWin.refreshNotifyData = function () {
                        for (var prop in pWin.notifyEventTargets) {
                            if (pWin.notifyEventTargets[prop]) {
                                pWin.notifyEventTargets[prop].reload();
                            }
                        }
                    }
                }
            }

            seajs.use(['lui/jquery', 'lui/topic'], function ($, topic) {
                //审批等操作完成后，自动刷新列表
                topic.subscribe('successReloadPage', function () {
                    reloadDatas();
                });
            });
            //统一门户列表中待办数量
            $(document).ready(function () {
                // 更新部件页签的数字角标
                updatePortalNotifyTitleCount();
                //定义全局刷新事件
                refreshGlobalNotifyData();
            });
        </script>
    </template:replace>
</template:include>