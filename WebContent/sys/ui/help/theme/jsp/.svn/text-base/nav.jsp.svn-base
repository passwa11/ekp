<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="com.landray.kmss.sys.ui.xml.model.SysUiTheme"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String fdId = request.getParameter("fdId");
	if(fdId==null){
		fdId = "default";
	}
	SysUiTheme theme = SysUiPluginUtil.getThemeById(fdId);
	request.setAttribute("sys.ui.theme", theme.getFdId());
	request.setAttribute("theme", theme);
%>
<script>
	function switchThemeHelpMenu() {
		var obj = LUI.$("#theme_help_menu");
		// if (obj.css('left') == '0px') {
		// 	var left = LUI.$(window).width() - obj.outerWidth(true);
		// 	obj.css('left', left + "px");
		// } else {
		// 	obj.css('left', '0px');
		// }
		if (obj.css('top') == '0px') {
			obj.css('top', "auto");
			obj.css('bottom', "0");
		} else {
			obj.css('bottom', "auto");
			obj.css('top', "0");
		}
		LUI('theme_help_menu_pop').overlay.hide();
	}

	function returnThemeIndex() {
		var url = location.href;
		var _arr = url.split('/');
		var _len = _arr.length;
		var i = url.lastIndexOf("/");
		var _paramUrl = url.substring(i + 1);
		if (_arr[_len - 2] == 'pages') {
			_paramUrl = 'pages/' + _paramUrl;
		}
		location.href = Com_Parameter.ContextPath + Com_SetUrlParameter("sys/ui/help/theme/index.jsp", "from", _paramUrl);
	}

	seajs.use('sys/ui/help/theme/css/help-theme.css');
	var _title = document.title;
	document.title= _title + "- ${theme.fdName}";
</script>
<div class="lux-theme-menu" id="theme_help_menu">
	<div class="lux-theme-innner">
		菜单
		<ui:popup align="down-left" id="theme_help_menu_pop">
			<div class="lux-theme-menu-title">
				<c:out value="${theme.fdName}" />(${theme.fdId})
			</div>
			<ui:toolbar layout="sys.ui.toolbar.ver.default" count="100" id="theme_help_menu_toolbar">
				<ui:button text="挡住我了" onclick="switchThemeHelpMenu()" order="1" />
				<ui:button text="切换到一级页面" href="/sys/ui/help/theme/pages/portal-default.jsp?fdId=${theme.fdId}"
					target="_self" />
				<ui:button text="切换到二级页面" href="/sys/ui/help/theme/pages/list.jsp?fdId=${theme.fdId}" target="_self" />
				<ui:button text="切换到三级页面" href="/sys/ui/help/theme/pages/form.jsp?fdId=${theme.fdId}" target="_self" />
				<ui:button text="后台管理" href="/sys/ui/help/theme/pages/list-profile.jsp?fdId=${theme.fdId}" target="_self" />
				<ui:button text="切换到通用样式" href="/sys/ui/help/theme/style.jsp?fdId=${theme.fdId}" target="_self" />
				<ui:button text="切换到图标列表" href="/sys/ui/help/theme/icon.jsp?fdId=${theme.fdId}" target="_self" />
				<ui:button text="返回到主题选择" href="javascript:returnThemeIndex();" target="_self" />
			</ui:toolbar>
		</ui:popup>
	</div>
</div>
<script>
	seajs.use(['lui/jquery'], function ($) {
		$(document).ready(function () {
			$(".lux-theme-menu").appendTo($("body"));
		})
	});
</script>