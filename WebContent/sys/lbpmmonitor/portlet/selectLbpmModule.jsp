<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">选择模块</template:replace>
	<template:replace name="body">	
	<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
	<script>
		function selectSimpleCategory(){
			var data = {};
			var rtnVal = new Array;
			var rtnFdName = new Array;
			var selectNodes = LKSTree.GetCheckedNode();
			if(selectNodes!=null){
				selectNodes = new Array(selectNodes);
				if(selectNodes.length > 0 && selectNodes[0].length > 0 ){
					selectNodes = selectNodes[0];
					for (var i = 0; i < selectNodes.length; i++){
						rtnVal.push(selectNodes[i].value);
						data.fdName = (selectNodes[i].text!=null && selectNodes[i].text!="")?selectNodes[i].text:selectNodes[i].title;
						rtnFdName.push(data.fdName);
					}
					data.fdId = rtnVal.join(";");
					data.fdName = rtnFdName.join(";");
				} 
				window.$dialog.hide(data);
			}else{
				alert("${ lfn:message('sys-lbpmmonitor:portlet.sysLbpmMonitor.pleaseSelect') }");
			}
		}
	</script>
	<script>
	function generateTree(){
		LKSTree = new TreeView(
			"LKSTree",
			" ${ lfn:message('sys-lbpmmonitor:portlet.sysLbpmMonitor.pleaseSelect') } ",
			document.getElementById("treeDiv")
		);
		LKSTree.isShowCheckBox = true;
		LKSTree.isMultSel = true
		/* LKSTree.isAutoSelectChildren = true; */
		LKSTree.treeRoot.AppendBeanData('sysLbpmMonitorPortletService&type=${param["type"]}&value=!{value}', null, null, null, null);
		LKSTree.Show();
	}
	
	</script>
	<table class="tb_normal" style="margin:20px auto;width:95%;height:460px;">
		<tr>
			<td valign="top" style="height: 400px;">
				<div id=treeDiv class="treediv" style="height: 400px;overflow-y: scroll;"></div>
				<script>generateTree();</script>
			</td>
		</tr>
		<tr>
			<td align="center"">
				<ui:button text=" ${ lfn:message('button.ok') } " onclick="selectSimpleCategory()"></ui:button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				
				<ui:button text=" ${ lfn:message('button.cancelSelect') } " styleClass="lui_toolbar_btn_gray" onclick="window.$dialog.hide({'fdId':'','fdName':''})"></ui:button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				
				<ui:button text=" ${ lfn:message('button.close') }  " styleClass="lui_toolbar_btn_gray" onclick="window.$dialog.hide(null)"></ui:button>
			</td>
		</tr>
	</table>
	</template:replace>
</template:include>