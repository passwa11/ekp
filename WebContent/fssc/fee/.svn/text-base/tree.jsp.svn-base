<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-fee:module.fssc.fee") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    var n1, n2, n3, n4, n5,n6,defaultNode;
    n1 = LKSTree.treeRoot;
    n1.isExpanded = true;
    //类别设置
	n1.AppendURLChild(
		"类别设置",
		"<c:url value="/sys/category/sys_category_main/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.fee.model.FsscFeeTemplate&mainModelName=com.landray.kmss.fssc.fee.model.FsscFeeMain&templateName=fdTemplate&categoryName=docCategory&authReaderNoteFlag=2" />"
	);
    // 模板设置
	defaultNode = n1.AppendCV2Child("<bean:message key="table.fsscFeeTemplate" bundle="fssc-fee" />",
		"com.landray.kmss.fssc.fee.model.FsscFeeTemplate",
		"<c:url value="/fssc/fee/fssc_fee_template/index.jsp?parentId=!{value}&ower=1" />");
	<% if(com.landray.kmss.sys.number.util.NumberResourceUtil.isModuleNumberEnable("com.landray.kmss.fssc.fee.model.FsscFeeMain")){ %>
		n1.AppendURLChild(
			"<bean:message bundle="sys-number" key="sysNumber.config.tree.numberMain"/>",
			"<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.fee.model.FsscFeeMain" />"
		);
	<%} %>
	//流程模板设置
	n1.AppendURLChild(
		"<bean:message key="tree.workflowTemplate" bundle="fssc-fee" />",
		"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.fee.model.FsscFeeTemplate&fdKey=fsscFeeMain" />"
	); 
	//表单模板设置
	n1.AppendURLChild(
		"<bean:message key="tree.xform.def" bundle="sys-xform" />",
		"<c:url value="/sys/xform/sys_form_common_template/index.jsp?fdModelName=com.landray.kmss.fssc.fee.model.FsscFeeTemplate&fdKey=fsscFeeMain&fdMainModelName=com.landray.kmss.fssc.fee.model.FsscFeeMain"/>"
	);
	// 表单存储设置
	n1.AppendURLChild(
		"<bean:message key="sysFormDb.tree.config" bundle="sys-xform" />",
		"<c:url value="/sys/xform/base/sys_form_db_table/index.jsp?fdTemplateModel=com.landray.kmss.fssc.fee.model.FsscFeeTemplate&fdKey=fsscFeeMain&fdModelName=com.landray.kmss.fssc.fee.model.FsscFeeMain"/>"
	);
    // 表单存储设置
	n1.AppendURLChild(
		"<bean:message key="table.fsscFeeExpenseItem" bundle="fssc-fee" />",
		"<c:url value="/fssc/fee/fssc_fee_expense_item/index.jsp"/>"
	);
	// 台账映射
	n1.AppendURLChild(
		"<bean:message key="table.fsscFeeMapp" bundle="fssc-fee" />",
		"<c:url value="/fssc/fee/fssc_fee_mapp/index.jsp"/>"
	);
	/*事前申请_列表自定义*/
   n1.AppendURLChild(
        '${ lfn:message("fssc-fee:py.XuQiuShenQingDan") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.fee.model.FsscFeeMain"/>'); 
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
			if(node.LV2UseURL != null)
				url = Com_Parameter.ContextPath + "fssc/fee/fssc_fee_template/index.jsp" + url.substr(url.indexOf("?"));
			// 设置回调URL，主要用于搜索模板
			url = Com_SetUrlParameter(url, "callback_url", "<c:url value="/fssc/fee/fssc_fee_template/index.jsp"/>");
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
		if (this.OnNodePostClick!=null)
			this.OnNodePostClick(node);
	}else
		this.ExpandNode(node);
}
</template:replace>
</template:include>
