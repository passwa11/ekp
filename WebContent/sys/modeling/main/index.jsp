<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.modeling.base.service.IModelingApplicationService" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="net.sf.json.JSONObject" %>
<%
    IModelingApplicationService appService = (IModelingApplicationService) SpringBeanUtil.getBean("modelingApplicationService");
    JSONObject appInfo = new JSONObject();
    try {
        appInfo = appService.getAppInfo(request.getParameter("fdAppId"), request.getParameter("fdNavId"));
        if (appInfo.get("nav") == null) {
            appInfo.put("nav", new JSONArray());
            request.getRequestDispatcher(appInfo.getString("error")).forward(request, response);
        }
        if (appInfo.getJSONObject("portalNav") == null) {
            appInfo.put("portalNav", new JSONObject());
        }
    } catch (Exception e) {
        request.getRequestDispatcher("/resource/jsp/error.jsp").forward(request, response);
    }
%>
<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title"><%=appInfo.get("name") %>
    </template:replace>
    <%--	<template:replace name="script">--%>
    <%--		--%>
    <%--	</template:replace>--%>
    <template:replace name="head">
        <script>
            var portalNav = '<%=appInfo.getJSONObject("portalNav") %>';
            if (portalNav) {
                window.portalData = domain.toJSON(portalNav);
                window.portalURL = portalData ? portalData.value : null;//全局对象
                //暂时屏蔽门户入口
                window.portalURL = null;
            }
        </script>
        <script type="text/javascript">
            // 动态模块的类型信息
            var __modelType = {
                "flow": {
                    "modelName": "com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain",
                    "actionUrl": "/sys/modeling/main/modelingAppModelMain.do",
                    isFlow: true
                },
                "noflow": {
                    "modelName": "com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain",
                    "actionUrl": "/sys/modeling/main/modelingAppSimpleMain.do",
                    isFlow: false
                }
            };

            seajs.use(['lui/framework/module', 'lui/topic', 'lui/framework/router/router-utils', 'lui/topic'], function (Module, topic, routerUtils, topic) {
                Module.install('sysModeling', {
                    //模块变量
                    $var: {
                        "fdApplicationId": "${param.fdAppId}",
                        "modelType": __modelType
                    },
                    //模块多语言
                    $lang: {},
                    //搜索标识符
                    $search: ''
                });

                window.isAddPortalNode = false;

                // 初始化事件
                LUI.ready(function () {
                    //有时候不调用，所以没办法监听面板加载完事件，所以添加了load事件监听
                });

                //暂时屏蔽门户入口，请勿删除
                /* Com_AddEventListener(window,'load',function(){
                    var num = setInterval(function(){
                        if(window.isAddPortalNode){
                            clearInterval(num);
                        }
                        if(window.portalData && window.portalData.title && $(".lui_accordionpanel_header_l:eq(0)").length > 0 && $("#portalHeader").length <= 0){
                             var $newHeader = $(".lui_accordionpanel_header_l:eq(0)").clone();
                             $newHeader.attr("id","portalHeader");
                             $newHeader.find("[data-lui-mark='panel.nav.title']").find(".lui_tabpanel_navs_item_title").text(window.portalData.title);
                             $newHeader.find("[data-lui-mark='panel.nav.toggle']").css("display","none");
                             var $newFrame = $("<div class='lui_accordionpanel_content_frame'></div>").append($newHeader);
                             $(".lui_accordionpanel_frame").prepend($newFrame);
                             $newHeader.bind('click',function(){
                                 //移除导航选中状态
                                 LUI.$("[data-lui-type*=AccordionPanel] li").removeClass("lui_list_nav_selected");
                                 topic.publish("nav.operation.clearStatus", null);
                                 var $router = routerUtils.getRouter();
                                 $router.push(window.portalData.href, window.portalData.params || {});
                             })
                             var path = routerUtils.getRouter(true)._getHashPath() || "";
                             if(!path){
                                 $newHeader.trigger($.Event('click'));
                             }
                             window.isAddPortalNode = true;
                         }
                  }, 10);
                }) */
            });
        </script>
        <script type="text/javascript" src="${LUI_ContextPath}/sys/modeling/main/resources/js/index.js"></script>
    </template:replace>
    <template:replace name="nav">
        <c:set var="icon" value='<%=appInfo.optString("icon") %>'></c:set>
        <c:if test="${empty icon}">
            <%-- 头部导航 --%>
            <ui:combin ref="menu.nav.title">
                <ui:varParam name="title" value='<%=appInfo.getString("name") %>'></ui:varParam>
            </ui:combin>
        </c:if>
        <c:if test="${not empty icon}">
            <ui:combin ref="menu.nav.create">
                <ui:varParam name="title" value='<%=appInfo.getString("name") %>'/>
                <ui:varParam name="button">
                    [
                    {
                    "text": "",
                    "href": "javascript:void(0);",
                    "icon": "iconfont_nav " + "<%=appInfo.getString("icon") %>"
                    }
                    ]
                </ui:varParam>
            </ui:combin>
        </c:if>
        <c:set var="allExpand" value="true"/>
        <%-- 左侧导航 --%>
        <div id="menu_nav" class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:forEach items='<%=appInfo.getJSONArray("nav") %>' var="navContainer" varStatus="vstatus">
                    <c:if test="${navContainer.get('expand') }">
                        <c:set var="allExpand" value="false"/>
                    </c:if>
                    <ui:content title='${ navContainer.get("title") }' expand='${ navContainer.get("expand") }'>
                        <ui:combin ref="modeling.menu.nav.simple">
                            <ui:varParam name="source">
                                <ui:source type="Static">
                                    ${ navContainer.getJSONArray("nodes") }
                                </ui:source>
                            </ui:varParam>
                        </ui:combin>
                    </ui:content>
                </c:forEach>
            </ui:accordionpanel>
        </div>
        <script>
            //当用户配置的时候默认收起所有一级导航时，显示第一个一级导航的第一个列表视图。
            window.onload = function () {
                //#172385 进入业务导航，默认进入第一个视图，但左边列表视图栏没有标识进入的是哪个视图
                setTimeout("setLiClass()",500);
                if ("${allExpand}") {
                    var url = document.location.toString();
                    //当已经进入导航后也不执行
                    //#172677 防止url没有params时，请求报错
                    if (url.indexOf("j_path=") > -1 && url.indexOf("-dynamic") < url.length-8) {
                        console.debug("全部收齐自动显示，但当已经进入导航后也不执行");
                        return;
                    }
                    var navs = <%=appInfo.getJSONArray("nav") %>;
                    if (!navs || navs.length == 0) {
                        return;
                    }
                    var node1stChildren = navs[0].nodes || [];
                    if (!node1stChildren[0]) {
                        return;
                    }
                    var node = node1stChildren[0];
                    seajs.use(['lui/framework/router/router-utils'],
                        function (routerUtils) {
                            try {
                                console.debug("自动加载开始");
                                var $router = routerUtils.getRouter();
                                $router.push(node.href, node.params || {});
                                console.debug("自动加载结束");
                            } catch (e) {
                                console.error("导航首次点击事件失效", e);
                            }
                        });
                }
            };
            function setLiClass(){
                var liSelected = $(".lui_list_nav_selected").length;
                if(liSelected == 0){
                    var firstLiDom = $(".lui_list_nav_list:first").children("li:first");
                    firstLiDom.addClass("lui_list_nav_selected");
                }
            }

        </script>
    </template:replace>
    <!-- 右侧内容区 -->
    <template:replace name="content">
    </template:replace>


</template:include>