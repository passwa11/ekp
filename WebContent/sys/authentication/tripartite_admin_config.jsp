<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.config.util.LicenseUtil"%>
<%@ include file="/resource/jsp/common.jsp"%>

<%
	// 需要判断License是否支持三员管理
	boolean isEnabledThreeAdmin = "true".equals(LicenseUtil.get("license-sanyuan"));
	if(isEnabledThreeAdmin) {
%>

<script type="text/javascript">
	function config_tripartite_admin_chgEnabled() {
		var tbObj = document.getElementById("config_admin");
		var field = document.getElementsByName("_value(kmss.tripartite.admin.enabled)")[0];
		for (var i = 0; i < tbObj.rows.length; i++) {
			tbObj.rows[i].style.display = field.checked ? "" : "none";
			var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
			for (var j = 0; j < cfgFields.length; j++) {
				cfgFields[j].disabled = !field.checked;
			}
		}
		$(document).trigger("kmss.tripartite.admin.enabled", field.checked);
	}
	
	// 初始化默认的管理员账号
	function config_init_tripartite_admin() {
		var sysadmin = document.getElementsByName("value(kmss.tripartite.sysadmin.names)")[0].value;
		var security = document.getElementsByName("value(kmss.tripartite.security.names)")[0].value;
		var auditor = document.getElementsByName("value(kmss.tripartite.auditor.names)")[0].value;
		if(sysadmin.length < 1) {
			document.getElementsByName("value(kmss.tripartite.sysadmin.names)")[0].value = "sysadmin";
		}
		if(security.length < 1) {
			document.getElementsByName("value(kmss.tripartite.security.names)")[0].value = "secadmin";
		}
		if(auditor.length < 1) {
			document.getElementsByName("value(kmss.tripartite.auditor.names)")[0].value = "secauditor";
		}
	}

	config_addOnloadFuncList(function() {
		config_tripartite_admin_chgEnabled();
		config_init_tripartite_admin();
	});
</script>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" colspan="3">
			<b>
				<label>
					<xform:checkbox property="value(kmss.tripartite.admin.enabled)" onValueChange="config_tripartite_admin_chgEnabled()" showStatus="edit">
						<xform:simpleDataSource value="true">启用三员管理</xform:simpleDataSource>
					</xform:checkbox>
				</label>
			</b>
		</td>
	</tr>
	<tbody id="config_admin"> 
		<tr>
			<td class="td_normal_title" width="15%">三员管理说明：</td>
			<td colspan="2">
				<p>启用三员管理之后，系统将禁用原有的“超级管理员”，同时自动启用“系统管理员”、“安全保密管理员”和“安全审计管理员”。</p>
				<p>启用三员管理之后，集团分级授权将强制禁用</p>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="15%">系统管理员</td>
			<td>
				<xform:text property="value(kmss.tripartite.sysadmin.names)" subject="系统管理员" style="width:85%" showStatus="readOnly" validators="checkAdmin"/><br>
			</td>
			<td rowspan="3" width="20%">初始密码为：password，登录后请及时修改。</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="15%">安全保密管理员</td>
			<td>
				<xform:text property="value(kmss.tripartite.security.names)" subject="安全保密管理员" style="width:85%" showStatus="readOnly" validators="checkAdmin"/><br>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="15%">安全审计管理员</td>
			<td>
				<xform:text property="value(kmss.tripartite.auditor.names)" subject="安全审计管理员" style="width:85%" showStatus="readOnly" validators="checkAdmin"/><br>
			</td>
		</tr>
	</tbody>
</table>

<script type="text/javascript">
	var _validation = $KMSSValidation(document.forms['sysConfigAdminForm']);
	_validation.addValidator('checkAdmin', '同一个人员不能同时授权多个管理员！', function(v, e, o) {
		var current = v.split(/[,;]/); // 当前输入的内容

		var sysadmin = document.getElementsByName("value(kmss.tripartite.sysadmin.names)")[0].value.split(/[,;]/);
		var security = document.getElementsByName("value(kmss.tripartite.security.names)")[0].value.split(/[,;]/);
		var auditor = document.getElementsByName("value(kmss.tripartite.auditor.names)")[0].value.split(/[,;]/);

		var isCheck = true;
		if("value(kmss.tripartite.sysadmin.names)" == e.getAttribute("name")) { // 系统管理员
			isCheck = __check(current, security, auditor);
		} else if("value(kmss.tripartite.security.names)" == e.getAttribute("name")) { // 安全管理员
			isCheck = __check(current, sysadmin, auditor);
		} else if("value(kmss.tripartite.auditor.names)" == e.getAttribute("name")) { // 安全审计员
			isCheck = __check(current, sysadmin, security);
		}

		return isCheck;
	});

	function __check(current, target1, target2) {
		var result = true;
		// 判断当前管理是否在另1内
		for(var i=0; i<current.length; i++) {
			var a = current[i].replace(/(^\s*)|(\s*$)/g, "");
			if(a == "") continue;
			for(var j=0; j<target1.length; j++) {
				var b = target1[j].replace(/(^\s*)|(\s*$)/g, "");
				if(b == "") continue;
				if(a == b) {
					result = false;
					break;
				}
			}
		}
		if(!result) return result;

		// 判断当前管理是否在另2内
		for(var i=0; i<current.length; i++) {
			var a = current[i].replace(/(^\s*)|(\s*$)/g, "");
			if(a == "") continue;
			for(var j=0; j<target2.length; j++) {
				var b = target2[j].replace(/(^\s*)|(\s*$)/g, "");
				if(b == "") continue;
				if(a == b) {
					result = false;
					break;
				}
			}
		}
		return result;
	}
</script>
<%
	}
%>