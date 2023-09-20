<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<style type="text/css">
			.fail_icon_div {
				background: url(images/icon_examine.png) no-repeat center;
	    		width: 48px;
	    		height: 48px;
			}
			
			.fail_msg_div {
				font-size:14px;
				margin-bottom: 10px;
			}
			
			.fail_link_div a {
				font-size:14px;
				color:blue;
			}
		</style>
		<script type="text/javascript">
			function toRetry(){
				window.parent.location.href = "<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=validateUser";
			}
		</script>
		
		<center>
			<div class="fail_icon_div"></div>
			<div class="fail_msg_div">
				<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.fail.msg" />
			</div>
			<div class="fail_link_div">
				<a href="javascript:void(0)" onclick="toRetry();">
					<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.fail.link" />
				</a>
			</div>
		</center>
	</template:replace>
</template:include>