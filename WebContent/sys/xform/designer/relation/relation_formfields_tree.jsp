<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
<script type="text/javascript">
	var LKSTree,dialogObject;
	Tree_IncludeCSSFile();
	//var Data_XMLCatche = dialogArguments.XMLCatche;
	var flag = (navigator.userAgent.indexOf('MSIE') > -1) || navigator.userAgent.indexOf('Trident') > -1;
	if(window.showModalDialog && flag){
		dialogObject = window.dialogArguments;
	}else{
		dialogObject = opener.Com_Parameter.Dialog;
	}
	
	function generateTree() {
		LKSTree = new TreeView("LKSTree", "${lfn:message('sys-xform-base:Designer_Lang.fieldLayout_associatedFormField')}", document.getElementById("treeDiv"));
		LKSTree.isShowCheckBox=true;
		LKSTree.isMultSel=false;
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
			if(!varInfo[i].label){
				continue
			}
			if(varInfo[i].controlType=='hidden'){
				continue;
			}
			var dataType = varInfo[i].type;
			//if (dataType == 'Double' || dataType == 'Double[]' ||  dataType == 'BigDecimal' || dataType == 'BigDecimal[]') {
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
				if(valueData.id != "" && valueData.id == varInfo[i].name){
					LKSTree.SetNodeChecked(node,true);
				}
				if (varInfo[i].controlType&&(varInfo[i].controlType=='detailsTable'||varInfo[i].controlType=='seniorDetailsTable')) {
                    node.isShowCheckBox=false;
                }
				node.value =varInfo[i].name;
				if (varInfo[i].controlType&&(varInfo[i].controlType=='address'||varInfo[i].controlType=='new_address')) {
					if(valueData.name==varInfo[i].label){
						LKSTree.ExpandNode(node);
						if(node.parent!=LKSTree.treeRoot){
							LKSTree.ExpandNode(node.parent);
						}
					}
					node.isShowCheckBox=false;
					node.FetchChildrenNode = setIdandName;
				}
			//}
		}
	}
	
	function setIdandName(){
		var valueData = dialogObject.valueData.GetHashMapArray()[0];
		var pNode = this;
		var node;
		node = Tree_GetChildByText(pNode, 'fdId');
		if(node == null){
			node = pNode.AppendChild('fdId');
		}
		node.value = pNode.value+'-fdId';
		if(valueData.id != "" && valueData.id == pNode.value+'-fdId'){
			LKSTree.SetNodeChecked(node,true);
		}
		var node2;
		node2 = Tree_GetChildByText(pNode, 'fdName');
		if(node2 == null){
			node2 = pNode.AppendChild('fdName');
		}
		node2.value = pNode.value+'-fdName';
		if(valueData.id != "" && valueData.id == pNode.value+'-fdName'){
			LKSTree.SetNodeChecked(node2,true);
		}
	}
	
	//选择关联控件
	function selectRelatedCtr(){
		var varInfo =top.dialogObject.Parameters.varInfo;
		var data = [];
		var selectNodes = LKSTree.GetCheckedNode();
		var bool = false;
		var parentName = '';
		for(var i=0; i < varInfo.length; i++){
			if(selectNodes!=null&&(selectNodes.value==varInfo[i].name+'-fdId'||selectNodes.value==varInfo[i].name+'-fdName')&&varInfo[i].name!='fdName'&&varInfo[i].name!='fdId'){
				bool=true;
				parentName = varInfo[i].label;
			}
		}
		if(selectNodes!=null){
			if(bool){
				data.push({"id":selectNodes.value,"name":parentName.replace(/\r\n|\n/g,"")});
			}else{
				data.push({"id":selectNodes.value,"name":selectNodes.text.replace(/\r\n|\n/g,"")});
			}
			dialogObject.rtnData =data;
			close();
			dialogObject.AfterShow();
			
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