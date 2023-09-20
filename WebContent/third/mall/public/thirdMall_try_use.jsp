<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Object url = request.getAttribute("url");
    //转发至体验环境
    response.sendRedirect(url.toString());
%>
