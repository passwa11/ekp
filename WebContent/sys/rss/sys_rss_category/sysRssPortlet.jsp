<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">选择分类</template:replace>
	<template:replace name="head">
		<template:super/>
		<style type="text/css">
			.lui_listview_columntable_table tbody {
				overflow-y: auto;
				height: 200px;
			}
		</style>
	</template:replace>
	<template:replace name="body">	
	<script type="text/javascript">
	var Com_Parameter = {
		ContextPath:"${KMSS_Parameter_ContextPath}",
		ResPath:"${KMSS_Parameter_ResPath}",
		Style:"${KMSS_Parameter_Style}",
		JsFileList:new Array,
		StylePath:"${KMSS_Parameter_StylePath}",
		Lang:"<%= request.getLocale().toString().toLowerCase().replace('_', '-') %>",
		CurrentUserId:"${KMSS_Parameter_CurrentUserId}"
	};
	</script>
	<script type="text/javascript" src="${ LUI_ContextPath }/resource/js/common.js"></script>
	<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
	<script>
		function selectCategory(){
			var data = {};
			var rtnVal = new Array;
			var selectNode = LKSTree.GetCheckedNode();
			if(selectNode!=null){
				data.fdId="";data.fdName="";
				data.fdId =selectNode.value;
				data.fdName = (selectNode.text!=null && selectNode.text!="")?selectNode.text:selectNode.title;
				window.$dialog.hide(data);
			}else{
				alert("${lfn:message('button.select')}");
			}
		}
	</script>
	<script>
	function generateTree(){
		LKSTree = new TreeView(
			"LKSTree",
			"${lfn:message('sys-rss:sysRssCategory.portlet')}",
			document.getElementById("treeDiv")
		);
		LKSTree.isShowCheckBox = true;
		LKSTree.isMultSel = false;
		LKSTree.treeRoot.AppendBeanData('sysRssCategoryTreeService&portlet=true&selectdId=!{fdRssIds}', null, null, null, null);
		LKSTree.Show();
	}
	</script>
	<table class="tb_normal" style="margin:20px auto;width:95%;height:460px;">
		<tr>
			<td valign="top" style="height: 400px;">
				<div id=treeDiv class="treediv"></div>
				<script>generateTree();</script>
			</td>
		</tr>
		<tr><td align="center">
			<ui:button text="${lfn:message('button.ok')}"  onclick="selectCategory();" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<ui:button text="${lfn:message('button.delete')}" styleClass="lui_toolbar_btn_gray" onclick="window.$dialog.hide({'fdId':'','fdName':''})"></ui:button>
				&nbsp;&nbsp;&nbsp;&nbsp;
			<ui:button text=" ${lfn:message('button.close')}  " styleClass="lui_toolbar_btn_gray" onclick="window.$dialog.hide(null)"></ui:button>
		</td></tr>
	</table>
	</template:replace>
</template:include>