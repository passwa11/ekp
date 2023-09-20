<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.category.interfaces.CategoryUtil" %>
<template:include ref="config.edit" sidebar="no">
<template:replace name="toolbar">
   <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="9">
	   <ui:button text="${lfn:message('button.add')}"  onclick="addCategory();" order="1" ></ui:button>
	   <ui:button text="${lfn:message('button.edit')}"  onclick="editCategory();" order="2" ></ui:button>
	   <ui:button text="${lfn:message('button.delete')}"  onclick="deleteCategories();" order="3" ></ui:button>
	   <ui:button text="${lfn:message('button.refresh')}"  onclick="location.reload();" order="6" ></ui:button>
   </ui:toolbar>
</template:replace>
<template:replace name="content">
<html:form action="/sys/bookmark/sys_bookmark_public_category/sysBookmarkPublicCategory.do">
</html:form>
<table class="tb_noborder" style="width:100%;">
	<tr>
		<td width="10pt"></td>
		<td>
			<div id=treeDiv class="treediv"></div>
		</td>
	</tr>
</table>
<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
<script>
window.onload = generateTree;
var LKSTree;
Tree_IncludeCSSFile();
function generateTree()
{
	LKSTree = new TreeView("LKSTree", 
		'<bean:message key="cate.tree.root" bundle="sys-bookmark"/>', 
		document.getElementById("treeDiv")
	);
	LKSTree.isShowCheckBox=true;
	LKSTree.isMultSel=true;
	LKSTree.isAutoSelectChildren = false;
	LKSTree.DblClickNode = viewCategory;
	
	var n1;
	n1 = LKSTree.treeRoot;
	n1.AppendBeanData("sysBookmarkCategoryTreeService&parentId=!{value}&type=public");
	
	LKSTree.Show();
}

function addCategory(){
	var url = '<c:url value="/sys/bookmark/sys_bookmark_public_category/sysBookmarkPublicCategory.do" />?method=add';
	var checkedNode = LKSTree.GetCheckedNode();
	if(checkedNode.length > 0){
		if(checkedNode[0].nodeType=="CATEGORY_SON"){
			alert("<bean:message key="error.illegalCreateCategory" bundle="sys-category"/>");
			return false;
		} else {
			var selectedId = checkedNode[0].value;
			url = Com_SetUrlParameter(url, "parentId", selectedId);
		}
	}
	Com_OpenWindow(url);
}

function editCategory(){
	var url = '<c:url value="/sys/bookmark/sys_bookmark_public_category/sysBookmarkPublicCategory.do" />?method=edit';
	if(List_CheckSelect()){
		var selectedId = LKSTree.GetCheckedNode()[0].value;
		url = Com_SetUrlParameter(url, "fdId", selectedId);
		Com_OpenWindow(url);
	}
}

function viewCategory(id) {
	if(id==null) return false;
	var node = Tree_GetNodeByID(this.treeRoot,id);
	if(node!=null && node.value!=null) {
		var url = '<c:url value="/sys/bookmark/sys_bookmark_public_category/sysBookmarkPublicCategory.do" />?method=view';
		url = Com_SetUrlParameter(url, "fdId", node.value);
		Com_OpenWindow(url);
	}
}

function deleteCategories(){
	if(!List_ConfirmDel())return;
	var selList = LKSTree.GetCheckedNode();
	var vals = selList.map(function(item,index) {
		return item.value;
	});
	var url = '<c:url value="/sys/bookmark/sys_bookmark_public_category/sysBookmarkPublicCategory.do" />?method=hasSubCategory';
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
		$.get(url,{selects:vals.join(',')},function(rtnVal) {
			if(rtnVal=='true') {
				dialog.alert('<bean:message bundle="sys-bookmark" key="sysBookmarkCategory.delete.alert"/>');
			}else {
				_addCheckedNodetToForm();
				Com_Submit(document.sysBookmarkPublicCategoryForm, 'deleteall');
			}
		});
	});
}

function _addCheckedNodetToForm() {
	var sls = document.getElementsByName('List_Selected');
	for (var i = 0; i < sls.length; i ++) {
		var p = sls[i].parentNode;
		p.removeChild(sls[i]);
	}
	var selList = LKSTree.GetCheckedNode();
	for(var i=selList.length-1;i>=0;i--){
		var input = document.createElement("INPUT");
		input.type="text";
		input.style.display="none";
		input.name="List_Selected";	
		input.value = selList[i].value;
		document.sysBookmarkPublicCategoryForm.appendChild(input);	
	}
}


function List_CheckSelect(){
	var obj = document.getElementsByName("List_Selected");
	if(LKSTree.GetCheckedNode().length>0){
		return true;
	}
	alert('<bean:message key="page.noSelect"/>');
	return false;
}
function List_ConfirmDel(){
	return List_CheckSelect() && confirm("<bean:message key="page.comfirmDelete"/>");
}
if (window.Datainit_Submit) {
	var old_Datainit_Submit = window.Datainit_Submit;
	Datainit_Submit = function() {
		_addCheckedNodetToForm();
		old_Datainit_Submit();
	}
}
</script>
<input type="hidden" name="List_Selected" />
</template:replace>
</template:include>