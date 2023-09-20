<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="
	com.landray.kmss.util.KmssMessageWriter,
	com.landray.kmss.util.KmssReturnPage" %>
<%
	String templateName = (String)request.getAttribute("templateName");
	String title = ResourceUtil.getString(request, "lbpmSummaryApprovalConfig.listview.notHandler.message", "sys-lbpmservice-support", new Object[]{templateName});
	request.setAttribute("title", title);
%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="prompt_container">
	<div class="prompt_frame">
		<div class="prompt_centerL">
			<div class="prompt_centerR">
				<div class="prompt_centerC">
					<div class="prompt_container clearfloat">
						<div>
							<div class="prompt_content_success"></div>
							<div class="prompt_content_right">
								<div class="prompt_content_inside">
									<bean:message key="return.title"/>
									<div class="msgtitle">${title }</div>
									<br>
								</div>
								<div class="prompt_buttons clearfloat">
									<div class="prompt_buttons clearfloat">
										<div class="btnmsg_l">
											<div class="btnmsg_r">
												<input type="button" class="btnmsg" value='<bean:message key="button.close"/>' onclick="Com_CloseWindow();">
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
</div>

