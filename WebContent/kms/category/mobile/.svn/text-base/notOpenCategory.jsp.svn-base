<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>	
<template:include file="/third/pda/template.jsp" compatibleMode="true">
	<template:replace name="title">
		<bean:message key="return.systemInfo"/>
	</template:replace>
	<template:replace name="head">
		<style>
			.muiLoginPage{background-color: #f0eff5;}
			.msgtitle{padding: 2rem;}
			.errortitle{padding: 2rem;}
		</style>
	</template:replace>
	<template:replace name="content">
		<div class="noDataTipWrapper gray">
	        <div class="icon">
	            <i class="mui mui-noFind"></i>
	        </div>
	        <div class="errortitle">
	            <bean:message key="kms.category.notOpen.error" bundle="kms-category"/>
	        </div>
	        <a class="btnReturn" onclick="top.open('${LUI_ContextPath }/','_self')"><i class="mui mui-back"></i></i><bean:message key="home.logoTitle" /></a>
    	</div>
	</template:replace>
</template:include>