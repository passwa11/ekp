<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.persistence.AccessManager" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="java.util.*" %>
<%
	String _fdId = request.getParameter("fdId");
	AccessManager accessManager = (AccessManager) SpringBeanUtil
			.getBean("accessManager");
	Map<String, Object> _params = new HashMap<String, Object>();
	_params.put("processId", _fdId);
	int _count = accessManager
						.findCount(
								"select count(*) from LbpmAuditNote lan where lan.fdProcess.fdId = :processId",
								_params);
	request.setAttribute("auditNoteCount",_count);
%>
