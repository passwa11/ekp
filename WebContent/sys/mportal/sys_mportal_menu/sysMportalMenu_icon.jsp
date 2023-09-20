<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
	<style>
		@font-face {
			font-family: 'FontMui';
			src: url("<%=request.getContextPath()%>/sys/mobile/css/font/fontmui.woff");
			src: url('<%=request.getContextPath()%>/sys/mobile/css/font/fontmui.eot?#iefix');
		}
	</style>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/font-mui.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mportal/sys_mportal_menu/css/icon.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mportal/sys_mportal_menu/css/iconList.css"></link>

		<ui:tabpanel>
		    <c:if test="${empty param.iconTypeRange or fn:contains(param.iconTypeRange,'1')==true}">
				<!-- 图片 -->
				<ui:content title="${lfn:message('sys-mportal:sysMportalImgSource.img')}">
					<div class="imageIconsContainer container">
						<c:forEach items="${imgClass }" var="imgC">
							<div class="mui ${imgC} mui-quick-entry-size" claz="${imgC}" ></div>
						</c:forEach>
					</div>
				</ui:content>
			</c:if>
			
			<c:if test="${empty param.iconTypeRange or fn:contains(param.iconTypeRange,'2')==true}">
	            <!-- 字体图标 -->
				<ui:content title="${lfn:message('sys-mportal:sysMportalMenuItem.fdIcon')}">
					<div class="fontIconsContainer container">
						<c:forEach items="${clazs }" var="claz">
							<div class="mui ${claz }" claz="${claz}"></div>
						</c:forEach>
					</div>
				</ui:content>
			</c:if>
            
            <c:if test="${empty param.iconTypeRange or fn:contains(param.iconTypeRange,'3')==true}">
	            <!-- 文字 -->
				<ui:content title="${lfn:message('sys-mportal:sysMportalMenuItem.character') }">
					<table class="tb_normal" style="width: 100%" id="character">
						<tr>
							<td class="td_normal_title" width="25%">${lfn:message("sys-mportal:sysMportalMenuItem.character.custom") }</td>
							<td width="75%"><input class="inputsgl" id="characterInput" style="width: 90%" validate="required characterInput()"></td>
							<td><ui:button text="${lfn:message('button.ok') }" onclick="selectIcon()"></ui:button></td>
						</tr>
	
					</table>
				</ui:content>
			</c:if>
			
		    <c:if test="${empty param.iconTypeRange or fn:contains(param.iconTypeRange,'4')==true}">
				<!-- 图片 -->
				<ui:content title="${lfn:message('sys-mportal:sysMportalImgSource.img')}">
					<div class="imageIconsContainer container">
						<c:forEach items="${iconClass}" var="imgD">
							<div class="mui ${imgD} mui-userConference-list" claz="${imgD}" ></div>
						</c:forEach>
					</div>
				</ui:content>
    
			</c:if>
			<!-- 素材库 -->
			<ui:content title="素材库">
				<div class="selectedImg">
					<div class="lui_profile_iconSelect_list">
						<c:import url="/sys/mportal/material.jsp"/>
					</div>
				</div>
			</ui:content>
   
		</ui:tabpanel>


		<script>
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js",null, "js");
		</script>
		<script>
			var validator = $KMSSValidation(document.getElementById('character'));

			validator.addValidators({
				'characterInput' : {
					error : "${lfn:message('sys-mportal:sysMportalIcon.length.error')}",
					test : function(v, e, o) {

						if (!v) {
							return false;
						}

						if (v.length > 1) {
							return false;
						} else {
							return true;
						}
					}
				}
			});

			seajs.use([ 'lui/jquery', 'theme!form' ], function($) {
				// 绑定图片图标点击事件
				$('.imageIconsContainer').on('click', function(evt) {
					var $target = $(evt.target);
					if ($target.hasClass('mui')) {
						var claz = $target.attr('claz');
						var returnData = { "iconType":1, "className":claz };
						window.$dialog.hide(returnData);
					}
				});

				// 绑定字体图标点击事件
				$('.fontIconsContainer').on('click', function(evt) {
					var $target = $(evt.target);
					if ($target.hasClass('mui')) {
						var claz = $target.attr('claz');
						var returnData = { "iconType":2, "className":claz };
						window.$dialog.hide(returnData);
					}					
				});

				// 输入文字点击确定按钮触发事件
				window.selectIcon = function() {
					if (!Com_Parameter.event["submit"][0]()) {
						return;
					}
					var returnData = { "iconType":3, "text": $('#characterInput').val() };
					window.$dialog.hide(returnData);

				}
				
			});
		</script>

	</template:replace>
</template:include>