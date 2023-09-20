<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.third.intell.model.IntellConfig"%>
<%
	IntellConfig robot_intellConfigEdit = new IntellConfig();
	if("true".equalsIgnoreCase(robot_intellConfigEdit.getItEnabled()) 
			&& "true".equalsIgnoreCase(robot_intellConfigEdit.getSmartTag()) ){
		String robot_fdModuleKey = request.getParameter("modelKey");
		String robot_fdModuleName = request.getParameter("modelDesc");
		String robot_config = request.getParameter("style");
		String robot_drag = request.getParameter("drag");
		String robot_aipUrl = robot_intellConfigEdit.getItDomain();
		if (robot_aipUrl!=null && robot_aipUrl.endsWith("/")){
			robot_aipUrl = robot_aipUrl.substring(0, robot_aipUrl.length()-1);
		}
		String robot_systemId = robot_intellConfigEdit.getSystemName();
%>
<script src="<%= robot_aipUrl%>/web/labc-robot-widgets/index.min.js"></script>
<script>
  const data = {
	fdModuleKey: '<%= robot_fdModuleKey%>',
	fdModuleName: '<%= robot_fdModuleName%>', 
    fdSystemId: '<%= robot_systemId%>',
    config:'<%=robot_config %>',
    url: '<%= robot_aipUrl%>',
    drag: '<%= robot_drag%>'
  }
  aipRobot.appendRobot(data)
</script>

<%
	}
%>