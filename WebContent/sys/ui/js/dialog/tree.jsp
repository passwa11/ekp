<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title"><bean:message bundle="sys-ui" key="sys.tree.select"/></template:replace>
	<template:replace name="body">
	<style>
		html,body {
			overflow: hidden;
		}
	</style>
	<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
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
				alert("<bean:message bundle='sys-ui' key='sys.tree.pleaseSelect'/>");
			}
		}
	</script>
	<script>
	function generateTree(){
		var bean = "${JsParam.service}";
		LKSTree = new TreeView("LKSTree","<bean:message bundle='sys-ui' key='sys.tree.pleaseSelect'/>",document.getElementById("treeDiv")	);
		LKSTree.isShowCheckBox = true;
		LKSTree.isMultSel = false;
		LKSTree.isAutoSelectChildren = null;
		LKSTree.treeRoot.AppendBeanData(bean, null, null, null, null);
		LKSTree.Show();
	}
	</script>
	<table class="tb_normal" style="margin:20px auto;width:95%;">
		<tr>
			<td valign="top">
				<div id=treeDiv class="treediv" style="height: 365px;overflow-y:scroll"></div>
				<script>generateTree();</script>
			</td>
		</tr>
		<tr>
			<td align="center">
				<ui:button text=" ${ lfn:message('button.ok') } " onclick="onSelectOk()"></ui:button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<%--
				<ui:button text=" ${ lfn:message('button.cancelSelect') } " styleClass="lui_toolbar_btn_gray" onclick="window.$dialog.hide({})"></ui:button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				 --%>
				<ui:button text=" ${ lfn:message('button.close') }  " styleClass="lui_toolbar_btn_gray" onclick="window.$dialog.hide(null)"></ui:button>
			</td>
		</tr>
	</table>
	</template:replace>
</template:include>