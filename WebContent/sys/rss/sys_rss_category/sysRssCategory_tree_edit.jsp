<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<template:include ref="config.edit" sidebar="no">
<template:replace name="toolbar">
<div style="width:100%;height:38px;border-bottom:1px solid #ccc!important;margin-bottom: 10px;margin-top: 5px">
   <ui:toolbar id="toolbar" style="float:right;margin-right:15px" count="9">
	   <ui:button text="${lfn:message('button.add')}"  onclick="addCategory();" order="1" ></ui:button>
	   <ui:button text="${lfn:message('button.edit')}"  onclick="editCategory();" order="2" ></ui:button>
	   <ui:button text="${lfn:message('button.delete')}"  onclick="deleteCategory();" order="3" ></ui:button>
	   <ui:button text="${lfn:message('button.refresh')}"  onclick="location.reload();" order="6" ></ui:button>
   </ui:toolbar>
</div>
</template:replace>
<template:replace name="content">
<html:form action="/sys/rss/sys_rss_category/sysRssCategory.do">
<table class="tb_noborder" style="width:100%;">
	<tr>
		<td width="10pt"></td>
		<td>
			<div id=treeDiv class="treediv"></div>
		</td>
	</tr>
</table>
</html:form>
<%--
	采用树型结构来展现分类的维护界面。
--%>
<script type="text/javascript">
seajs.use(['sys/ui/js/dialog'], function(dialog) {
	window.dialog = dialog;
});
</script>
<script type="text/javascript">
Com_IncludeFile("treeview.js");
window.onload = generateTree;
var LKSTree;
<%-- Com_Parameter.XMLDebug = true; --%>
function generateTree() {
	LKSTree = new TreeView("LKSTree", 
		'<bean:message key="tree.rss.cateory" bundle="sys-rss"/>', 
		document.getElementById("treeDiv")
	);
	
	LKSTree.isShowCheckBox = true;
	LKSTree.isMultSel = true;//是否多选
	LKSTree.isAutoSelectChildren = false;
	LKSTree.DblClickNode = viewCategory;

	var n1;
	n1 = LKSTree.treeRoot;
	n1.AppendBeanData("sysRssCategoryTreeService&selectdId=!{value}",null,null,null);
	
	LKSTree.Show();
}
function addCategory() {
	var url = '<c:url value="/sys/rss/sys_rss_category/sysRssCategory.do" />?method=add';
	var checkedNode = LKSTree.GetCheckedNode();
	if (checkedNode.length>0){
		if (checkedNode.nodeType=="CATEGORY_SON"){
			dialog.alert('<bean:message key="error.illegalCreateCategory" bundle="sys-category"/>');
			return false;
		} else {
			var selectedId = checkedNode[0].value;
			url = Com_SetUrlParameter(url, "parentId", selectedId);
		}
	}
	Com_OpenWindow(url);
}
function editCategory() {
	var url = '<c:url value="/sys/rss/sys_rss_category/sysRssCategory.do" />?method=edit';
	if (List_CheckSelect()){
		var checkedNode = LKSTree.GetCheckedNode();
		var selectedId = LKSTree.GetCheckedNode()[0].value;
		url = Com_SetUrlParameter(url, "fdId", selectedId);
		Com_OpenWindow(url);
	}
}	
	
function deleteCategory() {
	if(List_CheckSelect()){
		dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
			if(value==true){
				var selList = LKSTree.GetCheckedNode();
				for(var i=selList.length-1;i>=0;i--){
					var input = document.createElement("INPUT");
					input.type = "text";
					input.style.display = "none";
					input.name = "List_Selected";	
					input.value = selList[i].value;
					document.sysRssCategoryForm.appendChild(input);
				}
				Com_Submit(document.sysRssCategoryForm, 'deleteall');
			}
		});
	}
}

function viewCategory(id) {
	if (id == null) return false;
	var node = Tree_GetNodeByID(this.treeRoot, id);
	if (node != null && node.value != null) {
		var url = '<c:url value="/sys/rss/sys_rss_category/sysRssCategory.do" />?method=view';
		url = Com_SetUrlParameter(url, "fdId", node.value);
		Com_OpenWindow(url);
	}
}

function List_CheckSelect(){
	if (LKSTree.GetCheckedNode().length > 0) {
		return true;
	}
	dialog.alert('<bean:message key="page.noSelect"/>');
	return false;
}

function List_ConfirmDel() {
	return List_CheckSelect() && confirm('<bean:message key="page.comfirmDelete"/>');
}
</script>
</template:replace>
</template:include>