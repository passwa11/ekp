<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("third-weixin-work:third.weixin.work.profile.notify.messageKey") }', //根节点的名称
        document.getElementById('treeDiv')
    );
    var n1 = LKSTree.treeRoot; 
    
	n2 = n1.AppendURLChild(
		"<bean:message key="table.thirdWeixinNotifyLog" bundle="third-weixin-work" />",
		"<c:url value="/third/weixin/work/third_weixin_notify_log/list.jsp" />"
	);
	n3 = n1.AppendURLChild(
		"<bean:message key="table.thirdWeixinNotifyTaskcard" bundle="third-weixin-work" />",
		"<c:url value="/third/weixin/work/third_weixin_notify_taskcard/list.jsp" />"
	);
	n4 = n1.AppendURLChild(
		"<bean:message key="table.thirdWeixinNotifyQueErr" bundle="third-weixin-work" />",
		"<c:url value="/third/weixin/work/third_weixin_notify_que_err/list.jsp" />"
	);
	
	n1.AppendURLChild(
		"${lfn:message('third-weixin-work:table.thirdWeixinAuthLog')}",
		"<c:url value="/third/weixin/work/third_weixin_auth_log/list.jsp" />"
	);
    
    LKSTree.Show();
}
</template:replace>
</template:include>