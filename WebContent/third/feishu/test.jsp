<%@page import="com.landray.kmss.third.feishu.service.IThirdFeishuService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%

IThirdFeishuService thirdFeishuService = (IThirdFeishuService)SpringBeanUtil.getBean("thirdFeishuService");
thirdFeishuService.getUsers("od-a4149b2c5eefeb5e07277846612f3b41", true);

thirdFeishuService.updatePersonDepartment("b5ba1d57", "0");

%>