<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService,com.landray.kmss.util.SpringBeanUtil,java.util.List,com.landray.kmss.sys.time.model.SysTimeLeaveRule" %>
<template:include ref="mobile.list" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="hr.staff.nav.attendance.management"/>
	</template:replace>
	<template:replace name="head">
  		<link rel="stylesheet" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/vacation.css">
	</template:replace>
	<template:replace name="content">
		<%
			ISysTimeLeaveAmountService sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil.getBean("sysTimeLeaveAmountService");
			List<SysTimeLeaveRule> leaveRuleList = sysTimeLeaveAmountService.getAllLeaveRule();
			String leaveNames = "";
			for(SysTimeLeaveRule leaveRule : leaveRuleList) {
				leaveNames += leaveRule.getFdName() + ";";
			}
			pageContext.setAttribute("leaveNames", leaveNames);
		%>	
		<div id="va-header" data-dojo-type="mui/header/Header" data-dojo-props="height:'4rem'">
			<div
				data-dojo-type="mui/nav/MobileCfgNavBar"
				data-dojo-props="lazy:false,defaultUrl:'/hr/staff/mobile/personInfo/view/nav.jsp?personInfoId=${param.personInfoId }'">
			</div>
		</div>
		<div id="scroll" data-dojo-type="mui/list/NavSwapScrollableView" >
			<ul
		    	data-dojo-type="mui/list/JsonStoreList" 
		    	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffVacationListMixin">
			</ul>
		</div>
		<script>
		<%-- require(['dojo/topic'],function(topic){
			topic.publish("get/vacation/props","<%=leaveNames%>")
		}) --%>
		</script>
	</template:replace>
</template:include>