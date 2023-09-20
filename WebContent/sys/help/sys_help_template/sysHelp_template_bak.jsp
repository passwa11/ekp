<%@ page import="com.landray.kmss.sys.help.forms.SysHelpModuleForm" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
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
<style>
    .template_title_block{
        text-align: center;
        margin-bottom: 10px;
        margin-top: 5px;
    }
    .template_title{
        margin: 0px;
        text-rendering: optimizeLegibility;
        -webkit-font-feature-settings: "kern";
        font-kerning: normal;
        font-size: 32px;
    }
    .template_docContent{
        width: 100%;
        height: 100%;
    }
</style>
<template:include ref="default.simple">
    <template:replace name="body">
        <div class="template_title_block">
            <p class="template_title">
                <span>${fdName}</span>
            </p>
        </div>
        <ui:tabpanel layout="sys.ui.tabpanel.light" style="width:98%;margin-left:15px;" selectedIndex="${selectedIndex}">
            <c:if test="${buzOpen==true}">
                <ui:content title="业务概览">
                    <img id="buzImg" src="${LUI_ContextPath}${fdBuzImgSrc}" style="width:100%;"/>
                </ui:content>
            </c:if>
            <c:if test="${scenesOpen==true}">
                <ui:content title="场景体验">
                    <c:import url="/sys/help/sys_help_template/sysHelp_template_scense.jsp">
                        <c:param name="scenseUrl" value="${(fdCustSceneUrl==null||fdCustSceneUrl=='')?fdSceneUrl:fdCustSceneUrl}"/>
                        <c:param name="isCustomizedUrl" value="${(fdCustSceneUrl != null || fdCustSceneUrl != '')}"/>
                    </c:import>
                </ui:content>
            </c:if>
            <c:if test="${docOpen==true}">
                <ui:content title="帮助文档">
                    <iframe id="docContent" src="" frameBorder='0' class="template_docContent"></iframe>
                </ui:content>
            </c:if>
        </ui:tabpanel>
    </template:replace>
</template:include>
<script>
    LUI.ready(function(){
        seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog){
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
        });
    });
</script>