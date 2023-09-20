<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.edition.service.ISysEditionMainService"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- 删除已部署版本机制的主文档时，需要处理的逻辑 --%>
<%-- 
	调用方法：
		在业务模块需要删除文档时，调用代码：
		1. List页面（POST请求）
			// 注意，list页面需要传入fdModelName 和 fdType=POST
			dialog.iframe('/sys/edition/import/doc_delete_iframe.jsp?fdModelName=com.landray.kmss.km.doc.model.KmDocKnowledge&fdType=POST',
					"<bean:message key='ui.dialog.operation.title' bundle='sys-ui'/>",
					function (value){
                    	// 回调方法
						if(value) {
	                        dialog.result(value);
							topic.publish("list.refresh");
						}
					},
					// 注意，list页面删除需要传入data
					{width:400,height:200,params:{url:delUrl,data:[1,2,3,4,5]}}
			);
		
		1. View页面（GET请求）
			// 注意，view页面需要传入fdModelName 和 fdModelId
			dialog.iframe('/sys/edition/import/doc_delete_iframe.jsp?fdModelName=com.landray.kmss.km.doc.model.KmDocKnowledge&fdModelId=160bb5a512ff7ed00c914b04146b036b',
					"<bean:message key='ui.dialog.operation.title' bundle='sys-ui'/>",
					function (url){
                       // 回调方法
                       if(url) {
                    	   Com_OpenWindow(url, '_self');
                       }
					},
					// 注意，view页面删除时需要加入type:'GET'，默认为'POST'
					{width:400,height:200,params:{url:delUrl,type:'GET'}}
			);
 --%>
 
<%
	// List页面应该传入此参数：fdType=POST
	boolean	isMultiVersion = "POST".equalsIgnoreCase(request.getParameter("fdType"));
	// 如果不是POST请求，则认为是view页面删除，需要判断当前文档是否有多版本
	if(!isMultiVersion) {
		ISysEditionMainService sysEditionMainService = (ISysEditionMainService)SpringBeanUtil.getBean("sysEditionMainService");
		// 判断文档是否有多版本
		// 注意：在引用本JSP文件里，需要提供当前文档的modelName和modelId
		isMultiVersion = sysEditionMainService.isMultiVersion(request);
	}
%>
 
<template:include ref="default.dialog">
	<template:replace name="content">
		<style>
			.lui_dialog_deleteTips {
				width: 100%;
				padding-bottom: 10px;
			}
			.lui_dialog_deleteTips .radioUl {
				<%
				if(isMultiVersion) {
				%>
				text-align: left;
				<% } else { %>
				text-align: center;
				margin-top: 20px;
				<% } %>
				margin-left: 10px;
			}
			.lui_dialog_deleteTips .radioUl label {
				display: block;
				margin:10px 0;
				cursor: pointer;
			}
			.lui_dialog_deleteTips .radioUl span {
				margin-left: 5px;
			}
			.lui_dialog_deleteTips .tips {
				display: none;
			}
		</style>
		
		<center>
			<script  type="text/javascript">
				seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
					$(function() {
						fdModelName = '${JsParam.fdModelName}';
						 // 如果有部署收回站，提示信息需要修改
						 if (Com_Parameter.SoftDeleteEnableModules.length > 0 
								 && Com_Parameter.SoftDeleteEnableModules.indexOf(fdModelName) > -1) {
							 <%
								if(isMultiVersion) {
							 %>
							 $("#single_span").text('<bean:message key="page.delete.recycle.single" bundle="sys-edition"/>');
							 $("#multi_span").text('<bean:message key="page.delete.recycle.multi" bundle="sys-edition"/>');
							 <% } else { %>
							 $("#delete_span").text('<bean:message key="page.comfirmSoftDelete" bundle="sys-recycle"/>');
							 <% } %>
							
							 $(".tips").show();
						}
					});
					
					window.delCallback = function(data) {
						$dialog.hide(data);
					}
					
					//确认
					window.clickOK = function() {
						var data = $dialog.content.params.data;
						var url = $dialog.content.params.url;
						var type = $dialog.content.params.type;
						<%
						if(isMultiVersion) {
						%>
						if(!url) {
							dialog.alert('<bean:message key="errors.required" arg0="URL"/>');
							return false;
						}
						
						var deleteType = $("input[name='deleteType']:checked").val();
						url += "&deleteType=" + deleteType;
						
						// 如果有fdModelId，认定是view页面的删除
						if(type && type.toUpperCase() == 'GET') {
							$dialog.hide(url);
							return false;
						}
						if(!data) {
							dialog.alert('<bean:message key="errors.required" arg0="data"/>');
							return false;
						}
						
						var config = {
								url: url,
								data: data
						};
						__Com_Delete_Ajax(config, delCallback, dialog);
						<% } else { %>
						$dialog.hide(url);
						<% } %>
					};
				});
			</script>
			
			<!-- 删除提示框 Starts -->
			<div class="lui_dialog_deleteTips">
				<div class="radioUl">
					<%
						if(isMultiVersion) {
					%>
					<label><input type="radio" name="deleteType" value="single" checked/><span id="single_span"><bean:message key="page.delete.single" bundle="sys-edition"/></span></label>
					<label><input type="radio" name="deleteType" value="Multi"/><span id="multi_span"><bean:message key="page.delete.multi" bundle="sys-edition"/></span></label>
					<% } else { %>
					<span id="delete_span"><bean:message key="page.comfirmDelete"/></span>
					<% } %>
				</div>
				<div class="tips">
					<%=com.landray.kmss.sys.recycle.util.SysRecycleUtil.getClearRecycleByDayInfo()%>
				</div>
			</div>
			<!-- 删除提示框 Ends -->

			<ui:button text="${lfn:message('button.ok') }" onclick="clickOK();"></ui:button>
			<ui:button style="padding-left:10px"  text="${lfn:message('button.cancel') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();"></ui:button>
		</center>
	</template:replace>
</template:include>
