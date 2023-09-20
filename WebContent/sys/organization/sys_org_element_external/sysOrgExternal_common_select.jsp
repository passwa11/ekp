<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<select name="f_orgtype" onchange="changeVal(this.value);" style="margin-right: 10px;">
	<c:if test="${param.parent == null}">
		<%-- 组织类型 --%>
		<option value="orgType"><bean:message bundle="sys-organization" key="sysOrgEco.type" /></option>
	</c:if>
	<c:if test="${'1' eq param.nodeType or param.parent == null}">
		<%-- 组织 --%>
		<option value="Dept"><bean:message bundle="sys-organization" key="sysOrgElementExternal.dept" /></option>
	</c:if>
		<%-- 子组织 --%>
		<option value="subDept"><bean:message bundle="sys-organization" key="sysOrgElementExternal.subDept" /></option>
		<%-- 岗位 --%>
		<option value="Post"><bean:message bundle="sys-organization" key="sysOrgElement.post" /></option>
		<%-- 人员 --%>
		<option value="Person"><bean:message bundle="sys-organization" key="sysOrgElement.person" /></option>
</select>

<script>
	function changeVal(orgName) {
		var parent = Com_GetUrlParameter(location.href, "parent");
		var url = Com_Parameter.ContextPath;
		if("orgType"==orgName){
			url += "sys/organization/sys_org_element_external/index.jsp";
		}else if("subDept"==orgName){
			url += "sys/organization/sys_org_element_external/sysOrgExternalDept_index.jsp";
			//只查询子组织
			url = Com_SetUrlParameter(url, "subType", "4");
		}else if("Dept"==orgName){
			url += "sys/organization/sys_org_element_external/sysOrgExternal" + orgName + "_index.jsp";
			//只查询组织
			url = Com_SetUrlParameter(url, "subType", "2");
		}else{
			url += "sys/organization/sys_org_element_external/sysOrgExternal" + orgName + "_index.jsp";
		}
		url = Com_SetUrlParameter(url, "parent", parent);
		url = Com_SetUrlParameter(url, "nodeType", '${param.nodeType}');
		url = Com_SetUrlParameter(url, "s_path", Com_GetUrlParameter(location.href, "s_path"));
		url = Com_SetUrlParameter(url, "s_css", Com_GetUrlParameter(location.href, "s_css"));

		// 如果在“所有有效架构”和“所有无效架构”中切换类型，在左则的tree和“当前路径”中需要正确显示当前的架构信息
		// 因为页面使用iframe结构，这里在各个页面中的交互将使用jquery的事件来完成
		// if(parent == null)
		// 	window.parent.$(window.parent.document).trigger('orgtype.change',{type:orgName});
		// else
		Com_OpenWindow(url, "_self");
	}

	$(function() {
		var url = location.href.toString();
		var subType = Com_GetUrlParameter(location.href, "subType");
		var orgName = url.substring(0, url.lastIndexOf(".jsp"));
		orgName = orgName.substring(orgName.lastIndexOf("/") + 1);
		if(orgName.length>14){
			if(subType){
				if(subType=='2'){
					$("select[name='f_orgtype']").val('Dept');
				}else{
					$("select[name='f_orgtype']").val('subDept');
				}
			}else{
				orgName = orgName.substring(14, orgName.lastIndexOf("_"));
				$("select[name='f_orgtype']").val(orgName);
				var selected = $("select[name='f_orgtype']").val();
				if(selected==null && orgName=='Dept'){
					$("select[name='f_orgtype']").val('subDept');
				}

			}
		}

	});
</script>
