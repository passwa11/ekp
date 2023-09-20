<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">${lfn:message('button.select')}</template:replace>
	<template:replace name="body">
	<script type="text/javascript">Com_IncludeFile("treeview.js|data.js");</script>
	<script>
		function onSelectOk(){
			var datas = [];
			var rtnVal = new Array;
			var selectNodes = LKSTree.GetCheckedNode();
			if(selectNodes!=null){
				selectNodes = new Array(selectNodes)[0];
				for(var i=0;i<selectNodes.length;i++){
					var data={};
					data.name= selectNodes[i].value;
					data.title= (selectNodes[i].text!=null && selectNodes[i].text!="")?selectNodes[i].text:selectNodes[i].title;		
					if(selectNodes[i].parent.value)
						data.parentId= selectNodes[i].parent.value;
					datas.push(data);
				} 
				window.$dialog.hide(datas);
			}else{
				alert("请选择");
			}
		}
	</script>
	<script>
	function getFormFieldForRest(){
		var result= new Array();
		var rtn= parent.transFormFieldList();
		for(var i=0;i<rtn.length;i++){
			if(!rtn[i].isTemplateRow){
				var child=[];
				if(rtn[i].businessType=="arrayObject"){
					for(var j=0;j<rtn.length;j++){
						if(rtn[j].isTemplateRow&&rtn[j].id.indexOf(rtn[i].id)>-1){
							child.push(rtn[j]);
						}
					}
					rtn[i].children=child;
				}
				result.push(rtn[i]);
			}
		}
		return result;
	}
	function generateTree(){
		LKSTree = new TreeView("LKSTree","请选择",document.getElementById("treeDiv")	);
		LKSTree.isShowCheckBox = true;
		LKSTree.isMultSel = true;
		LKSTree.isAutoSelectChildren = null;
		var rtn=getFormFieldForRest();
		appendNode(LKSTree.treeRoot,rtn);
		LKSTree.Show();
	}
	function appendNode(node,rtn){
		for(var i=0;i<rtn.length;i++){
			var childNode = new TreeNode(rtn[i].label, null, null, rtn[i].id,rtn[i].label, null);
			node.AddChild(childNode);
			if(rtn[i].children){
				  appendNode(childNode,rtn[i].children);
			}
		}
	}
	</script>
	<table class="tb_normal" style="width:100%;">
		<tr>
			<td valign="top">
				<div id=treeDiv class="treediv" style="overflow-y:scroll"></div>
				<script>generateTree();</script>
			</td>
		</tr>
	</table>
	</template:replace>
</template:include>