<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.category.interfaces.CategoryUtil" %>
<template:include ref="config.edit" sidebar="no">
<template:replace name="toolbar">
<div style="width:100%;height:38px;border-bottom:1px solid #ccc!important;margin-bottom: 10px;margin-top: 5px">
   <ui:toolbar id="toolbar" style="float:right;margin-right:15px" count="9">
	   <ui:button text="${lfn:message('button.add')}"  onclick="addCategory();" order="1" ></ui:button>
	   <ui:button text="${lfn:message('button.edit')}"  onclick="editCategory();" order="2" ></ui:button>
	   <ui:button text="${lfn:message('button.delete')}"  onclick="deleteCategories();" order="3" ></ui:button>
	   <ui:button text="${lfn:message('button.copy')}"  onclick="copyCategory();" order="4" ></ui:button>
	   <ui:button text="${lfn:message('button.refresh')}"  onclick="location.reload();" order="6" ></ui:button>
   </ui:toolbar>
</div>
</template:replace>
<template:replace name="content">
<html:form action="/sys/category/sys_category_property/sysCategoryProperty.do">
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
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message key="tree.sysCategoryProperty.title" bundle="sys-category"/>", document.getElementById("treeDiv"));
	LKSTree.isShowCheckBox=true;
	LKSTree.isMultSel=false;
	LKSTree.isAutoSelectChildren = false;
	LKSTree.DblClickNode = viewCategory;
	var n1, n2;
	n1 = LKSTree.treeRoot;
	n1.authType = "01";
	n2 = n1.AppendPropertyData(null,false);
	LKSTree.Show();
}

function addCategory(){
	var url = "<c:url value="/sys/category/sys_category_property/sysCategoryProperty.do" />?method=add";
	if(LKSTree.GetCheckedNode() != null){
		var selectedId = LKSTree.GetCheckedNode().value;
		var selectedName = LKSTree.GetCheckedNode().text;
		url = Com_SetUrlParameter(url, "parentId", selectedId);
		//url = Com_SetUrlParameter(url, "parentName", selectedName);
	}
	Com_OpenWindow(url);
}

function editCategory(){
	var url = "<c:url value="/sys/category/sys_category_property/sysCategoryProperty.do" />?method=edit";
	if(List_CheckSelect()){
		var selectedId = LKSTree.GetCheckedNode().value;
		url = Com_SetUrlParameter(url, "fdId", selectedId);
		Com_OpenWindow(url);
	}
}

function deleteCategories(){
	if(!List_ConfirmDel())return;
	var input = document.createElement("INPUT");
	input.type="text";
	input.style.display="none";
	input.name="List_Selected";
	input.value=LKSTree.GetCheckedNode().value
	document.sysCategoryPropertyForm.appendChild(input);
	Com_Submit(document.sysCategoryPropertyForm, 'deleteall');	
}

function List_CheckSelect(){
	var obj = document.getElementsByName("List_Selected");
	if(LKSTree.GetCheckedNode() != null){
		return true;
	}
	alert("<bean:message key="page.noSelect"/>");
	return false;
}
function List_ConfirmDel(){
	return List_CheckSelect() && confirm("<bean:message key="page.comfirmDelete"/>");
}

function copyCategory(){
	var url = "<c:url value="/sys/category/sys_category_property/sysCategoryProperty.do" />?method=copy";
	if(List_CheckSelect()){
		var selectedId = LKSTree.GetCheckedNode().value;
		url = Com_SetUrlParameter(url, "fdCopyId", selectedId);
		Com_OpenWindow(url);
	}
}

function viewCategory(id) {
	if(id==null) return false;
	var node = Tree_GetNodeByID(this.treeRoot,id);
	if(node!=null && node.value!=null) {
		var url = "<c:url value="/sys/category/sys_category_property/sysCategoryProperty.do" />?method=view&fdId="+node.value;
		Com_OpenWindow(url);
	}
	
}
</script>
</html:form>
</template:replace>
</template:include>