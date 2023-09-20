<%@page import="com.landray.kmss.third.pda.interfaces.ThirdPdaSeparateService"%>
<%@page import="com.landray.kmss.third.pda.util.PdaFlagUtil"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileFormUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld"
    prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%
//这里的uri是@Separater里描述的uri
String uri = request.getRequestURI();
//解析出来会是一个数组，第一个元素是空串，[, km, review, mobile, view.jsp]，数组长度>=5
String[] split = uri.split("[/]");
//取1，2元素用于拼接模块url前缀
String _path = "/"+split[1]+"/"+split[2]+'/';
//System.out.println(_path);
com.landray.kmss.sys.config.design.SysCfgModule module = 
com.landray.kmss.sys.config.design.SysConfigs.getInstance().getModule(_path);
String msg2 = null;
if(module!=null){
    String msg = module.getMessageKey();
    msg2 = ResourceUtil.getString(msg);
}else{
    //兼容3级的模块情况
    _path = "/"+split[1]+"/"+split[2]+'/'+split[3]+'/';
    module = com.landray.kmss.sys.config.design.SysConfigs.getInstance().getModule(_path);
    if(module!=null){
        String msg = module.getMessageKey();
        msg2 = ResourceUtil.getString(msg);
    }
}
String defaultTitle = msg2==null?"loading":msg2;
pageContext.setAttribute("__title__",defaultTitle);

%>
<%
    boolean transformScale = MobileFormUtil.transformScale(request);
    request.setAttribute("transformScale",transformScale);
%>


<c:set var="tiny" value="true" scope="request" />
<c:set var="lang" value="<%=ResourceUtil.getLocaleByUser().getCountry()%>" />
<!DOCTYPE HTML>
<html class="mobile mui-${lang.toLowerCase()}-html <%="true".equals(SysUiConfigUtil.getFdIsSysMourning()) ? "mourning" : ""%>">
<head>
<meta name="viewport"
    content="viewport-fit=cover,width=device-width,initial-scale=1,minimum-scale=1<c:if test="${requestScope['transformScale'] == false}">,maximum-scale=1,user-scalable=no</c:if>" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style"
    content="black-translucent" />
<meta content="telephone=no" name="format-detection" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${__title__}</title>

<c:if test="${param.s_console == true }">
    <mui:cache-file path="/sys/mobile/js/lib/vconsole/vconsole.js"
        cacheType="md5" />
    <script type="text/javascript">
        new VConsole()
    </script>
</c:if>
<mui:cache-file name="common-tiny.css" cacheType="md5" />
<mui:cache-file name="view-tiny.css" cacheType="md5" />
<template:block name="csshead" />
</head>
<body>
    <div id="pageLoading">
        <div class="skeleton">
            <div class="short"></div><div class="long"></div><div class="long"></div><div class="normal"></div><div class="long"></div><div class="normal"></div>
        </div>
        <script>
            setTimeout(function(){
                document.querySelector('.skeleton').setAttribute('data-skeleton-animation','true');
            },1)
        </script>
    </div>
    <%@ include file="./dojoConfig-tiny.jsp"%>
    <script type="text/javascript"
        src="<%=request.getContextPath()%>/sys/mobile/js/dojo/dojo.js?s_cache=2adab333e133b33c0006f45e51413ea8"></script>
    <%
        String url = request.getRequestURI().replaceFirst(
                request.getContextPath(), "");
        String dataUrl = ThirdPdaSeparateService.getDataUrl(url, request);
        request.setAttribute("dataUrl", dataUrl);
        if (!PdaFlagUtil.checkClientIsPda(request)) {
    %>
    <script>
        require([ "mui/util" ], function(util) {
            var url = util.formatUrl("${dataUrl}");
            location.href = url;
        });
    </script>
    <%
        return;
        }
    %>
    <mui:cache-file name="mui-common.js" cacheType="md5" />
    <mui:cache-file name="mui-view.js" cacheType="md5" />
    <c:if
        test="${param.compatibleMode eq 'true' or param.compatibleMode eq '1'}">
        <c:import url="/sys/mobile/template/com_head.jsp"></c:import>
    </c:if>
    <template:block name="head" />
    <div id="content"
        <xform:viewShow>class="muiTemplateView"</xform:viewShow>>
        <template:block name="content" />
    </div>
    <script type="text/javascript">
        require([ "mui/load" ], function(load) {
            load.loadAndParse("${dataUrl}");
        });
    </script>
    <div data-dojo-type="mui/top/Top"
        data-dojo-mixins="mui/top/_TopViewMixin"></div>
    <%@ include file="/resource/jsp/watermarkMobile.jsp" %>
</body>
</html>