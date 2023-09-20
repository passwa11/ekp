<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" >
    <template:replace name="body">
    	<table width="100%"> 
		<tr>
			<td colspan="2">
				<!-- 列表 -->
	            <list:listview id="listview">
		                <ui:source type="AjaxJson">
		                    {url:'/sys/anonym/sys_anonym_log/sysAnonymLog.do?method=data&fdModelName=${lfn:escapeHtml(fdModelName)}&fdModelId=${lfn:escapeHtml(fdModelId) }&fdKey=${lfn:escapeHtml(fdKey) }'}
		                </ui:source>
		                <list:colTable>
		                    <list:col-serial/>
		                    <list:col-auto props="docOptTime;docOptor.fdName;fdOptInfo;nowCategory.fdName" url="" />
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
	            <!-- 翻页 -->
	            <list:paging layout="sys.ui.paging.simple" />
			</td>
		</tr>
		</table>
    </template:replace>
</template:include>