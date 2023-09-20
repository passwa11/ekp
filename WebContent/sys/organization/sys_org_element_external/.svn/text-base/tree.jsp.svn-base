<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sysOrgEco.name" bundle="sys-organization" />",
		document.getElementById("treeDiv")
	);
	var n1, n2;
	n1 = LKSTree.treeRoot;
	n1.AppendBeanData(
		"organizationTree&parent=!{value}&orgType="+(ORG_TYPE_ORGORDEPT|ORG_FLAG_BUSINESSALL)+"&sys_page=true&fdIsExternal=true&eco_type=outer", 
		null,
		openListView, true
	);
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ExpandNode(n1);
	LKSTree.ClickNode(n1);
	n1.parameter = '<c:url value="/sys/organization/sys_org_element_external/index.jsp"/>';
	setTimeout(function() {
		seajs.use(['lui/jquery'], function($) {
			$("#treeDiv table:first a:last").click();
		});
	}, 1000);
}

function openListView(param) {
	var orgName, url;
	if("1710b7f7840755f60ca960544b8bfc4c" == this.value) {
		// 无组织，直接跳到人员列表
		url = Com_Parameter.ContextPath + "sys/organization/sys_org_element_external/sysOrgExternalPerson_index.jsp";
	} else if(this.nodeType == '1') {
		// 组织类型，只能跳到组织列表
		url = Com_Parameter.ContextPath + "sys/organization/sys_org_element_external/sysOrgExternalDept_index.jsp";
	} else {
		try{
			var win = window;
			for(var frameWin = win.parent; frameWin!=null && frameWin!=win; frameWin=win.parent) {
				if(frameWin.frames["viewFrame"]!=null) {
					url = frameWin.frames["viewFrame"].location.href;
					orgName = url.substring(0, url.lastIndexOf(".jsp"));
					orgName = orgName.substring(orgName.lastIndexOf("/") + 1);
					orgName = orgName.substring(14, orgName.lastIndexOf("_"));
					if(orgName!="Dept" && orgName!="Post" && orgName!="Person") orgName = null;
					break;
				}
				win = frameWin;
			}
		} catch(e) {
			orgName = null;
		}
		if(orgName == null) orgName = "Person";
		url = Com_Parameter.ContextPath + "sys/organization/sys_org_element_external/sysOrgExternal" + orgName + "_index.jsp";
	}
	url = Com_SetUrlParameter(url, "s_path", Tree_GetNodePath(this,">>",this.treeView.treeRoot));
	url = Com_SetUrlParameter(url, "parent", this.value);
	url = Com_SetUrlParameter(url, "nodeType", this.nodeType);
	Com_OpenWindow(url, 3);
}

	</template:replace>
</template:include>