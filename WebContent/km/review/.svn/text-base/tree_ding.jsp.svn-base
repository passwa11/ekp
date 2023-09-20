<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//设置为以钉钉浏览器模式打开
	request.setAttribute("dingPcForce", "true");
%>
<template:include file="/sys/profile/resource/template/tree.jsp" bodyClass='luiReviewMngTree'>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/review/km_review_ui/dingSuit/css/tree.css?s_cache=${LUI_Cache }"/>
	</template:replace>
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
	LKSTree.SetCurrentNode = Dialog_SetCurrentNode;
	LKSTree.DrawNodeInnerHTML = Dialog_DrawNodeInnerHTML
	
	n1.isExpanded = true;
	//=========模块设置========
	//类别设置
	<%-- n1.AppendURLChild(
		"<bean:message key="kmReview.tree.categorySet" bundle="km-review" />",
		"<c:url value="/km/review/sysCategoryMain_tree_ding.jsp?modelName=com.landray.kmss.km.review.model.KmReviewTemplate&mainModelName=com.landray.kmss.km.review.model.KmReviewMain&templateName=fdTemplate&categoryName=docCategory&authReaderNoteFlag=2" />"
	); --%>
	
	// 	设置
	defaultNode = n1.AppendCV2Child("<bean:message key="table.kmReviewTemplate" bundle="km-review" />",
		"com.landray.kmss.km.review.model.KmReviewTemplate",
		"<c:url value="/km/review/km_review_template/index_ding.jsp?parentId=!{value}&ower=1" />");
	//超管或者没有开启集团分级权限或者没有开启钉钉审批高级版出现,否则隐藏
	<% if(UserUtil.getKMSSUser(request).isAdmin() 
			|| !"true".equals(ResourceUtil.getKmssConfigString("kmss.area.enabled")) 
			|| "false".equals(SysFormDingUtil.getEnableDing())){ %>
		<kmss:authShow roles="ROLE_KMREVIEW_SETTING">
		n2 = n1.AppendURLChild(
			"<bean:message bundle="km-review" key="kmReview.tree.modelSet"/>"
		);
		n2.isExpanded = true;
		//通用编号机制
		<% if(com.landray.kmss.sys.number.util.NumberResourceUtil.isModuleNumberEnable("com.landray.kmss.km.review.model.KmReviewMain")){ %>
			n2.AppendURLChild(
				"<bean:message bundle="sys-number" key="sysNumber.config.tree.numberMain"/>",
				"<c:url value="/sys/number/sys_number_main/index_ding.jsp?modelName=com.landray.kmss.km.review.model.KmReviewMain" />"
			);
			<%} %>
		//通用流程模板设置
		n2.AppendURLChild(
			"<bean:message key="tree.workflowTemplate" bundle="km-review" />",
			"<c:url value="/sys/lbpmservice/support/lbpm_template/index_ding.jsp?fdModelName=com.landray.kmss.km.review.model.KmReviewTemplate&fdKey=reviewMainDoc" />"
		); 
		//表单模板设置
		n2.AppendURLChild(
			"<bean:message key="tree.xform.def" bundle="sys-xform" />",
			"<c:url value="/sys/xform/sys_form_common_template/index_ding.jsp?fdModelName=com.landray.kmss.km.review.model.KmReviewTemplate&fdKey=reviewMainDoc&fdMainModelName=com.landray.kmss.km.review.model.KmReviewMain"/>"
		);
		// 表单存储设置
		n2.AppendURLChild(
			"<bean:message key="sysFormDb.tree.config" bundle="sys-xform" />",
			"<c:url value="/sys/xform/base/sys_form_db_table/index_ding.jsp?fdTemplateModel=com.landray.kmss.km.review.model.KmReviewTemplate&fdKey=reviewMainDoc&fdModelName=com.landray.kmss.km.review.model.KmReviewMain"/>"
		);
	
		// 模块通知机制设置--参数设置
	<%-- 	n2.AppendURLChild(
			"<bean:message key="kmReview.config.notify" bundle="km-review" />",
			"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.km.review.model.KmReviewConfigNotify&flag=ding" />"
		); --%>
	
		<%--//搜索设置
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
		</kmss:ifModuleExist>--%>
	
		</kmss:authShow>
	<%} %>
	//=========回收站========
	<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.km.review.model.KmReviewMain")) { %>
	n6 = n1.AppendURLChild("<bean:message key="module.sys.recycle" bundle="sys-recycle" />","<c:url value="/km/review/km_review_ui/sysRecycleBox.jsp" />");	
	<% } %>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	setTimeout(function(){
		LKSTree.ClickNode(defaultNode);
	},300);
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
				url = Com_Parameter.ContextPath + "km/review/km_review_ui/dingSuit/tree_template.jsp" + url.substr(url.indexOf("?"));
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
			node.parameter = "<c:url value="/sys/xform/include/sysFormModifiedLog_index.jsp?fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate"/>";
			Dialog_LogNode_Action(node);
			this.SetCurrentNode(node);
		} else if (Dialog_LogNode_IsLoadLog(node,"lbpmTemplateChangeLog")) {
			this.ExpandNode(node);
			node.parameter = "<c:url value="/sys/lbpmservice/changelog/lbpmTemplateChangeLog_index.jsp?fdTemplateModelName=com.landray.kmss.km.review.model.KmReviewTemplate"/>";
			Dialog_LogNode_Action(node);
			this.SetCurrentNode(node);
		} else if (Dialog_LogNode_IsLoadLog(node,"lbpmPrivilegeLog")) {
			this.ExpandNode(node);
			<kmss:authShow roles="ROLE_KMREVIEW_CATEGORY_MAINTAINER">
				node.parameter = "<c:url value="/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog_list.jsp?fdModelName=com.landray.kmss.km.review.model.KmReviewTemplate"/>";
				Dialog_LogNode_Action(node);
				this.SetCurrentNode(node);
			</kmss:authShow>
		}else {
			this.ExpandNode(node);
		}
	}
}
//override 增加选中效果
function Dialog_SetCurrentNode(node){
	if(typeof(node)=="number")
		node = Tree_GetNodeByID(this.treeRoot, node);
	if(node == null)
		return;
	if (this.currentNodeID==node.id)
		return;
	var now;
	var indent_level;
	var element;
	var CurNode;
	if (this.currentNodeID==-1){
		this.currentNodeID=node.id;
	}else{
		CurNode = Tree_GetNodeByID(this.treeRoot,this.currentNodeID);
		if(CurNode==null){
			this.currentNodeID=node.id;
		}else{
			now = CurNode;
			for(indent_level = 0;now.parent != null;now = now.parent)
				indent_level++;
			element = document.getElementById("TVN_"+this.currentNodeID);
			this.currentNodeID=node.id;
			Tree_SetNodeHTMLDirty(CurNode);
			if(element!=null){
				element.className=""
				element.rows[0].cells[1].className = "";
			}
		}
	}
	now = Tree_GetNodeByID(this.treeRoot,this.currentNodeID);
	for(indent_level = 0;now.parent != null;now = now.parent){
		if(!now.parent.isExpanded)
			this.ExpandNode(now.parent.id);
		indent_level++;
	}
	element = document.getElementById("TVN_"+this.currentNodeID);
	
	Tree_SetNodeHTMLDirty(node);
	
	if(element!=null){
		element.className = "Table_TreeNode_Current";
		element.rows[0].cells[1].className = "TVN_TreeNode_Current";
	}
}
//override 增加选中效果
function Dialog_DrawNodeInnerHTML(node, indent_level)
{
	var Result;
	var isEnd = false;
	if (this.OnNodeQueryDraw!=null){
		Result = this.OnNodeQueryDraw(node)
		if (typeof(Result)=="string")
			isEnd = true;
	}
	if(!isEnd){
		Result = "<table id='TVN_"+node.id+"'" + (this.currentNodeID==node.id?"class=Table_TreeNode_Current":"") + " cellpadding=0 cellspacing=0 border=0><tr>"
			+"<td valign=middle nowrap>"+this.DrawNodeIndentHTML(node, indent_level)+"</td>"
			+"<td valign=middle nowrap "+(this.currentNodeID==node.id?"class=TVN_TreeNode_Current":"")+">";
		if((node.isShowCheckBox==true || node.isShowCheckBox==null && this.isShowCheckBox==true) && node.value!=null)
			var ChkStr = "<input onClick='"+this.refName+".SelectNode("+node.id+")' type="+(this.isMultSel?"checkbox":"radio")
				+" id='CHK_"+node.id+"' value=\""+Com_HtmlEscape(node.value)+"\" name=List_Selected "+(node.isChecked?"Checked":"")+">";
		else
			var ChkStr = "";
		Result += "<a lks_nodeid="+node.id+" title=\""+Com_HtmlEscape(node.title)+"\" href=\"javascript:void(0)\" onClick=\""+this.refName+".ClickNode("+node.id+");\"";
		if(this.DblClickNode!=null)
			Result += " ondblclick=\""+this.refName+".DblClickNode("+node.id+");\"";
		Result += ">"+ChkStr+Com_HtmlEscape(node.text)+"</a>";
		Result += "</td></tr></table>";
		if (this.OnNodePostDraw!=null){
			var tmpStr = this.OnNodePostDraw(node,Result)
			if (typeof(tmpStr)=="string")
				Result = tmpStr;
		}
	}
	if(TREENODESTYLE.isOneoff)
		Tree_ResumeStyle();
	return Result;
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