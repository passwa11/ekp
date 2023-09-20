<%@ page language="java" pageEncoding="UTF-8"
	import="com.landray.kmss.sys.notify.service.ISysNotifyCategoryService,com.landray.kmss.util.SpringBeanUtil"%>
<% 
	//提前获取业务聚合分类
	ISysNotifyCategoryService sysNotifyCategoryService = (ISysNotifyCategoryService) SpringBeanUtil
		.getBean("sysNotifyCategoryService");
	java.util.List cate = sysNotifyCategoryService.getCategorys();
	request.setAttribute("cateList",cate);

%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="person.home">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-notify:table.sysNotifyTodo') }"></c:out>
	</template:replace>
	<template:replace name="content">
	   <ui:tabpanel layout="sys.ui.tabpanel.list" id="tabpanel">
	 	  <ui:content 
		 		title="${lfn:message('sys-lbpmperson:lbpmperson.submitprocess') }">
		 	  <ui:iframe   src="${LUI_ContextPath }/sys/lbpmperson/person_flow_creator/creator_index_person.jsp"></ui:iframe>
		  </ui:content>
		   <ui:content 
		 		title="${ lfn:message('sys-lbpmperson:lbpmperson.approvaling') }">
		 	  <ui:iframe   src="${LUI_ContextPath }/sys/lbpmperson/person_flow_approval/approval_index_person.jsp"></ui:iframe>
		  </ui:content>
		  <ui:content 
		 		title="${ lfn:message('sys-lbpmperson:lbpmperson.approvaled') }">
		 	  <ui:iframe   src="${LUI_ContextPath }/sys/lbpmperson/person_flow_approved/approved_index_person.jsp"></ui:iframe>
		  </ui:content>	
		  <ui:content 
		 		title="${ lfn:message('sys-lbpmperson:lbpmperson.myctracked') }">
		 	  <ui:iframe   src="${LUI_ContextPath }/sys/lbpmperson/person_flow_track/track_index_person.jsp"></ui:iframe>
		  </ui:content>	
		 	
		</ui:tabpanel>
	</template:replace>
</template:include>
