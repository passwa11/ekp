<%@page import="com.landray.kmss.third.mall.util.MallUtil,com.landray.kmss.sys.profile.model.LoginConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%
	//主题固定为蓝天凌云
	request.setAttribute("sys.ui.theme", "sky_blue");
	// 判断云商城是否授权
	request.setAttribute("isAuth", MallUtil.enableMall(MallUtil.KEY_PORTAL));
	LoginConfig loginConfigObj = new LoginConfig();
	String logintemplatevalue = loginConfigObj.getCustomLoginTemplateEnable();
	if("false".equals(logintemplatevalue)){
		
	}else{
%>
<template:include ref="config.view">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="../resource/css/login_upload.css">
	</template:replace>
	<template:replace name="content">
		<script>
			Com_IncludeFile("validation.js|plugin.js|validation.jsp");
		</script>
		<!-- 添加自定义模板 -->
		
		<html:form action="/sys/profile/sys_login_template/sysLoginTemplate.do" enctype="multipart/form-data">
			<html:hidden property="fdId" />
		    <html:hidden property="method_GET" />
		    <div class="ld-mall-temeplate">
			    <div class="ld-mall-temeplate-action clearfix">
				    <!-- 上传模板包 -->
				    <div class="ld-mall-temeplate-action-upload" onclick="uploadLoginTemplate();">
				    <img src="${ LUI_ContextPath }/sys/profile/resource/images/upload@2x.png" >
				    <div>${lfn:message('sys-profile:sys.profile.mall.upload')}</div>
				    </div>
				    <!-- 从模板中心下载 -->
				    <div class="ld-mall-temeplate-action-download" onclick="toMall();">
				    <img src="${ LUI_ContextPath }/sys/profile/resource/images/noNetwork.png" onclick="">
				    <div>${lfn:message('sys-profile:sys.profile.mall.toMall')}</div>
			    </div>
		    </div>
		    
		    <!-- 列表 -->
			<div class="lui_template_tabs lui_main_content">
			      <ui:tabpanel scroll="true" id="templateTabPanel">
			          <ui:content title="${lfn:message('sys-profile:sys.profile.mall.host')}">
			              <ui:iframe id="template_listview" src="${LUI_ContextPath }/third/mall/portal/thirdMallPortal_index.jsp?type=login"></ui:iframe>
			          </ui:content>
			      </ui:tabpanel>
			</div>
		</html:form>
		<script>
			//上传登录模板
			function uploadLoginTemplate() {
				seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
					var url = '/sys/profile/portal/uploadLoginbao.jsp';
					dialog.iframe(url,"${lfn:message('sys-profile:sys.loginTemplate.title')}", function(value) {
						window.location.reload(true);
					}, {
						"width" : 800,
						"height" : 600
					});
				});
			}
			// 跳到商城页面
			function toMall() {
				<c:if test="${isAuth eq 'true'}">
				window.top.location.href='${LUI_ContextPath}/sys/profile/index.jsp#portal/templateCenter';
				window.top.location.reload();
				//Com_OpenWindow("${LUI_ContextPath }/third/mall/portal/thirdMallPortal_toMall.jsp?type=login");
				</c:if>
		 		<c:if test="${isAuth eq 'false'}">
		 		seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
		 			dialog.alert('<bean:message key="third-mall:thirdMall.login.noAuth"/>');
				});
		 		</c:if>
			}
			
			LUI.ready(function() {
				LUI("template_listview").on("iframeLoaded", function() {
					frame = this.$iframeNode[0];
					frame.height = "500px";
				});
			});
		</script>
	</template:replace>
</template:include>
<%
	}
%>
