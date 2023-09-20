<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal" %>
<!doctype html>
<html>
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
<body class="lui_list_body">

<table style="width:100%; ">
    <tr>

        <td valign="top" class="lui_list_body_td">
            <div class="lui_list_body_frame" style="margin:0;padding: 0;">
                <div id="queryListView" style="width:100%">
                    <template:block name="path"/>
                    <template:block name="content"/>
                </div>
                <div id="mainContent" class="lui_list_mainContent" style="margin: 0;padding: 0;">

					<div class="lui_modeling">
						<div class="lui_modeling_aside" id="space-title">
							<div data-lui-type="lui/base!DataView" style="display:none;">
								<ui:source type="Static">
									[{
                                        "text" : "${lfn:message('sys-modeling-base:table.modelingAppNav')}",
                                        "iframeId":"trigger_iframe",
                                        "selected":"true",
                                        "src" :  "sys/modeling/base/profile/nav/index_body.jsp?fdAppId=${param.fdAppId}"
                                    },{
										"text" : "${lfn:message('sys-modeling-base:table.modelingAppSpace')}",
										"iframeId":"trigger_iframe",
										"src" :  "sys/modeling/base/space/index_body.jsp?fdAppId=${param.fdAppId}"
                                    },{
										"text" : "${lfn:message('sys-modeling-base:table.modelingAppMobile')}",
										"iframeId":"trigger_iframe",
										"src" :  "sys/modeling/base/mobile/index.jsp?fdAppId=${param.fdAppId}"
									}]
								</ui:source>
								<ui:render type="Javascript">
									<c:import url="/sys/modeling/base/resources/js/menu_side.js" charEncoding="UTF-8"></c:import>
								</ui:render>
							</div>
						</div>
						<div class="lui_modeling_main aside_main" id="space-content">
							<iframe id="trigger_iframe" class="lui_modeling_iframe_body" frameborder="no" border="0" src="<c:url value="/sys/modeling/base/profile/nav/index_body.jsp?fdAppId=${param.fdAppId}" />"></iframe>
						</div>
					</div>
			   </div>
            </div>
        </td>
    </tr>
</table>
<script>
    $(function () {
        var viewHeight = window.innerHeight - 90;
        $('#trigger_iframe').css({height: viewHeight});
    });
</script>
</body>
</html>
