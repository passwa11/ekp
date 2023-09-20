<%@page import="com.landray.kmss.sys.profile.model.LoginConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-profile" key="sys.profile.portal.login.title" /></template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="../resource/css/login_upload.css">
	</template:replace>
	<template:replace name="content">
		<%
			LoginConfig loginConfigObj = new LoginConfig();
			String logintemplatevalue = loginConfigObj.getCustomLoginTemplateEnable();
		%>
		<ui:tabpanel layout="sys.ui.tabpanel.default" id="tabpanel_Id">
			<ui:content title="${lfn:message('sys-profile:sys.profile.loginconfig.loginPageTemplate')}" id="tab_content_one">

				<h2 align="center" style="margin:10px 0">
					<span class="profile_config_title1"><bean:message bundle="sys-profile" key="sys.profile.portal.login.title" /></span>
				</h2>
				<div class="profile_loginSetting_frame">
					<ul class="profile_loginSetting_list">
						<c:forEach items="${pluginTemps}" var="template" varStatus="status">
						<li>
							<div class="profile_loginSetting_content">
								<div class="thumb_img_bg" style="background-image: url(<c:url value="${template.fdThumbnail}"/>);">
									<img class="thumb_img" src="<c:url value="${template.fdThumbnail}"/>" alt='${template.fdName}' />
								</div>
								<div class="profile_loginSetting_bg">
									<a href="javascript:__review('<c:url value="${template.fdJspUrl}?isDesign=1"/>');"><span class="profile_icon_view"></span><bean:message bundle="sys-profile" key="sys.profile.portal.login.preview" /></a>
									<span class="line"></span>
									<a href="javascript:__setDef('${template.fdId}');"><i class="profile_icon_default"></i><bean:message bundle="sys-profile" key="sys.profile.portal.login.setDefault" /></a>
									<c:if test="${template.fdCustomed }">
										<span class="line"></span>
										<a href="javascript:__customization(${template.fdKey});"><i class="profile_icon_custom"></i><bean:message bundle="sys-profile" key="sys.profile.portal.login.customization" /></a>
									</c:if>
								</div>
							</div>
							<p id="login_model_${template.fdKey}" class="loginTemplete_title">
								<c:if test="${template.fdDefault}">
									<i class="default_label"><bean:message bundle="sys-profile" key="sys.profile.portal.login.default" /></i>
								</c:if>
								${template.fdName}
							</p>
						</li>
						</c:forEach>
					</ul>
					<!-- 用户自定义上传登录页 Starts -->

				<div id="profile_loginsetting_box" class="profile_loginSetting_other_list">
					<p class="profile_loginSetting_split">
							<span><bean:message bundle="sys-profile" key="sys.loginTemplate.custom.template" /></span>
							<!-- 搜索 Starts -->
							<span class="profile_loginSetting_searchBar">
								<input type="text" placeholder="${lfn:message('sys-profile:sys.profile.template.search.text')}" class="profile_loginSetting_searchBarInt">
								<span class="profile_loginSetting_searchBtn"></span>
							</span>
							<!-- 搜索 Ends -->
						</p>
						<ul class="profile_loginSetting_list profile_ext_login_list">
							<li class="profile_loginSetting_uploadBtn">
								<div class="profile_loginSetting_content" onclick="uploadLoginTemplate();">
									<div class="thumb_img_bg">
										<span class="btn-upload"><i></i></span>
										<span class="uploadBtn_title"><bean:message bundle="sys-profile" key="sys.loginTemplate.custom.template.add" /></span>
									</div>
								</div>
							</li>
							<c:forEach items="${uploadTemps}" var="template" varStatus="status">
							<li loginId="${template.fdKey}" loginName="${template.fdName}">
								<div class="profile_loginSetting_content">
									<!-- 删除 Starts -->
									<span class="profile_custom_del" onclick="__delete('${template.fdId}');"><i></i></span>
									<!-- 删除 Ends -->
									<div class="thumb_img_bg" style="background-image: url(<c:url value="${template.fdThumbnail}"/>);"></div>
									<img class="thumb_img" src="<c:url value="${template.fdThumbnail}"/>" alt="${template.fdName}">
									<div class="profile_loginSetting_bg">
										<a href="javascript:__review('<c:url value="${template.fdJspUrl}"/>');" style="width: 25%;"><span class="profile_icon_view"></span><bean:message bundle="sys-profile" key="sys.profile.portal.login.preview" /></a>
										<span class="line"></span>
										<a href="javascript:__setDef('${template.fdId}');" style="width: 25%;" title="${lfn:message('sys-profile:sys.profile.portal.login.setDefault')}"><i class="profile_icon_default"></i><bean:message bundle="sys-profile" key="sys.profile.portal.login.setDefault" /></a>
										<span class="line"></span>
										<a href="javascript:__upload_custom('${template.fdId}');" style="width: 25%;"><i class="profile_icon_custom"></i><bean:message bundle="sys-profile" key="sys.profile.portal.login.customization" /></a>
										<span class="line"></span>
										<a href="javascript:__downloadTemplate('${template.fdId}');" style="width: 25%;"><i class="profile_icon_download"></i><bean:message bundle="sys-profile" key="sys.loginTemplate.download" /></a>
			
									</div>
								</div>
								<p id="login_model_${template.fdKey}" class="loginTemplete_title">
									<c:if test="${template.fdDefault}">
										<i class="default_label"><bean:message bundle="sys-profile" key="sys.profile.portal.login.default" /></i>
									</c:if>
									${template.fdName}
								</p>
							</li>
							</c:forEach>
						</ul>
					</div>
					<!-- 用户自定义上传登录页 Ends -->
				</div>
				<form target="_blank" action="<c:url value="/sys/profile/sys_login_template/sysLoginTemplate.do?method=download"/>" method="post" name="downloadTemplateForm">
					<input type="hidden" name="downloadId" id="downloadId" />
				</form>
			</ui:content>
			<ui:content title="${lfn:message('sys-profile:sys.profile.loginconfig.setting')}">
				<ui:event event="show">
					document.getElementById('loginConfig').src = '<c:url value="/sys/profile/LoginConfig.do" />?method=edit&modelName=com.landray.kmss.sys.profile.model.LoginConfig';
				</ui:event>
				<iframe id="loginConfig" width="100%" height="1000" frameborder=0 scrolling=no></iframe>
			</ui:content>
		</ui:tabpanel>
	 	<script type="text/javascript">
			$(function() {
				setLinkWidth();
			});
			
			
			setTimeout(function(){
				var loginTemplateValue = "<%=logintemplatevalue%>";
				if(loginTemplateValue==="true"){
					$("#profile_loginsetting_box").show();
				}else{
					$("#profile_loginsetting_box").hide();
				}

				var tabPanlWidgit = LUI.cachedInstances["tabpanel_Id"];
				var tabPanlOne = LUI.cachedInstances["tab_content_one"];
				try{
			 		if(tabPanlOne){	
			 			tabPanlOne.load=function(){
							tabPanlWidgit.titlesNode[0].on("click",function(){
								if(window.templateValue==="true"){
									$("#profile_loginsetting_box").show();
								}
								if(window.templateValue==="false"){
									$("#profile_loginsetting_box").hide();
								} 
							});
						}
			 		}
					
				}catch(e){
					if(window.console){
						console.log(e);
					}
				}
			},102);
			
			function setLinkWidth() {
				$.each($(".profile_loginSetting_list li"),function() {
					var links = $(this).find(".profile_loginSetting_bg a");
					var count = links.length;
					links.css("width",100/count+"%");
				});
			}
			

		 	// 预览
			function __review(url) {
				window.open(url);
			}

		 	// 设为默认
			function __setDef(id) {
				seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
					$.ajax({
						url : '<c:url value="/sys/profile/sys_login_template/sysLoginTemplate.do?method=updateDefault"/>',
						type : 'post',
						data : {'fdId' : id},
						dataType : "json",
						success : function(data) {
							window.location.reload();
							dialog.result(data);
						}
					});
				});
			}
			// 定制
		 	function __customization(key) {
		 		window.open('<c:url value="/sys/profile/sysProfileCuxTemplateAction.do?method=design"/>&key='+key);
		 	}
			// 删除
			function __delete(id) {
				var delUrl = '${LUI_ContextPath}/sys/profile/sys_login_template/sysLoginTemplate.do?method=delete&fdId='+id;
				var config = {
					url : delUrl,
					modelName : "com.landray.kmss.sys.profile.model.SysLoginTemplate"
				};
				Com_Delete(config,delCallback);
			}
			
			function delCallback(data){
				seajs.use(['lui/dialog'],function(dialog) {
					window.location.reload();
					dialog.result(data);
				});
			}
			
			//添加自定义模板
			function uploadLoginTemplate() {
				seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
					var url = "/sys/profile/portal/uploadLoginbao.jsp";
					 var config = {"width" : 600,"height" : 320};
			          <kmss:ifModuleExist path="/third/mall/">
			          url = "/sys/profile/portal/uploadLoginTemplate.jsp";
			          config = {"width" : 920,"height" : 650};
			          </kmss:ifModuleExist>
					dialog.iframe(url,"${lfn:message('sys-profile:sys.loginTemplate.custom.template.add')}", function(value) {
    					window.location.reload(true);
    				}, config);
				});
			}
			//用户上传的模板支持定制
			function __upload_custom(id) {
				seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
					var url = '/sys/profile/sys_login_template/sysLoginTemplate.do?method=customLogin&fdId='+id;
					dialog.iframe(url,"${lfn:message('sys-profile:sys.profile.portal.login.customization')}", function(value) {
    					window.location.reload(true);
    				}, {
    					"width" : 800,
    					"height" : 300
    				});
				});
			}
			
			function __downloadTemplate(fdId) {
				seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
					$("#downloadId").val(fdId);
					document.downloadTemplateForm.submit();
				});
			}

			// 搜索登录
			function __searchLogin(val){
				if(!val){
					$(".profile_ext_login_list li").show();
					return
				}
				$(".profile_ext_login_list li").each(function(){
					var loginId = $(this).attr("loginId") || "";
					var loginName = $(this).attr("loginName") || ""
					
					if(loginId.indexOf(val) > -1 || loginName.indexOf(val) > -1){
						$(this).show();
					}else {
						$(this).hide();
					}
				})
				$(".profile_loginSetting_uploadBtn").show()
			}

			$(".profile_loginSetting_searchBar .profile_loginSetting_searchBtn").on("click",function(){
				var _val = $(".profile_loginSetting_searchBar input").val();
				__searchLogin(_val);
			})

			$('.profile_loginSetting_searchBar input').bind('keydown',function(event){
				if(event.keyCode == "13") {
					var _val = $(this).val();
					__searchLogin(_val);
				}
			});
			

		</script>
	</template:replace>
</template:include>
