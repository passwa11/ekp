<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
Com_IncludeFile("common.js|calendar.js|data.js|dialog.js|jquery.js", null, "js");
var relationEntry = {};
//初始化relationEntry
if(top.dialogObject == null || top.dialogObject.relationEntry==null
		|| parent.SysRelation_IsChangeType){
	// 新建，修改类型
	relationEntry = createRelationEntry();
} else {
	// 编辑
	relationEntry = top.dialogObject.relationEntry;
}
function createRelationEntry(){
	return {fdId:"<%=com.landray.kmss.util.IDGenerator.generateID()%>",fdType:"5",fdModuleName:"<bean:message bundle="sys-relation" key="sysRelationEntry.fdType5" />",fdSearchScope:"",fdIsTemplate:"true",docStatus:"30;20;00"};
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
//清空字段
function clearField(fields){
	if (!fields) {
		return;
	}
	var fieldArr = fields.split(";");
	for (var i = 0, len = fieldArr.length; i < len; i++) {
		document.getElementsByName(fieldArr[i])[0].value="";
	}
}
// 替换所有字符串
String.prototype.replaceAll  = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"), s2);
};
// 从页面的域中读取域值，并赋给对象的某个属性中
function Relation_GetDataFromField(data, propertyFilter, obj){
	var fdSearchScope = "", _selectLis = document.getElementsByClassName("ul_cells_li");
	for (var i = 0, len = _selectLis.length; i < len; i++) {
		var value = _selectLis[i].firstChild;
		var text = value.nextSibling;
		fdSearchScope += value.innerHTML + ":" + text.innerHTML + ";";
	}
	data["fdSearchScope"] = fdSearchScope;
	
	var docStatus = "",_docStatus = document.getElementsByName("docStatus");
	for(var j = 0 ; j < _docStatus.length; j++){
		if(_docStatus[j].checked){
			docStatus += _docStatus[j].value + ";";
		}
	}
	data["docStatus"] = docStatus;
	
	var _fdDiffusionAuth = document.getElementsByName("fdDiffusionAuth")[0];
	if(_fdDiffusionAuth != null && typeof(_fdDiffusionAuth) != "undefined" ){
		var fdDiffusionAuth = "" ;
		if(_fdDiffusionAuth.checked){
			data["fdDiffusionAuth"] = "true";
		}else {
			data["fdDiffusionAuth"] = "false";
		}
	}
	
}
// 将data对象的属性填充到页面的域中
function Relation_PutDataToField(data, fieldFilter){
	for(var o in data){
		var fieldName = o;
		if(fieldFilter!=null)
			fieldName = fieldFilter(fieldName);
		if(fieldName==null)
			continue;
		if("fdSearchScope" == fieldName){
			var value = data[o];
			var valueArr = value.split(";");
			for(var i = 0;i < valueArr.length ; i++){
				if(valueArr[i].trim() == '' || valueArr[i] == null){
					continue;
				}
				var nodeArr = valueArr[i].split(":");
				Selected_Data.AddHashMap({id:nodeArr[0], name:nodeArr[1]});
			}
			modifyNodeInfo(LKSTree.treeRoot, true);
			LKSTree.Show();
			refreshSelectedList();
			continue;
		}
		if("fdDiffusionAuth" == fieldName){
			var value = data[o];
			if("true" == value){
				var _fdDiffusionAuth = document.getElementsByName("fdDiffusionAuth")[0];
				if(_fdDiffusionAuth != null && typeof(_fdDiffusionAuth) != "undefined" ){
					_fdDiffusionAuth.checked = true;
				}
			}
			continue;
		}
		if("docStatus" == fieldName){
			var value = data[o];
			var valueArr = value.split(";");
			var _docStatus = document.getElementsByName("docStatus");
			for(var i = 0 ; i < valueArr.length; i++){
				for(var j = 0; j<_docStatus.length;j++){
					if (_docStatus[j].value && valueArr[i] && (_docStatus[j].value == valueArr[i])) {
						_docStatus[j].checked = true;
						break;
					}
				}
			}
			continue;
		}
	}
}
function checkValueIsNull(str) {
	if(str && document.getElementsByName(str)[0] && document.getElementsByName(str)[0].value == ""){
		return true;
	}
	return false;
}
function doOK() {
	var flag = true, fdSearchScope = "", _F_SelectedList = document.getElementsByTagName("li");
	for (var i = 0, len = _F_SelectedList.length; i < len; i++) {
		if(flag) {
			flag = false;
		}
		fdSearchScope += _F_SelectedList[i].firstChild.innerHTML + ";";
	}
	if (flag) {// 搜索范围
		// 如果没有选择“搜索范围”，这里提示是否要删除“文档关联”配置
		if(confirm('<bean:message key="sys-relation:sysRelationEntry.fdSearchScope.empty.confirm" />')) {
			// 删除配置
			relationEntry.fdSearchScope = "";
			Relation_GetDataFromField(relationEntry);
		} else {
			// 返回
			return;
		}
	} else {
		if(fdSearchScope.length - 1 > 4000) {//搜索范围长度不能大于4000
			alert('<bean:message bundle="sys-relation" key="sysRelationEntry.ftsearch.searchScope.tooLong" />');
			return ;
		}
		Relation_GetDataFromField(relationEntry);
	}
	top.Com_DialogReturn(relationEntry);
	
	return false;
	
}
function dyniFrameSize() {
	try {
		// 调整高度
		var arguObj = document.getElementById("div_relation_Container");
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			window.frameElement.style.height = (arguObj.offsetHeight + 10) + "px";
			parent.resizeWindowHeight();
		}
	} catch(e) {
	}
}
Com_AddEventListener(window, "load", function(){
	Relation_PutDataToField(relationEntry);
	isSelectAll();  //如果全部范围都勾选，则选择全选checkbox
	dyniFrameSize();

	var auth_checkbox = document.getElementById("fdDiffusionAuth_checkbox");
	if(auth_checkbox)
		auth_checkbox.checked = ("true" == relationEntry.fdDiffusionAuth);
});


function optionCancel(obj,isAll){
	if(Selected_Data==null)
		return;
	if(isAll){
		Selected_Data.Clear();
	}else{
		var idObj =obj.parentNode.firstChild;
		var i = Selected_Data.IndexOf("id", idObj.innerHTML);
		Selected_Data.Delete(i);
	}
	var checkAll = document.getElementById("checkAll");
	checkAll.checked = false;
	refreshSelectedList();
	onSelectedDataDelete();
}
</script>
