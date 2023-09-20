<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
<script type="text/javascript">
	var LKSTree,dialogObject;
	Tree_IncludeCSSFile();
	//var Data_XMLCatche = dialogArguments.XMLCatche;

	if(window.showModalDialog){
		dialogObject = window.dialogArguments;
	}else{
		dialogObject = opener.Com_Parameter.Dialog;
	}
	
	function generateTree() {
		LKSTree = new TreeView("LKSTree", "${lfn:message('sys-xform-base:Designer_Lang.controlNew_AddressFormField')}", document.getElementById("treeDiv"));
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
			var dataType = varInfo[i].type;
			var controlType = varInfo[i].controlType;
			if (controlType == 'inputText' || controlType == 'textarea' || controlType == 'inputCheckbox' 
				|| controlType == 'inputRadio' || controlType == 'select'||controlType=='datetime') {
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
				//node.isShowCheckBox=false;
				node.value =varInfo[i].name;
			}
		}
	}
	
	//选择关联控件
	function selectRelatedCtr(){
		var data = [];
		var selectNodes = LKSTree.GetCheckedNode();
		if(selectNodes!=null){
			data.push({"id":selectNodes.value,"name":selectNodes.text.replace(/\r\n|\n/g,"")});
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
	<label style="font-size:10pt;color: blue;">
		<bean:message bundle="sys-xform-base" key="Designer_Lang.controlNew_AddressFormfieldsMsg"/>
	</label>
	<table class="tb_normal" style="margin:20px auto;width:95%;height:400px;">
		<tr>
			<td valign="top" style="height: 350px;">
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