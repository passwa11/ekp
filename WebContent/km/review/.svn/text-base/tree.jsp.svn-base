<%@page import="com.landray.kmss.km.review.model.KmReviewConfigNotify"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="kmReview.tree.title" bundle="km-review"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5,n6,defaultNode;
	n1 = LKSTree.treeRoot;
	LKSTree.ClickNode = Dialog_ClickNode;
	
	n1.isExpanded = true;
	//=========模块设置========
	//类别设置
	n1.AppendURLChild(
		"<bean:message key="kmReview.tree.categorySet" bundle="km-review" />",
		"<c:url value="/sys/category/sys_category_main/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.km.review.model.KmReviewTemplate&mainModelName=com.landray.kmss.km.review.model.KmReviewMain&templateName=fdTemplate&categoryName=docCategory&authReaderNoteFlag=2" />"
	);
	
	// 模板设置
	defaultNode = n1.AppendCV2Child("<bean:message key="table.kmReviewTemplate" bundle="km-review" />",
		"com.landray.kmss.km.review.model.KmReviewTemplate",
		"<c:url value="/km/review/km_review_template/index.jsp?parentId=!{value}&ower=1" />");
	
	<kmss:authShow roles="ROLE_KMREVIEW_SETTING">
	n2 = n1.AppendURLChild(
		"<bean:message bundle="km-review" key="kmReview.tree.modelSet"/>"
	);
	n2.isExpanded = true;
	<% if(com.landray.kmss.sys.number.util.NumberResourceUtil.isModuleNumberEnable("com.landray.kmss.km.review.model.KmReviewMain")){ %>
		n2.AppendURLChild(
			"<bean:message bundle="sys-number" key="sysNumber.config.tree.numberMain"/>",
			"<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.km.review.model.KmReviewMain" />"
		);
		<%} %>
	//流程模板设置
	n2.AppendURLChild(
		"<bean:message key="tree.workflowTemplate" bundle="km-review" />",
		"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.km.review.model.KmReviewTemplate&fdKey=reviewMainDoc" />"
	); 
	//表单模板设置
	n2.AppendURLChild(
		"<bean:message key="tree.xform.def" bundle="sys-xform" />",
		"<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.km.review.model.KmReviewTemplate&fdKey=reviewMainDoc&fdMainModelName=com.landray.kmss.km.review.model.KmReviewMain"/>"
	);
	// 表单存储设置
	n2.AppendURLChild(
		"<bean:message key="sysFormDb.tree.config" bundle="sys-xform" />",
		"<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.km.review.model.KmReviewTemplate&fdKey=reviewMainDoc&fdModelName=com.landray.kmss.km.review.model.KmReviewMain"/>"
	);

	// 模块通知机制设置
	n2.AppendURLChild(
		"<bean:message key="kmReview.config.notify" bundle="km-review" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.km.review.model.KmReviewConfigNotify" />"
	);
	
	//展示设置
	<kmss:authShow roles="ROLE_KMREVIEW_SETTING">
	n2.AppendURLChild(
		"<bean:message key="sys.showConfig" bundle="sys-profile" />",
		"<c:url value="/sys/profile/showConfig.do?method=edit&modelName=com.landray.kmss.km.review.model.KmReviewShowConfig" />"
	);
	</kmss:authShow>
	//搜索设置
	<kmss:ifModuleExist path="/sys/search/">
		<c:import url="/sys/search/search_tree.jsp" charEncoding="UTF-8">
			<c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate"></c:param>
			<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain"></c:param>
			<c:param name="fdKey" value="reviewMainDoc"></c:param>
			<c:param name="pNodeName" value="${lfn:message('km-review:search.config.tree.title') }"></c:param>
			<c:param name="pNode" value="n2"></c:param>
			<c:param name="showCate" value="true"></c:param>
		</c:import>
	</kmss:ifModuleExist>
	//列表显示设置
	n2.AppendURLChild(
		"<bean:message bundle="km-review" key="kmReviewMain.listShow"/>",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.review.model.KmReviewMain"/>"
	);
	</kmss:authShow>
	n3 = n1.AppendURLChild("<bean:message key="tree.sysCategory.maintains" bundle="sys-category" />")
	n3.authType="01";
	<kmss:authShow roles="ROLE_KMREVIEW_OPTALL">
	n3.authRole="optAll";
	</kmss:authShow>
	
	<%if(new KmReviewConfigNotify().getEnableDbcenterEcharts().equals("true")){ %>
		<kmss:ifModuleExist path="/dbcenter/echarts/">
			<kmss:authShow roles="ROLE_DBCENTERECHARTS_DEFAULT">
				<kmss:authShow roles="ROLE_KMREVIEW_SETTING">
				<c:import url="/dbcenter/echarts/application/navTree/tree.jsp" charEncoding="UTF-8">
					<c:param name="mainModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate"></c:param>
					<c:param name="fdKey" value="kmReviewMainDoc"></c:param>
					<c:param name="ptNode" value="n2"></c:param>
				</c:import>
				</kmss:authShow>
			</kmss:authShow>
		</kmss:ifModuleExist>
	<%} %>
	
	n3.AppendCategoryDataWithAdmin ("com.landray.kmss.km.review.model.KmReviewTemplate","<c:url value="/km/review/km_review_main/kmReviewMain_list_index.jsp?categoryId=!{value}"/>","<c:url value="/km/review/km_review_main/kmReviewMain_list_index.jsp?type=category&categoryId=!{value}"/>");
	
	<!-- 流程模板特权人变更日志 -->
	n3 = n1.AppendURLChild("<bean:message key="module.sys.lbpmservice.lbpmPrivilegeLog.support" bundle="sys-lbpmservice-support" />")
	n3.authType="01";
	n3.lbpmPrivilegeLog = true;
	<kmss:authShow roles="ROLE_KMREVIEW_CATEGORY_MAINTAINER">
	n3.authRole="optAll";
	</kmss:authShow>
	n3.AppendCategoryDataWithAdmin ("com.landray.kmss.km.review.model.KmReviewTemplate","<c:url value="/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog_list.jsp?fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&fdModelName=com.landray.kmss.km.review.model.KmReviewMain&categoryId=!{value}"/>","<c:url value="/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog_list.jsp?type=category&fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&fdModelName=com.landray.kmss.km.review.model.KmReviewMain&categoryId=!{value}"/>");
	
	
	<!-- 表单修改日志start  -->
	n3 = n1.AppendURLChild("<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.tree.title"/>")
	n3.authType="01";
	n3.xFormLog = true;
	<kmss:authShow roles="ROLE_KMREVIEW_CATEGORY_MAINTAINER">
	n3.authRole="optAll";
	</kmss:authShow>
	n3.AppendCategoryDataWithAdmin ("com.landray.kmss.km.review.model.KmReviewTemplate","<c:url value="/sys/xform/include/sysFormModifiedLog_index.jsp?fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&categoryId=!{value}"/>","<c:url value="/sys/xform/include/sysFormModifiedLog_index.jsp?type=category&fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&categoryId=!{value}"/>");
	<!-- 表单修改日志end -->
	
	<!-- 流程模板变更日志start -->
	n3 = n1.AppendURLChild("<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.tree.title"/>")
	n3.authType="01";
	n3.lbpmTemplateChangeLog = true;
	<kmss:authShow roles="ROLE_KMREVIEW_CATEGORY_MAINTAINER">
	n3.authRole="optAll";
	</kmss:authShow>
	n3.AppendCategoryDataWithAdmin ("com.landray.kmss.km.review.model.KmReviewTemplate","<c:url value="/sys/lbpmservice/changelog/lbpmTemplateChangeLog_index.jsp?fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&categoryId=!{value}"/>","<c:url value="/sys/lbpmservice/changelog/lbpmTemplateChangeLog_index.jsp?type=category&fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&categoryId=!{value}"/>");
	<!-- 流程模板变更日志end -->
	
	//所有流程
	n2 = n1.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.processMoniter" bundle="sys-lbpmmonitor"/>"
	);
	 
	//异常的流程
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.tree.errorFlow"/>",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=21&fdType=error&type=category&modelName=com.landray.kmss.km.review.model.KmReviewMain&fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&categoryId=!{value}" />"
	);
	
	//处理人无效的流程
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.tree.notValidFlow"/>",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getInvalidHandler&type=category&modelName=com.landray.kmss.km.review.model.KmReviewMain&fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&categoryId=!{value}" />"
	);
	
	//========== 接口调用的流程 ==========
	n3 = n2.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.interfacelog.infolog" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sys_interface_log/sysLbpmMonitor_interfaceLog.jsp?modelName=com.landray.kmss.km.review.model.KmReviewMain&fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&categoryId=!{value}" />"
	);
	
	
	//=========回收站========
	<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.km.review.model.KmReviewMain")) { %>
	n6 = n1.AppendURLChild("<bean:message key="module.sys.recycle" bundle="sys-recycle" />","<c:url value="/km/review/km_review_ui/sysRecycleBox.jsp" />");	
	<% } %>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	setTimeout(function(){
		LKSTree.ClickNode(defaultNode);
	},100);
}
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
			// 判断是否有模板可阅读权限
			<kmss:authShow roles="ROLE_KMREVIEW_TEMPLATE_VIEW">
			if(node.LV2UseURL != null)
				url = Com_Parameter.ContextPath + "km/review/tree_template.jsp" + url.substr(url.indexOf("?"));
			</kmss:authShow>
			// 设置回调URL，主要用于搜索模板
			url = Com_SetUrlParameter(url, "callback_url", "<c:url value="/km/review/template_search.jsp"/>");
			if(node.isHrefAddInfo==null)
				isHrefAddInfo = node.treeView.isHrefAddInfo;
			else
				isHrefAddInfo = node.isHrefAddInfo;
			if(isHrefAddInfo){
				var dns = TreeFunc_GetUrlDNS(url);
				if(dns==null || dns==TreeFunc_GetUrlDNS(location.href)){
					var path = Com_GetUrlParameter(location.href, "s_path");
					path = path==null?"":(path+">>");
					if(path!=""&&path!=null){
					url = Com_SetUrlParameter(url, "s_path", path+Tree_GetNodePathAnother(node,"　>　",node.treeView.treeRoot,true));
					}else{
					url = Com_SetUrlParameter(url, "s_path", path+Tree_GetNodePath(node,"　>　",node.treeView.treeRoot));
					}
				}
			}
			Com_OpenWindow(url, href[1], href[2]);
			isActRun = true;
		}
	}else{
		if(node.action(node.parameter)==false)
			return;
		isActRun = true;
	}
	if(isActRun){
		this.SetCurrentNode(node);
		if(Dialog_LogNode_IsLoadLog(node)) {
			this.ExpandNode(node);
		}
		if (this.OnNodePostClick!=null)
			this.OnNodePostClick(node);
	}else {
		if(Dialog_LogNode_IsLoadLog(node,"xFormLog")) {
			this.ExpandNode(node);
            <kmss:authShow roles="ROLE_KMREVIEW_CATEGORY_MAINTAINER">
                node.parameter = "<c:url value="/sys/xform/include/sysFormModifiedLog_index.jsp?fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&categoryId=!{value}"/>";
                if (node.nodeType === "CATEGORY") {
                    node.parameter += "&nodeType=category";
                }
                if (node.nodeType === "TEMPLATE") {
                    node.parameter += "&nodeType=template";
                }
                Dialog_LogNode_Action(node);
                this.SetCurrentNode(node);
            </kmss:authShow>
		} else if (Dialog_LogNode_IsLoadLog(node,"lbpmTemplateChangeLog")) {
			this.ExpandNode(node);
            <kmss:authShow roles="ROLE_KMREVIEW_CATEGORY_MAINTAINER">
                node.parameter = "<c:url value="/sys/lbpmservice/changelog/lbpmTemplateChangeLog_index.jsp?fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&categoryId=!{value}"/>";
                if (node.nodeType === "CATEGORY") {
                    node.parameter += "&nodeType=category";
                }
                if (node.nodeType === "TEMPLATE") {
                    node.parameter += "&nodeType=template";
                }
                Dialog_LogNode_Action(node);
                this.SetCurrentNode(node);
            </kmss:authShow>
		} else if (Dialog_LogNode_IsLoadLog(node,"lbpmPrivilegeLog")) {
			this.ExpandNode(node);
			<kmss:authShow roles="ROLE_KMREVIEW_CATEGORY_MAINTAINER">
				node.parameter = "<c:url value="/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog_list.jsp?fdModelName=com.landray.kmss.km.review.model.KmReviewMain"/>";
				if (node.nodeType === "CATEGORY") {
					node.parameter += "&nodeType=category";
				}
				if (node.nodeType === "TEMPLATE") {
					node.parameter += "&nodeType=template";
				}
				Dialog_LogNode_Action(node);
				this.SetCurrentNode(node);
			</kmss:authShow>
		}else {
			this.ExpandNode(node);
		}
	}
}

