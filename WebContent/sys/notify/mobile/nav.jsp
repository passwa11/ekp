<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 
	{ 
		url : "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=doing&fdType=13", 
		text : "${ lfn:message('sys-notify:header.type.todo') }",
		isNavCount:true, 
		selected : true 
	},
	{ 
		url : "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=doing&fdType=2", 
		text : "${ lfn:message('sys-notify:header.type.view') }",
		isNavCount:true, 
	},
	{ 
		url : "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=done", 
		text : "${ lfn:message('sys-notify:sysNotifyTodo.tree.finished') }",
		isNavCount:false
	}
	<%--
	{ 
		url : "/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=listNotifyMobileApproval", 
		text : "流程审批",
		isNavCount:true
	}
	--%>
]
