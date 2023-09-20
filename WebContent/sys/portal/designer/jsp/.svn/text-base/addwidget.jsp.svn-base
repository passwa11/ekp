<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
    request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.simple">
    <%@ include file="/sys/portal/designer/jsp/inc/head.jsp"%>
    <template:replace name="head">
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/portal/designer/css/page_edit.css?s_cache=${LUI_Cache }">
    </template:replace>
    <template:replace name="body">
        <script type="text/javascript">
            Com_IncludeFile("jquery.js|form.js", null, "js");
        </script>
        <script>
            var dialogWin = window.top;
            function onReady(){
                if(window.$dialog == null){
                    window.setTimeout(onReady, 100);
                    return
                }
                window.$ = LUI.$;
                //LUI("btnPrevStep").setVisible(false);
                LUI.$.getJSON("${LUI_ContextPath}/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=panelLayout&scene="+window.scene,function(json){
                    panelLayout = json;
                    $($("[name='portlet_panel_height_ext']").get(0)).attr("checked",true);
                    $($("[name='portlet_tabpanel_height_ext']").get(0)).attr("checked",true);
                    if(window.$dialog.dialogParameter != null){
                        //编辑模式
                        onConfigWidget(window.$dialog.dialogParameter);
                    }else{
                        //新增模式
                        // #149589 选择单标签默认值错误，会同时两次两次函数
                        //单标签
                        // portletChangePanelType();
                        // changePortletPanelLayout(document.getElementById("portlet_panel_layout"));

                        // //多标签
                        // portletChangeTabPanelType();
                        // changePortletTabPanelLayout(document.getElementById("portlet_tabpanel_layout"));

                        onStep2();
                    }
                });
            }
            LUI.ready(onReady);
            function onEnter(){
                var value = getPortletReturnData();
                //debugger;
                if(validation(value)){
                    window.$dialog.hide(value);
                }
            }
        </script>
        <%@ include file="/sys/portal/designer/jsp/inc/body.jsp"%>
    </template:replace>
</template:include>