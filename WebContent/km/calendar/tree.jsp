<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree(){
    
    <%-- 时间管理 --%>
	LKSTree = new TreeView("LKSTree", "<bean:message key="module.km.calendar" bundle="km-calendar"/>", document.getElementById("treeDiv"));
	var n1, n2, n3, n4, n5, defaultNode, n6;
	n1 = LKSTree.treeRoot;
		
	<kmss:authShow roles="ROLE_KMCALENDAR_BASE_CONFIG">
	    <%-- 模块设置 --%>
		n2 = n1.AppendURLChild("<bean:message  bundle="km-calendar" key="km.calendar.tree.module.set"/>");
		n2.isExpanded = true;
		<%-- 保存期限 、默认时间设置--%>
	    n2.AppendURLChild(
			"<bean:message  bundle="km-calendar" key="km.calendar.tree.delete.calendar.set"/>"
			,"<c:url value="/km/calendar/km_calendar_base_config/kmCalendarBaseConfig.do?method=edit&type=day" />"
		);
		<%-- 管理机制标签 --%>
		n2.AppendURLChild(
			"<bean:message  bundle="km-calendar" key="km.calendar.tree.calendar.label.config"/>"
			,"<c:url value="/km/calendar/km_calendar_agenda_label/index.jsp" />"
		);
		
		<%-- 共享设置管理 --%>
		n3 = n1.AppendURLChild(
			"<bean:message  bundle="km-calendar" key="km.calendar.tree.share.set"/>"
		);
		n3.isExpanded = true;	
		<%-- 个人共享设置 --%>	
		defaultNode = n3.AppendURLChild("<bean:message  bundle="km-calendar" key="kmCalendar.setting.authSetting"/>"
			,"<c:url value="/km/calendar/km_calendar_auth/index.jsp" />"
		);
		
		n3.AppendURLChild("<bean:message bundle="km-calendar" key="kmCalendarBaseConfig.auth.set"/>"
			,"<c:url value="/km/calendar/km_calendar_base_config/kmCalendarBaseConfig.do?method=editAuthConfig" />"
		);
		
		n6 = n1.AppendURLChild("<bean:message bundle="km-calendar" key="table.kmCalendarPersonGroup"/>","<c:url value="/km/calendar/km_calendar_person_group/index.jsp" />");
		
		n4 = n1.AppendURLChild(
			"<bean:message key="kmCalendarBaseConfig.synchro.setting" bundle="km-calendar"/>"
			,"<c:url value="/km/calendar/km_calendar_base_config/kmCalendarBaseConfig.do?method=editSynchroThreadSize" />"
		);
		
		n5 = n1.AppendURLChild(
			"<bean:message key="kmCalendarBaseConfig.timeParameter.setting" bundle="km-calendar"/>"
			,"<c:url value="/km/calendar/km_calendar_base_config/kmCalendarBaseConfig.do?method=editTimeParameter" />"
		);
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
 </template:replace>
</template:include>
