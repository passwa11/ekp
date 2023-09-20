<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
Com_IncludeFile("common.js|calendar.js|data.js|dialog.js|jquery.js", null, "js");
var attentionEntry = "";
//初始化attentionEntry
if(top.dialogObject != null && top.dialogObject.attentionEntry){
	// 编辑
	attentionEntry = top.dialogObject.attentionEntry;
} 
//全选
function selectAll(obj){
	if(obj.checked){
		isCheckAllFlag = true;
	}else {
		isCheckAllFlag = false;
	}
	var result = new Array;
	GetAllNode(n1, result);
	for(var i = 0; i< result.length; i++){
		if(obj.checked) {
			LKSTree.SetNodeChecked(result[i], true); 
		}else{
			LKSTree.SetNodeChecked(result[i], false); 
		}
	}
}
//得到所有节点
function GetAllNode(node, result){
	for(var now=node.firstChild; now!=null; now=now.nextSibling){
		result[result.length] = now;
		GetAllNode(now, result)
	}
}
//判断是否全部选中
function isSelectAll(){
	var nodes = LKSTree.GetCheckedNode();
	var result = new Array;
	GetAllNode(n1, result);
	var checkAll = document.getElementById("checkAll");
	if(nodes.length == result.length){
		checkAll.checked = true;
	}else{
		checkAll.checked = false;
	}
}
// 替换所有字符串
String.prototype.replaceAll  = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"), s2);
};
/**
 * 获取URL中的参数（使用unescape对返回参数值解码）
 */
function GetUrlParameter_Unescape(url, param){
	var re = new RegExp();
	re.compile("[\\?&]"+param+"=([^&]*)", "i");
	var arr = re.exec(url);
	if(arr==null) {
		return null;
	} else {
		return unescape(arr[1]);
	}
}
// 将data对象的属性填充到页面的域中
function Attention_PutDataToField(data, fieldFilter){
	if(data){
		var valueArr = data.split(";");
		for(var i = 0;i < valueArr.length ; i++){
			var urlValue = valueArr[i];
			if(urlValue == null || urlValue.trim() == ''){
				continue;
			}
			var showText = GetUrlParameter_Unescape(urlValue, "showText");
			Selected_Data.AddHashMap({id:urlValue,name:showText});
		}
	}
	modifyNodeInfo(LKSTree.treeRoot, true);
	LKSTree.Show();
	refreshSelectedList();
}
function checkValueIsNull(str) {
	if(str && document.getElementsByName(str)[0] && document.getElementsByName(str)[0].value == ""){
		return true;
	}
	return false;
}
function doOK() {
	var rtnVal = Selected_Data.GetHashMapArray();
	if(rtnVal.length==0 && top.dialogObject.notNull)
		alert(Com_Parameter.DialogLang.requiredSelect);
	else
		top.Com_DialogReturn(rtnVal);
}
function dyniFrameSize() {
	try {
		// 调整高度
		var arguObj = document.getElementById("div_attention_Container");
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			window.frameElement.style.height = (arguObj.offsetHeight + 10) + "px";
			parent.resizeWindowHeight();
		}
	} catch(e) {
	}
}
Com_AddEventListener(window, "load", function(){
	Attention_PutDataToField(attentionEntry);
	isSelectAll();  //如果全部范围都勾选，则选择全选checkbox
	dyniFrameSize();
});


function optionCancel(obj,isAll){
	if(Selected_Data==null)
		return;
	if(isAll){
		Selected_Data.Clear();
	}else{
		var idObj =obj.parentNode.firstChild;
		var i = Selected_Data.IndexOf("id", idObj.innerHTML);
		i = i>-1?i:Selected_Data.IndexOf("id", idObj.innerText);
		Selected_Data.Delete(i);
	}
	var checkAll = document.getElementById("checkAll");
	checkAll.checked = false;
	refreshSelectedList();
	onSelectedDataDelete();
}
</script>
