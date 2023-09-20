<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content" >
		<script>
			seajs.use(['lui/jquery'],function($){
				var container = $('<div/>');
				container.html(top.changeInfo).appendTo(document.body);
				container.css({
					'padding':'30px'
				});
			});	
		</script>
	</template:replace>
</template:include>