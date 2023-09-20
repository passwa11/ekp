<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<style>
			.lui_timearea_schedule{display: inline-block;margin-top: 10px;}
			.lui_timearea_btnBox {color:#fff;background:#3C4153;height:100px;padding: 10px;}
			.lui_timearea_btnBox .title{font-size:18px;padding:20px;}
			.lui_timearea_btnBox .desc{font-size:12px;}
			.lui_timearea_bottom{padding:10px;border: 1px solid #d5d5d5;}
		</style>
	</template:replace>
	<template:replace name="title">
	</template:replace>
	<%--内容区--%>
	<template:replace name="content">
		<center>
		<div class="lui_timearea_schedule lui_timearea_batch">
			<div class="lui_timearea_btnBox">
				<div class="title">${lfn:message('sys-time:sysTimeArea.select.batch') }</div>
				<div class="desc">${lfn:message('sys-time:sysTimeArea.select.batch.desc') }</div>
			</div>
			<div class="lui_timearea_bottom">
				<ui:button text="${lfn:message('button.select')}" order="2" onclick="doSelectCalendar(1);"></ui:button>
			</div>
		</div>
		
		<div class="lui_timearea_schedule lui_timearea_person">
			<div class="lui_timearea_btnBox">
				<div class="title">${lfn:message('sys-time:sysTimeArea.select.person') }</div>
				<div class="desc">${lfn:message('sys-time:sysTimeArea.select.person.desc') }</div>
			</div>
			<div class="lui_timearea_bottom">
				<ui:button text="${lfn:message('button.select')}" order="2" onclick="doSelectCalendar(2);"></ui:button>
			</div>
		</div>
		</center>
		<script>
		window.doSelectCalendar = function(value){
			window.$dialog.hide({value:value});
		}
			
		</script>
	</template:replace>
</template:include>