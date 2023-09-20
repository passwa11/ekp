<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("third-weixin:module.third.weixin") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    
    node.isExpanded = true;

    /*会话内容*/
    <kmss:auth requestURL="/third/weixin/third_weixin_chat_data_main/list.jsp" requestMethod="GET">
    var node_1_3_node = node.AppendURLChild(
        '${ lfn:message("third-weixin:table.thirdWeixinChatDataMain") }',
        '<c:url value="/third/weixin/third_weixin_chat_data_main/list.jsp"/>');
    </kmss:auth> 
    /*会话内容备份*/
    <kmss:auth requestURL="/third/weixin/third_weixin_chat_data_bak/list.jsp" requestMethod="GET">
    var node_1_4_node = node.AppendURLChild(
        '${ lfn:message("third-weixin:table.thirdWeixinChatDataBak") }',
        '<c:url value="/third/weixin/third_weixin_chat_data_bak/list.jsp"/>');
    </kmss:auth> 
    /*会话同步临时表*/
    <kmss:auth requestURL="/third/weixin/third_weixin_chat_data_temp/list.jsp" requestMethod="GET">
    var node_1_5_node = node.AppendURLChild(
        '${ lfn:message("third-weixin:table.thirdWeixinChatDataTemp") }',
        '<c:url value="/third/weixin/third_weixin_chat_data_temp/list.jsp"/>');
    </kmss:auth> 
    /*会话分组*/
    <kmss:auth requestURL="/third/weixin/third_weixin_chat_group/list.jsp" requestMethod="GET">
    var node_1_6_node = node.AppendURLChild(
        '${ lfn:message("third-weixin:table.thirdWeixinChatGroup") }',
        '<c:url value="/third/weixin/third_weixin_chat_group/list.jsp"/>');
    </kmss:auth> 
    /*企业微信账号*/
    <kmss:auth requestURL="/third/weixin/third_weixin_account/list.jsp" requestMethod="GET">
    var node_1_7_node = node.AppendURLChild(
        '${ lfn:message("third-weixin:table.thirdWeixinAccount") }',
        '<c:url value="/third/weixin/third_weixin_account/list.jsp"/>');
    </kmss:auth> 
    /*内部群信息*/
    <kmss:auth requestURL="/third/weixin/third_weixin_group_chat/list.jsp" requestMethod="GET">
    var node_1_8_node = node.AppendURLChild(
        '${ lfn:message("third-weixin:table.thirdWeixinGroupChat") }',
        '<c:url value="/third/weixin/third_weixin_group_chat/list.jsp"/>');
    </kmss:auth> 

    /*群聊会话*/
    <kmss:auth requestURL="/third/weixin/third_weixin_appchat/v.jsp" requestMethod="GET">
    var node_1_10_node = node.AppendURLChild(
        '${ lfn:message("third-weixin:table.thirdWeixinAppchat") }',
        '<c:url value="/third/weixin/third_weixin_appchat/list.jsp"/>');
    </kmss:auth> 
    
    LKSTree.Show();
}
</template:replace>
</template:include>