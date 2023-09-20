<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="content">
		<script type="text/javascript">
		require(["mui/device/adapter","dojo/ready"],function(adapter,ready){
			ready(function(){
				adapter.getWifiInfo(function(res){
					alert(JSON.stringify(res));
					document.write(JSON.stringify(res));
				},function(err){
					alert(JSON.stringify(err));
				});
			});
		});
		</script>
	</template:replace>
</template:include>