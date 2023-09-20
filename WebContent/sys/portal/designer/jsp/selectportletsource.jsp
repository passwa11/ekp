<%@page import="com.landray.kmss.sys.portal.util.SysPortalUtil.ModuleInfo"%>
<%@page import="com.landray.kmss.sys.portal.util.SysPortalUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.sys.ui.xml.model.SysUiPortlet"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("sys.ui.theme", "sky_blue");
//防止在ie8下由于缓存导致uuid重复(selectPortletSource)
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0);  
%>
<template:include ref="default.simple">
	<template:replace name="title">${ lfn:message('sys-portal:desgin.msg.addwidget') }</template:replace>
	<template:replace name="body">
	<script>
		seajs.use(['theme!form']);
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			window.preview = function(id) {
				dialog.iframe('/resource/jsp/widget.jsp?portletId=' + id + '&example=1',
						"${lfn:message('sys-portal:sys.portal.preview')}", null, {
					width : 500,
					height : 400,
					/* 新增数据预览与样例预览按钮 @author 吴进 by 20191024 */
					buttons : [{
						name : "${lfn:message('sys-portal:sys.portal.preview.example')}",
						value : 1,
						focus : true,
						fn : function(value, _dialog) {	
							if (value == 1) {
								var buttonText = "${lfn:message('sys-portal:sys.portal.preview.data')}";
								var button = _dialog.content.buttons[0];
								var buttonContainer = $(button).find(".lui_dialog_buttons_container")[0];
								$(buttonContainer).find(".lui_toolbar_btn_def").eq(0).attr("title", buttonText);
								$(buttonContainer).find(".lui_widget_btn_txt").eq(0).text(buttonText);
								
								this.value = 2;
								
								var imagesrc = $(_dialog.frame[0]).find("iframe")[0].contentWindow.document.getElementById("imagesrc").value;
								if (imagesrc!=null) {
									var path = formatsrc(imagesrc.trim());
									if (endsWith(path)) {
										$(_dialog.frame[0]).find("iframe").eq(0).attr("src", "${LUI_ContextPath}" + path);
									} else {
										$(_dialog.frame[0]).find("iframe").eq(0).attr("src", "${LUI_ContextPath}" + '/resource/jsp/widget.jsp?portletId=' + id + '&example=2');
									}
								}
							} else {
								var buttonText = "${lfn:message('sys-portal:sys.portal.preview.example')}";
								var button = _dialog.content.buttons[0];
								var buttonContainer = $(button).find(".lui_dialog_buttons_container")[0];
								$(buttonContainer).find(".lui_toolbar_btn_def").eq(0).attr("title", buttonText);
								$(buttonContainer).find(".lui_widget_btn_txt").eq(0).text(buttonText);
								
								this.value = 1;
								
								$(_dialog.frame[0]).find("iframe").eq(0).attr("src","${LUI_ContextPath}" + '/resource/jsp/widget.jsp?portletId=' + id + '&example=1');
							}
						}
					}]
				});
			}
		});
		function formatsrc(input) {
			var prefix = "{^", suffix = "$}";
			return input.replace(prefix, "").replace(suffix, "");
		}
		function endsWith(input) {
			var extension = input.substring(input.lastIndexOf('.') + 1);
			if ('png'==extension.trim() || 'jpg'==extension.trim() || 'PNG'==extension.trim() || 'JPG'==extension.trim()) {
				return true;
			} else {
				return false;
			}
		}
		</script>
	<script>
		function selectPortletSource(id,name,rid,rname,format,formats,operations){
			//debugger;
			var data = {
					"uuid":"opt_<%=IDGenerator.generateID()%>",
					"sourceId":id,
					"sourceName":name,
					"sourceFormat":format,
					"sourceFormats":formats,
					"renderId":rid,
					"renderName":rname,
					"operations":LUI.toJSON(unescape(operations))
			};
			window.$dialog.hide(data);
		}
		function buttonSearch(){
			//LUI("sourceList");
			var val = LUI.$("#moduleList").val();
			var keyword = LUI.$("#searchInput :text").val();
			seajs.use(['lui/topic'],function(topic){
				//var evt = {"a":"a"};
				var topicEvent = {
						criterions : [],
						query : []
					};
				topicEvent.query.push({"key":"__seq","value":[(new Date()).getTime()]});
				topicEvent.criterions.push({"key":"__module","value":[val]});
				topicEvent.criterions.push({"key":"__keyword","value":[keyword]});
				topic.publish("criteria.changed", topicEvent);				
			});
		}
		LUI.ready(function(){
			buttonSearch();
		});
	</script>
	<div style="margin:20px auto;width:95%;">
		<div style="border: 1px #e8e8e8 solid;padding: 5px;">
				<table class="tb_noborder" style="width: 100%">
					<tr>
						<td width="100">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.module') }</td>
						<td>
						<select id="moduleList" style="width: 200px;" onchange="buttonSearch(this.value)">
						<option value="__all">=${ lfn:message('sys-portal:sysPortalPage.desgin.msg.allmodule') }=</option>
						<%
						String sourceId = request.getParameter("sourceId");
						String moduleId = null;
						if(StringUtil.isNotNull(sourceId)){
							if(sourceId.endsWith(".source")){
								String portalId = sourceId.substring(0,sourceId.length()-7);
								try{
									moduleId = SysUiPluginUtil.getPortletById(portalId).getFdModule();
								}catch(Exception exx){
									//异常是有可能部件被删除了。
								}
							}							
						}
						
						/*
						 * 重载函数 getPortalModules(scene)
						 * 普通与匿名页面的过滤 
						 * @author 吴进 by 20191114
						 */
						String scene = request.getParameter("scene");
						List<?> modules = SysPortalUtil.getPortalModules(scene); 
						boolean isMultiServer = modules.size()>1;
						pageContext.setAttribute("isMultiServer",isMultiServer);
						if(isMultiServer){
							for(int i=0;i<modules.size();i++){
								ModuleInfo info=(ModuleInfo)modules.get(i);
								out.append("<optgroup label='"+info.getName()+"'>");
								Iterator<?> it = info.getChildren().iterator();
								while(it.hasNext()){
									ModuleInfo xxx = (ModuleInfo)it.next(); 
									if(xxx.getCode().equals(moduleId)){
										out.append("<option value='"+xxx.getCode()+"' selected>"+xxx.getName()+"</option>");
									}else{
										out.append("<option value='"+xxx.getCode()+"'>"+xxx.getName()+"</option>");
									}
								}
								out.append("</optgroup>");
							}
						}else{
							ModuleInfo info=(ModuleInfo)modules.get(0);
							Iterator<?> it = info.getChildren().iterator();
							while(it.hasNext()){
								ModuleInfo xxx = (ModuleInfo)it.next(); 
								if(xxx.getCode().equals(moduleId)){
									out.append("<option value='"+xxx.getCode()+"' selected>"+xxx.getName()+"</option>");
								}else{
									out.append("<option value='"+xxx.getCode()+"'>"+xxx.getName()+"</option>");
								}
							}
						}
						%>
						</select>
						</td>
						<td width="100">${ lfn:message('sys-portal:sysPortalPage.desgin.msg.keyword') }</td>
						<td>
						
						<div id="searchInput" data-lui-type="lui/search_box!SearchBox">
							<script type="text/config">
							{
								placeholder: "${ lfn:message('sys-portal:sysPortalPage.desgin.msg.inputq') }",
								width: '90%'
							}
							</script>
							<ui:event event="search.changed" args="evt">
								buttonSearch();
							</ui:event>
						</div>
						</td>
					</tr>
				</table>
			</div>	 
			<%-- &q.__module=<%= (moduleId == null ? "" : moduleId) %> --%>
			<div style="border: 1px #e8e8e8 solid;border-top-width: 0px;padding: 5px;height:430px;">
				<list:listview id="sourceList"  cfg-criteriaInit="true">
					<ui:source type="AjaxJson">
						{"url":"/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=selectSource&scene=${ param['scene'] }"}
					</ui:source>
					<list:colTable sort="false" layout="sys.ui.listview.listtable" onRowClick="selectPortletSource('!{fdSource}','!{fdName}','!{fdRenderId}','!{fdRenderName}','!{fdFormat}','!{fdFormats}',escape('!{operations}'))">
						<list:col-serial></list:col-serial>
						<c:if test="${isMultiServer}">
						<list:col-html title="服务器">
							{$
								{%row['fdServer']%}
							$}
						</list:col-html>
						</c:if>
						<list:col-auto props="fdName,fdModule,fdDescription"></list:col-auto>
						<list:col-html  style="width: 100px;" title="">
							{$
								<a class='com_btn_link' href="javascript:void(0)" onclick="selectPortletSource('{%row['fdSource']%}','{%row['fdName']%}','{%row['fdRenderId']%}','{%row['fdRenderName']%}','{%row['fdFormat']%}','{%row['fdFormats']%}','{%escape(row['operations'])%}')">${ lfn:message('sys-portal:sysPortalPage.msg.select') }</a>
							$}
							{$ 
				         		<a class='com_btn_link' href="javascript:preview('{%row['fdId']%}')">${ lfn:message('sys-portal:sys.portal.preview') }</a>
							$}
						</list:col-html>
					</list:colTable>
				</list:listview>
				<div style="height: 10px;"></div>
				<list:paging layout="sys.ui.paging.simple"></list:paging>
			</div>
	</div>
	</template:replace>
</template:include>