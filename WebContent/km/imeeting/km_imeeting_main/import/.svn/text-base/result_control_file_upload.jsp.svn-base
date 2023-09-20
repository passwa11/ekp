<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" rwd="true">
	<template:replace name="body">
		<list:criteria id="criteria1" expand="false">
			<list:cri-ref key="fdPersonName" ref="criterion.sys.docSubject" title="参会人名字"/>
		</list:criteria>
		
		<div>
			<list:listview id="listview">
				<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=getControlResult&fdMainId=${JsParam.fdId}&fdType=4'}
				</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.columntable" name="columntable">
					<list:col-auto props="name;padIMEI;start;end;success;msg"></list:col-auto>
				</list:colTable>
				<ui:event topic="list.loaded">  
			   seajs.use(['lui/jquery'],function($){
					if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
						if($(document.body).height() > 0){
							window.frameElement.style.height =  $(document.body).height() +10+ "px";
						}
					}
				});
			</ui:event>
			</list:listview>
		</div>
					 
	</template:replace>
</template:include>