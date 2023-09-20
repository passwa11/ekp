<%@ page language="java" contentType="application/jsonp; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp"%>
<%@ page import="net.sf.json.JSON"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ page import="com.landray.kmss.common.exception.AuthenticationFailureException"%>
<%@ page import="com.landray.kmss.sys.time.service.rule.SysTimeCountService" %>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgPerson" %>

<%
	if(UserUtil.getKMSSUser(request).isAnonymous()) {
		throw new AuthenticationFailureException("auth failure!");
	}
	String s_bean = request.getParameter("s_bean");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	String userId = request.getParameter("userId");
	String flag = request.getParameter("flag");
	SysTimeCountService sysTimeCountService = null;
	if (StringUtil.isNotNull(s_bean)) {
		// bean
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session
						.getServletContext());
		sysTimeCountService = (SysTimeCountService) ctx.getBean(s_bean);
	} else {
		// java
		/* String s_name = request.getParameter("s_name");
		if (StringUtil.isNotNull(s_name)) {
			sysTimeCountService = (SysTimeCountService) com.landray.kmss.util.ClassUtils.forName(s_name)
					.newInstance();
		} */
	}
	Long startDateTime = Long.valueOf(startDate);
	Long endDateTime = Long.valueOf(endDate);
	long timeDiff = 0;
	if ("day".equals(flag)){
		timeDiff = sysTimeCountService.getWorkingDays(userId,startDateTime,endDateTime);
	}else if ("hour".equals(flag)){
		timeDiff = sysTimeCountService.getManHour(userId,startDateTime,endDateTime);
	}
	
%>
<%-- <%=jsonp.toString()%> --%>
<%=timeDiff%>