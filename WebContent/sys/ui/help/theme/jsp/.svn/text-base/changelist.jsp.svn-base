<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="com.landray.kmss.sys.ui.xml.model.SysUiTheme"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
	function switchTemplateHelpMenu() {
		var obj = LUI.$("#template_help_menu");
		// if (obj.css('left') == '46px') {
		// 	var left = LUI.$(window).width() - obj.outerWidth(true);
		// 	obj.css('left', left - 46 + "px");
		// } else {
		// 	obj.css('left', '46px');
		// }
		if (obj.css('top') == '0px') {
			obj.css('top', "auto");
			obj.css('bottom', "0");
		} else {
			obj.css('bottom', "auto");
			obj.css('top', "0");
		}
		LUI('template_help_menu_pop').overlay.hide();
	}
</script>
<div class="lux-theme-menu lux-list-menu"
	id="template_help_menu">
	<div class="lux-theme-innner">
		切换列表页面模板
		<ui:popup align="down-left" id="template_help_menu_pop">
			<div class="lux-theme-menu-title">
				<c:out value="${theme.fdName}" />(${theme.fdId})
			</div>
			<ui:toolbar layout="sys.ui.toolbar.ver.default" count="100" id="template_help_menu_toolbar">
				<ui:button text="挡住我了" onclick="switchTemplateHelpMenu();" order="1" />
				<ui:button text="通用列表" href="/sys/ui/help/theme/pages/list.jsp?fdId=${theme.fdId}" target="_self" />
				<ui:button text="时间管理" href="/sys/ui/help/theme/pages/list-calendar.jsp?fdId=${theme.fdId}" target="_self" />
				<kmss:ifModuleExist path="/kms/kmaps/">
					<ui:button text="知识地图" href="/sys/ui/help/theme/pages/list-map.jsp?fdId=${theme.fdId}" target="_self" />
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/kms/learn/">
					<ui:button text="学习管理" href="/sys/ui/help/theme/pages/list-study.jsp?fdId=${theme.fdId}" target="_self" />
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/kms/loperation/">
					<ui:button text="学习运营" href="/sys/ui/help/theme/pages/list-loperation.jsp?fdId=${theme.fdId}" target="_self" />
				</kmss:ifModuleExist>
			</ui:toolbar>
		</ui:popup>
	</div>
</div>