<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.third.mall.util.MallUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<!-- 部件 -->
<%
	// 判断云商城是否授权
	request.setAttribute("isAuth", MallUtil.enableMall(MallUtil.KEY_PORTAL));
	//主题固定为蓝天凌云
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="config.view">
<template:replace name="content">
<script>
Com_IncludeFile("validation.js|plugin.js|validation.jsp");
</script>
<!-- 添加自定义模板 -->
<template:replace name="head">
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/sys/profile/resource/css/login_upload.css">
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/sys/profile/resource/css/index.css">
</template:replace>
<html:form action="/sys/profile/sys_login_template/sysLoginTemplate.do" enctype="multipart/form-data">
	<html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <div class="ld-mall-temeplate">
    <div class="ld-mall-temeplate-action clearfix">
	    <!-- 上传部件内容包 -->
	    <div class="ld-mall-temeplate-action-upload" onclick="uploadComponent();">
		    <img src="${ LUI_ContextPath }/sys/profile/resource/images/upload@2x.png" >
		    <div>${lfn:message('sys-ui:mall.component.upload') }</div>
	    </div>
	    <!-- 从模板中心下载 -->
	    <div class="ld-mall-temeplate-action-download" onclick="toMall();">
		    <img  src="${ LUI_ContextPath }/sys/profile/resource/images/noNetwork.png">
		    <div>${lfn:message('sys-profile:sys.profile.mall.toMall')}</div>
	    </div>
    </div>
    <!-- 列表 -->
	<div class="lui_template_tabs lui_main_content">
	      <ui:tabpanel scroll="true" id="templateTabPanel">
	          <ui:content title="${lfn:message('sys-ui:mall.component.hot')}">
	              <ui:iframe id="template_listview" src="${LUI_ContextPath }/third/mall/portal/thirdMallPortal_index.jsp?type=${JsParam.type}"></ui:iframe>
	          </ui:content>
	      </ui:tabpanel>
	    </div>
	    </html:form>
	<script>
		//上传内容包模板
		function uploadComponent() {
			seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
				dialog.iframe('/sys/ui/help/component/upload.jsp', "${lfn:message('sys-ui:mall.component.upload') }", function(value) {
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

				<%--Com_OpenWindow("${LUI_ContextPath }/third/mall/portal/thirdMallPortal_toMall.jsp?type=${JsParam.type}");--%>
			</c:if>
	 		<c:if test="${isAuth eq 'false'}">
	 		seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
	 			dialog.alert('<bean:message key="third-mall:thirdMall.component.noAuth"/>');
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
