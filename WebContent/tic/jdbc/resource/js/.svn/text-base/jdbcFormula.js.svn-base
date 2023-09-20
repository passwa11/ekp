
/******************************************
功能：弹出公式选择对话框
参数：
	idField：id字段，可不传
	nameField：name字段，可不传
	varInfo：变量列表，格式为[{name:name, label:label, type:type},…]，可通过Formula_GetVarInfoByModelName获取数据字典中的变量
	returnType：返回值类型，为数据字典中的类型，可用String[]表示字符串类表，用Object标识不限制返回值类型
	action：回调函数
	funcs：自定义函数包，多值用;分隔
	model：给定域模型名称，用于函数等方面的过滤
******************************************/
function Formula_DialogInput(idField, nameField, varInfo, returnType, 
		action, funcs, model, dbId, sourceSql, deleteCondition){
	var dialog = new KMSSDialog();
	var funcBean = "sysFormulaFuncTree";
	if(funcs!=null)
		funcBean += "&funcs=" + funcs;
	if(model!=null)
		funcBean += "&model=" + model;
	var funcInfo = new KMSSData().AddBeanData(funcBean).GetHashMapArray();
	dialog.formulaParameter = {
			varInfo: varInfo, 
			funcInfo: funcInfo, 
			returnType: returnType || "Object",
			funcs:funcs==null?"":funcs,
			model:model==null?"":model,
			dbId : dbId,
			sourceSql : sourceSql,
			deleteCondition : deleteCondition};
	dialog.BindingField(idField, nameField);
	dialog.SetAfterShow(action);
	dialog.URL = Com_Parameter.ContextPath + "tic/jdbc/resource/js/dialog_edit2.jsp";
	dialog.Show(850, 500);
}

function DocList_GetRowFields(obj, fieldName) {
	// 测试是否是明细表内容
	if (!(/\[\d+\]/g.test(fieldName)) && !(/\.\d+\./g.test(fieldName))) {
		return document.getElementsByName(fieldName);
	}
	var optTR = $(obj).closest('tr')[0];
	if(!optTR){
		return document.getElementsByName(fieldName);
	}
	var fields = $(optTR).find('[name="'+fieldName+'"]');
	if (fields.length > 0) {
		var r = [];
		fields.each(function() {r.push(this);});
		return r;
	}
	var optTB = $(optTR).closest('table')[0];
	var tbInfo = DocList_TableInfo[optTB.id];
	if (tbInfo == null) {
		return document.getElementsByName(fieldName);
	}
	var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
	// 找到正确的对象
	var fn = fieldName.replace(/\[\d+\]/g, "[!{index}]").replace(/\.\d+\./g, ".!{index}.");
	fn = fn.replace(/!\{index\}/g, rowIndex - tbInfo.firstIndex);
	fields = $(optTR).find('[name="'+fn+'"]');
	if (fields.length > 0) {
		var r = [];
		fields.each(function() {r.push(this);});
		return r;
	}
	return null;
}
