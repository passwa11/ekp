<%@page import="com.landray.kmss.sys.ui.util.ThemeUtil"%>
<%@page import="com.landray.kmss.sys.config.xml.XmlReaderContext"%>
<%@ page import="com.landray.kmss.sys.ui.util.ResourceCacheListener" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	if(request.getParameter("reLoad")!=null&&request.getParameter("reLoad").equals("yes")){
		new ResourceCacheListener().refreshUiCache();
	}
	request.setAttribute("uploadThemes", ThemeUtil.getCustomTheme());
	request.setAttribute("sys.ui.theme", "default");
	request.setAttribute("themeAppFolder", "/"+XmlReaderContext.UIEXT);
	request.setAttribute("defaultThemes", ThemeUtil.getDefTheme());
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/sys/profile/resource/css/login_upload.css">
		<style>
			.profile_head{
				height: 40px;
    			position: relative;
    			border-bottom: 1px solid #e5e5e5;
    			margin: 0 5px;
			}
			.profile_head .button{
				width: 80px;
			    display: inline-block;
			    position: absolute;
			    top: 5px;
			    right: 20px;
			    background-color: #4285f5;
			    color: #FFFFFF;
			    line-height: 30px;
			    border-radius: 3px;
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<div class="profile_themeSetting_frame">
			<div class='profile_head'>
				<p class='profile_loginSetting_split'><span>${lfn:message('sys-ui:mall.theme.sys') }</span>
				</p>
				<ui:button text="${lfn:message('sys-ui:sys.ui.old.page') }" styleClass="button" onclick="__old()" style="width:80px;margin-right: 100px;"/>
				<ui:button text="${lfn:message('sys-ui:ui.help.luiext.merge') }" styleClass="button" onclick="__mergeTheme()" style="width:80px"/>
			</div>
			<div class="profile_loginSetting_frame">
				<ul class="profile_loginSetting_list">
					<c:forEach items="${defaultThemes}" var="defaultTheme" varStatus="status">
					<li>
						<div class="profile_loginSetting_content">
							<div class="thumb_img_bg" style="background-image: url(<c:url value="${defaultTheme.fdThumb}"/>);">
								<img class="thumb_img" src="<c:url value="${defaultTheme.fdThumb}"/>" alt='<c:out value="${defaultTheme.fdName}"/>' />
							</div>
							<div class="profile_loginSetting_bg">
								<a href="javascript:__reviewTheme('<c:url value="${defaultTheme.fdThumb}"/>');"><span class="profile_icon_view"></span><bean:message bundle="sys-profile" key="sys.profile.portal.login.preview" /></a>
							</div>
						</div>
						<p id="login_model_default_0" class="loginTemplete_title">
							<c:out value="${defaultTheme.fdName}"/>
						</p>
					</li>
					</c:forEach>
				</ul>
				<!-- 用户自定义上传登录页 Starts -->
				<div id="profile_loginsetting_box" >
					<p class="profile_loginSetting_split">
						<span>${lfn:message('sys-ui:mall.theme.ext') }</span>
						<!-- 搜索 Starts -->
						<span class="profile_loginSetting_searchBar">
							<input type="text" placeholder="${lfn:message('sys-profile:sys.profile.theme.search.text')}" >
							<span class="profile_loginSetting_searchBtn"></span>
						</span>
						<!-- 搜索 Ends -->
					</p>
					<ul class="profile_loginSetting_list profile_ext_theme_list">
						<li class="profile_loginSetting_uploadBtn">
							<div class="profile_loginSetting_content" onclick="__uploadTheme();">
								<div class="thumb_img_bg">
									<span class="btn-upload"><i></i></span>
									<span class="uploadBtn_title">${lfn:message('sys-ui:mall.theme.add') }</span>
								</div>
							</div>
						</li>
						<c:forEach items="${uploadThemes}" var="uploadTheme" varStatus="status">
						<li themeId="${uploadTheme.fdId}" themeName="${uploadTheme.fdName}">
							<c:set var="fdThumb" value="" />
							<c:if test="${not empty uploadTheme.fdThumb}">
								<c:set var="fdThumb" value="${themeAppFolder }/${uploadTheme.fdId}/${uploadTheme.fdThumb}" />
							</c:if>
							<div class="profile_loginSetting_content">
								<!-- 删除 Starts -->
								<span class="profile_custom_del" onclick="__deleteTheme('${uploadTheme.fdId}','${uploadTheme.uiType}');"><i></i></span>
								<!-- 删除 Ends -->
								<c:choose>
									<c:when test="${empty fdThumb}">
										<div class="thumb_img_bg no_thumb"></div>
									</c:when>
									<c:otherwise>
										<div class="thumb_img_bg" style="background-image: url(<c:url value="${fdThumb}"/>);"></div>
										<img class="thumb_img" src="<c:url value="${fdThumb}"/>" alt="${uploadTheme.fdName}">
									</c:otherwise>
								</c:choose>
								
								<div class="profile_loginSetting_bg">
									<c:if test="${not empty fdThumb}">
									<a href="javascript:__reviewTheme('<c:url value="${fdThumb}"/>');" style="width: 25%;"><span class="profile_icon_view"></span><bean:message bundle="sys-profile" key="sys.profile.portal.login.preview" /></a>
									<span class="line"></span>
									</c:if>
									<a href="javascript:__downloadTheme('${uploadTheme.fdId}','${uploadTheme.fdName}');" style="width: 25%;"><i class="profile_icon_download"></i><bean:message bundle="sys-profile" key="sys.loginTemplate.download" /></a>
									<c:if test="${not empty uploadTheme.fdHelp}">
										<span class="line"></span>
										<a href="javascript:Com_OpenWindow('<c:url value="${uploadTheme.fdHelp}"/>');" style="width: 25%;"><i class="profile_icon_help"></i><bean:message key="home.help" /></a>
									</c:if>
								</div>
							</div>
							<p id="login_model_upload_${status}" class="loginTemplete_title">
								${uploadTheme.fdName}
							</p>
						</li>
						</c:forEach>
					</ul>
				</div>
				<!-- 用户自定义上传登录页 Ends -->
			</div>
		</div>
		<!-- 打包下载 -->
		<form target="_blank" name="themeForm" action="" method="post">
			<input type="hidden" name="method" value="download">
		</form>
	 	<script type="text/javascript">
			$(function() {
				setLinkWidth();
			});
			
			function setLinkWidth() {
				$.each($(".profile_loginSetting_list li"),function() {
					var links = $(this).find(".profile_loginSetting_bg a");
					var count = links.length;
					links.css("width",100/count+"%");
				});
			}

		 	// 预览
			function __reviewTheme(url) {
		 		if(url){
		 			Com_OpenWindow(url);
		 		}
			}
			function __deleteTheme(rid,uiType) {
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
					dialog.confirm("${lfn:message('sys-ui:ui.help.luiext.deletetheme.tip') }",function(val){
						if(val){
							$.get("${LUI_ContextPath}/sys/ui/sys_ui_extend/sysUiExtend.do?method=deleteExtend&id="+rid+"&uiType="+uiType,function(txt){
								if(txt=="1"){
									dialog.success("${lfn:message('sys-ui:ui.help.luiext.success') }");
									location.href = Com_SetUrlParameter(location.href, "reLoad", "yes");
								}
							});
						}
					});
				});				
			}
			function __downloadTheme(rid,name,evt) {
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
					dialog.confirm('<bean:message key="ui.help.luiext.downloadtheme.tip" bundle="sys-ui" arg0="' + name + '"/>',function(val){
						if(val){
							document.themeForm.action = "${LUI_ContextPath}/sys/ui/sys_ui_extend/sysUiExtend.do?method=download&id="+rid;
							//document.themeForm.submit();
							window.location.href="${LUI_ContextPath}/sys/ui/sys_ui_extend/sysUiExtend.do?method=download&id="+rid;
						}
					});
				});
			}
			function __uploadTheme(){
				seajs.use(['lui/dialog'],function(dialog){
					var url = "/sys/ui/help/lui-ext/upload.jsp";
					 var config = {"width" : 600,"height" : 320};
			          <kmss:ifModuleExist path="/third/mall/">
			          url = "/sys/ui/help/lui-ext/uploadThemeTemplate.jsp";
			          config = {"width" : 920,"height" : 650};
			          </kmss:ifModuleExist>
					dialog.iframe(url,"${lfn:message('sys-ui:mall.theme.upload') }",function(data){
							location.href = Com_SetUrlParameter(location.href, "reLoad", "yes");
					},config);
				});
			}
			function __mergeTheme(){
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
					$.get("${LUI_ContextPath}/sys/ui/sys_ui_extend/sysUiExtend.do?method=merge",function(txt){
						if(txt=='1'){
							dialog.success("${lfn:message('sys-ui:ui.help.luiext.success') }");
						}
					});
				});
			}
			function __old() {
				Com_OpenWindow("${LUI_ContextPath}/sys/ui/help/lui-ext/index_old.jsp", "_self");
			}

			// 搜索主题
			function __searchTheme(val){
				if(!val){
					$(".profile_ext_theme_list li").show();
					return
				}
				$(".profile_ext_theme_list li").each(function(){
					var themeId = $(this).attr("themeId") || "";
					var themeName = $(this).attr("themeName") || ""
					if(themeId.indexOf(val) > -1 || themeName.indexOf(val) > -1){
						$(this).show();
					}else {
						$(this).hide();
					}
				})
				$(".profile_loginSetting_uploadBtn").show()
			}

			$(".profile_loginSetting_searchBar .profile_loginSetting_searchBtn").on("click",function(){
				var _val = $(".profile_loginSetting_searchBar input").val();
				__searchTheme(_val)
			})

			$('.profile_loginSetting_searchBar input').bind('keydown',function(event){
				if(event.keyCode == "13") {
					var _val = $(this).val();
					__searchTheme(_val)
				}
			});
		</script>
	</template:replace>
</template:include>
