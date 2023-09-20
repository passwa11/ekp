<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
    <template:replace name="content">
        function generateTree()
        {
        window.LKSTree = new TreeView(
        'LKSTree',
        '<bean:message bundle="sys-profile" key="sys.profile.globalWaterMark" />',
        document.getElementById('treeDiv')
        );
        var node = LKSTree.treeRoot;
        node.isExpanded = true;

        <%-- WPS集成配置--%>
        <kmss:authShow roles="SYS_PROFILE_GLOBAL_WATERMARK_SETTING">
            var	node_1_1_node = node.AppendURLChild("<bean:message bundle="sys-profile"
                                                                      key="sys.profile.globalWaterMark.config" />",
            "<c:url
                value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.profile.model.GlobalWaterMarkConfig" />");
        </kmss:authShow>

        LKSTree.Show();
        setTimeout(function(){
            LKSTree.ClickNode(node_1_1_node);
        },100);
        }
    </template:replace>
</template:include>