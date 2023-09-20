<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>	
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.third.pda.util.PdaFlagUtil"%>

<template:include file="/third/pda/template.jsp" compatibleMode="true">
	<template:replace name="title">
		<bean:message key="return.systemInfo"/>
	</template:replace>
	<template:replace name="head">
		<style>
			.muiLoginPage{background-color: #f0eff5;}
		</style>
	</template:replace>
	<template:replace name="content">
			<div class="noPowerTipWrapper noPdaBrowse gray">
		        <div class="icon">
		            <i class="mui mui-noPdaBrowse"></i>
		        </div>
		        <p class="tips"></i><bean:message key="global.pda.visit"  bundle="third-pda"/></p>
		        <a class="btnReturn" onclick="redirectTo()"><i class="mui mui-back"></i></i><bean:message key="phone.view.back"  bundle="third-pda"/></a>
	    	</div>
	    
			
		</template:replace>
</template:include>
		<script type="text/javascript">
			function redirectTo(){
				history.back();
			}
		</script>