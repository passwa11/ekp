﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
var org, dept, person, post, group, orgInvalid, deptInvalid, postInvalid, personInvalid, groupInvalid;
// 监听组织架构类型切换事件，事件发布来源：sys/organization/org_common_select.jsp
window.parent.$(window.parent.document).on('orgtype.change', function(event, data){
	var node;
	switch(data.type) {
	case "org":
		node = data.available != null ? orgInvalid : org;
		break;
	case "dept":
		node = data.available != null ? deptInvalid : dept;
		break;
	case "post":
		node = data.available != null ? postInvalid : post;
		break;
	case "person":
		node = data.available != null ? personInvalid : person;
		break;
	case "group":
		node = data.available != null ? groupInvalid : group;
		break;
	}
	if(node)
		LKSTree.ClickNode(node);
});
function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-organization" key="organization.moduleName"/>", document.getElementById("treeDiv"));
	LKSTree.ClickNode = Dialog_ClickNode;
	var n1, n2, n3;
	n1 = LKSTree.treeRoot;
	n1.isExpanded = true;
	<kmss:authShow roles="ROLE_SYSORG_DEFAULT">
	//========== 层级架构 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.levelOrg"/>",
		"<c:url value="/sys/organization/sys_org_dept/index.jsp"/>?parent=!{value}&sys_page=true"
	);
	n2.AppendBeanData(
	"organizationTree&fdIsExternal=false&parent=!{value}&orgType="+(ORG_TYPE_ORGORDEPT|ORG_FLAG_BUSINESSALL)+"&sys_page=true", 
	"<c:url value="/sys/organization/sys_org_dept/index.jsp"/>?parent=!{value}&sys_page=true", 
	openListView, true);
	
	/*
	n2.AppendOrgData(
		ORG_TYPE_ORGORDEPT|ORG_FLAG_BUSINESSALL,
		"<c:url value="/sys/organization/sys_org_dept/index.jsp"/>?parent=!{value}",
		openListView
	);
	*/
	
	//========== 常用群组 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/organization/sys_org_group/index.jsp?parentcate="/>"
	);
	n2.AppendBeanData(
		Tree_GetBeanNameFromService('sysOrgGroupCateService', 'hbmParent', 'fdName:fdId'),
		"<c:url value="/sys/organization/sys_org_group/index.jsp?parentCate="/>!{value}"
	);
	//========== 所有架构 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.flatOrg"/>"
	);
	org = n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/organization/sys_org_org/index.jsp?all=true"/>"
	);
	dept = n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/organization/sys_org_dept/index.jsp?all=true"/>"
	);
	post = n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/organization/sys_org_post/index.jsp?all=true"/>"
	);
	person = n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/organization/sys_org_person/index.jsp?all=true"/>"
	);
	group = n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/organization/sys_org_group/index.jsp"/>"
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.locked"/>",
		"<c:url value="/sys/organization/sys_org_person/index.jsp?all=true&locked=true"/>"
	);
	
	<kmss:authShow roles="ROLE_SYSORG_ORG_ADMIN">
	//========== 无效架构 ==========
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.unavailable"/>"
	);
	orgInvalid = n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/organization/sys_org_org/index.jsp?available=0&all=true"/>"
	);
	deptInvalid = n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/organization/sys_org_dept/index.jsp?available=0&all=true"/>"
	);
	postInvalid = n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/organization/sys_org_post/index.jsp?available=0&all=true"/>"
	);
	personInvalid = n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/organization/sys_org_person/index.jsp?available=0&hidetoplist=true&all=true"/>"
	);
	groupInvalid = n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/organization/sys_org_group/index.jsp?available=0"/>"
	);
	</kmss:authShow>
	
	//========== 角色线 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgRoleLine"/>"
	);
	n2.AppendBeanData(
			"sysOrgRoleConfTree&parent=!{value}",
			"<c:url value="/sys/organization/sys_org_role_line/sysOrgRoleLine.do?method=roleTree&fdConfId=!{value}"/>"
		);

	//========== 数据查询 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrg.tree.search.query"/>"
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg"/>",
		2
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgDept"/>",
		2
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPost"/>",
		2
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPerson"/>",
		2
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgGroup"/>",
		2
	);
	
	<kmss:auth requestURL="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg">
	//========== 导入 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrg.tree.transport.in"/>"
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg"/>"
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgDept"/>"
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPost"/>"
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPerson"/>"
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgGroup"/>"
	);
	</kmss:auth>
	<kmss:authShow roles="SYSROLE_ADMIN">
	//========== 数据升级 ==========
	n2 = n1.AppendURLChild("<bean:message bundle="sys-organization" key="sysOrgElement.dataUpdate.title"/>","<c:url value="/sys/organization/sysOrgData_update.jsp" />");
	</kmss:authShow>
	
	<kmss:authShow roles="ROLE_SYSORG_CONFIG">
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrg.tree.config"/>"
	);
	<kmss:auth requestURL="/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do?method=view&fdId=0">
	//========== 通知配置 ==========
	n2.AppendURLChild(
		"<bean:message bundle="sys-oms-notify" key="orgSynchroNotify"/>",
		"<c:url value="/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do?method=view&fdId=0"/>"
	);
	</kmss:auth>
	//========== 角色线配置 ==========
	<kmss:auth requestURL="/sys/organization/sys_org_role/sysOrgRole.do?method=list">
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgRole.common.config"/>",
		"<c:url value="/sys/organization/sys_org_role/index.jsp"/>"
	);
	</kmss:auth>
	<kmss:auth requestURL="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=list">
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgRoleConf"/>",
		"<c:url value="/sys/organization/sys_org_role_conf/index.jsp"/>"
	);
	</kmss:auth>
	
	<kmss:auth requestURL="/sys/organization/sys_org_group_cate/sysOrgGroupCate.do?method=list">
	//========== 角色线类别 ==========
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgRoleConfCate"/>",
		"<c:url value="/sys/organization/sys_org_role_conf_cate/index.jsp"/>"
	);
	</kmss:auth>
	<kmss:auth requestURL="/sys/organization/sys_org_group_cate/sysOrgGroupCate.do?method=list">
	//========== 群组类别 ==========
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgGroupCate"/>",
		"<c:url value="/sys/organization/sys_org_group_cate/index.jsp"/>"
	);
	</kmss:auth>
	<kmss:auth requestURL="/sys/organization/sys_org_org/sysOrgOrg.do?method=updateDeptToOrg">
	//========== 将部门设置为机构 ==========
	n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgOrg.updateDeptToOrg"/>",
		"<c:url value="/sys/organization/sys_org_org/sysOrgOrg_updateDeptToOrg.jsp"/>"
	);
	</kmss:auth>
	
	//========== 可见性配置 ==========
	<kmss:auth requestURL="/sys/organization/sys_organization_visible/sysOrganizationVisible.do?method=list">
	n2.AppendURLChild(
		"<bean:message key="sysOrganizationVisible.config" bundle="sys-organization" />",
		"<c:url value="/sys/organization/sys_organization_visible/sysOrganizationVisibleList.do?method=edit" />"
	);
	</kmss:auth>
	
	//========== 职级配置 ==========
	<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=list">
	n2.AppendURLChild(
		"<bean:message key="sysOrganizationStaffingLevel.config" bundle="sys-organization" />",
		"<c:url value="/sys/organization/sys_organization_staffing_level/index.jsp" />"
	);
	</kmss:auth>
	
	//========== 职级过滤配置 ==========
	<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level_filter/sysOrganizationStaffingLevelFilter.do?method=edit">
	n2.AppendURLChild(
		"<bean:message key="sysOrganizationStaffingLevelFilter.setting" bundle="sys-organization" />",
		"<c:url value="/sys/organization/sys_organization_staffing_level_filter/sysOrganizationStaffingLevelFilter.do?method=edit" />"
	);
	</kmss:auth>
	
	<kmss:auth requestURL="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg">
	//========== 搜索配置 ==========
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrg.tree.search.config"/>"
	);
	n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg"/>"
	);
	n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgDept"/>"
	);
	n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPost"/>"
	);
	n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPerson"/>"
	);
	n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgGroup"/>"
	);
	</kmss:auth>
	
	<kmss:authShow roles="SYSROLE_ADMIN">
	//========== 实时搜索配置 ==========
	n2.AppendURLChild(
		"<bean:message key="sysOrgPerson.config.title" bundle="sys-organization" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.organization.model.SysOrganizationConfig" />"
	);
	
	</kmss:authShow>
	//忘记密码
	n2.AppendURLChild(
		"<bean:message key="sysOrgRetrievePassword.setting" bundle="sys-organization" />",
		"<c:url value="/sys/organization/sys_org_retrieve_password_config/sysOrgRetrievePasswordConfig.do?method=editR" />"
	);
	</kmss:authShow>
	</kmss:authShow>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
