<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal" %>
<!doctype html>
<html  style="height: 100%;overflow: hidden;">
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
                <div id="mainContent" class="lui_list_mainContent" style="margin: 0;padding: 0;height: 100%;">

                    <div class="lui_modeling">
                        <div class="lui_modeling_aside">
                            <div data-lui-type="lui/base!DataView" style="display:none;">
                                <ui:source type="Static">
                                    [{
                                    "text" : "${lfn:message('sys-xform-maindata:tree.relation.jdbc.custom')}",
                                    "iframeId":"base_iframe",
                                    "selected":"true",
                                    "src" : "sys/modeling/base/baseDbInfo.do?method=index&type=custom"
                                    },{
                                    "text" : "<bean:message bundle="sys-xform-maindata"
                                                            key="tree.relation.main.dadta.insystem"/>",
                                    "iframeId":"base_iframe",
                                    "src" : "sys/modeling/base/baseDbInfo.do?method=index&type=system"
                                    },{
                                    "text" : "${lfn:message('sys-modeling-base:modeling.material.library')}",
                                    "iframeId":"base_iframe",
                                    "src" : "sys/modeling/base/material/index.jsp"
                                    }]
                                </ui:source>
                                <ui:render type="Javascript">
                                    <c:import url="/sys/modeling/base/resources/js/menu_side.js"
                                              charEncoding="UTF-8"></c:import>
                                </ui:render>
                            </div>
                        </div>
                        <div class="lui_modeling_main aside_main"style="height:100%">
                            <iframe id="base_iframe" class="lui_modeling_iframe_body" frameborder="no" border="0"
                                    src="<c:url value="/sys/modeling/base/baseDbInfo.do?method=index&type=custom" />"></iframe>
                        </div>
                    </div>

                </div>
            </div>
        </td>
    </tr>
</table>
<script>
    $(function () {
        //var viewHeight = window.innerHeight - 180;
        var $iframe = document.getElementById("base_iframe");
        //$($iframe).css({height: viewHeight});

        if ($iframe.attachEvent) {
            $iframe.attachEvent("onload", function () {
                hideImportInitDataBtn();
            });
        } else {
            $iframe.onload = function () {
                hideImportInitDataBtn();
            };
        }
        function hideImportInitDataBtn() {
            //#169855 分页标签显示
            var viewHeight = $(".lui_modeling_main.aside_main")[0].clientHeight;
            $($iframe).css("height", viewHeight-50);
            if($iframe.src.indexOf('type=system') === -1) {
                return;
            }
            var interval = setInterval(__interval,50);
            function __interval(){
                let impBtnMsg = "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.import.initData')}";
                let impBtn = $($iframe).contents().find('div[title="'+impBtnMsg+'"]');
                if(impBtn.length === 0)
                    return;
                impBtn.hide();
                clearInterval(interval);
            }
        }
    });
</script>
</body>
</html>
