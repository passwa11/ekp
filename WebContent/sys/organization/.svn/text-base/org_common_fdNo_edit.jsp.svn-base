<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig"%>

<%-- 通用的组织架构编号编辑逻辑 --%>
<%-- 组织架构(机构、部门、人员、岗位、常用群组)编号是否必填 --%>
<%-- 如开启必填后，可以按类型（机构、部门、人员、岗位）自定义编号，前端录入的时候按类型校验唯一性，高级数据导入时同样需校验唯一性 --%>

<% if (new SysOrganizationConfig().isNoRequired()) { %>
<xform:text property="fdNo" validators="required uniqueNo" style="width:90%"></xform:text>
<span class="txtstrong">*</span>
<% } else { %>
<xform:text property="fdNo" style="width:90%"></xform:text>
<% } %>

<script language="JavaScript">
	var _validation = $KMSSValidation(document.forms[0]);

	// 编号校验
	_validation.addValidator(
			'uniqueNo',
			"<bean:message key='organization.error.fdNo.mustUnique' bundle='sys-organization' />",
			function(v, e, o) {
				if (v.length < 1)
					return true;
				var fdId = document.getElementsByName("fdId")[0].value,
					fdName = document.getElementsByName("fdName")[0].value,
					fdOrgType = document.getElementsByName("fdOrgType")[0].value,
					fdNo = document.getElementsByName("fdNo")[0].value;
				return checkNoUnique(fdId, fdName, fdOrgType, fdNo);
			});

	//校验名称是否唯一
	function checkNoUnique(fdId, fdName, fdOrgType, fdNo) {
		var url = encodeURI(Com_Parameter.ResPath
				+ "jsp/ajax.jsp?&serviceName=sysOrgElementService&fdOrgType="
				+ fdOrgType + "&fdId=" + fdId +"&fdName=" + fdName + "&fdNo=" + fdNo + "&_="
				+ new Date());
		var xmlHttpRequest;
		if (window.XMLHttpRequest) { // Non-IE browsers
			xmlHttpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					xmlHttpRequest = false;
				}
			}
		}
		if (xmlHttpRequest) {
			xmlHttpRequest.open("GET", url, false);
			xmlHttpRequest.send();
			var result = xmlHttpRequest.responseText.replace(/\s/g, "")
					.replace(/;/g, "\n");
			if (result != "") {
				return false;
			}
		}
		return true;
	}
</script>