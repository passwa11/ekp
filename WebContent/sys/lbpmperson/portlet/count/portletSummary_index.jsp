<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple">
	<template:replace name="body">
	<div style="width:100%;">
		 	<c:import url="/sys/lbpmperson/portlet/count/${requestScope['summary']}.jsp" charEncoding="UTF-8"></c:import>
	  </div> 
	</template:replace>
</template:include>