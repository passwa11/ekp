<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.organization.service.spring.SysOrgMatrixMainDataService"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content"> 
		<%
 			String matrixId = request.getParameter("matrixId");
 			String fieldName = request.getParameter("fieldName");
 			String mulSelect = request.getParameter("mulSelect");
 			JSONArray array = ((SysOrgMatrixMainDataService) SpringBeanUtil.getBean("sysOrgMatrixMainDataService")).getCriteriaByMainData(matrixId, fieldName);
			// 获取主数据配置的搜索项
			if(array != null && !array.isEmpty()) {
		%>
			<!-- 筛选器 -->
			<list:criteria id="criteria1" multi="false">
		<%
			for(int i=0; i<array.size(); i++) {
				JSONObject search = array.getJSONObject(i);
				String type = search.getString("type");
				String name = search.getString("name");
				String label = search.getString("label");
		%>
				<list:cri-ref ref="<%=type%>" key="<%=name%>" title="<%=label%>" />
		<%
			}
		%>
			</list:criteria>
		<%
			}
			
 		%>
		<%--数据显示--%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=mainData&fdId=${JsParam.matrixId}&fieldName=${JsParam.fieldName}'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" onRowClick="rowClick('!{fdId}');" name="columntable">
				<%
					if("true".equals(mulSelect)){
				%>
				<list:col-checkbox></list:col-checkbox>
				<% 	
					}
					else{
				%>
				<list:col-radio></list:col-radio>
				<% 
				}
				%>
				<list:col-serial></list:col-serial>	
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				mainDataInit();
			</ui:event>
		</list:listview>
		<list:paging></list:paging>
		
		<script type="text/javascript">
			seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
				// 加载已选择的数据
				var selected = "${JsParam.selected}";
				// 列表加载完成
				window.mainDataInit = function() {
					$.each($("input[name='List_Selected']"), function(i, n) {
						if($(n).val() == selected) {
							$(n).attr("checked", true);
						}
						if(i % 2 == 1) {
							$(n).parents("tr").css({"background" : "#f3f3f3"});
						}
					});
				}
				window.rowClick = function(id) {
					$("input[value='" + id + "']").click();
				}
			});
		</script>
	</template:replace>
</template:include>