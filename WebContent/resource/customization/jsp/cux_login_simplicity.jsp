<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Random,java.io.File,java.io.FilenameFilter" %>
<%@ page import="java.util.Locale,java.util.Date" %>
<%@ page import="com.landray.kmss.web.Globals" %>
<%@ page import="com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.DateUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
    Cookie open = new Cookie("isopen", "close");
    open.setMaxAge(0);
    open.setPath(request.getContextPath() + "/");
    response.addCookie(open);
    pageContext.setAttribute("locale", ResourceUtil.getLocaleByUser().getCountry());
    pageContext.setAttribute("loginImagePath", LoginTemplateUtil.getLoginImagePath());
%>

<template:include ref="default.login">
    <template:replace name="head">
        <link href="${ LUI_ContextPath }/resource/style/default/login_simplicity/font/iconfont.css?s_cache=${LUI_Cache}"
              rel="stylesheet" type="text/css"/>
        <link type="text/css" rel="stylesheet"
              href="${ LUI_ContextPath }/resource/style/default/login_simplicity/css/login.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${ LUI_ContextPath }/resource/style/default/login_simplicity/css/animate.css?s_cache=${LUI_Cache}"/>
        <script type="text/javascript" src="${LUI_ContextPath }/resource/customization/js/vue.min.js"></script>
        <style>
            .choose_hover {
                padding: 4px 6px;
                border: 3px dashed #f3b521;
            }
        </style>
        <script>
            Com_IncludeFile("jquery.js");
            Com_IncludeFile("jquery.fullscreenr.js|custom.js", "style/default/login_simplicity/js/");
        </script>
    </template:replace>
    <template:replace name="body">
        <div id="app" class="login-background-wrap" :class="[login_logo_position]">
            <!-- 背景图片 Starts -->
            <div class="login-background-img"><img id="login-bgImg" alt=""/></div>
            <!-- 背景图片 Ends -->
            <div class="login_header">
                <div class="login_top_bar" style="display:block;">
                    <ul v-bind:class="{choose_hover:isHeadLinksActive}">
                        <li
                                v-for="(link, index) in simplicity_head_links"
                                :key="link.id">
                            <a target="_blank"
                               v-bind:href="link.simplicity_head_link_href">{{${lfn:concat('link.simplicity_head_link_',locale)}}}</a>
                        </li>
                    </ul>
                </div>
                <div class="main_content">

                </div>
            </div>
            <div class="login_content main_content">
                <!-- 登录框 开始 -->
                <div class="login_iframe" :class="[login_form_align]">
                    <span class="logo" v-bind:class="{choose_hover:isLogoActive}">
                        <img v-bind:src="'${LUI_ContextPath }${loginImagePath }/login_simplicity/'+simplicity_logo" :style="{'height': login_login_height+'px' }"/>
                    </span>
                    <ui:combin ref="login.form">
                        <ui:varParam name="designed">1</ui:varParam>
                        <ui:varParam name="designBgColor">1</ui:varParam>
                    </ui:combin>
                    <!-- 登录框 结束 -->
                    <div class="login_footer">
                        <p>
                            <span v-bind:class="{choose_hover:isCopyrightActive}">{{${lfn:concat('simplicity_footerInfo_',locale)}}}</span>
                        </p>
                    </div>
                </div>
            </div>

        </div>
        <script type="text/javascript">
            <%
                String lang = request.getParameter("j_lang");
                if (lang == null) {
                    Locale xlocale = ((Locale) session.getAttribute(Globals.LOCALE_KEY));
                    if (xlocale != null)
                        lang = xlocale.getLanguage();
                }
                pageContext.setAttribute("j_lang", lang);
            %>
            LUI.ready(function () {
                if ("en-US" == "${lfn:escapeJs(j_lang)}") {
                    //英文环境
                    $('body').addClass('muilti_eng');
                }
            });

            // 获取随机登录图片信息，返回随机获取的图片名称
            function get_random_bg() {
                var cache = Math.floor(Math.random() * Math.pow(10, 13));
                <%
                    String path = request.getSession().getServletContext().getRealPath("/");
                    path = path.replaceAll("\\\\", "/");
                    if (path.endsWith("/")) {
                        path = path.substring(0,path.length()-1);
                    }
                    String filePath = path + LoginTemplateUtil.getLoginImagePath()+"/login_simplicity";
                    File file = new File(filePath);
                    Random random = new Random();
                    File[] files = file.listFiles(new FilenameFilter(){
                        public boolean accept(File file, String str) {
                            String name = str.toLowerCase();
                            return name.startsWith("simplicity_login_bg") && (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png"));
                        }
                    });
                    File bg = files[random.nextInt(files.length)];
                %>
                return "<%=bg.getName()%>?s_cache=" + cache;
            }

            //添加提示
            $('body').append('<div class="tipsClass"><%=ResourceUtil.getString("login.page.captial.tip")%></div>');
            $(document).ready(function () {
                var iconfig = window.parent.config;
                new Vue({
                    el: '#app',
                    data: iconfig,
                    methods: {
                        btnEnter: function (evt) {
                            var $target = $(evt.target);
                            var prop = $target.data('color');
                            if (iconfig[prop + '_hover'] != null)
                                $target.css({
                                    'background': iconfig[prop + '_hover'],
                                    'borderColor': iconfig[prop + '_hover']
                                });
                        },
                        btnLeave: function (evt) {
                            var $target = $(evt.target);
                            var prop = $target.data('color');
                            if (iconfig[prop] != null)
                                $target.css({'background': iconfig[prop], 'borderColor': iconfig[prop]});
                        }
                    }
                });
            });
        </script>
    </template:replace>
</template:include>

