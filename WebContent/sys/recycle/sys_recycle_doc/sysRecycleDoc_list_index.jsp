<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
<template:replace name="content">
<list:criteria id="criteria1">
	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
	</list:cri-ref>
	
</list:criteria>
<%@ include file="/sys/recycle/sys_recycle_doc/sysRecycleDoc_listview.jsp" %>
	</template:replace>
</template:include>