function Dialog_LogNode_IsLoadLog(node,type){
	if (!type) {
		return true;
	}
	var parent = node.parent;
	<kmss:authShow roles="ROLE_KMREVIEW_CATEGORY_MAINTAINER">
	parent = node;
	</kmss:authShow>
	while(parent != null) {
		if (typeof parent[type] != "undefined") {
			return true;
		}
		parent = parent.parent;
	}
	return false;
}

function Dialog_LogNode_Action(node){
	var href = typeof(node.parameter)=="string"?new Array(node.parameter):node.parameter;
	if(href!=null && href[0]!=""){
		var url = Com_ReplaceParameter(href[0], node);
		if(node.isHrefAddInfo==null)
			isHrefAddInfo = node.treeView.isHrefAddInfo;
		else
			isHrefAddInfo = node.isHrefAddInfo;
		if(isHrefAddInfo){
			var dns = TreeFunc_GetUrlDNS(url);
			if(dns==null || dns==TreeFunc_GetUrlDNS(location.href)){
				var path = Com_GetUrlParameter(location.href, "s_path");
				path = path==null?"":(path+">>");
				if(path!=""&&path!=null){
				url = Com_SetUrlParameter(url, "s_path", path+Tree_GetNodePathAnother(node,"　>　",node.treeView.treeRoot,true));
				}else{
				url = Com_SetUrlParameter(url, "s_path", path+Tree_GetNodePath(node,"　>　",node.treeView.treeRoot));
				}
			}
		}
		Com_OpenWindow(url, href[1], href[2]);
		isActRun = true;
	}
}

</template:replace>
</template:include>