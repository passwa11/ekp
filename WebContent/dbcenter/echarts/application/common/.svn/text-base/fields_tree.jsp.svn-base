<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
	Com_IncludeFile("treeview.js");
	Com_IncludeFile("jquery.js");
	Com_IncludeFile('userInfo.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
</script>
<script type="text/javascript">
	var LKSTree,dialogObject;
	Tree_IncludeCSSFile();

	if(window.showModalDialog){
		dialogObject = window.dialogArguments;
	}else{
		dialogObject = opener.Com_Parameter.Dialog;
	}
	
	function generateTree() {
		LKSTree = new TreeView("LKSTree", "${ lfn:message('dbcenter-echarts-application:dbEchartsNavTree.chooseField') }", document.getElementById("treeDiv"));
		LKSTree.isShowCheckBox=true;
		LKSTree.isMultSel=false;
		LKSTree.DblClickNode=DblClickNodeTreeNode;
		var n1;
		n1 = LKSTree.treeRoot;
		n1.isExpanded = true;
		
		var varInfos = dialogObject.data;//[{"text":xxxx,vars:[{'field':'' ,'fieldText':'','fieldType':''}],'braces':}] 
		// 显示所有节点
		for(var i = 0;i < varInfos.length;i++){
			var varInfo = varInfos[i]; // {"text":xxxx,vars:[{'field':'' ,'fieldText':'','fieldType':''}],'braces':返回值是否需要花括号} 
			var temp = n1.AppendChild(varInfo.text);
			temp.braces = varInfo.braces;
			temp.FetchChildrenNode = (function(v,node){
				// this为当前节点
				return function(){getVars.call(node,v)};
			})(varInfo.vars,temp);
			// 第一个节点展开
			if(i == 0){
				temp.isExpanded = true;
			}
		}
		
		LKSTree.Show();
	}
	
	// 默认展开
	function getVars(vars){
		var pNode = this;
		for(var i=0; i < vars.length; i++){
			buildItemNode(vars[i], pNode);
		}
	}
	
	function buildItems(){
		var items = userInfo.getItems("${param.inputType}",this.fieldType);
		var pNode = this;
		for(var i = 0;i < items.length;i++){
			buildItemNode(items[i], pNode);
		}
	}
	
	// item:{'field':节点值 ,'fieldText':节点名称,'fieldType':字段类型}
	function buildItemNode(item,parentNode){
		var node;
		var specialVal = "authElementAdmins"; // 屏蔽管理员
		if(item.field == specialVal){
			return;
		}
		var fieldText = item.fieldText;
		node = Tree_GetChildByText(parentNode, fieldText);
		if(node == null){
			node = parentNode.AppendChild(fieldText);
		}
		var itemVal = item.field;
		var rnTxt = fieldText;
		if(parentNode){
			node.braces = parentNode.braces;	
		}
		
		if(parentNode.value && parentNode.value != ''){
			itemVal = parentNode.value + "." + item.field;
			rnTxt = parentNode.rnTxt + "." + fieldText;
		}
		node.rnTxt = rnTxt;
		node.value = itemVal;
		var fieldType = item.fieldType;
		node.fieldType = fieldType;
		if (item.controlType && (item.controlType == 'address' || item.controlType == 'new_address')) {
			node.isShowCheckBox = false;
			node.FetchChildrenNode = buildAddressNode;
		}else if(fieldType.indexOf("com.landray.kmss") > -1){
			node.isShowCheckBox = false;
			node.FetchChildrenNode = buildItems;
		}
	}
	
	function buildAddressNode(){
		buildItemNode({'field':"id" ,'fieldText':'ID','fieldType':'String'},this);
		buildItemNode({'field':"name" ,'fieldText':"${ lfn:message('dbcenter-echarts-application:dbEchartsNavTree.name') }",'fieldType':'String'},this);
	}
	
	//选择关联控件
	function selectRelatedCtr(){
		var selectNode = LKSTree.GetCheckedNode();
		if(selectNode != null){
			var rn = {};
			if(selectNode.braces){
				rn.value = "{" + selectNode.value + "}";	
			}else{
				rn.value = selectNode.value;				
			}
			
			rn.text = selectNode.rnTxt;
			close();
			if(dialogObject.AfterShow){
				dialogObject.AfterShow(rn);	
			}
		}else{
			alert("${lfn:message('sys-xform-base:Designer_Lang.fieldLayout_selectMappingField')}");
		}
		
	}
	function clearSelect(){
		var rn = {};
		rn.value = "";
		rn.text = "";
		close();
		if(dialogObject.AfterShow){
			dialogObject.AfterShow(rn);	
		}
	}
	function DblClickNodeTreeNode(){
		selectRelatedCtr();
	}

	
</script>
</head>
<body>
	<table class="tb_normal" style="margin:20px auto;width:95%;height:100%;">
		<tr>
			<td valign="top" style="height: 300px;">
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