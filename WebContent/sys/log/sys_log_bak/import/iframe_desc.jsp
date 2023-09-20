<%@page import="com.landray.kmss.sys.log.util.LogBakConstant"%>
<%@page import="com.landray.kmss.sys.log.model.SysLogBakDetail"%>
<%@page import="com.landray.kmss.sys.log.model.SysLogBak"%>
<%@page import="com.landray.kmss.sys.log.service.ISysLogBakService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%
	String fdId = request.getParameter("fdId");
	if(fdId != null){
		ISysLogBakService service = (ISysLogBakService)SpringBeanUtil.getBean("sysLogBakService");
		SysLogBak bak = (SysLogBak)service.findByPrimaryKey(fdId);
		if(bak!=null){
			request.setAttribute("desc", bak.getFdDesc());
		}
	}
%>

<template:include ref="default.dialog">
	<template:replace name="content">
		<center>
			<script  type="text/javascript">
				seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
					//确认
					window.clickOK = function() {
						var data = $dialog.content.params.data;
						var url = $dialog.content.params.url;
						if(!url) {
							dialog.alert('<bean:message key="errors.required" arg0="URL"/>');
							return false;
						}

						var desc = $("textarea[name='desc']").val();
						if(!desc){
							desc = "";
						}
						data += "&fdDesc=" + desc;
						
						var config = {
								url: url,
								data: data
						};
						__Com_Delete_Ajax(config, callback, dialog);
					};
					
					window.callback = function(data) {
						$dialog.hide(data);
					}
				});
				
			</script>
			
			<!-- 提示框 Starts -->
			<div>
			<br/>
               	<xform:textarea value="${desc }" property="desc" htmlElementProperties="placeholder='${lfn:message('sys-log:sysLogBak.fdDesc')}'" style="width:95%;height:80%" showStatus="edit"/><br/>
			</div>
			<!-- 提示框 Ends -->
			<ui:button onclick="clickOK();" text="${lfn:message('button.ok')}" />
			<ui:button onclick="Com_CloseWindow();" text="${lfn:message('button.close')}" />
		</center>
		<style>
			span{
			    padding: 8px;
				word-break: break-word;
			}
		</style>
	</template:replace>
</template:include>
