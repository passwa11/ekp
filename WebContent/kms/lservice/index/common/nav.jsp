<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.landray.kmss.kms.lservice.util.UrlsUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/lservice/index/common/style/icon/iconfont.css" />
<%@ include file="/sys/ui/jsp/common.jsp"%>

	<ui:combin ref="memu.nav.no.expand">
		<ui:varParam name="source">
			<ui:source type="Static">
				[<ui:trim>
						<%
							String type = request.getParameter("type");
							
						
							List<Map<String, String>> urls = null;
							
							if("teacher".equals(type)) {
							    urls = UrlsUtil.getTeacherUrls();
							} else if("admin".equals(type)) {
								urls = UrlsUtil.getAdminUrls();
							} else {
								urls = UrlsUtil.getStudentUrls();
							}
				
							for (Map<String, String> bank : urls) {
				
									String title = ResourceUtil.getString(bank.get("titleMessage"));
				
									String jsp = bank.get("jsp");
									
									if(!UserUtil.checkAuthentication(jsp, "GET")) {
										continue;
									}
									
									String modelName = StringUtil.isNotNull(bank.get("modelName")) ? bank.get("modelName"): "";
									
									String icon = StringUtil.isNotNull(bank.get("icon")) ?  bank.get("icon") : "";
						%>
								{
										"text" : "<%=title%>",
										"href" :  "javascript:lservice.navOpenPage('${LUI_ContextPath}<%=jsp%>')",
					  					"icon" : "<%=icon%>" + " lservice_iconfont",
					  					"selected" : <%=modelName.equals(request.getParameter("modelName"))%>
								},
						
						<%
							
							}
							if("admin".equals(type)) {
						%>
							<kmss:authShow roles="ROLE_KMSLSERVICE_BACKSTAGE_MANAGER">
								{
								  	"text" : "${ lfn:message('kms-lservice:lservice.manger') }",
									"href" :  "${LUI_ContextPath }/sys/profile/index.jsp#app/learn",
								  	"icon" : " lservice-config lservice_iconfont",
								  	"isClickSelected" : false
								 }
							</kmss:authShow>
						<% 
							}
						%>
				</ui:trim>]
			</ui:source>
		</ui:varParam>
	</ui:combin>

<%--
<div class="lui_list_nav_frame">

	<ui:accordionpanel>

		<%
			List<Map<String, String>> studentUrls = UrlsUtil.getStudentUrls();

				for (Map<String, String> bank : studentUrls) {

					String title = ResourceUtil.getString(bank.get("titleMessage"));

					String navJsp = bank.get("navJsp");
		%>

		<ui:content title="<%=title%>">


			<c:import url="<%=navJsp%>" charEncoding="utf-8"></c:import>

		</ui:content>
		<%
			}
		%>

	</ui:accordionpanel>

</div>

		<script>
		function switchRole() {
			seajs.use( ['lui/dialog'], function(dialog) {
				dialog.iframe('/kms/lservice/index/common/switch_dialog.jsp?modelName=${JsParam.modelName}&type=${JsParam.type}', 
						"${lfn:message('kms-lservice:lservice.index.role.switch') }",
						 null, 
						 {	
							width:720,
							height:300
						});
			});
		}
		</script>
 --%>