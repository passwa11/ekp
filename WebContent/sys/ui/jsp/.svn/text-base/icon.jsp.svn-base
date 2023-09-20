<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiTools"%>
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
		<style type="text/css">
			html,body {
				height: 100%;
			}
			.flt {
				float: left;
				cursor: pointer;
				padding: 5px;
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
		List<String> icon = SysUiTools.scanIconCssName(type,Boolean.valueOf(status));
		request.setAttribute("icons", icon);
		%>
		<div class="lui_iconList">
			<div class="lui_profile_iconSelect_header">
				<!--
				<div class="lui_profile_iconSelect_category">
					<select name="iconType" id="iconType">
						<option value="sysIconList">系统图标</option>
					</select>
				</div>
				-->
				<div class="lui_profile_iconSelect_tab">
					<ul name="iconType" id="iconType">
						<li data-target="sysIconList"><bean:message bundle="sys-ui" key="ui.iconfont.system"/></li>
<%--						<c:if test="'${param.type=='m'}'">--%>
							<li data-target="sysMaterial">素材库</li>
<%--						</c:if>--%>
					</ul>
				</div>
			</div>
			<div class="lui_profile_iconSelect_content">
				<div class="lui_profile_iconSelect_list" id="sysIconList">
					<c:forEach items="${icons}" var="icon">
						<div class="one_icon flt">
							<div class="lui_icon_l">
								<div class="lui_icon_l ${icon}" style="background-color: #C78700;" title="${icon}" onclick="onIconClick(this.title);"></div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="lui_profile_material_list" id="sysMaterial">
				<c:import url="/sys/ui/jsp/icon_material_dlg.jsp"/>
			</div>
		</div>

		<script>
			function onIconClick(title){
				var type =1;
				window.$dialog.hide({
					"title": title,
					"type": type,
				});
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

				/*
				LUI.$("#iconType").change(function(){
					var target = $(this).val();
					$('.lui_profile_iconSelect_list').hide();
					$('#' + target).show();
				});
				*/

				LUI.$('#iconType').on('click', 'li', function(e){

					$('#iconType li').removeClass('status_current');
					$(this).addClass('status_current');

					var target = $(this).attr('data-target');
					if(target == "sysMaterial"){ //素材库
						$('.lui_profile_iconSelect_content').hide();
					}else{//系统的
						$('.lui_profile_material_list').hide();
						$('.lui_profile_iconSelect_content').show();
					}
					$('.lui_profile_iconSelect_list').hide();
					$('#' + target).show();

				});
				LUI.$(document).ready(function(){
					$('#iconType li:first-child').click();
				})
				$.getJSON('${LUI_ContextPath}/${themePath}json/ext.json', function(result) {
					if(result.extIcon) {

						//$('<option value="extIconList">扩展图标</option>').appendTo($('#iconType'));
						$('<li data-target="extIconList">扩展图标</li>').appendTo($('#iconType'));

						var extIconList = $('<div class="lui_profile_iconSelect_list" id="extIconList" style="display: none;"></div>').appendTo('.lui_profile_iconSelect_content');

						var datas = result.extIcon.iconList || [];
						for(var i = 0; i < datas.length; i++){
							var className = datas[i].className;

							var extIcon = $(
								'<div class="one_icon flt">' +
									'<div class="lui_icon_l">' +
										'<div class="lui_icon_l lui_icon_ext_' + className + '" style="background-color: #C78700;" title="lui_icon_ext_' + className + '" onclick="onIconClick(this.title);"></div>' +
									'</div class="lui_icon_l">' +
								'</div class="one_icon flt">'
							);

							extIcon.appendTo(extIconList);
						}

					}
				});

			});
		</script>

	</template:replace>
</template:include>