function openListView(para){
	var orgName, url;
	try{
		var win = window;
		for(var frameWin = win.parent; frameWin!=null && frameWin!=win; frameWin=win.parent){
			if(frameWin.frames["viewFrame"]!=null){
				url = frameWin.frames["viewFrame"].location.href;
				orgName = url.substring(0, url.indexOf(".do"));
				orgName = orgName.substring(0, orgName.lastIndexOf("/"));
				orgName = orgName.substring(orgName.lastIndexOf("_")+1);
				if(orgName!="org" && orgName!="dept" && orgName!="post" && orgName!="person")
					orgName = null;
				break;
			}
			win = frameWin;
		}
	}catch(e){
		orgName = null;
	}
	if(orgName==null)
		orgName = "dept";
	var url = Com_Parameter.ContextPath+"sys/organization/sys_org_"+orgName+"/index.jsp";
	url = Com_SetUrlParameter(url, "s_path", Tree_GetNodePath(this,">>",this.treeView.treeRoot));
	Com_OpenWindow(Com_SetUrlParameter(url, "parent", this.value), 3);
}

//点击树节点动作，由于角色线成员特殊处理，所以覆写treeview.js的点击方法
function Dialog_ClickNode(node){
	var isHrefAddInfo, path;
	var isActRun = false;
	if(typeof(node)=="number")
		node = Tree_GetNodeByID(this.treeRoot, node);
	if(node == null)
		return;
	if (this.OnNodeQueryClick!=null)
		if (this.OnNodeQueryClick(node)==false)
			return;
	if(node.action==null){
		if((node.isShowCheckBox==true || node.isShowCheckBox==null && this.isShowCheckBox==true) && node.value!=null){
			this.SetNodeChecked(node, this.isMultSel?"reverse":true);
			return;
		}
		var href = typeof(node.parameter)=="string"?new Array(node.parameter):node.parameter;
		if(href!=null && href[0]!=""){
			var url = Com_ReplaceParameter(href[0], node);
			// 角色线成员特殊处理，如果是角色线分类，则不请求后台
			if(!(url.indexOf("sysOrgRoleLine.do") != -1 && "cate" == node.nodeType)){
				if(node.isHrefAddInfo==null)
					isHrefAddInfo = node.treeView.isHrefAddInfo;
				else
					isHrefAddInfo = node.isHrefAddInfo;
				if(isHrefAddInfo){
					var dns = TreeFunc_GetUrlDNS(url);
					if(dns==null || dns==TreeFunc_GetUrlDNS(location.href)){
						var path = Com_GetUrlParameter(location.href, "s_path");
						path = path==null?"":(path+">>");
						url = Com_SetUrlParameter(url, "s_path", path+Tree_GetNodePath(node,">>",node.treeView.treeRoot));
					}
				}
				Com_OpenWindow(url, href[1], href[2]);
				isActRun = true;
			}
		}
	}else{
		if(node.action(node.parameter)==false)
			return;
		isActRun = true;
	}
	if(isActRun){
		this.SetCurrentNode(node);
		if (this.OnNodePostClick!=null)
			this.OnNodePostClick(node);
	}else
		this.ExpandNode(node);
}
<%@ include file="/resource/jsp/tree_down.jsp" %>