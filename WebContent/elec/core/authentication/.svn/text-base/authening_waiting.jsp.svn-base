<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.landray.kmss.util.SpringBeanUtil,
                java.util.List,
                com.landray.kmss.elec.core.ElecPlugin,
                com.landray.kmss.elec.core.authentication.IAuthenticationService,
                java.util.Map,
                java.util.TreeMap"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
        //String fdCharacter = request.getPar();
    	//String authType = request.getPar();
    	//String fdModelId =;
    	//String fdModelName =;
    	IAuthenticationService service=ElecPlugin.getAuthenticationService();
    	String providerName=service.getProviderName();
    	if("".equals(providerName)){
    		
    	}
    	response.setRedirect(service.getExternalAuthPageUrl());
%>
