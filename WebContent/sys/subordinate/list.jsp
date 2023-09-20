<%@page import="com.landray.kmss.sys.subordinate.plugin.AbstractSubordinateProvider"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.subordinate.plugin.PluginUtil"%>
<%@ page import="com.landray.kmss.sys.subordinate.plugin.PluginItem"%>
<%@ page import="com.landray.kmss.sys.subordinate.plugin.PropertyItem"%>
<%@ page import="com.landray.kmss.sys.subordinate.util.SubordinateUtil"%>
<%@ page import="com.landray.kmss.sys.subordinate.model.SysSubordinateModel"%>
<%@page import="com.landray.kmss.sys.subordinate.service.ISysSubordinateMappingService"%>

<%	
	String moduleMessageKey = request.getParameter("moduleMessageKey");
	String orgId = request.getParameter("orgId");
	List<SysSubordinateModel> modelList = SubordinateUtil.getInstance().getModelByModuleAndUser(moduleMessageKey);
	Map<String, PluginItem> modelMaps = PluginUtil.getConfigMap();
%>

<template:include ref="config.profile.list">
	<template:replace name="content">
	<%
		if(modelList.size() > 1) {
	%>
		<ui:tabpanel>
		<% 	for(SysSubordinateModel temp : modelList) {
				PluginItem model = modelMaps.get(temp.getModelName());
				String modelName = model.getModelName().substring(model.getModelName().lastIndexOf('.') + 1);
				modelName = modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
				String indexPath = model.getIndexPath() + "?orgId=" + orgId;
				AbstractSubordinateProvider provider = model.getProvider();
				List<PropertyItem> propertyItems = provider.items();
				if(propertyItems.size() > 1) {
					for(PropertyItem propertyItem : propertyItems) {
						request.setAttribute("propertyItem", propertyItem);
		%>
					<ui:content id="${propertyItem.item}Content" title='${propertyItem.itemMessageKey}'>
						<c:import url="<%=indexPath%>" charEncoding="UTF-8"></c:import>
			 		</ui:content>
		<%
					}
				} else {
		%>
			<ui:content id="<%=modelName%>" title='<%=ResourceUtil.getString(model.getMessageKey())%>'>
				<c:import url="<%=indexPath%>" charEncoding="UTF-8"></c:import>
	 		</ui:content>
		<%
				}
			}
		%>
		</ui:tabpanel>
	<%
		} else {
			PluginItem model = modelMaps.get(modelList.get(0).getModelName());
			String indexPath = model.getIndexPath() + "?orgId=" + orgId;
	%>
		<c:import url="<%=indexPath%>" charEncoding="UTF-8"></c:import>
	<%
		}
	%>
	</template:replace>
</template:include>
