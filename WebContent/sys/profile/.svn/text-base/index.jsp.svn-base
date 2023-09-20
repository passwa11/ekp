<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.profile.util.ProfileMenuUtil" %>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/profile/profile.tld" prefix="profile"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	boolean isSearchMenus = !TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN || (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN && TripartiteAdminUtil.isGeneralUser());
	if(isSearchMenus) {
		ProfileMenuUtil.getSearchMenus(request); // 获取能够作为搜索操作的二/三级菜单
	}
	String profileLogo = SysUiConfigUtil.getProfileLogoTitle();
	request.setAttribute("profileLogo", profileLogo);
%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<style>iframe{border:0;}</style>
		<% if (isSearchMenus) { %>
		<script type="text/javascript">
			var menuDatas = eval('(${menuDatas})');
			var searchNodataMsg = "<bean:message bundle="sys-profile" key="sys.profile.location.search.nodata"/>";
		</script>
		<% } %>
	</template:replace>
	<template:replace name="title">
		<bean:message bundle="sys-profile" key="module.sys.profile"/>
	</template:replace>
	<template:replace name="body">
		<!-- 后台配置中心 -->
		<profile:view id="sysProfileView">
			<!-- 顶部主导航栏 -->
			<div class="lui_profile_header">
				<div class="lui_profile_header_frame">
					<div class="lui_porfile_header_content clearfloat">
						<!-- LOGO -->
						<div class="lui_porfile_header_logo">
							<div class="lui_porfile_header_logo_div">
								<img class="lui_porfile_header_logo_img" src="${LUI_ContextPath}${profileLogo}"
									 onclick="window.open('${LUI_ContextPath}/','_self')" title="${lfn:message('home.logoTitle') }">
							</div>
						</div>
						<!-- 标题 -->
						<div class="lui_porfile_header_title"><bean:message bundle="sys-profile" key="sys.profile.header.title" /></div>
						<!-- 个人信息 -->
						<div class="lui_portal_header_userinfo">
							<portal:widget file="/sys/profile/userinfo.jsp"></portal:widget>
						</div>
						<% if (isSearchMenus) { %>
						<!-- 后台配置定位 -->
						<div class="lui_portal_header_location">
							<i class="lui_icon_m lui_icon_m_profile_navTop_location"></i>
						</div>
						<% } %>
						<!-- 顶部导航栏组件 -->
						<div class="lui_profile_header_navTop">
							<profile:navTop id="sysProfileNavTop">
								<ui:source type="Static">
									<%@ include file="/sys/profile/resource/js/data/navTop.jsp" %>
								</ui:source>
								<ui:render type="Javascript" ref="sys.profile.navTop.default"></ui:render>
							</profile:navTop>
						</div>
					</div>
				</div>
			</div>
			<!-- 左侧导航栏 -->

			<div class="lui_profile_navLeft_container" >
				<!-- 左侧导航栏组件 -->
				<profile:navLeft id="sysProfileNavLeft">
					<ui:source type="AjaxJson">
						{"url":"/sys/profile/sys_profile_main/sysCfgProfileConfig.do?method=data"}
					</ui:source>
					<ui:render type="Javascript" ref="sys.profile.navLeft.default"></ui:render>
				</profile:navLeft>
			</div>
			<!-- 右侧主页面 -->
			<div class="lui_profile_moduleMain">
				<% if (isSearchMenus) { %>
				<!-- 搜索定位 -->
				<div class="lui_profile_navLeft_mask" style="display: none;"></div>
				<div class="lui_profile_location_wrapper">
					<div class="location_input" style="display: none;">
						<i class="location_arrow"></i>
						<i class="icon_cancel"></i>
						<input type="text" placeholder="${lfn:message('sys-profile:sys.profile.location.search.text') }" onkeyup="searchMenu(this.value, event);"/>
						<div class="location_input_selection" style="display: none;max-height:200px;overflow-y:auto;">
							<ul id="selection_menus">
							</ul>
						</div>
					</div>
				</div>
				<% } %>
				<!-- 主业务组件 -->
				<profile:moduleMain id="sysProfileModuleMain"></profile:moduleMain>
			</div>
		</profile:view>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/profile/resource/js/profile.js?s_cache=${LUI_Cache}"></script>

		<script>


			function itemTitleClick(topKey,subKey,subUrl){
				seajs.use(['lui/jquery','lui/topic'], function($ , topic) {

					var currentItem= $('.lui_profile_header_navTop_item[data-type="'+ topKey +'"]');

					var rootInner= $('.lui_profile_header_navTop_rootInner');

					$('.lui_profile_header_navTop_item').removeClass('current');
					currentItem.addClass('current');

					if(!$.contains(rootInner[0],currentItem[0])){
						var firstNode = rootInner.children().eq(0);
						currentItem.after(firstNode);
						rootInner.prepend(currentItem);
					}


					topic.publish('sys.profile.navTop.change',{
						key : topKey
					});

					topic.publish('sys.profile.moduleMain.change',{
						key : subKey
					});


					topic.publish('sys.profile.navLeft.change',{
						key : subKey,
						url : subUrl
					});
				});
			}
			LUI.ready(function(){
				var logHeight = $(".lui_profile_header_frame").outerHeight();
				$(".lui_porfile_header_logo_img").css("max-height", logHeight);
			});

		</script>
		<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	</template:replace>
</template:include>

