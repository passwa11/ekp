<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<select name="f_orgtype" onchange="changeVal(this.value);" style="margin-right: 10px;">
	<option value="org"><bean:message bundle="sys-organization" key="sysOrgElement.org" /></option>
	<option value="dept"><bean:message bundle="sys-organization" key="sysOrgElement.dept" /></option>
	<option value="post"><bean:message bundle="sys-organization" key="sysOrgElement.post" /></option>
	<option value="person"><bean:message bundle="sys-organization" key="sysOrgElement.person" /></option>
	<c:if test="${JsParam.fdFlagDeleted != null}">
	<option value="group"><bean:message bundle="sys-organization" key="sysOrgElement.group" /></option>
	</c:if>
</select>

<script>
	function changeVal(orgName) {
		var available = Com_GetUrlParameter(location.href, "available");
		var all = Com_GetUrlParameter(location.href, "all");
		var parent = Com_GetUrlParameter(location.href, "parent");
		
		var url = Com_Parameter.ContextPath + "sys/organization/sys_org_" + orgName + "/index.jsp";
		url = Com_SetUrlParameter(url, "available", available);
		url = Com_SetUrlParameter(url, "all", all);
		url = Com_SetUrlParameter(url, "parent", parent);
		url = Com_SetUrlParameter(url, "s_path", Com_GetUrlParameter(location.href, "s_path"));
		url = Com_SetUrlParameter(url, "s_css", Com_GetUrlParameter(location.href, "s_css"));
		url = Com_SetUrlParameter(url, "fdFlagDeleted", Com_GetUrlParameter(location.href, "fdFlagDeleted"));
		url = Com_SetUrlParameter(url, "fdImportInfo", Com_GetUrlParameter(location.href, "fdImportInfo"));
		
		// 如果在“所有有效架构”和“所有无效架构”中切换类型，在左则的tree和“当前路径”中需要正确显示当前的架构信息
		// 因为页面使用iframe结构，这里在各个页面中的交互将使用jquery的事件来完成
		if(parent == null && all == "true")
			window.parent.$(window.parent.document).trigger('orgtype.change',{type:orgName, available:available});
		else
			Com_OpenWindow(url, "_self");
	}

	$(function() {
		var url = location.href.toString();
		var orgName = url.substring(0, url.lastIndexOf(".jsp"));
		orgName = orgName.substring(0, orgName.lastIndexOf("/"));
		orgName = orgName.substring(orgName.lastIndexOf("_") + 1);
		$("select[name='f_orgtype']").val(orgName);
	});
</script>