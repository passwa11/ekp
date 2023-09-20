<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- czk2019 -->
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
	return {fdId:"<%=com.landray.kmss.util.IDGenerator.generateID()%>",fdType:"8",fdModuleName:"<bean:message bundle='sys-relation' key='sysRelationEntry.person' />",
		fdParameter:""};
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

// 判断DOM元素是否有值
function checkValueIsNull(str) {
	if(str && document.getElementsByName(str)[0] && document.getElementsByName(str)[0].value == ""){
		return true;
	}
	return false;
}

/**
 * 按下确定按钮
 */
function doOK() {
	var _personId = document.getElementsByName("fdPersonIds")[0];
	var _personName = document.getElementsByName("fdPersonNames")[0];
	if (checkValueIsNull("fdPersonIds") || checkValueIsNull("fdPersonNames")) {// 人员
		_personName.focus();
		alert('<bean:message key="errors.required" argKey0="sys-relation:sysRelationEntry.person" />');
		return ;
	}
    //将页面录入的值填充到relationEntry对象
	//Relation_GetDataFromField(relationEntry);
    
    var relationPersons ={} ;
    relationPersons['fdPersonIds'] = _personId.value;
    relationPersons['fdPersonNames'] = _personName.value;
	relationEntry.relationPersons = relationPersons;
	top.Com_DialogReturn(relationEntry);
}
function dyniFrameSize() {
	try {
		// 调整高度
		
		var arguObj = document.getElementById("tab_person_setting");
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			window.frameElement.style.height = (arguObj.offsetHeight + 180) + "px";
			parent.resizeWindowHeight();
		}
	} catch(e) {
	}
}
//页面初始化载入
Com_AddEventListener(window, "load", function(){
	var relationPersons = relationEntry.relationPersons;
	if(relationPersons && relationPersons!=null){
		var _personId = document.getElementsByName("fdPersonIds")[0];
		var _personName = document.getElementsByName("fdPersonNames")[0];
		_personId.value = relationPersons.fdPersonIds;
		_personName.value = relationPersons.fdPersonNames;
	} 

	dyniFrameSize();
});
</script>
