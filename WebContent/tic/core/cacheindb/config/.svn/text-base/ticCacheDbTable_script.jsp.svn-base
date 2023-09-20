<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>

// 删除动态表格内容行
function deleteRows(tableId){
    var optTB = document.getElementById(tableId);
    var rowLen = optTB.rows.length;
    for(var i = rowLen; i > 1; i--){
	    DocList_DeleteRow(optTB.rows[i-1]);
    }
}

// 添加处理人动态行时初始化操作下拉框
function AddRow_Init() {
	DocList_AddRow();
	var tb = document.getElementById("TABLE_DocList");
	var index = tb.rows.length-2;
}

// 设置同一行操作名称
function selectType(operType, selectObj){
	var selectText = selectObj.options[selectObj.selectedIndex].innerText||selectObj.options[selectObj.selectedIndex].textContent;
}

// 校验是否至少选择了一个通过操作
function validateForm() {
	var tb = document.getElementById("TABLE_DocList");
	var index = tb.rows.length-1;
	var fcs =[];
	for (var i = 0; i < index; i++) {
		var fdColumn = "fdColumns["+i+"].fdColumn";
		var fdColumns = document.getElementsByName(fdColumn)[0];
		fcs[i]=fdColumns.value;
	}	
	for(var i=0;i<fcs.length;i++){
		for(var j=0;j<fcs.length;j++){
			if(i!=j){
				if(fcs[i]==fcs[j]){
					alert("<bean:message key='ticCacheindb.fdColumn.unique' bundle='tic-core-cacheindb' />");
					return false;
				}
			}
		}
	}
	return true;
}

// 页面加载时判断起草人、处理人操作是否显示, 编辑页面时获取节点操作
function initialContext(){
	var fdTable = document.getElementsByName("fdTable");
	if(fdTable[0].value==""){
		fdTable[0].value="tic_cache_";
	}
}
Com_AddEventListener(window, "load", initialContext);

</script>
