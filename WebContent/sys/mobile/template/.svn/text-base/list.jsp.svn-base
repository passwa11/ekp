<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="./tripartiteAdmin.jsp" %>
<c:set var="include" value="false"/>
<c:if test="${param.include eq 'true'}">
    <c:set var="include" value="true"/>
</c:if>
<c:if test="${include eq 'false'}">
    <!DOCTYPE HTML>
</c:if>
<c:set var="ui" value="oldMui"/>

<c:set var="lang" value="<%=ResourceUtil.getLocaleByUser().getCountry()%>" />
<c:if test="${param.newMui eq 'true'}">
    <c:set var="ui" value="newMui"/>
</c:if>

<c:if test="${include eq 'false'}">
    <html class="mobile mui-${lang.toLowerCase()}-html ${ui} <%="true".equals(SysUiConfigUtil.getFdIsSysMourning()) ? "mourning" : ""%>">
    <head>
        <meta name="viewport"
              content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no,viewport-fit=cover"/>
        <meta name="apple-mobile-web-app-capable" content="yes" />
        <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
        <meta name="format-detection" content="telephone=no" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><template:block name="title" /></title>
        <c:if test="${param.s_console == true }">
            <mui:cache-file path="/sys/mobile/js/lib/vconsole/vconsole.js" cacheType="md5"/>
            <script type="text/javascript">new VConsole()</script>
        </c:if>

        <c:choose>
            <c:when test="${param.tiny eq 'true' || tiny eq 'true' }">
                <mui:cache-file name="common-tiny.css" cacheType="md5"/>
                <mui:cache-file name="list-tiny.css" cacheType="md5"/>
            </c:when>
            <c:otherwise>
                <mui:cache-file name="common.css" cacheType="md5"/>
                <mui:cache-file name="list.css" cacheType="md5"/>
            </c:otherwise>
        </c:choose>

        <template:block name="csshead" />
    </head>
    <body class="${param.bodyClass}">
    <div id="pageLoading">
        <template:block name="loading">
            <ui:combin ref="${not empty param.loadRef ? param.loadRef : 'loading.default' }">
                <c:if test="${not empty param.loadCfg }">
                    <ui:varParam name="config" value="${param.loadCfg }"></ui:varParam>
                </c:if>
            </ui:combin>
        </template:block>
    </div>
    <%@ include file="./dojoConfig.jsp" %>
    <mui:cache-file name="dojo.js" cacheType="md5"/>
    <c:choose>
        <c:when test="${param.tiny eq 'true' || tiny eq 'true' }">
            <mui:cache-file name="mui-common.js" cacheType="md5"/>
            <mui:cache-file name="mui-list.js" cacheType="md5" />
        </c:when>
        <c:otherwise>
            <mui:cache-file name="mui.js" cacheType="md5"/>
        </c:otherwise>
    </c:choose>
    <template:block name="head" />
    <div id="content">
        <template:block name="content" />
    </div>
    <script type="text/javascript">
        <c:if test="${param.newMui == 'true'}">
        dojoConfig.newMui = true;
        </c:if>
        <c:if test="${param.canHash == 'true'}">
        dojoConfig.canHash = true;
        </c:if>
        require(["dojo/parser", "mui/main", "mui/pageLoading", "mui/util", "dojo/_base/window","dojox/mobile/sniff","dojo/domReady!"],
            function(parser, main, pageLoading, util, win, has){
                try {
                    parser.parse().then(function() {
                        win.doc.dojoClick = !has('ios');
                        pageLoading.hide();

                        setTimeout(function(){
                            try{
                                util.preLoading('${KMSS_Parameter_ContextPath}sys/mobile/js/lib/compatible.js?s_cache=7bf8b4a88bccb1113b6fd954bfc86a1f');
                                util.preLoading('<mui:min-cachepath name="sys-lbpm.js"/>');
                                util.preLoading('<mui:min-cachepath name="sys-lbpm-note.js"/>');
                                <c:choose>
                                <c:when test="${param.tiny eq 'true' || tiny eq 'true' }">
                                util.preLoading('<mui:min-cachepath name="view-tiny.css"/>');
                                </c:when>
                                <c:otherwise>
                                util.preLoading('<mui:min-cachepath name="view.css"/>');
                                </c:otherwise>
                                </c:choose>

                                //预加载view页面js
                            }catch(e){}
                        },2000);

                    });
                } catch (e) {
                    alert(e);
                }
            });
    </script>
    <div data-dojo-type="mui/top/Top"
         data-dojo-mixins="mui/top/_TopListMixin"
         data-dojo-props="bottom:'${param.sideTop}'"></div>
    <%@ include file="/resource/jsp/watermarkMobile.jsp" %>
    </body>


    </html>
</c:if>
<c:if test="${include eq 'true'}">
    <template:block name="head" />
    <div id="content">
        <template:block name="content" />
    </div>
</c:if>