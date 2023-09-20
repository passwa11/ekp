<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">选择</template:replace>
	<template:replace name="body">
	<style>
		html,body {
			overflow: hidden;
		}
	</style>
	<script type="text/javascript">Com_IncludeFile("treeview.js|data.js");</script>
	<script>
		function onSelectOk(){
			var data = {};
			var rtnVal = new Array;
			var selectNodes = LKSTree.GetCheckedNode();
			if(selectNodes!=null){
				selectNodes = new Array(selectNodes);
				//debugger;
				if(selectNodes.length > 0){
					data.value = selectNodes[0].value;
					data.text = (selectNodes[0].text!=null && selectNodes[0].text!="")?selectNodes[0].text:selectNodes[0].title;					
				} 
				window.$dialog.hide(data);
			}else{
				alert("请选择");
			}
		}
		function setDefArea(){
			var selectNodes = LKSTree.GetCheckedNode();
			if(selectNodes!=null){
				selectNodes = new Array(selectNodes);
				//debugger;
				if(selectNodes.length > 0){
					var data = new KMSSData();
					data.SendToBean('sysAuthDefaultAreaService&selectId='+selectNodes[0].value, function () {
						alert("设置成功");
						if(document.getElementById('defArea'))
							document.getElementById('defArea').innerHTML = (selectNodes[0].text!=null && selectNodes[0].text!="")?selectNodes[0].text:selectNodes[0].title;
						window.$dialog.hide();
					});			
				} 
			}else{
				alert("请选择");
			}
		}
		//选择默认场所后的回调函数
		function afterDefAreaSelect(rtnData){ 
			if(rtnData && rtnData.data && rtnData.data[0]) {
				
			}
		}
	</script>
	<script>
	function generateTree(){
		var bean = "${ JsParam['service'] }";
		LKSTree = new TreeView("LKSTree","请选择",document.getElementById("treeDiv")	);
		LKSTree.isShowCheckBox = true;
		LKSTree.isMultSel = false;
		LKSTree.isAutoSelectChildren = null;
		LKSTree.treeRoot.AppendBeanData(bean, null, null, null, null);
		LKSTree.Show();
	}
	</script>
	<table class="tb_normal" style="margin:20px auto;width:100%;">
		<tr>
			<td valign="top">
				<div id=treeDiv class="treediv" style="height: 365px;overflow-y:scroll"></div>
				<script>generateTree();</script>
			</td>
		</tr>
		<tr>
			<td align="center">
				<ui:button text="${lfn:message('button.ok')}" onclick="onSelectOk()"></ui:button>
				&nbsp;&nbsp;
				<ui:button text="设为默认场所" onclick="setDefArea()"></ui:button>&nbsp;&nbsp;
				<ui:button text=" ${lfn:message('button.close')}" styleClass="lui_toolbar_btn_gray" onclick="window.$dialog.hide(null)"></ui:button>
			</td>
		</tr>
	</table>
	</template:replace>
</template:include>