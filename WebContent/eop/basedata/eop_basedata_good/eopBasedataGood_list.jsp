<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
.lui_tree_operation{
   width:100%;
   height:38px;
   border-bottom:1px solid #ccc!important;
   margin-bottom: 10px;
   padding-top: 5px;
   background-color: #eee;
}
</style>
<template:include ref="config.edit" sidebar="no">
<template:replace name="toolbar">
<div  class="lui_tree_operation">
   <ui:toolbar id="toolbar" style="float:right;margin-right:15px" count="9">
	   <ui:button text = "${lfn:message('eop-basedata:eopBasedata.button.showList')}" onclick="showList();" order="1"></ui:button>
<kmss:auth requestURL="/eop/basedata/eop_basedata_good/eopBasedataGood?method=add">
<ui:button text = "${lfn:message('button.add')}" onclick="addCategory();" order="1"></ui:button>
</kmss:auth>
<kmss:auth requestURL="/eop/basedata/eop_basedata_good/eopBasedataGood?method=edit">
<ui:button text="${lfn:message('button.edit')}"  onclick="editCategory();" order="2" ></ui:button>
</kmss:auth>
<kmss:auth requestURL="/eop/basedata/eop_basedata_good/eopBasedataGood?method=deleteall">
<ui:button text="${lfn:message('button.deleteall')}"  onclick="deleteCategories();" order="3" ></ui:button>
</kmss:auth><ui:button text="${lfn:message('button.refresh')}" onclick="location.reload();" order="4" ></ui:button>
	</ui:toolbar>
</div>
<ui:fixed elem=".lui_tree_operation"></ui:fixed>
</template:replace>
<template:replace name="content">
<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
<script>
	window.onload = generateTree;
	var actionUrl = "<c:url value="/eop/basedata/eop_basedata_good/eopBasedataGood.do"/>";
	var LKSTree;
	<%--打开页面展开分类树--%>
	function generateTree(){
		LKSTree = new TreeView(
			"LKSTree",
			"<bean:message key='treeModel.alert.templateAlert' bundle='eop-basedata'/>",
			document.getElementById("treeDiv")
		);
		LKSTree.isShowCheckBox=true;  			<%-- 是否显示单选/复选框 --%>
		LKSTree.isMultSel=true;					<%-- 是否多选 --%>
		LKSTree.isAutoSelectChildren = false;	<%-- 选择父节点是否自动选中子节点 --%>
		LKSTree.DblClickNode = viewCategory;	<%-- 双击树节点事件 --%>

		var n1, n2;
		n1 = LKSTree.treeRoot;					<%-- 根节点 --%>
		n2 = n1.AppendBeanData("eopBasedataGoodService&parentId=!{value}");		<%--通过JavaBean的数据方式批量添加子结点,这里的bean填写前面的bean --%>
		LKSTree.Show();
	}


	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		<%-- 编辑 --%>
		window.editCategory = function() {
			var url = actionUrl+"?method=edit";
			if(List_CheckSelect()){
				if(LKSTree.GetCheckedNode().length==1){
					<%-- 只能选择一个类别进行编辑--%>
					var selectedId = LKSTree.GetCheckedNode()[0].value;
					url = Com_SetUrlParameter(url, "fdId", selectedId);
					Com_OpenWindow(url);
				}else{
					<%-- 选择多个类别进行编辑将提示“只能选择一个类别进行编辑，请重新选择” --%>
					dialog.alert("<bean:message key='treeModel.error.edit.select.message' bundle='eop-basedata'/>");
					return false;
				}
			}
		};

		<%-- 新建 --%>
		window.addCategory = function() {
			var url = actionUrl+"?method=add";
			var checkedNode = LKSTree.GetCheckedNode();
			if(checkedNode.length>0){
				if(LKSTree.GetCheckedNode().length==1){
					<%-- 选择一个类别进行新建将该类别ID作为父类别传入--%>
					var selectedId = checkedNode[0].value;
					url = Com_SetUrlParameter(url, "parentId", selectedId);
				}else{
					<%-- 选择多个类别进行新建将提示“只能选择一个类别作为父类别进行新建，请重新选择” --%>
					dialog.alert("<bean:message key='treeModel.error.new.select.message' bundle='eop-basedata'/>");
					return false;
				}
			}
			Com_OpenWindow(url);
		};

		<%-- 选择 --%>
		window.List_CheckSelect = function() {
			var obj = document.getElementsByName("List_Selected");
			if(LKSTree.GetCheckedNode().length>0){
				return true;
			}
			dialog.alert("<bean:message key="page.noSelect"/>");
			return false;
		};

		<%-- 删除 --%>
		window.deleteCategories = function() {
			if(!List_ConfirmDel()) {
				return;
			}
			dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
				if(value==true){
					var selList = LKSTree.GetCheckedNode();
					for(var i=selList.length-1;i>=0;i--){
						var input = document.createElement("INPUT");
						input.type="text";
						input.style.display="none";
						input.name="List_Selected";	
						input.value = selList[i].value;
						document.eopBasedataGoodForm.appendChild(input);	
					}
					Com_Submit(document.eopBasedataGoodForm, 'deleteall');	
				}
			});
			
		}

		<%-- 删除确认 --%>
		window.List_ConfirmDel = function() {
			return List_CheckSelect();
		};
	});
	

	<%-- 查看 --%>
	function viewCategory(id) {
		if(id==null) return false;
		var node = Tree_GetNodeByID(this.treeRoot,id);
		if(node!=null && node.value!=null) {
			var url = actionUrl+"?method=view&fdId="+node.value;
			Com_OpenWindow(url);
		}
	}

	function showList(){
		var url = "${LUI_ContextPath}/eop/basedata/eop_basedata_good/index.jsp";
		window.location.href = url;
	}
	

</script>
<html:form action="/eop/basedata/eop_basedata_good/eopBasedataGood.do">
	<div id="optBarDiv"> <%-- 操作条 --%>
		
	</div>
	<table class="tb_noborder"  style="width:100%;">
		<tr>
			<td width="10pt"></td>
			<td>
				<div id=treeDiv class="treediv" style="margin-bottom:50px"></div>
			</td>
		</tr>
	</table>
</html:form>

</template:replace>
</template:include>
