Com_IncludeFile("calendar.js|dialog.js|validator.jsp", null, "js");
function CommitSearch(){
	var resultURL = searchOption.viewConditionInfo.resultUrl;
	var url = Com_CopyParameter(searchOption.viewConditionInfo.resultUrl);
	if (resultURL == "") url = url.replace('method=condition', 'method=result');
	var target = searchOption.viewConditionInfo.s_target;
	var pageno = "0";
	url = Com_SetUrlParameter(url, "s_target", null);
	url = Com_SetUrlParameter(url,"pageno",null);
	
	$('.criterion').each(function(i, o){
		//增加vv_xxx值到url,vv_xxx的值为1，表示该项做空值搜索
		var vv_ = document.getElementsByName("vv_"+i)[0];
		if(vv_!=null && vv_.checked)
			url = Com_SetUrlParameter(url, "vv_"+i, "1");
		
		var type = this.getAttribute("kmss_type");
		var fields = document.getElementsByName("v0_"+i);
		if(fields.length==0)
			return false;
		switch(type){
		case "string":
			if(fields[0].value!="" && fields[0].type != 'checkbox') {
				url = Com_SetUrlParameter(url, fields[0].name, fields[0].value);
			} else if (fields[0].type == 'checkbox') {
				url = setCheckedListToUrl(url, fields);
			}
		break;
		case "date":
		case "number":
			if(type=="date")
				var validateFun = isDate;
			else
				var validateFun = isNumber;
			var field1 = document.getElementsByName("v1_"+i)[0];
			var field2 = document.getElementsByName("v2_"+i)[0];
			// 增加对扩展类型支持
			if (fields[0].options == null && fields[0].type == 'checkbox') {
				url = setCheckedListToUrl(url, fields);
				break;
			}
			var logicStr = fields[0].options[fields[0].selectedIndex].value;
			if(logicStr=="bt"){
				if(field1.value=="" && field2.value=="")
					return true;
				if(field1.value=="" || field2.value==""){
					alert(searchOption.viewConditionInfo.errorsRequired.replace("{0}", $(this).find("div:first")[0].innerText||$(this).find("div:first")[0].textContent));
					return false;
				}
				if(!validateFun(field2, $(this).find("div:first")[0].innerText||$(this).find("div:first")[0].textContent))
					return false;
			}else{
				if(field1.value=="")
					return true;
			}
			if(!validateFun(field1, $(this).find("div:first")[0].innerText||$(this).find("div:first")[0].textContent))
				return;
			url = Com_SetUrlParameter(url, fields[0].name, logicStr);
			url = Com_SetUrlParameter(url, field1.name, field1.value);
			if(logicStr=="bt")
				url = Com_SetUrlParameter(url, field2.name, field2.value);
		break;
		case "foreign":
			var typeField = document.getElementsByName("v2_"+i);
			var fieldName = fields[0].name;
			var blur = false;
			if(typeField.length>0 && typeField[0].checked){
				fields = document.getElementsByName("v1_"+i);
				blur = true;
				fieldName = fields[0].name;
			}
			
			typeField = document.getElementsByName("t1_"+i);
			if(typeField.length>0 && typeField[0].checked && !blur)
				fieldName = "v2_"+i;
			
			if(fields[0].value!="")
				url = Com_SetUrlParameter(url, fieldName, fields[0].value);
		break;
		case "enum":
			url = setCheckedListToUrl(url, fields);
		break;
		}
	});
	if(window.customSearch!=null)
		url = customSearch(url);
	if(url==null)
		return;
	var i = url.indexOf("?");
	if(url.length-url.indexOf("?")>1000)
		alert(searchOption.viewConditionInfo.viewConditionToLong);
	else{
		document.getElementById("searchIframe").src = url;
	}
}
function setCheckedListToUrl(url, fields) {
	var value = "";
	for(var j=0; j<fields.length; j++){
		if(fields[j].checked)
			value += ";"+fields[j].value;
	}
	if(value!="")
		url = Com_SetUrlParameter(url, fields[0].name, value.substring(1));
	return url;
}
function resetDisplay(criterion){
	if(!criterion || criterion.length <= 0)
		return;
	var vv_ = $(criterion).find("input[id^=vv_]");
	if(vv_!=null && vv_.length > 0 && typeof vv_[0] !="undefined"){
		vv_[0].checked = false;
		refreshReadonlyDisplay(vv_[0]);
	}
	var type = criterion[0].getAttribute("kmss_type");
	var name = criterion[0].getAttribute("data_name");
	var fields = $(criterion).find("input[name^=v0_]");
	switch(type){
		case "date":
		case "number":
			var obj = $(criterion).find("input[id^=span_v2_]")[0];
			if(obj)
				obj.style.display = "none";
			break;
		case "foreign":
			fields[0].value = "";
			var obj = criterion.find("table:first")[0];
			obj.rows[0].cells[0].style.display = "";
			if(name != 'docKeyword')
			obj.rows[0].cells[1].style.display = "none";
			break;
	}
	if(window.customReset!=null)
		customReset();
	//清空搜索项
	$(criterion).find("input[name^='v'],input[name^='t']").each(function() {
		var node = this;
		if(node.type == 'checkbox' || node.type == 'radio') {
			node.checked = false;
		}else {
			node.value = '';
		}
	});
	//取消枚举的勾选
	$(criterion).find(".criterion-normal").find("li:not(.criterion-all)").each(function(){
		$(this).find("a:first").attr("class", "");
	});
	//隐藏枚举下方的"确定""取消"按钮
	$(criterion).find(".criterion-button").hide();
	
	criterion.find(".commit-action").hide();
	criterion.find(".criterion-all").find("a:first").attr("class", "selected");
	CommitSearch();
}
//枚举勾选
function enumChange(o){
	if(!$(o).attr("class")){
		$(o).attr("class", "selected");
		//取消选中“不限”
		$(o).parents("li:first").parent().find(".criterion-all").find("a:first").attr("class", "");
		//选择框实际值
		$(o).parent().find("input:first")[0].checked = "checked";
		//隐藏枚举下方的"确定""取消"按钮
		$(o).parents(".criterion-value").find(".criterion-button").show();
	}else{
		$(o).attr("class", "");
		//选择框实际值
		$(o).parent().find("input:first")[0].checked = "";
	}
}
//枚举下方确定按钮
function enumOk(o){
	CommitSearch();
}
//枚举下方取消按钮
function enumCancel(o){
	console.log($(o).parents(".criterion:first"));
	resetDisplay($(o).parents(".criterion:first"));
}
//输入框改变事件
function valueOnChange(o){
	if($(o).val()){
		//显示“确定”
		$(o).parents("li:first").find(".commit-action").show();
		//取消选中“不限”
		$(o).parents("li:first").parent().find(".criterion-all").find("a:first").attr("class", "");
	}else{
		//隐藏“确定”
		$(o).parents("li:first").find(".commit-action").hide();
		//选中“不限”
		$(o).parents("li:first").parent().find(".criterion-all").find("a:first").attr("class", "selected");
	}
}
$(function(){
	//输入框 注册keyup事件
	$(".criteria-input-text").find("input:first").each(function(){
		$(this).keyup(function(event) {
			valueOnChange(this);
		});
		$(this).change(function(event) {
			valueOnChange(this);
		});
	});

	//时间控件注册发生改变事件
	$(".lui_criteria_date_span").find("input:first").each(function(){
		$(this).change(function(event) {
			valueOnChange(this);
		});
	});
	
	//确定按钮
	$(".commit-action").bind("click", CommitSearch);
	
});
function refreshMatchDisplay(obj){
	for(var tbObj=obj; tbObj.tagName!="TABLE"; tbObj=tbObj.parentNode);
	tbObj.rows[0].cells[0].style.display = obj.checked?"none":"";
	tbObj.rows[0].cells[1].style.display = obj.checked?"":"none";
}
function refreshLogicDisplay(obj, id){
	var spanObj = document.getElementById(id);
	if(obj.options[obj.selectedIndex].value=="bt")
		spanObj.style.display = "";
	else
		spanObj.style.display = "none";
}
function isNumber(field, message){
	if(field.value.split(".").length<3)
		if(!(/[^\d\.]/gi).test(field.value))
			return true;
	alert(searchOption.viewConditionInfo.errorsNumber.replace("{0}", message));
	field.focus();
	return false;
}
function isDate(field, message){
	viewConditionForm_DateValidations = function(){
		this.a0 = new Array(
			field.name,
			searchOption.viewConditionInfo.errorsDate.replace("{0}", message),
			new Function ("varName", "this.datePattern='" + searchOption.viewConditionInfo.dateFormat + "';  return this[varName];")
		);
	}
	return validateDate(document.forms[0]);
}
var viewConditionForm_DateValidations = null;

//add by wubing date 2007-03-01 
function refreshReadonlyDisplay(obj){
	for(var tbObj=obj; tbObj.tagName!="TR"; tbObj=tbObj.parentNode);
	tbObj.cells[1].style.display = obj.checked?"none":"";
	tbObj.cells[2].style.display = obj.checked?"":"none";
}