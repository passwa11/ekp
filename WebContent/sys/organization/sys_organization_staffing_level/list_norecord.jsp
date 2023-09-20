<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	JSONArray prompt = (JSONObject.fromObject(SysUiPluginUtil
			.getThemes(request))).getJSONArray("prompt");
	for (int i = 0; i < prompt.size(); i++){
%>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath }/<%=prompt.get(i)%>"/>
<%
	}
%>
<div class="prompt_container" >
	<div class="prompt_frame">
		<div class="prompt_centerL">
			<div class="prompt_centerR">
				<div class="prompt_centerC">
					<div class="prompt_container clearfloat" style="height: 176px;">
						<div>
							<div class="prompt_content_error"></div>
							<div class="prompt_content_right">
								<div class="prompt_content_inside">
									<bean:message key="return.title" />
									<div class="msgtitle"><bean:message key="sys-organization:sysOrganizationStaffingLevel.noRecord"/></div>
									<span class="prompt_content_timer">
										<bean:message key="sys-organization:sysOrganizationStaffingLevel.add.msg"/>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

