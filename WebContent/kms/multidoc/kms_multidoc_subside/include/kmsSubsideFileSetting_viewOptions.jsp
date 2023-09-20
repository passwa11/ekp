<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.kms.multidoc.service.IKmsMultidocSubsideService"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	String type = request.getParameter("type");
	String selected = request.getParameter("selected");
	String modelName = request.getParameter("modelName");
	String templateService = request.getParameter("templateService");
	String templateId = request.getParameter("templateId");
	IKmsMultidocSubsideService service = (IKmsMultidocSubsideService)SpringBeanUtil.getBean("kmsMultidocSubsideService");
	Map<String, String> map = service.getOptions(modelName, type, templateService, templateId);
	Iterator<String> it = map.keySet().iterator();
	while(it.hasNext()) {
		String key = it.next();
		if(key.equals(selected)) {
			out.println(map.get(key));
			break;
		}
	}
%>