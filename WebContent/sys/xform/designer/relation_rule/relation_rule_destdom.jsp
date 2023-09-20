<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
<script type="text/javascript">
	var LKSTree,dialogObject;
	Tree_IncludeCSSFile();
	var isShowCheckBox=${param.isShowCheckBox}
	//var Data_XMLCatche = dialogArguments.XMLCatche;

	if(window.showModalDialog){
		dialogObject = window.dialogArguments;
	}else{
		dialogObject = opener.Com_Parameter.Dialog;
	}
	
	function generateTree() {
		LKSTree = new TreeView("LKSTree", "${lfn:message('sys-xform-base:Designer_Lang.fieldLayout_associatedFormField')}", document.getElementById("treeDiv"));
		LKSTree.isShowCheckBox=true;
		LKSTree.isMultSel=true;
		LKSTree.DblClickNode=DblClickNodeTreeNode;
		var n1, n2;
		n1 = LKSTree.treeRoot;
		n1.FetchChildrenNode = getVars;
		n1.isExpanded = true;
		LKSTree.Show();
	}
	
	function getVars(){
		var varInfo = top.dialogObject.Parameters.varInfo;

		var valueData = dialogObject.valueData.GetHashMapArray()[0];
		
		for(var i=0; i < varInfo.length; i++){
			var dataType = varInfo[i].type;
			if (varInfo[i].label) {
				var textArr = varInfo[i].label.split(".");
				var pNode = this;
				var node;
				for(var j=0; j < textArr.length; j++){
					node = Tree_GetChildByText(pNode, textArr[j]);
					if(node == null){
						node = pNode.AppendChild(textArr[j]);
					}
					pNode = node;
				}
				if(valueData.id != ""){
					var valueDataIds= valueData.id.split(";");
					for(var j=0;j<valueDataIds.length;j++){
						if(valueDataIds[j]== varInfo[i].name){
							LKSTree.SetNodeChecked(node,true);
						}
					}
				}
				if(typeof(isShowCheckBox)=="undefined"||""==isShowCheckBox){
					isShowCheckBox=false;
				}else{
					isShowCheckBox=true;
				}
				node.value =varInfo[i].name;
				if (isDetaisTable(varInfo[i])&&!isShowCheckBox) {
					node.isShowCheckBox=false;
				}
			}
		}
	}

	function isDetaisTable(varInfo) {
	    return varInfo.controlType == "detailsTable" || varInfo.controlType == "seniorDetailsTable";
    }
	
	//选择关联控件
	function selectRelatedCtr(){
		var data = [];
		var selectNodes = LKSTree.GetCheckedNode();
		var varInfo = top.dialogObject.Parameters.varInfo;
		if(selectNodes!=null){
			for(var i=0;i<selectNodes.length;i++){
				var isSkip = false;
				if(typeof(isShowCheckBox)=="undefined"||""==isShowCheckBox){
					isShowCheckBox=false;
				}else{
					isShowCheckBox=true;
				}
				for (var j = 0; j < varInfo.length; j++) {
					if (varInfo[j].name == selectNodes[i].value 
							&& isDetaisTable(varInfo[j]) && !isShowCheckBox) {
						isSkip = true;
						break;
					}
				}
				if (!isSkip) {
					data.push({"id":selectNodes[i].value,"name":selectNodes[i].text});
				}
			}
			
			if (data.length > 0) {
				dialogObject.rtnData =data;
				close();
				dialogObject.AfterShow();
			}
		}else{
			alert('${lfn:message("sys-xform-base:Designer_Lang.fieldLayout_selectMappingField")}');
		}
		
	}
	function clearSelect(){
		var data = [];
		data.push({"id":"","name":""});
		dialogObject.rtnData =data;
		close();
		dialogObject.AfterShow();
	}
	function DblClickNodeTreeNode(){
		selectRelatedCtr();
	}

	//添加关闭事件
	//Com_AddEventListener(window, "beforeunload", function(){dialogObject.AfterShow();});
	
</script>
</head>
<body>
	<table class="tb_normal" style="margin:20px auto;width:95%;height:460px;">
		<tr>
			<td valign="top" style="height: 400px;">
				<div id=treeDiv class="treediv"></div>
				<script>generateTree();</script>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input type=button class="btnopt" value="<bean:message key="button.ok"/>" onclick="selectRelatedCtr();">
    			&nbsp;&nbsp;&nbsp;&nbsp;
    			<input type=button value="<bean:message bundle="sys-formula" key="button.clear"/>" 
					onclick="clearSelect();">
				&nbsp;&nbsp;&nbsp;&nbsp;	
				<input type="button" class="btnopt" value="<bean:message key="button.cancel"/>" onClick="window.close();">
			</td>
		</tr>
	</table>
</body>
</html>