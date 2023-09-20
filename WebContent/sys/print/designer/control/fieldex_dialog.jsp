<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<style>
.tags{
    font: 14px/28px 'Microsoft Yahei',Arial,Lucida Grande,Tahoma;
    border: 1px #E3E0D9 solid;
    display: inline-block;
    background: #FFF;
    text-align: center;
    padding: 2px 7px;
    margin: 5px 4px;
    cursor: pointer;
    -webkit-transition: all .3s ease-in-out;
    -moz-transition: all .3s ease-in-out;
    overflow: hidden;
    color: #989898;
    width:100px;
    line-height: 20px;
}
.tags:hover {
    border-color: #00956d;
}
</style>
<script type="text/javascript">
	Com_IncludeFile("treeview.js|jquery.js");
</script>
<script type="text/javascript">
	var LKSTree,dialogObject;
	Tree_IncludeCSSFile();
	var Data_XMLCatche = dialogArguments.XMLCatche;

	//已选字段
	var selectArray=[];
	
	if(window.showModalDialog){
		dialogObject = window.dialogArguments;
	}else{
		dialogObject = opener.Com_Parameter.Dialog;
	}
	
	function generateTree() {
		LKSTree = new TreeView("LKSTree", '<bean:message bundle="sys-xform-base" key="Designer_Lang.controlChinaValue_attr_relatedControl" />', document.getElementById("treeDiv"));
		//LKSTree.isShowCheckBox=true;
		LKSTree.isMultSel=true;
		var n1, n2;
		n1 = LKSTree.treeRoot;
		
		n1.FetchChildrenNode = getVars;
		n1.isExpanded = true;
		
		LKSTree.Show();
	}
	
	function getVars(){
		var varInfo = top.dialogArguments.Parameters.varInfo;
		var noshow=top.dialogArguments.Parameters.noshow;//不再显示的字段
		for(var i=0; i<varInfo.length; i++){
			if(! contain(noshow,varInfo[i])){
				var textArr = varInfo[i].label.split(".");
				var pNode = this;
				var node;
				for(var j=0; j<textArr.length; j++){
					node = Tree_GetChildByText(pNode, textArr[j]);
					if(node==null){
						node = pNode.AppendChild(textArr[j]);
					}
					pNode = node;
				}
				node.action = function(arg){
					selectArray.push(arg);
					var span=$('<span class="tags">'+arg.name+'</span>');
					$('#selectArea').append(span);
				};
				node.parameter={
						name:node.text,
						id:varInfo[i].name,
						type:varInfo[i].type
				};
				node.value =varInfo[i].name;
				node.type=varInfo[i].type;
			}
		}
	}
	
	
	//选择关联控件
	function selectRelatedCtr(){
		var result = {};
		result.setting={};
		var layout=$('[name="layout"]:checked').val();
		var noshow=$('[name="noshow"][value="1"]:checked');
		result["layout"]=layout;
		//布局:表格
		if(layout=="table"){
			result["cols"]=$('[name="tablecols"]').val();
			result["rows"]=$('[name="tablerows"]').val();
		}
		//布局:明细表
		if(layout=="detailsTable"){}
		result["data"]=selectArray;
		if(selectArray.length>0){
			//下次不显示已选字段
			if(noshow!=null && noshow.length>0){
				result["setting"].noshowflag=true;
			}
			dialogObject.rtnData =result;
			close();
		}else{
			alert('没有选中字段呀');
		}
	}

	//是否包含
	function contain(array,item){
		for(var i=0;i<array.length;i++){
			if(item.name==array[i].id){
				return true;
			}
		}
		return false;
	}

	//添加关闭事件
	Com_AddEventListener(window, "beforeunload", function(){dialogObject.AfterShow();});
	
</script>
</head>
<body>
	<table class="tb_normal" style="margin:20px auto;margin-top: 0px;">
		<tr>
			<td valign="top" style="height: 400px;border-right-color: #303030;border-right-style: solid;border-right-width: 1px;"  width="200">
				<div id=treeDiv class="treediv"></div>
				<script>generateTree();</script>
			</td>
			<td width="10"></td>
			<td valign="top" width="500">
				<div style="text-align: left;font-size: 18px;line-height: 30px;font-weight: bold;color: #3e9ece;margin-top: 10px">已选字段</div>
				<div style="height: 100px;padding-left: 20px;" id="selectArea"></div>
				<div class="txttitle" style="text-align: left;font-size: 18px;line-height: 30px;font-weight: bold;color: #3e9ece;margin-top: 10px">布局</div>
				<div id="layout">
					<br/>
					<input type="radio"value="default"  name="layout"/>默认<br/><br/>
					<input type="radio"  checked="checked"  value="table"  name="layout"/>自动排入表格
					<input type="text" name="tablerows" value="3" style="height: 18px;border: 0px;border-bottom: 1px solid #b4b4b4;text-align: center;width: 20px;">行   
					<input type="text" name="tablecols" value="4" style="height: 18px;border: 0px;border-bottom: 1px solid #b4b4b4;text-align: center;width: 20px;">列<br/><br/>
					<input type="radio"  value="detailsTable"  name="layout"/>自动组成明细表
				</div>
				<div class="txttitle" style="text-align: left;font-size: 18px;line-height: 30px;font-weight: bold;color: #3e9ece;margin-top: 10px">更多设置</div>
				<div id="setting">
					<br/>
					<input type="checkbox" checked="checked" value="1" name="noshow">下次不再显示已选字段
				</div>
				<div></div>
				<div style="text-align: center;margin-top: 20px;">
					<input type=button class="btnopt" value="<bean:message key="button.ok"/>" onclick="selectRelatedCtr();">
    				&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btnopt" value="<bean:message key="button.cancel"/>" onClick="window.close();">
				</div>
			</td>
		</tr>
	</table>
</body>
</html>