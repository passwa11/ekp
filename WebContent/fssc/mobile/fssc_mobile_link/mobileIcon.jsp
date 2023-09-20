<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiTools"%>
<%@page import="com.landray.kmss.fssc.mobile.util.FsscMobileUiTools"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	request.setAttribute("themePath", SysUiPluginUtil.getThemePath(request));
%>

<template:include ref="default.simple">
	<template:replace name="title">选择Icon</template:replace>
	<template:replace name="head">
		<template:super/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/help/font/css/iconfont.css?s_cache=${LUI_Cache }"/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }"/>
		<style type="text/css">
			html,body {
				height: 100%;
			}
			.flt {
				float: left;
				cursor: pointer;
				padding: 5px;
			}
			.icon_bussinessTrip { 
				background-image:url(../resource/images/bussinessTrip.png)!important; 
				width: 48px;
			  	height: 48px;
			  	background-size: 100% 100%;
			}
			.lui_profile_iconSelect_content img{
				width:48px;
				height:48px;
			}
		</style>

	</template:replace>
	<template:replace name="body">
		<%
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		if(StringUtil.isNull(type)){
			type = "l";
		}
		if(StringUtil.isNull(status)){
			status = "true";
		}
		List<String> icon = FsscMobileUiTools.scanIconCssName(type,Boolean.valueOf(status));
		request.setAttribute("icons", icon);
		%> 
		<div class="lui_iconList">
			<div class="lui_profile_iconSelect_header">
				<div class="lui_profile_iconSelect_tab">
					<ul name="iconType" id="iconType">
						<li class="status_current" data-target="sysIconList"><bean:message bundle="sys-ui" key="ui.iconfont.system"/></li>
					</ul>
				</div>
			</div>
			<div class="lui_profile_iconSelect_content">
				<div class="lui_profile_iconSelect_list" id="sysIconList">
					<c:forEach items="${icons}" var="icon">
						<div class="one_icon flt" onclick="onIconClick('${icon}.png')">
							<div class="lui_icon_l">
								<img src="${LUI_ContextPath }/fssc/mobile/resource/images/icon/${icon}.png">
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		
		<script>
			function onIconClick(title){
				window.$dialog.hide(title);
			}
			
			LUI.ready(function(){
				LUI.$(".lui_icon_l").hover(
					function(){
						LUI.$(this).addClass("lui_icon_on");
					},
					function(){
						LUI.$(this).removeClass("lui_icon_on");						
					}
				);
				
				LUI.$('#iconType').on('click', 'li', function(e){
					
					$('#iconType li').removeClass('status_current');
					$(this).addClass('status_current');
					
					var target = $(this).attr('data-target');
					$('.lui_profile_iconSelect_list').hide();
					$('#' + target).show();
					
				});
				
				
			});
		</script>
		
	</template:replace>
</template:include>
