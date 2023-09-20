<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/ui/extend/theme/default/style/common.css"></link>
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
					<div class="prompt_container clearfloat">
						<div>
							<div class="prompt_content_error"></div>
							<div class="prompt_content_right">
								<div class="prompt_content_inside">
									<div class="msgtitle">
									 <bean:message key="return.noRecord"/>
										<span class="prompt_content_timer">											
											<bean:message key="return.otherQuery.try"/>
										</span>
									</div>
									<div class="msgcontent">
										<div><bean:message key="return.noRecord.reason"/></div>
										<div class="msgtip">
											<ul>
												<li><bean:message key="return.noRecord.reason2"/></li>
												<li><bean:message bundle="sys-xform" key="sysFormMain.relevance.dontHaveCategory" /></li>
											</ul>
										</div>
									</div>	
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

