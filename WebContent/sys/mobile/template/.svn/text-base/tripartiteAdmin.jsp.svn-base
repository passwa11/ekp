<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>

<%
// 如果开启三员管理，禁用移动端
if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
%>

<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.message">
	<template:replace name="body">
		<div class="prompt_frame">
			<div class="prompt_centerL">
				<div class="prompt_centerR">
					<div class="prompt_centerC">
						<div class="prompt_container clearfloat">
							<div>
								<div class="prompt_content_error"></div>
								<div class="prompt_content_right">
									<div class="prompt_content_inside">
										<bean:message key="return.title" />
										<div class="msgtitle"><%=ResourceUtil.getString(request,"global.pageNotFound",null) %> </div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</template:replace>
</template:include>

<%
	return;
}
%>
 --%>