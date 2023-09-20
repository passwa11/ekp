<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
var org, dept, person, post, group, roleLine, admin, defaultNode, orgInvalid, deptInvalid, postInvalid, personInvalid, groupInvalid;

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
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-organization" key="sysOrg.address.tree.new"/>",
		document.getElementById("treeDiv")
	);
	LKSTree.ClickNode = Dialog_ClickNode;
	var n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSORG_DEFAULT">
	
	//========== 组织架构一览图 ==========
	n11 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.orgChart"/>", ""
	);
	n11.AppendBeanData(
		"organizationTree&fdIsExternal=false&parent=!{value}&orgType="+(ORG_TYPE_ORGORDEPT|ORG_FLAG_BUSINESSALL), 
		"<c:url value="/sys/organization/sys_org_chart/index.jsp"/>?parent=!{value}", 
		openChartView, true
	);
	
	//========== 层级架构 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.levelOrg"/>",
		"<c:url value="/sys/organization/sys_org_dept/index.jsp"/>?parent=!{value}&sys_page=true"
	);
	n2.AppendBeanData(
		"organizationTree&fdIsExternal=false&parent=!{value}&orgType="+(ORG_TYPE_ORGORDEPT|ORG_FLAG_BUSINESSALL)+"&sys_page=true&eco_type=inner", 
		"<c:url value="/sys/organization/sys_org_dept/index.jsp"/>?parent=!{value}&sys_page=true", 
		openListView, true
	);
	
	//========== 常用群组 ==========
	n3 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>"
	);
	
	//========== 所有有效架构 ==========
	n4 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.all.effective.organizational"/>"
	);
	
	//========== 所有无效架构 ==========
	n5 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.all.invalid.organizational"/>"
	);
	
	//========== 人员激活 ==========
	n12 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.personnel.activation"/>"
	);
	
	//========== 角色线 ==========
	n6 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrg.address.roleLine"/>"
	);

	//========== 矩阵组织 ==========
	n13 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/>"
	);
	
	//========== 通用岗位 ==========
	<kmss:auth requestURL="/sys/organization/sys_org_role/sysOrgRole.do?method=list">
	n7 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.common.sysOrgRole"/>",
		"<c:url value="/sys/organization/sys_org_role/index.jsp"/>"
	);
	</kmss:auth>
	
	//========== 角色模拟器 ==========
	n8 = n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.role.simulator"/>",
		"<c:url value="/sys/organization/sys_org_role/sysOrgRole_simulator.jsp" />"
	);
	
	
	//================================== 二级目录 =====================================//	
	
	
	<%-- 职级(职务)配置    二级目录 --%>
	<!-- 群组类别 -->
	<kmss:auth requestURL="/sys/organization/sys_org_group_cate/sysOrgGroupCate.do?method=list">
	n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgGroupCate"/>",
		"<c:url value="/sys/organization/sys_org_group_cate/index.jsp"/>"
	);
	</kmss:auth>
	<!-- 常用群组定义 -->
	n9 = n3.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/organization/sys_org_group/index.jsp?parentcate="/>"
	);
	n9.AppendBeanData(
		Tree_GetBeanNameFromService('sysOrgGroupCateService', 'hbmParent', 'fdName:fdId'),
		"<c:url value="/sys/organization/sys_org_group/index.jsp?parentCate="/>!{value}"
	);
	
	<%-- 所有有效架构    二级目录 --%>
	<!-- 机构 -->
	org = n4.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/organization/sys_org_org/index.jsp?all=true"/>"
	);
	<!-- 部门 -->
	dept = n4.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/organization/sys_org_dept/index.jsp?all=true"/>"
	);
	<!-- 岗位 -->
	post = n4.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/organization/sys_org_post/index.jsp?all=true"/>"
	);
	<!-- 员工 -->
	admin = person = defaultNode = n4.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/organization/sys_org_person/index.jsp?all=true"/>"
	);
	<!-- 常用分组-->
	group = n4.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/organization/sys_org_group/index.jsp"/>"
	);
	<!-- 锁定的员工 -->
	n4.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.locked.account"/>",
		"<c:url value="/sys/organization/sys_org_person/index.jsp?all=true&locked=true"/>"
	);
	
	<%-- 所有无效架构    二级目录 --%>
	<!-- 机构 -->
	orgInvalid = n5.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/organization/sys_org_org/index.jsp?available=0&all=true"/>"
	);
	<!-- 部门 -->
	deptInvalid = n5.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/organization/sys_org_dept/index.jsp?available=0&all=true"/>"
	);
	<!-- 岗位 -->
	postInvalid = n5.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/organization/sys_org_post/index.jsp?available=0&all=true"/>"
	);
	<!-- 员工 -->
	personInvalid = n5.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/organization/sys_org_person/index.jsp?available=0&hidetoplist=true&all=true"/>"
	);
	<!-- 常用分组-->
	groupInvalid = n5.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/organization/sys_org_group/index.jsp?available=0"/>"
	);
	
	<!-- 待激活人员 -->
	n12.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.personnel.activation.no"/>",
		"<c:url value="/sys/organization/sys_org_person/activation_index.jsp?available=0"/>"
	);
	
	<!-- 已激活人员 -->
	n12.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.personnel.activation.yes"/>",
		"<c:url value="/sys/organization/sys_org_person/activation_index.jsp?available=1"/>"
	);


	<%-- 角色线    二级目录 --%>
	<!-- 角色线类别 -->
	<kmss:auth requestURL="/sys/organization/sys_org_role_conf_cate/sysOrgRoleConfCate.do?method=add">
	n6.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgRoleConfCate"/>",
		"<c:url value="/sys/organization/sys_org_role_conf_cate/index.jsp"/>"
	);
	</kmss:auth>
	<!-- 角色线配置 -->
	<kmss:auth requestURL="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=list">
	roleLine = n6.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgRoleConf"/>",
		"<c:url value="/sys/organization/sys_org_role_conf/index.jsp"/>"
	);
	</kmss:auth>
	<!-- 角色线成员 -->
	n10 = n6.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgRoleLine"/>"
	);
	n10.AppendBeanData(
		"sysOrgRoleConfTree&parent=!{value}",
		"<c:url value="/sys/organization/sys_org_role_line/sysOrgRoleLine.do?method=roleTree&fdConfId=!{value}"/>"
	);

	<kmss:authShow roles="ROLE_SYSORG_MATRIX_ADMIN">
	<!-- 矩阵分类 -->
	n13.AppendURLChild(
		"<bean:message bundle="sys-organization" key="table.sysOrgMatrixCate"/>",
		"<c:url value="/sys/organization/sys_org_matrix_cate/index.jsp"/>"
	);
	</kmss:authShow>
	<!-- 矩阵配置 -->
	n14 = n13.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgMatrix.config"/>",
		"<c:url value="/sys/organization/sys_org_matrix/index.jsp?parentcate="/>"
	);
	n14.AppendBeanData(
		Tree_GetBeanNameFromService('sysOrgMatrixCateService', 'hbmParent', 'fdName:fdId'),
		"<c:url value="/sys/organization/sys_org_matrix/index.jsp?parentCate="/>!{value}"
	);
	<!-- 矩阵模拟器 -->
	n13.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator"/>",
		"<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix_simulator.jsp"/>"
	);
	</kmss:authShow>

	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n3);
	LKSTree.ExpandNode(n4);
	LKSTree.ExpandNode(n5);
	LKSTree.ExpandNode(n6);
	LKSTree.ExpandNode(n12);
	LKSTree.ExpandNode(n13);
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
				orgName = url.substring(0, url.lastIndexOf(".jsp"));
				orgName = orgName.substring(0, orgName.lastIndexOf("/"));
				orgName = orgName.substring(orgName.lastIndexOf("_") + 1);
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

function openChartView(){
	var url = Com_Parameter.ContextPath+"sys/organization/sys_org_chart/index.jsp";
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
						path = path==null?"":(path+"　>　");
						url = Com_SetUrlParameter(url, "s_path", path+Tree_GetNodePath(node,"　>　",node.treeView.treeRoot));
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

seajs.use(['lui/topic'], function(topic) {
	topic.subscribe('sys.profile.moduleMain.loaded',function(args){
		switch(args.key){
			case 'admin' : 
			case 'person' : {
				if(person) LKSTree.ClickNode(person);
				break;
			}
			case 'dept' : {
				if(dept) LKSTree.ClickNode(dept);
				break;
			}
			case 'org' : {
				if(org) LKSTree.ClickNode(org);
				break;
			}
			case 'post' : {
				if(post) LKSTree.ClickNode(post);
				break;
			}
			case 'group' : {
				if(group) LKSTree.ClickNode(group);
				break;
			}
			case 'roleLine' : {
				if(roleLine) LKSTree.ClickNode(roleLine);
				break;
			}
			default : {
				LKSTree.ClickNode(defaultNode);
			}
		}
	});
});
	</template:replace>
</template:include>