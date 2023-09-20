<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="content" >
		<xform:config isLoadDataDict="false" showStatus="edit">
			<script>
				Com_IncludeFile('doclist.js|jquery.js');
				Com_IncludeFile("docutil.js|validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
			</script>
			<script>DocList_Info.push('customBrowserTable');</script>
			<form action="">
				<table id="customBrowserTable" class="tb_normal" width="100%">
					<tr class="tr_normal_title">
						<td width="20px;">
							<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
						</td>
						<td width="300px"><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.setting.name" /></td>
						<td><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.setting.ua" /></td>
						<td width="30px">
							<a href="javascript:;" class="com_btn_link" onclick="DocList_AddRow('customBrowserTable');">${lfn:message('button.insert')}</a>
						</td>
					</tr>
					<%-- 模版行 --%>
					<tr style="display:none;" KMSS_IsReferRow="1">
						<td KMSS_IsRowIndex="1">
							!{index}
						</td>
						<td>
							<input type="text" name="name" class="inputsgl" validate="required customBrowserName" style="width:90%">
							<span class="txtstrong">*</span>
						</td>
						<td>
							<input type="text" name="keyword" class="inputsgl" validate="required" style="width:90%">
							<span class="txtstrong">*</span>
						</td>
						<td>
							<div style="text-align:center">
								<img src="${LUI_ContextPath}/resource/style/default/icons/delete.gif" title="${lfn:message('button.delete')}" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">
							</div>
						</td>
					</tr>
					<%-- 内容行 --%>
				</table>
			</form>
		</xform:config>
		
		<center style="margin:10px 0;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" order="1" onclick="saveData();"></ui:button>
			&nbsp;&nbsp;
			<a href="${LUI_ContextPath}/sys/profile/maintenance/custom_browser_setting_help.jsp" target="_blank" class="com_btn_link"><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.setting.showHelp" /></a>
		</center>
		
		<script type="text/javascript">
		// 初始化数据
		function initCustomBrowser() {
			var __DocList_TableInfo = DocList_TableInfo["customBrowserTable"];
			if(__DocList_TableInfo) {
				var _customBrowsers = "${kmssCustomBrowser}".split(";");
			  	$.each(_customBrowsers, function(i, n) {
			  		if(n.length < 5) // 过滤不合法的字符
			  			return true;
			  		var customBrowser = JSON.parse(unescape(n));
			  		var tr = DocList_AddRow('customBrowserTable'); // 增加一行
			  		$(tr).find("input[name=name]").val(customBrowser.name);
			  		$(tr).find("input[name=keyword]").val(customBrowser.keyword);
			  	});
			} else {
				setTimeout(function() {
					initCustomBrowser();
				}, 50);
			}
		}
		
		$(document).ready(function() {
			var _validator = $KMSSValidation();
			_validator.addValidator(
				'customBrowserName',
				'<bean:message  bundle="sys-profile" key="sys.profile.browserCheck.setting.name.err" />',
				function(v, e, o) {
					if (v == "") {
						return true;
					}
					return /^\w+$/.test(v);
			});
			
			window.saveData = function() {
				if(!_validator.validate()) {
					return;
				}
				seajs.use( [ 'lui/jquery' ], function($) {
					var datas = [];
					$.each($("#customBrowserTable>tbody>tr"), function(i, n) {
						if(i == 0) // 跳过第一行标题
							return true;
						var obj = {};
						obj.name = $(n).find("input[name=name]").val();
						obj.keyword = $(n).find("input[name=keyword]").val();
						datas.push(obj);
					});
					window.$dialog.hide(datas);
				});
			};
			
			initCustomBrowser();
		});
		</script>
	</template:replace>
</template:include>