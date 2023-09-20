<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">	
	 	<list:listview >
			<ui:source type="AjaxJson">
				{"url":"/sys/news/sys_news_main/sysNewsMain.do?method=viewAllPublish&fdModelNameParam=${ JsParam['fdModelNameParam'] }&fdModelIdParam=${ JsParam['fdModelIdParam'] }&fdKeyParam=${ JsParam['fdKeyParam'] }"}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.listtable" cfg-norecodeLayout="${param.norecodeLayout !=null and param.norecodeLayout != ''?param.norecodeLayout:'simple'}">
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				 seajs.use(['lui/jquery'],function($){
						try {
							if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
								window.frameElement.style.height = document.getElementsByTagName('div')[0].offsetHeight + 40 +"px";
							}
						} catch(e) {
						}
				   });
			</ui:event>
		</list:listview> 
	</template:replace>
</template:include>