<%@page import="com.landray.kmss.sys.quartz.service.ISysQuartzJobService"%>
<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String fdJobService = request.getParameter("fdJobService");
	String fdJobMethod = request.getParameter("fdJobMethod");
	HQLInfo hql = new HQLInfo();
	hql.setSelectBlock("fdId");
	hql.setWhereBlock("fdJobService=:fdJobService and fdJobMethod=:fdJobMethod");
	hql.setParameter("fdJobService", "synchroInService");
	hql.setParameter("fdJobMethod", "synchro");
	ISysQuartzJobService sysQuartzJobService = (ISysQuartzJobService)SpringBeanUtil.getBean("sysQuartzJobService");
	List list = sysQuartzJobService.findValue(hql);
	String fdId = request.getParameter("fdId");
	if (!ArrayUtil.isEmpty(list) && list.get(0) != null) {
		fdId = list.get(0).toString();
	}
	String url = request.getContextPath()+"/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=view&fdId="+fdId;
	response.sendRedirect(url);
%>


