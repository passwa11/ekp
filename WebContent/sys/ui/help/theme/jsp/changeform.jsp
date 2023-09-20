<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="com.landray.kmss.sys.ui.xml.model.SysUiTheme"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
	function switchTemplateHelpMenu() {
		var obj = LUI.$("#template_help_menu");
		if (obj.css('left') == '46px') {
			var left = LUI.$(window).width() - obj.outerWidth(true);
			obj.css('left', left - 46 + "px");
		} else {
			obj.css('left', '46px');
		}
		LUI('template_help_menu_pop').overlay.hide();
	}
</script>
<div class="lux-theme-menu lux-form-menu"
	id="template_help_menu">
	<div class="lux-theme-innner">
		切换三级页面模板
		<ui:popup align="down-left" style="background-color: white; display:none;" id="template_help_menu_pop">
			<div class="lux-theme-menu-title">
				<c:out value="${theme.fdName}" />(${theme.fdId})
			</div>
			<ui:toolbar layout="sys.ui.toolbar.ver.default" count="100" id="template_help_menu_toolbar">
				<ui:button text="挡住我了" onclick="switchTemplateHelpMenu();" order="1" />
				<ui:button text="通用三级页" href="/sys/ui/help/theme/pages/form.jsp?fdId=${theme.fdId}" target="_self" />
				<ui:button text="右侧导航" href="/sys/ui/help/theme/pages/form-right.jsp?fdId=${theme.fdId}" target="_self" />
				<!-- <ui:button text="学习管理" href="/sys/ui/help/theme/pages/list-study.jsp?fdId=${theme.fdId}" target="_self" /> -->
			</ui:toolbar>
		</ui:popup>
	</div>
</div>