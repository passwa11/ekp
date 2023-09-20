<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<table width=100%>
			<tr>
				<td><list:listview>
						<ui:source type="AjaxJson">
						{"url":"/kms/medal/kms_medal_log/kmsMedalLog.do?method=listdata&fdMedalId=${param.fdMedalId}"}
					</ui:source>
						<list:colTable isDefault="true" layout="sys.ui.listview.listtable"
							cfg-norecodeLayout="simple"
							rowHref="/kms/medal/kms_medal_log/kmsMedalLog.do?method=view&fdId=!{fdId}">
							<list:col-serial></list:col-serial>
							<list:col-auto props=""></list:col-auto>
						</list:colTable>
						<ui:event topic="list.loaded">  
						   seajs.use(['lui/jquery'],function($){
								if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
									window.frameElement.style.height =  $(document.body).height() + "px";
								}
							});
						</ui:event>							
					</list:listview>
					<div style="height: 15px;"></div> 
				<list:paging layout="sys.ui.paging.simple"></list:paging>	
				</td>
			</tr>
		</table>
	</template:replace>
</template:include>
