<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal" %>
<%@ page import="com.landray.kmss.util.UserUtil"%>

<%
    if (!UserUtil.checkRole("ROLE_THIRDMALL_USE")) {
        request.getRequestDispatcher("/resource/jsp/e403.jsp").forward(request, response);
    }

%>
<!doctype html>
<html >
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit"/>
    <%@ include file="/sys/ui/jsp/jshead.jsp" %>

    <script type="text/javascript">
        seajs.use(['theme!list', 'theme!portal']);
    </script>
    <script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript" src="${ LUI_ContextPath }/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/profile/resource/js/dropdown.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/ui/extend/theme/default/style/icon.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${LUI_ContextPath}/sys/modeling/base/relation/relation/css/relation.css?s_cache=${LUI_Cache}"/>

    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css"/>
    <title>
        <template:block name="title"/>
    </title>
    <template:block name="head"/>
</head>
<body class="lui_list_body" style="height:100%;">

<table style="width:100%;height: 100%;">
    <tr style="width:100%;">

        <td valign="top" class="lui_list_body_td" style="height: 100%;">
            <div class="lui_list_body_frame" style="margin:0;padding: 0;height: 100%;">
                <div id="queryListView" style="width:100%">
                    <template:block name="path"/>
                    <template:block name="content"/>
                </div>
                <div>
                    <!--开启了云商城-->
                    <c:set var="isExist" value="false"/>
                    <kmss:ifModuleExist path="/third/mall/">
                        <c:set var="isExist" value="true"/>
                    </kmss:ifModuleExist>
                    <c:if test="${'true' eq isExist}">
                        <ui:iframe id="main_show_iframe"
                                   src='${LUI_ContextPath }/third/mall/thirdMallPublic.do?method=publicIndex&type=sysApplication'>
                        </ui:iframe>
                    </c:if>
                    <c:if test="${'true' != isExist}">
                        暂未集成云商城模块
                    </c:if>
                </div>
            </div>
        </td>
    </tr>
</table>
</body>
</html> 
