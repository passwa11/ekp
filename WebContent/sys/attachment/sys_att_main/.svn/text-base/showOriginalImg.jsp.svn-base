<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>      
<%@ page import="com.landray.kmss.sys.attachment.plugin.customPage.util.SysAttCustomPageUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%
 //跳转自定义附件页面拓展点实现
 String actionForwardUrl = SysAttCustomPageUtil.getCustomPage(request);
 if(StringUtil.isNotNull(actionForwardUrl)) {
  //转发自定义附件页面
  request.setAttribute("iframeSrc","/sys/attachment/sys_att_main/showOriginalImg.jsp"+"?fdId="+request.getParameter("fdId"));
  request.getRequestDispatcher(actionForwardUrl).forward(request, response);
 }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
</head>
<body>
 
<table  align='center' width="100%" border=0 >
<tr><td align='center'>
<img src='<%=request.getContextPath()%>/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&amp;fdId=<%=StringEscapeUtils.escapeHtml(request.getParameter("fdId"))%>'  align='center' border='0'  >   
 </td>
 </tr>
 <tr>
 <td  align='center'>
 <input type="button" onclick="javascript:window.close();window.parent.close();" value="关闭"></input>
 </td></tr>
 </table>
</body>
</html>