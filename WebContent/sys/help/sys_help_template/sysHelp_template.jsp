<%@ page import="com.landray.kmss.sys.help.forms.SysHelpModuleForm" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.landray.kmss.sys.ui.xml.model.SysUiTheme" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%--<link href="${LUI_ContextPath}/sys/help/sys_help_template/css/sysHelp_template.css" rel="stylesheet">--%>
<link href="${LUI_ContextPath}/sys/help/sys_help_template/css/custom.css" rel="stylesheet">
<%
    //固定当前页面主题样式
    String fdId = "default";
    SysUiTheme theme = SysUiPluginUtil.getThemeById(fdId);
    if (theme != null) {
        request.setAttribute("sys.ui.theme", theme.getFdId());
        request.setAttribute("theme", theme);
    }
    String src = request.getParameter("url");
    pageContext.setAttribute("fdUrl", src);

    pageContext.setAttribute("selectedIndex", request.getParameter("index"));

    Object obj = request.getAttribute("moduleConfig");
    if (obj != null) {
        SysHelpModuleForm moduleConfig = (SysHelpModuleForm)obj;
        String root = "/resource/help";
        String fdBuzImgSrc = moduleConfig.getFdBuzImgSrc();
        if (StringUtils.isNotEmpty(fdBuzImgSrc)) {
            if (!fdBuzImgSrc.startsWith("/")) {
                root += "/";
            }
            fdBuzImgSrc = root + fdBuzImgSrc;
        }
        pageContext.setAttribute("docOpen", moduleConfig.getFdIsOpen());
        pageContext.setAttribute("buzOpen", moduleConfig.getFdBuzImgIsOpen());
        pageContext.setAttribute("scenesOpen", moduleConfig.getFdSceneIsOpen());
        pageContext.setAttribute("fdBuzImgSrc", fdBuzImgSrc);
        pageContext.setAttribute("fdSceneUrl", moduleConfig.getFdSceneUrl());
        pageContext.setAttribute("fdCustSceneUrl", moduleConfig.getFdCustSceneUrl());
        pageContext.setAttribute("fdName",moduleConfig.getFdName());
    }
%>
<template:include ref="default.simple">
    <template:replace name="head">

    </template:replace>
    <template:replace name="body">
        <div class="lui-help-iframe">
            <div class="lui-help-tab">
                <span class="lui-help-tab-title">${fdName}</span>
                <ul>
                    <c:if test="${buzOpen==true}">
                        <li id="helpTab0" class="lui-help-tab-li active" onclick="showTabPanel(0)"><bean:message bundle='sys-help' key='sysHelpModule.buzOverview' /></li>
                    </c:if>
                    <c:if test="${scenesOpen==true}">
                        <li id="helpTab1" class="lui-help-tab-li" onclick="showTabPanel(1)"><bean:message bundle='sys-help' key='sysHelpModule.sceneExp' /></li>
                    </c:if>
                    <c:if test="${docOpen==true}">
                        <li id="helpTab2" class="lui-help-tab-li" onclick="showTabPanel(2)"><bean:message bundle='sys-help' key='sysHelpModule.helpManual' /></li>
                    </c:if>
                </ul>
            </div>
            <div class="lui-help-content">
                <div class="lui-help-item active" id="switch0">
                    <c:if test="${buzOpen==true}">
                        <img id="buzImg" src="${LUI_ContextPath}${fdBuzImgSrc}" style="width:75%;"/>
                    </c:if>
                </div>
                <div class="lui-help-item" id="switch1">
                    <c:if test="${scenesOpen==true}">
                        <c:import url="/sys/help/sys_help_template/sysHelp_template_scense.jsp">
                            <c:param name="scenseUrl" value="${(fdCustSceneUrl==null||fdCustSceneUrl=='')?fdSceneUrl:fdCustSceneUrl}"/>
                            <c:param name="isCustomizedUrl" value="${(fdCustSceneUrl != null || fdCustSceneUrl != '')}"/>
                        </c:import>
                    </c:if>
                </div>
                <div class="lui-help-item" id="switch2">
                    <c:if test="${docOpen==true}">
                        <iframe id="docContent" src="" frameBorder='0' class="template_docContent"></iframe>
                    </c:if>
                </div>
            </div>
        </div>
    </template:replace>
</template:include>
<script>
    LUI.ready(function(){
        seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog){
            var  selectedIndex ="${selectedIndex}";
            show(selectedIndex);
            function show(selectedIndex){
                $(".lui-help-tab-li").removeClass("active");
                $(".lui-help-item").removeClass("active");
                $("#switch"+selectedIndex).addClass("active");
                $("#helpTab"+selectedIndex).addClass("active");
            }
            window.showTabPanel=function(idx){
                show(idx);
            }
            if('${docOpen==true}' == 'true'){
                var url = '${LUI_ContextPath}/sys/help/sys_help_config/sysHelpConfig.do?method=findConfigByUrl';
                $.ajax({
                    url: url,
                    type: 'post',
                    dataType: 'json',
                    data: {
                        'fdUrl': '${fdUrl}'
                    },
                    success: function(data) {
                        if(data.message == 'noData'){

                        }else if(data.message == 'more'){

                        }else{
                            $("#docContent").attr('src','${LUI_ContextPath}/sys/help/sys_help_main/sysHelpMain.do?method=findHTMLById&fdId='+data.mainId+'&configId='+data.configId);
                        }
                    }
                });
            }
        });
    });
</script>
